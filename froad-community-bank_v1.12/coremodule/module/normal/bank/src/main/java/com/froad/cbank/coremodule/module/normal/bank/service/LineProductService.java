package com.froad.cbank.coremodule.module.normal.bank.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.CreateSource;
import com.froad.cbank.coremodule.module.normal.bank.enums.DistributionType;
import com.froad.cbank.coremodule.module.normal.bank.enums.IsMarketableEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProductTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.util.TargetObjectFormat;
import com.froad.cbank.coremodule.module.normal.bank.vo.FileVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.LineProductVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.LineProductVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductInfoVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductVoReq;
import com.froad.thrift.service.ClientProductAuditService;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.service.MemberSecurityService;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.ClientProductAuditOrgCodeVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductBriefInfoVo;
import com.froad.thrift.vo.ProductBriefPageVo;
import com.froad.thrift.vo.ProductBriefPageVoRes;
import com.froad.thrift.vo.ProductBriefVoRes;
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
import com.froad.thrift.vo.order.AddMerchantVo;
import com.froad.thrift.vo.order.AddOrderVoReq;
import com.froad.thrift.vo.order.AddOrderVoRes;
import com.froad.thrift.vo.order.AddProductVo;
import com.froad.thrift.vo.order.MerchantReturnVo;
import com.froad.thrift.vo.order.ProductReturnVo;


/**
 * 线下积分兑换
 * 
 * @author yfy
 *
 */
@Service
public class LineProductService {
	
	@Resource
	ProductService.Iface productService;
	@Resource
	OrderService.Iface orderService;
	@Resource
	MemberInformationService.Iface memberInformationService;
	@Resource
	MemberSecurityService.Iface memberSecurityService;
	@Resource
	private MerchantManageService mmService;
	@Resource
	ClientProductAuditService.Iface clientProductAuditService;
	@Resource
	private OperatorService operatorService;
	/**
	 * 分页列表条件查询
	 * 
	 * @throws TException
	 */
	public QueryResult<LineProductVoRes> findPageByConditions(String clientId,
			String filter,int pageNumber,int pageSize) throws TException{
		
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		
		ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq();
		productFilterVoReq.setClientId(clientId);
		productFilterVoReq.setFilter(filter);
		
		QueryResult<LineProductVoRes> queryVo = new QueryResult<LineProductVoRes>();
		List<LineProductVoRes> lineProductList = new ArrayList<LineProductVoRes>();
		ProductBriefPageVoRes  productBriefPage = null;
		try {
			LogCvt.info("分页查询线下积分兑换礼品  参数" + JSON.toJSONString(productFilterVoReq));
			productBriefPage = productService.queryProducts(productFilterVoReq, page);
			LogCvt.info("分页查询线下积分兑换礼品 返回数据：" + JSON.toJSONString(productBriefPage));
			List<ProductBriefVoRes> productBriefInfoList = productBriefPage.getProductBriefVoList();
			if(productBriefInfoList != null && productBriefInfoList.size() > 0){
				for(ProductBriefVoRes productBriefVo : productBriefInfoList){
					LineProductVoRes lineProduct = new LineProductVoRes();
					lineProduct.setProductId(productBriefVo.getProductId());// 礼品编号
					lineProduct.setMerchantId(productBriefVo.getMerchantId());// 商户ID
					lineProduct.setProductName(productBriefVo.getName());// 商品名称
					lineProduct.setStorNum(productBriefVo.getStore());// 库存
					lineProduct.setPoint(productBriefVo.getPrice());// 积分
					// 审核时间
				//	lineProduct.setAuditTime(productBriefVo.getAuditTime());
					lineProductList.add(lineProduct);
				}
			}
			if(productBriefPage.getPage() != null){
				queryVo.setPageCount(productBriefPage.getPage().getPageCount());
				queryVo.setTotalCount(productBriefPage.getPage().getTotalCount());
				queryVo.setPageNumber(productBriefPage.getPage().getPageNumber());
			}
		} catch (Exception e) {
			LogCvt.info("获取线下积分兑换礼品列表" + e.getMessage(), e);
		}
		
		queryVo.setResult(lineProductList);
		return queryVo;
		
	}
	
	/**
	 * 兑换礼品分页列表条件查询
	 * 
	 * @throws TException
	 */
	public QueryResult<LineProductVoRes> findPageByProductConditions(String clientId,
			String filter,int pageNumber,int pageSize,int lastPageNumber,Long firstRecordTime,
			Long lastRecordTime) throws TException{
		
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setLastPageNumber(lastPageNumber);
		page.setFirstRecordTime(firstRecordTime);
		page.setLastRecordTime(lastRecordTime);
		
		ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq();
		productFilterVoReq.setClientId(clientId);
		productFilterVoReq.setFilter(filter);
		
		QueryResult<LineProductVoRes> queryVo = new QueryResult<LineProductVoRes>();
		List<LineProductVoRes> lineProductList = new ArrayList<LineProductVoRes>();
		ProductBriefPageVo productBriefPage = null; 
		try {
			productBriefPage = productService.findMerchantProductsByPage(productFilterVoReq, page);	
			List<ProductBriefInfoVo> productBriefInfoList = productBriefPage.getProductBriefInfoVoList();
			if(productBriefInfoList != null && productBriefInfoList.size() > 0){
				for(ProductBriefInfoVo productBriefInfo : productBriefInfoList){
					LineProductVoRes lineProduct = new LineProductVoRes();
					lineProduct.setProductId(productBriefInfo.getProductId());// 礼品编号
					lineProduct.setProductName(productBriefInfo.getName());// 礼品名称
					lineProduct.setOrgCode(productBriefInfo.getOrgCode());
					lineProduct.setPoint(productBriefInfo.getPrice());// 兑换积分
					lineProduct.setStorNum(productBriefInfo.getStore());// 库存
					lineProduct.setOrgName(productBriefInfo.getOrgName());// 机构名称
					lineProduct.setCreateTime(DateUtil.formatDate(
							new Date(productBriefInfo.getCreateTime()), 
							DateUtil.DATE_TIME_FORMAT_01));// 创建时间
					lineProduct.setStartDate(DateUtil.formatDate(
							new Date(productBriefInfo.getStartTime()), 
							DateUtil.DATE_TIME_FORMAT_01));// 兑换开始时间
					lineProduct.setEndDate(DateUtil.formatDate(
							new Date(productBriefInfo.getEndTime()), 
							DateUtil.DATE_TIME_FORMAT_01));// 兑换结束时间
					if(StringUtil.equals(productBriefInfo.getAuditState(), AuditFlagEnum.NEW.getCode())){
						lineProduct.setAuditState(AuditFlagEnum.NEW.getCode());// 审核状态
						lineProduct.setAuditStateName(AuditFlagEnum.NEW
								.getDescription());// 审核状态
					}else if(StringUtil.equals(productBriefInfo.getAuditState(), AuditFlagEnum.ACCEPTED.getCode())){
						lineProduct.setAuditState(AuditFlagEnum.ACCEPTED
								.getCode());// 审核状态
						lineProduct.setAuditStateName(AuditFlagEnum.ACCEPTED
								.getDescription());// 审核状态
					}else if(StringUtil.equals(productBriefInfo.getAuditState(), AuditFlagEnum.REJECTED.getCode())){
						lineProduct.setAuditState(AuditFlagEnum.REJECTED
								.getCode());// 审核状态
						lineProduct.setAuditStateName(AuditFlagEnum.REJECTED
								.getDescription());// 审核状态
					}
					if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.NO.getCode())){
						lineProduct.setIsMarketable(IsMarketableEnum.NO
								.getCode());// 上下架
						lineProduct.setIsMarketableName(IsMarketableEnum.NO.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.UP.getCode())){
						lineProduct.setIsMarketable(IsMarketableEnum.UP
								.getCode());// 上下架
						lineProduct.setIsMarketableName(IsMarketableEnum.UP.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.DOWN.getCode())){
						lineProduct.setIsMarketable(IsMarketableEnum.DOWN
								.getCode());// 上下架
						lineProduct.setIsMarketableName(IsMarketableEnum.DOWN.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.isDeleted.getCode())){
						lineProduct.setIsMarketable(IsMarketableEnum.isDeleted
								.getCode());// 上下架
						lineProduct.setIsMarketableName(IsMarketableEnum.isDeleted.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.disOffShelf.getCode())){
						lineProduct
								.setIsMarketable(IsMarketableEnum.disOffShelf
										.getCode());// 上下架
						lineProduct.setIsMarketableName(IsMarketableEnum.disOffShelf.getDescription());
					}else{
						lineProduct.setIsMarketableName("");
					}
					if(StringUtil.equals(productBriefInfo.getDeliveryOption(), DistributionType.HOME.getCode())){
						lineProduct.setDistributionType(DistributionType.HOME.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getDeliveryOption(), DistributionType.TAKE.getCode())){
						lineProduct.setDistributionType(DistributionType.TAKE.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getDeliveryOption(), DistributionType.HOME_OR_TAKE.getCode())){
						lineProduct.setDistributionType(DistributionType.HOME_OR_TAKE.getDescription());
					}
					// 审核时间
					lineProduct.setAuditTime(productBriefInfo.getAuditTime());
					lineProductList.add(lineProduct);
				}
			}
			if(productBriefPage.getPage()!= null){
				queryVo.setPageCount(productBriefPage.getPage().getPageCount());
				queryVo.setTotalCount(productBriefPage.getPage().getTotalCount());
				queryVo.setPageNumber(productBriefPage.getPage().getPageNumber());
				queryVo.setLastPageNumber(productBriefPage.getPage().getLastPageNumber());
				queryVo.setFirstRecordTime(productBriefPage.getPage().getFirstRecordTime());
				queryVo.setLastRecordTime(productBriefPage.getPage().getLastRecordTime());
			}
		} catch (Exception e) {
			LogCvt.info("线下积分礼品列表条件查询" + e.getMessage(), e);
		}
		
		queryVo.setResult(lineProductList);
		return queryVo;
		
	}
	
	/**
	 * 新增
	 * 
	 * @param presaleProductVo
	 * @return
	 */
	public MsgVo productAdd(ProductInfoVoReq lineProductVo,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			ProductInfoVo productInfo = new ProductInfoVo();
			productInfo.setOrgCodes(lineProductVo.getPratenOrgCodeList());// 法人行社
			List<String> orgCodeList = lineProductVo.getOrgCodeList();// 法人网点
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
			// ---------限购表--------
			ProductBuyLimitVo productLimit = new ProductBuyLimitVo();
			if(lineProductVo.getMemberlimitNum() != null){
				productLimit.setMax(lineProductVo.getMemberlimitNum());// 普通会员限购数量
			}
			productInfo.setBuyLimit(productLimit);
			// ---------商品表--------
			ProductVo product = new ProductVo();
			product.setIsLimit(false);
			if(productLimit.getMax() > 0){
				product.setIsLimit(true);
			}
			product.setClientId(lineProductVo.getClientId());
			product.setName(lineProductVo.getProductName());// 商品名称
			product.setFullName(lineProductVo.getProductFullName());
			product.setType(ProductTypeEnum.LINEUP.getCode());// 网点礼品
			product.setDeliveryOption(lineProductVo.getDistributionType());// 提货方式(0配送,1网点自提,2配送或自提二者皆)
			product.setOrgCode(lineProductVo.getOrgCode());// 当前机构
			if(StringUtil.isNotEmpty(lineProductVo.getStartDate())){
				product.setStartTime(Long.parseLong(lineProductVo
						.getStartDate()));// 开始时间
			}
			if(StringUtil.isNotEmpty(lineProductVo.getEndDate())){
				product.setEndTime(Long.parseLong(lineProductVo.getEndDate()));// 结束时间
			}
			if(StringUtil.isNotEmpty(lineProductVo.getPoint())){
				product.setPrice(Double.valueOf(lineProductVo.getPoint()));// 兑换积分
			}
			if(StringUtil.isNotEmpty(lineProductVo.getMarketPrice())){
				product.setMarketPrice(Double.valueOf(lineProductVo
						.getMarketPrice()));// 市场价
			}
			product.setStore(lineProductVo.getStoreNum());// 商品库存数量
			if (StringUtil.isNotBlank(lineProductVo.getDescription())) {
				product.setBriefIntroduction(lineProductVo.getDescription());// 礼品简介
			}
			product.setBuyKnow(lineProductVo.getProductKnow());// 兑换须知
			product.setIntroduction(lineProductVo.getProductDetails());// 礼品详情（介绍）
			product.setIsMarketable(null);
			product.setAuditState(AuditFlagEnum.NEW.getCode());// 待审核
			// 设置最初审核机构和结束机构
			ClientProductAuditOrgCodeVo productAuditVo = clientProductAuditService.
					getClientProductAuditByOrgCode(lineProductVo.getClientId(),
							lineProductVo.getOrgCode(), ProductTypeEnum.LINEUP.getCode());
			if(!StringUtil.isNotEmpty(productAuditVo.getStartAuditOrgCode()) 
					&& !StringUtil.isNotEmpty(productAuditVo.getEndAuditOrgCode())){
				msgVo.setResult(false);
				msgVo.setMsg("不能录入线下积分礼品");
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
			List<FileVo> fileList = lineProductVo.getFiles();
			if(fileList != null && fileList.size() > 0){
				for(FileVo file : fileList){
					ProductImageVo imageVo = new ProductImageVo();
					imageVo.setTitle("线下积分礼品");
					imageVo.setSource(file.getSource());
					imageVo.setLarge(file.getLarge());
					imageVo.setMedium(file.getMedium());
					imageVo.setThumbnail(file.getThumbnail());
					imageList.add(imageVo);
				}
			}
			productInfo.setImage(imageList);
			AddProductVoRes resultVo = productService.addProduct(vo,productInfo);
			if(resultVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("线下积分礼品新增" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("线下积分礼品新增异常");
		}
		return msgVo;
	}
	
	/**
	 * 修改
	 * 
	 * @param presaleProductVo
	 * @return
	 */
	public MsgVo productUpdate(ProductInfoVoReq lineProductVo,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			ProductInfoVo productInfo = new ProductInfoVo();
			productInfo.setOrgCodes(lineProductVo.getPratenOrgCodeList());// 法人行社
			List<String> orgCodeList = lineProductVo.getOrgCodeList();// 法人网点
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
			// ---------限购表--------
			ProductBuyLimitVo productLimit = new ProductBuyLimitVo();
			if(lineProductVo.getMemberlimitNum() != null){
				productLimit.setMax(lineProductVo.getMemberlimitNum());// 普通会员限购数量
			}
			productInfo.setBuyLimit(productLimit);
			// ---------商品表--------
			ProductVo product = new ProductVo();
			product.setIsLimit(false);
			if(productLimit.getMax() > 0){
				product.setIsLimit(true);
			}
			product.setClientId(lineProductVo.getClientId());
			product.setProductId(lineProductVo.getProductId());// 礼品编号
			product.setName(lineProductVo.getProductName());// 商品名称
			product.setFullName(lineProductVo.getProductFullName());
			product.setType(ProductTypeEnum.LINEUP.getCode());// 网点礼品
			product.setDeliveryOption(lineProductVo.getDistributionType());// 提货方式(1配送,2网点自提,3配送或自提二者皆)
			product.setOrgCode(lineProductVo.getOrgCode());// 当前机构号
			if(StringUtil.isNotEmpty(lineProductVo.getStartDate())){
				product.setStartTime(Long.parseLong(lineProductVo
						.getStartDate()));// 开始时间
			}
			if(StringUtil.isNotEmpty(lineProductVo.getEndDate())){
				product.setEndTime(Long.parseLong(lineProductVo.getEndDate()));// 结束时间
			}
			if(StringUtil.isNotEmpty(lineProductVo.getPoint())){
				product.setPrice(Double.valueOf(lineProductVo.getPoint()));// 兑换积分
			}
			if(StringUtil.isNotEmpty(lineProductVo.getMarketPrice())){
				product.setMarketPrice(Double.valueOf(lineProductVo
						.getMarketPrice()));// 市场价
			}
			product.setStore(lineProductVo.getStoreNum());// 商品库存数量
			if (StringUtil.isNotBlank(lineProductVo.getDescription())) {
				product.setBriefIntroduction(lineProductVo.getDescription());// 礼品简介
			}
			product.setBuyKnow(lineProductVo.getProductKnow());// 兑换须知
			product.setIntroduction(lineProductVo.getProductDetails());// 礼品详情（介绍）
			product.setAuditState(AuditFlagEnum.NEW.getCode());// 待审核
			
			// 设置最初审核机构和结束机构
			ClientProductAuditOrgCodeVo productAuditVo = clientProductAuditService.
					getClientProductAuditByOrgCode(lineProductVo.getClientId(),
							lineProductVo.getOrgCode(), ProductTypeEnum.LINEUP.getCode());
			if(!StringUtil.isNotEmpty(productAuditVo.getStartAuditOrgCode()) 
					&& !StringUtil.isNotEmpty(productAuditVo.getEndAuditOrgCode())){
				msgVo.setResult(false);
				msgVo.setMsg("不能修改线下积分礼品");
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
			List<FileVo> fileList = lineProductVo.getFiles();
			if(fileList != null && fileList.size() > 0){
				for(FileVo file : fileList){
					ProductImageVo imageVo = new ProductImageVo();
					imageVo.setTitle("线下积分礼品");
					imageVo.setSource(file.getSource());
					imageVo.setLarge(file.getLarge());
					imageVo.setMedium(file.getMedium());
					imageVo.setThumbnail(file.getThumbnail());
					imageList.add(imageVo);
				}
			}
			productInfo.setImage(imageList);
			
			ResultVo resultVo = productService.updateProduct(vo,productInfo);
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("线下积分礼品修改" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("线下积分礼品修改异常");
		}
		return msgVo;
	}
	
	/**
	 * 商品详情
	 */
	public ProductInfoVoReq productDetail(String clientId,String productId){
		ProductInfoVoReq product = new ProductInfoVoReq();
		ProductOperateVoReq productVoReq = new ProductOperateVoReq();
		productVoReq.setClientId(clientId);
		productVoReq.setProductId(productId);
		productVoReq.setType(ProductTypeEnum.LINEUP.getCode());
		try {
			ProductInfoVo productInfo = productService.getMerchantProductDetail(productVoReq);
			if(productInfo != null){
				if(productInfo.getProduct() != null){
					DecimalFormat df = new DecimalFormat("#0.00");
					product.setOrgCode(productInfo.getProduct().getOrgCode());// 获取机构号
					product.setProductId(productInfo.getProduct()
							.getProductId());// 商品ID
					product.setProductName(productInfo.getProduct().getName());// 商品名称
					product.setProductFullName(productInfo.getProduct().getFullName());
					product.setMerchantId(productInfo.getProduct().getMerchantId());
					if(productInfo.getProduct().getPrice()+"" != null){
						product.setPoint(TargetObjectFormat
								.getDoubleDecimalNum(productInfo.getProduct()
										.getPrice()));// 兑换积分
					}
//					if(productInfo.getProduct().getPrice()+"" != null){
//						product.setPoint(productInfo.getProduct()
//										.getPrice()+"");// 兑换积分
//					}
					if(productInfo.getProduct().getMarketPrice()+"" != null){
						BigDecimal markPrice = new BigDecimal(productInfo
								.getProduct().getMarketPrice());// 市场价
						product.setMarketPrice(df.format(markPrice).toString());// 市场价
					}
					product.setStoreNum(productInfo.getProduct().getStore());// 库存数量
					product.setStartDate(productInfo.getProduct()
							.getStartTime() + "");// 开始时间
					product.setEndDate(productInfo.getProduct().getEndTime()
							+ "");// 结束时间
					product.setDescription(productInfo.getProduct()
							.getBriefIntroduction());// 礼品简介
					product.setDistributionType(productInfo.getProduct()
							.getDeliveryOption());// 配送方式(1配送,2自提3配送或自提二者皆)
					product.setProductKnow(productInfo.getProduct()
							.getBuyKnow());// 兑换须知
					product.setProductDetails(productInfo.getProduct()
							.getIntroduction());// 礼品详情(介绍)
					product.setIsMarketable(productInfo.getProduct().getIsMarketable());
					product.setAuditState(productInfo.getProduct().getAuditState());
					product.setAuditComment(productInfo.getProduct().getAuditComment());
				}
				if(productInfo.getBuyLimit()!= null){
					product.setMemberlimitNum(productInfo.getBuyLimit()
							.getMax());// 普通会员限购数量
				}else{
					product.setMemberlimitNum(0);
				}
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
			LogCvt.info("线下积分礼品详情查询" + e.getMessage(), e);
		}
		
		return product;
	}
	
	/**
	 * 删除
	 * 
	 * @param clientId
	 * @param productIdList
	 * @return
	 */
	public MsgVo productDelete(String clientId,String productId,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			ProductOperateVoReq productOperateVo = new ProductOperateVoReq();
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
			LogCvt.info("线下积分礼品删除" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("线下积分礼品删除异常");
		}
		return msgVo;
	}
	
	/**
	 * 批量删除
	 * 
	 * @param clientId
	 * @param productIdList
	 * @return
	 */
	public MsgVo productDelete(String clientId,List<String> productIdList,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			for(String productId: productIdList){
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
			}
			
			
		} catch (Exception e) {
			LogCvt.info("线下积分礼品批量删除" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("线下积分礼品批量删除异常");
		}
		return msgVo;
	}
	
	/**
	 * 商品上/下架
	 */
	public MsgVo upDownProduct(String clientId,List<String> productIdList,String state,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			for(String productId: productIdList){
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
			}
		} catch (Exception e) {
			LogCvt.info(
					"线下积分礼品" + (state == "1" ? "上" : "下") + "架"
							+ e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("线下积分礼品" + (state == "1" ? "上" : "下") + "架异常");
		}
		return msgVo;
	}
	
	/**
	 * 积分查询
	 * 
	 * @param clientId
	 * @param accountCode
	 * @return
	 */
	public String productSelectPoint(String clientId,String accountCode){
		String price = "";
		try {
			ResultVo resultVo = memberInformationService.findBankPointsByMobile(clientId, accountCode);
			if(resultVo != null){
				price = resultVo.getResultDesc();
			}
		} catch (Exception e) {
			LogCvt.info("根据卡号查询积分" + e.getMessage(), e);
		}
		return price;
	}
	
	/**
	 * 根据卡号获取兑换码
	 * 
	 * @param clientId
	 * @param accountCode
	 * @return
	 */
	public MsgVo selectByTakeCode(String clientId,String accountCode,Integer points){
		MsgVo msgVo = new MsgVo();
		try {
		
			ResultVo resultVo = memberSecurityService.sendExchangeCode(clientId,accountCode,points,false);
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
		} catch (Exception e) {
			LogCvt.info("获取兑换码" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("获取兑换码异常");
		}
		return msgVo;
	}
	
	/**
	 * 验证兑换码
	 * 
	 * @param clientId
	 * @param accountCode
	 * @return
	 */
	public MsgVo verifyExchangeCode(LineProductVoReq voReq,String ip){
		MsgVo msgVo = new MsgVo();
		try {
			if(StringUtil.isNotEmpty(voReq.getMerchantId())){
				ResultVo resultVo = memberSecurityService.verifyExchangeCode(voReq.getClientId(),
						voReq.getAccountCode(),voReq.getTakeCode(),false);
				LogCvt.info("====积分兑换返回数据====: " + JSON.toJSONString(resultVo));
				if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
					msgVo = this.productValidation(voReq,ip);
				}else{
					msgVo.setResult(false);
					msgVo.setMsg(resultVo.getResultDesc());
				}
			}else{
				msgVo.setResult(false);
				msgVo.setMsg("没有此兑换网点的商户");
			}
		} catch (Exception e) {
			LogCvt.info("验证兑换码" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("验证兑换码异常");
		}
		return msgVo;
	}
	
	/**
	 * 礼品兑换
	 * 
	 * @param clientId
	 * @param takeCode
	 * @return
	 */
	public MsgVo productValidation(LineProductVoReq lineVoReq,String ip){
		MsgVo msgVo = new MsgVo();
		try {
			
			ProductInfoVoReq infoVoReq = productDetail(lineVoReq.getClientId(), lineVoReq.getProductId());
			if(infoVoReq==null){
				LogCvt.info("礼品兑换商品详情查询失败，客户端：" +lineVoReq.getClientId()+"商品ID："+ lineVoReq.getProductId());
				msgVo.setResult(false);
				msgVo.setMsg("礼品兑换失败");
				return msgVo;
			}
			
			AddOrderVoReq voReq = new AddOrderVoReq();
			voReq.setClientId(lineVoReq.getClientId());
			voReq.setMemberName("");
			voReq.setCreateSource(CreateSource.pc.getCode());//100-pc
			voReq.setOrderRequestType("1");// 1：网点积分兑换2:其他
			voReq.setCardNo(lineVoReq.getAccountCode());
			
			Integer totalPoints=Integer.parseInt(infoVoReq.getPoint())*lineVoReq.getNumber();
			//设置积分数
			voReq.setBankPoint(totalPoints);
			
			voReq.setIp(ip);
			long operatorId = Long.parseLong(lineVoReq.getOperatorId());
			LogCvt.info("====积分兑换前端操作员id====" + operatorId);
			OperatorVo operatorVo = operatorService.operatorDetail(lineVoReq.getClientId(), operatorId);
			LogCvt.info("====积分兑换操作员信息====" + JSON.toJSONString(operatorVo));
			if (operatorVo != null) {
				voReq.setOperatorName(operatorVo.getLoginName());
				voReq.setOperatorId(String.valueOf(operatorId));
			}
			List<AddProductVo> productVoList = new ArrayList<AddProductVo>();
			AddProductVo productVo = new AddProductVo();
			productVo.setProductId(lineVoReq.getProductId());
			productVo.setDeliveryType(DistributionType.TAKE.getCode());// 提货方式0-送货上门
																		// 1-网点自提
			productVo.setOrgCode(lineVoReq.getOrgCode());// 兑换网点
			productVo.setOrgName(lineVoReq.getOrgName());
			productVo.setQuantity(lineVoReq.getNumber());// 数量
			productVoList.add(productVo);
			List<AddMerchantVo> merchantList = new ArrayList<AddMerchantVo>();
			AddMerchantVo merchantVo = new AddMerchantVo();
			merchantVo.setMerchantId(lineVoReq.getMerchantId());
			merchantVo.setAddProductVoList(productVoList);
			merchantList.add(merchantVo);
			voReq.setAddMerchantVoList(merchantList);
			AddOrderVoRes orderVo = orderService.addOrder(voReq);
			if(orderVo.getResultVo().getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg("礼品兑换成功");
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(orderVo.getResultVo().getResultDesc());
				List<MerchantReturnVo> merchList = orderVo.getMerchantReturnVoList();
				if(merchList != null && merchList.size() > 0){
					// 商户没有异常
					if(merchList.get(0).getErrCode().equals(EnumTypes.success.getCode())){
						List<ProductReturnVo> productList = merchList.get(0).getProductReturnVoList();
						if(productList != null && productList.size() > 0){
							if(!productList.get(0).getErrCode().equals(EnumTypes.success.getCode())){
								// 商品异常
								msgVo.setMsg(productList.get(0).getErrMsg());
							}
						}
					}else{
						// 其他异常
						msgVo.setMsg(merchList.get(0).getErrMsg());
					}
				}
			}
		} catch (Exception e) {
			LogCvt.info("验证兑换码" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("验证兑换码异常");
		}
		return msgVo;
	}
	
	/**
	 * 校验
	 * 
	 * @param bankOrg
	 * @return
	 */
	public MsgVo verify(ProductInfoVoReq vo){
		MsgVo msgVo = new MsgVo();
		if(vo.getPratenOrgCodeList() == null || vo.getPratenOrgCodeList().size() == 0){
			msgVo.setResult(false);
			msgVo.setMsg("法人行社不能为空");
			return msgVo;
		}
		if(vo.getOrgCodeList() == null || vo.getOrgCodeList().size() == 0){
			msgVo.setResult(false);
			msgVo.setMsg("提货网点不能为空");
			return msgVo;
		}
		if (!StringUtil.isNotEmpty(vo.getProductName())) {
			msgVo.setResult(false);
			msgVo.setMsg("礼品简称不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getProductFullName())){
			msgVo.setResult(false);
			msgVo.setMsg("礼品全称不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getOrgCode())){
			msgVo.setResult(false);
			msgVo.setMsg("当前组织编码不能为空");
			return msgVo;
		}
		if(vo.getPoint() == null){
			msgVo.setResult(false);
			msgVo.setMsg("兑换积分不能为空");
			return msgVo;
		}
		if(vo.getStoreNum() == null){
			msgVo.setResult(false);
			msgVo.setMsg("库存数量不能为空");
			return msgVo;
		}
		if(vo.getMemberlimitNum() == null){
			msgVo.setResult(false);
			msgVo.setMsg("普通限购数量不能为空");
			return msgVo;
		}
		if(vo.getMemberlimitNum() != null && vo.getStoreNum() != null){
			if(vo.getMemberlimitNum() > vo.getStoreNum()){
				msgVo.setResult(false);
				msgVo.setMsg("限购数量不能大于库存数量");
				return msgVo;
			}
		}
		if(!StringUtil.isNotEmpty(vo.getStartDate()) || !StringUtil.isNotEmpty(vo.getEndDate())){
			msgVo.setResult(false);
			msgVo.setMsg("兑换时间不能为空");
			return msgVo;
		}
		if(StringUtil.isNotEmpty(vo.getStartDate()) && StringUtil.isNotEmpty(vo.getEndDate())){
			if(Long.valueOf(vo.getStartDate()) > Long.valueOf(vo.getEndDate())){
				msgVo.setResult(false);
				msgVo.setMsg("兑换开始时间不能大于兑换结束时间");
				return msgVo;
			}
		}
		/** 礼品简介就是副标题,支持非空 */
		if(!StringUtil.isNotEmpty(vo.getDescription())){
			msgVo.setResult(false);
			msgVo.setMsg("副标题不能为空");
			return msgVo;
		}
		if (vo.getDescription().length() > 64) {
			msgVo.setResult(false);
			msgVo.setMsg("副标题不能超过64个字符");
			return msgVo;
		}
		if(vo.getFiles() == null || vo.getFiles().size() == 0){
			msgVo.setResult(false);
			msgVo.setMsg("图片不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getProductKnow())){
			msgVo.setResult(false);
			msgVo.setMsg("兑换须知不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getProductDetails())){
			msgVo.setResult(false);
			msgVo.setMsg("礼品详情不能为空");
			return msgVo;
		}
		msgVo.setResult(true);
		return msgVo;
	}
	
	
	/**
	 * 校验
	 * 
	 * @param bankOrg
	 * @return
	 */
	public MsgVo verifyExchangeCode(LineProductVoReq voReq){
		MsgVo msgVo = new MsgVo();
		if(!StringUtil.isNotEmpty(voReq.getAccountCode())){
			msgVo.setResult(false);
			msgVo.setMsg("卡号不能为空");
			return msgVo;
		}
		if(voReq.getAccountCode().length() < 15){
			msgVo.setResult(false);
			msgVo.setMsg("卡号不能少于15位");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(voReq.getTakeCode())){
			msgVo.setResult(false);
			msgVo.setMsg("兑换码不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(voReq.getProductId())){
			msgVo.setResult(false);
			msgVo.setMsg("商品ID不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(voReq.getOrgCode())){
			msgVo.setResult(false);
			msgVo.setMsg("组织编码不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(voReq.getOrgName())){
			msgVo.setResult(false);
			msgVo.setMsg("机构名称不能为空");
			return msgVo;
		}
		if(voReq.getPoints() == null){
			msgVo.setResult(false);
			msgVo.setMsg("积分不能为空");
			return msgVo;
		}
		if(voReq.getNumber() == null){
			msgVo.setResult(false);
			msgVo.setMsg("数量不能为空");
			return msgVo;
		}
		if (voReq.getOperatorId() == null) {
			msgVo.setResult(false);
			msgVo.setMsg("操作员id不能为空");
			return msgVo;
		}
		msgVo.setResult(true);
		return msgVo;
	}
	
	/**
	 * 
	 * getLineListExport:(积分兑换商品列表下载优化).
	 *
	 * @author wufei
	 * 2015-9-7 上午10:51:15
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	public Map<String, Object> getLineListExport(ProductVoReq voReq) throws TException, ParseException {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ProductSearchVo productSearchVo = new ProductSearchVo();
		// 请求参数
		reqArgument(voReq, productSearchVo);
		ExportResultRes  result = productService.downProducts(productSearchVo, ProductTypeEnum.LINEUP.getCode());
		if(result.getResultVo() != null && StringUtil.isNotBlank(result.getUrl())){
			resMap.put("url", result.getUrl());
			resMap.put("code", result.getResultVo().getResultCode());
			resMap.put("message", result.getResultVo().getResultDesc());
		}else{
			resMap.put("code", "9999");
			resMap.put("message", "积分兑换商品导出失败");
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
		// 积分开始和结束
		if(StringUtil.isNotBlank(voReq.getPriceStart())){
			productSearchVo.setPointStart(Integer.parseInt(voReq.getPriceStart()));
		}
		if (StringUtil.isNotBlank(voReq.getPriceEnd())) {
			productSearchVo.setPointEnd(Integer.parseInt(voReq.getPriceEnd()));
		}
		if (StringUtil.isNotBlank(voReq.getOrgCode())) {
			productSearchVo.setOrgCode(voReq.getOrgCode());// 所属机构
		}
		if (StringUtil.isNotBlank(voReq.getIsMarketable())) {
			productSearchVo.setIsMarketable(voReq.getIsMarketable());// 上下架状态
		}
		if (StringUtil.isNotBlank(voReq.getProductName())) {
			productSearchVo.setProductName(voReq.getProductName());// 商品名称
		}
		if (StringUtil.isNotBlank(voReq.getMerchantName())) {
			productSearchVo.setMerchantName(voReq.getMerchantName());// 商户名称
		}
		if (StringUtil.isNotBlank(voReq.getAuditState())) {
			productSearchVo.setAuditState(voReq.getAuditState());// 审核状态
		}
		if (StringUtil.isNotBlank(voReq.getAuditStartTime())) {
			productSearchVo.setAuditStartTime(DateUtil.DateFormat(voReq.getAuditStartTime()));// 审核开始时间
		}
		if (StringUtil.isNotBlank(voReq.getAuditEndTime())) {
			productSearchVo.setAuditEndTime(DateUtil.DateFormatEnd(voReq.getAuditEndTime()));// 审核结束时间
		}
	}
	
}
