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
 * @Title: MerchantUserImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.oval.exception.ConstraintsViolatedException;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantUserLogic;
import com.froad.logic.impl.MerchantUserLogicImpl;
import com.froad.monitor.MerchantUserMonitor;
import com.froad.po.MerchantUser;
import com.froad.po.MerchantUserAddRes;
import com.froad.po.MerchantUserCheckRes;
import com.froad.po.MerchantUserLoginRes;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.vo.MerchantUserAddVoRes;
import com.froad.thrift.vo.MerchantUserCheckVoRes;
import com.froad.thrift.vo.MerchantUserLoginVoRes;
import com.froad.thrift.vo.MerchantUserPageVoRes;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.ValidatorUtil;


/**
 * 
 * <p>@Title: MerchantUserImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantUserServiceImpl extends BizMonitorBaseService implements MerchantUserService.Iface {
	private MerchantUserLogic merchantUserLogic = new MerchantUserLogicImpl();
	public MerchantUserServiceImpl() {}
	public MerchantUserServiceImpl(String name, String version) {
		super(name, version);
	}

    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return long    主键ID
     */
	@Override
	public MerchantUserAddVoRes addMerchantUser(OriginVo originVo, MerchantUserVo merchantUserVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加MerchantUser");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("添加商户用户信息");
		com.froad.util.LogUtils.addLog(originVo);
		MerchantUserAddVoRes merchantUserAddVoRes = new MerchantUserAddVoRes();
		MerchantUserAddRes merchantUserAddRes  = new MerchantUserAddRes();
		try {
			MerchantUser merchantUser = (MerchantUser)BeanUtil.copyProperties(MerchantUser.class, merchantUserVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantUser); // 校验bean
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			if (null == merchantUser.getClientId()) {
				throw new FroadServerException("客户端id不能为空");
			}
			if (StringUtils.isBlank(merchantUser.getMerchantId())) {
				throw new FroadServerException("商户id不能为空");
			}
			if (StringUtils.isEmpty(merchantUser.getOutletId())) {
				throw new FroadServerException("门店id不能为空");
			}
			if (null == merchantUser.getMerchantRoleId()) {
				throw new FroadServerException("用户角色id不能为空");
			}
			if (StringUtils.isBlank(merchantUser.getUsername())) {
				throw new FroadServerException("用户名不能为空");
			}
			if (StringUtils.isBlank(merchantUser.getPassword())) {
				throw new FroadServerException("密码名不能为空");
			}
			if (null == merchantUser.getIsRest()) {
				throw new FroadServerException("密码是否重置不能为空");
			}
			if (null == merchantUser.getIsDelete()) {
				throw new FroadServerException("是否删除不能为空");
			}
			
			
			merchantUserAddRes = merchantUserLogic.addMerchantUser(merchantUser);
		} catch (FroadServerException e) {
			LogCvt.error("添加商户用户失败, 原因:" + e.getMessage(), e);
			Result result = new Result();
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			merchantUserAddRes.setResult(result);
		} catch (Exception e) {
			LogCvt.error("添加商户用户[系统异常], 原因:" + e.getMessage(), e);
			Result result = new Result();
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
			merchantUserAddRes.setResult(result);
		}
		
		merchantUserAddVoRes = (MerchantUserAddVoRes)BeanUtil.copyProperties(MerchantUserAddVoRes.class, merchantUserAddRes);
		return merchantUserAddVoRes;
	}



    /**
     * 删除 MerchantUser
     * @param merchantUser
     * @return ResultVo    
     */
	@Override
	public ResultVo deleteMerchantUser(OriginVo originVo, MerchantUserVo merchantUserVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除MerchantUser");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("删除商户用户信息");
		com.froad.util.LogUtils.addLog(originVo);
		Result result = new Result();		
		try {
			MerchantUser merchantUser = (MerchantUser)BeanUtil.copyProperties(MerchantUser.class, merchantUserVo);
			result = merchantUserLogic.deleteMerchantUser(merchantUser);
			
		} catch (FroadServerException e) {
			LogCvt.error("删除商户类型失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除商户类型[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}



    /**
     * 修改 MerchantUser
     * @param merchantUser
     * @return ResultVo    
     */
	@Override
	public ResultVo updateMerchantUser(OriginVo originVo, MerchantUserVo merchantUserVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改MerchantUser");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("修改商户用户信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		Result result = new Result();	
		try {
			MerchantUser merchantUser = (MerchantUser)BeanUtil.copyProperties(MerchantUser.class, merchantUserVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantUser); // 校验bean
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			result = merchantUserLogic.updateMerchantUser(merchantUser);
			
		} catch (FroadServerException e) {
			LogCvt.error("修改商户类型失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改商户类型[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}

	/**
     * 查询 MerchantUser
     * @param id
     * @return MerchantUser
     * 
     * @param id
     */
	@Override
	public MerchantUserVo getMerchantUserById(long id) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询MerchantUser");
		MerchantUser merchantUser = merchantUserLogic.getMerchantUserById(id);
		MerchantUserVo merchantUserVo = new MerchantUserVo();
		if(null != merchantUser){
			merchantUserVo = (MerchantUserVo)BeanUtil.copyProperties(MerchantUserVo.class, merchantUser);
		}		 
		return merchantUserVo;
	}

	/**     
	 * 商户用户登录
	 * @param username
	 * @param password
	 * @param operatorIp
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantUserService.Iface#login(java.lang.String, java.lang.String)    
	 */	
	@Override
	public MerchantUserLoginVoRes login(OriginVo originVo, String username, String password) throws TException {
		MerchantUserMonitor.sendMerchantUserLoginServiceInvokeNumber(); // 发送业务监控
		
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("MerchantUser登录");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("商户用户登录");
		com.froad.util.LogUtils.addLog(originVo);
		
		
//		String operatorIp = originVo.getOperatorIp();
		
		MerchantUserLoginRes merchantUserLoginRes = merchantUserLogic.login(username, password, originVo);
		LogCvt.info("登录结果 - "+JSON.toJSONString(merchantUserLoginRes));
		MerchantUserLoginVoRes merchantUserLoginVoRes = (MerchantUserLoginVoRes)BeanUtil.copyProperties(MerchantUserLoginVoRes.class, merchantUserLoginRes);
		return merchantUserLoginVoRes;
	}
	
	/**   
	 * 用户登出  
	 * @param token
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantUserService.Iface#logout(java.lang.String)    
	 */
	
	@Override
	public boolean logout(OriginVo originVo, String token) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("MerchantUser用户登出");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("商户用户退出登录");
		com.froad.util.LogUtils.addLog(originVo);
		
		Boolean result = merchantUserLogic.logout(token);
		return null == result ? false : result;
	}
	
    /**
     * 查询 MerchantUser
     * @param merchantUser
     * @return List<MerchantUserVo>
     */
	@Override
	public List<MerchantUserVo> getMerchantUser(MerchantUserVo merchantUserVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询MerchantUser");
		MerchantUser merchantUser = (MerchantUser)BeanUtil.copyProperties(MerchantUser.class, merchantUserVo);

		List<MerchantUser> merchantUserList = merchantUserLogic.findMerchantUser(merchantUser);
		List<MerchantUserVo> merchantUserVoList = new ArrayList<MerchantUserVo>();
		for (MerchantUser po : merchantUserList) {
			MerchantUserVo vo = (MerchantUserVo)BeanUtil.copyProperties(MerchantUserVo.class, po);
			merchantUserVoList.add(vo);
		}
		return merchantUserVoList;
	}



    /**
     * 分页查询 MerchantUser
     * @param merchantUser
     * @return MerchantUserPageVoRes
     */
	@Override
	public MerchantUserPageVoRes getMerchantUserByPage(PageVo pageVo, MerchantUserVo merchantUserVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询MerchantUser");
		Page<MerchantUser> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		MerchantUser merchantUser = (MerchantUser)BeanUtil.copyProperties(MerchantUser.class, merchantUserVo);

		page = merchantUserLogic.findMerchantUserByPage(page, merchantUser);

		MerchantUserPageVoRes merchantUserPageVoRes = new MerchantUserPageVoRes();
		List<MerchantUserVo> merchantUserVoList = new ArrayList<MerchantUserVo>();
		for (MerchantUser po : page.getResultsContent()) {
			MerchantUserVo vo = (MerchantUserVo)BeanUtil.copyProperties(MerchantUserVo.class, po);
			merchantUserVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		merchantUserPageVoRes.setPage(pageVo);
		merchantUserPageVoRes.setMerchantUserVoList(merchantUserVoList);
		return merchantUserPageVoRes;
	}

	/**
     * 校验 token
     * @param token值
     * @param userId
     * @return MerchantUserVo
     */
	@Override
	public MerchantUserCheckVoRes tokenCheck(OriginVo originVo, String token, long userId) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("校验 token");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("商户用户校验 token");
		com.froad.util.LogUtils.addLog(originVo);
		
		MerchantUserCheckRes merchantUserCheckRes = merchantUserLogic.tokenCheck(token, userId);
		MerchantUserCheckVoRes result = new MerchantUserCheckVoRes();
		if(null != merchantUserCheckRes){
			result = (MerchantUserCheckVoRes)BeanUtil.copyProperties(MerchantUserCheckVoRes.class, merchantUserCheckRes);
		}
		return result;
	}
	
	/**
     * 查询 MerchantUserList
     * @return List<MerchantUserVo>
     * 
     * @param roleId
     * @param merchantIdList
     */
	@Override
	public List<MerchantUserVo> getMerchantUserByRoleIdAndMerchantIds(
			long roleId, List<String> merchantIdList) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询 MerchantUserList 参数 roleId 和 merchantIds");
		List<MerchantUser> merchantUserList = merchantUserLogic.getMerchantUserByRoleIdAndMerchantIds(roleId, merchantIdList);
		List<MerchantUserVo> merchantUserVoList = new ArrayList<MerchantUserVo>();
		for (MerchantUser po : merchantUserList) {
			MerchantUserVo vo = (MerchantUserVo)BeanUtil.copyProperties(MerchantUserVo.class, po);
			merchantUserVoList.add(vo);
		}
		return merchantUserVoList;
	}
	
	/**
     * 查询 MerchantUser
     * @param username
     * @return MerchantUser
     * 
     * @param username
     */
	@Override
	public MerchantUserVo getMerchantUserByUsername(String username,com.froad.thrift.vo.OriginVo originVo)
			throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询MerchantUser By username");
		MerchantUser merchantUser = merchantUserLogic.getMerchantUserByUsername(username,originVo);
		MerchantUserVo merchantUserVo = new MerchantUserVo();
		if(null == merchantUser)
			return merchantUserVo;
		merchantUserVo = (MerchantUserVo)BeanUtil.copyProperties(MerchantUserVo.class, merchantUser);
		return merchantUserVo;
	}
	
	
	@Override
	public int queryErrorCount(String clientId, String merchantId, long userId) throws TException{
		LogCvt.info("查询登录错误次数");
		try {
			return merchantUserLogic.findLoginErrorCount(clientId, merchantId, userId);
		} catch (FroadServerException e) {
			LogCvt.error("查询登录错误次数, 原因:" + e.getMessage(), e);
		} catch (Exception e) {
			LogCvt.error("查询登录错误次数, 原因:" + e.getMessage(), e);
		}
		return 0;
	}
	
	/**
	 * 通过用户名和客户端id获取用户登录失败次数.
	 */
	@Override
	public MerchantUserLoginVoRes getLoginFailureCount(OriginVo originVo,String username) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("MerchantUser登录获取失败次数");
		
		MerchantUserLoginRes merchantUserLoginRes = merchantUserLogic.getLoginFailureCount(originVo, username);
		LogCvt.info("登录获取失败次数 - "+JSON.toJSONString(merchantUserLoginRes));
		MerchantUserLoginVoRes merchantUserLoginVoRes = (MerchantUserLoginVoRes)BeanUtil.copyProperties(MerchantUserLoginVoRes.class, merchantUserLoginRes);
		return merchantUserLoginVoRes;
	}	
}