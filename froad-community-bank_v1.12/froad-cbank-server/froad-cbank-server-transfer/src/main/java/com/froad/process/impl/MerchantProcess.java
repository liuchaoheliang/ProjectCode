package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.common.mongo.MerchantDetailMongo;
import com.froad.common.redis.MerchantCategoryRedis;
import com.froad.common.redis.MerchantRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.MerchantEx;
import com.froad.db.ahui.mappers.MerchantTypeRelationMapper;
import com.froad.db.bps.entity.BankOrganization;
import com.froad.db.bps.mappers.BankOrganizationMapper;
import com.froad.db.chonggou.ClientMerchantAuditUtil;
import com.froad.db.chonggou.entity.AreaCG;
import com.froad.db.chonggou.entity.CategoryInfo;
import com.froad.db.chonggou.entity.MerchantCategoryCG;
import com.froad.db.chonggou.entity.MerchantTypeCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.entity.TypeInfo;
import com.froad.db.chonggou.mappers.AreaMapperCG;
import com.froad.db.chonggou.mappers.MerchantCategoryMapperCG;
import com.froad.db.chonggou.mappers.MerchantMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.ModuleID;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.common.enums.AuditState;
import com.froad.fft.persistent.entity.MerchantTypeRelation;
import com.froad.logback.LogCvt;
import com.froad.po.Merchant;
import com.froad.po.Org;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.SimpleID;



public class MerchantProcess extends AbstractProcess{
	
	private MerchantMapperCG merchantMapper  = null;
	private MerchantCategoryMapperCG mapper2 = null;
	private com.froad.db.ahui.mappers.MerchantExMapper merchantExMapper = null;
	public MerchantProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		
		merchantMapper  = sqlSession.getMapper(MerchantMapperCG.class);
		areaMapper = sqlSession.getMapper(AreaMapperCG.class);
		mapper3= ahSqlSession.getMapper(MerchantTypeRelationMapper.class);
		
		merchantExMapper = ahSqlSession.getMapper(com.froad.db.ahui.mappers.MerchantExMapper.class);
		
		mapper2 = sqlSession.getMapper(MerchantCategoryMapperCG.class);
		com.froad.db.ahui.entity.MerchantEx obj = new com.froad.db.ahui.entity.MerchantEx();
		obj.setOrderValue(null);
		List list = merchantExMapper.selectByConditions(obj);
		
		mapper = sqlSession.getMapper(TransferMapper.class);
		
		List<MerchantEx> listLicense = merchantExMapper.findAllMerchantLicense();
		for (MerchantEx merchant : listLicense) {
			mapLicense.put(merchant.getLicense(), 0);
		}
		
		dataConvert(list);
		
	}
	private TransferMapper mapper = null;
	private 	MerchantTypeRelationMapper mapper3 = null;
	final Map<String, Integer> mapLicense = new HashMap<String,Integer>();
	
	public void dataConvert(List<com.froad.db.ahui.entity.MerchantEx> list){
		
		Transfer transfer = null;
		Merchant m = null;
		MerchantCategoryCG merchantCategory = null;
		for (com.froad.db.ahui.entity.MerchantEx obj : list) {
			m = new Merchant();
			transfer = new Transfer();
			m.setCreateTime(obj.getCreateTime());
			m.setMerchantId(getMerchantID());
			
			transfer.setOldId(obj.getId().toString());
			transfer.setNewId(m.getMerchantId());
			transfer.setType(TransferTypeEnum.merchant_id);
			mapper.insert(transfer);
			
//			ConsoleLogger.info("new merchanId->"+m.getMerchantId() + " old merchantId-->"+obj.getId());
			
			m.setMerchantName(obj.getName());
			m.setMerchantFullname(obj.getFullName());
			m.setLogo(Constans.converImage(obj.getLogo()));
			m.setAddress(obj.getAddress());
			m.setContactName(obj.getContactName());
			m.setContactPhone(obj.getTel());
			m.setContactEmail(obj.getContactEmail());
			m.setLegalName(obj.getLegalName());
			if(obj.getLegalCredentType() != null && !"".equals(obj.getLegalCredentType())){
				if("身份证".equals(obj.getLegalCredentType())){
					m.setLegalCredentType(1);
				}
			}else{
				m.setLegalCredentType(null);
			}
			m.setPhone(obj.getContactPhone());
			m.setLegalCredentNo(obj.getLegalCredentNo());
			m.setContractBegintime(obj.getContractBegintime());
			m.setContractEndtime(obj.getContractEndtime());
			m.setContractStaff(ObjectUtils.toString(obj.getContractStaff()));
			
			m.setComplaintPhone(obj.getComplaintCall());
			
			String code = obj.getAuditState().getCode();
			
			/**
			 * AuditState 安徽生产老数据规则
			 * 0-审核未通过
			 * 1-审核通过
			 * 2-待审核
			 * 
			 * AuditState 安徽重构数据规则
			 * 0-待审核
			 * 1-审核通过
			 * 2-审核未通过
			 * 3-未提交
			 */
			if("0".equals(code)){
				m.setIsEnable(false);
				m.setAuditState(ProductAuditState.failAudit.getCode());
			}else if("1".equals(code)){
				m.setIsEnable(true);
				m.setAuditState(ProductAuditState.passAudit.getCode());
			}else if("2".equals(code)){
				m.setIsEnable(false);
				m.setAuditState(ProductAuditState.noAudit.getCode());
			}else{
				m.setIsEnable(false);
				m.setAuditState(ProductAuditState.noCommit.getCode());
			}
			
			m.setAuditStage("");//可为空 审核步骤
			
			m.setReviewStaff(obj.getReviewStaff());
			m.setAuditStaff(obj.getReviewStaff());
			
			transfer  = mapper.queryNewId(String.valueOf(obj.getAreaId()), TransferTypeEnum.area_id);
			if(transfer != null)
				m.setAreaId(Long.parseLong(transfer.getNewId()));
			else{
				m.setAreaId(0l);
			}
			
			if(obj.getInterbankAgency() != null && !"".equals(obj.getInterbankAgency())){
				m.setProOrgCode(Constans.filterOrg(obj.getInterbankAgency()));
			}
			if(obj.getTravelAgency() != null && !"".equals(obj.getTravelAgency())){
				m.setCityOrgCode(Constans.filterOrg(obj.getTravelAgency()));
			}
			
			if(obj.getLatticPoint() != null && !"".equals(obj.getLatticPoint())){
				m.setCountyOrgCode(Constans.filterOrg(obj.getLatticPoint()));
				m.setOrgCode(Constans.filterOrg(obj.getLatticPoint()));
			}else{
				m.setOrgCode(Constans.filterOrg(obj.getTravelAgency()));
			}
			
			// 审核机构 1-省联社审核通过 2-法人行社审核通过 3-网点审核通过
			String orgVerifyState = obj.getOrgVerifyState();
			
			if("1".equals(orgVerifyState)){
				m.setAuditState(ProductAuditState.passAudit.getCode());
				m.setAuditOrgCode("340000");
				m.setAuditStartOrgCode(m.getOrgCode());
				m.setAuditEndOrgCode("340000");
			}else if("2".equals(orgVerifyState)){
				m.setAuditOrgCode("340000");
				m.setAuditStartOrgCode(m.getOrgCode());
				m.setAuditEndOrgCode("340000");
			}else if("3".equals(orgVerifyState)){
				m.setAuditOrgCode(Constans.filterOrg(obj.getTravelAgency()));
				m.setAuditStartOrgCode(m.getOrgCode());
				m.setAuditEndOrgCode("340000");
			}else{
				m.setAuditOrgCode(null);
				m.setAuditStartOrgCode(null);
				m.setAuditEndOrgCode(null);
			}
			
			
			m.setClientId(Constans.clientId);
//			create("10","创建"),//创建
//			entering("20","录入"),//录入
//		    enable("30","启用"),//启用
//			disable("40","停用"),//停用  -->禁用
//			delete("50","删除") ;//删除  --> 解约
			String dataState = obj.getDataState().getCode();
			if("40".equals(dataState)){
				m.setIsEnable(false);
				m.setDisableStatus(MerchantDisableStatusEnum.disable.getCode());
			}else if("50".equals(dataState)){
				m.setIsEnable(false);
				m.setDisableStatus(MerchantDisableStatusEnum.unregistered.getCode());
			}else{
				
				m.setDisableStatus(MerchantDisableStatusEnum.normal.getCode());
			}
			
			
			int n = merchantExMapper.findMerchantById(obj.getId());
			if(n > 0){
				m.setMerchantStatus(false);
				m.setUserOrgCode(m.getOrgCode());
			}else{
				m.setIsEnable(true);
				m.setAuditState(ProductAuditState.passAudit.getCode());
				m.setMerchantStatus(true);
				m.setCountyOrgCode(null);
				m.setOrgCode(m.getCityOrgCode());
				m.setUserOrgCode(m.getOrgCode());
				
			}
			
			m.setAuditTime(obj.getAuditTime());
			
			m.setIntroduce(obj.getIntroduced());
			
			if(mapLicense.containsKey(obj.getLicense())){
				int count = mapLicense.get(obj.getLicense()).intValue();
				count++;
				mapLicense.put(obj.getLicense(), count);
				m.setLicense(obj.getLicense()+"-"+count);
			}else{
				m.setLicense(obj.getLicense());
			}
			
			m.setTaxReg(obj.getTaxReg());
			m.setAuditComment(obj.getAuditComment());
			
			m.setCompanyCredential(m.getOrgCode());
			CategoryInfo categoryInfo = new CategoryInfo();
			transfer  = mapper.queryNewId(obj.getMerchantCategoryId().toString(), TransferTypeEnum.merchant_category_id);
			if(transfer != null){
				categoryInfo.setCategoryId(Long.valueOf(transfer.getNewId()));
				merchantCategory = mapper2.findMerchantCategoryById(Long.parseLong(transfer.getNewId()));
				if(merchantCategory != null)
					categoryInfo.setName(merchantCategory.getName());
			}
			
			List<CategoryInfo> listCategoryInfo = new ArrayList<CategoryInfo>();
			listCategoryInfo.add(categoryInfo);
			
			
			List<MerchantTypeRelation> list2 = mapper3.findMerchantByMerchantId(obj.getId());
			
//			MerchantTypeCG merchantType = null;
			TypeInfo typeInfo = null;
			
			List<TypeInfo> listTypeInfo = new ArrayList<TypeInfo>();
			for (MerchantTypeRelation mobj : list2) {
				typeInfo = new TypeInfo();
				if(100000004 == mobj.getMerchantTypeId().longValue()){
					typeInfo.setMerchantTypeId(100000000l);
					typeInfo.setTypeName("团购");
					typeInfo.setType("0");
					listTypeInfo.add(typeInfo);
				}else if(100000002 == mobj.getMerchantTypeId().longValue()){
					typeInfo.setMerchantTypeId(100000001l);
					typeInfo.setTypeName("直接优惠");
					typeInfo.setType("1");
					listTypeInfo.add(typeInfo);
				}else if(100000005 == mobj.getMerchantTypeId().longValue()){
					typeInfo.setMerchantTypeId(100000002l);
					typeInfo.setTypeName("名优特惠");
					typeInfo.setType("2");
					listTypeInfo.add(typeInfo);
				}
			}
			
			try {
				addMerchant(m, listCategoryInfo, listTypeInfo);
			} catch (Exception e) {
				LogCvt.error("添加Merchant失败，原因:" + e.getMessage() + " 失败 new merchantId-->"+m.getMerchantId() + " old merchanId->"+obj.getId(), e);
			}
		}
		
	}
	
	
	private AreaMapperCG areaMapper = null;
	
	
	private String getMerchantID() {
		return simpleID.nextId();
	}
	
	private static SimpleID simpleID = new SimpleID(ModuleID.merchant);
	
	
	/**
	 * 增加 MerchantEx
	 * @param merchant
	 * @param categoryInfoList
	 * @param typeInfoList
	 * @return     商户编号
	 */
	public String addMerchant(Merchant merchant, List<CategoryInfo> categoryInfoList, List<TypeInfo> typeInfoList)  throws Exception{

		String result = null; 
		
		try { 
			
			if (merchant.getIsTop() == null)
				merchant.setIsTop(false);
			
			if(merchantMapper.addMerchant(merchant) > -1) { // 添加成功
			
				AreaCG area = areaMapper.findAreaById(merchant.getAreaId());
				if(null == area || StringUtils.isBlank(area.getName())) {
					LogCvt.error("区域对象为空不存在！-merchant.getAreaId()--->" + merchant.getAreaId());
//					return null;
				}
			
				/**********************操作Mongodb数据库**********************/
				 // 向mongodb插入数据
				if(!merchant.getMerchantStatus()) { // 不为银行商户才向Mongo中插入数据
					MerchantDetailMongo merchantDetailMongo = new MerchantDetailMongo();
					
					merchantDetailMongo.addMerchant(merchant, categoryInfoList, typeInfoList,area);
				}
				
				/**********************操作Redis缓存**********************/		
				
				
				/**********************操作Redis缓存**********************/	
				if(merchant.getMerchantStatus()) { // 为银行商户才向Redis中插入数据
//					LogCvt.info("缓存该银行虚拟商户所有信息");
					MerchantRedis.set_cbbank_bank_merchant_client_id_org_code(merchant);
				}
				/* 缓存全部商户 */
//				LogCvt.info("缓存该商户所有信息");
				MerchantRedis.set_cbbank_merchant_client_id_merchant_id(merchant);
				
				/* 缓存该客户端下改地区所有商户ID */
//				LogCvt.info("缓存该客户端下改地区所有商户ID");
				MerchantRedis.set_cbbank_area_merchant_client_id_area_id(merchant);
				
				/* 缓存该客户端下面所有置顶商户 */
				if (merchant.getIsTop()) { // 如果是置顶商户
//					LogCvt.info("缓存该客户端下面所有置顶商户");
					MerchantRedis.set_cbbank_top_merchant_client_id(merchant);
				}
				
				/* 修改待审核商户数量 */
//				if (merchant.getAuditState() == null || merchant.getAuditState().equals(ProductAuditState.noAudit.getCode())) { // 如果商户未通过审核   则缓存中待审核数量+1
//					LogCvt.info("缓存增加待审核商户数量");
////					MerchantRedis.set_cbank_preaudit_merchant_count_client_id_org_code(merchant.getClientId(), merchant.getOrgCode(), 1);
//					MerchantRedis.set_cbank_preaudit_merchant_count_client_id_org_code(merchant.getClientId(), merchant.getAuditOrgCode(), 1);
//				}
				
				
				/* 缓存商户分类下的商户id */
				if(CollectionUtils.isNotEmpty(categoryInfoList)) {
					for (CategoryInfo categoryInfo : categoryInfoList) {
//						LogCvt.info("缓存商户分类id:" + categoryInfo.getCategoryId() + "下的商户id");
						MerchantCategoryRedis.set_cbbank_merchant_category_all_client_id_merchant_category_id(merchant.getClientId(), categoryInfo.getCategoryId(), merchant.getMerchantId());
					}
				}
			} 
		} catch (Exception e) { 
			LogCvt.error("添加Merchant失败，原因:" + e.getMessage() + " 失败New merchantId->"+merchant.getMerchantId() , e);
//			e.printStackTrace();
			throw e;
		} 
		return result; 

	}


}

