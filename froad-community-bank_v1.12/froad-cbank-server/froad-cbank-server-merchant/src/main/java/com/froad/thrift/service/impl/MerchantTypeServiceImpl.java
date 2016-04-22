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
 * @Title: MerchantTypeImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.List;

import net.sf.oval.exception.ConstraintsViolatedException;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantTypeLogic;
import com.froad.logic.impl.MerchantTypeLogicImpl;
import com.froad.po.MerchantType;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantTypeService;
import com.froad.thrift.vo.MerchantTypeAddVoRes;
import com.froad.thrift.vo.MerchantTypePageVoRes;
import com.froad.thrift.vo.MerchantTypeVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.ValidatorUtil;


/**
 * 
 * <p>@Title: MerchantTypeImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantTypeServiceImpl extends BizMonitorBaseService implements MerchantTypeService.Iface {
	private MerchantTypeLogic merchantTypeLogic = new MerchantTypeLogicImpl();
	public MerchantTypeServiceImpl() {}
	public MerchantTypeServiceImpl(String name, String version) {
		super(name, version);
	}

    /**
     * 增加 MerchantType
     * @param merchantType
     * @return long    主键ID
     */
	@Override
	public MerchantTypeAddVoRes addMerchantType(OriginVo originVo,MerchantTypeVo merchantTypeVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加MerchantType");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("添加商户类型信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		
		MerchantTypeAddVoRes resVo = new MerchantTypeAddVoRes();
		Result result = new Result();
		
		try {
			MerchantType merchantType = (MerchantType)BeanUtil.copyProperties(MerchantType.class, merchantTypeVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantType); // 校验bean
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			if(merchantType.getTypeName() == null 
					|| "".equals(merchantType.getTypeName())){
				throw new FroadServerException("添加MerchantType失败,原因:TypeName不能为空!");
			}
			
			if(merchantType.getIsDelete() == null){
				throw new FroadServerException("添加MerchantType失败,原因:IsDelete不能为空!");
			}
			
			if(StringUtils.isBlank(merchantType.getClientId())){
				throw new FroadServerException("添加MerchantType失败,原因:ClientId不能为空!");
			}
			
			Long l = merchantTypeLogic.addMerchantType(merchantType);
			if(l != null && l > 0){
				resVo.setId(l);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加商户类型失败");
			}
			
		} catch (FroadServerException e) {
			LogCvt.error("添加商户类型失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("添加商户类型[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
	
		resVo.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return resVo;
	}



    /**
     * 删除 MerchantType
     * @param merchantType
     * @return boolean    
     */
	@Override
	public ResultVo deleteMerchantType(OriginVo originVo,MerchantTypeVo merchantTypeVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除MerchantType");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("删除商户类型信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		Result result = new Result();
		try{
			MerchantType merchantType = (MerchantType)BeanUtil.copyProperties(MerchantType.class, merchantTypeVo);
			Boolean b = merchantTypeLogic.deleteMerchantType(merchantType);
			if (!b) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除MerchantType失败");
			}	
		} catch (FroadServerException e) {
			LogCvt.error("删除商户类型失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除商户类型[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 修改 MerchantType
     * @param merchantType
     * @return boolean    
     */
	@Override
	public ResultVo updateMerchantType(OriginVo originVo,MerchantTypeVo merchantTypeVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改MerchantType");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("修改商户类型信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		Result result = new Result();
		try {
			MerchantType merchantType = (MerchantType)BeanUtil.copyProperties(MerchantType.class, merchantTypeVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantType); // 校验bean
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			Boolean b = merchantTypeLogic.updateMerchantType(merchantType);
			if (!b) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改MerchantType失败");
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
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	/**
	 * 根据id查询MerchantType
	 * @Title: getMerchantTypeById 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @param clientId
	 * @return
	 * @return MerchantTypeVo    返回类型 
	 * @throws
	 */
	@Override
	public MerchantTypeVo getMerchantTypeById(long id,String clientId) {
		LogCvt.info("根据id查询MerchantType");
		MerchantTypeVo merchantTypeVo = new MerchantTypeVo();
		MerchantType merchantType  = merchantTypeLogic.findMerchantTypeById(id,clientId);
		if(null != merchantType)
			merchantTypeVo = (MerchantTypeVo)BeanUtil.copyProperties(MerchantTypeVo.class, merchantType);
		return merchantTypeVo;
	}

    /**
     * 查询 MerchantType
     * @param merchantType
     * @return List<MerchantTypeVo>
     */
	@Override
	public List<MerchantTypeVo> getMerchantType(MerchantTypeVo merchantTypeVo) throws TException {
		LogCvt.info("查询MerchantType");
		MerchantType merchantType = (MerchantType)BeanUtil.copyProperties(MerchantType.class, merchantTypeVo);
		
		if(StringUtils.isBlank(merchantType.getClientId())){
			throw new FroadServerException("查询MerchantType失败,原因:clientId不能为空!");
		}

		List<MerchantType> merchantTypeList = merchantTypeLogic.findMerchantType(merchantType);
		List<MerchantTypeVo> merchantTypeVoList = (List<MerchantTypeVo>)BeanUtil.copyProperties(MerchantTypeVo.class, merchantTypeList);
		
		return merchantTypeVoList;
	}



    /**
     * 分页查询 MerchantType
     * @param merchantType
     * @return MerchantTypePageVoRes
     */
	@Override
	public MerchantTypePageVoRes getMerchantTypeByPage(PageVo pageVo, MerchantTypeVo merchantTypeVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询MerchantType");
		Page<MerchantType> page = (Page<MerchantType>) BeanUtil.copyProperties(Page.class, pageVo);
		MerchantType merchantType = (MerchantType) BeanUtil.copyProperties(MerchantType.class, merchantTypeVo);

		page = merchantTypeLogic.findMerchantTypeByPage(page, merchantType);

		MerchantTypePageVoRes merchantTypePageVoRes = new MerchantTypePageVoRes();
		List<MerchantTypeVo> merchantTypeVoList = (List<MerchantTypeVo>) BeanUtil.copyProperties(MerchantTypeVo.class, page.getResultsContent());

		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
		merchantTypePageVoRes.setPage(pageVo);
		merchantTypePageVoRes.setMerchantTypeVoList(merchantTypeVoList);
		return merchantTypePageVoRes;
	}
	@Override
	public List<MerchantTypeVo> getMerchantTypeVoByMerchantTypeIdList(String clientId,
			List<Long> merchantTypeIdList) throws TException {
		LogCvt.info("根据多merchanttypeid查询MerchantType");

		List<MerchantType> merchantTypeList = merchantTypeLogic.findMerchantTypeByMerchantTypeIdList(clientId,merchantTypeIdList);
		List<MerchantTypeVo> merchantTypeVoList = (List<MerchantTypeVo>)BeanUtil.copyProperties(MerchantTypeVo.class, merchantTypeList);
		
		return merchantTypeVoList;
	}


}
