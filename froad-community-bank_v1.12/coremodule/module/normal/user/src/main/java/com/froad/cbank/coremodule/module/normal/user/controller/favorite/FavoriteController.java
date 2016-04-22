package com.froad.cbank.coremodule.module.normal.user.controller.favorite;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.pojo.FavoritePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OutletFavoritePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductFavoritePojo;
import com.froad.cbank.coremodule.module.normal.user.support.FavoriteSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年3月24日 下午4:14:49
 * @version 1.0
 * @desc 收藏类
 */
@Controller
@RequestMapping
public class FavoriteController extends BasicSpringController {

	@Resource
	private FavoriteSupport favoriteSupport;
	
	/**
	 * @desc 获取商品收藏列表（按用户ID）
	 * @path /favorite/product/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月24日 下午5:10:07
	 * @param 
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/favorite/product/list", method = RequestMethod.GET)
	public void getProductFavoriteList(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		String clientId = getClient(req);
		if(pageNumber == null || pageNumber < 1) {
			pageNumber = Constants.PAGE_NUMBER;
		}
		if(pageSize == null || pageSize < 0) {
			pageSize = Constants.PAGE_SIZE;
		}
		lastPageNumber = lastPageNumber == null ? 1 : lastPageNumber;
		firstRecordTime = firstRecordTime == null ? 0L : firstRecordTime;
		lastRecordTime = lastRecordTime == null ? 0L : lastRecordTime;
		if(memberCode != null) {
			model.putAll(favoriteSupport.getProductFavoriteList(clientId, memberCode, pageNumber, pageSize, lastPageNumber, firstRecordTime, lastRecordTime));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
	
	/**
	 * @desc 获取门店收藏列表（按用户ID）
	 * @path /favorite/outlet/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月30日 下午4:47:05
	 * @param 
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/favorite/outlet/list", method = RequestMethod.GET)
	public void getOutletFavoriteList(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		String clientId = getClient(req);
		if(pageNumber == null || pageNumber < 1) {
			pageNumber = Constants.PAGE_NUMBER;
		}
		if(pageSize == null || pageSize < 0) {
			pageSize = Constants.PAGE_SIZE;
		}
		lastPageNumber = lastPageNumber == null ? 1 : lastPageNumber;
		firstRecordTime = firstRecordTime == null ? 0L : firstRecordTime;
		lastRecordTime = lastRecordTime == null ? 0L : lastRecordTime;
		if(memberCode != null) {
			model.putAll(favoriteSupport.getOutletFavoriteList(clientId, memberCode, pageNumber, pageSize, lastPageNumber, firstRecordTime, lastRecordTime));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
	
	/**
	 * @desc 收藏商品
	 * @path /favorite/product/add
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月24日 下午5:11:07
	 * @param 
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/favorite/product/add", method = RequestMethod.POST)
	public void collectProduct(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody ProductFavoritePojo pojo) {
		String clientId = getClient(req);
		if(memberCode != null) {
			model.putAll(favoriteSupport.collectProduct(clientId, memberCode, pojo));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
	
	/**
	 * @desc 收藏门店（商户）
	 * @path /favorite/outlet/add
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月30日 下午4:49:24
	 * @param 
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/favorite/outlet/add", method = RequestMethod.POST)
	public void collectOutlet(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody OutletFavoritePojo pojo) {
		String clientId = getClient(req);
		if(memberCode != null) {
			model.putAll(favoriteSupport.collectOutlet(clientId, memberCode, pojo));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
	
	/**
	 * @desc 取消收藏
	 * @path /favorite/delete
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月24日 下午5:13:02
	 * @param memberCoe(必填) productId/outletId(二选其一必填)
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/favorite/delete", method = RequestMethod.POST)
	public void deleteFavorite(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody FavoritePojo pojo) {
		String clientId = getClient(req);
		if(memberCode != null && (!StringUtil.isEmpty(pojo.getOutletId()) || !StringUtil.isEmpty(pojo.getProductId()))) {
			model.putAll(favoriteSupport.deleteFavorite(clientId, memberCode, pojo));
		} else {
			model.put("code", "9999");
			model.put("message", "必要值不可为空");
		}
	}
	
	/**
	 * @desc 获取（商品/门店）收藏统计数量
	 * @path /favorite/count
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月10日 下午8:18:48
	 * @param member(必填)
	 * @return 
	 */
	@RequestMapping(value = "/favorite/count", method = RequestMethod.GET)
	public void getFavoriteCount(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode) {
		String clientId = getClient(req);
		if(memberCode != null) {
			model.putAll(favoriteSupport.getFavoriteCount(clientId, memberCode));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
	
	/**
	 * @desc 查询商品/门店是否已收藏（按用户ID）
	 * @path /favorite/iscollected
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月3日 上午11:31:19
	 * @param memberCoe(必填) productId/outletId(二选其一必填)
	 * @return 
	 */
	@RequestMapping(value = "/favorite/iscollected", method = RequestMethod.GET)
	public void getOutletFavoriteStatus(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, String productId, String outletId) {
		String clientId = getClient(req);
		if(memberCode != null && (!StringUtil.isEmpty(productId) || !StringUtil.isEmpty(outletId))) {
			model.putAll(favoriteSupport.isCollected(clientId, memberCode, productId, outletId));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
	
}
