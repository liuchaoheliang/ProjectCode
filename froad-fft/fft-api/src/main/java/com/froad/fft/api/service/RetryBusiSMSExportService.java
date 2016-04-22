package com.froad.fft.api.service;

import com.froad.fft.bean.Result;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * *******************************************************
 *<p> 工程: fft-api </p>
 *<p> 类名: RetryBusiSMS.java </p>
 *<p> 描述: *-- <b>重发业务类型的短信</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年4月11日 下午4:10:52 </p>
 ********************************************************
 */
public interface RetryBusiSMSExportService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>重发预售短信</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月11日 下午4:15:19 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Result retryPresell(ClientAccessType clientAccessType, ClientVersion clientVersion,Long transId,String ip);
}
