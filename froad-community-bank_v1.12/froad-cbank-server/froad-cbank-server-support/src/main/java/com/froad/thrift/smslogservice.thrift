namespace java com.froad.thrift.service

/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
/* 引入 struct.thrift 文件 */
include "struct.thrift"
include "SMSVO.thrift"

/**
 * 活动 服务
 */
/**
 * 短信日志 服务
 */
service SmsLogService  extends BizMonitor.BizMonitorService{

    /**
     * 增加 SmsLog
     * @param smsLog
     * @return long    主键ID
     */
    i64 addSmsLog(1:Common.OriginVo originVo ,2:SMSVO.SmsLogVo smsLogVo);

    /**
     * 删除 SmsLog
     * @param smsLog
     * @return boolean    
     */
    bool deleteSmsLog(1:Common.OriginVo originVo ,2:SMSVO.SmsLogVo smsLogVo);

    /**
     * 修改 SmsLog
     * @param smsLog
     * @return boolean    
     */
    bool updateSmsLog(1:Common.OriginVo originVo ,2:SMSVO.SmsLogVo smsLogVo);
    
      /**
     * 修改 SmsLog
     * @param smsLog
     * @return boolean    
     */
    bool updateSmsLog1(1:SMSVO.SmsLogVo smsLogVo);

    /**
     * 查询 SmsLog
     * @param smsLog
     * @return List<SmsLogVo>
     */
    list<SMSVO.SmsLogVo> getSmsLog(1:SMSVO.SmsLogVo smsLogVo);

    /**
     * 分页查询 SmsLog
     * @param smsLog
     * @return SmsLogPageVoRes
     */
    struct.SmsLogPageVoRes getSmsLogByPage(1:Common.PageVo page, 2:SMSVO.SmsLogVo smsLogVo);
}

/**
 * 短信 服务
 */
service SmsMessageService  extends BizMonitor.BizMonitorService{
	/**
	* 发送短信
	*/
	SMSVO.SmsMessageResponseVo sendSMS(1:SMSVO.SmsMessageVo smsMessageVo);

	/**
	* 获取并发送手机验证码短信
	*/
	SMSVO.SmsMessageResponseVo sendMobleTokenSMS(1:SMSVO.SmsMessageVo smsMessageVo);
    
	/**
	*生成图片验证码插入短信日志
	*/
	SMSVO.SmsMessageResponseVo createImgage(1:SMSVO.SmsMessageVo smsMessageVo);

	/**
	* 短信Token验证接口
	*/
	Common.ResultVo verifyMobileToken(1:string token,2:string code);

	/**
	* 短信Token验证接口（带手机号验证）
	*/
	Common.ResultVo verifyMobileAndToken(1:string token,2:string code,3:string mobile);

	/**
	* 管理平台短信重发
	*/
	Common.ResultVo reSendSMSForManageBOSS(1:i64 smsLogId);

	
}