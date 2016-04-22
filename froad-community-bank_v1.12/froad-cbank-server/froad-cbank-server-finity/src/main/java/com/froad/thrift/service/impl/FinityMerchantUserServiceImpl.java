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

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantUserLogic;
import com.froad.logic.UserResourceLogic;
import com.froad.logic.impl.MerchantUserLogicImpl;
import com.froad.logic.impl.UserResourceLogicImpl;
import com.froad.po.CommonAddRes;
import com.froad.po.MerchantUser;
import com.froad.po.Result;
import com.froad.po.UserResource;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.FinityMerchantUserService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.MerchantUserPageVoRes;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.ValidatorUtil;

import net.sf.oval.exception.ConstraintsViolatedException;


/**
 * 
 * <p>@Title: MerchantUserImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class FinityMerchantUserServiceImpl extends BizMonitorBaseService implements FinityMerchantUserService.Iface {
	private MerchantUserLogic merchantUserLogic = new MerchantUserLogicImpl();
	private UserResourceLogic userResourceLogic = new UserResourceLogicImpl();
	public FinityMerchantUserServiceImpl() {}
	public FinityMerchantUserServiceImpl(String name, String version) {
		super(name, version);
	}

    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return long    主键ID
     */
	@Override
	public CommonAddVoRes addMerchantUser(OriginVo originVo, MerchantUserVo merchantUserVo, List<Long> resourceIds) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加MerchantUser");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("添加商户用户信息");
		com.froad.util.LogUtils.addLog(originVo);
		CommonAddVoRes commonAddVoRes = new CommonAddVoRes();
		CommonAddRes commonAddRes  = new CommonAddRes();
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
			if(resourceIds == null || resourceIds.size() == 0){
				throw new FroadServerException("商户用户资源不能为空");
			}
			if (null == merchantUser.getOperatorUserId()) {
				throw new FroadServerException("操作用户id不能为空");
			}
			
			commonAddRes = merchantUserLogic.addMerchantUser(merchantUser);
			if(ResultCode.success.getCode().equals(commonAddRes.getResult().getResultCode())){
				//添加商户用户资源 
				if(merchantUser.getId()!=null && merchantUser.getId().longValue()>0){
					List<UserResource> userResourceList = new ArrayList<UserResource>();
					UserResource userResource = null;
					for(Long resourceId : resourceIds){
						userResource = new UserResource();
						userResource.setUserId(merchantUser.getId());
						userResource.setResourceId(resourceId);
						userResource.setUserType(1);
						userResourceList.add(userResource);
					}
					userResourceLogic.addUserResourceByBatch(userResourceList);
				}else{
					LogCvt.error("添加商户用户失败, 原因merchantUserId:" + merchantUser.getId());
					Result result = new Result();
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("商户用户对应资源权限添加失败，请重试");
					commonAddRes.setResult(result);
				}
			}
		} catch (FroadServerException e) {
			LogCvt.error("添加商户用户失败, 原因:" + e.getMessage(), e);
			Result result = new Result();
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			commonAddRes.setResult(result);
		} catch (Exception e) {
			LogCvt.error("添加商户用户[系统异常], 原因:" + e.getMessage(), e);
			Result result = new Result();
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
			commonAddRes.setResult(result);
		}
		
		commonAddVoRes = (CommonAddVoRes)BeanUtil.copyProperties(CommonAddVoRes.class, commonAddRes);
		return commonAddVoRes;
	}


    /**
     * 修改 MerchantUser
     * @param merchantUser
     * @return ResultVo    
     */
	@Override
	public com.froad.thrift.vo.ResultVo updateMerchantUser(OriginVo originVo, MerchantUserVo merchantUserVo, List<Long> resourceIds) throws TException {
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
			if(ResultCode.success.getCode().equals(result.getResultCode())){
				userResourceLogic.updateUserResource(merchantUser.getId(), resourceIds);
			} 
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

	 public List<com.froad.thrift.vo.MerchantUserVo> getUserResource(String userId, int userType){
		 return null;
	 }
	 
	 
	 
	 /**
     * 分页查询 MerchantUser
     * @param merchantUser
     * @return MerchantUserPageVoRes
     */
	@Override
	public com.froad.thrift.vo.MerchantUserPageVoRes getMerchantUserByPage(PageVo pageVo, MerchantUserVo merchantUserVo) throws TException {
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
	
	@Override
	public List<MerchantUserVo> getMerchantUser(MerchantUserVo merchantUserVo) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

}
