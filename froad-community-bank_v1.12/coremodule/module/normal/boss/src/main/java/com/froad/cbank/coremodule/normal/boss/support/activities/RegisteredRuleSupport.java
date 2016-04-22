package com.froad.cbank.coremodule.normal.boss.support.activities;

import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.RegisteredRuleAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.RegisteredRuleDetailRes;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.RegisteredRuleDisReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.RegisteredRuleListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.RegisteredRuleListRes;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.RegisteredRuleUpdReq;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.RegisteredRuleInfoService;
import com.froad.thrift.service.VouchersRuleInfoService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;
import com.froad.thrift.vo.active.ActiveTagRelationVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.ExportRegisteredRuleInfoInfoRes;
import com.froad.thrift.vo.active.FindPageRegisteredRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindRegisteredRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindVouchersRuleInfoVoResultVo;
import com.froad.thrift.vo.active.RegisteredDetailRuleVo;
import com.froad.thrift.vo.active.RegisteredRuleInfoPageVoRes;
import com.froad.thrift.vo.active.RegisteredRuleInfoVo;

/**
 * @author luwanquan@f-road.com.cn
 * @date 2015年12月2日 上午10:11:17
 */
@Service
public class RegisteredRuleSupport {
	
	@Resource
	private RegisteredRuleInfoService.Iface registeredRuleInfoService;
	
	@Resource
	private ClientSupport clientSupport;
	
	@Resource
	private VouchersRuleInfoService.Iface vouchersRuleInfoService;
	
	/**
	 * 注册促销规则分页列表
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午12:33:18
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> queryRegisteredRuleList(RegisteredRuleListReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<RegisteredRuleListRes> list = new ArrayList<RegisteredRuleListRes>();
		RegisteredRuleListRes temp = null;
		Page page = new Page();
		//校验参数
		long startTime = StringUtil.isNotBlank(pojo.getStartTime()) ? PramasUtil.DateFormat(pojo.getStartTime()) : 0;
		long endTime = StringUtil.isNotBlank(pojo.getEndTime()) ? PramasUtil.DateFormatEnd(pojo.getEndTime()) : 0;
		if(startTime > endTime) {
			throw new BossException("活动开始时间不可大于活动结束时间");
		}
		
		//封装请求对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pojo.getPageNumber());
		pageVo.setPageSize(pojo.getPageSize());
		pageVo.setLastPageNumber(pojo.getLastPageNumber());
		pageVo.setFirstRecordTime(pojo.getFirstRecordTime());
		pageVo.setLastRecordTime(pojo.getLastRecordTime());
		
		RegisteredRuleInfoVo req = new RegisteredRuleInfoVo();
		ActiveBaseRuleVo req2 = new ActiveBaseRuleVo();
		req2.setClientId(StringUtil.isBlank(pojo.getClientId()) ? null : pojo.getClientId());
		req2.setActiveName(StringUtil.isBlank(pojo.getActiveName()) ? null : pojo.getActiveName());
		req2.setStatus(StringUtil.isBlank(pojo.getStatus()) ? null : pojo.getStatus());
		req2.setExpireStartTime(startTime);
		req2.setExpireEndTime(endTime);
		
		req.setActiveBaseRule(req2);
		//调用SERVER端接口
		FindPageRegisteredRuleInfoVoResultVo resp = registeredRuleInfoService.getRegisteredRuleInfoByPage(pageVo, req);
		//封装返回结果对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			RegisteredRuleInfoPageVoRes res = resp.getRegisteredRuleInfoPageVoRes();
			BeanUtils.copyProperties(page, resp.getRegisteredRuleInfoPageVoRes().getPage());
			if(!ArrayUtil.empty(res.getRegisteredRuleInfoVoList())) {
				List<ClientRes> client = clientSupport.getClient();//获取客户端列表
				for(RegisteredRuleInfoVo tmp : res.getRegisteredRuleInfoVoList()) {
					temp = new RegisteredRuleListRes();
					BeanUtils.copyProperties(temp, tmp.getRegisteredDetailRule());
					BeanUtils.copyProperties(temp, tmp.getActiveBaseRule());
					//获取客户端名称
					for(ClientRes c : client){
						if(c.getClientId().equals(tmp.getActiveBaseRule().getClientId())){
							temp.setClientName(c.getClientName());
							break;
						}
					}
					list.add(temp);
				}
			}
			map.put("registeredRuleList", list);
			map.put("page", page);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 注册规则促销详情
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午12:34:48
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> queryRegisteredRuleDetail(String clientId, String activeId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		RegisteredRuleDetailRes reg = null;
		//调用SERVER端接口
		FindRegisteredRuleInfoVoResultVo resp = registeredRuleInfoService.getRegisteredRuleInfoById(clientId, activeId);
		//封装返回结果对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			reg = new RegisteredRuleDetailRes();
			BeanUtils.copyProperties(reg, resp.getRegisteredRuleInfoVo().getActiveTagRelation());
			BeanUtils.copyProperties(reg, resp.getRegisteredRuleInfoVo().getRegisteredDetailRule());
			BeanUtils.copyProperties(reg, resp.getRegisteredRuleInfoVo().getActiveBaseRule());
			DecimalFormat df = new DecimalFormat("0.00");
			reg.setBankRate(df.format(Integer.valueOf(reg.getBankRate()) / 1000.0).toString());
			reg.setFftRate(df.format(Integer.valueOf(reg.getFftRate()) / 1000.0).toString());
			//获取客户端名称
			List<ClientRes> client = clientSupport.getClient();
			for(ClientRes c : client){
				if(c.getClientId().equals(resp.getRegisteredRuleInfoVo().getActiveBaseRule().getClientId())){
					reg.setClientName(c.getClientName());
					break;
				}
			}
			//获取所奖励的红包规则名称
			if(StringUtil.isNotBlank(reg.getVouchersActiveId())) {
				FindVouchersRuleInfoVoResultVo res = vouchersRuleInfoService.getActiveRuleInfoById(reg.getClientId(), reg.getVouchersActiveId());
				if(res.getVouchersRuleInfo() != null) {
					reg.setVouchersActiveName(res.getVouchersRuleInfo().getActiveBaseRule().getActiveName());
				}
			}
			map.put("reg", reg);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 新增注册促销规则
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午12:21:59
	 * @param org
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> addRegisteredRule(RegisteredRuleAddReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//校验请求对象部分字段
		long startTime = PramasUtil.DateFormat(pojo.getStartTime());
		long endTime = PramasUtil.DateFormat(pojo.getEndTime());
		double bankRate = Double.valueOf(pojo.getBankRate());
		double fftRate = Double.valueOf(pojo.getFftRate());
		if(startTime < new Date().getTime()) {
			throw new BossException("活动开始时间不可小于当前系统时间");
		}
		if(startTime > endTime) {
			throw new BossException("活动开始时间不可大于活动结束时间");
		}
		if((bankRate + fftRate) != 100) {//银行补贴+方付通补贴=100
			throw new BossException("银行补贴与方付通补贴合计应为100");
		}
		//封装请求对象
		RegisteredRuleInfoVo req = new RegisteredRuleInfoVo();
		ActiveTagRelationVo req1 = new ActiveTagRelationVo();
		req1.setClientId(pojo.getClientId());
		if(pojo.getTriggerType()){
			req1.setItemType(pojo.getItemType());//活动类型（0-不限定、1–商户、2–门店、3–商品、4–未定义）
			if(pojo.getItemId()!=null){
				req1.setItemId(pojo.getItemId());
			}else{
				req1.setItemId("");
			}
		}else{
			req1.setItemType("4");
			req1.setItemId("");
		}
		
		
		ActiveBaseRuleVo req2 = new ActiveBaseRuleVo();
		req2.setType("6");//活动类型（1-满减活动、2-满送、3-代金券、4-打折促销、6-注册送）
		if(pojo.getTriggerType()){
			req2.setLimitType(pojo.getLimitType());
		}else{
			req2.setLimitType("4");//限制类型（0–不限制、1–限制商户、2–限制门店、3–限制商品、4-未定义）
		}
		req2.setActiveName(pojo.getActiveName());
		req2.setClientId(pojo.getClientId());
		req2.setExpireStartTime(startTime);
		req2.setExpireEndTime(endTime);
		req2.setBankRate(Double.valueOf(bankRate * 1000).intValue());//银行补贴
		req2.setFftRate(Double.valueOf(fftRate * 1000).intValue());//方付通补贴
		req2.setDescription(pojo.getDescription());
		req2.setOperator("" + org.getOperatorId());
		req2.setStatus("3");
		req2.setSettleType("0");//结算方式（0-实时结算、1-延期结算）
		
		RegisteredDetailRuleVo req3 = new RegisteredDetailRuleVo();
		BeanUtils.copyProperties(req3, pojo);// 封装注册详情信息
		/*
		req3.setClientId(pojo.getClientId());
		req3.setAwardType(pojo.getAwardType());
		req3.setIsAwardCre(pojo.getIsAwardCre());
		req3.setTriggerType(pojo.getTriggerType());
		
		//全局限定的日间隔如果为null则赋值0
			if(null!=pojo.getTotalDay()){
					req3.setTotalDay(pojo.getTotalDay());
		    }else{
					req3.setTotalDay(0);
		     }
	   //全局限定总数如果为null则赋值0
		    if(null!=pojo.getTotalCount()){
					req3.setTotalCount(pojo.getTotalCount());
			}else{
					req3.setTotalCount(0);
			}
		    
		req3.setIsTotalDay(pojo.getIsTotalDay());
		//奖励方式
		if("2".equals(pojo.getAwardType())) {//红包
			req3.setVouchersActiveId(pojo.getVouchersActiveId());
		} else if ("4".equals(pojo.getAwardType())) {//联盟积分
			req3.setPerUnionIntegral(pojo.getPerUnionIntegral());
			req3.setTotalUnionIntegral(pojo.getTotalUnionIntegral());
		}
		*/
		req.setActiveTagRelation(req1);
		req.setActiveBaseRule(req2);
		req.setRegisteredDetailRule(req3);
		//调用SERVER端接口
		AddResultVo resp = registeredRuleInfoService.addRegisteredRuleInfo(org, req);
		//封装返回结果对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 修改注册促销规则
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午1:04:54
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> updateRegisteredRule(RegisteredRuleUpdReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//校验请求对象部分字段
		long startTime = PramasUtil.DateFormat(pojo.getStartTime());
		long endTime = PramasUtil.DateFormat(pojo.getEndTime());
		double bankRate = Double.valueOf(pojo.getBankRate());
		double fftRate = Double.valueOf(pojo.getFftRate());
		if(startTime > endTime) {
			throw new BossException("活动开始时间不可大于活动结束时间");
		}
		if((bankRate + fftRate) != 100) {//银行补贴+方付通补贴=100
			throw new BossException("银行补贴与方付通补贴合计应为100");
		}
		//封装请求对象
		RegisteredRuleInfoVo req = new RegisteredRuleInfoVo();
		ActiveTagRelationVo req1 = new ActiveTagRelationVo();
		req1.setClientId(pojo.getClientId());
		if(pojo.getTriggerType()){
			req1.setItemType(pojo.getItemType());//活动类型（0-不限定、1–商户、2–门店、3–商品、4–未定义）
			if(pojo.getItemId()!=null){
				req1.setItemId(pojo.getItemId());
			}else{
				req1.setItemId("");
			}
		}else{
			req1.setItemType("4");
			req1.setItemId("");
		}
		
		ActiveBaseRuleVo req2 = new ActiveBaseRuleVo();
		req2.setType("6");//活动类型（1-满减活动、2-满送、3-代金券、4-打折促销、5-首单、6-注册送）
		if(pojo.getTriggerType()){
			req2.setLimitType(pojo.getLimitType());
		}else{
			req2.setLimitType("4");//限制类型（0–不限制、1–限制商户、2–限制门店、3–限制商品、4-未定义）
		}
		req2.setActiveId(pojo.getActiveId());
		req2.setActiveName(pojo.getActiveName());
		req2.setClientId(pojo.getClientId());
		req2.setExpireStartTime(startTime);
		req2.setExpireEndTime(endTime);
		req2.setBankRate(Double.valueOf(bankRate * 1000).intValue());//银行补贴
		req2.setFftRate(Double.valueOf(fftRate * 1000).intValue());//方付通补贴
		req2.setDescription(pojo.getDescription());
		req2.setOperator("" + org.getOperatorId());
		req2.setStatus("3");
		req2.setSettleType("0");//结算方式（0-实时结算、1-延期结算）
		
		RegisteredDetailRuleVo req3 = new RegisteredDetailRuleVo();
		BeanUtils.copyProperties(req3, pojo);// 封装注册详情信息
		/*
		req3.setClientId(pojo.getClientId());
		req3.setAwardType(pojo.getAwardType());
		req3.setIsAwardCre(pojo.getIsAwardCre());
		req3.setTriggerType(pojo.getTriggerType());

		//全局限定的日间隔如果为null则赋值0
		if(pojo.getTotalDay()!=null){
			req3.setTotalDay(pojo.getTotalDay());
		}else{
			req3.setTotalDay(0);
		}
		//全局限定总数如果为null则赋值0
		if(null!=pojo.getTotalCount()){
			req3.setTotalCount(pojo.getTotalCount());
		}else{
			req3.setTotalCount(0);
		}
		
		req3.setIsTotalDay(pojo.getIsTotalDay());
		
		if("2".equals(pojo.getAwardType())) {//红包
			req3.setVouchersActiveId(pojo.getVouchersActiveId());
		} else if ("4".equals(pojo.getAwardType())) {//联盟积分
			req3.setPerUnionIntegral(pojo.getPerUnionIntegral());
			req3.setTotalUnionIntegral(pojo.getTotalUnionIntegral());
		}
		*/
		req.setActiveTagRelation(req1);
		req.setActiveBaseRule(req2);
		req.setRegisteredDetailRule(req3);
		//调用SERVER端接口
		ResultVo resp = registeredRuleInfoService.updateRegisteredRuleInfo(org, req);
		if(Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		} else {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 禁用注册促销规则
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午1:11:29
	 * @param org
	 * @param clientId
	 * @param activeId
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> disableRegisteredRule(RegisteredRuleDisReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用SERVER端接口
		ResultVo resp = registeredRuleInfoService.disableRegisteredRuleInfo(org, pojo.getClientId(), pojo.getActiveId(), "" + org.getOperatorId());
		if(Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		} else {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 下载注册促销活动规则
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月7日 下午4:14:21
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> downloadRegisteredRule(String clientId, String activeId, String triggerTypeTip) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		RegisteredRuleInfoVo req = new RegisteredRuleInfoVo();
		ActiveBaseRuleVo req2 = new ActiveBaseRuleVo();
		RegisteredDetailRuleVo req3 = new RegisteredDetailRuleVo();
		if("true".equals(triggerTypeTip)){
			req3.setTriggerType(true);
		}else{
			req3.setTriggerType(false);
		}
		req2.setClientId(clientId);
		req2.setActiveId(activeId);
		req.setActiveBaseRule(req2);
		req.setRegisteredDetailRule(req3);
		//调用SERVER端
		try {
			ExportRegisteredRuleInfoInfoRes resp = registeredRuleInfoService.exportRegisteredRuleInfoInfoResUrl(req);
			//返回导出Excel下载路径
			if(StringUtil.isNotBlank(resp.getExportUrl())) {
				map.put("url", resp.getExportUrl());
				map.put("null", "");
			} else {
				throw new BossException("未能成功下载注册促销规则，请稍后重试");
			}
		} catch (TTransportException e) {
			if(e.getCause() instanceof SocketTimeoutException) {
				throw new BossException("注册促销规则下载超时，请稍后重试");
			} else {
				throw e;
			}
		}
		return map;
	}
}
