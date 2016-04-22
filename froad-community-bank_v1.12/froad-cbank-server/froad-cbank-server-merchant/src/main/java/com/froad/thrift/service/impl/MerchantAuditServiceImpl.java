package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantAccountLogic;
import com.froad.logic.MerchantAuditLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.MerchantUserLogic;
import com.froad.logic.impl.MerchantAccountLogicImpl;
import com.froad.logic.impl.MerchantAuditLogicImpl;
import com.froad.logic.impl.MerchantLogicImpl;
import com.froad.logic.impl.MerchantUserLogicImpl;
import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.MerchantTemp;
import com.froad.po.MerchantUser;
import com.froad.po.Result;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.TypeInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantAuditService.Iface;
import com.froad.thrift.vo.MerchantAuditVoRes;
import com.froad.thrift.vo.MerchantTempVo;
import com.froad.thrift.vo.MerchantTempVoReq;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

public class MerchantAuditServiceImpl extends BizMonitorBaseService implements
		Iface {

	private MerchantAuditLogic logic = new MerchantAuditLogicImpl();

	public MerchantAuditServiceImpl() {
	}

	public MerchantAuditServiceImpl(String name, String version) {
		super(name, version);
	}

	/**
	 * 修改商户必审信息
	 */
	@Override
	public MerchantAuditVoRes auditMerchant(OriginVo originVo,
			MerchantTempVoReq merchantTempVoReq) throws TException {
		Result result = new Result();
		MerchantAuditVoRes voRes = new MerchantAuditVoRes();
		try {
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("修改商户必审信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			//拷贝属性
			MerchantTempVo mTempVo = merchantTempVoReq.getMerchantTempVo();
			MerchantTemp merchantTemp = (MerchantTemp) BeanUtil.copyProperties(MerchantTemp.class, mTempVo);

			
			if (StringUtils.isBlank(merchantTemp.getClientId())) {
				throw new FroadServerException("客户端ID不能为空!");
			}
			if (StringUtils.isBlank(merchantTemp.getMerchantId())) {
				throw new FroadServerException("商户ID不能为空!");
			}
			if (StringUtils.isBlank(merchantTemp.getAuditId())) {
				throw new FroadServerException("审核流水号不能为空!");
			}

			if (StringUtils.isBlank(merchantTemp.getContactName())
					&& merchantTemp.getMerchantCategoryId() == null
					&& StringUtils.isBlank(merchantTemp.getLegalName())
					&& merchantTemp.getLegalCredentType() == null
					&& StringUtils.isBlank(merchantTemp.getLegalCredentNo())
					&& StringUtils.isBlank(merchantTemp.getOrgCode())
					&& StringUtils.isBlank(merchantTemp.getMerchantTypeId())
					&& StringUtils.isBlank(merchantTemp.getAccountName())
					&& StringUtils.isBlank(merchantTemp.getAcountNo())
					&& StringUtils.isBlank(merchantTemp.getLoginMobile())
					//20151019 add
					&& StringUtils.isBlank(merchantTemp.getMerchantName())
					&& StringUtils.isBlank(merchantTemp.getPhone())
					&& StringUtils.isBlank(merchantTemp.getContactPhone())
					&& StringUtils.isBlank(merchantTemp.getLicense())
					&& StringUtils.isBlank(merchantTemp.getContractStaff())
					//20151125 trimray add
					&& Checker.isEmpty(merchantTemp.getIsOutsource())
					&& Checker.isEmpty(merchantTemp.getCompanyId())
					) {

				throw new FroadServerException("未发现有商户信息修改内容!");
			}
			//检验商户分类、商户类型多值是否一致有值
			combine(merchantTemp);

			Merchant m = new Merchant();
			m.setMerchantId(merchantTemp.getMerchantId());
			m.setEditAuditState(ProductAuditState.noAudit.getCode());
			MerchantLogic ml = new MerchantLogicImpl();
			int n = ml.countMerchant(m);
			if (n > 0) {
				throw new FroadServerException("该商户已有一条待审核未完成流程,不能再次新增审核!");
			}
			
			HashMap<String,Object> primevalMap = new HashMap<String,Object>();
			
			//获取原商户信息，存于cb_merchant_temp.primeval字段中，格式为json
			m = ml.findMerchantByMerchantId(merchantTemp.getMerchantId());
			if(Checker.isEmpty(m)){
				throw new FroadServerException("商户信息不存在[merchantId:"+merchantTemp.getMerchantId()+"]!");
			}
			primevalMap.put("merchantName", m.getMerchantName());
			primevalMap.put("merchantFullname", m.getMerchantFullname());
			primevalMap.put("contactName", m.getContactName());
			primevalMap.put("contactPhone", m.getContactPhone());// 20151019 add  联系人手机
			primevalMap.put("phone", m.getPhone());// 20151116 add  联系人电话
			primevalMap.put("license", m.getLicense());// 20151019 add 
			primevalMap.put("contractStaff", m.getContractStaff());// 20151019 add 
			primevalMap.put("legalName", m.getLegalName());
			primevalMap.put("legalCredentType", m.getLegalCredentType());
			primevalMap.put("legalCredentNo", m.getLegalCredentNo());
			primevalMap.put("orgCode", m.getOrgCode());
			//20151125 trimray add
			primevalMap.put("isOutsource", m.getIsOutsource());//是否外包 false=否，true=是
			primevalMap.put("companyId", m.getCompanyId());//对应的外包公司id
			
			
			List<CategoryInfo> categoryInfoList = ml.findMerchantCategoryInfo(merchantTemp.getMerchantId());
			if(categoryInfoList != null && categoryInfoList.size() > 0){
				primevalMap.put("category", categoryInfoList.get(0).getName());
			}else{
				primevalMap.put("category", "");
			}
			
			List<TypeInfo> typeInfolist = ml.findMerchantTypeInfo(merchantTemp.getMerchantId());
			if(categoryInfoList != null && categoryInfoList.size() > 0){
				StringBuilder typeName= new StringBuilder();
				for(TypeInfo typeInfo : typeInfolist){
					typeName.append(typeInfo.getTypeName());
					typeName.append(",");
					primevalMap.put("categoryType", typeName.toString());
				}
			}else{
				primevalMap.put("categoryType", "");
			}
			
			//查商户帐号信息
			MerchantAccountLogic accountLogic = new MerchantAccountLogicImpl(); 
			MerchantAccount account = accountLogic.findMerchantAccountByMerchantId(merchantTemp.getClientId(), merchantTemp.getMerchantId(), "0");
			if(Checker.isNotEmpty(account)){
				primevalMap.put("acctName", account.getAcctName());
				primevalMap.put("acctNumber", account.getAcctNumber());
			}else{
				primevalMap.put("acctName", "");
				primevalMap.put("acctNumber", "");
			}
			
			//登录人手机   
			MerchantUserLogic userLogic = new MerchantUserLogicImpl();
			MerchantUser user = new MerchantUser();
			user.setClientId(merchantTemp.getClientId());
			user.setMerchantId(merchantTemp.getMerchantId());
			user.setMerchantRoleId(100000000l);
			user = userLogic.findMerchantUser(user).get(0);
			if(user != null){
				primevalMap.put("loginMobile", user.getPhone());
			}else{
				primevalMap.put("loginMobile", "");
			}
			
			merchantTemp.setPrimeval(JSON.toJSONString(primevalMap));

			boolean b = logic.auditMerchant(originVo, merchantTemp);
			if (b) {
				result.setResultCode(ResultCode.success.getCode());
			}
		} catch (FroadServerException e) {
			LogCvt.error("添加商户审核失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("添加商户审核[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}
		voRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class,
				result));
		return voRes;
	}
	
	public void combine(MerchantTemp merchantTemp){
		
		Long id = merchantTemp.getMerchantCategoryId();
		if(id == null){
			id = 0l;
		}
		String name = merchantTemp.getMerchantCategoryName();
		if(StringUtils.isBlank(name)){
			name = "";
		}
	
		if( !((id > 0 && name.length() > 0) 
				|| (id == 0 && name.length() == 0))){
			throw new FroadServerException("商户分类请求参数数据不全,如需修改分类,分类ID/名称必须同时有值!");
		}
		
		String typeId = merchantTemp.getMerchantTypeId();
		if(StringUtils.isBlank(typeId)){
			typeId = "";
		}
		String typeName = merchantTemp.getMerchantTypeName();
		if(StringUtils.isBlank(typeName)){
			typeName = "";
		}
		String type = merchantTemp.getMerchantTypeValue();
		if(StringUtils.isBlank(type)){
			type = "";
		}
		int[] num = new int[]{typeId.length(),typeName.length(),type.length()};
		if(!((num[0] > 0 && num[1] > 0 && num[2] > 0)
				|| (num[0] == 0 && num[1] == 0 && num[2] == 0))){
			throw new FroadServerException("商户类型请求参数数据不全,如需修改类型,类型ID/名称必须同时有值!");
		}
	}

	@Override
	public MerchantAuditVoRes synchMerchantEdit(String auditId)
			throws TException {

		Result result = new Result();
		MerchantAuditVoRes voRes = new MerchantAuditVoRes();
		try {
			result  = logic.auditMerchantEdit(auditId);

		} catch (FroadServerException e) {
			LogCvt.error("同步商户审核失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("同步商户审核[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}
		voRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class,
				result));
		return voRes;
	}

	@Override
	public MerchantTempVo getAuditMerchant(OriginVo originVo, String auditId)
			throws TException {
		MerchantTempVo vo = new MerchantTempVo();
		try {
			MerchantTemp temp = logic.findAuditMerchantByAuditId(auditId);
			vo = (MerchantTempVo) BeanUtil.copyProperties(MerchantTempVo.class,
					temp);

		} catch (Exception e) {
			LogCvt.error("查询商户审核[系统异常], 原因:" + e.getMessage(), e);
		}
		return vo;
	}

	@Override
	public List<MerchantTempVo> getMerchantTemp(MerchantTempVo merchantTempVo)
			throws TException {
		MerchantTemp temp = new MerchantTemp();
		List<MerchantTempVo> list = null;
		try {
			list = new ArrayList<MerchantTempVo>();
			temp = (MerchantTemp) BeanUtil.copyProperties(MerchantTemp.class,
					merchantTempVo);

			list = (List<MerchantTempVo>) BeanUtil.copyProperties(
					MerchantTempVo.class, logic.findMerchantTemp(temp));

		} catch (Exception e) {
			LogCvt.error("查询商户审核列表[系统异常], 原因:" + e.getMessage(), e);
		}
		return list;
	}

}
