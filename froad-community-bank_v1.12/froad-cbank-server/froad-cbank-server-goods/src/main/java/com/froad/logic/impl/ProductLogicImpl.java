/**
 * 
 * @Title: ProductLogicImpl.java
 * @Package com.froad.logic.impl
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.bson.types.ObjectId;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.froad.common.beans.ResultBean;
import com.froad.common.enums.ProductQuerySort;
import com.froad.db.mongo.ProductDetailMongoUtil;
import com.froad.db.mongo.ProductDetialMongo;
import com.froad.db.mongo.ProductFavoriteMongo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.H5MongoPage;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.db.mysql.mapper.VipProductMapper;
import com.froad.db.redis.MerchantRedis;
import com.froad.db.redis.ProductCommonRedis;
import com.froad.db.redis.ProductRedis;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ActivitiesStatus;
import com.froad.enums.ActivitiesType;
import com.froad.enums.AuditState;
import com.froad.enums.DeliveryType;
import com.froad.enums.ModuleID;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.QrCodeType;
import com.froad.enums.ResultCode;
import com.froad.enums.SeckillFlagEnum;
import com.froad.log.GoodsLogs;
import com.froad.log.vo.HeadKey;
import com.froad.log.vo.ProductDetailLog;
import com.froad.log.vo.ProductLog;
import com.froad.logback.LogCvt;
import com.froad.logic.ActivitiesLogic;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.ProductLogic;
import com.froad.logic.ProviderCommonLogic;
import com.froad.po.ActivitiesInfo;
import com.froad.po.Area;
import com.froad.po.Org;
import com.froad.po.Outlet;
import com.froad.po.OutletProduct;
import com.froad.po.OutletProductInfo;
import com.froad.po.OutletProductQrCode;
import com.froad.po.Product;
import com.froad.po.ProductAreaOutlet;
import com.froad.po.ProductBaseInfo;
import com.froad.po.ProductCategory;
import com.froad.po.ProductComment;
import com.froad.po.ProductCommentFilter;
import com.froad.po.ProductDetailSimple;
import com.froad.po.ProductFilter;
import com.froad.po.ProductGroup;
import com.froad.po.ProductInfo;
import com.froad.po.ProductListInfo;
import com.froad.po.ProductOutletInfo;
import com.froad.po.ProductPresell;
import com.froad.po.ProductTemp;
import com.froad.po.ProductViewInfo;
import com.froad.po.Provider;
import com.froad.po.Result;
import com.froad.po.VipBindProductInfo;
import com.froad.po.mongo.MerchantOutletFavorite;
import com.froad.po.mongo.OutletDetail;
import com.froad.po.mongo.ProductActivitiesInfo;
import com.froad.po.mongo.ProductArea;
import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductCategoryInfo;
import com.froad.po.mongo.ProductCityArea;
import com.froad.po.mongo.ProductCityOutlet;
import com.froad.po.mongo.ProductCommentInfo;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductImage;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.ProductOutlet;
import com.froad.po.mongo.StarLevelFilter;
import com.froad.po.mongo.StoreProductInfo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.thread.FroadThreadPool;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.QrCodeService;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.EditProductAuditStateVo;
import com.froad.thrift.vo.FiledSort;
import com.froad.thrift.vo.GoodsInfoVo;
import com.froad.thrift.vo.OutletDetailSimplePageVoRes;
import com.froad.thrift.vo.OutletDetailSimpleVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.QrCodeRequestVo;
import com.froad.thrift.vo.QrCodeResponseVo;
import com.froad.thrift.vo.QueryBoutiqueGoodsFilterVo;
import com.froad.thrift.vo.QueryProductFilterVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.GoodsConstants;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.froad.util.PageModel;
import com.froad.util.ProductBeanUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.RedisMQKeys;
import com.froad.util.SimpleID;
import com.froad.vo.ProductUpdateableField;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * 
 * <p>@Title: ProductLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProductLogicImpl implements ProductLogic {
    
    private static SimpleID simpleID = new SimpleID(ModuleID.product);
    
	private MongoManager manager = new MongoManager();
	private RedisManager redis = new RedisManager();
	private ProductDetialMongo productDetialMongo = new ProductDetialMongo();
	
	//收藏上限
	private static int FAVORITELIMIT = 100;
	
    /**
     * 增加商品
     * @param product
     * @return Long    主键ID
     */
	@Override
	public ResultBean addProduct(ProductInfo productInfo) {
        long startTime = System.currentTimeMillis();
	    ResultBean resultBean = null;
	    try{
	        boolean isNoNeedAudit = false;
	        //判断是否需要审核 boss平台新加商品不需要审核
	        if(productInfo.getPlatType()!=null){
	            isNoNeedAudit = isExist(productInfo.getPlatType(),GoodsConstants.NO_NEED_AUDIT_PLAT_CODES);
	        }
	        Product product = productInfo.getProduct();
	        //若没传值初始化值 为非秒杀
	        if(Checker.isEmpty(product.getIsSeckill())){
                product.setIsSeckill(SeckillFlagEnum.notSeckill.getCode());
            }
	        //若没传值初始化值 为未上架
	        if(Checker.isEmpty(product.getIsMarketable())){
	            product.setIsMarketable(ProductStatus.noShelf.toString());
	        }
	        //若没传值初始化值 为待审核
	        if(Checker.isEmpty(product.getAuditState())){
                product.setAuditState(ProductAuditState.noAudit.toString());
            }
	        
	        Date now = new Date();
	        if(isNoNeedAudit){//boss添加 直接上架并审核通过
                product.setIsMarketable(ProductStatus.onShelf.toString());
                product.setAuditState(ProductAuditState.passAudit.toString());
                product.setAuditOrgCode("0");
                product.setAuditStartOrgCode("0");
                product.setAuditEndOrgCode("0");
                product.setAuditStage("");
                product.setAuditStaff(productInfo.getPlatType());
                product.setAuditTime(now);
                product.setAuditComment("boss自动审核");
            } else {//银行PC、商户PC
                product.setAuditStage("");
                product.setReviewStaff("");
                product.setAuditStaff("");
                product.setAuditTime(null);
            }
	        if(product.getAuditOrgCode()==null) {
	        	product.setAuditOrgCode("0");
	        	product.setAuditStartOrgCode("0");
                product.setAuditEndOrgCode("0");
	        }
	        //活动放到活动管理平台统一绑定
	        productInfo.setActivities(null);
	        
	        product.setIsPoint(false);
	        
	        CommonLogic comLogic = new CommonLogicImpl();
	        //判断商户是否存在及是否已失效
	        if(Checker.isEmpty(product.getMerchantId())){
	        	//取商户信息
	            Map<String,String> merchantMap = comLogic.getBankMerchantRedis(product.getClientId(), product.getOrgCode());
	            if(Checker.isEmpty(merchantMap)){
                    resultBean = new ResultBean(ResultCode.failed,"商户已失效或不存在不能新增商品");
                    return resultBean;
                } else if(Checker.isEmpty(merchantMap.get("is_enable")) || "0".equals(merchantMap.get("is_enable"))){
                    resultBean = new ResultBean(ResultCode.failed,"商户已失效不能新增商品");
                    return resultBean;
                } 
	            //重新设置商户Id和商户名称
                product.setMerchantId(merchantMap.get("merchant_id"));
                product.setMerchantName(merchantMap.get("merchant_name"));
            } else {
                Map<String,String> merchantMap = comLogic.getMerchantRedis(product.getClientId(), product.getMerchantId());
                if(Checker.isEmpty(merchantMap)){
                    resultBean = new ResultBean(ResultCode.failed,"商户不存在不能新增商品");
                    return resultBean;
                } else if(Checker.isEmpty(merchantMap.get("is_enable")) || "0".equals(merchantMap.get("is_enable"))){
                    resultBean = new ResultBean(ResultCode.failed,merchantMap.get("merchant_name")+"商户已失效不能新增商品");
                    return resultBean;
                }
                //设置商户名称
                product.setMerchantName(merchantMap.get("merchant_name"));
            }
	        product.setCreateTime(now);//设置创建时间为当前值
	        product.setSellCount(0);//新加商品设置销量为0
	        product.setPoint(0);//新加商品设置评价为0
	        //更新上架时间
	        if(ProductStatus.onShelf.getCode().equals(product.getIsMarketable())){
	        	//已上架
	            product.setRackTime(now);//上架时间
	        }
	        
	        //设置商品Id
	        product.setProductId(simpleID.nextId());
	        product.setPlatType(productInfo.getPlatType());
	        
	        productInfo.setProduct(product);
	       
	        //排序权重
            if(Checker.isEmpty(product.getOrderValue()) || product.getOrderValue().intValue()==0){
            	product.setOrderValue(1);
	        }
            //设置1-2-3级orgCode
	        if(Checker.isNotEmpty(product.getClientId()) && Checker.isNotEmpty(product.getOrgCode())){
	        	List<Org> orgLevel=comLogic.getSuperOrgList(product.getClientId(),product.getOrgCode());
	        	for(Org org:orgLevel){
	        		if(Checker.isNotEmpty(org)&& "1".equals(org.getOrgLevel())){
	        			product.setProOrgCode(org.getOrgCode());
	        		}else if(Checker.isNotEmpty(org)&& "2".equals(org.getOrgLevel())){
	        			product.setCityOrgCode(org.getOrgCode());
	        		}else if(Checker.isNotEmpty(org)&& "3".equals(org.getOrgLevel())){
	        			product.setCountryOrgCode(org.getOrgCode());
	        		}
	        	}
	        }
	        
	        //设置category_tree_path商品分类tree_path
	        if(productInfo.getProductCategory()!=null && productInfo.getProductCategory().getId()!=null && productInfo.getProductCategory().getId()>0){
	            ProductCategory productCategory = comLogic.findCategoryById(product.getClientId(), productInfo.getProductCategory().getId());
	            if(Checker.isNotEmpty(productCategory)){
	                product.setCategoryTreePath(productCategory.getTreePath());
	            }
	        }
	        
	        /**********************操作MySQL数据库**********************/
            boolean productSqlFlag = false;
            SqlSession addSqlSession = null;
	        try{
	            addSqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	            ProductMapper addProductMapper = addSqlSession.getMapper(ProductMapper.class);
	          
	            //生成二维码
	            //商品类型对应的二维码类型
	            String qrCodeType = QrCodeType.PRODUCT.getCode();
	            if(ProductType.group.getCode().equals(product.getType())) {
	            	qrCodeType = QrCodeType.GROUP.getCode();//团购商品
	            } else if(ProductType.presell.getCode().equals(product.getType())) {
	            	qrCodeType = QrCodeType.PRESELL.getCode();//预售商品
	            } else if(ProductType.special.getCode().equals(product.getType())) {
	            	qrCodeType = QrCodeType.SPECIAL.getCode();//名优特惠商品
	            } else if(ProductType.onlinePoint.getCode().equals(product.getType())) {
	            	qrCodeType = QrCodeType.ONLINEPOINT.getCode();//在线积分兑换商品
	            } else if(ProductType.dotGift.getCode().equals(product.getType())) {
	            	qrCodeType = QrCodeType.DOTGIFT.getCode();//网点礼品商品
	            }
	            
	            String qrCodeUrl = generateQrCode(product.getClientId(), product.getProductId(), qrCodeType);
	            product.setCodeUrl(qrCodeUrl);
	            addProductMapper.addProduct(product);//基础信息保存到mysql
	            
	            if(product.getType().equals(ProductType.group.getCode())){
	                ProductGroup groupProduct = productInfo.getProductGroup();
	                groupProduct.setProductId(product.getProductId());
	                groupProduct.setCreateTime(now);
	                addProductMapper.addProductGroup(groupProduct);//团购信息保存到mysql
	            } else if(product.getType().equals(ProductType.presell.getCode())){
	                ProductPresell presellProduct = productInfo.getProductPresell();
	                presellProduct.setProductId(product.getProductId());
	                presellProduct.setCreateTime(now);
	                addProductMapper.addProductPresell(presellProduct);//预售信息保存到mysql
	            }
	            addSqlSession.commit(true);
	            productSqlFlag = true;
	        } catch (Exception e) { 
	        	if(null != addSqlSession)
	        		addSqlSession.rollback(true);  
	            LogCvt.error("添加Product mysql失败，原因:" + e.getMessage(),e); 
	        } finally { 
	            if(null != addSqlSession) {
	                addSqlSession.close(); 
	            } 
	        } 
	        
	        /**********************操作Redis缓存**********************/
	        this.productRedis(productInfo);
	        
	        
	        /**********************操作Mongo，添加商品详情**********************/
            boolean productMongoFlag = this.addProductDetail(productInfo);
	        if(productSqlFlag==true && productMongoFlag==true){
	            resultBean = new ResultBean(ResultCode.success,"添加商品成功",product.getProductId());
	        } else if(productSqlFlag==false && productMongoFlag==false){
	            resultBean = new ResultBean(ResultCode.failed,"添加商品失败");
	        } else {
	            resultBean = new ResultBean(ResultCode.success,"添加商品成功,"+(productSqlFlag==false?"mysql添加商品失败":"")+(productMongoFlag==false?"mongo添加商品失败":""),product.getProductId());
	        }

	        addProductOperaLog(product.getProductId(), null, null, null, null, "add");
	        
	        if(productSqlFlag==true){
	        	//指定商品类型可以满减活动(商品类型逗号分隔,比如1,2)1团购
	        	String fullReductionProductTypes = GoodsConstants.GOODS_PRODUCT_TYPE_FULL_REDUCTION;
	            if(fullReductionProductTypes!=null && (fullReductionProductTypes+",").indexOf(product.getType()+",")!=-1){
	            	putFullCut(product.getClientId(), product.getProductId());
	            }
	        }
	        
	    } catch (Exception e) { 
            LogCvt.error("添加Product失败，原因:" + e.getMessage(),e); 
            resultBean = new ResultBean(ResultCode.failed,"添加Product失败");
        }
        long endTime = System.currentTimeMillis();
        LogCvt.debug("-------新加商品:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
	    return resultBean;
	}
	
	
	/**
	 * 生成临时表信息
	 * @param productInfo
	 * @return
	 */
	private ProductTemp createProductTemp(ProductInfo productInfo){
		ProductTemp productTemp = new ProductTemp();
		productTemp.setIsHistory(false);
        Product product = productInfo.getProduct();
        ProductBeanUtil.copyProperties(productTemp, product);//复制信息
        if(ProductType.group.getCode().equals(product.getType())) {
        	ProductGroup productGroup = productInfo.getProductGroup();
            ProductBeanUtil.copyProperties(productTemp, productGroup);//团购商品
        } else if(ProductType.presell.getCode().equals(product.getType())) {
        	ProductPresell productPresell = productInfo.getProductPresell();
            ProductBeanUtil.copyProperties(productTemp, productPresell);//预售商品
        } 
        productTemp.setAuditComment(null);
        return productTemp;
    }
	
	private void putFullCut(final String clientId, final String productId) {
        FroadThreadPool.execute(new Runnable() { // 异步塞缓存
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                LogCvt.debug("参加满减活动的商品需要调用满减通知接口[开始...]时间戳=" + startTime);
                try {
                	ActivitiesLogic activitiesLogic = new ActivitiesLogicImpl();
                	activitiesLogic.putFullCut(clientId, productId);
                } catch (Exception e) {
                    LogCvt.error("参加满减活动的商品需要调用满减通知接口失败, 原因:" + e.getMessage(), e);
                } finally {
                    long endTime = System.currentTimeMillis();
                    LogCvt.debug("参加满减活动的商品需要调用满减通知接口[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                }
            }
        });
    }


    /**
     * 删除商品
     * @param product
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteProduct(Product product) {
	    Boolean result = false;
	    
	    SqlSession sqlSession = null;
	    try {
	        sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            result = true;
            //更新mysql的is_marketable为删除状态
            product.setIsMarketable(ProductStatus.isDeleted.getCode());
            productMapper.deleteLogicProduct(product); //mysql逻辑删除
            sqlSession.commit(true);
            
            List<Product> ps = productMapper.getProductByProductId(product);
            if(ps!=null && ps.size()>0){
                Product p = ps.get(0);
                /* redis缓存 */
                String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(p.getClientId(), p.getMerchantId(), p.getProductId());
                //更新redis缓存 is_marketable为删除状态
                Map<String, String> hash = redis.getMap(key);
                if(hash!=null){
                    hash.put("is_marketable", product.getIsMarketable()+"");
                    redis.putMap(key, hash);
                }
                //更新mongo is_marketable为删除状态
                DBObject value = new BasicDBObject();
                value.put("is_marketable", product.getIsMarketable());
                DBObject where = new BasicDBObject();
                where.put("client_id", p.getClientId());
                where.put("_id", p.getProductId());
                manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
                //打印删除日志
                GoodsLogs.deleteProduct(p.getProductId(), p.getClientId(), new Date().getTime(),"PRODUCTDELETE");
            }
	    } catch (Exception e) {
	        result = false;
	        if(null != sqlSession)
	        	sqlSession.rollback(true);  
            LogCvt.error("删除Product失败，原因:" + e.getMessage(),e); 
	    } finally { 
	        if(null != sqlSession) {
	            sqlSession.close(); 
	        }
	    }
		return result; 
	}


	/**
	 * 修改商品
	 * 
	 * @param product
	 * @return Result
	 */
	@Override
	public Result updateProduct(ProductInfo productInfo) {
		Result result = new Result();
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);

			Product product = productInfo.getProduct();
			
			List<Product> ps = productMapper.getProductByProductId(product);
			if (ps != null && ps.size() > 0) {
				Product p = ps.get(0);
				if (ProductAuditState.noAudit.getCode().equals(p.getAuditState())) {
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("此商品正在审核中不能修改");
					return result;
				}
				Date checkRackTime = p.getRackTime();
				Date checkDownTime = p.getDownTime();
				if (p.getVipPrice() != null && p.getVipPrice() > 0.0 && p.getVipPrice() > product.getPrice()) {
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("vip价格必须小于销售价");
					return result;
				}
				
				// 商户id没有传值设置为新加时候的商户id
				if (Checker.isEmpty(product.getMerchantId())) {
					product.setMerchantId(p.getMerchantId());
				}
				product.setIsPoint(p.getIsPoint());

				CommonLogic comLogic = new CommonLogicImpl();
				Map<String, String> merchantMap = comLogic.getMerchantRedis(product.getClientId(),p.getMerchantId());
				if (merchantMap == null || merchantMap.size() == 0) {
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("商户不存在不能修改商品");
					return result;
				} else {
					if (merchantMap.get("is_enable") == null || "0".equals(merchantMap.get("is_enable"))) {
						result.setResultCode(ResultCode.failed.getCode());
						result.setResultDesc(merchantMap.get("merchant_name") + "商户已失效不能修改商品");
						return result;
					}
				}
				// 若没传值初始化值设置为新加时候的秒杀值
				if (Checker.isEmpty(product.getIsSeckill())) {
					product.setIsSeckill(p.getIsSeckill());
				}
				// 是秒杀商品时 该商品的结束时间不能小于秒杀的结束时间
				if (!SeckillFlagEnum.notSeckill.getCode().equals(p.getIsSeckill())) {
					List<Product> seckillProducts = productMapper.getSeckillProduct(product);
					if (seckillProducts != null && seckillProducts.size() > 0) {
						if (product.getEndTime() != null && seckillProducts.get(0).getEndTime() != null 
								&& seckillProducts.get(0).getEndTime().getTime() > product.getEndTime().getTime()) {
							result.setResultCode(ResultCode.failed.getCode());
							result.setResultDesc("您的此商品正在参加秒杀活动，请先结束秒杀活动后再修改");
							return result;
						}
					}
				}
				// 查出临时表
				ProductTemp pt = productMapper.getAuditingTempProductByProductId(p.getProductId());
				// 正在审核的不能修改
				if (pt!=null && ProductAuditState.noAudit.getCode().equals(pt.getAuditState())) {
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("此商品正在审核中不能修改");
					return result;
				}

				// 查出mongo中的数据
				DBObject seach = new BasicDBObject();
				seach.put("_id", product.getProductId());
				seach.put("client_id", product.getClientId());
				ProductDetail pd = manager.findOne(seach,MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);

				// 购买限制信息设置
				ProductBuyLimit buyLimit = productInfo.getBuyLimit();
				if (pd != null) {
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
					if (buyLimit != null && (buyLimit.getMax() > 0 || buyLimit.getMaxVip() > 0 || buyLimit.getMaxCard() > 0)) {
						product.setIsLimit(true);
					} else {
						product.setIsLimit(false);
					}
				} 
				productInfo.setBuyLimit(buyLimit);
				
				// 设置category_tree_path商品分类tree_path
				if (productInfo.getProductCategory() != null && productInfo.getProductCategory().getId() != null && productInfo.getProductCategory().getId() > 0) {
					ProductCategory productCategory = comLogic.findCategoryById(product.getClientId(),productInfo.getProductCategory().getId());
					if (Checker.isNotEmpty(productCategory)) {
						product.setCategoryTreePath(productCategory.getTreePath());
					}
				}
				
				ProductGroup pg = null;
				ProductPresell pr = null;
				if(ProductType.group.getCode().equals(p.getType())){//团购商品
                    List<ProductGroup> pgs = productMapper.getProductGroupByProductId(p);
                    if(pgs!=null && pgs.size()>0){
                        pg = pgs.get(0);
                    }
                } else if(ProductType.presell.getCode().equals(p.getType())){//预售商品
                    List<ProductPresell> prs = productMapper.getProductPresellByProductId(p);
                    if(prs!=null && prs.size()>0){
                    	pr = prs.get(0);
                    }
                }
				// 没有修改的字段不能提交修改 给提示
				//判断此次修改是否有需要审核的字段发生变化
				boolean isHaveNeedAudtitFieldUpdate = isHaveNeedAudtitFieldUpdate(pd, pt, p, pg, pr, productInfo);
				//判断此次修改是否有不需要审核字段发生变化
				boolean isHaveNoNeedAudtitFieldUpdate = isHaveNoNeedAudtitFieldUpdate(pd, pt, p, pg, pr, productInfo);
				if (!isHaveNeedAudtitFieldUpdate && !isHaveNoNeedAudtitFieldUpdate) {
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("该商品此次没有修改任何字段");
					return result;
				}

				// 团购商品审核状态为1的时候需要审核
				if (!p.getType().equals(ProductType.group.getCode())) {
					product.setIsMarketable(ProductStatus.noShelf.getCode());// 审核机构代码不为0的是未上架
					product.setAuditStage("");
					if (product.getAuditState() == null) {// 审核状态没有传值
						// 设置为新加时候的审核状态
						product.setAuditState(p.getAuditState());
					}
					// 修改时候如果审核状态不是未提交审核的 全部变成待审核状态
					if (!ProductAuditState.noCommit.toString().equals(product.getAuditState())) {
						product.setAuditState(ProductAuditState.noAudit.toString());
					}
				}

				// 活动管理平台绑定
				productInfo.setActivities(null);

				if (ProductStatus.onShelf.getCode().equals(product.getIsMarketable())) {// 上架的有上架时间
					product.setRackTime(new Date());
				} else if (ProductStatus.offShelf.getCode().equals(product.getIsMarketable())) {// 下架的有下架时间
					product.setDownTime(new Date());
				}
				productInfo.setProduct(product);
				
				// 保存商品所属的机构代码 支持4级机构代码
				if (Checker.isNotEmpty(product.getClientId()) && Checker.isNotEmpty(product.getOrgCode())) {
					List<Org> orgLevel = comLogic.getSuperOrgList(product.getClientId(), product.getOrgCode());
					for (Org org : orgLevel) {
						if (Checker.isNotEmpty(org) && "1".equals(org.getOrgLevel())) {
							product.setProOrgCode(org.getOrgCode());
						} else if (Checker.isNotEmpty(org) && "2".equals(org.getOrgLevel())) {
							product.setCityOrgCode(org.getOrgCode());
						} else if (Checker.isNotEmpty(org) && "3".equals(org.getOrgLevel())) {
							product.setCountryOrgCode(org.getOrgCode());
						}
					}
				}

				if (product.getIsLimit() && buyLimit != null 
						&& (buyLimit.getMax() > 0 
								|| buyLimit.getMaxVip() > 0 
								|| buyLimit.getMaxCard() > 0)) {
					product.setIsLimit(true);
				} else {
					product.setIsLimit(false);
				}
				
				productInfo.setProduct(product);
				
				if (ProductAuditState.passAudit.getCode().equals(p.getAuditState())) {
					if(isHaveNoNeedAudtitFieldUpdate) {//更新免审核的字段到主表
						ProductTemp noNeedAuditProductTemp = new ProductTemp();
						noNeedAuditProductTemp.setProductId(p.getProductId());
						noNeedAuditProductTemp.setClientId(p.getClientId());
						//免审核的字段
						if(product.getStore()!=null && (p.getStore()==null || product.getStore().intValue()!=p.getStore())) {
							noNeedAuditProductTemp.setStore(product.getStore());
						}
						if(product.getEndTime()!=null && (p.getEndTime()==null || product.getEndTime().getTime()!=p.getEndTime().getTime())) {
							noNeedAuditProductTemp.setEndTime(product.getEndTime());
						}
						if (product.getType().equals(ProductType.group.getCode())) {
							if(productInfo.getProductGroup().getExpireEndTime()!=null 
									&& (pg==null || pg.getExpireEndTime()==null || productInfo.getProductGroup().getExpireEndTime().getTime()!=pg.getExpireEndTime().getTime())) {
								noNeedAuditProductTemp.setExpireEndTime(productInfo.getProductGroup().getExpireEndTime());
							}
						}
						
						/******将主表的数据保留一份到临时表 start**/
						if(!isHaveNeedAudtitFieldUpdate) {
							ProductInfo productInfoTemp = new ProductInfo();
							productInfoTemp.setProduct(product);
							if (product.getType().equals(ProductType.group.getCode())) {
								productInfoTemp.setProductGroup(productInfo.getProductGroup());// 商品临时表信息
							} else if (product.getType().equals(ProductType.presell.getCode())) {
								productInfoTemp.setProductPresell(productInfo.getProductPresell());// 商品临时表信息
							}
							ProductTemp productTemp = this.createProductTemp(productInfoTemp);
							productTemp.setIsMarketable(ProductStatus.onShelf.getCode());
							productTemp.setAuditState(ProductAuditState.passAudit.getCode());
							productTemp.setIsHistory(true);
							
							// 审核通过之后编辑商品信息临时表需要保留编辑前的基础和图片信息
							// 并且只需要更新临时表，主表,redis和mongo不更新
							productTemp.setPhotoList(JSON.toJSONString(productInfo.getProductImages()));
							if (productInfo.getBuyLimit() != null) {
								productTemp.setBuyLimit(JSON.toJSONString(productInfo.getBuyLimit()));
							}
							ProductInfo primevalProductInfo = new ProductInfo();
							primevalProductInfo.setProduct(p);
							if (product.getType().equals(ProductType.group.getCode())) {
								List<ProductGroup> pgs = productMapper.getProductGroupByProductId(product);
								if (pgs != null && pgs.size() > 0) {
									primevalProductInfo.setProductGroup(pgs.get(0));
								}
							} else if (product.getType().equals(ProductType.presell.getCode())) {
								List<ProductPresell> pps = productMapper.getProductPresellByProductId(product);
								if (pps != null && pps.size() > 0) {
									primevalProductInfo.setProductPresell(pps.get(0));
								}
							}
							primevalProductInfo.setBuyLimit(pd.getBuyLimit());
							primevalProductInfo.setProductImages(pd.getImageInfo());
							productTemp.setPrimeval(JSON.toJSONString(primevalProductInfo));

							productTemp.setCreateTime(new Date());
							productTemp.setAuditComment("只修改了免审核字段");
							productMapper.addTempProduct(productTemp);// 基础信息保存到mysql临时表
							/******将主表的数据保留一份到临时表 end**/
						}
						
						//修改了不需要审核字段值更新到Product主表中
						productMapper.updateNoNeedAuditProduct(noNeedAuditProductTemp);
						if (product.getType().equals(ProductType.group.getCode()) && noNeedAuditProductTemp.getExpireEndTime()!=null) {
							//修改了不需要审核字段值更新到ProductGroup主表中
							productMapper.updateNoNeedAuditProductGroup(noNeedAuditProductTemp);
						}
						//redis store 缓存
						if(noNeedAuditProductTemp.getStore()!=null &&noNeedAuditProductTemp.getStore()>0) {
							String re = redis.putString(RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(p.getClientId(), p.getMerchantId(), p.getProductId()), ObjectUtils.toString(noNeedAuditProductTemp.getStore(), ""));
							LogCvt.debug("只修改了免审核字段store时更新redis库存，key参数:clientId:"+p.getClientId()+",merchantId:"+p.getMerchantId()+",productId:"+ p.getProductId()+",store:"+noNeedAuditProductTemp.getStore()+",redis result:"+re);
						}
						//redis end_time
						if(noNeedAuditProductTemp.getEndTime()!=null || noNeedAuditProductTemp.getExpireEndTime()!=null) {
							String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId());
							if(noNeedAuditProductTemp.getEndTime()!=null) {
								Long rend = redis.hset(key, "end_time", ObjectUtils.toString(noNeedAuditProductTemp.getEndTime().getTime(), "0"));
								LogCvt.debug("只修改了免审核字段end_time时更新redis，key:"+key+",endTime:"+noNeedAuditProductTemp.getEndTime()+",redis result:"+rend);
							}
							if (noNeedAuditProductTemp.getExpireEndTime()!=null && product.getType().equals(ProductType.group.getCode())) {
								Long rexend = redis.hset(key, "expire_end_time", ObjectUtils.toString(noNeedAuditProductTemp.getExpireEndTime().getTime(), "0"));
								LogCvt.debug("只修改了免审核字段expire_end_time时更新redis，key:"+key+",expire_end_time:"+noNeedAuditProductTemp.getExpireEndTime()+",redis result:"+rexend);
							}
						}
						//mongo end_time
						if(noNeedAuditProductTemp.getEndTime()!=null) {
							DBObject where = new BasicDBObject();
							where.put("_id", product.getProductId());
							if(Checker.isNotEmpty(p.getClientId())){
			                    where.put("client_id", p.getClientId());
			                }
							DBObject valueObj = new BasicDBObject();
							valueObj.put("end_time", noNeedAuditProductTemp.getEndTime().getTime());
							
							if(productInfo.getBuyLimit()!=null && productInfo.getBuyLimit().getEndTime()>0 
									&& pd!=null && pd.getBuyLimit()!=null && productInfo.getBuyLimit().getEndTime()!=pd.getBuyLimit().getEndTime() ) {
								pd.getBuyLimit().setEndTime(productInfo.getBuyLimit().getEndTime());
								valueObj.put("buy_limit.end_time", pd.getBuyLimit().getEndTime());
							}
							int mo = manager.update(valueObj, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");// 向mongodb更新数据
							LogCvt.debug("只修改了免审核字段end_time时更新mongo结果:"+mo);
						}
						
						if(pt != null){//更新临时表
							//修改了不需要审核字段值更新到临时表中
							productMapper.updateNoNeedAuditTempProduct(noNeedAuditProductTemp);
						}
					}
					/*******更新了需要审核的字段 start**/
					if (isHaveNeedAudtitFieldUpdate) {
						ProductInfo productInfoTemp = new ProductInfo();
						productInfoTemp.setProduct(product);
						if (product.getType().equals(ProductType.group.getCode())) {
							productInfoTemp.setProductGroup(productInfo.getProductGroup());// 商品临时表信息
						} else if (product.getType().equals(ProductType.presell.getCode())) {
							productInfoTemp.setProductPresell(productInfo.getProductPresell());// 商品临时表信息
						}
						ProductTemp productTemp = this.createProductTemp(productInfoTemp);
						productTemp.setIsMarketable(ProductStatus.noShelf.getCode());
						
						// 审核通过之后编辑商品信息临时表需要保留编辑前的基础和图片信息
						// 并且只需要更新临时表，主表,redis和mongo不更新
						productTemp.setPhotoList(JSON.toJSONString(productInfo.getProductImages()));
						if (productInfo.getBuyLimit() != null) {
							productTemp.setBuyLimit(JSON.toJSONString(productInfo.getBuyLimit()));
						}
						if (pt!=null && Checker.isNotEmpty(pt.getPrimeval())) {
							productTemp.setPrimeval(pt.getPrimeval());
						} else {
							ProductInfo primevalProductInfo = new ProductInfo();
							primevalProductInfo.setProduct(p);
							if (product.getType().equals(ProductType.group.getCode())) {
								List<ProductGroup> pgs = productMapper.getProductGroupByProductId(product);
								if (pgs != null && pgs.size() > 0) {
									primevalProductInfo.setProductGroup(pgs.get(0));
								}
							} else if (product.getType().equals(ProductType.presell.getCode())) {
								List<ProductPresell> pps = productMapper.getProductPresellByProductId(product);
								if (pps != null && pps.size() > 0) {
									primevalProductInfo.setProductPresell(pps.get(0));
								}
							}
							primevalProductInfo.setBuyLimit(pd.getBuyLimit());
							primevalProductInfo.setProductImages(pd.getImageInfo());
							productTemp.setPrimeval(JSON.toJSONString(primevalProductInfo));
						}
						// 删除临时表
						productMapper.deleteTempProduct(p.getProductId());

						productTemp.setCreateTime(new Date());
						productMapper.addTempProduct(productTemp);// 基础信息保存到mysql临时表
						
						Map<String,String> param = new HashMap<String,String>();
						param.put("productId", productTemp.getProductId());
						param.put("clientId", productTemp.getClientId());
						param.put("auditState", productTemp.getAuditState());
						productMapper.updateProductEditAuditStatus(param);//更新主表的子审核状态
						productMapper.updateProductEditAuditStatus(param);//更新主表的子审核状态
						
					}
					/*******更新了需要审核的字段 end**/
				} else {// 直接修改主表,临时表,redis和mongo 并且临时表没有编辑前的基础和图片信息
					
					ProductDetail productDetail = null;
					if(productInfo!=null && productInfo.getProduct()!=null && Checker.isNotEmpty(productInfo.getProduct().getProductId())){
						productDetail = changToProductDetail(productInfo,sqlSession,productMapper);
						if(productDetail!=null) {
							//修改商品时创建时间，活动信息 不修改
							if (pd != null) {
					            productDetail.setPlatType(pd.getPlatType());
					            if(pd.getCreateTime()!=null){
					                productDetail.setCreateTime(pd.getCreateTime());
					            }
					            productDetail.setActivitiesInfo(pd.getActivitiesInfo());
					            //保留VIP规则绑定关系
					            productDetail.setVipPrice(pd.getVipPrice());
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
							
						} else {
							result.setResultCode(ResultCode.failed.getCode());
							result.setResultDesc("生成mongo中商品信息有误");
							return result;
						}
					} else {
						result.setResultCode(ResultCode.failed.getCode());
						result.setResultDesc("商品信息不正确");
						return result;
					}
					if (productDetail != null && productDetail.getIsLimit() == 1) {
						product.setIsLimit(true);
					} else {
						product.setIsLimit(false);
					}

					productMapper.updateProduct(product); // 更新mysql基础信息
					if (product.getType().equals(ProductType.group.getCode())) {
						ProductGroup groupProduct = productInfo.getProductGroup();
						productMapper.updateProductGroup(groupProduct);// 更新团购mysql信息
					} else if (product.getType().equals(ProductType.presell.getCode())) {
						ProductPresell presellProduct = productInfo.getProductPresell();
						productMapper.updateProductPresell(presellProduct);// 更新mysql预售信息
					}

					// 商品详情插入mongo
					if (pd != null) {
						DBObject where = new BasicDBObject();
		                if(Checker.isNotEmpty(product.getClientId())){
		                    where.put("client_id", product.getClientId());
		                }
		                where.put("_id", product.getProductId());
		                Object whereObject = where;
		                
						manager.update(productDetail, whereObject, MongoTableName.CB_PRODUCT_DETAIL, "$set");// 向mongodb更新数据
			            DBObject valueObj = ProductDetailMongoUtil.unsetProductDetail(productDetail);
			            if(valueObj!=null){
			                manager.update(valueObj, where, MongoTableName.CB_PRODUCT_DETAIL, null);//更新mongo vip_price buy_limit和is_limit
			            }
					} else {
						manager.add(productDetail, MongoTableName.CB_PRODUCT_DETAIL);// 向mongodb插入数据
					}
			        
					// 商品redis
					productRedis(productInfo);
					
					addProductOperaLog(p.getProductId(), checkRackTime, checkDownTime, "1", null, "update");
				}
				sqlSession.commit(true);
				
				//图片发生变化
				if(pt!=null) {
					if(Checker.isNotEmpty(pt.getPhotoList())) {
						Set<String> isDelPhotoSet = new HashSet<String>();
						Set<String> tempPhotoSet = new HashSet<String>();
						List<ProductImage> newImages = productInfo.getProductImages();
						for(ProductImage newImage : newImages) {
							tempPhotoSet.add(newImage.getSource());
						}
						List<ProductImage> oldImages = JSON.parseArray(pt.getPhotoList(), ProductImage.class);
						for(ProductImage oldImage : oldImages) {
							if(!tempPhotoSet.contains(oldImage.getSource())) {
								isDelPhotoSet.add(oldImage.getSource());
							}
						}
						if(isDelPhotoSet.size()>0) {
							redis.putSet(RedisMQKeys.cbbank_undelete_images,isDelPhotoSet);
							LogCvt.debug("7牛图片删除,key:"+RedisMQKeys.cbbank_undelete_images+",pohtoList:"+JSON.toJSONString(isDelPhotoSet));
						}
					}
				} else {
					Set<String> isDelPhotoSet = new HashSet<String>();
					Set<String> tempPhotoSet = new HashSet<String>();
					List<ProductImage> newImages = productInfo.getProductImages();
					List<ProductImage> oldImages = pd.getImageInfo();
					for(ProductImage newImage : newImages) {
						tempPhotoSet.add(newImage.getSource());
					}
					for(ProductImage oldImage : oldImages) {
						if(!tempPhotoSet.contains(oldImage.getSource())) {
							isDelPhotoSet.add(oldImage.getSource());
						}
					}
					if(isDelPhotoSet.size()>0) {
						redis.putSet(RedisMQKeys.cbbank_undelete_images,isDelPhotoSet);
						LogCvt.debug("7牛图片删除,key:"+RedisMQKeys.cbbank_undelete_images+",pohtoList:"+JSON.toJSONString(isDelPhotoSet));
					}
				}
				
				result.setResultCode(ResultCode.success.getCode());
				result.setResultDesc("商品修改成功");
			} else {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("商品不存在不能修改");
			}
		} catch (Exception e) {
			if (null != sqlSession) {
				sqlSession.rollback(true);
			}
			LogCvt.error("修改商品失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("修改商品失败");
		} finally {
			if (null != sqlSession) {
				sqlSession.close();
			}
		}
		return result;
	}
	
	
	/**
	 * 判断此次修改是否有需要审核的字段发生变化
	 * @param pd mongo表中的限购，图片等信息
	 * @param pt 临时表中基础信息
	 * @param p
	 * @param pg
	 * @param pr
	 * @param productInfo 此次修改的字段信息
	 * @return
	 */
	private boolean isHaveNeedAudtitFieldUpdate(ProductDetail pd,ProductTemp pt,Product p,ProductGroup pg,ProductPresell pr,ProductInfo productInfo){
		Product product = productInfo.getProduct();
		
		ProductUpdateableField newProductField = new ProductUpdateableField();
		ProductUpdateableField oldProductField = new ProductUpdateableField();
		
		newProductField.setClientId(product.getClientId());
		newProductField.setMerchantId(product.getMerchantId());
		newProductField.setProductId(product.getProductId());
		newProductField.setStartTime(product.getStartTime());
		newProductField.setDeliveryOption(product.getDeliveryOption());
		newProductField.setName(product.getName());
		newProductField.setFullName(product.getFullName());
		newProductField.setPrice(product.getPrice());
		newProductField.setCost(product.getCost());
		newProductField.setMarketPrice(product.getMarketPrice());
		newProductField.setIsLimit(product.getIsLimit());
		newProductField.setBriefIntroduction(product.getBriefIntroduction());
		newProductField.setIntroduction(product.getIntroduction());
		newProductField.setBuyKnow(product.getBuyKnow());
		newProductField.setDeliveryMoney(product.getDeliveryMoney());
		newProductField.setIsSeckill(product.getIsSeckill());
		newProductField.setCategoryTreePath(product.getCategoryTreePath());
		if(product.getVipPrice()!=null && product.getVipPrice()>0.0) {
			newProductField.setVipPrice(product.getVipPrice());
		}
		newProductField.setExpireStartTime(product.getExpireStartTime());
		newProductField.setDeliveryTime(product.getDeliveryTime());
		newProductField.setSeoKeyWords(product.getSeoKeyWords());
		newProductField.setAfterShop(product.getAfterShop());
		newProductField.setGoodFlag(product.getGoodFlag());
//		newProductField.setOrderValue(product.getOrderValue());
		newProductField.setWeight(product.getWeight());
		newProductField.setWeightUnit(product.getWeightUnit());
		newProductField.setIsBest(product.getIsBest());
		newProductField.setImageInfos(productInfo.getProductImages());
		ProductBuyLimit buyLimit = productInfo.getBuyLimit();
		ProductBuyLimit tbuyLimit = null;
		if(buyLimit!=null) {
			tbuyLimit = new ProductBuyLimit();
			tbuyLimit.setId(buyLimit.getId());
			tbuyLimit.setMin(buyLimit.getMin());
			tbuyLimit.setMax(buyLimit.getMax());
			tbuyLimit.setMaxVip(buyLimit.getMaxVip());
			tbuyLimit.setMaxCard(buyLimit.getMaxCard());
			tbuyLimit.setStartTime(buyLimit.getStartTime());
		}
		newProductField.setBuyLimit(tbuyLimit);
		
		if(pt!=null) {
			oldProductField.setClientId(pt.getClientId());
			oldProductField.setMerchantId(pt.getMerchantId());
			oldProductField.setProductId(pt.getProductId());
			oldProductField.setStartTime(pt.getStartTime());
			oldProductField.setDeliveryOption(pt.getDeliveryOption());
			oldProductField.setName(pt.getName());
			oldProductField.setFullName(pt.getFullName());
			oldProductField.setPrice(pt.getPrice());
			oldProductField.setCost(pt.getCost());
			oldProductField.setMarketPrice(pt.getMarketPrice());
			
			oldProductField.setIsLimit(pt.getIsLimit());
			oldProductField.setBriefIntroduction(pt.getBriefIntroduction());
			oldProductField.setIntroduction(pt.getIntroduction());
			oldProductField.setBuyKnow(pt.getBuyKnow());
			oldProductField.setDeliveryMoney(pt.getDeliveryMoney());
			oldProductField.setIsSeckill(pt.getIsSeckill());
			oldProductField.setCategoryTreePath(pt.getCategoryTreePath());
			if(pt.getVipPrice()!=null && pt.getVipPrice()>0) {
				oldProductField.setVipPrice(pt.getVipPrice());
			}
			oldProductField.setExpireStartTime(pt.getExpireStartTime());
			oldProductField.setDeliveryTime(pt.getDeliveryTime());
			oldProductField.setSeoKeyWords(pt.getSeoKeyWords());
			oldProductField.setAfterShop(pt.getAfterShop());
			oldProductField.setGoodFlag(pt.getGoodFlag());
//			oldProductField.setOrderValue(pt.getOrderValue());
			oldProductField.setWeight(pt.getWeight());
			oldProductField.setWeightUnit(pt.getWeightUnit());
			oldProductField.setIsBest(pt.getIsBest());
			if(Checker.isNotEmpty(pt.getBuyLimit())) {
				ProductBuyLimit oldBuyLimit = JSON.parseObject(pt.getBuyLimit(), ProductBuyLimit.class);
				ProductBuyLimit tOldBuyLimit = null;
				if(oldBuyLimit!=null) {
					tOldBuyLimit = new ProductBuyLimit();
					tOldBuyLimit.setId(oldBuyLimit.getId());
					tOldBuyLimit.setMin(oldBuyLimit.getMin());
					tOldBuyLimit.setMax(oldBuyLimit.getMax());
					tOldBuyLimit.setMaxVip(oldBuyLimit.getMaxVip());
					tOldBuyLimit.setMaxCard(oldBuyLimit.getMaxCard());
					tOldBuyLimit.setStartTime(oldBuyLimit.getStartTime());
				}
				oldProductField.setBuyLimit(tOldBuyLimit);
			}
			if(Checker.isNotEmpty(pt.getPhotoList())) {
				oldProductField.setImageInfos(JSON.parseArray(pt.getPhotoList(), ProductImage.class));
			}
		} else {
			oldProductField.setClientId(p.getClientId());
			oldProductField.setMerchantId(p.getMerchantId());
			oldProductField.setProductId(p.getProductId());
			oldProductField.setStartTime(p.getStartTime());
			oldProductField.setDeliveryOption(p.getDeliveryOption());
			oldProductField.setName(p.getName());
			oldProductField.setFullName(p.getFullName());
			oldProductField.setPrice(p.getPrice());
			oldProductField.setCost(p.getCost());
			oldProductField.setMarketPrice(p.getMarketPrice());
			
			oldProductField.setIsLimit(p.getIsLimit());
			oldProductField.setBriefIntroduction(p.getBriefIntroduction());
			oldProductField.setIntroduction(p.getIntroduction());
			oldProductField.setBuyKnow(p.getBuyKnow());
			oldProductField.setDeliveryMoney(p.getDeliveryMoney());
			oldProductField.setIsSeckill(p.getIsSeckill());
			oldProductField.setCategoryTreePath(p.getCategoryTreePath());
			if(p.getVipPrice()!=null && p.getVipPrice()>0) {
				oldProductField.setVipPrice(p.getVipPrice());
			}
			
			if(pg!=null) {
				oldProductField.setExpireStartTime(pg.getExpireStartTime());
			} else if(pr!=null) {
				oldProductField.setExpireStartTime(pr.getExpireStartTime());
			}
			
			oldProductField.setDeliveryTime(p.getDeliveryTime());
			oldProductField.setSeoKeyWords(p.getSeoKeyWords());
			oldProductField.setAfterShop(p.getAfterShop());
			oldProductField.setGoodFlag(p.getGoodFlag());
//			oldProductField.setOrderValue(p.getOrderValue());
			oldProductField.setWeight(p.getWeight());
			oldProductField.setWeightUnit(p.getWeightUnit());
			oldProductField.setIsBest(p.getIsBest());
			oldProductField.setImageInfos(pd.getImageInfo());
			
			ProductBuyLimit tOldBuyLimit = null;
			if(pd.getBuyLimit()!=null) {
				tOldBuyLimit = new ProductBuyLimit();
				tOldBuyLimit.setId(pd.getBuyLimit().getId());
				tOldBuyLimit.setMin(pd.getBuyLimit().getMin());
				tOldBuyLimit.setMax(pd.getBuyLimit().getMax());
				tOldBuyLimit.setMaxVip(pd.getBuyLimit().getMaxVip());
				tOldBuyLimit.setMaxCard(pd.getBuyLimit().getMaxCard());
				tOldBuyLimit.setStartTime(pd.getBuyLimit().getStartTime());
			}
			oldProductField.setBuyLimit(tOldBuyLimit);
		}
		String newStr = JSON.toJSONString(newProductField);
		String oldStr = JSON.toJSONString(oldProductField);
		if(newStr.equals(oldStr)){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 判断此次修改是否有不需要审核字段发生变化
	 * @param pd mongo表中的限购，图片等信息
	 * @param pt 临时表中基础信息
	 * @param p
	 * @param pg
	 * @param pr
	 * @param productInfo 此次修改的字段信息
	 * @return
	 */
	private boolean isHaveNoNeedAudtitFieldUpdate(ProductDetail pd,ProductTemp pt,Product p,ProductGroup pg,ProductPresell pr,ProductInfo productInfo){
		Product product = productInfo.getProduct();
		
		ProductUpdateableField newProductField = new ProductUpdateableField();
		ProductUpdateableField oldProductField = new ProductUpdateableField();
		
		newProductField.setEndTime(product.getEndTime());
		newProductField.setStore(product.getStore());
		newProductField.setExpireEndTime(product.getExpireEndTime());
		
		ProductBuyLimit buyLimit = productInfo.getBuyLimit();
		if(buyLimit!=null && buyLimit.getEndTime()>0) {
			ProductBuyLimit tbuyLimit = new ProductBuyLimit();
			tbuyLimit.setEndTime(buyLimit.getEndTime());
			newProductField.setBuyLimit(tbuyLimit);
		}
		
		if(pt!=null) {
			oldProductField.setEndTime(pt.getEndTime());
			
			String key = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId());
			String store = redis.get(key);
			if(Checker.isNotEmpty(store)){
				oldProductField.setStore(Integer.valueOf(store));
			}
			
			oldProductField.setExpireEndTime(pt.getExpireEndTime());
			
			if(Checker.isNotEmpty(pt.getBuyLimit())) {
				ProductBuyLimit oldBuyLimit = JSON.parseObject(pt.getBuyLimit(), ProductBuyLimit.class);
				if(oldBuyLimit!=null && oldBuyLimit.getEndTime()>0) {
					ProductBuyLimit tOldBuyLimit = new ProductBuyLimit();
					tOldBuyLimit.setEndTime(oldBuyLimit.getEndTime());
					oldProductField.setBuyLimit(tOldBuyLimit);
				}
			}
		} else {
			oldProductField.setEndTime(p.getEndTime());
			
			String key = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId());
			String store = redis.get(key);
			if(Checker.isNotEmpty(store)){
				oldProductField.setStore(Integer.valueOf(store));
			}
			
			if(pg!=null) {
				oldProductField.setExpireEndTime(pg.getExpireEndTime());
			} else if(pr!=null) {
				oldProductField.setExpireEndTime(pr.getExpireEndTime());
			}
			
			if(pd.getBuyLimit()!=null && pd.getBuyLimit().getEndTime()>0) {
				ProductBuyLimit tOldBuyLimit = new ProductBuyLimit();
				tOldBuyLimit.setEndTime(pd.getBuyLimit().getEndTime());
				oldProductField.setBuyLimit(tOldBuyLimit);
			}
		}
		
		
		String newStr = JSON.toJSONString(newProductField);
		String oldStr = JSON.toJSONString(oldProductField);
		if(newStr.equals(oldStr)){
			return false;
		}
		return true;
	}
    

    /**
     * 查询单个商品详情
     * @param product
     * @return ProductViewInfo
     */
	@Override
	public ProductViewInfo getProduct(Product product) {
	    ProductViewInfo pv = null;
	    SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            List<ProductViewInfo> productViewInfos = productMapper.getProduct(product);//查询mysql中基础信息
            if(productViewInfos!=null && productViewInfos.size()>0){
                pv = productViewInfos.get(0);
                if(ProductType.group.getCode().equals(pv.getType())){//团购商品
                    List<ProductGroup> pgs = productMapper.getProductGroupByProductId(product);
                    if(pgs!=null && pgs.size()>0){
                        ProductGroup pg = pgs.get(0);
                        pv.setTrueBuyerNumber(pg.getTrueBuyerNumber());
                        pv.setVirtualBuyerNumber(pg.getVirtualBuyerNumber());
                        pv.setExpireStartTime(pg.getExpireStartTime());
                        pv.setExpireEndTime(pg.getExpireEndTime());
                    }
                } else if(ProductType.presell.getCode().equals(pv.getType())){//预售商品
                    List<ProductPresell> prs = productMapper.getProductPresellByProductId(product);
                    if(prs!=null &&prs.size()>0){
                        ProductPresell pr = prs.get(0);
                        pv.setProductSupplier(pr.getProductSupplier());
                        pv.setMaxPerOutlet(pr.getMaxPerOutlet());
                        pv.setDeliveryStartTime(pr.getDeliveryStartTime());
                        pv.setDeliveryEndTime(pr.getDeliveryEndTime());
                        pv.setTrueBuyerNumber(pr.getTrueBuyerNumber());
                        pv.setVirtualBuyerNumber(pr.getVirtualBuyerNumber());
                        pv.setExpireStartTime(pr.getExpireStartTime());
                        pv.setExpireEndTime(pr.getExpireEndTime());
                        pv.setClusterState(pr.getClusterState());
                        pv.setClusterType(pr.getClusterType());
                    }
                }
                
                CommonLogic commonLogic = new CommonLogicImpl();
                long ostart = System.currentTimeMillis();
                //机构名称orgName
                if(pv.getOrgCode()!=null && !"".equals(pv.getOrgCode().trim())){
                    Org org = commonLogic.queryByOrgCode(pv.getClientId(), pv.getOrgCode());
                    if(org!=null){
                        pv.setOrgName(org.getOrgName());
                    }
                }
                long oend = System.currentTimeMillis();
                LogCvt.info("H5用户查看商品详情查询mysql中机构信息总耗时:"+(oend-ostart));
                
                long mstart = System.currentTimeMillis();
                //取商户缓存
                Map<String,String> merchantMap = commonLogic.getMerchantRedis(pv.getClientId(), pv.getMerchantId());
				if(Checker.isNotEmpty(merchantMap)){
				    String merchantName=null;
	                String phone = null;//商户名称、签约到期时间、联系电话
	                Date contractEndtime = null;
					//商户缓存数据
					merchantName = merchantMap.get("merchant_name");
					if(Checker.isNotEmpty(merchantMap.get("contract_endtime"))){
					    contractEndtime = new Date(Long.valueOf(merchantMap.get("contract_endtime")));
					}
					phone = merchantMap.get("phone");
					
					pv.setMerchantName(merchantName);
	                pv.setContractEndtime(contractEndtime);
	                pv.setPhone(phone);
				}
                
                long mend = System.currentTimeMillis();
                LogCvt.info("H5用户查看商品详情查询mysql中机构信息总耗时:"+(mend-mstart));
            }
        } catch (Exception e) {
            LogCvt.error("查询 Product明细供修改查看product失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
		return pv;
	}
	
    
	/**
     * 查询存储在mongo中的商品信息
	 */
    @Override
    public ProductDetail getProductDetail(Product product,DBObject keysColl) {
        try{
            DBObject where = new BasicDBObject();
            if(Checker.isNotEmpty(product.getClientId())){
                where.put("client_id", product.getClientId());
            }
            where.put("_id", product.getProductId());
            ProductDetail productDetail = null;
            if(keysColl!=null){
            	productDetail = manager.findOne(where, keysColl, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            } else {
            	productDetail = manager.findOne(where, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            }
            return productDetail;
        } catch (Exception e) { 
            LogCvt.error("获取ProductDetail失败，原因:" + e.getMessage(),e); 
        }
        return null;
    }
    
    
    @Override
    public List<ActivitiesInfo> getPointActivities(Map<String, Object> param) {
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            List<ActivitiesInfo> acs = productMapper.getPointActivities(param);
            return acs;
        }catch(Exception e){
            LogCvt.error("根据ids查询活动失败，原因:" + e.getMessage(),e); 
        }finally{
             if(null != sqlSession) {
                 sqlSession.close();
             }
        }
        return null;
    }


    /**
     * 上下架商品
     * @param product
     * @return Result
     */
    @Override
    public Result updateProductStatus(Product product) {
        Result result = new Result();
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            List<Product> ps = productMapper.getProductByProductId(product);
            if(ps!=null && ps.size()>0){
                Product p = ps.get(0);
                Date checkRackTime=p.getRackTime();
                Date checkDownTime=p.getDownTime();
                if(product.getIsMarketable().equals(ProductStatus.onShelf.toString())) {
                    if(!ProductAuditState.passAudit.toString().equals(p.getAuditState())) {
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("审核通过才可以 上架操作");
                        return result;
                    }
                }
                if(product.getIsMarketable().equals(ProductStatus.offShelf.toString())) {
                	if(!ProductAuditState.passAudit.toString().equals(p.getAuditState())) {
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("审核通过才可以下架操作");
                        return result;
                    }
                }
                if(product.getIsMarketable().equals(ProductStatus.offShelf.toString()) && !ProductStatus.onShelf.toString().equals(p.getIsMarketable())) {
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("上架后的商品才可以操作下架");
                    return result;
                }
                CommonLogic comLogic = new CommonLogicImpl();
                Map<String,String> merchantMap = comLogic.getMerchantRedis(p.getClientId(), p.getMerchantId());
                if(merchantMap==null || merchantMap.size()==0) {
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("商户不存在");
                    return result;
                }  else if(merchantMap.get("is_enable")==null || "0".equals(merchantMap.get("is_enable"))) {
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc(merchantMap.get("merchant_name")+"商户已失效");
                    return result;
                }
                
                Date now = new Date();
                if(product.getIsMarketable().equals(ProductStatus.noShelf.getCode())) {
                    product.setRackTime(null);
                    product.setDownTime(null);
                } else if(product.getIsMarketable().equals(ProductStatus.onShelf.getCode())) {
                    product.setRackTime(now);
                } else if(product.getIsMarketable().equals(ProductStatus.offShelf.getCode())) {
                    product.setDownTime(now);
                }
                //更新mysql
                productMapper.updateProductStatus(product); 
                sqlSession.commit(true);
                
                /* 更新redis缓存is_marketable */
                String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(p.getClientId(), p.getMerchantId(), p.getProductId());
                redis.hset(key, "is_marketable", product.getIsMarketable());
                //更新mongo is_marketable
                DBObject value = new BasicDBObject();
                value.put("is_marketable", product.getIsMarketable());
                if(ProductStatus.onShelf.getCode().equals(product.getIsMarketable())) {
                    value.put("rack_time", now.getTime());
                }
                DBObject where = new BasicDBObject();
                where.put("client_id", p.getClientId());
                if(p.getMerchantId()!=null && !"".equals(p.getMerchantId())) {
                    where.put("merchant_id", p.getMerchantId());
                }
                where.put("_id", product.getProductId());
                manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");//更新mongo is_marketable
                //打印修改日志
                addProductOperaLog(p.getProductId(), checkRackTime,checkDownTime,"1",null, "update");
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("该商品不存在");
                return result;
            }
        } catch (Exception e) {
        	if(null != sqlSession)
        		sqlSession.rollback(true);  
            LogCvt.error("上下架 Product失败，原因:" + e.getMessage(),e); 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("上下架 Product失败");
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return result; 
    }


    @Override
    public Boolean isProductExist(Product product) {
        Boolean isExist = false;
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            Integer productNum = productMapper.getProductCount(product);
            if(productNum!=null && productNum>0) {
                isExist = true;
            } 
        } catch (Exception e) {
            isExist = false;
            LogCvt.error("判断商品是否重复存在 Product失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return isExist; 
    }

    /**
     * 商品分页查询 管理平台的分页查询 查询mysql中的商品信息:boss平台新加的商品看不到
     */
    @Override
    public List<ProductListInfo> findProductListByPage(
            Page<ProductListInfo> page, ProductFilter product) {
        List<ProductListInfo> list = null;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            Map<String,List<String>> param = null;
            if(Checker.isNotEmpty(product.getClientId()) && Checker.isNotEmpty(product.getOrgCode())){
            	String[] orgCodes = product.getOrgCode().split(",");
            	CommonLogic comLogic = new CommonLogicImpl();
            	product.setProOrgCode(null);
                product.setOrgCode(null);
                product.setCityOrgCode(null);
                product.setCountryOrgCode(null);
            	if(orgCodes!=null && orgCodes.length>1){
            		param = new HashMap<String,List<String>>();
            		List<String> proOrgCodes = new ArrayList<String>();
            		List<String> cityOrgCodes = new ArrayList<String>();
            		List<String> countryOrgCodes = new ArrayList<String>();
            		List<String> forgCodes = new ArrayList<String>();
            		for(String orgCode : orgCodes){
            			Org bankOrg = comLogic.getOrgByOrgCode(orgCode, product.getClientId());
            			if(bankOrg!=null){
                            if(OrgLevelEnum.orgLevel_one.getLevel().equals(bankOrg.getOrgLevel())){
                            	proOrgCodes.add(bankOrg.getOrgCode());
                            } else if(OrgLevelEnum.orgLevel_two.getLevel().equals(bankOrg.getOrgLevel())){
                                cityOrgCodes.add(bankOrg.getOrgCode());
                            } else if(OrgLevelEnum.orgLevel_three.getLevel().equals(bankOrg.getOrgLevel())){
                                countryOrgCodes.add(bankOrg.getOrgCode());
                            } else if(OrgLevelEnum.orgLevel_four.getLevel().equals(bankOrg.getOrgLevel())){
                                forgCodes.add(bankOrg.getOrgCode());
                            }
                        }
            		}
            		if(proOrgCodes.size()>0){
            			param.put("province_agency", proOrgCodes);
            		}
            		if(cityOrgCodes.size()>0){
            			param.put("city_agency", cityOrgCodes);
            		}
            		if(countryOrgCodes.size()>0){
            			param.put("county_agency", countryOrgCodes);
            		}
            		if(forgCodes.size()>0){
            			param.put("org_code", forgCodes);
            		}
            	} else if(orgCodes!=null && orgCodes.length==1){
            		//根绝机构号查询该机构可以看到的商品
                    Org bankOrg = comLogic.getOrgByOrgCode(orgCodes[0], product.getClientId());
                    if(bankOrg!=null){
                        if(OrgLevelEnum.orgLevel_one.getLevel().equals(bankOrg.getOrgLevel())){
                            product.setProOrgCode(bankOrg.getOrgCode());
                        } else if(OrgLevelEnum.orgLevel_two.getLevel().equals(bankOrg.getOrgLevel())){
                            product.setProOrgCode(bankOrg.getProvinceAgency());
                            product.setCityOrgCode(bankOrg.getOrgCode());
                        } else if(OrgLevelEnum.orgLevel_three.getLevel().equals(bankOrg.getOrgLevel())){
                            product.setProOrgCode(bankOrg.getProvinceAgency());
                            product.setCityOrgCode(bankOrg.getCityAgency());
                            product.setCountryOrgCode(bankOrg.getOrgCode());
                        } else if(OrgLevelEnum.orgLevel_four.getLevel().equals(bankOrg.getOrgLevel())){
                            product.setProOrgCode(bankOrg.getProvinceAgency());
                            product.setCityOrgCode(bankOrg.getCityAgency());
                            product.setCountryOrgCode(bankOrg.getCountyAgency());
                            product.setOrgCode(bankOrg.getOrgCode());
                        }
                    }
            	}
            }
            long startTime = System.currentTimeMillis();
            product.setExcludePlatType(PlatType.boss.getValue()+"");
            list =  productMapper.findProductListByPage(page, product, param);
            if(list!=null && list.size()>0) {
            	List<String> productIds = new ArrayList<String>();
            	for(ProductListInfo p : list) {
            		if(ProductAuditState.passAudit.getCode().equals(p.getAuditState()) && ProductType.group.getCode().equals(p.getType())) {
            			productIds.add(p.getProductId());
            		}
            	}
            	if(productIds.size()>0) {
            		List<ProductTemp> pts = productMapper.getTempProductByProductIds(productIds);
            		if(pts!=null && pts.size()>0) {
            			Map<String,String> auditStates = new HashMap<String,String>();
            			for(ProductTemp pt : pts) { 
            				auditStates.put(pt.getProductId(), pt.getAuditState());
            			}
            			String auditState = null;
            			for(ProductListInfo pp : list) {
            				auditState = auditStates.get(pp.getProductId());
                    		if(ProductAuditState.passAudit.getCode().equals(pp.getAuditState()) && auditState!=null) {
                    			//商品审核状态对应的名称("0审核中","1审核通过","2审核不通过","3未提交","4审核通过(更新中)","5审核通过(更新审核未通过)"，6更新未提交)
                    	        if(ProductAuditState.noAudit.getCode().equals(auditState)) {
                    	        	pp.setAuditState("4");
                    	        } else if(ProductAuditState.failAudit.getCode().equals(auditState)) {
                    	        	pp.setAuditState("5");
                    	        } else if(ProductAuditState.noCommit.getCode().equals(auditState)) {
                    	        	pp.setAuditState("6");
                    	        }
                    		}
                    	}
            		}
            	}
            }
            long endTime = System.currentTimeMillis();
            LogCvt.info("-------mapper中findProductListByPage:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
            
        }catch (Exception e) {
            LogCvt.error("商品管理分页查询list Product失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return list;
    }
    
    /**
     * H5分页查询商品 查询mongo中的商品信息
     * @param page
     * @param product
     * @return MongoPage
     */
    @Override
    public MongoPage findProductsByPage(
            Page<ProductViewInfo> page, ProductFilter product) {
        // mongo操作
        
        MongoPage mPage = new MongoPage(page.getPageNumber(), page.getPageSize());
        DBObject dbObject = new BasicDBObject();
        try{
            /**********************操作Mongodb数据库**********************/
        	
        	//根据区code转换成区id
        	if(Checker.isNotEmpty(product.getAreaCode())){
                SqlSession sqlSession = null;
                try{
                    sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
                    ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
                    
                    Map<String,String> param = new HashMap<String,String>();
                    
                    if(product.getClientId()!=null && !"".equals(product.getClientId())){
                    	param.put("clientId", product.getClientId());
                    }
                    param.put("areaCode", product.getAreaCode());
                    Area a = productMapper.findAreaByCode(param);
                    if(a!=null){
                        String areaTreePath = a.getTreePath();
                        if(Checker.isNotEmpty(areaTreePath)){
                        	String[] treePtah = areaTreePath.split(",");
                            if(treePtah.length==3){//areaId为区
                            	product.setAreaId(a.getId());
                            } else if(treePtah.length==2){//areaId为市
                                product.setCityId(a.getId());
                            }
                        }
                    }
                } catch (Exception e) { 
                    LogCvt.error("根据区code转换成区id失败，原因:" + e.getMessage(),e); 
                } finally { 
                    if(null != sqlSession) {
                        sqlSession.close(); 
                    } 
                } 
            } else if(product.getAreaId()!=null && product.getAreaId()>0){//根据areaid判断areaid是市级地区id还是区级地区id
            	CommonLogic com = new CommonLogicImpl();
                Area area = com.findAreaById(product.getAreaId());
                if(area!=null){
                    //最后的节点
                    int level = 0;
                    if(area.getTreePath()!=null){
                        String[] areas = area.getTreePath().split(",");
                        level = areas.length;
                    }
                    if(level==2){//两级代表市
                    	product.setCityId(product.getAreaId());
                    }
                }
            }
            
            //设置排序，支持多个字段排序
            if(product.getOrderFileds()!=null && product.getOrderFileds().size()>0){
                Sort sort = null;
                String value = null;
                for(String key : product.getOrderFileds().keySet()){
                    value = product.getOrderFileds().get(key);
                    if(value!=null){
                        if(sort==null){
                            sort = new Sort(key,value.equalsIgnoreCase("desc")?OrderBy.DESC:OrderBy.ASC);
                        } else {
                            sort.on(key,value.equalsIgnoreCase("desc")?OrderBy.DESC:OrderBy.ASC);
                        }
                    }
                }
                mPage.setSort(sort);
            } else {
                if(ProductType.group.getCode().equals(product.getType())){
                    //团购
                    mPage.setSort(new Sort("sell_cout", OrderBy.DESC));
                } else if(ProductType.presell.getCode().equals(product.getType())){
                    //预售
                    mPage.setSort(new Sort("start_time", OrderBy.DESC));
                }
            }
            if(product.getClientId()!=null && !"".equals(product.getClientId())){
                dbObject.put("client_id", product.getClientId());
            }
            if(product.getMerchantId()!=null && !"".equals(product.getMerchantId())){
                dbObject.put("merchant_id", product.getMerchantId());
            }
            if(product.getName()!=null && !"".equals(product.getName())){
    			String pro_name = product.getName();
    			StringBuilder regexStr = new StringBuilder( ".*");
    			regexStr.append(pro_name).append(".*");
    			Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
    			BasicDBObject like = new BasicDBObject();
    			like.append("$regex", pattern);
                dbObject.put("name", like);
            }
            if(product.getFullName()!=null && !"".equals(product.getFullName())){
    			String pro_full_name = product.getFullName();
    			StringBuilder regexStr = new StringBuilder( ".*");
    			regexStr.append(pro_full_name).append(".*");
    			Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
    			BasicDBObject like = new BasicDBObject();
    			like.append("$regex", pattern);
                dbObject.put("full_name", like);
            }
            if(product.getDeliveryOption()!=null && !"".equals(product.getDeliveryOption())){
                dbObject.put("delivery_option", product.getDeliveryOption());
            }
            if(product.getType()!=null && !"".equals(product.getType()) && !"0".equals(product.getType())){
                dbObject.put("product_type", product.getType());
            }
            if(product.getIsBest()!=null && !"".equals(product.getIsBest())){
                dbObject.put("is_best", BooleanUtils.toInteger(product.getIsBest(), 1, 0, 0));
            }
            if(product.getIsLimit()!=null && !"".equals(product.getIsLimit())){
                dbObject.put("is_limit", BooleanUtils.toInteger(product.getIsLimit(), 1, 0, 0));
            }
            if(product.getIsHot()!=null){
                dbObject.put("sell_count", new BasicDBObject(QueryOperators.GTE,GoodsConstants.HOT_SELLl_COUNT));
            } else {
                if(product.getSellCountStart()!=null && product.getSellCountStart()>0 && product.getSellCountEnd()!=null && product.getSellCountEnd()>0){
                    dbObject.put("sell_count", new BasicDBObject(QueryOperators.GTE,product.getSellCountStart()).append(QueryOperators.LTE,product.getSellCountEnd()));
                } else if(product.getSellCountStart()!=null && product.getSellCountStart()>0){
                    dbObject.put("sell_count", new BasicDBObject(QueryOperators.GTE,product.getSellCountStart()));
                } else if(product.getSellCountEnd()!=null && product.getSellCountEnd()>0){
                    dbObject.put("sell_count", new BasicDBObject(QueryOperators.LTE,product.getSellCountEnd()));
                }
            }
            if(product.getMarketPriceStart()!=null && product.getMarketPriceStart()>0 && product.getMarketPriceEnd()!=null && product.getMarketPriceEnd()>0){
                dbObject.put("market_price", new BasicDBObject(QueryOperators.GTE,product.getMarketPriceStart()).append(QueryOperators.LTE,product.getMarketPriceEnd()));
            } else if(product.getMarketPriceStart()!=null && product.getMarketPriceStart()>0){
                dbObject.put("market_price", new BasicDBObject(QueryOperators.GTE,product.getMarketPriceStart()));
            } else if(product.getMarketPriceEnd()!=null && product.getMarketPriceEnd()>0){
                dbObject.put("market_price", new BasicDBObject(QueryOperators.LTE,product.getMarketPriceEnd()));
            }
            if(product.getPriceStart()!=null && product.getPriceStart()>0 && product.getPriceEnd()!=null && product.getPriceEnd()>0){
                dbObject.put("price", new BasicDBObject(QueryOperators.GTE,product.getPriceStart()).append(QueryOperators.LTE,product.getPriceEnd()));
            } else if(product.getPriceStart()!=null && product.getPriceStart()>0){
                dbObject.put("price", new BasicDBObject(QueryOperators.GTE,product.getPriceStart()));
            } else if(product.getPriceEnd()!=null && product.getPriceEnd()>0){
                dbObject.put("price", new BasicDBObject(QueryOperators.LTE,product.getPriceEnd()));
            }
            if(product.getMerchantName()!=null && !"".equals(product.getMerchantName())){
                dbObject.put("merchant_name", new BasicDBObject("$regex",Pattern.compile("^.*"+product.getMerchantName()+".*$", Pattern.CASE_INSENSITIVE).toString()));
            }
            if(product.getStartTimeStart()!=null && product.getStartTimeEnd()!=null){
                dbObject.put("start_time", new BasicDBObject(QueryOperators.GTE,product.getStartTimeStart().getTime()).append(QueryOperators.LTE,product.getStartTimeEnd().getTime()));
            } else if(product.getStartTimeStart()!=null){
                dbObject.put("start_time", new BasicDBObject(QueryOperators.GTE,product.getStartTimeStart().getTime()));
            } else if(product.getStartTimeEnd()!=null){
                dbObject.put("start_time", new BasicDBObject(QueryOperators.LTE,product.getStartTimeEnd().getTime()));
            }
            if(product.getEndTimeStart()!=null && product.getEndTimeEnd()!=null){
                dbObject.put("end_time", new BasicDBObject(QueryOperators.GTE,product.getEndTimeStart().getTime()).append(QueryOperators.LTE,product.getEndTimeEnd().getTime()));
            } else if(product.getEndTimeStart()!=null){
                dbObject.put("end_time", new BasicDBObject(QueryOperators.GTE,product.getEndTimeStart().getTime()));
            } else if(product.getEndTimeEnd()!=null){
                dbObject.put("end_time", new BasicDBObject(QueryOperators.LTE,product.getEndTimeEnd().getTime()));
            }
            Long now = new Date().getTime();
            if(product.getPresellNum()!=null && !"".equals(product.getPresellNum())){
                //1正在预售,2下期预售,3往期预售
                if("1".equals(product.getPresellNum())){
                	//已经上架的查询出来
                    dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
                    
                    dbObject.put("start_time", new BasicDBObject(QueryOperators.LTE,now));
                    dbObject.put("end_time", new BasicDBObject(QueryOperators.GTE,now));
                } else if("2".equals(product.getPresellNum())){
                	//已经上架的查询出来
                    dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
                    
                    dbObject.put("start_time", new BasicDBObject(QueryOperators.GT,now));
                } else if("3".equals(product.getPresellNum())){
                	//上架 下架也查询出来
                    List<String> productStatus =  new ArrayList<String>();
                    productStatus.add(ProductStatus.onShelf.getCode());
                    productStatus.add(ProductStatus.offShelf.getCode());
                    dbObject.put("is_marketable", new BasicDBObject("$in", productStatus));
                    
                    dbObject.put("end_time", new BasicDBObject(QueryOperators.LT,now));
                } else {
                	//已经上架的查询出来
                    dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
                }
            } else {
            	//已经上架的查询出来
                dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
            }
//            if(dbObject.get("start_time")==null){
//                dbObject.put("start_time", new BasicDBObject(QueryOperators.LTE,now));
//            }
            if(dbObject.get("end_time")==null){
                dbObject.put("end_time", new BasicDBObject(QueryOperators.GTE,now));
            }
            if(product.getCategoryId()!=null && !"".equals(product.getCategoryId()) && !"-1".equals(product.getCategoryId())){
                dbObject.put("product_category_info.product_category_id", product.getCategoryId());
            }

            //线下积分查询
            if(ProductType.dotGift.getCode().equals(product.getType())){
                if(product.getCityId()!=null && product.getCityId()>0){
                    dbObject.put("city_areas.city_id", product.getCityId());
                } else if(product.getAreaId()!=null && product.getAreaId()>0){
                    dbObject.put("city_areas.countys.area_id", product.getAreaId());                
                }
                if(product.getOutletId()!=null && !"".equals(product.getOutletId())){
                    dbObject.put("org_outlets.org_outlets.outlet_id", product.getOutletId());
                }
            }else{
            	if(product.getCityId()!=null && product.getCityId()>0){
                    List<DBObject> orArray = new ArrayList<DBObject>();
                    orArray.add(new BasicDBObject("delivery_option",  new BasicDBObject(QueryOperators.NE, DeliveryType.take.getCode())));
                    DBObject aa = new BasicDBObject("delivery_option",  DeliveryType.take.getCode());
                    aa.put("city_areas.city_id", product.getCityId());
                    orArray.add(aa);                  
                    dbObject.put("$or", JSON.toJSON(orArray));     
                } else if(product.getAreaId()!=null && product.getAreaId()>0){
                    List<DBObject> orArray = new ArrayList<DBObject>();
                    orArray.add(new BasicDBObject("delivery_option",  new BasicDBObject(QueryOperators.NE, DeliveryType.take.getCode())));
                    DBObject aa = new BasicDBObject("delivery_option",  DeliveryType.take.getCode());
                    aa.put("city_areas.countys.area_id", product.getAreaId());  
                    orArray.add(aa);                  
                    dbObject.put("$or", JSON.toJSON(orArray));     
                }
            }

            
            mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            @SuppressWarnings("unchecked")
			List<ProductDetail> productDetails = (List<ProductDetail>)mPage.getItems();
            if(null != productDetails) {
                for (ProductDetail productDetail : productDetails) {
                    String sotreStr = redis.getString(RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(product.getClientId(), productDetail.getMerchantId(), productDetail.getId()));
                    if(NumberUtils.isNumber(sotreStr)){
                        productDetail.setStore(Integer.parseInt(sotreStr));
                    }
                }
            }
        } catch (Exception e) { 
            LogCvt.error("查询OutletProduct失败，原因:" + e.getMessage(),e); 
        }
        return mPage;
    }
    
    
    /**
     * 新加商品存储到mongo中
     * @param productInfo
     * @return boolean
     */
    public boolean addProductDetail(ProductInfo productInfo) {
        boolean isSuccess = false;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            ProductDetail productDetail = changToProductDetail(productInfo,sqlSession,productMapper);
            if(productDetail!=null){
                isSuccess = manager.add(productDetail, MongoTableName.CB_PRODUCT_DETAIL) > -1;// 向mongodb插入数据
            }
            
        } catch (Exception e) { 
            LogCvt.error("添加ProductDetail失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return isSuccess;
    }
    
    /**
     * 更新商品时候需要更新商品mongo表
     * @param productInfo
     * @return ProductDetail
     */
    public ProductDetail updateProductDetail(ProductInfo productInfo) {
        boolean isSuccess = false;
        SqlSession sqlSession = null;
        try{
            if(productInfo!=null && productInfo.getProduct()!=null && productInfo.getProduct().getProductId()!=null && !"".equals(productInfo.getProduct().getProductId())){
                
                sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
                ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
                
                ProductDetail productDetail = changToProductDetail(productInfo,sqlSession,productMapper);
                DBObject where = new BasicDBObject();
                if(productInfo.getProduct().getClientId()!=null &&!"".equals(productInfo.getProduct().getClientId())){
                    where.put("client_id", productInfo.getProduct().getClientId());
                }
                where.put("_id", productInfo.getProduct().getProductId());
                Object whereObject = where;
                
                DBObject seach=new BasicDBObject();
                seach.put("_id",productInfo.getProduct().getProductId());
                seach.put("client_id", productInfo.getProduct().getClientId());
                ProductDetail pd= manager.findOne(seach, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                if(pd!=null){
                    //修改商品时销量，库存量，创建时间，活动信息 不修改
                    productDetail.setPlatType(pd.getPlatType());
                    productDetail.setSellCount(pd.getSellCount());
                    productDetail.setStoreCount(pd.getStoreCount());
                    if(pd.getCreateTime()!=null){
                        productDetail.setCreateTime(pd.getCreateTime());
                    }
                    productDetail.setActivitiesInfo(pd.getActivitiesInfo());
                    
                    //保留VIP规则绑定关系
                    productDetail.setVipPrice(pd.getVipPrice());
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
                    isSuccess =  manager.update(productDetail, whereObject, MongoTableName.CB_PRODUCT_DETAIL, "$set")> -1;// 向mongodb更新数据
                    boolean isUnset = false;
                    DBObject valueObj = new BasicDBObject();
                    DBObject w1 = new BasicDBObject();
                    if(productDetail.getBuyLimit()==null){
                        //删除buy_limit这个字段值
                        w1.put("buy_limit", 1);
                        isUnset = true;
                    }
                    if(productDetail.getCityAreas()==null || productDetail.getCityAreas().size()==0){//市级-区关系
                        //删除city_areas这个字段值
                        w1.put("city_areas", 1);
                        isUnset = true;
                    }
                    if(productDetail.getOrgOutlets()==null || productDetail.getOrgOutlets().size()==0){//市级-门店关系
                    	//删除org_outlets这个字段值
                        w1.put("org_outlets", 1);
                        isUnset = true;
                    }
                    if(productDetail.getOrgCodes()==null || productDetail.getOrgCodes().size()==0){//商品对应网店所属的机构代码列表
                    	//删除org_codes这个字段值
                        w1.put("org_codes", 1);
                        isUnset = true;
                    }
                    if(productDetail.getActivitiesInfo()==null || productDetail.getActivitiesInfo().size()==0){//商品活动
                    	//删除activities_info这个字段值
                        w1.put("activities_info", 1);
                        isUnset = true;
                    }
                    if(productDetail.getProductCategoryInfo()==null || productDetail.getProductCategoryInfo().size()==0){// 商品分类
                    	//删除product_category_info这个字段值
                        w1.put("product_category_info", 1);
                        isUnset = true;
                    }
                    if(isUnset){
                    	valueObj.put("$unset", w1);
                        manager.update(valueObj, where, MongoTableName.CB_PRODUCT_DETAIL, null);//更新mongo vip_price buy_limit和is_limit
                    }
                    
                } else {
                    isSuccess = manager.add(productDetail, MongoTableName.CB_PRODUCT_DETAIL) > -1;// 向mongodb插入数据
                }
                if(isSuccess){
                    return productDetail;
                }
            }
        } catch (Exception e) { 
            LogCvt.error("修改ProductDetail失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        }
        return null;
    }
    
    /**
     * 将商品信息转换成mongo中存储的信息对象
     * @param productInfo
     * @param sqlSession
     * @param productMapper
     * @return ProductDetail
     */
    public ProductDetail changToProductDetail(ProductInfo productInfo,SqlSession sqlSession,ProductMapper productMapper) {
        
        ProductDetail productDetail = new ProductDetail(); 
        try{
            //基础信息设置
            Product product = productInfo.getProduct();
            productDetail.setId(product.getProductId());
            productDetail.setPlatType(productInfo.getPlatType());
            productDetail.setIsSeckill(product.getIsSeckill());
            productDetail.setIsBest(BooleanUtils.toInteger(product.getIsBest(), 1, 0, 0));
            productDetail.setIsLimit(BooleanUtils.toInteger(product.getIsLimit(), 1, 0, 0));
            productDetail.setName(product.getName());
            productDetail.setFullName(product.getFullName());
            productDetail.setBriefIntroduction(product.getBriefIntroduction());
            productDetail.setProductType(product.getType());
            productDetail.setPrice(product.getPrice());
            productDetail.setMarketPrice(product.getMarketPrice());
            productDetail.setClientId(product.getClientId());
            productDetail.setIsMarketable(product.getIsMarketable());
            productDetail.setDeliveryOption(product.getDeliveryOption());
            productDetail.setSellCount(product.getSellCount());
            productDetail.setStartTime(product.getStartTime());
            productDetail.setEndTime(product.getEndTime());
            productDetail.setRackTime(product.getRackTime());
            productDetail.setMerchantId(product.getMerchantId());
            productDetail.setMerchantName(product.getMerchantName());
            productDetail.setCreateTime(new Date());
            
            //图片关联信息设置
            if(productInfo.getProductImages()!=null && productInfo.getProductImages().size()>0){
                List<ProductImage> imageInfo = new  ArrayList<ProductImage>();
                for(ProductImage image : productInfo.getProductImages()){
                    imageInfo.add(image);
                }
                productDetail.setImageInfo(imageInfo);
            }
            
            //商品分类信息设置
            List<ProductCategoryInfo> productCategoryInfo = new ArrayList<ProductCategoryInfo>();
            
            String categoryTreePath = product.getCategoryTreePath();
            if(Checker.isNotEmpty(categoryTreePath)){
            	boolean isContain = false;
            	ProductCategoryInfo productCategory = null;
            	
            	String[] pcids = categoryTreePath.split(" ");
            	if(pcids!=null && pcids.length>0) {
                	for(String pcid : pcids) {
                        productCategory = new ProductCategoryInfo();
                        if(Checker.isNotEmpty(pcid)) {
                        	productCategory.setProductCategoryId(Long.valueOf(pcid));
                        	productCategoryInfo.add(productCategory);
                        	if(productInfo.getProductCategory().getId().longValue()==Long.valueOf(pcid).longValue()){
                                isContain=true;
                            }
                        }
                    }
            	}
                
                //该分类的父分类的id也会保留到mongo里
            	if(isContain==false){
                    productCategory = new ProductCategoryInfo();
                    productCategory.setProductCategoryId(productInfo.getProductCategory().getId());
                    productCategoryInfo.add(productCategory);
                }
                
            } else {
            	ProductCategoryInfo productCategory = new ProductCategoryInfo();
                productCategory.setProductCategoryId(productInfo.getProductCategory().getId());
                productCategoryInfo.add(productCategory);
            }
            productDetail.setProductCategoryInfo(productCategoryInfo);
            
            //购买限制信息设置
            if(product.getIsLimit() && productInfo.getBuyLimit()!=null){
                productDetail.setBuyLimit(productInfo.getBuyLimit());
            }
            
            //前端页面所选的门店对应的机构代码,供查看详细信息时供前端直接勾选已经选择的网点机构
            productDetail.setOrgCodes(productInfo.getOrgCodes());
            //门店信息设置
            if(productInfo.getProductOutlets()!=null && productInfo.getProductOutlets().size()>0){
                productDetail.setOutletInfo(productInfo.getProductOutlets());
                //获取门店和区域 将门店所在的区冗余到市级-区和市级-门店集合中
                setAreasOutletByOutletids(productDetail,productMapper);
                productDetail.setOutletInfo(null);
            } else if(ProductType.group.getCode().equals(product.getType())){//团购商品的 去查询该商品所属的商户下所有有效门店所在的区冗余到市级-区集合中
            	if(Checker.isNotEmpty(product.getMerchantId())){
            		CommonLogic comLogic = new CommonLogicImpl();
                    List<Outlet> outlets = comLogic.getOutletListByMerchantIdOrOutletId(product.getMerchantId(), null);
                    if(outlets!=null && outlets.size()>0){
                    	//获取门店和区域 将门店所在的区冗余到市级-区和市级-门店集合中
                    	productDetail = ProductDetailMongoUtil.setAreaOutletToGroupProduct(productDetail, outlets, null);
                    }
            	}
            }
        } catch (Exception e) { 
            LogCvt.error("changeToProductDetail失败，原因:" + e.getMessage(),e); 
        }
        return productDetail;
    }
    
    


    @Override
    public OutletProductQrCode addOutletProduct(OutletProduct outletProduct) {
    	long start = System.currentTimeMillis();
        OutletProductQrCode po = null;
        try{
            
            DBObject dbObject = new BasicDBObject();
            if(outletProduct.getClientId()!=null&&!"".equals(outletProduct.getClientId())){
                dbObject.put("client_id", outletProduct.getClientId());
            }
            if(outletProduct.getMerchantId()!=null && !"".equals(outletProduct.getMerchantId())){
                dbObject.put("merchant_id", outletProduct.getMerchantId());
            }
            if(outletProduct.getOutletId()!=null && !"".equals(outletProduct.getOutletId())){
                dbObject.put("outlet_id", outletProduct.getOutletId());
            }
            if(outletProduct.getCost()>0){
                dbObject.put("cost", outletProduct.getCost());
            }
            OutletProduct bean = manager.findOne(dbObject, MongoTableName.CB_OUTLET_PRODUCT, OutletProduct.class);
            String productId = null;
            if(bean==null){
                outletProduct.setId(simpleID.nextId());
                
                DBObject opdbObject = new BasicDBObject();
                opdbObject.put("_id", outletProduct.getId());
                opdbObject.put("client_id", outletProduct.getClientId());
                opdbObject.put("merchant_id", outletProduct.getMerchantId());
                opdbObject.put("outlet_id", outletProduct.getOutletId());
                opdbObject.put("cost", outletProduct.getCost());
                
                boolean isSuccess = manager.add(opdbObject, MongoTableName.CB_OUTLET_PRODUCT) !=-1;// 向mongodb插入数据
                if(isSuccess){
                    productId = outletProduct.getId();
                }
            } else {
                productId = bean.getId();
            }
            if(productId!=null){
                po = new OutletProductQrCode();
                String simpId = simpleID.nextId();
                
                long end1 = System.currentTimeMillis();
                LogCvt.debug("向mongo中新加面对面商品用时:"+(end1-start));
                
                po.setProductId(productId);
                po.setQrCode(simpId);
//                String qrCodeUrl = generateQrCode(outletProduct.getClientId(),simpId,QrCodeType.FACE2FACE.getCode());
                /**
                 * 面对面生成二维码时候 二维码内容得是完整的url
                 * https://环境mp.ubank365.com/clientId/m/order/qrcode?type=xxxxxx&id=xxxxxxx
                 * 中间有一个clientId, clientId ＝> {taizhou, chongqing} 之类的我们现有的clientId
                 * 
                 * https://dev1mp.ubank365.com/clientId/m/order/qrcode?type=xxqrCodeType&id=xxproductId
                 */
                String opQrcodeContentUrl = GoodsConstants.OUTLET_PRODUCT_QRCODE_CONTENT_URL;
                String keyword = opQrcodeContentUrl.replaceAll("clientId", outletProduct.getClientId())
                		.replaceAll("xxqrCodeType", QrCodeType.FACE2FACE.getCode())
                		.replaceAll("xxproductId", simpId);
                String qrCodeUrl = generateQrCode(outletProduct.getClientId(),keyword,QrCodeType.URLFACE2FACE.getCode());
                
                long end2 = System.currentTimeMillis();
                LogCvt.debug("新加面对面商品生成二维码用时:"+(end2-end1));
                
                po.setUrl(qrCodeUrl);
                String key = RedisKeyUtil.cbbank_outlet_product_clientId_qrcode(outletProduct.getClientId(),po.getQrCode());
                
                
                redis.hset(key, "state", "1");
                redis.hset(key, "id", productId+"_"+outletProduct.getUserId());
                
                long end3 = System.currentTimeMillis();
                LogCvt.debug("新加面对面商品redis缓存用时:"+(end3-end2));
            }
        } catch (Exception e) { 
            LogCvt.error("添加面对面商品失败，原因:" + e.getMessage(),e); 
        }
        return po; 
    }
    
    /**
     * 获取面对面商品详细信息
     */
    public OutletProductInfo getOutletProduct(OutletProductInfo outletProductInfo){
        try{
            //从缓存拿出二维码和面对面商品对应的商品id对应关系
            String key = RedisKeyUtil.cbbank_outlet_product_clientId_qrcode(outletProductInfo.getClientId(),outletProductInfo.getQrCode());
            
            String filed = redis.hget(key, "id");//productId+"_"+outletProduct.getUserId());
            if(filed!=null && !"".equals(filed) && filed.indexOf("_")!=-1){
                String[] productId_userId = filed.split("_");
                String productId = productId_userId[0];
                
                DBObject dbObject = new BasicDBObject();
                dbObject.put("client_id", outletProductInfo.getClientId());
                dbObject.put("_id", productId);
                OutletProduct bean = manager.findOne(dbObject, MongoTableName.CB_OUTLET_PRODUCT, OutletProduct.class);
                if(bean!=null){
                    outletProductInfo.setProductId(bean.getId());
                    outletProductInfo.setMerchantId(bean.getMerchantId());
                    outletProductInfo.setOutletId(bean.getOutletId());
                    outletProductInfo.setCost(bean.getCost());
                    outletProductInfo.setConsumeAmount(bean.getConsumeAmount());
                    outletProductInfo.setNotDiscountAmount(bean.getNotDiscountAmount());
                    outletProductInfo.setDiscountRate(bean.getDiscountRate());
                    
                    CommonLogic comLogic = new CommonLogicImpl();
                    Map<String,String> merchantMap = comLogic.getMerchantRedis(outletProductInfo.getClientId(), bean.getMerchantId());
                    if(merchantMap!=null){
                        outletProductInfo.setMerchantName(merchantMap.get("merchant_name"));
                        if("0".equals(bean.getOutletId())){
                            DBObject merchantdb = new BasicDBObject();
                            merchantdb.put("client_id", outletProductInfo.getClientId());
                            merchantdb.put("merchant_id", bean.getMerchantId());
                            OutletDetail outletDetail = manager.findOne(merchantdb, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
                            if(outletDetail!=null){
                            	outletProductInfo.setLogo(outletDetail.getDefaultImage());
                            }
                        }else{
                            DBObject outletdb = new BasicDBObject();
                            outletdb.put("client_id", outletProductInfo.getClientId());
                            outletdb.put("_id", bean.getOutletId());
                            OutletDetail outletDetail = manager.findOne(outletdb, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
                            if(outletDetail!=null){
                            	outletProductInfo.setLogo(outletDetail.getDefaultImage());
                            }
                        }
                    }
                    
                    key = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(outletProductInfo.getClientId(), bean.getMerchantId(), bean.getOutletId());
                    Map<String, String>  hash = redis.getMap(key);
                    String outletName = null;
                    if(hash!=null){
                        outletName = hash.get("outlet_name");
                    }
                    if(outletName==null || "".equals(outletName)){
                        SqlSession sqlSession = null;
                        try{
                            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
                            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
                            
                            Map<String,Object> param = new HashMap<String,Object>();
                            param.put("clientId", outletProductInfo.getClientId());
                            param.put("outletId", bean.getOutletId());
                            List<ProductOutletInfo> outlets = productMapper.findOutlets(param);
                            if(outlets!=null && outlets.size()>0){
                                outletName = outlets.get(0).getOutletName();
                            }
                        } catch (Exception e) { 
                            LogCvt.error("根据二维码查看面对面商品详情时获取门店名称失败，原因:" + e.getMessage(),e); 
                        } finally { 
                            if(null != sqlSession) {
                                sqlSession.close(); 
                            } 
                        } 
                    }
                    outletProductInfo.setOutletName(outletName);
                }
            } else {
                
            }
        } catch (Exception e) { 
            LogCvt.error("根据二维码查看面对面商品详情失败，原因:" + e.getMessage(),e); 
        }
        return outletProductInfo;
    }


    @Override
    public Boolean deleteOutletProduct(OutletProduct outletProduct) {
        boolean isSuccess = false;
        try{
            DBObject dbObject = new BasicDBObject();
            boolean isHaveCon = false;
            if(outletProduct.getClientId()!=null&&!"".equals(outletProduct.getClientId())){
                isHaveCon=true;
                dbObject.put("client_id", outletProduct.getClientId());
            }
            if(outletProduct.getMerchantId()!=null && !"".equals(outletProduct.getMerchantId())){
                isHaveCon=true;
                dbObject.put("merchant_id", outletProduct.getMerchantId());
            }
            if(outletProduct.getOutletId()!=null && !"".equals(outletProduct.getOutletId())){
                isHaveCon=true;
                dbObject.put("outlet_id", outletProduct.getOutletId());
            }
            if(outletProduct.getCost()>0){
                isHaveCon=true;
                dbObject.put("cost", outletProduct.getCost());
            }
            if(outletProduct.getId()!=null && !"".equals(outletProduct.getId())){
                isHaveCon=true;
                dbObject.put("_id", outletProduct.getId());
            }
            if(isHaveCon){
                isSuccess = manager.remove(dbObject, MongoTableName.CB_OUTLET_PRODUCT) !=-1;// 从mongodb删除数据
            }
        } catch (Exception e) { 
            LogCvt.error("删除ProductDetail失败，原因:" + e.getMessage(),e); 
        }
        return isSuccess;
    }


    @Override
    public MongoPage getOutletProductListByPage(Page<OutletProduct> page,OutletProduct outletProduct) {
        MongoPage mPage = new MongoPage(page.getPageNumber(), page.getPageSize());
        try{
            /**********************操作Mongodb数据库**********************/
            // mongo操作
            
            mPage.setSort(new Sort("_id", OrderBy.DESC));
            
            DBObject dbObject = new BasicDBObject();
            if(outletProduct.getClientId()!=null&&!"".equals(outletProduct.getClientId())){
                dbObject.put("client_id", outletProduct.getClientId());
            }
            if(outletProduct.getMerchantId()!=null && !"".equals(outletProduct.getMerchantId())){
                dbObject.put("merchant_id", outletProduct.getMerchantId());
            }
            if(outletProduct.getOutletId()!=null && !"".equals(outletProduct.getOutletId())){
                dbObject.put("outlet_id", outletProduct.getOutletId());
            }
            if(outletProduct.getCost()>0){
                dbObject.put("cost", outletProduct.getCost());
            }
            mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_OUTLET_PRODUCT, OutletProduct.class);
        } catch (Exception e) { 
            LogCvt.error("查询OutletProduct失败，原因:" + e.getMessage(),e); 
        }
        return mPage;
    }


    /**
     * 添加商品评论
     * 
     */
    @Override
    public Result addProductComment(ProductComment productComment) {
        
        long startTime = System.currentTimeMillis();
        
        Result result = new Result();
        boolean isSuccess=false;
        boolean isSuccess1=false;
        SqlSession sqlSession = null;
        try{
            boolean flag=false;//判断子订单中product是否评论
            String productId=productComment.getProductId();
            String orderId=productComment.getOrderId();
            long ss = System.currentTimeMillis();
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            long se = System.currentTimeMillis();
            LogCvt.info("-------新加评价信息获取mapper链接耗时:"+(se - ss)+"--startTime:"+ss+"---endTime:"+se);
            
            Product p = new Product();
            p.setClientId(productComment.getClientId());
            p.setProductId(productId);
            
            Product product = null;
            CommonLogic comLogic = new CommonLogicImpl();
            try{
                long pst = System.currentTimeMillis();
                List<Product> ps = productMapper.getProductByProductId(p);
                long ped = System.currentTimeMillis();
                LogCvt.info("-------新加评价信息查询商品基础信息耗时:"+(ped - pst)+"--startTime:"+ped+"---endTime:"+pst);
                
                if(ps!=null && ps.size()>0){
                    product = ps.get(0);
                }else{
                	result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("商品已经不存在不能评论");
                    return result;
                }
                if(productComment.getClientId()==null || "".equals(productComment.getClientId())){
                    productComment.setClientId(product.getClientId());
                }
                if(productComment.getMerchantId()==null || "".equals(productComment.getMerchantId())){
                    productComment.setMerchantId(product.getMerchantId());
                }
                //获取商户信息redis，redis中不存在就从mysql取
                long ms = System.currentTimeMillis();
                Map<String,String> merchantMap = comLogic.getMerchantRedis(productComment.getClientId(),productComment.getMerchantId());
                long me = System.currentTimeMillis();
                LogCvt.info("-------新加评价信息获取商户信息redis耗时:"+(me - ms)+"--startTime:"+ms+"---endTime:"+me);
                if(merchantMap!=null){
                    productComment.setPhone(merchantMap.get("phone"));
                    productComment.setMerchantName(merchantMap.get("merchant_name"));
                }
                
            } catch (Exception e) { 
                LogCvt.error("添加ProductComment时候查询productName失败，原因:" + e.getMessage(),e); 
                new ResultBean(ResultCode.product_no_exits);
            } 
            
            String clientId=productComment.getClientId();
            
            //设置lock锁定，控制在mongo处理期间的锁定
            long locks = System.currentTimeMillis();
            String lockKey = RedisKeyUtil.cbbank_product_add_comment_lock_client_id_product_id_order_id(clientId,productId,orderId);
            long locke = System.currentTimeMillis();
            LogCvt.info("-------新加评价信息设置lock锁定，控制在mongo处理期间的锁定耗时:"+(locke - locks)+"--startTime:"+locks+"---endTime:"+locke);
            
            long lockResult = ProductRedis.setLock(lockKey, 5);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
            if(lockResult == 1) {// key不存在   此次设置成功   说明商品评论没重复提交
            	 LogCvt.info("商品评论设置lockKey[" + lockKey + "是第一次提交");
            }else{
            	result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("不可重复提交商品评论");
                return result;
            }
            
            //用户多次提交控制，先查子订单是否有此数据
            List<ProductMongo> products = null;
            long soms = System.currentTimeMillis();
            SubOrderMongo som = IsExistProductCommentState(productComment,manager);
            long some = System.currentTimeMillis();
            LogCvt.info("-------新加评价信息查子订单基础信息耗时:"+(some - soms)+"--startTime:"+soms+"---endTime:"+some);
            if(som!=null){
                products = som.getProducts();
                if(products!=null && products.size()>0){
                    for(ProductMongo pr : products){
                        if(pr.getProductId().equals(productId)){
                            if(!"1".equals(pr.getCommentState())){//如果此商品没有这个字段证明没有评论，已经评论了就返回
                                flag=true;
                                break;
                            }else{
                                result.setResultCode(ResultCode.productcomment_is_comment.getCode());
                                result.setResultDesc("用户已经评论过此订单");
                                return result;
                            }
                        }
                    }
                } else {
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc(orderId+"该子订单号的商品不存在不能评论");
                    return result;
                }
            } else{
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc(orderId+"该子订单号不存在不能评论");
                return result;
            }
            LogCvt.info("-------新加评价信息判断子订单基础信息耗时:"+(System.currentTimeMillis() - some)+"--startTime:"+some+"---endTime:"+System.currentTimeMillis());
            ProductCommentInfo pcInfo = new ProductCommentInfo();
            //冗余缩略图到评价表中
            long pics = System.currentTimeMillis();
            if(Checker.isNotEmpty(productComment.getImagePic())){
            	pcInfo.setImagePic(productComment.getImagePic());
            } else {
            	 if(productComment.getProductId()!=null){
            	     //先查找对应商品的缩略图
            	     DBObject seach=new BasicDBObject();
            	     seach.put("_id",productId);
                     seach.put("client_id", clientId);
                     ProductDetail pd= manager.findOne(seach, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                     if(pd!=null){
                         if(pd.getImageInfo()!=null && pd.getImageInfo().size()>0){
                             pcInfo.setImagePic(pd.getImageInfo().get(0).getThumbnail());
                         }
                         if(productComment.getMerchantName()==null || "".equals(productComment.getMerchantName())){
                             productComment.setMerchantName(pd.getMerchantName());
                         }
                     } else {
                         LogCvt.info("没找到对应商品的缩略图");
                     }
                 }
            }
            
            LogCvt.info("-------新加评价信息查找对应商品的缩略图基础信息耗时:"+(System.currentTimeMillis() - pics)+"--startTime:"+pics+"---endTime:"+System.currentTimeMillis());
            //将机构冗余到mongo表中
            long orgs = System.currentTimeMillis();
            if(productComment.getMerchantId()!=null || !"".equals(productComment.getMerchantId())){
                Map<OrgLevelEnum, String> orgLevel=comLogic.getSuperOrgByMerchantId(productComment.getClientId(),productComment.getMerchantId());
                if(orgLevel!=null){
                    pcInfo.setForgCode(orgLevel.get(OrgLevelEnum.orgLevel_one));
                    pcInfo.setSorgCode(orgLevel.get(OrgLevelEnum.orgLevel_two));
                    pcInfo.setTorgCode(orgLevel.get(OrgLevelEnum.orgLevel_three));
                    pcInfo.setLorgCode(orgLevel.get(OrgLevelEnum.orgLevel_four));
                }
            }
            long orge = System.currentTimeMillis();
            LogCvt.info("-------新加评价信息将机构冗余到mongo表中耗时:"+(orge - orgs)+"--startTime:"+orgs+"---endTime:"+orge);
            pcInfo.setClientId(productComment.getClientId());
            pcInfo.setProductId(productComment.getProductId());
            pcInfo.setCreateTime(new Date().getTime());
            pcInfo.setProductName(product.getName());
            if(productComment.getOrderValue()==null){
                pcInfo.setOrderValue(1);
            } else {
                pcInfo.setOrderValue(productComment.getOrderValue());
            }
            pcInfo.setMerchantId(productComment.getMerchantId());
            pcInfo.setMerchantName(productComment.getMerchantName());
            pcInfo.setMemberCode(productComment.getMemberCode());
            pcInfo.setMemberName(productComment.getMemberName());
            pcInfo.setOrderId(productComment.getOrderId());
            pcInfo.setOrderType(productComment.getOrderType());
            pcInfo.setType(product.getType());
            if(Checker.isNotEmpty(productComment.getBigOrderId())){
                pcInfo.setBigOrderId(productComment.getBigOrderId());
            } else {
                pcInfo.setBigOrderId(som.getOrderId());
            }
            
            if(productComment.getCommentDescription()!=null){
            	pcInfo.setCommentDescription(productComment.getCommentDescription());
            }
            pcInfo.setStarLevel(productComment.getStarLevel());
            if(productComment.getPhone()!=null && !"".equals(productComment.getPhone())){
                pcInfo.setPhone(productComment.getPhone());
            }
            LogCvt.info("新加评价信息:"+JSON.toJSONString(pcInfo));
            long as = System.currentTimeMillis();
            isSuccess = manager.add(pcInfo, MongoTableName.CB_PRODUCT_COMMENT) !=-1;// 向mongodb插入数据
            long ae = System.currentTimeMillis();
            LogCvt.info("-------addProductComment新加评价信息:耗时"+(ae - as)+"--startTime:"+ae+"---endTime:"+as);
            
            //评价新增后对应子订单的商品需要加上已评论字段
            if(isSuccess){
                if(productComment.getStarLevel()!=null && productComment.getStarLevel()>0){
                    long s = System.currentTimeMillis();
                    //商品评论星级放到redis里
                    int point =  (product.getPoint()==null || product.getPoint()==0)?productComment.getStarLevel():(product.getPoint()+productComment.getStarLevel())/2;
                    product.setPoint(productComment.getStarLevel());
                    productMapper.updatePoint(product);
                    sqlSession.commit();
                    
                    //商品评论星级放到redis里
                    String pkey = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId());
                    Long pointHsetResult = redis.hset(pkey, "point", point+"");
                    long e = System.currentTimeMillis();
                    LogCvt.info("-------addProductComment新加商品评论更新商品评分:耗时"+(e - s)+"--startTime:"+e+"---endTime:"+s+",point redis hset 结果:"+pointHsetResult);
                }
                if(flag){
                    //更新子订单对应的商品评价状态
                    DBObject where=new BasicDBObject();
                    where.put("client_id", productComment.getClientId());
                    where.put("sub_order_id", productComment.getOrderId());
                    where.put("order_id", som.getOrderId());
                    where.put("products.product_id", productComment.getProductId());
                    
                    DBObject value=new BasicDBObject();
                    value.put("products.$.comment_state","1");
                    long s = System.currentTimeMillis();
                    isSuccess1= manager.update(value, where, MongoTableName.CB_SUBORDER_PRODUCT, "$set")>0;  
                    long e = System.currentTimeMillis();
                    LogCvt.info("-------addProductComment新加商品评论更新子订单对应的商品评价状态:耗时"+(e - s)+"--startTime:"+e+"---endTime:"+s);
                }
            }
            
            //删除锁lockKey
            ProductRedis.del_cbbank_product_add_comment_lock_client_id_product_id_order_id(clientId,productId,orderId);
        } catch (Exception e) {
        	if(null!=sqlSession)
        		sqlSession.rollback(true);  
            LogCvt.error("添加ProductComment失败，原因:" + e.getMessage(),e); 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("添加商品评论失败");
            return result;
        }finally{
            if(null!=sqlSession){
                sqlSession.close();
            }
        }
        long endTime = System.currentTimeMillis();
        LogCvt.info("-------addProductComment新加商品评论总耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        
        if(isSuccess){
        	if(isSuccess1){
        	    result.setResultCode(ResultCode.success.getCode());
                result.setResultDesc("添加商品评论成功");
                return result;
        	}else{
        	    result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("商品评论成功！更新对应子订单评论状态失败!");
                return result;
        	}
        }else{
     	  result.setResultCode(ResultCode.failed.getCode());
          result.setResultDesc("添加商品评论失败");
          return result;
        }
    }


    @Override
    public ResultBean deleteProductComment(ProductComment productComment) {
        boolean isSuccess = false;
        try{
            DBObject dbObject = new BasicDBObject();
            if(productComment.getClientId()!=null && !"".equals(productComment.getClientId())){
                dbObject.put("client_id", productComment.getClientId());
            }
            if(productComment.getMerchantId()!=null && !"".equals(productComment.getMerchantId())){
                dbObject.put("merchant_id", productComment.getMerchantId());
            }
            if(productComment.getCommentId()!=null && !"".equals(productComment.getCommentId())){
                dbObject.put("_id", new ObjectId(productComment.getCommentId()));
                isSuccess = manager.remove(dbObject, MongoTableName.CB_PRODUCT_COMMENT) !=-1;// 从mongodb删除数据
            }else{
                return new ResultBean(ResultCode.productcomment_param_empty,"评论ID不能为空"); 
            }
        } catch (Exception e) { 
            LogCvt.error("删除ProductComment失败，原因:" + e.getMessage(),e);
            return  new ResultBean(ResultCode.failed,"删除商品评论失败");
        }
        if(isSuccess){
            return new ResultBean(ResultCode.success);
        }else{
            return new ResultBean(ResultCode.failed,"删除商品评论失败");
        }
    }


    @Override
    public Result replayProductComment(ProductComment productComment) {
    	boolean isSuccess = false;
    	Result result = new Result();
        try{
            DBObject value = new BasicDBObject();
            value.put("recomment", productComment.getRecomment());
            value.put("recomment_time", new Date().getTime());
            value.put("merchant_user_name", productComment.getMerchantUserName());
            	
            DBObject where = new BasicDBObject();
            if(productComment.getClientId()!=null && !"".equals(productComment.getClientId())){
                where.put("client_id", productComment.getClientId());
            }
            where.put("_id", new ObjectId(productComment.getCommentId()));
            isSuccess = manager.update(value,where,MongoTableName.CB_PRODUCT_COMMENT,"$set") > 0;// 向mongodb更新数据
        } catch (Exception e) { 
            LogCvt.error("回复ProductComment失败，原因:" + e.getMessage(),e); 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("回复商品评论失败");
        }
        if(isSuccess){
            result.setResultCode(ResultCode.success.getCode());
            result.setResultDesc("回复商品评论成功");
        }else{
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("回复商品评论失败");
        }
        return result;
    }


    @Override
    public ResultBean updateProductComment(ProductComment productComment) {
    	boolean isSuccess = false;
        try{
            DBObject where = new BasicDBObject();
            
            if(productComment.getClientId()!=null&&!"".equals(productComment.getClientId())){
                where.put("client_id", productComment.getClientId());
            }else{
            	return new ResultBean(ResultCode.productcomment_param_empty,"用户ID不能为空");
            }
            if(productComment.getCommentId()!=null && !"".equals(productComment.getCommentId())){
                where.put("_id", new ObjectId(productComment.getCommentId()));
            }else{
            	return new ResultBean(ResultCode.productcomment_param_empty,"评论ID不能为空");
            }
            
            DBObject value = new BasicDBObject();
            value.put("comment_description", "[追加评价]"+productComment.getCommentDescription());
            ProductCommentInfo productCommentInfo = manager.findOne(where, MongoTableName.CB_PRODUCT_COMMENT, ProductCommentInfo.class);
            if(productCommentInfo!=null){
                String comment = productCommentInfo.getCommentDescription();
                value.put("comment_description", comment+value.get("comment_description"));
                isSuccess = manager.update(value,where,MongoTableName.CB_PRODUCT_COMMENT,"$set") > 0;// 向mongodb更新数据
            }
        } catch (Exception e) { 
            LogCvt.error("追加ProductComment失败，原因:" + e.getMessage(),e); 
            return new ResultBean(ResultCode.failed,"追加商品评论失败");
        }
        if(isSuccess){
        	return new ResultBean(ResultCode.success);
        }else{
        	return new ResultBean(ResultCode.failed);
        }
    }


    @Override
    public ProductComment getProductCommentDetail(ProductComment productComment) {
    	 ProductComment pc = new ProductComment();
         try{
             DBObject where = new BasicDBObject();
             if(productComment.getClientId()!=null && !"".equals(productComment.getClientId())){
                 where.put("client_id", productComment.getClientId());
             }
             if(productComment.getMerchantId()!=null && !"".equals(productComment.getMerchantId())){
                 where.put("merchant_id", productComment.getMerchantId());
             }
             if(productComment.getCommentId()!=null && !"".equals(productComment.getCommentId())){
                 where.put("_id", new ObjectId(productComment.getCommentId()));
                 ProductCommentInfo productCommentInfo = manager.findOne(where, MongoTableName.CB_PRODUCT_COMMENT, ProductCommentInfo.class);
                 if(productCommentInfo!=null){
                     ProductBeanUtil.copyProperties(pc, productCommentInfo);
                     if(productCommentInfo.getLorgCode()!=null && !"".equals(productCommentInfo.getLorgCode())){
                         pc.setOrgCode(productCommentInfo.getLorgCode());
                     }else if(productCommentInfo.getTorgCode()!=null && !"".equals(productCommentInfo.getTorgCode())){
                         pc.setOrgCode(productCommentInfo.getTorgCode());
                     }else if(productCommentInfo.getSorgCode()!=null && !"".equals(productCommentInfo.getSorgCode())){
                         pc.setOrgCode(productCommentInfo.getSorgCode());
                     }else if(productCommentInfo.getForgCode()!=null && !"".equals(productCommentInfo.getForgCode())){
                         pc.setOrgCode(productCommentInfo.getForgCode());
                     }
                 }
             }
         } catch (Exception e) { 
             LogCvt.error("getProductCommentDetail失败，原因:" + e.getMessage(),e); 
         }
         return pc;
    }


    @Override
    public ProductComment queryProductComment(ProductComment productComment) {
        ProductComment pc = new ProductComment();
        try{
            DBObject where = new BasicDBObject();
            if(productComment.getCommentId()!=null){
                where.put("_id", new ObjectId(productComment.getCommentId()));
            }
            if(productComment.getClientId()!=null&&!"".equals(productComment.getClientId())){
                where.put("client_id", productComment.getClientId());
            }
            if(productComment.getOrderId()!=null && !"".equals(productComment.getOrderId())){
                where.put("order_id", productComment.getOrderId());
            }
            if(productComment.getOrderType()!=null && !"".equals(productComment.getOrderType())){
                where.put("order_type", productComment.getOrderType());
            }
            if(productComment.getProductId()!=null && !"".equals(productComment.getProductId())){
                where.put("product_id", productComment.getProductId());
            }
            ProductCommentInfo productCommentInfo = manager.findOne(where, MongoTableName.CB_PRODUCT_COMMENT, ProductCommentInfo.class);
            if(productCommentInfo!=null){
                pc.setCommentId(productCommentInfo.getCommentId());
                if(productCommentInfo.getCommentDescription()!=null){
                    pc.setCommentDescription(productCommentInfo.getCommentDescription());
                    pc.setStarLevel(productCommentInfo.getStarLevel());
                }
            }
        } catch (Exception e) { 
            LogCvt.error("queryProductComment失败，原因:" + e.getMessage(),e); 
        }
        return pc;
    }


    @Override
    public MongoPage getProductCommentList(
            Page<ProductComment> page, ProductCommentFilter productCommentFilter) {
    	 MongoPage mPage = new MongoPage(page.getPageNumber(), page.getPageSize());
         try{
             /**********************操作Mongodb数据库**********************/
             // mongo操作
             
             mPage.setSort(new Sort("create_time", OrderBy.DESC));
             
             DBObject where = new BasicDBObject();
             if(productCommentFilter.getClientId()!=null&&!"".equals(productCommentFilter.getClientId())){
                 where.put("client_id", productCommentFilter.getClientId());
             }
             if(productCommentFilter.getProductId()!=null && !"".equals(productCommentFilter.getProductId())){
                 where.put("product_id", productCommentFilter.getProductId());
             }
             if(productCommentFilter.getOrderType()!=null && !"".equals(productCommentFilter.getOrderType()) ){
            	 where.put("order_type", productCommentFilter.getOrderType());
             }
             if(productCommentFilter.getType()!=null && !"".equals(productCommentFilter.getType()) ){
                 where.put("type", productCommentFilter.getType());
             }
             if(productCommentFilter.getMerchantId()!=null&&!"".equals(productCommentFilter.getMerchantId())){
            	 where.put("merchant_id", productCommentFilter.getMerchantId());
             }
             if(productCommentFilter.getOrderId()!=null&&!"".equals(productCommentFilter.getOrderId())){
            	 where.put("order_id", productCommentFilter.getOrderId());
             }
             //如果传进来org_code,先查询是几级用户
             if(Checker.isNotEmpty(productCommentFilter.getOrgCode())){
            	 CommonLogic orgic=new CommonLogicImpl();
            	 String[] orgCodes = productCommentFilter.getOrgCode().split(",");
            	 if(orgCodes!=null && orgCodes.length>1){
            		 List<String> proOrgCodes = new ArrayList<String>();
            		 List<String> cityOrgCodes = new ArrayList<String>();
            		 List<String> countryOrgCodes = new ArrayList<String>();
            		 List<String> forgCodes = new ArrayList<String>();
            		 for(String orgCode : orgCodes){
            			 OrgLevelEnum orglevel=orgic.getOrgLevelByOrgCode(orgCode, productCommentFilter.getClientId());
            			 if(orglevel!=null){
            				 if(orglevel.getLevel().equals("1")){
            					 proOrgCodes.add(orgCode);
            				 } else if(orglevel.getLevel().equals("2")){
            					 cityOrgCodes.add(orgCode);
            				 } else if(orglevel.getLevel().equals("3")){
            					 countryOrgCodes.add(orgCode);
            				 } else{
            					 forgCodes.add(orgCode);
            				 }
            			 }
            		 }
            		 if(proOrgCodes.size()>0 || cityOrgCodes.size()>0 || countryOrgCodes.size()>0 || forgCodes.size()>0){
            			 List<DBObject> orArray = new ArrayList<DBObject>();
            			 if(proOrgCodes.size()>0){
                             orArray.add(new BasicDBObject("forg_code",  new BasicDBObject("$in", proOrgCodes)));  
                		 }
                		 if(cityOrgCodes.size()>0){
                			 orArray.add(new BasicDBObject("sorg_code",  new BasicDBObject("$in", cityOrgCodes))); 
                		 }
                		 if(countryOrgCodes.size()>0){
                			 orArray.add(new BasicDBObject("torg_code",  new BasicDBObject("$in", countryOrgCodes)));
                		 }
                		 if(forgCodes.size()>0){
                			 orArray.add(new BasicDBObject("lorg_code",  new BasicDBObject("$in", forgCodes)));
                		 }
                         where.put("$or", JSON.toJSON(orArray));
            		 }
            	 } else if(orgCodes!=null && orgCodes.length==1){
            		 OrgLevelEnum orglevel=orgic.getOrgLevelByOrgCode(productCommentFilter.getOrgCode(), productCommentFilter.getClientId());
                	 if(orglevel!=null){
                		 if(orglevel.getLevel().equals("1")){
                			 where.put("forg_code",productCommentFilter.getOrgCode() );
                		 }else if(orglevel.getLevel().equals("2")){
                			 where.put("sorg_code",productCommentFilter.getOrgCode() );
                		 }else if(orglevel.getLevel().equals("3")){
                			 where.put("torg_code",productCommentFilter.getOrgCode() );
                		 }else{
                			 where.put("lorg_code",productCommentFilter.getOrgCode() );
                		 }
                	 }
            	 }
             }
             if(productCommentFilter.getStarLevel()!=null && !"".equals(productCommentFilter.getStarLevel())){
                 if(StarLevelFilter.sl.toString().equals(productCommentFilter.getStarLevel())){
                     
                 } else if(StarLevelFilter.slle5.toString().equals(productCommentFilter.getStarLevel())){
                     where.put("start_level", new BasicDBObject(QueryOperators.LTE,5));
                 } else if(StarLevelFilter.slle4.toString().equals(productCommentFilter.getStarLevel())){
                     where.put("start_level", new BasicDBObject(QueryOperators.LTE,4));
                 } else if(StarLevelFilter.slle3.toString().equals(productCommentFilter.getStarLevel())){
                     where.put("start_level", new BasicDBObject(QueryOperators.LTE,3));
                 } else if(StarLevelFilter.slle2.toString().equals(productCommentFilter.getStarLevel())){
                     where.put("start_level", new BasicDBObject(QueryOperators.LTE,2));
                 } else if(StarLevelFilter.slle1.toString().equals(productCommentFilter.getStarLevel())){
                     where.put("start_level", new BasicDBObject(QueryOperators.LTE,1));
                 } else if(StarLevelFilter.sl5.toString().equals(productCommentFilter.getStarLevel())){
                     where.put("start_level",5);
                 } else if(StarLevelFilter.sl4.toString().equals(productCommentFilter.getStarLevel())){
                     where.put("start_level",4);
                 } else if(StarLevelFilter.sl3.toString().equals(productCommentFilter.getStarLevel())){
                     where.put("start_level",3);
                 } else if(StarLevelFilter.sl2.toString().equals(productCommentFilter.getStarLevel())){
                     where.put("start_level",2);
                 } else if(StarLevelFilter.sl1.toString().equals(productCommentFilter.getStarLevel())){
                     where.put("start_level",1);
                 }
             }
             if(productCommentFilter.getMerchantName()!=null && !"".equals(productCommentFilter.getMerchantName())){
                 where.put("merchant_name", new BasicDBObject("$regex",Pattern.compile("^.*"+productCommentFilter.getMerchantName()+".*$", Pattern.CASE_INSENSITIVE).toString()));
             }
             if(productCommentFilter.getProductName()!=null && !"".equals(productCommentFilter.getProductName())){
                 where.put("product_name", new BasicDBObject("$regex",Pattern.compile("^.*"+RegexString(productCommentFilter.getProductName())+".*$", Pattern.CASE_INSENSITIVE).toString()));
             }
             if(productCommentFilter.getMemberName()!=null && !"".equals(productCommentFilter.getMemberName())){
                 where.put("member_name", new BasicDBObject("$regex",Pattern.compile("^.*"+productCommentFilter.getMemberName()+".*$", Pattern.CASE_INSENSITIVE).toString()));
             }
             if( productCommentFilter.getPointStartTime()!=null&&productCommentFilter.getPointEndTime()!=null){
            	 where.put("create_time", new BasicDBObject(QueryOperators.GTE,productCommentFilter.getPointStartTime()).append(QueryOperators.LTE, productCommentFilter.getPointEndTime()));
             }else if(productCommentFilter.getPointStartTime()!=null && productCommentFilter.getPointStartTime()>0){
                 where.put("create_time", new BasicDBObject(QueryOperators.GTE,productCommentFilter.getPointStartTime()));
             }else if(productCommentFilter.getPointEndTime()!=null && productCommentFilter.getPointEndTime()>0){
                 where.put("create_time", new BasicDBObject(QueryOperators.LTE,productCommentFilter.getPointEndTime()));
             }
             //回复状态0/1未回复，1/2回复，-1全部
             if(productCommentFilter.getIsReply()==1){
                 where.put("recomment", null);
             } else if(productCommentFilter.getIsReply()==2){
            	 where.put("recomment", new BasicDBObject(QueryOperators.EXISTS,"true"));
             }
             mPage = manager.findByPage(mPage, where, MongoTableName.CB_PRODUCT_COMMENT, ProductCommentInfo.class);
         } catch (Exception e) { 
             LogCvt.error("查询OutletProduct失败，原因:" + e.getMessage(),e); 
         }
         return mPage;
    }


    @Override
    public MongoPage queryProductComments(Page<ProductComment> page,
            ProductCommentFilter productCommentFilter) {
    	  MongoPage mPage = new MongoPage(page.getPageNumber(), page.getPageSize());
          try{
              /**********************操作Mongodb数据库**********************/
              // mongo操作
              
              mPage.setSort(new Sort("create_time", OrderBy.DESC));
              
              DBObject where = new BasicDBObject();
              boolean isHaveWhere = false;
              if(productCommentFilter.getClientId()!=null&&!"".equals(productCommentFilter.getClientId())){
                  where.put("client_id", productCommentFilter.getClientId());
                  isHaveWhere = true;
              }
              if(productCommentFilter.getProductId()!=null && !"".equals(productCommentFilter.getProductId())){
                  where.put("product_id", productCommentFilter.getProductId());
                  isHaveWhere = true;
              }
              if(productCommentFilter.getProductName()!=null && !"".equals(productCommentFilter.getProductName())){
                  where.put("product_name", new BasicDBObject("$regex",Pattern.compile("^.*"+RegexString(productCommentFilter.getProductName())+".*$", Pattern.CASE_INSENSITIVE).toString()));
                  isHaveWhere = true;
              }
              if(productCommentFilter.getMemberCode()!=null && !"".equals(productCommentFilter.getMemberCode())){
                  where.put("member_code", productCommentFilter.getMemberCode());
                  isHaveWhere = true;
              }
              if( productCommentFilter.getPointStartTime()!=null&&productCommentFilter.getPointEndTime()!=null){
             	 where.put("create_time", new BasicDBObject(QueryOperators.GTE,productCommentFilter.getPointStartTime()).append(QueryOperators.LTE, productCommentFilter.getPointEndTime()));
              }else if(productCommentFilter.getPointStartTime()!=null && productCommentFilter.getPointStartTime()>0){
                  where.put("create_time", new BasicDBObject(QueryOperators.GTE,productCommentFilter.getPointStartTime()));
              }else if(productCommentFilter.getPointEndTime()!=null && productCommentFilter.getPointEndTime()>0){
                  where.put("create_time", new BasicDBObject(QueryOperators.LTE,productCommentFilter.getPointEndTime()));
              }
             if(productCommentFilter.getIsSeachAll()!=null&& !"".equals(productCommentFilter.getIsSeachAll())){
            	 if(productCommentFilter.getPointStartTime()==null && productCommentFilter.getPointEndTime()==null){
            		 where.put("create_time", new BasicDBObject(QueryOperators.EXISTS,"true"));
            	 }
             }
             //回复状态1未回复，2回复，-1全部
             if(productCommentFilter.getIsReply()==1){
                 where.put("recomment", null);
             } else if(productCommentFilter.getIsReply()==2){
            	 where.put("recomment", new BasicDBObject(QueryOperators.EXISTS,"true"));
             }
             if(Checker.isNotEmpty(productCommentFilter.getOrgCode())){
            	 String[] orgCodes = productCommentFilter.getOrgCode().split(",");
                 if((orgCodes!=null && orgCodes.length>1) 
                 		|| (orgCodes!=null && orgCodes.length==1 && (productCommentFilter.getOrgLevel()==null || "".equals(productCommentFilter.getOrgLevel())))
                 		){
                 	if(productCommentFilter.getClientId()!=null && !"".equals(productCommentFilter.getClientId())){
                 		List<String> proOrgCodes = new ArrayList<String>();
                     	List<String> cityOrgCodes = new ArrayList<String>();
                     	List<String> countryOrgCodes = new ArrayList<String>();
                     	List<String> forgCodes = new ArrayList<String>();
                     	CommonLogic orgic = new CommonLogicImpl();
                     	for(String orgCode : orgCodes){
                     		OrgLevelEnum orglevel=orgic.getOrgLevelByOrgCode(orgCode, productCommentFilter.getClientId());
                     		if(orglevel!=null){
                     			if(orglevel.getLevel().equals("1")){
                     				proOrgCodes.add(orgCode);
                     			} else if(orglevel.getLevel().equals("2")){
                     				cityOrgCodes.add(orgCode);
                     			} else if(orglevel.getLevel().equals("3")){
                     				countryOrgCodes.add(orgCode);
                     			} else{
                     				forgCodes.add(orgCode);
                     			}
                     		}
                     	}
                     	if(proOrgCodes.size()>0 || cityOrgCodes.size()>0 || countryOrgCodes.size()>0 || forgCodes.size()>0){
                     		List<DBObject> orArray = new ArrayList<DBObject>();
                     		if(proOrgCodes.size()>0){
                                 orArray.add(new BasicDBObject("forg_code",  new BasicDBObject("$in", proOrgCodes)));  
                     		}
                     		if(cityOrgCodes.size()>0){
                     			orArray.add(new BasicDBObject("sorg_code",  new BasicDBObject("$in", cityOrgCodes))); 
                     		}
                     		if(countryOrgCodes.size()>0){
                     			orArray.add(new BasicDBObject("torg_code",  new BasicDBObject("$in", countryOrgCodes)));
                     		}
                     		if(forgCodes.size()>0){
                     			orArray.add(new BasicDBObject("lorg_code",  new BasicDBObject("$in", forgCodes)));
                     		}
                       		where.put("$or", JSON.toJSON(orArray));
                     	}
                 	}
                 } else if(orgCodes!=null && orgCodes.length==1){
                 	if(productCommentFilter.getOrgLevel()!=null && !"".equals(productCommentFilter.getOrgLevel())){
                 		if(productCommentFilter.getOrgLevel().equals("1")){
                 			where.put("forg_code", productCommentFilter.getOrgCode());	
                 		} else if(productCommentFilter.getOrgLevel().equals("2")){
                 			where.put("sorg_code", productCommentFilter.getOrgCode());	
                 		} else if(productCommentFilter.getOrgLevel().equals("3")){
                 			where.put("torg_code", productCommentFilter.getOrgCode());
                 		} else {
                 			where.put("lorg_code", productCommentFilter.getOrgCode());
                 		}
                     }
                 }
             }
             if(isHaveWhere){
                 mPage = manager.findByPage(mPage, where, MongoTableName.CB_PRODUCT_COMMENT, ProductCommentInfo.class);
             }
          } catch (Exception e) { 
              LogCvt.error("queryProductComments失败，原因:" + e.getMessage(),e); 
          }
          return mPage;
    }

    
    @Override
	public ResultBean updateProductDetail(ProductDetail productDetail) {
    	boolean isSuccess = false;
        try{
            DBObject value = new BasicDBObject();
            DBObject where = new BasicDBObject();
            value.put("activities_info",JSON.parse(JSonUtil.toJSonString(productDetail.getActivitiesInfo())));
            where.put("client_id", productDetail.getClientId());
            where.put("_id", productDetail.getId());
            where.put("merchant_id", productDetail.getMerchantId());
            isSuccess= manager.update(value,where,MongoTableName.CB_PRODUCT_DETAIL,"$set") >0;
        } catch (Exception e) { 
            LogCvt.error("ProductDetail关联活动失败，原因:" + e.getMessage(),e); 
            return new ResultBean(ResultCode.failed,"添加商品关联活动失败");
        }
        if(isSuccess){
     	   return new ResultBean(ResultCode.success);
        }else{
     	   return new ResultBean(ResultCode.failed,"添加商品关联活动失败");
        }
	}
    /**
     * 生成二维码
     * @param clientId 客户端id
     * @param keyWord 二维码内容
     * @param qrCodeType 商品类型
     * @return String 二维码url
     */
    private String generateQrCode(String clientId,String keyWord,String qrCodeType){
        try {
            LogCvt.info("开始生成二维码"+new Date().getTime());
            QrCodeService.Iface client = (QrCodeService.Iface) ThriftClientProxyFactory.createIface(QrCodeService.class, GoodsConstants.THRIFT_QRCODE_HOST, Integer.valueOf(GoodsConstants.THRIFT_QRCODE_PORT), 60000);
            
            QrCodeRequestVo qrcodeRequestVo = new QrCodeRequestVo(qrCodeType+keyWord, clientId);
            //面对面商品二维码url
            QrCodeResponseVo qrCodeResponseVo = client.retrieveQrCode(qrcodeRequestVo);
            LogCvt.info("生成二维码结束"+new Date().getTime());
            return qrCodeResponseVo.getUrl();
        }catch (Exception e) {
            LogCvt.error("商品二维码url异常：" +e,e);
            LogCvt.error("qrcodehost" +GoodsConstants.THRIFT_QRCODE_HOST+"qrcodeport:"+GoodsConstants.THRIFT_QRCODE_PORT+",keyWord:"+keyWord+",clientId:"+clientId+",qrCodeType:"+qrCodeType);
        }
        return null;
    }
    
    /**
     * value在constantsStr中是否存在.
     * @param value
     * @param constantsStr
     * @return
     * @return Boolean
     */
    private Boolean isExist(String value,String constantsStr){
        if(constantsStr!=null && !"".equals(constantsStr) && value!=null && !"".equals(value)){
            String[] strs = constantsStr.split(",");
            if(strs!=null && strs.length>0){
                for(String str : strs){
                    if(value.equals(str)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * 判断能否新加 指定商品类型只能在指定平台新加
     * @param type
     * @param platType
     * @return Boolean
     */
    private Boolean isValidTypeForPlatType(String type,String platType){
        String typesPlatTypes = GoodsConstants.GOODS_PLAT_TYPE_PRODUCT_TYPE;
        if(typesPlatTypes!=null && !"".equals(typesPlatTypes)){
            String[] typePlatTypes = typesPlatTypes.split(";");
            for(String typePlatType : typePlatTypes){
                String[] typePlats = typePlatType.split(":");
                if(typePlats.length==2 && type.equals(typePlats[0])){
                    if(platType.equals(typePlats[1])){
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * 商品redis缓存信息
     * @param productInfo
     * @return boolean
     */
    private boolean productRedis(ProductInfo productInfo) {
        try{
            Product product = productInfo.getProduct();
            if(product!=null){
            	
            	Map<String,Object> productInfoMap = new HashMap<String,Object>();
            	productInfoMap.put("product", product);
                
                ProductGroup groupProduct = null;
                ProductPresell presellProduct = null;
                if(product.getType().equals(ProductType.group.getCode())){
                    groupProduct = productInfo.getProductGroup();
                    productInfoMap.put("productGroup", groupProduct);
                } else if(product.getType().equals(ProductType.presell.getCode())){
                    presellProduct = productInfo.getProductPresell();
                    productInfoMap.put("productPresell", presellProduct);
                }
                
                //库存redis缓存
                LogCvt.info("product参数转换:"+product.getClientId()+product.getMerchantId()+ product.getProductId()+product.getStore());
                redis.putString(RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId()), ObjectUtils.toString(product.getStore(), ""));
                
                //每个门店最大提货redis缓存
                if(presellProduct!=null){
                	if(presellProduct.getMaxPerOutlet()!=null){
                		redis.putString(RedisKeyUtil.cbbank_product_presell_max_product_id(presellProduct.getProductId()), presellProduct.getMaxPerOutlet()+"");
                	} else {
                		redis.putString(RedisKeyUtil.cbbank_product_presell_max_product_id(presellProduct.getProductId()), "");
                	}
                }
                
                //商品基本信息redis缓存
                ProductCommonRedis.set_cbbank_product_client_id_merchant_id_product_id(productInfoMap);
            }
            return true;
        } catch (Exception e) {
            LogCvt.error("商品基本信息,库存,每个门店最大提货redis缓存异常：" +e,e);
            return false;
        }
    }
        
	@Override
	public Result replayProductCommentBatch(String recomment,String merchantUserName,List<ProductComment> productComments) {
	    Result result = new Result();
	    StringBuffer sb=new StringBuffer();
        Boolean isSuccess=false;
        try{
            long now = new Date().getTime();
            int success=0;//成功标志
            int fail=0;//失败标志
            
            DBObject value = new BasicDBObject();
            value.put("recomment",recomment);
            value.put("recomment_time", now);
            value.put("merchant_user_name", merchantUserName);
            
            DBObject where = null;
            for(ProductComment productComment : productComments){
                where = new BasicDBObject();
                if(productComment.getClientId()!=null&&!"".equals(productComment.getClientId())){
                    where.put("client_id", productComment.getClientId());
                }
                if(productComment.getCommentId()!=null && !"".equals(productComment.getCommentId())){
                    where.put("_id", new ObjectId(productComment.getCommentId()));
                    isSuccess = manager.update(value,where,MongoTableName.CB_PRODUCT_COMMENT,"$set") > 0;// 向mongodb更新数据
                    if(isSuccess){
                        success++;
                    }else{
                        fail++;
                    }
                }
             }
             if(success>0){
                 sb.append("成功回复数:").append(success).append(",");
             }
             if(fail>0){
                 sb.append("失败回复数:").append(fail).append(",");
             }
             result.setResultCode(ResultCode.success.getCode());
             result.setResultDesc("批量更新成功");
		 } catch (Exception e) { 
             LogCvt.error("回复ProductComment失败，原因:" + e.getMessage(),e); 
             result.setResultCode(ResultCode.failed.getCode());
             result.setResultDesc("批量更新失败");
         }
		 return result;
	}


    @Override
    public List<ProductAreaOutlet> getProductAreaOutlets(
    		ProductAreaOutlet productArea) {
        List<ProductAreaOutlet> areaOutlets = new ArrayList<ProductAreaOutlet>();
        try{
            /**********************操作Mongodb数据库**********************/
            // mongo操作
            
            DBObject where2 = new BasicDBObject();
            if(productArea.getClientId()!=null&&!"".equals(productArea.getClientId())){
                where2.put("client_id", productArea.getClientId());
            }
            if(productArea.getProductId()!=null && !"".equals(productArea.getProductId())){
                where2.put("_id", productArea.getProductId());
            }
            if(where2.keySet().size()>0){
            	ProductDetail pd = (ProductDetail)manager.findOne(where2, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                if(pd!=null){
                	//根据查询条件传进来的areaid判断是市级地区id还是区级地区id
                    Long cityId = null;//市级地区id
                    Long areaId = null;//区级地区id
                    if(productArea.getAreaId()!=null && productArea.getAreaId()>0){
                        CommonLogic comLogic = new CommonLogicImpl();
                        Area area = comLogic.findAreaById(productArea.getAreaId());
                        if(area!=null){
                            String areaTreePath = area.getTreePath();
                            if(Checker.isNotEmpty(areaTreePath)){
                                String[] treePtah = areaTreePath.split(",");
                                if(treePtah.length==2){//areaId为市
                                	cityId = productArea.getAreaId();
                                } 
                                if(treePtah.length==3){//areaId为区
                                	cityId=Long.valueOf(treePtah[1]);
                                	areaId = productArea.getAreaId();
                                } 
                            }
                        }
                    }
                	List<ProductCityOutlet> pdOutlets = pd.getOrgOutlets();
                    if(pdOutlets != null && pdOutlets.size() > 0) {
                    	boolean cityFlag = false;
                    	boolean areaFlag = false;
                    	for (ProductCityOutlet out : pdOutlets) {
                    		cityFlag = false;
            				if (cityId!=null && cityId>0) {
            					if(cityId.longValue() == out.getCityId()){
            						cityFlag = true;
            					}
            				} else {
            					cityFlag = true;
            				}
            				if(cityFlag){
            					if (out.getOrgOutlets() != null && out.getOrgOutlets().size() > 0) {
            						for (ProductOutlet childOutlet : out.getOrgOutlets()) {
            							areaFlag = false;
            							if(areaId!=null && areaId>0){
            								if(areaId.longValue() == childOutlet.getAreaId()){
            									areaFlag = true;
            								}
            							} else {
            								areaFlag = true;
            							}
            							if(areaFlag){
                                        	ProductAreaOutlet po=new ProductAreaOutlet();
             				                ProductBeanUtil.copyProperties(po,childOutlet);
                                            areaOutlets.add(po);
            							}
            						}
            					}
            				}
            			}
                    }
                }
            }
        } catch (Exception e) { 
            LogCvt.error("getProductAreaOutlets失败，原因:" + e.getMessage(),e); 
        }
        return areaOutlets;
    }
    /**
     * 商户，机构失效调用 批量失效商品，商品变成普通下架状态
     */
    @Override
    public boolean invalidProductBatch(Product product) {
        Boolean result = false;
        
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            if(Checker.isEmpty(product.getMerchantId()) && Checker.isNotEmpty(product.getOrgCode())){
                CommonLogic comLogic = new CommonLogicImpl();
                Map<String,String> merchantMap = comLogic.getBankMerchantRedis(product.getClientId(), product.getOrgCode());
                if(merchantMap!=null){
                	product.setMerchantId(merchantMap.get("merchant_id"));
                }
            }
            
            if(Checker.isNotEmpty(product.getMerchantId())){
                result = true;
                
                Map<String,Object> param = new HashMap<String,Object>();
                param.put("clientId", product.getClientId());
                param.put("merchantId", product.getMerchantId());
                param.put("isMarketable", ProductStatus.onShelf.getCode());
                List<Product> ps = productMapper.getProducts(param);
                
                //禁用商户时 将 该普通商户的商品的待审核状态变成未提交
                Map<String, Object> auditParam = new HashMap<String,Object>();
                auditParam.put("clientId",product.getClientId());
                auditParam.put("merchantId",product.getMerchantId());
                auditParam.put("oldAuditState",ProductAuditState.noAudit.getCode());
                auditParam.put("auditState",ProductAuditState.noCommit.getCode());
                productMapper.updateProductAuditState(auditParam);  
                
                Map<String,Object> updateParam = new HashMap<String,Object>();
                updateParam.put("clientId", product.getClientId());
                updateParam.put("merchantId", product.getMerchantId());
                updateParam.put("oldIsMarketable", ProductStatus.onShelf.getCode());
                updateParam.put("isMarketable", ProductStatus.offShelf.getCode());
                updateParam.put("downTime", new Date());
                productMapper.updateProductsStatus(updateParam);
                sqlSession.commit(true);
                
                
                //批量更新mongo
                DBObject value = new BasicDBObject();
                value.put("is_marketable", updateParam.get("isMarketable"));
                DBObject where = new BasicDBObject();
                where.put("client_id", product.getClientId());
                where.put("merchant_id", product.getMerchantId());
                where.put("is_marketable", ProductStatus.onShelf.getCode());
                manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set", false, true);
                
                if(ps!=null && ps.size()>0){
                    String key = null;
                    
                    for(Product pps : ps){
                        /* redis缓存 */
                        key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(pps.getClientId(), pps.getMerchantId(), pps.getProductId());
                        redis.hset(key, "is_marketable", ProductStatus.offShelf.getCode());
                        
                        //修改日志
                        addProductOperaLog(pps.getProductId(), null,null,"2",ProductStatus.offShelf.toString(), "update");
                    }
                }
            }
        } catch (Exception e) {
            result = false;
            if(null!=sqlSession)
            	sqlSession.rollback(true);  
            LogCvt.error("批量失效Product失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return result;
    }
    
    /**
     * 禁用启用商户和机构 目前只有禁用商户的地方调用该方法
     */
    @Override
    public boolean endisableProductBatch(Map<String, String> productParam) {
        Boolean result = false;
        SqlSession endisSqlSession = null;
        try {
            if(productParam!=null){
                endisSqlSession = MyBatisManager.getSqlSessionFactory().openSession();
                ProductMapper endisProductMapper = endisSqlSession.getMapper(ProductMapper.class);
                if(Checker.isNotEmpty(productParam.get("merchantId"))){
                    boolean isOper = false;
                    Map<String, Object> param = new HashMap<String,Object>();
                    param.put("clientId",productParam.get("clientId"));
                    param.put("merchantId",productParam.get("merchantId"));
                    //不改变上架下架时间 设置后mapper就不会更新上架时间和下架时间
                    
                    Map<String, Object> updateParam = new HashMap<String,Object>();
                    updateParam.put("clientId",productParam.get("clientId"));
                    updateParam.put("merchantId",productParam.get("merchantId"));
                    
                    Map<String, Object> auditParam = new HashMap<String,Object>();
                    auditParam.put("clientId",productParam.get("clientId"));
                    auditParam.put("merchantId",productParam.get("merchantId"));
                    if("0".equals(productParam.get("flag"))){
                        isOper = true;
                        
                        param.put("isMarketable", ProductStatus.onShelf.getCode());
                        //商品批量禁用
                        updateParam.put("isMarketable", ProductStatus.disOffShelf.getCode());
                        updateParam.put("oldIsMarketable", ProductStatus.onShelf.getCode());
                        updateParam.put("downTime", new Date());
                        //禁用商户时将该普通商户的商品的待审核状态变成未提交审核状态
                        
                        auditParam.put("auditState",ProductAuditState.noCommit.getCode());
                        auditParam.put("oldAuditState",ProductAuditState.noAudit.getCode());
                    } else if("1".equals(productParam.get("flag"))){
                        isOper = true;
                        
                        param.put("isMarketable", ProductStatus.disOffShelf.getCode());
                        //商品批量启用
                        updateParam.put("isMarketable", ProductStatus.onShelf.getCode());
                        updateParam.put("oldIsMarketable", ProductStatus.disOffShelf.getCode());
                        updateParam.put("rackTime", new Date());
                        
                        // 启用商户时 将该普通商户的商品的未提交审核状态变成待审核状态
                        auditParam.put("auditState",ProductAuditState.noAudit.getCode());
                        auditParam.put("oldAuditState",ProductAuditState.noCommit.getCode());
                        auditParam.put("filterType",ProductType.group.getCode());
                    }
                    if(isOper){
                    	//禁用商户时将该普通商户的商品的待审核状态变成未提交审核状态 启用商户时 将该普通商户的商品的未提交审核状态变成待审核状态
                        
                    	//循环更新redis缓存的isMarketable为禁用下架
                        List<Product> ps = endisProductMapper.getProducts(param);
                        
                        //禁用商户时将该普通商户的商品的待审核状态变成未提交审核状态 启用商户时 将该普通商户的商品的未提交审核状态变成待审核状态
                        endisProductMapper.updateProductAuditState(auditParam);
                        //批量更新mysql 商品的isMarketable为禁用下架
                        endisProductMapper.updateProductsStatus(updateParam); 
                        endisSqlSession.commit(true);
                        
                        //批量更新mongo
                        DBObject value = new BasicDBObject();
                        value.put("is_marketable", updateParam.get("isMarketable"));
                        DBObject where = new BasicDBObject();
                        where.put("client_id", updateParam.get("clientId"));
                        where.put("merchant_id", updateParam.get("merchantId"));
                        where.put("is_marketable", updateParam.get("oldIsMarketable"));
                        manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set", false, true);
                      
                        //循环更新redis缓存的isMarketable为禁用下架
                        if(ps!=null && ps.size()>0){
                            String key = null;
                            for(Product pps : ps){
                            	if(pps.getIsMarketable().equals(updateParam.get("oldIsMarketable"))){
                            		/* redis缓存 */
                                    key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(pps.getClientId(), pps.getMerchantId(), pps.getProductId());
                                    redis.hset(key, "is_marketable", (String)updateParam.get("isMarketable"));
                                    //打印修改日志
                                    addProductOperaLog(pps.getProductId(), null,null,"2",(String)updateParam.get("isMarketable"), "update");
                            	}
                            }
                        }
                    }
                }
            }
            result = true;
        } catch (Exception e) {
            result = false;
            if(null!=endisSqlSession)
            	endisSqlSession.rollback(true);  
            LogCvt.error("批量禁用启用Product失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != endisSqlSession) {
                endisSqlSession.close(); 
            }
        }
        return result;
    }
    
	@Override
	public ResultBean addProductOutlets(ProductDetail productDetail) {
		Boolean isSuccess=false;
        try{
            
           	 DBObject value = new BasicDBObject();
             DBObject where = new BasicDBObject();
                value.put("outlet_info",JSON.parse(JSonUtil.toJSonString(productDetail.getOutletInfo())));
                where.put("client_id", productDetail.getClientId());
                where.put("_id", productDetail.getId());
                where.put("merchant_id", productDetail.getMerchantId());
                isSuccess= manager.update(value,where,MongoTableName.CB_PRODUCT_DETAIL,"$set") >0;
            
        } catch (Exception e) { 
            LogCvt.error("ProductDetail关联门店失败，原因:" + e.getMessage(),e); 
            return new ResultBean(ResultCode.failed,"商品关联门店添加失败");
        }
        if(isSuccess){
        	return new ResultBean(ResultCode.success);
        }else{
        	
        	return new ResultBean(ResultCode.failed,"商品关联门店添加失败");
        }
	}


	@Override
	public Result deleteProductBatch(String clientId,List<String> productIds) {
	    Result result = new Result();
	    SqlSession sqlSession = null;
	    try{
	        sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("clientId", clientId);
            param.put("productIds", productIds);
            param.put("isMarketable", ProductStatus.isDeleted.getCode());
            
            //批量更新mysql的isMarketable为删除状态
            productMapper.deleteLogicProducts(param);
            sqlSession.commit(true);
            
            //循环更新redis缓存和mongo的isMarketable为删除状态
            String key = null;
            Map<String, String> hash =null; 
            DBObject value = new BasicDBObject();  
            value.put("is_marketable", ProductStatus.isDeleted.getCode());//循环更新mongo的isMarketable为删除状态
            DBObject where = null;
            List<Product> ps = productMapper.getProducts(param);//从mysql中查询出数据，再更新redis缓存和mongo的isMarketable为删除状态
            if(ps!=null && ps.size()>0){
                for(Product p : ps){
                    //循环更新redis缓存的isMarketable为删除状态
                    key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(p.getClientId(), p.getMerchantId(), p.getProductId());
                    hash = redis.getMap(key);
                    if(hash!=null){
                        hash.put("is_marketable", ProductStatus.isDeleted.getCode());  
                        redis.putMap(key, hash);
                    }
                    
                    //循环更新mongo的isMarketable为删除状态
                    where = new BasicDBObject();
                    where.put("client_id", p.getClientId());
                    where.put("merchant_id", p.getMerchantId());
                    where.put("_id", p.getProductId());
                    manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
                    
                }
            }
            result.setResultCode(ResultCode.success.getCode());
            result.setResultDesc("批量删除 Product成功"); 
	    } catch (Exception e) {
	    	if(null != sqlSession)
	    		sqlSession.rollback(true);  
            LogCvt.error("批量删除 Product失败，原因:" + e.getMessage(),e); 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("批量删除 Product失败"); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return result; 
	}


	@Override
	public int queryProductCommentCount(
			ProductCommentFilter productCommentFilter) {
	    
	    DBObject where=new BasicDBObject();
	    int result=0;
	try{
		where.put("client_id", productCommentFilter.getClientId());
		if(productCommentFilter.getProductId()!=null){
			where.put("product_id",productCommentFilter.getProductId());
		}
		if(productCommentFilter.getMemberCode()!=null){
			where.put("member_code", productCommentFilter.getMemberCode());
		}
		result=manager.getCount(where, MongoTableName.CB_PRODUCT_COMMENT);
	}catch(Exception e){
		LogCvt.error("查询roductCommentCount失败，原因:" + e.getMessage(),e); 
	}
	return result;
	}
	
	private SubOrderMongo IsExistProductCommentState(ProductComment pc,MongoManager manager){
	    DBObject where =new BasicDBObject();
	    where.put("client_id",pc.getClientId());
	    where.put("sub_order_id", pc.getOrderId());
		return manager.findOne(where,  MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	}


	
	/** 是否收藏商品
	* @Title: countProducts 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param productId
	* @param @return
	* @return int
	* @throws 
	*/
	public int isExitsStoreProductInfo(String clientId, long memberCode,String productId){
		ProductFavoriteMongo mongo = new ProductFavoriteMongo();
		int result = 0;
		try {
			result = mongo.isExitsStoreProductInfo(clientId, memberCode, productId);
		} catch (Exception e) {
			LogCvt.error("查询MerchantOutletFavorite失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
	
	/** 
	 * 增加商品收藏
	* @Title: addStoreProductInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param productId
	* @param @param storeProductInfo
	* @param @return
	* @return int
	* @throws 
	*/
	@Override
	public ResultBean addStoreProductInfo(String clientId, long memberCode,StoreProductInfo storeProductInfo){
		ProductFavoriteMongo mongo = new ProductFavoriteMongo();
		ResultBean result = new ResultBean(ResultCode.success,"商品收藏成功");
		ProductDetail productDetail = null;
		int store_count=1;
		String id=clientId+"_"+memberCode;
		String productId = storeProductInfo.getProductId();
		try {
			productDetail = mongo.findProductDetailByoutletId(productId);
			if(productDetail == null){
				LogCvt.error("添加失败，商品详情表中不存在该商品");
				return new ResultBean(ResultCode.outlet_detail_no_exits);
			}else{
				Integer storeCount = productDetail.getStoreCount();
				if(storeCount == null){
					mongo.addStoreOutletStoreCount(productId);
					productDetail = mongo.findProductDetailByoutletId(productId);
					storeCount =  0;
				}
				store_count =storeCount+store_count;
				MerchantOutletFavorite m= mongo.findMerchantOutletFavoriteById(id);
				
				if(m != null){
					int storlutletSize = 0;
					List<StoreProductInfo> proList=mongo.queryStoreProductInfo(clientId, memberCode);
					if(proList == null){
						storlutletSize = 0;
					}else{
						storlutletSize = proList.size();
					}
					if(storlutletSize >= FAVORITELIMIT)
					{
						LogCvt.error("添加失败，添加商品收藏数已达上限"+FAVORITELIMIT);
						return new ResultBean(ResultCode.product_count_limit);
					}
					int count = isExitsStoreProductInfo(clientId,memberCode,storeProductInfo.getProductId());
					if(count == 0){
						mongo.updateStoreProductInfo(clientId, memberCode, storeProductInfo);
						mongo.updateproductDetail(clientId, memberCode, productId, store_count);
					}else{
						LogCvt.error("商品已收藏，不能再次收藏");
						return new ResultBean(ResultCode.product_exits);
					}
				}else{
					mongo.addStoreProductInfos(clientId, memberCode, storeProductInfo);
					mongo.updateproductDetail(clientId, memberCode, productId, store_count);
				}
				
			}
			
		} catch (Exception e) {
			result = new ResultBean(ResultCode.failed,"Mongo商品收藏失败");
			LogCvt.error("插入StoreProductInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
	
	/** 
	 * 取消商品收藏
	* @Title: removeStoreProductInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param productId
	* @param @return
	* @return int
	* @throws 
	*/
	@Override
	public int removeStoreProductInfo(String clientId, long memberCode, String productId){
		ProductFavoriteMongo mongo = new ProductFavoriteMongo();
		ProductDetail productDetail = null;
		int result = -1;
		int store_count=-1;
		try {
			productDetail = mongo.findProductDetailByoutletId(productId);
			Integer storeCount = productDetail.getStoreCount();
			store_count =storeCount+store_count;
			result = mongo.removeStoreProductInfo(clientId, memberCode, productId);
			mongo.updateproductDetail(clientId, memberCode, productId, store_count);
		} catch (Exception e) {
			LogCvt.error("移除StoreProductInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
	/** 获取全部收藏商品
	* @Title: queryStoreProductInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return List<StoreProductInfo>
	* @throws 
	*/
	@Override
	public List<StoreProductInfo> queryStoreProductInfo(String clientId, long memberCode){
		ProductFavoriteMongo mongo = new ProductFavoriteMongo();
		List<StoreProductInfo> storeProductInfo = null;
		try {
			storeProductInfo = mongo.queryStoreProductInfo(clientId, memberCode);
		} catch (Exception e) {
			LogCvt.error("获取StoreProductInfo失败，原因:" + e.getMessage(), e);
		}
		return storeProductInfo;
	}
	
	/**
     * 分页查询 StoreProductInfo
     * @param page
     * @param storeProductInfo
     * @return Page<StoreProductInfo>    结果集合 
     */
	@SuppressWarnings({ "null", "unchecked" })
	public Page<StoreProductInfo> findStoreProductInfoByPage(Page<StoreProductInfo> page, String clientId, long memberCode)
	{
		ProductFavoriteMongo mongo = new ProductFavoriteMongo();
		List<StoreProductInfo> storeProductInfoList = new ArrayList<StoreProductInfo>();
		List<StoreProductInfo> result = new ArrayList<StoreProductInfo>();
		try {
			storeProductInfoList = mongo.queryStoreProductInfo(clientId, memberCode);
			if(storeProductInfoList != null || storeProductInfoList.size()!=0){
				PageModel pm=new PageModel(storeProductInfoList,page.getPageSize());
				result=pm.getObjects(page.getPageNumber());
				page.setResultsContent(result);
				page.setTotalCount(storeProductInfoList.size());
				page.setPageCount(pm.getTotalPages());
			}
		} catch (Exception e) {
			LogCvt.error("获取StoreProductInfo失败，原因:" + e.getMessage(), e);
		}
		return page;
	}
	
	/**
	 * 批量提交审核
	 */
	@Override
	public ResultBean updateAuditProductBatch(List<Product> products) {
	    SqlSession sqlSession = null;
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);

	    	for(Product product : products){
	    		//更新状态
                productMapper.updateAuditProductBatch(product);
                
                sqlSession.commit(true);
	    	}
		}catch(Exception e){
			if(null != sqlSession)
				sqlSession.rollback(true);
			LogCvt.error("批量审核Product失败，原因:" + e.getMessage(),e); 
			return new ResultBean(ResultCode.failed,"批量审核商品状态失败");
		}finally{
			sqlSession.commit(true);
			if(null != sqlSession) {
		         sqlSession.close(); 
		    }
		}
		return new ResultBean(ResultCode.success);
	}
	
	
	public List<ProductBaseInfo> getProductBaseInfo(String clientId,List<ProductBaseInfo> products){
	    List<ProductBaseInfo> productBaseInfo = new ArrayList<ProductBaseInfo>();
	    if(products!=null && products.size()>0){
	        
	        Map<String, String> hash = null;
	        String key = null;
	        List<String> productIds = new ArrayList<String>();
	        List<String> pIds = new ArrayList<String>();
	        Map<String,ProductBaseInfo> productsMap = new HashMap<String,ProductBaseInfo>();
	        for(ProductBaseInfo product : products){
	            key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(clientId, product.getMerchantId(), product.getProductId());
	            hash = redis.getMap(key);
	            if(hash!=null && hash.size()>0){
	                product.setName(hash.get("product_name"));
	                product.setIsMarketable(hash.get("is_marketable"));
	                product.setType(hash.get("product_type"));
	                if(hash.get("price")!=null && !"".equals(hash.get("price"))){
	                    product.setPrice(Integer.valueOf(hash.get("price")));
	                }
	                if(hash.get("marketPrice")!=null && !"".equals(hash.get("marketPrice"))){
	                    product.setMarketPrice(Integer.valueOf(hash.get("marketPrice")));
                    }
	                if(hash.get("vipPrice")!=null && !"".equals(hash.get("vipPrice"))){
	                    product.setVipPrice(Integer.valueOf(hash.get("")));
                    }
	            } 
	            if(product.getName()==null || "".equals(product.getName())) {
	                productIds.add(product.getProductId());
	            }
	            pIds.add(product.getProductId());
	            productsMap.put(product.getProductId(), product);
	        }
	        ProductBaseInfo pp = null;
	        if(productIds.size()>0){
	            SqlSession sqlSession = null;
	            try{
	                sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	                ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
	                Map<String,Object> param = new HashMap<String,Object>();
	                param.put("clientId", clientId);
	                param.put("productIds", productIds);
	                
	                List<Product> ps = productMapper.getProducts(param);
	                if(ps!=null && ps.size()>0){
	                    for(Product p : ps){
	                        pp = productsMap.get(p.getProductId());
                            if(pp != null){
                                pp.setMerchantId(p.getMerchantId());
                                pp.setPrice(p.getPrice());
                                pp.setName(p.getName());
                                pp.setIsMarketable(p.getIsMarketable());
                                pp.setType(p.getType());
                                pp.setMarketPrice(p.getMarketPrice());
                                productsMap.put(pp.getProductId(),pp);
                            }
	                    }
	                }
	            }catch(Exception e){
	                LogCvt.error("getProductBaseInfo失败，原因:" + e.getMessage(),e); 
	            }finally{
	                 if(null != sqlSession) {
	                     sqlSession.close();
	                 }
	            }
	        }
	        
	        if(pIds.size()>0){
	            
                DBObject where = new BasicDBObject();
                if(clientId!=null && !"".equals(clientId)){
                    where.put("client_id", clientId);
                }
                where.put("_id", new BasicDBObject("$in", pIds));
                @SuppressWarnings("unchecked")
				List<ProductDetail> pds = (List<ProductDetail>)manager.findAll(where, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                if(pds!=null && pds.size()>0){
                    for(ProductDetail pd : pds){
                        pp = productsMap.get(pd.getId());
                        if(pp != null){
                        	pp.setVipPrice(pd.getVipPrice());
                        	if(pd.getImageInfo()!=null && pd.getImageInfo().size()>0){
                                pp.setImagePic(pd.getImageInfo().get(0).getThumbnail());
                                productsMap.put(pp.getProductId(),pp);
                            }
                        }
                    }
                    
                }
	        }
	        for(String pid : productsMap.keySet()){
	            productBaseInfo.add(productsMap.get(pid));
	        }
	    }
	    return productBaseInfo;
	}
    
    /**
     * 转移[]符号
     * @Title: RegexString
     * @author: Yaren Liang 2015年4月20日
     * @modify: Yaren Liang 2015年4月20日
     * @param @param msg
     * @param @return    
     * @return String    
     * @throws
     */ 
    private  String RegexString(String msg){
        StringBuffer sb=new StringBuffer();
        if(msg.contains("[")&&msg.contains("]")){
            String FMsg=msg.substring(msg.indexOf('[')+1, msg.indexOf(']'));
            sb.append("\\[").append(FMsg).append("\\]");
            String[] SMsg=msg.split("]");
            if(SMsg.length>1){
                sb.append(SMsg[1]);
            }
        }else if(msg.contains("[")&&!msg.contains("]")){
            String FMsg=msg.substring(msg.indexOf('[')+1,msg.length());
            sb.append("\\[").append(FMsg);
//            String[] SMsg=msg.split("]");
        }else{
            sb.append(msg); 
        }
        return sb.toString();
        
    }


    @Override
    public List<AddProductVoRes> addProductBatch(List<ProductInfo> productInfos) {
        List<AddProductVoRes> resultVos = new ArrayList<AddProductVoRes>();
        if(productInfos==null || productInfos.size()==0){
            AddProductVoRes addProductVoRes= new AddProductVoRes();
            addProductVoRes.setProductId(null);
            ResultVo resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.success.getCode());
            resultVo.setResultDesc("没有批量新加商品");
            addProductVoRes.setResult(resultVo);
            resultVos.add(addProductVoRes);
            return resultVos;
        }
        
        AddProductVoRes addProductVoRes = null;
        ResultVo resultVo = null;
        
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            Date now = new Date();
            boolean isNoNeedAudit = false;
            List<Product> products = new ArrayList<Product>();
            List<ProductGroup> groupProducts = new ArrayList<ProductGroup>();
            List<ProductPresell> productPresells = new ArrayList<ProductPresell>();
            
            CommonLogic comLogic = new CommonLogicImpl();
            Org bankOrg = null;
            Map<String,String> merchantMap = null;
            for(ProductInfo productInfo : productInfos){
                try{
                    isNoNeedAudit = false;
                    //判断是否需要审核
                    if(productInfo.getPlatType()!=null){
                        isNoNeedAudit = isExist(productInfo.getPlatType(),GoodsConstants.NO_NEED_AUDIT_PLAT_CODES);
                    } else {
                        LogCvt.info("没有指定新加平台，新加商品失败");
                        
                        addProductVoRes = new AddProductVoRes();
                        addProductVoRes.setProductId(null);
                        resultVo = new ResultVo();
                        resultVo.setResultCode(ResultCode.failed.getCode());
                        resultVo.setResultDesc(productInfo.getProduct().getName()+"没有指定新加平台，不能新加\n");
                        addProductVoRes.setResult(resultVo);
                        resultVos.add(addProductVoRes);
                        continue;
                    }
                    //判断能否在新加
                    boolean isValid = isValidTypeForPlatType(productInfo.getProduct().getType(),productInfo.getPlatType());
                    if(isValid==false){
                        ProductType productType = ProductType.getType(productInfo.getProduct().getType());
                        if(productType!=null){
                            LogCvt.info(productType.getDescribe()+"类型的商品只能在指定的"+productInfo.getPlatType()+"平台上新加，新加商品失败");
                            
                            addProductVoRes = new AddProductVoRes();
                            addProductVoRes.setProductId(null);
                            resultVo = new ResultVo();
                            resultVo.setResultCode(ResultCode.failed.getCode());
                            resultVo.setResultDesc(productType.getDescribe()+"类型的商品"+productInfo.getProduct().getName()+"只能在指定的"+productInfo.getPlatType()+"平台上新加，不能新加\n");
                            addProductVoRes.setResult(resultVo);
                            resultVos.add(addProductVoRes);
                            
                        } else {
                            LogCvt.info("指定商品类型只能在指定的平台新加，新加商品失败");
                            
                            addProductVoRes = new AddProductVoRes();
                            addProductVoRes.setProductId(null);
                            resultVo = new ResultVo();
                            resultVo.setResultCode(ResultCode.failed.getCode());
                            resultVo.setResultDesc("没有指定商品类型的商品"+productInfo.getProduct().getName()+"只能在指定的"+productInfo.getPlatType()+"平台上新加，不能新加\n");
                            addProductVoRes.setResult(resultVo);
                            resultVos.add(addProductVoRes);
                        }
                        continue;
                    }
                    //若没传值初始化值 为非秒杀
                    if(Checker.isEmpty(productInfo.getProduct().getIsSeckill())){
                        productInfo.getProduct().setIsSeckill(SeckillFlagEnum.notSeckill.getCode());
                    }
                    if(productInfo.getProduct().getIsMarketable()==null || "".equals(productInfo.getProduct().getIsMarketable())){
                        productInfo.getProduct().setIsMarketable(ProductStatus.noShelf.toString());
                    }
                    if(productInfo.getProduct().getAuditState()==null || "".equals(productInfo.getProduct().getAuditState())){
                        productInfo.getProduct().setAuditState(ProductAuditState.noAudit.toString());
                    }
                    if(isNoNeedAudit){
                        productInfo.getProduct().setIsMarketable(ProductStatus.onShelf.toString());
                        productInfo.getProduct().setAuditState(ProductAuditState.passAudit.toString());
                        productInfo.getProduct().setAuditOrgCode("0");
                        productInfo.getProduct().setAuditStartOrgCode("0");
                        productInfo.getProduct().setAuditEndOrgCode("0");
                        productInfo.getProduct().setAuditStage("");
                        productInfo.getProduct().setAuditStaff(productInfo.getPlatType());
                        productInfo.getProduct().setAuditTime(now);
                    } else {
                        if(productInfo.getProduct().getAuditState()==null){
                            productInfo.getProduct().setAuditState(ProductAuditState.noCommit.toString());
                        } else if((!ProductAuditState.noAudit.toString().equals(productInfo.getProduct().getAuditState()))
                                && (!ProductAuditState.noCommit.toString().equals(productInfo.getProduct().getAuditState()))){
                            productInfo.getProduct().setAuditState(ProductAuditState.noCommit.toString());
                        }
                        productInfo.getProduct().setAuditStage("");
                        productInfo.getProduct().setReviewStaff("");
                        productInfo.getProduct().setAuditStaff("");
                        productInfo.getProduct().setAuditTime(null);
                    }
                    
                    productInfo.setActivities(null);
                    
                    if(productInfo.getProduct().getMerchantId()==null || "".equals(productInfo.getProduct().getMerchantId())){
                        merchantMap = comLogic.getBankMerchantRedis(productInfo.getProduct().getClientId(), productInfo.getProduct().getOrgCode());
                        if(merchantMap!=null){
                            productInfo.getProduct().setMerchantId(merchantMap.get("merchant_id"));
                            productInfo.getProduct().setMerchantName(merchantMap.get("merchant_name"));
                        }
                    } else {
                        merchantMap = comLogic.getMerchantRedis(productInfo.getProduct().getClientId(), productInfo.getProduct().getMerchantId());
                        if(Checker.isEmpty(merchantMap)){
                            //重新设置商户Id和商户名称
                            productInfo.getProduct().setMerchantName(merchantMap.get("merchant_name"));
                        }
                    }
                    if(productInfo.getProduct().getMerchantId()!=null && !"".equals(productInfo.getProduct().getMerchantId())){
                        productInfo.getProduct().setCreateTime(now);
                        productInfo.getProduct().setSellCount(0);
                        productInfo.getProduct().setPoint(0);
                        if(ProductStatus.noShelf.getCode().equals(productInfo.getProduct().getIsMarketable())){
                            productInfo.getProduct().setRackTime(null);
                            productInfo.getProduct().setDownTime(null);
                        } else if(ProductStatus.onShelf.getCode().equals(productInfo.getProduct().getIsMarketable())){
                            productInfo.getProduct().setRackTime(now);
                            productInfo.getProduct().setDownTime(null);
                        } else if(ProductStatus.offShelf.getCode().equals(productInfo.getProduct().getIsMarketable())){
                            productInfo.getProduct().setDownTime(now);
                        } 
                        Product product = productInfo.getProduct();
                        product.setProductId(simpleID.nextId());
                        productInfo.getProduct().setProductId(product.getProductId());
                        
                        if(product.getClientId()!=null&&!"".equals(product.getClientId()) && product.getOrgCode()!=null && !"".equals(product.getOrgCode())){
                            bankOrg = comLogic.getOrgByOrgCode(product.getOrgCode(), product.getClientId());
                            if(bankOrg!=null){
                                if(OrgLevelEnum.orgLevel_one.getLevel().equals(bankOrg.getOrgLevel())){
                                    productInfo.getProduct().setAuditState(ProductAuditState.passAudit.toString());
                                    product.setProOrgCode(bankOrg.getOrgCode());
                                } else if(OrgLevelEnum.orgLevel_two.getLevel().equals(bankOrg.getOrgLevel())){
                                    product.setProOrgCode(bankOrg.getProvinceAgency());
                                    product.setCityOrgCode(bankOrg.getOrgCode());
                                } else if(OrgLevelEnum.orgLevel_three.getLevel().equals(bankOrg.getOrgLevel())){
                                    product.setProOrgCode(bankOrg.getProvinceAgency());
                                    product.setCityOrgCode(bankOrg.getCityAgency());
                                    product.setCountryOrgCode(bankOrg.getOrgCode());
                                } else if(OrgLevelEnum.orgLevel_four.getLevel().equals(bankOrg.getOrgLevel())){
                                    product.setProOrgCode(bankOrg.getProvinceAgency());
                                    product.setCityOrgCode(bankOrg.getCityAgency());
                                    product.setCountryOrgCode(bankOrg.getCountyAgency());
                                }
                            }
                        }
                        products.add(product);
                        if(product.getType().equals(ProductType.group.getCode())){
                            ProductGroup groupProduct = productInfo.getProductGroup();
                            groupProduct.setProductId(product.getProductId());
                            groupProduct.setCreateTime(now);
                            groupProducts.add(groupProduct);
                        } else if(product.getType().equals(ProductType.presell.getCode())){
                            ProductPresell presellProduct = productInfo.getProductPresell();
                            presellProduct.setProductId(product.getProductId());
                            presellProduct.setCreateTime(now);
                            productPresells.add(presellProduct);
                        }
                        productInfo.getProduct().setProductId(product.getProductId());
                        
                        addProductVoRes = new AddProductVoRes();
                        addProductVoRes.setProductId(product.getProductId());
                        resultVo = new ResultVo();
                        resultVo.setResultCode(ResultCode.success.getCode());
                        resultVo.setResultDesc(product.getName()+"批量新加商品是新加成功");
                        addProductVoRes.setResult(resultVo);
                        resultVos.add(addProductVoRes);
                    }
                } catch (Exception e) { 
                    LogCvt.error("批量添加Product时候"+productInfo.getProduct().getName()+"添加失败" + e.getMessage(),e); 
                    
                    addProductVoRes = new AddProductVoRes();
                    addProductVoRes.setProductId(null);
                    resultVo = new ResultVo();
                    resultVo.setResultCode(ResultCode.failed.getCode());
                    resultVo.setResultDesc(productInfo.getProduct().getName()+"批量新加商品是新加失败");
                    addProductVoRes.setResult(resultVo);
                    resultVos.add(addProductVoRes);
                } 
            }
            if(products!=null && products.size()>0){
                productMapper.addProductByBatch(products);
                if(groupProducts!=null && groupProducts.size()>0){
                    productMapper.addProductGroupByBatch(groupProducts);
                }
                if(productPresells!=null && productPresells.size()>0){
                    productMapper.addProductPresellByBatch(productPresells);
                }
                sqlSession.commit(true);
                
                
                for(ProductInfo productInfo : productInfos){
                    Product p = productInfo.getProduct();
                    //商品类型对应的二维码类型
    	            String qrCodeType = QrCodeType.PRODUCT.getCode();
    	            if(ProductType.group.getCode().equals(p.getType())) {
    	            	qrCodeType = QrCodeType.GROUP.getCode();//团购商品
    	            } else if(ProductType.presell.getCode().equals(p.getType())) {
    	            	qrCodeType = QrCodeType.PRESELL.getCode();//预售商品
    	            } else if(ProductType.special.getCode().equals(p.getType())) {
    	            	qrCodeType = QrCodeType.SPECIAL.getCode();//名优特惠商品
    	            } else if(ProductType.onlinePoint.getCode().equals(p.getType())) {
    	            	qrCodeType = QrCodeType.ONLINEPOINT.getCode();//在线积分兑换商品
    	            } else if(ProductType.dotGift.getCode().equals(p.getType())) {
    	            	qrCodeType = QrCodeType.DOTGIFT.getCode();//网点礼品商品
    	            }
    	            
                    generateQrCode(p.getClientId(), p.getProductId(), qrCodeType);
                    //商品基本信息redis
                    productRedis(productInfo);
                    //商品详情插入mongo
                    addProductDetail(productInfo);
                }
            }
        } catch (Exception e) { 
        	if(null != sqlSession)
        		sqlSession.rollback(true);  
            LogCvt.error("批量添加Product失败，原因:" + e.getMessage(),e); 
            
            addProductVoRes = new AddProductVoRes();
            addProductVoRes.setProductId(null);
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("批量添加Product失败");
            addProductVoRes.setResult(resultVo);
            resultVos.add(addProductVoRes);
            
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        }
        return resultVos;
    }
    
    public void registerProductService(String hostAndPort){
    	
    	redis.put(RedisKeyUtil.cbank_good_ip_port(), hostAndPort);
    }


    @Override
    public Result updateProductStatusBatch(String clientId,String status,List<String> productIds) {
        Result result = new Result();
        StringBuilder warningMessages = new StringBuilder();
        StringBuilder successMessages = new StringBuilder();
        
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            ProductStatus productStatus = ProductStatus.getType(status);
            
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("clientId", clientId);
            param.put("productIds", productIds);
            List<Product> ps = productMapper.getProducts(param);
            List<Product> validProducts = new ArrayList<Product>();//可以上下架的商品
            List<String> validProductIds = new ArrayList<String>();//可以上下架的商品id
            List<String> invalidProductIds = new ArrayList<String>();//不可以上下架的商品id
            
            CommonLogic comLogic = new CommonLogicImpl();
            Map<String,String> merchantMap = null;
            if(ps!=null && ps.size()>0){
                //循环判断是否符合上下架
                for(Product p : ps){
                    merchantMap = comLogic.getMerchantRedis(p.getClientId(), p.getMerchantId());
                    if(merchantMap==null || merchantMap.size()==0){
                        invalidProductIds.add(p.getProductId());
                        warningMessages.append(p.getName()).append("的商户不存在,");
                    } else if(merchantMap.get("is_enable")==null || "0".equals(merchantMap.get("is_enable"))){
                        invalidProductIds.add(p.getProductId());
                        warningMessages.append(p.getName()).append("的").append(merchantMap.get("merchant_name")).append("商户已失效");
                    } else if((ProductStatus.offShelf.toString().equals(status) || ProductStatus.onShelf.toString().equals(status)) && (!ProductAuditState.passAudit.toString().equals(p.getAuditState()))){
                        invalidProductIds.add(p.getProductId());
                        warningMessages.append(p.getName());
                        if(productStatus!=null){
                            warningMessages.append("审核通过才可以将状态改成").append(productStatus.getDescribe());
                        } else {
                            warningMessages.append("审核通过才可以 上架、下架操作");
                        }
                    } else if(ProductStatus.offShelf.toString().equals(status) && !ProductStatus.onShelf.toString().equals(p.getIsMarketable())){
                        invalidProductIds.add(p.getProductId());
                        warningMessages.append(p.getName()).append("上架后才可以进行下架");
                    } else {
                        validProductIds.add(p.getProductId());
                        validProducts.add(p);
                        successMessages.append(p.getName()).append("可以 上下架操作");
                    }
                }
                if(validProductIds.size()>0){//对 可以上下架的商品进行批量上下架
                    param = new HashMap<String,Object>();
                    param.put("isMarketable", status);
                    param.put("clientId", clientId);
                    param.put("productIds", validProductIds);
                    
                    //更新mysql
                    Date now = new Date();
                    if(ProductStatus.noShelf.getCode().equals(status)){
                        param.put("rackTime", null);
                        param.put("downTime", null);
                    } else if(ProductStatus.onShelf.getCode().equals(status)){
                        param.put("rackTime", now);
                        param.put("downTime", null);
                        
                    } else if(ProductStatus.offShelf.getCode().equals(status)){
                        param.put("downTime", now);
                    }
                    productMapper.updateProductsStatus(param); 
                    sqlSession.commit(true);
                    
                    //更新redis和mongo
                    String key = null;
                    Map<String, String> hash = null;
                    DBObject value = new BasicDBObject();
                    value.put("is_marketable", status);
                    if(ProductStatus.onShelf.getCode().equals(status)){
                        value.put("rack_time", now.getTime());
                    }
                    DBObject where = new BasicDBObject();
                    for(Product validProduct : validProducts){
                        /* 更新redis缓存状态 */
                        key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(validProduct.getClientId(), validProduct.getMerchantId(), validProduct.getProductId());
                        hash = redis.getMap(key);
                        if(hash!=null){
                            hash.put("is_marketable", status);
                            redis.putMap(key, hash);
                        }
                        
                        //更新mongo
                        where = new BasicDBObject();
                        if(validProduct.getClientId()!=null && !"".equals(validProduct.getClientId())){
                            where.put("client_id", validProduct.getClientId());
                        }
                        if(validProduct.getMerchantId()!=null && !"".equals(validProduct.getMerchantId())){
                            where.put("merchant_id", validProduct.getMerchantId());
                        }
                        where.put("_id", validProduct.getProductId());
                        manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");//更新mongo商品的状态
                        
                    }
                }
                if(invalidProductIds.size()>0){
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc(warningMessages.toString());
                } else {
                    result.setResultCode(ResultCode.success.getCode());
                    if(productStatus!=null){
                        result.setResultDesc("成功地批量将商品状态改成"+productStatus.getDescribe());
                    } else {
                        result.setResultDesc("批量上下架成功");
                    }
                }
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("没有批量上下架数据");
            }
        } catch (Exception e) {
        	if(null != sqlSession)
        		sqlSession.rollback(true);  
            LogCvt.error("上下架 Product失败，原因:" + e.getMessage(),e); 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("上下架 Product失败");
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return result; 
    }

	
    /**   
     * 个人h5查询商品  
     * @param page
     * @param product
     * @return    
     * @see com.froad.logic.ProductLogic#findProducts(com.froad.db.mysql.bean.Page, com.froad.po.ProductFilter)    
     */ 
    @Override
    public H5MongoPage findProducts(Page<ProductViewInfo> page, ProductFilter product) {
        LogCvt.info("time:"+DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT5, new Date())+"查询H5商品列表:"+JSON.toJSONString(product));
        
        H5MongoPage h5MongoPage = new H5MongoPage(page.getPageNumber(), page.getPageSize());
        MongoPage mPage = new MongoPage(page.getPageNumber(), page.getPageSize());
        try{
            DBObject dbObject = new BasicDBObject();//查询条件
            if (product.getClientId() != null && !"".equals(product.getClientId())) {
                dbObject.put("client_id", product.getClientId());
            }
            if (product.getType() != null && !"".equals(product.getType())) {
                dbObject.put("product_type", product.getType());
            } else {//不能搜索出精品商城的商品
            	dbObject.put("product_type",  new BasicDBObject(QueryOperators.NE, ProductType.boutique.getCode()));
            }
            Integer pageSize = mPage.getPageSize();
            pageSize = null == pageSize ? 10 : pageSize; // 默认10条
            
            int start = (mPage.getPageNumber() - 1) * mPage.getPageSize();
            int end = (mPage.getPageNumber() - 1) * mPage.getPageSize() + mPage.getPageSize();
            
            final int queryMongodbDataCount = 100; // 每次向mongodb查询的数据量
            final int index = end <= queryMongodbDataCount ? 1 : (int) ((end/queryMongodbDataCount)+(end%queryMongodbDataCount>0?1:0)); // 向mongodb第几次查询queryMongodbDataCount条数据
            
            String sort = "";
            if(product.getOrderFileds()!=null && product.getOrderFileds().size()>0){
                Sort orderSort = null;
                String value = null;
                for(String order_field : product.getOrderFileds().keySet()){
                    value = product.getOrderFileds().get(order_field);
                    if(value!=null){
                        sort += order_field+"-"+(value.equalsIgnoreCase("desc")?OrderBy.DESC:OrderBy.ASC)+",";
                        if(orderSort==null){
                            orderSort = new Sort(order_field,value.equalsIgnoreCase("desc")?OrderBy.DESC:OrderBy.ASC);
                        } else {
                            orderSort.on(order_field, value.equalsIgnoreCase("desc")?OrderBy.DESC:OrderBy.ASC);
                        }
                    }
                }
                mPage.setSort(orderSort);
                if(sort!=null && !"".equals(sort)){
                    sort = sort.substring(0, sort.length()-1);
                }
            } else {
                if(ProductType.group.getCode().equals(product.getType())){
                    //团购
                    sort = "sell_count"+"-"+OrderBy.DESC+","+"start_time"+"-"+OrderBy.DESC;
                    mPage.setSort(new Sort("sell_count", OrderBy.DESC).on("start_time", OrderBy.DESC));
                } else if(ProductType.presell.getCode().equals(product.getType())){
                    //预售
                    sort = "start_time"+"-"+OrderBy.DESC;
                    mPage.setSort(new Sort("start_time", OrderBy.DESC));
                }
            }
            //根据查询条件传进来的areaid判断是市级地区id还是区级地区id
            Long cityId = null;//市级地区id
            Long careaId = null;//区级地区id
            if(product.getAreaId()!=null && product.getAreaId()>0){
            	careaId = product.getAreaId();
                CommonLogic comLogic = new CommonLogicImpl();
                Area area = comLogic.findAreaById(product.getAreaId());
                if(area!=null){
                    String areaTreePath = area.getTreePath();
                    if(Checker.isNotEmpty(areaTreePath)){
                        String[] treePtah = areaTreePath.split(",");
                        if(treePtah.length==2){//areaId为市
                        	cityId = product.getAreaId();
                        } 
                    }
                }
            }
            
            final String client_id = ObjectUtils.toString(product.getClientId(), "");
            /*个人h5查询商品  */
            /*设置缓存有效时间*/
            int time = 5;
            if(ProductType.group.getCode().equals(product.getType()) && product.getMerchantId()!=null){/*个人h5查询团购商品  */
            	//已经上架的查询出来
                dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
                
                final String category_id = ObjectUtils.toString(product.getCategoryId(), ""); // 分类id product.getCategoryId();
                final String product_name = ObjectUtils.toString(product.getName(), "");
                final String areaId = ObjectUtils.toString(product.getAreaId(), "");//团购商品所属商户对应的区域
                final String merchantId = ObjectUtils.toString(product.getMerchantId(), "");//团购商品所属商户对应的区域
                final String key = RedisKeyUtil.cbbank_product_group_merchant_h5_index_client_id_category_id_product_name_areaId_merchantId_sort(index, client_id, category_id, product_name, areaId,merchantId, sort);
                final String lockKey = RedisKeyUtil.cbbank_product_group_merchant_h5_lock_index_client_id_category_id_product_name_areaId_merchantId_sort(index, client_id, category_id, product_name, areaId,merchantId, sort);
                LogCvt.info("个人h5查询商品查询,key:" + key+",lockKey:"+lockKey);
                /*设置查询条件*/
                if(product.getCategoryId()!=null && !"".equals(product.getCategoryId()) && !"-1".equals(product.getCategoryId())){
                    //商品分类查询条件
                    dbObject.put("product_category_info.product_category_id", product.getCategoryId());
                }
                if (product.getName() != null && !"".equals(product.getName())) {
                    //商品名称模糊查询
        			String pro_name = product.getName();
        			StringBuilder regexStr = new StringBuilder(".*");
        			regexStr.append(pro_name).append(".*");
        			Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
        			BasicDBObject like = new BasicDBObject();
        			like.append("$regex", pattern);
                    dbObject.put("name", like);
                }
                //团购商品对应的商户所在的对应的区域     
                if(cityId!=null && cityId>0){//市级地区id
                	dbObject.put("city_areas.city_id", cityId);
                } else if(careaId!=null && careaId>0){//区级地区id
                	dbObject.put("city_areas.countys.area_id", careaId);     
                }
//                dbObject.put("start_time", new BasicDBObject(QueryOperators.LTE, new Date().getTime())); // 需要加上开始时间还没到的不显示
                dbObject.put("end_time", new BasicDBObject(QueryOperators.GTE, new Date().getTime())); // 结束时间大于等于当前时间
                dbObject.put("merchant_id", product.getMerchantId()); // 
                if(product_name!=null && !"".equals(product_name)){
                    time = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_CHANGE_LOCK_TIME);//团购商品查询条件经常变缓存有效时间,5代表5分钟
                } else {
                    time = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_STEADY_LOCK_TIME);//团购商品查询条件不常变缓存有效时间,30代表30分钟
                }
                long lockResult = ProductRedis.setLock(lockKey, time);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
                if(lockResult == 1) {// key不存在   此次设置成功   说明原来没设置过这个值   需要查询mongodb
                    LogCvt.info("个人h5查询团购商品key[" + key + "是第一次查询, 查询mongodb");
                    long startTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询团购商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[开始...]时间戳=" + startTime);
                    mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                    long endTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询团购商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");

                    h5MongoPage.setPageCount(mPage.getPageCount());
                    h5MongoPage.setTotalCount(mPage.getTotalCount());
                    h5MongoPage.setItems(mPage.getItems());
                    if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                        h5MongoPage.setHasNext(true);
                    } else {
                        h5MongoPage.setHasNext(false);
                    }
                    
                    setH5PoductsCache(mPage, index, queryMongodbDataCount, dbObject, key, time);//异步将index，100条设置到缓存中
                } else {
                    // key已经存在   本次设置不成功   说明不是第一次查询  先看redis有没有数据  如有则返回  无则查mongodb
                    LogCvt.info("个人h5查询团购商品key[" + key + "非首次查询, 先看redis有没有数据  如有则返回  无则查mongodb");
                    long count = redis.llen(key); // 获取缓存key中有多少条数据
                    if(count >= mPage.getPageNumber() * mPage.getPageSize()) { // 缓存中的数据  足够本次查询的数据
                        List<ProductDetail> pds = ProductRedis.get_cbbank_product_h5_redis(key, start, end - 1);
                        h5MongoPage.setPageCount(mPage.getPageCount()+1);
                        h5MongoPage.setTotalCount((int) count);// 设置总页数
                        h5MongoPage.setItems(pds);
                        h5MongoPage.setHasNext(true);
                    } else {// 缓存中的数据  不够本次查询的数据  需要查询mongo数据库 pageSize条数据返回给前端   然后  起多线程查queryMongodbDataCount条数据添加到redis
                        long startTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询团购商品mongodb查询[" + start + "]至[" + end + "]条数据[开始...]时间戳=" + startTime);
                        mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                        long endTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询团购商品mongodb查询[" + start + "]至[" + end + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                        
                        h5MongoPage.setPageCount(mPage.getPageCount());
                        h5MongoPage.setTotalCount(mPage.getTotalCount());
                        h5MongoPage.setItems(mPage.getItems());
                        if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                            h5MongoPage.setHasNext(true);
                        } else {
                            h5MongoPage.setHasNext(false);
                        }
                    }
                }
            }else if(ProductType.group.getCode().equals(product.getType())){/*个人h5查询团购商品  */
            	//已经上架的查询出来
                dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
                
                final String category_id = ObjectUtils.toString(product.getCategoryId(), ""); // 分类id product.getCategoryId();
                final String product_name = ObjectUtils.toString(product.getName(), "");
                final String areaId = ObjectUtils.toString(product.getAreaId(), "");//团购商品所属商户对应的区域
                final String key = RedisKeyUtil.cbbank_product_group_h5_index_client_id_category_id_product_name_areaId_sort(index, client_id, category_id, product_name, areaId, sort);
                final String lockKey = RedisKeyUtil.cbbank_product_group_h5_lock_index_client_id_category_id_product_name_areaId_sort(index, client_id, category_id, product_name, areaId, sort);
                LogCvt.info("个人h5查询商品查询,key:" + key+",lockKey:"+lockKey);
                /*设置查询条件*/
                if(product.getCategoryId()!=null && !"".equals(product.getCategoryId()) && !"-1".equals(product.getCategoryId())){
                    //商品分类查询条件
                    dbObject.put("product_category_info.product_category_id", product.getCategoryId());
                }
                if (product.getName() != null && !"".equals(product.getName())) {
                    //商品名称模糊查询
        			String pro_name = product.getName();
        			StringBuilder regexStr = new StringBuilder(".*");
        			regexStr.append(pro_name).append(".*");
        			Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
        			BasicDBObject like = new BasicDBObject();
        			like.append("$regex", pattern);
                    dbObject.put("name", like);
                }
                //团购商品对应的商户所在的对应的区域     
                if(cityId!=null && cityId>0){//市级地区id
                	dbObject.put("city_areas.city_id", cityId);
                } else if(careaId!=null && careaId>0){//区级地区id
                	dbObject.put("city_areas.countys.area_id", careaId);     
                }
//                dbObject.put("start_time", new BasicDBObject(QueryOperators.LTE, new Date().getTime())); // 需要加上开始时间还没到的不显示
                dbObject.put("end_time", new BasicDBObject(QueryOperators.GTE, new Date().getTime())); // 结束时间大于等于当前时间
                
                if(product_name!=null && !"".equals(product_name)){
                    time = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_CHANGE_LOCK_TIME);//团购商品查询条件经常变缓存有效时间,5代表5分钟
                } else {
                    time = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_STEADY_LOCK_TIME);//团购商品查询条件不常变缓存有效时间,30代表30分钟
                }
                long lockResult = ProductRedis.setLock(lockKey, time);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
                if(lockResult == 1) {// key不存在   此次设置成功   说明原来没设置过这个值   需要查询mongodb
                    LogCvt.info("个人h5查询团购商品key[" + key + "是第一次查询, 查询mongodb");
                    long startTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询团购商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[开始...]时间戳=" + startTime);
                    mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                    long endTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询团购商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");

                    h5MongoPage.setPageCount(mPage.getPageCount());
                    h5MongoPage.setTotalCount(mPage.getTotalCount());
                    h5MongoPage.setItems(mPage.getItems());
                    if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                        h5MongoPage.setHasNext(true);
                    } else {
                        h5MongoPage.setHasNext(false);
                    }
                    
                    setH5PoductsCache(mPage, index, queryMongodbDataCount, dbObject, key, time);//异步将index，100条设置到缓存中
                } else {
                    // key已经存在   本次设置不成功   说明不是第一次查询  先看redis有没有数据  如有则返回  无则查mongodb
                    LogCvt.info("个人h5查询团购商品key[" + key + "非首次查询, 先看redis有没有数据  如有则返回  无则查mongodb");
                    long count = redis.llen(key); // 获取缓存key中有多少条数据
                    if(count >= mPage.getPageNumber() * mPage.getPageSize()) { // 缓存中的数据  足够本次查询的数据
                        List<ProductDetail> pds = ProductRedis.get_cbbank_product_h5_redis(key, start, end - 1);
                        h5MongoPage.setPageCount(mPage.getPageCount()+1);
                        h5MongoPage.setTotalCount((int) count);// 设置总页数
                        h5MongoPage.setItems(pds);
                        h5MongoPage.setHasNext(true);
                    } else {// 缓存中的数据  不够本次查询的数据  需要查询mongo数据库 pageSize条数据返回给前端   然后  起多线程查queryMongodbDataCount条数据添加到redis
                        long startTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询团购商品mongodb查询[" + start + "]至[" + end + "]条数据[开始...]时间戳=" + startTime);
                        mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                        long endTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询团购商品mongodb查询[" + start + "]至[" + end + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                        
                        h5MongoPage.setPageCount(mPage.getPageCount());
                        h5MongoPage.setTotalCount(mPage.getTotalCount());
                        h5MongoPage.setItems(mPage.getItems());
                        if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                            h5MongoPage.setHasNext(true);
                        } else {
                            h5MongoPage.setHasNext(false);
                        }
                    }
                }
            } else if(ProductType.presell.getCode().equals(product.getType())){/*个人h5查询预售商品  */
                /*个人h5预售商品*/
                final String presellNum = ObjectUtils.toString(product.getPresellNum(), ""); // 预售商品期号:1正在预售,2下期预售,3往期预售
                final String product_name = ObjectUtils.toString(product.getName(), "");
                final String areaId = ObjectUtils.toString(product.getAreaId(), "");//团购商品所属商户对应的区域
                final String key = RedisKeyUtil.cbbank_product_presell_h5_index_client_id_presellNum_areaId_product_name_sort(index, client_id, presellNum,areaId,product_name, sort);
                final String lockKey = RedisKeyUtil.cbbank_product_presell_h5_lock_index_client_id_presellNum_areaId_product_name_sort(index, client_id, presellNum, areaId,product_name,sort);
                
                /*设置查询条件*/
                long now = new Date().getTime();
                if(product.getPresellNum()!=null && !"".equals(product.getPresellNum())){
                    //1正在预售,2下期预售,3往期预售
                    if("1".equals(product.getPresellNum())){
                    	//已经上架的查询出来
                        dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
                        
                        dbObject.put("start_time", new BasicDBObject(QueryOperators.LTE,now));
                        dbObject.put("end_time", new BasicDBObject(QueryOperators.GTE,now));
                        time = Integer.valueOf(GoodsConstants.GOODS_PRESELL_REDIS_PRESELL1_LOCK_TIME);//预售商品查询正在预售缓存有效时间,5代表5分钟
                    } else if("2".equals(product.getPresellNum())){
                    	//已经上架的查询出来
                        dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
                        
                        dbObject.put("start_time", new BasicDBObject(QueryOperators.GT,now));
                        time = Integer.valueOf(GoodsConstants.GOODS_PRESELL_REDIS_PRESELL2_LOCK_TIME);//预售商品查询下期预售缓存有效时间,30代表30分钟
                    } else if("3".equals(product.getPresellNum())){
                    	//上架 下架也查询出来
                        List<String> productStatus =  new ArrayList<String>();
                        productStatus.add(ProductStatus.onShelf.getCode());
                        productStatus.add(ProductStatus.offShelf.getCode());
                        dbObject.put("is_marketable", new BasicDBObject("$in", productStatus));
                        
                        dbObject.put("end_time", new BasicDBObject(QueryOperators.LT,now));
                        time = Integer.valueOf(GoodsConstants.GOODS_PRESELL_REDIS_PRESELL3_LOCK_TIME);//预售商品查询往期预售缓存有效时间,30代表30分钟
                    } else {
                    	//已经上架的查询出来
                        dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
                    }
                }
                //预售商品可以自提的对应的区域     
                if(cityId!=null && cityId>0){//市级地区id
                	List<DBObject> orArray = new ArrayList<DBObject>();
                    orArray.add(new BasicDBObject("delivery_option",  new BasicDBObject(QueryOperators.NE, DeliveryType.take.getCode())));
                    DBObject aa = new BasicDBObject("delivery_option",  DeliveryType.take.getCode());
                    aa.put("city_areas.city_id", cityId);
                    orArray.add(aa);                  
                    dbObject.put("$or", JSON.toJSON(orArray));
                } else if(careaId!=null && careaId>0){//区级地区id
                	List<DBObject> orArray = new ArrayList<DBObject>();
                    orArray.add(new BasicDBObject("delivery_option",  new BasicDBObject(QueryOperators.NE, DeliveryType.take.getCode())));
                    DBObject aa = new BasicDBObject("delivery_option",  DeliveryType.take.getCode());
                    aa.put("city_areas.countys.area_id", careaId);  
                    orArray.add(aa);                  
                    dbObject.put("$or", JSON.toJSON(orArray));
                }
                if (product.getName() != null && !"".equals(product.getName())) {
                    //商品名称模糊查询
        			String pro_name = product.getName();
        			StringBuilder regexStr = new StringBuilder(".*");
        			regexStr.append(pro_name).append(".*");
        			Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
        			BasicDBObject like = new BasicDBObject();
        			like.append("$regex", pattern);
                    dbObject.put("name", like);
                }
                long lockResult = ProductRedis.setLock(lockKey, time);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
                if(lockResult == 1) {// key不存在   此次设置成功   说明原来没设置过这个值   需要查询mongodb
                    LogCvt.info("个人h5查询预售商品key[" + key + "是第一次查询, 查询mongodb");
                    long startTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询预售商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[开始...]时间戳=" + startTime);
                    mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                    long endTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询预售商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");

                    h5MongoPage.setPageCount(mPage.getPageCount());
                    h5MongoPage.setTotalCount(mPage.getTotalCount());
                    h5MongoPage.setItems(mPage.getItems());
                    if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                        h5MongoPage.setHasNext(true);
                    } else {
                        h5MongoPage.setHasNext(false);
                    }
                    
                    setH5PoductsCache(mPage, index, queryMongodbDataCount, dbObject, key, time);//异步将index，100条设置到缓存中
                } else {
                    // key已经存在   本次设置不成功   说明不是第一次查询  先看redis有没有数据  如有则返回  无则查mongodb
                    LogCvt.info("个人h5查询预售商品key[" + key + "非首次查询, 先看redis有没有数据  如有则返回  无则查mongodb");
                    long count = redis.llen(key); // 获取缓存key中有多少条数据
                    if(count >= mPage.getPageNumber() * mPage.getPageSize()) { // 缓存中的数据  足够本次查询的数据
                        List<ProductDetail> pds = ProductRedis.get_cbbank_product_h5_redis(key, start, end - 1);
                        h5MongoPage.setPageCount(mPage.getPageCount()+1);
                        h5MongoPage.setTotalCount((int) count);// 设置总页数
                        h5MongoPage.setItems(pds);
                        h5MongoPage.setHasNext(true);
                    } else {// 缓存中的数据  不够本次查询的数据  需要查询mongo数据库 pageSize条数据返回给前端   然后  起多线程查queryMongodbDataCount条数据添加到redis
                        long startTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询预售商品mongodb查询[" + start + "]至[" + end + "]条数据[开始...]时间戳=" + startTime);
                        mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                        long endTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询预售商品mongodb查询[" + start + "]至[" + end + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                        
                        h5MongoPage.setPageCount(mPage.getPageCount());
                        h5MongoPage.setTotalCount(mPage.getTotalCount());
                        h5MongoPage.setItems(mPage.getItems());
                        if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                            h5MongoPage.setHasNext(true);
                        } else {
                            h5MongoPage.setHasNext(false);
                        }
                    }
                }
                
            } else if(ProductType.special.getCode().equals(product.getType())){/*个人h5查询名优特惠商品  */
            	//已经上架的查询出来
                dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
                
                final String product_name = ObjectUtils.toString(product.getName(), "");
                final String key = RedisKeyUtil.cbbank_product_special_h5_index_client_id_product_name_sort(index, client_id, product_name, sort);
                final String lockKey = RedisKeyUtil.cbbank_product_special_h5_lock_index_client_id_product_name_sort(index, client_id, product_name, sort);
                
                /*设置查询条件*/
                if (product.getName() != null && !"".equals(product.getName())) {
                    //商品名称模糊查询
                    //商品名称模糊查询
        			String pro_name = product.getName();
        			StringBuilder regexStr = new StringBuilder(".*");
        			regexStr.append(pro_name).append(".*");
        			Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
        			BasicDBObject like = new BasicDBObject();
        			like.append("$regex", pattern);
                    dbObject.put("name", like);
                }
                long now = new Date().getTime();
               // dbObject.put("start_time", new BasicDBObject(QueryOperators.LTE,now));
                dbObject.put("end_time", new BasicDBObject(QueryOperators.GTE,now));
                
                if(product_name!=null && !"".equals(product_name)){
                    time = Integer.valueOf(GoodsConstants.GOODS_SPECIAL_REDIS_CHANGE_LOCK_TIME);//名优特惠商品查询条件经常变缓存有效时间,5代表5分钟
                } else {
                    time = Integer.valueOf(GoodsConstants.GOODS_SPECIAL_REDIS_STEADY_LOCK_TIME);//名优特惠商品查询条件不常变缓存有效时间,30代表30分钟
                }
                
                long lockResult = ProductRedis.setLock(lockKey, time);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
                if(lockResult == 1) {// key不存在   此次设置成功   说明原来没设置过这个值   需要查询mongodb
                    LogCvt.info("个人h5查询名优特惠商品key[" + key + "是第一次查询, 查询mongodb");
                    long startTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询名优特惠商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[开始...]时间戳=" + startTime);
                    mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                    long endTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询名优特惠商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");

                    h5MongoPage.setPageCount(mPage.getPageCount());
                    h5MongoPage.setTotalCount(mPage.getTotalCount());
                    h5MongoPage.setItems(mPage.getItems());
                    if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                        h5MongoPage.setHasNext(true);
                    } else {
                        h5MongoPage.setHasNext(false);
                    }
                    
                    setH5PoductsCache(mPage, index, queryMongodbDataCount, dbObject, key, time);//异步将index，100条设置到缓存中
                } else {
                    // key已经存在   本次设置不成功   说明不是第一次查询  先看redis有没有数据  如有则返回  无则查mongodb
                    LogCvt.info("个人h5查询名优特惠商品key[" + key + "非首次查询, 先看redis有没有数据  如有则返回  无则查mongodb");
                    long count = redis.llen(key); // 获取缓存key中有多少条数据
                    if(count >= mPage.getPageNumber() * mPage.getPageSize()) { // 缓存中的数据  足够本次查询的数据
                        List<ProductDetail> pds = ProductRedis.get_cbbank_product_h5_redis(key, start, end - 1);
                        h5MongoPage.setPageCount(mPage.getPageCount()+1);
                        h5MongoPage.setTotalCount((int) count);// 设置总页数
                        h5MongoPage.setItems(pds);
                        h5MongoPage.setHasNext(true);
                    } else {// 缓存中的数据  不够本次查询的数据  需要查询mongo数据库 pageSize条数据返回给前端   然后  起多线程查queryMongodbDataCount条数据添加到redis
                        long startTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询名优特惠商品mongodb查询[" + start + "]至[" + end + "]条数据[开始...]时间戳=" + startTime);
                        mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                        long endTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询名优特惠商品mongodb查询[" + start + "]至[" + end + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                        
                        h5MongoPage.setPageCount(mPage.getPageCount());
                        h5MongoPage.setTotalCount(mPage.getTotalCount());
                        h5MongoPage.setItems(mPage.getItems());
                        if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                            h5MongoPage.setHasNext(true);
                        } else {
                            h5MongoPage.setHasNext(false);
                        }
                    }
                }
            } else if(ProductType.onlinePoint.getCode().equals(product.getType())){/*个人h5查询在线积分商品  */
            	//已经上架的查询出来
                dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
                
                final String key = RedisKeyUtil.cbbank_product_onlinePoint_h5_index_client_id_sort(index, client_id, sort);
                final String lockKey = RedisKeyUtil.cbbank_product_onlinePoint_h5_lock_index_client_id_sort(index, client_id, sort);
                
                /*设置查询条件*/
                long now = new Date().getTime();
                //dbObject.put("start_time", new BasicDBObject(QueryOperators.LTE,now));
                dbObject.put("end_time", new BasicDBObject(QueryOperators.GTE,now));
                
                time = Integer.valueOf(GoodsConstants.GOODS_ONLINEPOINT_REDIS_LOCK_TIME);//线下礼品兑换商品查询缓存有效时间,30代表30分钟
                
                long lockResult = ProductRedis.setLock(lockKey, time);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
                if(lockResult == 1) {// key不存在   此次设置成功   说明原来没设置过这个值   需要查询mongodb
                    LogCvt.info("个人h5查询在线积分商品key[" + key + "是第一次查询, 查询mongodb");
                    long startTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询在线积分商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[开始...]时间戳=" + startTime);
                    mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                    long endTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询在线积分商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");

                    h5MongoPage.setPageCount(mPage.getPageCount());
                    h5MongoPage.setTotalCount(mPage.getTotalCount());
                    h5MongoPage.setItems(mPage.getItems());
                    if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                        h5MongoPage.setHasNext(true);
                    } else {
                        h5MongoPage.setHasNext(false);
                    }
                    
                    setH5PoductsCache(mPage, index, queryMongodbDataCount, dbObject, key, time);//异步将index，100条设置到缓存中
                } else {
                    // key已经存在   本次设置不成功   说明不是第一次查询  先看redis有没有数据  如有则返回  无则查mongodb
                    LogCvt.info("个人h5查询在线积分商品key[" + key + "非首次查询, 先看redis有没有数据  如有则返回  无则查mongodb");
                    long count = redis.llen(key); // 获取缓存key中有多少条数据
                    if(count >= mPage.getPageNumber() * mPage.getPageSize()) { // 缓存中的数据  足够本次查询的数据
                        List<ProductDetail> pds = ProductRedis.get_cbbank_product_h5_redis(key, start, end - 1);
                        h5MongoPage.setPageCount(mPage.getPageCount()+1);
                        h5MongoPage.setTotalCount((int) count);// 设置总页数
                        h5MongoPage.setItems(pds);
                        h5MongoPage.setHasNext(true);
                    } else {// 缓存中的数据  不够本次查询的数据  需要查询mongo数据库 pageSize条数据返回给前端   然后  起多线程查queryMongodbDataCount条数据添加到redis
                        long startTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询在线积分商品mongodb查询[" + start + "]至[" + end + "]条数据[开始...]时间戳=" + startTime);
                        mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                        long endTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询在线积分商品mongodb查询[" + start + "]至[" + end + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                        
                        h5MongoPage.setPageCount(mPage.getPageCount());
                        h5MongoPage.setTotalCount(mPage.getTotalCount());
                        h5MongoPage.setItems(mPage.getItems());
                        if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                            h5MongoPage.setHasNext(true);
                        } else {
                            h5MongoPage.setHasNext(false);
                        }
                    }
                }
            } else if(ProductType.dotGift.getCode().equals(product.getType())){/*个人h5查询线下礼品兑换商品  */
            	//已经上架的查询出来
                dbObject.put("is_marketable", ProductStatus.onShelf.getCode());
                
                final String key = RedisKeyUtil.cbbank_product_dotgift_h5_index_client_id_sort(index, client_id, sort);
                final String lockKey = RedisKeyUtil.cbbank_product_dotgift_h5_lock_index_client_id_sort(index, client_id, sort);
                
                /*设置查询条件*/
                long now = new Date().getTime();
               // dbObject.put("start_time", new BasicDBObject(QueryOperators.LTE,now));
                dbObject.put("end_time", new BasicDBObject(QueryOperators.GTE,now));
                
                time = Integer.valueOf(GoodsConstants.GOODS_DOTGIFT_REDIS_LOCK_TIME);//线下礼品兑换商品查询缓存有效时间,30代表30分钟
                
                long lockResult = ProductRedis.setLock(lockKey, time);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
                if(lockResult == 1) {// key不存在   此次设置成功   说明原来没设置过这个值   需要查询mongodb
                    LogCvt.info("个人h5查询线下礼品兑换商品key[" + key + "是第一次查询, 查询mongodb");
                    long startTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询线下礼品兑换商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[开始...]时间戳=" + startTime);
                    mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                    long endTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询线下礼品兑换商品mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");

                    h5MongoPage.setPageCount(mPage.getPageCount());
                    h5MongoPage.setTotalCount(mPage.getTotalCount());
                    h5MongoPage.setItems(mPage.getItems());
                    if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                        h5MongoPage.setHasNext(true);
                    } else {
                        h5MongoPage.setHasNext(false);
                    }
                    
                    setH5PoductsCache(mPage, index, queryMongodbDataCount, dbObject, key, time);//异步将index，100条设置到缓存中
                } else {
                    // key已经存在   本次设置不成功   说明不是第一次查询  先看redis有没有数据  如有则返回  无则查mongodb
                    LogCvt.info("个人h5查询线下礼品兑换商品key[" + key + "非首次查询, 先看redis有没有数据  如有则返回  无则查mongodb");
                    long count = redis.llen(key); // 获取缓存key中有多少条数据
                    if(count >= mPage.getPageNumber() * mPage.getPageSize()) { // 缓存中的数据  足够本次查询的数据
                        List<ProductDetail> pds = ProductRedis.get_cbbank_product_h5_redis(key, start, end - 1);
                        
                        h5MongoPage.setPageCount(mPage.getPageCount()+1);
                        h5MongoPage.setTotalCount((int) count);// 设置总页数
                        h5MongoPage.setItems(pds);
                        h5MongoPage.setHasNext(true);
                        
                    } else {// 缓存中的数据  不够本次查询的数据  需要查询mongo数据库 pageSize条数据返回给前端   然后  起多线程查queryMongodbDataCount条数据添加到redis
                        long startTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询线下礼品兑换商品mongodb查询[" + start + "]至[" + end + "]条数据[开始...]时间戳=" + startTime);
                        mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前10条返回给前端
                        long endTime = System.currentTimeMillis();
                        LogCvt.info("个人h5查询线下礼品兑换商品mongodb查询[" + start + "]至[" + end + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                        
                        h5MongoPage.setPageCount(mPage.getPageCount());
                        h5MongoPage.setTotalCount(mPage.getTotalCount());
                        h5MongoPage.setItems(mPage.getItems());
                        if(h5MongoPage.getPageCount()>h5MongoPage.getPageNumber()){
                            h5MongoPage.setHasNext(true);
                        } else {
                            h5MongoPage.setHasNext(false);
                        }
                    }
                }
            }
        } catch(Exception e){
            LogCvt.error("个人h5查询商品异常，原因:" + e.getMessage(), e); 
        }
        
        return h5MongoPage;
    }
    
    
    /** 
     * @Title: setH5PoductsCache 向缓存里放数据
     * @author vania
     * @version 1.0
     * @param mPage
     * @param index
     * @param queryMongodbDataCount
     * @param dbObject
     * @param key
     * @param time
     * @return void
     */
    private void setH5PoductsCache(final MongoPage mongoPage,final int index, final int queryMongodbDataCount, final DBObject dbObject , final String key, final int time) {
        FroadThreadPool.execute(new Runnable() { // 异步塞缓存
            @Override
            public void run() {
                MongoPage mPage = new MongoPage(index, queryMongodbDataCount);
                mPage.setSort(mongoPage.getSort());
                long startTime = System.currentTimeMillis();
                LogCvt.info("个人h5查询商品异步[ThreadPool]设置redis缓存,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据[开始...]时间戳=" + startTime);
                try {
                    mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);// 查询前100条返回给前端
                    // 把数据添加到缓存
                    @SuppressWarnings("unchecked")
					List<ProductDetail> productDetails = (List<ProductDetail>)mPage.getItems();
                    if(productDetails!=null && productDetails.size()>0){
                        ProductRedis.set_cbbank_product_h5_redis(key, productDetails, time);
                    }
                } catch (Exception e) {
                    LogCvt.error("个人h5查询商品异步[ThreadPool]设置redis缓存失败, 原因:" + e.getMessage(), e);
                } finally {
                    long endTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询商品异步[ThreadPool]设置redis缓存,currentPage" + mPage.getPageNumber() + ",pageSize" + mPage.getPageSize() + "数据数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                }
            }
        });
    }
    
    /**
     * 设置商品的市级门店和市级区(门店信息有前端页面传递过来时候将门店所在区域和商品关联起来逻辑)
     * @param pd
     * @param productMapper
     * @return 
     */
    private void setAreasOutletByOutletids(ProductDetail pd,ProductMapper productMapper){
        long startTime = System.currentTimeMillis();
        
        //根据门店id获取对应的区id
        List<ProductOutlet> productOutlets = pd.getOutletInfo();
		if (productOutlets != null && productOutlets.size() > 0) {
			CommonLogic comLogic = new CommonLogicImpl();
            //该客户端下的所有的虚拟门店集合
            Map<String,ProductOutletInfo> allClientBankOutlets = comLogic.get_cbbank_client_bank_outlet_redis(pd.getClientId(), 0, -1);
            Map<Long,Area> areaMap = new HashMap<Long,Area>();
            
            if(allClientBankOutlets!=null && allClientBankOutlets.size()>0){
            	
            	Map<Long,List<ProductArea>> countys = new HashMap<Long,List<ProductArea>>();//城市下的区
                Map<Long,List<ProductOutlet>> pcosMap = new HashMap<Long,List<ProductOutlet>>();//城市下的门店
                
            	ProductOutletInfo bankOutlet = null;
            	Area area = null;
            	Long cityId = null;
                Long areaId = null;
                Set<Long> areaIds = new HashSet<Long>();
                for (ProductOutlet outlet : productOutlets) {
                    cityId = null;
                    areaId = null;
                    bankOutlet = allClientBankOutlets.get(outlet.getOutletId());
                    if (bankOutlet != null && bankOutlet.getAreaId()>0) {
                    	area = areaMap.get(bankOutlet.getAreaId());
                    	if(area==null){
                    		area = comLogic.findAreaById(bankOutlet.getAreaId());
                    		if(area!=null){
                    			areaMap.put(area.getId(),area);
                    		}
                    	}
                        if (Checker.isNotEmpty(area.getTreePath())) {
                            String[] treePtah = area.getTreePath().split(",");
                            if(treePtah.length==3){//门店所属的areaId为区
                                areaId = Long.valueOf(treePtah[2]);
                                cityId = Long.valueOf(treePtah[1]);
                            } else if(treePtah.length==2){//门店所属的areaId为市
                                cityId = Long.valueOf(treePtah[1]);
                            }
                            if(!areaIds.contains(bankOutlet.getAreaId())) {//门店所属的areaId没有计算过的需要计算该区对应的市
                                areaIds.add(bankOutlet.getAreaId());
                                if(areaId!=null){//门店所属的areaId为区
                                    if(countys.get(cityId)==null){
                                        countys.put(cityId, new ArrayList<ProductArea>());
                                    }
                                    ProductArea pa = new ProductArea();
                                    pa.setAreaId(bankOutlet.getAreaId());
                                    pa.setAreaName(area.getName());
                                    countys.get(cityId).add(pa);
                                }
                            }
                            if(cityId!=null){
                                if(pcosMap.get(cityId)==null){
                                    pcosMap.put(cityId, new ArrayList<ProductOutlet>());
                                }
                                ProductOutlet pco = new ProductOutlet();
                                pco.setAddress(bankOutlet.getAddress());
                                pco.setOrgCode(bankOutlet.getOrgCode());
                                pco.setOutletId(bankOutlet.getOutletId());
                                pco.setOutletName(bankOutlet.getOutletName());
                                pco.setPhone(bankOutlet.getPhone());
                                pco.setAreaId(bankOutlet.getAreaId());
                                pcosMap.get(cityId).add(pco);
                            }
                        }// end if (Checker.isNotEmpty(area.getTreePath())) {
                        
                    }//end if (bankOutlet != null && bankOutlet.getAreaId()>0) {
                    
                }// end for (ProductOutlet outlet : productOutlets) {
                
                //城市下的区
                List<ProductCityArea> cityAreas = new ArrayList<ProductCityArea>();
                for(Long pacityId : countys.keySet()){
                    ProductCityArea pca = new ProductCityArea();
                    pca.setCityId(pacityId);
                    area = areaMap.get(pacityId);
                    if(area==null){
                    	area = comLogic.findAreaById(pacityId);
                		if(area!=null){
                			areaMap.put(area.getId(),area);
                		}
                	}
                    if(area!=null){
                        pca.setCityName(area.getName());
                    }
                    pca.setCountys(countys.get(pacityId));
                    cityAreas.add(pca);
                }
                if(cityAreas.size()>0){
                    pd.setCityAreas(cityAreas);
                }
                
                //城市下的门店
                List<ProductCityOutlet> orgOutlets = new ArrayList<ProductCityOutlet>();
                for(Long pcityId : pcosMap.keySet()){
                    ProductCityOutlet pco= new ProductCityOutlet();
                    pco.setCityId(pcityId);
                    area = areaMap.get(pcityId);
                    if(area==null){
                    	area = comLogic.findAreaById(pcityId);
                		if(area!=null){
                			areaMap.put(area.getId(),area);
                		}
                	}
                    if(area!=null){
                        pco.setCityName(area.getName());
                    }
                    pco.setOrgOutlets(pcosMap.get(pcityId));
                    orgOutlets.add(pco);
                }
                if(orgOutlets.size()>0){
                    pd.setOrgOutlets(orgOutlets);
                }
                
            }// end if(allClientBankOutlets!=null && allClientBankOutlets.size()>0){
        }// end if (productOutlets != null && productOutlets.size() > 0) {
        long endTime = System.currentTimeMillis();
        LogCvt.debug("-------添加商品到mongo时设置商品的市级门店和市级区:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
    }


    /**
     * 绑定送积分活动
     */
    @SuppressWarnings("unchecked")
	@Override
    public Result addActivtyToProducts(Long activitiesId,
            List<String> productIds) {
        Result result = new Result();
        
        SqlSession sqlSession = null;
        try{
            String pointRate = null;//银行兑换积分比例
            
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            ActivitiesInfo ac = productMapper.findPoints(activitiesId);
            if(ac==null){
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("活动已经不存在不能绑定商品");
                return  result;
            }
            boolean isValid = false;//只有未启用和启用的活动才能绑定商品
            if(ac.getStatus()!=null 
                    && (ActivitiesStatus.no.getCode().equals(ac.getStatus())
                    || ActivitiesStatus.yes.getCode().equals(ac.getStatus()))){
                isValid = true;
            }
            if(!isValid){
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("只有未启用和启用的活动才能绑定商品");
                return  result;
            }
            //获取积分比例
            pointRate = productMapper.findPointRate(ac.getClientId());
            
            List<ProductDetail> pds = null;
            
            int count=0;
            DBObject whereObj = getValidContition(ac,pointRate);
            
            whereObj.put("_id", new BasicDBObject("$in", productIds));
            
            pds = (List<ProductDetail>)manager.findAll(whereObj, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            
            DBObject valueObj = new BasicDBObject();
            whereObj = new BasicDBObject();
            whereObj.put("client_id", ac.getClientId());
            
            if(pds!=null && pds.size()>0){
                boolean success = false;
                CommonLogic comLogic = new CommonLogicImpl();
                for(ProductDetail pd : pds){
                    whereObj.put("_id", pd.getId());
                    ProductActivitiesInfo a = new ProductActivitiesInfo();
                    a.setActivitiesId(ac.getId());
                    a.setActivitiesType(ActivitiesType.point.getCode());
                    valueObj.put("activities_info", JSON.toJSON(a));
                    success = manager.update(valueObj, whereObj, MongoTableName.CB_PRODUCT_DETAIL, "$push", true, false)>0;//push进去
                    if(success){
                        count++;
                    }
                    //更新mysql的is_point 字段值
                    Product p = new Product();
                    p.setIsPoint(true);
                    p.setProductId(pd.getId());
                    p.setClientId(pd.getClientId());
                    p.setMerchantId(pd.getMerchantId());
                    productMapper.updateProductIsPoint(p);
                    
                    //更新redis is_point 字段值
                    String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(p.getClientId(), p.getMerchantId(), p.getProductId());
                    Map<String, String> hash = redis.getMap(key);
                    if(hash==null || hash.size()==0){
                        hash = comLogic.getProductRedis(p.getClientId(), p.getMerchantId(), p.getProductId());
                    }
                    hash.put("is_point", ObjectUtils.toString(BooleanUtils.toInteger(p.getIsPoint(), 1, 0, 0), ""));
                    redis.putMap(key, hash);
                }
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("没有符合绑定活动的商品");
                return result;
            }
            //更新该条送积分活动已经绑定的商品数量
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("count", count);
            param.put("id", ac.getId());
            productMapper.updatePointsCount(param);
            sqlSession.commit(true);
                
            result.setResultCode(ResultCode.success.getCode());
            result.setResultDesc("活动绑定商品成功");
            
        } catch (Exception e) { 
            if(null != sqlSession){
                sqlSession.rollback(true); 
            }
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("活动绑定商品失败");
            LogCvt.error("活动绑定商品失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        }
        return result;
    }
    
    /**
     * 获取可以绑定送积分活动的条件
     * 展示可绑定的商品时，需要满足：
     * 1.该商品处于未上架状态；且属于本活动所关联的客户端中的商品；
     * 2.已绑定过送积分的商品将不会被展示，即一个商品只允许绑定一个活动；
     * -保存绑定记录时需要检查：本条送积分规则中赠送的值（折算为现金后的价值）必须小于任意关联该活动的商品售价的值（有配置VIP1价格的，还必须要小于VIP1价）；
     * -“绑定本客户端当前所有商品”，该功能后面所显示的数量是指当前客户端未上架的、且未被绑定过的商品的数量之和，也等于该页所展示的所有可被绑定的商品总数；保存绑定时，同样也需要检查赠送的值小于所有商品的价格及VIP1价格；
     * 3. 目前只支持精品预售中的商品进行送积分活动，但需要预留其他类型的商品配置该活动的处理，后期将可能添加其他业务的该活动；
     * 4.通过BOSS系统或者银行管理端录入的预售商品均可以被绑定商品送积分活动；
     * @param ac
     * @param pointRate
     * @return DBObject
     */
    private DBObject getValidContition(ActivitiesInfo ac,String pointRate){
        DBObject whereObj = new BasicDBObject();
        
        //本活动所关联的客户端中的商品可绑定
        whereObj.put("client_id", ac.getClientId());
        
        //获取可以绑定送积分活动的指定商品类型
        List<String> types = getProductTypePoints();
        //获取指定平台新加的商品可以绑定送积分活动
        List<String> platTypes = getPlatTypePoints();
        //获取指定商品状态可以绑定送积分活动
        List<String> productStatuss = getProductStatusPoints();
        
        //未上架的才可以绑定活动
        if(productStatuss!=null && productStatuss.size()>0){
            if(productStatuss.size()==1){
                whereObj.put("is_marketable", productStatuss.get(0));
            } else {
                whereObj.put("is_marketable", new BasicDBObject("$in", productStatuss));
            }
        } else {
            whereObj.put("is_marketable", ProductStatus.noShelf.getCode());//未上架的才可以绑定活动
        }
        //VIP活动只支持精品预售中的商品
        if(types!=null && types.size()>0){
            if(types.size()==1){
                whereObj.put("product_type", types.get(0));
            } else if(types.size()>1){
                whereObj.put("product_type", new BasicDBObject("$in", types));
            }
        } else {
            whereObj.put("product_type", ProductType.presell.getCode());
        }
        //通过BOSS系统录入或通过银行管理端录入的预售商品均可被展示及绑定
        if(platTypes!=null && platTypes.size()>0){
            if(platTypes.size()==1){
                whereObj.put("plat_type", platTypes.get(0));
            } else {
                whereObj.put("plat_type", new BasicDBObject("$in", platTypes));
            }
        } else {
            platTypes = new ArrayList<String>();
            platTypes.add(PlatType.boss.getValue()+"");
            platTypes.add(PlatType.bank.getValue()+"");
            whereObj.put("plat_type", new BasicDBObject("$in", platTypes));
        }
        //赠送的积分折算为现金后的价值小于商品的价格 并且小于vip价格 赠送积分数量是乘以1000后保存在数据库表中的
        int price = ac.getPoints();//Arith.mul(Arith.div(ac.getPoints(), GoodsConstants.DOUBLE_INTGER_CHANGE),GoodsConstants.DOUBLE_INTGER_CHANGE);
        if(Checker.isNotEmpty(pointRate)){
            price = Arith.mul(Arith.div(Arith.div(ac.getPoints(), GoodsConstants.DOUBLE_INTGER_CHANGE), Integer.valueOf(pointRate)),GoodsConstants.DOUBLE_INTGER_CHANGE);
        }
        whereObj.put("price", new BasicDBObject(QueryOperators.GT,price));
        
        List<DBObject> orArray = new ArrayList<DBObject>();
        orArray.add(new BasicDBObject("vip_price", new BasicDBObject(QueryOperators.EXISTS, false)));
        orArray.add(new BasicDBObject("vip_price", 0));
        orArray.add(new BasicDBObject("vip_price", new BasicDBObject(QueryOperators.GT,price)));
        whereObj.put("$or", JSON.toJSON(orArray));
//        
//        //已绑定过送积分的商品将不会被展示，即一个商品只允许绑定一个活动
//        List<DBObject> orArrays = new ArrayList<DBObject>();
//        orArrays.add(new BasicDBObject("activities_info", new BasicDBObject(QueryOperators.EXISTS, false)));
//        orArrays.add(new BasicDBObject("activities_info.activities_type", new BasicDBObject("$ne", ActivitiesType.point.getCode())));
//        whereObj.put("$or", JSON.toJSON(orArrays));
        
        whereObj.put("activities_info.activities_type", new BasicDBObject("$ne", ActivitiesType.point.getCode()));
        
        return whereObj;
    }
    
    /**
     * 获取可以绑定送积分活动的指定商品类型(商品类型逗号分隔,比如1,2)2预售
     * @return List<String>
     */
    private List<String> getProductTypePoints(){
        String productTypeVips = GoodsConstants.GOODS_PRODUCT_TYPE_POINTS;
        if(productTypeVips!=null){
            String[] productTypeVipArray = productTypeVips.split(",");
            if(productTypeVipArray.length>0){
                List<String> types = new ArrayList<String>();
                for(String type : productTypeVipArray){
                    if(type!=null && !"".equals(type)){
                        types.add(type);
                    }
                }
                return types;
            }
        }
        return null;
    }
    
    /**
     * 获取指定平台新加的商品可以绑定送积分活动(平台类型逗号分隔,比如1,2) boss 1, 银行端 2
     * @return List<String>
     */
    private List<String> getPlatTypePoints(){
        String platTypeVips = GoodsConstants.GOODS_PLAT_TYPE_POINTS;
        if(platTypeVips!=null){
            String[] platTypeVipArray = platTypeVips.split(",");
            if(platTypeVipArray.length>0){
                List<String> types = new ArrayList<String>();
                for(String type : platTypeVipArray){
                    if(type!=null && !"".equals(type)){
                        types.add(type);
                    }
                }
                return types;
            }
        }
        return null;
    }
    
    /**
     * 获取指定商品状态可以绑定送积分活动(商品上下架状态类型逗号分隔,比如0,1,2) 0未上架
     * @return List<String>
     */
    private List<String> getProductStatusPoints(){
        String productStatusVips = GoodsConstants.GOODS_ISMARKETABLE_POINTS;
        if(productStatusVips!=null){
            String[] productStatusVipArray = productStatusVips.split(",");
            if(productStatusVipArray.length>0){
                List<String> statuss = new ArrayList<String>();
                for(String status : productStatusVipArray){
                    if(status!=null && !"".equals(status)){
                        statuss.add(status);
                    }
                }
                return statuss;
            }
        }
        return null;
    }


    /**
     * 活动解除绑定商品
     */
    @SuppressWarnings("unchecked")
	@Override
    public Result removeActivtyFromProducts(long activtyId, String productId) {
        Result result = new Result();
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            ActivitiesInfo ac = productMapper.findPoints(activtyId);
            if(ac==null){
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("活动不存在不能进行解除绑定商品");
                return result;
            }
            
            DBObject whereObj = new BasicDBObject();
            whereObj.put("client_id", ac.getClientId());
            whereObj.put("activities_info.activities_id", ac.getId());
            int count = 0;
            int point=0;
            List<ProductDetail> pds = null;
            if(Checker.isNotEmpty(productId)){
                whereObj.put("_id", productId);
                pds = (List<ProductDetail>)manager.findAll(whereObj, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                count = removePointActivty(ac, pds);//解除送积分活动与商品表的绑定关系
            } else {
                 //1000条进行解除mongo商品绑定该送积分活动绑定
                int pageNumber =1;
                int pageSize = 1000;
                int total = 1000;
                MongoPage mPage = new MongoPage(pageNumber, pageSize);
                mPage.setSort(new Sort("create_time", OrderBy.DESC));
                while(total==1000){
                    pds = null;
                    mPage = manager.findByPage(mPage, whereObj, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                    pds = (List<ProductDetail>)mPage.getItems();
                    if(pds != null) {
                        total = pds.size();
                        point = pds.size();
                        count += removePointActivty(ac, pds);//解除送积分活动与商品表的绑定关系
                    } else {
                        total = 0;
                    }
                }
            }
            if(point==0){
                result.setResultCode(ResultCode.success.getCode());
                result.setResultDesc("活动解除绑定商品成功");
                return result;
            }
            if(count>0){
                result.setResultCode(ResultCode.success.getCode());
                result.setResultDesc("活动解除绑定商品成功");
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("活动解除绑定商品失败");
            }
        } catch (Exception e) { 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("活动解除绑定商品失败");
            LogCvt.error("活动解除绑定商品失败，原因:" + e.getMessage(),e);
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return result;
    }
    
    /**
     * 解除送积分活动和商品的绑定关系
     * @param ac
     * @param pds
     * @return
     * @throws Exception
     * @return int
     */
    private int removePointActivty(ActivitiesInfo ac,List<ProductDetail> pds) throws Exception{
        int count = 0;
        
        DBObject valueObj = new BasicDBObject();
        DBObject whereObj = new BasicDBObject();
        whereObj.put("client_id", ac.getClientId());
        
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            if(pds!=null && pds.size()>0){
                
                boolean success = false;
                CommonLogic comLogic = new CommonLogicImpl();
                for(ProductDetail pd : pds){
                    
                    Product p = new Product();
                    p.setIsPoint(false);
                    p.setProductId(pd.getId());
                    p.setClientId(pd.getClientId());
                    p.setMerchantId(pd.getMerchantId());
                    
                    //更新redis is_point 字段值
                    String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(p.getClientId(), p.getMerchantId(), p.getProductId());
                    Map<String, String> hash = redis.getMap(key);
                    if(hash==null || hash.size()==0){
                        hash = comLogic.getProductRedis(p.getClientId(), p.getMerchantId(), p.getProductId());
                    }
                    hash.put("is_point", ObjectUtils.toString(BooleanUtils.toInteger(p.getIsPoint(), 1, 0, 0), ""));
                    redis.putMap(key, hash);
                    
                    //更新mysql的is_point 字段值
                    productMapper.updateProductIsPoint(p);
                    
                    //把活动从mongo中解绑
                    ProductActivitiesInfo a = new ProductActivitiesInfo();
                    a.setActivitiesId(ac.getId());
                    a.setActivitiesType(ActivitiesType.point.getCode());
                    valueObj.put("activities_info", JSON.toJSON(a));
                    
                    whereObj.put("_id", pd.getId());
                    success = manager.update(valueObj, whereObj, MongoTableName.CB_PRODUCT_DETAIL, "$pull", true, false)>0;
                    
                    if(success){
                        count++;
                    }
                }
                if(count>0){
                    Map<String,Object> param = new HashMap<String,Object>();
                    param.put("id", ac.getId());
                    //更新该条送积分活动已经绑定的商品数量
                    param.put("count", -count);
                    productMapper.updatePointsCount(param);
                    sqlSession.commit(true);
                }
            }
        } catch (Exception e) { 
            if(null != sqlSession){
                sqlSession.rollback(true); 
            }
            LogCvt.error("活动解除绑定商品失败，原因:" + e.getMessage(),e); 
            throw new Exception();
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return count;
    }


    /**
     * 查询送积分活动已经绑定的商品列表
     */
    @Override
    public MongoPage findActivtyProductsByPage(
            long activtyId, Page<ProductDetail> page) {
        MongoPage mPage = new MongoPage(page.getPageNumber(), page.getPageSize());
        DBObject whereObject = new BasicDBObject();
        
        ActivitiesInfo ac = null;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            ac = productMapper.findPoints(activtyId);
        } catch (Exception e) { 
            LogCvt.error("活动绑定商品查询活动mysql失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        }
        if(ac!=null){
            try{
                /**********************操作Mongodb数据库**********************/
                mPage.setSort(new Sort("create_time", OrderBy.DESC));
                
                if(ac.getClientId()!=null && !"".equals(ac.getClientId())){
                    whereObject.put("client_id", ac.getClientId());
                }
                whereObject.put("activities_info.activities_id", ac.getId());
                mPage = manager.findByPage(mPage, whereObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            } catch (Exception e) { 
                LogCvt.error("查询活动绑定的商品列表失败，原因:" + e.getMessage(),e); 
            }
        }
        return mPage;
    }


    /**
     * 查询可以绑定送积分活动的商品列表
     */
    @Override
    public MongoPage findProductsForActivtyByPage(long activtyId, String name,
            Page<ProductDetail> page) {
        MongoPage mPage = new MongoPage(page.getPageNumber(), page.getPageSize());
        
        SqlSession sqlSession = null;
        try{
            String pointRate = null;//银行兑换积分比例
            
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            ActivitiesInfo ac = productMapper.findPoints(activtyId);
            
            if(ac!=null && !ActivitiesStatus.repeal.getCode().equals(ac.getStatus())){//只有未启用和启用的活动才能绑定商品
                
                pointRate = productMapper.findPointRate(ac.getClientId());
                
                DBObject whereObj = getValidContition(ac,pointRate);
                
                if(name!=null && !"".equals(name)){
                    //商品名称模糊查询
                    StringBuilder regexStr = new StringBuilder(".*");
                    regexStr.append(name).append(".*");
                    Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
                    BasicDBObject like = new BasicDBObject();
                    like.append("$regex", pattern);
                    whereObj.put("name", like);
                }
                
                /**********************操作Mongodb数据库**********************/
                mPage.setSort(new Sort("create_time", OrderBy.DESC));
                
                mPage = manager.findByPage(mPage, whereObj, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            }
        } catch (Exception e) { 
            LogCvt.error("活动绑定商品查询失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        }
        return mPage;
    }

    @Override
    public boolean validMerchantProductBatch(String merchantId) {
        Boolean result = false;
        SqlSession sqlSession = null;
        try {
            if(merchantId!=null && !"".equals(merchantId)){
                sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
                ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
                result = true;
                
                //启用商户时 将该普通商户的商品的未提交状态变成待审核
                Map<String, Object> auditParam = new HashMap<String,Object>();
              
                auditParam.put("merchantId",merchantId);
                auditParam.put("auditState",ProductAuditState.noAudit.getCode());
                auditParam.put("oldAuditState",ProductAuditState.noCommit.getCode());
                auditParam.put("filterType",ProductType.group.getCode());
                
                productMapper.updateProductAuditState(auditParam);
                sqlSession.commit(true);
            }
        } catch (Exception e) {
            result = false;
            if(null!=sqlSession)
                sqlSession.rollback(true);  
            LogCvt.error("启用商户时 将该普通商户的商品的未提交状态变成待审核失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return result;
    }


    /**
     * 更新预售和线下积分 市级-区 市级-门店 的关联关系
     */
    @Override
    public String updateProductDetailCityArea(int pageNumber, int pageSize,
            String type) {
        SqlSession sqlSession = null;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        try{
            MongoPage mPage = new MongoPage(pageNumber, pageSize);
            mPage.setSort(new Sort("start_time", OrderBy.DESC));
            
            DBObject whereObject = new BasicDBObject();
            whereObject.put("product_type", type);
            whereObject.put("outlet_info", new BasicDBObject(QueryOperators.EXISTS, true));
//            whereObject.put("city_areas", new BasicDBObject(QueryOperators.EXISTS, false));
            
            mPage = manager.findByPage(mPage, whereObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            
            @SuppressWarnings("unchecked")
			List<ProductDetail> productDetails = (List<ProductDetail>)mPage.getItems();
            if(productDetails!=null && productDetails.size()>0){
                
                CommonLogic comLogic = new CommonLogicImpl();
                
                sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
                ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
                
                
                DBObject value = null;
                DBObject where = null;
                
                Map<String,ProductOutletInfo> outletsMap = new HashMap<String,ProductOutletInfo>();
                
                Map<Long,List<ProductArea>> countys = null;
                Map<Long,List<ProductOutlet>> pcosMap = null;
                Map<String, Object> param = new HashMap<String, Object>();
                ProductOutletInfo poi = null;
                Long cityId = null;
                Long areaId = null;
                List<ProductOutlet> productOutlets = null;
                boolean isSeccuses = false;
                for(ProductDetail pd : productDetails){
                    
                    //根据门店id获取对应的区id
                    productOutlets = pd.getOutletInfo();
                    if (productOutlets != null && productOutlets.size() > 0) {
                        
                        countys = new HashMap<Long,List<ProductArea>>();//城市下的区
                        pcosMap = new HashMap<Long,List<ProductOutlet>>();//城市下的门店
                        
                        param.put("clientId", pd.getClientId());
                        
                        Set<Long> areaIds = new HashSet<Long>();
                        for (ProductOutlet outlet : productOutlets) {
                            cityId = null;
                            areaId = null;
                            
                            poi = outletsMap.get(outlet.getOutletId());
                            if(poi==null){
                                param.put("outletId", outlet.getOutletId());
                                poi = productMapper.findOutletById(param);
                            }
                            if (poi != null) {
                                outletsMap.put(poi.getOutletId(), poi);
                                if (poi.getAreaId()>0 && Checker.isNotEmpty(poi.getAreaTreePath())) {
                                    String[] treePtah = poi.getAreaTreePath().split(",");
                                    if(treePtah.length==3){//门店所属的areaId为区
                                        areaId = Long.valueOf(treePtah[2]);
                                        cityId = Long.valueOf(treePtah[1]);
                                    } else if(treePtah.length==2){//门店所属的areaId为市
                                        cityId = Long.valueOf(treePtah[1]);
                                    }
                                    if(!areaIds.contains(poi.getAreaId())) {//门店所属的areaId没有计算过的需要计算该区对应的市
                                        areaIds.add(poi.getAreaId());
                                        if(areaId!=null){//门店所属的areaId为区
                                            if(countys.get(cityId)==null){
                                                countys.put(cityId, new ArrayList<ProductArea>());
                                            }
                                            ProductArea pa = new ProductArea();
                                            pa.setAreaId(poi.getAreaId());
                                            pa.setAreaName(poi.getAreaName());
                                            countys.get(cityId).add(pa);
                                        }
                                    }
                                    if(cityId!=null){
                                        if(pcosMap.get(cityId)==null){
                                            pcosMap.put(cityId, new ArrayList<ProductOutlet>());
                                        }
                                        ProductOutlet pco = new ProductOutlet();
                                        pco.setAddress(poi.getAddress());
                                        pco.setOrgCode(poi.getOrgCode());
                                        pco.setOutletId(poi.getOutletId());
                                        pco.setOutletName(poi.getOutletName());
                                        pco.setPhone(poi.getPhone());
                                        pco.setAreaId(poi.getAreaId());
                                        pcosMap.get(cityId).add(pco);
                                    }
                                }// end if (poi.getAreaId()>0 && Checker.isNotEmpty(poi.getAreaTreePath()))
                            }//end if (poi != null)
                        }// end for (ProductOutlet outlet : productOutlets)
                        
                        Area area = null;
                        //城市下的区
                        List<ProductCityArea> cityAreas = new ArrayList<ProductCityArea>();
                        for(Long pacityId : countys.keySet()){
                            ProductCityArea pca = new ProductCityArea();
                            pca.setCityId(pacityId);
                            area = comLogic.findAreaById(pacityId);
                            if(area!=null){
                                pca.setCityName(area.getName());
                            }
                            pca.setCountys(countys.get(pacityId));
                            cityAreas.add(pca);
                        }
                        
                        //城市下的门店
                        List<ProductCityOutlet> orgOutlets = new ArrayList<ProductCityOutlet>();
                        for(Long pcityId : pcosMap.keySet()){
                            ProductCityOutlet pco= new ProductCityOutlet();
                            pco.setCityId(pcityId);
                            area = comLogic.findAreaById(pcityId);
                            if(area!=null){
                                pco.setCityName(area.getName());
                            }
                            pco.setOrgOutlets(pcosMap.get(pcityId));
                            orgOutlets.add(pco);
                        }
                        
                        if(cityAreas.size()>0 || orgOutlets.size()>0){
                            value = new BasicDBObject();
                            if(cityAreas.size()>0){
                                value.put("city_areas", JSON.parse(JSonUtil.toJSonString(cityAreas)));
                            }
                            if(orgOutlets.size()>0){
                                value.put("org_outlets", JSON.parse(JSonUtil.toJSonString(orgOutlets)));
                            }
                            
                            where = new BasicDBObject();
                            where.put("client_id", pd.getClientId());
                            where.put("_id", pd.getId());
                            isSeccuses = manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set")>-1;
                            if(isSeccuses){
                                i++;
                                sb.append(pd.getId()+"成功\n");
                            }
                        }
                        
                    }//end if (productOutlets != null && productOutlets.size() > 0)
                }// end for(ProductDetail pd : productDetails)
            }// end if(productDetails!=null && productDetails.size()>0)
        } catch (Exception e) { 
            LogCvt.error("更新预售和线下积分 市级-区 市级-门店 的关联关系失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return "成功数量"+i+"\n"+sb.toString();
    }


    @Override
    public MongoPage queryVipPresellProducts(
            MongoPage mongoPage, ProductFilter product) {
      try{
          mongoPage = productDetialMongo.queryVipPresellProducts(mongoPage, product);
      } catch(Exception e){
          LogCvt.error("VIP预售列表，原因:" + e.getMessage(), e); 
      }
      return mongoPage;
    }
    

    /**
     * 每个商户门店查询2条商品 
     * 未到团购期商品，如设置上架后，则在前台用户可见但不可购买；如过了团购期商品，则自动下架前台不可见
     * 商品信息最多展示2条商品信息 商品销售量由多至少排序 按照更新时间倒序排列
     * pageSize 5条
     */
    @SuppressWarnings("unchecked")
	@Override
    public Map<String,List<ProductDetail>> queryGroupProducts(MongoPage mPage,QueryProductFilterVo filterVo,List<String> merchantIds,int pageSize) {
        DBObject dbObject = new BasicDBObject();
        try{
            /**********************操作Mongodb数据库**********************/
        	final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
            final String productName = ObjectUtils.toString(filterVo.getProductName(), "");
            
            if(clientId!=null && !"".equals(clientId)){
                dbObject.put("client_id", clientId);
            }
            dbObject.put("product_type", ProductType.group.getCode());
            dbObject.put("is_marketable", ProductStatus.onShelf.getCode());//已经上架的查询出来
            
            if(productName!=null && !"".equals(productName)){
                StringBuilder regexStr = new StringBuilder( ".*");
                regexStr.append(productName).append(".*");
                Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
                
                List<DBObject> orArray = new ArrayList<DBObject>();
                orArray.add(new BasicDBObject("name", ""));
                orArray.add(new BasicDBObject("full_name", ""));
                
                
                JSONArray jsonOrArray = (JSONArray)JSON.toJSON(orArray);
                for(Object ob : jsonOrArray){
                    JSONObject jb = (JSONObject)ob;
                    for (String key : jb.keySet()) {
                        if(key.equals("name")){
                            BasicDBObject nameLike = new BasicDBObject();
                            nameLike.append("$regex", pattern);
                            jb.put(key, nameLike);
                        } else if(key.equals("full_name")){
                            BasicDBObject fullNameLike = new BasicDBObject();
                            fullNameLike.append("$regex", pattern);
                            jb.put(key, fullNameLike);
                        }
                    }
                }
                dbObject.put("$or", jsonOrArray);
            }
            /*设置查询条件*/
            if(filterVo.getProductCategoryId()>0){
                //商品分类查询条件
                dbObject.put("product_category_info.product_category_id", filterVo.getProductCategoryId());
            }
            dbObject.put("end_time", new BasicDBObject(QueryOperators.GTE, new Date().getTime())); // 结束时间大于等于当前时间,过了团购期商品不显示
            
            Map<String,List<ProductDetail>> map = new HashMap<String,List<ProductDetail>>();
            if(merchantIds!=null && merchantIds.size()>0){
                //设置排序，支持多个字段排序
                mPage.setSort(new Sort("sell_count", OrderBy.DESC).on("start_time", OrderBy.DESC));//商品销售量由多至少排序 按照更新时间(开始时间，因为没有更新时间)倒序排列
                int num = 0;
                List<ProductDetail> productDetails = null;
                for(String merchantId : merchantIds){//每个商户查出2条商品，查到有商品的5条商户就可以
                    if(num==pageSize){//pageSize5个就够了
                        break;
                    }
                    dbObject.put("merchant_id", merchantId);
                    mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                    
                    productDetails = (List<ProductDetail>)mPage.getItems();
                    if(productDetails!=null && productDetails.size()>0){
                        for(ProductDetail pd : productDetails){
                            if(map.get(pd.getMerchantId())==null){
                                map.put(pd.getMerchantId(),new ArrayList<ProductDetail>());
                            }
                            map.get(pd.getMerchantId()).add(pd);
                        }
                        num++;
                    }
                }
                if(merchantIds.size()==1){
                	map.put("mPagePageCount_"+mPage.getPageCount(), null);//总页数
                	map.put("mPageTotalCount_"+mPage.getTotalCount(), null);//总数量
                }
            } else {//查出符合条件的商品，每个商户查出2条就够
              //设置排序，支持多个字段排序
                Sort sort = new Sort("sell_count", OrderBy.DESC).on("start_time", OrderBy.DESC);
                
                DBObject orderBy = sort.getSortObject();//商品销售量由多至少排序 按照更新时间(开始时间，因为没有更新时间)倒序排列
                
                List<ProductDetail> productDetails = (List<ProductDetail>)manager.findAll(dbObject, orderBy, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                if(productDetails!=null && productDetails.size()>0){
                    for(ProductDetail pd : productDetails){
                        if(map.get(pd.getMerchantId())==null){
                            map.put(pd.getMerchantId(),new ArrayList<ProductDetail>());
                        }
                        map.get(pd.getMerchantId()).add(pd);
                    }
                }
            }
            return map;
        } catch (Exception e) { 
            LogCvt.error("查询商户门店下的商品(特惠商品列表)失败，原因:" + e.getMessage(),e); 
        }
        return null;
    }
    
	public VipBindProductInfo findVipBindProduct(String productId) {
        SqlSession sqlSession = null;
        VipBindProductInfo  vbpi=null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            //根据vipId查询VIP规则
              vbpi = vipProductMapper.findVipBindProduct(productId);

             
        } catch (Exception e) { 
            LogCvt.error("分页查询可以绑定VIP规则的商品列表失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        }
        return vbpi;
	}


    /**
     * 生成单个二维码,有直接获取，无则重新生成
     * @param productId
     * @return Product
     */
	public Product findUrlCodeById(String productId) {
		Product resultProduct = new Product();
		Product product = new Product();
		product.setProductId(productId);
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
			// 根据vipId查询VIP规则
			List<Product> list = productMapper.getProductByProductId(product);
			if (list.size() > 0) {
				resultProduct = list.get(0);
				if (resultProduct!=null&&Checker.isEmpty(resultProduct.getCodeUrl())) {
			        //生成二维码
			        //商品类型对应的二维码类型
			        String qrCodeType = QrCodeType.PRODUCT.getCode();
			        if(ProductType.group.getCode().equals(resultProduct.getType())) {
			        	qrCodeType = QrCodeType.GROUP.getCode();//团购商品
			        } else if(ProductType.presell.getCode().equals(resultProduct.getType())) {
			        	qrCodeType = QrCodeType.PRESELL.getCode();//预售商品
			        } else if(ProductType.special.getCode().equals(resultProduct.getType())) {
			        	qrCodeType = QrCodeType.SPECIAL.getCode();//名优特惠商品
			        } else if(ProductType.onlinePoint.getCode().equals(resultProduct.getType())) {
			        	qrCodeType = QrCodeType.ONLINEPOINT.getCode();//在线积分兑换商品
			        } else if(ProductType.dotGift.getCode().equals(resultProduct.getType())) {
			        	qrCodeType = QrCodeType.DOTGIFT.getCode();//网点礼品商品
			        }
					String url = generateQrCode(resultProduct.getClientId(),resultProduct.getProductId(),qrCodeType);// 生成二维码
					if (!Checker.isEmpty(url)) {
						resultProduct.setCodeUrl(url);
						Map<String, Object> urlParam = new HashMap<String, Object>();
						urlParam.put("clientId", resultProduct.getClientId());
						urlParam.put("productId", resultProduct.getProductId());
						urlParam.put("codeUrl", url);
						productMapper.updateProductCodeUrl(urlParam);
						sqlSession.commit(true);
					}
				}
			}
		} catch (Exception e) {
        	if(null != sqlSession)
        		sqlSession.rollback(true);  
            LogCvt.error("更改codeurl:更新Product mysql失败，原因:" + e.getMessage(),e); 
		} finally {
			if (null != sqlSession) {
				sqlSession.close();
			}
		}
		return resultProduct;
	}


	/**
	 * 获取类目营销里的按距我最近排序的特惠商品列表
	 * 
	 * @param pageVo
	 * @param filterVo
	 * @param fs
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ProductDetailSimple> queryDistanceGroupGoods(PageVo pageVo, QueryProductFilterVo filterVo, FiledSort fs)
			throws Exception {
		StringBuilder sort = new StringBuilder(fs.getSortName());
		if (fs.getSortBy() < 0) {// 排序规则 负数代表降序，整数代表升序
			sort.append("-desc");
		} else {
			sort.append("-asc");
		}
		
		final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
		final String productName = ObjectUtils.toString(filterVo.getProductName(), "");
		final long areaId = filterVo.getAreaId();
		

		// 缓存里特惠商品的key缓存时间  符合条件的商品缓存时间30代表30分钟
		int groupProductTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_STEADY_LOCK_TIME);
		if (filterVo.getLongitude() > 0.0 || filterVo.getLatitude() > 0.0) {// 上传了纬度
			groupProductTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_LAT_CON_LOCK_TIME);// 特惠商品查询根据经纬度条件查询缓存有效时间,2代表2分钟
		}
		// key生成逻辑
		// 缓存里特惠商品的key
		String groupProductkey = RedisKeyUtil.cbbank_group_product_client_id_category_id_lat_con_product_name_dis(
				clientId, areaId, sort.toString(), filterVo.getProductCategoryId(), filterVo.getLatitude(),
				filterVo.getLongitude(), productName, filterVo.getDistance());
		
		String groupProductLockkey = RedisKeyUtil.cbbank_group_product_lock_client_id_category_id_lat_con_product_name_dis(clientId,
				areaId, sort.toString(), filterVo.getProductCategoryId(), filterVo.getLatitude(),
				filterVo.getLongitude(), productName, filterVo.getDistance());
					
		// 符合条件的商户门店查询条件的商户
		String groupMerchantKey = RedisKeyUtil.cbbank_product_merchant_client_id_lat_con(clientId,
				filterVo.getAreaId(), filterVo.getLatitude(), filterVo.getLongitude(), filterVo.getDistance());
		
		//符合条件的商户门店查询条件的商户 和符合商户商品查询条件的商户 的交集key
		String unionGroupProductkey = RedisKeyUtil.cbbank_group_product_merchant_client_id_category_id_lat_con_product_name_dis(
        		clientId, areaId, filterVo.getProductCategoryId(), filterVo.getLatitude(), filterVo.getLongitude(),
        		productName, filterVo.getDistance());
		

		List<ProductDetailSimple> productDetailSimples = null;
		if (Checker.isNotEmpty(groupProductkey)) {
			/********** zrange查询特惠商品列表start **********/
			// 已经缓存过商品数据
			// 查询商品
			int start = (pageVo.getPageNumber() - 1) * pageVo.getPageSize();
			int end = pageVo.getPageNumber() * pageVo.getPageSize() - 1;
			productDetailSimples = ProductRedis.get_cbbank_group_product_h5_redis(groupProductkey, start, end);
			boolean isEnough = true;
			if (productDetailSimples == null) {
				productDetailSimples = new ArrayList<ProductDetailSimple>();
			}
			if (productDetailSimples.size() < pageVo.getPageSize()) {
				isEnough = false;
			}
			int num = productDetailSimples.size();// 已经查到的数量
			while (!isEnough) {// 数据不够 还要继续查询
				// 查询 设置redis逻辑:
				// (1)接着查询缓存中符合条件的商户门店中的商户ids里的没有查询过的前50个商户的全部特惠商品
				// (2)设置redis逻辑
				boolean isFinish = distanceGroupGoodsRedis(filterVo, groupProductkey, groupProductLockkey, unionGroupProductkey, groupMerchantKey,
						groupProductTime, pageVo, fs);

				// 从缓存中查出不够的数量的商品
				start = start + num;
				num = 0;
				List<ProductDetailSimple> temps = ProductRedis.get_cbbank_group_product_h5_redis(groupProductkey,
						start, end);
				if (temps != null && temps.size() > 0) {
					productDetailSimples.addAll(temps);
					num = temps.size();
				}
				if (productDetailSimples.size() == pageVo.getPageSize()) {
					isEnough = true;
				} else {
					// 去取设置过的特惠商品列表的最后一条商品所属的商户id
					String merchantId = ProductRedis.getLastGroupProductMerchartId(groupProductkey);
					// 设置过的商户门店的最后一条商户id
					Set<String> strs = ProductRedis.zrevrange(groupMerchantKey, 0, 0);
					if (strs != null && strs.size() > 0) {
						for (String str : strs) {
							if (str.equals(merchantId)) {
								isEnough = true;
							}
						}
					} else {
						isEnough = true;
					}
				}
				if(isFinish){//已经查完就不需要再去查询
					isEnough = true;
				}
			}
			/********** zrange查询特惠商品列表end **********/
		}
		return productDetailSimples;
	}

	/**
	 * 类目营销里的按距我最近排序的特惠商品列表redis缓存操作 50个商户的商品进行缓存
	 * @param filterVo
	 * @param groupProductkey
	 * @param groupProductLockkey
	 * @param unionGroupProductkey
	 * @param groupMerchantKey
	 * @param groupProductTime
	 * @param pageVo
	 * @param fs
	 * @return 返回是否已经把符合条件的所欲商户的商品查完放到redis中
	 * @throws Exception
	 */
	private boolean distanceGroupGoodsRedis(QueryProductFilterVo filterVo, String groupProductkey,
			String groupProductLockkey, String unionGroupProductkey, String groupMerchantKey, int groupProductTime, PageVo pageVo, FiledSort fs)
			throws Exception {
		long startTime = System.currentTimeMillis();
		LogCvt.debug("个人h5查询类目营销里的特惠商品数据[开始...]时间戳=" + startTime);

		/****** 商户缓存设置 ***/
		/* 设置缓存有效时间 */
		int merchantTime = 5;// 商户门店缓存时间
		/****** 商户门店缓存设置 ***/
		groupProductMerchantOutletRedis(filterVo, pageVo, merchantTime);
		/****** 商户门店缓存设置end ***/
		
		/****** 商户缓存和有商品的商户id交集缓存设置 ***/
		groupProductUnionMerchantRedis(filterVo, unionGroupProductkey, groupProductTime, groupMerchantKey, merchantTime);
		/****** 商户缓存和有商品的商户id交集缓存设置 end ***/
        
		// 去商户门店的存放商户ids的key去拿没有设置过对应商户的全部特惠商品列表的前50个商户id:
		// (1)取设置过的特惠商品列表的最后一条商品所属的商户id
		String merchantId = ProductRedis.getLastGroupProductMerchartId(groupProductkey);

		// (2)取merchantId在商户门店存放商户ids的key里的score值
		double min = 0;
		double max = 49;
		if (Checker.isNotEmpty(merchantId)) {
			Double score = ProductRedis.zscore(unionGroupProductkey, merchantId);
			if (score != null) {
				min = score + 1;
				max = score + 50;
			}
		}

		// 将key的值设为value，当且仅当key不存在。设置成功，返回1。设置失败，返回0。
		long gpkeyLockResult = ProductRedis.setLock(groupProductLockkey, groupProductTime);
		if (gpkeyLockResult == 1) {// 设置成功说明该key redis里没有数据 需要从0 查询到end值条数上商品数据
			min = 0;
			ProductRedis.expire(groupProductkey, groupProductTime); // 设置超时时间
			ProductRedis.expire(groupProductkey+":productid:score", groupProductTime); // 设置超时时间
		}
		// (3)取score之后的50个商户ids
		Set<String> merchantIds = ProductRedis.getGroupProductMerchartIds(unionGroupProductkey, min, max);
		String[] a = new String[merchantIds.size()];
		int i = 0;
		for(String mid : merchantIds){
			a[i] = mid;
			i++;
		}
		// 第一个
		String firstMerchantId = ((a == null || a.length == 0) ? "" : a[0]);
		// 最后一个
		String lastMerchantId = ((a == null || a.length == 0) ? "" : a[a.length - 1]);

		// firstMerchantId和lastMerchantId之间的商户id对应的商品只有一个地方进行redis写
		long unionLockResult = ProductRedis.setLock(groupProductLockkey + "_" + firstMerchantId + "_" + lastMerchantId,
				groupProductTime);
		// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1。设置失败，返回 0 。
		if (unionLockResult == 1) {
			// key不存在 此次设置成功,说明缓存没有缓存过商品数据,需要查询符合条件的商户门店和查询全部的符合条件的商品(从mongo查，符合条件的商户门店中的商户ids里的前50个商户的全部特惠商品)
			// (4)取merchantIds的全部特惠商品
			List<ProductDetail> pds = null;

			if (merchantIds != null && merchantIds.size() > 0) {
				// 根据商户门店信息查询符合条件的商品
				// 类目商品按照所在门店距离由近至远进行排序，如距离相同按照商品销售数量由高到低排序，如销售数量相同则按商品上架时间由远至近排序
				ProductDetialMongo productDetialMongo = new ProductDetialMongo();
				pds = productDetialMongo.queryGroupProducts(filterVo, fs, merchantIds, null);
			}
			// (5)将特惠商品列表放入缓存zset里面
			if (pds != null && pds.size() > 0) {
				double lastScore = 0;
				Set<String> strs = ProductRedis.zrevrange(groupProductkey, 0, 0);
				if (strs != null && strs.size() > 0) {
					for (String str : strs) {
						lastScore = ProductRedis.zscore(groupProductkey, str);
					}
				}
				ProductRedis.set_cbbank_group_product_h5_redis(groupProductkey, lastScore, pds);
			}
		}
		long endTime = System.currentTimeMillis();
		LogCvt.debug("个人h5查询类目营销里的特惠商品数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
		if(merchantIds==null || merchantIds.size()<(max-min+1)){
			return true;
		} else {
			return false;
		}
	}
	
	private void groupProductUnionMerchantRedis(QueryProductFilterVo filterVo, String unionGroupProductkey, int groupProductTime, String groupMerchantKey, int merchantTime) throws Exception {
		final long areaId = filterVo.getAreaId();
		final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
        final String productName = ObjectUtils.toString(filterVo.getProductName(), "");
        
		//符合条件的商户门店查询条件的商户 和符合商户商品查询条件的商户 的交集key
        String unionGroupProductLockkey = RedisKeyUtil.cbbank_group_product_merchant_lock_client_id_category_id_lat_con_product_name_dis(
        		clientId, areaId, filterVo.getProductCategoryId(), filterVo.getLatitude(), filterVo.getLongitude(), productName, filterVo.getDistance());
    
        //个人H5类目营销里的特惠商品列表一个经纬度的或一个地区的有符合条件的商品的商户id
        String groupProductMerchantIdkey = RedisKeyUtil.cbbank_group_product_merchant_id_client_id_category_id_lat_con_product_name_dis(
        		clientId, areaId, filterVo.getProductCategoryId(), filterVo.getLatitude(), filterVo.getLongitude(), productName, filterVo.getDistance());
        
        String groupProductMerchantIdLockkey = RedisKeyUtil.cbbank_group_product_merchant_id_lock_client_id_category_id_lat_con_product_name_dis(
				clientId, areaId, filterVo.getProductCategoryId(), filterVo.getLatitude(), filterVo.getLongitude(), productName, filterVo.getDistance());
		
		long unionLockResult = ProductRedis.setLock(unionGroupProductLockkey, merchantTime);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
        if(unionLockResult == 1) {//key不存在   此次设置成功   说明原来没设置过这个值 需要查询符合条件的商户门店和查询全部的符合条件的商品(从mongo查，每个商户只需要2条特惠商品)
            long groupProductMerchantIdLockResult = ProductRedis.setLock(groupProductMerchantIdLockkey, groupProductTime);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
            if(groupProductMerchantIdLockResult == 1) {//key不存在   此次设置成功   说明原来没设置过这个值 查询全部的符合条件的商品(从mongo查，每个商户只需要2条特惠商品)
              //根据商户门店信息查询符合条件的商品
                long startTime = System.currentTimeMillis();
                LogCvt.debug("个人h5查询类目营销特惠商品列表所属的商户id查询数据[开始...]时间戳=" + startTime);
                
                //查询条件
                BasicDBObject query = productDetialMongo.groupProductQueryDBObject(filterVo);
                //排序
                Sort sort = new Sort("sell_count", OrderBy.DESC).on("rack_time", OrderBy.ASC);
                List<String> productMerchantIds = productDetialMongo.queryProductMercantIds(query,sort);
                
                if(productMerchantIds!=null && productMerchantIds.size()>0){
                	ProductRedis.set_cbbank_group_product_merchant_h5_redis(groupProductMerchantIdkey, productMerchantIds, groupProductTime);
                    LogCvt.debug("个人h5查询类目营销特惠商品列表所属的商户zsetGrouProductMerchantIds:"+JSON.toJSONString(productMerchantIds));
                }
                long endTime = System.currentTimeMillis();
                LogCvt.debug("个人h5查询类目营销特惠商品列表所属的商户id查询数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                
            }
            
            //求交集
            ProductRedis.zsetinterstore(unionGroupProductkey, groupMerchantKey, groupProductMerchantIdkey, merchantTime);
            LogCvt.debug("个人h5查询类目营销特惠商品列表交集商户id:"+JSON.toJSONString(ProductRedis.get_cbbank_product_merchant_h5_redis(unionGroupProductkey, 0, -1)));
        }
        
        
	}

	/**
	 * 营销类目里的其他排序的特惠商品列表(销量，价格，折扣排序)
	 * 
	 * @param pageVo
	 * @param filterVo
	 * @param fs
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ProductDetailSimple> queryGeneralGroupGoods(PageVo pageVo, QueryProductFilterVo filterVo, FiledSort fs)
			throws Exception {
		List<ProductDetailSimple> productDetailSimples = null;

		int start = (pageVo.getPageNumber() - 1) * pageVo.getPageSize();
		int end = pageVo.getPageNumber() * pageVo.getPageSize() - 1;

		final int queryMongodbDataCount = 100; // 每次向mongodb查询的数据量
		final int index = end <= queryMongodbDataCount ? 1 : (int) ((end / queryMongodbDataCount) + (end
				% queryMongodbDataCount > 0 ? 1 : 0)); // 向mongodb第几次查询queryMongodbDataCount条数据

		// 已经缓存过商品数据
		// 查询商品
		StringBuilder sort = new StringBuilder(fs.getSortName());
		if (fs.getSortBy() < 0) {// 排序规则 负数代表降序，整数代表升序
			sort.append("-desc");
		} else {
			sort.append("-asc");
		}

		final long areaId = filterVo.getAreaId();
		final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
        final String productName = ObjectUtils.toString(filterVo.getProductName(), "");

		// 缓存里特惠商品的key缓存时间 符合条件的商品缓存时间 30代表30分钟
		int groupProductTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_STEADY_LOCK_TIME);
		// 缓存里特惠商品的key
		// 商品key生成逻辑
		String groupProductkey = RedisKeyUtil.cbbank_group_product_index_client_id_area_id_category_id_sort_product_name(
				index, clientId, areaId, filterVo.getProductCategoryId(), sort.toString(),
				productName);

		String groupProductLockkey = RedisKeyUtil
				.cbbank_group_product_lock_index_client_id_area_id_category_id_sort_product_name(index,
						clientId, areaId, filterVo.getProductCategoryId(), sort.toString(),
						productName);

		if (Checker.isNotEmpty(groupProductkey)) {
			long lockResult = ProductRedis.setLock(groupProductLockkey, groupProductTime);
			// 将key的值设为 value，当且仅当key不存在。设置成功，返回1。设置失败，返回0。
			if (lockResult == 1) {// key不存在 此次设置成功 说明原来没设置过这个值 需要查询mongodb
				LogCvt.debug("个人h5查询营销类目里的其他排序的特惠商品列表(销量，价格，折扣排序)key[" + groupProductkey + "是第一次查询, 查询mongodb");
				// 从mongo中查询需要的条数
				MongoPage mPage = new MongoPage(pageVo.getPageNumber(), pageVo.getPageSize());
				productDetailSimples = queryMongoGroupGoods(mPage, filterVo, fs);
				// 异步将index，100条设置到缓存中
				generalGroupGoodsRedis(index, queryMongodbDataCount, filterVo, groupProductkey, groupProductLockkey, groupProductTime, fs);
			} else {
				// key已经存在 本次设置不成功 说明不是第一次查询 先看redis有没有数据 如有则返回 无则查mongodb
				LogCvt.debug("个人h5查询营销类目里的其他排序的特惠商品列表(销量，价格，折扣排序)key[" + groupProductkey
						+ "非首次查询, 先看redis有没有数据  如有则返回  无则查mongodb");
				long count = redis.zcard(groupProductkey); // 获取缓存key中有多少条数据
				if (count >= pageVo.getPageNumber() * pageVo.getPageSize()) { // 缓存中的数据
																				// 足够本次查询的数据
					productDetailSimples = ProductRedis.get_cbbank_group_product_h5_redis(groupProductkey, start, end);
				} else {// 缓存中的数据 不够本次查询的数据 需要查询mongo数据库 pageSize条数据返回给前端 然后
						// 起多线程查queryMongodbDataCount条数据添加到redis
					// 从mongo中查询需要的条数
					MongoPage mPage = new MongoPage(pageVo.getPageNumber(), pageVo.getPageSize());
					productDetailSimples = queryMongoGroupGoods(mPage, filterVo, fs);
				}
			}
		}
		return productDetailSimples;
	}

	/**
	 * 直接查询mongo营销类目里的特惠商品列表
	 * 
	 * @param mPage
	 * @param filterVo
	 * @param fs
	 * @return
	 */
	private List<ProductDetailSimple> queryMongoGroupGoods(MongoPage mPage, QueryProductFilterVo filterVo, FiledSort fs) {
		List<ProductDetailSimple> productDetailSimples = new ArrayList<ProductDetailSimple>();
		long startTime = System.currentTimeMillis();
		LogCvt.debug("个人h5查询营销类目里的特惠商品列表mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize"
				+ mPage.getPageSize() + "数据[开始...]时间戳=" + startTime);
		// 查询
		ProductDetialMongo productDetialMongo = new ProductDetialMongo();
		List<ProductDetail> pds = productDetialMongo.queryGroupProducts(filterVo, fs, null, mPage);
		
		if (pds != null && pds.size() > 0) {
			for (ProductDetail pd : pds) {
				productDetailSimples.add(ProductRedis.formatProductDetailToSimple(pd));
			}
		}
		long endTime = System.currentTimeMillis();
		LogCvt.debug("个人h5查询营销类目里的特惠商品列表mongodb查询,currentPage" + mPage.getPageNumber() + ",pageSize"
				+ mPage.getPageSize() + "数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
		return productDetailSimples;
	}

	/**
	 * 营销类目里的其他排序的特惠商品列表(销量，价格，折扣排序)进行缓存
	 * 
	 * @param index
	 * @param queryMongodbDataCount
	 * @param filterVo
	 * @param groupProductkey
	 * @param groupProductTime
	 * @param fs
	 * @throws Exception
	 */
	private void generalGroupGoodsRedis(final int index, final int queryMongodbDataCount,
			final QueryProductFilterVo filterVo, final String groupProductkey, final String groupProductLockkey, final int groupProductTime,
			final FiledSort fs) throws Exception {
		FroadThreadPool.execute(new Runnable() { // 异步塞缓存
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				MongoPage mPage = new MongoPage(index, queryMongodbDataCount);
				LogCvt.debug("个人h5查询营销类目里的其他排序的特惠商品列表(销量，价格，折扣排序)异步[ThreadPool]设置redis缓存,key["
						+ groupProductkey + ",currentPage" + mPage.getPageNumber() + ",pageSize"
						+ mPage.getPageSize() + "数据[开始...]时间戳=" + startTime);
				try {
					// 查询
					ProductDetialMongo productDetialMongo = new ProductDetialMongo();
					List<ProductDetail> pds = productDetialMongo.queryGroupProducts(filterVo, fs, null, mPage);
					
					ProductRedis.expire(groupProductkey, groupProductTime); // 设置超时时间
					// 将特惠商品列表放入缓存zset里面
					if (pds != null && pds.size() > 0) {
						ProductRedis.set_cbbank_group_product_h5_redis(groupProductkey, 0, pds);
					}
						
				} catch (Exception e) {
					LogCvt.error(
							"个人h5查询营销类目里的其他排序的特惠商品列表(销量，价格，折扣排序)异步[ThreadPool]设置redis缓存失败, 原因:"
									+ e.getMessage(), e);
				} finally {
					long endTime = System.currentTimeMillis();
					LogCvt.debug("个人h5查询营销类目里的其他排序的特惠商品列表(销量，价格，折扣排序)异步[ThreadPool]设置redis缓存数据[结束...]时间戳="
							+ endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
				}
			}
		});
					
	}

	/**
	 * 特惠商品列表需要商户门店信息时候需要调用商户模块的接口:商户门店缓存设置
	 * 
	 * @param filterVo
	 * @param pageVo
	 * @throws Exception
	 */
	private void groupProductMerchantOutletRedis(QueryProductFilterVo filterVo, PageVo pageVo, int merchantTime) throws Exception {
		/****** 商户缓存设置 ***/
		Long areaId = filterVo.getAreaId();
		final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
		
		if (filterVo.getLongitude() > 0.0 || filterVo.getLatitude() > 0.0) {// 上传了纬度
			merchantTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_LAT_CON_LOCK_TIME);// 特惠商品查询根据经纬度条件查询缓存有效时间,2代表2分钟
		} else if (areaId > 0) {// 没上传纬度按区
			merchantTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_STEADY_LOCK_TIME);// 团购商品查询条件不常变缓存有效时间,30代表30分钟
		}

		// key生成逻辑
		// 符合条件的商户门店查询条件的商户
		String groupMerchantKey = RedisKeyUtil.cbbank_product_merchant_client_id_lat_con(clientId,
				filterVo.getAreaId(), filterVo.getLatitude(), filterVo.getLongitude(), filterVo.getDistance());
		// 符合条件的商户门店查询条件的商户
		String groupMerchantLockKey = RedisKeyUtil.cbbank_product_merchant_lock_client_id_lat_con(clientId,
				filterVo.getAreaId(), filterVo.getLatitude(), filterVo.getLongitude(), filterVo.getDistance());
		

		// 将key的值设为 value，当且仅当key不存在。设置成功，返回1。设置失败，返回0。
		long groupMerchantLockResult = ProductRedis.setLock(groupMerchantLockKey, merchantTime);
		if (groupMerchantLockResult == 1) {
			// key不存在 此次设置成功 说明原来没设置过这个值 需要查询全部的商户门店，调用商户模块的门店接口
			LogCvt.debug("个人h5查询类目营销里的特惠商品商户门店groupMerchantKey[" + groupMerchantKey + "],groupMerchantLockKey["
					+ groupMerchantLockKey + "]是第一次查询, 查询全部的商户门店，调用门店接口");

			long startTime = System.currentTimeMillis();
			LogCvt.debug("数据[开始...]时间戳=" + startTime);

			// 调用商户模块的接口
			MerchantLogic merchantLogic = new MerchantLogicImpl();
			// 查全部的门店商户信息
			int skip = -1;
			OutletDetailSimplePageVoRes outletPageVo = merchantLogic.queryMerchantOutlets(filterVo, pageVo, skip);

			// 商户门店信息
			List<OutletDetailSimpleVo> outletDetailSimpleVos = outletPageVo != null ? outletPageVo
					.getOutletDetailSimpleVoList() : null;
			if (outletDetailSimpleVos != null && outletDetailSimpleVos.size() > 0) {
				// 将商户id放redis里面
				ProductRedis
						.set_cbbank_product_merchant_h5_redis(groupMerchantKey, outletDetailSimpleVos, merchantTime);
				// 将商户门店信息放redis里面
				ProductRedis.set_cbbank_product_merchant_outlet_h5_redis(groupMerchantKey, outletDetailSimpleVos,
						merchantTime);
				LogCvt.debug("类目营销里的zsetMerchantIds:"
						+ JSON.toJSONString(ProductRedis.get_cbbank_product_merchant_h5_redis(groupMerchantKey, 0, -1)));
			}
			long endTime = System.currentTimeMillis();
			LogCvt.debug("个人h5查询类目营销里的特惠商品商户门店数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");

		}
		/****** 商品缓存设置 ***/

	}

	@Override
	public Map<String, OutletDetailSimpleVo> queryGroupGoodMerhantOutlets(
			List<ProductDetailSimple> productDetailSimples, QueryProductFilterVo filterVo, PageVo pageVo)
			throws Exception {
		if (productDetailSimples != null && productDetailSimples.size() > 0) {
			final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");

			/****** 获取商户门店的地址和名称 确保缓存有值，没有需要进行商户缓存设置 ***/
			/* 设置缓存有效时间 */
			int merchantTime = 5;// 商户门店缓存时间
			groupProductMerchantOutletRedis(filterVo, pageVo, merchantTime);
			/****** 获取商户门店的地址和名称 确保缓存有值，没有需要进行商户缓存设置 ***/
			
			// 符合条件的商户门店查询条件的商户
			String groupMerchantKey = RedisKeyUtil.cbbank_product_merchant_client_id_lat_con(clientId,
					filterVo.getAreaId(), filterVo.getLatitude(), filterVo.getLongitude(), filterVo.getDistance());

			Map<String, OutletDetailSimpleVo> merchantOutlets = new HashMap<String, OutletDetailSimpleVo>();
			for (ProductDetailSimple productDetailSimple : productDetailSimples) {
				OutletDetailSimpleVo outletDetailSimpleVo = ProductRedis
						.get_cbbank_product_merchant_outlet_h5_redis(groupMerchantKey + ":"
								+ productDetailSimple.getMerchantId());
				if (outletDetailSimpleVo != null) {
					merchantOutlets.put(productDetailSimple.getMerchantId(), outletDetailSimpleVo);
				}
			}
			return merchantOutlets;
		}
		return null;
	}


	@Override
	public boolean updateGroupProductAreaOutlet(String type, OutletVo outletVo) {
		boolean success = false;
		if(type.equals("1")){//门店新加
			success = addProductOutletRelation(outletVo);
        } else if(type.equals("2")){//门店修改");
    		//门店
        	DBObject whereObj = new BasicDBObject();
    		whereObj.put("product_type", ProductType.group.getCode());
            whereObj.put("is_marketable", new BasicDBObject(QueryOperators.NE, ProductStatus.isDeleted.getCode()));
            whereObj.put("org_outlets.org_outlets.outlet_id", outletVo.getOutletId());
            
            ProductDetialMongo productDetialMongo = new ProductDetialMongo();
            Map<String,Object> p = productDetialMongo.queryGroupProductOneOutlet(whereObj);
    		ProductDetail pd = (ProductDetail)p.get("pd");
    		
    		ProductOutlet productOutlet = (ProductOutlet)p.get("po");
            
    		//是否发生变化
    		if(pd!=null && productOutlet!=null){
    			if(outletVo.getAreaId()==0){
        			outletVo.setAreaId(productOutlet.getAreaId());
        		}
        		boolean isUpdate = false;
        		if(outletVo.getAreaId()!=productOutlet.getAreaId()){
        			isUpdate = true;
        		}
        		if(outletVo.getAddress()!=null && !outletVo.getAddress().equals(productOutlet.getAddress())){
        			isUpdate = true;
        		}
        		if(outletVo.getOutletName()!=null && !outletVo.getOutletName().equals(productOutlet.getOutletName())){
        			isUpdate = true;
        		}
        		if(outletVo.getPhone()!=null && !outletVo.getPhone().equals(productOutlet.getPhone())){
        			isUpdate = true;
        		}
        		if(isUpdate){
        			success = deleteProductOutletRelation(outletVo.getOutletId());
                	if(success){
                		success = addProductOutletRelation(outletVo);
                	}
        		} else {
        			success = true;
        		}
    		}
		} else if(type.equals("3")){//门店删除");
			success = deleteProductOutletRelation(outletVo.getOutletId());
		}
		return success; 
	}
	
	/**
	 * 新加门店及该门店所在地区和商品的关联关系
	 * @param outletVo
	 */
	private boolean addProductOutletRelation(OutletVo outletVo){
		int areaSuccess = -1;
		int outletSuccess = -1;
		
		DBObject whereObj = new BasicDBObject();
    	whereObj.put("merchant_id", outletVo.getMerchantId());
		whereObj.put("product_type", ProductType.group.getCode());
        whereObj.put("is_marketable", new BasicDBObject(QueryOperators.NE, ProductStatus.isDeleted.getCode()));
        ProductDetail pd = manager.findOne(whereObj, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
        if(pd!=null){
        	CommonLogic comLogic = new CommonLogicImpl();
    		Area city = null;
    		Area area = null;
            Area aArea = comLogic.findAreaById(outletVo.getAreaId());
            if(aArea!=null && Checker.isNotEmpty(aArea.getTreePath())){
            	//需要将改门店和其所在的区域关联到商品中
        		String[] treePtah = aArea.getTreePath().split(",");
                if(treePtah.length==3){//门店所属的areaId为区
                    area = aArea;
                    city = comLogic.findAreaById(Long.valueOf(treePtah[1]));
                } else if(treePtah.length==2){//门店所属的areaId为市
                    city = aArea;
                }
                
                if(city!=null){
                	//门店信息
                	ProductOutlet po = new ProductOutlet();
                    po.setAddress(outletVo.getAddress());
                    po.setOutletId(outletVo.getOutletId());
                    po.setOutletName(outletVo.getOutletName());
                    po.setPhone(outletVo.getPhone());
                    po.setAreaId(outletVo.getAreaId());
                    
                    /***********整个地区信息结构*****/
                    List<ProductCityArea> cityAreas = pd.getCityAreas();
                    if(cityAreas==null){
                    	cityAreas =  new ArrayList<ProductCityArea>();
                    } 
                    boolean isFindArea = false;
                    for(ProductCityArea opca : cityAreas){
                    	if(city.getId().longValue() == opca.getCityId()){
                    		isFindArea = true;
                    		if(area!=null){
                    			if(opca.getCountys()!=null){
                    				boolean isContain = false;
                    				for(ProductArea opa : opca.getCountys()){
                    					if(area.getId().longValue() == opa.getAreaId()){
                    						isContain = true;
                    						break;
                    					}
                    				}
                    				if(!isContain){
                    					ProductArea pa = new ProductArea();
                                        pa.setAreaId(area.getId());
                                        pa.setAreaName(area.getName());
                                        opca.getCountys().add(pa);
                    				}
                        		} else {
                        			List<ProductArea> countys = new ArrayList<ProductArea>();
                                	ProductArea pa = new ProductArea();
                                    pa.setAreaId(area.getId());
                                    pa.setAreaName(area.getName());
                                    countys.add(pa);
                                    opca.setCountys(countys);//城市下的区
                        		}
                    		}
                    		break;
                    	}
                    }
                    if(!isFindArea){
                    	//城市 区
                    	ProductCityArea pca = new ProductCityArea();
                        pca.setCityId(city.getId());
                        pca.setCityName(city.getName());
                    	//城市下的区
                        if(area!=null){
                        	List<ProductArea> countys = new ArrayList<ProductArea>();
                        	ProductArea pa = new ProductArea();
                            pa.setAreaId(area.getId());
                            pa.setAreaName(area.getName());
                            countys.add(pa);
                            pca.setCountys(countys);//城市下的区
                        }
                        cityAreas.add(pca);
                    }
                    if(cityAreas.size()>0){
                    	DBObject valueObj1 = new BasicDBObject();
                    	valueObj1.put("city_areas", JSON.toJSON(cityAreas));
                    	areaSuccess = manager.updateMulti(valueObj1, whereObj, MongoTableName.CB_PRODUCT_DETAIL, "$set");
                    }
                    /***********整个地区信息结构 end*****/
                    
                    /***********门店信息结构*****/
                    List<ProductCityOutlet> orgOutlets = pd.getOrgOutlets();
                    if(orgOutlets==null){
                    	orgOutlets = new ArrayList<ProductCityOutlet>();
                    }
                    boolean isFindOutlet = false;
                    for(ProductCityOutlet opco : orgOutlets){
                		if(city.getId().longValue() == opco.getCityId()){
                			isFindOutlet = true;
                			if(opco.getOrgOutlets()!=null){
                				boolean isContain = false;
                				for(ProductOutlet opo : opco.getOrgOutlets()){
                					if(po.getOutletId().equals(opo.getOutletId())){
                						isContain = true;
                						break;
                					}
                				}
                				if(!isContain){
                					opco.getOrgOutlets().add(po);
                				}
                			} else {
                				List<ProductOutlet> outlets = new ArrayList<ProductOutlet>();
                                outlets.add(po);
                                opco.setOrgOutlets(outlets);
                			}
                		}
                	}
                    if(!isFindOutlet){
                    	//城市 门店
                        ProductCityOutlet pco= new ProductCityOutlet();
                        pco.setCityId(city.getId());
                        pco.setCityName(city.getName());
                    	//门店
                		List<ProductOutlet> outlets = new ArrayList<ProductOutlet>();
                        outlets.add(po);
                        pco.setOrgOutlets(outlets);
                        orgOutlets.add(pco);
                    }
                    if(orgOutlets.size()>0){
                    	DBObject valueObj1 = new BasicDBObject();
                    	valueObj1.put("org_outlets", JSON.toJSON(orgOutlets));
                    	outletSuccess = manager.updateMulti(valueObj1, whereObj, MongoTableName.CB_PRODUCT_DETAIL, "$set");
                    }
                    /***********门店信息结构 end*****/
                }
            }
        } else {
        	areaSuccess=0;
        	outletSuccess=0;
        }
        return areaSuccess>-1 && outletSuccess>-1;
	}
    
	/**
	 * 删除门店及该门店所在地区和商品的关联关系
	 * @param outletId
	 */
	private boolean deleteProductOutletRelation(String outletId){
		int areaSuccess = -1;
		int outletSuccess = -1;
		
		DBObject whereObj = new BasicDBObject();
		whereObj.put("product_type", ProductType.group.getCode());
        whereObj.put("is_marketable", new BasicDBObject(QueryOperators.NE, ProductStatus.isDeleted.getCode()));
        whereObj.put("org_outlets.org_outlets.outlet_id", outletId);
        ProductDetail pd = manager.findOne(whereObj, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
        if(pd!=null){
        	ProductOutlet po = null;
        	List<ProductOutlet> nopos = null;
        	/***********门店信息结构*****/
            List<ProductCityOutlet> orgOutlets = new ArrayList<ProductCityOutlet>();
            if(pd.getOrgOutlets()!=null){
            	for(ProductCityOutlet opco : pd.getOrgOutlets()){
            		List<ProductOutlet> opos = new ArrayList<ProductOutlet>();
            		if(opco.getOrgOutlets()!=null){
        				for(ProductOutlet opo : opco.getOrgOutlets()){
        					if(outletId.equals(opo.getOutletId())){
        						po = opo;
        					} else {
        						opos.add(opo);
        					}
        				}
        			} 
            		if(po!=null){
            			if(opos.size()>0){
            				opco.setOrgOutlets(opos);
            				orgOutlets.add(opco);
            				nopos = opos;
            			}
    				} else {
    					orgOutlets.add(opco);
    				}
            	}
            }
            /***********门店信息结构 end*****/
            
            
            /***********整个地区信息结构*****/
            if(po!=null){
            	List<ProductCityArea> cityAreas = new ArrayList<ProductCityArea>();
                if(pd.getCityAreas()!=null){
                	for(ProductCityArea opca : pd.getCityAreas()){
                		List<ProductArea> countys = new ArrayList<ProductArea>();
                		if(opca.getCountys()!=null){
            				for(ProductArea opa : opca.getCountys()){
            					if(po.getAreaId().longValue() == opa.getAreaId()){
            						boolean isContain = false;
            						if(nopos!=null && nopos.size()>0){
            							for(ProductOutlet npo : nopos){
                							if(npo.getAreaId().longValue() == opa.getAreaId()){
                								isContain = true;
                								break;
                							}
                						}
            						}
            						if(isContain){
            							countys.add(opa);
            						}
            					} else {
            						countys.add(opa);
            					}
            				}
            				
                		}
                		if(countys.size()>0){
                			opca.setCountys(countys);
                			cityAreas.add(opca);
                		}
                	}
                } 
                DBObject valueObj2 = new BasicDBObject();
                if(cityAreas.size()>0){
                	valueObj2.put("city_areas", JSON.toJSON(cityAreas));
                	areaSuccess = manager.updateMulti(valueObj2, whereObj, MongoTableName.CB_PRODUCT_DETAIL, "$set");
                } else {
                	DBObject w1 = new BasicDBObject();
                    w1.put("city_areas", 1);
                    valueObj2.put("$unset", w1);
                    areaSuccess = manager.updateMulti(valueObj2, whereObj, MongoTableName.CB_PRODUCT_DETAIL, null);
                }
            }
            /***********整个地区信息结构 end*****/
            
            /***********更新mongo门店信息结构*****/
            DBObject valueObj1 = new BasicDBObject();
            if(orgOutlets.size()>0){
            	//set
            	valueObj1.put("org_outlets", JSON.toJSON(orgOutlets));
            	outletSuccess = manager.updateMulti(valueObj1, whereObj, MongoTableName.CB_PRODUCT_DETAIL, "$set");
            } else {
            	DBObject w1 = new BasicDBObject();
                w1.put("org_outlets", 1);
                valueObj1.put("$unset", w1);
                outletSuccess = manager.updateMulti(valueObj1, whereObj, MongoTableName.CB_PRODUCT_DETAIL, null);
            }
            /***********更新mongo门店信息结构 end*****/
        } else {
        	areaSuccess=0;
        	outletSuccess=0;
        }
        return areaSuccess>-1 && outletSuccess>-1;
	}


	@Override
	public void updateHisGroupGoodsOutlets() {
		DBObject whereObj = new BasicDBObject();
        whereObj.put("product_type", ProductType.group.getCode());
        whereObj.put("is_marketable", new BasicDBObject(QueryOperators.NE, ProductStatus.isDeleted.getCode()));
        whereObj.put("org_outlets.0", new BasicDBObject(QueryOperators.EXISTS, false));
		@SuppressWarnings("unchecked")
		List<String> merchantIds = (List<String>) manager.distinct(MongoTableName.CB_PRODUCT_DETAIL, "merchant_id", whereObj);
		if(merchantIds!=null && merchantIds.size()>0){
			Map<Long,Area> areasMap = new HashMap<Long,Area>();
			
			CommonLogic com = new CommonLogicImpl();
			
			for(String merchantId : merchantIds){
				List<Outlet> outlets = com.getOutletListByMerchantIdOrOutletId(merchantId, null);
				if(outlets!=null && outlets.size()>0){
					ProductDetail productDetail = new ProductDetail();
					//将团购商品所属商户下门店所在地区和商品关联
					productDetail = ProductDetailMongoUtil.setAreaOutletToGroupProduct(productDetail, outlets, areasMap);
			        
			        if((productDetail.getCityAreas()!=null && productDetail.getCityAreas().size()>0) || (productDetail.getOrgOutlets()!=null && productDetail.getOrgOutlets().size()>0)){
			        	DBObject where = new BasicDBObject();
				        where.put("product_type", ProductType.group.getCode());
				        where.put("is_marketable", new BasicDBObject(QueryOperators.NE, ProductStatus.isDeleted.getCode()));
				        where.put("merchant_id", merchantId);
				        where.put("org_outlets.0", new BasicDBObject(QueryOperators.EXISTS, false));
				        
			        	DBObject value = new BasicDBObject();
			        	if(productDetail.getCityAreas()!=null && productDetail.getCityAreas().size()>0){
			        		value.put("city_areas", JSON.toJSON(productDetail.getCityAreas()));
			        	}
			        	if(productDetail.getOrgOutlets()!=null && productDetail.getOrgOutlets().size()>0){
			        		value.put("org_outlets", JSON.toJSON(productDetail.getOrgOutlets()));
			        	}
			        	int sucNum = manager.updateMulti(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
			        	LogCvt.debug("merchantId:"+merchantId+",sucNum:"+sucNum);
			        }
				}
			}
		}
	}
	
	@Override
	public Result autoOffShelfProductSaleEnd() {
		Result result = new Result();
		
		//预售 团购商品 上架的 end_time到期的 自动下架
		List<String> types = new ArrayList<String>();
        //指定商品类型销售期到期自动下架(商品类型逗号分隔,比如1,2)1团购,2预售
        String productTypes = GoodsConstants.GOODS_SALE_END_OFF_PRODUCT_TYPE;
        if(productTypes!=null){
        	String[] productTypeArray = productTypes.split(",");
            if(productTypeArray.length>0){
                for(String type : productTypeArray){
                    if(type!=null && !"".equals(type)){
                        types.add(type);
                    }
                }
            }
        }
        if(types.size()>0){
        	SqlSession sqlSession = null;
            try {
            	sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
                ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
                
                Date now = new Date();
                String offStatus = ProductStatus.offShelf.getCode();//下架状态
                
                //redis用到
                Set<String> keys = new HashSet<String>();
                //mongo where
                Set<DBObject> whereParams = new HashSet<DBObject>();
                //mysql value where
                Set<Map<String,Object>> params = new HashSet<Map<String,Object>>();
                
                for(String type : types){
                	Map<String,Object> param = new HashMap<String,Object>();
            		//mysql where
                    param.put("isMarketable", ProductStatus.onShelf.getCode());
                    param.put("endTimeEnd", now);
                    param.put("type", type);
                	List<ProductListInfo> ps = productMapper.findSaleEndProductList(param);
                	if(ps!=null && ps.size()>0){
                		//mysql value
                        param.put("newIsMarketable", offStatus);
                		param.put("downTime", now);
                		params.add(param);
                        
                        //更新redis
                        for(ProductListInfo p : ps){
                        	keys.add(RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(p.getClientId(), p.getMerchantId(), p.getProductId()));
                        	//打印修改日志
                        	addProductOperaLog(p.getProductId(), null,null,"2",offStatus, "update");
                        }
                	}
                	
                	//mongo where
                    DBObject where = new BasicDBObject();
                    where.put("is_marketable", ProductStatus.onShelf.getCode());
            		where.put("end_time", new BasicDBObject(QueryOperators.LTE,now.getTime()));
            		where.put("product_type", type);
            		whereParams.add(where);
                }
                
                /* 更新mysql is_marketable状态和downTime */
                if(params.size()>0){
                	for(Map<String,Object> param : params){
                		productMapper.updateSaleEndProductStatus(param);
                		LogCvt.debug("团购预售商品销售期到期自动下架,mysql where"+param);
                    }
                    sqlSession.commit(true);
                }
                
                /* 更新redis缓存is_marketable状态 */
                if(keys.size()>0){
                    LogCvt.debug("团购预售商品销售期到期自动下架,redis :");
                	for(String key : keys){
                		long count = redis.hset(key, "is_marketable", offStatus);
                		LogCvt.debug("key:"+key+",count:"+count);
                	}
                	LogCvt.debug("团购预售商品销售期到期自动下架,redis end,keys size:"+keys.size());
                }
                
                /* 更新mongo is_marketable状态 */
                if(whereParams.size()>0){
                	//mongo value
                	DBObject value = new BasicDBObject();
                    value.put("is_marketable", offStatus);
                    for(DBObject where : whereParams){
                    	int num = manager.updateMulti(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
                        LogCvt.debug("团购预售商品销售期到期自动下架,mongo where:"+where+",value:"+value+",num:"+num);
                    }
                }
                
                result.setResultCode(ResultCode.success.getCode());
                result.setResultDesc("销售期到期自动下架成功");
            } catch (Exception e) {
            	if(null != sqlSession){
            		sqlSession.rollback(true);  
            	}
                LogCvt.error("销售期到期自动下架失败，原因:" + e.getMessage(),e); 
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("销售期到期自动下架失败");
            } finally { 
                if(null != sqlSession) {
                    sqlSession.close(); 
                }
            }
        } else {
        	result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("没有需要销售期到期自动下架的商品类型");
        }
        return result; 
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
	    					log.setAction("PRODUCTADD");
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


	@Override
	public Set<String> findProductMerchantIds(PageVo pageVo,QueryProductFilterVo filterVo) {
		try {
			int start = (pageVo.getPageNumber()-1)*pageVo.getPageSize();
	        int end = pageVo.getPageNumber()*pageVo.getPageSize()-1;
	        
	        /*设置缓存有效时间*/
	        int unionTime = 5;//交集缓存时间
	        
	        final long areaId = filterVo.getAreaId();
	        final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
            final String productName = ObjectUtils.toString(filterVo.getProductName(), "");
	        
	        //符合商户商品查询条件的商户
	        String groupProductkey = RedisKeyUtil.cbbank_product_merchant_client_id_category_id_product_name(clientId, filterVo.getProductCategoryId(), productName);
	        String groupProductLockkey = RedisKeyUtil.cbbank_product_merchant_lock_client_id_category_id_product_name(clientId, filterVo.getProductCategoryId(), productName);
	        
	        //符合条件的商户门店查询条件的商户
	        String groupMerchantKey = RedisKeyUtil.cbbank_product_merchant_client_id_lat_con(
	        		clientId, areaId, filterVo.getLatitude(), filterVo.getLongitude(), filterVo.getDistance());
	        String groupMerchantLockKey = RedisKeyUtil.cbbank_product_merchant_lock_client_id_lat_con(
	        		clientId, areaId, filterVo.getLatitude(), filterVo.getLongitude(), filterVo.getDistance());
	        
	        //符合条件的商户门店查询条件的商户 和符合商户商品查询条件的商户 的交集key
	        String unionGroupProductkey = RedisKeyUtil.cbbank_group_product_merchant_client_id_category_id_lat_con_product_name_dis(
	        		clientId, areaId, filterVo.getProductCategoryId(), filterVo.getLatitude(), filterVo.getLongitude(), productName, filterVo.getDistance());
	        String unionGroupProductLockkey = RedisKeyUtil.cbbank_group_product_merchant_lock_client_id_category_id_lat_con_product_name_dis(
	        		clientId, areaId, filterVo.getProductCategoryId(), filterVo.getLatitude(), filterVo.getLongitude(), productName, filterVo.getDistance());
	        
	        if(filterVo.getLongitude()>0.0 || filterVo.getLatitude()>0.0){//上传了纬度
	        	unionTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_LAT_CON_LOCK_TIME);
	        } else if(areaId>0){//没上传纬度按区
	        } else {
	            return null;
	        }
	        
	        //商户门店缓存
	        this.setH5GroupProductMerchantOutRedis(groupMerchantKey, groupMerchantLockKey, pageVo, filterVo);
	        
	        //商品所属的商户缓存
	        this.setH5GroupProductMerchantIdRedis(groupProductkey, groupProductLockkey, pageVo, filterVo);
	        
	        LogCvt.debug("个人h5查询特惠商品商户门店的商户和商品商户的交集key:unionGroupProductkey[" + unionGroupProductkey + "],unionGroupProductLockkey["+unionGroupProductLockkey+ "]");
	        long unionLockResult = ProductRedis.setLock(unionGroupProductLockkey, unionTime);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
	        if(unionLockResult == 1) {//key不存在   此次设置成功   说明原来没设置过这个值 需要查询符合条件的商户门店和查询全部的符合条件的商品(从mongo查，每个商户只需要2条特惠商品)
	            //求交集
	            ProductRedis.zsetinterstore(unionGroupProductkey, groupMerchantKey, groupProductkey, unionTime);
	            LogCvt.debug("个人h5查询特惠商品交集商户id:"+JSON.toJSONString(ProductRedis.get_cbbank_product_merchant_h5_redis(unionGroupProductkey, 0, -1)));
	        }
	        Set<String> merchatIds = ProductRedis.get_cbbank_product_merchant_h5_redis(unionGroupProductkey, start, end);
	                
	        if(merchatIds==null || merchatIds.size()==0){
	        	//求交集
	        	LogCvt.debug("个人h5查询特惠商品交集商户id为空重新求交集商户id");
	            ProductRedis.zsetinterstore(unionGroupProductkey, groupMerchantKey, groupProductkey, unionTime);
	            ProductRedis.expire(unionGroupProductLockkey, unionTime);
	            
	            LogCvt.debug("个人h5查询特惠商品交集商户id:"+JSON.toJSONString(ProductRedis.get_cbbank_product_merchant_h5_redis(unionGroupProductkey, 0, -1)));
	            merchatIds = ProductRedis.get_cbbank_product_merchant_h5_redis(unionGroupProductkey, start, end);
	        } else {
	        	long i = ProductRedis.unionMerchantId(groupProductkey, groupMerchantKey);
	        	Set<String> mids = ProductRedis.get_cbbank_product_merchant_h5_redis(unionGroupProductkey, 0, -1);
	        	if(mids!=null && mids.size()!=i){
	        		//求交集
	            	LogCvt.debug("个人h5查询特惠商品交集商户id数量和缓存中的数量不一致重新求交集商户id");
	                ProductRedis.zsetinterstore(unionGroupProductkey, groupMerchantKey, groupProductkey, unionTime);
	                ProductRedis.expire(unionGroupProductLockkey, unionTime);
	                
	                LogCvt.debug("个人h5查询特惠商品交集商户id:"+JSON.toJSONString(ProductRedis.get_cbbank_product_merchant_h5_redis(unionGroupProductkey, 0, -1)));
	                merchatIds = ProductRedis.get_cbbank_product_merchant_h5_redis(unionGroupProductkey, start, end);
	        	}
	        }
	        //异步判断最近一小时是否有该查询条件的新加商品
	        this.queryNewGroupPoducts(filterVo, pageVo);
	        
	        return merchatIds;
		} catch (Exception e) {
            LogCvt.error("有商品名称或商品分类查询条件的特惠商品推荐列表，特惠商品列表 查询门店信息和门店下商品信息失败，原因:" + e.getMessage(),e);
        }
		return null;
	}
	
	/**
     * 商户门店缓存
     * @param groupMerchantKey
     * @param groupMerchantLockKey
     * @param pageVo
     * @param filterVo
     */
    private void setH5GroupProductMerchantOutRedis(String groupMerchantKey, String groupMerchantLockKey, PageVo pageVo, QueryProductFilterVo filterVo){
    	try {
    		/*设置缓存有效时间*/
            int merchantTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_CHANGE_LOCK_TIME);//商户门店缓存时间 团购商品查询条件经常变缓存有效时间,5代表5分钟
            
        	if(filterVo.getLongitude()>0.0 || filterVo.getLatitude()>0.0){//上传了纬度
            	merchantTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_LAT_CON_LOCK_TIME);//特惠商品查询根据经纬度条件查询缓存有效时间,2代表2分钟
            } else if(filterVo.getAreaId()>0){//没上传纬度按区
                merchantTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_STEADY_LOCK_TIME);//团购商品查询条件不常变缓存有效时间,30代表30分钟
            } else {
                return;
            }
        	
        	//有商品名称和商品分类查询条件 需要查全部的门店商户信息
            int skip = -1;
        	
        	LogCvt.debug("个人h5查询特惠商品的商户门店的商户key:groupMerchantKey[" + groupMerchantKey + "],groupMerchantLockKey["+groupMerchantLockKey+ "]");
            long groupMerchantLockResult = ProductRedis.setLock(groupMerchantLockKey, merchantTime);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
            if(groupMerchantLockResult == 1) {// key不存在   此次设置成功   说明原来没设置过这个值   需要查询全部的商户门店，调用商户模块的门店接口
                LogCvt.debug("个人h5查询特惠商品商户门店groupMerchantKey[" + groupMerchantKey + "],groupMerchantLockKey["+groupMerchantLockKey+ "]是第一次查询, 查询全部的商户门店，调用门店接口");
                long startTime = System.currentTimeMillis();
                LogCvt.debug("数据[开始...]时间戳=" + startTime);
                
                MerchantLogic merchantLogic  = new MerchantLogicImpl();
                OutletDetailSimplePageVoRes outletPageVo = merchantLogic.queryMerchantOutlets(filterVo, pageVo, skip);
                
                //商户门店信息
                List<OutletDetailSimpleVo> outletDetailSimpleVos = outletPageVo!=null?outletPageVo.getOutletDetailSimpleVoList():null;
                if(outletDetailSimpleVos!=null && outletDetailSimpleVos.size()>0){
                    //将商户id放redis里面
                    ProductRedis.set_cbbank_product_merchant_h5_redis(groupMerchantKey, outletDetailSimpleVos, merchantTime);
                    //将商户门店信息放redis里面
                    ProductRedis.set_cbbank_product_merchant_outlet_h5_redis(groupMerchantKey, outletDetailSimpleVos, merchantTime);
                    LogCvt.debug("zsetMerchantIds:"+JSON.toJSONString(ProductRedis.get_cbbank_product_merchant_h5_redis(groupMerchantKey, 0, -1)));
                }
                long endTime = System.currentTimeMillis();
                LogCvt.debug("个人h5查询特惠商品商户门店数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
            }
    	} catch (Exception e) {
            LogCvt.error("有个人h5查询特惠商品商户门店缓存失败，原因:" + e.getMessage(),e);
        }
    	
    }
    
    /**
     * 商品所属的商户缓存
     * @param groupProductkey
     * @param groupProductLockkey
     * @param pageVo
     * @param filterVo
     */
    private void setH5GroupProductMerchantIdRedis(final String groupProductkey, final String groupProductLockkey, final PageVo pageVo, final QueryProductFilterVo filterVo){
    	try{
    		final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
            final String productName = ObjectUtils.toString(filterVo.getProductName(), "");
            
    		//符合商户商品查询条件的商户缓存时间
            int groupProductTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_STEADY_LOCK_TIME);//符合条件的商户门店商品缓存时间 30代表30分钟
            if(Checker.isNotEmpty(productName)){
            	groupProductTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_CHANGE_LOCK_TIME);//团购商品查询条件经常变缓存有效时间,5代表5分钟
            }
            
    		LogCvt.debug("个人h5查询特惠商品的商品的商户的key:groupProductkey[" + groupProductkey + "],groupProductLockkey["+groupProductLockkey+ "]");
            long groupProductLockResult = ProductRedis.setLock(groupProductLockkey, groupProductTime);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
            if(groupProductLockResult == 1) {//key不存在   此次设置成功   说明原来没设置过这个值 查询全部的符合条件的商品(从mongo查，每个商户只需要2条特惠商品)
                //根据商户门店信息查询符合条件的商品
                long startTime = System.currentTimeMillis();
                LogCvt.debug("个人h5查询特惠商品商户id查询数据[开始...]时间戳=" + startTime);
                ProductDetialMongo productDetialMongo = new ProductDetialMongo();
                ProductFilter product = new ProductFilter();
                product.setClientId(clientId);
                product.setName(productName);
                product.setFullName(productName);
                product.setCategoryId(filterVo.getProductCategoryId());
                List<String> productMerchantIds = productDetialMongo.queryGroupProductMercantIds(product);
                if(productMerchantIds!=null && productMerchantIds.size()>0){
                	ProductRedis.set_cbbank_group_product_merchant_h5_redis(groupProductkey, productMerchantIds, groupProductTime);
                    LogCvt.debug("zsetGrouProductMerchantIds:"+JSON.toJSONString(productMerchantIds));
                }
                long endTime = System.currentTimeMillis();
                LogCvt.debug("个人h5查询特惠商品商户id查询数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                
            }
    	} catch (Exception e) {
            LogCvt.error("有个人h5查询特惠商品商品所属商户缓存失败，原因:" + e.getMessage(),e);
        }
    }
    
    /**
     * 异步判断最近一小时是否有该查询条件的新加商品
     * @param filterVo
     * @param pageVo
     */
    private void queryNewGroupPoducts(final QueryProductFilterVo filterVo, final PageVo pageVo) {
        FroadThreadPool.execute(new Runnable() { // 异步塞缓存
            @Override
            public void run() {
            	try {
            		final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
                    final String productName = ObjectUtils.toString(filterVo.getProductName(), "");
                    
            		//符合商户商品查询条件的商户
                    String groupProductkey = RedisKeyUtil.cbbank_product_merchant_client_id_category_id_product_name(clientId, filterVo.getProductCategoryId(), productName);
                    String groupProductLockkey = RedisKeyUtil.cbbank_product_merchant_lock_client_id_category_id_product_name(clientId, filterVo.getProductCategoryId(), productName);
                    
                    //数据库中最近一小时新加的上架的团购商品所属的商户id
                    List<String> productMerchantIds = findGroupProductMerchantIds(filterVo);
                    if(productMerchantIds!=null && productMerchantIds.size()>0){
                    	//redis中符合条件的商品所属的商户id
                        Map<String,String> productMerchantIdsMap = redis.getMap(groupProductkey);
                        if(productMerchantIdsMap!=null && productMerchantIdsMap.size()>0){
                        	for(String merchantId : productMerchantIds){
            					if(productMerchantIdsMap.get(merchantId)==null){
            						//刷商品所属商户id redis
            						redis.del(groupProductkey);
            						redis.del(groupProductLockkey);
            						setH5GroupProductMerchantIdRedis(groupProductkey, groupProductLockkey, pageVo, filterVo);
            						break;
            					}
            				}
                        } else {
                        	//刷商品所属商户id redis
                        	redis.del(groupProductkey);
    						redis.del(groupProductLockkey);
                        	setH5GroupProductMerchantIdRedis(groupProductkey, groupProductLockkey, pageVo, filterVo);
                        }
                    }
            	} catch (Exception e) {
            		LogCvt.error("异步判断最近一小时是否有该查询条件的新加商品失败，原因:" + e.getMessage(),e);
            	}
            }
        });
    }
	
	//查询最近一小时是否有新加的上架的商品
	private List<String> findGroupProductMerchantIds(QueryProductFilterVo filterVo) {
        SqlSession sqlSession = null;
        try{
        	final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
            final String productName = ObjectUtils.toString(filterVo.getProductName(), "");
            
        	Map<String,Object> param = new HashMap<String,Object>();
        	if(clientId!=null && !"".equals(clientId)){
        		param.put("clientId", clientId);
            }
        	param.put("type", ProductType.group.getCode());
        	param.put("isMarketable", ProductStatus.onShelf.getCode());//已经上架的查询出来
        	if(productName!=null && !"".equals(productName)){
                param.put("name", productName);
                param.put("fullName", productName);
            }
		    //商品分类查询条件
            if(clientId!=null && !"".equals(clientId) && filterVo.getProductCategoryId()>0){
            	CommonLogic comLogic = new CommonLogicImpl();
            	ProductCategory pc = comLogic.findCategoryById(clientId, filterVo.getProductCategoryId());
            	if(pc!=null && Checker.isNotEmpty(pc.getTreePath())){
            		param.put("categoryTreePath", pc.getTreePath());
            	}
            }
            param.put("endTimeStart", new Date()); // 结束时间大于等于当前时间,过了团购期商品不显示
            
            param.put("createTimeStart", DateUtil.minuteAdd(new Date(), -60)); //异步判断最近一小时是否有该查询条件的新加商品
	        
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
          
            List<String> merchantIds = productMapper.findProductMerchantIds(param);
            return merchantIds;
        } catch (Exception e) { 
            LogCvt.error("查询最近一小时是否有新加的上架的商品失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        }
		return null;
	}


	@Override
	public ResultBean addGoods(GoodsInfoVo productVo, String platType) {
		ResultBean resultBean = null;
		CommonLogic comLogic = new CommonLogicImpl();
		//判断供货商是否存在及是否已失效
		Map<String,String> providerMap = comLogic.getProviderRedis(productVo.getMerchantId());
		if(Checker.isEmpty(providerMap)){
            resultBean = new ResultBean(ResultCode.failed,"供货商不存在不能新增精品商城商品");
            return resultBean;
        } else if(Checker.isEmpty(providerMap.get("status")) || "0".equals(providerMap.get("status"))){// 状态: 0禁用, 1启用
            resultBean = new ResultBean(ResultCode.failed,providerMap.get("merchant_name")+"供货商已禁用不能新增精品商城商品");
            return resultBean;
        }
        Date now = new Date();
        
        Product product = generateGoods(productVo);
        /** 平台来源*/
        product.setPlatType(platType);
        /** 设置创建时间为当前值*/
        product.setCreateTime(now);
        /** 供货商名称 */
        product.setMerchantName(providerMap.get("merchant_name"));
        //初始化固定字段信息
        initPtoduct(product, productVo);
        //设置category_tree_path商品分类tree_path
        if(productVo.getCategoryId()>0){
            ProductCategory productCategory = comLogic.findCategoryById(productVo.getClientId(), productVo.getCategoryId());
            if(Checker.isNotEmpty(productCategory)){
                product.setCategoryTreePath(productCategory.getTreePath());
            }
        }
        ProductDetail productDetail = generateProductDetail(product,productVo);
        //初始化mongog的几个固定的字段信息
        initProductDetail(product, productDetail);
        
        /**********************操作MySQL数据库**********************/
        boolean sqlFlag = false;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            productMapper.addGoods(product);//基础信息保存到mysql
            sqlSession.commit(true);
            sqlFlag = true;
        } catch (Exception e) { 
        	if(null != sqlSession){
        		sqlSession.rollback(true); 
        	}
            LogCvt.error("添加精品商城商品mysql失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        
        /**********************操作Redis缓存**********************/
        setBoutiqueGoodsRedis(product);
        
        /**********************操作Mongo，添加商品详情**********************/
        boolean mongoFlag = false;
        try{
        	mongoFlag = manager.add(productDetail, MongoTableName.CB_PRODUCT_DETAIL) > -1;// 向mongodb插入数据
        } catch (Exception e) { 
            LogCvt.error("添加精品商城商品Mongo失败，原因:" + e.getMessage(),e); 
        }
     
        if(sqlFlag==true && mongoFlag==true){
            resultBean = new ResultBean(ResultCode.success,"添加精品商城商品成功",product.getProductId());
        } else if(sqlFlag==false && mongoFlag==false){
            resultBean = new ResultBean(ResultCode.failed,"添加精品商城商品失败");
        } else {
            resultBean = new ResultBean(ResultCode.success,"添加精品商城商品成功,"+(sqlFlag==false?"mysql添加精品商城商品失败":"")+(mongoFlag==false?"mongo添加精品商城商品失败":""),product.getProductId());
        }
        
        
        /**********************打印新增日志**********************/
        addProductOperaLog(product.getProductId(), null,null,null,null, "add");
        
		return resultBean;
	}


	/**
	 * 生成mysql的精品商城商品基础信息
	 * @param productVo
	 * @return
	 */
	private Product generateGoods(GoodsInfoVo productVo){
		Product product = new Product();
        /** 供货商id*/
        product.setMerchantId(productVo.getMerchantId());
    	/** 商品简称 */
    	product.setName(productVo.getName());   
        /** 商品全称 */
    	product.setFullName(productVo.getFullName());
    	/** 副标题 */
    	product.setBriefIntroduction(productVo.getBriefIntroduction());
        /** 搜索关键词 */
    	product.setSeoKeyWords(productVo.getSeoKeyWords());
    	 /** 库存数量 */
        product.setStore(productVo.getStore());
        /** 商品介绍 */
        product.setIntroduction(productVo.getIntroduction());
        /** 商品购买须知 */
        product.setBuyKnow(productVo.getBuyKnow());
        /** 售后说明*/
        product.setAfterShop(productVo.getAfterShop());
    	//复制基础信息 double类型的价格乘以1000转换成int型存储在数据里
        /** 市场价 */
        product.setMarketPrice(Arith.mul(productVo.getMarketPrice(),GoodsConstants.DOUBLE_INTGER_CHANGE));
        /** 商城价 */ 
        product.setPrice(Arith.mul(productVo.getPrice(),GoodsConstants.DOUBLE_INTGER_CHANGE));
        /** VIP价 */ 
        if(productVo.getVipPrice()>0.0){
        	product.setVipPrice(Arith.mul(productVo.getVipPrice(),GoodsConstants.DOUBLE_INTGER_CHANGE));
        }
        /** 预计发货时间 */
        if(productVo.getDeliveryTime()>0){
        	product.setDeliveryTime(new Date(productVo.getDeliveryTime()));
        }
        
        StringBuilder goodFlag = new StringBuilder();
        /** 是否推荐 */
        if(productVo.isRecommend){
        	product.setIsRecommend(1);
        	goodFlag.append("1");
        } else {
        	product.setIsRecommend(0);
        	goodFlag.append("0");
        }
        /** 是否热销 */
        if(productVo.isHot){
        	product.setIsHot(1);
        	goodFlag.append("1");
        } else {
        	product.setIsHot(0);
        	goodFlag.append("0");
        }
        /** 是否新品 */
        if(productVo.isNew){
        	product.setIsNew(1);
        	goodFlag.append("1");
        } else {
        	product.setIsNew(0);
        	goodFlag.append("0");
        }
        product.setGoodFlag(goodFlag.toString());
        return product;
	}
	
	/**
	 * 新加精品商城商品初始化固定的字段信息
	 * @param product
	 * @param productVo
	 */
	private void initPtoduct(Product product,GoodsInfoVo productVo){
		/** 客户端id*/
        product.setClientId(productVo.getClientId());
        /** 商品类型 "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品,"6":精品商城商品;*/
        product.setType(productVo.getType());
        /** 上下架状态 默认为未上架*/
        product.setIsMarketable(ProductStatus.noShelf.getCode());
        /** 没有运费 */
        product.setDeliveryMoney(0);
        /** 配送 */
        product.setDeliveryOption(DeliveryType.home.getCode());
        //初始化值 为非秒杀
        if(Checker.isEmpty(productVo.getIsSeckill())) {
        	product.setIsSeckill(SeckillFlagEnum.notSeckill.getCode());
        } else {
        	product.setIsSeckill(productVo.getIsSeckill());//秒杀
        }
        product.setIsPoint(false);//是否送积分
        product.setSellCount(0);//新加商品设置销量为0
        product.setPoint(0);//新加商品设置评价为0
        product.setOrderValue(1);//权重
        product.setIsBest(false);
        product.setOrgCode("");
        product.setAuditOrgCode("");
        product.setAuditStartOrgCode("");
        product.setAuditEndOrgCode("");
        product.setAuditState(AuditState.passAudit.getCode());
        //设置商品Id
        product.setProductId(simpleID.nextId());
	}
	
	/**
	 * 生成mongo的精品商城商品信息
	 * @param p
	 * @param productVo
	 * @return
	 */
	private ProductDetail generateProductDetail(Product p, GoodsInfoVo productVo){
        ProductDetail productDetail = new ProductDetail(); 
        
        productDetail.setName(p.getName());
        productDetail.setFullName(p.getFullName());
        productDetail.setBriefIntroduction(p.getBriefIntroduction());
        productDetail.setPrice(p.getPrice());
        productDetail.setMarketPrice(p.getMarketPrice());
        productDetail.setDeliveryTime(p.getDeliveryTime());
        productDetail.setMerchantId(p.getMerchantId());
        productDetail.setMerchantName(p.getMerchantName());
        productDetail.setIsRecommend(p.getIsRecommend());//荐
        productDetail.setIsHot(p.getIsHot());//热
        productDetail.setIsNew(p.getIsNew());// 新
        productDetail.setVipPrice(p.getVipPrice());// VIP价格
        
        /** 商品图片列表 */
        if(productVo.getImages()!=null && productVo.getImages().size()>0){//图片信息
        	List<ProductImage> productImages = new ArrayList<ProductImage>();
        	
            List<ProductImageVo> images = productVo.getImages();
            ProductImage productIamge = null;
            for(ProductImageVo image : images){
                productIamge = new ProductImage();
                ProductBeanUtil.copyProperties(productIamge, image);
                productImages.add(productIamge);
            }
            productDetail.setImageInfo(productImages);
        }
        
        /** 商品分类信息设置 该分类的父分类的id也会保留到mongo里 */
        String treePath = p.getCategoryTreePath();
        List<ProductCategoryInfo> productCategoryInfo = new ArrayList<ProductCategoryInfo>();
        ProductCategoryInfo productCategory = null;
        if(Checker.isNotEmpty(treePath)){
            String[] pcids = treePath.split(" ");
            for(String pcid : pcids){
                if(Checker.isNotEmpty(pcid)){
                	productCategory = new ProductCategoryInfo();
                	productCategory.setProductCategoryId(Long.valueOf(pcid));
                	productCategoryInfo.add(productCategory);
                }
            }
        } else {
        	productCategory = new ProductCategoryInfo();
            productCategory.setProductCategoryId(productVo.getCategoryId());
            productCategoryInfo.add(productCategory);
        }
        productDetail.setProductCategoryInfo(productCategoryInfo);
        
        
        /** 购买限制信息设置 */
        if(productVo.getMax()>0 || productVo.getMaxVip()>0 || productVo.getMin()>0){//vip限购数量 普通限购数量 最低限购限购数量 3个 其中有一个有值代表限购
        	p.setIsLimit(true);
        	productDetail.setIsLimit(1);
        	ProductBuyLimit buyLimit = new ProductBuyLimit();
            if(productVo.getMin()>0){
            	buyLimit.setMin(productVo.getMin());
            }
            if(productVo.getMax()>0){
            	buyLimit.setMax(productVo.getMax());
            }
            if(productVo.getMaxVip()>0){
            	buyLimit.setMaxVip(productVo.getMaxVip());
            }
            productDetail.setBuyLimit(buyLimit);
        } else {
        	productDetail.setIsLimit(0);
        	productDetail.setBuyLimit(null);
        	p.setIsLimit(false);
        }
        return productDetail;
	}

	/**
	 * 初始化mongog的几个固定的字段信息
	 * @param p
	 * @param productDetail
	 */
	private void initProductDetail(Product p, ProductDetail productDetail){
		//基础信息设置
        productDetail.setId(p.getProductId());
        productDetail.setPlatType(p.getPlatType());
        productDetail.setIsSeckill(p.getIsSeckill());// 秒杀
        productDetail.setIsBest(BooleanUtils.toInteger(p.getIsBest(), 1, 0, 0));
        productDetail.setProductType(p.getType());
        productDetail.setClientId(p.getClientId());
        productDetail.setIsMarketable(p.getIsMarketable());
        productDetail.setDeliveryOption(p.getDeliveryOption());
        productDetail.setSellCount(p.getSellCount());
        productDetail.setCreateTime(p.getCreateTime());
        productDetail.setStoreCount(0);//用户收藏数
	}
	
	/**
	 * 精品商城商品redis缓存操作
	 * @param product
	 */
	private boolean setBoutiqueGoodsRedis(Product product){
		try{
        	//库存redis缓存
            LogCvt.debug("精品商城商品库存redis key参数转换:"+product.getClientId()+product.getMerchantId()+ product.getProductId()+product.getStore());
            redis.putString(RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId()), ObjectUtils.toString(product.getStore(), ""));
            
            if(product!=null){
            	Map<String,Object> productInfoMap = new HashMap<String,Object>();
            	productInfoMap.put("product", product);
                
                //商品基本信息redis缓存
                ProductCommonRedis.set_cbbank_product_client_id_merchant_id_product_id(productInfoMap);
            }
            
            return true;
        } catch (Exception e) {
            LogCvt.error("精品商城商品基本信息,库存redis缓存异常：" +e,e);
        }
		return false;
	}

	@Override
	public Result updateGoods(GoodsInfoVo productVo) {
		Result result = new Result();
		
		CommonLogic comLogic = new CommonLogicImpl();
        //判断供货商是否存在及是否已失效
		Map<String,String> providerMap = comLogic.getProviderRedis(productVo.getMerchantId());
		if(Checker.isEmpty(providerMap)){
			result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("供货商不存在不能修改精品商城商品");
            return result;
        } else if(Checker.isEmpty(providerMap.get("status")) || "0".equals(providerMap.get("status"))){// 状态: 0禁用, 1启用
        	result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc(providerMap.get("merchant_name")+"供货商已禁用不能修改精品商城商品");
            return result;
        }
		Product product = generateGoods(productVo);
        if(Checker.isNotEmpty(productVo.getIsSeckill())) {
        	product.setIsSeckill(productVo.getIsSeckill());//秒杀
        }
		/** 供货商名称 */
        product.setMerchantName(providerMap.get("merchant_name"));
        //设置category_tree_path商品分类tree_path
        if(productVo.getCategoryId()>0){
            ProductCategory productCategory = comLogic.findCategoryById(productVo.getClientId(), productVo.getCategoryId());
            if(Checker.isNotEmpty(productCategory)){
                product.setCategoryTreePath(productCategory.getTreePath());
            }
        }
        ProductDetail productDetail = generateProductDetail(product,productVo);
        if(Checker.isNotEmpty(product.getIsSeckill())) {
        	productDetail.setIsSeckill(product.getIsSeckill());//秒杀
        }
        
	    SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            Product pt = new Product();
            pt.setProductId(productVo.getProductId());
            List<Product> ps = productMapper.getProductByProductId(pt);
            if(ps!=null && ps.size()>0){
                Product p = ps.get(0);
                
                if(ProductStatus.onShelf.getCode().equals(p.getIsMarketable())){
                	result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("已上架或者已提交上架精品商城商品不可修改");
                    return result;
                }
                product.setClientId(p.getClientId());
                product.setType(p.getType());
                product.setProductId(productVo.getProductId());
                product.setIsMarketable(p.getIsMarketable());
                /** 没有运费 */
                product.setDeliveryMoney(p.getDeliveryMoney());
                /** 配送 */
                product.setDeliveryOption(p.getDeliveryOption());
                product.setIsPoint(p.getIsPoint());//是否送积分
                product.setSellCount(p.getSellCount());//新加商品设置销量为0
                product.setPoint(p.getPoint());//新加商品设置评价为0
                
                /**********************操作Redis缓存**********************/
                try{
                	if(!p.getMerchantId().equals(product.getMerchantId())){//换供应商了 要把原来的key删除
                		//原来库存redis缓存key
                		String sk = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(product.getClientId(), p.getMerchantId(), product.getProductId());
                		//原来基本信息redis缓存key
                		String pk = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(product.getClientId(), p.getMerchantId(), product.getProductId());
                		redis.del(sk,pk);
                	}
                } catch (Exception e) {
                    LogCvt.error("精品商城商品换供应商了要把原来的redis基本信息,库存key删除缓存异常：" +e,e);
                }
                boolean redisFlag = setBoutiqueGoodsRedis(product);
                
                /**********************操作Mongo，添加商品详情**********************/
                productDetail = updateGoodsDetail(productDetail,product);
                
                /**********************操作MySQL数据库**********************/
                boolean sqlFlag = false;
                try{
                    productMapper.updateGoods(product);
                    sqlSession.commit(true);
                    sqlFlag = true;
                } catch (Exception e) { 
                    if(null != sqlSession){
                    	sqlSession.rollback(true);  
                    }
                    LogCvt.error("修改精品商城商品mysql失败，原因:" + e.getMessage(),e); 
                }
                
                if(redisFlag==true && sqlFlag==true && productDetail!=null){
                    result.setResultCode(ResultCode.success.getCode());
                    result.setResultDesc("精品商城商品修改成功");
                } else if(redisFlag==false && sqlFlag==false && productDetail==null){
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("精品商城商品修改失败");
                } else {
                    result.setResultCode(ResultCode.success.getCode());
                    result.setResultDesc("精品商城商品修改成功"+(sqlFlag==false?"mysql修改失败":"")+(redisFlag==false?"redis修改失败":"")+(productDetail==null?"mongo修改失败":""));
                }
                
                /**********************打印修改日志**********************/
                addProductOperaLog(product.getProductId(), null,null,null,null, "update");
                
                return result;
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("精品商城商品不存在不能修改");
                return result;
            }
        } catch (Exception e) {
            LogCvt.error("修改精品商城商品失败，原因:" + e.getMessage(),e); 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("修改精品商城商品失败");
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
		return result; 
	}
	
	/**
	 * 更新精品商城mongo的商品信息
	 * @param productDetail
	 * @param product
	 * @return
	 */
	private ProductDetail updateGoodsDetail(ProductDetail productDetail,Product product){
		boolean isSuccess = false;
        try{
        	
        	DBObject where = new BasicDBObject();
            where.put("_id", product.getProductId());
            Object whereObject = where;
            
            ProductDetail pd= manager.findOne(where, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            if(pd!=null){
                //修改商品时销量，库存量，创建时间，活动信息 不修改
                productDetail.setPlatType(pd.getPlatType());
                productDetail.setSellCount(pd.getSellCount());
                productDetail.setStoreCount(pd.getStoreCount());
                if(Checker.isEmpty(productDetail.getIsSeckill())) {
                	productDetail.setIsSeckill(pd.getIsSeckill());//秒杀
                }
                productDetail.setIsBest(pd.getIsBest());
                productDetail.setProductType(pd.getProductType());
                productDetail.setClientId(pd.getClientId());
                productDetail.setIsMarketable(pd.getIsMarketable());
                productDetail.setDeliveryOption(pd.getDeliveryOption());
                if(pd.getCreateTime()!=null){
                    productDetail.setCreateTime(pd.getCreateTime());
                }
                productDetail.setActivitiesInfo(pd.getActivitiesInfo());
                
                isSuccess =  manager.update(productDetail, whereObject, MongoTableName.CB_PRODUCT_DETAIL, "$set")> -1;// 向mongodb更新数据
                boolean isUnset = false;
                DBObject valueObj = new BasicDBObject();
                DBObject w1 = new BasicDBObject();
                if(productDetail.getBuyLimit()==null){
                    //删除buy_limit这个字段值
                    w1.put("buy_limit", 1);
                    isUnset = true;
                }
                if(productDetail.getActivitiesInfo()==null || productDetail.getActivitiesInfo().size()==0){//商品活动
                	//删除activities_info这个字段值
                    w1.put("activities_info", 1);
                    isUnset = true;
                }
                if(productDetail.getProductCategoryInfo()==null || productDetail.getProductCategoryInfo().size()==0){// 商品分类
                	//删除product_category_info这个字段值
                    w1.put("product_category_info", 1);
                    isUnset = true;
                }
                if(isUnset){
                	valueObj.put("$unset", w1);
                    manager.update(valueObj, where, MongoTableName.CB_PRODUCT_DETAIL, null);//更新mongo vip_price buy_limit和is_limit
                }
            } else {
            	initProductDetail(product, productDetail);
                isSuccess = manager.add(productDetail, MongoTableName.CB_PRODUCT_DETAIL) > -1;// 向mongodb插入数据
            }
            if(isSuccess){
                return productDetail;
            }
                
        } catch (Exception e) { 
            LogCvt.error("修改精品商城商品mongo失败，原因:" + e.getMessage(),e); 
        } 
        return null;
	}


	@Override
	public Result updateGoodsStatusOn(String productId) {
		Result result = new Result();
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            Product product = new Product();
            product.setProductId(productId);
            List<Product> ps = productMapper.getProductByProductId(product);
            if(ps!=null && ps.size()>0){
                Product p = ps.get(0);
                CommonLogic comLogic = new CommonLogicImpl();
                //判断供货商是否存在及是否已失效
        		Map<String,String> providerMap = comLogic.getProviderRedis(p.getMerchantId());
        		if(Checker.isEmpty(providerMap)){
        			result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("供货商不存在");
                    return result;
                } else if(Checker.isEmpty(providerMap.get("status")) || "0".equals(providerMap.get("status"))){// 状态: 0禁用, 1启用
                	result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc(providerMap.get("merchant_name")+"供货商已禁用");
                    return result;
                }
        		Date now = new Date();
        		
        		product.setIsMarketable(ProductStatus.onShelf.getCode());//上架
        		product.setRackTime(now);
                //更新mysql
                productMapper.updateGoodsStatus(product); 
                sqlSession.commit(true);
                
                /* 更新redis缓存is_marketable */
                String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(p.getClientId(), p.getMerchantId(), p.getProductId());
                redis.hset(key, "is_marketable", product.getIsMarketable()+"");
                
                //更新mongo is_marketable
                DBObject value = new BasicDBObject();
                value.put("is_marketable", product.getIsMarketable());
                value.put("rack_time", now.getTime());
                
                DBObject where = new BasicDBObject();
                where.put("client_id", p.getClientId());
                where.put("_id", p.getProductId());
                manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
                
                //该商品所在查询条件下的key失效
                reflashH5BoutiquePoductsCacheKey(p);
                
                result.setResultCode(ResultCode.success.getCode());
                result.setResultDesc("上架精品商城商品成功");
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("该商品不存在");
                return result;
            }
        } catch (Exception e) {
        	if(null != sqlSession){
        		sqlSession.rollback(true);
        	}
            LogCvt.error("上架精品商城商品 失败，原因:" + e.getMessage(),e); 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("上架精品商城商品失败");
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return result; 
	}


	@Override
	public Result updateGoodsStatusOff(String productId) {
        Result result = new Result();
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            Product product = new Product();
            product.setProductId(productId);
            List<Product> ps = productMapper.getProductByProductId(product);
            if(ps!=null && ps.size()>0){
                Product p = ps.get(0);
                if(!ProductStatus.onShelf.toString().equals(p.getIsMarketable())){
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("上架后的商品才可以操作下架");
                    return result;
                }
        		Date now = new Date();
        		
        		product.setIsMarketable(ProductStatus.offShelf.getCode());//下架
        		product.setDownTime(now);
                //更新mysql
                productMapper.updateGoodsStatus(product); 
                sqlSession.commit(true);
                
                /* 更新redis缓存is_marketable */
                String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(p.getClientId(), p.getMerchantId(), p.getProductId());
                redis.hset(key, "is_marketable", product.getIsMarketable()+"");
                
                //更新mongo is_marketable
                DBObject value = new BasicDBObject();
                value.put("is_marketable", product.getIsMarketable());
                
                DBObject where = new BasicDBObject();
                where.put("client_id", p.getClientId());
                where.put("_id", p.getProductId());
                manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
                
                //该商品所在的查询key失效
                try{
                	String pikey = RedisKeyUtil.cbbank_product_boutique_h5_keys_product_id(p.getProductId());
                    List<String> keys = redis.lrange(pikey, 0, -1);
                    if(keys!=null && keys.size()>0){
                    	for(String k : keys){
                    		ProductRedis.del_key(k);
                    	}
                    }
                } catch (Exception e) {
                    LogCvt.error("保存 每个商品id被缓存过的key,下架时候 让这些缓存过的key失效异常：" +e,e);
                }
                
                result.setResultCode(ResultCode.success.getCode());
                result.setResultDesc("下架精品商城商品成功");
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("该商品不存在");
                return result;
            }
        } catch (Exception e) {
        	if(null != sqlSession){
        		sqlSession.rollback(true);
        	}
            LogCvt.error("下架精品商城商品 失败，原因:" + e.getMessage(),e); 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("下架精品商城商品失败");
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return result;
	}


	@Override
	public List<Product> findGoodsByPage(Page<ProductListInfo> page,
			ProductFilter productFilter) {
		long startTime = System.currentTimeMillis();
		List<Product> list = null;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            if(productFilter.getCategoryId()!=null && productFilter.getCategoryId()>0){
            	CommonLogic common = new CommonLogicImpl();
                ProductCategory pc = common.findCategoryById(productFilter.getClientId(), productFilter.getCategoryId());
                if(Checker.isNotEmpty(pc)){
              	  productFilter.setCategoryTreePath(pc.getTreePath());
                }
            }
            list =  productMapper.findGoodsListByPage(page, productFilter);
            long endTime = System.currentTimeMillis();
            LogCvt.debug("boss查询精品商城商品列表分页查询mapper中findGoodsListByPage:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        }catch (Exception e) {
            LogCvt.error("boss查询精品商城商品列表分页查询失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return list;
	}


	@Override
	public Product getGoodsDetail(String productId) {
	    SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            Product pv = productMapper.getProductDetail(productId);
            return pv;
        } catch (Exception e) {
            LogCvt.error("查询精品商城商品明细失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
		return null;
	}


	@Override
	public List<ProductDetail> queryGoods(PageVo pageVo,
			QueryBoutiqueGoodsFilterVo filterVo, FiledSort fs, int needGoodsNum) throws Exception {
        try{
        	if(needGoodsNum<1){//查询是否有下一页时候只需要 至少有一条就代表有下一页
        		needGoodsNum = pageVo.getPageSize();
        	}
        	//需要查询mongo的end
    		int mend = pageVo.getPageNumber() * pageVo.getPageSize() - 1;

    		final int queryMongodbDataCount = 100; // 每次向mongodb查询的数据量
    		final int index = mend < queryMongodbDataCount ? 1 : (int) ((mend / queryMongodbDataCount) + (mend
    				% queryMongodbDataCount > 0 ? 1 : 0)); // 向mongodb第几次查询queryMongodbDataCount条数据
    		
    		//redis的start end
    		int start = (pageVo.getPageNumber() - 1) * pageVo.getPageSize();
        	int end = start + needGoodsNum - 1;
    		
    		// 排序规则 负数代表降序，整数代表升序
    		StringBuilder sort = new StringBuilder();
    		if(fs!=null && fs.getSortName()!=null){
    			sort.append(fs.getSortName());
    			if (fs.getSortBy() < 0) {
        			sort.append("-desc");
        		} else {
        			sort.append("-asc");
        		}
    		}
            final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
            final long categoryId = filterVo.getProductCategoryId(); // 分类id
            final String productName = ObjectUtils.toString(filterVo.getProductName(), "");
            final String goodFlag = filterVo.getGoodFlag()==null?"":filterVo.getGoodFlag();//推荐列表,热销列表,新品列表,秒杀
            
            /*个人h5精品商城商品*/
            /*设置缓存有效时间*/
            int time = 5;
    		final String key = RedisKeyUtil.cbbank_product_boutique_h5_index_client_id_category_id_product_name_good_flag_sort(index, clientId, categoryId, productName, goodFlag, sort.toString());
            final String lockKey = RedisKeyUtil.cbbank_product_boutique_h5_lock_index_client_id_category_id_product_name_good_flag_sort(index, clientId, categoryId, productName, goodFlag, sort.toString());
            LogCvt.debug("个人h5精品商城商品查询,key:" + key+",lockKey:"+lockKey);
            
            DBObject query = new BasicDBObject();//查询条件
            query.put("product_type", ProductType.boutique.getCode());//精品商城商品
            query.put("is_marketable", ProductStatus.onShelf.getCode());//已经上架的查询出来
            if (clientId != null && !"".equals(clientId)) {
            	query.put("client_id", clientId);
            }
            if(categoryId>0){
            	query.put("product_category_info.product_category_id", categoryId);//商品分类查询条件
            }
            if (productName != null && !"".equals(productName)) {//商品名称模糊查询
    			StringBuilder regexStr = new StringBuilder(".*");
    			regexStr.append(productName).append(".*");
    			Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
                
                List<DBObject> orArray = new ArrayList<DBObject>();
                orArray.add(new BasicDBObject("name", ""));
                orArray.add(new BasicDBObject("full_name", ""));
                
                
                JSONArray jsonOrArray = (JSONArray)JSON.toJSON(orArray);
                for(Object ob : jsonOrArray){
                    JSONObject jb = (JSONObject)ob;
                    for (String nkey : jb.keySet()) {
                        if(nkey.equals("name")){
                            BasicDBObject nameLike = new BasicDBObject();
                            nameLike.append("$regex", pattern);
                            jb.put(nkey, nameLike);
                        } else if(nkey.equals("full_name")){
                            BasicDBObject fullNameLike = new BasicDBObject();
                            fullNameLike.append("$regex", pattern);
                            jb.put(nkey, fullNameLike);
                        }
                    }
                }
                query.put("$or", jsonOrArray);
                
                time = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_CHANGE_LOCK_TIME);//商品查询条件经常变缓存有效时间,5代表5分钟
            } else {
            	time = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_STEADY_LOCK_TIME);//商品查询条件不常变缓存有效时间,30代表30分钟
            }
            //'1'推荐;'2'热销;'3'新品;'4'秒杀
            if("1".equals(goodFlag)){//特惠推荐列表:点击后进入商品列表页，展示所有带有“推荐“标签的商品
            	query.put("is_recommend", 1);
            } else if("2".equals(goodFlag)){//热销列表:点击后进入商品列表页，展示所有带有“热销”标签的精品商城商品
            	query.put("is_hot", 1);
            } else if("3".equals(goodFlag)){//新品列表:点击后进入商品列表页，展示所有带有“新”标签的精品商城商品
            	query.put("is_new", 1);
            } else if("4".equals(goodFlag)){//秒杀列表:点击后进入商品列表页，展示所有带有“秒杀”标签的精品商城商品
            	query.put("is_seckill", "1");
            }
            if(fs!=null && ProductQuerySort.recommend.getCode().equals(fs.getSortName())){//推荐优先  精品商城个人H5用到 我的VIP页面里的为您推荐用到
                query.put("vip_price", new BasicDBObject(QueryOperators.GT,0));
                query.put("price", new BasicDBObject(QueryOperators.NE,0));
        	}
            
            List<ProductDetail> pds = null;
            long lockResult = ProductRedis.setLock(lockKey, time);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
            if(lockResult == 1) {// key不存在   此次设置成功   说明原来没设置过这个值   需要查询mongodb  然后  起多线程查queryMongodbDataCount条数据添加到redis
                LogCvt.debug("个人h5查询精品商城商品key[" + key + "是第一次查询, 查询mongodb");
                long startTime = System.currentTimeMillis();
                LogCvt.debug("个人h5查询精品商城商品mongodb查询,currentPage" + pageVo.getPageNumber() + ",pageSize" + pageVo.getPageSize() +", needGoodsNum:"+needGoodsNum+ "数据[开始...]时间戳=" + startTime);
                pds = productDetialMongo.queryBoutiqueProducts(query, pageVo.getPageNumber(), pageVo.getPageSize(), needGoodsNum, fs);
                long endTime = System.currentTimeMillis();
                LogCvt.debug("个人h5查询精品商城商品mongodb查询,currentPage" + pageVo.getPageNumber() + ",pageSize" + pageVo.getPageSize() +", needGoodsNum:"+needGoodsNum+ "数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                if(pds!=null && pds.size()>0){
                	setH5BoutiquePoductsCache(index, queryMongodbDataCount, query, fs, clientId, lockKey, key, time);//异步将index，100条设置到缓存中
                }
            } else {
                // key已经存在   本次设置不成功   说明不是第一次查询  先看redis有没有数据  如有则返回  无则查mongodb
                LogCvt.debug("个人h5查询团购商品key[" + key + "非首次查询, 先看redis有没有数据  如有则返回  无则查mongodb");
                long count = redis.llen(key); // 获取缓存key中有多少条数据
                if(count >= (end+1)) { // 缓存中的数据  足够本次查询的数据
                    pds = ProductRedis.get_cbbank_boutique_product_h5_redis(key, start, end);
                } else {// 缓存中的数据  不够本次查询的数据  需要查询mongo数据库 pageSize条数据返回给前端
                	int totalCount = manager.getCount(query,  MongoTableName.CB_PRODUCT_DETAIL);//momgo总条数
                	if(totalCount > count) { //momgo总条数大于缓存中的数据量 需要查mongo 并且让redis缓存失效
                		if(needGoodsNum==pageVo.getPageSize()){//如果是查询是否有下一页的不需要让redis缓存失效
                			//让redis缓存失效
                    		ProductRedis.del_key(key);
                    		ProductRedis.del_key(lockKey);
                		}
                		
                		//momgo总条数大于缓存中的数据量 需要查mongo
                		LogCvt.debug("个人h5查询精品商城商品直接查询mongodb");
                        long startTime = System.currentTimeMillis();
                        LogCvt.debug("个人h5查询精品商城商品mongodb查询,currentPage" + pageVo.getPageNumber() + ",pageSize" + pageVo.getPageSize() +", needGoodsNum:"+needGoodsNum+ "数据[开始...]时间戳=" + startTime);
                        pds = productDetialMongo.queryBoutiqueProducts(query, pageVo.getPageNumber(), pageVo.getPageSize(), needGoodsNum, fs);
                        long endTime = System.currentTimeMillis();
                        LogCvt.debug("个人h5查询精品商城商品mongodb查询,currentPage" + pageVo.getPageNumber() + ",pageSize" + pageVo.getPageSize() +", needGoodsNum:"+needGoodsNum+ "数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                		
                    } else {
                    	pds = ProductRedis.get_cbbank_boutique_product_h5_redis(key, start, end);
                    }
                }
            }
            return pds;
        } catch(Exception e){
            LogCvt.error("个人h5查询精品商城商品异常，原因:" + e.getMessage(), e); 
        }
        return null;
	}
	
	private void setH5BoutiquePoductsCache(final int index, final int queryMongodbDataCount, final DBObject dbObject , final FiledSort fs, final String clientId, final String lockKey, final String key, final int time) {
        FroadThreadPool.execute(new Runnable() { // 异步塞缓存
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                LogCvt.debug("个人h5查询精品商城商品异步[ThreadPool]设置redis缓存,currentPage" + index + ",pageSize" + queryMongodbDataCount + "数据[开始...]时间戳=" + startTime);
                try {
                	List<ProductDetail>  productDetails = productDetialMongo.queryBoutiqueProducts(dbObject, index, queryMongodbDataCount, queryMongodbDataCount, fs);
                    if(productDetails!=null && productDetails.size()>0){
                        ProductRedis.set_cbbank_boutique_product_h5_redis(key, productDetails, time);
                        //保存 每个商品id被缓存过的key，以便上架或下架时候 让这些缓存过的key失效
                        ProductRedis.set_cbbank_boutique_product_h5_productId_keys_redis(clientId, lockKey, key, productDetails, time);
                    }
                } catch (Exception e) {
                    LogCvt.error("个人h5查询精品商城商品异步[ThreadPool]设置redis缓存失败, 原因:" + e.getMessage(), e);
                } finally {
                    long endTime = System.currentTimeMillis();
                    LogCvt.debug("个人h5查询精品商城商品异步[ThreadPool]设置redis缓存,currentPage" + index + ",pageSize" + queryMongodbDataCount + "数据数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                }
            }
        });
    }
	
	private void reflashH5BoutiquePoductsCacheKey(final Product product) {
        FroadThreadPool.execute(new Runnable() { // 异步塞缓存
            @Override
            public void run() {
            	//保存 每个商品id被缓存过的key，以便上架或下架时候 让这些缓存过的key失效
                try{
                	String ckey = RedisKeyUtil.cbbank_product_boutique_h5_keys_client_id(product.getClientId());
                	String productName =  product.getName();
                	String fullName =  product.getFullName();
                	String categoryTreePath =  product.getCategoryTreePath();
                	String goodFlag = product.getGoodFlag();
                	String isRecommend = String.valueOf(goodFlag.charAt(0)); 
                	String isHot = String.valueOf(goodFlag.charAt(1));
                	String isNew = String.valueOf(goodFlag.charAt(2));
                    List<String> keys = redis.lrange(ckey, 0, -1);
                    if(keys!=null && keys.size()>0){
                    	String[] keysStr = null;
                    	boolean isDel = false;
                    	for(String k : keys){
                    		keysStr = k.split(":");//cbbank:product_boutique:h5:{index}:{client_id}:{category_id}:{product_name}:{good_flag}:{sort}
                    		if(keysStr!=null && keysStr.length==9){
                    			String categoryId = keysStr[5];
                    			String pn = keysStr[6];
                    			String good_flag = keysStr[7];
                    			isDel = false;
                    			if("1".equals(isRecommend) && "1".equals(good_flag)){
                    				isDel = true;
                    			} else if("1".equals(isHot) && "2".equals(good_flag)){
                    				isDel = true;
                    			} else if("1".equals(isNew) && "3".equals(good_flag)){
                    				isDel = true;
                    			} else if(Checker.isNotEmpty(productName) && Checker.isNotEmpty(pn) && productName.indexOf(pn)!=-1){
                    				isDel = true;
                    			} else if(Checker.isNotEmpty(fullName) && Checker.isNotEmpty(pn) && fullName.indexOf(pn)!=-1){
                					isDel = true;
                				} else if(Checker.isNotEmpty(categoryTreePath) && Checker.isNotEmpty(categoryId) 
                   					 && !"0".equals(categoryId) && (" "+categoryTreePath+" ").indexOf(" "+categoryId+" ")!=-1){
                					isDel = true;
                        		} else if("0".equals(categoryId)){
                   					isDel = true;
                           		}
                    			if(isDel){
                    				ProductRedis.del_key(k);
                    			}
                    		}
                    	}
                    }
                } catch (Exception e) {
                    LogCvt.error("保存 每个商品id被缓存过的key,上架时候 让这些缓存过的key失效异常：" +e,e);
                }
            }
        });
    }

	@Override
	public OutletProductQrCode addH5OutletProduct(OutletProduct outletProduct) {
		long start1 = System.currentTimeMillis();
        OutletProductQrCode po = null;
        try{
            
            DBObject dbObject = new BasicDBObject();
            dbObject.put("client_id", outletProduct.getClientId());
            dbObject.put("merchant_id", outletProduct.getMerchantId());
            dbObject.put("outlet_id", outletProduct.getOutletId());
            dbObject.put("consume_amount", outletProduct.getConsumeAmount());
            dbObject.put("not_discount_amount", outletProduct.getNotDiscountAmount());
            dbObject.put("discount_rate", outletProduct.getDiscountRate());
            dbObject.put("cost", outletProduct.getCost());
                
            OutletProduct bean = manager.findOne(dbObject, MongoTableName.CB_OUTLET_PRODUCT, OutletProduct.class);
            String productId = null;
            if(bean==null){
                outletProduct.setId(simpleID.nextId());
                dbObject.put("_id", outletProduct.getId());
                boolean isSuccess = manager.add(dbObject, MongoTableName.CB_OUTLET_PRODUCT) !=-1;// 向mongodb插入数据
                
                if(isSuccess){
                    productId = outletProduct.getId();
                }
            } else {
                productId = bean.getId();
            }
            if(productId!=null){
                po = new OutletProductQrCode();
                po.setProductId(productId);
            }
            long end1 = System.currentTimeMillis();
            LogCvt.debug("个人h5新加面对面商品用时:"+(end1-start1));
        } catch (Exception e) { 
            LogCvt.error("个人h5添加面对面商品失败，原因:" + e.getMessage(),e); 
        }
        return po; 
	}

	@Override
	public Result updateProductAuditState(
			List<EditProductAuditStateVo> productAuditStates) {
		Result result = new Result();
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            if(productAuditStates.size()==1) {
            	Map<String,Object> param = new HashMap<String,Object>();
            	param.put("auditState", productAuditStates.get(0).getAuditState());
            	param.put("productId", productAuditStates.get(0).getProductId());
            	if(ProductAuditState.noAudit.getCode().equals(productAuditStates.get(0).getAuditState())) {
            		param.put("oldAuditState", ProductAuditState.noCommit.getCode());
            	} else if(ProductAuditState.noCommit.getCode().equals(productAuditStates.get(0).getAuditState())) {
            		param.put("oldAuditState", ProductAuditState.noAudit.getCode());
            	}
            	productMapper.updateTempProductAuditStatus(param);
                productMapper.updateProductAuditStatus(param);
            } else {
            	Map<String,List<String>> map = new HashMap<String,List<String>>();
                for(EditProductAuditStateVo productAuditState : productAuditStates) {
                	if(map.get(productAuditState.getAuditState())==null) {
                		map.put(productAuditState.getAuditState(), new ArrayList<String>());
                	}
                	map.get(productAuditState.getAuditState()).add(productAuditState.getProductId());
                }
                Map<String,Object> param = null;
                for(String key : map.keySet()) {
                	param = new HashMap<String,Object>();
                	param.put("auditState", key);
                	param.put("productIds", map.get(key));
                	if(ProductAuditState.noAudit.getCode().equals(key)) {
                		param.put("oldAuditState", ProductAuditState.noCommit.getCode());
                	} else if(ProductAuditState.noCommit.getCode().equals(key)) {
                		param.put("oldAuditState", ProductAuditState.noAudit.getCode());
                	}
                	productMapper.updateTempProductAuditStatus(param);
                    productMapper.updateProductAuditStatus(param);
                }
            }
            sqlSession.commit(true);
        } catch (Exception e) {
        	if(null != sqlSession) {
        		sqlSession.rollback(true);
        	}
            LogCvt.error("更新审核状态失败，原因:" + e.getMessage(),e); 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("更新审核状态失败");
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return result; 
	}


	/**
	 * 查询单个商品mysql表和mongo表中的基础信息
	 * @param productId
	 * @return
	 */
	@Override
	public Map<String,Object> getProductInfo(String productId) {
		Map<String,Object> map = null;
	    SqlSession sqlSession = null;
        try {
        	long pstart = System.currentTimeMillis();
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            Product product = productMapper.findProduct(productId);//查询mysql中基础信息
            map = new HashMap<String,Object>();
            map.put("p", product);
            if(product!=null){
	        	if(Checker.isEmpty(product.getCodeUrl())){
	                Product codeInfo=findUrlCodeById(productId);
	                product.setCodeUrl(codeInfo.getCodeUrl());
	            }
	        }
            
            if(ProductType.group.getCode().equals(product.getType())){//团购商品
                ProductGroup pg = productMapper.findGroupProduct(productId);
                map.put("pg", pg);
            } else if(ProductType.presell.getCode().equals(product.getType())){//预售商品
                ProductPresell pr = productMapper.findPresellProduct(productId);
                map.put("pr", pr);
            }
            if(ProductAuditState.passAudit.getCode().equals(product.getAuditState())
            		&& Checker.isNotEmpty(product.getEditAuditState()) 
            		&& !ProductAuditState.passAudit.getCode().equals(product.getEditAuditState())){
            	ProductTemp pt = productMapper.getAuditingTempProductByProductId(productId);
            	if(pt!=null) {
            		map.put("pt", pt);
				}
            }
            long pend = System.currentTimeMillis();
            LogCvt.debug("管理端查看有对比项的商品详情查询商品mysql耗时:"+(pend-pstart));
            
            long pdstart = System.currentTimeMillis();
            DBObject where = new BasicDBObject();
            where.put("_id", product.getProductId());
            DBObject keys = new BasicDBObject();
            keys.put("merchant_name", "$merchant_name");
    		keys.put("is_recommend", "$is_recommend");
    		keys.put("is_new", "$is_new");
    		keys.put("is_hot", "$is_hot");
    		keys.put("vip_price", "$vip_price");
    		keys.put("product_category_info", "$product_category_info");
    		keys.put("buy_limit", "$buy_limit");
    		keys.put("image_info", "$image_info");
            ProductDetail pd = manager.findOne(where, keys, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            map.put("pd", pd);
            long pdend = System.currentTimeMillis();
            LogCvt.debug("管理端查看有对比项的商品详情查询商品mongo耗时:"+(pdend-pdstart));
            
            LogCvt.debug("管理端查看有对比项的商品详情总耗时:"+(pdend-pstart));
        } catch (Exception e) {
            LogCvt.error("查询 Product明细供修改查看product失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
		return map;
	}
	
	@Override
	public List<ProductListInfo> getProductsByPage(
            Page<ProductListInfo> page, ProductFilter productFilter) {
        List<ProductListInfo> list = null;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            Map<String,List<String>> param = null;
            if(Checker.isNotEmpty(productFilter.getClientId()) && Checker.isNotEmpty(productFilter.getOrgCode())){
            	String[] orgCodes = productFilter.getOrgCode().split(",");
            	CommonLogic comLogic = new CommonLogicImpl();
            	productFilter.setProOrgCode(null);
            	productFilter.setOrgCode(null);
            	productFilter.setCityOrgCode(null);
            	productFilter.setCountryOrgCode(null);
            	if(orgCodes!=null && orgCodes.length>1){
            		param = new HashMap<String,List<String>>();
            		List<String> proOrgCodes = new ArrayList<String>();
            		List<String> cityOrgCodes = new ArrayList<String>();
            		List<String> countryOrgCodes = new ArrayList<String>();
            		List<String> forgCodes = new ArrayList<String>();
            		for(String orgCode : orgCodes){
            			Org bankOrg = comLogic.getOrgByOrgCode(orgCode, productFilter.getClientId());
            			if(bankOrg!=null){
                            if(OrgLevelEnum.orgLevel_one.getLevel().equals(bankOrg.getOrgLevel())){
                            	proOrgCodes.add(bankOrg.getOrgCode());
                            } else if(OrgLevelEnum.orgLevel_two.getLevel().equals(bankOrg.getOrgLevel())){
                                cityOrgCodes.add(bankOrg.getOrgCode());
                            } else if(OrgLevelEnum.orgLevel_three.getLevel().equals(bankOrg.getOrgLevel())){
                                countryOrgCodes.add(bankOrg.getOrgCode());
                            } else if(OrgLevelEnum.orgLevel_four.getLevel().equals(bankOrg.getOrgLevel())){
                                forgCodes.add(bankOrg.getOrgCode());
                            }
                        }
            		}
            		if(proOrgCodes.size()>0){
            			param.put("province_agency", proOrgCodes);
            		}
            		if(cityOrgCodes.size()>0){
            			param.put("city_agency", cityOrgCodes);
            		}
            		if(countryOrgCodes.size()>0){
            			param.put("county_agency", countryOrgCodes);
            		}
            		if(forgCodes.size()>0){
            			param.put("org_code", forgCodes);
            		}
            	} else if(orgCodes!=null && orgCodes.length==1){
            		//根绝机构号查询该机构可以看到的商品
                    Org bankOrg = comLogic.getOrgByOrgCode(orgCodes[0], productFilter.getClientId());
                    if(bankOrg!=null){
                        if(OrgLevelEnum.orgLevel_one.getLevel().equals(bankOrg.getOrgLevel())){
                        	productFilter.setProOrgCode(bankOrg.getOrgCode());
                        } else if(OrgLevelEnum.orgLevel_two.getLevel().equals(bankOrg.getOrgLevel())){
                        	productFilter.setProOrgCode(bankOrg.getProvinceAgency());
                        	productFilter.setCityOrgCode(bankOrg.getOrgCode());
                        } else if(OrgLevelEnum.orgLevel_three.getLevel().equals(bankOrg.getOrgLevel())){
                        	productFilter.setProOrgCode(bankOrg.getProvinceAgency());
                        	productFilter.setCityOrgCode(bankOrg.getCityAgency());
                        	productFilter.setCountryOrgCode(bankOrg.getOrgCode());
                        } else if(OrgLevelEnum.orgLevel_four.getLevel().equals(bankOrg.getOrgLevel())){
                        	productFilter.setProOrgCode(bankOrg.getProvinceAgency());
                        	productFilter.setCityOrgCode(bankOrg.getCityAgency());
                        	productFilter.setCountryOrgCode(bankOrg.getCountyAgency());
                        	productFilter.setOrgCode(bankOrg.getOrgCode());
                        }
                    }
            	}
            }
            long startTime = System.currentTimeMillis();
            productFilter.setExcludePlatType(PlatType.boss.getValue()+"");
            list =  productMapper.findProductsByPage(page, productFilter, param);
            if(list!=null && list.size()>0) {
            	List<String> productIds = new ArrayList<String>();
            	for(ProductListInfo p : list) {
            		if(ProductAuditState.passAudit.getCode().equals(p.getAuditState())) {
            			productIds.add(p.getProductId());
            		}
            	}
            	if(productIds.size()>0) {
            		List<ProductTemp> pts = productMapper.getTempProductByProductIds(productIds);
            		if(pts!=null && pts.size()>0) {
            			Map<String,String> auditStates = new HashMap<String,String>();
            			for(ProductTemp pt : pts) { 
            				auditStates.put(pt.getProductId(), pt.getAuditState());
            			}
            			String auditState = null;
            			for(ProductListInfo pp : list) {
            				auditState = auditStates.get(pp.getProductId());
                    		if(ProductAuditState.passAudit.getCode().equals(pp.getAuditState()) && auditState!=null) {
                    			//商品审核状态对应的名称("0审核中","1审核通过","2审核不通过","3未提交","4审核通过(更新中)","5审核通过(更新审核未通过)"，6更新未提交)
                    	        if(ProductAuditState.noAudit.getCode().equals(auditState)) {
                    	        	pp.setAuditState("4");
                    	        } else if(ProductAuditState.failAudit.getCode().equals(auditState)) {
                    	        	pp.setAuditState("5");
                    	        } else if(ProductAuditState.noCommit.getCode().equals(auditState)) {
                    	        	pp.setAuditState("6");
                    	        }
                    		}
                    	}
            		}
            	}
            }
            long endTime = System.currentTimeMillis();
            LogCvt.debug("-------mapper中findProductsByPage:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
            
        }catch (Exception e) {
            LogCvt.error("商品管理分页查询商品列表失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return list;
    }
	
}
