package com.froad.process.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.ProductGroup;
import com.froad.db.ahui.mappers.ProductGroupMapper;
import com.froad.db.chonggou.entity.ProductCG;
import com.froad.db.chonggou.entity.ProductGroupCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.RedisKeyUtil;

public class ProductGroupProcess extends AbstractProcess {
    private Map<String,Transfer> transferTypeEnumMap=new HashMap<String,Transfer>();
    private String CLIENTID="anhui";
	public ProductGroupProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		LogCvt.info("ProductGroupProcess:Process.........");
		insertProductGroup();
		
	}
   public void insertProductGroup(){
	   try{/**************************************数据库操作***************************************************/
		   TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		   ProductGroupMapper poductGroupMapper=ahSqlSession.getMapper(ProductGroupMapper.class);
		   com.froad.db.chonggou.mappers.ProductMapper poductGroupMapperCG=sqlSession.getMapper(com.froad.db.chonggou.mappers.ProductMapper.class);
		   /**************************************数据库操作***************************************************/
		   SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		   Date date=new Date();
		   List<Transfer> transferProducts=transferMapper.queryGroupList(TransferTypeEnum.product_id);
		   List<Transfer> transferMerchants=transferMapper.queryGroupList(TransferTypeEnum.merchant_id);
			for(Transfer transfer:transferProducts){
				transferTypeEnumMap.put(transfer.getOldId()+transfer.getType(), transfer);
			}
			for(Transfer transfer:transferMerchants){
				transferTypeEnumMap.put(transfer.getOldId()+transfer.getType(), transfer);
			}
			LogCvt.info("中间表内存加载,transferProducts.size"+transferProducts.size()+",transferMerchants.size"+transferMerchants.size());
		   //获取所有
		   List<ProductGroup> productGroups=poductGroupMapper.selectAllProductGroup();
		   LogCvt.info("团购大小："+productGroups.size());
		   /**
		    * 依赖商品ID,商户ID原来安徽表关联fft_product和fft_product_group取出旧ID以及merchant_id
		    * merchant_id主要用于缓存
		    */
		   for(ProductGroup productGroup:productGroups){
			   LogCvt.info("ProductOID="+productGroup.getId());
			   ProductGroupCG productGroupCG=new ProductGroupCG();
			   productGroupCG.setClientId(CLIENTID);
			   Transfer transfer=transferTypeEnumMap.get(productGroup.getId()+TransferTypeEnum.product_id.getCode());
			   if(transfer!=null){
				   LogCvt.info("ProductId:"+transfer.getNewId());
				   productGroupCG.setProductId(transfer.getNewId());
				   if(productGroup.getCreateTime()!=null&&!"".equals(productGroup.getCreateTime())){
					   productGroupCG.setCreateTime(productGroup.getCreateTime());
				   }else{
					   String datetime=format.format(date);
					   try {
						   productGroupCG.setCreateTime(format.parse(datetime));
					   } catch (ParseException e) {
						   e.printStackTrace();
					   }
				   }		   
				   if(productGroup.getTrueBuyerNumber()!=null&&!"".equals(productGroup.getTrueBuyerNumber())){
					   productGroupCG.setTrueBuyerNumber(productGroup.getTrueBuyerNumber());
				   }
				   if(productGroup.getVirtualBuyerNumber()!=null&&!"".equals(productGroup.getVirtualBuyerNumber())){
					   productGroupCG.setVirtualBuyerNumber(productGroup.getVirtualBuyerNumber());
				   }
				   if(productGroup.getExpireTime()!=null){
					   productGroupCG.setExpireEndTime(productGroup.getExpireTime());
				   }
				//   String start_time="2001-01-01 00:00:00";
//				   try {
					   productGroupCG.setExpireStartTime(productGroup.getCreateTime());
//				   } catch (ParseException e) {
//					   e.printStackTrace();
//				   }
				   poductGroupMapperCG.addProductGroup(productGroupCG);
				   //放入缓存
				//   setRedis(productGroupCG,poductGroupMapperCG,productGroup,transferMapper);
			   }else{
				   LogCvt.info("product_id不存在,old_product_id="+productGroup.getId());
			   }
			  
		   }
	   }catch(Exception e){
		   LogCvt.error(e.getMessage(),e);
	   }
	   
   }
   public void setRedis(ProductGroupCG productGroupCG,com.froad.db.chonggou.mappers.ProductMapper poductGroupMapperCG,ProductGroup product,TransferMapper transferMapper){
	   try{
		   //查询新的ID
		   Transfer transfer=transferTypeEnumMap.get(product.getMerchantId()+TransferTypeEnum.merchant_id.getCode());
		   if(transfer!=null){
			   LogCvt.info("newMerchantId"+transfer.getNewId());
			   Map<String, String> hash = new HashMap<String, String>();
			   ProductCG productCG=new ProductCG();
			   productCG.setProductId(productGroupCG.getProductId());
			   productCG.setClientId(productGroupCG.getClientId());
			   hash.put("true_buyer_number", ObjectUtils.toString(productGroupCG.getTrueBuyerNumber(), ""));
			   hash.put("virtual_buyer_number", ObjectUtils.toString(productGroupCG.getVirtualBuyerNumber(), ""));
			   if(productGroupCG.getExpireStartTime()!=null){
				   hash.put("expire_start_time", ObjectUtils.toString(productGroupCG.getExpireStartTime().getTime(), ""));
			   }
			   if(productGroupCG.getExpireEndTime()!=null){
				   hash.put("expire_end_time", ObjectUtils.toString(productGroupCG.getExpireEndTime().getTime(), ""));
			   }
			   String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(productGroupCG.getClientId(), transfer.getNewId(), productGroupCG.getProductId());
			   LogCvt.info("加入缓存....productGroupCGID"+productGroupCG.getProductId()+",MerchantId:"+transfer.getNewId());
			   if(key!=null){
				   redis.putMap(key, hash);
			   }
		   }else{
			   LogCvt.info("==============================================缓存加入失败,对应的merchant_id不存在,原ID为"+product.getMerchantId());
		   }
	   }catch(Exception e){
		   LogCvt.error(e.getMessage(),e);
	   }
	  
	   
   }
}

