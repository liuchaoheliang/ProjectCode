package com.froad.cbank.coremodule.normal.boss.support.pay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.pay.PaymentInfoVo;
import com.froad.cbank.coremodule.normal.boss.pojo.pay.PaymentInfoVoReq;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.BossQueryPaymentService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.BossQueryPaymentListRes;
import com.froad.thrift.vo.payment.BossQueryPaymentVo;

@Service
public class PaySupport {
	
	@Resource
	private BossQueryPaymentService.Iface bossQueryPaymentService;
	
	/**
	  * 方法描述：支付记录列表查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @throws Exception 
	  * @time: 2015年4月27日 下午4:43:27
	  */
	public HashMap<String, Object> queryPaymentList(PaymentInfoVoReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<PaymentInfoVo> list = new ArrayList<PaymentInfoVo>();
		PaymentInfoVo temp = null;
		Page page = new Page();
		//封装请求对象
		BossQueryPaymentVo req = new BossQueryPaymentVo();
		BeanUtils.copyProperties(req, pojo);
		//封装分页对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pojo.getPageNumber());
		pageVo.setPageSize(pojo.getPageSize());
		pageVo.setFirstRecordTime(pojo.getFirstRecordTime());
		pageVo.setLastPageNumber(pojo.getLastPageNumber());
		pageVo.setLastRecordTime(pojo.getLastRecordTime());
		pageVo.setBegDate(StringUtil.isNotBlank(pojo.getBeginTime()) ? PramasUtil.DateFormat(pojo.getBeginTime()) : 0);//条件筛选起始时间
		pageVo.setEndDate(StringUtil.isNotBlank(pojo.getEndTime()) ? PramasUtil.DateFormatEnd(pojo.getEndTime()) : 0);//条件筛选结束时间
		if(pageVo.getBegDate() > pageVo.getEndDate()) {
			throw new BossException("起始筛选时间不可大于结束筛选时间");
		}
		req.setPageVo(pageVo);
		
		//所属组织
		List<String> orgs = new ArrayList<String>();
		for(String org:pojo.getOrgCodes().split(",")){
			orgs.add(org);
		}
		req.setOrgCodes(orgs);
		
		//调用SERVER端接口
		BossQueryPaymentListRes resp = bossQueryPaymentService.getPaymentList(req);
		//封装返回对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			BeanUtils.copyProperties(page, resp.getPageVo());
			if(!ArrayUtil.empty(resp.getPaymentQueryVos())) {
				for(BossQueryPaymentVo tmp : resp.getPaymentQueryVos()) {
					temp = new PaymentInfoVo();
					BeanUtils.copyProperties(temp, tmp);
					list.add(temp);
				}
			}
			map.put("pageCount", page.getPageCount());
			map.put("totalCountExport", page.getTotalCount());
			map.put("pageNumber", page.getPageNumber());
			map.put("page", page);
			map.put("payOrderList", list);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return  map;
	}
	
	/**
	 * 支付订单列表导出
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年8月31日 上午10:49:39
	 * @param voReq
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> payListExport(PaymentInfoVoReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		BossQueryPaymentVo req = new BossQueryPaymentVo();
		BeanUtils.copyProperties(req, pojo);
		PageVo pageVo = new PageVo();
		pageVo.setBegDate(StringUtil.isNotBlank(pojo.getBeginTime()) ? PramasUtil.DateFormat(pojo.getBeginTime()) : 0);//条件筛选起始时间
		pageVo.setEndDate(StringUtil.isNotBlank(pojo.getEndTime()) ? PramasUtil.DateFormatEnd(pojo.getEndTime()) : 0);//条件筛选结束时间
		if(pageVo.getBegDate() > pageVo.getEndDate()) {
			throw new BossException("起始筛选时间不可大于结束筛选时间");
		}
		req.setPageVo(pageVo);
		
		//所属组织
		List<String> orgs = new ArrayList<String>();
		for(String org:pojo.getOrgCodes().split(",")){
			orgs.add(org);
		}
		req.setOrgCodes(orgs);
		
		//调用SERVER端接口
		ExportResultRes resp = bossQueryPaymentService.exportPaymentList(req);
		//封装返回对象
		ResultVo result = resp.getResultVo();
		LogCvt.info("支付订单列表导出返回结果：" + JSON.toJSONString(result));
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
