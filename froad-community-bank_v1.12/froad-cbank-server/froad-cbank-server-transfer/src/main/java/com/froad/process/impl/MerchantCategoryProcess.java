package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mappers.MerchantCategoryMapper;
import com.froad.db.chonggou.entity.MerchantCategoryCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantCategoryMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.MerchantCategory;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;
import com.froad.util.RedisKeyUtil;

public class MerchantCategoryProcess extends AbstractProcess {
	
	private MerchantCategoryMapper merchantCategoryMapper;
	private MerchantCategoryMapperCG merchantCategoryMapperCG;
	private TransferMapper transferMapper;
	
	public MerchantCategoryProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		LogCvt.info("商户分类表cb_merchant_category 数据迁移开始");
		merchantCategoryMapper = ahSqlSession.getMapper(MerchantCategoryMapper.class);
		merchantCategoryMapperCG = sqlSession.getMapper(MerchantCategoryMapperCG.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		List<MerchantCategory> merchantCategories = merchantCategoryMapper.findAll();
		if(Checker.isEmpty(merchantCategories)){
			LogCvt.info("原安徽表fft_merchant_category 无数据需迁移");
			return;
		}
		
		LogCvt.info("原安徽表fft_merchant_category 共有"+merchantCategories.size()+"条数据需迁移");
		for(MerchantCategory category : merchantCategories){
			MerchantCategoryCG categoryCG = new MerchantCategoryCG();
			categoryCG.setClientId(Constans.clientId);
			categoryCG.setName(category.getName());
			categoryCG.setParentId(Long.valueOf(ObjectUtils.toString(category.getParentId(), "0")));
			categoryCG.setTreePath(ObjectUtils.toString(category.getTreePath()));
			categoryCG.setIsDelete(false);
			categoryCG.setIcoUrl(Constans.converImage(category.getIcoUrl()));
			categoryCG.setOrderValue(category.getOrderValue());
			long result = merchantCategoryMapperCG.addMerchantCategory(categoryCG);
			if(result > -1){
				LogCvt.info("商户分类[categoryId:"+category.getId()+"] 数据迁移成功");
				// 添加缓存
				addMerchantCategoryRedis(categoryCG);
				
				Transfer transfer = new Transfer();
				transfer.setOldId(String.valueOf(category.getId()));
				transfer.setNewId(String.valueOf(categoryCG.getId()));
				transfer.setType(TransferTypeEnum.merchant_category_id);
				transferMapper.insert(transfer);
			}
		}
		
		Map<Long, Long> categoryInfo = getCategoryInfo();
		
		// 更新parent_id/tree_path
		for(MerchantCategory category : merchantCategories){
			if(Checker.isEmpty(categoryInfo.get(category.getId()))) continue;
			
			MerchantCategoryCG categoryCG = new MerchantCategoryCG();
			categoryCG.setId(categoryInfo.get(category.getId()));
			
			if(Checker.isNotEmpty(category.getParentId())){
				categoryCG.setParentId(categoryInfo.get(category.getParentId()));
				updateParentIdRedis(categoryCG);
			}

			if(Checker.isNotEmpty(category.getTreePath())){
				String treePath[] = category.getTreePath().split(" ");
				StringBuilder tree = new StringBuilder();
				for(String s : treePath){
					tree.append(categoryInfo.get(Long.valueOf(s))+" ");
				}
				categoryCG.setTreePath(tree.toString().trim());
				updateTreePathRedis(categoryCG);
			}
			merchantCategoryMapperCG.updateMerchantCategory(categoryCG);
		}
		
		ConsoleLogger.info("商户分类表cb_merchant_category 数据迁移结束");
	}
	
	
	private Map<Long, Long> getCategoryInfo(){
		List<Transfer> list = transferMapper.queryGroupList(TransferTypeEnum.merchant_category_id);
		Map<Long, Long> info = new HashMap<Long, Long>();
		for(Transfer transfer : list){
			info.put(Long.valueOf(transfer.getOldId()), Long.valueOf(transfer.getNewId()));
		}
		return info;
	}
	
	private void addMerchantCategoryRedis(MerchantCategoryCG categoryCG){
		String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(Constans.clientId, categoryCG.getId());
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("name", ObjectUtils.toString(categoryCG.getName()));
		hash.put("tree_path", ObjectUtils.toString(categoryCG.getTreePath()));
		hash.put("parent_id", ObjectUtils.toString(categoryCG.getParentId()));
		boolean result = "OK".equals(redis.putMap(key, hash));
		LogCvt.info("商户分类缓存key==>"+key+" 设置"+(result?"成功":"失败"));
	}
	
	private void updateParentIdRedis(MerchantCategoryCG categoryCG){
		String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(Constans.clientId, categoryCG.getId());
		Long result = redis.hset(key, "parent_id", String.valueOf(categoryCG.getParentId()));
		LogCvt.info("商户分类缓存key==>"+key+" parent_id更新"+(result > -1?"成功":"失败"));
	}
	
	private void updateTreePathRedis(MerchantCategoryCG categoryCG){
		String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(Constans.clientId, categoryCG.getId());
		Long result = redis.hset(key, "tree_path", categoryCG.getTreePath());
		LogCvt.info("商户分类缓存key==>"+key+" tree_path更新"+(result != -1?"成功":"失败"));
	}
}
