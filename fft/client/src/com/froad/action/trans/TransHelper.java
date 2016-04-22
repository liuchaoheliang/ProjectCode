package com.froad.action.trans;

import com.froad.common.TranCommand;

public class TransHelper {

	
	/**
	  * 方法描述：根据支付方式计算支付渠道
	  * @param: payMethod 支付方式
	  * @return: payChannel 支付渠道
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 16, 2014 6:51:57 PM
	  */
	public static String makePayChannel(String payMethod){
		if(payMethod.equals(TranCommand.CASH)||
				payMethod.equals(TranCommand.POINTS_FFT_CASH)||
				payMethod.equals(TranCommand.POINTS_BANK_CASH)||
				payMethod.equals(TranCommand.POINTS_FFT_CASH_SCOPE)||
				payMethod.equals(TranCommand.POINTS_BANK_CASH_SCOPE)){
			return TranCommand.PAY_CHANNEL_PHONE;
		}
		if(payMethod.equals(TranCommand.POINTS_FFT_ALPAY_SCOPE)||
				payMethod.equals(TranCommand.POINTS_BANK_ALPAY_SCOPE)||
						payMethod.equals(TranCommand.ALPAY)){
			return TranCommand.PAY_CHANNEL_ALIPAY;
		}
		return "";
	}
}
