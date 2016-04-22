package com.froad.fft.dto;

import com.froad.fft.enums.SmsType;

public class SmsDto  extends BaseDto{

	private String[] args;
	private SmsType smsType;
	private String sendUser;
	private String toPhoneNumber;
	private String sendIP;//触发短信发送方所在IP段
	private boolean isCheck = false;//是否在发送该条短信时进行短信频繁程度校验
	
	//------------------------------------
	private boolean flag;//发送结果
	private String msg; //发送结果描述
	//------------------------------------
	
	/**
	 * 发送短信Dto 
	 * @param smsType
	 * @param sendUser 记录发送者帐号，null 标识为系统发送 
	 * @param args 可变参数数组，用于填充短信模版
	 * @param toPhoneNumber
	 */
	public SmsDto(SmsType smsType,String sendUser,String[] args,String toPhoneNumber,String sendIP) {
		this.smsType = smsType;
		this.sendUser = sendUser;
		this.args = args;
		this.toPhoneNumber = toPhoneNumber;
		this.sendIP = sendIP;
	}
	
	/**
	 * 发送短信Dto 
	 * @param smsType
	 * @param sendUser 记录发送者帐号，null 标识为系统发送  isCheck 是否进行短信频繁度检查
	 * @param args 可变参数数组，用于填充短信模版
	 * @param toPhoneNumber
	 */
	public SmsDto(SmsType smsType,String sendUser,String[] args,String toPhoneNumber,String sendIP,boolean isCheck) {
		this.smsType = smsType;
		this.sendUser = sendUser;
		this.args = args;
		this.toPhoneNumber = toPhoneNumber;
		this.sendIP = sendIP;
		this.isCheck = isCheck;
	}
	
	public SmsDto (boolean flag, String msg){
		this.flag = flag;
		this.msg = msg;
	}

	public String[] getArgs() {
		return args;
	}

	public SmsType getSmsType() {
		return smsType;
	}

	public String getToPhoneNumber() {
		return toPhoneNumber;
	}

	public String getSendUser() {
		return sendUser;
	}

	public String getSendIP() {
		return sendIP;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public boolean isFlag() {
		return flag;
	}

	public String getMsg() {
		return msg;
	}
	
	
	
}
