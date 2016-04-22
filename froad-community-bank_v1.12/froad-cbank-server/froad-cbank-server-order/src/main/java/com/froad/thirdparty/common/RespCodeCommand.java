package com.froad.thirdparty.common;

/**
 * 响应码
 * 
 * @author FQ
 *
 */
public class RespCodeCommand {

	public static final String SUCCESS = "0000";// 成功
	public static final String INVALID_XML = "9001";// 解析响应报文出错
	public static final String CHECK_SIGNMSG_FAIL = "9002";// 验签失败
	public static final String EXCEPTION = "9999";// 异常
	public static final String HFCZ_SUCCESS = "0";// 话费充值成功
	public static final String HFCZ_FAILED = "1";// 话费充值失败
	public static final String NO_POINT_ACOUNT = "1000";// 没有积分账户的响应码
}
