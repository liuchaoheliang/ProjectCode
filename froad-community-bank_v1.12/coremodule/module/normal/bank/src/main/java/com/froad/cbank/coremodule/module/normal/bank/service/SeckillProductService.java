package com.froad.cbank.coremodule.module.normal.bank.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.IsMarketableEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.FileVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrdinaryProductVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.SeckillProductDetil;
import com.froad.cbank.coremodule.module.normal.bank.vo.SeckillProductSetVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.SeckillProductVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.SeckillProductVoRes;
import com.froad.thrift.service.ClientProductAuditService;
import com.froad.thrift.service.ProductSeckillService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductBriefInfoVo;
import com.froad.thrift.vo.ProductBriefPageVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ProductSeckillInfoVo;
import com.froad.thrift.vo.ProductSeckillPageVo;
import com.froad.thrift.vo.ProductSeckillVo;
import com.froad.thrift.vo.ResultVo;

/**
 * 秒杀商品管理
 * 
 * @author liuyanyun
 * @date 2015-04-21 下午 03:35:31
 */
@Service
public class SeckillProductService {

	@Resource
	ProductSeckillService.Iface productSeckillService;

	@Resource
	ProductService.Iface productService;

	@Resource
	ClientProductAuditService.Iface clientProductAuditService;

	/**
	 * 获取非秒杀商品列表查询
	 * 
	 * @param clientId
	 * @param filter
	 * @param pageNumber
	 * @param pageSize
	 * @return QueryResult<SeckillProductVoRes>
	 * 
	 * @author wangzhangxu
	 * @date 2015-05-06 下午 03:35:31
	 */
	public QueryResult<OrdinaryProductVoRes> findProductPageByConditions(
			String clientId, String filter, int pageNumber, int pageSize,int lastPageNumber,Long firstRecordTime,
			Long lastRecordTime) {
		// 分页
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setLastPageNumber(lastPageNumber);
		page.setFirstRecordTime(firstRecordTime);
		page.setLastRecordTime(lastRecordTime);

		ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq();
		productFilterVoReq.setClientId(clientId);
		if (StringUtils.isNotEmpty(filter)) {
			productFilterVoReq.setFilter(filter);
		}

		QueryResult<OrdinaryProductVoRes> queryVo = new QueryResult<OrdinaryProductVoRes>();
		List<OrdinaryProductVoRes> ordinaryProductList = new ArrayList<OrdinaryProductVoRes>();
		ProductBriefPageVo productBriefPage = null;
		try {
			productBriefPage = productService.findMerchantProductsByPage(
					productFilterVoReq, page);
			List<ProductBriefInfoVo> productBriefInfoList = productBriefPage
					.getProductBriefInfoVoList();
			if (productBriefInfoList != null && productBriefInfoList.size() > 0) {
				for (ProductBriefInfoVo productBriefInfo : productBriefInfoList) {
					OrdinaryProductVoRes ordinaryProduct = new OrdinaryProductVoRes();
					ordinaryProduct.setClientId(productBriefInfo.getClientId());
					ordinaryProduct.setMerchantId(productBriefInfo.getMerchantId());
					ordinaryProduct.setMerchantName(productBriefInfo.getMerchantName());
					ordinaryProduct.setOrgCode(productBriefInfo.getOrgCode());
					ordinaryProduct.setOrgName(productBriefInfo.getOrgName());// 所属机构
					ordinaryProduct.setProductId(productBriefInfo
							.getProductId());// 商品ID
					ordinaryProduct.setProductName(productBriefInfo.getName());// 商品名称
					ordinaryProduct.setProductType(productBriefInfo.getType());
					ordinaryProduct.setPrice(productBriefInfo.getPrice() + "");
					ordinaryProduct.setMarketPrice(productBriefInfo.getMarketPrice() + "");
					ordinaryProduct.setStore(productBriefInfo.getStore() + "");
					if (StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.NO.getCode())) {
						ordinaryProduct.setIsMarketable(IsMarketableEnum.NO
								.getCode());// 未上架
						ordinaryProduct.setIsMarketableName(IsMarketableEnum.NO.getDescription());
					} else if (StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.UP.getCode())) {
						ordinaryProduct.setIsMarketable(IsMarketableEnum.UP
								.getCode());// 上架
						ordinaryProduct.setIsMarketableName(IsMarketableEnum.UP.getDescription());
					} else if (StringUtil.equals(productBriefInfo.getIsMarketable(), IsMarketableEnum.DOWN.getCode())) {
						ordinaryProduct.setIsMarketable(IsMarketableEnum.DOWN
								.getCode());// 下架
						ordinaryProduct.setIsMarketableName(IsMarketableEnum.DOWN.getDescription());
					}
					ordinaryProductList.add(ordinaryProduct);
				}

				if (productBriefPage.getPage() != null) {
					queryVo.setPageCount(productBriefPage.getPage().getPageCount());
					queryVo.setTotalCount(productBriefPage.getPage().getTotalCount());
					queryVo.setPageNumber(productBriefPage.getPage().getPageNumber());
					queryVo.setLastPageNumber(productBriefPage.getPage().getLastPageNumber());
					queryVo.setFirstRecordTime(productBriefPage.getPage().getFirstRecordTime());
					queryVo.setLastRecordTime(productBriefPage.getPage().getLastRecordTime());
				}
			}

		} catch (TException e) {
			LogCvt.error("获取非秒杀商品列表查询，调用thrift接口异常", e);
		} catch (Exception e) {
			LogCvt.error("获取非秒杀商品列表查询异常", e);
		}
		queryVo.setResult(ordinaryProductList);
		return queryVo;
	}

	/**
	 * 秒杀商品列表查询
	 * 
	 * @param filter
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */

	public QueryResult<SeckillProductVoRes> findPageByConditions(
			String clientId, String filter, int pageNumber, int pageSize) {
		// 分页
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);

		ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq();
		productFilterVoReq.setClientId(clientId);
		if (StringUtils.isNotEmpty(filter)) {
			productFilterVoReq.setFilter(filter);
		}
		QueryResult<SeckillProductVoRes> queryVo = new QueryResult<SeckillProductVoRes>();
		List<SeckillProductVoRes> seckillProductList = new ArrayList<SeckillProductVoRes>();
		ProductSeckillPageVo productSeckillPage = null;
		try {
			productSeckillPage = productSeckillService.findSeckillByPage(
					productFilterVoReq, page);
			List<ProductSeckillVo> productSeckillVoList = productSeckillPage
					.getSeckillVoList();
			if (productSeckillVoList != null && productSeckillVoList.size() > 0) {
				for (ProductSeckillVo productSeckillVo : productSeckillVoList) {
					SeckillProductVoRes seckillProduct = new SeckillProductVoRes();
					seckillProduct
							.setProductId(productSeckillVo.getProductId());// 商品ID
					seckillProduct.setProductName(productSeckillVo.getName());// 商品名称
					seckillProduct.setProductType(productSeckillVo.getType());
					seckillProduct.setMerchantId(productSeckillVo.getMerchantId());
					seckillProduct.setMerchantName(productSeckillVo.getMerchantName());
					seckillProduct.setOrgCode(productSeckillVo.getOrgCode());
					seckillProduct.setOrgName(productSeckillVo.getOrgName());
					seckillProduct.setPrice(String.valueOf(productSeckillVo
							.getPrice()));// 优惠价格
					seckillProduct.setSecPrice(String.valueOf(productSeckillVo
							.getSecPrice()));// 秒杀价格
					seckillProduct.setVipSecPrice(String
							.valueOf(productSeckillVo.getVipSecPrice()));// VIP秒杀价格
					seckillProduct.setBuyLimit(productSeckillVo.getBuyLimit());// 秒杀限量
					seckillProduct.setStartDate(productSeckillVo.getStartTime()
							+ "");// 秒杀开始时间
					seckillProduct.setEndDate(productSeckillVo.getEndTime()
							+ "");// 秒杀结束时间
					seckillProduct.setStore(productSeckillVo.getStore()); // 商品库存
					seckillProduct.setSecStore(productSeckillVo.getSecStore());// 秒杀库存
					if (StringUtil.equals(productSeckillVo.getAuditState(), AuditFlagEnum.NEW.getCode())) {
						seckillProduct.setAuditState(AuditFlagEnum.NEW.getCode());
						seckillProduct.setAuditStateName(AuditFlagEnum.NEW.getDescription());
					} else if (StringUtil.equals(productSeckillVo.getAuditState(), AuditFlagEnum.ACCEPTED.getCode())) {
						seckillProduct.setAuditState(AuditFlagEnum.ACCEPTED.getCode());
						seckillProduct.setAuditStateName(AuditFlagEnum.ACCEPTED.getDescription());
					} else if (StringUtil.equals(productSeckillVo.getAuditState(), AuditFlagEnum.REJECTED.getCode())) {
						seckillProduct.setAuditState(AuditFlagEnum.REJECTED.getCode());
						seckillProduct.setAuditStateName(AuditFlagEnum.REJECTED.getDescription());
					}
					if (StringUtil.equals(productSeckillVo.getIsMarketable(), IsMarketableEnum.NO.getCode())) {
						seckillProduct.setIsMarketable(IsMarketableEnum.NO
								.getCode());// 上下架
						seckillProduct.setIsMarketableName(IsMarketableEnum.NO.getDescription());
					} else if (StringUtil.equals(productSeckillVo.getIsMarketable(), IsMarketableEnum.UP.getCode())) {
						seckillProduct.setIsMarketable(IsMarketableEnum.UP
								.getCode());// 上下架
						seckillProduct.setIsMarketableName(IsMarketableEnum.UP.getDescription());
					} else if (StringUtil.equals(productSeckillVo.getIsMarketable(), IsMarketableEnum.DOWN.getCode())) {
						seckillProduct.setIsMarketable(IsMarketableEnum.DOWN
								.getCode());// 上下架
						seckillProduct.setIsMarketableName(IsMarketableEnum.DOWN.getDescription());
					}
					
					seckillProductList.add(seckillProduct);
				}

				if (productSeckillPage != null) {
					queryVo.setPageCount(String.valueOf(productSeckillPage.getPage().getPageCount()));
					queryVo.setPageNumber(String.valueOf(productSeckillPage.getPage().getPageNumber()));
					queryVo.setTotalCount(productSeckillPage.getPage().getTotalCount());
				}
			}
		} catch (TException e) {
			LogCvt.error("获取秒杀商品列表查询，调用thrift接口异常", e);
		} catch (Exception e) {
			LogCvt.error("获取秒杀商品列表查询异常", e);
		}

		queryVo.setResult(seckillProductList);
		return queryVo;
	}

	/**
	 * 秒杀商品批量设置
	 * 
	 * @param voReq
	 * @return
	 */

	public MsgVo batchSet(SeckillProductSetVoReq voReq, OriginVo vo) {
		MsgVo msgVo = new MsgVo();
		try {
			List<ProductSeckillVo> productSeckillVoList = new ArrayList<ProductSeckillVo>();
			long now = System.currentTimeMillis();
			List<SeckillProductVoReq> productReqList = voReq.getProductList();
			for (SeckillProductVoReq productReq : productReqList) {
				ProductSeckillVo product = new ProductSeckillVo();
				product.setClientId(voReq.getClientId());
				product.setProductId(productReq.getProductId());
				product.setSecPrice(Double.parseDouble(productReq.getSecPrice()));
				product.setVipSecPrice(Double.parseDouble(productReq.getVipSecPrice()));
				product.setBuyLimit(productReq.getBuyLimit());
				product.setSecStore(productReq.getSecStore());
				
				product.setStartTime(Long.parseLong(voReq.getStartDate()));
				product.setEndTime(Long.parseLong(voReq.getEndDate()));
				
				product.setIsMarketable(IsMarketableEnum.NO.getCode()); // 未上架
				product.setAuditState(AuditFlagEnum.NEW.getCode());// 待审核

				product.setCreateTime(now);
				
				// 秒杀审核通过菜单来控制，不是指定审核机构来审核
				product.setAuditStartOrgCode("");
				product.setAuditEndOrgCode("");
				product.setAuditOrgCode("");
				
				/*
				 * // 设置最初审核机构和结束机构 ClientProductAuditOrgCodeVo productAuditVo =
				 * clientProductAuditService
				 * .getClientProductAuditByOrgCode(voReq.getClientId(),
				 * voReq.getOrgCode(), productReq.getProductType());
				 * 
				 * if (productAuditVo != null &&
				 * StringUtil.isNotEmpty(productAuditVo.getStartAuditOrgCode())
				 * &&
				 * StringUtil.isNotEmpty(productAuditVo.getEndAuditOrgCode())) {
				 * product
				 * .setAuditStartOrgCode(productAuditVo.getStartAuditOrgCode());
				 * product
				 * .setAuditEndOrgCode(productAuditVo.getEndAuditOrgCode());
				 * product
				 * .setAuditOrgCode(productAuditVo.getStartAuditOrgCode()); //
				 * 总行添加且无需双人审核则直接审核通过 if
				 * (StringUtil.equals(productAuditVo.getStartAuditOrgCode(),
				 * AuditFlagEnum.NEW.getCode()) &&
				 * StringUtil.equals(productAuditVo.getEndAuditOrgCode(),
				 * AuditFlagEnum.NEW.getCode())) {
				 * product.setAuditState(AuditFlagEnum.ACCEPTED.getCode());//
				 * 审核通过
				 * product.setIsMarketable(IsMarketableEnum.UP.getCode());// 上架
				 * } } else { msgVo.setResult(false);
				 * msgVo.setMsg("秒杀商品最初审核机构不能为空"); return msgVo; }
				 */
				productSeckillVoList.add(product);
			}

			ResultVo resultVo = productSeckillService.addProductSeckill(productSeckillVoList, vo);
			
			if (resultVo.getResultCode().equals(EnumTypes.success.getCode())) {
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			} else {
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("秒杀商品批量设置异常");
		}
		return msgVo;
	}

	/**
	 * 秒杀商品修改
	 * 
	 * @param voReq
	 * @return
	 */
	public MsgVo update(SeckillProductVoReq voReq, OriginVo vo) {
		MsgVo msgVo = new MsgVo();
		try {
			ProductSeckillVo product = new ProductSeckillVo();
			product.setClientId(voReq.getClientId());
			product.setProductId(voReq.getProductId());
			product.setSecPrice(Double.valueOf(voReq.getSecPrice()));
			product.setVipSecPrice(Double.valueOf(voReq.getVipSecPrice()));
			product.setSecStore(voReq.getSecStore());
			product.setBuyLimit(voReq.getBuyLimit());
			product.setStartTime(Long.valueOf(voReq.getStartDate()));
			product.setEndTime(Long.valueOf(voReq.getEndDate()));
			product.setAuditState(AuditFlagEnum.NEW.getCode());// 待审核

			// 秒杀审核通过菜单来控制，不是指定审核机构来审核
			product.setAuditStartOrgCode("");
			product.setAuditEndOrgCode("");
			product.setAuditOrgCode("");
			
			/*
			 * // 设置最初审核机构和结束机构 ClientProductAuditOrgCodeVo productAuditVo =
			 * clientProductAuditService
			 * .getClientProductAuditByOrgCode(voReq.getClientId(),
			 * voReq.getOrgCode(), voReq.getProductType());
			 * 
			 * if (productAuditVo != null &&
			 * StringUtil.isNotEmpty(productAuditVo.getStartAuditOrgCode()) &&
			 * StringUtil.isNotEmpty(productAuditVo.getEndAuditOrgCode())) {
			 * product
			 * .setAuditStartOrgCode(productAuditVo.getStartAuditOrgCode());
			 * product.setAuditEndOrgCode(productAuditVo.getEndAuditOrgCode());
			 * product.setAuditOrgCode(productAuditVo.getStartAuditOrgCode()); }
			 * else { msgVo.setResult(false); msgVo.setMsg("秒杀商品最初审核机构不能为空");
			 * return msgVo; }
			 */
			
			ResultVo resultVo = productSeckillService.updateProductSeckill(
					product, vo);
			if (resultVo.getResultCode().equals(EnumTypes.success.getCode())) {
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			} else {
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
				LogCvt.debug(resultVo.getResultDesc()
						+ JSONObject.toJSONString(voReq));
			}
		} catch (Exception e) {
			msgVo.setResult(false);
			msgVo.setMsg("秒杀商品修改异常");
			LogCvt.error(msgVo.getMsg(), e);
		}
		return msgVo;
	}

	/**
	 * 秒杀商品上下架
	 * 
	 * @param voReq
	 * @return
	 */
	public MsgVo upDownProduct(String clientId, String productId, String isMarketable, OriginVo vo) {
		MsgVo msgVo = new MsgVo();
		try {
			ProductSeckillVo productVo = new ProductSeckillVo();
			productVo.setClientId(clientId);
			productVo.setProductId(productId);
			productVo.setIsMarketable(isMarketable);// 0未上架1-已上架2-已下架
			ResultVo resultVo = productSeckillService.updateProductSeckillStatus(productVo, vo);
			if (resultVo.getResultCode().equals(EnumTypes.success.getCode())) {
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			} else {
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("秒杀商品" + ("1".equals(isMarketable) ? "上" : "下")
					+ "架异常");
		}
		return msgVo;
	}

	/**
	 * 秒杀商品详情
	 * 
	 * @param clientId
	 * @param productId
	 */
	public SeckillProductDetil detail(String clientId, String productId) {
		SeckillProductDetil productDetil = new SeckillProductDetil();
		ProductSeckillVo seckillInfo = new ProductSeckillVo();
		seckillInfo.setClientId(clientId);
		seckillInfo.setProductId(productId);
		try {
			ProductSeckillInfoVo productInfo = productSeckillService.getProductSeckillDetail(seckillInfo);
			if (productInfo != null) {
				ProductSeckillVo product = productInfo.getProductSeckill();
				if (product != null) {
					DecimalFormat df = new DecimalFormat("#0.00");
					productDetil.setProductName(product.getName());// 秒杀商品名称
					productDetil.setProductType(product.getType());// 秒杀商品类型
					productDetil.setMarketPrice(String.valueOf(df
							.format(product.getMarketPrice())));// 市场价
					productDetil.setSecPrice(String.valueOf(df.format(product
							.getSecPrice())));// 秒杀价
					productDetil.setVipSecPrice(String.valueOf(df
							.format(product.getVipSecPrice())));// VIP秒杀价
					productDetil.setSecStore(String.valueOf(product
							.getSecStore()));// 秒杀库存数量
					productDetil.setBuyLimit(String.valueOf(product
							.getBuyLimit()));// 秒杀限购数量
					productDetil.setStartDate(String.valueOf(product
							.getStartTime()));// 秒杀开始时间
					productDetil
							.setEndDate(String.valueOf(product.getEndTime()));// 秒杀结束时间
					productDetil.setDescription(product.getBriefIntroduction());// 商品简介
					productDetil.setDistributionType(product
							.getDeliveryOption());// 配送方式
					productDetil.setProductKnow(product.getBuyKnow());// 购买须知
					productDetil.setProductDetails(product.getIntroduction());// 商品详情
					productDetil.setAuditComment(product.getAuditComment());
				}
				
				List<String> bankOrgList = new ArrayList<String>();
				List<String> bankOrgNameList = new ArrayList<String>();
				if (productInfo.getOutlet() != null) {
					List<ProductOutletVo> productOutletList = productInfo.getOutlet();
					for (ProductOutletVo productOutletVo : productOutletList) {
						bankOrgList.add(productOutletVo.getOutletId());
						bankOrgNameList.add(productOutletVo.getOutletName());
					}
				}
				productDetil.setOrgCodeList(bankOrgList);// 网点
				productDetil.setOrgNameList(bankOrgNameList);// 网点名称
				productDetil.setParentOrgCodeList(productInfo.getOrgCodes());// 发人行社
				
				// 商品图片
				ArrayList<FileVo> imageList = new ArrayList<FileVo>();
				List<ProductImageVo> productImageList = productInfo.getImage();
				if (productImageList != null && productImageList.size() > 0) {
					for (ProductImageVo productImage : productImageList) {
						FileVo fileImage = new FileVo();
						fileImage.setTitle(productImage.getTitle());// 图片标题
						fileImage.setSource(productImage.getSource());// 图片原图地址
						fileImage.setLarge(productImage.getLarge());// 图片大图地址
						fileImage.setMedium(productImage.getMedium());// 图片中图地址
						fileImage.setThumbnail(productImage.getThumbnail());// 图片小图地址
						imageList.add(fileImage);
					}
				}
				productDetil.setFileList(imageList);// 商品图片
			}
		} catch (TException e) {
			LogCvt.error(e.getMessage(), e);
		}

		return productDetil;
	}

	/**
	 * 秒杀商品删除
	 * 
	 * @param clientId
	 * @param productId
	 */
	public MsgVo delete(String clientId, String productId, OriginVo vo) {
		MsgVo msgVo = new MsgVo();
		try {
			ProductSeckillVo product = new ProductSeckillVo();
			product.setClientId(clientId);
			product.setProductId(productId);
			ResultVo resultVo = productSeckillService.deleteProductSeckill(
					product, vo);
			if (resultVo.getResultCode().equals(EnumTypes.success.getCode())) {
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			} else {
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}

		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("秒杀商品删除异常");
		}
		return msgVo;
	}

	/**
	 * 校验
	 * 
	 * @param bankOrg
	 * @return
	 */
	public Boolean verify(SeckillProductVoReq voReq) {
		if (StringUtils.isEmpty(voReq.getProductId())) {
			return false;
		}
		if (StringUtils.isEmpty(voReq.getProductType())) {
			return false;
		}
		if (StringUtils.isEmpty(voReq.getSecPrice())) {
			return false;
		}
		if (StringUtils.isEmpty(voReq.getVipSecPrice())) {
			return false;
		}
		if (voReq.getSecStore() <= 0) {
			return false;
		}
		if (voReq.getBuyLimit() < 0) {
			return false;
		}
		if (!StringUtil.isNotEmpty(voReq.getStartDate())) {
			return false;
		}
		if (!StringUtil.isNotEmpty(voReq.getEndDate())) {
			return false;
		}

		long start = 0, end = 0;
		if (StringUtil.isEmpty(voReq.getStartDate())) {
			return false;
		} else {
			try {
				start = Long.parseLong(voReq.getStartDate());
			} catch (Exception e) {
				return false;
			}
		}

		if (StringUtil.isEmpty(voReq.getEndDate())) {
			return false;
		} else {
			try {
				end = Long.parseLong(voReq.getEndDate());
			} catch (Exception e) {
				return false;
			}
		}

		if (start == 0 || end == 0 || end <= start
				|| end < System.currentTimeMillis()) {
			return false;
		}

		return true;
	}

	/**
	 * 校验
	 */
	public Boolean verify(SeckillProductSetVoReq voReq) {
		if (voReq.getProductList() == null
				|| voReq.getProductList().size() == 0) {
			return false;
		}
		
		if (StringUtil.isEmpty(voReq.getOrgCode())) {
			return false;
		}

		for (SeckillProductVoReq p : voReq.getProductList()) {
			p.setStartDate(voReq.getStartDate());
			p.setEndDate(voReq.getEndDate());

			if (!verify(p)) {
				return false;
			}
		}

		return true;
	}
}
