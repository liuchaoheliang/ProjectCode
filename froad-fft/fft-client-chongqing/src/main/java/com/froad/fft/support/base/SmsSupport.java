package com.froad.fft.support.base;

import com.froad.fft.dto.SmsDto;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: SmsSupport.java </p>
 *<p> 描述: *-- <b>发送短信的support</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年3月29日 下午2:08:13 </p>
 ********************************************************
 */
public interface SmsSupport {

	public SmsDto sendSms(SmsDto smsDto);
}
