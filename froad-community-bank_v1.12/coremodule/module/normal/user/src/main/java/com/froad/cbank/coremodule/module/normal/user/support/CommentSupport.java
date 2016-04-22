package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.OutletCommentPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductCommentPojo;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.cbank.coremodule.module.normal.user.utils.DateUtils;
import com.froad.thrift.service.OutletCommentService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.CreateTimeFilterVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletCommentAddVoRes;
import com.froad.thrift.vo.OutletCommentPageVoRes;
import com.froad.thrift.vo.OutletCommentVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductCommentFilterReq;
import com.froad.thrift.vo.ProductCommentPageVo;
import com.froad.thrift.vo.ProductCommentVo;
import com.froad.thrift.vo.RecommentNotEmptyVo;
import com.froad.thrift.vo.ResultVo;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.service.UserSpecService;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年3月27日 下午2:43:21
 * @version 1.0
 * @desc 评论管理支持类
 */
@Service
public class CommentSupport extends BaseSupport {

	@Resource
	private OutletCommentService.Iface outletCommentService;
	
	@Resource
	private ProductService.Iface productService;
	
	@Resource
	private UserSpecService userSpecService;
	
	@Resource
	private OutletService.Iface outletService;
	
	/**
	 * @desc 根据 商品ID 或 用户ID 获取商品评论分页列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月7日 下午2:05:22
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getProductCommentList(String clientId, String memberCode, String productId, Integer days, Boolean isRecomment, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		ProductCommentFilterReq req = new ProductCommentFilterReq();
		ProductCommentPageVo resp = null;
		List<ProductCommentPojo> productCommentList = new ArrayList<ProductCommentPojo>();
		ProductCommentPojo pojo = null;
		PagePojo pagePojo = new PagePojo();
		//封装过滤条件
		HashMap<String, Object> filter = new HashMap<String, Object>();
		filter.put("memberCode", memberCode);
		filter.put("productId", productId);
		if(days != null) {
			days = days < 0 ? -days : days;
			filter.put("pointStartTime", DateUtils.addDays(new Date(), -days).getTime());
			filter.put("pointEndTime", new Date().getTime());
		} else {
			//查询全部
			filter.put("isSeachAll", "true");
		}
		//-1-全部/1-未回复/2-已回复
		if(isRecomment != null) {
			filter.put("isReply", isRecomment ? 2 : 1);
		} else {
			filter.put("isReply", -1);
		}
		//封装分页对象
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setLastPageNumber(lastPageNumber);
		page.setFirstRecordTime(firstRecordTime);
		page.setLastRecordTime(lastRecordTime);
		//封装请求对象
		req.setClientId(clientId);
		req.setFilter(JSON.toJSONString(filter));
		try {
			resp = productService.queryProductComments(req, page);
			if(resp.getPage() != null) {
				BeanUtils.copyProperties(pagePojo, resp.getPage());
			}
			if(!ArrayUtil.empty(resp.getProductCommentVoList())) {
				for(ProductCommentVo tmp : resp.getProductCommentVoList()) {
					pojo = new ProductCommentPojo();
					BeanUtils.copyProperties(pojo, tmp);
					//隐藏用户名
					String memberName = pojo.getMemberName().substring(0, 1)+"****"+ pojo.getMemberName().substring(pojo.getMemberName().length()-1);
					pojo.setMemberName(memberName);
					productCommentList.add(pojo);
				}
			}
			resResult.put("page", pagePojo);
			resResult.put("productCommentList", productCommentList);
		} catch (TException e) {
			LogCvt.info("商品评论分页列表查询异常" + e.getMessage(), e);
		}
		return resResult;
	}
	
	/**
	 * @desc 根据 门店ID 或 用户ID 获取门店评论分页列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月27日 下午3:40:27
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getOutletCommentList(String clientId, Integer pageNumber, Integer pageSize, String memberCode, String outletId, Integer days, Boolean isRecomment, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		OutletCommentVo req = new OutletCommentVo();
		OutletCommentPageVoRes resp = null;
		List<OutletCommentPojo> outletCommentList = new ArrayList<OutletCommentPojo>();
		OutletCommentPojo pojo = null;
		PagePojo pagePojo = new PagePojo();
		//封装page分页对象
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setLastPageNumber(lastPageNumber);
		page.setFirstRecordTime(firstRecordTime);
		page.setLastRecordTime(lastRecordTime);
		//封装是否已回复对象
		RecommentNotEmptyVo recomment = null;
		if(isRecomment != null) {
			recomment = new RecommentNotEmptyVo();
			recomment.setNotEmpty(isRecomment);
		}
		//封装开始/结束时间
		CreateTimeFilterVo timeFilter = null;
		if(days != null) {
			days = days < 0 ? -days : days;
			timeFilter = new CreateTimeFilterVo();
			timeFilter.setBegTime(DateUtils.addDays(new Date(), -days).getTime());
			timeFilter.setEndTime(new Date().getTime());
		}
		//封装请求对象
		req.setClientId(clientId);
		req.setMemberCode(memberCode);
		req.setOutletId(outletId);
		req.setRecommentNotEmpty(recomment);
		req.setCreateTimeFilter(timeFilter);
		try {
			resp = outletCommentService.getOutletCommentByPage(page, req);
			if(resp.getPage() != null) {
				BeanUtils.copyProperties(pagePojo, resp.getPage());
			}
			if(!ArrayUtil.empty(resp.getOutletCommentVoList())) {
				for(OutletCommentVo tmp : resp.getOutletCommentVoList()) {
					pojo = new OutletCommentPojo();
					BeanUtils.copyProperties(pojo, tmp);
					//隐藏用户名
					String memberName = pojo.getMemberName().substring(0, 1)+"****"+ pojo.getMemberName().substring(pojo.getMemberName().length()-1);
					pojo.setMemberName(memberName);
					outletCommentList.add(pojo);
				}
			}
			resResult.put("outletCommentList", outletCommentList);
			resResult.put("page", pagePojo);
		} catch (TException e) {
			LogCvt.info("门店评论分页列表查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "门店评论分页列表查询异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 评论商品
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月7日 下午1:30:12
	 * @param
	 		clientId			//客户端ID
	 		productId			//商品ID
	 		productName			//商品名称
	 		merchantId			//商户ID
	 		merchantName		//商户名称
	 		orderId				//订单ID
	 		orderType			//订单类型
	 		memberCode			//用户ID
	 		memberName			//用户名称
	 		starLevel			//星级评分（1-5）
	 		commentDescription	//评论内容
	 		imagePic			//商品图片
	 		phone				//商户电话
	 * @return 
	 */
	public HashMap<String, Object> addProductComment(String clientId, String memberCode, ProductCommentPojo pojo) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		ProductCommentVo req = new ProductCommentVo();
		ResultVo resp = null;
		try {
			//获取会员名称
			UserResult user = userSpecService.queryMemberByMemberCode(Long.parseLong(memberCode));
			//封装评论请求对象
			req.setClientId(clientId);
			req.setMemberCode(memberCode);
			if(user != null) {
				//隐藏身份证敏感信息(联合登录用户)
				UserSpecDto userDto = user.getUserList().get(0);
				String memberName = userDto.getLoginID();
				if(userDto.getLoginType() == 6 && (memberName.length() == 15 || memberName.length() == 18)){
					memberName = memberName.substring(0, 1) + "******" + memberName.substring(memberName.length()-3, memberName.length());
				}
				req.setMemberName(memberName);
			}
			req.setProductId(pojo.getProductId());
			req.setOrderId(pojo.getOrderId());
			req.setBigOrderId(pojo.getBigOrderId());
			req.setOrderType(pojo.getOrderType());
			req.setStarLevel(pojo.getStarLevel());
			req.setCommentDescription(pojo.getCommentDescription());
			//新增商品评论
			resp = productService.addProductComment(req);
			if(!StringUtil.equals(resp.getResultCode(), Constants.RESULT_CODE_SUCCESS)) {
				resResult.put("code", resp.getResultCode());
				resResult.put("message", resp.getResultDesc());
			} else {
				Monitor.send(MonitorEnums.user_product_comment, String.valueOf(1));
			}
		} catch (TException e) {
			LogCvt.info("商品评论异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "商品评论异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 评论门店
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月27日 下午4:18:48
	 * @param
	 		clientId			//客户端ID
			merchantId			//商户ID
			outletId			//门店ID
			outletName			//门店名称
			memberCode			//会员ID
			memberName			//会员名称
			starLevel			//星级评分（1-5）
			commentDescription	//评论内容
			outletImage			//门店图片
			outletPhone			//门店电话
	 * @return 
	 */
	public HashMap<String, Object> addOutletComment(String clientId, String memberCode, OutletCommentPojo pojo) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		OutletCommentVo req = new OutletCommentVo();
		OriginVo req2 = new OriginVo();
		UserResult user = null;
		OutletCommentAddVoRes resp = null;
		try {		
			boolean hasComment = true ;
			if(pojo.getCommentType() == 1){
				//判断是否已经面对面评论过该订单
				String orderId = pojo.getOrderId();
				if(StringUtil.isBlank(orderId)){
					resResult.put("code",  "9999");
					resResult.put("message", "必要参数为空");
					return resResult;
				}
				
				hasComment =isExitsFaceToFaceComment(memberCode, pojo.getOutletId(),orderId);
			}else if(pojo.getCommentType() == 0){
				//是否当天已经存在商户评论
				hasComment = outletCommentService.isExistComment(memberCode, pojo.getOutletId(), new Date().getTime());
			}else{
				resResult.put("code",  "9999");
				resResult.put("message", "未知的评论渠道");
				return resResult;
			}
			if(hasComment){
				resResult.put("code",  "9999");
				resResult.put("message", "已评论过");
				return resResult;
			}
			
			//获取会员名称
			user = userSpecService.queryMemberByMemberCode(Long.parseLong(memberCode));
			//封装请求对象
			req.setClientId(clientId);
			req.setOutletId(pojo.getOutletId());
			req.setStarLevel(pojo.getStarLevel());
			req.setCommentDescription(pojo.getCommentDescription());
			req.setMemberCode(memberCode);
			req.setCommentType(pojo.getCommentType());
			req.setOrderId(pojo.getOrderId());
			if(user != null) {
				//隐藏身份证敏感信息(联合登录用户)
				UserSpecDto userDto = user.getUserList().get(0);
				String memberName = userDto.getLoginID();
				if(userDto.getLoginType() == 6 && (memberName.length() == 15 || memberName.length() == 18)){
					memberName = memberName.substring(0, 1) + "******" + memberName.substring(memberName.length()-3, memberName.length());
				}
				req.setMemberName(memberName);
			}
			req2.setOperatorId(Long.parseLong(memberCode));
			
			//新增门店评论
			resp = outletCommentService.addOutletComment(req2, req);
			if(StringUtil.equals(resp.getResult().getResultCode(), Constants.RESULT_CODE_SUCCESS)) {
				resResult.put("code",  Constants.RESULT_CODE_SUCCESS);
				resResult.put("message", "添加评论成功");
			} else {
				resResult.put("code", resp.getResult().getResultCode());
				resResult.put("message", resp.getResult().getResultDesc());
				Monitor.send(MonitorEnums.user_outlet_comment, String.valueOf(1));
			}
		} catch (TException e) {
			LogCvt.info("门店评论异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "门店评论异常");
		}
		return resResult;
	}
	
	/**面对面-判断用户针对该订单是否评论过
	 * @author zhouyuhan
	 * @param memberCode 会员code
	 * @param outletId 门店id
	 * @return
	 * @date 2015年11月30日 下午5:39:41
	 */
	public boolean isExitsFaceToFaceComment(String memberCode,String outletId,String orderId) {
		boolean resq = false;
		try {
			resq = outletCommentService.isExitsFaceToFaceComment(memberCode, outletId,orderId);
		} catch (TException e) {
			LogCvt.info("获取用户是否评论过判断结果异常", e);

		}
		return resq;
	}
	
	/**
	 * @desc 获取评论统计数量
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月10日 下午2:20:17
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getCommentCount(String clientId, String memberCode, String productId, String merchantId, String outletId) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		//封装商品评论统计请求对象
		ProductCommentFilterReq reqProduct = new ProductCommentFilterReq();
		HashMap<String, String> filter = new HashMap<String, String>();
		filter.put("productId", productId);
		filter.put("memberCode", memberCode);
		reqProduct.setClientId(clientId);
		reqProduct.setFilter(JSON.toJSONString(filter));
		//封装门店评论统计请求对象
		OutletCommentVo reqOutlet = new OutletCommentVo();
		reqOutlet.setClientId(clientId);
		reqOutlet.setMerchantId(merchantId);
		reqOutlet.setOutletId(outletId);
		reqOutlet.setMemberCode(memberCode);
		try {
			if(!StringUtil.isEmpty(memberCode)) {
				//个人（商品/门店）评论数统计
				LogCvt.info(">>进入个人评论数统计");
				resResult.put("selfProductCommentCount", productService.queryProductCommentCount(reqProduct));
				resResult.put("selfOutletCommentCount", outletCommentService.getOutletCommentSum(reqOutlet));
			} else if(!StringUtil.isEmpty(merchantId) && !StringUtil.isEmpty(outletId)) {
				//门店评论数统计
				LogCvt.info(">>进入门店评论数统计");
				resResult.put("outletCommentCount", outletCommentService.getOutletCommentSum(reqOutlet));
			} else if(!StringUtil.isEmpty(productId)) {
				//商品评论数统计
				LogCvt.info(">>进入商品评论数统计");
				resResult.put("productCommentCount", productService.queryProductCommentCount(reqProduct));
			}
		} catch (TException e) {
			LogCvt.info("评论数量统计异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "评论数量统计异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 获取商品评论详情
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月11日 上午9:55:23
	 * @param clientId/commentId
	 * @return productCommentDetail
	 */
	public HashMap<String, Object> getProductCommentDetail(String clientId, String commentId) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		ProductCommentVo req = new ProductCommentVo();
		ProductCommentPojo productCommentDetail = null;
		//封装请求对象
		req.setClientId(clientId);
		req.setCommentId(commentId);
		try {
			req = productService.queryProductComment(req);
			if(req != null) {
				productCommentDetail = new ProductCommentPojo();
				BeanUtils.copyProperties(productCommentDetail, req);
			}
			resResult.put("productCommentDetail", productCommentDetail);
		} catch (TException e) {
			LogCvt.info("商品评论详情查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "商品评论详情查询异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 获取门店评论详情
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月13日 上午10:08:14
	 * @param clientId/commentId
	 * @return outletCommentDetail
	 */
	public HashMap<String, Object> getOutletCommentDetail(String clientId, String commentId) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		OutletCommentVo resp = new OutletCommentVo();
		OutletCommentPojo outletCommentDetail = null;
		try {
			resp = outletCommentService.getOutletCommentById(commentId);
			if(resp != null) {
				outletCommentDetail = new OutletCommentPojo();
				BeanUtils.copyProperties(outletCommentDetail, resp);
			}
			resResult.put("outletCommentDetail", outletCommentDetail);
		} catch (TException e) {
			LogCvt.info("门店评论详情查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "门店评论详情查询异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 查看当前门店当日是否已评价
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年5月6日 下午7:01:03
	 * @param memberCode/outletId
	 * @return isComment
	 */
	public HashMap<String, Object> isComment(String memberCode, String outletId) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		boolean isComment = false;
		try {
			isComment = outletCommentService.isExistComment(memberCode, outletId, new Date().getTime());
			resResult.put("isComment", isComment);
		} catch (TException e) {
			LogCvt.info("门店是否已评论状态查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "门店是否已评论状态查询异常");
		}
		return resResult;
	}
	
	
	
	
	
	
	/**
	 * 获取会员商品评论总数
	 * @param clientId
	 * @param memberCode
	 * @return
	 */
	public int getMemberProductCommentCount(String clientId, Long memberCode){
		
		ProductCommentFilterReq req = new ProductCommentFilterReq();
		ProductCommentPageVo resp = null;
		//封装过滤条件
		HashMap<String, Object> filter = new HashMap<String, Object>();
		filter.put("memberCode", memberCode);
		filter.put("isSeachAll", "true");
		//-1-全部/1-未回复/2-已回复
		filter.put("isReply", -1);
		//封装分页对象
		PageVo page = new PageVo();
		page.setPageNumber(1);
		page.setPageSize(10);
		page.setLastPageNumber(0);
		page.setFirstRecordTime(0);
		page.setLastRecordTime(0);
		//封装请求对象
		req.setClientId(clientId);
		req.setFilter(JSON.toJSONString(filter));
		try {
			resp = productService.queryProductComments(req, page);
		} catch (TException e) {
			LogCvt.info("商品评论查询异常" + e.getMessage(), e);
			return 0;
		}
		if(resp !=null && resp.getPage() != null){
			return resp.getPage().getTotalCount();
		}else{
			return 0;
		}
		
	}
	
	
	
	
	/**
	 * 获取会员商户评论总数
	 * @param clientId
	 * @param memberCode
	 * @return
	 */
	public int getMemberOutletCommentCount(String clientId, Long memberCode) {
		OutletCommentVo req = new OutletCommentVo();
		OutletCommentPageVoRes resp = null;
		//封装page分页对象
		PageVo page = new PageVo();
		page.setPageNumber(1);
		page.setPageSize(10);
		page.setLastPageNumber(0);
		page.setFirstRecordTime(0);
		page.setLastRecordTime(0);
		
		//封装请求对象
		req.setClientId(clientId);
		req.setMemberCode(String.valueOf(memberCode));
		try {
			resp = outletCommentService.getOutletCommentByPage(page, req);

		} catch (TException e) {
			LogCvt.info("门店评论查询异常" + e.getMessage(), e);
			return 0;
		}
		if(resp !=null && resp.getPage() != null){
			return resp.getPage().getTotalCount();
		}else{
			return 0;
		}
	}
	
	
	
}
