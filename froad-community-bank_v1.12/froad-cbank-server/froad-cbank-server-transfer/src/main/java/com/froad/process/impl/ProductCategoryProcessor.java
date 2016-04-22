package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.ProductCategoryCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ModuleID;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.api.ProductCategoryMapper;
import com.froad.fft.persistent.entity.ProductCategory;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SimpleID;

public class ProductCategoryProcessor extends AbstractProcess {
	public ProductCategoryProcessor(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		LogCvt.info("ProductCategoryProcessor:Process.........");
		int i=0;//转换标志
		int j=0;
       /***********************************************数据库操作***********************************************************/			
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		ProductCategoryMapper pcm=ahSqlSession.getMapper(ProductCategoryMapper.class);
		com.froad.db.chonggou.mappers.ProductCategoryMapper pcmCG=sqlSession.getMapper(com.froad.db.chonggou.mappers.ProductCategoryMapper.class);
		Map<Object,Object> transMap=new HashMap<Object,Object>();
		/***********************************************数据库操作***********************************************************/			
		List<ProductCategory> productCategorys=pcm.findAllProductCategory();
		LogCvt.info("productCategorys的大小为"+productCategorys.size());
		//数据迁移开始
		for(ProductCategory productCategory:productCategorys){
			ProductCategoryCG productCategoryCG=new ProductCategoryCG();
			productCategoryCG.setClientId("anhui");
			productCategoryCG.setName(productCategory.getName());
			transMap.put(i, productCategory.getId());
			++i;
			if(productCategory.getTreePath()!=null&&!"".equals(productCategory.getTreePath())){
				productCategoryCG.setTreePath(productCategory.getTreePath());
			}
			productCategoryCG.setParentId(100000000L);
			if(productCategory.getOrderValue()!=null){
				productCategoryCG.setOrderValue(productCategory.getOrderValue());
			}else{
				productCategoryCG.setOrderValue(0);
			}
			
			if(productCategory.getIcoUrl()!=null&&!"".equals(productCategory.getIcoUrl())){
				productCategoryCG.setIcoUrl(productCategory.getIcoUrl());
			}else{
				productCategoryCG.setIcoUrl("");
			}
			pcmCG.addProductCategory(productCategoryCG);
		}
		//额外添加全部分类
         //数据添加完成后再查出来进行修改
		List<ProductCategoryCG> productCategoryCGs=pcmCG.findAllProductCategory();
		//新旧ID转换
		//修改tree_path和父节点
		
		for(ProductCategoryCG productCategoryCG:productCategoryCGs){
			//插入一条信息就依次记录i
			Transfer transfer=new Transfer();
			Long oid=(Long)transMap.get(j);
			transfer.setOldId(oid.toString());
			transfer.setNewId(String.valueOf(productCategoryCG.getId()));
			transfer.setType(TransferTypeEnum.productcategory_id);
			transferMapper.insert(transfer);
			++j;
			if(productCategoryCG.getTreePath()!=null&&!"".equals(productCategoryCG.getTreePath())){
					productCategoryCG.setTreePath(String.valueOf(productCategoryCG.getId()));
			}
			pcmCG.updateProductCategory(productCategoryCG);
			/* redis缓存 */
	        Map<String, String> hash = new HashMap<String, String>();
	        hash.put("name", ObjectUtils.toString(productCategoryCG.getName(), ""));
	        hash.put("tree_path", ObjectUtils.toString(productCategoryCG.getTreePath(),""));
	        hash.put("parent_id", ObjectUtils.toString(productCategoryCG.getParentId(), ""));
	        hash.put("is_delete", BooleanUtils.toString(productCategoryCG.getIsDelete(), "1", "0", "0"));
	        String key = RedisKeyUtil.cbbank_product_category_client_id_product_category_id(productCategoryCG.getClientId(), productCategoryCG.getId());
	        RedisManager redis = new RedisManager();
	        redis.putMap(key, hash);
		}
	}
	
	
   public String splitSimple(String simple){
	   if(simple.contains("p")){
		   return simple.replace("p", "").toString();
	   }
	   return simple;
   }
}

