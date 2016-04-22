namespace java com.froad.thrift.vo

/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
/**
 * 获取二维码响应对象
 */
struct SmsMessageResponseVo {

	1:string resultCode,	# 接口结果码
	2:string resultDesc,	# 结果描述
	3:string url,		# 图片url
	4:string token,		# 验证码凭证

}
/**
 * 短信消息
 */
struct SmsMessageVo {
	/** 短信类型(图⽚片验证码为特殊类型-1) */
     1: i32 smsType;   
      /** 手机号码(图片验证码无) */
     2:optional string mobile;   
       /** 可传参数 */
     3:optional list<string> values;  
      /** 发送IP */
     4:optional string sendIp;   
      /** 发送短信方 NULL表示为系统发送 */
     5: optional string sendUser;   
     /** 客户端编号 */
     6: string clientId;    
     /** 有效时间 */
     7:optional i32 validTime;    
     /** 验证码 */
     8:optional string code;  
     
}
/**
 * 短信日志
 */
struct SmsLogVo {
    /** 主键ID */
    1: optional i64 id;    
    /** 客户端编号 */
    2: optional string clientId;    
    /** 创建时间 */
    3: optional i64 createTime;    
    /** 失效时间 */
    4: optional i64 expireTime;    
    /**  短信类型(图片验证码为特殊类型-1) */
    5: optional i32 smsType;    
    /** 手机号码(图片验证码无) */
    6: optional string mobile;    
    /** 发送内容 */
    7: optional string content;    
    /** 是否发送成功 */
    8: optional bool isSuccess;    
    /** 发送短信方 */
    9: optional string sendUser;    
    /** 发送IP */
    10: optional string sendIp;    
    /** 验证码是否已使用 */
    11: optional bool isUsed;    
    /**  验证码 */
    12: optional string code;    
    /** 验证码凭证 */
    13: optional string token;    
    /** 验证码图片地址 */
    14: string url;      
    /** 最小类型 - 查询使用 */
    15: optional i32 smsTypeMin;
    /** 最大类型 - 查询使用 */
    16: optional i32 smsTypeMax;
    /** smsType标识 */
    17: optional string smsTypeMark;
}