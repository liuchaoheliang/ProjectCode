package com.froad.process.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.cbank.persistent.entity.MerchantOutlet;
import com.froad.cbank.persistent.entity.MerchantOutletBank;
import com.froad.cbank.persistent.entity.Product;
import com.froad.cbank.persistent.entity.ProductGroup;
import com.froad.cbank.persistent.entity.ProductImage;
import com.froad.cbank.persistent.entity.ProductLimit;
import com.froad.cbank.persistent.entity.ProductMerchantOutlet;
import com.froad.cbank.persistent.entity.ProductPresell;
import com.froad.common.qrcode.QrCodeGenerateService;
import com.froad.common.qrcode.QrCodeRequestVo;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.AreaCG;
import com.froad.db.chonggou.entity.MerchantCG;
import com.froad.db.chonggou.entity.ProductArea;
import com.froad.db.chonggou.entity.ProductBuyLimit;
import com.froad.db.chonggou.entity.ProductCG;
import com.froad.db.chonggou.entity.ProductCategoryInfoCG;
import com.froad.db.chonggou.entity.ProductCityArea;
import com.froad.db.chonggou.entity.ProductCityOutlet;
import com.froad.db.chonggou.entity.ProductDetail;
import com.froad.db.chonggou.entity.ProductGroupCG;
import com.froad.db.chonggou.entity.ProductImageCG;
import com.froad.db.chonggou.entity.ProductOutlet;
import com.froad.db.chonggou.entity.ProductPresellCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.AreaMapperCG;
import com.froad.db.chonggou.mappers.MerchantMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.MerchantOutletBankMapper;
import com.froad.db.chongqing.mappers.MerchantOutletMapper;
import com.froad.db.chongqing.mappers.ProductCommentMapper;
import com.froad.db.chongqing.mappers.ProductGroupMapper;
import com.froad.db.chongqing.mappers.ProductImageMapper;
import com.froad.db.chongqing.mappers.ProductLimitMapper;
import com.froad.db.chongqing.mappers.ProductMapper;
import com.froad.db.chongqing.mappers.ProductMerchantOutletMapper;
import com.froad.db.chongqing.mappers.ProductPresellMapper;
import com.froad.enums.ModuleID;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.QrCodeType;
import com.froad.enums.SeckillFlagEnum;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.JsoupUtil;
import com.froad.util.MongoTableName;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ProductProcess extends AbstractProcess{
	private com.froad.db.chonggou.mappers.ProductMapper productMapperCG;
	private TransferMapper transferMapper;
	private Map<Object,Object> transferMap;
	private MerchantMapperCG merchantMapper;
	private MerchantCG merchant;
	private static SimpleID simpleID = new SimpleID(ModuleID.product);
	/*************常量****************/
	private final String DELIVERYOPTION="1";
	private final String ORGCODE="000000";
	private final String PORID="PORID";//id 标识
	private final int MAXVIP=0;
	private final int MAXCARD=0;
	private final String STRING="string";
	private final String DATE="date";
	private final String INTEGER="int";
	private final String DOUBLE="double";
	private final String LONG="long";
	private final String CLIENTID="client_id";
	private final String GROUPPLAT="3";
	private final String PRESELLPLAY="2";
	private boolean isValue=false;//判断预售团购是否存在,存在才插入商品表
	public ProductProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}
	
	@Override
	public void before() {
		LogCvt.info("商品删除开始..............");
		productMapperCG=sqlSession.getMapper(com.froad.db.chonggou.mappers.ProductMapper.class);
	Integer productnum=	productMapperCG.deleteProduct(Constans.clientId);
	if(productnum!=null){
		LogCvt.info("商品删除数量为.............."+productnum);
	}
	Integer groupnum= productMapperCG.deleteProductGroup(Constans.clientId);
	if(groupnum!=null){
		LogCvt.info("团购商品删除数量为.............."+groupnum);
	}
	Integer presellnum=	productMapperCG.deleteProductPresell(Constans.clientId);
    if(presellnum!=null){
    	LogCvt.info("预售商品删除数量为.............."+presellnum);
	}
	DBObject serch=new BasicDBObject();
	serch.put(CLIENTID, Constans.clientId);
	int result=mongo.remove(serch, MongoTableName.CB_PRODUCT_DETAIL);  
	if(result>0){
		LogCvt.info("商品mongo删除成功");	
	}else{
		LogCvt.info("商品mongo删除失败");	
	}
	
	}
	@Override
	public void process() {
		/***********************数据源开启***************************/
		 ProductMapper productMapper=cqSqlSession.getMapper(ProductMapper.class);
		merchantMapper=sqlSession.getMapper(MerchantMapperCG.class);
		/********************数据加载内存**************************/
		                     LoadMap();
		                     
		List<Product> productcqs=productMapper.selectAllProduct();
		for(Product productcq:productcqs){
			LogCvt.info("ProductID"+productcq.getId()+"开始迁移..........................");
			ProductCG productCG=new ProductCG();
			/********商品基本属性赋值***********/
			setProductInfo(productCG,productcq);
			/********商品mongo***********/
			if(isValue){
			setProductDetail(productCG,productcq);
			}
		}
		
		
		
		
		
		
		
	}
	/**
	 * 
	  组件方法：
	  1.LoadMap()内存加载
	  2.setProductInfo()设置商品基本属性
	  3.setProductDetail()设置商品mongo的详情表属性
	  4.setMerchantInfo()设置商品基本属性中和商户关联的部分,主要是机构
	  5.setAudit()设置商品审核部分
	  6.setProductGroup()设置团购商品的基本属性
	  7.setProductPresell()设置预售商品的基本属性
	  8.InsertTranfer()中间表插入
	  9.productRedis()设置商品缓存
	  10.setIsMarketable()设置商品上下架状态
	  11.setProductType()设置商品类型
	  12.setImageInfo()设置Mongo图片
	  13.setBuyLimit()设置购买限制
	  14.setProductCatgory()设置商品目录
	  15.setOrgCodes设置预售提货点
	 *  
	 */
	//内存加载
	public void LoadMap(){
		transferMap=new HashMap<Object,Object>();
	    AreaMapperCG areaMapper=sqlSession.getMapper(AreaMapperCG.class);
		ProductLimitMapper productLimitMapper=cqSqlSession.getMapper(ProductLimitMapper.class);
		transferMapper=sqlSession.getMapper(TransferMapper.class);
		List<Transfer> transferMerchants=transferMapper.queryGroupList(TransferTypeEnum.merchant_id);
		List<Transfer> transferOutlets=transferMapper.queryGroupList(TransferTypeEnum.outlet_id);
		List<Transfer> transferCategorys=transferMapper.queryGroupList(TransferTypeEnum.productcategory_id);
		List<Transfer> transferAreas=transferMapper.queryGroupList(TransferTypeEnum.area_id);
		List<AreaCG> areas=areaMapper.findallArea();
		List<ProductLimit> productLimits=productLimitMapper.selectAllLimit();
		for(Transfer transfer:transferMerchants){
			transferMap.put(transfer.getOldId()+transfer.getType(), transfer);
		}
		for(Transfer transfer:transferOutlets){
			transferMap.put(transfer.getOldId()+transfer.getType(), transfer);
		}
		for(Transfer transfer:transferCategorys){
			transferMap.put(transfer.getOldId()+transfer.getType(), transfer);
		}
		for(Transfer transfer:transferAreas){
			transferMap.put(transfer.getOldId()+transfer.getType(), transfer);
		}
		for(AreaCG area:areas){
			transferMap.put(area.getId(), area);
		}
		for(ProductLimit productLimit:productLimits){
			transferMap.put(productLimit.getProductId()+PORID, productLimit);
		}
		//修复程序用
//		List<Transfer> transferproducts=transferMapper.queryGroupList(TransferTypeEnum.product_id);
//		if(transferproducts!=null){
//			for(Transfer transfer:transferproducts){
//				transferMap.put(transfer.getOldId()+transfer.getType(), transfer);
//			}
//		}
	}
	
  public void setProductInfo(ProductCG productCG,Product productcq){
	  //修复程序用
	    // Transfer transferProduct=(Transfer)transferMap.get(String.valueOf(productcq.getId())+TransferTypeEnum.product_id);
		//  productCG.setProductId(transferProduct==null?simpleID.nextId():transferProduct.getNewId());
	      productCG.setProductId(simpleID.nextId());
		  productCG.setCreateTime(productcq.getCreateTime());
		  productCG.setClientId(Constans.clientId);
		 
		  /*************商品基本属性（不涉及其他表）********************/
		  setIsMarketable(productcq,productCG); //上下架状态
		  productCG.setType(setProductType(productcq.getType().getCode()));
		  productCG.setDeliveryOption(DELIVERYOPTION);
		  productCG.setName((String)checkIsEmpty(productcq.getName(),STRING));
		  productCG.setFullName((String)checkIsEmpty(productcq.getName(),STRING));
		  productCG.setPrice(DealWithPrice(productcq.getPrice(), true));   
		  productCG.setCost(DealWithPrice(productcq.getCost(), true));
		  productCG.setMarketPrice(DealWithPrice(productcq.getMarketPrice(), true)); 
		  productCG.setWeight((String)checkIsEmpty(productcq.getWeight(),STRING));
		  productCG.setWeightUnit((String)checkIsEmpty(productcq.getWeightUnit(),STRING));
		  productCG.setStore((Integer)checkIsEmpty(productcq.getStore(),INTEGER));
		  productCG.setOrderValue((Integer)checkIsEmpty(productcq.getOrderValue(),INTEGER));
		  productCG.setIsSeckill(SeckillFlagEnum.notSeckill.getCode());
		  productCG.setIsBest(productcq.getIsBest());
		  productCG.setPoint(setAvgPoint(productcq));
		  productCG.setIntroduction(Constans.converText((String)checkIsEmpty(productcq.getIntroduction(),STRING)));
		  productCG.setBuyKnow(JsoupUtil.getHtmlText((String)checkIsEmpty(productcq.getBuyKnow(),STRING)));
		  productCG.setReviewStaff("");
		  productCG.setDeliveryMoney(0);
		  Transfer transferCategory=(Transfer)transferMap.get(String.valueOf(productcq.getProductCategoryId())+TransferTypeEnum.productcategory_id.getCode());
		  productCG.setCategoryTreePath((String)checkIsEmpty(transferCategory.getNewId(),STRING));
		  productCG.setBriefIntroduction((String)checkIsEmpty(productcq.getFullName(),STRING));
		  productCG.setIsLimit(IsLimit((ProductLimit)transferMap.get(productcq.getId()+PORID)));
		  productCG.setPlatType(setPlatType(productcq.getType().getCode()));
		  /*************商品基本属性（不涉及其他表）********************/
		  
		  /**********商户机构赋值***********/
		  setMerchantInfo(productCG,productcq);
		
		  /**********机构审核赋值***********/
		  setAudit(productCG,productcq);
		  
		  /**
		   * 添加团购和预售商品,如果商品不存在isValue=false,则不再继续下面的步骤
		   */
		  if(productCG.getType().equals(ProductType.group.getCode())){
			  isValue= setProductGroup(productCG,productcq);
		  }else{
			  isValue=setProductPresell(productCG,productcq);
		  }
		  if(!isValue){
			  LogCvt.error("商品ID："+productcq.getId()+",名称为"+productcq.getName()+"的商品不存在预售和团购商品中");
			  return;
		  }
		  //添加商品
		 int result= productMapperCG.addProduct(productCG);
		 //插入中间表
		 if(result>0){
			 LogCvt.info("ProductID为"+productcq.getId()+"的商品插入cb_product成功");
			 InsertTranfer(productCG,productcq);
			 isValue=true;
			 //库存redis
			  setStrinRedis(RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(productCG.getClientId(), productCG.getMerchantId(), productCG.getProductId()),ObjectUtils.toString(productCG.getStore(), ""));
		     //生成二维码
			    QrCodeGenerateService Qservice=new QrCodeGenerateService();
				QrCodeRequestVo requestVo=new QrCodeRequestVo(QrCodeType.PRODUCT.getCode()+productCG.getProductId(),productCG.getClientId());
				Qservice.generateQrCode(requestVo);
			//商品信息缓存
				productRedis(productCG);
		 }else{
			 LogCvt.info("ProductID为"+productcq.getId()+"的商品插入cb_product失败");
			 isValue=false;
		 }
		 
	  
	  
  }
  public void setProductDetail(ProductCG productCG,Product productcq){
	  /*********基本属性*******/
	  ProductDetail productDetail=new ProductDetail();
	  productDetail.setId(productCG.getProductId());
	  productDetail.setClientId(Constans.clientId);
	  productDetail.setBriefIntroduction(productCG.getBriefIntroduction());
	  setBuyLimit(productcq,productDetail);//购买限制
	  productDetail.setDeliveryOption(DELIVERYOPTION);
	  productDetail.setEndTime(productCG.getEndTime());
	  productDetail.setStartTime(productCG.getStartTime());
	  productDetail.setFullName(productCG.getFullName());
	  productDetail.setIsBest(setIsObject(productCG.getIsBest()));
	  productDetail.setIsLimit(setIsObject(productCG.getIsLimit()));
	  productDetail.setIsMarketable(productCG.getIsMarketable());
	  productDetail.setIsSeckill(productCG.getIsSeckill());
	  productDetail.setMarketPrice(productCG.getMarketPrice());
	  productDetail.setMerchantId(productCG.getMerchantId());
	  productDetail.setMerchantName(productCG.getMerchantName());
	  productDetail.setName(productCG.getName());
	  productDetail.setPrice(productCG.getPrice());
	  setProductCatgory(productCG,productDetail);//商品目录
	  productDetail.setProductType(productCG.getType());
	  productDetail.setRackTime(productCG.getRackTime());
	  productDetail.setSellCount(productCG.getSellCount());
	  productDetail.setStoreCount(productCG.getStore());
	  setImageInfo(productcq,productDetail);//图片设置
      setOrgCodes(productcq, productDetail);//
      /********插入mongo*************/
      boolean  isSuccess = mongo.add(productDetail, MongoTableName.CB_PRODUCT_DETAIL) > -1;// 向mongodb插入数据
      if(isSuccess){
    	  LogCvt.info("ProductId为"+productcq.getId()+"插入Mongo成功");
      }else{
    	  LogCvt.info("ProductId为"+productcq.getId()+"插入Mongo失败");
      }
      
  }
   public boolean setProductPresell(ProductCG productCG,Product productcq){
       ProductPresellMapper  poductpresellMapper=cqSqlSession.getMapper(ProductPresellMapper.class);
	   ProductPresell presell= poductpresellMapper.selectProductPresellById(productcq.getId());
	   if(presell!=null){
		   ProductPresellCG productPresell=new ProductPresellCG();
		   productPresell.setClientId(Constans.clientId);
		   productPresell.setCreateTime(presell.getCreateTime());
		   productPresell.setProductId(productCG.getProductId());
		   productPresell.setProductSupplier("");
		   productPresell.setMaxPerOutlet(0);
		   productPresell.setDeliveryStartTime(presell.getDeliveryStartTime());
		   productPresell.setDeliveryEndTime(presell.getDeliveryEndTime());
		   productPresell.setTrueBuyerNumber((Integer)checkIsEmpty(presell.getTrueBuyerNumber(),INTEGER));
		   productPresell.setVirtualBuyerNumber((Integer)checkIsEmpty(presell.getVirtualBuyerNumber(),INTEGER));
		   productPresell.setClusterState(IsClusterState(presell.getClusterState().getCode()));
		   productPresell.setExpireStartTime(presell.getStartTime());
		   productPresell.setExpireEndTime(presell.getExpireTime());
		   productPresell.setClusterType(setClusterType(presell.getClusterType().getCode()));
		   productCG.setStartTime(presell.getStartTime());
		   productCG.setEndTime(presell.getEndTime());
		   productCG.setSellCount((Integer)checkIsEmpty(presell.getTrueBuyerNumber(), INTEGER));
		   productMapperCG.addProductPresell(productPresell);
		   productCG.setProductPresell(productPresell);
		   LogCvt.info("ProductId为"+productcq.getId()+"的预售商品迁移完成");
		   return true;
	   }else{
		   LogCvt.info("ProductId为"+productcq.getId()+"的预售商品不存在"); 
		   return false;
	   }
	  
	  
  }
    public boolean setProductGroup(ProductCG productCG,Product productcq){
    	ProductGroupMapper productGroupMapperCQ=cqSqlSession.getMapper(ProductGroupMapper.class);
    	ProductGroup productGroup=productGroupMapperCQ.selectProductGroupById(productcq.getId());
    	if(productGroup!=null){
    	ProductGroupCG productGroupCG=new ProductGroupCG();
    	productGroupCG.setClientId(Constans.clientId);
    	productGroupCG.setCreateTime(productGroup.getCreateTime());	
    	productGroupCG.setProductId(productCG.getProductId());
    	productGroupCG.setTrueBuyerNumber((Integer)checkIsEmpty(productGroup.getTrueBuyerNumber(),INTEGER));
    	productCG.setStartTime(productGroup.getStartTime());
    	productCG.setEndTime(productGroup.getEndTime());
    	productCG.setSellCount((Integer)checkIsEmpty(productGroup.getTrueBuyerNumber(), INTEGER));
    	productGroupCG.setVirtualBuyerNumber((Integer)checkIsEmpty(productGroup.getVirtualBuyerNumber(),INTEGER));
    	productGroupCG.setExpireStartTime(productGroup.getStartTime());
    	productGroupCG.setExpireEndTime(productGroup.getExpireTime());
    	productMapperCG.addProductGroup(productGroupCG);
    	productCG.setProductGroup(productGroupCG);
    	LogCvt.info("ProductId为"+productcq.getId()+"的团购商品迁移完成");
    	return true;
    	}else{
    		LogCvt.info("ProductId为"+productcq.getId()+"的团购商品不存在");
    		return false;
    	}
    	
    	
    }
  
  
    public void setAudit(ProductCG productCG,Product productcq){
    	productCG.setAuditState(productcq.getProductVerifyType().getCode());
    	/**
    	 * 1、	org_verify_state=3，网点审核通过对应商户被发展法人机构。
           2、	org_verify_state=2，设置为省联社机构000000。
           3、	org_verify_state=3，设置为省联社机构000000。
    	 */
    	if(productcq.getOrgVerifyState()!=null){
    		if(productcq.getOrgVerifyState().getCode().equals("3")){
    			if(merchant.getCityOrgCode()!=null){
    				productCG.setAuditOrgCode(merchant.getCityOrgCode());
    			}else if(merchant.getProOrgCode()!=null){
    				productCG.setAuditOrgCode(merchant.getProOrgCode());
    			}
    		}else{
    			productCG.setAuditOrgCode(ORGCODE);
    		}
    	}else{
    		productCG.setAuditOrgCode(ORGCODE);
    	}
    	productCG.setAuditState(productcq.getProductVerifyType().getCode());
    	productCG.setAuditStaff((String)checkIsEmpty(productcq.getInspectors(),STRING));
    	productCG.setAuditComment((String)checkIsEmpty(productcq.getAuditMemo(),STRING));
    	productCG.setAuditEndOrgCode(ORGCODE);
    	productCG.setAuditTime(productcq.getUpdateTime());
    	
    	/**
    	 * 根据商户的被发展网点决定，网点为空未二级机构，否则为网点。
    	 */
    	if(merchant.getCountyOrgCode()==null){
    		if(merchant.getCityOrgCode()!=null){
    			productCG.setAuditStartOrgCode(merchant.getCityOrgCode());
    		}else{
    			productCG.setAuditStartOrgCode(merchant.getProOrgCode());
    		}
    	}else {
    		productCG.setAuditStartOrgCode(merchant.getCountyOrgCode());
    	}
  }
	public void setMerchantInfo(ProductCG productCG,Product productcq){
		boolean flag=false;
		Transfer transfer=(Transfer)transferMap.get(productcq.getMerchantId()+TransferTypeEnum.merchant_id.getCode());
		productCG.setMerchantId(transfer.getNewId());
		 merchant=merchantMapper.findMerchantByMerchantId(String.valueOf(productCG.getMerchantId()));
		if(merchant!=null){
			productCG.setMerchantName((String)checkIsEmpty(merchant.getMerchantName(),STRING));
			/**
			 * 机构层级设置,和商户的发展机构一样
			 */
			if(merchant.getCityOrgCode()!=null&&merchant.getCountyOrgCode()!=null&&merchant.getProOrgCode()!=null&&merchant.getOrgCode()!=null){
				productCG.setProOrgCode(merchant.getProOrgCode());
				productCG.setCountryOrgCode(merchant.getCountyOrgCode());
				productCG.setCityOrgCode(merchant.getCityOrgCode());
				productCG.setOrgCode(merchant.getOrgCode());	
				flag=true;
			}
			if(merchant.getCityOrgCode()!=null&&merchant.getCountyOrgCode()!=null&&merchant.getProOrgCode()!=null){
				if(!flag){
					productCG.setProOrgCode(merchant.getProOrgCode());
					productCG.setCityOrgCode(merchant.getCityOrgCode());
					productCG.setCountryOrgCode(merchant.getCountyOrgCode());
					productCG.setOrgCode(merchant.getCountyOrgCode());
					flag=true;
				}
			}
			if(merchant.getCityOrgCode()!=null&&merchant.getProOrgCode()!=null) {
			   if(!flag){
				   productCG.setProOrgCode(merchant.getProOrgCode());
				   productCG.setCityOrgCode(merchant.getCityOrgCode());
				   productCG.setOrgCode(merchant.getCityOrgCode());
					flag=true;
			   }
			}
            if(merchant.getProOrgCode()!=null){
            	 if(!flag){
            		 productCG.setProOrgCode(merchant.getProOrgCode());
            		 productCG.setOrgCode(merchant.getProOrgCode());
				   }
            }
		}
		
	}
	//中间表插入
	public void InsertTranfer(ProductCG productCG,Product productcq){
		Transfer transfer1=new Transfer();
		transfer1.setOldId(String.valueOf(productcq.getId()));
		transfer1.setNewId(String.valueOf(productCG.getProductId()));
		transfer1.setType(TransferTypeEnum.product_id);
		transferMapper.insert(transfer1);
	}
	public void setIsMarketable(Product productcq,ProductCG productCG){
		 /** 0、is_delete=true,商品为删除状态
		   * 1、	is_marketable=false，rack_time不为空，设置为2已下架
	         2、	is_marketable=false，rack_time为空，设置为0未上架
	         3、	is_marketable=true，设置为1已上架
	         4、	新的is_marketable=2下架状态，设置为update_time.
		   */
              if(productcq.getIsDelete()){
            	   productCG.setIsMarketable(ProductStatus.isDeleted.getCode());   
            } else if(!productcq.getIsMarketable()&&productcq.getRackTime()!=null&&!"".equals(productcq.getRackTime())){
			  productCG.setIsMarketable(ProductStatus.offShelf.getCode());
			  productCG.setRackTime(productcq.getRackTime());
			  productCG.setDownTime(productcq.getUpdateTime());
			}else if(!productcq.getIsMarketable()&&productcq.getRackTime()==null){
			  productCG.setIsMarketable(ProductStatus.noShelf.getCode());
			}else if(productcq.getIsMarketable()){
				productCG.setIsMarketable(ProductStatus.onShelf.getCode());
				productCG.setRackTime(productcq.getRackTime());
			}
	}

    public String setProductType(String type){
    	if(type.equals("01")){
    		return ProductType.group.getCode();
    	}else if(type.equals("02")){
    		return ProductType.presell.getCode();
    	}
    	return null;
    }
   
	 private void productRedis(ProductCG product) {
    	 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 boolean flag=false;
         if(product!=null){
             /* 缓存 */
             Map<String, String> hash = new HashMap<String, String>();
             hash.put("is_marketable", ObjectUtils.toString(product.getIsMarketable(), ""));
             hash.put("product_type", ObjectUtils.toString(product.getType(), ""));
             hash.put("price", ObjectUtils.toString(product.getPrice(), ""));
             hash.put("marketPrice", ObjectUtils.toString(product.getMarketPrice(), ""));
             hash.put("product_name", ObjectUtils.toString(product.getName(), ""));
             if(product.getStartTime()!=null){
                 hash.put("start_time", ObjectUtils.toString(product.getStartTime().getTime(), ""));
             }
             if(product.getEndTime()!=null){
                 hash.put("end_time", ObjectUtils.toString(product.getEndTime().getTime(), ""));
             }
             if(product.getDeliveryMoney()!=null&&product.getDeliveryMoney()>0){
                 hash.put("delivery_money", ObjectUtils.toString(product.getDeliveryMoney(), ""));
             }
                 ProductGroupCG groupProduct = product.getProductGroup();
                 if(groupProduct!=null){
                     hash.put("true_buyer_number", ObjectUtils.toString(groupProduct.getTrueBuyerNumber(), ""));
                     hash.put("virtual_buyer_number", ObjectUtils.toString(groupProduct.getVirtualBuyerNumber(), ""));
							hash.put("expire_start_time", ObjectUtils.toString(groupProduct.getExpireStartTime().getTime(), ""));
                     if(groupProduct.getExpireEndTime()!=null){
                         hash.put("expire_end_time", ObjectUtils.toString(groupProduct.getExpireEndTime().getTime(), ""));
                     }
                 }
              
                 ProductPresellCG presellProduct = product.getProductPresell();
                 if(presellProduct!=null){
                     if(presellProduct.getDeliveryStartTime()!=null){
                         hash.put("delivery_start_time", ObjectUtils.toString(presellProduct.getDeliveryStartTime().getTime(), ""));
                     }
                     if(presellProduct.getDeliveryEndTime()!=null){
                         hash.put("delivery_end_time", ObjectUtils.toString(presellProduct.getDeliveryEndTime().getTime(), ""));
                     }
                     hash.put("true_buyer_number", ObjectUtils.toString(presellProduct.getTrueBuyerNumber(), ""));
                     hash.put("virtual_buyer_number", ObjectUtils.toString(presellProduct.getVirtualBuyerNumber(), ""));
					 hash.put("expire_start_time", ObjectUtils.toString(presellProduct.getExpireStartTime(), ""));
                     if(presellProduct.getExpireEndTime()!=null){
                         hash.put("expire_end_time", ObjectUtils.toString(presellProduct.getExpireEndTime().getTime(), ""));
                     }
                     flag=presellProduct.getClusterState();
                     hash.put("cluster_state", ObjectUtils.toString(BooleanUtils.toInteger(flag, 1, 0, 0), ""));
                 }
             String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId());
             LogCvt.info("缓存ID,Merchant_ID:"+product.getMerchantId()+",Product_ID:"+product.getProductId());
             redis.putMap(key, hash);
         }
     }
	 public void setBuyLimit(Product productcq,ProductDetail productDetail){
		 ProductLimit productLimit=(ProductLimit) transferMap.get(productcq.getId()+PORID);
		 ProductBuyLimit productLimitCG=new ProductBuyLimit();
		     if(productLimit!=null){
		    	 productLimitCG.setStartTime(productLimit.getStartTime().getTime());
		    	 productLimitCG.setEndTime(productLimit.getEndTime().getTime());
		    	 productLimitCG.setMax(productLimit.getMax());
		    	 productLimitCG.setMaxVip(MAXVIP);
		    	 productLimitCG.setMaxCard(MAXCARD);
		    	 productDetail.setBuyLimit(productLimitCG);
		     }
	 }
	 public void setProductCatgory(ProductCG productCG,ProductDetail productDetail){
		 ProductCategoryInfoCG productCategory=null;
		 List<ProductCategoryInfoCG> productCategoryInfo = new ArrayList<ProductCategoryInfoCG>();
		 String treePath=productCG.getCategoryTreePath();
		 if(treePath != null){
			 productCategory = new ProductCategoryInfoCG();
			 productCategory.setProductCategoryId(Long.valueOf(treePath));
			 productCategoryInfo.add(productCategory);
		    productDetail.setProductCategoryInfo(productCategoryInfo);
	    }
	 }
	 public void setImageInfo(Product productcq,ProductDetail productDetail){
		ProductImageMapper productImageMapper=cqSqlSession.getMapper(ProductImageMapper.class);
		List<ProductImage>  productImages=productImageMapper.selectProductImageByProductId(productcq.getId());
		List<ProductImageCG> productImageCGs=new ArrayList<ProductImageCG>();
		if(productImages.size()>0&&productImages!=null){
			for(ProductImage productImage:productImages){
				ProductImageCG productImageCG=new ProductImageCG();
				if(productImage.getTitle()!=null&&!"".equals(productImage.getTitle())){
					productImageCG.setTitle(productImage.getTitle());
				}
				if(productImage.getSource()!=null&&!"".equals(productImage.getSource())){
					productImageCG.setSource(Constans.getSource(productImage.getSource()));
				}
				if(productImage.getLarge()!=null&&!"".equals(productImage.getLarge())){
					productImageCG.setLarge(Constans.getLarge(productImage.getLarge()));
				}
				if(productImage.getMedium()!=null&&!"".equals(productImage.getMedium())){
					productImageCG.setMedium(Constans.getMedium(productImage.getMedium()));
				}
				if(productImage.getThumbnail()!=null&&!"".equals(productImage.getThumbnail())){
					productImageCG.setThumbnail(Constans.getThumbnail(productImage.getThumbnail()));
				}
				productImageCGs.add(productImageCG);
			}	
			productDetail.setImageInfo(productImageCGs);
		}else{
			if(productcq.getImage()!=null){
				ProductImageCG productImageCG=new ProductImageCG();
				productImageCG.setLarge(Constans.converImage(productcq.getImage()));
				productImageCG.setMedium(Constans.converImage(productcq.getImage()));
				productImageCG.setThumbnail(Constans.converImage(productcq.getImage()));
				productImageCGs.add(productImageCG);
				productDetail.setImageInfo(productImageCGs);
			}else{
				LogCvt.info("image.........ProductId为"+productcq.getId()+"没有默认图片");
			}
		}
	 }
	 public void setOrgCodes(Product productcq,ProductDetail productDetail){
		
		       
		 if(productcq.getType().getCode().equals("02")){
		    	   List<String> orgCodes=new ArrayList<String>();
		    	   MerchantOutletBankMapper merchantOutletBankMapper=cqSqlSession.getMapper(MerchantOutletBankMapper.class);
		    	   MerchantOutletMapper merchantOutletMapper=cqSqlSession.getMapper(MerchantOutletMapper.class);
		    	   List<MerchantOutletBank> merchantOutletBanks=merchantOutletBankMapper.findMerchantOutletBankByProductId(productcq.getId());
		    	   if(merchantOutletBanks.size()>0&&merchantOutletBanks!=null){
		    		   Map<Long,ProductCityArea> cityAreaMap=new HashMap<Long,ProductCityArea>();
		    		   Map<String,String> orgMap=new HashMap<String,String>();//用来存储
		    		   List<ProductCityOutlet> orgOutlets=new ArrayList<ProductCityOutlet>();
		    		   List<ProductCityArea> productCityAreas=new ArrayList<ProductCityArea>();
		    		   for(MerchantOutletBank merchantOutletBank:merchantOutletBanks){
		    			   /**
		    			    * org_outlets&&city_areas
		    			    * city_areas:cityAreaMap也是去除相同门店对应的地区hasArea判断是否已经含有相同的地区，如果有的话就不记录了
		    			    */
		    			   MerchantOutlet merchantOulet=merchantOutletMapper.selectOutletByOultetId(merchantOutletBank.getMerchantOutletId());
		    			   Transfer transArea=(Transfer)transferMap.get(merchantOulet==null?"":String.valueOf(merchantOulet.getAreaId())+TransferTypeEnum.area_id);
		    			   if(transArea!=null){
		    				   AreaCG area =(AreaCG)transferMap.get(Long.parseLong(transArea.getNewId()));
		    				   String[] treepath=splitAreaTreePath(area.getTreePath());
		    				   if(treepath.length>=2){
		    					   /*****************city_areas******************/
		    					   ProductCityArea cityArea=new ProductCityArea();
		    					   List<ProductArea> productAreas=new ArrayList<ProductArea>();
		    					   AreaCG areacity =(AreaCG)transferMap.get(Long.parseLong(treepath[1]));
		    					   cityArea.setCityId(Long.parseLong(treepath[1]));
		    					   cityArea.setCityName(areacity.getName());
		    					   if(treepath.length==3){
		    						   boolean hasArea=true;
		    						   if(cityAreaMap.containsKey(Long.parseLong(treepath[1]))){
		    							   ProductCityArea productCityArea=cityAreaMap.get(Long.parseLong(treepath[1]));
		    							   List<ProductArea> productAreas1=productCityArea.getCountys();
		    							   for(int k=0;k<productAreas.size();k++){
		    								   if(Long.parseLong(treepath[2])==productAreas.get(k).getAreaId()){
		    									   hasArea=false;
		    								   }
		    							   }
		    						   }
		    						   if(hasArea){
		    							   ProductArea productArea=new ProductArea();
		    							   productArea.setAreaId(Long.parseLong(treepath[2]));
		    							   AreaCG areacounty =(AreaCG)transferMap.get(Long.parseLong(treepath[2]));
		    							   productArea.setAreaName(areacounty.getName());
		    							   productAreas.add(productArea);
		    							   cityArea.setCountys(productAreas);
		    							   productCityAreas.add(cityArea);
		    							   cityAreaMap.put(cityArea.getCityId(), cityArea);
		    						   }
		    					   } //仅针对预售
		        					   /**
					    			    * OrgCodes处理
					    			    * 使用orgMap去除重复的bankOrgCode,多个门店对应同一个bankOrgCode的话只记录一个
					    			    */
					    			   String bankOrgCode="";
					    			   //特殊处理,防止出现最高级机构，没有父级机构的情况
					    			   if(merchantOutletBank.getParentBankOrg()!=null){
					    				   bankOrgCode=Constans.filterOrg(merchantOutletBank.getParentBankOrg());
					    			   }else{
					    				   bankOrgCode=Constans.filterOrg(merchantOutletBank.getBankOrg());
					    			   }
					    			   if(!orgMap.containsKey(bankOrgCode)){
					    				   orgCodes.add(bankOrgCode);
					    				   orgMap.put(bankOrgCode,bankOrgCode);
					    			   }
			    					   /*************org_outlets**************/
					    			   ProductOutlet productOutlet=new ProductOutlet();
					    			   List<ProductOutlet> productOutlets=new ArrayList<ProductOutlet>();
					    			   ProductCityOutlet orgOutlet=new ProductCityOutlet();
			    					   orgOutlet.setCityId(Long.parseLong(treepath[1]));
			    					   orgOutlet.setCityName(areacity.getName());
			    					   productOutlet.setAddress((String)checkIsEmpty(merchantOulet.getAddress(),STRING));
			    					   productOutlet.setOrgCode(Constans.filterOrg(merchantOutletBank.getBankOrg()));
			    					   productOutlet.setOutletName((String)checkIsEmpty(merchantOulet.getName(),STRING));
			    					   Transfer tranfer=(Transfer)transferMap.get(String.valueOf(merchantOulet.getId())+TransferTypeEnum.outlet_id);
			    					   productOutlet.setOutletId(tranfer.getNewId());
			    					   productOutlet.setPhone((String)checkIsEmpty(merchantOulet.getTel(),STRING));
			    					   productOutlets.add(productOutlet);
			    					   orgOutlet.setOrgOutlets(productOutlets);
			    					   orgOutlets.add(orgOutlet); 
		    					   }
		    				   }else{
		    					   LogCvt.info("ProductId为"+productcq.getId()+"没有城市ID");
		    				   }
		    			   }
		    		   if(orgCodes.size()>0){
		    			   productDetail.setOrgCodes(orgCodes);
		    		   }
		    		   if(productCityAreas.size()>0){
		    			   productDetail.setCityAreas(productCityAreas);
		    		   }
		    		   if(orgOutlets.size()>0){
		    			   productDetail.setOrgOutlets(orgOutlets);
		    		   }
		    		   }
		    	   }else if(productcq.getType().getCode().equals("01")){
		    		   List<String> orgCodes=new ArrayList<String>();
			    	   ProductMerchantOutletMapper productMerchantOutletMapper=cqSqlSession.getMapper(ProductMerchantOutletMapper.class);
			    	   MerchantOutletMapper merchantOutletMapper=cqSqlSession.getMapper(MerchantOutletMapper.class);
			    	   List<ProductMerchantOutlet> productMerchantOutlets=productMerchantOutletMapper.findProductOutletByProductId(productcq.getId());
			    	   if(productMerchantOutlets.size()>0&&productMerchantOutlets!=null){
			    		   Map<Long,ProductCityArea> cityAreaMap=new HashMap<Long,ProductCityArea>();
			    		   List<ProductCityOutlet> orgOutlets=new ArrayList<ProductCityOutlet>();
			    		   List<ProductCityArea> productCityAreas=new ArrayList<ProductCityArea>();
			    		   for(ProductMerchantOutlet productMerchantOutlet:productMerchantOutlets){
			    			   /**
			    			    * org_outlets&&city_areas
			    			    * city_areas:cityAreaMap也是去除相同门店对应的地区hasArea判断是否已经含有相同的地区，如果有的话就不记录了
			    			    */
			    			   MerchantOutlet merchantOulet=merchantOutletMapper.selectOutletByOultetId(productMerchantOutlet.getMerchantOutletId());
			    			   Transfer transArea=(Transfer)transferMap.get(String.valueOf(merchantOulet.getAreaId())+TransferTypeEnum.area_id);
			    			   if(transArea!=null){
			    				   AreaCG area =(AreaCG)transferMap.get(Long.parseLong(transArea.getNewId()));
			    				   String[] treepath=splitAreaTreePath(area.getTreePath());
			    				   if(treepath.length>=2){
			    					   /*****************city_areas******************/
			    					   ProductCityArea cityArea=new ProductCityArea();
			    					   List<ProductArea> productAreas=new ArrayList<ProductArea>();
			    					   AreaCG areacity =(AreaCG)transferMap.get(Long.parseLong(treepath[1]));
			    					   cityArea.setCityId(Long.parseLong(treepath[1]));
			    					   cityArea.setCityName(areacity.getName());
			    					   if(treepath.length==3){
			    						   boolean hasArea=true;
			    						   if(cityAreaMap.containsKey(Long.parseLong(treepath[1]))){
			    							   ProductCityArea productCityArea=cityAreaMap.get(Long.parseLong(treepath[1]));
			    							   List<ProductArea> productAreas1=productCityArea.getCountys();
			    							   for(int k=0;k<productAreas.size();k++){
			    								   if(Long.parseLong(treepath[2])==productAreas.get(k).getAreaId()){
			    									   hasArea=false;
			    								   }
			    							   }
			    						   }
			    						   if(hasArea){
			    							   ProductArea productArea=new ProductArea();
			    							   productArea.setAreaId(Long.parseLong(treepath[2]));
			    							   AreaCG areacounty =(AreaCG)transferMap.get(Long.parseLong(treepath[2]));
			    							   productArea.setAreaName(areacounty.getName());
			    							   productAreas.add(productArea);
			    							   cityArea.setCountys(productAreas);
			    							   productCityAreas.add(cityArea);
			    							   cityAreaMap.put(cityArea.getCityId(), cityArea);
			    						   }
			    					   }
		    	            }
			    	     }
			    	 }
			    		   if(productCityAreas.size()>0){
			    			   productDetail.setCityAreas(productCityAreas);
			    		   }
			    	   }
			    	   
		    	   }
		    	  // refresh(orgMap,orgCodes,cityAreaMap,productCityAreas);//重新初始化
		       
			 

	 }
	 
	 
	 /*******************************组件方法*****************************************/
	 


	 
	 /**
 ************************************************************************************
                                                                                   工具方法
  ************************************************************************************                                                                                 
 */
	 public boolean IsLimit(ProductLimit productLimit){
		 if(productLimit!=null){
			 if(productLimit.getMax()>0){
				 return true;
			 }
		 }
		 return false;
	 }
	 //金额转换成int
	    public  Integer DealWithPrice(String price,boolean flag){
	    	if(price!=null){
	    		Double newPrice=Double.parseDouble(price);
	    		if(flag){//为true则需要乘以1000
	    			Double newPrice1=newPrice*1000;
	    			int newPrice2=newPrice1.intValue();
	    			return newPrice2;
	    		}else{
	    			int newPrice2=newPrice.intValue();
	    			return newPrice2;
	    		}
	    	}
	    	return 0;
		       
		}
	    
	    public Object checkIsEmpty(Object obj,String jobj) {
	    	if(obj instanceof String){
	    		if(obj!=null){
	    			return obj;
	    		}
	    	}
	    	if(obj instanceof Integer || obj instanceof Double){
	    		if(obj!=null){
	    			return obj;
	    		}
	    	}
	    	if(obj instanceof Date){
	    		if(obj!=null){
	    			return obj;
	    		}
	    	}
	    	if(obj ==null){
	    		if(jobj.equals(STRING)){
	    			return "";
	    		}
	    		if(jobj.equals(INTEGER)){
	    			return 0;
	    		}
	    		if(jobj.equals(DOUBLE)){
	    			return 0.0;
	    		}
	    		if(jobj.equals(LONG)){
	    			return 0L;
	    		}
	    	}
	    	return null;
	    }
	   
	    public int setAvgPoint(Product productcq){
	    	/**
	    	 * 将评论表中分数相加除以总数
	    	 */
	    	 ProductCommentMapper productCommentMapper=cqSqlSession.getMapper(ProductCommentMapper.class);
		    int Point=0;
		    Double total=0.0;
		    total=  productCommentMapper.selectProductCommentByProductIdOnInt(productcq.getId());
		    if(total!=null){
		    	Point=changInt(total,1);
		    }
//			List<ProductComment> productComments=productCommentMapper.selectProductCommentByProductId(productcq.getId());
//			if(productComments!=null&&productComments.size()>0){
//				for(ProductComment productComment:productComments){
//					int startLevel=0;
//					double pricePoint=0;
//					double qualityPoint=0;
//					double servePoint=0;
//					double nowtotal=0;
//					if(productComment.getEnvironmentPoint()!=null){
//						startLevel=productComment.getEnvironmentPoint();
//					}
//					if(productComment.getPricePoint()!=null){
//						pricePoint=productComment.getPricePoint();
//					}
//					if(productComment.getQualityPoint()!=null){
//						qualityPoint=productComment.getQualityPoint();
//					}
//					if(productComment.getServerPoint()!=null){
//						servePoint=productComment.getServerPoint();
//					}	
//					nowtotal=startLevel+pricePoint+qualityPoint+servePoint;
//					total+=nowtotal;
//				}
			
//			}
		 
		 return Point;
	 }
	    public int changInt(double total,int size){
	   	 double d=total/size;
	   	 return Integer.parseInt(new java.text.DecimalFormat("0").format(d));
	    }
	    private void setStrinRedis(String key,String value) {
	        redis.putString(key, value);
	    }
		public boolean IsClusterState(String state){
	 		if(state!=null){
	 			if(state.equals("1")){
	 				return true;
	 			}else{
	 				return false;
	 			}
	 		}else{
	 			return false;
	 		}
	 	}
		public int setIsObject(boolean obj){
			if(obj){
				return 1;
			}
			return 0;
		}
		public void refresh(Map<String,String> orgMap,List<String> orgCodes,Map<Long,ProductCityArea> cityAreaMap,List<ProductCityArea> productCityAreas){
			if(orgCodes.size()>0){
				 for(int i=orgCodes.size();i>0;i--){
					 orgMap.remove(orgCodes.get(i-1));
					 orgCodes.remove(i-1);
				 }
			}
			if(productCityAreas.size()>0){
				for(int i=productCityAreas.size();i>0;i--){
					cityAreaMap.remove(productCityAreas.get(i-1));
					productCityAreas.remove(i-1);
				}
			}
		}
		public  String[] splitAreaTreePath(String treePath){
			String[] areas=treePath.split(",");
			return areas;
		}
		 public String setPlatType(String type){
		    	if(type.equals("01")){
		    		return GROUPPLAT;
		    	}else{
		    		return PRESELLPLAY;
		    	}
		    	
		    	
		    }
		 public boolean setClusterType(String type){
			 if(type.equals("0")){
				 return false;
			 }else{
				 return true;
			 }
		 }
}

