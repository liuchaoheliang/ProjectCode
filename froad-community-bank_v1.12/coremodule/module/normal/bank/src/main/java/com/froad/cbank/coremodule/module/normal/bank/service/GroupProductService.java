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

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.controller.GroupProductController;
import com.froad.cbank.coremodule.module.normal.bank.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.BessDataEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.IsMarketableEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProductTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankAuditProductListResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankAuditProductReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankAuditProductResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankTaskResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BaseVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.FileVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.GroupProductVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductCategoryReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductInfoVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductVoReq;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.FallowExecuteService;
import com.froad.thrift.service.FallowQueryService;
import com.froad.thrift.service.MerchantTypeService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.ProductCategoryService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.ActorVo;
import com.froad.thrift.vo.AuditInstanceDetailVo;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.ExecuteTaskReqVo;
import com.froad.thrift.vo.ExecuteTaskResVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.GetPendAuitListPageVo;
import com.froad.thrift.vo.GetPendAuitListReqVo;
import com.froad.thrift.vo.GetTaskOverviewReqVo;
import com.froad.thrift.vo.GetTaskOverviewResVo;
import com.froad.thrift.vo.GetTaskReqVo;
import com.froad.thrift.vo.GetTaskResVo;
import com.froad.thrift.vo.MerchantTypeVo;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductBriefInfoVo;
import com.froad.thrift.vo.ProductBriefPageVo;
import com.froad.thrift.vo.ProductBriefVo;
import com.froad.thrift.vo.ProductCategoryVo;
import com.froad.thrift.vo.ProductDetailInfo;
import com.froad.thrift.vo.ProductDetailVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.ProductSearchVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.TaskListResVo;

import net.sf.json.JSONObject;

/**
 * 团购商品管理
 * @author ylchu
 *
 */
@Service
public class GroupProductService {

	@Resource
	ProductService.Iface productService;
	@Resource
	ProductCategoryService.Iface productCategoryService;
	@Resource
	FallowQueryService.Iface fallowQueryService;
	@Resource
	FallowExecuteService.Iface fallowExecuteService;
	@Resource
	BankOperatorService.Iface bankOperatorService;
	@Resource
	OrgService.Iface orgService;
	@Resource
	MerchantUserService.Iface merchantUserService;
	@Resource
	MerchantTypeService.Iface merchantTypeService;
	/**
	 * 团购
	 * @param filter
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws TException 
	 */
	public QueryResult<GroupProductVoRes> findPageByConditions(String clientId,
			String filter, int pageNumber, int pageSize, int lastPageNumber,
			Long firstRecordTime, Long lastRecordTime) throws TException {
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setLastPageNumber(lastPageNumber);
		page.setFirstRecordTime(firstRecordTime);
		page.setLastRecordTime(lastRecordTime);

		ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq();
		productFilterVoReq.setClientId(clientId);
		if (StringUtils.isNoneBlank(filter)) {
			productFilterVoReq.setFilter(filter);
		}

		QueryResult<GroupProductVoRes> queryVo = new QueryResult<GroupProductVoRes>();
		List<GroupProductVoRes> groupProductList = new ArrayList<GroupProductVoRes>();
		ProductBriefPageVo productBriefPage = null;
		try {
			productBriefPage = productService
					.findMerchantProductsByPage(productFilterVoReq, page);
			List<ProductBriefInfoVo> productBriefInfoList = productBriefPage
					.getProductBriefInfoVoList();
			if (productBriefInfoList != null
					&& productBriefInfoList.size() > 0) {
				for (ProductBriefInfoVo productBriefInfo : productBriefInfoList) {
					GroupProductVoRes groupProduct = new GroupProductVoRes();
					groupProduct.setAuditStateName(
							productBriefInfo.getAuditStateName());
					groupProduct.setProductId(productBriefInfo.getProductId());// 商品ID
					groupProduct.setOrgName(productBriefInfo.getOrgName());// 所属机构
					groupProduct.setProductName(productBriefInfo.getName());// 商品名称
					groupProduct.setMerchantName(
							productBriefInfo.getMerchantName());// 商户名称
					groupProduct.setStore(
							Integer.toString(productBriefInfo.getStore()));// 库存数量
					groupProduct.setConsumeStartDate(DateUtil.formatDate(
							new Date(productBriefInfo.getStartTime()),
							DateUtil.DATE_FORMAT_01));// 开始消费日期
					groupProduct.setConsumeEndDate(DateUtil.formatDate(
							new Date(productBriefInfo.getEndTime()),
							DateUtil.DATE_FORMAT_01));// 最后消费日期
					groupProduct
							.setAuditState(productBriefInfo.getAuditState());// 审核状态
					if (StringUtil.equals(productBriefInfo.getAuditState(),
							AuditFlagEnum.NEW.getCode())) {
						groupProduct.setAuditStateName(
								AuditFlagEnum.NEW.getDescription());// 审核状态
					} else if (StringUtil.equals(
							productBriefInfo.getAuditState(),
							AuditFlagEnum.ACCEPTED.getCode())) {
						groupProduct.setAuditStateName(
								AuditFlagEnum.ACCEPTED.getDescription());// 审核状态
					} else if (StringUtil.equals(
							productBriefInfo.getAuditState(),
							AuditFlagEnum.REJECTED.getCode())) {
						groupProduct.setAuditStateName(
								AuditFlagEnum.REJECTED.getDescription());// 审核状态
					} else if (StringUtil.equals(
							productBriefInfo.getAuditState(),
							AuditFlagEnum.NO_NEW.getCode())) {
						groupProduct.setAuditStateName(
								AuditFlagEnum.NO_NEW.getDescription());// 审核状态
					} else {
						groupProduct.setAuditStateName("");
					}
					groupProduct.setIsMarketable(
							productBriefInfo.getIsMarketable());
					if (StringUtil.equals(productBriefInfo.getIsMarketable(),
							IsMarketableEnum.NO.getCode())) {
						groupProduct.setIsMarketableName(
								IsMarketableEnum.NO.getDescription());
					} else if (StringUtil.equals(
							productBriefInfo.getIsMarketable(),
							IsMarketableEnum.UP.getCode())) {
						groupProduct.setIsMarketableName(
								IsMarketableEnum.UP.getDescription());
					} else if (StringUtil.equals(
							productBriefInfo.getIsMarketable(),
							IsMarketableEnum.DOWN.getCode())) {
						groupProduct.setIsMarketableName(
								IsMarketableEnum.DOWN.getDescription());
					} else if (StringUtil.equals(
							productBriefInfo.getIsMarketable(),
							IsMarketableEnum.isDeleted.getCode())) {
						groupProduct.setIsMarketableName(
								IsMarketableEnum.isDeleted.getDescription());
					} else if (StringUtil.equals(
							productBriefInfo.getIsMarketable(),
							IsMarketableEnum.disOffShelf.getCode())) {
						groupProduct.setIsMarketableName(
								IsMarketableEnum.disOffShelf.getDescription());
					} else {
						groupProduct.setIsMarketableName("");
					}
					// 审核时间
					groupProduct.setAuditTime(productBriefInfo.getAuditTime());
					groupProduct.setCategoryName(
							productBriefInfo.getCategoryName());
					// 团购价格
					groupProduct.setPrice(
							Double.toString(productBriefInfo.getPrice()));
					groupProductList.add(groupProduct);
				}
			}
			PageVo pageRes = productBriefPage.getPage();
			// LogCvt.info("1===========返回的pageNumber:" +
			// pageRes.getPageNumber());
			if (pageRes != null) {
				queryVo.setPageCount(pageRes.getPageCount());
				queryVo.setTotalCount(pageRes.getTotalCount());
				queryVo.setPageNumber(pageRes.getPageNumber());
				queryVo.setLastPageNumber(pageRes.getLastPageNumber());
				queryVo.setFirstRecordTime(pageRes.getFirstRecordTime());
				queryVo.setLastRecordTime(pageRes.getLastRecordTime());
			}
		} catch (Exception e) {
			LogCvt.info("团购商品列表条件查询" + e.getMessage(), e);
		}

		queryVo.setResult(groupProductList);
		return queryVo;
	}

	/**
	 * 
	 * copyProductValue:团购商品待审核返回数据封装
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月25日 下午3:48:15
	 * @param groupProduct
	 *            目标
	 * @param productBriefVo
	 *            数据源
	 */
	@SuppressWarnings("unused")
	private void copyProductValue(GroupProductVoRes groupProduct,
			ProductBriefVo productBriefVo) {
		groupProduct.setProductId(productBriefVo.getProductId());// 商品ID
		groupProduct.setProductName(productBriefVo.getName());// 商品名称
		groupProduct.setMerchantName(productBriefVo.getMerchantName());// 商户名称
		groupProduct
				.setStore(Integer.toString(productBriefVo
					.getStore()));// 库存数量
			groupProduct.setConsumeStartDate(DateUtil.formatDate(
				new Date(productBriefVo.getStartTime()),
					DateUtil.DATE_FORMAT_01));//开始消费日期
			groupProduct.setConsumeEndDate(DateUtil.formatDate(
				new Date(productBriefVo.getEndTime()),
					DateUtil.DATE_FORMAT_01));//最后消费日期
		groupProduct.setAuditState(productBriefVo.getAuditState());// 审核状态码
		groupProduct.setAuditStateName(productBriefVo.getAuditStateName());// 审核状态名称
		groupProduct.setIsMarketable(productBriefVo.getIsMarketable());
		if (StringUtil.equals(productBriefVo.getIsMarketable(),
				IsMarketableEnum.NO.getCode())) {
				groupProduct.setIsMarketableName(IsMarketableEnum.NO.getDescription());
		} else if (StringUtil.equals(productBriefVo.getIsMarketable(),
				IsMarketableEnum.UP.getCode())) {
				groupProduct.setIsMarketableName(IsMarketableEnum.UP.getDescription());
		} else if (StringUtil.equals(productBriefVo.getIsMarketable(),
				IsMarketableEnum.DOWN.getCode())) {
				groupProduct.setIsMarketableName(IsMarketableEnum.DOWN.getDescription());
		} else if (StringUtil.equals(productBriefVo.getIsMarketable(),
				IsMarketableEnum.isDeleted.getCode())) {
				groupProduct.setIsMarketableName(IsMarketableEnum.isDeleted.getDescription());
		} else if (StringUtil.equals(productBriefVo.getIsMarketable(),
				IsMarketableEnum.disOffShelf.getCode())) {
				groupProduct.setIsMarketableName(IsMarketableEnum.disOffShelf.getDescription());
		} else {
			groupProduct.setIsMarketableName("");
		}
			//审核时间
		groupProduct.setAuditTime(productBriefVo.getAuditTime());
		groupProduct.setCategoryName(productBriefVo.getCategoryName());
			// 团购价格
		groupProduct.setPrice(Double.toString(productBriefVo.getPrice()));
	}
	
	/**
	 * 团购商品详情
	 * @param productId
	 * @return
	 */
	public ProductInfoVoReq productDetail(String clientId, String productId){
		ProductInfoVoReq groupProductVo = new ProductInfoVoReq();
		ProductOperateVoReq productVoReq = new ProductOperateVoReq();
		productVoReq.setClientId(clientId);
		productVoReq.setProductId(productId);
		productVoReq.setType(ProductTypeEnum.GROUP.getCode());
		try {
			ProductInfoVo productInfo = productService.getMerchantProductDetail(productVoReq);
			if(productInfo != null){
				if(productInfo.getProduct() != null){
					DecimalFormat df = new DecimalFormat("#0.00");
					groupProductVo.setProductId(productInfo.getProduct().getProductId());//商品ID
					groupProductVo.setProductName(productInfo.getProduct().getName());//商品名称
					groupProductVo.setProductFullName(productInfo.getProduct().getFullName());
					groupProductVo.setMerchantName(productInfo.getProduct().getMerchantName());//商户名称
					groupProductVo.setOrgName(productInfo.getProduct().getOrgName());//机构名称
//					groupProductVo.setAddress(productInfo.getProduct().getAddress());//地址
					BigDecimal markPrice = new BigDecimal(productInfo.getProduct().getMarketPrice());//市场价
					groupProductVo.setMarketPrice(df.format(markPrice).toString());//市场价
					BigDecimal salePrice = new BigDecimal(productInfo.getProduct().getPrice());//团购价
					groupProductVo.setGroupPrice(df.format(salePrice).toString());//团购价
					groupProductVo.setStoreNum(productInfo.getProduct().getStore());//库存数量
					groupProductVo.setConsumeStartDate(DateUtil.formatDate(
							new Date(productInfo.getProduct().getExpireStartTime()),
							DateUtil.DATE_FORMAT_01));//券开始时间
					groupProductVo.setConsumeEndDate(DateUtil.formatDate(
							new Date(productInfo.getProduct().getExpireEndTime()),
							DateUtil.DATE_FORMAT_01));//券结束时间
					groupProductVo.setStartDate(DateUtil.formatDate(
							new Date(productInfo.getProduct().getStartTime()),
							DateUtil.DATE_FORMAT_01));//团购开始时间
					groupProductVo.setEndDate(DateUtil.formatDate(
							new Date(productInfo.getProduct().getEndTime()),
							DateUtil.DATE_FORMAT_01));//团购结束时间
					groupProductVo.setContractEndtime(DateUtil.formatDate(
							new Date(productInfo.getProduct().getContractEndtime()),
							DateUtil.DATE_FORMAT_01));
					groupProductVo.setProductKnow(productInfo.getProduct().getBuyKnow());//兑换须知
					groupProductVo.setDescription(productInfo.getProduct().getBriefIntroduction());//商品简介
					groupProductVo.setProductDetails(productInfo.getProduct().getIntroduction());//商品详情(介绍)
					groupProductVo.setAuditState(productInfo.getProduct().getAuditState());
					groupProductVo.setIsMarketable(productInfo.getProduct().getIsMarketable());
					groupProductVo.setAuditComment(productInfo.getProduct().getAuditComment());
					
				}
				if(productInfo.getBuyLimit() != null){
					groupProductVo.setLimitNum(productInfo.getBuyLimit().getMax());//普通会员限购数量
				}else{
					groupProductVo.setLimitNum(0);
				}
				
				//商品图片
				ArrayList<FileVo> imageList = new ArrayList<FileVo>();
				List<ProductImageVo> productImageList = productInfo.getImage();
				if(productImageList != null && productImageList.size() > 0){
					for(ProductImageVo productImage : productImageList){
						FileVo fileImage = new FileVo();
						fileImage.setTitle(productImage.getTitle());//图片标题
						fileImage.setSource(productImage.getSource());//图片原图地址
						fileImage.setLarge(productImage.getLarge());//图片大图地址
						fileImage.setMedium(productImage.getMedium());//图片中图地址
						fileImage.setThumbnail(productImage.getThumbnail());//图片小图地址
						imageList.add(fileImage);
					}
				}
				groupProductVo.setFiles(imageList);
				
			}
		} catch (TException e) {
			LogCvt.info("团购商品详情查询"+e.getMessage(), e);
		}
		return groupProductVo;
	}
	
	/**
	 * 查询商品分类query_product_categorye
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String, Object> query_product_categorye(BaseVo po) {
		Map<String, Object> map = new HashMap<String, Object>();
		ProductCategoryVo productCategoryVo=new ProductCategoryVo();
		productCategoryVo.setClientId(po.getClientId());
		productCategoryVo.setParentId(0);
		List<ProductCategoryVo> list;
		try {
			list = productCategoryService.queryManageProductCategorys(po.getClientId(),0);
			if(list!=null&&list.size() > 0){
				List<ProductCategoryReq> listRes=new ArrayList<ProductCategoryReq>();
				for(ProductCategoryVo vo : list){
//					if(vo.getParentId() == 0 ){ 
//						continue;
//					}
					ProductCategoryReq res=new ProductCategoryReq();
//					TargetObjectFormat.getOutletVo(vo, res);
					res.setId(vo.getId());
					res.setClientId(vo.getClientId());
					res.setParentId(vo.getParentId());
					res.setName(vo.getName());
					res.setTreePath(vo.getTreePath());
					listRes.add(res);
				}
				map.put("categoryeList", listRes);
			}
		} catch (TException e) {
			LogCvt.info("商品分类查询:"+e.getMessage(), e);
		}
		
		return map;
	}
	
	/**
	 * 
	 * getProListExport: 团购商品列表下载优化
	 *
	 * @author 明灿
	 * 2015年9月6日 上午11:33:30
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	public Map<String, Object> getGroupListExport(ProductVoReq voReq) throws TException, ParseException {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ProductSearchVo productSearchVo = new ProductSearchVo();
		// 请求参数
		reqArgument(voReq, productSearchVo);
		ExportResultRes  result = productService.downProducts(productSearchVo, ProductTypeEnum.GROUP.getCode());
		if(result.getResultVo() != null && StringUtil.isNotBlank(result.getUrl())){
			resMap.put("url", result.getUrl());
			resMap.put(ResultEnum.CODE.getCode(),
					result.getResultVo().getResultCode());
			resMap.put(ResultEnum.MESSAGE.getCode(),
					result.getResultVo().getResultDesc());
		}else{
			resMap.put(ResultEnum.FAIL.getCode(),
					ResultEnum.FAIL.getDescrition());
			resMap.put(ResultEnum.MESSAGE.getCode(), "团购商品导出失败");
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

	/**
	 * 
	 * getAuditProductList:团购商品待审核列表
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月25日 下午5:31:49
	 * @param auditReqVo
	 * @param pageVo
	 * @return
	 * @throws TException
	 * @throws ParseException
	 */
	public Map<String, Object> getAuditProductList(
			GetPendAuitListReqVo auditReqVo, PageVo pageVo)
					throws TException, ParseException {
		Map<String, Object> resMap = new HashMap<String, Object>();
		LogCvt.info("待审核团购列表请求参数:" + JSON.toJSONString(auditReqVo));
		GetPendAuitListPageVo pendAuitList = fallowQueryService.getPendAuitList(auditReqVo, pageVo);
		LogCvt.info("待审核团购列表返回:" + JSON.toJSONString(pendAuitList));
		List<BankAuditProductListResVo> productList = new ArrayList<BankAuditProductListResVo>();
		BankAuditProductListResVo productVo = null;
		if (pendAuitList != null) {
			ResultVo resultVo = pendAuitList.getResult();
			if (resultVo != null){
				if (resultVo.getResultCode()
						.equals(ResultEnum.SUCCESS.getDescrition())) {
				List<AuditInstanceDetailVo> auditDetailList = pendAuitList
						.getAuditDetailList();
				if (auditDetailList != null && auditDetailList.size() > 0) {
					for (AuditInstanceDetailVo auditInstanceDetailVo : auditDetailList) {
						productVo = new BankAuditProductListResVo();
						/**
						 * copy value
						 */
						productVo = this.copyAuditValue(productVo,
								auditInstanceDetailVo,
								auditReqVo.getOrigin().getClientId());
						productList.add(productVo);
					}
				}
				resMap.put("productList", productList);
			}else{
				resMap.put(ResultEnum.CODE.getCode(), resultVo.getResultCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						resultVo.getResultDesc());
				}
			}
			this.setPagesValueToResMap(resMap, pendAuitList.getPage());
		}
		return resMap;
	}
	
	/**
	 * 
	 * copyAuditValue:团购商品待审核信息
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月25日 下午6:06:48
	 * @param productVo
	 * @param auditInstanceDetailVo
	 * @return
	 * @throws ParseException
	 * @throws TException
	 */
	private BankAuditProductListResVo copyAuditValue(
			BankAuditProductListResVo productVo,
			AuditInstanceDetailVo auditInstanceDetailVo, String clientId)
					throws ParseException, TException {
		String josnData = auditInstanceDetailVo.getBessData();
		JSONObject jsonObject = JSONObject.fromObject(josnData);
		productVo.setAuditId(auditInstanceDetailVo.getInstanceId());// 审核流水号
		productVo.setAuditType(auditInstanceDetailVo.getProcessTypeDetail());// 审核类型
		productVo.setCreateTime(auditInstanceDetailVo.getCreateTime());// 创建时间
		ActorVo nextActor = auditInstanceDetailVo.getNextActor();
		if (nextActor != null) {
			productVo.setTaskId(nextActor.getTaskId());// 任务id
		}
		if (jsonObject != null) {
			// productVo.setProductId(this.getJsonString(jsonObject,
			// BessDataEnum.productId.getKey()));// 商品主键
			productVo.setProductName(this.getJsonString(jsonObject, BessDataEnum.productName.getKey()));// 商户名称
			productVo.setCategory(this.getJsonString(jsonObject,
					BessDataEnum.typeInfo.getKey()));// 商户类型
			/**
			 * 转化商户类型
			 */
			this.getCategory(productVo, clientId);
			productVo.setMerchantName(this.getJsonString(jsonObject,
					BessDataEnum.merchantName.getKey()));// 商户名称
			// productVo.setOrgCode(this.getJsonString(jsonObject,
			// BessDataEnum.userOrgCode.getKey()));// 操作员
			if (jsonObject.containsKey(BessDataEnum.endTime.getKey())) {
				productVo.setEndDate(
						jsonObject.getLong(BessDataEnum.endTime.getKey()));// 团购结束时间
			}
			if (jsonObject.containsKey(BessDataEnum.startTime.getKey())) {
				productVo.setStartDate(
						jsonObject.getLong(BessDataEnum.startTime.getKey()));// 团购开始时间
			}
			productVo.setOrgCode(
					this.getOrgName(auditInstanceDetailVo.getOrgCode()));// 所属机构
			productVo.setProductCategory(this.getJsonString(jsonObject,
					BessDataEnum.categoryInfo.getKey()));// 商品所属分类
			/**
			 * 转化商品分类
			 */
			productVo.setProductCategory(this.getProductCategory(
					productVo.getProductCategory(), clientId));
		}
		productVo.setProductId(auditInstanceDetailVo.getBessId());// 商品主键
		productVo.setTaskId(this.getTaskId(auditInstanceDetailVo));
		return productVo;
	}

	private String getProductCategory(String productCategory, String clientId)
			throws TException {
		List<String> ids = new ArrayList<String>();
		StringBuffer value = new StringBuffer();
		if (StringUtil.isNotBlank(productCategory)) {
			String[] temp = productCategory.split(" ");
			for (String str : temp) {
				ids.add(str);
			}
			List<ProductCategoryVo> productCategorys = productCategoryService
					.getProductCategoryByIds(clientId, ids);
			LogCvt.info("待审核团购列表-商品分类:" + JSON.toJSONString(productCategorys));
			if (productCategorys != null && productCategorys.size() > 0) {
				for (int i = 0; i < productCategorys.size(); i++) {
					if (i != (productCategorys.size() - 1)) {
						value.append(productCategorys.get(i).getName())
								.append("->");
					} else {
						value.append(productCategorys.get(i).getName());
					}
				}
			}
		}
		return value.toString();
	}

	/**
	 * 
	 * getCategory:转化商户类型
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月26日 下午3:51:41
	 * @param productVo
	 * @param clientId
	 * @throws TException
	 */
	private void getCategory(BankAuditProductListResVo productVo,
			String clientId) throws TException {
		if (StringUtil.isNotBlank(productVo.getCategory())) {
			String[] strs = productVo.getCategory()
					.substring(1, productVo.getCategory().length() - 1)
					.split(",");
			StringBuffer merchantTypes = new StringBuffer();
			for (int i = 0; i < strs.length; i++) {
				MerchantTypeVo merchantType = merchantTypeService
						.getMerchantTypeById(Long.parseLong(strs[i]),
								clientId);
				LogCvt.info("**********商户类型***********"
						+ JSON.toJSONString(merchantType));
				merchantTypes.append(merchantType.getTypeName());
				merchantTypes.append(",");
				productVo.setCategory(merchantTypes.substring(0,
						merchantTypes.length() - 1));
			}
		}
	}

	/**
	 * 
	 * getOrgName:根据orgCode获取机构名称
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月25日 下午2:54:29
	 * @param orgCode
	 * @return
	 * @throws TException
	 */
	private String getOrgName(String orgCode) throws TException {
		String orgName = "";
		OrgVo paramOrgVo = new OrgVo();
		paramOrgVo.setOrgCode(orgCode);
		List<OrgVo> org = orgService.getOrg(paramOrgVo);
		if (org != null && org.size() > 0) {
			LogCvt.info("待审核团购商品列表-获取机构名称:" + JSON.toJSONString(org.get(0)));
			if (org.get(0) != null) {
				orgName = org.get(0).getOrgName();
			}
		} else {
			LogCvt.info("待审核团购商品列表-获取机构名称:null");
		}
		return orgName;
	}

	/**
	 * 
	 * getJsonString:获取json数字中key对应value值
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月22日 下午12:33:39
	 * @param jsonObject
	 * @param key
	 * @return
	 */
	private String getJsonString(JSONObject jsonObject, String key) {
		String value = null;
		if (jsonObject.containsKey(key)) {
			value = jsonObject.getString(key) == null ? null
					: jsonObject.getString(key).toString();
		}
		return value;
	}
	/**
	 * 
	 * getTaskId:获取下一个审核的任务id
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月28日 上午10:34:39
	 * @param auditInstanceDetailVo
	 * @return
	 */
	private String getTaskId(AuditInstanceDetailVo auditInstanceDetailVo) {
		String taskId = "";
		ActorVo nextActor = auditInstanceDetailVo.getNextActor();
		if (nextActor != null) {
			taskId = nextActor.getTaskId();
		}
		return taskId;
	}

	/**
	 * 
	 * setPagesValueToResMap:封装分页信息
	 *
	 * @author 明灿 2015年9月29日 上午10:42:10
	 * @param reMap
	 *            返回前端的Map集合
	 * @param pageRes
	 *            server返回的page信息
	 *
	 */
	private void setPagesValueToResMap(Map<String, Object> reMap,
			PageVo pageVo) {
		if (pageVo != null) {
			reMap.put("pageCount", pageVo.getPageCount());
			reMap.put("totalCount", pageVo.getTotalCount());
			reMap.put("pageNumber", pageVo.getPageNumber());
			reMap.put("lastPageNumber", pageVo.getLastPageNumber());
			reMap.put("firstRecordTime", pageVo.getFirstRecordTime());
			reMap.put("lastRecordTime", pageVo.getLastRecordTime());
		} else {
			reMap.put("pageCount", 0);
			reMap.put("totalCount", 0);
			reMap.put("pageNumber", 1);
			reMap.put("lastPageNumber", 0);
			reMap.put("firstRecordTime", 0);
			reMap.put("lastRecordTime", 0);
		}
	}

	/**
	 * 
	 * auditProdcut:审核团购商品
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月28日 下午3:16:57
	 * @param reqVo
	 * @return
	 * @throws TException
	 */
	public Map<String, Object> auditProdcut(BankAuditProductReqVo reqVo,
			OriginVo origin) throws TException {
		LogCvt.info("团购商品审核请求参数:" + JSON.toJSONString(reqVo));
		Map<String, Object> resMap = new HashMap<String, Object>();
		ExecuteTaskReqVo req = new ExecuteTaskReqVo();
		// 审核请求参数封装
		this.requestParams(req, reqVo);
		req.setOrigin(origin);
		// 银行端orgCode和orgId两个值一样
		req.setOrgCode(origin.getOrgId());
		// 请求server接口
		ExecuteTaskResVo taskResVo = fallowExecuteService.executeTask(req);
		LogCvt.info("团购商品审核请求返回数据:" + JSON.toJSONString(taskResVo));
		if (taskResVo != null && taskResVo.getResult() != null) {
			resMap.put(ResultEnum.CODE.getCode(),
					taskResVo.getResult().getResultCode());
			resMap.put(ResultEnum.MESSAGE.getCode(),
					taskResVo.getResult().getResultDesc());
			if (EnumTypes.success
					.equals(taskResVo.getResult().getResultCode())) {
				resMap.put("taskId", taskResVo.getTaskId());
			}
		} else {
			resMap.put(ResultEnum.FAIL.getCode(),
					ResultEnum.FAIL.getDescrition());
			resMap.put(ResultEnum.MESSAGE.getCode(), "团购商品审核异常");
		}
		return resMap;
	}

	/**
	 * 
	 * requestParams:执行审核请求参数封装
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月28日 下午3:55:32
	 * @param req
	 * @param reqVo
	 */
	private void requestParams(ExecuteTaskReqVo req,
			BankAuditProductReqVo reqVo) {
		req.setAuditState(reqVo.getAuditState());// 审核状态
		req.setBessId(reqVo.getProductId());// 业务id
		req.setInstanceId(reqVo.getInstanceId());// 审核流水号
		req.setRemark(reqVo.getRemark());// 审核备注
		req.setTaskId(reqVo.getTaskId());// 任务id
	}

	/**
	 * 
	 * getProductDetail:待审核商品详情
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月28日 下午5:26:14
	 * @param originVo
	 * @param auditNumber
	 * @param productId
	 * @param type
	 * @return
	 * @throws TException
	 */
	public Map<String, Object> getProductDetail(OriginVo originVo,
			String auditNumber, String productId, String type)
					throws TException {
		StringBuffer log = new StringBuffer();
		log.append("团购商品待审核详情请求参数: 审核流水号=").append(auditNumber)
				.append("; 商品id=").append(productId).append("; 请求类型=")
				.append(type);
		LogCvt.info(log.toString());
		Map<String, Object> resMap = new HashMap<String, Object>();
		BankAuditProductResVo auditProductResVo = new BankAuditProductResVo();
		// 商品详情
		ProductDetailInfo productDetailInfo = productService
				.getProductDetail(productId);
		LogCvt.info("商品详情返回数据:" + JSON.toJSONString(productDetailInfo));
		if (productDetailInfo != null) {
			ProductDetailVo productDetailVo = productDetailInfo
					.getProductDetailVo();
			// copy the new value
			auditProductResVo = this.copyProductDetailValue(auditProductResVo,
					productDetailVo);
			if (GroupProductController.UPDATE.equals(type)) {
				ProductDetailVo oldProductDetailVo = productDetailInfo
						.getOldProductDetailVo();
				// copy old product value
				if (oldProductDetailVo != null) {
					auditProductResVo = this.copyOldProductDetailValue(
							auditProductResVo, oldProductDetailVo);
				}
			}
		}
		// 审核详情
		GetTaskOverviewReqVo req = new GetTaskOverviewReqVo();
		req.setInstanceId(auditNumber);
		req.setOrigin(originVo);
		GetTaskOverviewResVo taskOverview = fallowQueryService
				.getTaskOverview(req);
		LogCvt.info("审核详情返回数据:" + JSON.toJSONString(taskOverview));
		this.copyTaskDetailValue(auditProductResVo, taskOverview);
		String creator = taskOverview.getCreator();
		LogCvt.info("任务概括创建人id:" + creator);
		if (StringUtil.isNotBlank(creator)) {
			MerchantUserVo merchantUserVo = merchantUserService
					.getMerchantUserById(Long.parseLong(creator));
			LogCvt.info("任务概括创建人返回数据:" + JSON.toJSONString(merchantUserVo));
			if (merchantUserVo != null) {
				auditProductResVo.setCreater(merchantUserVo.getUsername());// 创建人
			}
		}
		// 创建机构
		auditProductResVo
				.setOrgCode(this.getOrgName(auditProductResVo.getOrgCode()));// 创建机构
		// 审核列表
		GetTaskReqVo reqTask = new GetTaskReqVo();
		reqTask.setInstanceId(auditNumber);
		reqTask.setOrigin(originVo);
		GetTaskResVo taskList = fallowQueryService.getTaskList(reqTask);
		LogCvt.info("审核列表返回数据:" + JSON.toJSONString(taskList));
		this.copyTaskListValue(auditProductResVo, taskList,
				originVo.getClientId());
		resMap.put("productDetail", auditProductResVo);
		return resMap;
	}

	/**
	 * 
	 * @Title: getOrgNameByOrgCode
	 * @Description: 返回机构名称
	 * @param clientId
	 * @param orgCode
	 * @return @throws TException
	 * @return String @throws
	 */
	private String getOrgNameByOrgCode(String clientId, String orgCode)
			throws TException {
		String orgName = "";
		OrgVo orgVo = orgService.getOrgById(clientId, orgCode);
		LogCvt.info("门店审核详情请求orgName返回:" + JSON.toJSONString(orgVo));
		if (orgVo != null) {
			orgName = orgVo.getOrgName();
		}
		return orgName;
	}

	/**
	 * 
	 * copyTaskListValue:拷贝审核任务列表
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月28日 下午6:47:11
	 * @param auditProductResVo
	 * @param taskList
	 * @return
	 * @throws TException
	 * @throws NumberFormatException
	 */
	private BankAuditProductResVo copyTaskListValue(
			BankAuditProductResVo auditProductResVo, GetTaskResVo taskList,
			String clientId) throws NumberFormatException, TException {
		if (taskList != null) {
			List<TaskListResVo> taskListRes = taskList.getTaskListRes();
			List<BankTaskResVo> bankTaskList = new ArrayList<BankTaskResVo>();
			BankTaskResVo bankTaskResVo = null;
			if (taskListRes != null && taskListRes.size() > 0) {
				for (TaskListResVo taskListResVo : taskListRes) {
					bankTaskResVo = new BankTaskResVo();
					bankTaskResVo.setAuditor(getAuditorNameByUserId(clientId,
							taskListResVo.getAuditor()));// 审核人userId
					bankTaskResVo.setAuditOrgName(getOrgNameByOrgCode(clientId,
							taskListResVo.getOrgId()));// 审核机构
					bankTaskResVo.setAuditStatus(taskListResVo.getAuditState());// 状态
					bankTaskResVo.setAuditTime(taskListResVo.getAuditTime());// 审核时间
					bankTaskResVo.setComment(taskListResVo.getRemark());// 备注
					bankTaskResVo.setCreateTime(taskListResVo.getCreateTime());// 创建时间
					bankTaskResVo.setTaskId(taskListResVo.getTaskId());// 任务流水号
					bankTaskList.add(bankTaskResVo);
				}
			}
			auditProductResVo.setTaskList(bankTaskList);
		}
		return auditProductResVo;
	}

	/**
	 * 
	 * copyTaskDetailValeu:拷贝审核任务详情
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月28日 下午6:46:01
	 * @param auditProductResVo
	 * @param taskOverview
	 * @return
	 */
	private BankAuditProductResVo copyTaskDetailValue(
			BankAuditProductResVo auditProductResVo,
			GetTaskOverviewResVo taskOverview) {
		auditProductResVo.setAuditNumber(taskOverview.getInstanceId());// 审核流水号
		auditProductResVo.setProductId(taskOverview.getBessId());// 商品编号
		auditProductResVo.setAuditStatus(taskOverview.getAuditState());// 审核状态
		auditProductResVo.setCreateTime(taskOverview.getCreateTime());// 创建时间
		return auditProductResVo;
	}

	/**
	 * 
	 * copyOldProductDetailValue:拷贝商品旧属性
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月28日 下午6:43:16
	 * @param auditProductResVo
	 * @param oldProductDetailVo
	 * @return
	 * @throws TException
	 */
	private BankAuditProductResVo copyOldProductDetailValue(
			BankAuditProductResVo auditProductResVo,
			ProductDetailVo oldProductDetailVo) throws TException {
		auditProductResVo.setOldProductName(oldProductDetailVo.getName());// 商品简称
		auditProductResVo
				.setOldProductFullName(oldProductDetailVo.getFullName());// 商品全称
		auditProductResVo
				.setOldDescription(oldProductDetailVo.getBriefIntroduction());// 副标题
		auditProductResVo.setOldStoreNum(oldProductDetailVo.getStore());// 库存
		auditProductResVo.setOldStartDate(oldProductDetailVo.getStartTime());// 团购开始时间
		auditProductResVo.setOldEndDate(oldProductDetailVo.getEndTime());// 团购结束时间
		auditProductResVo
				.setOldStartCodeTime(oldProductDetailVo.getExpireStartTime());// 验证码有效开始时间
		auditProductResVo
				.setOldEndCodeTime(oldProductDetailVo.getExpireEndTime());// 验证码有效结束时间
		auditProductResVo
				.setOldMarketPrice(oldProductDetailVo.getMarketPrice());// 市场价
		auditProductResVo.setOldGroupPrice(oldProductDetailVo.getPrice());// 团购价
		auditProductResVo.setOldProductCategory(
				this.getProductCategoryName(oldProductDetailVo));// 商品分类名称
		auditProductResVo.setOldProductKnow(oldProductDetailVo.getBuyKnow());// 购买须知
		if(oldProductDetailVo.getBuyLimit()!=null&&oldProductDetailVo.getBuyLimit().getMax()>0){
			auditProductResVo.setOldMax(oldProductDetailVo.getBuyLimit().getMax());
		}
		return auditProductResVo;
	}

	/**
	 * 
	 * copyProductDetailValue:拷贝商品新属性
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月28日 下午6:43:53
	 * @param auditProductResVo
	 * @param productDetailVo
	 * @return
	 * @throws TException
	 */
	private BankAuditProductResVo copyProductDetailValue(
			BankAuditProductResVo auditProductResVo,
			ProductDetailVo productDetailVo) throws TException {
		auditProductResVo.setOrgCode(productDetailVo.getOrgCode());// 创建商品的商户所属机构
		auditProductResVo.setProductName(productDetailVo.getName());// 商品简称
		auditProductResVo.setProductFullName(productDetailVo.getFullName());// 商品全称
		auditProductResVo
				.setDescription(productDetailVo.getBriefIntroduction());// 副标题
		auditProductResVo.setStoreNum(productDetailVo.getStore());// 库存
		auditProductResVo.setStartDate(productDetailVo.getStartTime());// 团购开始时间
		auditProductResVo.setEndDate(productDetailVo.getEndTime());// 团购结束时间
		auditProductResVo
				.setStartCodeTime(productDetailVo.getExpireStartTime());// 验证码有效开始时间
		auditProductResVo.setEndCodeTime(productDetailVo.getExpireEndTime());// 验证码有效结束时间
		auditProductResVo.setMarketPrice(productDetailVo.getMarketPrice());// 市场价
		auditProductResVo.setGroupPrice(productDetailVo.getPrice());// 团购价
		auditProductResVo.setProductCategory(
				this.getProductCategoryName(productDetailVo));// 商品分类名称
		auditProductResVo.setProductKnow(productDetailVo.getBuyKnow());//购买须知
		auditProductResVo.setProductDetails(productDetailVo.getIntroduction());//商品详情
		auditProductResVo.setPhotos(this.getPhotos(productDetailVo));// 商品图片
		if(productDetailVo.getBuyLimit()!=null&&productDetailVo.getBuyLimit().getMax()>0){
			auditProductResVo.setMax(productDetailVo.getBuyLimit().getMax());
		}
		return auditProductResVo;
	}
	
	/**
	 * 
	 * getProductCategoryName:获取商品分类路劲
	 * @author chenmingcan@froad.com.cn
	 * 2016年1月29日 下午5:28:00
	 * @param productDetailVo
	 * @return
	 * @throws TException
	 */
	private String getProductCategoryName(ProductDetailVo productDetailVo)
			throws TException {
		String productCategoryName = null;
		long categoryId = productDetailVo.getProductCategoryId();
		ProductCategoryVo procdCate = new ProductCategoryVo();
		procdCate.setClientId(productDetailVo.getClientId());
		procdCate.setId(categoryId);
		ProductCategoryVo vo = productCategoryService
				.getProductCategoryById(procdCate);
		LogCvt.info("商品详情-分类返回:" + JSON.toJSONString(vo));
		if (vo != null) {
			String treePath = vo.getTreePath();
			productCategoryName = this.getProductCategory(treePath,
					productDetailVo.getClientId());
		}
		return productCategoryName;
	}

	/**
	 * 
	 * getPhotos:获取商品原图
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月29日 上午11:22:12
	 * @param productDetailVo
	 * @return
	 */
	private List<String> getPhotos(ProductDetailVo productDetailVo) {
		List<String> photos = null;
		List<ProductImageVo> images = productDetailVo.getImages() ;
		if (images!= null&&images.size() > 0) {
			photos = new ArrayList<String>();
			for (ProductImageVo photo :images) {
				photos.add(photo.getSource());
			}
		}
		return photos;
	}

	/**
	 * 
	 * getAuditorNameByUserId:根据审核人的id获取审核人名称
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月29日 上午10:14:05
	 * @param clientId
	 * @param auditorUserId
	 * @return
	 * @throws NumberFormatException
	 * @throws TException
	 */
	private String getAuditorNameByUserId(String clientId, String auditorUserId)
			throws NumberFormatException, TException {
		String auditorName = "";
		if (!StringUtil.isNotBlank(auditorUserId)) {
			return auditorName;
		}
		BankOperatorVo operatorVo = bankOperatorService
				.getBankOperatorById(clientId, Long.parseLong(auditorUserId));
		LogCvt.info("团购商品待审核-操作员对象返回:" + JSON.toJSONString(operatorVo));
		if (operatorVo != null) {
			auditorName = operatorVo.getUsername();
		}
		return auditorName;
	}

}
