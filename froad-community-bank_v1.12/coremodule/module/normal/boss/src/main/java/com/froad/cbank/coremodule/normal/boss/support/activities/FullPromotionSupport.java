package com.froad.cbank.coremodule.normal.boss.support.activities;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.enums.ActiveType;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.FullPromotionListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.FullPromotionListRes;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.FullPromotionVo;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.FullPromotionVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.NumberUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.ActiveRuleInfoService;
import com.froad.thrift.service.VouchersRuleInfoService;
import com.froad.thrift.service.VouchersSearchService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;
import com.froad.thrift.vo.active.ActiveDetailRuleVo;
import com.froad.thrift.vo.active.ActiveRuleInfoPageVoRes;
import com.froad.thrift.vo.active.ActiveRuleInfoVo;
import com.froad.thrift.vo.active.ActiveTagRelationVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.ExportActiveOrderInfoRes;
import com.froad.thrift.vo.active.FindActiveRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindPageActiveRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindVouchersCountVO;
import com.froad.thrift.vo.active.FindVouchersRuleInfoVoResultVo;

/**
 *  满额促销
 * @author yfy
 * @date: 2015年11月6日 上午9:24:16
 * 
 */
@Service
public class FullPromotionSupport {

	@Resource
	ActiveRuleInfoService.Iface activeRuleInfoService;
	@Resource
	ClientSupport clientSupport;
	@Resource
	private VouchersRuleInfoService.Iface vouchersRuleInfoService;
	@Resource
	VouchersSearchService.Iface vouchersSearchService;
	/**
	 * 满额促销分页列表查询
	 * @param listReq
	 * @return
	 * @throws TException
	 * @throws ParseException 
	 */
	public Map<String, Object> list(FullPromotionListReq listReq) throws TException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		//分页条件
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(listReq.getPageNumber());
		pageVo.setPageSize(listReq.getPageSize());
		ActiveBaseRuleVo activeBaseRule = new ActiveBaseRuleVo();
		//列表查询条件
		if(StringUtil.isNotBlank(listReq.getActiveName())){
			activeBaseRule.setActiveName(listReq.getActiveName());
		}
		if(StringUtil.isNotBlank(listReq.getClientId())){
			activeBaseRule.setClientId(listReq.getClientId());
		}
		if(StringUtil.isNotBlank(listReq.getStatus())){
			activeBaseRule.setStatus(listReq.getStatus());
		}
		if(StringUtil.isNotBlank(listReq.getStartTime())){
			activeBaseRule.setExpireStartTime(PramasUtil.DateFormat(listReq.getStartTime()));
		}
		if(StringUtil.isNotBlank(listReq.getEndTime())){
			activeBaseRule.setExpireEndTime(PramasUtil.DateFormatEnd(listReq.getEndTime()));
		}
		ActiveRuleInfoVo activeRuleInfoVo = new ActiveRuleInfoVo();
		activeRuleInfoVo.setActiveBaseRule(activeBaseRule);
		
		FullPromotionListRes fullPromotionListRes = null;
		List<FullPromotionListRes> listRes = null;
		Page page = new Page();
		FindPageActiveRuleInfoVoResultVo resultVo = activeRuleInfoService.getActiveRuleInfoByPage(pageVo, activeRuleInfoVo);
		ActiveRuleInfoPageVoRes voRes = resultVo.getActiveRuleInfoPageVoRes();
		if(voRes != null){
			BeanUtils.copyProperties(page, voRes.getPage());// 封装分页数据信息
			List<ActiveRuleInfoVo> activeRuleInfoVoList = voRes.getActiveRuleInfoVoList();
			if(activeRuleInfoVoList != null && activeRuleInfoVoList.size() > 0){
				listRes = new ArrayList<FullPromotionListRes>();
				List<ClientRes> clientList = clientSupport.getClient();
				for(ActiveRuleInfoVo vo : activeRuleInfoVoList){
					fullPromotionListRes = new FullPromotionListRes();
					ActiveBaseRuleVo baseVo = vo.getActiveBaseRule();
					BeanUtils.copyProperties(fullPromotionListRes, baseVo);// 封装数据基本信息
					ActiveDetailRuleVo detailVo = vo.getActiveDetailRule();
					fullPromotionListRes.setMinLimit(NumberUtil.subZeroAndDot(new BigDecimal(
							detailVo.getMinLimit()).divide(
							new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000保留两位小数
					fullPromotionListRes.setPayMethod(detailVo.getPayMethod());//支付方式（预留）
					fullPromotionListRes.setIsPrePay(detailVo.isPrePay);
					if(clientList != null && clientList.size() > 0 
							&& StringUtil.isNotBlank(baseVo.getClientId())){
						//获取客户端名称
						for(ClientRes clientRes : clientList){
							if(clientRes.getClientId().equals(baseVo.getClientId())){
								fullPromotionListRes.setClientName(clientRes.getClientName());
								break;
							}
						}
					}
					listRes.add(fullPromotionListRes);
				}
			}
		}
		map.put("page", page);
		map.put("list", listRes);
		return map;
	}
	
	/**
	 * 满额促销新增
	 * @param originVo
	 * @param addReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 * @throws ParseException 
	 */
	public Map<String, Object> add(OriginVo originVo,FullPromotionVoReq addReq) throws TException,BossException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		//基础规则类
		ActiveBaseRuleVo activeBaseRule = new ActiveBaseRuleVo();
		BeanUtils.copyProperties(activeBaseRule, addReq);// 封装数据基本信息
		if(addReq.getIsPrePay()){
			activeBaseRule.setType(ActiveType.fullCut.getCode());
		}else{
			activeBaseRule.setType(ActiveType.fullGive.getCode());
		}
		activeBaseRule.setExpireStartTime(PramasUtil.DateFormat(addReq.getExpireStartTime()));
		activeBaseRule.setExpireEndTime(PramasUtil.DateFormat(addReq.getExpireEndTime()));
		if(StringUtil.isNotBlank(addReq.getMerchantRate())){
			activeBaseRule.setMerchantRate(new BigDecimal(addReq.getMerchantRate()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000	
		}
		if(StringUtil.isNotBlank(addReq.getBankRate())){
			activeBaseRule.setBankRate(new BigDecimal(addReq.getBankRate()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}
		if(StringUtil.isNotBlank(addReq.getFftRate())){
			activeBaseRule.setFftRate(new BigDecimal(addReq.getFftRate()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}
		//详情规则类
		ActiveDetailRuleVo activeDetailRule = new ActiveDetailRuleVo();
		BeanUtils.copyProperties(activeDetailRule, addReq);// 封装数据基本信息
		if(StringUtil.isNotBlank(addReq.getMinLimit())){
			activeDetailRule.setMinLimit(new BigDecimal(addReq.getMinLimit()).multiply(
					new BigDecimal(1000)).longValue());//乘以1000
		}
		if(StringUtil.isNotBlank(addReq.getRetMoney())){
			activeDetailRule.setRetMoney(new BigDecimal(addReq.getRetMoney()).multiply(
					new BigDecimal(1000)).longValue());//乘以1000
		}
		if(StringUtil.isNotBlank(addReq.getMaxMoney())){
			activeDetailRule.setMaxMoney(new BigDecimal(addReq.getMaxMoney()).multiply(
					new BigDecimal(1000)).longValue());//乘以1000
		}
		//标签关联类
		ActiveTagRelationVo activeTagRelation = new ActiveTagRelationVo();
		activeTagRelation.setClientId(addReq.getClientId());
		activeTagRelation.setItemType(addReq.getItemType());//标签类型
		if(addReq.getItemType().equals("0")){
			activeTagRelation.setItemId("");//不限制
		}else{
			activeTagRelation.setItemId(addReq.getItemId());//标签ID
		}
		ActiveRuleInfoVo addRes = new ActiveRuleInfoVo();
		addRes.setActiveBaseRule(activeBaseRule);//基础规则类
		addRes.setActiveDetailRule(activeDetailRule);//详情规则类
		addRes.setActiveTagRelation(activeTagRelation);//标签关联类
		//调用server端api营销新增接口
		AddResultVo resultVo = activeRuleInfoService.addActiveRuleInfo(originVo, addRes);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultVo().getResultCode())
				&& resultVo.getId() != 0){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "新增满额促销信息成功");
		}else{
			throw new BossException(resultVo.getResultVo().getResultCode(), resultVo.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 满额促销修改
	 * @param originVo
	 * @param addReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 * @throws ParseException 
	 */
	public Map<String, Object> update(OriginVo originVo,FullPromotionVoReq updateReq) throws TException,BossException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		//基础规则类
		ActiveBaseRuleVo activeBaseRule = new ActiveBaseRuleVo();
		BeanUtils.copyProperties(activeBaseRule, updateReq);// 封装数据基本信息
		if(updateReq.getIsPrePay()){//支付前
			activeBaseRule.setType(ActiveType.fullCut.getCode());//满减
		}else{
			activeBaseRule.setType(ActiveType.fullGive.getCode());//满送
		}
		activeBaseRule.setExpireStartTime(PramasUtil.DateFormat(updateReq.getExpireStartTime()));
		activeBaseRule.setExpireEndTime(PramasUtil.DateFormat(updateReq.getExpireEndTime()));
		if(StringUtil.isNotBlank(updateReq.getMerchantRate())){
			activeBaseRule.setMerchantRate(new BigDecimal(updateReq.getMerchantRate()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000	
		}
		if(StringUtil.isNotBlank(updateReq.getBankRate())){
			activeBaseRule.setBankRate(new BigDecimal(updateReq.getBankRate()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}
		if(StringUtil.isNotBlank(updateReq.getFftRate())){
			activeBaseRule.setFftRate(new BigDecimal(updateReq.getFftRate()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}
		//详情规则类
		ActiveDetailRuleVo activeDetailRule = new ActiveDetailRuleVo();
		activeDetailRule.setId(updateReq.getDetailId());
		BeanUtils.copyProperties(activeDetailRule, updateReq);// 封装数据基本信息
		if(StringUtil.isNotBlank(updateReq.getMinLimit())){
			activeDetailRule.setMinLimit(new BigDecimal(updateReq.getMinLimit()).multiply(
					new BigDecimal(1000)).longValue());//乘以1000
		}
		if(StringUtil.isNotBlank(updateReq.getRetMoney())){
			activeDetailRule.setRetMoney(new BigDecimal(updateReq.getRetMoney()).multiply(
					new BigDecimal(1000)).longValue());//乘以1000
		}
		if(StringUtil.isNotBlank(updateReq.getMaxMoney())){
			activeDetailRule.setMaxMoney(new BigDecimal(updateReq.getMaxMoney()).multiply(
					new BigDecimal(1000)).longValue());//乘以1000
		}
		//标签关联类
		ActiveTagRelationVo activeTagRelation = new ActiveTagRelationVo();
		activeTagRelation.setId(updateReq.getTagId());
		BeanUtils.copyProperties(activeTagRelation, updateReq);// 封装数据基本信息
		if(updateReq.getItemType().equals("0")){
			activeTagRelation.setItemId("");//不限制
		}else{
			activeTagRelation.setItemId(updateReq.getItemId());//标签ID
		}
		
		ActiveRuleInfoVo updateRes = new ActiveRuleInfoVo();
		updateRes.setActiveBaseRule(activeBaseRule);
		updateRes.setActiveDetailRule(activeDetailRule);
		updateRes.setActiveTagRelation(activeTagRelation);
		//调用server端api营销修改接口
		ResultVo resultVo = activeRuleInfoService.updateActiveRuleInfo(originVo, updateRes);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "修改满额促销信息成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 满额促销详情
	 * @param clientId
	 * @param activeId
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> detail(String clientId,String activeId) throws TException,BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		//调用server端api营销详情接口
		FindActiveRuleInfoVoResultVo findActiveRuleInfoVoResultVo=activeRuleInfoService.getActiveRuleInfoById(clientId, activeId);
		ResultVo resultVo = findActiveRuleInfoVoResultVo.getResultVo();
		ActiveRuleInfoVo activeRuleInfoVo = findActiveRuleInfoVoResultVo.getActiveRuleInfoVo();
		
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			FullPromotionVo fullPromotionVo=new FullPromotionVo(); 
			//基础规则
			ActiveBaseRuleVo activeBaseRuleVo = activeRuleInfoVo.getActiveBaseRule();
			if(activeBaseRuleVo != null){
				BeanUtils.copyProperties(fullPromotionVo, activeBaseRuleVo);// 封装数据基本信息
				fullPromotionVo.setMerchantRate(NumberUtil.subZeroAndDot(new BigDecimal(
						activeBaseRuleVo.getMerchantRate()).divide(
						new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000
				fullPromotionVo.setBankRate(NumberUtil.subZeroAndDot(new BigDecimal(
						activeBaseRuleVo.getBankRate()).divide(
						new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000
				fullPromotionVo.setFftRate(NumberUtil.subZeroAndDot(new BigDecimal(
						activeBaseRuleVo.getFftRate()).divide(
						new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000
				//获取客户端名称
				List<ClientRes> clientList = clientSupport.getClient();
				if(clientList != null && clientList.size() > 0 
						&& StringUtil.isNotBlank(activeBaseRuleVo.getClientId())){
					//获取客户端名称
					for(ClientRes clientRes : clientList){
						if(clientRes.getClientId().equals(activeBaseRuleVo.getClientId())){
							fullPromotionVo.setClientName(clientRes.getClientName());
							break;
						}
					}
				}
				
			}
			//详情规则
			ActiveDetailRuleVo activeDetailRuleVo = activeRuleInfoVo.getActiveDetailRule();
			if(activeDetailRuleVo != null){
				fullPromotionVo.setDetailId(activeDetailRuleVo.getId());
				BeanUtils.copyProperties(fullPromotionVo, activeDetailRuleVo);// 封装数据基本信息
				fullPromotionVo.setMinLimit(NumberUtil.subZeroAndDot(new BigDecimal(
						activeDetailRuleVo.getMinLimit()).divide(
						new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000
				fullPromotionVo.setRetMoney(NumberUtil.subZeroAndDot(new BigDecimal(
						activeDetailRuleVo.getRetMoney()).divide(
						new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000
				fullPromotionVo.setMaxMoney(NumberUtil.subZeroAndDot(new BigDecimal(
						activeDetailRuleVo.getMaxMoney()).divide(
						new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000
				//获取所奖励的红包规则名称
				if(StringUtil.isNotBlank(fullPromotionVo.getPrePayActiveId())) {
					FindVouchersRuleInfoVoResultVo res = vouchersRuleInfoService.getActiveRuleInfoById(fullPromotionVo.getClientId(), fullPromotionVo.getPrePayActiveId());
					if(res.getVouchersRuleInfo() != null) {
						fullPromotionVo.setPrePayActiveName(res.getVouchersRuleInfo().getActiveBaseRule().getActiveName());
					}
				}
			}
			//标签关联
			ActiveTagRelationVo activeTagRelationVo = activeRuleInfoVo.getActiveTagRelation();
			if(activeTagRelationVo != null){
				fullPromotionVo.setTagId(activeTagRelationVo.getId());
				fullPromotionVo.setItemId(activeTagRelationVo.getItemId());
				fullPromotionVo.setItemType(activeTagRelationVo.getItemType());
			}
			map.put("fullPromotionVo", fullPromotionVo);
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 满额促销禁用
	 * @param originVo
	 * @param clientId 客户端
	 * @param activeId 活动ID
	 * @param operator 操作人
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> disable(OriginVo originVo,String clientId,String activeId,String operator) throws TException,BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		//调用server端api营销禁用接口
		ResultVo resultVo = activeRuleInfoService.disableActiveRuleInfo(originVo, clientId, activeId, operator);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "禁用该满额促销信息成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 满额促销下载交易记录
	 * @param originVo
	 * @param clientId
	 * @param id
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> download(OriginVo originVo,String clientId,String activeId,Boolean isPrePay) throws TException,BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		ActiveBaseRuleVo activeBaseRule = new ActiveBaseRuleVo();
		activeBaseRule.setActiveId(activeId);//活动ID
		activeBaseRule.setClientId(clientId);
		
		ActiveDetailRuleVo activeDetailRule = new ActiveDetailRuleVo();
		activeDetailRule.setIsPrePay(isPrePay);
		
		ActiveRuleInfoVo activeRuleInfoVo = new ActiveRuleInfoVo();
		activeRuleInfoVo.setActiveBaseRule(activeBaseRule);
		activeRuleInfoVo.setActiveDetailRule(activeDetailRule);
		
		ExportActiveOrderInfoRes exportRes = activeRuleInfoService.exportActiveOrderInfoUrl(activeRuleInfoVo);
		map.put("url", exportRes.getExportUrl());
		map.put("code", Constants.RESULT_SUCCESS);
		return map;
	}
	
	
	/**
	 * 满赠查询红包个数
	 * @param clientId
	 * @param activeId
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> findVouchersCount(String clientId,String activeId) throws TException,BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		//调用server端api获取红包个数
		FindVouchersCountVO findVouchersCountVO=vouchersSearchService.FindVouchersCountInfo(clientId, activeId);
		ResultVo resultVo = findVouchersCountVO.getResultVo();
		int vouchersCount = findVouchersCountVO.getVouchersCount();
		
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("vouchersCount", vouchersCount);
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		
		return map;
	}
}
