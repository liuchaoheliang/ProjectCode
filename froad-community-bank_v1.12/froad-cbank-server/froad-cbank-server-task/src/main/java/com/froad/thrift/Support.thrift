namespace java com.froad.thrift.service

/* 引入Common.thrift.thrift文件 */
include "Common.thrift"

/**
 * 短信模块
 */
struct SmsMessageVo {
     1: required i32 smsType;    /** smsType */
     2: optional string mobile;    /** mobile */
     3: optional list<string> values;    /** values */
     4: optional string sendIp;    /** sendIp */
     5: optional string sendUser;    /** sendUser */
     6: required string clientId;    /** clientId */
     7: optional i32 validTime;    /** validTime */
     
    
}

/**
* 获取二维码响应对象
*/
struct SmsMessageResponseVo {

	1:string resultCode,	# 接口结果码
	2:string resultDesc,	# 结果描述
	3:string url,		# 图片url
	4:string token,		# 验证码凭证

}


service SmsMessageService {
	/**
	* 发送短信
	*/
	SmsMessageResponseVo sendSMS(1:SmsMessageVo smsMessageVo);

}