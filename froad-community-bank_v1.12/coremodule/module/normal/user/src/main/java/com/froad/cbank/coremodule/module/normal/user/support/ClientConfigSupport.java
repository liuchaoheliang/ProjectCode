package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.enums.PaymentChannel;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.cbank.coremodule.module.normal.user.vo.CardCloseVo;
import com.froad.cbank.coremodule.module.normal.user.vo.DisplayPayChannelVo;
import com.froad.cbank.coremodule.module.normal.user.vo.FunctionModuleVo;
import com.froad.cbank.coremodule.module.normal.user.vo.PointsVo;
import com.froad.cbank.coremodule.module.normal.user.vo.SmartCardVo;
import com.froad.thrift.service.BankAccessModuleService;
import com.froad.thrift.service.ClientPaymentChannelService;
import com.froad.thrift.vo.BankAccessModuleListRes;
import com.froad.thrift.vo.BankAccessModuleListVo;
import com.froad.thrift.vo.ClientPaymentChannelVo;

/**
 * @Description: 客户端配置功能
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年10月16日 上午11:41:39
 */
@Service
public class ClientConfigSupport {

	@Resource
	private BankAccessModuleService.Iface bankAccessModuleService;
	
	@Resource
	private ClientPaymentChannelService.Iface clientPaymentChannelService;
	
	/**
	 * 
	 * @Description: 获取客户端功能模块配置
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月16日 上午11:53:56
	 * @param clientId
	 */
	public List<FunctionModuleVo> getClientFunctionModuleConfiguration(String clientId){
		BankAccessModuleListRes res = null;
		FunctionModuleVo functionModuleVo = null;
		List<FunctionModuleVo> functionModuleList = new ArrayList<FunctionModuleVo>();
		try {
			res = bankAccessModuleService.getBankAccessModuleList(clientId);
		} catch (TException e) {
			LogCvt.error("获取客户端功能模块配置发生异常",e);
		}
		
		if(res != null && Constants.RESULT_CODE_SUCCESS.equals(res.getResult().getResultCode())){
			for(BankAccessModuleListVo module : res.getModuleList()){
				functionModuleVo = new FunctionModuleVo();
				functionModuleVo.setType(module.getType());
				functionModuleVo.setModuleName(module.getModuleName());
				functionModuleVo.setModuleAlias(module.getModuleAlias());
				functionModuleVo.setIconUrl(module.getIconUrl());
				functionModuleVo.setSortValue(module.getSortValue());
				
				functionModuleList.add(functionModuleVo);
			}
		}
		
		Collections.sort(functionModuleList);
		return functionModuleList;
	}
	
	
	/**
	 * 获取客户端支付渠道
	 * @Description: TODO
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月16日 下午2:58:31
	 * @param clientId
	 */
	public Map<String,Object> getClientPaymentChannel(String clientId){
		Map<String,Object> result = new HashMap<String, Object>();
		// 支付渠道列表
		DisplayPayChannelVo displayChannel = new DisplayPayChannelVo();
		SmartCardVo smartCard = new SmartCardVo();
		SmartCardVo bankCard = new SmartCardVo();
		PointsVo unionPoint = new PointsVo();
		PointsVo bankPoint = new PointsVo();
		CardCloseVo cardClose = new CardCloseVo();
		
		ClientPaymentChannelVo payChannelVo = new ClientPaymentChannelVo();
		payChannelVo.setClientId(clientId);
		payChannelVo.setIsDelete(false);
		
		List<ClientPaymentChannelVo> payChannelList = null;
		try {
			payChannelList = clientPaymentChannelService.getClientPaymentChannel(payChannelVo);
		} catch (TException e) {
			LogCvt.error("获取支付渠道发生异常",e);
			return result;
		}

		for (ClientPaymentChannelVo payChannel : payChannelList) {

			if (PaymentChannel.foil_card.getCode().equals(payChannel.getType())) {// 贴膜卡
				displayChannel.setDisplaySmartCard(true);
				displayChannel.setSmartCardOrgNo(payChannel.getPaymentOrgNo());
				smartCard.setBankName(payChannel.getName());
				smartCard.setIconUrl(payChannel.getIcoUrl());
				smartCard.setPayChannelName(payChannel.getFullName());
				
			} else if (PaymentChannel.fast_pay.getCode().equals(payChannel.getType())) {// 快捷支付
				displayChannel.setDisplayFastPay(true);
				displayChannel.setFastPayOrgNo(payChannel.getPaymentOrgNo());
				bankCard.setBankName(payChannel.getName());
				bankCard.setIconUrl(payChannel.getIcoUrl());
				bankCard.setPayChannelName(payChannel.getFullName());
				
			} else if (PaymentChannel.froad_point.getCode().equals(payChannel.getType())) {// 方付通积分
				displayChannel.setDisplayUnionPoint(true);
				displayChannel.setUnionPointOrgNo(payChannel.getPaymentOrgNo());
				unionPoint.setBankName(payChannel.getName());
				unionPoint.setPayChannelName(payChannel.getFullName());
				unionPoint.setIconUrl(payChannel.getIcoUrl());
				unionPoint.setPointRate(payChannel.getPointRate());
			} else if (PaymentChannel.bank_point.getCode().equals(payChannel.getType())) {// 银行积分
				displayChannel.setDisplayBankPoint(true);
				displayChannel.setBankPointOrgNo(payChannel.getPaymentOrgNo());
				bankPoint.setBankName(payChannel.getName());
				bankPoint.setPayChannelName(payChannel.getFullName());
				bankPoint.setIconUrl(payChannel.getIcoUrl());
				bankPoint.setPointRate(payChannel.getPointRate());
			}else if(PaymentChannel.cyberbank_pay.getCode().equals(payChannel.getType())){ //卡密支付
				displayChannel.setDisplayCardClose(true);
				displayChannel.setCardCloseOrgNo(payChannel.getPaymentOrgNo());
				cardClose.setPayChannelName(payChannel.getFullName());
				cardClose.setBankName(payChannel.getName());
				cardClose.setIconUrl(payChannel.getIcoUrl());
			}

		}

		result.put("payChannel", displayChannel);
		result.put("smartCard", smartCard);
		result.put("bankCard", bankCard);
		result.put("unionPoint", unionPoint);
		result.put("bankPoint", bankPoint);
		result.put("cardClose", cardClose);
		
		return result;
	}
	
	
	
}
