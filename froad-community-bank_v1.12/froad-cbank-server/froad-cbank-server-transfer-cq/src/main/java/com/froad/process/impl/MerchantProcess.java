package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.cbank.persistent.entity.Merchant;
import com.froad.cbank.persistent.entity.MerchantCategory;
import com.froad.common.mongo.MerchantDetailMongo;
import com.froad.common.redis.MerchantRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.AreaCG;
import com.froad.db.chonggou.entity.CategoryInfo;
import com.froad.db.chonggou.entity.MerchantCG;
import com.froad.db.chonggou.entity.MerchantCategoryCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.entity.TypeInfo;
import com.froad.db.chonggou.mappers.AreaMapperCG;
import com.froad.db.chonggou.mappers.MerchantCategoryMapperCG;
import com.froad.db.chonggou.mappers.MerchantMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.MerchantCategoryMapper;
import com.froad.db.chongqing.mappers.MerchantMapper;
import com.froad.db.chongqing.mappers.MerchantOutletBankMapper;
import com.froad.db.chongqing.mappers.MerchantTypeRelationMapper;
import com.froad.enums.ModuleID;
import com.froad.enums.TransferTypeEnum;
import com.froad.cbank.persistent.entity.MerchantTypeRelation;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.Constans;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SimpleID;

public class MerchantProcess extends AbstractProcess {

	// 重庆
	private MerchantMapper merchantMapper = null;

	// 重构
	private MerchantMapperCG merchantMapperCG = null;
	
	private AreaMapperCG  areaMapperCG = null;
	
	private TransferMapper transferMapper = null;
	
	private MerchantDetailMongo merchantDetailMongo = null;
	
	private MerchantOutletBankMapper merchantOutletBankMapper = null;
	
	private MerchantCategoryMapper merchantCategoryMapper = null;
	private MerchantCategoryMapperCG merchantCategoryMapperCG = null;
	
	private MerchantTypeRelationMapper merchantTypeRelationMapper = null;
	
	private MerchantRedis merRedis = null;
	
	
	
	private final Map<Long, Long> categoryMap = new HashMap<Long, Long>();
	
	private final Map<String, String> areaMap = new HashMap<String, String>();

	public MerchantProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void before() {
		merchantDetailMongo = new MerchantDetailMongo(mongo);
		merRedis = new MerchantRedis(redis);
		
		merchantMapperCG = sqlSession.getMapper(MerchantMapperCG.class);
		areaMapperCG = sqlSession.getMapper(AreaMapperCG.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		merchantMapper = cqSqlSession.getMapper(MerchantMapper.class);
		merchantOutletBankMapper = cqSqlSession.getMapper(MerchantOutletBankMapper.class);
		
		merchantCategoryMapperCG = sqlSession.getMapper(MerchantCategoryMapperCG.class);
		merchantCategoryMapper = cqSqlSession.getMapper(MerchantCategoryMapper.class);
		merchantTypeRelationMapper = cqSqlSession.getMapper(MerchantTypeRelationMapper.class);
		
		List<Transfer> list = transferMapper.queryGroupList(TransferTypeEnum.area_id);
		for (Transfer t : list) {
			areaMap.put(t.getOldId(), t.getNewId());
		}
		
		
		boolean n = merchantMapperCG.removeMerchant(Constans.clientId);
		if (n) {
			LogCvt.info("重庆迁移数据商户Mysql删除完成!");
		}
		boolean b = merchantDetailMongo.deleteMerchantDetailByClientId(Constans.clientId);
		if(b){
			LogCvt.info("重庆迁移数据商户Mongodb删除完成!");
		}
		boolean flag = merchantCategoryMapperCG.removeMerchantCategory(Constans.clientId);
		if(flag){
			LogCvt.info("重庆迁移数据商户分类Mysql删除完成!");
		}
		
	}

	@Override
	public void process() {
		
		LogCvt.info("商户分类数据迁移开始");
		transferMerchantCategory();
		LogCvt.info("商户分类数据迁移结束");
		
		
		LogCvt.info("商户表数据迁移开始");
		transferMerchant();
		LogCvt.info("商户表数据迁移完成!");

	}
	
	private String getMerchantID() {
		return simpleID.nextId();
	}
	
	private static SimpleID simpleID = new SimpleID(ModuleID.merchant);

	
	public void transferMerchant() {

		MerchantCG merchantCG = null;
		Transfer transfer = null;
		CategoryInfo categoryInfo = null;
		List<CategoryInfo> listCategoryInfo =null;
		TypeInfo typeInfo = null;
		List<MerchantTypeRelation> listType = null;
		List<TypeInfo> listTypeInfo = null;
		
		List<Merchant> list = merchantMapper.selectByCondition(new Merchant());
		
		for (Merchant obj : list) {
			merchantCG = new MerchantCG();
			transfer = new Transfer();
			
			merchantCG.setCreateTime(obj.getCreateTime());
			merchantCG.setClientId(Constans.clientId);
			
			merchantCG.setMerchantId(getMerchantID());
			transfer.setOldId(obj.getId().toString());
			transfer.setNewId(merchantCG.getMerchantId());
			transfer.setType(TransferTypeEnum.merchant_id);
			transferMapper.insert(transfer);
			
			if(StringUtils.isNotBlank(obj.getInterbankAgency())){
				merchantCG.setProOrgCode(Constans.filterOrg(obj.getInterbankAgency()));
			}
			if(StringUtils.isNotBlank(obj.getTravelAgency())){
				merchantCG.setCityOrgCode(Constans.filterOrg(obj.getTravelAgency()));
			}
			if(StringUtils.isNotBlank(obj.getLatticePoint())){
				merchantCG.setCountyOrgCode(Constans.filterOrg(obj.getLatticePoint()));
				merchantCG.setOrgCode(Constans.filterOrg(obj.getLatticePoint()));
			}else{
				if(StringUtils.isNotBlank(obj.getTravelAgency())){
					merchantCG.setOrgCode(Constans.filterOrg(obj.getTravelAgency()));
				}else{
					merchantCG.setOrgCode("000000");
				}
			}
			
			merchantCG.setMerchantName(obj.getName());
			merchantCG.setMerchantFullname(obj.getFullName());
			merchantCG.setLogo(Constans.converImage(obj.getLogo()));
			merchantCG.setAddress(obj.getAddress());
			merchantCG.setPhone(obj.getTel());
			
			merchantCG.setAreaId(Long.parseLong(areaMap.get(obj.getAreaId()+"") == null ? "0" : areaMap.get(obj.getAreaId().toString())));
			
//			int n = merchantOutletBankMapper.findMerchantOutletBankByMerchantId(obj.getId());
			// 重庆农村商业银行就一个银行商户
			if("100000001".equals(obj.getId().toString())){
				merchantCG.setMerchantStatus(true);
			}else{
				merchantCG.setMerchantStatus(false);
			}
			
			if(obj.getIsDelete()){
				merchantCG.setIsEnable(false);
				merchantCG.setDisableStatus("2");
			}else{
				merchantCG.setIsEnable(true);
				merchantCG.setDisableStatus("0");
			}
			
			merchantCG.setIsTop(obj.getIsTop());
			merchantCG.setIntroduce(obj.getIntroduced());
			merchantCG.setLicense(obj.getLicense());
			merchantCG.setTaxReg(obj.getTaxReg());
			merchantCG.setContractBegintime(obj.getContractBegintime());
			merchantCG.setContractEndtime(obj.getContractEndtime());
			merchantCG.setContractStaff(obj.getContractStaff());
			merchantCG.setAuditEndOrgCode("000000");
			
			if(StringUtils.isNotBlank(obj.getLatticePoint())){
				merchantCG.setAuditStartOrgCode(Constans.filterOrg(obj.getLatticePoint()));
			}else{
				if(StringUtils.isNotBlank(obj.getTravelAgency())){
					merchantCG.setAuditStartOrgCode(Constans.filterOrg(obj.getTravelAgency()));
				}else{
					merchantCG.setAuditStartOrgCode("000000");
				}
			}
			
			merchantCG.setAuditState(obj.getAuditState().getCode());
			
			if("1".equals(obj.getOrgVerifyState())){
				merchantCG.setAuditOrgCode("000000");
			}else if("2".equals(obj.getOrgVerifyState())){
				merchantCG.setAuditOrgCode("000000");
			}else if("3".equals(obj.getOrgVerifyState())){
				merchantCG.setAuditOrgCode(Constans.filterOrg(obj.getTravelAgency()));
			}else{
				merchantCG.setProOrgCode("000000");
				merchantCG.setAuditOrgCode("000000");
				merchantCG.setAuditEndOrgCode("000000");
			}
			merchantCG.setAuditStage("0");
			merchantCG.setAuditTime(obj.getAuditTime());
			merchantCG.setAuditComment(obj.getAuditComment());
			merchantCG.setAuditStaff(obj.getReviewStaff());
			merchantCG.setReviewStaff(obj.getReviewStaff());
			merchantCG.setContactName(obj.getContactName());
			merchantCG.setContactPhone(obj.getContactPhone());
			merchantCG.setContactEmail(obj.getContactEmail());
			merchantCG.setLegalName(obj.getLegalName());
			merchantCG.setLegalCredentNo(obj.getLegalCredentNo());
			if(obj.getLegalCredentType() != null && !"".equals(obj.getLegalCredentType())){
				if("身份证".equals(obj.getLegalCredentType())){
					merchantCG.setLegalCredentType(1);
				}
			}else{
				merchantCG.setLegalCredentType(null);
			}
			merchantCG.setComplaintPhone(obj.getComplaintCall());
			merchantCG.setUserOrgCode(merchantCG.getOrgCode());
			merchantCG.setCompanyCredential(obj.getOrgCode());
			
			//转换分类
			listCategoryInfo = new ArrayList<CategoryInfo>();
			categoryInfo = new CategoryInfo();
			categoryInfo.setCategoryId(categoryMap.get(obj.getMerchantCategoryId()));
			categoryInfo.setName(obj.getMerchantCategoryName());
			listCategoryInfo.add(categoryInfo);
			
			
			listType = merchantTypeRelationMapper.findMerchantByMerchantId(obj.getId());
			listTypeInfo = new ArrayList<TypeInfo>();
			
			for (MerchantTypeRelation mobj : listType) {
				typeInfo = new TypeInfo();
				if(100000004 == mobj.getMerchantTypeId().longValue()){
					typeInfo.setMerchantTypeId(100000000l);
					typeInfo.setTypeName("团购");
					typeInfo.setType("0");
					listTypeInfo.add(typeInfo);
				}else if(100000003 == mobj.getMerchantTypeId().longValue()){
					typeInfo.setMerchantTypeId(100000001l);
					typeInfo.setTypeName("直接优惠");
					typeInfo.setType("1");
					listTypeInfo.add(typeInfo);
				}
			}
			
			addMerchant(merchantCG,listCategoryInfo,listTypeInfo);
		}
	}

	public void addMerchant(MerchantCG merchant,
			List<CategoryInfo> categoryInfoList, List<TypeInfo> typeInfoList) {

		if (merchant.getIsTop() == null)
			merchant.setIsTop(false);

		if (merchantMapperCG.addMerchant(merchant) > -1) { // 添加成功

			AreaCG area = areaMapperCG.findAreaById(merchant.getAreaId());
			if (null == area || StringUtils.isBlank(area.getName())) {
				LogCvt.error("区域对象为空不存在！-merchant.getAreaId()--->"
						+ merchant.getAreaId() + "商户ID:"+merchant.getMerchantId());
			}

			/********************** 操作Mongodb数据库 **********************/
			// 向mongodb插入数据
			if (!merchant.getMerchantStatus()) { // 不为银行商户才向Mongo中插入数据
				merchantDetailMongo.addMerchant(merchant, categoryInfoList,
						typeInfoList, area);
			}

			setReidsCache(merchant,categoryInfoList);
		
		}

	}
	
	public void setReidsCache(MerchantCG merchant,List<CategoryInfo> categoryInfoList){
		/********************** 操作Redis缓存 **********************/
		if (merchant.getMerchantStatus()) { // 为银行商户才向Redis中插入数据
			merRedis.set_cbbank_bank_merchant_client_id_org_code(merchant);
		}
		/* 缓存全部商户 */
		merRedis.set_cbbank_merchant_client_id_merchant_id(merchant);

		/* 缓存该客户端下改地区所有商户ID */
		merRedis.set_cbbank_area_merchant_client_id_area_id(merchant);

		/* 缓存该客户端下面所有置顶商户 */
		if (merchant.getIsTop()) { // 如果是置顶商户
			merRedis.set_cbbank_top_merchant_client_id(merchant);
		}

		/* 缓存商户分类下的商户id */
		if (CollectionUtils.isNotEmpty(categoryInfoList)) {
			for (CategoryInfo categoryInfo : categoryInfoList) {
				set_cbbank_merchant_category_all_client_id_merchant_category_id(
								merchant.getClientId(),
								categoryInfo.getCategoryId(),
								merchant.getMerchantId());
			}
		}
	}
	
	public  boolean set_cbbank_merchant_category_all_client_id_merchant_category_id(String client_id, Long category_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_merchant_category_all_client_id_merchant_category_id( client_id, category_id);
		Boolean  result = redis.put(key, ObjectUtils.toString(merchant_id, "")) > -1;
		return result ;
	}
	
	/**
	 * 对商户分类数据进行迁移
	  * @Title: transferMerchantCategory
	  * @author: zhangxiaohua 2015-6-26
	  * @modify: zhangxiaohua 2015-6-26
	  * @param     
	  * @return void    
	  * @throws
	 */
	public void transferMerchantCategory(){

		List<MerchantCategory> merchantCategories = merchantCategoryMapper.selectByCondition(new MerchantCategory());
		if(Checker.isEmpty(merchantCategories)){
			return;
		}
		
		MerchantCategoryCG categoryCG = null;
		int n = merchantCategories.size()-1;
		for(MerchantCategory category : merchantCategories){
			categoryCG = new MerchantCategoryCG();
			categoryCG.setClientId(Constans.clientId);
			categoryCG.setName(category.getName());
			categoryCG.setParentId(Long.valueOf(ObjectUtils.toString(category.getParentId(), "0")));
			categoryCG.setTreePath(ObjectUtils.toString(category.getTreePath()));
			categoryCG.setIsDelete(category.getIsEnabled() ? false : true);
			categoryCG.setIcoUrl(Constans.converImage(category.getIcoUrl()));
			categoryCG.setOrderValue(n);
			long result = merchantCategoryMapperCG.addMerchantCategory(categoryCG);
			if(result > -1){
				// 添加缓存
				addMerchantCategoryRedis(categoryCG);
				categoryMap.put(Long.valueOf(category.getId()), Long.valueOf(categoryCG.getId()));
			}
			n--;
		}
		
		
		for(MerchantCategory category : merchantCategories){
			if(Checker.isEmpty(categoryMap.get(category.getId())))
				continue;
			
			categoryCG = new MerchantCategoryCG();
			categoryCG.setId(categoryMap.get(category.getId()));
			
			if(Checker.isNotEmpty(category.getTreePath())){
				String treePath[] = category.getTreePath().split(" ");
				StringBuilder tree = new StringBuilder();
				for(String s : treePath){
					tree.append(categoryMap.get(Long.valueOf(s))+"");
				}
				categoryCG.setTreePath(tree.toString().trim());
			}
			merchantCategoryMapperCG.updateMerchantCategory(categoryCG);
		}
	}
	
	
	private void addMerchantCategoryRedis(MerchantCategoryCG categoryCG){
		String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(Constans.clientId, categoryCG.getId());
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("name", ObjectUtils.toString(categoryCG.getName()));
		hash.put("tree_path", ObjectUtils.toString(categoryCG.getTreePath()));
		hash.put("parent_id", ObjectUtils.toString(categoryCG.getParentId()));
		boolean result = "OK".equals(redis.putMap(key, hash));
	}
	
	

}
