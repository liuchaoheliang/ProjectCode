package com.froad.logic;

import java.util.List;

import com.froad.common.beans.ResultBean;
import com.froad.thrift.vo.SmsMessageVo;

public interface SmsLogic {
	/**
	 * 调用短信模块发送短信
	 * 
	 * @param mobile
	 * @param smsType
	 * @param clientId
	 * @param valueList
	 * @return
	 */
	public boolean sendSMS(String mobile, int smsType, String clientId, List<String> valueList);

    /**
    * 调用短信模块发送短信，返回响应VO
    * <p>Function: sendSMSReturnBean</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月18日 下午1:14:20
    * @version 1.0
    * @param mobile
    * @param smsType
    * @param clientId
    * @param valueList
    * @return
    * ResultBean
    */
    public ResultBean sendSMSReturnBean(String mobile, int smsType, String clientId, List<String> valueList);

    /**
    * 发送短信
    * <p>Function: sendSMS</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月18日 下午2:26:15
    * @version 1.0
    * @param smsMessage
    * @return
    * ResultBean
    */
    public ResultBean sendSMS(SmsMessageVo smsMessage);
}
