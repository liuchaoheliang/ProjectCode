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
 * @Title: MerchantResourceImpl.java
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
import com.froad.logic.MerchantResourceLogic;
import com.froad.logic.impl.MerchantResourceLogicImpl;
import com.froad.po.MerchantResource;
import com.froad.po.MerchantResourceAddRes;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantResourceService;
import com.froad.thrift.vo.MerchantResourceAddVoRes;
import com.froad.thrift.vo.MerchantResourcePageVoRes;
import com.froad.thrift.vo.MerchantResourceVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.ValidatorUtil;


/**
 * 
 * <p>@Title: MerchantResourceImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantResourceServiceImpl extends BizMonitorBaseService implements MerchantResourceService.Iface {
	private MerchantResourceLogic merchantResourceLogic = new MerchantResourceLogicImpl();
	public MerchantResourceServiceImpl() {}
	public MerchantResourceServiceImpl(String name, String version) {
		super(name, version);
	}

    /**
     * 增加 MerchantResource
     * @param merchantResource
     * @return MerchantResourceAddVoRes
     */
	@Override
	public MerchantResourceAddVoRes addMerchantResource(OriginVo originVo, MerchantResourceVo merchantResourceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加MerchantResource");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("添加商户资源信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		MerchantResourceAddVoRes merchantResourceAddVoRes = new MerchantResourceAddVoRes();
		try {
			MerchantResource merchantResource = (MerchantResource)BeanUtil.copyProperties(MerchantResource.class, merchantResourceVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantResource); // 校验bean			
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			if(merchantResource.getParentId() == null) {
				throw new FroadServerException("上级资源id不能为空");
			}
			if(merchantResource.getIsEnabled() == null) {
				throw new FroadServerException("资源是否可用不能为空");
			}
			if(merchantResource.getIsParent() == null) {
				throw new FroadServerException("是否为父节点不能为空");
			}
			if(StringUtils.isEmpty(merchantResource.getSn())) {
				throw new FroadServerException("模块(元素)编号不能为空");
			}
			if(StringUtils.isEmpty(merchantResource.getRightSn())) {
				throw new FroadServerException("权限编号不能为空");
			}
			
			MerchantResourceAddRes merchantResourceAddRes = merchantResourceLogic.addMerchantResource(merchantResource);
			merchantResourceAddVoRes = (MerchantResourceAddVoRes)BeanUtil.copyProperties(MerchantResourceAddVoRes.class, merchantResourceAddRes);
		} catch (FroadServerException e) {
			LogCvt.error("添加商户资源失败, 原因:" + e.getMessage(), e);
			Result result = new Result();
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			merchantResourceAddVoRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		} catch (Exception e) {
			LogCvt.error("添加商户资源[系统异常], 原因:" + e.getMessage(), e);
			Result result = new Result();
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
			merchantResourceAddVoRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		}	
		return merchantResourceAddVoRes;
	}



    /**
     * 删除 MerchantResource
     * @param merchantResource
     * @return ResultVo    
     */
	@Override
	public ResultVo deleteMerchantResource(OriginVo originVo, MerchantResourceVo merchantResourceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除MerchantResource");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("删除商户资源信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		Result result = new Result();
		try{
			MerchantResource merchantResource = (MerchantResource)BeanUtil.copyProperties(MerchantResource.class, merchantResourceVo);
			result = merchantResourceLogic.deleteMerchantResource(merchantResource);
		} catch (FroadServerException e) {
			LogCvt.error("删除商户资源失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除商户资源[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}



    /**
     * 修改 MerchantResource
     * @param merchantResource
     * @return ResultVo    
     */
	@Override
	public ResultVo updateMerchantResource(OriginVo originVo, MerchantResourceVo merchantResourceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改MerchantResource");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("修改商户资源信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		Result result = new Result();
		try {
			MerchantResource merchantResource = (MerchantResource)BeanUtil.copyProperties(MerchantResource.class, merchantResourceVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantResource); // 校验bean			
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			result = merchantResourceLogic.updateMerchantResource(merchantResource);
			
		} catch (FroadServerException e) {
			LogCvt.error("修改商户资源失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改商户资源[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}

	/**
	 * 移动MerchantResource
	 * @Title: moveMerchantResourceTo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param originVo
	 * @param srcResourceId
	 * @param destResourceId
	 * @param action 0-移动到前面   1移动到后面
	 * @return
	 * @return ResultVo    返回类型 
	 * @throws
	 */
	public ResultVo moveMerchantResourceTo(OriginVo originVo, long srcResourceId, long destResourceId, int action) {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("移动商户资源");
		// 添加操作日志记录
		if (null != originVo) {
			if (StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("移动商户资源");
			com.froad.util.LogUtils.addLog(originVo);
		}
		Result result = new Result();
		try {

			if (!merchantResourceLogic.moveMerchantResourceTo(srcResourceId, destResourceId, action)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("移动商户资源失败!");
			}

		} catch (FroadServerException e) {
			LogCvt.error("移动商户资源, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("移动商户资源[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		ResultVo resultVo = (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}

    /**
     * 查询 MerchantResource
     * @param merchantResource
     * @return List<MerchantResourceVo>
     */
	@Override
	public List<MerchantResourceVo> getMerchantResource(MerchantResourceVo merchantResourceVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询MerchantResource");
		MerchantResource merchantResource = (MerchantResource)BeanUtil.copyProperties(MerchantResource.class, merchantResourceVo);

		List<MerchantResource> merchantResourceList = merchantResourceLogic.findMerchantResource(merchantResource);
		List<MerchantResourceVo> merchantResourceVoList = new ArrayList<MerchantResourceVo>();
		for (MerchantResource po : merchantResourceList) {
			MerchantResourceVo vo = (MerchantResourceVo)BeanUtil.copyProperties(MerchantResourceVo.class, po);
			merchantResourceVoList.add(vo);
		}
		return merchantResourceVoList;
	}



    /**
     * 分页查询 MerchantResource
     * @param merchantResource
     * @return MerchantResourcePageVoRes
     */
	@Override
	public MerchantResourcePageVoRes getMerchantResourceByPage(PageVo pageVo, MerchantResourceVo merchantResourceVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询MerchantResource");
		Page<MerchantResource> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		MerchantResource merchantResource = (MerchantResource)BeanUtil.copyProperties(MerchantResource.class, merchantResourceVo);

		page = merchantResourceLogic.findMerchantResourceByPage(page, merchantResource);

		MerchantResourcePageVoRes merchantResourcePageVoRes = new MerchantResourcePageVoRes();
		List<MerchantResourceVo> merchantResourceVoList = new ArrayList<MerchantResourceVo>();
		for (MerchantResource po : page.getResultsContent()) {
			MerchantResourceVo vo = (MerchantResourceVo)BeanUtil.copyProperties(MerchantResourceVo.class, po);
			merchantResourceVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		merchantResourcePageVoRes.setPage(pageVo);
		merchantResourcePageVoRes.setMerchantResourceVoList(merchantResourceVoList);
		return merchantResourcePageVoRes;
	}


}
