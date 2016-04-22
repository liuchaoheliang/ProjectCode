package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.cbank.persistent.entity.ProductCategory;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.ProductCategoryCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.ProductCategoryMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.RedisKeyUtil;
/**
 * 
  * @ClassName: ProductCatoryProcess
  * @Description: TODO
  * @author Yaren Liang 2015年6月25日
  * @modify Yaren Liang 2015年6月25日
 */
public class ProductCatoryProcess extends AbstractProcess {
	//private String CLIENTID="chongqing";
	private com.froad.db.chonggou.mappers.ProductCategoryMapper pcmCG;
	

	public ProductCatoryProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void before() {
		LogCvt.info("ProductCategoryProcessor:删除开始.........");
		pcmCG=sqlSession.getMapper(com.froad.db.chonggou.mappers.ProductCategoryMapper.class);
		pcmCG.deleteProductCategory(Constans.clientId);
	}

	@Override
	public void process() {
		LogCvt.info("ProductCategoryProcessor:Process.........");
           /***********************************************数据库操作***********************************************************/			
			TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
			ProductCategoryMapper pcm=cqSqlSession.getMapper(ProductCategoryMapper.class);
			/***********************************************数据库操作***********************************************************/			
			List<ProductCategory> productCategorys=pcm.findAllProductCategory();
			LogCvt.info("productCategorys的大小为"+productCategorys.size());
			//数据迁移开始
			for(ProductCategory productCategory:productCategorys){
				LogCvt.info("插入的ProductId为"+productCategory.getId());
				ProductCategoryCG productCategoryCG=new ProductCategoryCG();
				productCategoryCG.setClientId(Constans.clientId);
				productCategoryCG.setName(productCategory.getName());
				
				if(productCategory.getTreePath()!=null&&!"".equals(productCategory.getTreePath())){
					productCategoryCG.setTreePath(productCategory.getTreePath());
				}
				if(productCategory.getParentId()!=null&&!"".equals(productCategory.getParentId())){
					productCategoryCG.setParentId(productCategory.getClientId());
				}else{
					productCategoryCG.setParentId(0L);
				}
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
				
			Long id=pcmCG.addProductCategory(productCategoryCG);
			//插入中间表
			Transfer transfer=new Transfer();
			transfer.setOldId(productCategory.getId().toString());
			transfer.setNewId(productCategoryCG.getId().toString());
			transfer.setType(TransferTypeEnum.productcategory_id);
			transferMapper.insert(transfer);
			}
			//额外添加全部分类
	         //数据添加完成后再查出来进行修改
			List<ProductCategoryCG> productCategoryCGs=pcmCG.findAllProductCategory();
			//新旧ID转换
			//修改tree_path和父节点
			for(ProductCategoryCG productCategoryCG:productCategoryCGs){
				LogCvt.info("修改的newProductId为"+productCategoryCG.getId());
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

