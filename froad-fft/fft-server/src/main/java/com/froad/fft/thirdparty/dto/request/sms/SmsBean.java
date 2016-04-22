package com.froad.fft.thirdparty.dto.request.sms;

import com.froad.fft.persistent.common.enums.SmsType;
import com.froad.fft.persistent.entity.SmsLog;

public class SmsBean {

	private SmsLog smsLog;
	
	private String[] valueArgs;
	
	private boolean isCheck;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>限定构造参数</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月23日 下午2:49:37 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public SmsBean(SmsType smsType,Long clientId,String mobile,String[] valueArgs,String sendUser,String sendIp,boolean isCheck){
		this.smsLog = new SmsLog(smsType, clientId, mobile, false, sendUser,sendIp);
		this.valueArgs = valueArgs;
		this.isCheck = isCheck;
	}

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>设置短信最终发送结果</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月23日 下午2:49:37 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public void setSuccess(Boolean isSuccess) {
		this.smsLog.setIsSuccess(isSuccess);
	}
	
	public SmsLog getSmsLog() {
		return smsLog;
	}
	public void setContent(String content){
		this.smsLog.setContent(content);
	}

	public String[] getValueArgs() {
		return valueArgs;
	}

	public boolean isCheck() {
		return isCheck;
	}
	
	
	
}
