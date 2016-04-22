package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.enums.SeckillProductStatus;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.SeckillProductDetailPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.SeckillProductPojo;
import com.froad.thrift.service.ProductSeckillService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ProductSeckillInfoPageVo;
import com.froad.thrift.vo.ProductSeckillInfoVo;
import com.froad.thrift.vo.ProductSeckillVo;

/**
 * 秒杀商品相关接口支持类
 * 
 * @author wangzhangxu
 * @date 2015年5月04日 上午10:18:57
 * @version v1.0
 */
@Service
public class SeckillProductSupport extends BaseSupport {

	@Resource
	private ProductSeckillService.Iface productSeckillService;

	/**
	 * 获取秒杀商品列表
	 * @author wangzhangxu
	 * @date 2015年5月04日 上午10:18:57
	 * @version v1.0
	 */
	public HashMap<String, Object> getProductList(String clientId, String filter, PagePojo pagePojo) {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		// 分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		
		LogCvt.info("查询条件：" + filter);
		ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq();
		productFilterVoReq.setClientId(clientId);
		// 处理掉json中的转义符号
		productFilterVoReq.setFilter(filter.replace("\\", ""));
		
		ProductSeckillInfoPageVo pageVoRes = null;
		PagePojo page = new PagePojo();
		List<SeckillProductPojo> plist = null;
		SeckillProductPojo productPojo = null;
		
		try {
			LogCvt.info("调用接口传入参数:" + JSON.toJSONString(productFilterVoReq));
			pageVoRes = productSeckillService.findH5SeckillByPage(productFilterVoReq, pageVo);
			
			if (pageVoRes.isSetPage()) {
				BeanUtils.copyProperties(page, pageVoRes.getPage());
			}
			
			long now = System.currentTimeMillis();

			if (pageVoRes.getSeckillInfoVoList() != null && pageVoRes.getSeckillInfoVoList().size() != 0) {
				plist = new ArrayList<SeckillProductPojo>();
				ProductSeckillVo psTemp = null;
				for (ProductSeckillInfoVo temp : pageVoRes.getSeckillInfoVoList()) {
					productPojo = new SeckillProductPojo();
					psTemp = temp.getProductSeckill();
					productPojo.setClientId(psTemp.getClientId());
					productPojo.setMerchantId(psTemp.getMerchantId());
					productPojo.setProductId(psTemp.getProductId());
					productPojo.setType(psTemp.getType());
					productPojo.setName(psTemp.getName());
					productPojo.setPrice(psTemp.getSecPrice() + "");
					productPojo.setVipPrice(psTemp.getVipSecPrice() + "");
					productPojo.setMarketPrice(psTemp.getMarketPrice() + "");
					productPojo.setStore(psTemp.getSecStore() + "");
					productPojo.setSellCount(psTemp.getTrueBuyerNumber() + "");
					productPojo.setStartTime(psTemp.getStartTime() + "");
					productPojo.setEndTime(psTemp.getEndTime() + "");
					if (temp.getImage() != null && temp.getImage().size() != 0) {
						productPojo.setSmallImgUrl(temp.getImage().get(0).getThumbnail());
					}
					productPojo.setBriefIntroduction(psTemp.getBriefIntroduction());
					
					// 秒杀状态
					if (now < psTemp.getStartTime()) {
						productPojo.setSecStatus(SeckillProductStatus.soon.getCode());
					} else if (psTemp.getEndTime() < now) {
						productPojo.setSecStatus(SeckillProductStatus.expired.getCode());
					} else if (psTemp.getStartTime() < now && now < psTemp.getEndTime()) {
						if (psTemp.getSecStore() > 0) {
							productPojo.setSecStatus(SeckillProductStatus.ing.getCode());
						} else {
							productPojo.setSecStatus(SeckillProductStatus.soldout.getCode());
						}
					}
					plist.add(productPojo);
				}
			}

		} catch (TException e) {
			LogCvt.error("获取秒杀商品列表出错", e);
		} catch (Exception e) {
			LogCvt.error("获取秒杀商品列表出错", e);
		}
		resMap.put("page", page);
		resMap.put("productList", plist);
		return resMap;
	}
	
	/**
	 * 获取秒杀商品详情
	 * @author wangzhangxu
	 * @date 2015年5月04日 上午10:18:57
	 * @version v1.0
	 */
	public HashMap<String, Object> getProductDetail(String clientId, String productId, String type) {
		LogCvt.debug("获取秒杀商品详情，clientId：" + clientId + ", productId:" + productId);
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		ProductSeckillVo productSeckillVo = new ProductSeckillVo();
		productSeckillVo.setClientId(clientId);
		productSeckillVo.setProductId(productId);
		productSeckillVo.setType(type);
		
		ProductSeckillInfoVo productInfoVo = null;
		try {
			productInfoVo = productSeckillService.getProductSeckillDetail(productSeckillVo);
			SeckillProductDetailPojo detailPojo = null;
			SeckillProductDetailPojo.MerchantOutlet outlet = null;
			SeckillProductDetailPojo.ImageInfo imageInfo = null;
			
			// 封装商品详情Po
			if (productInfoVo.getProductSeckill() != null) {
				ProductSeckillVo psTemp = productInfoVo.getProductSeckill();
				detailPojo = new SeckillProductDetailPojo();
				detailPojo.setClientId(psTemp.getClientId());
				detailPojo.setMerchantId(psTemp.getMerchantId());
				detailPojo.setProductId(psTemp.getProductId());
				detailPojo.setType(psTemp.getType());
				detailPojo.setDeliveryOption(psTemp.getDeliveryOption());
				detailPojo.setName(psTemp.getName());
				detailPojo.setFullName(psTemp.getFullName());//
				detailPojo.setPrice(psTemp.getSecPrice() + "");
				detailPojo.setVipPrice(psTemp.getVipSecPrice() + "");
				detailPojo.setMarketPrice(psTemp.getMarketPrice() + "");
				detailPojo.setDeliveryMoney(psTemp.getDeliveryMoney() + "");
				detailPojo.setStore(psTemp.getSecStore() + "");
				detailPojo.setBuyLimit(psTemp.getBuyLimit() + "");
				detailPojo.setBriefIntroduction(psTemp.getBriefIntroduction());
				detailPojo.setIntroduction(psTemp.getIntroduction());
				detailPojo.setBuyKnow(psTemp.getBuyKnow());
				detailPojo.setStartTime(psTemp.getStartTime() + "");
				detailPojo.setEndTime(psTemp.getEndTime() + "");
				detailPojo.setTrueBuyerNumber(psTemp.getTrueBuyerNumber() + "");
				detailPojo.setMerchantName(psTemp.getMerchantName());//
				detailPojo.setPhone(psTemp.getPhone());
				detailPojo.setServerTime(System.currentTimeMillis());
				
				// 封装图片集合
				if (productInfoVo.getImage() != null && productInfoVo.getImage().size() != 0) {
					List<SeckillProductDetailPojo.ImageInfo> imageList = new ArrayList<SeckillProductDetailPojo.ImageInfo>();
					for (ProductImageVo temp : productInfoVo.getImage()) {
						imageInfo = new SeckillProductDetailPojo().new ImageInfo();
						BeanUtils.copyProperties(imageInfo, temp);
						imageList.add(imageInfo);
					}
					detailPojo.setImageInfoList(imageList);
				}
				
				// 封装门店集合
				if (productInfoVo.getOutlet() != null && productInfoVo.getOutlet().size() != 0) {
					List<SeckillProductDetailPojo.MerchantOutlet> outletList = new ArrayList<SeckillProductDetailPojo.MerchantOutlet>();
					for (ProductOutletVo temp : productInfoVo.getOutlet()) {
						outlet = new SeckillProductDetailPojo().new MerchantOutlet();
						BeanUtils.copyProperties(outlet, temp);
						outletList.add(outlet);
					}
					detailPojo.setMerchantOutletList(outletList);
				}
			}
			
			resMap.put("resResult", detailPojo);
		} catch (TException e) {
			LogCvt.error("获取秒杀商品详情出错", e);
		} catch (Exception e) {
			LogCvt.error("获取秒杀商品详情出错", e);
		} 
		return resMap;
	}
}
