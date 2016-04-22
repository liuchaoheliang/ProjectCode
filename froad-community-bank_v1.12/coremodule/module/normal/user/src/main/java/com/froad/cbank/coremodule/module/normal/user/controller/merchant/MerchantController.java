package com.froad.cbank.coremodule.module.normal.user.controller.merchant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.FunctionModule;
import com.froad.cbank.coremodule.module.normal.user.support.MerchantSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;



/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年3月24日 下午4:23:05
 * @version 1.0
 * @desc 商户类
 */
@Controller
@RequestMapping
public class MerchantController extends BasicSpringController {
	
	@Resource
	private MerchantSupport merchantSupport;
	
	/**
	 * @desc 获取商户分类列表
	 * @path /merchant/category
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月31日 上午11:33:07
	 * @param 
	 * @return 
	 */
	@RequestMapping(value = "/merchant/category", method = RequestMethod.GET)
	public void getMerchantCategory(ModelMap model, HttpServletRequest req, Long parentId, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		String clientId = getClient(req);
		if(pageNumber != null || pageSize != null) {
			if(pageNumber == null || pageNumber < 1) {
				pageNumber = Constants.PAGE_NUMBER;
			}
			if(pageSize == null || pageSize < 0) {
				pageSize = Constants.PAGE_SIZE;
			}
			lastPageNumber = lastPageNumber == null ? 1 : lastPageNumber;
			firstRecordTime = firstRecordTime == null ? 0L : firstRecordTime;
			lastRecordTime = lastRecordTime == null ? 0L : lastRecordTime;
		}
		model.putAll(merchantSupport.getMerchantCategory(clientId, parentId, pageNumber, pageSize, lastPageNumber, firstRecordTime, lastRecordTime));
	}

	/**
	 * @desc 根据定位信息获取‘按距离升序门店列表’（若无定位信息，则获取‘按收藏数降序门店列表’）
	 * @path /outlet/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月24日 下午4:39:05
	 * @param 
	 * @return 
	 */
	@FunctionModule
	@RequestMapping(value = "/outlet/list", method = RequestMethod.GET)
	public void getOutletList(ModelMap model, HttpServletRequest req, Long areaId, Long parentAreaId, Double longitude, Double latitude, Long categoryId, String keyword, Integer pageNumber, Integer pageSize, Integer pageCount,Integer totalCount,Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime, Integer sortBy, Double distance) {
		Long startTime = System.currentTimeMillis();
		
		String clientId = getClient(req);
		if(pageNumber == null || pageNumber < 1) {
			pageNumber = Constants.PAGE_NUMBER;
		}
		if(pageSize == null || pageSize < 0) {
			pageSize = Constants.PAGE_SIZE;
		}
		if(pageCount == null || pageCount < 0) {
			pageCount = Constants.PAGE_COUNT;
		}
		if(totalCount == null || totalCount < 0) {
			totalCount = Constants.TOTAL_COUNT;
		}
		lastPageNumber = lastPageNumber == null ? 1 : lastPageNumber;
		firstRecordTime = firstRecordTime == null ? 0L : firstRecordTime;
		lastRecordTime = lastRecordTime == null ? 0L : lastRecordTime;
		sortBy = sortBy == null ? 0 : sortBy;
		distance = distance == null ? 0 : distance;  //距离范围，单位（米），不传默认为0，查询全部
		model.putAll(merchantSupport.getOutletList(clientId, areaId, parentAreaId, longitude, latitude, categoryId, keyword, pageNumber, pageSize,pageCount,totalCount, lastPageNumber, firstRecordTime, lastRecordTime, sortBy, distance));

		Monitor.send(MonitorEnums.user_outlet_list, String.valueOf(System.currentTimeMillis() - startTime));
	}
	
	/**
	 * @desc 根据商户ID获取门店列表
	 * @path /merchant/outlet/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月7日 下午11:22:59
	 * @param 
	 * @return 
	 */
	@RequestMapping(value = "/merchant/outlet/list", method = RequestMethod.GET)
	public void getMerchantOutletList(ModelMap model, HttpServletRequest req, String merchantId, Integer pageNumber, Integer pageSize, Integer lastPageNumber,Double longitude, Double latitude, Long firstRecordTime, Long lastRecordTime) {
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
		if(StringUtil.isNotBlank(merchantId)) {
			model.putAll(merchantSupport.getOutletListByMerchantId(clientId, merchantId, pageNumber, pageSize, lastPageNumber,longitude,latitude, firstRecordTime, lastRecordTime));
		} else {
			model.put("code", "9999");
			model.put("message", "商户ID不可为空");
		}
		
	}
	
	/**
	 * @desc 获取门店详情（按门店ID）
	 * @path /outlet/detail
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月24日 下午4:44:56
	 * @param 
	 * @return 
	 */
	@FunctionModule
	@RequestMapping(value = {"/outlet/detail","/view/merchantDetail"}, method = RequestMethod.GET)
	public void getOutletDetail(ModelMap model, HttpServletRequest req, @RequestHeader(value="memberCode",defaultValue="" ) Long memberCode, String outletId) {
		String clientId = getClient(req);
		if(!StringUtil.isEmpty(outletId)) {
			model.putAll(merchantSupport.getOutletDetail(clientId, memberCode, outletId));
		} else {
			model.put("code", "9999");
			model.put("message", "门店ID不可为空");
		}
	}
	
	/**
	 * @desc 获取门店相册列表
	 * @path /outlet/photo/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月2日 下午7:27:53
	 * @param 
	 * @return 
	 */
	@RequestMapping(value = "/outlet/photo/list", method = RequestMethod.GET)
	public void getOutletPhotoList(ModelMap model, HttpServletRequest req, String outletId) {
		String clientId = getClient(req);
		if(!StringUtil.isEmpty(outletId)) {
			model.putAll(merchantSupport.getOutletPhotoList(clientId, outletId));
		} else {
			model.put("code", "9999");
			model.put("message", "门店ID不可为空");
		}
	}
	
	
	
	/**
	  * 方法描述：根据区域ID查询区域下门店集合
	  * @param: areaId 区域ID标识
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月16日 下午3:03:03
	  */
	@RequestMapping(value = "/outlet/simple/list" , method = RequestMethod.GET)
	public void getProductOutlet(ModelMap model, HttpServletRequest req, Long areaId ){
		String clientId = getClient(req);
		if(areaId!=null){
			model.putAll(merchantSupport.getOutlet(areaId, clientId));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	
	/**
	 * @desc 获取商户分类列表
	 * @path /merchant/category
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月31日 上午11:33:07
	 * @param 
	 * @return 
	 */
	@RequestMapping(value = "/merchant/category/list", method = RequestMethod.GET)
	public void getMerchantCategoryList(ModelMap model, HttpServletRequest req, Long parentId ,Long areaId) {
		String clientId = getClient(req);
		if(StringUtil.isNotBlank(clientId)){
			model.putAll(merchantSupport.getMerchantCategoryList(clientId, parentId,areaId));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	
}
