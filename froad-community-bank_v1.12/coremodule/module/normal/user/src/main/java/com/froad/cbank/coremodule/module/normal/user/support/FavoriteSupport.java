package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.CategoryInfoPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.FavoritePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OutletFavoritePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductFavoritePojo;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.thrift.service.MerchantOutletFavoriteService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.OutletDetailVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductBaseInfoVo;
import com.froad.thrift.vo.ProductStoreFilterVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.StoreOutletInfoPageVoRes;
import com.froad.thrift.vo.StoreOutletInfoVo;
import com.froad.thrift.vo.StoreProductInfoPageVoRes;
import com.froad.thrift.vo.StoreProductInfoVo;



/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月2日 下午6:51:06
 * @version 1.0
 * @desc 收藏夹支持类
 */
@Service
public class FavoriteSupport extends BaseSupport {

	@Resource
	private MerchantOutletFavoriteService.Iface merchantOutletFavoriteService;
	
	@Resource
	private ProductService.Iface productService;
	
	@Resource
	private OutletService.Iface outletService;
	
	/**
	 * @desc 获取商品收藏列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月8日 下午8:10:21
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getProductFavoriteList(String clientId, Long memberCode, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		StoreProductInfoPageVoRes resp = null;
		List<ProductFavoritePojo> productFavoriteList = new ArrayList<ProductFavoritePojo>();
		HashMap<String, ProductFavoritePojo> map = new HashMap<String, ProductFavoritePojo>();
		PagePojo pagePojo = new PagePojo();
		ProductFavoritePojo pojo = null;
		//封装分页对象
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setLastPageNumber(lastPageNumber);
		page.setFirstRecordTime(firstRecordTime);
		page.setLastRecordTime(lastRecordTime);
		try {
			resp = productService.getStoreProductInfoByPage(page, clientId, memberCode);
			if(resp.getPage() != null) {
				BeanUtils.copyProperties(pagePojo, resp.getPage());
			}
			if(!ArrayUtil.empty(resp.getStoreProductInfoVoList())) {
				for(StoreProductInfoVo tmp : resp.getStoreProductInfoVoList()) {
					pojo = new ProductFavoritePojo();
					BeanUtils.copyProperties(pojo, tmp);
					map.put(pojo.getProductId(), pojo);
				}
				List<ProductBaseInfoVo> productList = null;
				//封装商品ID集合
				List<ProductStoreFilterVo> productIdList = new ArrayList<ProductStoreFilterVo>();
				ProductStoreFilterVo productStore = null;
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					productStore = new ProductStoreFilterVo();
					productStore.setProductId(iterator.next());
					productIdList.add(productStore);
				}
				//根据商品ID集合 获取商品列表，封装属性
				productList = productService.getProductBaseInfo(clientId, productIdList);
				if(!ArrayUtil.empty(productList)) {
					for(ProductBaseInfoVo tmp : productList) {
						pojo = map.get(tmp.getProductId());
						BeanUtils.copyProperties(pojo, tmp);
						productFavoriteList.add(pojo);
					}
				}
			}
			resResult.put("page", pagePojo);
			resResult.put("productFavoriteList", productFavoriteList);
		} catch (TException e) {
			LogCvt.info("商品收藏列表查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "商品收藏列表查询异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 获取门店收藏列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月10日 下午4:34:32
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getOutletFavoriteList(String clientId, Long memberCode, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		StoreOutletInfoPageVoRes resp = null;
		List<OutletFavoritePojo> outletFavoriteList = new ArrayList<OutletFavoritePojo>();
		HashMap<String, OutletFavoritePojo> map = new HashMap<String, OutletFavoritePojo>();
		PagePojo pagePojo = new PagePojo();
		OutletFavoritePojo pojo = null;
		//封装分页对象
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setLastPageNumber(lastPageNumber);
		page.setFirstRecordTime(firstRecordTime);
		page.setLastRecordTime(lastRecordTime);
		try {
			resp = merchantOutletFavoriteService.getStoreOutletInfoByPage(page, clientId, memberCode);
			if(resp.getPage() != null) {
				BeanUtils.copyProperties(pagePojo, resp.getPage());
			}
			if(!ArrayUtil.empty(resp.getStoreOutletInfoVoList())) {
				for(StoreOutletInfoVo tmp : resp.getStoreOutletInfoVoList()) {
					pojo = new OutletFavoritePojo();
					BeanUtils.copyProperties(pojo, tmp);
					map.put(pojo.getOutletId(), pojo);
				}
				List<OutletDetailVo> outletList = null;
				//封装门店ID集合
				List<String> outletIdList = new ArrayList<String>();
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					outletIdList.add(iterator.next());
				}
				//根据门店ID集合 获取门店列表，封装属性
				outletList = outletService.getOutletDetailbyOutletIdList(outletIdList);
				if(!ArrayUtil.empty(outletList)) {
					for(OutletDetailVo tmp : outletList) {
						pojo = map.get(tmp.getId());
						BeanUtils.copyProperties(pojo, tmp);
						//封装门店分类集合
						List<CategoryInfoPojo> categoryList = null;
						if(tmp.getCategoryInfoSize() > 0) {
							categoryList = new ArrayList<CategoryInfoPojo>();
							CategoryInfoPojo categoryInfo = null;
							for(CategoryInfoVo categoryTemp : tmp.getCategoryInfo()) {
								categoryInfo = new CategoryInfoPojo();
								BeanUtils.copyProperties(categoryInfo, categoryTemp);
								categoryList.add(categoryInfo);
							}
						}
						pojo.setCategoryList(categoryList);
						outletFavoriteList.add(pojo);
					}
				}
			}
			resResult.put("page", pagePojo);
			resResult.put("outletFavoriteList", outletFavoriteList);
		} catch (TException e) {
			LogCvt.info("门店收藏列表查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "门店收藏列表查询异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 收藏商品
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月10日 下午7:47:04
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> collectProduct(String clientId, Long memberCode, ProductFavoritePojo pojo) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		StoreProductInfoVo req = new StoreProductInfoVo();
		ResultVo resp = new ResultVo();
		//封装请求对象
		req.setProductId(pojo.getProductId());
		try {
			resp = productService.addStoreProductInfoVo(clientId, memberCode, req);
			if(!StringUtil.equals(resp.getResultCode(), Constants.RESULT_CODE_SUCCESS)) {
				resResult.put("code", resp.getResultCode());
				resResult.put("message", resp.getResultDesc());
			}
		} catch (TException e) {
			LogCvt.info("商品收藏异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "商品收藏异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 收藏门店
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月10日 下午7:47:04
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> collectOutlet(String clientId, Long memberCode, OutletFavoritePojo pojo) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		StoreOutletInfoVo req = new StoreOutletInfoVo();
		ResultVo resp = new ResultVo();
		//封装请求对象
		req.setMerchantId(pojo.getMerchantId());
		req.setOutletId(pojo.getOutletId());
		try {
			resp = merchantOutletFavoriteService.addStoreOutletInfoVo(clientId, memberCode, req);
			if(!StringUtil.equals(resp.getResultCode(), Constants.RESULT_CODE_SUCCESS)) {
				resResult.put("code", resp.getResultCode());
				resResult.put("message", resp.getResultDesc());
			}
		} catch (TException e) {
			LogCvt.info("门店收藏异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "门店收藏异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 取消收藏（商品/门店）
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月10日 下午4:34:48
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> deleteFavorite(String clientId, Long memberCode, FavoritePojo pojo) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		try {
			if(!StringUtil.isEmpty(pojo.getProductId())) {
				//取消收藏商品
				if(!productService.deleteStoreProductInfoVo(clientId, memberCode, pojo.getProductId())) {
					LogCvt.info(">>从SERVER端返回取消收藏商品结果：false");
				}
			} else if(!StringUtil.isEmpty(pojo.getOutletId())) {
				//取消收藏门店
				if(!merchantOutletFavoriteService.deleteStoreOutletInfoVo(clientId, memberCode, pojo.getOutletId())) {
					LogCvt.info(">>从SERVER端返回取消收藏门店结果：false");
				}
			}
		} catch (TException e) {
			LogCvt.info("取消收藏异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "取消收藏异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 统计（商品/门店）收藏数量
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月10日 下午8:13:00
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getFavoriteCount(String clientId, Long memberCode) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		Map<String, Integer> resp = null;
		try {
			resp = merchantOutletFavoriteService.countProductStoreOutletInfo(clientId, memberCode);
			resResult.put("selfProductFavoriteCount", resp.get("productcount") == null ? 0 : resp.get("productcount"));//个人商品收藏数
			resResult.put("selfOutletFavoriteCount", resp.get("outletcount") == null ? 0 : resp.get("outletcount"));//个人门店收藏数
		} catch (TException e) {
			LogCvt.info("收藏数量统计异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "收藏数量统计异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 是否已收藏
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月10日 下午7:22:24
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> isCollected(String clientId, Long memberCode, String productId, String outletId) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		boolean isCollected = false;
		try {
			if(!StringUtil.isEmpty(productId)) {
				isCollected = productService.isExitsStoreProductInfo(clientId, memberCode, productId);
			} else if(!StringUtil.isEmpty(outletId)) {
				isCollected = merchantOutletFavoriteService.isExitsStoreOutletInfo(clientId, memberCode, outletId);
			}
			resResult.put("isCollected", isCollected);
		} catch (TException e) {
			LogCvt.info("是否已收藏状态查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "是否已收藏状态查询异常");
		}
		return resResult;
	}
}
