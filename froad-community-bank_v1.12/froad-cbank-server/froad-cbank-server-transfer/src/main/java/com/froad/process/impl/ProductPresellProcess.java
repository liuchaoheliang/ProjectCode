package com.froad.process.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.ProductPresell;
import com.froad.db.ahui.mappers.ProductPresellMapper;
import com.froad.db.chonggou.entity.ProductCG;
import com.froad.db.chonggou.entity.ProductPresellCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.RedisKeyUtil;

public class ProductPresellProcess extends AbstractProcess{
	private String CLIENTID="anhui";
	public ProductPresellProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		LogCvt.info("ProductPresellProcess:Process.........");	
		inserProductPresell();
	}
    public void inserProductPresell(){
    	try{
    		/**********************************数据库操作*********************************************/
    		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
    		ProductPresellMapper poductpresellMapper=ahSqlSession.getMapper(ProductPresellMapper.class);
    		com.froad.db.chonggou.mappers.ProductMapper poductpresellMapperCG=sqlSession.getMapper(com.froad.db.chonggou.mappers.ProductMapper.class);
    		/**********************************数据库操作*********************************************/
    		/**
    		 * 1迁移预售表
    		 * 2加入预售缓存
    		 */
    		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    		List<ProductPresell>  productPreslls=poductpresellMapper.selectAllProductPresell();
    		LogCvt.info("ProductPresell.size"+productPreslls.size());
    		for(ProductPresell productPresll:productPreslls){
    			ProductPresellCG productPresllCG=new ProductPresellCG();
    			productPresllCG.setClientId(CLIENTID);
    			Transfer transfer=transferMapper.queryNewId(String.valueOf(productPresll.getId()), TransferTypeEnum.product_id);
    			LogCvt.info("ProductPresell"+productPresll.getId());
    			if(transfer!=null){
    				productPresllCG.setProductId(transfer.getNewId());
        			productPresllCG.setMaxPerOutlet(0);
        			if(productPresll.getCreateTime()!=null){
        				productPresllCG.setCreateTime(productPresll.getCreateTime());
        			}
        			if(productPresll.getDeliveryStartTime()!=null){
        				productPresllCG.setDeliveryStartTime(productPresll.getDeliveryStartTime());
        			}
        			if(productPresll.getDeliveryEndTime()!=null){
        				productPresllCG.setDeliveryEndTime(productPresll.getDeliveryEndTime());
        			}
        			if(productPresll.getTrueBuyerNumber()!=null){
        				productPresllCG.setTrueBuyerNumber(productPresll.getTrueBuyerNumber());
        			}
        			if(productPresll.getVirtualBuyerNumber()!=null){
        				productPresllCG.setVirtualBuyerNumber(productPresll.getVirtualBuyerNumber());
        			}
        			if(productPresll.getClusterState()!=null){
        				if(productPresll.getClusterState().getCode().equals("1")){
        					productPresllCG.setClusterState(true);
        				}
        				if(productPresll.getClusterState().getCode().equals("2")){
        					productPresllCG.setClusterState(false);
        				}
        				if(productPresll.getClusterState().getCode().equals("0")){
        					productPresllCG.setClusterState(false);
        				}
        			}
        			if(productPresll.getClusterType()!=null){
        				productPresllCG.setClusterType(true);
        			}
        			if(productPresll.getTicketExpireTime()!=null){
        				productPresllCG.setExpireEndTime(productPresll.getTicketExpireTime());
        			}
//        			String datetime="2000-01-01 00:00:00";
//        			try {
        				productPresllCG.setExpireStartTime(productPresll.getCreateTime());
//        			} catch (ParseException e) {
//        				e.printStackTrace();
//        			}
        			 
        			poductpresellMapperCG.addProductPresell(productPresllCG);
        		//	setRedis(productPresllCG,poductpresellMapperCG,productPresll,transferMapper);
    			}else{
    				LogCvt.info("FailProductId================================================="+productPresll.getId());
    			}
    			
    		}
    		
    	}catch(Exception e){
    	  LogCvt.error(e.getMessage(),e);
    	}
    }
    public void setRedis(ProductPresellCG productPresllCG,com.froad.db.chonggou.mappers.ProductMapper poductpresellMapperCG,ProductPresell productPresell,TransferMapper transferMapper ){
    	LogCvt.info("开始加入缓存....");
 	   LogCvt.info("true_buyer_number:"+productPresllCG.getTrueBuyerNumber()+",virtual_buyer_number:"+productPresllCG.getVirtualBuyerNumber()+",expire_start_time"+productPresllCG.getExpireStartTime()
 			   +",expire_end_time:"+productPresllCG.getExpireEndTime());
 	   
 	  Transfer transfer=transferMapper.queryNewId(productPresell.getMerchantId(), TransferTypeEnum.merchant_id);
 	  if(transfer!=null){
 			Map<String, String> hash = new HashMap<String, String>();
 	    	ProductCG productCG=new ProductCG();
 	    	productCG.setClientId(productPresllCG.getClientId());
 	    	productCG.setProductId(productPresllCG.getProductId());
 	    	if(productPresllCG.getDeliveryStartTime()!=null){
 	          hash.put("delivery_start_time", ObjectUtils.toString(productPresllCG.getDeliveryStartTime().getTime(), ""));
 	      }
 	      if(productPresllCG.getDeliveryEndTime()!=null){
 	          hash.put("delivery_end_time", ObjectUtils.toString(productPresllCG.getDeliveryEndTime().getTime(), ""));
 	      }
 	      hash.put("true_buyer_number", ObjectUtils.toString(productPresllCG.getTrueBuyerNumber(), ""));
 	      hash.put("virtual_buyer_number", ObjectUtils.toString(productPresllCG.getVirtualBuyerNumber(), ""));
 	      if(productPresllCG.getExpireStartTime()!=null){
 	          hash.put("expire_start_time", ObjectUtils.toString(productPresllCG.getExpireStartTime().getTime(), ""));
 	      }
 	      if(productPresllCG.getExpireEndTime()!=null){
 	          hash.put("expire_end_time", ObjectUtils.toString(productPresllCG.getExpireEndTime().getTime(), ""));
 	      }
 	      hash.put("cluster_state", ObjectUtils.toString(BooleanUtils.toInteger(productPresllCG.getClusterState(), 1, 0, 0), ""));
 	      String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(productPresllCG.getClientId(), productPresell.getMerchantId(), productPresllCG.getProductId());
 	      LogCvt.info("加入缓存....productGroupCGID"+productPresllCG.getProductId()+",MerchantId:"+productPresell.getMerchantId());
 	      redis.putMap(key, hash);
 	  }else{
 		  LogCvt.info("==============================================缓存加入失败,对应的merchant_id不存在,原ID为"+productPresell.getMerchantId());
 	  }
    
    }
}

