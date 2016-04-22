package com.froad.cbank.coremodule.normal.boss.support.order;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.order.PresellOrderReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.PresellOrderRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.BossOrderQueryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.order.BossPresellBillListReq;
import com.froad.thrift.vo.order.BossPresellBillListRes;
import com.froad.thrift.vo.order.BossPresellBillVo;
@Service
public class PresellOrderSupport {
	
	@Resource
	BossOrderQueryService.Iface bossOrderQueryService;
	
	public HashMap<String, Object> querypresellBillList(
			PresellOrderReq req) throws TException, ParseException {
		BossPresellBillListReq presellBillReq=new BossPresellBillListReq();
		//客户端id
		if(StringUtil.isNotBlank(req.getClientId())){
			presellBillReq.setClientId(req.getClientId());
		}
		//大订单编号
		if(StringUtil.isNotBlank(req.getSubOrderId())){
			presellBillReq.setSubOrderId(req.getSubOrderId());
		}
		//开始时间
		if(StringUtil.isNotBlank(req.getStartTime())){
			presellBillReq.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
		}
		//结束时间
		if(StringUtil.isNotBlank(req.getEndTime())){
			presellBillReq.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		}
		//商品编号
		if(StringUtil.isNotBlank(req.getProductName())){
			presellBillReq.setProductName(req.getProductName());
		}
		if(StringUtil.isNotBlank(req.getOrderStatus())){
			presellBillReq.setOrderStatus(req.getOrderStatus());
		}
		
		Page page = new Page();
		List<PresellOrderRes> billList = new ArrayList<PresellOrderRes>();	
		BossPresellBillListRes res=null;
		PresellOrderRes bill=null;
		res=bossOrderQueryService.getPresellBillList(presellBillReq);
		HashMap<String,Object> resMap=new HashMap<String,Object>();
		//分页实体转换
		if(res.getBillVo()!=null&&res.getBillVo().size()>0){
			for (BossPresellBillVo vo : res.getBillVo()) {
				bill=new PresellOrderRes();
				BeanUtils.copyProperties(bill,vo);
				billList.add(bill);
			}			
		}
		resMap.put("totalCountExport",billList.size());
		resMap.put("page", page);
		resMap.put("list", billList);
		return resMap;
	}
	
	/**
	 * 预售账单导出
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年8月28日 下午1:14:49
	 * @param req
	 * @return map
	 * @throws ParseException
	 * @throws TException
	 */
	public HashMap<String, Object> querypresellBillListExport(PresellOrderReq req) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求参数
		BossPresellBillListReq presellBillReq = new BossPresellBillListReq();
		if(StringUtil.isNotBlank(req.getClientId())){
			presellBillReq.setClientId(req.getClientId());//客户端id
		}
		if(StringUtil.isNotBlank(req.getSubOrderId())){
			presellBillReq.setSubOrderId(req.getSubOrderId());//子订单编号
		}
		if(StringUtil.isNotBlank(req.getStartTime())){
			presellBillReq.setStartTime(PramasUtil.DateFormat(req.getStartTime()));//开始时间
		}
		if(StringUtil.isNotBlank(req.getEndTime())){
			presellBillReq.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));//结束时间
		}
		if(StringUtil.isNotBlank(req.getProductName())){
			presellBillReq.setProductName(req.getProductName());//商品名称
		}
		if(StringUtil.isNotBlank(req.getOrderStatus())){
			presellBillReq.setOrderStatus(req.getOrderStatus());//订单状态
		}
		//调用SERVER端接口
		ExportResultRes resp = bossOrderQueryService.getPresellBillExportUrl(presellBillReq);
		//封装返回结果对象
		ResultVo result = resp.getResultVo();
		LogCvt.info("预售账单导出返回结果：" + JSON.toJSONString(result));
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
