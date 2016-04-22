/**
 * 商户业务逻辑Logic
 * @Title: MerchantLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.Constants;
import com.froad.common.beans.MerchantExportData;
import com.froad.db.mongo.MerchantDetailMongo;
import com.froad.db.mongo.MerchantMongo;
import com.froad.db.mongo.OutletDetailMongo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.db.redis.MerchantCategoryRedis;
import com.froad.db.redis.MerchantRedis;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ProductAuditState;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.AreaLogic;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.MerchantUserLogic;
import com.froad.logic.OutletLogic;
import com.froad.po.Area;
import com.froad.po.Merchant;
import com.froad.po.MerchantUser;
import com.froad.po.Org;
import com.froad.po.Result;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.TypeInfo;
import com.froad.support.DataPlatLogSupport;
import com.froad.support.Support;
import com.froad.thread.FroadThreadPool;
import com.froad.thrift.vo.SmsMessageVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.ClientUtil;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * 
 * <p>@Title: MerchantLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantLogicImpl implements MerchantLogic {
	private Support support = new Support();
	
	private static final String CB_MERCHANT_DETAIL = "cb_merchant_detail";
//	private static final String MONGO_KEY_CATEGORY_INFO = "category_info";
//	private static final String MONGO_KEY_CATEGORY_ID = "category_id";
//	private static final String MONGO_KEY_TYPE_ID = "type_id";

	private MongoManager manager = new MongoManager();
	private MerchantDetailMongo merchantDetailMongo = new MerchantDetailMongo();
	private OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
	private CommonLogic commonLogic = new CommonLogicImpl();
	private AreaLogic areaLogic = new AreaLogicImpl();
	private OutletLogic outletLogic = new OutletLogicImpl();
	
	/**
	 * 增加 Merchant
	 * @param merchant
	 * @param categoryInfoList
	 * @param typeInfoList
	 * @return     商户编号
	 * @see com.froad.logic.MerchantLogic#addMerchant(com.froad.po.Merchant, java.util.List, java.util.List)
	 */
	@Override
	public String addMerchant(Merchant merchant, List<CategoryInfo> categoryInfoList, List<TypeInfo> typeInfoList)  throws FroadServerException, Exception{

		String result = null; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try {
			// 设置merchant分类和类型信息，用于redis缓存
			merchant.setCategoryInfoObjList(categoryInfoList);
			merchant.setTypeInfoObjList(typeInfoList);
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			
			merchant.setCreateTime(new Date()); // 创建时间
			if(merchant.getContractBegintime()==null)
				merchant.setContractBegintime(new Date()); // 签约时间
			if (merchant.getIsEnable() == null)
				merchant.setIsEnable(false);
			if(StringUtils.isBlank(merchant.getDisableStatus()))
				merchant.setDisableStatus(MerchantDisableStatusEnum.normal.getCode());
			if (merchant.getIsTop() == null)
				merchant.setIsTop(false);
			
			Area area = areaLogic.findAreaById(merchant.getAreaId());

			if(!merchant.getMerchantStatus()) { // 不为银行商户才计算上级机构
				LogCvt.info("查询机构码为:" + merchant.getOrgCode() + "的详细信息.");
				
				Org org = commonLogic.getOrgByOrgCode(merchant.getOrgCode(), merchant.getClientId());
				if(org == null) {
					throw new FroadServerException("机构码:" + merchant.getOrgCode() + "不存在或已经删除!");
				}
				if (org.getIsEnable() != null && !org.getIsEnable()) { // 机构不可用
					throw new FroadServerException("机构码:" + merchant.getOrgCode() + "不可用!");
				}
				LogCvt.info("查询机构码为:" + merchant.getOrgCode() + "的一级机构码为:" + org.getProvinceAgency() + ", 二级机构码为:" + org.getCityAgency() + ", 三级机构码为:" + org.getCountyAgency());
				if (!NumberUtils.isNumber(org.getOrgLevel())){
					throw new FroadServerException("机构码:" + merchant.getOrgCode() + "的级别非法!");
				}
				merchant.setProOrgCode(org.getProvinceAgency());// 省级机构
				merchant.setCityOrgCode(org.getCityAgency());// 市级机构
				merchant.setCountyOrgCode(org.getCountyAgency());// 区级机构	
				merchant.setOrgCode(org.getOrgCode());
				
				Map<OrgLevelEnum, String> map = commonLogic.setOrgMap(org); // 获取机构  对应的 1 2 3 4级机构
				for (Map.Entry<OrgLevelEnum,String> kv : map.entrySet()) {
					OrgLevelEnum orgLeveEnum = kv.getKey();
					String superOrgCode = kv.getValue();
					switch (orgLeveEnum) {
					case orgLevel_one: //1 省级机构
						merchant.setProOrgCode(superOrgCode);
						break;
					case orgLevel_two: //2 市级机构
						merchant.setCityOrgCode(superOrgCode);
						break;
					case orgLevel_three: //3 区级机构
						merchant.setCountyOrgCode(superOrgCode);
						break;
					case orgLevel_four: //4 网点
						merchant.setOrgCode(superOrgCode);
						break;
					default: // 默认
//							merchant.setOrgCode(levelOrg);
						break;
					}
				}
				merchant.setOrgCode(org.getOrgCode());	
				
			} else { // 如果为银行商户则要判断是否已经存在
				Merchant merchantCNT = new Merchant();
				merchantCNT.setOrgCode(merchant.getOrgCode());
				merchantCNT.setIsEnable(true);
				merchantCNT.setMerchantStatus(true); // 查虚拟商户
				merchantCNT.setClientId(merchant.getClientId());
				if(merchantMapper.countMerchant(merchantCNT) > 0){
					throw new FroadServerException("组织编码已存在！");
				}
			}
			
			if(merchantMapper.addMerchant(merchant) > -1) { // 添加成功
				LogCvt.info("MySQL添加merchant成功");
				result = merchant.getMerchantId();		
			
			
				/**********************操作Mongodb数据库**********************/
				 // 向mongodb插入数据
				if(!merchant.getMerchantStatus()) { // 不为银行商户才向Mongo中插入数据
					LogCvt.info("普通商户(非银行默认)添加cb_merchant_detail表");
//					merchantDetailMongo.addMerchant(merchant, categoryInfoList, typeInfoList);
					MerchantDetail merchantDetail = new MerchantDetail();

					merchantDetail = (MerchantDetail)BeanUtil.copyProperties(MerchantDetail.class, merchant);

					merchantDetail.setId(merchant.getMerchantId());
					merchantDetail.setMerchantName(merchant.getMerchantName());
					merchantDetail.setIntroduced(merchant.getIntroduce());

					if(CollectionUtils.isNotEmpty(categoryInfoList)) {
						merchantDetail.setCategoryInfo(categoryInfoList);
					}

					if(CollectionUtils.isNotEmpty(typeInfoList)) {
						merchantDetail.setTypeInfo(typeInfoList);
					}
					
					if(area!=null){
						merchantDetail.setTreePathName(area.getTreePathName()); // 设置地区treePathName
					}
					
					merchantDetailMongo.addMerchantDetail(merchantDetail);
				}
				
				/* 设置缓存 */
				enableCache(merchant, categoryInfoList);
				
				sqlSession.commit(true);
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
				if(null != sqlSession)  
					sqlSession.rollback(true); 
				result = null; 
				throw e;
			
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = null; 
			throw e;
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return result; 

	}

	
	/** 
	 * 设置缓存
	 * @Title: enableCache 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchant
	 * @param categoryInfoList
	 * @throws Exception
	 * @return void    返回类型 
	 * @throws 
	 */ 
	private void enableCache(Merchant merchant, List<CategoryInfo> categoryInfoList) throws Exception {
		/**********************操作Redis缓存**********************/	
		if(merchant.getMerchantStatus()) { // 为银行商户才向Redis中插入数据
			LogCvt.info("缓存该银行虚拟商户所有信息");
			MerchantRedis.set_cbbank_bank_merchant_client_id_org_code(merchant);
		}
		/* 缓存全部商户 */
		LogCvt.info("缓存该商户所有信息");
		MerchantRedis.set_cbbank_merchant_client_id_merchant_id(merchant);
		
		/* 缓存该客户端下改地区所有商户ID */
		LogCvt.info("缓存该客户端下改地区所有商户ID");
		MerchantRedis.set_cbbank_area_merchant_client_id_area_id(merchant);
		
		/* 缓存该客户端下面所有置顶商户 */
		if (merchant.getIsTop()) { // 如果是置顶商户
			LogCvt.info("缓存该客户端下面所有置顶商户");
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
				LogCvt.info("缓存商户分类id:" + categoryInfo.getCategoryId() + "下的商户id");
				MerchantCategoryRedis.set_cbbank_merchant_category_all_client_id_merchant_category_id(merchant.getClientId(), categoryInfo.getCategoryId(), merchant.getMerchantId());
			}
		}
	}

	/**
    * 批量增加 Merchant
    * @param list<merchant>
    * @return list<Result>    结果集
    * 
    * @param merchantList
    */
	@Override
	public List<Result> addMerchantByBatch(List<Merchant> merchantList)  throws FroadServerException, Exception{
		// TODO Auto-generated method stub
		List<Result> resultList = new ArrayList<Result>();
		String id = null;
//		for(Merchant merchant : merchantList ){
//			id = this.addMerchant(merchant);
//			Result result = new Result();
//			if( id != null ){
//				result.setResultCode(ResultCode.success.getCode());
//			}else{
//				result.setResultCode(ResultCode.failed.getCode());
//			}
//			result.setResultDesc(merchant.getMerchantName());
//			resultList.add(result);
//		}
		return resultList;
	}

    /**
     * 删除 Merchant
     * @param merchant
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteMerchant(Merchant merchant, MerchantDisableStatusEnum disableStatus)  throws FroadServerException, Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			if(StringUtils.isBlank(merchant.getMerchantId())) {
				throw new FroadServerException("商户id不能为空!");
			}
			
			Merchant dm = new Merchant();
			dm.setMerchantId(merchant.getMerchantId());
			
			dm.setIsEnable(false);
			dm.setDisableStatus(disableStatus.getCode());
			dm.setOperateUser(merchant.getOperateUser());
			dm.setOperateTime(merchant.getOperateTime());
			
			result = merchantMapper.updateMerchant(dm); 
			if(result) { // 如果删除成功
				LogCvt.info("MySQL删除merchant成功");
				merchant = merchantMapper.findMerchantByMerchantId(merchant.getMerchantId());
//				List<Merchant> list = merchantMapper.findMerchant(dm);
				/**********************操作mongodb数据库**********************/
				/*删除商户详细信息*/
				if(!merchant.getMerchantStatus()){ // 不为银行商户才向Mongo中插入数据
					LogCvt.info("普通商户(非银行默认)删除cb_merchant_detail表");
					merchantDetailMongo.deleteMerchantDetail(merchant.getMerchantId());
				}
				
				/**********************操作redis缓存**********************/	
				disableCache(merchant);

				if(disableStatus.equals(MerchantDisableStatusEnum.unregistered)) {
					LogCvt.info("删除商户id为:" + merchant.getMerchantId() + "下的所有的门店");
					outletLogic.deleteOutletByMerchantId(merchant.getMerchantId());
				} else if(disableStatus.equals(MerchantDisableStatusEnum.disable)) {
					LogCvt.info("禁用商户id为:" + merchant.getMerchantId() + "下的所有的门店");
					outletLogic.disableOutletByMerchantId(merchant.getMerchantId());
				}
				
				/*商户下的所有的用户*/
				
				sqlSession.commit(true);
				// 修改商户日志落地
				new DataPlatLogSupport().deleteMerchantLog(merchant.getClientId(),merchant.getMerchantId());
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = null; 
			throw e;
		
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = false; 
			throw e;
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return result; 

	}


	
	/** 
	 * 删除缓存
	 * @Title: disableCache 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchant
	 * @return void    返回类型 
	 * @throws 
	 */ 
	private void disableCache(Merchant merchant) {
		if(merchant.getMerchantStatus()) { // 为银行商户才向Redis中插入数据
			LogCvt.info("缓存该银行虚拟商户所有信息");
			MerchantRedis.set_cbbank_bank_merchant_client_id_org_code(merchant);
		}
		/* 删除对应的门店缓存 */
		
		LogCvt.info("商户缓存删除对应的商户缓存");
		MerchantRedis.del_cbbank_merchant_client_id_merchant_id(merchant.getClientId(), merchant.getMerchantId());
		
		/* 删除置顶商户缓存 */
		LogCvt.info("删除置顶商户缓存");
		MerchantRedis.del_cbbank_top_merchant_client_id(merchant.getClientId(), merchant.getMerchantId());
		
		/* 删除该客户端下改地区所有商户ID缓存 */
		LogCvt.info("删除该客户端" + merchant.getClientId() + "下改地区所有商户ID缓存");
		MerchantRedis.del_cbbank_area_merchant_client_id_area_id(merchant.getClientId(), merchant.getAreaId(), merchant.getMerchantId());
		
		/* 删除商户分类下的商户id缓存 */
		if (!merchant.getMerchantStatus()) { // 不为银行商户才向Mongo中插入数据
			List<CategoryInfo> categoryInfoList = merchantDetailMongo.findMerchantCategoryInfo(merchant.getMerchantId());
			if (CollectionUtils.isNotEmpty(categoryInfoList)) {
				for (CategoryInfo categoryInfo : categoryInfoList) {
					LogCvt.info("删除商户分类id:" + categoryInfo.getCategoryId() + "下的商户id");
					MerchantCategoryRedis.del_cbbank_merchant_category_all_client_id_merchant_category_id(merchant.getClientId(), categoryInfo.getCategoryId(), merchant.getMerchantId());
				}
			}
		}
	}
	
	/**
	 * 物理删除 Merchant
	 * @Title: removeMerchant 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @throws FroadServerException, Exception
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean removeMerchant(String merchantId) throws FroadServerException, Exception {


		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			if(StringUtils.isBlank(merchantId)) {
				throw new FroadServerException("商户id不能为空!");
			}
			
			Merchant merchant = merchantMapper.findMerchantByMerchantId(merchantId); // 先查一把
			result = merchantMapper.removeMerchant(merchantId); 
			if(result) { // 如果删除成功
				LogCvt.info("MySQL物理删除merchant成功");
				/**********************操作mongodb数据库**********************/
				/*删除商户详细信息*/
				if(!merchant.getMerchantStatus()){ // 不为银行商户才向Mongo中插入数据
					LogCvt.info("普通商户(非银行默认)物理删除cb_merchant_detail表");
					merchantDetailMongo.removeMerchantDetail(merchantId);
				}
				
				/**********************操作redis缓存**********************/		
				if(merchant.getMerchantStatus()) { // 为银行商户才向Redis中插入数据
					LogCvt.info("物理删除该银行虚拟商户所有信息");
					MerchantRedis.remove_cbbank_bank_merchant_client_id_org_code(merchant.getClientId(), merchant.getOrgCode());
				}
				/* 删除对应的门店缓存 */
				
				LogCvt.info("商户缓存物理删除对应的商户缓存");
				MerchantRedis.remove_cbbank_merchant_client_id_merchant_id(merchant.getClientId(), merchant.getMerchantId());
				
				/* 删除置顶商户缓存 */
				LogCvt.info("物理删除置顶商户缓存");
				MerchantRedis.del_cbbank_top_merchant_client_id(merchant.getClientId(), merchant.getMerchantId());
				
				/* 删除该客户端下改地区所有商户ID缓存 */
				LogCvt.info("物理删除该客户端" + merchant.getClientId() + "下改地区所有商户ID缓存");
				MerchantRedis.del_cbbank_area_merchant_client_id_area_id(merchant.getClientId(), merchant.getAreaId(), merchant.getMerchantId());
				
				/* 删除商户分类下的商户id缓存 */
				if (!merchant.getMerchantStatus()) { // 不为银行商户才向Mongo中插入数据
					List<CategoryInfo> categoryInfoList = merchantDetailMongo.findMerchantCategoryInfo(merchant.getMerchantId());
					if (CollectionUtils.isNotEmpty(categoryInfoList)) {
						for (CategoryInfo categoryInfo : categoryInfoList) {
							LogCvt.info("删除商户分类id:" + categoryInfo.getCategoryId() + "下的商户id");
							MerchantCategoryRedis.del_cbbank_merchant_category_all_client_id_merchant_category_id(merchant.getClientId(), categoryInfo.getCategoryId(), merchant.getMerchantId());
						}
					}
				}
				
				sqlSession.commit(true); 
				// 修改商户日志落地
				new DataPlatLogSupport().deleteMerchantLog(merchant.getClientId(),merchantId);
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = null; 
			throw e;
		
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = false; 
			throw e;
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return result; 
	
	}

    /**
     * 修改 Merchant
     * @param merchant
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateMerchant(Merchant merchant, List<CategoryInfo> categoryInfoList, List<TypeInfo> typeInfoList)  throws FroadServerException, Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			
			final String merchantId = merchant.getMerchantId();
			final String nowMerchantName = merchant.getMerchantName();
			
			Merchant merchantOld = merchantMapper.findMerchantByMerchantId(merchantId); // 数据库的数据
			if(Checker.isEmpty(merchantOld)){
				throw new FroadServerException("商户["+merchantId+"]信息记录不存在！");
			}
			if (!merchantOld.getAuditState().equals(ProductAuditState.passAudit.getCode())) { // 如果原来不为是 1-审核通过时
				LogCvt.info("如果原来商户的审核状态,不为是 1-审核通过时,需要重新审核");
				
				if(StringUtils.equals("0", merchant.getAuditOrgCode())) { // 总行修改
					merchant.setAuditState(ProductAuditState.passAudit.getCode()); // 重置待审核状态 1-审核通过
					merchant.setIsEnable(true); // 重置启用状态
				} else { // 非总行
					merchant.setAuditState(ProductAuditState.noAudit.getCode()); // 重置待审核状态 0-待审核
					merchant.setIsEnable(false); // 重置启用状态
				}
				merchant.setAuditStage("");// 重置待审核步骤
				
				//当商户名称非空时，将商户名称存入商户全名字段
				if(!StringUtils.isBlank(merchant.getMerchantName())){
					merchant.setMerchantFullname(merchant.getMerchantName());
				}

			}

			LogCvt.debug("修改的商户=====>" + JSON.toJSONString(merchant,true));

			result = merchantMapper.updateMerchant(merchant);  // 修改商户	
			
			if(result) { // 如果修改成功
				LogCvt.info("MySQL修改merchant成功");
				merchant = merchantMapper.findMerchantByMerchantId(merchant.getMerchantId());
				/**********************操作mongodb数据库**********************/
				if(!merchant.getMerchantStatus()) { // 不为银行商户才向Mongo中修改数据
					LogCvt.info("修改商户详细信息");
					merchantDetailMongo.updateMerchantDetailById(merchant, categoryInfoList, typeInfoList);
					
					LogCvt.info("修改商户下面的所有门店的分类信息");
					outletDetailMongo.updateOutletDetailCategoryInfo(merchant.getMerchantId(), categoryInfoList); // 修改门店表的分类信息
					LogCvt.info("修改商户下面的所有门店的类型信息");
					outletDetailMongo.updateOutletDetailTypeInfo(merchant.getMerchantId(), typeInfoList); // 修改门店表的类型信息
					
				}
				
				// 设置merchant分类和类型信息，用于redis缓存
				merchant.setCategoryInfoObjList(categoryInfoList);
				merchant.setTypeInfoObjList(typeInfoList);
				
				/**********************操作redis缓存**********************/
				if(!merchant.getIsEnable().equals(merchantOld.getIsEnable())) {
					if (merchant.getIsEnable()) {
						enableCache(merchant, categoryInfoList);
					} else {
						disableCache(merchant);
					}
				}
				
				if(merchant.getMerchantStatus()) { // 为银行商户才向Redis中插入数据
					LogCvt.info("缓存该银行虚拟商户所有信息");
					MerchantRedis.set_cbbank_bank_merchant_client_id_org_code(merchant);
				}
				/* 更新全部商户缓存 */
				LogCvt.info("修改商户信息缓存");
				MerchantRedis.set_cbbank_merchant_client_id_merchant_id(merchant);
				
				if(!merchant.getIsTop()) { // 删除置顶缓存
					LogCvt.info("删除商户id为" + merchant.getClientId() + "的置顶缓存");
					MerchantRedis.del_cbbank_top_merchant_client_id(merchant.getClientId(), merchant.getMerchantId());
				}
				
				/* 缓存商户分类下的商户id */
				if(CollectionUtils.isNotEmpty(categoryInfoList)) {
					List<CategoryInfo> categoryInfoOldList = this.findMerchantCategoryInfo(merchant.getMerchantId()); // 原来的商户分类
					if(CollectionUtils.isNotEmpty(categoryInfoOldList)) {
						for (CategoryInfo categoryInfoOld : categoryInfoOldList) {
							LogCvt.info("删除原商户分类id:" + categoryInfoOld.getCategoryId() + "下的商户id");
							MerchantCategoryRedis.del_cbbank_merchant_category_all_client_id_merchant_category_id(merchant.getClientId(), categoryInfoOld.getCategoryId(), merchant.getMerchantId());
						}
					}
					for (CategoryInfo categoryInfo : categoryInfoList) {
						LogCvt.info("缓存商户分类id:" + categoryInfo.getCategoryId() + "下的商户id");
						MerchantCategoryRedis.set_cbbank_merchant_category_all_client_id_merchant_category_id(merchant.getClientId(), categoryInfo.getCategoryId(), merchant.getMerchantId());
					}
				}
				
				
				
//				if(!"0".equals(merchantOld.getAuditOrgCode())) // 如果原来的待审核机构 不为总行 则要-1
//					MerchantRedis.set_cbank_preaudit_merchant_count_client_id_org_code(merchantOld.getClientId(), merchantOld.getAuditOrgCode(), -1); // 把原来的待审核机构的 待审核商户数量-1
//				
//				if (!merchantOld.getAuditState().equals(ProductAuditState.passAudit.getCode())) { // 如果原来不为是 1-审核通过时
//					if(!"0".equals(merchant.getAuditOrgCode().trim())) { // 非总行修改 待审核商户数量+1
//						MerchantRedis.set_cbank_preaudit_merchant_count_client_id_org_code(merchant.getClientId(), merchant.getAuditOrgCode(), 1); // 待审核商户数量+1
//					}
//				}
				
				sqlSession.commit(true); 
				
				// 添加商户日志落地
				new DataPlatLogSupport().modifyMerchantLog(merchant.getClientId(),merchantId);
				
				/*异步修改商品表的商户名字*/
				String oldMerchantName = merchantOld.getMerchantName();
				if(StringUtils.isNotBlank(nowMerchantName) && !nowMerchantName.equals(oldMerchantName)) {
					LogCvt.info("修改了商户名称, 为了不影响主流程[启线程,异步修改]商品表中的商户名称!!!!");
					FroadThreadPool.execute(new Runnable() {
						
						@Override
						public void run() {
							CommonLogic commonLogic = new CommonLogicImpl();
							commonLogic.updateProduct_MerchantNameByMerchantId(merchantId, nowMerchantName);
						}
					});
				}
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
//			LogCvt.error("修改Merchant失败，原因:" + e.getMessage(), e);
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
//			LogCvt.error("修改Merchant失败，原因:" + e.getMessage(), e);
			throw e;
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}  
		return result; 

	}

	
	/**
	 * 查询一个Merchant
	 * 
	 * @param merchant
	 * @return
	 * @see com.froad.logic.MerchantLogic#findOneMerchant(com.froad.po.Merchant)
	 */
	@Override
	public Merchant findOneMerchant(Merchant merchant) {

		Page<Merchant> page = new Page<Merchant>();
		page.setPageSize(1); // 只查询1条

		page = this.findMerchantByPage(page, merchant);
		List<Merchant> list = page.getResultsContent();
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	

    /**
     * 查询 Merchant
     * @param merchant
     * @return List<Merchant>    结果集合 
     */
	@Override
	public List<Merchant> findMerchant(Merchant merchant) {

		List<Merchant> result = new ArrayList<Merchant>(); 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			/**********************操作redis缓存**********************/
			
			
			/**********************操作mongodb数据库**********************/
			// 转换 Merchant 根据 CategoryInfoList 得到 MerchantIdList
			merchant = changeMerchantOfMerchantIdListByCategoryAndTypeList(merchant);
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			 
			result = merchantMapper.findMerchant(merchant); 
			// sqlSession.commit(true);
			
			
			
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("查询Merchant失败，原因:" + e.getMessage(), e); 
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}  
		return result; 

	}
	
	
	/**
     * 统计 Merchant
     * @param merchant
     * @return Integer
     */
	@Override
	public Integer countMerchant(Merchant merchant) {

		Integer result = -1; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			/**********************操作redis缓存**********************/
			
			
			/**********************操作mongodb数据库**********************/
			// 转换 Merchant 根据 CategoryInfoList 得到 MerchantIdList
			
			/**********************操作MySQL数据库**********************/
			 
			result = merchantMapper.countMerchant(merchant); 
			// sqlSession.commit(true);
			
			
			
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("统计Merchant失败，原因:" + e.getMessage(), e); 
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}  
		return result; 

	}
	
	/**
	 * 查询单个商户
	 * @Title: findMerchantByMerchantId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @return List<Merchant>    返回类型 
	 * @throws
	 */
	public Merchant findMerchantByMerchantId(String merchantId){
		Merchant result = null; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			/**********************操作redis缓存**********************/
			
			
			/**********************操作mongodb数据库**********************/
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			 
			result = merchantMapper.findMerchantByMerchantId(merchantId); 
			// sqlSession.commit(true);
			
			
			
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);  
			LogCvt.error("查询Merchant失败，原因:" + e.getMessage(), e); 
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}  
		return result; 
	}

	// 转换 商户信息 根据 CategoryInfoList 得到 MerchantIdList
	private Merchant changeMerchantOfMerchantIdListByCategoryAndTypeList(Merchant merchant){
		
		/**********************操作mongodb数据库**********************/
		/**           用查询条件 categoryInfoList(分类列表)            */
		/**    在 cb_merchant_detail(商户详情)中找出相应的商户编号列表        */
		/**                     供下面的查询使用                                            */
		/***********************************************************/
//		List<Long> categoryInfoList = merchant.getCategoryInfoList();
//		if( categoryInfoList != null && categoryInfoList.size() > 0 ){
//			
//			// 转换成 DBObject - 根据分类列表查询商户详情的查询条件对象
//			DBObject dbObject = changeToDBObjectByCategoryList(categoryInfoList);
//			
//			List<MerchantDetail> merchantDetailList = (List<MerchantDetail>)manager.findAll(dbObject, CB_MERCHANT_DETAIL, MerchantDetail.class);
//			if( merchantDetailList != null && merchantDetailList.size() > 0 ){
//				List<String> merchantIdList = new ArrayList<String>();
//				for( MerchantDetail merchantDetail : merchantDetailList ){
//					merchantIdList.add(merchantDetail.getId());
//				}
//				merchant.setMerchantIdList(merchantIdList);
//			}
//		}
		

		// 转换成 DBObject - 根据分类列表查询商户详情的查询条件对象
		BasicDBObject dbObject = changeToDBObjectByCategoryAndTypeList(merchant.getCategoryInfoList(), merchant.getTypeInfoList());

		if (!dbObject.isEmpty()) {
			List<MerchantDetail> merchantDetailList = (List<MerchantDetail>) manager.findAll(dbObject, CB_MERCHANT_DETAIL, MerchantDetail.class);
			if(CollectionUtils.isEmpty(merchantDetailList)) {
				return null;
			}
			if (merchantDetailList != null && merchantDetailList.size() > 0) {
				List<String> merchantIdList = new ArrayList<String>();
				for (MerchantDetail merchantDetail : merchantDetailList) {
					merchantIdList.add(merchantDetail.getId());
				}
				merchant.setMerchantIdList(merchantIdList);
			}
		}
		return merchant;
	}
	
	// 转换成 DBObject - 根据分类列表查询商户详情的查询条件对象
	private BasicDBObject changeToDBObjectByCategoryAndTypeList(List<Long> categoryInfoList, List<Long> typeInfoList) {
		
		BasicDBObject dbObject = new BasicDBObject();
		int index = 0;
		if (CollectionUtils.isNotEmpty(categoryInfoList)) {
			Long[] categoryIdArr = new Long[categoryInfoList.size()];
			for (Long categoryInfo : categoryInfoList) {
				if (categoryInfo != null) {
					categoryIdArr[index++] = categoryInfo;
				}
			}
			BasicDBObject categorys = new BasicDBObject();
			categorys.append(QueryOperators.IN, categoryIdArr);

			dbObject.put("category_info.category_id", categorys);
		}
		if (CollectionUtils.isNotEmpty(typeInfoList)) {
			Long[] typeIdArr = new Long[typeInfoList.size()];
			index = 0;
			for (Long typeInfo : typeInfoList) {
				if (typeInfo != null) {
					typeIdArr[index++] = typeInfo;
				}
			}

			BasicDBObject types = new BasicDBObject();
			types.append(QueryOperators.IN, typeIdArr);

			dbObject.put("type_info.merchant_type_id", types);
		}
		return dbObject;
		
	}

    /**
     * 分页查询 Merchant
     * @param page
     * @param merchant
     * @return Page    结果集合 
     */
	@Override
	public Page<Merchant> findMerchantByPage(Page<Merchant> page, Merchant merchant) {
		LogCvt.info("商户分页查询开始");
		long st = System.currentTimeMillis();

		List<Merchant> result = new ArrayList<Merchant>(); 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			/**********************操作redis缓存**********************/
			
			
			/**********************操作mongodb数据库**********************/
			// 转换 Merchant 根据 CategoryInfoList 得到 MerchantIdList
			merchant = changeMerchantOfMerchantIdListByCategoryAndTypeList(merchant);
			if(null == merchant) {// 根据分类和类型没查到直接返回  空 
				return page;
			}
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			// 多机构级别查询
			if(merchant.getOrgCode()!= null && merchant.getOrgCode().split(",").length > 0){
				List<String> orgCodeList = new ArrayList<String>(Arrays.asList(merchant.getOrgCode().split(",")));
				List<String> orgCodesCondition = commonLogic.queryLastOrgCode(merchant.getClientId(), orgCodeList);
				merchant.setOrgCodesCondition(orgCodesCondition);
				merchant.setOrgCode(null);
				
			}
			
			
			result = merchantMapper.findByPage(page, merchant); 
			LogCvt.info("[商户管理平台]-商户分页查询-结束！总耗时（" + (System.currentTimeMillis() - st) + "）ms" );
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("分页查询Merchant失败，原因:" + e.getMessage(),e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}

	/**
	 *   组装多级机构商户类别和商户类别导出查询
	 * @param merchant
	 */
	private void fixOrgCodes(Merchant merchant) {
		String clientId = merchant.getClientId();
		if(merchant.getOrgCode()!= null && merchant.getOrgCode().split(",").length > 0){
			List<String> orgCodeList = new ArrayList<String>(Arrays.asList(merchant.getOrgCode().split(",")));
			List<Org> orgList = commonLogic.getOrgByList(clientId, orgCodeList);
			
			Collections.sort(orgList, new Comparator<Org>() {
		        public int compare(Org arg0, Org arg1) {
		            return arg0.getOrgLevel().compareTo(arg1.getOrgLevel());
		        }
		    });
			
			
			// 组织查询的机构信息
			Set<String> sOrgCodes = new HashSet<String>();
			Set<String> tOrgCodes = new HashSet<String>();
			Set<String> fuOrgCodes = new HashSet<String>();
			int maxLevel = 0;
			for(int i = orgList.size() -1; i >=0; i--){
				Org org = orgList.get(i);
				int level = Integer.parseInt(org.getOrgLevel());
				if(level > maxLevel){
					maxLevel = level;
				}
				String provinceAgency = org.getProvinceAgency();
				String cityAgency = org.getCityAgency();
				String countyAgency = org.getCountyAgency();
				String orgCode = org.getOrgCode();
				// 一级机构
//					if(provinceAgency ==null || "".equals(provinceAgency.trim())){
//						fiOrgCodes.add(orgCode);
//						continue;
//					}else{
//						fiOrgCodes.add(provinceAgency);
//					}
				
				// 二级机构
				if(cityAgency ==null || "".equals(cityAgency.trim())){
					
					// 如果二级机构为空，有大于二级机构数据补全三级
					if(level < maxLevel && !sOrgCodes.contains(orgCode)){
						List<Org> subOrgList = commonLogic.queryByParentOrgCode(clientId, orgCode);
						for(Org subOrg : subOrgList){
							if("3".equals(subOrg.getOrgLevel())){
								tOrgCodes.add(subOrg.getOrgCode());
							}else if("4".equals(subOrg.getOrgLevel())){
								fuOrgCodes.add(subOrg.getOrgCode());
							}
						}
					}
					sOrgCodes.add(orgCode);
					continue;
				}else{
					sOrgCodes.add(cityAgency);
				}
				// 三级机构
				if(countyAgency == null || "".equals(countyAgency.trim())){
					
					if(level < maxLevel && !tOrgCodes.contains(orgCode)){
						List<Org> subOrgList = commonLogic.queryByParentOrgCode(clientId, orgCode);
						for(Org subOrg : subOrgList){
							fuOrgCodes.add(subOrg.getOrgCode());
						}
					}
					tOrgCodes.add(orgCode);
					continue;
				}else{
					tOrgCodes.add(countyAgency);
				}
				// 四级机构
				fuOrgCodes.add(cityAgency);
				
			}
			
//			merchant.setsOrgCodes(sOrgCodes);
//			merchant.settOrgCodes(tOrgCodes);
//			merchant.setFoOrgCodes(fuOrgCodes);
		}
		
		merchant.setOrgCode(null);
	}

	/**
	 * 查询商户详情
	 * @param merchat_id
	 * @return    
	 * @see com.froad.logic.MerchantLogic#findMerchatDetailByMerchantId(java.lang.Long)
	 */
	@Override
	public MerchantDetail findMerchatDetailByMerchantId(String merchat_id) {
		/**********************操作Mongodb数据库**********************/
		 // 向mongodb查询数据
		return merchantDetailMongo.findMerchantDetailById(merchat_id);
	}
	
	/**
	 * 分页查询商户详情
	 * @Title: findMerchantDetailByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param mongoPage
	 * @param merchantDetail
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	@Override
	public MongoPage findMerchantDetailByPage(MongoPage mongoPage, MerchantDetail merchantDetail){
		try {
			return merchantDetailMongo.findMerchantDetailByPage(mongoPage, merchantDetail);
		} catch (Exception e) {
			LogCvt.error("分页查询商户详情失败, 原因:" + e.getMessage(), e);
		}
		return mongoPage;
	}

	/**
	 * 禁用指定id商户()
	 * @param merchantId
	 * @return
	 * @throws FroadServerException, Exception    
	 * @see com.froad.logic.MerchantLogic#disableMerchant(java.lang.String)
	 */
	@Override
	public boolean disableMerchant(String merchantId)  throws FroadServerException, Exception {
		LogCvt.info("禁用商户id为"+merchantId+"的商户.");
		Boolean result = null; 
//		SqlSession sqlSession = null;
//		MerchantMapper merchantMapper = null;
		try { 
			Merchant dm = new Merchant();
			dm.setMerchantId(merchantId);
			
			this.deleteMerchant(dm, MerchantDisableStatusEnum.disable);
			
			result =  true;
		} catch (FroadServerException e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
			result = false; 
//			LogCvt.error("禁用Merchant失败，原因:" + e.getMessage()); 
			throw e;
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
			result = false; 
//			LogCvt.error("禁用Merchant失败，原因:" + e.getMessage()); 
			throw e;
		} finally {
//			if(null != sqlSession)
//				sqlSession.close();
		}  
		return result;
	}
	
	/**     
	 * 禁用指定机构下的所有商户()
	 * @param org_code
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#disableMerchant(java.lang.String)    
	 */
	@Override
	public boolean disableMerchant(String client_id,String org_code)  throws FroadServerException, Exception{
		LogCvt.info("禁用客户端id为"+client_id+"机构码为" + org_code + "下的所有商户.");
		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
//			OutletMapper mapper = sqlSession.getMapper(OutletMapper.class);
			
			Merchant m = new Merchant();
			m.setClientId(client_id);
			m.setOrgCode(org_code);
			m.setIsEnable(true);
			List<Merchant> list = merchantMapper.findMerchant(m); 
			
			for (Merchant merchant : list) {
//				this.disableMerchant(merchant);
				this.deleteMerchant(merchant, MerchantDisableStatusEnum.disable);
				
//				Merchant dm = new Merchant();
//				dm.setId(merchant.getId());
//				dm.setMerchantId(merchant.getMerchantId());
//				
//				dm.setIsEnable(false);
//				dm.setDisableStatus(MerchantDisableStatusEnum.disable.getCode()); // 设置为禁用
//				merchantMapper.updateMerchant(dm);
			}
			sqlSession.commit(true);
			result =  true;
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
//			LogCvt.error("查询Merchant失败，原因:" + e.getMessage()); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
//			LogCvt.error("查询Merchant失败，原因:" + e.getMessage()); 
			throw e;
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}  
		return result;
	}
	

	/**     
	 * @param merchant
	 * @return
	 * @throws FroadServerException, Exception    
	 * @see com.froad.logic.MerchantLogic#enableMerchant(com.froad.po.Merchant)    
	 */
	
	@Override
	public boolean enableMerchant(Merchant merchant) throws FroadServerException, Exception {

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			
//			merchant.setIsEnable(false); // 只查询未启用用状态的
			merchant.setDisableStatus(MerchantDisableStatusEnum.disable.getCode()); // 只查询禁用状态的
			List<Merchant> merchantList = merchantMapper.findMerchant(merchant); // 数据库的数据
			
			if(CollectionUtils.isEmpty(merchantList)) {
				throw new FroadServerException("商户不存在");
			}
			for (Merchant merchant2 : merchantList) {
				if(merchant2.getDisableStatus().equals(MerchantDisableStatusEnum.unregistered.getCode())) { // 过滤掉已经解约的商户
					continue;					
				}
				this.enableMerchant(merchant2.getMerchantId());
			}
			result = true;
			// 修改商户日志落地
			new DataPlatLogSupport().modifyMerchantLog(merchant.getClientId(),merchant.getMerchantId());
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
//			LogCvt.error("启用Merchant失败，原因:" + e.getMessage(), e);
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
//			LogCvt.error("启用Merchant失败，原因:" + e.getMessage(), e);
			throw e;
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}  
		return result; 
	}

	
	/**     
	 * @param merchantId
	 * @return
	 * @throws FroadServerException, Exception    
	 * @see com.froad.logic.MerchantLogic#enableMerchant(java.lang.String, java.lang.String)    
	 */
	
	@Override
	public boolean enableMerchant(String merchantId) throws FroadServerException, Exception {

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			
			Merchant merchant = merchantMapper.findMerchantByMerchantId(merchantId); // 数据库的数据
			
			if(null == merchant) {
				throw new FroadServerException("商户不存在");
			}
			if(merchant.getDisableStatus().equals(MerchantDisableStatusEnum.unregistered.getCode()) ) {
				throw new FroadServerException("商户已经解约,不能再启用!");
			}
			if(merchant.getIsEnable()) { // 商户已经是启用状态了
				return true;
			}
			
			Merchant updMerchant = new Merchant();
			updMerchant.setMerchantId(merchantId);
			updMerchant.setIsEnable(true); // 设置为启用
			updMerchant.setDisableStatus(MerchantDisableStatusEnum.normal.getCode());
			merchant.setIsEnable(true); // 设置为启用    后面塞缓存就没必要再重新去查询了
			merchant.setDisableStatus(MerchantDisableStatusEnum.normal.getCode());
			
			result = merchantMapper.updateMerchant(updMerchant);  // 修改商户	
			
			if(result) { // 如果修改成功
				LogCvt.info("MySQL修改merchant成功");
//				merchant = merchantMapper.findMerchantByMerchantId(merchant.getMerchantId());
				/**********************操作mongodb数据库**********************/
				if(!merchant.getMerchantStatus()) { // 不为银行商户才向Mongo中修改数据
					LogCvt.info("修改商户详细信息");
					merchantDetailMongo.updateMerchantDetailById(merchant, null, null);
					
//					OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
//					LogCvt.info("修改商户下面的所有门店的分类信息");
//					outletDetailMongo.updateOutletDetailCategoryInfo(merchant.getMerchantId(), categoryInfoList); // 修改门店表的分类信息
//					LogCvt.info("修改商户下面的所有门店的类型信息");
//					outletDetailMongo.updateOutletDetailTypeInfo(merchant.getMerchantId(), typeInfoList); // 修改门店表的类型信息
					
				}
				
				
				
				/**********************操作redis缓存**********************/
				List<CategoryInfo> categoryInfoList = merchantDetailMongo.findMerchantCategoryInfo(merchant.getMerchantId());
				enableCache(merchant, categoryInfoList);
				
				outletLogic.enableOutletByMerchantId(merchantId);
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
//			LogCvt.error("启用Merchant失败，原因:" + e.getMessage(), e);
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
//			LogCvt.error("启用Merchant失败，原因:" + e.getMessage(), e);
			throw e;
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}  
		return result; 
	}
	
	/**
	 * 续约商户
	 * @Title: extensionMerchant 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @param contractEndtime
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	@Override
	public boolean extensionMerchant(String merchantId, Long contractEndtime) throws FroadServerException, Exception{
		LogCvt.info("续约商户id为" + merchantId + "签约到期时间为" + contractEndtime + ".");
		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
//			OutletMapper mapper = sqlSession.getMapper(OutletMapper.class);
			
			Merchant merchant = new Merchant();
//			merchant = merchantMapper.findMerchantByMerchantId(merchantId);
			
			merchant.setMerchantId(merchantId);
			merchant.setIsEnable(true); // 重置为有效
			
//			LogCvt.info("查询机构码为:" + merchant.getOrgCode() + "的详细信息.");
//			Org org = new Support().getOrgCode(merchant.getClientId(), merchant.getOrgCode());
//			if (org != null) {
//				LogCvt.info("查询机构码为:" + merchant.getOrgCode() + "的一级机构码为:" + org.getProvinceAgency() + ", 二级机构码为:" + org.getCityAgency() + ", 三级机构码为:" + org.getCountyAgency());
//			}
			
			// 续约不需要重新审核
//			merchant.setAuditOrgCode(OrgSuperUtil.getOrgSuper(org)); // 重置待审核机构
//			merchant.setAuditState(ProductAuditState.noAudit.getCode()); // 重置待审核状态
//			merchant.setAuditStage("");// 重置待审核步骤
//			Calendar ca = Calendar.getInstance();
//			ca.set(Calendar.YEAR, 0); // 设置为0000年
//			merchant.setAuditTime(ca.getTime());// 重置待审时间
//			merchant.setAuditComment("");// 重置待审核备注
//			merchant.setAuditStaff("");// 重置待审核人员
//			merchant.setReviewStaff("");// 重置待复核人员

			merchant.setDisableStatus(MerchantDisableStatusEnum.normal.getCode()); // 设置禁用状态为正常状态
			merchant.setContractEndtime(new Date(contractEndtime)); // 设置签约到期时间
			
			
			if(merchantMapper.updateMerchant(merchant)) { // 修改成功
				merchant = merchantMapper.findMerchantByMerchantId(merchantId);
				
				// 续约不需要重新审核
//				MerchantRedis.set_cbank_preaudit_merchant_count_client_id_org_code(merchant.getClientId(), merchant.getAuditOrgCode(), 1); // 待审核商户数量+1
				
				/*修改其他缓存*/
				/**********************操作Mongodb数据库**********************/
				 // 向mongodb插入数据
//				if(!merchant.getMerchantStatus()) { // 不为银行商户才向Mongo中插入数据
//					LogCvt.info("普通商户(非银行默认)添加cb_merchant_detail表");

//					merchantDetailMongo.addMerchant(merchant, categoryInfoList, typeInfoList);
//				}
				merchantDetailMongo.updateMerchantDetailById(merchant, null, null);
				
				/**********************操作Redis缓存**********************/			
				/* 缓存全部商户 */
				LogCvt.info("缓存该商户所有信息");
				MerchantRedis.set_cbbank_merchant_client_id_merchant_id(merchant);
				
				/* 缓存该客户端下改地区所有商户ID */
				LogCvt.info("缓存该客户端下改地区所有商户ID");
				MerchantRedis.set_cbbank_area_merchant_client_id_area_id(merchant);
				
				/* 缓存该客户端下面所有置顶商户 */
				if (merchant.getIsTop()) { // 如果是置顶商户
					LogCvt.info("缓存该客户端下面所有置顶商户");
					MerchantRedis.set_cbbank_top_merchant_client_id(merchant);
				}
				
				/* 修改待审核商户数量 */
//				if (merchant.getAuditState() == null || merchant.getAuditState().equals(ProductAuditState.noAudit.getCode())) { // 如果商户未通过审核   则缓存中待审核数量+1
//					LogCvt.info("缓存增加待审核商户数量");
////					MerchantRedis.set_cbank_preaudit_merchant_count_client_id_org_code(merchant.getClientId(), merchant.getOrgCode(), 1);
//						MerchantRedis.set_cbank_preaudit_merchant_count_client_id_org_code(merchant.getClientId(), merchant.getAuditOrgCode(), 1);
//				}
				
				
				/* 缓存商户分类下的商户id */
				List<CategoryInfo> categoryInfoList = merchantDetailMongo.findMerchantCategoryInfo(merchant.getMerchantId());
				if(CollectionUtils.isNotEmpty(categoryInfoList)) {
					for (CategoryInfo categoryInfo : categoryInfoList) {
						LogCvt.info("缓存商户分类id:" + categoryInfo.getCategoryId() + "下的商户id");
						MerchantCategoryRedis.set_cbbank_merchant_category_all_client_id_merchant_category_id(merchant.getClientId(), categoryInfo.getCategoryId(), merchant.getMerchantId());
					}
				}
			}
			
			sqlSession.commit(true);
			result = true;
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = false;
//			LogCvt.error("续约Merchant失败，原因:" + e.getMessage()); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = false;
//			LogCvt.error("续约Merchant失败，原因:" + e.getMessage()); 
			throw e;
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}  
		return result;
	}
	
	
//	/**
//	 * 禁门店
//	  * @Title: disableOutlet
//	  * @Description: TODO
//	  * @author: zhangxiaohua 2015-4-9
//	  * @modify: zhangxiaohua 2015-4-9
//	  * @param @param merchantId
//	  * @param @param sqlSession    
//	  * @return void    
//	  * @throws
//	 */
//	public void disableOutlet(String merchantId,OutletMapper mapper, OutletDetailMongo outletDetailMongo ) throws FroadServerException, Exception {
//		
//		Outlet outlet = new Outlet();
//		outlet.setMerchantId(merchantId);
//		List<Outlet> list = mapper.findOutlet(outlet);
//		
//		for (Outlet outlet2 : list) {
//			outlet2.setIsEnable(false);
//			
//			//修改mysql状态
//			mapper.updateOutlet(outlet2);
//			
//			//修改mongodb状态
//			outletDetailMongo.updateOutletDetail(outlet2, null);
//			
//			//修改redis状态
//			OutletRedis.set_cbbank_outlet_client_id_merchant_id_outlet_id(outlet2);
//		}
//		
//	}
	
	
	/**
	 * 查询orgCodeList机构所有的预售网点
	 * @Title: findBankMerchant 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param orgCodeList
	 * @return
	 * @return List<Merchant>    返回类型 
	 * @throws
	 */
	public List<Merchant> findBankMerchant(String clientId, List<String> orgCodeList){
		List<Merchant> result = null; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			/**********************操作redis缓存**********************/
			
			
			/**********************操作mongodb数据库**********************/
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			 
			result = merchantMapper.findBankMerchant(clientId, orgCodeList) ;
			// sqlSession.commit(true);
			
			
			
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);  
			LogCvt.error("查询银行虚拟商户失败，原因:" + e.getMessage(), e); 
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}  
		return result; 
	}
	
	
	
	/**
	 * 根据商户id集合查询详情
	 * @Title: findMerchantDetailbyOutletIdList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletIdList
	 * @return
	 * @return List<MerchantDetail>    返回类型 
	 * @throws
	 */
	public List<MerchantDetail> findMerchantDetailbyMerchantIdList(List<String> merchantIdList){
		return merchantDetailMongo.findMerchantDetailbyOutletIdList(merchantIdList);
	}

	
	/**
	 * 根据商户id集合商户简称(key为商户id,value为商户名称)
	 * @Title: findMerchantNamebyMerchantIdList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantIdList
	 * @return
	 * @return List<Map<String, String>>    返回类型 
	 * @throws
	 */
	public Map<String, String> findMerchantNamebyMerchantIdList(String clientId,List<String> merchantIdList){
		if (StringUtils.isEmpty(clientId)) {
			LogCvt.info("根据客户端id和商户id集合, 客户端id为空, 直接查询数据库");
			return merchantDetailMongo.findMerchantNamebyOutletIdList(merchantIdList);
		} else {
			Map<String, String> resultMap = new HashMap<String, String>();
			for (String merchant_id : merchantIdList) {
				Map<String, String> map = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchant_id);
				if(map != null && !map.isEmpty()) {
					resultMap.put(merchant_id, map.get("merchant_name"));
				}
			}
			return resultMap;
		}
//		return null;
	}

	/**
	 * 查询商户分类
	 * @Title: findMerchantCategoryInfo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @return List<CategoryInfo>    返回类型 
	 * @throws
	 */
	public List<CategoryInfo> findMerchantCategoryInfo(String merchantId){
		return merchantDetailMongo.findMerchantCategoryInfo(merchantId);
	}
	
	/**
	 * 查询商户类型
	 * @Title: findMerchantCategoryInfo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @return List<TypeInfo>    返回类型 
	 * @throws
	 */
	public List<TypeInfo> findMerchantTypeInfo(String merchantId){
		return merchantDetailMongo.findMerchantTypeInfo(merchantId);
	}


	@Override
	public Boolean updateMerchant(Merchant merchant)
			throws FroadServerException, Exception {

		Boolean result = false; 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			
			final String merchantId = merchant.getMerchantId();
			final String nowMerchantName = merchant.getMerchantName();
			
			Merchant merchantOld = merchantMapper.findMerchantByMerchantId(merchant.getMerchantId()); // 数据库的数据
			
			result = merchantMapper.updateMerchantByAuditThrough(merchant);  // 修改商户	
			if(result) { // 如果修改成功
				LogCvt.info("MySQL审核通过商户修改merchant成功");
				merchant = merchantMapper.findMerchantByMerchantId(merchant.getMerchantId());
				/**********************操作mongodb数据库**********************/
				if(!merchant.getMerchantStatus()) { // 不为银行商户才向Mongo中修改数据
					LogCvt.info("修改商户详细信息");
					merchantDetailMongo.updateMerchantDetailById(merchant, null, null);
					
				}
				
				
				if(merchant.getMerchantStatus()) { // 为银行商户才向Redis中插入数据
					LogCvt.info("缓存该银行虚拟商户所有信息");
					MerchantRedis.set_cbbank_bank_merchant_client_id_org_code(merchant);
				}
				/* 更新全部商户缓存 */
				LogCvt.info("修改商户信息缓存");
				merchantOld.setMerchantName(merchant.getMerchantName());
				merchantOld.setLogo(merchant.getLogo());
				merchantOld.setPhone(merchant.getPhone());
				merchantOld.setMerchantStatus(merchant.getMerchantStatus());
				merchantOld.setAreaId(merchant.getAreaId());
				merchantOld.setOrgCode(merchant.getOrgCode());
				merchantOld.setIsEnable(merchant.getIsEnable());
				merchantOld.setDisableStatus(merchant.getDisableStatus());
				merchantOld.setUserOrgCode(merchant.getUserOrgCode());
				MerchantRedis.set_cbbank_merchant_client_id_merchant_id(merchantOld);
				
//				if (!merchantOld.getAreaId().equals(merchant.getAreaId())) { // 修改了地区id
//					LogCvt.info("修改了商户id为:" + merchant.getMerchantId() + "的地区:" + merchantOld.getAreaId() + "为地区:" + merchant.getAreaId());
//					
//					LogCvt.info("删除缓存该客户端下原地区:" + merchantOld.getAreaId() + ",下的商户ID:" + merchantOld.getMerchantId());
//					MerchantRedis.del_cbbank_area_merchant_client_id_area_id(merchantOld.getClientId(), merchantOld.getAreaId(), merchantOld.getMerchantId());
//
//					/* 缓存该客户端下改地区所有商户ID */
//					LogCvt.info("重新缓存该客户端下现地区:" + merchant.getAreaId() + ",下商户ID:" + merchant.getMerchantId());
//					MerchantRedis.set_cbbank_area_merchant_client_id_area_id(merchant);
//
//				}
				
				if(!merchant.getIsTop()) { // 删除置顶缓存
					LogCvt.info("删除商户id为" + merchant.getClientId() + "的置顶缓存");
					MerchantRedis.del_cbbank_top_merchant_client_id(merchant.getClientId(), merchant.getMerchantId());
				}
							
				
				sqlSession.commit(true); 
				
				/*异步修改商品表的商户名字*/
				String oldMerchantName = merchantOld.getMerchantName();
				if(StringUtils.isNotBlank(nowMerchantName) && !nowMerchantName.equals(oldMerchantName)) {
					LogCvt.info("修改了商户名称, 为了不影响主流程[启线程,异步修改]商品表中的商户名称!!!!");
					FroadThreadPool.execute(new Runnable() {
						
						@Override
						public void run() {
							CommonLogic commonLogic = new CommonLogicImpl();
							commonLogic.updateProduct_MerchantNameByMerchantId(merchantId, nowMerchantName);
						}
					});
				}
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
			throw e;
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}  
		return result; 

	}

	/**
	 * 商户列表信息导出功能.
	 * 
	 */
	@Override
	public MerchantExportData exportMerchantManage(List<Merchant> merchantList,Merchant tempMerchant) {
		LogCvt.info("构建商户列表报表Merchant");
		MerchantExportData merchantExportData = new MerchantExportData();
		List<MerchantDetail> merchantDetailMongoList = null;
		Map<String, List<TypeInfo>> typeInfoMap = null;
		Map<String,List<CategoryInfo>> categoryInfoMap = null;
		// 构建报表头部
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("营业执照号");
		header.add("商户名称");
		header.add("用户名");
		header.add("审核员");
		header.add("创建时间");
		header.add("审核时间");
		header.add("审核状态");
		header.add("联系人手机");
		header.add("联系人电话");
		header.add("签约人");	
		header.add("商户类型");
		header.add("签约行机构");
		header.add("证件号");		
		header.add("所属机构");		
		header.add("所属分类");
		header.add("是否外包");
		header.add("收款账户名");
		header.add("收款账户号");
		header.add("登录人手机");

		// 构建报表内容体
		List<List<String>> data = new ArrayList<List<String>>();
		int serNo = 0;
		// 1:获取商户ID集合
		List<String> merchantIdList = new ArrayList<String>();
		for (Merchant merchant : merchantList) {
			if (!merchantIdList.contains((merchant.getMerchantId()))) {
				merchantIdList.add(merchant.getMerchantId());
			}
		}

		// 2:查询mongo集合
		if(null != merchantIdList && merchantIdList.size()>0){
			long beginTime = System.currentTimeMillis();
			DBObject queryMerchantDetail = new BasicDBObject();
			DBObject merchantIdCondition = new BasicDBObject();
			merchantIdCondition.put(QueryOperators.IN, merchantIdList.toArray());
			queryMerchantDetail.put("_id", merchantIdCondition);
			merchantDetailMongoList = (List<MerchantDetail>) manager.findAll(queryMerchantDetail,MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
			LogCvt.info("[商户详情]-查询Mongo详情-查询耗时：" + (System.currentTimeMillis() - beginTime)	+ "ms");
			
			if(null != merchantDetailMongoList && merchantDetailMongoList.size()>0){
				//  3:设置商户类型
				typeInfoMap = new HashMap<String, List<TypeInfo>>();
				categoryInfoMap = new HashMap<String,List<CategoryInfo>>();
				
				for (MerchantDetail merchantDetail : merchantDetailMongoList) {
					typeInfoMap.put(merchantDetail.getId(),	merchantDetail.getTypeInfo());
					categoryInfoMap.put(merchantDetail.getId(),	merchantDetail.getCategoryInfo());
				}
			}
		}
		
		if (null != merchantList && merchantList.size() > 0) {
			List<String> columnList = null;
			List<com.froad.po.mongo.TypeInfo> typeInfoVoList = null;
			List<com.froad.po.mongo.CategoryInfo> categoryInfoVoList = null;
			StringBuffer typeInfoVoSb = null;
			StringBuffer categoryInfoSb = null;
			for (Merchant mervhant : merchantList) {
				// 检查是否重复的商户数据,因为历史数据超级管理员可能是多个
				if(mervhant.getId().equals(tempMerchant.getId()))continue;
				tempMerchant.setId(mervhant.getId());
				
				columnList = new ArrayList<String>();
				columnList.add("" + (++serNo));
				columnList.add(mervhant.getLicense()==null?"":mervhant.getLicense());				
				columnList.add(mervhant.getMerchantName() == null ? "": mervhant.getMerchantName());				
				columnList.add(mervhant.getMerchantUserName()==null?"":mervhant.getMerchantUserName());
				columnList.add(mervhant.getAuditStaff() == null ? "": mervhant.getAuditStaff());
				String createTimeValue = "";
				String auditTimeValue = "";
				if (null != mervhant.getCreateTime()) {
					createTimeValue = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1,mervhant.getCreateTime());
				}
				if (null != mervhant.getAuditTime()) {
					auditTimeValue = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1,mervhant.getAuditTime());
				}
				columnList.add(createTimeValue);
				columnList.add(auditTimeValue);
				
				if (mervhant.getAuditState().equals(ProductAuditState.noAudit.getCode())) {
					columnList.add(ProductAuditState.noAudit.getDescribe());// 状态
				} else if (mervhant.getAuditState().equals(ProductAuditState.passAudit.getCode())) {
					columnList.add(ProductAuditState.passAudit.getDescribe());// 状态
				} else if (mervhant.getAuditState().equals(ProductAuditState.failAudit.getCode())) {
					columnList.add(ProductAuditState.failAudit.getDescribe());// 状态
				} else if (mervhant.getAuditState().equals(ProductAuditState.noCommit.getCode())) {
					columnList.add(ProductAuditState.noCommit.getDescribe());// 状态
				} else if (mervhant.getAuditState().equals(ProductAuditState.waitSynchAudit.getCode())) {
					columnList.add(ProductAuditState.waitSynchAudit.getDescribe());// 状态
				} else  {
					columnList.add("");// 状态
				}
				//联系人手机
				columnList.add(mervhant.getContactPhone()==null?"":mervhant.getContactPhone());
				//联系人电话
				columnList.add(mervhant.getContactTelephone()==null?"":mervhant.getContactTelephone());
				//签约人
				columnList.add(mervhant.getContractStaff()==null?"":mervhant.getContractStaff());
				
				String merchantTypeVal = "";
				if(EmptyChecker.isNotEmpty(typeInfoMap)){
					typeInfoVoList = typeInfoMap.get(mervhant.getMerchantId());					
				}
				if (null != typeInfoVoList && typeInfoVoList.size() > 0) {
					typeInfoVoSb = new StringBuffer();
					for (com.froad.po.mongo.TypeInfo typeInfoVo : typeInfoVoList) {
						typeInfoVoSb.append(typeInfoVo.getTypeName()).append(",");
					}
					merchantTypeVal = typeInfoVoSb.toString();
					columnList.add(StringUtils.substring(merchantTypeVal, 0,merchantTypeVal.length() - 1));
				} else {
					columnList.add(merchantTypeVal);
				}
				
				//签约行机构
				columnList.add(mervhant.getOrgName()==null?"":mervhant.getOrgName());
				//证件号
				columnList.add(mervhant.getLegalCredentNo()==null?"":mervhant.getLegalCredentNo());
				//所属机构
				columnList.add(mervhant.getOrgName() == null ? "" : mervhant.getOrgName());
				
				//所属分类
				String merchantCategoryVal = "";
				if(EmptyChecker.isNotEmpty(categoryInfoMap)){
					categoryInfoVoList = categoryInfoMap.get(mervhant.getMerchantId());					
				}
				if (null != categoryInfoVoList && categoryInfoVoList.size() > 0) {
					categoryInfoSb = new StringBuffer();
					for (com.froad.po.mongo.CategoryInfo categoryInfo : categoryInfoVoList) {
						categoryInfoSb.append(categoryInfo.getName()).append(",");
					}
					merchantCategoryVal = categoryInfoSb.toString();
					columnList.add(StringUtils.substring(merchantCategoryVal, 0,merchantCategoryVal.length() - 1));
				} else {
					columnList.add(merchantCategoryVal);
				}
				
				//是否外包
				String isOutsource = "";
				if(null != mervhant.getIsOutsource()){
					if(mervhant.getIsOutsource()==true){
						isOutsource = "是";
					}else{
						isOutsource = "否";
					}
				}else{
					isOutsource = "否";
				}
				columnList.add(isOutsource);
				//收款账户名
				columnList.add(mervhant.getAcctName()==null?"":mervhant.getAcctName());
				//收款账号
				columnList.add(mervhant.getAcctNumber()==null?"":mervhant.getAcctNumber());
				//登录人手机
				columnList.add(mervhant.getPhone()==null?"":mervhant.getPhone());
				data.add(columnList);
			}
		}
		merchantExportData.setHeader(header);
		merchantExportData.setData(data);
		merchantExportData.setSheetName("商户列表");
		merchantExportData.setExcelFileName("商户列表查询");
		return merchantExportData;
	}

	/**
	 * 同步更改商户状态、同步redis、mongo.(提供定时任务调用的)
	 * @param merchantId 商户Id
	 * @param clientId  客户端Id
	 * @param isSynSucc true-同步成功，false-同步失败
	 * @param synType  0-同步通知，1-审核通知
	 * @return
	 * @throws FroadServerException
	 * @throws Exception
	 */
	@Override
	public Boolean syncMerchantInfo(String merchantId,String clientId,String isSynSucc,int synType)throws FroadServerException, Exception {		
		LogCvt.info("定时任务同步更改商户状态开始,商户Id:"+merchantId+",客户端Id:"+clientId+",成功状态:"+isSynSucc+",通知类型:"+(synType==0?"同步通知":"审核通知"));
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		Boolean isSuccess = false;
		String merchantAuditState = "";
		boolean isSynSuccess = (Constants.SYN_OR_AUDIT_SUCCESS.equals(isSynSucc)?true:false);
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			Merchant merchantOld = merchantMapper.findMerchantByMerchantId(merchantId);
			if(Checker.isNotEmpty(merchantOld)){
				merchantAuditState = merchantOld.getAuditState();
				/***********************判断录入审核/编辑审核****************************/
				int auditTypeFlag = Constants.INPUT_AUDIT;//审核类型标识：0-录入审核 1-编辑审核
				if(ProductAuditState.passAudit.getCode().equals(merchantAuditState)){
					if(ProductAuditState.passAudit.equals(merchantOld.getEditAuditState())){
						LogCvt.info("商户已审核通过，商户id:"+merchantId+",审核状态:"+merchantOld.getAuditState());
						return true;
					}					
        			if(ProductAuditState.noAudit.getCode().equals(merchantOld.getEditAuditState())
        			   || ProductAuditState.waitSynchAudit.getCode().equals(merchantOld.getEditAuditState())){
        				auditTypeFlag = Constants.EDIT_AUDIT;//编辑审核
        			}
            	}
				
				Merchant merchant = new Merchant();
				merchant.setMerchantId(merchantId);
				merchant.setClientId(clientId);

				//同步操作
				if(Constants.SYN_TYPE == synType){	//同步通知
				     isSuccess = this.synMerchantSynType(auditTypeFlag,merchant,merchantMapper,isSynSuccess);							
				}else if(Constants.AUDIT_TYPE ==synType){//审核通知
					isSuccess =  this.synMerchantAuditType(auditTypeFlag,merchant,merchantMapper,isSynSuccess);
				}
				sqlSession.commit(true); 
			}else{
				LogCvt.error("商户信息不存在,商户Id:"+merchantId);	
			}
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			LogCvt.error("同步更改商户状态失败,商户ID："+merchantId+",审核状态:"+merchantAuditState+",原因:" + e.getMessage() , e);
			throw new FroadServerException("同步更改商户状态失败,商户ID："+merchantId+",审核状态:"+merchantAuditState+",原因:" + e.getMessage() , e);
		
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			LogCvt.error("同步更改商户状态操作[系统异常],商户ID："+merchantId+",审核状态:"+merchantAuditState+",原因:" + e.getMessage(), e);
			throw new FroadServerException("同步更改商户状态操作[系统异常],商户ID："+merchantId+",审核状态:"+merchantAuditState+",原因:" + e.getMessage() , e);
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		LogCvt.info("同步更改商户状态结束");
	return isSuccess; 
	}
	
	/**
	 * 同步白名单操作，内部通过商户DB及Redis.
	 * 
	 * @param merchantMapper
	 * @param merchant
	 * @return
	 */
	private boolean synDbOrRedis(MerchantMapper merchantMapper,Merchant merchant){
		boolean isSuccess = false;
		//1:更新商户信息
		isSuccess = merchantMapper.updateMerchant(merchant);
		
		//2:更新redis
		isSuccess =	MerchantRedis.updateAuditMerchant(merchant);

		return isSuccess;
	}
	
	
	/**
	 * 同步白名单操作，内部同步审核操作的信息.
	 * @param merchantId
	 * @param clientId
	 * @param merchant
	 * @param merchantMapper
	 * @param isSynSuccess
	 * @param auditStartOrgCode
	 * @return
	 * @throws TException
	 */
	private boolean synMerchantAuditType(int auditTypeFlag,Merchant merchant,MerchantMapper merchantMapper,boolean isSynSuccess) throws TException{
		boolean isSuccess = false;
		String clientId = merchant.getClientId();
		String merchantId = merchant.getMerchantId();
		
		//审核通过通知
		if(isSynSuccess){
			//行内审核模式的商户状态改成审核通过，isenable =true； 同步redis、mongo,发短信
			if (ClientUtil.getClientId(clientId)) {
				if(auditTypeFlag == Constants.INPUT_AUDIT){//录入审核
					merchant.setAuditState(ProductAuditState.passAudit.getCode());
					merchant.setIsEnable(true);
					
					//1：更新mysql及redis
					isSuccess = synDbOrRedis(merchantMapper,merchant);

					//2:更新mongo
					isSuccess = MerchantMongo.updateAuditMerchant(merchant);
					
					//3:发短信
					LogCvt.info("发送短信信息");
					sendSmeMessage(merchantId,clientId);
				}else{
					//编辑审核，只修改编辑审核状态
					merchant.setEditAuditState(ProductAuditState.passAudit.getCode());
					isSuccess = merchantMapper.updateMerchant(merchant);
				}
				//打印日志，便于跟踪
				LogCvt.info(ClientUtil.getClientId(clientId)?"行内":"非行内"+"审核模式审核"+(isSynSuccess?"通过":"不通过")
						+"["+(auditTypeFlag==0?"录入审核":"编辑审核")+"]，商户id:"+merchant.getMerchantId()
						+","+(auditTypeFlag==0?"录入审核":"编辑审核")+"状态:"
						+(auditTypeFlag==0?merchant.getAuditState():merchant.getEditAuditState()));

			}
			
		}else{
			//行内审核模式的商户状态改成审核不通过,审核备注=商户账户白名单银行审核不通过， 同步redis、mongo,
			if (ClientUtil.getClientId(clientId)) {
				if(auditTypeFlag == Constants.INPUT_AUDIT){//录入审核
					merchant.setAuditState(ProductAuditState.failAudit.getCode());
					merchant.setAuditComment("商户账户白名单银行审核不通过");
					
					isSuccess = synDbOrRedis(merchantMapper,merchant);				

					//2:更新mongo
					isSuccess = MerchantMongo.updateAuditMerchant(merchant);

				}else{
					//编辑审核，只修改编辑审核状态
					merchant.setEditAuditState(ProductAuditState.failAudit.getCode());
					isSuccess = merchantMapper.updateMerchant(merchant);
				}
				//打印日志，便于跟踪
				LogCvt.info(ClientUtil.getClientId(clientId)?"行内":"非行内"+"审核模式审核"+(isSynSuccess?"通过":"不通过")
						+"["+(auditTypeFlag==0?"录入审核":"编辑审核")+"]，商户id:"+merchant.getMerchantId()
						+","+(auditTypeFlag==0?"录入审核":"编辑审核")+"状态:"
						+(auditTypeFlag==0?merchant.getAuditState():merchant.getEditAuditState()));

			}
		}
		return isSuccess;
	}
	
	
	/**
	 * 同步白名单，内部同步方法.(审核及定时任务公用的一个接口)
	 * 
	 * @param merchantId
	 * @param clientId
	 * @param merchant 需修改的merchant对象
	 * @param merchantMapper
	 * @param isSynSuccess 是否同步成功
	 * @return
	 * @throws TException
	 */
	@Override
	public boolean synMerchantSynType(int auditTypeFlag,Merchant merchant,MerchantMapper merchantMapper,boolean isSynSuccess) throws TException{
		boolean isSuccess = false;
		String clientId = merchant.getClientId();
		String merchantId = merchant.getMerchantId();
		
		if(isSynSuccess){		
			//不走行内审核
			if (!ClientUtil.getClientId(clientId)) {
				//非行内审核模式-同步成功
				if(auditTypeFlag == Constants.INPUT_AUDIT ){//录入审核
					merchant.setAuditState(ProductAuditState.passAudit.getCode());
					merchant.setIsEnable(true);
					
					//1:更新mysql及redis
					isSuccess = synDbOrRedis(merchantMapper,merchant);			
		
					//2:更新mongo
					isSuccess = MerchantMongo.updateAuditMerchant(merchant);
					
					//3:发短信
					LogCvt.info("发送短信信息");
					sendSmeMessage(merchantId,clientId);
					
				}else {
					//编辑审核，只修改编辑审核状态
					merchant.setEditAuditState(ProductAuditState.passAudit.getCode());
					isSuccess = merchantMapper.updateMerchant(merchant);
				}
				
			} else {
				//行内审核模式-同步成功
				if(auditTypeFlag == Constants.INPUT_AUDIT ){//录入审核
					merchant.setAuditState(ProductAuditState.waitSynchAudit.getCode());
					isSuccess = synDbOrRedis(merchantMapper,merchant);	
				}else {
					merchant.setEditAuditState(ProductAuditState.waitSynchAudit.getCode());
					isSuccess = merchantMapper.updateMerchant(merchant);
				}
					
			}	
		}else{
			// 行内审核模式，改成更改商户状态为审核通过白名单待同步4
			// 非行内审核模式，同步失败不处理定时任务同步成功结果后通知，此时商户是待审核状态。
			if (ClientUtil.getClientId(clientId)) {
				//行内审核模式-同步失败
				if(auditTypeFlag == Constants.INPUT_AUDIT ){//录入审核
					merchant.setAuditState(ProductAuditState.waitSynchAudit.getCode());
					isSuccess = synDbOrRedis(merchantMapper,merchant);		
				}else{
					merchant.setEditAuditState(ProductAuditState.waitSynchAudit.getCode());
					isSuccess = merchantMapper.updateMerchant(merchant);
				}
			}else{
				if(auditTypeFlag == Constants.INPUT_AUDIT ){
					//录入审核（需更新外部传进来的merchant修改信息）
					isSuccess = merchantMapper.updateMerchant(merchant);
				}
			}
		}
		//打印日志，便于跟踪
		LogCvt.info((ClientUtil.getClientId(clientId)?"行内":"非行内")+"审核模式同步"+(isSynSuccess?"成功":"失败")
				+"["+(auditTypeFlag==0?"录入审核":"编辑审核")+"]，商户id:"+merchant.getMerchantId()
				+","+(auditTypeFlag==0?"录入审核":"编辑审核")+"状态:"
				+(auditTypeFlag==0?merchant.getAuditState():merchant.getEditAuditState())+";注：状态值为null表示未修改状态");
		
		return isSuccess;
	}
	
	
	
	/**
	 * 同步白名单，发送短信操作.
	 * 
	 * @param merchantId 商户Id
	 * @param clientId 客户端Id
	 * @throws TException
	 */
	private void sendSmeMessage(String merchantId,String clientId) throws TException{
		List<String> valueList = new ArrayList<String>();
		MerchantUserLogic userLogic = new MerchantUserLogicImpl();
		MerchantUser merchantUser = new MerchantUser();
		merchantUser.setClientId(clientId);
		merchantUser.setMerchantId(merchantId);
		merchantUser.setMerchantRoleId(100000000l);
		String userName = "";
		String phone = "";
		List<MerchantUser> merchantUserList = userLogic.findMerchantUser(merchantUser);
		if(null != merchantUserList && merchantUserList.size()>0){
			merchantUser = merchantUserList.get(0);
			
			userName = merchantUser.getUsername() == null ? "": merchantUser.getUsername();
			phone = merchantUser.getPhone() == null ? "" : merchantUser.getPhone();
			
			//构建短信内容
			valueList.add(userName);
			valueList.add(phone);
			valueList.add(Constants.RESET_PASSWORD_INIT);
			
			//构建短信VO对象
			SmsMessageVo smsMessageVo = new SmsMessageVo();
			smsMessageVo.setSmsType(Constants.RESET_PASSWORD_SMS_TYPE);
			smsMessageVo.setMobile(phone);
			smsMessageVo.setValues(valueList);
			smsMessageVo.setClientId(clientId);	
			
			support.sendSmsMessage(smsMessageVo);
		}		
	}


	/**
	 * 商户列表查询导出分页查询.
	 */
	@Override
	public Page<Merchant> findExportByPage(Page<Merchant> page,	Merchant merchant) {		
		LogCvt.info("商户列表导出分页查询开始");
		long st = System.currentTimeMillis();

		List<Merchant> result = new ArrayList<Merchant>(); 
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		try { 
			
			
			/**********************操作mongodb数据库**********************/
			// 转换 Merchant 根据 CategoryInfoList 得到 MerchantIdList
			merchant = changeMerchantOfMerchantIdListByCategoryAndTypeList(merchant);
			if(null == merchant) {// 根据分类和类型没查到直接返回  空 
				return page;
			}
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);

			String orgCodeArray[] = StringUtils.split(merchant.getOrgCode(),',');
			// 多机构级别查询
			if(merchant.getOrgCode()!= null &&  orgCodeArray.length > 0){
				List<String> orgCodeList = new ArrayList<String>(Arrays.asList(orgCodeArray));
				List<String> orgCodesCondition = commonLogic.queryLastOrgCode(merchant.getClientId(), orgCodeList);
				merchant.setOrgCodesCondition(orgCodesCondition);
				merchant.setOrgCode(null);
				
			}
			result = merchantMapper.findExportByPage(page, merchant); 
			LogCvt.info("[商户管理平台]-商户列表导出分页查询-结束！总耗时（" + (System.currentTimeMillis() - st) + "）ms" );
			page.setResultsContent(result);
		} catch (Exception e) { 
			LogCvt.error("商户列表导出分页查询Merchant失败，原因:" + e.getMessage(),e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 
	}

}