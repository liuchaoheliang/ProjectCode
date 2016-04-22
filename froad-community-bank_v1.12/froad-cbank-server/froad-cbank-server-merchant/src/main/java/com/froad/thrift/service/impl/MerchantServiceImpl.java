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
 * @Title: MerchantImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.oval.exception.ConstraintsViolatedException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.common.beans.MerchantExportData;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.ModuleID;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantCategoryLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.MerchantTypeLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.MerchantCategoryLogicImpl;
import com.froad.logic.impl.MerchantLogicImpl;
import com.froad.logic.impl.MerchantTypeLogicImpl;
import com.froad.po.Merchant;
import com.froad.po.MerchantCategory;
import com.froad.po.MerchantType;
import com.froad.po.Org;
import com.froad.po.Result;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.TypeInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.MerchantAddVoRes;
import com.froad.thrift.vo.MerchantDetailPageVoRes;
import com.froad.thrift.vo.MerchantDetailVo;
import com.froad.thrift.vo.MerchantPageVoRes;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.MerchantVoReq;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.TypeInfoVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.SimpleID;
import com.froad.util.ValidatorUtil;



/**
 * 
 * <p>@Title: MerchantImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantServiceImpl extends BizMonitorBaseService implements MerchantService.Iface {
	public static int querySize = 20000;
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	private static SimpleID simpleID = new SimpleID(ModuleID.merchant);
	
	private MerchantLogic merchantLogic = new MerchantLogicImpl();
	private MerchantCategoryLogic merchantCategoryLogic = new MerchantCategoryLogicImpl();
	private MerchantTypeLogic merchantTypeLogic = new MerchantTypeLogicImpl();
	
	public MerchantServiceImpl() {}
	public MerchantServiceImpl(String name, String version) {
		super(name, version);
	}
	
    /**
     * 增加 Merchant
     * @param merchant
     * @return merchantId 商户编号
     */
	@Override
	public MerchantAddVoRes addMerchant(OriginVo originVo, MerchantVoReq merchantVoReq) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加Merchant");
		MerchantAddVoRes resVo  = new MerchantAddVoRes();
		Result result = new Result();
		try {
			//添加操作日志记录
			if(null != originVo) {
				if(StringUtils.isBlank(originVo.getDescription()))
					originVo.setDescription("添加商户信息");
				com.froad.util.LogUtils.addLog(originVo);
			}
			
		
			MerchantVo merchantVo = merchantVoReq.getMerchantVo();
			List<CategoryInfoVo> categoryInfoVoList = merchantVoReq.getCategoryInfoVoList();
			List<TypeInfoVo> typeInfoVoList = merchantVoReq.getTypeInfoVoList();
			Merchant merchant = (Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo);
			
			
			try {
				ValidatorUtil.getValidator().assertValid(merchant); // 校验bean
			} catch (ConstraintsViolatedException e) {
				throw new FroadServerException(e.getMessage());
			}
			
			if(merchant.getClientId() == null) {
				throw new FroadServerException("客户端id不能为空!");
			}
			if(StringUtils.isBlank(merchant.getOrgCode())) {
				throw new FroadServerException("被发展组织机构代码不能为空!");
			}
			if(StringUtils.isBlank(merchant.getMerchantName())) {
				throw new FroadServerException("商户名不能为空!");
			}
			if(StringUtils.isBlank(merchant.getPhone())){
				throw new FroadServerException("联系人电话不能为空!");
			}
			if(StringUtils.isBlank(merchant.getContactPhone())){
				throw new FroadServerException("联系人手机不能为空!");
			}
			if(StringUtils.isBlank(merchant.getMerchantFullname())) {
				merchant.setMerchantFullname(merchant.getMerchantName());
			}
			if(merchant.getMerchantStatus() == null) {
				throw new FroadServerException("是否银行机构对应的商户不能为空!");
			}
			if(merchant.getIsEnable() == null) {
				throw new FroadServerException("是否有效商户不能为空!");
			}
			if(merchant.getDisableStatus() == null) {
				merchant.setDisableStatus(MerchantDisableStatusEnum.normal.getCode());
			}
			if(merchant.getContractStaff() == null) {
				throw new FroadServerException("签约人员不能为空!");
			}
			
			if(merchant.getContractBegintime() == null || 
					merchant.getContractEndtime() == null) {
				
				Date begDate = DateUtil.getNow();
    	    	Date endDate = DateUtil.skipDateTime(begDate, 365);
    	    	merchant.setContractBegintime(begDate);
    	    	merchant.setContractEndtime(endDate);
    	    	
			}
			if(!merchant.getMerchantStatus()) { // 不为银行商户才判断分类和类型是否为空
				
				//不为银行虚似商户时，对外包字段进行检查
				if(merchant.getIsOutsource() == null) {
					throw new FroadServerException("是否外包不能为空!");
				}
				if(merchant.getIsOutsource()){
					if(merchant.getCompanyId()==null){
						throw new FroadServerException("外包公司名称不能为空!");
					}
				}
				
				if(CollectionUtils.isEmpty(categoryInfoVoList)) {
					throw new FroadServerException("添加普通商户时, 商户分类不能为空!");
				}
				if(CollectionUtils.isEmpty(typeInfoVoList)) {
					throw new FroadServerException("添加普通商户时, 商户类型不能为空!");
				}
				if(Checker.isEmpty(merchant.getLicense())) {
					throw new FroadServerException("营业执照号不能为空!");
				}
				//营业执照号唯一性检查
				checkLicense(merchant.getLicense(),merchant.getMerchantStatus());
			}
			
			/**起始机构、终审机构、待审核机构    不存储审核机构信息，以审核系统的为准**/
			merchant.setAuditStartOrgCode("-1");
			merchant.setAuditEndOrgCode("-1");
			merchant.setAuditOrgCode("-1");

			List<MerchantCategory> merchantCategoryList = null;
			List<CategoryInfo> categoryInfoList = new ArrayList<CategoryInfo>();
			if (CollectionUtils.isNotEmpty(categoryInfoVoList)) {
				List<Long> categoryIdList = new ArrayList<Long>();
				for (CategoryInfoVo ci : categoryInfoVoList) {
					if (ci != null)
						categoryIdList.add(ci.getCategoryId());
				}
				LogCvt.debug("根据上送的分类id查询分类信息");
				merchantCategoryList = merchantCategoryLogic.findMerchantCategoryByCategoryIdList(merchant.getClientId(), categoryIdList);
				for (MerchantCategory mc : merchantCategoryList) {
					CategoryInfo ci = new CategoryInfo();
					ci.setCategoryId(mc.getId());
					ci.setName(mc.getName());
					categoryInfoList.add(ci);
				}
			}
	
			List<MerchantType> merchantTypeList = null;
			List<TypeInfo> typeInfoList = new ArrayList<TypeInfo>();
			if (CollectionUtils.isNotEmpty(typeInfoVoList)) {
				List<Long> typeIdList = new ArrayList<Long>();
				for (TypeInfoVo ti : typeInfoVoList) {
					if (ti != null)
						typeIdList.add(ti.getMerchantTypeId());
				}
				LogCvt.debug("根据上送的类型id查询类型信息");
				merchantTypeList = merchantTypeLogic.findMerchantTypeByMerchantTypeIdList(originVo.getClientId(),typeIdList);
				for (MerchantType mc : merchantTypeList) {
					TypeInfo ci = new TypeInfo();
					ci.setMerchantTypeId(mc.getId());
					ci.setTypeName(mc.getTypeName());
					ci.setType(mc.getType());
					typeInfoList.add(ci);
				}
			}
			
			merchant.setMerchantId(getMerchantID()); // 生成商户ID
			merchant.setEditAuditState(ProductAuditState.passAudit.getCode());//编辑审核商户在新增时该字段默认为1
			String merchantId = merchantLogic.addMerchant(merchant, categoryInfoList, typeInfoList);
			if(StringUtils.isNotBlank(merchantId)) {
				resVo.setMerchantId(merchantId);
			} else {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加商户失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("添加商户失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("添加商户[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		resVo.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return resVo;
	}

	
	/**
	 * 检查营业执照号唯一
	  * @Title: checkLicense
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-6-16
	  * @modify: zhangxiaohua 2015-6-16
	  * @param @param license
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public void checkLicense(String license,boolean status){
		if(!status){
			//有效商户营业执照判断
			Merchant m = new Merchant();
			m.setLicense(license);
			m.setIsEnable(true);
			int n = merchantLogic.countMerchant(m);
			if(n > 0){
				throw new FroadServerException("添加的商户营业执照已存在!");
			}
			//新增待审核商户营业执照判断
			m.setIsEnable(false);
			m.setAuditState(ProductAuditState.noAudit.getCode());
			int n1 = merchantLogic.countMerchant(m);
			if(n1 > 0){
				throw new FroadServerException("添加的商户营业执照已存在!");
			}
		}
	}
	

	
	/**   
	 * 批量增加商户  
	 * @param merchantVoList
	 * @return List<com.froad.thrift.vo.ResultVo> 结果集
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#addMerchantByBatch(java.util.List)    
	 */
	
	@Override
	public List<MerchantAddVoRes> addMerchantByBatch(OriginVo originVo, List<MerchantVoReq> merchantVoReqList) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("批量添加Merchant");
		List<MerchantAddVoRes> result = new ArrayList<MerchantAddVoRes>();
		try {
				
			
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("批量添加商户信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			
			// 转换参数
	//		List<Merchant> merchantList = new ArrayList<Merchant>();
			
	//		List<String> resultVoList = new ArrayList<String>(); 
			
			for( MerchantVoReq merchantVoReq : merchantVoReqList ){
				
	//			MerchantVo merchantVo = merchantVoReq.getMerchantVo();
	//			List<CategoryInfoVo> categoryInfoVoList = merchantVoReq.getCategoryInfoVoList();
	//			List<TypeInfoVo> typeInfoVoList = merchantVoReq.getTypeInfoVoList();
	//			Merchant merchant = (Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo);
	//			List<CategoryInfo> categoryInfoList = (List<CategoryInfo>)BeanUtil.copyProperties(CategoryInfo.class, categoryInfoVoList);
	//			List<TypeInfo> typeInfoList = (List<TypeInfo>)BeanUtil.copyProperties(TypeInfo.class, typeInfoVoList);
	//			
	//			merchant = (Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo);
	//			merchant.setMerchantId(getMerchantID()); // 生成ID
	//			
	//			String result = merchantLogic.addMerchant(merchant, categoryInfoList, typeInfoList);
	//			
	//			resultVoList.add(result);
				
				
				result.add(this.addMerchant(null, merchantVoReq));
			}
			
			// 方法调用
					
			// 转换返回值
		
		} catch (FroadServerException e) {
			LogCvt.error("批量添加商户失败, 原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.failed.getCode());
//			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("批量添加商户[系统异常], 原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.merchant_error.getCode());
//			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		return result;
	}
	
	/**     
	 * 物理删除Merchant
	 * @param originVo
	 * @param merchantId
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#removeMerchant(com.froad.thrift.vo.OriginVo, java.lang.String)    
	 */
	
	@Override
	public ResultVo removeMerchant(OriginVo originVo, String merchantId) throws TException {

		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("物理删除Merchant");
		Result result = new Result();
		try {		
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("物理删除商户信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			if(StringUtils.isBlank(merchantId)) {
				throw new FroadServerException("商户id不能为空!");
			}
//			Boolean result = merchantLogic.deleteMerchant(merchant);
			if(!merchantLogic.removeMerchant(merchantId)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("商户删除失败!");
			}
//			return null == result ? false : result;
		} catch (FroadServerException e) {
			LogCvt.error("物理删除商户失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("物理删除商户[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		} 
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	
	}
	
    /**
     * 删除 Merchant
     * @param merchant
     * @return boolean    
     */
	@Override
	public ResultVo deleteMerchant(OriginVo originVo, MerchantVo merchantVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除Merchant");
		Result result = new Result();
		try {		
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("删除商户信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			Merchant merchant = (Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo);
			if(StringUtils.isBlank(merchant.getMerchantId())) {
				throw new FroadServerException("商户id不能为空!");
			}
			
			//增加解约时间及解约人验证 add by trimray 2015/10/20
			if(merchant.getOperateTime()==null){
				throw new FroadServerException("操作日期不能为空!");
			}
			if(StringUtils.isBlank(merchant.getOperateUser())){
				throw new FroadServerException("操作人不能为空!");
			}
			
//			Boolean result = merchantLogic.deleteMerchant(merchant);
			if(!merchantLogic.deleteMerchant(merchant, MerchantDisableStatusEnum.unregistered)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("商户删除失败!");
			}
//			return null == result ? false : result;
		} catch (FroadServerException e) {
			LogCvt.error("删除商户失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除商户[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		} 
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 修改 Merchant
     * @param merchant
     * @return boolean    
     */
	@Override
	public ResultVo updateMerchant(OriginVo originVo, MerchantVoReq merchantVoReq) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改Merchant");
		Result result = new Result();
		try {		
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("修改商户信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			MerchantVo merchantVo = merchantVoReq.getMerchantVo();
			List<CategoryInfoVo> categoryInfoVoList = merchantVoReq.getCategoryInfoVoList();
			List<TypeInfoVo> typeInfoVoList = merchantVoReq.getTypeInfoVoList();
			
			Merchant merchant = (Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo);

			try {
					ValidatorUtil.getValidator().assertValid(merchant); // 校验bean
				} catch (ConstraintsViolatedException e) {
					throw new FroadServerException(e.getMessage());
				}
			
			if(StringUtils.isBlank(merchant.getMerchantId())) {
				throw new FroadServerException("商户id不能为空!");
			}
			if(StringUtils.isEmpty(merchant.getAuditStartOrgCode())) {
				throw new FroadServerException("初始审核机构不能为空!");
			}
			if(StringUtils.isEmpty(merchant.getAuditEndOrgCode())) {
				throw new FroadServerException("最终审核机构不能为空!");
			}
			if(StringUtils.isBlank(merchant.getAuditOrgCode())) {
				throw new FroadServerException("待审核机构不能为空!");
			}
			if(merchant.getIsOutsource() == null) {
				throw new FroadServerException("是否外包不能为空!");
			}
			if(merchant.getIsOutsource()){
				if(merchant.getCompanyId()==null){
					throw new FroadServerException("外包公司名称不能为空!");
				}
			}
			
			List<MerchantCategory> merchantCategoryList = null;
			List<CategoryInfo> categoryInfoList = new ArrayList<CategoryInfo>();
			if (CollectionUtils.isNotEmpty(categoryInfoVoList)) {
				List<Long> categoryIdList = new ArrayList<Long>();
				for (CategoryInfoVo ci : categoryInfoVoList) {
					if (ci != null)
						categoryIdList.add(ci.getCategoryId());
				}
				LogCvt.debug("根据上送的分类id查询分类信息");
				merchantCategoryList = merchantCategoryLogic.findMerchantCategoryByCategoryIdList(merchant.getClientId(), categoryIdList);
				for (MerchantCategory mc : merchantCategoryList) {
					CategoryInfo ci = new CategoryInfo();
					ci.setCategoryId(mc.getId());
					ci.setName(mc.getName());
					categoryInfoList.add(ci);
				}
			}
	
			List<MerchantType> merchantTypeList = null;
			List<TypeInfo> typeInfoList = new ArrayList<TypeInfo>();
			if (CollectionUtils.isNotEmpty(typeInfoVoList)) {
				List<Long> typeIdList = new ArrayList<Long>();
				for (TypeInfoVo ti : typeInfoVoList) {
					if (ti != null)
						typeIdList.add(ti.getMerchantTypeId());
				}
				LogCvt.debug("根据上送的类型id查询类型信息");
				merchantTypeList = merchantTypeLogic.findMerchantTypeByMerchantTypeIdList(merchant.getClientId(),typeIdList);
				for (MerchantType mc : merchantTypeList) {
					TypeInfo ci = new TypeInfo();
					ci.setMerchantTypeId(mc.getId());
					ci.setTypeName(mc.getTypeName());
					ci.setType(mc.getType());
					typeInfoList.add(ci);
				}
			}
			
			if (!merchantLogic.updateMerchant(merchant, categoryInfoList, typeInfoList)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("商户修改失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("修改商户失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改商户[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		} 
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}


	/**     
	 * 禁用指定机构下的所有商户()
	 * @param client_id
	 * @param org_code
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#disableMerchant(java.lang.String)    
	 */	
	@Override
	public ResultVo disableMerchant(OriginVo originVo, String client_id, String org_code) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据客户端id和银行机构码禁用商户和门店");
		Result result = new Result();
		try {
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("禁用商户信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			// return merchantLogic.disableMerchant(client_id, org_code);
			if (!merchantLogic.disableMerchant(client_id, org_code)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("禁用商户失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("禁用商户失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("禁用商户[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		} 
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	/**
	 * 禁用指定商户id()
	 * @param originVo
	 * @param merchantVo
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#disableMerchantByMerchantId(com.froad.thrift.vo.OriginVo, java.lang.String)
	 * modify by trimray 2015/10/20 */
	@Override
	public ResultVo disableMerchantByMerchantId(OriginVo originVo, MerchantVo merchantVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据商户id禁用商户和门店");
		Result result = new Result();
		try {
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("禁用商户信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			Merchant merchant = (Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo);
			
			if(StringUtils.isBlank(merchant.getMerchantId())) {
				throw new FroadServerException("商户id不能为空!");
			}
			
			if(merchant.getOperateTime()==null){
				throw new FroadServerException("操作日期不能为空!");
			}
			if(StringUtils.isBlank(merchant.getOperateUser())){
				throw new FroadServerException("操作人不能为空!");
			}

			if (!merchantLogic.deleteMerchant(merchant, MerchantDisableStatusEnum.disable)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("禁用商户失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("禁用商户失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("禁用商户[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		} 
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	

	/**   
	 * 启用商户  
	 * @param originVo
	 * @param MerchantVo
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#enableMerchant(com.froad.thrift.vo.OriginVo, java.lang.String, java.lang.String)    
	 */	
	@Override
	public ResultVo enableMerchant(OriginVo originVo, MerchantVo merchantVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据商户id启用商户");
		Result result = new Result();
		try {
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("启用商户信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			Merchant merchant = (Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo);
			
			// return merchantLogic.disableMerchant(client_id, org_code);
			if(StringUtils.isBlank(merchant.getClientId())) {
				throw new FroadServerException("客户端id不能为空!");
			}
			/*
			List<String> subOrgCode = null;
			if(StringUtils.isNotBlank(merchant.getOrgCode())) {
				try {
					subOrgCode = new Support().getAllSubOrgCodes(merchant.getClientId(), merchant.getOrgCode()); // 查询org_code的下属机构查询orgCode以及下属机构
				} catch (Exception e) {
					LogCvt.error("查询orgCode以及下属机构所有的预售网点失败", e);
//					return result;
				} 
			}
			*/
			if (!merchantLogic.enableMerchant(merchant)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("启用商户失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("启用商户失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("启用商户[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		} 
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	/**    
	 * 启用商户 
	 * @param originVo
	 * @param merchantId
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#enableMerchantByMerchantId(com.froad.thrift.vo.OriginVo, java.lang.String)    
	 */
	
	@Override
	public ResultVo enableMerchantByMerchantId(OriginVo originVo, String merchantId) throws TException {
		
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据商户id启用商户和门店");
		Result result = new Result();
		try {
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("启用商户信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			// return merchantLogic.disableMerchant(client_id, org_code);
			if(StringUtils.isBlank(merchantId)) {
				throw new FroadServerException("商户id不能为空!");
			}
			Merchant merchant = new Merchant();
			merchant.setMerchantId(merchantId);
			if (!merchantLogic.enableMerchant(merchantId)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("启用商户失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("启用商户失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("启用商户[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		} 
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	
	
	
	/**     
	 * 续约商户
	 * @param merchantId
	 * @param contractEndtime
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#extensionMerchant(java.lang.String, long)    
	 */	
	@Override
	public ResultVo extensionMerchant(OriginVo originVo, String merchantId, long contractEndtime) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据商户id续约商户");
		Result result = new Result();
		try {
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("续约商户信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			
			if(contractEndtime< System.currentTimeMillis()) {
				LogCvt.error("续约商户失败, 原因:签约到期时间不能少于当前时间");
				throw new FroadServerException("签约到期时间不能少于当前时间");
			}
//			return merchantLogic.extensionMerchant(merchantId, contractEndtime);
			if (!merchantLogic.extensionMerchant(merchantId, contractEndtime)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("续约商户失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("续约商户失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("续约商户[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		} 
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}

	
	/**    
	 * 查询单个Merchant 
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#getOneMerchant(MerchantVo merchantVo)    
	 */	
	@Override
	public MerchantVo getOneMerchant(MerchantVo merchantVo) throws TException {
		LogCvt.info("查询单个Merchant");
//		MerchantVo merchantVo= new MerchantVo();
		Merchant merchant = new Merchant();
		merchant = merchantLogic.findOneMerchant((Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo));

		merchantVo = (MerchantVo)BeanUtil.copyProperties(MerchantVo.class, merchant);
		return merchantVo;
	}
	
    /**
     * 查询 Merchant
     * @param merchant
     * @return List<MerchantVo>
     */
	@Override
	public List<MerchantVo> getMerchant(MerchantVo merchantVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询Merchant");
		Merchant merchant = (Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo);

//		String merchantName = merchant.getMerchantName();
//		if(StringUtils.isNotBlank(merchantName) && merchantName.indexOf("%")>-1) {
//			merchant.setMerchantName(merchantName.replaceAll("%", "\\\\%"));
//		}
//		String merchantFullName = merchant.getMerchantFullname();
//		if(StringUtils.isNotBlank(merchantFullName) && merchantFullName.indexOf("%")>-1) {
//			merchant.setMerchantFullname(merchantFullName.replaceAll("%", "\\\\%"));
//		}
		List<Merchant> merchantList = merchantLogic.findMerchant(merchant);
		List<MerchantVo> merchantVoList = new ArrayList<MerchantVo>();
		for (Merchant po : merchantList) {
			MerchantVo vo = (MerchantVo)BeanUtil.copyProperties(MerchantVo.class, po);
			merchantVoList.add(vo);
		}
		return merchantVoList;
	}
	
	/**
     * 统计 Merchant
     * @param merchant
     * @return int
     */
	@Override
	public int countMerchant(MerchantVo merchantVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("统计Merchant");
		Merchant merchant = (Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo);

		return merchantLogic.countMerchant(merchant);
	}

	
	/**   
	 * 查询商户详情  
	 * @param merchantId
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#getMerchantDetail(java.lang.String)    
	 */
	
	@Override
	public MerchantDetailVo getMerchantDetail(String merchantId) throws TException {
		LogCvt.info("根据商户id查询MerchantDetail");
		MerchantDetailVo vo = new MerchantDetailVo() ;
		
//		MerchantDetailMongo  mmongo = new MerchantDetailMongo();
//		MerchantDetail  merchantDetail = mmongo.findMerchantDetailById(merchantId);
		
		MerchantDetail  merchantDetail = merchantLogic.findMerchatDetailByMerchantId(merchantId);
		if(merchantDetail != null) {
			vo = (MerchantDetailVo) BeanUtil.copyProperties(MerchantDetailVo.class, merchantDetail);
			
			Org org = commonLogic.queryByOrgCode(merchantDetail.getClientId(),merchantDetail.getOrgCode());
			if(null != org) {
				vo.setOrgName(org.getOrgName());
			}
		}
		return vo;
	}
	
	
	/**   
	 * 查询商户分类信息    
	 * @param merchantId
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#getMerchantCategoryInfo(java.lang.String)    
	 */
	
	@Override
	public List<CategoryInfoVo> getMerchantCategoryInfo(String merchantId) throws TException {
		LogCvt.info("根据商户id查询CategoryInfo");
		List<CategoryInfo> list = merchantLogic.findMerchantCategoryInfo(merchantId);
		if(CollectionUtils.isEmpty(list)) {
			return new ArrayList<CategoryInfoVo>();
		}		
		return (List<CategoryInfoVo>)BeanUtil.copyProperties(CategoryInfoVo.class, merchantLogic.findMerchantCategoryInfo(merchantId));
	}
	
	/**   
	 * 查询商户类型信息  
	 * @param merchantId
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#getMerchantTypeInfo(java.lang.String)    
	 */
	
	@Override
	public List<TypeInfoVo> getMerchantTypeInfo(String merchantId) throws TException {
		LogCvt.info("根据商户id查询TypeInfo");
		List<TypeInfo> list = merchantLogic.findMerchantTypeInfo(merchantId);
		if(CollectionUtils.isEmpty(list)) {
			return new ArrayList<TypeInfoVo>();
		}
		return (List<TypeInfoVo>)BeanUtil.copyProperties(TypeInfoVo.class, list);
	}
	

	/**     
	 * @param client_id
	 * @param merchantId
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#getMerchantByMerchantId(long, java.lang.String)    
	 */
	
	@Override
	public MerchantVo getMerchantByMerchantId(String merchantId) throws TException {
		LogCvt.info("根据商户id查询Merchant");
		MerchantVo vo = new MerchantVo();
		Merchant m = merchantLogic.findMerchantByMerchantId(merchantId);
		if(m != null)
			vo = (MerchantVo) BeanUtil.copyProperties(MerchantVo.class, m);
		return vo;
	}

    /**
     * 分页查询 Merchant
     * @param merchant
     * @return MerchantPageVoRes
     */
	@Override
	public MerchantPageVoRes getMerchantByPage(PageVo pageVo, MerchantVo merchantVo) throws TException {
		LogCvt.info("分页查询Merchant");
		Page<Merchant> page = (Page<Merchant>)BeanUtil.copyProperties(Page.class, pageVo);
		Merchant merchant = (Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo);

		page = merchantLogic.findMerchantByPage(page, merchant);

		MerchantPageVoRes merchantPageVoRes = new MerchantPageVoRes();
		List<MerchantVo> merchantVoList = (List<MerchantVo>)BeanUtil.copyProperties(MerchantVo.class, page.getResultsContent());
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		merchantPageVoRes.setPage(pageVo);
		merchantPageVoRes.setMerchantVoList(merchantVoList);
		return merchantPageVoRes;
	}
	
	
	
	/**     
	 * 分页查询商户详情
	 * @param page
	 * @param merchantDetailVo
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#getMerchantDetailByPage(com.froad.thrift.vo.PageVo, com.froad.thrift.vo.MerchantDetailVo)    
	 */	
	@Override
	public MerchantDetailPageVoRes getMerchantDetailByPage(PageVo page, MerchantDetailVo merchantDetailVo) throws TException {
		LogCvt.info("分页查询MerchantDetail");
		MerchantDetailPageVoRes merchantDetailPageVoRes = new MerchantDetailPageVoRes();
		try {				
			
			MerchantDetail merchantDetail = (MerchantDetail) BeanUtil.copyProperties(MerchantDetail.class, merchantDetailVo);
			MongoPage mongoPage = (MongoPage) BeanUtil.copyProperties(MongoPage.class, page);
//			mongoPage.setPageNumber(page.getPageNumber());
//			mongoPage.setPageSize(page.getPageSize());
			
			mongoPage = merchantLogic.findMerchantDetailByPage(mongoPage, merchantDetail);
			
			page = (PageVo) BeanUtil.copyProperties(PageVo.class, mongoPage);
			
//			page.setPageCount(mongoPage.getPageCount());
//			page.setTotalCount(mongoPage.getTotalCount());
//			page.setPageCount(mongoPage.getPageCount());
			
			merchantDetailPageVoRes.setPage(page);
			merchantDetailPageVoRes.setMerchantDetailVoList((List)  BeanUtil.copyProperties(MerchantDetailVo.class, mongoPage.getItems()));
		} catch (Exception e) {
			LogCvt.error("分页查询MerchantDetail失败,原因:" + e.getMessage(), e);
		}
		return merchantDetailPageVoRes;
	}

	/**
	 * 生成商户ID
	 * @Title: getMerchantID 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return
	 * @return String    返回类型 
	 * @throws
	 */
	private String getMerchantID() {
		return simpleID.nextId();
	}


	
	/**  
	 * 根据商户id集合查询详情   
	 * @param merchantIdList
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#getMerchantDetailbyOutletIdList(java.util.List)    
	 */	
	@Override
	public List<MerchantDetailVo> getMerchantDetailbyMerchantIdList(List<String> merchantIdList) throws TException {
		LogCvt.info("根据商户id集合查询详情");
		List<MerchantDetailVo> result = new ArrayList<MerchantDetailVo>();
		
		result = (List<MerchantDetailVo>)BeanUtil.copyProperties(MerchantDetailVo.class,  merchantLogic.findMerchantDetailbyMerchantIdList(merchantIdList));
		
		return result;
	}
	
	/**   
	 * 根据商户id集合商户简称(key为商户id,value为商户名称)  
	 * @param merchantIdList
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#getMerchantNamebyMerchantIdList(java.util.List)    
	 */	
	@Override
	public Map<String, String> getMerchantNamebyMerchantIdList(String clientId,List<String> merchantIdList) throws TException {
		LogCvt.info("根据商户id集合查询商户名字");
		if(CollectionUtils.isEmpty(merchantIdList)) {
			LogCvt.error("getMerchantNamebyMerchantIdList接口根据商户id集合查询商户名字, 传入的参数为空");
			return new HashMap<String, String>();
		}
		Map<String, String> result = null;
		
		result = (Map<String, String>)BeanUtil.copyProperties(Map.class,  merchantLogic.findMerchantNamebyMerchantIdList(clientId,merchantIdList));
		if(result != null && !result.isEmpty()) {
			return result;
		}
		return new HashMap<String, String>();
	}
	
	
	@Override
	public ResultVo updateMerchantByAuditThrough(OriginVo originVo,
			MerchantVoReq merchantVoReq) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改审核通过的Merchant");
		Result result = new Result();
		try {		
			//添加操作日志记录
			if(StringUtils.isBlank(originVo.getDescription()))
				originVo.setDescription("修改审核通过的商户信息");
			com.froad.util.LogUtils.addLog(originVo);
			
			MerchantVo merchantVo = merchantVoReq.getMerchantVo();
			List<CategoryInfoVo> categoryInfoVoList = merchantVoReq.getCategoryInfoVoList();
			List<TypeInfoVo> typeInfoVoList = merchantVoReq.getTypeInfoVoList();
			
			Merchant merchant = (Merchant)BeanUtil.copyProperties(Merchant.class, merchantVo);
			
			if (CollectionUtils.isNotEmpty(categoryInfoVoList)) {
				throw new FroadServerException("审核通过后商户分类不能修改!");
			}
			if (CollectionUtils.isNotEmpty(typeInfoVoList)) {
				throw new FroadServerException("审核通过后商户类型不能修改!");
			}
//			if(StringUtils.isNotBlank(merchant.getContactName())){
//				throw new FroadServerException("审核通过后商户联系人姓名不能修改!");
//			}
			if(StringUtils.isNotBlank(merchant.getLegalName())){
				throw new FroadServerException("审核通过后商户法人姓名不能修改!");
			}
			if(merchant.getLegalCredentType() != null){
				throw new FroadServerException("审核通过后商户法人证件号类型不能修改!");
			}
			if(StringUtils.isNotBlank(merchant.getLegalCredentNo())){
				throw new FroadServerException("审核通过后商户法人证件号不能修改!");
			}
			if(StringUtils.isNotBlank(merchant.getLicense())){
				throw new FroadServerException("审核通过后商户营业执照号码不能修改!");
			}
			if(merchant.getContractBegintime() != null){
				throw new FroadServerException("审核通过后商户签约时间不能修改!");
			}
			if(merchant.getContractEndtime() != null){
				throw new FroadServerException("审核通过后商户签约到期时间不能修改!");
			}
			if(StringUtils.isNotBlank(merchant.getContractStaff())){
				throw new FroadServerException("审核通过后商户签约人员不能修改!");
			}
			
			if (!merchantLogic.updateMerchant(merchant)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("审核通过的商户修改失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("修改审核通过商户失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改审核通过商户[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		} 
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	

	/**
	 * 商户列表查询生成报表，ftp上传生成的报表文件至ftp服务器，返回url.
	 * 
	 * @param merchantVo
	 * @author: ganhongwei 2015-9-5
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.MerchantService.Iface#getMerchantExport(com.froad.thrift.vo.MerchantVo
	 *      merchantVo)
	 */
	@Override
	public ExportResultRes getMerchantExport(MerchantVo merchantVo) throws TException {
		LogCvt.info("商户列表报表导出导出-开始");
		long st = System.currentTimeMillis();
		List<Merchant> merchantList = null;
		Result result = new Result();
		Page<Merchant> pageMerchant = null;
		ResultVo resultVo = null;
		String url = "";
		
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(0);
		pageVo.setPageSize(querySize);
		try {
			
			// 去除商户用户管理员多个重复情况
			Merchant tempMerchant = new Merchant();
			tempMerchant.setId(0l);
			
			// 1:查询列表
			pageMerchant = getExportMerchantByPage(pageVo, merchantVo);
			if(null != pageMerchant){
				merchantList = (List<Merchant>) BeanUtil.copyProperties(Merchant.class, pageMerchant.getResultsContent());
				MerchantExportData merchantExportData = merchantLogic.exportMerchantManage(merchantList,tempMerchant);
				ExcelWriter excelWriter = new ExcelWriter(querySize);
				// 导出第一页
				excelWriter.write(merchantExportData.getHeader(),merchantExportData.getData(),merchantExportData.getSheetName(),merchantExportData.getExcelFileName());
				MerchantExportData merchantExportTempData = null;
				if (pageVo.getPageNumber() > 0) {
					for (int i = 1; i <= pageVo.getPageNumber()	&& pageVo.getPageNumber() < pageVo.getPageCount(); i++) {
						// 设置页数
						pageVo.setPageNumber(i + 1);
						pageMerchant = getExportMerchantByPage(pageVo, merchantVo);
						merchantList = (List<Merchant>) BeanUtil.copyProperties(Merchant.class, pageMerchant.getResultsContent());
						// 转换成报表数据
						merchantExportTempData = merchantLogic.exportMerchantManage(merchantList,tempMerchant);
						// 导出
						excelWriter.write(merchantExportTempData.getHeader(),merchantExportTempData.getData(),merchantExportTempData.getSheetName(),merchantExportTempData.getExcelFileName());
					}
				}
				url = excelWriter.getUrl();		
			}		
		} catch (FroadServerException e) {
			LogCvt.error("商户列表查询生成报表失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("商户列表查询生成报表[系统异常], 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.merchant_error.getCode());
			result.setResultDesc(ResultCode.merchant_error.getMsg());
		}
		resultVo = (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		// 2:生产excel并上传服务器，返回服务器路径
		ExportResultRes  exportResultRes = new ExportResultRes();
		exportResultRes.setResultVo(resultVo);
		exportResultRes.setUrl(url);
		LogCvt.info("[商户管理平台]-商户列表报表-结束！总耗时（" + (System.currentTimeMillis() - st) + "）ms， excel下载链接：" + url);
		return exportResultRes;
	}
	
	
	/**
	 * 商户列表报表导出功能获取商户分页内部方法.
	 * 
	 * @param pageVo
	 * @param merchantVo
	 * @return
	 */
	private Page<Merchant> getExportMerchantByPage(PageVo pageVo,MerchantVo merchantVo){
		Page<Merchant> page = (Page<Merchant>)BeanUtil.copyProperties(Page.class, pageVo);
		Merchant merchant = (Merchant) BeanUtil.copyProperties(Merchant.class,merchantVo);
		Page<Merchant> pageMerchant = merchantLogic.findExportByPage(page,merchant);
		if(null != pageMerchant){
			convertPage(page,pageVo);			
		}
       return pageMerchant;		
	}
	
	/**
	 * 商户导出报表追加分页内部方法.
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
	 * 白名单同步操作,提供给openAPI调用. 
	 * @param merchantId   商户Id
	 * @param clientId    客户端id	 
	 * @return ResultVo
	 * @throws TException
	 * @see com.froad.thrift.service.MerchantService.Iface#syncsMerchantInfo(java.lang.String,java.lang.String)
	 */
	@Override
	public ResultVo syncMerchantInfo(String merchantId, String clientId,String isSynSucc,String synType) throws TException {
		long st = System.currentTimeMillis();
		Result result = new Result();		
		try {
			boolean isSuccess = merchantLogic.syncMerchantInfo(merchantId,clientId,isSynSucc,Integer.valueOf(synType));
			if(isSuccess){
				result.setResultCode(ResultCode.success.getCode());
				result.setResultDesc(ResultCode.success.getMsg());
			}			
		} catch (FroadServerException e) {
			LogCvt.error("白名单同步请求操作失败,原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg()+","+e.getMessage());
		} catch (Exception e) {
			LogCvt.error("白名单同步请求操作[系统异常],商户ID："+merchantId+",原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg()+","+e.getMessage());
		}	
		LogCvt.info("商户白名单同步操作-结束！总耗时（" + (System.currentTimeMillis() - st) + "）ms");
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
}