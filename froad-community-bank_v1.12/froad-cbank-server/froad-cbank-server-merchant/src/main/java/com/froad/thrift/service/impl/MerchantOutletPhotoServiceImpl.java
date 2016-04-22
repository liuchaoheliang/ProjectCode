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
 * @Title: MerchantOutletPhotoImpl.java
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
import com.froad.logic.MerchantOutletPhotoLogic;
import com.froad.logic.impl.MerchantOutletPhotoLogicImpl;
import com.froad.po.MerchantOutletPhoto;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantOutletPhotoService;
import com.froad.thrift.vo.MerchantOutletPhotoAddVoRes;
import com.froad.thrift.vo.MerchantOutletPhotoPageVoRes;
import com.froad.thrift.vo.MerchantOutletPhotoVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.ValidatorUtil;


/**
 * 
 * <p>@Title: MerchantOutletPhotoImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantOutletPhotoServiceImpl extends BizMonitorBaseService implements MerchantOutletPhotoService.Iface {
	private MerchantOutletPhotoLogic merchantOutletPhotoLogic = new MerchantOutletPhotoLogicImpl();
	public MerchantOutletPhotoServiceImpl() {}
	public MerchantOutletPhotoServiceImpl(String name, String version) {
		super(name, version);
	}

    /**
     * 增加 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return long    主键ID
     */
	@Override
	public MerchantOutletPhotoAddVoRes addMerchantOutletPhoto(OriginVo originVo,MerchantOutletPhotoVo merchantOutletPhotoVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加MerchantOutletPhoto");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("添加门店相册信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		
		MerchantOutletPhotoAddVoRes resVo = new MerchantOutletPhotoAddVoRes();
		Result result = new Result();
		try {
			MerchantOutletPhoto merchantOutletPhoto = (MerchantOutletPhoto)BeanUtil.copyProperties(MerchantOutletPhoto.class, merchantOutletPhotoVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantOutletPhoto); // 校验bean				
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			if(merchantOutletPhoto.getMerchantId() == null 
					|| "".equals(merchantOutletPhoto.getMerchantId())){
				throw new FroadServerException("添加MerchantOutletPhoto失败,原因:MerchantId不能为空!");
			}
			
			if(merchantOutletPhoto.getOutletId() == null 
					|| "".equals(merchantOutletPhoto.getOutletId())){
				throw new FroadServerException("添加MerchantOutletPhoto失败,原因:OutletId不能为空!");
			}
			
			if(merchantOutletPhoto.getIsDefault() == null){
				throw new FroadServerException("添加MerchantOutletPhoto失败,原因:IsDefault不能为空!");
			}
			
			Long l = merchantOutletPhotoLogic.addMerchantOutletPhoto(merchantOutletPhoto);
			
			if(l != null && l > 0){
				resVo.setId(l);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加商户相册失败");
			}
		
		} catch (FroadServerException e) {
			LogCvt.error("添加商户相册失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("添加商户相册[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		
		resVo.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return resVo;
	}

	/**
     * 批量增加 MerchantOutletPhoto
     * @param list<merchantOutletPhoto>
     * @return list<Common.ResultVo>    结果集
     * 
     * @param merchantOutletPhotoVoList
     */
	@SuppressWarnings("unchecked")
	@Override
    public List<com.froad.thrift.vo.ResultVo> addMerchantOutletPhotoByBatch(OriginVo originVo,List<com.froad.thrift.vo.MerchantOutletPhotoVo> merchantOutletPhotoVoList) throws TException{
		List<ResultVo> resultVoList = new ArrayList<ResultVo>(); 
		LogCvt.info("批量添加MerchantOutletPhoto");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("批量添加门店相册信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		try{
			// 转换参数
			List<MerchantOutletPhoto> merchantOutletPhotoList = new ArrayList<MerchantOutletPhoto>();
			MerchantOutletPhoto merchantOutletPhoto = null;
			for( MerchantOutletPhotoVo merchantOutletPhotoVo : merchantOutletPhotoVoList ){
				merchantOutletPhoto = (MerchantOutletPhoto)BeanUtil.copyProperties(MerchantOutletPhoto.class, merchantOutletPhotoVo);
				
				merchantOutletPhotoList.add(merchantOutletPhoto);
			} 
			
			// 方法调用
			List<Result> resultList = merchantOutletPhotoLogic.addMerchantOutletPhotoByBatch(merchantOutletPhotoList);
			
			// 转换返回值
			resultVoList = (List<ResultVo>)BeanUtil.copyProperties(ResultVo.class, resultList);
	    } catch (FroadServerException e) {
			LogCvt.error("批量添加商户相册失败, 原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.failed.getCode());
//			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("批量添加商户相册[系统异常], 原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.merchant_error.getCode());
//			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return resultVoList;
    }
	
	/**     
	 * 保存 MerchantOutletPhoto,以上送的集合为准
	 * @param merchantOutletPhotoVoList
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantOutletPhotoService.Iface#saveMerchantOutletPhoto(java.util.List)    
	 */	
	@Override
	public ResultVo saveMerchantOutletPhoto(OriginVo originVo, List<MerchantOutletPhotoVo> merchantOutletPhotoVoList) throws TException {
		LogCvt.info("批量保存MerchantOutletPhoto");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("批量保存门店相册信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		Result result = new Result();
		try {
			List<MerchantOutletPhoto> merchantOutletPhotoList = (List<MerchantOutletPhoto>) BeanUtil.copyProperties(MerchantOutletPhoto.class, merchantOutletPhotoVoList);
			for (MerchantOutletPhoto merchantOutletPhoto : merchantOutletPhotoList) {
				try {
					ValidatorUtil.getValidator().assertValid(merchantOutletPhoto); // 校验bean				
				} catch (ConstraintsViolatedException e) { // 校验不通过
					throw new FroadServerException(e.getMessage());
				}
			}
			
			if(!merchantOutletPhotoLogic.saveMerchantOutletPhoto(merchantOutletPhotoList)){
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("保存门店相册失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("保存门店相册失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("保存门店相册[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}

    /**
     * 删除 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return boolean    
     */
	@Override
	public ResultVo deleteMerchantOutletPhoto(OriginVo originVo,MerchantOutletPhotoVo merchantOutletPhotoVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除MerchantOutletPhoto");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("删除门店相册信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		Result result = new Result();
		try {
			MerchantOutletPhoto merchantOutletPhoto = (MerchantOutletPhoto) BeanUtil.copyProperties(MerchantOutletPhoto.class, merchantOutletPhotoVo);
			Boolean b = merchantOutletPhotoLogic.deleteMerchantOutletPhoto(merchantOutletPhoto);
			if (!b) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除商户相册失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("删除门店相册失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除门店相册[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 修改 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return boolean    
     */
	@Override
	public ResultVo updateMerchantOutletPhoto(OriginVo originVo,MerchantOutletPhotoVo merchantOutletPhotoVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改MerchantOutletPhoto");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("修改门店相册信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		Result result = new Result();
		try {				
			MerchantOutletPhoto merchantOutletPhoto = (MerchantOutletPhoto)BeanUtil.copyProperties(MerchantOutletPhoto.class, merchantOutletPhotoVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(merchantOutletPhoto); // 校验bean				
			} catch (ConstraintsViolatedException e) { // 校验不通过
				throw new FroadServerException(e.getMessage());
			}
			
			Boolean b = merchantOutletPhotoLogic.updateMerchantOutletPhoto(merchantOutletPhoto);
			if (!b) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改商户相册失败");
			}	
		} catch (FroadServerException e) {
			LogCvt.error("修改门店相册失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改门店相册[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 查询 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return List<MerchantOutletPhotoVo>
     */
	@Override
	public List<MerchantOutletPhotoVo> getMerchantOutletPhoto(MerchantOutletPhotoVo merchantOutletPhotoVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询MerchantOutletPhoto");
		MerchantOutletPhoto merchantOutletPhoto = (MerchantOutletPhoto)BeanUtil.copyProperties(MerchantOutletPhoto.class, merchantOutletPhotoVo);

		List<MerchantOutletPhoto> merchantOutletPhotoList = merchantOutletPhotoLogic.findMerchantOutletPhoto(merchantOutletPhoto);
		List<MerchantOutletPhotoVo> merchantOutletPhotoVoList = new ArrayList<MerchantOutletPhotoVo>();
		for (MerchantOutletPhoto po : merchantOutletPhotoList) {
			MerchantOutletPhotoVo vo = (MerchantOutletPhotoVo)BeanUtil.copyProperties(MerchantOutletPhotoVo.class, po);
			merchantOutletPhotoVoList.add(vo);
		}
		return merchantOutletPhotoVoList;
	}



    /**
     * 分页查询 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return MerchantOutletPhotoPageVoRes
     */
	@Override
	public MerchantOutletPhotoPageVoRes getMerchantOutletPhotoByPage(PageVo pageVo, MerchantOutletPhotoVo merchantOutletPhotoVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询MerchantOutletPhoto");
		Page<MerchantOutletPhoto> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		MerchantOutletPhoto merchantOutletPhoto = (MerchantOutletPhoto)BeanUtil.copyProperties(MerchantOutletPhoto.class, merchantOutletPhotoVo);

		page = merchantOutletPhotoLogic.findMerchantOutletPhotoByPage(page, merchantOutletPhoto);

		MerchantOutletPhotoPageVoRes merchantOutletPhotoPageVoRes = new MerchantOutletPhotoPageVoRes();
		List<MerchantOutletPhotoVo> merchantOutletPhotoVoList = new ArrayList<MerchantOutletPhotoVo>();
		for (MerchantOutletPhoto po : page.getResultsContent()) {
			MerchantOutletPhotoVo vo = (MerchantOutletPhotoVo)BeanUtil.copyProperties(MerchantOutletPhotoVo.class, po);
			merchantOutletPhotoVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		merchantOutletPhotoPageVoRes.setPage(pageVo);
		merchantOutletPhotoPageVoRes.setMerchantOutletPhotoVoList(merchantOutletPhotoVoList);
		return merchantOutletPhotoPageVoRes;
	}
	
	


}
