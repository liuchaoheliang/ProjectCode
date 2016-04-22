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
 * @Title: MerchantRoleResourceImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantRoleResourceLogic;
import com.froad.logic.impl.MerchantRoleResourceLogicImpl;
import com.froad.po.MerchantRoleResourceAddRes;
import com.froad.po.Result;
import com.froad.po.mongo.MerchantRoleResource;
import com.froad.po.mongo.Resource;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantRoleResourceService;
import com.froad.thrift.vo.MerchantRoleResourceAddVoRes;
import com.froad.thrift.vo.MerchantRoleResourceVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResourceVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: MerchantRoleResourceImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantRoleResourceServiceImpl extends BizMonitorBaseService implements MerchantRoleResourceService.Iface {
	
	private MerchantRoleResourceLogic merchantRoleResourceLogic = new MerchantRoleResourceLogicImpl();
	
	public MerchantRoleResourceServiceImpl() {}
	public MerchantRoleResourceServiceImpl(String name, String version) {
		super(name, version);
	}
	
    /**
     * 增加 MerchantRoleResource
     * @param merchantRoleResource
     * @return MerchantRoleResourceAddVoRes
     */
	@Override
	public MerchantRoleResourceAddVoRes addMerchantRoleResource(OriginVo originVo, MerchantRoleResourceVo merchantRoleResourceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加MerchantRoleResource");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("添加商户角色资源信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		MerchantRoleResource merchantRoleResource = new MerchantRoleResource();
		merchantRoleResource.set_id(merchantRoleResourceVo.get_id());
		merchantRoleResource.resources = new ArrayList<Resource>();
//			BeanUtil.copyProperties(merchantRoleResource.getResources(), merchantRoleResourceVo.getResources());
		Resource resource = null;
		for( ResourceVo resourceVo : merchantRoleResourceVo.getResources() ){
			resource = (Resource)BeanUtil.copyProperties(Resource.class, resourceVo);
			
			merchantRoleResource.resources.add(resource);
		} 
		MerchantRoleResourceAddRes merchantRoleResourceAddRes = merchantRoleResourceLogic.addMerchantRoleResource(merchantRoleResource);
		MerchantRoleResourceAddVoRes merchantRoleResourceAddVoRes = (MerchantRoleResourceAddVoRes)BeanUtil.copyProperties(MerchantRoleResourceAddVoRes.class, merchantRoleResourceAddRes);
		return merchantRoleResourceAddVoRes;
	}



    /**
     * 删除 MerchantRoleResource
     * @param _id (client_id_role_id)
     * @return ResultVo    
     */
	@Override
	public ResultVo deleteMerchantRoleResource(OriginVo originVo, String _id) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除MerchantRoleResource");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("删除商户角色资源信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		Result result = new Result();
		try {
//			MerchantRoleResource merchantRoleResource = new MerchantRoleResource();
//			try {
//				BeanUtils.copyProperties(merchantRoleResource, merchantRoleResourceVo);
//			} catch (IllegalAccessException e) {
//				LogCvt.error(e.getMessage());
//			} catch (InvocationTargetException e) {
//				LogCvt.error(e.getMessage());
//			} 
			result = merchantRoleResourceLogic.deleteMerchantRoleResource(_id);
		} catch (FroadServerException e) {
			LogCvt.error("删除商户角色资源失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除商户角色资源[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}



    /**
     * 修改 MerchantRoleResource
     * @param merchantRoleResource
     * @return ResultVo    
     */
	@Override
	public ResultVo updateMerchantRoleResource(OriginVo originVo, MerchantRoleResourceVo merchantRoleResourceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改MerchantRoleResource");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("修改商户角色资源信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		Result result = new Result();
		MerchantRoleResource merchantRoleResource = new MerchantRoleResource();
		merchantRoleResource.set_id(merchantRoleResourceVo.get_id());
		merchantRoleResource.resources = new ArrayList<Resource>();
		//			BeanUtil.copyProperties(merchantRoleResource.getResources(), merchantRoleResourceVo.getResources());
		Resource resource = null;
		try{
			for( ResourceVo resourceVo : merchantRoleResourceVo.getResources() ){
				resource = (Resource)BeanUtil.copyProperties(Resource.class, resourceVo);
				
				merchantRoleResource.resources.add(resource);
			} 
			result = merchantRoleResourceLogic.updateMerchantRoleResource(merchantRoleResource);
		} catch (FroadServerException e) {
			LogCvt.error("删除商户角色资源失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除商户角色资源[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}



    /**
     * 查询 MerchantRoleResource
     * @param _id (client_id_role_id)
     * @return MerchantRoleResourceVo
     */
	@Override
	public MerchantRoleResourceVo getMerchantRoleResource(String _id) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询MerchantRoleResource");
		MerchantRoleResourceVo merchantRoleResourceVo = new MerchantRoleResourceVo();
		MerchantRoleResource merchantRoleResource = merchantRoleResourceLogic.findMerchantRoleResource(_id);
		if( merchantRoleResource != null ){
			merchantRoleResourceVo.set_id(merchantRoleResource.get_id());
			merchantRoleResourceVo.resources = new ArrayList<ResourceVo>();
//				BeanUtil.copyProperties(merchantRoleResource.getResources(), merchantRoleResourceVo.getResources());
			for( Resource resource : merchantRoleResource.getResources() ){
				ResourceVo resourceVo = (ResourceVo)BeanUtil.copyProperties(ResourceVo.class, resource);
				
				merchantRoleResourceVo.resources.add(resourceVo);
			}
		} 
		
		return merchantRoleResourceVo;
	}
	
	/**
     * 查询 List<MerchantRoleResource>
     * @param client_id
     * @return List<MerchantRoleResourceVo>
     * 
     * @param client_id
     */
	@Override
	public List<MerchantRoleResourceVo> getMerchantRoleResourceListByClientId(
			String client_id) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询MerchantRoleResource列表");
		List<MerchantRoleResourceVo> result = new ArrayList<MerchantRoleResourceVo>();
		List<MerchantRoleResource> merchantRoleResourceList = merchantRoleResourceLogic.getMerchantRoleResourceListByClientId(client_id);
		if( merchantRoleResourceList != null && merchantRoleResourceList.size() > 0 ){
			for( MerchantRoleResource merchantRoleResource : merchantRoleResourceList ){
				MerchantRoleResourceVo merchantRoleResourceVo = (MerchantRoleResourceVo)BeanUtil.copyProperties(MerchantRoleResourceVo.class, merchantRoleResource);
				result.add(merchantRoleResourceVo);
			}
		}
		return result;
	}
	
	/**
     * 查询 List<MerchantRoleResource> (全部)
     * @return List<MerchantRoleResourceVo>
     */
	@Override
	public List<MerchantRoleResourceVo> getMerchantRoleResourceList()
			throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询MerchantRoleResource列表 null param");
		List<MerchantRoleResourceVo> result = new ArrayList<MerchantRoleResourceVo>();
		List<MerchantRoleResource> merchantRoleResourceList = merchantRoleResourceLogic.getMerchantRoleResourceList();
		if( merchantRoleResourceList != null && merchantRoleResourceList.size() > 0 ){
			for( MerchantRoleResource merchantRoleResource : merchantRoleResourceList ){
				MerchantRoleResourceVo merchantRoleResourceVo = (MerchantRoleResourceVo)BeanUtil.copyProperties(MerchantRoleResourceVo.class, merchantRoleResource);
				result.add(merchantRoleResourceVo);
			}
		}
		return result;
	}


}
