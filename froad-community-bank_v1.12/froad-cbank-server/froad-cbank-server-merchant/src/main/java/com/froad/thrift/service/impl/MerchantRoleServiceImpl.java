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
 * @Title: MerchantRoleImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.lang.reflect.InvocationTargetException;
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
import com.froad.logic.MerchantRoleLogic;
import com.froad.logic.impl.MerchantRoleLogicImpl;
import com.froad.po.MerchantRole;
import com.froad.po.MerchantRoleAddRes;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantRoleService;
import com.froad.thrift.vo.MerchantRoleAddVoRes;
import com.froad.thrift.vo.MerchantRolePageVoRes;
import com.froad.thrift.vo.MerchantRoleVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.ValidatorUtil;


/**
 * 
 * <p>@Title: MerchantRoleImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantRoleServiceImpl extends BizMonitorBaseService implements MerchantRoleService.Iface {
	private MerchantRoleLogic merchantRoleLogic = new MerchantRoleLogicImpl();
	public MerchantRoleServiceImpl() {}
	public MerchantRoleServiceImpl(String name, String version) {
		super(name, version);
	}

    /**
     * 增加 MerchantRole
     * @param merchantRole
     * @return MerchantRoleAddVoRes
     */
	@Override
	public MerchantRoleAddVoRes addMerchantRole(OriginVo originVo, MerchantRoleVo merchantRoleVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加MerchantRole");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("添加商户角色信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		MerchantRoleAddVoRes merchantRoleAddVoRes = new MerchantRoleAddVoRes ();
		try {
			MerchantRole merchantRole = (MerchantRole)BeanUtil.copyProperties(MerchantRole.class, merchantRoleVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantRole); // 校验bean
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			MerchantRoleAddRes merchantRoleAddRes = merchantRoleLogic.addMerchantRole(merchantRole);
			merchantRoleAddVoRes = (MerchantRoleAddVoRes)BeanUtil.copyProperties(MerchantRoleAddVoRes.class, merchantRoleAddRes);
		} catch (FroadServerException e) {
			LogCvt.error("添加商户角色失败, 原因:" + e.getMessage(), e);
			Result result = new Result();
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			merchantRoleAddVoRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		} catch (Exception e) {
			LogCvt.error("添加商户角色[系统异常], 原因:" + e.getMessage(), e);
			Result result = new Result();
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
			merchantRoleAddVoRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		}
		return merchantRoleAddVoRes;
	}



    /**
     * 删除 MerchantRole
     * @param merchantRole
     * @return ResultVo    
     */
	@Override
	public ResultVo deleteMerchantRole(OriginVo originVo, MerchantRoleVo merchantRoleVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除MerchantRole");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("删除商户角色信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		Result result = new Result();
		MerchantRole merchantRole = (MerchantRole)BeanUtil.copyProperties(MerchantRole.class, merchantRoleVo);
		try {
			result = merchantRoleLogic.deleteMerchantRole(merchantRole);
		} catch (FroadServerException e) {
			LogCvt.error("删除商户相册失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除商户相册[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}



    /**
     * 修改 MerchantRole
     * @param merchantRole
     * @return ResultVo    
     */
	@Override
	public ResultVo updateMerchantRole(OriginVo originVo, MerchantRoleVo merchantRoleVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改MerchantRole");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("修改商户角色信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		Result result = new Result();
		try {
			MerchantRole merchantRole = (MerchantRole)BeanUtil.copyProperties(MerchantRole.class, merchantRoleVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantRole); // 校验bean
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			result = merchantRoleLogic.updateMerchantRole(merchantRole);
			
		} catch (FroadServerException e) {
			LogCvt.error("修改商户角色失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改商户角色[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}



    /**
     * 查询 MerchantRole
     * @param merchantRole
     * @return List<MerchantRoleVo>
     */
	@Override
	public List<MerchantRoleVo> getMerchantRole(MerchantRoleVo merchantRoleVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询MerchantRole");
		MerchantRole merchantRole = (MerchantRole)BeanUtil.copyProperties(MerchantRole.class, merchantRoleVo);

		List<MerchantRole> merchantRoleList = merchantRoleLogic.findMerchantRole(merchantRole);
		List<MerchantRoleVo> merchantRoleVoList = new ArrayList<MerchantRoleVo>();
		for (MerchantRole po : merchantRoleList) {
			MerchantRoleVo vo = (MerchantRoleVo)BeanUtil.copyProperties(MerchantRoleVo.class, po);
			merchantRoleVoList.add(vo);
		}
		return merchantRoleVoList;
	}



    /**
     * 分页查询 MerchantRole
     * @param merchantRole
     * @return MerchantRolePageVoRes
     */
	@Override
	public MerchantRolePageVoRes getMerchantRoleByPage(PageVo pageVo, MerchantRoleVo merchantRoleVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询MerchantRole");
		Page<MerchantRole> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		MerchantRole merchantRole = (MerchantRole)BeanUtil.copyProperties(MerchantRole.class, merchantRoleVo);

		page = merchantRoleLogic.findMerchantRoleByPage(page, merchantRole);

		MerchantRolePageVoRes merchantRolePageVoRes = new MerchantRolePageVoRes();
		List<MerchantRoleVo> merchantRoleVoList = new ArrayList<MerchantRoleVo>();
		for (MerchantRole po : page.getResultsContent()) {
			MerchantRoleVo vo = (MerchantRoleVo)BeanUtil.copyProperties(MerchantRoleVo.class, po);
			merchantRoleVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		merchantRolePageVoRes.setPage(pageVo);
		merchantRolePageVoRes.setMerchantRoleVoList(merchantRoleVoList);
		return merchantRolePageVoRes;
	}
	
	@Override
	public MerchantRoleVo getMerchantRoleByClientIdAndRoleDesc(String clientId,
			String description) throws TException {
		LogCvt.info("单个MerchantRole角色查询");
		if(StringUtils.isBlank(clientId)){
			throw new FroadServerException("查询MerchantRole失败,原因:客户端id不能为空!");
		}
		if(StringUtils.isBlank(clientId)){
			throw new FroadServerException("查询MerchantRole失败,原因:角色描述不能为空!");
		}
		MerchantRole po = merchantRoleLogic.findMerchantRoleByClientIdAndDesc(clientId, description);
		MerchantRoleVo vo = (MerchantRoleVo)BeanUtil.copyProperties(MerchantRoleVo.class, po);
		return vo;
	}


}
