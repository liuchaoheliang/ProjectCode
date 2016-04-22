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
 * @Title: MerchantAccountImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.oval.exception.ConstraintsViolatedException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.Constants;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.AccountAuditState;
import com.froad.enums.AccountSynchState;
import com.froad.enums.AccountTypeEnum;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantAccountLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.impl.MerchantAccountLogicImpl;
import com.froad.logic.impl.MerchantLogicImpl;
import com.froad.po.MerchantAccount;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.vo.MerchantAccountAddVoRes;
import com.froad.thrift.vo.MerchantAccountPageVoRes;
import com.froad.thrift.vo.MerchantAccountVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.ClientUtil;
import com.froad.util.TripleDesUtils;
import com.froad.util.ValidatorUtil;


/**
 * 
 * <p>@Title: MerchantAccountImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantAccountServiceImpl extends BizMonitorBaseService implements MerchantAccountService.Iface {
	private MerchantAccountLogic merchantAccountLogic = new MerchantAccountLogicImpl();
	public MerchantAccountServiceImpl() {}
	public MerchantAccountServiceImpl(String name, String version) {
		super(name, version);
	}

    /**
     * 增加 MerchantAccount
     * @param merchantAccount
     * @return long    主键ID
     */
	@Override
	public MerchantAccountAddVoRes addMerchantAccount(OriginVo originVo,MerchantAccountVo merchantAccountVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加MerchantAccount");
		MerchantAccountAddVoRes resVo = new MerchantAccountAddVoRes();
		Result result = new Result();
		try {
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("添加商户账号信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			MerchantAccount merchantAccount = (MerchantAccount)BeanUtil.copyProperties(MerchantAccount.class, merchantAccountVo);
			try {
				ValidatorUtil.getValidator().assertValid(merchantAccount); // 校验bean				
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			if(merchantAccount.getMerchantId() == null 
					|| "".equals(merchantAccount.getMerchantId())){
				throw new FroadServerException("添加MerchantAccount失败,原因:MerchantId不能为空!");
			}
			
			if(merchantAccount.getOutletId() == null 
					|| "".equals(merchantAccount.getOutletId())){
				throw new FroadServerException("添加MerchantAccount失败,原因:OutletId不能为空!");
			}
			if(merchantAccount.getClientId() == null && "".equals(merchantAccount.getClientId())){
				throw new FroadServerException("添加MerchantAccount失败,原因:ClientId不能为空!");
			}
			
			if(merchantAccount.getAcctName() == null 
					|| "".equals(merchantAccount.getAcctName())){
				throw new FroadServerException("添加MerchantAccount失败,原因:AcctName不能为空!");
			}
			
			if(merchantAccount.getAcctNumber() == null 
					|| "".equals(merchantAccount.getAcctNumber())){
				throw new FroadServerException("添加MerchantAccount失败,原因:AcctNumber不能为空!");
			}
			
			if(merchantAccount.getIsDelete() == null){
				throw new FroadServerException("添加MerchantAccount失败,原因:IsDelete不能为空!");
			}
			
			if(merchantAccount.getAcctType() == null
					|| "".equals(merchantAccount.getAcctType())){
				throw new FroadServerException("添加MerchantAccount失败,原因:AcctType不能为空!");
			} else {
//				if(!AccountTypeEnum.SQ.getCode().equals(merchantAccount.getAcctType()) && !AccountTypeEnum.FQ.getCode().equals(merchantAccount.getAcctType()) ) {
//					throw new FroadServerException("添加MerchantAccount失败,原因:AcctType不合法!");
//				}
			}
			
//			if(merchantAccount.getCreateTime() == null){
				merchantAccount.setCreateTime(new java.util.Date());
//			}
			

			Long rlt = merchantAccountLogic.addMerchantAccount(merchantAccount);
			if(rlt != null && rlt > 0){
				resVo.setId(rlt);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加商户/门店账号失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("添加商户/门店账号失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("添加商户/门店账号[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		
		resVo.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return resVo;
	}



    /**
     * 删除 MerchantAccount
     * @param merchantAccount
     * @return boolean    
     */
	@Override
	public ResultVo deleteMerchantAccount(OriginVo originVo,MerchantAccountVo merchantAccountVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除MerchantAccount");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("删除商户账号信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		Result result = new Result();
		try {
			MerchantAccount findMerchantAccount = (MerchantAccount) BeanUtil.copyProperties(MerchantAccount.class, merchantAccountVo);
			List<MerchantAccount> merchantAccountList = merchantAccountLogic.findMerchantAccount(findMerchantAccount);
			MerchantAccount merchantAccount = null;
			if (null != merchantAccountList && merchantAccountList.size() > 0) {
				merchantAccount = merchantAccountList.get(0);
				if (!merchantAccountLogic.deleteMerchantAccount(merchantAccount)) {
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("删除商户/门店账号失败");
				}
			}else{
				throw new FroadServerException("[merchantId="+findMerchantAccount.getMerchantId()+",outletId="+findMerchantAccount.getOutletId()+"] 账户不存在！");
			}
		} catch (FroadServerException e) {
			LogCvt.error("添加MerchantAcount失败,原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("添加MerchantAcount[系统异常],原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}

	/**   
	 * 物理删除 MerchantAccount(商户添加时回滚用到)  
	 * @param originVo
	 * @param id
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantAccountService.Iface#removeMerchantAccount(com.froad.thrift.vo.OriginVo, long)    
	 */
	
	@Override
	public ResultVo removeMerchantAccount(OriginVo originVo, long id) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("物理删除MerchantAccount");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("物理删除商户账号信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		Result result = new Result();
		
		try {
			if (!merchantAccountLogic.removeMerchantAccount(id)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("物理删除商户/门店账号失败");
			}	
		} catch (FroadServerException e) {
			LogCvt.error("物理删除MerchantAcount失败,原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("物理删除MerchantAcount[系统异常],原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}

    /**
     * 修改 MerchantAccount
     * @param merchantAccount
     * @return boolean    
     */
	@Override
	public ResultVo updateMerchantAccount(OriginVo originVo,MerchantAccountVo merchantAccountVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改MerchantAccount");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("修改商户账号信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		Result result = new Result();
		try {
			MerchantAccount merchantAccount = (MerchantAccount)BeanUtil.copyProperties(MerchantAccount.class, merchantAccountVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantAccount); // 校验bean				
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
//			if(StringUtils.isNotBlank(merchantAccount.getAcctType()) && !AccountTypeEnum.SQ.getCode().equals(merchantAccount.getAcctType()) && !AccountTypeEnum.FQ.getCode().equals(merchantAccount.getAcctType()) ) {
//				throw new FroadServerException("添加MerchantAccount失败,原因:AcctType不合法!");
//			}
			Boolean b = merchantAccountLogic.updateMerchantAccount(merchantAccount);
			if (!b) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改商户/门店账号失败");
			}	
		} catch (FroadServerException e) {
			LogCvt.error("修改MerchantAcount失败,原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改MerchantAcount[系统异常],原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 查询 MerchantAccount
     * @param merchantAccount
     * @return List<MerchantAccountVo>
     */
	@Override
	public List<MerchantAccountVo> getMerchantAccount(MerchantAccountVo merchantAccountVo) throws TException {
		LogCvt.info("查询MerchantAccount");
		MerchantAccount merchantAccount = (MerchantAccount)BeanUtil.copyProperties(MerchantAccount.class, merchantAccountVo);

		List<MerchantAccount> merchantAccountList = merchantAccountLogic.findMerchantAccount(merchantAccount);
		List<MerchantAccountVo> merchantAccountVoList = new ArrayList<MerchantAccountVo>();
		for (MerchantAccount po : merchantAccountList) {
			MerchantAccountVo vo = (MerchantAccountVo)BeanUtil.copyProperties(MerchantAccountVo.class, po);
			merchantAccountVoList.add(vo);
		}
		return merchantAccountVoList;
	}



    /**
     * 分页查询 MerchantAccount
     * @param merchantAccount
     * @return MerchantAccountPageVoRes
     */
	@Override
	public MerchantAccountPageVoRes getMerchantAccountByPage(PageVo pageVo, MerchantAccountVo merchantAccountVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询MerchantAccount");
		Page<MerchantAccount> page = (Page<MerchantAccount>)BeanUtil.copyProperties(Page.class, pageVo);
		MerchantAccount merchantAccount = (MerchantAccount)BeanUtil.copyProperties(MerchantAccount.class, merchantAccountVo);

		page = merchantAccountLogic.findMerchantAccountByPage(page, merchantAccount);

		MerchantAccountPageVoRes merchantAccountPageVoRes = new MerchantAccountPageVoRes();
		List<MerchantAccountVo> merchantAccountVoList = new ArrayList<MerchantAccountVo>();
		for (MerchantAccount po : page.getResultsContent()) {
			MerchantAccountVo vo = (MerchantAccountVo)BeanUtil.copyProperties(MerchantAccountVo.class, po);
			merchantAccountVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		merchantAccountPageVoRes.setPage(pageVo);
		merchantAccountPageVoRes.setMerchantAccountVoList(merchantAccountVoList);
		return merchantAccountPageVoRes;
	}


	
	/**     
	 * 根据门店ID查询门店账号,提供给openAPI调用
	 * @param key
	 * @param merchantId_outletId
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantAccountService.Iface#getMerchantAccountByKeyAndOutletId(java.lang.String, java.lang.String)    
	 */	
	@Override
	public String getMerchantAccountByKeyAndOutletId(String key, String merchantId_outletId) throws TException {
		
		final String password = "ee75cf7c098e4aa58481353b97507aba";
		String responseText = "";
		Map<String, Object> result = new TreeMap<String, Object>();
		if (!password.equals(key)) {
			result.put("resultCode", "9999");
			result.put("resultDesc", "密码错误");
			LogCvt.error("根据门店ID查询门店账号错误, 原因:密码错误!");
			String str = result.toString();
			responseText = this.encrypt(str);
			// response.getWriter().write(responseText);
			return responseText;
		}
		merchantId_outletId = this.decrypt(merchantId_outletId); // 解密门店ID
		String[] mid_oid = merchantId_outletId.split("_"); // 商户id和门店id用_分割
		if (mid_oid.length != 2) {
			LogCvt.error("无效的请求参数：" + merchantId_outletId);
			result.put("resultCode", "9999");
			result.put("resultDesc", "无效的请求参数");
			LogCvt.error("根据门店ID查询门店账号错误, 原因:无效的请求参数!");
			String str = result.toString();
			responseText = this.encrypt(str);
			// response.getWriter().write(responseText);
			return responseText;
		}
		String merchantId = mid_oid[0]; // 商户id
		String outletId =   mid_oid[1]; // 门店id 
//		if (outletId == null) {
//			LogCvt.error("无效的门店号：" + outletId);
//			result.put("resultCode", "9999");
//			result.put("resultDesc", "无效的商户号");
//			String str = result.toString();
//			responseText = this.encrypt(str);
//			// response.getWriter().write(responseText);
//			return responseText;
//		}
		MerchantAccount merchantAccount = new MerchantAccount();
		merchantAccount.setMerchantId(merchantId);
		merchantAccount.setOutletId(outletId);
		merchantAccount.setIsDelete(false);
		merchantAccount.setAcctType(AccountTypeEnum.SQ.getCode()); // 1-收款  2-付款
		List<MerchantAccount> list = merchantAccountLogic.findMerchantAccount(merchantAccount);
		if(CollectionUtils.isEmpty(list)) {
			
			merchantAccount.setOutletId("0"); // zhijie
			list = merchantAccountLogic.findMerchantAccount(merchantAccount);
			if (CollectionUtils.isEmpty(list)) {

				LogCvt.error("商户门店账号信息不存在或已经被删除，商户编号：" + merchantId + "，门店编号："
						+ outletId);
				result.put("resultCode", "9999");
				result.put("resultDesc", "商户门店账号信息不存在或已经被删除");
				LogCvt.error("根据门店ID查询门店账号错误, 原因:商户门店账号信息不存在或已经被删除!");
				String str = result.toString();
				responseText = this.encrypt(str);
				// response.getWriter().write(responseText);
				return responseText;
			}
			
		}
//		MerchantAccount merchantAccount = merchantAccountLogic.findMerchantAccountByOutletId(outletId);
		merchantAccount = list.get(0); // 拿第一个
//		if (merchantAccount.getIsDelete()) {
//			LogCvt.error("门店账号信息已经被删除，商户编号：" + outletId);
//			result.put("resultCode", "9999");
//			result.put("resultDesc", "门店账号信息已经被删除");
//			String str = result.toString();
//			responseText = this.encrypt(str);
//			// response.getWriter().write(responseText);
//			return responseText;
//		} else {
			result.put("resultCode", "0000");
			result.put("resultDesc", "成功");
			result.put("accountName", merchantAccount.getAcctName());
			result.put("bankCardNo", merchantAccount.getAcctNumber());
			String str = result.toString();
			LogCvt.debug("加密前的商户门店账号信息:" + str);
			responseText = this.encrypt(str);
			LogCvt.debug("加密后的商户门店账号信息:" + responseText);
//		}
		return responseText;
	}

	private String encrypt(String plain){
		try {
			return TripleDesUtils.encrypt(plain);
		} catch (Exception e) {
			LogCvt.error("加密失败，明文："+plain,e);
			return null;
		}
	}
	
	private String decrypt(String encrypt){
		try {
			return TripleDesUtils.decrypt(encrypt);
		} catch (Exception e) {
			LogCvt.error("解密失败，密文："+encrypt,e);
			return null;
		}
	}	
}