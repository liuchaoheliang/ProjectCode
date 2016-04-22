package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.Constants;
import com.froad.db.mongo.MerchantDetailMongo;
import com.froad.db.mongo.OutletDetailMongo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.db.mysql.mapper.MerchantTempMapper;
import com.froad.db.redis.MerchantCategoryRedis;
import com.froad.db.redis.MerchantRedis;
import com.froad.enums.AuditType;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantAccountLogic;
import com.froad.logic.MerchantAuditLogic;
import com.froad.logic.MerchantUserLogic;
import com.froad.po.AuditProcess;
import com.froad.po.AuditTask;
import com.froad.po.BankOperator;
import com.froad.po.ClientMerchantAudit;
import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.MerchantTemp;
import com.froad.po.MerchantUser;
import com.froad.po.Org;
import com.froad.po.Result;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.TypeInfo;
import com.froad.support.Support;
import com.froad.thrift.vo.OriginVo;
import com.froad.util.Checker;
import com.froad.util.ClientUtil;
import com.froad.util.SerialNumberUtil;

public class MerchantAuditLogicImpl implements MerchantAuditLogic {

	/**
	 * 添加临时表信息及修改商户表信息为待审核状态
	 */
	@Override
	public boolean auditMerchant(OriginVo originVo, MerchantTemp mTemp) throws Exception {
		
		SqlSession sqlSession = null;
		MerchantTempMapper mTempMapper = null;
		CommonLogic comLogic =  new CommonLogicImpl();
		
		boolean result = false;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			mTempMapper = sqlSession.getMapper(MerchantTempMapper.class);
			
			mTemp.setAuditId(mTemp.getAuditId());
			mTemp.setCreateTime(new Date());
			
			if(StringUtils.isNotBlank(mTemp.getOrgCode())){
				Org org = comLogic.getOrgByOrgCode(mTemp.getOrgCode(), mTemp.getClientId());
				Org orgTmp = null;
				if(org == null) {
					throw new FroadServerException("机构码:" + mTemp.getOrgCode() + "不存在或已经删除!");
				}
				if (org.getIsEnable() != null && !org.getIsEnable()) { // 机构不可用
					throw new FroadServerException("机构码:" + mTemp.getOrgCode() + "不可用!");
				}
				mTemp.setOrgName(org.getOrgName());
				
				//当市级机构非空时设置
				if(org.getCityAgency()!=null && !StringUtils.isBlank(org.getCityAgency())){
					orgTmp = comLogic.getOrgByOrgCode(org.getCityAgency(), mTemp.getClientId());
					if(org!=null){
						mTemp.setCityOrgCode(orgTmp.getOrgCode());
						mTemp.setCityOrgName(orgTmp.getOrgName());
					}
				}else{
					mTemp.setCityOrgCode(org.getOrgCode());
					mTemp.setCityOrgName(org.getOrgName());
				}
				
				//区级机构非空设置值
				if(org.getCountyAgency()!=null && !StringUtils.isBlank(org.getCountyAgency())){
					orgTmp = comLogic.getOrgByOrgCode(org.getCountyAgency(), mTemp.getClientId());
					if(org!=null){
						mTemp.setCountyOrgCode(orgTmp.getOrgCode());
						mTemp.setCountyOrgName(orgTmp.getOrgName());
					}
				}else{
					mTemp.setCountyOrgCode(org.getOrgCode());
					mTemp.setCountyOrgName(org.getOrgName());
				}
			}
			//添加临时表数据
			mTempMapper.auditMerchant(mTemp);
			
			result = true;
			sqlSession.commit(true);
			
		} catch (Exception e) {
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false;
			LogCvt.error("添加商户审核通过在审核失败，原因:" + e.getMessage(), e);
			throw e;
		}finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return result;
	}
	
	public AuditTask getAuditTask(OriginVo originVo,MerchantTemp mTemp) throws Exception{
		AuditTask auditTask = new AuditTask();
		CommonLogic comLogic =  new CommonLogicImpl();
		try {
			auditTask.setCreateTime(new Date());
			auditTask.setClientId(mTemp.getClientId());
			auditTask.setThridId(mTemp.getMerchantId());
			
			auditTask.setAuditId(SerialNumberUtil.getAuditIdSerialNumber());
			
			BankOperator bank = comLogic.getBankOperatorById( mTemp.getClientId(),originVo.getOperatorId());
			auditTask.setOrgCode(bank.getOrgCode());
			auditTask.setUserName(bank.getUsername());
//			LogCvt.debug(mTemp.getClientId()+"========"+bank.getOrgCode());
			Org org = comLogic.queryByOrgCode(mTemp.getClientId(), bank.getOrgCode());
			auditTask.setOrgName(org.getOrgName());
			auditTask.setType(AuditType.merchant.getType());
			
			Merchant m = new MerchantLogicImpl().findMerchantByMerchantId(mTemp.getMerchantId());
			auditTask.setName(m.getMerchantFullname());
			auditTask.setBusCode(m.getLicense());
			
			ClientMerchantAudit clientMerchantAudit = new Support().getClientMerchantAudit(mTemp.getClientId(), auditTask.getOrgCode()); // 查询初始审核机构和最终审核机构
			if(null != clientMerchantAudit) {
				
				auditTask.setAuditStartOrgCode(clientMerchantAudit.getStartAuditOrgCode()); // 设置初始审核机构
				auditTask.setAuditEndOrgCode(clientMerchantAudit.getEndAuditOrgCode()); // 设置最终审核机构
				if(StringUtils.isBlank(clientMerchantAudit.getStartAuditOrgCode()))
					auditTask.setAuditOrgCode(clientMerchantAudit.getEndAuditOrgCode()); // 设置当前审核机构 为 初始审核机构
				else{
					auditTask.setAuditOrgCode(clientMerchantAudit.getStartAuditOrgCode()); 
				}
			}else{
				throw new FroadServerException("获取初始审核机构和最终审核机构失败!");
			}
			auditTask.setAuditState(ProductAuditState.noAudit.getCode());
			auditTask.setState("0");
			auditTask.setAuditTime(null);
		} catch (Exception e) {
			throw e;
		}

		return auditTask;
	}
	
	public AuditProcess getAuditProcess(OriginVo originVo, MerchantTemp mTemp,String auditId,String orgCode) throws Exception{
		AuditProcess aProcess = new AuditProcess();
		try {
			aProcess.setCreateTime(new Date());
			aProcess.setClientId(mTemp.getClientId());
			aProcess.setAuditId(auditId);
			aProcess.setTaskId(SerialNumberUtil.getTaskIdSerialNumber());
			aProcess.setOrgCode(orgCode);
			Org org = new CommonLogicImpl().queryByOrgCode(mTemp.getClientId(), orgCode);
			if(org != null)
				aProcess.setOrgName(org.getOrgName());	
			aProcess.setAuditState(ProductAuditState.noAudit.getCode());
		} catch (Exception e) {
			throw e;
		}
		return aProcess;
	}

	/**
	 * 将商户临时表同步到主表
	 */
	@Override
	public Result auditMerchantEdit(String auditId) throws Exception {
		
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		MerchantTempMapper merchantTempMapper = null;
		MerchantDetailMongo merchantDetailMongo = new MerchantDetailMongo();
		OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
		Result result =  new Result();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			merchantTempMapper = sqlSession.getMapper(MerchantTempMapper.class);
			
			MerchantTemp mtemp = merchantTempMapper.findMerchantTempByAuditId(auditId);
			if(Checker.isEmpty(mtemp)){
				throw new Exception("商户临时表未找到符合的记录["+auditId+"]");
			}
			//merchantTemp转换为Merchant对象
			Merchant m = this.convertMerchant(mtemp);

			
			boolean b = true;
			if(Checker.isNotEmpty(m)){
				//修改商户表mysql数据
				b = merchantMapper.updateAuditMerchant(m);
				
				//修改内部bug商户编辑审核，修改商户名称遗漏逻辑：修改对应的商品的商户名称.
				if(StringUtils.isNotBlank(m.getMerchantName())){
					 new CommonLogicImpl().updateProduct_MerchantNameByMerchantId(m.getMerchantId(), m.getMerchantName());
				}
			}
			
			String merchantId = mtemp.getMerchantId();
			String clientId = mtemp.getClientId();
			
			if(b){				
				MerchantDetail detail = getMerchantDetail(mtemp);
				if(Checker.isNotEmpty(detail)){
					Merchant newsMerchant = merchantMapper.findMerchantByMerchantId(merchantId);
					List<CategoryInfo> categoryInfoOldList = new MerchantLogicImpl().findMerchantCategoryInfo(merchantId); // 原来的商户分类
					
					LogCvt.info("修改商户详细信息");
					merchantDetailMongo.updateMerchantDetailById(newsMerchant, detail.getCategoryInfo(), detail.getTypeInfo());
					LogCvt.info("修改商户下面的所有门店的分类信息");
					outletDetailMongo.updateOutletDetailCategoryInfo(merchantId, detail.getCategoryInfo()); // 修改门店表的分类信息
					
					LogCvt.info("修改门店信息中的商户名称");
					outletDetailMongo.updateOutletDetailMerchantNameInfo(merchantId,detail.getMerchantName());
					
					LogCvt.info("修改商户下面的所有门店的类型信息");
					outletDetailMongo.updateOutletDetailTypeInfo(merchantId, detail.getTypeInfo()); // 修改门店表的类型信息
					
					
					
					LogCvt.info("商户缓存删除对应的商户缓存");
					MerchantRedis.del_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
					
					/* 更新全部商户缓存 */
					LogCvt.info("修改商户信息缓存");
					newsMerchant.setCategoryInfoObjList(detail.getCategoryInfo());
					newsMerchant.setTypeInfoObjList(detail.getTypeInfo());
					MerchantRedis.set_cbbank_merchant_client_id_merchant_id(newsMerchant);
					
					
					/* 缓存商户分类下的商户id */
					if(CollectionUtils.isNotEmpty(detail.getCategoryInfo())) {
						categoryInfoOldList = new MerchantLogicImpl().findMerchantCategoryInfo(newsMerchant.getMerchantId()); // 原来的商户分类
						if(CollectionUtils.isNotEmpty(categoryInfoOldList)) {
							for (CategoryInfo categoryInfoOld : categoryInfoOldList) {
								LogCvt.info("删除原商户分类id:" + categoryInfoOld.getCategoryId() + "下的商户id");
								MerchantCategoryRedis.del_cbbank_merchant_category_all_client_id_merchant_category_id(newsMerchant.getClientId(), categoryInfoOld.getCategoryId(), newsMerchant.getMerchantId());
							}
						}
						for (CategoryInfo categoryInfo : detail.getCategoryInfo()) {
							LogCvt.info("缓存商户分类id:" + categoryInfo.getCategoryId() + "下的商户id");
							MerchantCategoryRedis.set_cbbank_merchant_category_all_client_id_merchant_category_id(newsMerchant.getClientId(), categoryInfo.getCategoryId(), newsMerchant.getMerchantId());
						}
					}
				}

				//账号、户名
				if(Checker.isNotEmpty(mtemp.getAcountNo()) 
						|| Checker.isNotEmpty(mtemp.getAccountName())){
					LogCvt.debug("帐号/户名有变更……");
					
					MerchantAccountLogic accountLogic = new MerchantAccountLogicImpl(); 
					
					MerchantAccount account = accountLogic.findMerchantAccountByMerchantId(clientId, merchantId, "0");
					if(Checker.isNotEmpty(account)){
						String beforAcctName = account.getAcctName();
						String beforAcctNo = account.getAcctNumber();
						//修改商户帐号信息(mtemp表只记录变更过的值)
						account.setAcctName(Checker.isNotEmpty(mtemp.getAccountName())?mtemp.getAccountName():beforAcctName);
						account.setAcctNumber(Checker.isNotEmpty(mtemp.getAcountNo())?mtemp.getAcountNo():beforAcctNo);
						
						//注册银行白名单，并根据注册结果更新账户信息表的同步状态及审核状态！
						boolean isSync = false;
						result = accountLogic.registBankWhiteList(account, "0", Constants.INPUT_AUDIT_MERCHANT_TYPE);
						if(result.getResultCode().equals(ResultCode.success.getCode())){
							isSync = true;
						}
						
						Merchant whiteMerchant = new Merchant();
						whiteMerchant.setMerchantId(merchantId);
						//走行内审核
						if(ClientUtil.getClientId(clientId)){
							whiteMerchant.setEditAuditState(ProductAuditState.waitSynchAudit.getCode());
						}else{
							//同步成功
							if(isSync){
								whiteMerchant.setEditAuditState(ProductAuditState.passAudit.getCode());
							}else{
								//同步失败，恢复默认值
								whiteMerchant.setEditAuditState(ProductAuditState.noAudit.getCode());
							}
						}
						merchantMapper.updateMerchant(whiteMerchant);

					}
				}
				
		
				//登录人、登录人手机
				if(StringUtils.isNotBlank(mtemp.getLoginMobile()) 
						|| StringUtils.isNotBlank(mtemp.getLoginName())){
					
					MerchantUserLogic userLogic = new MerchantUserLogicImpl();
					MerchantUser user = new MerchantUser();
				
					user.setClientId(clientId);
					user.setMerchantId(merchantId);
					user.setMerchantRoleId(100000000l);
					
					List<MerchantUser> userList = userLogic.findMerchantUser(user);
					if(Checker.isNotEmpty(userList) && userList.size()==1){
						user = userList.get(0);
						
						user.setPhone(mtemp.getLoginMobile());
						user.setUsername(mtemp.getLoginName());
						
						userLogic.updateMerchantUser(user);
					}
				}
				
			}
			sqlSession.commit(true);
			result.setResultCode(ResultCode.success.getCode());
			
		} catch (Exception e) {
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			LogCvt.error("编辑审核通过在同步主表操作时失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			throw e;
		}finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return result;
	}
	/**
	 * merchantTemp转换为Merchant对象
	 * @param merchantTemp
	 * @return Merchant
	 */
	private Merchant convertMerchant(MerchantTemp merchantTemp){
		
		if(Checker.isEmpty(merchantTemp)
				|| 
				(Checker.isEmpty(merchantTemp.getContactName()) 
						&& Checker.isEmpty(merchantTemp.getLegalName()) 
						&& Checker.isEmpty(merchantTemp.getLegalCredentType())  
						&& Checker.isEmpty(merchantTemp.getLegalCredentNo())  
						&& Checker.isEmpty(merchantTemp.getOrgCode())  
						&& Checker.isEmpty(merchantTemp.getCityOrgCode())  
						&& Checker.isEmpty(merchantTemp.getCountyOrgCode())
						// 20151020 ll add  商户名称，联系人电话，联系人手机，营业执照号，签约人
						&& Checker.isEmpty(merchantTemp.getMerchantName())
						&& Checker.isEmpty(merchantTemp.getPhone())
						&& Checker.isEmpty(merchantTemp.getContactPhone())
						&& Checker.isEmpty(merchantTemp.getLicense())
						&& Checker.isEmpty(merchantTemp.getContractStaff())
						//20151126 trimray add 是否外包，外包公司id
						&& Checker.isEmpty(merchantTemp.getIsOutsource())
						&& Checker.isEmpty(merchantTemp.getCompanyId())
						
						)){
			LogCvt.info("此次编辑未修改商户表中的mysql内容");
			return null;
		}
		
		Merchant m = new Merchant();
		m.setMerchantId(merchantTemp.getMerchantId());
		m.setClientId(merchantTemp.getClientId());
		
		m.setContactName(merchantTemp.getContactName());
		m.setLegalName(merchantTemp.getLegalName());
		if(StringUtils.isNotBlank(merchantTemp.getLegalCredentType()))
			m.setLegalCredentType(Integer.parseInt(merchantTemp.getLegalCredentType()));
		m.setLegalCredentNo(merchantTemp.getLegalCredentNo());
		m.setOrgCode(merchantTemp.getOrgCode());
		m.setCityOrgCode(merchantTemp.getCityOrgCode());
		m.setCountyOrgCode(merchantTemp.getCountyOrgCode());
		m.setMerchantName(merchantTemp.getMerchantName());
		m.setMerchantFullname(merchantTemp.getMerchantName());
		m.setPhone(merchantTemp.getPhone());
		m.setContactPhone(merchantTemp.getContactPhone());
		m.setLicense(merchantTemp.getLicense());
		m.setContractStaff(merchantTemp.getContractStaff());
		//20151126 trimray add 是否外包，外包公司id
		m.setIsOutsource(merchantTemp.getIsOutsource());
		m.setCompanyId(merchantTemp.getCompanyId());

		return m;
	}
	
	/**
	 * 获取商户详情mongo
	 * @param merchantTemp
	 * @return
	 */
	private MerchantDetail getMerchantDetail(MerchantTemp merchantTemp){
		
		boolean flag = true;
		MerchantDetail detail = new MerchantDetail();
		
		detail.setId(merchantTemp.getMerchantId());
		//商户分类
		if(merchantTemp.getMerchantCategoryId() != null && merchantTemp.getMerchantCategoryId() > 0 
				&& StringUtils.isNotBlank(merchantTemp.getMerchantCategoryName())){
			CategoryInfo info = new CategoryInfo();
			List<CategoryInfo> list = new ArrayList<CategoryInfo>();
			info.setCategoryId(merchantTemp.getMerchantCategoryId());
			info.setName(merchantTemp.getMerchantCategoryName());
			list.add(info);
			detail.setCategoryInfo(list);
			flag = true;
		}
		
		//商户类型
		if(StringUtils.isNotBlank(merchantTemp.getMerchantTypeId()) 
				&& StringUtils.isNotBlank(merchantTemp.getMerchantTypeName()) 
				&& StringUtils.isNotBlank(merchantTemp.getMerchantTypeValue())){
			
			String[] merchantTypeId = merchantTemp.getMerchantTypeId().split(",");
			String[] merchantName = merchantTemp.getMerchantTypeName().split(",");
			String[] type = merchantTemp.getMerchantTypeValue().split(",");
			TypeInfo info = null;
			List<TypeInfo> list = new ArrayList<TypeInfo>();
			for (int i = 0; i < merchantTypeId.length; i++) {
				info = new TypeInfo();
				info.setMerchantTypeId(Long.parseLong(merchantTypeId[i]));
				info.setTypeName(merchantName[i]);
				info.setType(type[i]);
				list.add(info);
			}
			detail.setTypeInfo(list);
			flag = true;
		}
		
		//有修改的对象
		if(flag){
			return detail;
		}else{
			return null;
		}
	}

	/**
	 * 根据审核流水号查询商户临时表数据
	 */
	public MerchantTemp findAuditMerchantByAuditId(String auditId) throws Exception {
		SqlSession sqlSession = null;
		MerchantTempMapper mTempMapper = null;
		MerchantTemp merTemp = new MerchantTemp();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			mTempMapper = sqlSession.getMapper(MerchantTempMapper.class);
			
			merTemp = mTempMapper.findMerchantTempByAuditId(auditId);
			
			
		} catch (Exception e) {
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			LogCvt.error("查询商户审核通过在审核失败，原因:" + e.getMessage(), e);
			throw e;
		}finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return merTemp;
	}

	/**
	 * 查询商户临时表数据
	 */
	@Override
	public List<MerchantTemp> findMerchantTemp(MerchantTemp merchantTemp)
			throws Exception {
		SqlSession sqlSession = null;
		MerchantTempMapper mTempMapper = null;
		List<MerchantTemp> list = new ArrayList<MerchantTemp>();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			mTempMapper = sqlSession.getMapper(MerchantTempMapper.class);
			
			list = mTempMapper.findMerchantTemp(merchantTemp);
			
			
		} catch (Exception e) {
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			LogCvt.error("查询商户审核通过在审核列表失败，原因:" + e.getMessage(), e);
			throw e;
		}finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return list;
	}
}
