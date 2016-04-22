package com.froad.cbank.coremodule.normal.boss.support.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.Arith;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.SustainActiveRelationVo;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.VouchersCouponCodeListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.VouchersCouponCodeVo;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.VouchersRuleListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.VouchersRuleListRes;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.VouchersRuleVo;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.VouchersRuleVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.NumberUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.ActiveSustainRelationService;
import com.froad.thrift.service.VouchersRuleInfoService;
import com.froad.thrift.service.VouchersSearchService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;
import com.froad.thrift.vo.active.ActiveSustainRelationVo;
import com.froad.thrift.vo.active.ActiveTagRelationVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.ExportVouchersDetailInfo;
import com.froad.thrift.vo.active.FindPageVouchersRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindPromotionActiveByPageVO;
import com.froad.thrift.vo.active.FindVouchersDetailInfoVO;
import com.froad.thrift.vo.active.FindVouchersRuleInfoVoResultVo;
import com.froad.thrift.vo.active.TemporaryVouchersDetailInfoVO;
import com.froad.thrift.vo.active.VouchersCouponCodeVO;
import com.froad.thrift.vo.active.VouchersDetailRuleVo;
import com.froad.thrift.vo.active.VouchersRuleInfoPageVoRes;
import com.froad.thrift.vo.active.VouchersRuleInfoVo;

/**
 * 红包规则
 * @author yfy
 * @date: 2015年11月24日 下午15:05:01
 * 
 */
@Service
public class VouchersRuleSupport {

	@Resource
	VouchersRuleInfoService.Iface vouchersRuleInfoService;
	@Resource
	ActiveSustainRelationService.Iface activeSustainRelationService;
	//新增跟详情查询红包券码
	@Resource
	VouchersSearchService.Iface vouchersSearchService;
	//findPromotionActiveByPage
	@Resource
	ClientSupport clientSupport;
	
	/**
	 * 红包规则分页列表查询
	 * @param listReq
	 * @return
	 * @throws TException
	 * @throws ParseException 
	 */
	public Map<String, Object> list(VouchersRuleListReq listReq) throws TException, ParseException {
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
		VouchersRuleInfoVo vouchersRuleInfoVo = new VouchersRuleInfoVo();
		vouchersRuleInfoVo.setActiveBaseRule(activeBaseRule);
		
		VouchersRuleListRes vouchersRuleListRes = null;
		List<VouchersRuleListRes> listRes = null;
		Page page = new Page();
		FindPageVouchersRuleInfoVoResultVo resultVo = vouchersRuleInfoService.getActiveRuleInfoByPage(pageVo, vouchersRuleInfoVo);
		VouchersRuleInfoPageVoRes voRes = resultVo.getVouchersRuleInfoPageRes();
		if(voRes != null){
			BeanUtils.copyProperties(page, voRes.getPage());// 封装分页数据信息
			List<VouchersRuleInfoVo> vouchersRuleInfoVoList = voRes.getVouchersRuleInfoList();
			if(vouchersRuleInfoVoList != null && vouchersRuleInfoVoList.size() > 0){
				listRes = new ArrayList<VouchersRuleListRes>();
				List<ClientRes> clientList = clientSupport.getClient();
				for(VouchersRuleInfoVo vo : vouchersRuleInfoVoList){
					vouchersRuleListRes = new VouchersRuleListRes();
					ActiveBaseRuleVo baseVo = vo.getActiveBaseRule();
					BeanUtils.copyProperties(vouchersRuleListRes, baseVo);// 封装数据基本信息
					
					VouchersDetailRuleVo detailVo = vo.getVouchersDetailRule();
					vouchersRuleListRes.setMinMoney(NumberUtil.subZeroAndDot(new BigDecimal(
							detailVo.getMinMoney()).divide(
							new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000保留两位小数
					vouchersRuleListRes.setMaxMoney(NumberUtil.subZeroAndDot(new BigDecimal(
							detailVo.getMaxMoney()).divide(
							new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000保留两位小数
					vouchersRuleListRes.setTotalMoney(NumberUtil.subZeroAndDot(new BigDecimal(
							detailVo.getTotalMoney()).divide(
							new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000保留两位小数
					if(clientList != null && clientList.size() > 0 
							&& StringUtil.isNotBlank(baseVo.getClientId())){
						//获取客户端名称
						for(ClientRes clientRes : clientList){
							if(clientRes.getClientId().equals(baseVo.getClientId())){
								vouchersRuleListRes.setClientName(clientRes.getClientName());
								break;
							}
						}
					}
					listRes.add(vouchersRuleListRes);
				}
			}
		}
		map.put("page", page);
		map.put("list", listRes);
		return map;
	}
	
	/**
	 * 红包规则新增
	 * @param originVo
	 * @param addReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 * @throws ParseException 
	 */
	public Map<String, Object> add(OriginVo originVo,VouchersRuleVoReq addReq) throws TException,BossException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		//基础规则类
		ActiveBaseRuleVo activeBaseRule = new ActiveBaseRuleVo();
		BeanUtils.copyProperties(activeBaseRule, addReq);// 封装数据基本信息
		//红包类型 固定 3
		activeBaseRule.setType("3");
		activeBaseRule.setCreateTime(new Date().getTime());
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
		VouchersDetailRuleVo vouchersDetailRule = new VouchersDetailRuleVo();
		BeanUtils.copyProperties(vouchersDetailRule, addReq);// 封装数据基本信息
		//设置全局个数
		if(StringUtil.isNotBlank(addReq.getTotCount())){
			vouchersDetailRule.setTotalCount(addReq.getTotCount());
		}else{
			vouchersDetailRule.setTotalCount(0);
		}
		if(StringUtil.isNotBlank(addReq.getMinMoney())){
			vouchersDetailRule.setMinMoney(new BigDecimal(addReq.getMinMoney()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}
		if(StringUtil.isNotBlank(addReq.getMaxMoney())){//有红包最大金额则设置
			vouchersDetailRule.setMaxMoney(new BigDecimal(addReq.getMaxMoney()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}else{//没有红包最大金额则赋值最小金额
			vouchersDetailRule.setMaxMoney(new BigDecimal(addReq.getMinMoney()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}
		if(StringUtil.isNotBlank(addReq.getTotalMoney())){
			vouchersDetailRule.setTotalMoney(new BigDecimal(addReq.getTotalMoney()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}
		//支持促销活动
		List<SustainActiveRelationVo> sustainActiveList = addReq.getSustainActiveList();
		List<ActiveSustainRelationVo> activeSustainRelationList = new ArrayList<ActiveSustainRelationVo>();
		if(sustainActiveList != null && sustainActiveList.size() > 0){
			for(SustainActiveRelationVo vo : sustainActiveList){
				ActiveSustainRelationVo activeSustainRelationVo = new ActiveSustainRelationVo();
				
				BeanUtils.copyProperties(activeSustainRelationVo, vo);// 封装数据基本信息
				activeSustainRelationVo.setClientId(addReq.getClientId());
				activeSustainRelationVo.setSustainActiveName(vo.getActiveName());
				activeSustainRelationList.add(activeSustainRelationVo);
			}
		}
		vouchersDetailRule.setActiveSustainRelationList(activeSustainRelationList);
		//红包券码列表
		List<VouchersCouponCodeVo> codeList = addReq.getCodeList();
		List<VouchersCouponCodeVO> scodeList = new ArrayList<VouchersCouponCodeVO>();
		VouchersCouponCodeVO scode=null;
		if(codeList != null && codeList.size() > 0){
			for (VouchersCouponCodeVo wcode: codeList) {
				scode=new VouchersCouponCodeVO();
				BeanUtils.copyProperties(scode, wcode);
				scodeList.add(scode);
			}
		}
		vouchersDetailRule.setVouchersCouponCodelist(scodeList);
		//标签关联类
		ActiveTagRelationVo activeTagRelation = new ActiveTagRelationVo();
		activeTagRelation.setClientId(addReq.getClientId());
		activeTagRelation.setItemType(addReq.getItemType());//标签类型
		if(addReq.getItemType().equals("0")){
			activeTagRelation.setItemId("");//不限制
		}else{
			activeTagRelation.setItemId(addReq.getItemId());//标签ID
		}
		VouchersRuleInfoVo addRes = new VouchersRuleInfoVo();
		
		addRes.setActiveBaseRule(activeBaseRule);//基础规则类
		addRes.setVouchersDetailRule(vouchersDetailRule);//详情规则类
		addRes.setActiveTagRelation(activeTagRelation);//标签关联类
		//调用server端api营销新增接口
		AddResultVo resultVo = vouchersRuleInfoService.addVouchersRuleInfo(originVo, addRes);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultVo().getResultCode())
				&& resultVo.getId() != 0){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", resultVo.getResultVo().getResultDesc());
		}else{
			throw new BossException(resultVo.getResultVo().getResultCode(), resultVo.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 红包规则修改
	 * @param originVo
	 * @param addReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 * @throws ParseException 
	 */
	public Map<String, Object> update(OriginVo originVo,VouchersRuleVoReq updateReq) throws TException,BossException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		//基础规则类
		ActiveBaseRuleVo activeBaseRule = new ActiveBaseRuleVo();
		BeanUtils.copyProperties(activeBaseRule, updateReq);// 封装数据基本信息	
		//红包类型 固定 3
		activeBaseRule.setType("3");
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
		VouchersDetailRuleVo vouchersDetailRule = new VouchersDetailRuleVo();
		BeanUtils.copyProperties(vouchersDetailRule, updateReq);// 封装数据基本信息
		//设置全局使用总数
		if(StringUtil.isNotBlank(updateReq.getTotCount())){
		vouchersDetailRule.setTotalCount(updateReq.getTotCount());
		}else{
		vouchersDetailRule.setTotalCount(0);
		}
		if(StringUtil.isNotBlank(updateReq.getMinMoney())){
			vouchersDetailRule.setMinMoney(new BigDecimal(updateReq.getMinMoney()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}
		if(StringUtil.isNotBlank(updateReq.getMaxMoney())){//有红包最大金额则设置
			vouchersDetailRule.setMaxMoney(new BigDecimal(updateReq.getMaxMoney()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}else{//没有红包最大金额则赋值最小金额
			vouchersDetailRule.setMaxMoney(new BigDecimal(updateReq.getMinMoney()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}
		if(StringUtil.isNotBlank(updateReq.getTotalMoney())){
			vouchersDetailRule.setTotalMoney(new BigDecimal(updateReq.getTotalMoney()).multiply(
					new BigDecimal(1000)).intValue());//乘以1000
		}
		//支持促销活动
		List<SustainActiveRelationVo> sustainActiveList = updateReq.getSustainActiveList();
		List<ActiveSustainRelationVo> activeSustainRelationList = new ArrayList<ActiveSustainRelationVo>();
		if(sustainActiveList != null && sustainActiveList.size() > 0){
			for(SustainActiveRelationVo vo : sustainActiveList){
				ActiveSustainRelationVo activeSustainRelationVo = new ActiveSustainRelationVo();
				BeanUtils.copyProperties(activeSustainRelationVo, vo);// 封装数据基本信息
				activeSustainRelationVo.setClientId(updateReq.getClientId());
				activeSustainRelationVo.setSustainActiveName(vo.getActiveName());
				activeSustainRelationList.add(activeSustainRelationVo);
			}
		}
		vouchersDetailRule.setActiveSustainRelationList(activeSustainRelationList);
		//标签关联类
		ActiveTagRelationVo activeTagRelation = new ActiveTagRelationVo();
		activeTagRelation.setActiveId(updateReq.getActiveId());
		activeTagRelation.setClientId(updateReq.getClientId());
		activeTagRelation.setItemType(updateReq.getItemType());//标签类型
		if(updateReq.getItemType().equals("0")){
			activeTagRelation.setItemId("");//不限制
		}else{
			activeTagRelation.setItemId(updateReq.getItemId());//标签ID
		}
		VouchersRuleInfoVo updateRes = new VouchersRuleInfoVo();
		updateRes.setActiveBaseRule(activeBaseRule);//基础规则类
		updateRes.setVouchersDetailRule(vouchersDetailRule);//详情规则类
		updateRes.setActiveTagRelation(activeTagRelation);//标签关联类
		//调用server端api营销修改接口
		ResultVo resultVo = vouchersRuleInfoService.updateVouchersRuleInfo(originVo, updateRes);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "修改红包规则信息成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 红包规则详情
	 * @param clientId
	 * @param activeId
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> detail(String clientId,String activeId) throws TException,BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		//调用server端api营销详情接口
		FindVouchersRuleInfoVoResultVo findVouchersRuleInfoVoResultVo = vouchersRuleInfoService.getActiveRuleInfoById(clientId, activeId);
		ResultVo resultVo = findVouchersRuleInfoVoResultVo.getResultVo();
		VouchersRuleInfoVo activeRuleInfoVo = findVouchersRuleInfoVoResultVo.getVouchersRuleInfo();
		
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			VouchersRuleVo vouchersRuleVo=new VouchersRuleVo(); 
			//基础规则
			ActiveBaseRuleVo activeBaseRuleVo = activeRuleInfoVo.getActiveBaseRule();
			if(activeBaseRuleVo != null){
				BeanUtils.copyProperties(vouchersRuleVo, activeBaseRuleVo);// 封装数据基本信息
				vouchersRuleVo.setMerchantRate(NumberUtil.subZeroAndDot(new BigDecimal(
						activeBaseRuleVo.getMerchantRate()).divide(
						new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000
				vouchersRuleVo.setBankRate(NumberUtil.subZeroAndDot(new BigDecimal(
						activeBaseRuleVo.getBankRate()).divide(
						new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000
				vouchersRuleVo.setFftRate(NumberUtil.subZeroAndDot(new BigDecimal(
						activeBaseRuleVo.getFftRate()).divide(
						new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP).toString()));//除以1000
				
			
				//获取客户端名称
				List<ClientRes> clientList = clientSupport.getClient();
				if(clientList != null && clientList.size() > 0 
						&& StringUtil.isNotBlank(activeBaseRuleVo.getClientId())){
					//获取客户端名称
					for(ClientRes clientRes : clientList){
						if(clientRes.getClientId().equals(activeBaseRuleVo.getClientId())){
							vouchersRuleVo.setClientName(clientRes.getClientName());
							break;
						}
					}
				}
			}
			//详情规则
			VouchersDetailRuleVo vouchersDetailRuleVo = activeRuleInfoVo.getVouchersDetailRule();
			if(vouchersDetailRuleVo != null){
				vouchersRuleVo.setDetailId(vouchersDetailRuleVo.getId());
				BeanUtils.copyProperties(vouchersRuleVo, vouchersDetailRuleVo);// 封装数据基本信息
				double minMoney=Arith.div(vouchersDetailRuleVo.getMinMoney(), 1000d);
				double maxMoney=Arith.div(vouchersDetailRuleVo.getMaxMoney(), 1000d);
				double totalMoney=Arith.div(vouchersDetailRuleVo.getTotalMoney(), 1000d);
				//最小金额除以1000
				vouchersRuleVo.setMinMoney(String.valueOf(minMoney));
				//最大金额除以1000
				vouchersRuleVo.setMaxMoney(String.valueOf(maxMoney));
				//总金额除以1000
				vouchersRuleVo.setTotalMoney(String.valueOf(totalMoney));
				//设置全局使用总数
				vouchersRuleVo.setTotCount(vouchersDetailRuleVo.getTotalCount());
			//支持促销活动
				List<SustainActiveRelationVo> sustainActiveList = new ArrayList<SustainActiveRelationVo>();
				List<ActiveSustainRelationVo> activeSustainRelationList = vouchersDetailRuleVo.getActiveSustainRelationList();
				if(activeSustainRelationList != null && activeSustainRelationList.size() > 0){
					for(ActiveSustainRelationVo vo : activeSustainRelationList){
						SustainActiveRelationVo sustainActiveRelationVo = new SustainActiveRelationVo();
						BeanUtils.copyProperties(sustainActiveRelationVo, vo);// 封装数据基本信息
						sustainActiveRelationVo.setActiveName(vo.getSustainActiveName());
						sustainActiveRelationVo.setActiveId(vo.getSustainActiveId());
						sustainActiveList.add(sustainActiveRelationVo);
					}
				}
				vouchersRuleVo.setSustainActiveList(sustainActiveList);
			}
			//标签关联
			ActiveTagRelationVo activeTagRelationVo = activeRuleInfoVo.getActiveTagRelation();
			if(activeTagRelationVo != null){
				vouchersRuleVo.setTagId(activeTagRelationVo.getId());
				vouchersRuleVo.setItemId(activeTagRelationVo.getItemId());
				vouchersRuleVo.setItemType(activeTagRelationVo.getItemType());
			}
			map.put("vouchersRuleVo", vouchersRuleVo);
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 红包规则禁用
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
		ResultVo resultVo = vouchersRuleInfoService.disableVouchersRuleInfo(originVo, clientId, activeId, operator);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "禁用该红包规则信息成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 促销活动分页列表查询
	 * @param listReq
	 * @return
	 * @throws TException
	 * @throws ParseException 
	 */
	public Map<String, Object> activeList(VouchersRuleListReq listReq) throws TException, ParseException {
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
		if(StringUtil.isNotBlank(listReq.getActiveType())){
			activeBaseRule.setType(listReq.getActiveType());	
		}
		if(StringUtil.isNotBlank(listReq.getActiveId())){
			activeBaseRule.setActiveId(listReq.getActiveId());
		}
		activeBaseRule.setStatus("3");
		VouchersRuleInfoVo vouchersRuleInfoVo = new VouchersRuleInfoVo();
		vouchersRuleInfoVo.setActiveBaseRule(activeBaseRule);
		
		SustainActiveRelationVo v = null;
		List<SustainActiveRelationVo> listRes = new ArrayList<SustainActiveRelationVo>();
		Page page = new Page();
		
		FindPromotionActiveByPageVO res=activeSustainRelationService.findPromotionActiveByPage(pageVo, activeBaseRule);
		//获取客户端名称
		List<ClientRes> clientList = clientSupport.getClient(); 
		if(res!=null){
	    	   List<ActiveBaseRuleVo> abList= res.getActiveBaseRuleVoList();
	    	   if(abList!=null&&abList.size()>0){
	    	   for (ActiveBaseRuleVo a : abList) {
				v=new SustainActiveRelationVo();
				v.setActiveId(a.getActiveId());
				v.setId(a.getId());
				v.setBeginTime(a.getExpireStartTime());
				v.setClientId(a.getClientId());
				v.setDescription(a.getDescription());
				v.setEndTime(a.getExpireEndTime());
				v.setSustainActiveType(a.getType());
				v.setActiveName(a.getActiveName());
				if(clientList != null && clientList.size() > 0 
						&& StringUtil.isNotBlank(a.getClientId())){
					//获取客户端名称
					for(ClientRes clientRes : clientList){
						if(clientRes.getClientId().equals(a.getClientId())){
							v.setClientName(clientRes.getClientName());
							break;
						}
					}
				}
				listRes.add(v);
			}
	    }
	    	   BeanUtils.copyProperties(page, res.getPage());
	       }
		map.put("page", page);
		map.put("sustainActiveList", listRes);
		map.put("redPackageType", "000");
		return map;
	}
	
	
	/**
	 * 新增或修改页面--红包券码分页列表查询
	 * @param listReq
	 * @return
	 * @throws TException
	 * @throws ParseException 
	 */
	public Map<String, Object> codeList(VouchersCouponCodeListReq listReq) throws TException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		//分页条件
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(listReq.getPageNumber());
		pageVo.setPageSize(listReq.getPageSize());
		String clientId="",activeId="";
		//列表查询条件
		if(StringUtil.isNotBlank(listReq.getActiveId())){
			activeId=listReq.getActiveId();
		}
		if(StringUtil.isNotBlank(listReq.getClientId())){
			clientId=listReq.getClientId();
		}
		
		List<VouchersCouponCodeVo> listRes = new ArrayList<VouchersCouponCodeVo>();	
		List<VouchersCouponCodeVO> codeList =null;
		//后端Vo
		VouchersCouponCodeVo wcode=null;
		
		Page page = new Page();
		FindVouchersDetailInfoVO res= vouchersSearchService.findVouchersDetailTemporaryInfo(pageVo,clientId, activeId );
	
		if(res!=null&&res.getVouchersCouponCodelist().size()>0){
			codeList=res.getVouchersCouponCodelist();
			for (VouchersCouponCodeVO scode : codeList) {
				LogCvt.info("回显红包券码："+JSON.toJSONString(scode));
				wcode=new VouchersCouponCodeVo();
				BeanUtils.copyProperties(wcode, scode);
					//金额除以1000
					Double money=Arith.div(wcode.getVouchersMoney(), 1000d);
					//余额除以1000
					Double resmoney=Arith.div(wcode.getVouchersResMoney(), 1000d);
					
					wcode.setVouchersMoney(money);
					wcode.setVouchersResMoney(resmoney);
				
				listRes.add(wcode);
			}			
			BeanUtils.copyProperties(page, res.getPage());
		}

		LogCvt.info("回显红包券码列表："+JSON.toJSONString(listRes));
		map.put("page", page);
		map.put("codeList", listRes);
		return map;
	}
	
	/**
	 * 详情页---红包券码明细分页列表查询
	 * @param listReq
	 * @return
	 * @throws TException
	 * @throws ParseException 
	 */
	public Map<String, Object> dlCodeList(VouchersCouponCodeListReq listReq) throws TException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		//分页条件
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(listReq.getPageNumber());
		pageVo.setPageSize(listReq.getPageSize());
		String clientId="",activeId="";
		//列表查询条件
		if(StringUtil.isNotBlank(listReq.getActiveId())){
			activeId=listReq.getActiveId();
		}
		if(StringUtil.isNotBlank(listReq.getClientId())){
			clientId=listReq.getClientId();
		}
		
		List<VouchersCouponCodeVo> listRes = new ArrayList<VouchersCouponCodeVo>();	
		List<VouchersCouponCodeVO> codeList =null;
		//后端Vo
		VouchersCouponCodeVo wcode=null;
		
		Page page = new Page();
		FindVouchersDetailInfoVO res= vouchersSearchService.findVouchersDetailInfo(pageVo,clientId, activeId );
	
		if(res!=null&&res.getVouchersCouponCodelistSize()>0){
			codeList=res.getVouchersCouponCodelist();
				for (VouchersCouponCodeVO scode : codeList) {
					wcode=new VouchersCouponCodeVo();
					BeanUtils.copyProperties(wcode, scode);
						//金额除以1000
						Double money=Arith.div(wcode.getVouchersMoney(), 1000d);
						//余额除以1000
						Double resmoney=Arith.div(wcode.getVouchersResMoney(), 1000d);
						
						wcode.setVouchersMoney(money);
						wcode.setVouchersResMoney(resmoney);
					
					listRes.add(wcode);
				}
			BeanUtils.copyProperties(page, res.getPage());
		}
		map.put("page", page);
		map.put("codeList", listRes);
		return map;
	}
	
	public Map<String,Object> uploadCode(OriginVo originVo,MultipartFile file) throws IOException, BossException, TException{
		Map<String, Object> map = new HashMap<String, Object>();
		Set<VouchersCouponCodeVO> codeSet=new HashSet<VouchersCouponCodeVO>();
		List<String> repeatList=new ArrayList<String>();
		//封装给后台
		List<VouchersCouponCodeVO> vouchersCouponCodelist=new ArrayList<VouchersCouponCodeVO>();
		TemporaryVouchersDetailInfoVO req = new TemporaryVouchersDetailInfoVO();
		
		VouchersCouponCodeVO vo=null;
		//解析txt文件
		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
		String tmp = null;
		while((tmp = br.readLine()) != null) {
			if(StringUtils.isNotBlank(tmp)) {
				vo=new VouchersCouponCodeVO();
				String code=tmp.trim();
				if(code.length()>20){
					code =code.substring(0, 20);
				}
				vo.setVouchersIds(code);
				boolean isRepeat=codeSet.add(vo);
				//判断是否重复
				if(!isRepeat){
					repeatList.add(code);
				}
			}
		}
		br.close();
		
		//如果有重复则报错
		if(repeatList.size()>0){
			throw new BossException("9999", "上传券码失败，因有重复券码，失败券码："+repeatList.toString());
		}
		//set集合转成list
		vouchersCouponCodelist=new ArrayList<VouchersCouponCodeVO>(codeSet);
		
		LogCvt.info("上传红包券码个数："+vouchersCouponCodelist.size());
		//一次上传个数
		int size=5000;
		//共上传
		int length=vouchersCouponCodelist.size()/size;
		int isallSuccess=0;
		long activeId =0;
		if(length>1){
			//分批传给后台
			List<VouchersCouponCodeVO> codeList=null;
			for(int i=0;i<=length;i++){
				//截取开始下标			
				int beginIndex=i*size;
				//截取结束下标
				int endIndex=(i+1)*size;
				codeList=new ArrayList<VouchersCouponCodeVO>();
				//如果是最后一页，则只截取到最后一页
					if(i==length){
						LogCvt.info("最后一页：新增红包券码："+beginIndex+"---总条数："+vouchersCouponCodelist.size());
						codeList=vouchersCouponCodelist.subList(beginIndex, vouchersCouponCodelist.size());
					}else{
						//从解析出来的列表里读5000条
						LogCvt.info("新增红包券码："+beginIndex+"---"+endIndex);
						codeList=vouchersCouponCodelist.subList(beginIndex, endIndex);
					}
				//设置红包券码
				req.setVouchersCouponCodelist(codeList);
				if(activeId!=0){
					req.setActiveId(activeId);
				}
				//调后台接口
				AddResultVo resultVo = vouchersRuleInfoService.addTemporaryVouchersRuleInfo(originVo, req);
				
				ResultVo rs = resultVo.getResultVo();	
				LogCvt.info("响应码："+rs.getResultCode());		
				if(Constants.RESULT_SUCCESS.equals(rs.getResultCode())){				
					activeId=resultVo.getId();
				}else{
				//	isallSuccess++;
				}
			}	
		}else{
			//下面是文件未超过5000的情况
			req.setVouchersCouponCodelist(vouchersCouponCodelist);
			AddResultVo resultVo = vouchersRuleInfoService.addTemporaryVouchersRuleInfo(originVo, req);			
			ResultVo rs = resultVo.getResultVo();			
			if(Constants.RESULT_SUCCESS.equals(rs.getResultCode())){				
				activeId=resultVo.getId();
			}else{
			//	isallSuccess++;
			}
		}
		LogCvt.info("上传红包券码个数："+vouchersCouponCodelist.size());
		LogCvt.info("上传红包券码是否成功（0成功，>0 部分成功）："+isallSuccess);		
		if(isallSuccess==0){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "上传关联文件成功");
			map.put("temporaryActiveId", activeId);
			map.put("repeatList", repeatList);
		}else{
			throw new BossException("9999", "web 后端--上传关联文件失败！");
		}
		return map;
		
	}
	/**
	 * 红包规则下载交易记录
	 * @param originVo
	 * @param clientId
	 * @param id
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> download(String clientId,String activeId) throws TException,BossException {
		Map<String, Object> map = new HashMap<String, Object>();
	    ExportVouchersDetailInfo exportRes = vouchersRuleInfoService.exportVouchersDetailInfo(clientId,activeId);
	   
	    if(null!=exportRes){
	    	LogCvt.info("下载红包明细导出URL:"+exportRes.getExportUrl());
			map.put("url", exportRes.getExportUrl());
			map.put("code", Constants.RESULT_SUCCESS);
	    }else{
	    	throw new BossException("下载红包使用明细失败！");
	    }
		return map;
	}

}
