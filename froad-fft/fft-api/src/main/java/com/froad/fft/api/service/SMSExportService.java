package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SmsDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * *******************************************************
 *<p> 工程: fft-api </p>
 *<p> 类名: SMSExportService.java </p>
 *<p> 描述: *-- <b>用于给服务端</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年3月28日 下午7:45:24 </p>
 ********************************************************
 */
public interface SMSExportService {

	public SmsDto sendSms(ClientAccessType clientAccessType, ClientVersion clientVersion,SmsDto smsDto)throws FroadException;
}
