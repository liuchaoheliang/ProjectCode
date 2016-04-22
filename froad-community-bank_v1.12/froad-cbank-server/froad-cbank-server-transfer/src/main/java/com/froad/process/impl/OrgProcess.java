package com.froad.process.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateUtils;

import com.froad.common.redis.MerchantRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mappers.MerchantOutletMapper;
import com.froad.db.ahui.mappers.OutletBankMapper;
import com.froad.db.bps.entity.BankOrganization;
import com.froad.db.bps.mappers.BankOrganizationMapper;
import com.froad.db.chonggou.entity.OrgRelation;
import com.froad.db.chonggou.entity.OutletCG;
import com.froad.db.chonggou.entity.SubOrgsInfo;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantMapperCG;
import com.froad.db.chonggou.mappers.OrgMapper;
import com.froad.db.chonggou.mappers.OutletMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.redis.OrgRedis;
import com.froad.enums.BankType;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.ModuleID;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.MerchantOutlet;
import com.froad.fft.persistent.entity.OutleBank;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Merchant;
import com.froad.po.Org;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.Constans;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.froad.util.OrgSuperUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class OrgProcess extends AbstractProcess {
	
	private BankOrganizationMapper bankOrganizationMapper;
	private OutletBankMapper outletBankMapper;
	private MerchantOutletMapper merchantOutletMapper;
	private OrgMapper orgMapper;
	private TransferMapper transferMapper;
	private MerchantMapperCG merchantMapperCG;
	private OutletMapperCG outletMapperCG;
	
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	private SimpleID merchantSimpleID = new SimpleID(ModuleID.merchant);
	private SimpleID outletSimpleID = new SimpleID(ModuleID.outlet);
	
	private final String splitter = "\\^\\^";
	private final String initPhone = "96669";
	
	private int merchantCount = 0;
	private int outletCount = 0;
	
	
	public OrgProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		LogCvt.info("机构表cb_org 数据迁移开始");
		bankOrganizationMapper = bpsSqlSession.getMapper(BankOrganizationMapper.class);
		outletBankMapper = ahSqlSession.getMapper(OutletBankMapper.class);
		merchantOutletMapper = ahSqlSession.getMapper(MerchantOutletMapper.class);
		
		orgMapper = sqlSession.getMapper(OrgMapper.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		merchantMapperCG = sqlSession.getMapper(MerchantMapperCG.class);
		outletMapperCG = sqlSession.getMapper(OutletMapperCG.class);
		
		List<BankOrganization> bankOrganizations = bankOrganizationMapper.findAllOrganization();
		if(Checker.isEmpty(bankOrganizations)){
			LogCvt.error("原安徽bps表tr_bank_organization无数据");
			return;
		}
		LogCvt.info("原安徽bps表tr_bank_organization 共有"+bankOrganizations.size()+"条数据需迁移");
		
		/*
		 * 省联社机构需要新增商户和门店
		 * 法人行社机构不需要新增商户(商户ID从中间表取), 需要新增门店
		 * 网点机构需要新增商户,不需要新增门店
		 */
		//获取银行类型bank_type
		String bankType = BankType.province_city.getType();
		boolean result = false;
		// 一次获取商户/门户/地区中间表信息
		Map<String, String> merchantInfo = getTransferInfo(TransferTypeEnum.merchant_id);
		Map<String, String> outletInfo = getTransferInfo(TransferTypeEnum.outlet_id);
		Map<String, String> areaInfo = getTransferInfo(TransferTypeEnum.area_id);
		
		List<Org> orgList = new ArrayList<Org>();
		for(BankOrganization bankOrganization : bankOrganizations){
			String orgCode = Constans.filterOrg(bankOrganization.getOrgCode());	//1000_340000 去掉前面的1000
			
			//获取bank_org->merchant_id->outlet_id关系
			OutleBank outleBank = outletBankMapper.queryByBankOrg(bankOrganization.getOrgCode());
			// 获取地区ID
			Long areaId = getAreaIdByOutletBank(outleBank, areaInfo);
			
			Org filter = new Org();
			filter.setOrgCode(orgCode);
			filter.setClientId(Constans.clientId);
			filter = orgMapper.findOrgById(filter); 
			if(Checker.isNotEmpty(filter)){
				LogCvt.error("客户端表cb_org下机构号[orgCode:"+filter.getOrgCode()+"] 已存在！");
				continue;
			}
			
			Org org = new Org();
			org.setClientId(Constans.clientId);
			org.setBankType(bankType);
			org.setOrgCode(orgCode);
			org.setOrgName(bankOrganization.getOrgName());
			org.setAreaId(areaId);
			
			String path = bankOrganization.getPath();	//1000_340000^^1000_340000^^1000_3400008887
			String[] paths = path.split(splitter);
			switch (bankOrganization.getLevel()) {	//层级（1,2,3)
			case 1:
				org.setOrgLevel(OrgLevelEnum.orgLevel_one.getLevel());
				
				String merchantId1 = merchantSimpleID.nextId();
				String outletId1 = outletSimpleID.nextId();
				
				// 新增商户和门店
				Merchant merchant = new Merchant();
				merchant.setCreateTime(bankOrganization.getCreateTime());
				merchant.setMerchantId(merchantId1);
				merchant.setOrgCode(orgCode);
				merchant.setClientId(Constans.clientId);
				merchant.setMerchantName(bankOrganization.getOrgName());
				merchant.setMerchantFullname(bankOrganization.getOrgName());
				merchant.setMerchantStatus(true);
				merchant.setAreaId(areaId);
				merchant.setPhone(initPhone);
				merchant.setIsEnable(true);
				merchant.setDisableStatus(MerchantDisableStatusEnum.normal.getCode());
				merchant.setIsTop(true);
				merchant.setProOrgCode(orgCode);
				merchant.setContractBegintime(new Date());
				merchant.setContractEndtime(DateUtils.addYears(new Date(), 1));
				merchant.setContractStaff("");
				merchant.setAuditStartOrgCode(orgCode);
				merchant.setAuditEndOrgCode(orgCode);
				merchant.setAuditState(ProductAuditState.passAudit.getCode());
				merchant.setAuditOrgCode("0");	//待审核机构编号(完成置0)
				// 添加商户
				merchantId1 = addMerchant(merchant);
				
				OutletCG outlet1 = new OutletCG();
				outlet1.setCreateTime(bankOrganization.getCreateTime());
				outlet1.setClientId(Constans.clientId);
				outlet1.setMerchantId(merchantId1);
				outlet1.setOutletId(outletId1);
				outlet1.setAreaId(areaId);
				outlet1.setOutletName(bankOrganization.getOrgName());
				outlet1.setOutletFullname(bankOrganization.getOrgName());
				outlet1.setPhone(initPhone);
				outlet1.setOutletStatus(true);
				outlet1.setIsEnable(true);
				outlet1.setDisableStatus("0");
				// 添加门店
				outletId1 = addOutlet(outlet1);
				// 根据新增后的商户和门店ID进行设置
				org.setMerchantId(merchantId1);
				org.setOutletId(outletId1);
				
				break;
			case 2:
				org.setOrgLevel(OrgLevelEnum.orgLevel_two.getLevel());
				org.setProvinceAgency(Constans.filterOrg(paths[0]));
				
				//获取bank_org->merchant_id关系
				if(Checker.isEmpty(outleBank) || Checker.isEmpty(outleBank.getMerchatId())){
					LogCvt.error("原安徽表fft_outlet_bank找不到bank_org->merchant_id关系==>银行机构号[org_code:"+bankOrganization.getOrgCode()+"]");
					continue;
				}
				String merchantId2 = ObjectUtils.toString(outleBank.getMerchatId());
				org.setMerchantId(merchantInfo.get(merchantId2));
				
				String outletId2 = outletSimpleID.nextId();
				
				OutletCG outlet2 = new OutletCG();
				outlet2.setCreateTime(bankOrganization.getCreateTime());
				outlet2.setClientId(Constans.clientId);
				outlet2.setMerchantId(merchantInfo.get(merchantId2));
				outlet2.setOutletId(outletId2);
				outlet2.setAreaId(areaId);
				outlet2.setOutletName(bankOrganization.getOrgName());
				outlet2.setOutletFullname(bankOrganization.getOrgName());
				outlet2.setPhone(initPhone);
				outlet2.setOutletStatus(true);
				outlet2.setIsEnable(true);
				outlet2.setDisableStatus("0");
				//新增门店
				outletId2 = addOutlet(outlet2);
				// 根据新增后的门店ID进行设置
				org.setOutletId(outletId2);
				
				break;
			case 3:
				org.setOrgLevel(OrgLevelEnum.orgLevel_three.getLevel());
				org.setProvinceAgency(Constans.filterOrg(paths[0]));
				org.setCityAgency(Constans.filterOrg(paths[1]));
				
				//获取bank_org->merchant_id->outlet_id关系
				if(Checker.isEmpty(outleBank) || Checker.isEmpty(outleBank.getOutleId())){
					LogCvt.error("原安徽表fft_outlet_bank找不到 bank_org->outlet_id关系==>银行机构号[org_code:"+bankOrganization.getOrgCode()+"]");
					continue;
				}
				
				String merchantId3 = merchantSimpleID.nextId();
				Merchant merchant3 = new Merchant();
				merchant3.setCreateTime(bankOrganization.getCreateTime());
				merchant3.setMerchantId(merchantId3);
				merchant3.setOrgCode(Constans.filterOrg(paths[2]));
				merchant3.setClientId(Constans.clientId);
				merchant3.setMerchantName(bankOrganization.getOrgName());
				merchant3.setMerchantFullname(bankOrganization.getOrgName());
				merchant3.setMerchantStatus(true);
				merchant3.setAreaId(areaId);
				merchant3.setPhone(initPhone);
				merchant3.setIsEnable(true);
				merchant3.setDisableStatus(MerchantDisableStatusEnum.normal.getCode());
				merchant3.setIsTop(false);
				merchant3.setProOrgCode(Constans.filterOrg(paths[0]));
				merchant3.setCityOrgCode(Constans.filterOrg(paths[1]));
				merchant3.setCountyOrgCode(Constans.filterOrg(paths[2]));
				merchant3.setContractBegintime(new Date());
				merchant3.setContractEndtime(DateUtils.addYears(new Date(), 1));
				merchant3.setContractStaff("");
				merchant3.setAuditStartOrgCode(Constans.filterOrg(paths[1]));
				merchant3.setAuditEndOrgCode(Constans.filterOrg(paths[0]));
				merchant3.setAuditState(ProductAuditState.passAudit.getCode());
				merchant3.setAuditOrgCode("0");	//待审核机构编号(完成置0)
				// 新增商户
				merchantId3 = addMerchant(merchant3);
				
				org.setMerchantId(merchantId3);
				
				String outletId3 = ObjectUtils.toString(outleBank.getOutleId());
				//从中间表中获取outletId
				String outletId = outletInfo.get(outletId3);
				org.setOutletId(outletId);
				// 更新机构门店
//				OutletCG outlet = new OutletCG();
//				outlet.setMerchantId(merchantId3);
//				outlet.setOutletId(outletId3);
//				outletMapperCG.updateOutlet(outlet);
				break;
			default:
				continue;
			}
			
			org.setIsEnable(BooleanUtils.toBoolean(bankOrganization.getStatus(), "1", "0"));
			org.setNeedReview(false); //全部初始化为0，不需要复审
			org.setOrgType(true); //全部初始化为1表示业务机构
			
			/**********************操作mongo**********************/
			result = this.addOrgRelation(org);
			LogCvt.info("mongo表cb_org_relation[orgCode:"+bankOrganization.getOrgCode()+"] 数据迁移"+(result?"成功":"失败"));
			
			if(result){
				orgList.add(org);
				
				
				/**********************操作Redis缓存**********************/
				//缓存该商户Id下对应的1-2-3-4级机构关系
				OrgRedis.set_cbbank_merchant_org_level_merchant_id(org.getMerchantId(), commonLogic.setOrgMap(org));
				
				//设置client_id+org_code的机构信息
				String key=RedisKeyUtil.cbbank_org_client_id_org_code(org.getClientId(), org.getOrgCode());
				OrgRedis.set_cbbank_org(key, org);
				
				//设置client_id+outlet_id的机构信息
				key=RedisKeyUtil.cbbank_outlet_org_client_id_outlet_id(org.getClientId(), org.getOutletId());
				OrgRedis.set_cbbank_org(key, org);
				
				//设置client_id+merchant_id的机构信息
				key=RedisKeyUtil.cbbank_merchant_org_client_id_merchant_id(org.getClientId(), org.getMerchantId());
				OrgRedis.set_cbbank_org(key, org);
				
				
			}
		}
		
		result = orgMapper.addOrgByBatch(orgList);
		
		LogCvt.info("目标表cb_org 批量数据迁移"+(result?"成功":"失败")+", 共迁移数据"+orgList.size()+"条");
		LogCvt.info("新增机构商户共"+merchantCount+"个");
		LogCvt.info("新增机构网点共"+outletCount+"个");
		
		LogCvt.info("机构表cb_org 数据迁移结束");
	}
	
	
	private long getAreaIdByOutletBank(OutleBank outleBank, Map<String, String> areaInfo){
		long areaId = 0;
		if(Checker.isNotEmpty(outleBank)){
			MerchantOutlet merchantOutlet = merchantOutletMapper.selectById(outleBank.getOutleId());
			String oldAreaId = "";
			if(Checker.isNotEmpty(merchantOutlet)){
				oldAreaId = ObjectUtils.toString(merchantOutlet.getAreaId());
			}
			areaId = Long.valueOf(ObjectUtils.toString(areaInfo.get(oldAreaId), "0"));
		}
		return areaId;
	}
	
	/**
	 * 根据类型获取中间表信息
	 * @Title: getTransferInfo 
	 * @Description: 
	 * @author: froad-huangyihao 2015年5月8日
	 * @modify: froad-huangyihao 2015年5月8日
	 * @param typeEnum
	 * @return
	 * @throws
	 */
	private Map<String, String> getTransferInfo(TransferTypeEnum typeEnum){
		Map<String, String> info = new HashMap<String, String>();
		List<Transfer> list =transferMapper.queryGroupList(typeEnum);
		for (Transfer transfer : list) {
			info.put(transfer.getOldId(), transfer.getNewId());
		}
		return info;
	}
	
	/**
	 *  保存至机构商户关系表Mongo处理逻辑
	  * @Title: addOrgMerchantOutlet
	  * @Description: 
	  * @author: ll 2015年3月26日
	  * @modify: ll 2015年3月26日
	  * @param @param orgRelatin
	  * @param @return   bool 
	  * @throws
	 */
	private Boolean addOrgRelation(Org org){
		boolean result = false;
		
		if(org.getOrgType()){//业务机构
			//机构号
			String orgCode = org.getOrgCode();
			//机构名称
			String orgName = org.getOrgName();
			//商户id
			String merchantId = ObjectUtils.toString(org.getMerchantId());
			//门店id
			String outletId = ObjectUtils.toString(org.getOutletId());
			
			//添加本身机构商户关系Mongo，第一次新增的机构没有下级机构sub_orgs为空
			OrgRelation orgRelation = new OrgRelation();
			orgRelation.setId(orgCode);
			orgRelation.setClientId(org.getClientId());
			orgRelation.setMerchantId(merchantId);
			orgRelation.setOutletId(outletId);
			orgRelation.setSubOrgs(new ArrayList<SubOrgsInfo>());
			result = this.addOrgRelationMongo(orgRelation);
			
			/**下级机构商户关系list集合**/
			//根据org_level机构级别判断包含所属上级机构<机构级别1-2-3-4->
			String orgTop=OrgSuperUtil.getOrgSuper(org);//上级机构
			if("".equals(orgTop)){//级别1的机构无上级机构
				return true;//一级机构没有上级，直接return掉
			}
			
			//判断是否有上级机构的Mongo
			/**查询上级机构存储的mongodb**/
			OrgRelation orgRelationTop = this.findByOrgCode(orgTop, org.getClientId());
			if(Checker.isNotEmpty(orgRelationTop)){
				
				//声明下级机构对象
				SubOrgsInfo subOrg = new SubOrgsInfo();
				subOrg.setOrgCode(orgCode);
				subOrg.setOrgName(orgName);
				subOrg.setMerchantId(merchantId);
				subOrg.setOutletId(outletId);
				
				//将新增的机构添加到上一级的mongo中
				result = this.addSubOrgInfo(orgRelationTop.getId(),orgRelationTop.getClientId(), subOrg);
			}
		}else{
			result = true;
		}
		return result;
		
	}
	
	
	/**
	 *  查询机构商户关系表Mongo
	  * @Title: findByOrgCode
	  * @Description: 
	  * @author: ll 2015年3月26日
	  * @modify: ll 2015年3月26日
	  * @param @param orgCode
	  * @param @return   OrgRelation 
	  * @throws
	 */
	private OrgRelation findByOrgCode(String orgCode, String clientId){
		DBObject dbObject = new BasicDBObject();
		dbObject.put("_id", orgCode);
		dbObject.put("client_id", clientId);
		OrgRelation bean = mongo.findOne(dbObject, MongoTableName.CB_ORG_RELATION, OrgRelation.class);
		return bean;
	}
	
	/**
	 * 添加Mongo中cb_org_relation表_id下的list某个元素
	 * @author: ll 2015年3月27日
	 * @modify: ll 2015年3月27日
	 * @param _id Mongo中的id标识
	 * @param subOrgsInfo Mongo存储list下的对象
	 * @return bool
	 */
	private boolean addSubOrgInfo(String id,String clientId,SubOrgsInfo subOrgsInfo){
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", id);
		whereObj.put("client_id", clientId);
		
		DBObject pushObj = new BasicDBObject();
		pushObj.put("sub_orgs", JSON.parse(JSonUtil.toJSonString(subOrgsInfo)));
		
		int result = mongo.update(pushObj, whereObj, MongoTableName.CB_ORG_RELATION, "$push");
		return result != -1;
	}
	
	/**
	 *  保存至机构商户关系表Mongo
	  * @Title: addOrgRelationMongo
	  * @Description: 
	  * @author: ll 2015年3月26日
	  * @modify: ll 2015年3月26日
	  * @param  orgRelatin
	  * @param    bool 
	  * @throws
	 */
	private boolean addOrgRelationMongo(OrgRelation orgRelation){
		OrgRelation bean= findByOrgCode(orgRelation.getId(),orgRelation.getClientId());
		int result = -1;
		if(bean == null){
			result = mongo.add(orgRelation, MongoTableName.CB_ORG_RELATION);
		}
		return result != -1;
	}
	
	
	/**
	 * 增加 Merchant
	 * @param merchant
	 * @param categoryInfoList
	 * @return     商户编号
	 * @see com.froad.logic.MerchantLogic#addMerchant(com.froad.po.Merchant, java.util.List, java.util.List)
	 */
	private String addMerchant(Merchant merchant){

		String resultMerchantId = merchant.getMerchantId(); 
		
		Merchant merchantCNT = new Merchant();
		merchantCNT.setMerchantName(merchant.getMerchantName());
		merchantCNT.setOrgCode(merchant.getOrgCode());
		merchantCNT.setClientId(merchant.getClientId());
		Merchant queryMerchant = merchantMapperCG.findOneMerchant(merchantCNT);
		if(Checker.isNotEmpty(queryMerchant)){
			LogCvt.info("商户[merchantName:"+merchant.getMerchantName()+"] 已存在");
			resultMerchantId = queryMerchant.getMerchantId();
			return resultMerchantId;
		}
		
		if(merchantMapperCG.addMerchant(merchant) > -1) { // 添加成功
			merchantCount ++;
			LogCvt.info("MySQL添加merchant[merchantId:"+merchant.getMerchantId()+"]成功");
			
			/**********************操作Redis缓存**********************/	
//			if(merchant.getMerchantStatus()) { // 为银行商户才向Redis中插入数据
//				addBankMerchantRedis(merchant);
//			}
			
			if(merchant.getMerchantStatus()) { // 为银行商户才向Redis中插入数据
//				LogCvt.info("缓存该银行虚拟商户所有信息");
				MerchantRedis.set_cbbank_bank_merchant_client_id_org_code(merchant);
			}
			
			/* 缓存全部商户 */
//			addMerchantRedis(merchant);
			MerchantRedis.set_cbbank_merchant_client_id_merchant_id(merchant);
			
			/* 缓存该客户端下改地区所有商户ID */
//			addAreaMerchantRedis(merchant);
			MerchantRedis.set_cbbank_area_merchant_client_id_area_id(merchant);
			
			/* 缓存该客户端下面所有置顶商户 */
//			if (merchant.getIsTop()) { // 如果是置顶商户
//				addTopMerchantRedis(merchant);
//			}
			
			if (merchant.getIsTop()) { // 如果是置顶商户
//				LogCvt.info("缓存该客户端下面所有置顶商户");
				MerchantRedis.set_cbbank_top_merchant_client_id(merchant);
			}
			
			
			
		} 
		return resultMerchantId; 
	}
	
	
	private void addBankMerchantRedis(Merchant merchant){
		String key = RedisKeyUtil.cbbank_bank_merchant_client_id_org_code(Constans.clientId, merchant.getOrgCode());
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("merchant_id", ObjectUtils.toString(merchant.getMerchantId(), ""));
		hash.put("merchant_name", ObjectUtils.toString(merchant.getMerchantName(), ""));
		hash.put("logo", ObjectUtils.toString(merchant.getLogo(), ""));
		hash.put("phone", ObjectUtils.toString(merchant.getPhone(), ""));
		hash.put("merchant_status", BooleanUtils.toString(merchant.getMerchantStatus(), "1", "0", ""));
		hash.put("is_enable", BooleanUtils.toString(merchant.getIsEnable(), "1", "0", ""));
		boolean result = "OK".equals(redis.putMap(key, hash));
		LogCvt.info("银行虚拟商户key==>"+key+" 设置"+(result?"成功":"失败"));
	}
	
	
	/**
	 * 添加商户信息缓存
	 * @Title: addMerchantRedis 
	 * @Description: 
	 * @author: froad-huangyihao 2015年5月3日
	 * @modify: froad-huangyihao 2015年5月3日
	 * @param merchant
	 * @return
	 * @throws
	 */
	private void addMerchantRedis(Merchant merchant){
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(Constans.clientId, merchant.getMerchantId());
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("merchant_name", ObjectUtils.toString(merchant.getMerchantName()));
		hash.put("logo", ObjectUtils.toString(merchant.getLogo()));
		hash.put("phone", ObjectUtils.toString(merchant.getPhone()));
		hash.put("area_id", ObjectUtils.toString(merchant.getAreaId(), "0"));
		hash.put("merchant_status", BooleanUtils.toString(merchant.getMerchantStatus(), "1", "0", ""));
		hash.put("org_code", ObjectUtils.toString(merchant.getOrgCode()));
		hash.put("audit_org_code", ObjectUtils.toString(merchant.getAuditOrgCode()));
		hash.put("audit_state", ObjectUtils.toString(merchant.getAuditState()));
		hash.put("audit_stage", ObjectUtils.toString(merchant.getAuditStage()));
		hash.put("contract_endtime", ObjectUtils.toString(merchant.getContractEndtime().getTime()));
		hash.put("is_enable", BooleanUtils.toString(merchant.getIsEnable(), "1", "0", ""));
		String result = redis.putMap(key, hash);
		boolean flag = "OK".equals(result);
		LogCvt.info("商户缓存key==>"+key+" 设置"+(flag?"成功":"失败"));
	}
	
	/**
	 * 缓存该客户端下改地区所有商户ID
	 * @Title: addAreaMerchantRedis 
	 * @Description: 
	 * @author: froad-huangyihao 2015年5月3日
	 * @modify: froad-huangyihao 2015年5月3日
	 * @param merchant
	 * @return
	 * @throws
	 */
	private void addAreaMerchantRedis(Merchant merchant){
		String key = RedisKeyUtil.cbbank_area_merchant_client_id_area_id(Constans.clientId, merchant.getAreaId());
		String result = redis.putString(key, merchant.getMerchantId());
		boolean flag = "OK".equals(result);
		LogCvt.info("商户地区缓存key==>"+key+" 设置"+(flag?"成功":"失败"));
	}
	
	
	private void addTopMerchantRedis(Merchant merchant){
		String key = RedisKeyUtil.cbbank_top_merchant_client_id(Constans.clientId);
		String result = redis.putString(key, merchant.getMerchantId());
		boolean flag = "OK".equals(result);
		LogCvt.info("商户置顶缓存key==>"+key+" 设置"+(flag?"成功":"失败"));
	}
	
	
	private String addOutlet(OutletCG outletCG){
		
		String resultOutletId = outletCG.getOutletId();
		/**********************操作MySQL数据库**********************/
		OutletCG outletCNT = new OutletCG();
		outletCNT.setClientId(Constans.clientId);
		outletCNT.setOutletName(outletCG.getOutletName());
		OutletCG query = outletMapperCG.findOneOutletCG(outletCNT);
		if(Checker.isNotEmpty(query)){
			LogCvt.info("门店[outletName:"+outletCG.getOutletName()+"]已经存在!");
			resultOutletId = query.getOutletId();
			return resultOutletId;
		}
		
		if (outletMapperCG.addOutlet(outletCG) > -1) { 
			outletCount ++;
			LogCvt.info("MySQL添加outlet[outletId:"+outletCG.getOutletId()+"]成功");

			/**********************操作Redis缓存**********************/
			/* 设置全部缓存 */
			LogCvt.info("设置门店全部缓存");
			addOutletRedis(outletCG);
		} 
		return resultOutletId;
	}
	
	private void addOutletRedis(OutletCG outletCG){
		String key = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(Constans.clientId, outletCG.getMerchantId(), outletCG.getOutletId());
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("outlet_name", ObjectUtils.toString(outletCG.getOutletName(), ""));
		hash.put("outlet_status", BooleanUtils.toString(outletCG.getOutletStatus(), "1", "0", ""));
		hash.put("longitude", ObjectUtils.toString(outletCG.getLongitude(), "0"));
		hash.put("latitude", ObjectUtils.toString(outletCG.getLatitude(), "0"));
		hash.put("is_enable", BooleanUtils.toString(outletCG.getIsEnable(), "1", "0", ""));
		String result = redis.putMap(key, hash);
		boolean flag = "OK".equals(result);
		LogCvt.info("门店缓存key==>"+key+" 设置"+(flag?"成功":"失败"));
	}
	
}
