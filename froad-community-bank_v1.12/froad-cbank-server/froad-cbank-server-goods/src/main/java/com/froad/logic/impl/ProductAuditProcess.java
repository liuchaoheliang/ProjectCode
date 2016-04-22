package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.ProductDetailMongoUtil;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.db.redis.MerchantRedis;
import com.froad.db.redis.ProductRedis;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.log.GoodsLogs;
import com.froad.log.vo.HeadKey;
import com.froad.log.vo.ProductDetailLog;
import com.froad.log.vo.ProductLog;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.ProviderCommonLogic;
import com.froad.po.Outlet;
import com.froad.po.Product;
import com.froad.po.ProductTemp;
import com.froad.po.Provider;
import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductCategoryInfo;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductImage;
import com.froad.po.mq.AuditMQ;
import com.froad.service.AbstractProcessService;
import com.froad.util.Checker;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.froad.util.RedisKeyUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 商品审核的阻塞队列
 * @author ll
 *
 */
public class ProductAuditProcess extends AbstractProcessService {
	
	private MongoManager manager = new MongoManager();

	/**
	 * 商品审核：
	 * 		录入审核和编辑审核
	 * 
	 * @author ll 20151014 
	 */
	@Override
	public void processMsg(String msg) {
		LogCvt.info("处理商品审核消息:"+msg);
		//将json转换成AuditMQ请求处理对象
		AuditMQ auditMQ = JSonUtil.toObject(msg, AuditMQ.class);
		SqlSession sqlSession = null;
		ProductMapper productMapper = null;
		
		try{
			if(!ResultCode.success.getCode().equals(auditMQ.getResult().getResultCode())) {
				return;
			}
			
			String mqAuditState =  auditMQ.getAuditState();//审核状态
			
			if(ProductAuditState.passAudit.getCode().equals(mqAuditState)
        			|| ProductAuditState.failAudit.getCode().equals(mqAuditState)
        			|| ProductAuditState.noAudit.getCode().equals(mqAuditState)) {
				/********************审核信息组装 start ***************/
				
				String auditId = auditMQ.getAuditId();//审核流水号
				String productId = auditMQ.getBusinessId();//商品id
				String clientId = auditMQ.getClientId();
				String auditStaff = auditMQ.getAuditStaff();
				String auditComment = auditMQ.getAuditComment();
				
				String merchantId =null;
				String auditStage = null;
				
				Product product=new Product();
				product.setClientId(clientId);
				product.setProductId(productId);
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				productMapper = sqlSession.getMapper(ProductMapper.class);
				Product p = productMapper.getAuditingProductById(product);
				if(p==null) {
					LogCvt.error("商品"+productId+"记录不存在");
					return;
				}		
				ProductTemp pt = productMapper.getAuditingTempProductById(product);
				if(ProductAuditState.passAudit.getCode().equals(p.getAuditState()) && pt==null) {
					LogCvt.error("商品"+productId+"已经审核过");
					return;
				}	
				
				/********************处理审核信息 start***************/
				if(pt!=null) {
					merchantId = pt.getMerchantId();
					auditStage = pt.getAuditStage();
				} else {
					merchantId = p.getMerchantId();
					auditStage = p.getAuditStage();
				}
				ProductTemp auditProduct=new ProductTemp();
				auditProduct.setClientId(clientId);
				auditProduct.setMerchantId(merchantId);
				auditProduct.setProductId(productId);
				auditProduct.setAuditTime(new Date());
				auditProduct.setReviewStaff(auditStaff);
				auditProduct.setAuditComment(auditComment);
				auditProduct.setAuditId(auditId);
				
				/****************不同审核状态逻辑不同****************/
				boolean isUpdateAuditInfoToProduct = false;//是否需要将审核信息更新到主表
				boolean isUpdateAuditInfoToProductTemp = false;//是否需要将审核信息更新到临时表
				boolean isUpdateEditAuditState = false;//是否需要更新主表的子审核状态
				boolean isUpdateProductRedisAndMongo = false;//该商品之前是审核通过上过架  再次修改是否需要将临时表中的数据同步到主表，mongo，redis中
				//组装好的信息，完成将临时表的信息数据同步到主表,mongo和redis
				ProductTemp productTemp = null;
				ProductDetail pd = null;
				ProductDetail productDetail = null;
            	
				boolean isLog = false;//是否打印日志，只有更新主表信息时候需要打印
				//审核通过
				if(ProductAuditState.passAudit.getCode().equals(mqAuditState)) {//审核通过分终审和非终审
					/**
					 * 是终审 
					 * 需要更新审核状态，上架状态和修改信息到主表中
					 */
					if(auditMQ.getIsFinalAudit()) {//是终审
						isLog = true;
						
						//审核状态为审核通过
						auditProduct.setAuditStage("1");
						auditProduct.setAuditState(ProductAuditState.passAudit.getCode());
						
						/**********所属的商户是有效状态时候需要自动上架**/
						CommonLogic comLogic = new CommonLogicImpl();
						Map<String,String> merchantMap = comLogic.getMerchantRedis(clientId, merchantId);
		                if(Checker.isEmpty(merchantMap)) {
		                	LogCvt.error("商户不存在不能更新商品"+productId+"审核状态");
		                } else if(Checker.isEmpty(merchantMap.get("is_enable")) || "0".equals(merchantMap.get("is_enable"))) {
		                	LogCvt.error(merchantMap.get("merchant_name")+"商户已失效不能更新商品"+productId+"审核状态");
		                } else {
		                	auditProduct.setIsMarketable(ProductStatus.onShelf.getCode());
		                	auditProduct.setRackTime(new Date());
		                }
		                /**********所属的商户是有效状态时候需要自动上架**/
		                
		                /**
						 * 如果该商品之前是审核通过上过架 
						 * 需要更新审核信息到主表和临时表
						 * 需要将将临时表中数据同步到主表，mongo和redis中
						 */
		                if(pt!=null) {//将临时表的信息数据同步到主表,mongo和redis
		                	/*******组装信息 start***/
		                	productTemp = productMapper.getAuditingTempProductByProductId(productId);
		                	if(productTemp!=null) {
		        				isUpdateProductRedisAndMongo = true;//该商品之前是审核通过上过架  再次修改是否需要将临时表中的数据同步到主表，mongo，redis中
		                		
		                		DBObject seach = new BasicDBObject();
	    						seach.put("_id", product.getProductId());
	    						seach.put("client_id", product.getClientId());
	    						pd = manager.findOne(seach,MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
		                		productDetail = this.getMongoProductDetail(productTemp, pd);
		    					if (productDetail != null && productDetail.getIsLimit() == 1) {
		    						productTemp.setIsLimit(true);
		    					} else {
		    						productTemp.setIsLimit(false);
		    					}
		                	}
		                	/*******组装信息 end***/
		                	isUpdateAuditInfoToProduct = true;//是否需要将审核信息更新到主表
		    				isUpdateAuditInfoToProductTemp = true;//是否需要将审核信息更新到临时表
		                	auditProduct.setIsHistory(true);//更新临时表的is_history字段
		                	isUpdateEditAuditState = true;//是否需要更新主表的子审核状态
						} else {//直接将审核信息更新到主表
							isUpdateAuditInfoToProduct = true;//是否需要将审核信息更新到主表
						}
					} else {//不是终审 更新审核信息
						auditProduct.setAuditState(ProductAuditState.noAudit.getCode());
						auditProduct.setAuditStage("0");
						if(pt!=null) {
							//将审核信息更新到商品临时表
		    				isUpdateAuditInfoToProductTemp = true;//是否需要将审核信息更新到临时表
		    				isUpdateEditAuditState = true;//是否需要更新主表的子审核状态
						} else {
							//将审核信息更新到商品主表
							isUpdateAuditInfoToProduct = true;//是否需要将审核信息更新到主表
						}
					}
				} else if(ProductAuditState.failAudit.getCode().equals(mqAuditState)) {//更新审核信息
					auditProduct.setAuditState(ProductAuditState.failAudit.getCode());
					if("0".equals(auditStage)){
						auditProduct.setAuditStage("1");
						auditProduct.setReviewStaff(auditStaff);
					}else{
						auditProduct.setAuditStage("0");
						auditProduct.setAuditStaff(auditStaff);
					}
					if(pt!=null) {//更新临时表
	    				isUpdateAuditInfoToProductTemp = true;//是否需要将审核信息更新到临时表
	    				isUpdateEditAuditState = true;//是否需要更新主表的子审核状态
					} else {//更新主表
						isUpdateAuditInfoToProduct = true;//是否需要将审核信息更新到主表
					}
				} else {//已经提交审核 创建审核
					auditProduct.setAuditState(ProductAuditState.noAudit.getCode());
					auditProduct.setAuditStage("0");
					if(pt!=null) {
						//更新商品临时表
	    				isUpdateAuditInfoToProductTemp = true;//是否需要将审核信息更新到临时表
	    				isUpdateEditAuditState = true;//是否需要更新主表的子审核状态
					} else {
						//直接更新商品主表
						isUpdateAuditInfoToProduct = true;//是否需要将审核信息更新到主表
					}
					
				}
				/********************审核信息组装 end ***************/
				
				//需要将审核信息更新到主表
				if(isUpdateAuditInfoToProduct) {
					productMapper.updateAuditingProduct(auditProduct);
				}
				//需要将审核信息更新到临时表
				if(isUpdateAuditInfoToProductTemp) {
					productMapper.updateTempProduct(auditProduct);
				}
				//是否需要更新主表的子审核状态
				if(isUpdateEditAuditState) {
					Map<String,String> param = new HashMap<String,String>();
					param.put("productId", auditProduct.getProductId());
					param.put("clientId", auditProduct.getClientId());
					param.put("auditState", auditProduct.getAuditState());
					productMapper.updateProductEditAuditStatus(param);//更新主表的子审核状态
				}
				
				//该商品之前是审核通过上过架  再次修改是否需要将临时表中的数据同步到主表，mongo，redis中
				if(isUpdateProductRedisAndMongo && productTemp!=null) {//不包括store,is_marketable字段更新
					/******将临时表的基础信息数据同步到主表 start**/
					productMapper.updateAuditPassProduct(productTemp);
					/******将临时表的基础信息数据同步到主表 end**/
				}
				
				//提交mysql数据
				sqlSession.commit(true);
				
				//该商品之前是审核通过上过架  再次修改是否需要将临时表中的数据同步到主表，mongo，redis中
				if(isUpdateProductRedisAndMongo && productDetail!=null) {
					/******将临时表的基础信息数据同步到mongo start**/
					if (pd != null) {
						DBObject where = new BasicDBObject();
						where.put("_id", product.getProductId());
		                if(Checker.isNotEmpty(product.getClientId())){
		                    where.put("client_id", product.getClientId());
		                }
		                Object whereObject = where;
		                
						manager.update(productDetail, whereObject, MongoTableName.CB_PRODUCT_DETAIL, "$set");// 向mongodb更新数据
			            
						DBObject valueObj = ProductDetailMongoUtil.unsetProductDetail(productDetail);
			            if(valueObj!=null) {
			            	manager.update(valueObj, where, MongoTableName.CB_PRODUCT_DETAIL, null);//更新mongo vip_price buy_limit和is_limit
			            }
					} else {
						manager.add(productDetail, MongoTableName.CB_PRODUCT_DETAIL);// 向mongodb插入数据
					}
					/******将临时表的基础信息数据同步到mongo end**/
				}
				
				//该商品之前是审核通过上过架  再次修改是否需要将临时表中的数据同步到主表，mongo，redis中
				if(isUpdateProductRedisAndMongo) {
					/******将临时表的基础信息数据同步到redis start**/
					//商品基本信息redis缓存
					String prodcutKey = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(clientId, merchantId, productId);
					ProductRedis.del_key(prodcutKey);
					CommonLogic comLogic = new CommonLogicImpl();
					comLogic.getProductRedis(clientId, merchantId, productId);
					
//					//库存redis缓存
//	                
//	                //每个门店最大提货redis缓存
					/******将临时表的基础信息数据同步到redis end**/
				}
				
				//单独处理上架状态
				if(ProductStatus.onShelf.getCode().equals(auditProduct.getIsMarketable())) {
					ProductRedis.auditProduct(clientId, merchantId, productId, ProductStatus.onShelf.getCode());
					
					//更新mongo is_marketable
	                DBObject value = new BasicDBObject();
	                value.put("is_marketable", auditProduct.getIsMarketable());
	                value.put("rack_time", auditProduct.getRackTime().getTime());
	                DBObject where = new BasicDBObject();
	                where.put("client_id", p.getClientId());
	                where.put("_id", p.getProductId());
	                manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");//更新mongo is_marketable
				}
				
				if(isLog) {
					if(ProductAuditState.passAudit.getCode().equals(p.getAuditState()) && pt!=null) {
						//打印修改日志
		                addProductOperaLog(p.getProductId(), p.getRackTime(), p.getDownTime(), "1", null, "update");
					} else {
						//打印修改日志
		                addProductOperaLog(p.getProductId(), p.getRackTime(), p.getDownTime(), "1", null, "add");
					}
				}
				/********************处理审核信息 end***************/
			} else {
				LogCvt.debug("审核状态"+auditMQ.getAuditState()+"有误，只处理审核通过["+ProductAuditState.passAudit.getCode()+"],审核未通过["+ProductAuditState.failAudit.getCode()+"]的数据");
			}
		} catch(Exception e) {
			if (null != sqlSession)
				sqlSession.rollback(true);
			LogCvt.error("商品审核异常："+e.getMessage(),e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
	}
	
	/**
	 * 根据临时表的数据生成mongo中商品详情对象
	 * @param productTemp
	 * @param pd
	 * @return
	 */
	private ProductDetail getMongoProductDetail(ProductTemp productTemp,ProductDetail pd) {
		// 商品详情mongo
		ProductDetail productDetail = new ProductDetail(); 
		productDetail.setIsSeckill(productTemp.getIsSeckill());
        productDetail.setIsBest(BooleanUtils.toInteger(productTemp.getIsBest(), 1, 0, 0));
        productDetail.setIsLimit(BooleanUtils.toInteger(productTemp.getIsLimit(), 1, 0, 0));
        productDetail.setName(productTemp.getName());
        productDetail.setFullName(productTemp.getFullName());
        productDetail.setBriefIntroduction(productTemp.getBriefIntroduction());
        productDetail.setPrice(productTemp.getPrice());
        productDetail.setMarketPrice(productTemp.getMarketPrice());
        productDetail.setDeliveryOption(productTemp.getDeliveryOption());
        productDetail.setStartTime(productTemp.getStartTime());
        productDetail.setEndTime(productTemp.getEndTime());
        
        //图片关联信息设置
        if(Checker.isNotEmpty(productTemp.getPhotoList())) {
        	List<ProductImage> images = JSON.parseArray(productTemp.getPhotoList(), ProductImage.class);
            if(images!=null && images.size()>0){
                List<ProductImage> imageInfo = new  ArrayList<ProductImage>();
                for(ProductImage image : images){
                    imageInfo.add(image);
                }
                productDetail.setImageInfo(imageInfo);
            }
        }
        String categoryTreePath = productTemp.getCategoryTreePath();
        if(Checker.isNotEmpty(categoryTreePath)){
        	//商品分类信息设置
            List<ProductCategoryInfo> productCategoryInfo = new ArrayList<ProductCategoryInfo>();
            
        	ProductCategoryInfo productCategory = null;
        	
        	String[] pcids = categoryTreePath.split(" ");
        	if(pcids!=null && pcids.length>0) {
            	for(String pcid : pcids) {
                    productCategory = new ProductCategoryInfo();
                    if(Checker.isNotEmpty(pcid)) {
                    	productCategory.setProductCategoryId(Long.valueOf(pcid));
                    	productCategoryInfo.add(productCategory);
                    }
                }
        	}
        	productDetail.setProductCategoryInfo(productCategoryInfo);
        }
        
        //购买限制信息设置
        if(productTemp.getIsLimit() && Checker.isNotEmpty(productTemp.getBuyLimit())) {
			ProductBuyLimit buyLimit = JSON.parseObject(productTemp.getBuyLimit(), ProductBuyLimit.class);
            productDetail.setBuyLimit(buyLimit);
        }
        
        if(pd!=null) {//修改商品时创建时间，活动信息 不修改
        	// 购买限制信息设置
			ProductBuyLimit buyLimit = productDetail.getBuyLimit();
			
			if (pd.getBuyLimit() != null && pd.getBuyLimit().getMaxVip() > 0) {
				if (buyLimit != null) {
					buyLimit.setId(pd.getBuyLimit().getId());
					buyLimit.setMaxVip(pd.getBuyLimit().getMaxVip());
				} else {
					buyLimit = new ProductBuyLimit();
					buyLimit.setId(pd.getBuyLimit().getId());
					buyLimit.setMaxVip(pd.getBuyLimit().getMaxVip());
				}
			}
			productDetail.setBuyLimit(buyLimit);
			
        	productDetail.setPlatType(pd.getPlatType());
            if(pd.getCreateTime()!=null){
                productDetail.setCreateTime(pd.getCreateTime());
            }
            productDetail.setActivitiesInfo(pd.getActivitiesInfo());
            //保留VIP规则绑定关系
            productDetail.setVipPrice(pd.getVipPrice());
        } else {
        	productDetail.setProductType(productTemp.getType());
            productDetail.setId(productTemp.getProductId());
            productDetail.setPlatType(productTemp.getPlatType());
            productDetail.setClientId(productTemp.getClientId());
            productDetail.setSellCount(productTemp.getSellCount());
            productDetail.setMerchantId(productTemp.getMerchantId());
            productDetail.setMerchantName(productTemp.getMerchantName());
            productDetail.setCreateTime(new Date());
            if(ProductType.group.getCode().equals(productTemp.getType())){//团购商品的 去查询该商品所属的商户下所有有效门店所在的区冗余到市级-区集合中
            	if(Checker.isNotEmpty(productTemp.getMerchantId())){
            		CommonLogic comLogic = new CommonLogicImpl();
                    List<Outlet> outlets = comLogic.getOutletListByMerchantIdOrOutletId(productTemp.getMerchantId(), null);
                    if(outlets!=null && outlets.size()>0){
                    	//获取门店和区域 将门店所在的区冗余到市级-区和市级-门店集合中
                    	productDetail = ProductDetailMongoUtil.setAreaOutletToGroupProduct(productDetail, outlets, null);
                    }
            	}
            }
        }
        if(productDetail.getBuyLimit()!=null){
            if(productDetail.getBuyLimit().getMax()>0 
                    || productDetail.getBuyLimit().getMaxVip()>0 
                    || productDetail.getBuyLimit().getMaxCard()>0){//vip限购数量 普通限购数量 贴膜卡限购数量 3个 其中有一个有值代表限购
                productDetail.setIsLimit(1);
            } else {
                productDetail.setIsLimit(0);
            }
        } else {
            productDetail.setIsLimit(0);
        }
        return productDetail;
	}
    		
	
	/**
	 * 更新商品信息打印日志
	 * @param productId 商品id
	 * @param checkRackTime
	 * @param checkDownTime
	 * @param type
	 * @param isMarketable
	 * @param action ("update"修改) ("add"新加)
	 */
	public void addProductOperaLog(String productId,Date checkRackTime,Date checkDownTime,String type,String isMarketable,String action){
		// 打印修改日志   打印新增日志
		SqlSession sqlSession = null;
		try {
			if (Checker.isNotEmpty(productId)) {
				Product pro = new Product();
				pro.setProductId(productId);
				
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
	            List<Product> ps = productMapper.getProductByProductId(pro);
	            if(ps!=null && ps.size()>0){
	                Product product = ps.get(0);
	                if(product!=null){
	                	ProductLog log = new ProductLog();
	    				log.setTime(new Date().getTime());
	    				log.setClient_id(product.getClientId());
	    				
	    				HeadKey key = new HeadKey();
	    				key.setProduct_id(product.getProductId());
	    				log.setKey(key);
	    				
	    				ProductDetailLog data = new ProductDetailLog();
	    				data.setProduct_id(product.getProductId());
	    				data.setProduct_name(product.getName());
	    				data.setProduct_type(product.getType());
	    				data.setMerchant_id(product.getMerchantId());
	    				String category_tree_path = product.getCategoryTreePath();
	    				if(Checker.isNotEmpty(category_tree_path)){
	    					String categoryId=null;
	    					String[] pcs = category_tree_path.split(" ");
    	                    if(pcs!=null && pcs.length>0){
    	                        categoryId = pcs[pcs.length-1];
    	                    }
	    					if (categoryId != null) {
	    						data.setCategory_id(Long.valueOf(categoryId));
	    					}
	    					if(category_tree_path!=null){
	    						data.setCategory_tree_path(category_tree_path);
	    					}
	    				}
	    				if(ProductType.boutique.getCode().equals(product.getType())){
	    					ProviderCommonLogic providerCommonLogic = new ProviderCommonLogicImpl();
	    					Provider provider = providerCommonLogic.findByMerchantId(product.getMerchantId());
	    					if(provider!=null){
		    					String merchant_name = provider.getMerchantName();
		    					if(merchant_name!=null){
		    						data.setMerchant_name(merchant_name);
		    					}
		    				}
	    				} else {
	    					Map<String, String> merchantMap = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(product.getClientId(),product.getMerchantId());
		    				if(merchantMap!=null && !"".equals(merchantMap)){
		    					String merchant_name = merchantMap.get("merchant_name");
		    					if(merchant_name!=null){
		    						data.setMerchant_name(merchant_name);
		    					}
		    				}
		    				
		    				CommonLogic commonLogic = new CommonLogicImpl();
		    				Map<String, String> merchantCategoryMap = commonLogic.getMerchantAndCategoryRedis(product.getClientId(),product.getMerchantId());
		    				if(merchantCategoryMap!=null && !"".equals(merchantCategoryMap)){
		    					String merchant_category_id = merchantCategoryMap.get("category_id");
		    					String merchant_category_name = merchantCategoryMap.get("category_name");
		    					if(merchant_category_id!=null){
		    						data.setMerchant_category_id(merchant_category_id);
		    					}
		    					if(merchant_category_name!=null){
		    						data.setMerchant_category_name(merchant_category_name);
		    					}
		    				}
		    				
		    				
		    				if(ProductType.group.getCode().equals(product.getType()) || ProductType.presell.getCode().equals(product.getType())){
		    					Map<String,String> pm = commonLogic.getProductRedis(product.getClientId(),product.getMerchantId(),product.getProductId());
		    					if(pm!=null){
		    						if (Checker.isNotEmpty(pm.get("expire_start_time"))) {
				    					data.setExpire_start_time(Long.valueOf(pm.get("expire_start_time")));
				    				}
				    				if (Checker.isNotEmpty(pm.get("expire_end_time"))) {
				    					data.setExpire_end_time(Long.valueOf(pm.get("expire_end_time")));
				    				}
		    					}
		    				}
	    				}
	    				
	    				if(product.getAuditTime()!=null){
	    				   data.setAudit_time(product.getAuditTime().getTime());
	    				}
	    				if(Checker.isNotEmpty(product.getOrgCode())){
	    					data.setOrg_code(product.getOrgCode());
	    				}
	    				if(Checker.isNotEmpty(product.getAuditState())){
	    					data.setAudit_state(product.getAuditState());
	    				}
	    				if (product.getStartTime() != null) {
	    					data.setStart_time(product.getStartTime().getTime());
	    				}
	    				if (product.getEndTime() != null) {
	    					data.setEnd_time(product.getEndTime().getTime());
	    				}
	    				if (product.getDeliveryTime() != null) {
	    					data.setDelivery_time(product.getDeliveryTime().getTime());
	    				}
	    				
	    				data.setPrice(product.getPrice());
	    				if(product.getMarketPrice()!=null){
	    					data.setMarket_price(product.getMarketPrice());
	    				}
	    				if(product.getVipPrice()!=null){
	    					data.setVip_price(product.getVipPrice());
	    				}
	    				data.setCreate_time(product.getCreateTime().getTime());
	    				
	    				if("add".equals(action)){
	    					log.setAction("PRODUCTAUDIT");
	    					data.setIs_marketable(product.getIsMarketable());
	    					log.setData(data);
	    					LogCvt.debug("商品新加落地日志内容:"+JSON.toJSONString(log));
	    					GoodsLogs.addProduct(log);
	    				} else if("update".equals(action)){
	    					log.setAction("PRODUCTMODIFY");
	    					if ("1".equals(type)) {
	    						data.setIs_marketable(product.getIsMarketable());
	    						if (checkRackTime == null) {
	    							data.setIs_one_up("1");
	    						}
	    						if (checkDownTime == null) {
	    							data.setIs_one_down("1");
	    						}
	    					}
	    					if ("2".equals(type)) {
	    						data.setIs_marketable(isMarketable);
	    						if (product.getRackTime() == null) {
	    							data.setIs_one_up("1");
	    						}
	    						if (product.getDownTime() != null) {
	    							data.setIs_one_down("1");
	    						}
	    					}
	    					log.setData(data);
	    					LogCvt.debug("商品信息修改落地日志内容:"+JSON.toJSONString(log));
	    					GoodsLogs.updateProduct(log);
	    				}
	                }
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error("商品新加修改落地日志失败，原因:" + e.getMessage(), e);
		} finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
	}
	
}
