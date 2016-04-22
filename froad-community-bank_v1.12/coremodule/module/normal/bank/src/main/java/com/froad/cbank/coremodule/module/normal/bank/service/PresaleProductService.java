package com.froad.cbank.coremodule.module.normal.bank.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.IsMarketableEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProductTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.ImgUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.ActivitiesVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.FileVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.PresaleProductVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductInfoVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductVoReq;
import com.froad.thrift.service.ActivitiesService;
import com.froad.thrift.service.ClientProductAuditService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.ClientProductAuditOrgCodeVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductActivitiesVo;
import com.froad.thrift.vo.ProductBriefInfoVo;
import com.froad.thrift.vo.ProductBriefPageVo;
import com.froad.thrift.vo.ProductBuyLimitVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ProductSearchVo;
import com.froad.thrift.vo.ProductStatusVoReq;
import com.froad.thrift.vo.ProductVo;
import com.froad.thrift.vo.ResultVo;

/**
 * 预售商品管理
 * 
 * @author yfy
 * @date 2015-03-27 上午 10:15:11
 */
@Service
public class PresaleProductService {
	
	@Resource
	ProductService.Iface productService;
	@Resource
	ActivitiesService.Iface activitiesService;
	@Resource
	ClientProductAuditService.Iface clientProductAuditService;
	
	/**
	 * 分页列表条件查询
	 * 
	 * @throws TException
	 */
	public QueryResult<PresaleProductVoRes> findPageByConditions(String clientId,String filter,
			int pageNumber,int pageSize,int lastPageNumber,Long firstRecordTime,
			Long lastRecordTime) throws TException{
		
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setLastPageNumber(lastPageNumber);
		page.setFirstRecordTime(firstRecordTime);
		page.setLastRecordTime(lastRecordTime);
		
		ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq();
		productFilterVoReq.setClientId(clientId);
		if(StringUtils.isNotEmpty(filter)){
			productFilterVoReq.setFilter(filter);
		}
		
		QueryResult<PresaleProductVoRes> queryVo = new QueryResult<PresaleProductVoRes>();
		List<PresaleProductVoRes> presaleProductList = new ArrayList<PresaleProductVoRes>();
		ProductBriefPageVo productBriefPage = null; 
		try {
			productBriefPage = productService.findMerchantProductsByPage(productFilterVoReq, page);	
			List<ProductBriefInfoVo> productBriefInfoList = productBriefPage.getProductBriefInfoVoList();
			if(productBriefInfoList != null && productBriefInfoList.size() > 0){
				for(ProductBriefInfoVo productBriefInfo : productBriefInfoList){
					PresaleProductVoRes presaleProduct = new PresaleProductVoRes();
					presaleProduct
							.setProductId(productBriefInfo.getProductId());// 商品ID
					presaleProduct.setOrgName(productBriefInfo.getOrgName());// 所属机构
					presaleProduct.setOrgCode(productBriefInfo.getOrgCode());
					presaleProduct.setProductName(productBriefInfo.getName());// 商品名称
					presaleProduct.setSalePrice(Double
							.toString(productBriefInfo.getPrice()));// 预售价格
					presaleProduct.setStore(Integer.toString(productBriefInfo
							.getStore()));// 库存数量
					presaleProduct.setStartDate(DateUtil.formatDate(
							new Date(productBriefInfo.getStartTime()), 
							DateUtil.DATE_FORMAT_01));// 预售开始时间
					presaleProduct.setEndDate(DateUtil.formatDate(
							new Date(productBriefInfo.getEndTime()), 
							DateUtil.DATE_FORMAT_01));// 预售结束时间
					presaleProduct.setTakeStartDate(DateUtil.formatDate(
							new Date(productBriefInfo.getDeliveryStartTime()), 
							DateUtil.DATE_FORMAT_01));// 提货开始时间
					presaleProduct.setTakeEndDate(DateUtil.formatDate(
							new Date(productBriefInfo.getDeliveryEndTime()), 
							DateUtil.DATE_FORMAT_01));// 提货开始时间
					presaleProduct.setAuditState(productBriefInfo
							.getAuditState());// 审核状态
					if(StringUtil.equals(productBriefInfo.getAuditState(), AuditFlagEnum.NEW.getCode())){
						presaleProduct.setAuditStateName(AuditFlagEnum.NEW
								.getDescription());// 审核状态
					}else if(StringUtil.equals(productBriefInfo.getAuditState(), AuditFlagEnum.ACCEPTED.getCode())){
						presaleProduct.setAuditStateName(AuditFlagEnum.ACCEPTED
								.getDescription());// 审核状态
					}else if(StringUtil.equals(productBriefInfo.getAuditState(), AuditFlagEnum.REJECTED.getCode())){
						presaleProduct.setAuditStateName(AuditFlagEnum.REJECTED
								.getDescription());// 审核状态
					}else if(StringUtil.equals(productBriefInfo.getAuditState(), AuditFlagEnum.NO_NEW.getCode())){
						presaleProduct.setAuditStateName(AuditFlagEnum.NO_NEW
								.getDescription());// 审核状态
					}else{
						presaleProduct.setAuditStateName("");
					}
					presaleProduct.setIsMarketable(productBriefInfo
							.getIsMarketable());// 未上架
					if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.NO.getCode())){
						presaleProduct.setIsMarketableName(IsMarketableEnum.NO.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.UP.getCode())){
						presaleProduct.setIsMarketableName(IsMarketableEnum.UP.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.DOWN.getCode())){
						presaleProduct.setIsMarketableName(IsMarketableEnum.DOWN.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.isDeleted.getCode())){
						presaleProduct.setIsMarketableName(IsMarketableEnum.isDeleted.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.disOffShelf.getCode())){
						presaleProduct.setIsMarketableName(IsMarketableEnum.disOffShelf.getDescription());
					}else{
						presaleProduct.setIsMarketableName("");
					}
					// 审核时间
					presaleProduct.setAuditTime(productBriefInfo.getAuditTime());
					presaleProductList.add(presaleProduct);
				}
			}
			if(productBriefPage.getPage() != null){
				queryVo.setPageCount(productBriefPage.getPage().getPageCount());
				queryVo.setTotalCount(productBriefPage.getPage().getTotalCount());
				queryVo.setPageNumber(productBriefPage.getPage().getPageNumber());
				queryVo.setLastPageNumber(productBriefPage.getPage().getLastPageNumber());
				queryVo.setFirstRecordTime(productBriefPage.getPage().getFirstRecordTime());
				queryVo.setLastRecordTime(productBriefPage.getPage().getLastRecordTime());
			}
			
		} catch (Exception e) {
			LogCvt.info("预售商品列表条件查询" + e.getMessage(), e);
		}
		
		queryVo.setResult(presaleProductList);
		return queryVo;
		
	}
	
	/**
	 * 新增
	 * 
	 * @param presaleProductVo
	 * @return
	 */
	public MsgVo productAdd(ProductInfoVoReq presaleProductVo,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			ProductInfoVo productInfo = new ProductInfoVo();
			productInfo.setOrgCodes(presaleProductVo.getPratenOrgCodeList());// 法人行社
			List<String> orgCodeList = presaleProductVo.getOrgCodeList();// 法人网点
			// ---------提货网点------
			List<ProductOutletVo> productOutletList = new ArrayList<ProductOutletVo>();
			if(orgCodeList != null && orgCodeList.size() > 0){
				for(String orgCode : orgCodeList){
					ProductOutletVo productOutletVo = new ProductOutletVo();
					productOutletVo.setOutletId(orgCode);
					productOutletList.add(productOutletVo);
				}
			}
			productInfo.setOutlet(productOutletList);
			// ---------活动表--------
			List<ProductActivitiesVo> activities = new ArrayList<ProductActivitiesVo>();
			List<ActivitiesVo> activitiesList = presaleProductVo
					.getActivities();
			if (activitiesList != null && activitiesList.size() > 0) {
				for (ActivitiesVo activitiesvo : activitiesList) {
					ProductActivitiesVo productActivitiesVo = new ProductActivitiesVo();
					productActivitiesVo.setActivitiesType(activitiesvo
							.getActivitiesType());
					if (StringUtil.isNotEmpty(activitiesvo.getPoints())) {
						productActivitiesVo.setPoints(Integer
								.valueOf(activitiesvo.getPoints()));// 积分
					}
					// else if (StringUtil
					// .isNotEmpty(activitiesvo.getVipPrice())) {
					// productActivitiesVo.setVipPrice("{"
					// + activitiesvo.getActivitiesType() + ":"
					// + activitiesvo.getVipPrice() + "}");// vip价格
					// }
					activities.add(productActivitiesVo);
				}
			}
			productInfo.setActivities(activities);
			// ---------商品表--------
			ProductVo product = new ProductVo();
			// VIP价格更改
			if (StringUtil.isNotBlank(presaleProductVo.getVipPrice())) {
				product.setVipPrice(Double.parseDouble(presaleProductVo.getVipPrice()));
			}
			// ---------限购表--------
			ProductBuyLimitVo productLimit = new ProductBuyLimitVo();
			if(presaleProductVo.getViplimitNum() != null){
				productLimit.setMaxVip(presaleProductVo.getViplimitNum());// VIP限购数量
			}
			if(presaleProductVo.getMemberlimitNum() != null){
				productLimit.setMax(presaleProductVo.getMemberlimitNum());// 普通会员限购数量
			}
			productInfo.setBuyLimit(productLimit);
			product.setIsLimit(false);
			if(productLimit.getMax() > 0){
				product.setIsLimit(true);
			}
			product.setOrgCode(presaleProductVo.getOrgCode());// 当前机构
			product.setClientId(presaleProductVo.getClientId());
			product.setName(presaleProductVo.getProductName());// 商品名称
			product.setFullName(presaleProductVo.getProductFullName());// 商品全称
			product.setType(ProductTypeEnum.PRESALE.getCode());// 预售
			if(StringUtil.isNotEmpty(presaleProductVo.getMarketPrice())){
				product.setMarketPrice(Double.valueOf(presaleProductVo
						.getMarketPrice()));// 市场价
			}
			if(StringUtil.isNotEmpty(presaleProductVo.getSalePrice())){
				product.setPrice(Double.valueOf(presaleProductVo.getSalePrice()));// 销售价
			}
			if(StringUtil.isNotEmpty(presaleProductVo.getStartDate())){
				product.setStartTime(Long.parseLong(presaleProductVo
						.getStartDate()));// 开始时间
			}
			if(StringUtil.isNotEmpty(presaleProductVo.getEndDate())){
				product.setEndTime(Long.parseLong(presaleProductVo.getEndDate()));// 结束时间
			}
			if(StringUtil.isNotEmpty(presaleProductVo.getTakeStartDate())){
				product.setDeliveryStartTime(Long.parseLong(presaleProductVo
						.getTakeStartDate()));// 提货开始时间
			}
			if(StringUtil.isNotEmpty(presaleProductVo.getTakeEndDate())){
				product.setDeliveryEndTime(Long.parseLong(presaleProductVo
						.getTakeEndDate()));// 提货结束时间
			}
			product.setStore(presaleProductVo.getStoreNum());// 商品库存数量
			if(StringUtil.isNotEmpty(presaleProductVo.getPostage())){
				product.setDeliveryMoney(Double.valueOf(presaleProductVo
						.getPostage()));// 邮费
			}else{
				product.setDeliveryMoney(0);// 邮费
			}
			product.setDeliveryOption(presaleProductVo.getDistributionType());// 配送方式(0配送,1自提2配送或自提二者皆)
			if (StringUtil.isNotBlank(presaleProductVo.getDescription())) {
				product.setBriefIntroduction(presaleProductVo.getDescription());// 商品简介
			}
			product.setBuyKnow(presaleProductVo.getProductKnow());// 购买须知
			product.setIntroduction(presaleProductVo.getProductDetails());// 商品详情（介绍）
			product.setIsMarketable(null);
			product.setAuditState(AuditFlagEnum.NEW.getCode());// 待审核
			// 设置最初审核机构和结束机构
			ClientProductAuditOrgCodeVo productAuditVo = clientProductAuditService.
					getClientProductAuditByOrgCode(presaleProductVo.getClientId(),
							presaleProductVo.getOrgCode(), ProductTypeEnum.PRESALE.getCode());
			if(!StringUtil.isNotEmpty(productAuditVo.getStartAuditOrgCode()) 
					&& !StringUtil.isNotEmpty(productAuditVo.getEndAuditOrgCode())){
				msgVo.setResult(false);
				msgVo.setMsg("不能录入预售商品");
				return msgVo;
			}else{
				product.setAuditStartOrgCode(productAuditVo.getStartAuditOrgCode());
				product.setAuditEndOrgCode(productAuditVo.getEndAuditOrgCode());
				if(StringUtil.isNotEmpty(productAuditVo.getStartAuditOrgCode())){
					product.setAuditOrgCode(productAuditVo.getStartAuditOrgCode());
				}else{
					product.setAuditOrgCode(productAuditVo.getEndAuditOrgCode());
				}
			}	
			
			productInfo.setProduct(product);
			// ---------图片------
			List<ProductImageVo> imageList = new ArrayList<ProductImageVo>();
			List<FileVo> fileList = presaleProductVo.getFiles();
			if(fileList != null && fileList.size() > 0){
				for(FileVo file : fileList){
					ProductImageVo imageVo = new ProductImageVo();
					imageVo.setTitle("预售商品");
					imageVo.setSource(file.getSource());
					imageVo.setLarge(file.getLarge());
					imageVo.setMedium(file.getMedium());
					imageVo.setThumbnail(file.getThumbnail());
					imageList.add(imageVo);
				}
			}
			productInfo.setImage(imageList);
			LogCvt.info("预售商品新增  参数" + JSON.toJSONString(productInfo));
			AddProductVoRes resultVo = productService.addProduct(vo, productInfo);
			if(resultVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("预售商品新增" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("预售商品新增异常");
		}
		return msgVo;
	}
	
	/**
	 * 修改
	 * 
	 * @param presaleProductVo
	 * @return
	 */
	public MsgVo productUpdate(ProductInfoVoReq presaleProductVo,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			ProductInfoVo productInfo = new ProductInfoVo();
			productInfo.setOrgCodes(presaleProductVo.getPratenOrgCodeList());// 法人行社
			List<String> orgCodeList = presaleProductVo.getOrgCodeList();// 法人网点
			// ---------提货网点------
			List<ProductOutletVo> productOutletList = new ArrayList<ProductOutletVo>();
			if(orgCodeList != null && orgCodeList.size() > 0){
				for(String orgCode : orgCodeList){
					ProductOutletVo productOutletVo = new ProductOutletVo();
					productOutletVo.setOutletId(orgCode);
					productOutletList.add(productOutletVo);
				}
			}
			productInfo.setOutlet(productOutletList);
			// ---------活动表--------
			List<ProductActivitiesVo> activities = new ArrayList<ProductActivitiesVo>();
			/** 需求变更 */
			List<ActivitiesVo> activitiesList = presaleProductVo
					.getActivities();
			if (activitiesList != null && activitiesList.size() > 0) {
				for (ActivitiesVo activitiesvo : activitiesList) {
					ProductActivitiesVo productActivitiesVo = new ProductActivitiesVo();
					productActivitiesVo.setActivitiesType(activitiesvo
							.getActivitiesType());
					if (StringUtil.isNotEmpty(activitiesvo.getPoints())) {
						productActivitiesVo.setPoints(Integer
								.valueOf(activitiesvo.getPoints()));// 积分
					}
//					else if (StringUtil.isNotEmpty(activitiesvo.getVipPrice())) {
//						productActivitiesVo.setVipPrice("{" + activitiesvo.getActivitiesType() + ":"
//								+ activitiesvo.getVipPrice() + "}");// vip价格
//					}
//					activities.add(productActivitiesVo);
				}
			}
			productInfo.setActivities(activities);
			// --------------限购表----------------
			ProductBuyLimitVo productLimit = new ProductBuyLimitVo();
			if(presaleProductVo.getViplimitNum() != null){
				productLimit.setMaxVip(presaleProductVo.getViplimitNum());// VIP限购数量
			}
			if(presaleProductVo.getMemberlimitNum() != null){
				productLimit.setMax(presaleProductVo.getMemberlimitNum());// 普通会员限购数量
			}
			productInfo.setBuyLimit(productLimit);
			// --------------商品表----------------
			ProductVo product = new ProductVo();
			// VIP价格更改
			if (StringUtil.isNotBlank(presaleProductVo.getVipPrice())) {
				product.setVipPrice(Double.parseDouble(presaleProductVo.getVipPrice()));
			}
			product.setIsLimit(false);
			if(productLimit.getMax() > 0){
				product.setIsLimit(true);
			}
			product.setClientId(presaleProductVo.getClientId());
			product.setOrgCode(presaleProductVo.getOrgCode());// 当前机构
			product.setProductId(presaleProductVo.getProductId());// 商品ID
			product.setName(presaleProductVo.getProductName());// 商品名称
			product.setFullName(presaleProductVo.getProductFullName());// 商品全称
			product.setType(ProductTypeEnum.PRESALE.getCode());// 预售
			if(StringUtil.isNotEmpty(presaleProductVo.getMarketPrice())){
				product.setMarketPrice(Double.valueOf(presaleProductVo
						.getMarketPrice()));// 市场价
			}
			if(StringUtil.isNotEmpty(presaleProductVo.getSalePrice())){
				product.setPrice(Double.valueOf(presaleProductVo.getSalePrice()));// 销售价
			}
			product.setStore(presaleProductVo.getStoreNum());// 商品库存数量
			if(StringUtil.isNotEmpty(presaleProductVo.getStartDate())){
				product.setStartTime(Long.parseLong(presaleProductVo
						.getStartDate()));// 开始时间
			}
			if(StringUtil.isNotEmpty(presaleProductVo.getEndDate())){
				product.setEndTime(Long.parseLong(presaleProductVo.getEndDate()));// 结束时间
			}
			if(StringUtil.isNotEmpty(presaleProductVo.getTakeStartDate())){
				product.setDeliveryStartTime(Long.parseLong(presaleProductVo
						.getTakeStartDate()));// 提货开始时间
			}
			if(StringUtil.isNotEmpty(presaleProductVo.getTakeEndDate())){
				product.setDeliveryEndTime(Long.parseLong(presaleProductVo
						.getTakeEndDate()));// 提货结束时间
			}
			if(StringUtil.isNotEmpty(presaleProductVo.getPostage())){
				product.setDeliveryMoney(Double.valueOf(presaleProductVo
						.getPostage()));// 邮费
			}else{
				product.setDeliveryMoney(0);// 邮费
			}
			product.setDeliveryOption(presaleProductVo.getDistributionType());// 配送方式(1配送,2自提3配送或自提二者皆)
			
			product.setBriefIntroduction(presaleProductVo.getDescription());// 商品简介
			
			product.setBuyKnow(presaleProductVo.getProductKnow());// 购买须知
			product.setIntroduction(presaleProductVo.getProductDetails());// 商品详情（介绍）
			product.setAuditState(AuditFlagEnum.NEW.getCode());// 待审核
			// 设置最初审核机构和结束机构
			ClientProductAuditOrgCodeVo productAuditVo = clientProductAuditService.
					getClientProductAuditByOrgCode(presaleProductVo.getClientId(),
							presaleProductVo.getOrgCode(), ProductTypeEnum.PRESALE.getCode());
			if(!StringUtil.isNotEmpty(productAuditVo.getStartAuditOrgCode()) 
					&& !StringUtil.isNotEmpty(productAuditVo.getEndAuditOrgCode())){
				msgVo.setResult(false);
				msgVo.setMsg("不能修改预售商品");
				return msgVo;
			}else{
				product.setAuditStartOrgCode(productAuditVo.getStartAuditOrgCode());
				product.setAuditEndOrgCode(productAuditVo.getEndAuditOrgCode());
				if(StringUtil.isNotEmpty(productAuditVo.getStartAuditOrgCode())){
					product.setAuditOrgCode(productAuditVo.getStartAuditOrgCode());
				}else{
					product.setAuditOrgCode(productAuditVo.getEndAuditOrgCode());
				}
			}	
			productInfo.setProduct(product);
			// ---------图片------
			List<ProductImageVo> imageList = new ArrayList<ProductImageVo>();
			List<FileVo> fileList = presaleProductVo.getFiles();
			if(fileList != null && fileList.size() > 0){
				for(FileVo file : fileList){
					ProductImageVo imageVo = new ProductImageVo();
					imageVo.setTitle("预售商品");
					imageVo.setSource(file.getSource());
					imageVo.setLarge(file.getLarge());
					imageVo.setMedium(file.getMedium());
					imageVo.setThumbnail(file.getThumbnail());
					imageList.add(imageVo);
				}
			}
			productInfo.setImage(imageList);
			
			ResultVo resultVo = productService.updateProduct(vo, productInfo);
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("预售商品修改" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("预售商品修改异常");
		}
		return msgVo;
	}
	
	/**
	 * 商品详情
	 */
	public ProductInfoVoReq productDetail(String productId,String clientId){
		ProductInfoVoReq product = new ProductInfoVoReq();
		ProductOperateVoReq productVoReq = new ProductOperateVoReq();
		productVoReq.setClientId(clientId);
		productVoReq.setProductId(productId);
		productVoReq.setType(ProductTypeEnum.PRESALE.getCode());
		try {
			ProductInfoVo productInfo = productService.getMerchantProductDetail(productVoReq);
			if(productInfo != null){
				if(productInfo.getProduct() != null){
					DecimalFormat df = new DecimalFormat("#0.00");
					product.setOrgCode(productInfo.getProduct().getOrgCode());// 获取机构号
					product.setOrgName(productInfo.getProduct().getOrgName());// 机构名称
					product.setProductId(productInfo.getProduct()
							.getProductId());// 商品ID
					product.setProductName(productInfo.getProduct().getName());// 商品名称
					product.setProductFullName(productInfo.getProduct().getFullName());
					if(StringUtil.isNotEmpty(productInfo.getProduct().getMarketPrice()+"")){
						BigDecimal markPrice = new BigDecimal(productInfo
								.getProduct().getMarketPrice());// 市场价
						product.setMarketPrice(df.format(markPrice).toString());// 市场价
					}
					if(StringUtil.isNotEmpty(productInfo.getProduct().getPrice()+"")){
						BigDecimal salePrice = new BigDecimal(productInfo
								.getProduct().getPrice());// 预售价
						product.setSalePrice(df.format(salePrice).toString());// 预售价
					}
					product.setStoreNum(productInfo.getProduct().getStore());// 库存数量
					if(StringUtil.isNotEmpty(productInfo.getProduct().getStartTime()+"")){
						product.setStartDate(productInfo.getProduct()
								.getStartTime() + "");// 预售开始时间
					}
					if(StringUtil.isNotEmpty(productInfo.getProduct().getEndTime()+"")){
						product.setEndDate(productInfo.getProduct()
								.getEndTime() + "");// 预售结束时间
					}
					if(StringUtil.isNotEmpty(productInfo.getProduct().getDeliveryStartTime()+"")){
						product.setTakeStartDate(productInfo.getProduct()
								.getDeliveryStartTime() + "");// 提货开始时间
					}
					if(StringUtil.isNotEmpty(productInfo.getProduct().getDeliveryEndTime()+"")){
						product.setTakeEndDate(productInfo.getProduct()
								.getDeliveryEndTime() + "");// 提货结束时间
					}
					if(StringUtil.isNotEmpty(productInfo.getProduct().getDeliveryMoney()+"")){
						BigDecimal deliveryMoney = new BigDecimal(productInfo
								.getProduct().getDeliveryMoney());// 预售价
						product.setPostage(df.format(deliveryMoney).toString());// 邮费
					}
					// VIP价格
					product.setVipPrice(String.valueOf(productInfo.getProduct().getVipPrice()));
					product.setDescription(productInfo.getProduct()
							.getBriefIntroduction());// 商品简介
					product.setDistributionType(productInfo.getProduct()
							.getDeliveryOption());// 配送方式(1配送,2自提3配送或自提二者皆)
					product.setProductKnow(productInfo.getProduct()
							.getBuyKnow());// 购买须知
					product.setProductDetails(productInfo.getProduct()
							.getIntroduction());// 商品详情(介绍)
					product.setIsMarketable(productInfo.getProduct().getIsMarketable());
					// 审核备注
					product.setAuditComment(productInfo.getProduct().getAuditComment());
					// 审核状态
					product.setAuditState(productInfo.getProduct().getAuditState());
				}
				if(productInfo.getBuyLimit() != null){
					product.setViplimitNum(productInfo.getBuyLimit().getMaxVip());// vip限购数量
					product.setMemberlimitNum(productInfo.getBuyLimit().getMax());// 普通会员限购数量
				}else{
					product.setViplimitNum(0);// vip限购数量
					product.setMemberlimitNum(0);// 普通会员限购数量
				}
				ArrayList<ActivitiesVo> activitiesList = new ArrayList<ActivitiesVo>();
				List<ProductActivitiesVo> activities = productInfo
						.getActivities();
				if (activities != null) {
					for (ProductActivitiesVo actVo : activities) {
						ActivitiesVo activitiesVo = new ActivitiesVo();
						activitiesVo.setActivitiesType(actVo
								.getActivitiesType());
						// if (actVo.getVipPrice() != null) {
						// String vipPrice = actVo.getVipPrice()
						// .replace("{", "").replace("}", "");
						// String[] str = vipPrice.split(":");
						// if (str.length > 1 && str[1] != null) {
						// activitiesVo.setVipPrice(str[1]);
						// }
						// } else if (actVo.getPoints() > 0) {
						// activitiesVo.setPoints(actVo.getPoints() + "");
						// }
						activitiesList.add(activitiesVo);
					}
				}
				product.setActivities(activitiesList);// 活动
				product.setPratenOrgCodeList(productInfo.getOrgCodes());// 发人行社
				List<String> bankOrgList = new ArrayList<String>();
				List<String> bankOrgNameList = new ArrayList<String>();
				if(productInfo.getOutlet() != null){
					List<ProductOutletVo> productOutletList = productInfo.getOutlet();
					for(ProductOutletVo productOutletVo : productOutletList){
						bankOrgList.add(productOutletVo.getOutletId());
						bankOrgNameList.add(productOutletVo.getOutletName());
					}
				}
				product.setOrgCodeList(bankOrgList);
				product.setOrgNameList(bankOrgNameList);
				// 商品图片
				ArrayList<FileVo> imageList = new ArrayList<FileVo>();
				List<ProductImageVo> productImageList = productInfo.getImage();
				if(productImageList != null && productImageList.size() > 0){
					for(ProductImageVo productImage : productImageList){
						FileVo fileImage = new FileVo();
						fileImage.setTitle(productImage.getTitle());// 图片标题
						fileImage.setSource(productImage.getSource());// 图片原图地址
						fileImage.setLarge(productImage.getLarge());// 图片大图地址
						fileImage.setMedium(productImage.getMedium());// 图片中图地址
						fileImage.setThumbnail(productImage.getThumbnail());// 图片小图地址
						imageList.add(fileImage);
					}
				}
				product.setFiles(imageList);
				
			}
		} catch (TException e) {
			LogCvt.info("预售商品详情查询" + e.getMessage(), e);
		}
		
		return product;
	}
	
	
	/**
	 * 商品上/下架
	 */
	public MsgVo upDownProduct(String clientId,String productId,String state,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			ProductStatusVoReq productStatusVoReq = new ProductStatusVoReq();
			productStatusVoReq.setClientId(clientId);
			productStatusVoReq.setProductId(productId);// 商品ID
			productStatusVoReq.setStatus(state);// 0未上架1-已上架2-已下架
			ResultVo resultVo = productService.updateProductStatus(vo, productStatusVoReq);
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
		} catch (Exception e) {
			LogCvt.info(
					"商品" + (state == "1" ? "上" : "下") + "架" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("商品" + (state == "1" ? "上" : "下") + "架异常");
		}
		return msgVo;
	}

	/**
	 * 删除
	 * 
	 * @param clientId
	 * @param productId
	 * @return
	 */
	public MsgVo productDelete(String clientId,String productId,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
				ProductOperateVoReq productOperateVo = new ProductOperateVoReq();
				productOperateVo.setClientId(clientId);
				productOperateVo.setProductId(productId);
			ResultVo resultVo = productService.deleteProduct(vo,
					productOperateVo);// 物理删除
				if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
					msgVo.setResult(true);
					msgVo.setMsg(resultVo.getResultDesc());
				}else{
					msgVo.setResult(false);
					msgVo.setMsg(resultVo.getResultDesc());
				}
			
		} catch (Exception e) {
			LogCvt.info("商品删除" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("商品删除异常");
		}
		return msgVo;
	}
	
	/**
	 * 是否有权限录入商品
	 * 
	 * @param clientId
	 * @param myOrgCode
	 * @return
	 * @throws TException
	 */
	public HashMap<String,Object> verifyAddProduct(String clientId,String orgCode,String type) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		ClientProductAuditOrgCodeVo productAuditVo = clientProductAuditService.
				getClientProductAuditByOrgCode(clientId,orgCode, type);
		if(!StringUtil.isNotEmpty(productAuditVo.getStartAuditOrgCode()) && 
				!StringUtil.isNotEmpty(productAuditVo.getEndAuditOrgCode())){
			reMap.put("code", EnumTypes.success.getCode());
			reMap.put("message", true);
		}else{
			reMap.put("code", EnumTypes.fail.getCode());
			reMap.put("message", false);
		}
		return reMap;
	}
	
	/**
	 * 图片上传 imgUpload
	 * 
	 * @param reqMap
	 * @param
	 * @throws Exception
	 * @throws IOException
	 */
	public ArrayList<FileVo> imgUpload(MultipartFile[] files) throws BankException {
		ArrayList<FileVo> al = new ArrayList<FileVo>();
		int count=0;
		for(MultipartFile f : files){
			count++;
			FileVo img=ImgUtil.zipAndUpload(f);
			img.setDefault(count==1?true:false);
			al.add(img);
		}
		return al;
	}
	
	/**
	 * 图片格式检查
	 * 
	 * @tilte ckeckImage
	 * @author zxl
	 * @throws BankException
	 * @date 2015年5月19日 下午2:23:29
	 */
	public void checkImage(MultipartFile[] file) throws BankException{
		
		for(MultipartFile f : file){
			checkImage(f);
		}
	}
	
	/**
	 * 图片格式检查
	 * 
	 * @tilte ckeckImage
	 * @author zxl
	 * @date 2015年5月19日 下午2:26:49
	 * @param file
	 * @throws BankException
	 * @throws MerchantException
	 */
	public void checkImage(MultipartFile file) throws BankException {
		String fileName = file.getOriginalFilename();
		if(fileName.indexOf(".")<0){
			throw new BankException(EnumTypes.img_type_fail);
		}
		String det = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		if(!Constants.IMAGE.contains(det)){
			throw new BankException(EnumTypes.img_type_fail);
		}
	}
	
	/**
	 * 校验
	 * 
	 * @param bankOrg
	 * @return
	 */
	public MsgVo verify(ProductInfoVoReq vo){
		MsgVo msgVo = new MsgVo();
		if(!StringUtil.isNotEmpty(vo.getOrgCode())){
			msgVo.setResult(false);
			msgVo.setMsg("当前机构不能为空");
			return msgVo;
		}
		if(vo.getPratenOrgCodeList() == null || vo.getPratenOrgCodeList().size() == 0){
			msgVo.setResult(false);
			msgVo.setMsg("法人行社不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getProductName())){
			msgVo.setResult(false);
			msgVo.setMsg("商品名称不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getProductFullName())){
			msgVo.setResult(false);
			msgVo.setMsg("商品全称不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getMarketPrice())){
			msgVo.setResult(false);
			msgVo.setMsg("市场价不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getSalePrice())){
			msgVo.setResult(false);
			msgVo.setMsg("预售价不能为空");
			return msgVo;
		}
		if(StringUtil.isNotEmpty(vo.getMarketPrice()) && StringUtil.isNotEmpty(vo.getSalePrice())){
			Double marketPrice = Double.valueOf(vo.getMarketPrice());
			Double salePrice = Double.valueOf(vo.getSalePrice());
			if(salePrice > marketPrice){
				msgVo.setResult(false);
				msgVo.setMsg("预售价不能大于市场价");
				return msgVo;
			}
		}
		if(vo.getStoreNum() == null){
			msgVo.setResult(false);
			msgVo.setMsg("库存数量不能为空");
			return msgVo;
		}
		if(vo.getMemberlimitNum() == null){
			msgVo.setResult(false);
			msgVo.setMsg("限购数量不能为空");
			return msgVo;
		}
		if(vo.getMemberlimitNum() != null && vo.getStoreNum() != null){
			if(vo.getMemberlimitNum() > vo.getStoreNum()){
				msgVo.setResult(false);
				msgVo.setMsg("限购数量不能大于库存数量");
				return msgVo;
			}
		}
		if(!StringUtil.isNotEmpty(vo.getStartDate())||!StringUtil.isNotEmpty(vo.getEndDate())){
			msgVo.setResult(false);
			msgVo.setMsg("预售时间不能为空");
			return msgVo;
		}
		if(StringUtil.isNotEmpty(vo.getStartDate()) && StringUtil.isNotEmpty(vo.getEndDate())){
			if(Long.valueOf(vo.getStartDate()) > Long.valueOf(vo.getEndDate())){
				msgVo.setResult(false);
				msgVo.setMsg("预售开始时间不能大于预售结束时间");
				return msgVo;
			}
		}
		if(!StringUtil.isNotEmpty(vo.getTakeEndDate()) || !StringUtil.isNotEmpty(vo.getTakeStartDate())){
			msgVo.setResult(false);
			msgVo.setMsg("提货时间不能为空");
			return msgVo;
		}
		if(StringUtil.isNotEmpty(vo.getTakeStartDate()) && StringUtil.isNotEmpty(vo.getTakeEndDate())){
			if(Long.valueOf(vo.getTakeStartDate()) > Long.valueOf(vo.getTakeEndDate())){
				msgVo.setResult(false);
				msgVo.setMsg("提货开始时间不能大于提货结束时间");
				return msgVo;
			}
		}
		if(StringUtil.isNotEmpty(vo.getEndDate()) && StringUtil.isNotEmpty(vo.getTakeStartDate())){
			if(Long.valueOf(vo.getEndDate()) > Long.valueOf(vo.getTakeStartDate())){
				msgVo.setResult(false);
				msgVo.setMsg("预售结束时间不能大于提货开始时间");
				return msgVo;
			}
		}
		if(StringUtil.isNotEmpty(vo.getDescription())){
			if (vo.getDescription().length() > 64) {
				msgVo.setResult(false);
				msgVo.setMsg("副标题不能大于64个字符");
				return msgVo;
			}
		}
		if(vo.getFiles() == null || vo.getFiles().size() == 0){
			msgVo.setResult(false);
			msgVo.setMsg("图片不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getProductKnow())){
			msgVo.setResult(false);
			msgVo.setMsg("购买须知不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getProductDetails())){
			msgVo.setResult(false);
			msgVo.setMsg("商品详情不能为空");
			return msgVo;
		}
		msgVo.setResult(true);
		return msgVo;
	}
	
	/**
	 * 
	 * getPresaleListExport:(预售商品下载优化)
	 *
	 * @author wufei
	 * 2015-9-7 上午10:46:40
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	public Map<String, Object> getPresaleListExport(ProductVoReq voReq) throws TException, ParseException {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ProductSearchVo productSearchVo = new ProductSearchVo();
		// 请求参数
		reqArgument(voReq, productSearchVo);
		ExportResultRes  result = productService.downProducts(productSearchVo, ProductTypeEnum.PRESALE.getCode());
		if(result.getResultVo() != null && StringUtil.isNotBlank(result.getUrl())){
			resMap.put("url", result.getUrl());
			resMap.put("code", result.getResultVo().getResultCode());
			resMap.put("message", result.getResultVo().getResultDesc());
		}else{
			resMap.put("code", "9999");
			resMap.put("message", "预售商品导出失败");
		}
		return resMap;
	}
	
	/**
	 * 
	 * reqArgument:商品列表下载优化请求参数封装
	 *
	 * @author 明灿
	 * 2015年9月6日 上午11:33:58
	 * @param voReq
	 * @param productSearchVo
	 * @throws ParseException
	 *
	 */
	private void reqArgument(ProductVoReq voReq, ProductSearchVo productSearchVo) throws ParseException {
		productSearchVo.setClientId(voReq.getClientId());
		if (StringUtil.isNotBlank(voReq.getOrgCode())) {
			productSearchVo.setOrgCode(voReq.getOrgCode());// 所属机构
		}
		if (StringUtil.isNotBlank(voReq.getProductName())) {
			productSearchVo.setProductName(voReq.getProductName());// 商品名称
		}
		if (StringUtil.isNotBlank(voReq.getIsMarketable())) {
			productSearchVo.setIsMarketable(voReq.getIsMarketable());// 上下架状态
		}
		if (StringUtil.isNotBlank(voReq.getMerchantName())) {
			productSearchVo.setMerchantName(voReq.getMerchantName());// 商户名称
		}
		if (StringUtil.isNotBlank(voReq.getAuditState())) {
			productSearchVo.setAuditState(voReq.getAuditState());// 审核状态
		}
		if (StringUtil.isNotBlank(voReq.getAuditStartTime())) {// 时间
			productSearchVo.setAuditStartTime(DateUtil.dateToTheDayBeforeDawn(voReq.getAuditStartTime()));// 审核开始时间
		}
		if (StringUtil.isNotBlank(voReq.getAuditEndTime())) {
			productSearchVo.setAuditEndTime(DateUtil.dateToTheDayAfterDawn(voReq.getAuditEndTime()));// 审核结束时间
		}
	}
}
