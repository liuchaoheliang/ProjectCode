package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.pay.Pay;
import com.froad.client.pay.PayService;



	/**
	 * 类描述：与webservice交互的类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jun 9, 2013 3:05:51 PM 
	 */
public class PayActionSupport {
	
	private static Logger logger = Logger.getLogger(PayActionSupport.class);
	
	private PayService payService;
	
	public void updatePay(Pay pay){
		try {
			payService.updatePay(pay);
		} catch (Exception e) {
			logger.error("更新支付记录出现异常",e);
		}
	}
	
	public PayService getPayService() {
		return payService;
	}
	public void setPayService(PayService payService) {
		this.payService = payService;
	}
	
}
