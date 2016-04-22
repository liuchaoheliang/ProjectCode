package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.TransDto;
import com.froad.db.ahui.mappers.MerchantProductCommentMapper;
import com.froad.db.ahui.mappers.ProductCommentMapper;
import com.froad.db.ahui.mappers.ProductMapper;
import com.froad.db.ahui.mappers.TransMapper;
import com.froad.db.chonggou.entity.ProductCG;
import com.froad.db.chonggou.entity.ProductCommentInfo;
import com.froad.db.chonggou.entity.ProductDetail;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantMapperCG;
import com.froad.db.chonggou.mappers.OrgMapper;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.MerchantProductComment;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.ProductComment;
import com.froad.logback.LogCvt;
import com.froad.po.Merchant;
import com.froad.po.Org;
import com.froad.process.AbstractProcess;
import com.froad.util.MongoTableName;
import com.froad.util.RedisKeyUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ProductCommentProcess extends  AbstractProcess{
	private Map<String,Transfer> transferTypeEnumMap=new HashMap<String,Transfer>();
	private OrgMapper orgMapper;
	private MerchantMapperCG merchantMapper;
	private String CLIENTID="anhui";
	public ProductCommentProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		LogCvt.info("ProductCommentProcess:Process.........");
		insertProductComment();
	}
	
    public void insertProductComment(){
    	try{
    		String productName = null;
    		/*****************************************************数据库操作**********************************************************/
    		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
    		com.froad.db.chonggou.mappers.ProductMapper productCGMapper=sqlSession.getMapper(com.froad.db.chonggou.mappers.ProductMapper.class);
    		ProductCommentMapper productCommentMapper=ahSqlSession.getMapper(ProductCommentMapper.class);
    		ProductMapper productMapper=ahSqlSession.getMapper(ProductMapper.class);
    		orgMapper=sqlSession.getMapper(OrgMapper.class);
    		com.froad.db.chonggou.mappers.ProductMapper productMapperCG=sqlSession.getMapper(com.froad.db.chonggou.mappers.ProductMapper.class);
    		TransMapper transMapper=ahSqlSession.getMapper(TransMapper.class);
    		MerchantProductCommentMapper merchantProductCommentMapper=ahSqlSession.getMapper(MerchantProductCommentMapper.class);
    		merchantMapper=sqlSession.getMapper(MerchantMapperCG.class);
    		MerchantMapperCG merchantMapper=sqlSession.getMapper(MerchantMapperCG.class);
    		/***************************************************************************************************************/
    		List<ProductComment> productComments=productCommentMapper.selectAllProductComment();
    	   List<Transfer> transferProducts=transferMapper.queryGroupList(TransferTypeEnum.product_id);
  		   List<Transfer> transferMerchants=transferMapper.queryGroupList(TransferTypeEnum.merchant_id);
  		   List<Transfer> transferOrders=transferMapper.queryGroupList(TransferTypeEnum.order_id);
  			for(Transfer transfer:transferProducts){
  				transferTypeEnumMap.put(transfer.getOldId()+transfer.getType(), transfer);
  			}
  			for(Transfer transfer:transferMerchants){
  				transferTypeEnumMap.put(transfer.getOldId()+transfer.getType(), transfer);
  			}
  			for(Transfer transfer:transferOrders){
  				transferTypeEnumMap.put(transfer.getOldId()+transfer.getType(), transfer);
  			}
    		LogCvt.info("迁移评论表开始,大小为"+productComments.size());
    		/**************************************************迁移开始*****************************************************************/
    		for(ProductComment productComment:productComments){
    			//老表中DataState为50的数据是删除状态，不用迁移
    			if(!productComment.getDataState().getCode().equals("50")){
    				ProductCommentInfo pcInfo = new ProductCommentInfo();//mongo的实体
        			pcInfo.setClientId(CLIENTID);
        			Transfer transfer=transferTypeEnumMap.get(productComment.getProductId()+TransferTypeEnum.product_id.getCode());
        			if(transfer!=null){
        				LogCvt.debug("ProductCommentProductId"+productComment.getProductId());
        				pcInfo.setProductId(transfer.getNewId());
            			pcInfo.setCreateTime(productComment.getCreateTime().getTime());
            			pcInfo.setOrderValue(1);
            			if(productComment.getMemberMobile()!=null&&!"".equals(productComment.getMemberMobile())){
            				pcInfo.setPhone(productComment.getMemberMobile());
            			}
            			/***************添加商品类型********************/
            			ProductCG productCG=new ProductCG();
            			productCG.setClientId(pcInfo.getClientId());
            			productCG.setProductId(pcInfo.getProductId());
            			ProductCG productCG1=productCGMapper.getProductById(productCG);
            			pcInfo.setType(productCG1.getType());
            			//添加评论
            			if(productComment.getMemberCode()!=null&&!"".equals(productComment.getMemberCode())){
            				pcInfo.setMemberCode(String.valueOf(productComment.getMemberCode()));
            			}
            			if(productComment.getMemberName()!=null&&!"".equals(productComment.getMemberName())){
            				pcInfo.setMemberName(productComment.getMemberName());
            			}
            			if(productComment.getProductName()!=null&&!"".equals(productComment.getProductName())){
            				pcInfo.setProductName(productComment.getProductName());
            			}
            			Product product1 =productMapper.selectMerchantByProductId(productComment.getProductId());
            			//获取新的Merchant_id
            			//Merchant merchant=product1.getMerchant();
            			pcInfo.setMerchantName(product1.getName());
            			Transfer transfer1=transferTypeEnumMap.get(product1.getMerchantId()+TransferTypeEnum.merchant_id.getCode());
            			pcInfo.setMerchantId(transfer1.getNewId());
            			//分转换
            			if(productComment.getStarLevel()!=null){
            				if(productComment.getStarLevel().name().equals("one_star")){
            					pcInfo.setStarLevel(1);
            				}else if(productComment.getStarLevel().name().equals("two_star")){
            					pcInfo.setStarLevel(2);
            				}else if(productComment.getStarLevel().name().equals("three_star")){
            					pcInfo.setStarLevel(3);
            				}else if(productComment.getStarLevel().name().equals("four_star")){
            					pcInfo.setStarLevel(4);
            				}else if(productComment.getStarLevel().name().equals("five_star")){
            					pcInfo.setStarLevel(5);
            				}else{
            					pcInfo.setStarLevel(0);
            				}
            			}
            			if(productComment.getCommentDescription()!=null&&!"".equals(productComment.getCommentDescription())){
            				pcInfo.setCommentDescription(productComment.getCommentDescription());
            			}
            			//先到redis查找是否有商品名称的缓存没有则去数据库查找
            			ProductCG product = new ProductCG();
            			product.setClientId(pcInfo.getClientId());
            			product.setMerchantId(pcInfo.getMerchantId());
            			product.setProductId(pcInfo.getProductId());
            			String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(pcInfo.getClientId(), pcInfo.getMerchantId(), pcInfo.getProductId());
            			
            			Map<String, String> hash = redis.getMap(key);
            			if(hash!=null){
            				productName = hash.get("product_name");
            			}
            			if(productName==null) {
            				try{
            					List<ProductCG> ps = productMapperCG.getProductByProductId(product);
            					if(ps!=null && ps.size()>0){
            						productName=ps.get(0).getName();
            					}
            				} catch (Exception e) { 
            					sqlSession.rollback(true);  
            					LogCvt.error("添加ProductComment时候查询productName失败，原因:" + e.getMessage()); 
            				} 
            			}
            			//找出评论对应的缩略图
            			if(pcInfo.getProductId()!=null){
            				//先查找对应商品的缩略图
            				DBObject seach=new BasicDBObject();
            				seach.put("_id",pcInfo.getProductId());
            				seach.put("client_id", pcInfo.getClientId());
            				seach.put("merchant_id",pcInfo.getMerchantId());
            				ProductDetail pd= mongo.findOne(seach, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            				if(pd!=null){
            					if(pd.getImageInfo()!=null){
            						pcInfo.setImagePic(pd.getImageInfo().get(0).getThumbnail());
            					}
            				}
            			}
            			//找出子订单号和订单类型
            			if(productComment.getTransSn()!=null&&!"".equals(productComment.getTransSn())){
            				TransDto trans=transMapper.queryTransBySn(productComment.getTransSn());
            				if(trans!=null){
            					Transfer transferorder=transferTypeEnumMap.get(trans.getId()+TransferTypeEnum.order_id.getCode());
            					if(transferorder!=null){
            						String[] orderId=transferorder.getNewId().split(",");//子订单组成:orderid,sub_order_id
            						pcInfo.setOrderId(orderId[1]);
            						pcInfo.setBigOrderId(orderId[0]);
            					}
            				}
            				pcInfo.setOrderType(trans.getType().getCode());
            				com.froad.po.Merchant merchant=merchantMapper.findMerchantByMerchantId(product.getMerchantId());
            				//商品四级机构和商户一样
    						boolean flag=false;
    						if(merchant.getCityOrgCode()!=null&&merchant.getCountyOrgCode()!=null&&merchant.getProOrgCode()!=null&&merchant.getOrgCode()!=null){
    							pcInfo.setForgCode(merchant.getProOrgCode());
    							pcInfo.setSorgCode(merchant.getCityOrgCode());
    							pcInfo.setTorgCode(merchant.getCountyOrgCode());
    							pcInfo.setLorgCode(merchant.getOrgCode());	
    							flag=true;
    						}
    						if(merchant.getCityOrgCode()!=null&&merchant.getCountyOrgCode()!=null&&merchant.getProOrgCode()!=null){
    							if(!flag){
    								pcInfo.setForgCode(merchant.getProOrgCode());
    								pcInfo.setSorgCode(merchant.getCityOrgCode());
    								pcInfo.setTorgCode(merchant.getCountyOrgCode());
    								flag=true;
    							}
    						}
    						if(merchant.getCityOrgCode()!=null&&merchant.getProOrgCode()!=null) {
    						   if(!flag){
    							   pcInfo.setForgCode(merchant.getProOrgCode());
    								pcInfo.setSorgCode(merchant.getCityOrgCode());
    								flag=true;
    						   }
    						}
                            if(merchant.getProOrgCode()!=null){
                            	 if(!flag){
                            		 pcInfo.setForgCode(merchant.getProOrgCode());
      						   }
                            }
            				
//            				Map<OrgLevelEnum, String> orgLevel=getSuperOrgByMerchantId(pcInfo.getMerchantId());
//            				if(orgLevel!=null){
//            					pcInfo.setForgCode(orgLevel.get(OrgLevelEnum.orgLevel_one));
//            					pcInfo.setSorgCode(orgLevel.get(OrgLevelEnum.orgLevel_two));
//            					pcInfo.setTorgCode(orgLevel.get(OrgLevelEnum.orgLevel_three));
//            					pcInfo.setLorgCode(orgLevel.get(OrgLevelEnum.orgLevel_four));
//            				}
            			}
            			//找出评论对应的回复内容
            			MerchantProductComment merchantProductComment1=new MerchantProductComment();
            			merchantProductComment1.setMerchantId(product1.getMerchantId());
            			merchantProductComment1.setProductCommentId(productComment.getId());
            			List<MerchantProductComment>    merchantProductComments = merchantProductCommentMapper.selectMerchantCommentAll(merchantProductComment1);
            			if(merchantProductComments!=null&&merchantProductComments.size()>0){
            				MerchantProductComment merchantProductComment=merchantProductComments.get(0);
            				if(merchantProductComment.getCommentDescription()!=null&&!"".equals(merchantProductComment.getCommentDescription())){
            					pcInfo.setRecomment(merchantProductComment.getCommentDescription());
            				}else{
            					pcInfo.setRecomment("");
            				}
            				if(merchantProductComment.getCreateTime()!=null){
            					pcInfo.setRecommentTime(merchantProductComment.getCreateTime().getTime());
            				}
            			}
            			pcInfo.setMerchantUserName("");
            			boolean isSuccess = mongo.add(pcInfo, MongoTableName.CB_PRODUCT_COMMENT) !=-1;// 向mongodb插入数据
            			if(!isSuccess){
            				LogCvt.info("插入失败的评论,merchant_id="+pcInfo.getMerchantId()+"product_id="+pcInfo.getProductId());
            			}
        			}else{
        				LogCvt.info("FailProductCommnetId"+productComment.getProductId());
        			}
    			}
    			
    			
    		}
    		
    	}catch(Exception e){
    		LogCvt.error(e.getMessage(),e);
    	}
    	
    	
    }
    public Map<OrgLevelEnum, String> getSuperOrgByMerchantId(String merchantId) {
		if(merchantId == null){
			return null;
		}
		Map<OrgLevelEnum, String> map = null;
		
		try { 
			//过滤条件
			Org orgFileter = new Org();
			orgFileter.setMerchantId(merchantId);
			Org resultOrg=orgMapper.findOrgById(orgFileter);
			
			//如果merchantid属于3级机构发展，那么4就返回0，1、2、3返回有效值。
			//如果merchantid属于4级机构，那么0、1、2、3都返回有效值。
			
			
			
			//机构商户
			if(resultOrg!=null){
				map=this.setOrgMap(resultOrg);
			}else{//普通商户
				Merchant merchant=merchantMapper.findMerchantByMerchantId(merchantId);
				if(merchant!=null){
					String orgCode=merchant.getOrgCode();//发展机构
					String clientId=merchant.getClientId();
					//设置过滤条件，进行查机构对象
					orgFileter=new Org();
					orgFileter.setOrgCode(orgCode);
					orgFileter.setClientId(clientId);
					
					//结果机构对象
					resultOrg=orgMapper.findOrgById(orgFileter);
					if(resultOrg!=null){
						map=this.setOrgMap(resultOrg);
					}
					
				}
				
				
			}
		}catch (Exception e) { 
			LogCvt.error("根据Id查询Org失败，原因:" + e.getMessage()); 
		} 
		
		return map;
	}

	/**
	 * 设置map值
	 * @param resultOrg
	 * @return
	 */
	private Map<OrgLevelEnum, String> setOrgMap(Org resultOrg){
		Map<OrgLevelEnum, String> map = new HashMap<OrgLevelEnum, String>();
		//如果merchantid属于3级机构发展，那么4就返回0，1、2、3返回有效值。
		//如果merchantid属于4级机构，那么0、1、2、3都返回有效值。
		String orgLevel=resultOrg.getOrgLevel();
		if(OrgLevelEnum.orgLevel_three.getLevel().equals(orgLevel)){
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getProvinceAgency());
			map.put(OrgLevelEnum.orgLevel_two, resultOrg.getCityAgency());
			map.put(OrgLevelEnum.orgLevel_three, resultOrg.getOrgCode());
			//map.put(OrgLevelEnum.orgLevel_four, "0");
		}else if(OrgLevelEnum.orgLevel_four.getLevel().equals(orgLevel)){
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getProvinceAgency());
			map.put(OrgLevelEnum.orgLevel_two, resultOrg.getCityAgency());
			map.put(OrgLevelEnum.orgLevel_three, resultOrg.getCountyAgency());
			map.put(OrgLevelEnum.orgLevel_four, resultOrg.getOrgCode());
		}else if(OrgLevelEnum.orgLevel_two.getLevel().equals(orgLevel)){
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getProvinceAgency());
			map.put(OrgLevelEnum.orgLevel_two, resultOrg.getOrgCode());
		}else{
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getOrgCode());
		}
		
		return map;
	}
	
	/**
	 * 
	 * @Title: getOrgLevelByOrgCode 
	 * @Description: 根据机构号返回该机构等级
	 * @author: froad-huangyihao 2015年4月10日
	 * @modify: froad-huangyihao 2015年4月10日
	 * @param orgCode
	 * @param clientId
	 * @return	机构等级
	 * @throws
	 */
	public OrgLevelEnum getOrgLevelByOrgCode(String orgCode, String clientId) {
		if(orgCode == null && clientId == null){
			return null;
		}
		SqlSession sqlSession = null;
		OrgLevelEnum orgLevelEnum = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			OrgMapper orgMapper = sqlSession.getMapper(OrgMapper.class);
			
			Org temp = new Org();
			temp.setOrgCode(orgCode);
			temp.setClientId(clientId);
			Org org = orgMapper.findOrgById(temp);
			orgLevelEnum = OrgLevelEnum.getByLevel(org.getOrgLevel());
		} catch (Exception e) {
			LogCvt.error("根据机构号返回该机构等级查询异常", e);
		} finally {
			if(sqlSession != null) sqlSession.close(); 
		}
		
		return orgLevelEnum;
	}

}

