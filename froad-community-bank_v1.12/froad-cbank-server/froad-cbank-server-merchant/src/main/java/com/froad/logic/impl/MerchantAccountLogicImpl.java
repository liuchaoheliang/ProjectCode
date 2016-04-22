/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: MerchantAccountLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.Constants;
import com.froad.db.mongo.MerchantDetailMongo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.MerchantAccountMapper;
import com.froad.db.redis.MerchantAccountRedis;
import com.froad.db.redis.OutletRedis;
import com.froad.enums.AccountAuditState;
import com.froad.enums.AccountSynchState;
import com.froad.enums.AccountTypeEnum;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.log.MerchantLogs;
import com.froad.log.vo.MerchantDetailLog;
import com.froad.log.vo.MerchantLog;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantAccountLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.OutletLogic;
import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.Outlet;
import com.froad.po.Result;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.support.DataPlatLogSupport;
import com.froad.support.Support;
import com.froad.thrift.vo.MerchantBankWhiteVo;
import com.froad.util.Checker;
import com.froad.util.ClientUtil;
import com.froad.util.Md5Utils;


/**
 * 
 * <p>@Title: MerchantAccountLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantAccountLogicImpl implements MerchantAccountLogic {
	private Support support = new Support();
	
	private OutletLogic outletLogic = new OutletLogicImpl();
	private MerchantLogic merchantLogic = new MerchantLogicImpl();
	private CommonLogic  commonLogic = new CommonLogicImpl();
	private MerchantLogs logs = new MerchantLogs();
	
    /**
     * 增加 MerchantAccount
     * @param merchantAccount
     * @return Long    主键ID
     */
	@Override
	public  Long addMerchantAccount(MerchantAccount merchantAccount) throws FroadServerException,Exception{

		Long result = -1l; 
		SqlSession sqlSession = null;
		MerchantAccountMapper merchantAccountMapper = null;
		try { 
			String clientId = merchantAccount.getClientId();
			String merchantId = merchantAccount.getMerchantId();
			String outletId = merchantAccount.getOutletId();
			
			Map<String, String> merchantMap = commonLogic.getMerchantRedis(clientId, merchantId); // 获取商户信息
			if(null == merchantMap || merchantMap.isEmpty()) {
				throw new FroadServerException("商户信息不存在!");
			}
//			if (!BooleanUtils.toBoolean(merchantMap.get("is_enable"), "1", "0")) {
//				throw new FroadServerException("商户状态不正常!");
//			}
			if (StringUtils.equals(merchantMap.get("disable_status"), MerchantDisableStatusEnum.unregistered.getCode())) {
				throw new FroadServerException("商户已经解约!");
			}
			
//			String merchantName = merchantMap.get("merchant_name");
			
//			Map<String, String> outletMap = null;
//			if(StringUtils.isNotBlank(outletId) && !outletId.equals("0")) { // 添加的是门店账号  
//				outletMap = outletLogic.findOutletRedis(clientId, merchantId, outletId);
//				if(null == outletMap || outletMap.isEmpty()) {
//					throw new FroadServerException("门店信息不存在!");
//				}
////				if (!BooleanUtils.toBoolean(outletMap.get("is_enable"), "1", "0")) {
////					throw new FroadServerException("门店状态不正常!");
////				}
//				if (StringUtils.equals(outletMap.get("disable_status"), OutletDisableStatusEnum.delete.getCode())) {
//					throw new FroadServerException("门店已经删除!");
//				}
//			}
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantAccountMapper = sqlSession.getMapper(MerchantAccountMapper.class);
			
			MerchantAccount mat = new MerchantAccount();
			mat.setMerchantId(merchantId);
			mat.setOutletId(outletId);
			mat.setIsDelete(merchantAccount.getIsDelete());
			
			List<MerchantAccount> list = merchantAccountMapper.findMerchantAccount(mat);
			
			//商户有且只有一个账号
			if(null != list && list.size() > 0){
				throw new FroadServerException("商户/门店账号已存在!");
			}
			merchantAccount.setCreateTime(new Date()); // 创建时间
			merchantAccount.setSynchState(AccountSynchState.init.getCode());
			merchantAccount.setAuditState(AccountAuditState.init.getCode());
			//录入时,初始化mac值.
			String acctMac = this.findMerchantAccountMac(merchantAccount.getClientId(), merchantAccount.getAcctNumber(), merchantAccount.getAcctName());
			merchantAccount.setMac(acctMac);
			
			if (merchantAccountMapper.addMerchantAccount(merchantAccount) > -1) { 
				result = merchantAccount.getId(); 
				LogCvt.info("MySQL添加商户/门店账号成功");
			}

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			
			if(result > 0){
			/* 设置全部缓存 */
				if(StringUtils.isBlank(merchantAccount.getClientId()) 
						|| StringUtils.isBlank(merchantAccount.getMerchantId())
						|| StringUtils.isBlank(merchantAccount.getOutletId())){
					throw new FroadServerException("设置MerchantAccount缓存数据时clientID/merchanId/outletId不能为空!");
				}
				boolean flag  = MerchantAccountRedis.set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(merchantAccount);
				if(!flag){
					throw new FroadServerException("设置MerchantAccount缓存数据失败!");
				}
				/** begin:根据V1.1中重庆白名单审核需求，去除账户白名单同步代码**/
				
//				//根据每个行的规则做白名单添加(目前重庆无需添加,安徽需要添加)
//				if(ClientUtil.getClientId(merchantAccount.getClientId())){
//					
//					/* 添加银行账号白名单 */			
//					support.addMerchantBankWhiteList(merchantId, merchantName, merchantAccount.getAcctNumber(), merchantAccount.getClientId(), merchantAccount.getAcctName());
//				}
				/** end:根据V1.1中重庆白名单审核需求，去除账户白名单同步代码**/
				
			/* 方付通商户的账户 */
//			MongoService mongo = new MongoManager();
//			DBObject where = new BasicDBObject();
//			
//			mongo.getCount(where, MongoTableName.CB_MERCHANT_DETAIL);
			
			/* 设置方付通账户单独缓存 */
//			MerchantAccountRedis.set_cbbank_fft_account_client_id(merchantAccount);
			
				sqlSession.commit(true);
				// 添加商户日志落地
				if("0".equals(merchantAccount.getOutletId())){
					new DataPlatLogSupport().addMerchantLog(clientId,merchantId);
				}
				
			} else {
				sqlSession.rollback(true); 
			}
			result = merchantAccount.getId();
		} catch (FroadServerException e) { 
			result = null;
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			result = null;
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 删除 MerchantAccount
     * @param merchantAccount
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteMerchantAccount(MerchantAccount merchantAccount) throws FroadServerException,Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantAccountMapper merchantAccountMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantAccountMapper = sqlSession.getMapper(MerchantAccountMapper.class);

			result = merchantAccountMapper.deleteMerchantAccount(merchantAccount); 

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			if(result){
				merchantAccount = merchantAccountMapper.findMerchantAccountById(merchantAccount.getId());
				if(null == merchantAccount){
					throw new FroadServerException("删除MerchantAccount缓存数据失败!查询不到具体实体对象!");
				}
				/* 删除缓存 */
				result = MerchantAccountRedis.del_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(merchantAccount);
				if(!result){
					throw new FroadServerException("删除MerchantAccount缓存数据失败!");
				}
			
				
				String clientId = merchantAccount.getClientId();
				String merchantId = merchantAccount.getMerchantId();
				String outletId = merchantAccount.getOutletId();	
				Map<String, String> merchantMap = commonLogic.getMerchantRedis(clientId, merchantId); // 获取商户信息
				if(null != merchantMap && !merchantMap.isEmpty()) {
//					throw new FroadServerException("商户信息不存在!");
					String merchantName = merchantMap.get("merchant_name");
					
					if(StringUtils.isNotBlank(outletId) && !outletId.equals("0")) { // 删除的是门店账号  
						synMerchantBankWhite(clientId,merchantId,merchantAccount);
					}else{
						/* 删除银行账号白名单 */		
						syncDeleteMerchantBankWhite(merchantId,merchantName,merchantAccount);
					}
					
					
				}
			/* 方付通商户的账户 */
//			MongoService mongo = new MongoManager();
//			DBObject where = new BasicDBObject();
//			
//			mongo.getCount(where, MongoTableName.CB_MERCHANT_DETAIL);
			/* 删除方付通账户单独缓存 */
//			MerchantAccountRedis.del_cbbank_fft_account_client_id(merchantAccount);
			
//			result = true; 
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			result = false;
			if(null != sqlSession)  
				sqlSession.rollback(true);  
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
     * 物理删除 MerchantAccount
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean removeMerchantAccount(Long id) throws FroadServerException,Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantAccountMapper merchantAccountMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantAccountMapper = sqlSession.getMapper(MerchantAccountMapper.class);

			MerchantAccount merchantAccount = merchantAccountMapper.findMerchantAccountById(id);
			
			result = merchantAccountMapper.removeMerchantAccount(id); 

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			if(result){
				if(null == merchantAccount){
					throw new FroadServerException("物理删除MerchantAccount缓存数据失败!查询不到具体实体对象!");
				}
				/* 删除缓存 */
				result = MerchantAccountRedis.del_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(merchantAccount);
				if(!result){
					throw new FroadServerException("物理删除MerchantAccount缓存数据失败!");
				}
			
				
				String clientId = merchantAccount.getClientId();
				String merchantId = merchantAccount.getMerchantId();
				String outletId = merchantAccount.getOutletId();				
				Map<String, String> merchantMap = commonLogic.getMerchantRedis(clientId, merchantId); // 获取商户信息
				if(null != merchantMap && !merchantMap.isEmpty()) {
//					throw new FroadServerException("商户信息不存在!");
					String merchantName = merchantMap.get("merchant_name");
					if(StringUtils.isNotBlank(outletId) && !outletId.equals("0")) { // 删除的是门店账号  
						synMerchantBankWhite(clientId,merchantId,merchantAccount);
					}else{
						/* 删除银行账号白名单 */		
						syncDeleteMerchantBankWhite(merchantId,merchantName,merchantAccount);
					}
				}
				
				
			/* 方付通商户的账户 */
//			MongoService mongo = new MongoManager();
//			DBObject where = new BasicDBObject();
//			
//			mongo.getCount(where, MongoTableName.CB_MERCHANT_DETAIL);
			/* 删除方付通账户单独缓存 */
//			MerchantAccountRedis.del_cbbank_fft_account_client_id(merchantAccount);
			
//			result = true; 
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = false; 
//			LogCvt.error("物理删除MerchantAccount失败，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = false; 
//			LogCvt.error("物理删除MerchantAccount失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 MerchantAccount
     * @param merchantAccount
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateMerchantAccount(MerchantAccount merchantAccount)throws FroadServerException,Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantAccountMapper merchantAccountMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantAccountMapper = sqlSession.getMapper(MerchantAccountMapper.class);
			
			if(StringUtils.isBlank(merchantAccount.getSynchState())){
				merchantAccount.setSynchState(AccountSynchState.init.getCode());
			}
			if(StringUtils.isBlank(merchantAccount.getAuditState())){
				merchantAccount.setAuditState(AccountAuditState.init.getCode());
			}
			
			//计算mac值
			if(Checker.isNotEmpty(merchantAccount.getClientId())
					&& Checker.isNotEmpty(merchantAccount.getAcctNumber()) 
					&& Checker.isNotEmpty(merchantAccount.getAcctName())){
				
				String acctMac = this.findMerchantAccountMac(merchantAccount.getClientId(), merchantAccount.getAcctNumber(), merchantAccount.getAcctName());
				merchantAccount.setMac(acctMac);
			}
			
			//修改mysql
			result = merchantAccountMapper.updateMerchantAccount(merchantAccount); 
			
			/**********************操作Redis缓存**********************/
			/* 更新缓存 */
			result = MerchantAccountRedis.set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(merchantAccount);
			if(!result){
				throw new FroadServerException("修改MerchantAccount缓存数据失败!");
			}
			
			if(result){
				sqlSession.commit(true);
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
     * 支付多银行客户端和boss查询
     * 查询 MerchantAccount
     * @param merchantAccount
     * @return List<MerchantAccount>    结果集合 
     */
	@Override
	public List<MerchantAccount> findMerchantAccount(MerchantAccount merchantAccount) {

		List<MerchantAccount> result = new ArrayList<MerchantAccount>(); 
		SqlSession sqlSession = null;
		MerchantAccountMapper merchantAccountMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantAccountMapper = sqlSession.getMapper(MerchantAccountMapper.class);

			result = merchantAccountMapper.findMerchantAccount(merchantAccount); 
		} catch (Exception e) { 
			LogCvt.error("查询MerchantAccount失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 支付多银行客户端和boss查询
     * 分页查询 MerchantAccount
     * @param page
     * @param merchantAccount
     * @return Page<MerchantAccount>    结果集合 
     */
	@Override
	public Page<MerchantAccount> findMerchantAccountByPage(Page<MerchantAccount> page, MerchantAccount merchantAccount) {

		List<MerchantAccount> result = new ArrayList<MerchantAccount>(); 
		SqlSession sqlSession = null;
		MerchantAccountMapper merchantAccountMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantAccountMapper = sqlSession.getMapper(MerchantAccountMapper.class);

			result = merchantAccountMapper.findByPage(page, merchantAccount); 
			page.setResultsContent(result);
		} catch (Exception e) { 
			LogCvt.error("分页查询MerchantAccount失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


	
	/**     
	 * @param id
	 * @return    
	 * @see com.froad.logic.MerchantAccountLogic#findMerchantAccountById(java.lang.Long)    
	 */
	
	@Override
	public MerchantAccount findMerchantAccountById(Long id) {
		MerchantAccount result = null; 
		SqlSession sqlSession = null;
		MerchantAccountMapper merchantAccountMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantAccountMapper = sqlSession.getMapper(MerchantAccountMapper.class);

			result = merchantAccountMapper.findMerchantAccountById(id); 
		} catch (Exception e) { 
			LogCvt.error("查询MerchantAccount失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}


	
	/**     
	 * @param outletId
	 * @return    
	 * @see com.froad.logic.MerchantAccountLogic#findMerchantAccountByOutletId(java.lang.String)    
	 */
	
	@Override
	public MerchantAccount findMerchantAccountByOutletId(String outletId) {
		MerchantAccount result = null; 
		SqlSession sqlSession = null;
		MerchantAccountMapper merchantAccountMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantAccountMapper = sqlSession.getMapper(MerchantAccountMapper.class);

			result = merchantAccountMapper.findMerchantAccountByOutletId(outletId); 
		} catch (Exception e) { 
			LogCvt.error("查询MerchantAccount失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}

	/**
	 * 获取商户账号信息
	 * @Title: findMerchantAccountByMerchantId 
	 * @author ll
	 * @version 1.5
	 * @see: TODO
	 * @param clientId
	 * @param merchantId 商户Id
	 * @param outletId 门店Id
	 * @return MerchantAccount    返回类型 
	 * @throws
	 */
	@Override
	public MerchantAccount findMerchantAccountByMerchantId(String clientId,String merchantId,String outletId) {
		MerchantAccount account = null; 
		try { 
			Map<String,String> accountRedis = MerchantAccountRedis.getAll_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(clientId, merchantId, "0");
			if(Checker.isNotEmpty(accountRedis)){///操作Redis缓存
				LogCvt.debug("查询缓存cbbank:merchant_outlet_account:client_id:merchant_id:outlet_id有值"+accountRedis.values());
				account = new MerchantAccount();
				account.setClientId(clientId);
				account.setMerchantId(merchantId);
				account.setOutletId(outletId);
				//acct_name/acct_number/acct_type/opening_bank
				account.setAcctName(accountRedis.get("acct_name"));
				account.setAcctNumber(accountRedis.get("acct_number"));
				account.setAcctType(accountRedis.get("acct_type"));
				account.setOpeningBank(ObjectUtils.toString(accountRedis.get("opening_bank"),""));
				
			}else{
				//重新塞redis
				MerchantAccount filterMerchantAccount = new MerchantAccount();
				filterMerchantAccount.setClientId(clientId);
				filterMerchantAccount.setMerchantId(merchantId);
				filterMerchantAccount.setOutletId(outletId);
				filterMerchantAccount.setIsDelete(false);
				filterMerchantAccount.setAcctType(AccountTypeEnum.SQ.getCode()); // 1-收款  2-付款
				
				List<MerchantAccount> merchantAccountList = this.findMerchantAccount(filterMerchantAccount);
				if(Checker.isNotEmpty(merchantAccountList)){
					MerchantAccountRedis.set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(merchantAccountList.get(0));
					account = merchantAccountList.get(0);
				}else{
					LogCvt.error("商户账号信息不存在或已经被删除，商户编号：" + merchantId);
				}
			}
			
		}catch (Exception e) { 
			LogCvt.error("查询MerchantAccount失败，原因:" + e.getMessage(), e); 
		} 
		return account; 
	}
	
	
	/**     
	 * 注册银行白名单
	 * @param account 账户对象  要求提供：client_id,merchant_id/outlet_id,acct_number,acct_name
	 * @param optionType 行方操作类型:0-新增,1-修改,2-删除
	 * @param moduleType 模块类型:0-商户,1-门店
	 * @return Result
	 */	
	public Result registBankWhiteList(MerchantAccount account, String optionType, String moduleType) throws FroadServerException,Exception{

		Result result = null;
		String registId = "";	
		String registName = "";
		String clientId = account.getClientId();
		MerchantAccount maccount = new MerchantAccount();
		maccount.setClientId(clientId);
		maccount.setIsDelete(false);
		maccount.setAcctNumber(account.getAcctNumber());
		maccount.setAcctName(account.getAcctName());
		maccount.setOpeningBank(account.getOpeningBank());
		maccount.setAcctType(account.getAcctType());
		//商户白名单 
		if(moduleType.equals(Constants.INPUT_AUDIT_MERCHANT_TYPE)){
			registId = account.getMerchantId()==null?"":account.getMerchantId();
			Map<String, String> merchantMap = commonLogic.getMerchantRedis(clientId, registId); // 获取商户信息
			if (null == merchantMap || merchantMap.isEmpty()) {
				throw new FroadServerException("商户信息不存在!");
			}
			if (StringUtils.equals(merchantMap.get("disable_status"),
					MerchantDisableStatusEnum.unregistered.getCode())) {
				throw new FroadServerException("商户已经解约!");
			}
			registName = merchantMap.get("merchant_name") == null ? "": merchantMap.get("merchant_name");
			maccount.setMerchantId(registId);
			maccount.setOutletId(account.getOutletId());
			//门店白名单
		}else if(moduleType.equals(Constants.INPUT_AUDIT_OUTLET_TYPE)){//同步白名单门店类型
			registId = account.getOutletId()==null?"":account.getOutletId();
			String merchantId = account.getMerchantId() == null?"":account.getMerchantId();
			Map<String, String> outletMap = getOutletRedis(clientId, merchantId,registId); // 获取商户信息
			if (null == outletMap || outletMap.isEmpty()) {
				throw new FroadServerException("门店信息不存在!");
			}
			if (StringUtils.equals(outletMap.get("disable_status"),
					MerchantDisableStatusEnum.unregistered.getCode())) {
				throw new FroadServerException("门店已经解约!");
			}
			registName = outletMap.get("outlet_name") == null ? "": outletMap.get("outlet_name");
			maccount.setMerchantId(account.getMerchantId());
			maccount.setOutletId(registId);
		}else{
			throw new FroadServerException("非法参数,sourceType="+moduleType);
		}	
		MerchantBankWhiteVo merchantBankWhiteVo = new MerchantBankWhiteVo();
		merchantBankWhiteVo.setMerchantIdOrOutletId(registId);
		merchantBankWhiteVo.setMerchantNameOrOutletName(registName);
		merchantBankWhiteVo.setAccountNo(account.getAcctNumber());
		merchantBankWhiteVo.setClientId(clientId);
		merchantBankWhiteVo.setAccountName(account.getAcctName());
		merchantBankWhiteVo.setOptionType(optionType);
		merchantBankWhiteVo.setMac(findMerchantAccountMac(account.getClientId(),account.getAcctNumber(),account.getAcctName()));
		
		result = (Result) support.setMerchantBankWhiteList(merchantBankWhiteVo);
		
		//向银行注册白名单成功
		if(result.getResultCode().equals(ResultCode.success.getCode())){
			// 不走行内审核,当同步成功时，需要将审核状态改为审核通过
			if (!ClientUtil.getClientId(clientId)) {
				maccount.setSynchState(AccountSynchState.synchSucc.getCode());
				maccount.setAuditState(AccountAuditState.passAudit.getCode());
			} else {
				maccount.setSynchState(AccountSynchState.synchSucc.getCode());
				maccount.setAuditState(AccountAuditState.ingAudit.getCode());
			}
		}else{
			maccount.setAuditState(AccountAuditState.init.getCode());
			maccount.setSynchState(AccountSynchState.synchFail.getCode());
		}
		
		//更新账户同步状态及审核状态
		this.updateMerchantAccount(maccount);

		return result;
	}
	
	
	/**
	 * 获取门店信息内部方法.
	 */
	private Map<String, String> getOutletRedis(String clientId,String merchantId, String outletId) {
		Map<String, String> outletMap = OutletRedis.get_cbbank_outlet_client_id_merchant_id_outlet_id(clientId,
						merchantId, outletId);
		// 如果redis为空，从mysql取
		if (Checker.isEmpty(outletMap) || !outletMap.containsKey("outlet_name")	|| !outletMap.containsKey("is_enable") || !outletMap.containsKey("disable_status")) {
			LogCvt.info("redis获取门店信息查询为空，从mysql查询门店信息，查询条件：outletId:"	+ outletId);
			Outlet outlet = outletLogic.findOutletByOutletId(outletId);
			if (Checker.isNotEmpty(outlet)) {
				try {
					LogCvt.info("redis获取门店信息查询为空，重新设置缓存");
					OutletRedis.set_cbbank_outlet_client_id_merchant_id_outlet_id(outlet);
				} catch (Exception e) {
					LogCvt.error("Common设置门店缓存失败, 原因:" + e.getMessage(), e);
				}
				outletMap = OutletRedis.get_cbbank_outlet_client_id_merchant_id_outlet_id(clientId, merchantId, outletId);
			}
		}
		return outletMap;
	}	
	
	
	/**
	 * 白名单账户mac值计算
	 * @param clientId 客户编号
	 * @param acctNo 账号
	 * @param acctName 户名
	 * @return 返回mac
	 */
	public String findMerchantAccountMac(String clientId,String acctNo,String acctName){
		//生成mac.
		 StringBuffer macSb = new StringBuffer();
		 macSb.append(acctNo);
		 macSb.append(acctName);
		 macSb.append(Md5Utils.getMd5KeyValue(clientId));
		 //2:获取密钥
		 String macValue = Md5Utils.getMD5ofStr(macSb.toString());			
		
		 //3:截取密文约定取最后8字节（我们给银行的接口文件是这样约定的）
		 macValue = StringUtils.substring(macValue, macValue.length()-8,macValue.length());
		 LogCvt.info("账号:"+acctNo+",户名:"+acctName+"生成MAC为:"+macValue);
		 
		 return macValue;
	}
	
	/**
	 * 内部处理方法,检查是否存在商户账户信息.
	 * @param clientId
	 * @param merchantId
	 * @param outletId
	 * @return
	 */
	private boolean isNotExistMerchantAccount(MerchantAccount merchantAccount){
		boolean isNotExist = true;		
		MerchantAccount findMerchantAccount = new MerchantAccount();
		findMerchantAccount.setClientId(merchantAccount.getClientId());
		findMerchantAccount.setMerchantId(merchantAccount.getMerchantId());
		findMerchantAccount.setIsDelete(merchantAccount.getIsDelete());
		findMerchantAccount.setAcctName(merchantAccount.getAcctName());
		findMerchantAccount.setAcctNumber(merchantAccount.getAcctNumber());
		findMerchantAccount.setAcctType(AccountTypeEnum.SQ.getCode());
		
		List<MerchantAccount> merchantAccountList = (List<MerchantAccount>) findMerchantAccount(findMerchantAccount);
		if(null != merchantAccountList && merchantAccountList.size()>0){
			isNotExist = false;
		}
		return isNotExist;
	}
	
	
	/**
	 * 删除同步白名单内部操作方法.
	 * 
	 * @param merchantIdOrOutletId
	 * @param merchantNameOrOutletName
	 * @param merchantAccount
	 * @throws Exception
	 */
	private void syncDeleteMerchantBankWhite(String merchantIdOrOutletId,String merchantNameOrOutletName,MerchantAccount merchantAccount)throws Exception{
		MerchantBankWhiteVo merchantBankWhiteVo = new MerchantBankWhiteVo();
		merchantBankWhiteVo.setMerchantIdOrOutletId(merchantIdOrOutletId);
		merchantBankWhiteVo.setMerchantNameOrOutletName(merchantNameOrOutletName);
		merchantBankWhiteVo.setAccountNo(merchantAccount.getAcctNumber());
		merchantBankWhiteVo.setClientId(merchantAccount.getClientId());
		merchantBankWhiteVo.setAccountName(merchantAccount.getAcctName());
		merchantBankWhiteVo.setMac(merchantAccount.getMac());
		support.deleteMerchantBankWhiteList(merchantBankWhiteVo);
	}
	
	/**
	 * 删除的是门店账号同步白名单内部方法.
	 * 
	 * @param clientId
	 * @param merchantId
	 * @param merchantAccount
	 * @throws Exception
	 */
	private void synMerchantBankWhite(String clientId,String merchantId,MerchantAccount merchantAccount)throws Exception{
		Map<String, String> outletMap = getOutletRedis(clientId, merchantId,merchantAccount.getOutletId()); // 获取商户信息
		if (null == outletMap || outletMap.isEmpty()) {
			throw new FroadServerException("门店信息不存在!");
		}
		if (StringUtils.equals(outletMap.get("disable_status"),
				MerchantDisableStatusEnum.unregistered.getCode())) {
			throw new FroadServerException("门店已经解约!");
		}
	    String outletName = outletMap.get("outlet_name") == null ? "": outletMap.get("outlet_name");
	    MerchantAccount findMerchantAccount = new MerchantAccount();
	    findMerchantAccount.setClientId(merchantAccount.getClientId());
	    findMerchantAccount.setMerchantId(merchantAccount.getMerchantId());
	    findMerchantAccount.setOutletId(merchantAccount.getOutletId());
	    findMerchantAccount.setAcctName(merchantAccount.getAcctName());
	    findMerchantAccount.setAcctNumber(merchantAccount.getAcctNumber());
	    findMerchantAccount.setIsDelete(false);
		if(isNotExistMerchantAccount(findMerchantAccount)){
			/* 删除银行账号白名单 */		
			syncDeleteMerchantBankWhite(merchantAccount.getOutletId(),outletName,merchantAccount);
		}
	}
}