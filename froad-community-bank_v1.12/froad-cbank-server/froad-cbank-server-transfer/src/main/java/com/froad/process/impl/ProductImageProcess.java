package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mappers.ProductImageMapper;
import com.froad.db.ahui.mappers.ProductMapper;
import com.froad.db.chonggou.entity.ProductDetail;
import com.froad.db.chonggou.entity.ProductImageCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.ProductImage;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class ProductImageProcess extends AbstractProcess{
       private String CLIENTID="client_id";
       private String PRODUCTID="_id";
       private String TITLE="title";
       private String SOURCE="source";
       private String LARGE="large";
       private String MEDIUM="medium";
       private String THNUMB="thumbnail";
       private String ADDRESS="";//新地址
       private List<Product> productsAnhuis;
       private ProductImageMapper productImageMapper;
	public ProductImageProcess(String name,ProcessServiceConfig config) {
		super(name,config);
	}


	@Override
	public void process() {
		LogCvt.info("ProductImageProcess+Process.........");
		//数据表迁移
		insertProductImage();
	}
    public void insertProductImage(){
    	/*******************************************数据库操作*****************************************************/
        productImageMapper=ahSqlSession.getMapper(ProductImageMapper.class);
    	TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
    	ProductMapper productMapper=ahSqlSession.getMapper(ProductMapper.class);
    	com.froad.db.ahui.mappers.ProductMapper productMapperAnhui = ahSqlSession.getMapper(com.froad.db.ahui.mappers.ProductMapper.class);
    	/*******************************************数据库操作*****************************************************/
    	try{
    		/**
    		 * 1.寻找商品详情并取出
    		 * 2.如果有则将记录删除
    		 * 3.合并
    		 */
    		List<Transfer> transferProducts=transferMapper.queryGroupList(TransferTypeEnum.product_id);
    		productsAnhuis=productMapperAnhui.selectAllProductInAnhui();
    		Map<String,Transfer> transferTypeEnumMap=new HashMap<String,Transfer>();
    		LogCvt.info("中间表内存加载");
			for(Transfer transfer:transferProducts){
				transferTypeEnumMap.put(transfer.getOldId()+transfer.getType(), transfer);
			}
    	//	List<ProductImage> productImages=productImageMapper.selectAllProductImage();
    		for(Product productsAnhui:productsAnhuis){
    			List<ProductImageCG> productImagesCG=new ArrayList<ProductImageCG>();
    			List<ProductImage> productImages=productImageMapper.selectAllProductImageByProductId(productsAnhui.getId());
    			if(productImages!=null&&productImages.size()>0){
    				for(ProductImage productImage:productImages){
    					ProductImageCG pi=new ProductImageCG();
    	    			
    	    			boolean result=false;
    	    			Transfer transfer=transferTypeEnumMap.get(productImage.getProductId()+TransferTypeEnum.product_id.getCode());
    	    		
    	    				if(productImage.getTitle()!=null&&!"".equals(productImage.getTitle())){
    	    					pi.setTitle(productImage.getTitle());
    	    				}
    	    				if(productImage.getSource()!=null&&!"".equals(productImage.getSource())){
    	    					pi.setSource(Constans.getSource(productImage.getSource()));
    	    				}
    	    				if(productImage.getLarge()!=null&&!"".equals(productImage.getLarge())){
    	    					pi.setLarge(Constans.getLarge(productImage.getLarge()));
    	    				}
    	    				if(productImage.getMedium()!=null&&!"".equals(productImage.getMedium())){
    	    					pi.setMedium(Constans.getMedium(productImage.getMedium()));
    	    				}
    	    				if(productImage.getThumbnail()!=null&&!"".equals(productImage.getThumbnail())){
    	    					pi.setThumbnail(Constans.getThumbnail(productImage.getThumbnail()));
    	    				}
    	    				/****************************************************************************/
    	    				productImagesCG.add(pi);
    	    	//			if(productDetail!=null){
    	    					
//    	    					if(productDetail.getImageInfo()!=null){
//    	    						value.put("image_info", JSON.parse(JSonUtil.toJSonString(productDetail.getImageInfo())));
//    	    						result=mongo.update(value, where1, MongoTableName.CB_PRODUCT_DETAIL, "$pullAll")>0;
//    	    						if(result){
//    	    							LogCvt.info("PRODUCTID:"+productImage.getProductId()+"去除成功");
//    	    							DBObject value1=new BasicDBObject();
//    	    							productImagesCG=productDetail.getImageInfo();
//    	    							productImagesCG.add(pi);
//    	    				//			for (ProductImageCG productImageCG : productImagesCG) {
//    	    								value1.put("image_info", JSON.parse(JSonUtil.toJSonString(productImagesCG)));
//    	    								result= mongo.update(value1, where1, MongoTableName.CB_PRODUCT_DETAIL, "$set")>0;
//    						//			}
//    	    						}
//    	    					}
////    	    					else{
////    	    						List<ProductImageCG> productImagesCG=new ArrayList<ProductImageCG>();
////    	    						productImagesCG.add(pi);
////    	    						value.put("image_info", JSON.parse(JSonUtil.toJSonString(productImagesCG)));
////    	    						result= mongo.update(value, where1, MongoTableName.CB_PRODUCT_DETAIL, "$set")>0;
////    	    					}
//    	    				}else{
//    	    					LogCvt.info("PRODUCTID:"+productImage.getProductId()+"不存在");
//    	    				}
    	    			if(setImage(transfer,productImagesCG)){
    	    				LogCvt.info("PRODUCTID:"+productImage.getProductId()+"添加成功");
    	    			}else{
    	    				LogCvt.info("PRODUCTID:"+productImage.getProductId()+"添加失败");
    	    			}
    				}
    			}else{//原图片没有的进行迁移
    				boolean result=false;
    				ProductImageCG pi=new ProductImageCG();
	    			DBObject where1=new BasicDBObject();
	    			DBObject value=new BasicDBObject();
	    			Transfer transfer=transferTypeEnumMap.get(productsAnhui.getId()+TransferTypeEnum.product_id.getCode());
	    			where1.put(CLIENTID, "anhui");
	    			where1.put(PRODUCTID, transfer.getNewId());
	    			if(productsAnhui.getImage()!=null){
	    				pi.setLarge(Constans.converImage(productsAnhui.getImage()));
	    				pi.setMedium(Constans.converImage(productsAnhui.getImage()));
	    				pi.setThumbnail(Constans.converImage(productsAnhui.getImage()));
	    		//	ProductDetail productDetail=mongo.findOne(where1, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
	    			//if(productDetail!=null){
	    				productImagesCG=new ArrayList<ProductImageCG>();
						productImagesCG.add(pi);
						value.put("image_info", JSON.parse(JSonUtil.toJSonString(productImagesCG)));
						result= mongo.update(value, where1, MongoTableName.CB_PRODUCT_DETAIL, "$set")>0;	
//	    			}else{
//	    				LogCvt.info("PRODUCTID:"+productsAnhui.getId()+"不存在");
//	    			}
    			}else{
    				LogCvt.info("PRODUCTID:"+productsAnhui.getId()+"的图片不存在");
    			}
	    			if(result){
	    				LogCvt.info("PRODUCTID:"+productsAnhui.getId()+"添加成功");
	    			}else{
	    				LogCvt.info("PRODUCTID:"+productsAnhui.getId()+"添加失败");
	    			}
    		}
    		}
    	}catch(Exception e){
    		LogCvt.error(e.getMessage(),e);
    	}
    	
    }
    public boolean setImage(Transfer transfer,List<ProductImageCG> productImagesCG){
    	DBObject where1=new BasicDBObject();
		DBObject value=new BasicDBObject();
		where1.put(CLIENTID, "anhui");
		where1.put(PRODUCTID, transfer.getNewId());
    	value.put("image_info", JSON.parse(JSonUtil.toJSonString(productImagesCG)));
       boolean result= mongo.update(value, where1, MongoTableName.CB_PRODUCT_DETAIL, "$set")>0;
 	return result;
    	
    	
    }
}

