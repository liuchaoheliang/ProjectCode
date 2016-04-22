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
 * @Title: MerchantCategoryImpl.java
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
import com.froad.logic.MerchantCategoryLogic;
import com.froad.logic.impl.MerchantCategoryLogicImpl;
import com.froad.po.MerchantCategory;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantCategoryService;
import com.froad.thrift.vo.MerchantCategoryAddVoRes;
import com.froad.thrift.vo.MerchantCategoryPageVoRes;
import com.froad.thrift.vo.MerchantCategoryVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.ValidatorUtil;


/**
 * 
 * <p>@Title: MerchantCategoryImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantCategoryServiceImpl extends BizMonitorBaseService implements MerchantCategoryService.Iface {
	private MerchantCategoryLogic merchantCategoryLogic = new MerchantCategoryLogicImpl();
	public MerchantCategoryServiceImpl() {}
	public MerchantCategoryServiceImpl(String name, String version) {
		super(name, version);
	}

    /**
     * 增加 MerchantCategory
     * @param merchantCategory
     * @return long    主键ID
     */
	@Override
	public MerchantCategoryAddVoRes addMerchantCategory(OriginVo originVo,MerchantCategoryVo merchantCategoryVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加MerchantCategory");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("添加商户分类信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		MerchantCategoryAddVoRes resVo = new MerchantCategoryAddVoRes();
		Result result = new Result();
		try {
			MerchantCategory merchantCategory = (MerchantCategory)BeanUtil.copyProperties(MerchantCategory.class, merchantCategoryVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantCategory); // 校验bean				
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			if(merchantCategory.getClientId() == null 
					|| "".equals(merchantCategory.getClientId())){
				throw new FroadServerException("添加MerchantCategory失败,原因:ClientId不能为空!");
			}
			if(merchantCategory.getName() == null
					|| "".equals(merchantCategory.getName())){
				throw new FroadServerException("添加MerchantCategory失败,原因:name不能为空!");
			}
			
			if(merchantCategory.getIsDelete() == null){
				throw new FroadServerException("添加MerchantCategory失败,原因:IsDelete不能为空!");
			}
			
			
			Long l = merchantCategoryLogic.addMerchantCategory(merchantCategory);
			
			if(l != null && l > 0){
				resVo.setId(l);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加商户分类失败");
			}
			
		} catch (FroadServerException e) {
			LogCvt.error("添加商户分类失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("添加商户分类[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}

		resVo.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return resVo;
	}



    /**
     * 删除 MerchantCategory
     * @param merchantCategory
     * @return boolean    
     */
	@Override
	public ResultVo deleteMerchantCategory(OriginVo originVo,MerchantCategoryVo merchantCategoryVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除MerchantCategory");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("删除商户分类信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		Result result = new Result();
		try {
			MerchantCategory merchantCategory = (MerchantCategory)BeanUtil.copyProperties(MerchantCategory.class, merchantCategoryVo);
			Boolean b = merchantCategoryLogic.deleteMerchantCategory(merchantCategory);
			if (!b) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除商户分类失败");
			}	
		} catch (FroadServerException e) {
			LogCvt.error("删除商户分类失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除商户分类[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 修改 MerchantCategory
     * @param merchantCategory
     * @return boolean    
     */
	@Override
	public ResultVo updateMerchantCategory(OriginVo originVo,MerchantCategoryVo merchantCategoryVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改MerchantCategory");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("删除商户分类信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		Result result = new Result();
		try {
			MerchantCategory merchantCategory = (MerchantCategory)BeanUtil.copyProperties(MerchantCategory.class, merchantCategoryVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantCategory); // 校验bean				
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			Boolean b = merchantCategoryLogic.updateMerchantCategory(merchantCategory);
			if (!b) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改商户分类失败");
			}	
		} catch (FroadServerException e) {
			LogCvt.error("修改商户分类失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改商户分类[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}


	/**
	 * 根据id查询分类
	 * @Title: findMerchantCategoryById 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @return
	 * @return MerchantCategory    返回类型 
	 * @throws
	 */
	@Override
	public MerchantCategoryVo getMerchantCategoryById(String clientId, long id) {
		LogCvt.info("根据id查询MerchantCategory");
		MerchantCategoryVo merchantCategoryVo = new MerchantCategoryVo();
		MerchantCategory merchantCategory = merchantCategoryLogic.findMerchantCategoryById(clientId, id);
		if (null != merchantCategory)
			merchantCategoryVo = (MerchantCategoryVo) BeanUtil.copyProperties(MerchantCategoryVo.class, merchantCategory);
		return merchantCategoryVo;
	}
	

    /**
     * 查询 MerchantCategory
     * @param merchantCategory
     * @return List<MerchantCategoryVo>
     */
	@Override
	public List<MerchantCategoryVo> getMerchantCategory(MerchantCategoryVo merchantCategoryVo) throws TException {
		LogCvt.info("查询MerchantCategory");
		MerchantCategory merchantCategory = (MerchantCategory)BeanUtil.copyProperties(MerchantCategory.class, merchantCategoryVo);

		List<MerchantCategory> merchantCategoryList = merchantCategoryLogic.findMerchantCategory(merchantCategory);
		
		List<MerchantCategoryVo> merchantCategoryVoList = (List<MerchantCategoryVo>)BeanUtil.copyProperties(MerchantCategoryVo.class, merchantCategoryList);
		return merchantCategoryVoList;
	}



    /**
     * 分页查询 MerchantCategory
     * @param merchantCategory
     * @return MerchantCategoryPageVoRes
     */
	@Override
	public MerchantCategoryPageVoRes getMerchantCategoryByPage(PageVo pageVo, MerchantCategoryVo merchantCategoryVo) throws TException {
		LogCvt.info("分页查询MerchantCategory");
		Page<MerchantCategory> page = (Page) BeanUtil.copyProperties(Page.class, pageVo);
		MerchantCategory merchantCategory = (MerchantCategory) BeanUtil.copyProperties(MerchantCategory.class, merchantCategoryVo);

		page = merchantCategoryLogic.findMerchantCategoryByPage(page, merchantCategory);

		MerchantCategoryPageVoRes merchantCategoryPageVoRes = new MerchantCategoryPageVoRes();
		List<MerchantCategoryVo> merchantCategoryVoList = (List<MerchantCategoryVo>) BeanUtil.copyProperties(MerchantCategoryVo.class, page.getResultsContent());

		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
		merchantCategoryPageVoRes.setPage(pageVo);
		merchantCategoryPageVoRes.setMerchantCategoryVoList(merchantCategoryVoList);
		return merchantCategoryPageVoRes;
	}
	
	@Override
	public List<MerchantCategoryVo> getMerchantCategoryByCategoryIdList(String clientId, List<Long> categoryIdList)
			throws TException {
		List<MerchantCategory> merchantCategoryList = merchantCategoryLogic.findMerchantCategoryByCategoryIdList(clientId,categoryIdList);
		
		List<MerchantCategoryVo> merchantCategoryVoList = (List<MerchantCategoryVo>)BeanUtil.copyProperties(MerchantCategoryVo.class, merchantCategoryList);
		return merchantCategoryVoList;
	}
	
	/**
	 * 商户分类信息获取
	 */
	@Override
	public List<MerchantCategoryVo> getMerchantCategoryByH5(MerchantCategoryVo merchantCategoryVo) throws TException {
		
		List<MerchantCategoryVo> merchantCategoryVoList = null;
		
		try {
			LogCvt.info("商户分类信息获取");
			MerchantCategory merchantCategory = (MerchantCategory) BeanUtil.copyProperties(MerchantCategory.class, merchantCategoryVo);

			List<MerchantCategory> merchantCategoryList = merchantCategoryLogic.findMerchantCategoryByH5(merchantCategory);

			merchantCategoryVoList = (List<MerchantCategoryVo>) BeanUtil.copyProperties(MerchantCategoryVo.class, merchantCategoryList);
			
			return merchantCategoryVoList;
		} catch (FroadServerException e) {
			LogCvt.error("商户分类信息获取失败, 原因:" + e.getMessage(), e);
		} catch (Exception e) {
			LogCvt.error("商户分类信息获取[系统异常], 原因:" + e.getMessage(), e);
		}
		return merchantCategoryVoList;
	}
}
