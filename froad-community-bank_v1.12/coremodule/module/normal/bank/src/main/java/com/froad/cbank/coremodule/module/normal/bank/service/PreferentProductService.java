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

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.DistributionType;
import com.froad.cbank.coremodule.module.normal.bank.enums.IsMarketableEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProductTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.FileVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.PreferentProductVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductInfoVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductVoReq;
import com.froad.thrift.service.ClientProductAuditService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.ClientProductAuditOrgCodeVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductBriefInfoVo;
import com.froad.thrift.vo.ProductBriefPageVo;
import com.froad.thrift.vo.ProductBuyLimitVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.ProductSearchVo;
import com.froad.thrift.vo.ProductVo;
import com.froad.thrift.vo.ResultVo;

/**
 * 名优特惠商品管理
 * 
 * @author ylchu
 *
 */
@Service
public class PreferentProductService {

	@Resource
	ProductService.Iface productService;
	@Resource
	ClientProductAuditService.Iface clientProductAuditService;
	@Resource
	MerchantService.Iface merchantService;
	
	/**
	 * 名优特惠
	 * 
	 * @param filter
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public QueryResult<PreferentProductVoRes> findPageByConditions(String clientId,String filter,
			int pageNumber,int pageSize,int lastPageNumber,Long firstRecordTime,
			Long lastRecordTime){
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
		
		QueryResult<PreferentProductVoRes> queryVo = new QueryResult<PreferentProductVoRes>();
		List<PreferentProductVoRes> productList = new ArrayList<PreferentProductVoRes>();
		ProductBriefPageVo productBriefPage = null; 
		try {
			productBriefPage = productService.findMerchantProductsByPage(productFilterVoReq, page);	
			List<ProductBriefInfoVo> productBriefInfoList = productBriefPage.getProductBriefInfoVoList();
			if(productBriefInfoList != null && productBriefInfoList.size() > 0){
				for(ProductBriefInfoVo productBriefInfo : productBriefInfoList){
					PreferentProductVoRes preferentProduct = new PreferentProductVoRes();
					DecimalFormat df = new DecimalFormat("#0.00");
					preferentProduct.setProductId(productBriefInfo
							.getProductId());// 商品ID
					preferentProduct.setOrgName(productBriefInfo.getOrgName());// 所属机构
					preferentProduct.setProductName(productBriefInfo.getName());// 商品名称
					preferentProduct.setMerchantName(productBriefInfo
							.getMerchantName());// 商户名称
					preferentProduct.setOrgCode(productBriefInfo.getOrgCode());// 录入人机构号
					BigDecimal markPrice = new BigDecimal(
							productBriefInfo.getMarketPrice());// 市场价
					preferentProduct.setMarketPrice(df.format(markPrice)
							.toString());// 市场价
					BigDecimal price = new BigDecimal(productBriefInfo.getPrice());
					preferentProduct.setSalePrice(df.format(price).toString());// 特惠价
					preferentProduct.setStoreNum(productBriefInfo.getStore());// 当前库存
					preferentProduct.setStartDate(DateUtil.formatDate(
							new Date(productBriefInfo.getStartTime()), 
							DateUtil.DATE_FORMAT_01));// 活动开始时间
					preferentProduct.setEndDate(DateUtil.formatDate(
							new Date(productBriefInfo.getEndTime()), 
							DateUtil.DATE_FORMAT_01));// 活动结束时间
					preferentProduct.setAuditState(productBriefInfo
							.getAuditState());// 审核状态
					if(StringUtil.equals(productBriefInfo.getAuditState(), AuditFlagEnum.NEW.getCode())){
						preferentProduct.setAuditStateName(AuditFlagEnum.NEW
								.getDescription());// 审核状态
					}else if(StringUtil.equals(productBriefInfo.getAuditState(), AuditFlagEnum.ACCEPTED.getCode())){
						preferentProduct
								.setAuditStateName(AuditFlagEnum.ACCEPTED
										.getDescription());// 审核状态
					}else if(StringUtil.equals(productBriefInfo.getAuditState(), AuditFlagEnum.REJECTED.getCode())){
						preferentProduct
								.setAuditStateName(AuditFlagEnum.REJECTED
										.getDescription());// 审核状态
					}else if(StringUtil.equals(productBriefInfo.getAuditState(), AuditFlagEnum.NO_NEW.getCode())){
						preferentProduct.setAuditStateName(AuditFlagEnum.NO_NEW
								.getDescription());// 审核状态
					}else{
						preferentProduct.setAuditStateName("");
					}
					preferentProduct.setIsMarketable(productBriefInfo
							.getIsMarketable());// 未上架
					if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.NO.getCode())){
						preferentProduct.setIsMarketableName(IsMarketableEnum.NO.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.UP.getCode())){
						preferentProduct.setIsMarketableName(IsMarketableEnum.UP.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.DOWN.getCode())){
						preferentProduct.setIsMarketableName(IsMarketableEnum.DOWN.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.isDeleted.getCode())){
						preferentProduct.setIsMarketableName(IsMarketableEnum.isDeleted.getDescription());
					}else if(StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.disOffShelf.getCode())){
						preferentProduct.setIsMarketableName(IsMarketableEnum.disOffShelf.getDescription());
					}else{
						preferentProduct.setIsMarketableName("");
					}
					// 审核时间
					preferentProduct.setAuditTime(productBriefInfo.getAuditTime());
					// 审核人
					preferentProduct.setAuditStaff(productBriefInfo
							.getAuditStaff());
					productList.add(preferentProduct);
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
			LogCvt.info("名优特惠商品列表条件查询" + e.getMessage(), e);
		}
		
		queryVo.setResult(productList);
		return queryVo;
	}
	
	/**
	 * 新增
	 * 
	 * @param presaleProductVo
	 * @return
	 */
	public MsgVo productAdd(ProductInfoVoReq preferentProductVo,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			ProductInfoVo productInfo = new ProductInfoVo();
			// ---------限购表--------
			ProductBuyLimitVo productLimit = new ProductBuyLimitVo();
			if(preferentProductVo.getLimitNum() != null){
				productLimit.setMax(preferentProductVo.getLimitNum());// 限购数量
			}
			// ---------商品表--------
			productInfo.setBuyLimit(productLimit);
			ProductVo product = new ProductVo();
			product.setIsLimit(false);
			if(productLimit.getMax() > 0){
				product.setIsLimit(true);
			}
			product.setClientId(preferentProductVo.getClientId());
			product.setMerchantId(preferentProductVo.getMerchantId());// 商户Id
			product.setOrgCode(preferentProductVo.getOrgCode());// 当前机构
			product.setName(preferentProductVo.getProductName());// 商品名称
			product.setFullName(preferentProductVo.getProductFullName());// 商品全称
			product.setType(ProductTypeEnum.PREFERENTIAL.getCode());// 名优特惠
			if(StringUtil.isNotEmpty(preferentProductVo.getMarketPrice())){
				product.setMarketPrice(Double.valueOf(preferentProductVo
						.getMarketPrice()));// 市场价
			}
			if(StringUtil.isNotEmpty(preferentProductVo.getSalePrice())){
				product.setPrice(Double.valueOf(preferentProductVo
						.getSalePrice()));// 特惠价
			}
			product.setDeliveryMoney(0);// 邮费
			product.setStore(preferentProductVo.getStoreNum());// 商品库存数量
			if(StringUtil.isNotEmpty(preferentProductVo.getStartDate())){
				product.setStartTime(Long.parseLong(preferentProductVo
						.getStartDate()));// 开始时间
			}
			if(StringUtil.isNotEmpty(preferentProductVo.getEndDate())){
				product.setEndTime(Long.parseLong(preferentProductVo
						.getEndDate()));// 结束时间
			}
			product.setDeliveryOption(DistributionType.HOME.getCode());// 配送方式(0配送,1自提2配送或自提二者皆)
			if (StringUtil.isNotBlank(preferentProductVo.getDescription())) {
				product.setBriefIntroduction(preferentProductVo
						.getDescription());// 商品简介
			}
			product.setBuyKnow(preferentProductVo.getProductKnow());// 购买须知
			product.setIntroduction(preferentProductVo.getProductDetails());// 商品详情（介绍）
			product.setIsMarketable(null);
			product.setAuditState(AuditFlagEnum.NEW.getCode());// 待审核
			// 设置最初审核机构和结束机构
			ClientProductAuditOrgCodeVo productAuditVo = clientProductAuditService.
					getClientProductAuditByOrgCode(preferentProductVo.getClientId(),
							preferentProductVo.getOrgCode(), ProductTypeEnum.PREFERENTIAL.getCode());
			if(!StringUtil.isNotEmpty(productAuditVo.getStartAuditOrgCode()) 
					&& !StringUtil.isNotEmpty(productAuditVo.getEndAuditOrgCode())){
				msgVo.setResult(false);
				msgVo.setMsg("不能录入名优特惠商品");
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
			List<FileVo> fileList = preferentProductVo.getFiles();
			if(fileList != null && fileList.size() > 0){
				for(FileVo file : fileList){
					ProductImageVo imageVo = new ProductImageVo();
					imageVo.setTitle("名优特惠商品");
					imageVo.setSource(file.getSource());
					imageVo.setLarge(file.getLarge());
					imageVo.setMedium(file.getMedium());
					imageVo.setThumbnail(file.getThumbnail());
					imageList.add(imageVo);
				}
			}
			productInfo.setImage(imageList);
			
			AddProductVoRes resultVo = productService.addProduct(vo, productInfo);
			if(resultVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("名优特惠商品新增" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("名优特惠商品新增异常");
		}
		return msgVo;
	}
	
	/**
	 * 修改
	 * 
	 * @param presaleProductVo
	 * @return
	 */
	public MsgVo productUpdate(ProductInfoVoReq preferentProductVo,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			ProductInfoVo productInfo = new ProductInfoVo();
			// --------------限购表----------------
			ProductBuyLimitVo productLimit = new ProductBuyLimitVo();
			if(preferentProductVo.getLimitNum() != null){
				productLimit.setMax(preferentProductVo.getLimitNum());// 限购数量
			}
			productInfo.setBuyLimit(productLimit);
			// --------------商品表----------------
			ProductVo product = new ProductVo();
			product.setIsLimit(false);
			if(productLimit.getMax() > 0){
				product.setIsLimit(true);
			}
			product.setClientId(preferentProductVo.getClientId());
			product.setMerchantId(preferentProductVo.getMerchantId());// 商户Id
			product.setProductId(preferentProductVo.getProductId());// 商品ID
			product.setOrgCode(preferentProductVo.getOrgCode());// 当前所选商户机构
			product.setName(preferentProductVo.getProductName());// 商品名称
			product.setFullName(preferentProductVo.getProductFullName());// 商品全称
			product.setType(ProductTypeEnum.PREFERENTIAL.getCode());// 名优特惠
			if(StringUtil.isNotEmpty(preferentProductVo.getMarketPrice())){
				product.setMarketPrice(Double.valueOf(preferentProductVo
						.getMarketPrice()));// 市场价
			}
			if(StringUtil.isNotEmpty(preferentProductVo.getSalePrice())){
				product.setPrice(Double.valueOf(preferentProductVo
						.getSalePrice()));// 特惠价
			}
			product.setDeliveryMoney(0);// 邮费
			product.setStore(preferentProductVo.getStoreNum());// 商品库存数量
			if(StringUtil.isNotEmpty(preferentProductVo.getStartDate())){
				product.setStartTime(Long.parseLong(preferentProductVo
						.getStartDate()));// 开始时间
			}
			if(StringUtil.isNotEmpty(preferentProductVo.getEndDate())){
				product.setEndTime(Long.parseLong(preferentProductVo
						.getEndDate()));// 结束时间
			}
			product.setDeliveryOption(DistributionType.HOME.getCode());// 配送方式(0送货上门,1自提2配送或自提二者皆)
			product.setBriefIntroduction(preferentProductVo
						.getDescription());// 商品简介
			
			product.setBuyKnow(preferentProductVo.getProductKnow());// 购买须知
			product.setIntroduction(preferentProductVo.getProductDetails());// 商品详情（介绍）
			product.setAuditState(AuditFlagEnum.NEW.getCode());// 待审核
			// 设置最初审核机构和结束机构
			ClientProductAuditOrgCodeVo productAuditVo = clientProductAuditService.
					getClientProductAuditByOrgCode(preferentProductVo.getClientId(),
							preferentProductVo.getOrgCode(), ProductTypeEnum.PREFERENTIAL.getCode());
			if(!StringUtil.isNotEmpty(productAuditVo.getStartAuditOrgCode()) 
					&& !StringUtil.isNotEmpty(productAuditVo.getEndAuditOrgCode())){
				msgVo.setResult(false);
				msgVo.setMsg("不能修改名优特惠商品");
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
			List<FileVo> fileList = preferentProductVo.getFiles();
			if(fileList != null && fileList.size() > 0){
				for(FileVo file : fileList){
					ProductImageVo imageVo = new ProductImageVo();
					imageVo.setTitle("名优特惠商品");
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
			LogCvt.info("名优特惠商品修改" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("名优特惠商品修改异常");
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
		productVoReq.setType(ProductTypeEnum.PREFERENTIAL.getCode());
		try {
			ProductInfoVo productInfo = productService.getMerchantProductDetail(productVoReq);
			if(productInfo != null){
				if(productInfo.getProduct() != null){
					DecimalFormat df = new DecimalFormat("#0.00");
					product.setMerchantId(productInfo.getProduct()
							.getMerchantId());// 商户Id
					product.setMerchantName(productInfo.getProduct()
							.getMerchantName());// 商户名称
					product.setOrgCode(productInfo.getProduct().getOrgCode());// 获取机构号
					product.setProductId(productInfo.getProduct()
							.getProductId());// 商品ID
					product.setProductName(productInfo.getProduct().getName());// 商品名称
					product.setProductFullName(productInfo.getProduct()
							.getFullName());// 商品全称
					if(StringUtil.isNotEmpty(productInfo.getProduct().getMarketPrice()+"")){
						BigDecimal markPrice = new BigDecimal(productInfo
								.getProduct().getMarketPrice());// 市场价
						product.setMarketPrice(df.format(markPrice).toString());// 市场价
					}
					if(StringUtil.isNotEmpty(productInfo.getProduct().getPrice()+"")){
						BigDecimal salePrice = new BigDecimal(productInfo
								.getProduct().getPrice());// 名优特惠价
						product.setSalePrice(df.format(salePrice).toString());// 名优特惠价
					}
					if(StringUtil.isNotEmpty(productInfo.getProduct().getDeliveryMoney()+"")){
						BigDecimal postage = new BigDecimal(productInfo
								.getProduct().getDeliveryMoney());// 邮费
						product.setPostage(df.format(postage).toString());
					}
					product.setStoreNum(productInfo.getProduct().getStore());// 库存数量
					if(StringUtil.isNotEmpty(productInfo.getProduct().getStartTime()+"")){
						product.setStartDate(productInfo.getProduct()
								.getStartTime() + "");// 开始时间
					}
					if(StringUtil.isNotEmpty(productInfo.getProduct().getEndTime()+"")){
						product.setEndDate(productInfo.getProduct()
								.getEndTime() + "");// 结束时间
					}
					product.setDescription(productInfo.getProduct()
							.getBriefIntroduction());// 商品简介
					product.setProductKnow(productInfo.getProduct()
							.getBuyKnow());// 购买须知
					product.setProductDetails(productInfo.getProduct()
							.getIntroduction());// 商品详情(介绍)
					product.setAuditState(productInfo.getProduct().getAuditState());
					product.setIsMarketable(productInfo.getProduct().getIsMarketable());
					product.setPhone(productInfo.getProduct().getPhone());
					// 审核备注
					product.setAuditComment(productInfo.getProduct().getAuditComment());
				}
				if(productInfo.getBuyLimit() != null){
					product.setLimitNum(productInfo.getBuyLimit().getMax());// 普通会员限购数量
				}else{
					product.setLimitNum(0);
				}
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
			LogCvt.info("名优特惠商品详情查询" + e.getMessage(), e);
		}
		
		return product;
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
		if(!StringUtil.isNotEmpty(vo.getMerchantId())){
			msgVo.setResult(false);
			msgVo.setMsg("发货商户不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getProductName())){
			msgVo.setResult(false);
			msgVo.setMsg("商品简称不能为空");
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
			msgVo.setMsg("特惠价不能为空");
			return msgVo;
		}
		if(StringUtil.isNotEmpty(vo.getMarketPrice()) && StringUtil.isNotEmpty(vo.getSalePrice())){
			Double marketPrice = Double.valueOf(vo.getMarketPrice());
			Double salePrice = Double.valueOf(vo.getSalePrice());
			if(salePrice > marketPrice){
				msgVo.setResult(false);
				msgVo.setMsg("特惠价不能大于市场价");
				return msgVo;
			}
		}
		if(vo.getStoreNum() == null){
			msgVo.setResult(false);
			msgVo.setMsg("库存数量不能为空");
			return msgVo;
		}
		if(vo.getLimitNum() == null){
			msgVo.setResult(false);
			msgVo.setMsg("限购数量不能为空");
			return msgVo;
		}
		if(vo.getLimitNum() != null && vo.getStoreNum() != null){
			if(vo.getLimitNum() > vo.getStoreNum()){
				msgVo.setResult(false);
				msgVo.setMsg("限购数量不能大于库存数量");
				return msgVo;
			}
		}
		if(!StringUtil.isNotEmpty(vo.getStartDate())||!StringUtil.isNotEmpty(vo.getEndDate())){
			msgVo.setResult(false);
			msgVo.setMsg("销售时间不能为空");
			return msgVo;
		}
		if(StringUtil.isNotEmpty(vo.getStartDate()) && StringUtil.isNotEmpty(vo.getEndDate())){
			if(Long.valueOf(vo.getStartDate()) > Long.valueOf(vo.getEndDate())){
				msgVo.setResult(false);
				msgVo.setMsg("销售开始时间不能大于销售结束时间");
				return msgVo;
			}
		}
//		if(!StringUtil.isNotEmpty(vo.getDescription())){
//			msgVo.setResult(false);
//			msgVo.setMsg("副标题不能为空");
//			return msgVo;
//		}
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
//		if(!StringUtil.isNotEmpty(vo.getPostage())){
//			msgVo.setResult(false);
		// msgVo.setMsg("运费设置不能为空");
//			return msgVo;
//		}
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
	 * getPreferentListExport:(名优特惠商品下载优化).
	 *
	 * @author wufei
	 * 2015-9-7 上午10:53:57
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	public Map<String, Object> getPreferentListExport(ProductVoReq voReq) throws TException, ParseException {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ProductSearchVo productSearchVo = new ProductSearchVo();
		// 请求参数
		reqArgument(voReq, productSearchVo);
		ExportResultRes  result = productService.downProducts(productSearchVo, ProductTypeEnum.PREFERENTIAL.getCode());
		if(result.getResultVo() != null && StringUtil.isNotBlank(result.getUrl())){
			resMap.put("url", result.getUrl());
			resMap.put("code", result.getResultVo().getResultCode());
			resMap.put("message", result.getResultVo().getResultDesc());
		}else{
			resMap.put("code", "9999");
			resMap.put("message", "名优特惠商品导出失败");
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
		if (StringUtil.isNotBlank(voReq.getIsMarketable())) {
			productSearchVo.setIsMarketable(voReq.getIsMarketable());// 上下架状态
		}
		if (StringUtil.isNotBlank(voReq.getMerchantName())) {
			productSearchVo.setMerchantName(voReq.getMerchantName());// 商户名称
		}
		if (StringUtil.isNotBlank(voReq.getProductName())) {
			productSearchVo.setProductName(voReq.getProductName());// 商品名称
		}
		if (StringUtil.isNotBlank(voReq.getAuditState())) {
			productSearchVo.setAuditState(voReq.getAuditState());// 审核状态
		}
		if (StringUtil.isNotBlank(voReq.getAuditStartTime())) {
			productSearchVo.setAuditStartTime(DateUtil.dateToTheDayBeforeDawn(voReq.getAuditStartTime()));// 审核开始时间
		}
		if (StringUtil.isNotBlank(voReq.getAuditEndTime())) {
			productSearchVo.setAuditEndTime(DateUtil.dateToTheDayAfterDawn(voReq.getAuditEndTime()));// 审核结束时间
		}
	}
}
