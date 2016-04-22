
/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"


namespace java com.froad.qrcode

const string NORMAL_RESPONSE = "0000";
const string ABNORMAL_RESPONSE = "9999";

/**
* 获取二维码请求对象
* 
* keyword:预售-00+product_id，团购-01+product_id，面对面-10+product_id+U+merchant_user_id，券-11+ticket_id（客户端将券二维码转换为字符串给用户使用时需要去掉11的前缀）
*/
struct QrCodeRequestVo {
	/** 关键字  */
	1:string keyword,
	/** 银行代码  */
	2:string clientId,
}

/**
* 获取二维码响应对象
*/
struct QrCodeResponseVo {
	/** 二维码图片url  */
	1:string url,
	/** 接口结果码  */
	2:string resultCode,
	/** 结果描述  */
	3:string resultDesc,
}

/**
* 获取二维码对外接口
*/
service QrCodeService extends BizMonitor.BizMonitorService{
	/**
	* 获取二维码url
	*/
	QrCodeResponseVo retrieveQrCode(1:QrCodeRequestVo qrcodeRequestVo);
	/**
	* 生成图片验证码,返回图片验证码url
	*/
	string generateWordImage(1:string content);
}