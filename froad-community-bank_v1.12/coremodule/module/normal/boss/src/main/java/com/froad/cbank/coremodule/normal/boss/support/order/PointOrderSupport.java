package com.froad.cbank.coremodule.normal.boss.support.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.enums.OrderStatus;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.order.PointOrderReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.PointOrderRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.BossOrderQueryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.order.BossPointOrderListReq;
import com.froad.thrift.vo.order.BossPointOrderListRes;
import com.froad.thrift.vo.order.BossPointOrderVo;

/**
 * 
 * 类描述:boss线上积分兑换查询support
 * 
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author "chenzhangwei"
 * @time 2015年8月20日 上午11:47:24
 * @email "chenzhangwei@f-road.com.cn"
 */
@Service
public class PointOrderSupport {

	// 注入boss线上积分兑换查询BossOrderQueryService
	@Resource
	BossOrderQueryService.Iface bossOrderQueryService;

	/**
	 * 
	 * 方法描述:boss线上积分兑换查询
	 * 
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年8月20日 上午11:50:17
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> queryPonitOrderByPage(PointOrderReq req)
			throws Exception {
		PageVo pageVo = new PageVo();
		BossPointOrderListReq orderReq = new BossPointOrderListReq();

		// 订单编号
		if (StringUtil.isNotBlank(req.getOrderId())) {
			orderReq.setSubOrderId(req.getOrderId());
		}
		// 开始时间
		if (StringUtil.isNotBlank(req.getStartTime())) {
			orderReq.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
		}
		// 结束时间
		if (StringUtil.isNotBlank(req.getEndTime())) {
			orderReq.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		}
		// 商品名称
		if (StringUtil.isNotBlank(req.getProductName())) {
			orderReq.setProductName(req.getProductName());
		}
		// 订单状态
		if (StringUtil.isNotBlank(req.getOrderStatus())) {
			orderReq.setOrderStatus(req.getOrderStatus());
		}
		// 客户端id
		if (StringUtil.isNotBlank(req.getClientId())) {
			orderReq.setClientId(req.getClientId());
		}
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<PointOrderRes> list = new ArrayList<PointOrderRes>();
		PointOrderRes res = null;
		// 分页对象转化
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		Page page = new Page();
		orderReq.setPageVo(pageVo);
		LogCvt.info("请求分页数据。。。:" + JSON.toJSONString(orderReq));
		// 执行查询
		BossPointOrderListRes pointRes = bossOrderQueryService
				.queryPointOrderList(orderReq);
		// 分页实体转换
		BeanUtils.copyProperties(page, pointRes.getPageVo());
		if (!ArrayUtil.empty(pointRes.getOrders())) {
			for (BossPointOrderVo temp : pointRes.getOrders()) {
				res = new PointOrderRes();
				BeanUtils.copyProperties(res, temp);
				Date dd =DateUtil.longToDate(Long.valueOf(temp.getCreateTime()));
				String dateTmp = DateUtil.formatDate(dd, true);
				res.setCreateTime(dateTmp);
				list.add(res);
			}
		}
		resMap.put("pageCount", page.getPageCount());
		resMap.put("totalCountExport", page.getTotalCount());
		resMap.put("pageNumber", page.getPageNumber());
		resMap.put("page", page);
		resMap.put("pointOrderList", list);
		return resMap;
	}
	
	/**
	 * 
	 * 方法描述:订单状态转换
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年8月24日 下午2:48:59
	 * @param ls
	 * @return
	 */
	public List<PointOrderRes> exChange(List<PointOrderRes> ls){
		for (PointOrderRes pointOrderRes : ls) {
			pointOrderRes.setOrderStatus(OrderStatus.getDescribeByCode(pointOrderRes.getOrderStatus()));
		}
		return ls;
	}
	
	/**
	 * 线上积分兑换列表导出
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月8日 下午3:24:20
	 * @return map
	 */
	public HashMap<String, Object> exportPointOrder(PointOrderReq req) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		BossPointOrderListReq orderReq = new BossPointOrderListReq();
		// 订单编号
		if (StringUtil.isNotBlank(req.getOrderId())) {
			orderReq.setSubOrderId(req.getOrderId());
		}
		// 开始时间
		if (StringUtil.isNotBlank(req.getStartTime())) {
			orderReq.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
		}
		// 结束时间
		if (StringUtil.isNotBlank(req.getEndTime())) {
			orderReq.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		}
		// 商品名称
		if (StringUtil.isNotBlank(req.getProductName())) {
			orderReq.setProductName(req.getProductName());
		}
		// 订单状态
		if (StringUtil.isNotBlank(req.getOrderStatus())) {
			orderReq.setOrderStatus(req.getOrderStatus());
		}
		// 客户端id
		if (StringUtil.isNotBlank(req.getClientId())) {
			orderReq.setClientId(req.getClientId());
		}
		//调用SERVER端接口
		ExportResultRes resp = bossOrderQueryService.exportPointOrderTicket(orderReq);
		//封装返回结果
		ResultVo result = resp.getResultVo();
		LogCvt.info("线上积分兑换列表导出返回结果：" + JSON.toJSONString(result));
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			HashMap<String, String> obj = new HashMap<String, String>();
			obj.put("url", resp.getUrl());
			map.put("result", obj);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
}
