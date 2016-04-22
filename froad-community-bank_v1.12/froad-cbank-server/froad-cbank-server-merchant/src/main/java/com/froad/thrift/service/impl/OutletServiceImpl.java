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
 * @Title: OutletImpl.java
 * @Package com.froad.thrift.service.impl
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.oval.exception.ConstraintsViolatedException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.common.beans.MerchantExportData;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.AccountTypeEnum;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.ModuleID;
import com.froad.enums.OutletDisableStatusEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantAccountLogic;
import com.froad.logic.MerchantCategoryLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.MerchantOutletPhotoLogic;
import com.froad.logic.OutletAuditLogic;
import com.froad.logic.OutletLogic;
import com.froad.logic.impl.MerchantAccountLogicImpl;
import com.froad.logic.impl.MerchantCategoryLogicImpl;
import com.froad.logic.impl.MerchantLogicImpl;
import com.froad.logic.impl.MerchantOutletPhotoLogicImpl;
import com.froad.logic.impl.OutletAuditLogicImpl;
import com.froad.logic.impl.OutletLogicImpl;
import com.froad.monitor.OutletMonitor;
import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.MerchantCategory;
import com.froad.po.MerchantOutletPhoto;
import com.froad.po.Outlet;
import com.froad.po.OutletPrefer;
import com.froad.po.OutletTemp;
import com.froad.po.Result;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.Location;
import com.froad.po.mongo.OutletDetail;
import com.froad.support.Support;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletAddVoRes;
import com.froad.thrift.vo.OutletDetailPageVoRes;
import com.froad.thrift.vo.OutletDetailSimpleInfoPageVoRes;
import com.froad.thrift.vo.OutletDetailSimpleInfoVo;
import com.froad.thrift.vo.OutletDetailSimplePageVoRes;
import com.froad.thrift.vo.OutletDetailSimpleVo;
import com.froad.thrift.vo.OutletDetailVo;
import com.froad.thrift.vo.OutletMongoInfoPageVoRes;
import com.froad.thrift.vo.OutletMongoInfoVo;
import com.froad.thrift.vo.OutletPageVoRes;
import com.froad.thrift.vo.OutletPreferPageVoRes;
import com.froad.thrift.vo.OutletPreferVo;
import com.froad.thrift.vo.OutletTempAddVoRes;
import com.froad.thrift.vo.OutletTempVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.QrCodeRequestVo;
import com.froad.thrift.vo.QrCodeResponseVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.TypeInfoVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.Constants;
import com.froad.util.JSonUtil;
import com.froad.util.PropertiesUtil;
import com.froad.util.SimpleID;
import com.froad.util.ThriftConfig;
import com.froad.util.ValidatorUtil;


/**
 * 
 * <p>@Title: OutletImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class OutletServiceImpl extends BizMonitorBaseService implements OutletService.Iface {
	private static SimpleID simpleID = new SimpleID(ModuleID.outlet);
	
	private OutletLogic outletLogic = new OutletLogicImpl();
	private MerchantLogic merchantLogic = new MerchantLogicImpl();
	private MerchantCategoryLogic merchantCategoryLogic = new MerchantCategoryLogicImpl();
	private OutletAuditLogic outletAuditLogic = new OutletAuditLogicImpl();
	private MerchantOutletPhotoLogic merchantOutletPhotoLogic = new MerchantOutletPhotoLogicImpl();
	public static int querySize = 20000;
	
	public OutletServiceImpl() {}
	public OutletServiceImpl(String name, String version) {
		super(name, version);
	}
	

    /**
     * 增加 Outlet
     * @param outlet
     * @return outletId 门店编号
     */
	@Override
	public OutletAddVoRes addOutlet(OriginVo originVo, OutletVo outletVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加Outlet");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("添加门店信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		OutletAddVoRes resVo = new OutletAddVoRes();
		Result result = new Result();
		try {
			Outlet outlet = (Outlet)BeanUtil.copyProperties(Outlet.class, outletVo);
			
			try {
				ValidatorUtil.getValidator().assertValid(outlet); // 校验bean
			} catch (ConstraintsViolatedException e) {
				throw new FroadServerException(e.getMessage());
			}
			
			if(StringUtils.isBlank(outlet.getMerchantId())) {
				throw new FroadServerException("门店所属商户id不能为空!");
			}
			if(null == outlet.getAreaId()) {
				throw new FroadServerException("门店所属地区id不能为空!");
			}
			if(null == outlet.getOutletStatus()) {
				throw new FroadServerException("是否银行机构对应门店不能为空!");
			}
			if(StringUtils.isBlank(outlet.getOutletName())) {
				throw new FroadServerException("门店简称不能为空!");
			}
			if(StringUtils.isBlank(outlet.getOutletFullname())) {
				throw new FroadServerException("门店全称不能为空!");
			}
			if(StringUtils.isBlank(outlet.getPhone())) {
				throw new FroadServerException("联系电话不能为空!");
			}
			if(outlet.getOutletStatus() == null) {
				throw new FroadServerException("是否银行机构对应的门店不能为空!");
			} else {
				if (!outlet.getOutletStatus()) { // 普通门店
					String lon = outlet.getLongitude();					
					if(!NumberUtils.isNumber(lon) || -180 > Double.parseDouble(lon) || Double.parseDouble(lon) > 180) {
						throw new FroadServerException("经度不合法!");
					}
					String lat = outlet.getLatitude();
					if(!NumberUtils.isNumber(lat) || -90 > Double.parseDouble(lat) || Double.parseDouble(lat) > 90) {
						throw new FroadServerException("纬度不合法!");
					}
				}
			}
			if(outlet.getIsEnable() == null) {
				throw new FroadServerException("是否有效门店不能为空!");
			}
			if(outlet.getDisableStatus() == null) {
				outlet.setDisableStatus(OutletDisableStatusEnum.normal.getCode());
			}
			
			if (!outlet.getOutletStatus()) { // 普通门店
				if(StringUtils.isBlank(outlet.getAuditState())){
					outlet.setAuditState("3");
				}
			}else{
				outlet.setAuditState("1");//如果是银行虚假门店时，直接审核通过
			}
			
			
			outlet.setCreateTime(new Date());
			outlet.setOutletId(getOutletID());
			outlet.setEditAuditState("1");//更新审核状态在新增时该字段默认为1
			
			
			//商户分类
			if (CollectionUtils.isNotEmpty(outletVo.getCategoryInfo())) {
				
				List<MerchantCategory> merchantCategoryList = null;
				List<CategoryInfo> categoryInfoList = new ArrayList<CategoryInfo>();
				
				List<Long> categoryIdList = new ArrayList<Long>();
				for (CategoryInfoVo civ : outletVo.getCategoryInfo()) {
					if (civ != null)
						categoryIdList.add(civ.getCategoryId());
				}
				LogCvt.debug("根据上送的分类id查询分类信息");
				
				merchantCategoryList = merchantCategoryLogic.findMerchantCategoryByCategoryIdList(outlet.getClientId(), categoryIdList);
				for (MerchantCategory mc : merchantCategoryList) {
					CategoryInfo ci = new CategoryInfo();
					ci.setCategoryId(mc.getId());
					ci.setName(mc.getName());
					categoryInfoList.add(ci);
				}
				
				//门店分类
				outlet.setCategoryInfo(categoryInfoList);
			}
			
			//生成门店二维码
			Support support = new Support();
			QrCodeRequestVo req = new QrCodeRequestVo();
			req.setClientId(outlet.getClientId());
			String keyword = MessageFormat.format(ThriftConfig.DOMAIN_NAME, new String[]{outlet.getClientId(),outlet.getOutletId()});
			req.setKeyword(keyword);
			QrCodeResponseVo qrcode = support.getQRCode(req);
			if(qrcode==null || !ResultCode.success.getCode().equals(qrcode.getResultCode())){
				throw new FroadServerException("门店二维码生成异常");
			}else{
				outlet.setQrcodeUrl(qrcode.getUrl());//存入二维码文件相对路径
			}
			
			//优惠状态
			if(outlet.getPreferStatus()==null){
				outlet.setPreferStatus(false);//如果空时，默认为false
			}
			
			String outletId = outletLogic.addOutlet(outlet);

			if(StringUtils.isNotBlank(outletId)) {
				resVo.setOutletId(outletId);
			} else {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加门店失败");
			}
//			resVo.setOutletId(outletLogic.addOutlet(outlet));
			
		} catch (FroadServerException e) {
			LogCvt.error("添加门店失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("添加门店[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		resVo.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return resVo;
	}

	
	
	/** 
	 * 批量增加门店    
	 * @param outletVoList
	 * @return list<Common.ResultVo>    结果集
	 * @throws TException    
	 * @see com.froad.thrift.service.OutletService.Iface#addOutletByBatch(java.util.List)    
	 */	
	@Override
	public List<OutletAddVoRes> addOutletByBatch(OriginVo originVo, List<OutletVo> outletVoList) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("批量添加Outlet");
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("批量添加门店信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		List<OutletAddVoRes> list = new ArrayList<OutletAddVoRes>(); 
		try {
				
			
	//		// 转换参数
	//		List<Outlet> outletList = new ArrayList<Outlet>(); 
	//		Outlet outlet = null;
	//		for( OutletVo outletVo : outletVoList ){
	//			outlet = (Outlet)BeanUtil.copyProperties(Outlet.class, outletVo);
	//			outletList.add(outlet);
	//		}
	//		
	//		// 方法调用
	//		List<Result> resultList = outletLogic.addOutletByBatch(outletList);
	//		
	//		// 转换返回值
	//		List<ResultVo> resultVoList = new ArrayList<ResultVo>(); 
	//		ResultVo resultVo = null;
	//		for( Result result : resultList ){
	//			resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
	//			resultVoList.add(resultVo);
	//		}
	//		
	//		return resultVoList;
			
			for (OutletVo outletVo : outletVoList) {
				list.add(this.addOutlet(null, outletVo));
			}
		} catch (FroadServerException e) {
			LogCvt.error("批量添加门店失败, 原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.failed.getCode());
//			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("批量添加门店[系统异常], 原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.merchant_error.getCode());
//			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return list;
	}


    /**
     * 删除 Outlet
     * @param outlet
     * @return boolean    
     */
	@Override
	public ResultVo deleteOutlet(OriginVo originVo, OutletVo outletVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除Outlet");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("删除门店信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		Result result = new Result();
		try {
			Outlet outlet = (Outlet) BeanUtil.copyProperties(Outlet.class, outletVo);
			
			if(StringUtils.isNotBlank(outlet.getMerchantId())){
				Merchant m = merchantLogic.findMerchantByMerchantId(outlet.getMerchantId());
				if(m.getIsEnable() && MerchantDisableStatusEnum.normal.getCode().equals(m.getDisableStatus())){
					OutletVo o = new OutletVo();
					o.setIsEnable(true);
					o.setOutletId(outlet.getOutletId());
					//如果删除门店的isenable状态为false时，不进行唯一门店检查
					int n = countOutlet(o);
					if(n>0){
						o = new OutletVo();
						o.setIsEnable(true);
						o.setMerchantId(outlet.getMerchantId());
						n = countOutlet(o);
						if(n == 1){
							throw new FroadServerException("删除门店失败，商户必须保证有一家门店!");
						}
					}
				}
			}else{
				throw new FroadServerException("删除门店失败商户ID必须带入!");
			}
			
			if (!outletLogic.deleteOutlet(outlet, OutletDisableStatusEnum.delete)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("门店删除失败");
			}			
		} catch (FroadServerException e) {
			LogCvt.error("删除门店失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除门店[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}


	/**     
	 * 物理删除(添加门店错误时回滚用) Outlet
	 * @param originVo
	 * @param outletId
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.OutletService.Iface#removeOutlet(com.froad.thrift.vo.OriginVo, java.lang.String)    
	 */
	
	@Override
	public ResultVo removeOutlet(OriginVo originVo, String outletId) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("物理删除Outlet");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("物理删除门店信息");
		com.froad.util.LogUtils.addLog(originVo);
		
		Result result = new Result();
		try {
			if(StringUtils.isBlank(outletId)) {
				throw new FroadServerException("门店id不能为空");
			}
			if (!outletLogic.removeOutlet(outletId)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("物理门店删除失败");
			}			
		} catch (FroadServerException e) {
			LogCvt.error("物理删除门店失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("物理删除门店[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	


    /**
     * 修改 Outlet
     * @param outlet
     * @return boolean    
     */
	@Override
	public ResultVo updateOutlet(OriginVo originVo, OutletVo outletVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改Outlet");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("修改门店信息");
		com.froad.util.LogUtils.addLog(originVo);
		
//		LogCvt.debug("修改Outlet,上送的vo:"+JSON.toJSONString(outletVo,true));
		Result result = new Result();
		try {
			Outlet outlet = (Outlet)BeanUtil.copyProperties(Outlet.class, outletVo);
			
			try {

				ValidatorUtil.getValidator().assertValid(outlet); // 校验bean
			} catch (ConstraintsViolatedException e) {
				throw new FroadServerException(e.getMessage());
			}
			
			LogCvt.debug("修改Outlet,转换后的po:"+JSON.toJSONString(outlet,true));
			
			String lon = outlet.getLongitude();					
			if(StringUtils.isNotBlank(lon) && (!NumberUtils.isNumber(lon) || -180 > Double.parseDouble(lon) || Double.parseDouble(lon) > 180)) {
				throw new FroadServerException("经度不合法!");
			}
			String lat = outlet.getLatitude();
			if(StringUtils.isNotBlank(lat) && (!NumberUtils.isNumber(lat) || -90 > Double.parseDouble(lat) || Double.parseDouble(lat) > 90)) {
				throw new FroadServerException("纬度不合法!");
			}
			
			//商户分类
			if (CollectionUtils.isNotEmpty(outletVo.getCategoryInfo())) {
				
				List<MerchantCategory> merchantCategoryList = null;
				List<CategoryInfo> categoryInfoList = new ArrayList<CategoryInfo>();
				
				List<Long> categoryIdList = new ArrayList<Long>();
				for (CategoryInfoVo civ : outletVo.getCategoryInfo()) {
					if (civ != null)
						categoryIdList.add(civ.getCategoryId());
				}
				LogCvt.debug("根据上送的分类id查询分类信息");
				
				merchantCategoryList = merchantCategoryLogic.findMerchantCategoryByCategoryIdList(outlet.getClientId(), categoryIdList);
				for (MerchantCategory mc : merchantCategoryList) {
					CategoryInfo ci = new CategoryInfo();
					ci.setCategoryId(mc.getId());
					ci.setName(mc.getName());
					categoryInfoList.add(ci);
				}
				
				//门店分类
				outlet.setCategoryInfo(categoryInfoList);
			}
			
			
			if (!outletLogic.updateOutlet(outlet)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("门店修改失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("修改门店失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改门店[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}		
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}

	/**
	 * 根据门店id查询单个 Outlet
	 * @param outletId
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.OutletService.Iface#getOutletByOutletId(java.lang.String)
	 */
	@Override
	public OutletVo getOutletByOutletId(String outletId) throws TException {
		
		LogCvt.info("根据outletId查询Outlet");
		OutletVo outletVo = new OutletVo();
		List<CategoryInfoVo> categoryInfoVoList = null;
		Outlet outlet =  outletLogic.findOutletByOutletId(outletId);
		if(null == outlet){
			return outletVo;
		}else{
			
			OutletDetail detail = outletLogic.findOutletDetailByOutletId(outlet.getOutletId());
			
			if(Checker.isNotEmpty(detail)){
				outlet.setCategoryInfo(detail.getCategoryInfo());
			}else{
				LogCvt.error("未能在Mongo找到该门店["+outlet.getId()+"]的记录！");
			}
		}

		return (OutletVo)BeanUtil.copyProperties(OutletVo.class, outlet);
	}

    /**
     * 查询 Outlet
     * @param outlet
     * @return List<OutletVo>
     */
	@Override
	public List<OutletVo> getOutlet(OutletVo outletVo) throws TException {
		LogCvt.info("查询Outlet");
		Outlet outlet = (Outlet)BeanUtil.copyProperties(Outlet.class, outletVo);

		List<Outlet> outletList = outletLogic.findOutlet(outlet);
		
		OutletVo vo = null;
		Merchant merchant = null;
		OutletDetail detail = null;
		List<TypeInfoVo> typeInfoVoList = null;
		List<CategoryInfoVo> categoryInfoVoList = null;
		List<OutletVo> outletVoList = new ArrayList<OutletVo>();
		for (Outlet outlet2 : outletList) {
			vo = (OutletVo)BeanUtil.copyProperties(OutletVo.class, outlet2);
			merchant = merchantLogic.findMerchantByMerchantId(vo.getMerchantId());
			detail = outletLogic.findOutletDetailByOutletId(vo.getOutletId());
			if(Checker.isNotEmpty(detail)){
				typeInfoVoList = (List<TypeInfoVo>)BeanUtil.copyProperties(TypeInfoVo.class, detail.getTypeInfo());
				categoryInfoVoList = (List<CategoryInfoVo>)BeanUtil.copyProperties(CategoryInfoVo.class, detail.getCategoryInfo());
				vo.setTypeInfo(typeInfoVoList);
				vo.setCategoryInfo(categoryInfoVoList);
				vo.setStarLevel(detail.getStarLevel() + "");
			}else{
				LogCvt.error("未能在Mongo找到该门店["+vo.getOutletId()+"]的记录！");
			}
			
			
			vo.setComplaintPhone(merchant.getComplaintPhone());
			outletVoList.add(vo);
		}
		
		return outletVoList;
	}
	
	
	/**
     * 统计 Outlet
     * @param outlet
     * @return int
     */
	@Override
	public int countOutlet(OutletVo outletVo) throws TException {
		LogCvt.info("统计Outlet");
		Outlet outlet = (Outlet)BeanUtil.copyProperties(Outlet.class, outletVo);

		return outletLogic.countOutlet(outlet);
	}
	
	/**
	 * 查询机构对应的虚拟门店信息
	 * @param client_id
	 * @param org_code
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.OutletService.Iface#getBankOutlet(java.lang.String, java.lang.String)
	 */
	@Override
	public OutletVo getBankOutlet(String client_id, String org_code) throws TException {
		LogCvt.info("查询机构对应的虚拟 Outlet信息");
//		Merchant merchant = new Merchant();
//		merchant.setClientId(client_id);
//		merchant.setOrgCode(org_code);
//		merchant.setMerchantStatus(true); // 只查询银行虚拟商户
//		merchant.setIsEnable(true); // 只查可用的
//		merchant = merchantLogic.findOneMerchant(merchant);
//		if (null != merchant) {
//			Outlet outlet = new Outlet();
//			outlet.setMerchantId(merchant.getMerchantId()); // 取对应的虚拟商户id
//			outlet.setOutletStatus(true); // 只查询银行虚拟商户
//			outlet.setIsEnable(true); // 只查可用的
//
//			List<Outlet> list = outletLogic.findOutlet(outlet); 
//			
//			if (CollectionUtils.isNotEmpty(list)) {
//				return (OutletVo) BeanUtil.copyProperties(OutletVo.class, list.get(0));
//			}
//		}
		OutletVo outletVo = new OutletVo();
		Outlet outlet = outletLogic.findBankOutlet(client_id, org_code);
		if (null != outlet) {
			outletVo = (OutletVo) BeanUtil.copyProperties(OutletVo.class, outlet);
		}
		return outletVo;
	}
	
	/**   
	 * 查询提货网点(预售用到)  
	 * @param org_code
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.OutletService.Iface#getSubBankOutlet(java.lang.String)    
	 */	
	@Override
	public List<OutletVo> getSubBankOutlet(String client_id, String org_code) throws TException {
		LogCvt.info("查询提货网点(预售用到) Outlet");
		List<OutletVo> outletVoList = new ArrayList<OutletVo>();
		
		/*
		 只查询一级
		Merchant merchant = new Merchant();
		merchant.setClientId(client_id);
		merchant.setOrgCode(org_code);
		merchant.setIsEnable(true);
		List<Merchant> merchantList = merchantLogic.findMerchant(merchant);
		List<String> merchantIdList = new ArrayList<String>();
		
		if(CollectionUtils.isEmpty(merchantList)){
			return outletVoList;
		}
		for (Merchant merchant2 : merchantList) {
			merchantIdList.add(merchant2.getMerchantId());
		}
		
		List<Outlet> outletList = outletLogic.findBankOutlet(merchantIdList);
		*/
		
		
		/*
		List<String> subOrgCode = null;
		try {
			subOrgCode = new Support().getAllSubOrgCodes(client_id, org_code); // 查询org_code的下属机构查询orgCode以及下属机构
		} catch (Exception e) {
			LogCvt.error("查询orgCode以及下属机构所有的预售网点失败", e);
			return outletVoList;
		} 
//		if(CollectionUtils.isEmpty(subOrgCode)) {
//			LogCvt.error("查询orgCode无下属机构");
//			return outletVoList;
//		}
		List<String> orgCodeList = new ArrayList<String>();
		for (String o : subOrgCode) {// 过滤当前机构
			if(!o.equals(org_code)) {
				orgCodeList.add(o);
			}
		}
		if(CollectionUtils.isEmpty(orgCodeList)) {
			LogCvt.info("传入的机构没有下属机构");
			return outletVoList;
		}
//		List<Merchant> merchantList = merchantLogic.findBankMerchant(client_id, subOrgCode);
		List<Merchant> merchantList = merchantLogic.findBankMerchant(client_id, orgCodeList);
		
		List<String> merchantIdList = new ArrayList<String>();
		
		if(CollectionUtils.isEmpty(merchantList)) {
			LogCvt.info("传入的机构没有下属门店机构");
			return outletVoList;
		}
		for (Merchant merchant2 : merchantList) {
			merchantIdList.add(merchant2.getMerchantId());
		}
		List<Outlet> outletList = outletLogic.findBankOutlet(merchantIdList);
		 */
		
		List<Outlet> outletList = outletLogic.findSubBankOutlet(client_id, org_code);		
		outletVoList = (List<OutletVo>)BeanUtil.copyProperties(OutletVo.class, outletList);
		return outletVoList;
	}



    /**
     * 分页查询 Outlet
     * @param outlet
     * @return OutletPageVoRes
     */
	@Override
	public OutletPageVoRes getOutletByPage(PageVo pageVo, OutletVo outletVo) throws TException {
		LogCvt.info("分页查询Outlet");
		Page<Outlet> page = (Page) BeanUtil.copyProperties(Page.class, pageVo);
		Outlet outlet = (Outlet) BeanUtil.copyProperties(Outlet.class, outletVo);

//		String outletName = outlet.getOutletName();
//		if(StringUtils.isNotBlank(outletName) && outletName.indexOf("%")>-1) {
//			outlet.setOutletName(outletName.replaceAll("%", "\\\\%"));
//		}
//		String outletFullname = outlet.getOutletFullname();
//		if(StringUtils.isNotBlank(outletFullname) && outletFullname.indexOf("%")>-1) {
//			outlet.setOutletFullname(outletFullname.replaceAll("%", "\\\\%"));
//		}
		page = outletLogic.findOutletByPage(page, outlet, null);

		OutletPageVoRes outletPageVoRes = new OutletPageVoRes();
		List<OutletVo> outletVoList = (List<OutletVo>)BeanUtil.copyProperties(OutletVo.class, page.getResultsContent());
		
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		outletPageVoRes.setPage(pageVo);
		outletPageVoRes.setOutletVoList(outletVoList);
		return outletPageVoRes;
	}

	
	/**   
	 * 分页查询门店  
	 * @param page
	 * @param outletDetailVo
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.OutletService.Iface#getOutletDetailByPage(com.froad.thrift.vo.PageVo, com.froad.thrift.vo.OutletDetailVo)    
	 */	
	@Override
	public OutletDetailPageVoRes getOutletDetailByPage(PageVo page, OutletDetailVo outletDetailVo) throws TException {
		OutletDetailPageVoRes outletDetailPageVoRes = new OutletDetailPageVoRes();
		try {
			OutletDetail outletDetail = (OutletDetail) BeanUtil.copyProperties(OutletDetail.class, outletDetailVo);
//			Page<OutletDetail> pagepo = (Page<OutletDetail>) BeanUtil.copyProperties(Page.class, page);
			MongoPage mongoPage = (MongoPage) BeanUtil.copyProperties(MongoPage.class, page);
//			mongoPage.setPageNumber(page.getPageNumber());
//			mongoPage.setPageSize(page.getPageSize());
			
			mongoPage = outletLogic.findOutletDetailByPage(mongoPage, outletDetail);
			
			page = (PageVo) BeanUtil.copyProperties(PageVo.class, mongoPage);
			
//			page.setPageCount(mongoPage.getPageCount());
//			page.setTotalCount(mongoPage.getTotalCount());
//			page.setPageCount(mongoPage.getPageCount());
			
//			pagepo.setResultsContent((List<OutletDetail>) mongoPage.getItems());
			
			
			outletDetailPageVoRes.setPage(page);
			outletDetailPageVoRes.setOutletDetailVoList((List)  BeanUtil.copyProperties(OutletDetailVo.class, mongoPage.getItems()));
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
		} 
		
		return outletDetailPageVoRes;
	}

	
	/**     
	 * cha
	 * @param page
	 * @param outletVo
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.OutletService.Iface#getHottestOutletByPage(com.froad.thrift.vo.PageVo, com.froad.thrift.vo.OutletVo)    
	 */
	
//	public OutletPageVoRes getHottestOutletByPage(PageVo pageVo, OutletVo outletVo) throws TException {
//		LogCvt.info("分页查询Outlet");
//		Page<Outlet> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
//		Outlet outlet = (Outlet)BeanUtil.copyProperties(Outlet.class, outletVo);
//
//		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
//		orderBy.put("store_count", "DESC");
//		
//		page = outletLogic.findOutletByPage(page, outlet, orderBy);
//
//		OutletPageVoRes outletPageVoRes = new OutletPageVoRes();
//		List<OutletVo> outletVoList = (List<OutletVo>) BeanUtil.copyProperties(OutletVo.class, page.getResultsContent());
//		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
//		outletPageVoRes.setPage(pageVo);
//		outletPageVoRes.setOutletVoList(outletVoList);
//		return outletPageVoRes;
//	}
	
	/**    
	 * 个人H5搜索热门门店 
	 * @param page
	 * @param outletDetailVo
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.OutletService.Iface#getHottestOutletDetailByPage(com.froad.thrift.vo.PageVo, com.froad.thrift.vo.OutletDetailVo)    
	 */
	
	@Override
	public OutletDetailSimpleInfoPageVoRes getHottestOutletDetailByPage(PageVo page, OutletDetailVo outletDetailVo) throws TException {
		OutletMonitor.sendHottestOutletServiceInvokeNumber(); // 发送业务监控
		
		OutletDetailSimpleInfoPageVoRes outletDetailSimpleInfoPageVoRes = new OutletDetailSimpleInfoPageVoRes();
		long startTime = System.currentTimeMillis();
		try {
			
			OutletDetail outletDetail = (OutletDetail) BeanUtil.copyProperties(OutletDetail.class, outletDetailVo);
//			if(StringUtils.isEmpty(outletDetail.getClientId())) {
//				throw new FroadServerException("客户端id不能为空!");
//			}
			Location location = outletDetail.getLocation();
			if(null != location) {
				Double lon = location.getLongitude();
				if(null == lon) {
					throw new FroadServerException("经度不能为空!");
				}
				if(-180 > (lon) || (lon) > 180) {
					throw new FroadServerException("经度不合法!");
				}
				Double lat = location.getLatitude();
				if(null == lat) {
					throw new FroadServerException("纬度不能为空!");
				}
				if(-90 > (lat) || (lat) > 90) {
					throw new FroadServerException("纬度不合法!");
				}
			}
			
//			Page<OutletDetail> pagepo = (Page<OutletDetail>) BeanUtil.copyProperties(Page.class, page);
			MongoPage mongoPage = (MongoPage) BeanUtil.copyProperties(MongoPage.class, page);
//			mongoPage.setPageNumber(page.getPageNumber());
//			mongoPage.setPageSize(page.getPageSize());
			
			Sort sort = new Sort("store_count", OrderBy.DESC);
			mongoPage.setSort(sort);
			
			mongoPage = outletLogic.findHottestOutletDetailByPage(mongoPage, outletDetail);
			
			page = (PageVo) BeanUtil.copyProperties(PageVo.class, mongoPage);
			
//			page.setPageCount(mongoPage.getPageCount());
//			page.setTotalCount(mongoPage.getTotalCount());
//			page.setPageCount(mongoPage.getPageCount());
			
			outletDetailSimpleInfoPageVoRes.setPage(page);
			outletDetailSimpleInfoPageVoRes.setOutletDetailSimpleInfoVoList((List)  BeanUtil.copyProperties(OutletDetailSimpleInfoVo.class, mongoPage.getItems()));
			
			long endTime = System.currentTimeMillis();
			long time = endTime - startTime;
			OutletMonitor.sendHottestOutletServiceConsumingTime(time); // 发送业务监控
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error("附近搜索最热门店出错,原因:"+e.getMessage(),e);
		} 
		
		return outletDetailSimpleInfoPageVoRes;
	}
	
	/**     
	 * 附近搜索门店
	 * @param page
	 * @param outletDetailVo
	 * @param distance
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.OutletService.Iface#getNearbyOutlet(com.froad.thrift.vo.PageVo, com.froad.thrift.vo.OutletDetailVo, double)    
	 */	
//	@Deprecated
//	public OutletDetailPageVoRes getNearbyOutlet(PageVo page, OutletDetailVo outletDetailVo, double distance) throws TException {
//		OutletDetailPageVoRes outletDetailPageVoRes = new OutletDetailPageVoRes();
//		try {
//			OutletDetail outletDetail = (OutletDetail) BeanUtil.copyProperties(OutletDetail.class, outletDetailVo);
////			Page<OutletDetail> pagepo = (Page<OutletDetail>) BeanUtil.copyProperties(Page.class, page);
//			MongoPage mongoPage = new MongoPage();
//			mongoPage.setCurrentPage(page.getPageNumber());
//			mongoPage.setPageSize(page.getPageSize());
//			
//			mongoPage = outletLogic.findNearbyOutlet(mongoPage, outletDetail, distance);
//			
//			page.setPageCount(mongoPage.getTotalPage());
//			page.setTotalCount(mongoPage.getTotalNumber());
//			page.setPageCount(mongoPage.getTotalPage());
//			
//			outletDetailPageVoRes.setPage(page);
//			outletDetailPageVoRes.setOutletDetailVoList((List)  BeanUtil.copyProperties(OutletDetailVo.class, mongoPage.getItems()));
//		} catch (Exception e) {
//			e.printStackTrace();
//			LogCvt.error("附近搜索门店出错,原因:"+e.getMessage(),e);
//		} 
//		
//		return outletDetailPageVoRes;
//	}
	
	/**
	 *   个人H5，特惠商户及搜索
	  * @Title: getNearbyOutlet
	  * @Description: TODO
	  * @author: share 2015年6月11日
	  * @modify: share 2015年6月11日
	  * @param @param page
	  * @param @param outletDetailVo
	  * @param @param distance
	  * @param @param orderBy
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.OutletService.Iface#getNearbyOutlet(com.froad.thrift.vo.PageVo, com.froad.thrift.vo.OutletDetailVo, double, int)
	 */
	@Override
	public OutletDetailSimpleInfoPageVoRes getNearbyOutlet(PageVo page, OutletDetailVo outletDetailVo,double distance, int orderBy) throws TException {
		OutletMonitor.sendNearOutletServiceInvokeNumber(); // 发送业务监控
		
		OutletDetailSimpleInfoPageVoRes outletDetailSimpleInfoPageVoRes  = new OutletDetailSimpleInfoPageVoRes();
		long startTime = System.currentTimeMillis();
		try {
			
			if(null == outletDetailVo) {
				throw new FroadServerException("附近搜索门店查询条件不能为空");
			}
			OutletDetail outletDetail = (OutletDetail) BeanUtil.copyProperties(OutletDetail.class, outletDetailVo);
			Location location = outletDetail.getLocation();
			// 经纬度信息不为空时检查
			if(null != location) {
				Double lon = location.getLongitude();
				if(null == lon) {
					throw new FroadServerException("经度不能为空!");
				}
				if(-180 > (lon) || (lon) > 180) {
					throw new FroadServerException("经度不合法!");
				}
				Double lat = location.getLatitude();
				if(null == lat) {
					throw new FroadServerException("纬度不能为空!");
				}
				if(-90 > (lat) || (lat) > 90) {
					throw new FroadServerException("纬度不合法!");
				}
			}
			
			MongoPage mongoPage = (MongoPage) BeanUtil.copyProperties(MongoPage.class, page);
			
			mongoPage = outletLogic.findNearbyOutlet(mongoPage, outletDetail,distance,orderBy);
			
			page = (PageVo) BeanUtil.copyProperties(PageVo.class, mongoPage);
			
			outletDetailSimpleInfoPageVoRes.setPage(page);
			outletDetailSimpleInfoPageVoRes.setOutletDetailSimpleInfoVoList((List)BeanUtil.copyProperties(OutletDetailSimpleInfoVo.class, mongoPage.getItems()));
			
			long endTime = System.currentTimeMillis();
			long time = endTime - startTime;
			
			OutletMonitor.sendNearOutletServiceConsumingTime(time); // 发送业务监控
			
		} catch (Exception e) {
			LogCvt.error("附近搜索门店出错,原因:" + e.getMessage(), e);
		} 
		return outletDetailSimpleInfoPageVoRes;
	}
	
	
	/**
	 * 生成门店ID
	 * @Title: getOutletID 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return
	 * @return String    返回类型 
	 * @throws
	 */
	private String getOutletID() {
		return simpleID.nextId();
	}



	/**
	 * 门店详情
	 * @author zxh
	 * @version 1.0
	 * @see: TODO
	 * @return
	 * @return OutletDetailVo    返回类型 
	 * @throws
	 */
	@Override
	public OutletDetailVo getOutletDetail(String outletId) throws TException {
		OutletDetailVo vo = new OutletDetailVo();
		try {
			OutletDetail outletDetail = outletLogic.findOutletDetailByOutletId(outletId);
			if(outletDetail != null){
				vo = (OutletDetailVo)BeanUtil.copyProperties(OutletDetailVo.class, outletDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error("查询门店详情出错,原因:"+e.getMessage(),e);
		} 
		return vo;
	}
	
	/**     
	 * 根据门店id集合查询详情
	 * @param outletIdList
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.OutletService.Iface#getOutletDetailbyOutletIdList(java.util.List)    
	 */	
	@Override
	public List<OutletDetailVo> getOutletDetailbyOutletIdList(List<String> outletIdList) throws TException {
		LogCvt.info("根据门店id集合查询详情");
		List<OutletDetailVo> voList = new ArrayList<OutletDetailVo>();
		try {
			List<OutletDetail> list = outletLogic.findOutletDetailbyOutletIdList(outletIdList);
			voList =  (List<OutletDetailVo>) BeanUtil.copyProperties(OutletDetailVo.class, list);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error("根据门店id集合查询详情,原因:"+e.getMessage(),e);
		} 
		return voList;
	}
	
	/**
	 * 根据经纬度和商户ID查询门店信息列表和相对距离
	  * @Title: getOutletMongoInfoVoVoByPage
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-6-7
	  * @modify: zhangxiaohua 2015-6-7
	  * @param @param page
	  * @param @param longitude
	  * @param @param latitude
	  * @param @param merchantId
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.OutletService.Iface#getOutletMongoInfoVoVoByPage(com.froad.thrift.vo.PageVo, double, double, java.lang.String)
	 */
	@Override
	public OutletMongoInfoPageVoRes getOutletMongoInfoVoByPage(PageVo page,double longitude, double latitude, String merchantId)throws TException {
		OutletMongoInfoPageVoRes outletDetailPageVoRes = new OutletMongoInfoPageVoRes();
		try {
			MongoPage mongoPage = (MongoPage) BeanUtil.copyProperties(MongoPage.class, page);
			
			mongoPage = outletLogic.findOutletMongoInfoByPage(mongoPage,longitude,latitude, merchantId);
			
			page = (PageVo) BeanUtil.copyProperties(PageVo.class, mongoPage);
			
			outletDetailPageVoRes.setPage(page);
			outletDetailPageVoRes.setUtletMongoInfoVoList((List)  BeanUtil.copyProperties(OutletMongoInfoVo.class, mongoPage.getItems()));
			
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
		} 
		
		return outletDetailPageVoRes;
	}
	@Override
	public OutletDetailSimplePageVoRes getNearbyOutletByPage(PageVo page,
			OutletDetailVo outletDetailVo, double distance,int skip) throws TException {
		OutletMonitor.sendNearOutletServiceInvokeNumber(); // 发送业务监控
		
		OutletDetailSimplePageVoRes outletDetailSimplePageVoRes  = new OutletDetailSimplePageVoRes();
		long startTime = System.currentTimeMillis();
		try {
			
			if(null == outletDetailVo) {
				throw new FroadServerException("附近搜索门店查询条件不能为空");
			}
			OutletDetail outletDetail = (OutletDetail) BeanUtil.copyProperties(OutletDetail.class, outletDetailVo);
			Location location = outletDetail.getLocation();
			// 经纬度信息不为空时检查
			if(null != location) {
				Double lon = location.getLongitude();
				if(null == lon) {
					throw new FroadServerException("经度不能为空!");
				}
				if(-180 > (lon) || (lon) > 180) {
					throw new FroadServerException("经度不合法!");
				}
				Double lat = location.getLatitude();
				if(null == lat) {
					throw new FroadServerException("纬度不能为空!");
				}
				if(-90 > (lat) || (lat) > 90) {
					throw new FroadServerException("纬度不合法!");
				}
			}
			
			MongoPage mongoPage = (MongoPage) BeanUtil.copyProperties(MongoPage.class, page);
			
			mongoPage = outletLogic.findNearbyOutletByPage(mongoPage, outletDetail,distance,skip);
			
			page = (PageVo) BeanUtil.copyProperties(PageVo.class, mongoPage);
			
			outletDetailSimplePageVoRes.setPage(page);
			outletDetailSimplePageVoRes.setOutletDetailSimpleVoList((List)BeanUtil.copyProperties(OutletDetailSimpleVo.class, mongoPage.getItems()));
			
			long endTime = System.currentTimeMillis();
			long time = endTime - startTime;
			
			OutletMonitor.sendNearOutletServiceConsumingTime(time); // 发送业务监控
			
		} catch (Exception e) {
			LogCvt.error("附近搜索门店出错,原因:" + e.getMessage(), e);
		} 
		return outletDetailSimplePageVoRes;
	}
	@Override
	public ResultVo syncOutletInfo(OutletVo outletVo,String isSynSucc, String synType)
			throws TException {
		LogCvt.info("门店白名单同步操作-开始,商户Id:"+outletVo.getMerchantId()+",客户端Id:"+outletVo.getClientId()+",门店ID:"+outletVo.getMerchantId()+",isSynSucc:"+isSynSucc+",synType:"+synType);
		long st = System.currentTimeMillis();
		Result result = new Result();
		Outlet outlet = (Outlet)BeanUtil.copyProperties(Outlet.class, outletVo);
		try {
			 result = outletLogic.syncOultetInfo(outlet,isSynSucc, Integer.valueOf(synType));
		} catch (FroadServerException e) {
			LogCvt.error("白名单同步请求操作失败,原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg()+","+e.getMessage());
		} catch (Exception e) {
			LogCvt.error("白名单同步请求操作[系统异常],商户ID："+outlet.getMerchantId()+",门店ID"+outlet.getOutletId()+",原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg()+","+e.getMessage());
		}	
		LogCvt.info("门店白名单同步操作-结束！总耗时（" + (System.currentTimeMillis() - st) + "）ms");
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	
   /**
    * 门店管理点击提交审核触发事件实现接口(录入审核：outlet表录入审核状态=非审核通过状态),(编辑审核：outlet表录入审核状态为审核通过).
    * @param outletId  门店Id
    * @param clientId  客户端Id
    * @param auditId   审核流水号
    * @return boolean    
    */
	@Override
	public ResultVo commitAuditOutlet(String outletId,String auditId) throws TException {
		Result result = new Result();
//		boolean isSucc = false;
//		try{
//			//1:查询门店状态
//			Outlet outlet = outletLogic.findOutletByOutletId(outletId);
//			if(outlet!=null){
//				//audit_status审核状态为1 时，则为编辑审核，  audit_status审核状态非1时为录入审核
//				if(outlet.getAuditState().equals(ProductAuditState.passAudit.getCode())){
//					if(outlet.getEditAuditState().equals(ProductAuditState.passAudit.getCode())){
//						throw new FroadServerException("提交审核失败，原因为审核状态为审核通过！");
//					}
//					//执行编辑审核提交相关代码 
//					OutletTemp outletTemp = outletAuditLogic.findOutletTempByOutletId(outletId);
//					if(null != outletTemp){
//						OutletTemp targetOutletTemp = new OutletTemp();
//						targetOutletTemp.setOutletId(outletTemp.getOutletId());
//						targetOutletTemp.setAuditId(auditId);//更新审核流水号
//						isSucc = outletAuditLogic.uddateOutletTemp(outletTemp);//执行更新
//						
//						Outlet  targetOutlet = new Outlet();
//						targetOutlet.setOutletId(outlet.getOutletId());
//						targetOutlet.setEditAuditState(ProductAuditState.noAudit.getCode());//更新审核状态为待审核
//						isSucc = outletLogic.updateOutlet(targetOutlet);//执行更新
//					}
//				}else{
//					//录入审核操作
//					if(!outlet.getAuditState().equals(ProductAuditState.noCommit.getCode())){
//						throw new FroadServerException("提交审核失败，录入审核状态异常！");
//					}
//					Outlet  targetOutlet = new Outlet();
//					targetOutlet.setOutletId(outlet.getOutletId());
//					targetOutlet.setAuditState(ProductAuditState.noAudit.getCode());//更新审核状态为待审核
//					isSucc = outletLogic.updateOutlet(targetOutlet);//执行更新
//				}
//				if(isSucc){
//					result.setResultCode(ResultCode.success.getCode());
//					result.setResultDesc(ResultCode.success.getMsg());				
//				}else{
//					result.setResultCode(ResultCode.failed.getCode());
//					result.setResultDesc("门店Id="+outletId+",提交审核失败！");
//				}				
//			}else{
//				result.setResultCode(ResultCode.failed.getCode());
//				result.setResultDesc("提示审核失败，未获取到门店信息！");
//			}
//			
//			
//		}catch (FroadServerException e) {
//			LogCvt.error("提示审核失败, 原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.failed.getCode());
//			result.setResultDesc(e.getMessage());
//		} catch (Exception e) {
//			LogCvt.error("提示审核时[系统异常], 原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.merchant_error.getCode());
//			result.setResultDesc(ResultCode.merchant_error.getMsg());
//		}
		
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	/**
    * 门店录入审核通过后，点击修改操作接口.
    * 1、门店录入审核通过后，商户平台用户点击修改门店操作，
    * @param outletTempVo 门店临时vo
    * @return
    * 
    */
	@Override
	public OutletTempAddVoRes saveOutletTempEditOutlet(OriginVo originVo,OutletTempVo outletTempVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加OutletTemp");
		//添加操作日志记录
		if(null != originVo) {
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("添加门店临时信息");
			com.froad.util.LogUtils.addLog(originVo);
		}
		OutletTempAddVoRes resVo = new OutletTempAddVoRes();
		Result result = new Result();
		OutletTemp targetTemp = null;
		boolean isSuccess = false;
		String outletId  = "";
		try{
			OutletTemp outletTemp = (OutletTemp)BeanUtil.copyProperties(OutletTemp.class, outletTempVo);
			try {
				ValidatorUtil.getValidator().assertValid(outletTemp); // 校验bean
			} catch (ConstraintsViolatedException e) {
				throw new FroadServerException(e.getMessage());
			}
			if(StringUtils.isBlank(outletTemp.getMerchantId())) {
				throw new FroadServerException("门店所属商户id不能为空!");
			}
			if(null == outletTemp.getAreaId()) {
				throw new FroadServerException("门店所属地区id不能为空!");
			}
			if(StringUtils.isBlank(outletTemp.getOutletName())) {
				throw new FroadServerException("门店简称不能为空!");
			}
			if(StringUtils.isBlank(outletTemp.getOutletFullName())) {
				throw new FroadServerException("门店全称不能为空!");
			}
			outletLogic.checkSensitiveWordByObject(outletTemp);//敏感词过滤
			targetTemp = outletAuditLogic.findOutletTempByOutletId(outletTemp.getOutletId(),"0");
			if(null != targetTemp){
				isSuccess = outletAuditLogic.removeOutletTemp(targetTemp);			
			}
			Outlet outlet = outletLogic.findOutletByOutletId(outletTemp.getOutletId());
			if (null == outlet) {
				throw new FroadServerException("门店id="	+ outletTemp.getOutletId() + "查询门店不存在!");
			}
			outletTemp.setCreateTime(new Date());
			outletTemp.setAuditId("0");
			String prrimeval = CombinationPrimeval(outlet);
			outletTemp.setPrimeval(prrimeval);
			
			outletId = outletAuditLogic.addOutletTemp(outletTemp);
			LogCvt.info("编辑门店获取原值="+prrimeval);
			
			// 更改outlet表编辑审核状态
			Outlet targetOutlet = new Outlet();
			targetOutlet.setOutletId(outletId);
			targetOutlet.setMerchantId(outlet.getMerchantId());
			targetOutlet.setEditAuditState(ProductAuditState.noCommit.getCode());
			isSuccess = outletLogic.updateOutlet(targetOutlet);

			if(StringUtils.isNotBlank(outletId) && isSuccess) {
				resVo.setOutletId(outletId);
				result.setResultCode(ResultCode.success.getCode());
				result.setResultDesc(ResultCode.success.getMsg());
			} else {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加门店临时失败");
			}
			
		}catch (FroadServerException e) {
			LogCvt.error("添加门店临时失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("添加门店临时[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		resVo.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return resVo;
	}

	/**
    * 查看门店修改字段信息.
    * @param outletId 门店Id
    * @return OutletTempVo
    */
	@Override
	public OutletTempVo getOutletModifyInfos(String outletId) throws TException {
		LogCvt.info("查询门店修改字段OutletTemp");
		OutletTempVo resultVo = new OutletTempVo();
		try{
			OutletTemp targetTemp = outletAuditLogic.findOutletTempByOutletId(outletId,null);
	        resultVo = (OutletTempVo)BeanUtil.copyProperties(OutletTempVo.class, targetTemp);
		}catch (FroadServerException e) {
			LogCvt.error("查询门店修改字段失败, 原因:" + e.getMessage(), e);
		} catch (Exception e) {
			LogCvt.error("查询门店修改字段[系统异常], 原因:" + e.getMessage(), e);
	    }
		return resultVo;
	 }
	
	/**
	 * 银行管理平台-审核箱中查询门店修改字段接口.
	 * @param outletId
	 * @param auditId
	 * @return
	 * @throws TException
	 */
	@Override
     public OutletTempVo getOutletModifyInfoByAuditBox(String outletId,String auditId)throws TException{
    	 LogCvt.info("银行管理平台-审核箱中查询门店修改字段OutletTemp");
 		OutletTempVo resultVo = new OutletTempVo();
 		try{
 			OutletTemp targetTemp = outletAuditLogic.findOutletTempByOutletId(outletId,auditId);
 			if(null == targetTemp){
 				 LogCvt.info("门店Id="+outletId+"审核流水号"+auditId+"不存在,获取容错模式门店修改信息！");
 				targetTemp = outletAuditLogic.findOutletTempByOutletId(outletId, null);
 			}	
 	        resultVo = (OutletTempVo)BeanUtil.copyProperties(OutletTempVo.class, targetTemp);
 		}catch (FroadServerException e) {
 			LogCvt.error("银行管理平台-审核箱中查询门店修改字段失败, 原因:" + e.getMessage(), e);
 		} catch (Exception e) {
 			LogCvt.error("银行管理平台-审核箱中查询门店修改字段[系统异常], 原因:" + e.getMessage(), e);
 	    }
 		return resultVo;
     }
	
	
	/**
	 * 本地生成门店原始值JSON.
	 * 
	 * @param outlet
	 * @return
	 */
	public String CombinationPrimeval(Outlet outlet) {
		HashMap<String, Object> primevalMap = new HashMap<String, Object>();

		// 获取原商户信息，存于cb_outlet_temp.primeval字段中，格式为json
		if (Checker.isEmpty(outlet)) {
			throw new FroadServerException("门店信息不存在[outletId:"+ outlet.getOutletId() + "]!");
		}
		primevalMap.put("outletName", outlet.getOutletName() == null ? "": outlet.getOutletName());
		primevalMap.put("outletFullname",outlet.getOutletFullname() == null ? "" : outlet.getOutletFullname());
		primevalMap.put("phone",				outlet.getPhone() == null ? "" : outlet.getPhone());
		primevalMap.put("businessHours", outlet.getBusinessHours() == null ? ""	: outlet.getBusinessHours());

		primevalMap.put("areaId",outlet.getAreaId() == null ? "" : outlet.getAreaId());
		primevalMap.put("address",outlet.getAddress() == null ? "" : outlet.getAddress());
		primevalMap.put("contactName", outlet.getContactName() == null ? ""	: outlet.getContactName());
		primevalMap.put("contactPhone", outlet.getContactPhone() == null ? "": outlet.getContactPhone());
		primevalMap.put("description", outlet.getDescription() == null ? "": outlet.getDescription());
		primevalMap.put("preferDetails", outlet.getPreferDetails() == null ? "": outlet.getPreferDetails());
		primevalMap.put("discount",	outlet.getDiscount() == null ? "" : outlet.getDiscount());
		primevalMap.put("discountCode", outlet.getDiscountCode() == null ? "": outlet.getDiscountCode());
		primevalMap.put("discountRate", outlet.getDiscountRate() == null ? "": outlet.getDiscountRate());

		// 查商户帐号信息
		MerchantAccountLogic accountLogic = new MerchantAccountLogicImpl();
		MerchantAccount findAccount = new MerchantAccount();
		findAccount.setMerchantId(outlet.getMerchantId());
		findAccount.setOutletId(outlet.getOutletId());
		findAccount.setClientId(outlet.getClientId());
		findAccount.setAcctType(AccountTypeEnum.SQ.getCode());
		findAccount.setIsDelete(false);
		List<MerchantAccount> merchantAccountList = accountLogic.findMerchantAccount(findAccount);
		MerchantAccount  account = null;		
		if(null != merchantAccountList && merchantAccountList.size()>0){
			account = merchantAccountList.get(0);
		}
		if (Checker.isNotEmpty(account)) {
			primevalMap.put("acctName", account.getAcctName()==null?"":account.getAcctName());
			primevalMap.put("acctNumber", account.getAcctNumber()==null?"":account.getAcctNumber());
		} else {
			primevalMap.put("acctName", "");
			primevalMap.put("acctNumber", "");
		}

		MerchantOutletPhoto merchantOutletPhoto = new MerchantOutletPhoto();
		merchantOutletPhoto.setOutletId(outlet.getOutletId());
		merchantOutletPhoto.setMerchantId(outlet.getMerchantId());
		List<MerchantOutletPhoto> merchantOutletPhotoList = merchantOutletPhotoLogic.findMerchantOutletPhoto(merchantOutletPhoto);
		
		if (null != merchantOutletPhotoList	&& merchantOutletPhotoList.size() > 0) {
			primevalMap.put("merchantOutletPhotoList", JSON.toJSONString(merchantOutletPhotoList));
			LogCvt.info("原相册信息="+JSON.toJSONString(merchantOutletPhotoList));
		}else{
			primevalMap.put("merchantOutletPhotoList","");
		}
		
		//增加门店分类信息
		StringBuffer outletCategoryId = new StringBuffer();
		StringBuffer outletCategoryName = new StringBuffer();
		OutletDetail detail = outletLogic.findOutletDetailByOutletId(outlet.getOutletId());
		if(Checker.isNotEmpty(detail)){
			List<CategoryInfo> cateList = detail.getCategoryInfo();
			if(cateList!=null && cateList.size()>0){
				for (CategoryInfo categoryInfo : cateList) {
					outletCategoryId.append(categoryInfo.getCategoryId()).append(",");
					outletCategoryName.append(categoryInfo.getName()).append(",");
				}
				if(outletCategoryId!=null && outletCategoryId.length()>0){
					outletCategoryId.deleteCharAt(outletCategoryId.length()-1);
					outletCategoryName.deleteCharAt(outletCategoryName.length()-1);
					primevalMap.put("outletCategoryId", outletCategoryId);
					primevalMap.put("outletCategoryName", outletCategoryName);
				}
			}
		}else{
			LogCvt.error("未能在Mongo找到该门店["+outlet.getOutletId()+"]的记录！");
		}
		
		
		return JSonUtil.toJSonString(primevalMap);
	}
	
	
	/**
	 * 门店列表查询生成报表，ftp上传生成的报表文件至ftp服务器，返回url.
	 * 
	 * @param merchantVo
	 * @author: ganhongwei 2016-1-5
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.MerchantService.Iface#getMerchantExport(com.froad.thrift.vo.MerchantVo
	 *      merchantVo)
	 */
	@Override
	public ExportResultRes getOutletExport(OutletPreferVo outletPreferVo) throws TException {
		LogCvt.info("门店列表报表导出导出-开始");
		long st = System.currentTimeMillis();
		List<OutletPrefer> outletPreferList = null;
		Result result = new Result();
		Page<OutletPrefer> pageOutletPrefer = null;
		ResultVo resultVo = null;
		String url = "";
		
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(0);
		pageVo.setPageSize(querySize);
		try {
			
			// 1:查询列表
			pageOutletPrefer = convertExportOutletByPage(pageVo, outletPreferVo);
			if(null != pageOutletPrefer){
				outletPreferList = (List<OutletPrefer>) BeanUtil.copyProperties(OutletPrefer.class, pageOutletPrefer.getResultsContent());
				MerchantExportData merchantExportData = outletLogic.exportOutletManage(outletPreferList);
				ExcelWriter excelWriter = new ExcelWriter(querySize);
				// 导出第一页
				excelWriter.write(merchantExportData.getHeader(),merchantExportData.getData(),merchantExportData.getSheetName(),merchantExportData.getExcelFileName());
				MerchantExportData merchantExportTempData = null;
				if (pageVo.getPageNumber() > 0) {
					for (int i = 1; i <= pageVo.getPageNumber()	&& pageVo.getPageNumber() < pageVo.getPageCount(); i++) {
						// 设置页数
						pageVo.setPageNumber(i + 1);
						pageOutletPrefer = convertExportOutletByPage(pageVo, outletPreferVo);
						outletPreferList = (List<OutletPrefer>) BeanUtil.copyProperties(OutletPrefer.class, pageOutletPrefer.getResultsContent());
						// 转换成报表数据
						merchantExportTempData = outletLogic.exportOutletManage(outletPreferList);
						// 导出
						excelWriter.write(merchantExportTempData.getHeader(),merchantExportTempData.getData(),merchantExportTempData.getSheetName(),merchantExportTempData.getExcelFileName());
					}
				}
				url = excelWriter.getUrl();		
			}		
		} catch (FroadServerException e) {
			LogCvt.error("门店列表查询生成报表失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("门店列表查询生成报表[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		resultVo = (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		// 2:生产excel并上传服务器，返回服务器路径
		ExportResultRes  exportResultRes = new ExportResultRes();
		exportResultRes.setResultVo(resultVo);
		exportResultRes.setUrl(url);
		LogCvt.info("[银行管理平台]-门店列表报表-结束！总耗时（" + (System.currentTimeMillis() - st) + "）ms， excel下载链接：" + url);
		return exportResultRes;
	}
	
	
	/**
	 * 门店列表报表导出功能获取门店分页内部方法.
	 * 
	 * @param pageVo
	 * @param outletVo
	 * @return
	 */
	private Page<OutletPrefer> convertExportOutletByPage(PageVo pageVo,OutletPreferVo outletPreferVo){
		Page<OutletPrefer> page = (Page<OutletPrefer>)BeanUtil.copyProperties(Page.class, pageVo);
		OutletPrefer outletPrefer = (OutletPrefer) BeanUtil.copyProperties(OutletPrefer.class,outletPreferVo);
		Page<OutletPrefer> pageOutlet = outletLogic.findOutletPreferByPage(page, outletPrefer);
		if(null != pageOutlet){
			convertPage(page,pageVo);			
		}
       return pageOutlet;		
	}
	
	/**
	 * 门店导出报表追加分页内部方法.
	 * 
	 * @param page
	 * @param vo
	 */
	 private void convertPage(Page page, PageVo vo) {
	    vo.setPageCount(page.getPageCount());
	    vo.setPageNumber(page.getPageNumber());
	    vo.setPageSize(page.getPageSize());
	    vo.setTotalCount(page.getTotalCount());
	    vo.setLastPageNumber(page.getPageNumber());
	    vo.setHasNext(page.getPageCount() > page.getPageNumber());
	 }
	 
	 /**
	  * 银行PC-门店惠付分页查询接口.
	  */
	@Override
	public OutletPreferPageVoRes getOutletPreferByPage(PageVo pageVo,OutletPreferVo outletPreferVo) throws TException {
		Page<OutletPrefer> page = (Page<OutletPrefer>)BeanUtil.copyProperties(Page.class, pageVo);
		OutletPrefer outletPrefer = (OutletPrefer) BeanUtil.copyProperties(OutletPrefer.class,outletPreferVo);
		Page<OutletPrefer> pageOutlet = outletLogic.findOutletPreferByPage(page, outletPrefer);
		
		OutletPreferPageVoRes OutletPreferPageVoRes = new OutletPreferPageVoRes();
		List<OutletPreferVo> outletPreferVoList = (List<OutletPreferVo>)BeanUtil.copyProperties(OutletPreferVo.class, pageOutlet.getResultsContent());
		
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		OutletPreferPageVoRes.setPage(pageVo);
		OutletPreferPageVoRes.setOutletPreferVoList(outletPreferVoList);
		
		return OutletPreferPageVoRes;		
	}	
}