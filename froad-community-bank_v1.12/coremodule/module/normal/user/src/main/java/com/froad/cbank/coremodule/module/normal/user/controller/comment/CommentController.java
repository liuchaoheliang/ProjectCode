package com.froad.cbank.coremodule.module.normal.user.controller.comment;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.pojo.OutletCommentPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductCommentPojo;
import com.froad.cbank.coremodule.module.normal.user.support.CommentSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年3月24日 下午4:17:54
 * @version 1.0
 * @desc 评论类
 */
@Controller
@RequestMapping
public class CommentController extends BasicSpringController {

	@Resource
	private CommentSupport commentSupport;

	/**
	 * @desc 获取商品评论分页列表
	 * @path /comment/product/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月30日 下午3:36:03
	 * @param productId(必填)
	 * @return 
	 */
	@RequestMapping(value = "/comment/product/list", method = RequestMethod.GET)
	public void getProductCommentList(ModelMap model, HttpServletRequest req, String productId, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		Long startTime = System.currentTimeMillis();
		
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
		if(!StringUtil.isEmpty(productId)) {
			model.putAll(commentSupport.getProductCommentList(clientId, null, productId, null, null, pageNumber, pageSize, lastPageNumber, firstRecordTime, lastRecordTime));

			Monitor.send(MonitorEnums.user_product_comment_list, String.valueOf(System.currentTimeMillis() - startTime));
		} else {
			model.put("code", "9999");
			model.put("message", "商品ID不可为空");
		}
	}
	
	/**
	 * @desc 获取个人商品评论列表
	 * @path /self/comment/product/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月24日 上午11:52:16
	 * @param memberCode(必填)/days(要筛选的日期范围)/isRecomment(是否已回复)
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/self/comment/product/list", method = RequestMethod.GET)
	public void getSelfProductCommentList(ModelMap model, HttpServletRequest req, @RequestHeader String memberCode, Integer days, Boolean isRecomment, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		Long startTime = System.currentTimeMillis();
		
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
		if(!StringUtil.isEmpty(memberCode)) {
			model.putAll(commentSupport.getProductCommentList(clientId, memberCode, null, days, isRecomment, pageNumber, pageSize, lastPageNumber, firstRecordTime, lastRecordTime));

			Monitor.send(MonitorEnums.user_self_product_comment_list, String.valueOf(System.currentTimeMillis() - startTime));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}

	/**
	 * @desc 获取门店评论分页列表（按门店ID）
	 * @path /comment/outlet/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月24日 下午5:06:31
	 * @param outletId(必填)
	 * @return 
	 */
	@RequestMapping(value = "/comment/outlet/list", method = RequestMethod.GET)
	public void getOutletCommentList(ModelMap model, HttpServletRequest req, Integer pageNumber, Integer pageSize, String outletId, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		Long startTime = System.currentTimeMillis();
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
		if(!StringUtil.isEmpty(outletId)) {
			model.putAll(commentSupport.getOutletCommentList(clientId, pageNumber, pageSize, null, outletId, null, null, lastPageNumber, firstRecordTime, lastRecordTime));

			Monitor.send(MonitorEnums.user_outlet_comment_list, String.valueOf(System.currentTimeMillis() - startTime));
		} else {
			model.put("code", "9999");
			model.put("message", "门店ID不可为空");
		}
	}
	
	/**
	 * @desc 获取个人门店评论列表
	 * @path /self/comment/outlet/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月24日 上午11:59:24
	 * @param memberCode(必填)/days(筛选的日期范围)/isRecomment(是否已回复)
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/self/comment/outlet/list", method = RequestMethod.GET)
	public void getSelfOutletCommentList(ModelMap model, HttpServletRequest req, Integer pageNumber, Integer pageSize, @RequestHeader String memberCode, Integer days, Boolean isRecomment, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		Long startTime = System.currentTimeMillis();
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
		if(!StringUtil.isEmpty(memberCode)) {
			model.putAll(commentSupport.getOutletCommentList(clientId, pageNumber, pageSize, memberCode, null, days, isRecomment, lastPageNumber, firstRecordTime, lastRecordTime));

			Monitor.send(MonitorEnums.user_self_outlet_comment_list, String.valueOf(System.currentTimeMillis() - startTime));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}

	/**
	 * @desc 评论商品
	 * @path /comment/product/add
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月27日 下午3:57:38
	 * @param 
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/comment/product/add", method = RequestMethod.POST)
	public void addProductComment(ModelMap model, HttpServletRequest req, @RequestHeader String memberCode, @RequestBody ProductCommentPojo pojo) {
		String clientId = getClient(req);
		if(!StringUtil.isEmpty(memberCode)) {
			model.putAll(commentSupport.addProductComment(clientId, memberCode, pojo));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
	
	/**
	 * @desc 评论门店
	 * @path /comment/outlet/add
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月24日 下午5:04:50
	 * @param 
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/comment/outlet/add", method = RequestMethod.POST)
	public void addOutletComment(ModelMap model, HttpServletRequest req, @RequestHeader String memberCode, @RequestBody OutletCommentPojo pojo) {
		String clientId = getClient(req);
		if(!StringUtil.isEmpty(memberCode)) {
			model.putAll(commentSupport.addOutletComment(clientId, memberCode, pojo));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
	
	/**
	 * 面对面-判断用户是否评论过
	 * @author zhouyuhan
	 * @param model
	 * @param req
	 * @param memberCode 会员code
	 * @param outletId 门店id
	 * @date 2015年11月30日 下午5:41:01
	 */
	@Token
	@RequestMapping(value = "/comment/outlet/isHadComment", method = RequestMethod.GET)
	public void isExitsFaceToFaceComment(ModelMap model, HttpServletRequest req, @RequestHeader String memberCode,String outletId,String orderId) {
		if(!StringUtil.isEmpty(memberCode) && !StringUtil.isEmpty(outletId)) {
			boolean flag = commentSupport.isExitsFaceToFaceComment(memberCode,outletId,orderId);
			model.put("boolean",flag);
		} else {
			model.put("code", "9999");
			model.put("message", "必传参数不可为空");
		}
	}
	
	/**
	 * @desc 统计评论数量
	 * @path /comment/count
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月3日 上午11:02:51
	 * @param productId/merchantId + outletId(两组选其一必填)
	 * @return 
	 */
	@RequestMapping(value = "/comment/count", method = RequestMethod.GET)
	public void getCommentCount(ModelMap model, HttpServletRequest req, String productId, String merchantId, String outletId) {
		String clientId = getClient(req);
		if(!StringUtil.isEmpty(productId) || (!StringUtil.isEmpty(merchantId) && !StringUtil.isEmpty(outletId))) {
			model.putAll(commentSupport.getCommentCount(clientId, null, productId, merchantId, outletId));
		} else {
			model.put("code", "9999");
			model.put("message", "必要值不可为空");
		}
	}
	
	/**
	 * @desc 统计个人商品/门店 评论 数量
	 * @path /self/comment/count
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月24日 下午1:43:05
	 * @param memberCode(必填)
	 * @return
	 */
	@RequestMapping(value = "/self/comment/count", method = RequestMethod.GET)
	public void getSelfCommentCount(ModelMap model, HttpServletRequest req, @RequestHeader String memberCode) {
		String clientId = getClient(req);
		if(!StringUtil.isEmpty(memberCode)) {
			model.putAll(commentSupport.getCommentCount(clientId, memberCode, null, null, null));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
	
	/**
	 * @desc 获取商品评论详情
	 * @path /comment/product/detail
	 * @author 陆万全 lwq luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月24日 下午5:07:54
	 * @param commentId(必填)
	 * @return 
	 */
	@RequestMapping(value = "/comment/product/detail", method = RequestMethod.GET)
	public void getProductCommentDetail(ModelMap model, HttpServletRequest req, String commentId) {
		String clientId = getClient(req);
		if(!StringUtil.isEmpty(commentId)) {
			model.putAll(commentSupport.getProductCommentDetail(clientId, commentId));
		} else {
			model.put("code", "9999");
			model.put("message", "评论ID不可为空");
		}
	}
	
	/**
	 * @desc 获取门店评论详情
	 * @path /comment/outlet/detail
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月13日 下午2:26:30
	 * @param commentId(必填)
	 * @return 
	 */
	@RequestMapping(value = "/comment/outlet/detail", method = RequestMethod.GET)
	public void getOutletCommentDetail(ModelMap model, HttpServletRequest req, String commentId) {
		String clientId = getClient(req);
		if(!StringUtil.isEmpty(commentId)) {
			model.putAll(commentSupport.getOutletCommentDetail(clientId, commentId));
		} else {
			model.put("code", "9999");
			model.put("message", "评论ID不可为空");
		}
	}
	
	/**
	 * @desc 当日是否已评价门店
	 * @path /comment/outlet/iscomment
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年5月6日 下午6:14:55
	 * @param memberCode(必填)，outletId(必填)
	 * @return 
	 */
	@RequestMapping(value = "/comment/outlet/iscomment", method= RequestMethod.GET)
	public void isComment(ModelMap model, @RequestHeader String memberCode, String outletId) {
		if(memberCode != null && !StringUtil.isEmpty(outletId)) {
			model.putAll(commentSupport.isComment(memberCode, outletId));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID和门店ID不可为空");
		}
	}
}
