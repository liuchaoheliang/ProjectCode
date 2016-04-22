package com.froad.fft.thirdparty.request.sms;

import com.froad.fft.dto.SmsDto;
import com.froad.fft.thirdparty.dto.request.sms.SmsBean;

public interface SMSMessageFunc {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>调用sms平台发送短信</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月23日 下午2:51:42 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public SmsDto sendSMSMessage(SmsBean smsDto);
}
