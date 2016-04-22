package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.cbank.persistent.entity.MerchantProductComment;
import com.froad.cbank.persistent.entity.Product;
import com.froad.cbank.persistent.entity.ProductComment;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.MerchantCG;
import com.froad.db.chonggou.entity.ProductCG;
import com.froad.db.chonggou.entity.ProductCommentInfo;
import com.froad.db.chonggou.entity.ProductDetail;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantMapperCG;
import com.froad.db.chonggou.mappers.OrgMapper;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.MerchantProductCommentMapper;
import com.froad.db.chongqing.mappers.ProductCommentMapper;
import com.froad.db.chongqing.mappers.ProductMapper;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.po.Merchant;
import com.froad.po.Org;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.MongoTableName;
import com.froad.util.RedisKeyUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ProductCommentProcess extends  AbstractProcess{
	private Map<String,Transfer> transferTypeEnumMap=new HashMap<String,Transfer>();
	private OrgMapper orgMapper;
	private MerchantMapperCG merchantMapper;
	private TransferMapper transferMapper;
	public ProductCommentProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}
	@Override
	public void before()  {
		LogCvt.info("重庆ProductCommentProcess:删除开始.........");
		DBObject delete=new BasicDBObject();
		delete.put("client_id", Constans.clientId);
		int result=mongo.remove(delete, MongoTableName.CB_PRODUCT_COMMENT);
		if(result>0){
			LogCvt.info("重庆ProductCommentProcess:删除成功.........");
		}else{
			LogCvt.info("重庆ProductCommentProcess:删除失败.........");
		}
	}

	@Override
	public void process() {
		LogCvt.info("ProductCommentProcess:转移开始.........");
		/*****************************************************数据库操作**********************************************************/
	    transferMapper = sqlSession.getMapper(TransferMapper.class);
		ProductCommentMapper productCommentMapper=cqSqlSession.getMapper(ProductCommentMapper.class);
		orgMapper=sqlSession.getMapper(OrgMapper.class);
		merchantMapper=sqlSession.getMapper(MerchantMapperCG.class);
		//内存加载
		 LoadMap();
	     List<ProductComment> productComments=productCommentMapper.selectAllProductComment();
		LogCvt.info("迁移评论表开始,大小为"+productComments.size());
		for(ProductComment productComment:productComments){
				ProductCommentInfo pcInfo = new ProductCommentInfo();//mongo的实体
    			Transfer transfer=transferTypeEnumMap.get(productComment.getProductId()+TransferTypeEnum.product_id.getCode());
    			if(transfer!=null){
    				LogCvt.debug("ProductCommentProductId"+productComment.getProductId());
    				pcInfo.setProductId(transfer.getNewId());
    				//基本属性赋值
    				setBaseInfo(pcInfo, productComment);
    				//商品类型赋值
        			setProductType(pcInfo);
        			//获取缩略图
        			setImage(pcInfo);
        			//设置机构号
        			setOrgCode(pcInfo);
        			//找出评论对应的回复内容
                    setRecomment(pcInfo, productComment);
        			boolean isSuccess = mongo.add(pcInfo, MongoTableName.CB_PRODUCT_COMMENT) !=-1;// 向mongodb插入数据
        			if(!isSuccess){
        				LogCvt.info("插入失败的评论,merchant_id="+pcInfo.getMerchantId()+"product_id="+pcInfo.getProductId());
        			}
    			}else{
    				LogCvt.info("FailProductCommnetId"+productComment.getProductId());
    			}
		}
	}
	
	
	
	
	public void setRecomment(ProductCommentInfo pcInfo,ProductComment productComment){
		MerchantProductCommentMapper merchantProductCommentMapper=cqSqlSession.getMapper(MerchantProductCommentMapper.class);
		MerchantProductComment merchantProductComment1=new MerchantProductComment();
		merchantProductComment1.setMerchantId(productComment.getMerchantId());
		merchantProductComment1.setProductCommentId(productComment.getId());
		List<MerchantProductComment>    merchantProductComments = merchantProductCommentMapper.selectMerchantCommentAll(merchantProductComment1);
		if(merchantProductComments!=null&&merchantProductComments.size()>0){
			MerchantProductComment merchantProductComment=merchantProductComments.get(0);
			if(merchantProductComment.getDescription()!=null&&!"".equals(merchantProductComment.getDescription())){
				pcInfo.setRecomment(merchantProductComment.getDescription());
			}else{
				pcInfo.setRecomment("");
			}
			if(merchantProductComment.getCreateTime()!=null){
				pcInfo.setRecommentTime(merchantProductComment.getCreateTime().getTime());
			}
			if(merchantProductComment.getMerchantUserName()!=null&&!"".equals(merchantProductComment.getMerchantUserName())){
				pcInfo.setMerchantUserName(merchantProductComment.getMerchantUserName());
			}else{
				pcInfo.setMerchantUserName("");
			}
		}
	}
   public void setImage(ProductCommentInfo pcInfo){
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
  
     public void setProductType(ProductCommentInfo pcInfo){
    	 com.froad.db.chonggou.mappers.ProductMapper productCGMapper=sqlSession.getMapper(com.froad.db.chonggou.mappers.ProductMapper.class);
    		ProductCG productCG=new ProductCG();
			productCG.setClientId(pcInfo.getClientId());
			productCG.setProductId(pcInfo.getProductId());
			ProductCG productCG1=productCGMapper.getProductById(productCG);
			pcInfo.setType(productCG1.getType());
     }
	public void setOrgCode(ProductCommentInfo pcInfo){
		//商品四级机构和商户一样
		MerchantCG merchant=merchantMapper.findMerchantByMerchantId(pcInfo.getMerchantId());
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
	}
	
	public void setBaseInfo(ProductCommentInfo pcInfo,ProductComment productComment){
		boolean flag=false;              //做兼容,如果没有评论人就用手机号代替评论人
		pcInfo.setClientId(Constans.clientId);
		pcInfo.setCreateTime(productComment.getCreateTime().getTime());
		pcInfo.setOrderValue(productComment.getOrderValue());
		pcInfo.setMerchantName(productComment.getMerchantName());
		if(productComment.getMemberMobile()!=null&&!"".equals(productComment.getMemberMobile())){
			pcInfo.setPhone(productComment.getMemberMobile());
			pcInfo.setMemberName(productComment.getMemberMobile());
			flag=true;
		}
		if(productComment.getMemberCode()!=null&&!"".equals(productComment.getMemberCode())){
			pcInfo.setMemberCode(String.valueOf(productComment.getMemberCode()));
		}
//		if(productComment.getMemberName()!=null&&!"".equals(productComment.getMemberName())){
//			pcInfo.setMemberName(productComment.getMemberName());
//		}else if(flag){
//			pcInfo.setMemberName(pcInfo.getPhone());
//		}
		if(productComment.getProductName()!=null&&!"".equals(productComment.getProductName())){
			pcInfo.setProductName(productComment.getProductName());
		}
		//分转换
		if(productComment.getStarLevel()!=null){
			pcInfo.setStarLevel(productComment.getStarLevel());
		}
		if(productComment.getCommentDescription()!=null&&!"".equals(productComment.getCommentDescription())){
			pcInfo.setCommentDescription(productComment.getCommentDescription());
		}
		if(productComment.getProductName()!=null){
			pcInfo.setProductName(productComment.getProductName());
		}else{
			pcInfo.setProductName("");
		}
		Transfer transfer1=transferTypeEnumMap.get(productComment.getMerchantId()+TransferTypeEnum.merchant_id.getCode());
		pcInfo.setMerchantId(transfer1.getNewId());
		//找出子订单号和订单类型
		if(productComment.getOrderId()!=null&&!"".equals(productComment.getOrderId())){
				Transfer transferorder=transferTypeEnumMap.get(String.valueOf(productComment.getOrderId())+TransferTypeEnum.order_id.getCode());
				if(transferorder!=null){
					String[] orderId=transferorder.getNewId().split(",");//子订单组成:orderid,sub_order_id
					pcInfo.setOrderId(orderId[1]);
					pcInfo.setBigOrderId(orderId[0]);
				}
			
			pcInfo.setOrderType(setOrderType(productComment.getOrderType().getCode()));
		}
	}
    public void LoadMap(){
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
  	
    }
    public String setOrderType(String type){
    	if(type.equals("01")){
    		return "1";
    	}else if(type.equals("02")){
    		return "2";
    	}else if(type.equals("03")){
    		return "0";
    	}
    	return "";
    }
	
}

