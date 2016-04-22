package com.froad.logic.impl;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.alibaba.fastjson.JSONObject;
import com.froad.common.beans.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.SmsLogic;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.SmsMessageResponseVo;
import com.froad.thrift.vo.SmsMessageVo;
import com.froad.util.JSonUtil;
import com.froad.util.PropertiesUtil;

public class SmsLogicImpl implements SmsLogic {
	
	/**
	 * thrift.properties
	 */
	private static final String THRIFT_PROPERTIES_FILE = "thrift";
	
	/**
	 * properties host key
	 */
	private static final String PROPERTIES_HOST_KEY = "thrift.sms.host";
	
	/**
	 * properties port key
	 */
	private static final String PROPERTIES_PORT_KEY = "thrift.sms.port";

	@Override
	public ResultBean sendSMSReturnBean(String mobile, int smsType, String clientId, List<String> valueList) {
        
        SmsMessageVo smsMessageVo = new SmsMessageVo();
        smsMessageVo.setSmsType(smsType);
        smsMessageVo.setMobile(mobile);
        smsMessageVo.setValues(valueList);
        smsMessageVo.setClientId(clientId);
        
        return sendSMS(smsMessageVo);
	}
	@Override
	public ResultBean sendSMS(SmsMessageVo smsMessage){
        SmsMessageResponseVo responseVo = null;
        String host = null;
        int port = 0;
        TTransport transport = null;
        TProtocol protocol = null;
        TMultiplexedProtocol multiProtocol = null;
        SmsMessageService.Client client = null;

        try {
            host = PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_HOST_KEY);
            port = Integer.valueOf(PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_PORT_KEY));
            
            // 设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            // 设置传输协议为 TBinaryProtocol 
            protocol = new TBinaryProtocol(transport); 
            multiProtocol = new TMultiplexedProtocol(protocol,SmsMessageService.class.getSimpleName());
            client = new SmsMessageService.Client(multiProtocol);
//            
            LogCvt.info(new StringBuffer("开始发送短信：").append(JSonUtil.toJSonString(smsMessage)).toString());
            
            // 发送短信
            responseVo = client.sendSMS(smsMessage);
            
            LogCvt.info(new StringBuffer(smsMessage.mobile).append(" 短信发送结果：").append(JSonUtil.toJSonString(responseVo)).toString());
            

        } catch (Exception e) {
        	LogCvt.error("连接SMS 短信服务异常 smsMessage: " + JSONObject.toJSONString(smsMessage) + " port: " + port + " host: " + host, e);
        	 return new ResultBean(ResultCode.failed);
        }  finally {
            IOUtils.closeQuietly(transport);
        }
        
        if (responseVo!=null&&responseVo.getResultCode().equals(ResultCode.success.getCode())){
            return new ResultBean(ResultCode.success,responseVo.getResultDesc(),responseVo);
        } else {
            return new ResultBean(ResultCode.failed,responseVo.getResultDesc(),responseVo);
        }
	}
	
    @Override
    public boolean sendSMS(String mobile, int smsType, String clientId, List<String> valueList) {
        return ResultCode.success.getCode().equals(this.sendSMSReturnBean(mobile, smsType, clientId, valueList).getCode());
    }
	

}
