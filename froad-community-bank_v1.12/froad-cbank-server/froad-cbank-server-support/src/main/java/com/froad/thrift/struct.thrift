namespace java com.froad.thrift.vo

include "SMSVO.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "SMSVO.thrift"

/**
 * 新增结果
 */
struct AddResultVo {
	 /**  ResultVo结果*/
	1:Common.ResultVo resultVo;	
	 /** 主键id */
	2:i64 id;       
}

/**
 * 热词
 */
struct HotWordVo {
    1:string clientId;
    /** 热词 */
	2:string hotWord;	
	/** 地区id */
	3:i64 areaId;     
	/** 搜索总次数 */
	4:i32 searchCount;    
	/** 搜索有结果次数 */
	5:i32 searchCountResul;  
	/** 类型0 全部1 商品2商户 */
	6:i32 categoryType;
	/** 0 全部1手工插入2系统统计*/
	7:i32 type;
}

/**
 * 热词分页
 */
struct HotWordPageRes {
	 /** 热词list*/
	1:list<HotWordVo> hotList;  
	 /** page */
	2:Common.PageVo page;    
}

/**
 * 活动
 */
struct ActivitiesVo {
	/** id */
    1: optional i64 id;    
    /** 客户端ID */
    2: optional string clientId;  
    /** 创建时间 */  
    3: optional i64 createTime;   
    /** 活动名称 */
    4: optional string activitiesName;
    /** 赠送积分数量 */    
    5: optional i32 points;    
    /** 活动开始时间 */
    6: optional i64 beginTime;  
    /** 活动结束时间 */  
    7: optional i64 endTime;    
    /** 状态 0-未生效 1-已生效 2-已作废 3-已删除 */
    8: optional string status;
    /** 绑定商品数 */    
    9: optional i32 count;    
    
}

/**
 * 活动分页
 */
struct ActivitiesPageVoRes {
	1:Common.PageVo page;
	2:list<ActivitiesVo> activitiesVoList;
}

/**
 * AdImageVo
 */
struct AdImageVo {
    /** 图片标题 */
    1:string title;    
    /** 图片原图地址 */
    2:string source;    
    /** 图片大图地址 */
    3:string large;    
    /** 图片中图地址 */
    4:string medium;    
    /** 图片小图地址 */
    5:string thumbnail;   
}

/**
 * 广告位
 * 注 - 已经摒弃使用;新的广告位是 AdLocationVo
 */
struct AdPositionVo {
    1: optional i64 id;    /** 主键ID 广告位ID */
    2: optional string clientId;    /** 客户端ID */
    3: optional string name;    /** 名称 */
    4: optional i32 width;    /** 宽度 */
    5: optional i32 height;    /** 高度 */
    6: optional string terminalType;   /** 终端类型 */
    7: optional string positionStyle;    /** 形状 */
    8: optional string positionPage;    /** 页面标识 */
    9: optional i32 positionPoint;    /** 页面原点 */
    10: optional i32 sizeLimit;    /** 大小限制 - kb */
    11: optional string description;    /** 描述 */
    12: optional bool isEnable;    /** 是否有效 */
}

/**
 * 广告位分页
 */
struct AdPositionPageVoRes {
	1:Common.PageVo page;
	2:list<AdPositionVo> adPositionVoList;
}

/**
 * 广告
 * 注 - 已经摒弃使用;新的广告是 AdvertisingVo
 */
struct AdVo {
    1: optional i64 id;    /** 主键ID */
    2: optional string clientId;    /** 客户端ID */
    3: optional string title;    /** 标题 */
    4: optional i32 type;    /** 类型 0-文本 1-图片 2-flash */
    5: optional i64 beginTime;    /** 开始时间 */
    6: optional i64 endTime;    /** 结束时间 */
    7: optional string content;    /** 内容 */
    8: optional string path;    /** 路径 */
    9: optional string link;    /** 链接地址 */
    10: optional bool isEnabled;    /** 是否启用 */
    11: optional bool isBlankTarge;    /** 是否在新窗打开 */
    12: optional i32 clickCount;    /** 点击次数 */
    13: optional i64 adPositionId;    /** 广告位ID */

}

/**
 * 广告分页
 */
struct AdPageVoRes {
	1:Common.PageVo page;
	2:list<AdVo> adVoList;
}

/**
 * 协议
 */
struct AgreementVo {
    1: optional i64 id;    /** 主键ID */
    2: optional string clientId;    /** 客户端ID */
    3: optional string content;    /** 详细内容 */
}

/**
 * 协议分页
 */
struct AgreementPageVoRes {
	1:Common.PageVo page;
	2:list<AgreementVo> agreementVoList;
}

/**
 * 地区
 */
struct AreaVo {
	
    1: optional i64 id;    /** 自增主键ID（地区ID）*/
    /** 客户端id */
    2: optional string clientId;
    /** 名称 */
    3: optional string name;    
    /** 简拼 */
    4: optional string vagueLetter;    
    /** 树路径 */
    5: optional string treePath;    
    /** 树路径名称 */
    6: optional string treePathName;  
    /** 上级地区ID */
    7: optional i64 parentId;    
    /** 是否启用 */
    8: optional bool isEnable;    
    /** 地图code;供前端交互地区时候使用 */
    9: optional string areaCode;    
}

/**
 * 地区分页
 */
struct AreaPageVoRes {
	1:Common.PageVo page;
	2:list<AreaVo> areaVoList;
}

/**
 * 代金券
 */
struct CashVo {
    1: optional i64 id;    /** 主键ID */
    2: optional string cashCn;    /** 代金券批次号 */
    3: optional string cashCode;    /** 代金券ID */
    4: optional i32 money;    /** 面值 */
    5: optional i32 usedMoney;    /** 已用金额 */
    6: optional bool isEnabled;    /** 是否启用 */
    7: optional bool isUsed;    /** 是否已经使用 */
    8: optional string orderId;    /** 以下四项在使用后有 */
    9: optional string subOrderId;    /** subOrderId */
    10: optional string merchantId;    /** merchantId */
    11: optional string productId;    /** productId */
}

/**
 * 代金券分页
 */
struct CashPageVoRes {
	1:Common.PageVo page;
	2:list<CashVo> cashVoList;
}

/**
 * 物流公司
 */
struct DeliveryCorpVo {
    1: optional i64 id;    /** 物流公司ID */
    2: optional string corpCode;    /** 快递公司编码 */
    3: optional string name;    /** 名称 */
    4: optional string url;    /** 网址 */
    5: optional i32 orderValue;    /** 排序 */
    6: optional bool isEnable;    /** 是否启用 */
}

/**
 * 物流分页
 */
struct DeliveryCorpPageVoRes {
	1:Common.PageVo page;
	2:list<DeliveryCorpVo> deliveryCorpVoList;
}

const string NORMAL_RESPONSE = "0000";
const string ABNORMAL_RESPONSE = "9999";

/**
* 获取二维码请求对象
* 
* keyword:预售-00+product_id，团购-01+product_id，面对面-10+product_id，券-11+ticket_id（客户端将券二维码转换为字符串给用户使用时需要去掉11的前缀）
*/
struct QrCodeRequestVo {
	1:string keyword;	# 关键字
	2:string clientId;		# 银行代码
}

/**
* 获取二维码响应对象
*/
struct QrCodeResponseVo {
	1:string url;			# 二维码图片url
	2:string resultCode;	# 接口结果码
	3:string resultDesc;	# 结果描述
}

/**
 * 短信模板
 */
struct SmsContentVo {
    /** 主键ID */
    1: optional i64 id;    
    /** 客户端编号 */
    2: optional string clientId;    
    /** 短信类型 */
    3: optional i32 smsType;    
    /** 模版内容 */
    4: optional string content;    
    /** 短信渠道 */
    5: optional string channel;    
    /** 后缀 */
    6: optional string msgSuffix;    
    /** 有效时间 */
    7: optional i32 validTime;    
    /** 是否启用 */
    8: optional bool isEnable;    
}

/**
 * 短信模板分页
 */
struct SmsContentPageVoRes {
	1:Common.PageVo page;
	2:list<SmsContentVo> smsContentVoList;
}



/**
 * 短信日志分页
 */
struct SmsLogPageVoRes {
	1:Common.PageVo page;
	2:list<SMSVO.SmsLogVo> smsLogVoList;}

/**
 * Boss敏感词
 */

struct BossSenseWordsVo {
    /** 主键id */
    1: optional i64 id;    
    /** 敏感词 */
    2: optional string word;    
    /** 是否启用 */
    3: optional bool isEnable;    
}


/**
 * Boss敏感词分页
 */
struct BossSenseWordsPageVoRes {
	1: optional Common.PageVo page;
	2: optional list<BossSenseWordsVo> bossSenseWordsVoList;
}

/**
 * Boss机构
 */
struct BossOrgVo {
    /** 主键id */
    1: optional i64 id;    
    /** 上级机构编码 */
    2: optional string parentOrgCode;    
    /** 机构编码 */
    3: optional string orgCode;    
    /** 机构名称 */
    4: optional string orgName;    
    /** 角色Id*/
    5: optional i64 roleId;    
    /** 是否启用 */
    6: optional bool isEnable    
}

/**
 * Boss机构分页
 */
struct BossOrgPageVoRes {
	1: optional Common.PageVo page;
	2: optional list<BossOrgVo> bossOrgVoList;
}

/**
 * Boss用户Vo
 */
struct BossUserVo {
    /** 主键id */
    1: optional i64 id;    
    /** 登陆名 */
    2: optional string username;    
    /** 登录密码 */
    3: optional string password;    
    /** 角色ID */
    4: optional i64 roleId;    
    /** 手机号 */
    5: optional string mobile;    
    /** 邮箱 */
    6: optional string email;    
    /** 操作员姓名 */
    7: optional string name;    
    /** 部门 */
    8: optional string department;    
    /** 职位 */
    9: optional string position;    
    /** 是否可用 0-不可用，1-可用 */
    10: optional bool isEnable;    
    /** 密码是否重置 1-是，0-否*/
    11: optional bool isReset    
    /** 备注 */
    12: optional string remark   
    /** 角色名称 */ 
    13: optional string roleName
}

/**
 * Boss用户分页
 */
struct BossUserPageVoRes {
	1: optional Common.PageVo page;
	2: optional list<BossUserVo> bossUserVoList;
}

/**
 * Boss用户登录
 */
struct BossUserLoginVoRes {
	
	/** 公共响应信息 */
	1:Common.ResultVo result; 
	/** 返回的用户信息 */
	2:BossUserVo bossUser; 
	/** 登录失败次数 */
	3:i32 loginFailureCount; 
	/** token值 */
	4:string token; 
}

/**
 * Boss用户校验
 */
struct BossUserCheckVoRes {
	
	/** 公共响应信息 */
	1:Common.ResultVo result; 
	/** 返回的用户信息 */
	2:BossUserVo bossUser; 
}

/**
 * Boss用户机构关联
 */ 
struct BossUserOrgVo {
    /** id */
    1: optional i64 id;    
    /** userId */
    2: optional i64 userId;    
    /** orgCode */
    3: optional string orgCode    
}
 
/**
 * Boss用户机构关联分页
 */ 
struct BossUserOrgPageVoRes {
	1: optional Common.PageVo page;
	2: optional list<BossUserOrgVo> bossUserOrgVoList;
}

/**
 * Boss角色
 */
struct BossRoleVo {
    1: optional i64 id;    /** id */
    2: optional string roleName;    /** roleName */
    3: optional bool isEnable;    /** isEnable */
    4: optional string remark    /** remark */
}

/**
 * Boss角色分页
 */ 
struct BossRolePageVoRes {
	1: optional Common.PageVo page;
	2: optional list<BossRoleVo> bossRoleVoList;
}

/**
 * 启动页详情
 * TerminalStartVo
 */
struct TerminalStartVo {
	/** 客户端Id */
    1: optional string clientId;   
    /** app类型  1-个人 2-商户 */
    2: optional string appType;    
    /** 终端类型 1-pc 2-andriod 3-ios */
    3: optional string terminalType; 	
    /** 图片标识Id */ 
    4: optional string imageId; 	
    /** 图片路径url  */ 
    5: optional string imagePath;   	
}

/**
 * 广告位详情
 * AdLocationVo
 */
struct AdLocationVo {
	/** 主键ID 广告位ID */
    1: optional i64 id;  
    /** 名称 */    
    2: optional string name;  
    /** 终端类型 */
    3: optional string terminalType;
    /** 页面标识 */ 
    4: optional string positionPage; 
    /** 大小限制-单位为K */   
    5: optional i32 sizeLimit; 
    /** 宽度 */  
    6: optional i32 width;    
    /** 高度 */
    7: optional i32 height;       
    /** 第一附加参数类型 1-地区 2-商户类型 3-商品类型 */
    8: optional string paramOneType;   
    /** 第二附加参数类型 1-地区 2-商户类型 3-商品类型 */
    9: optional string paramTwoType;
    /** 第三附加参数类型 1-地区 2-商户类型 3-商品类型 */
    10: optional string paramThreeType;  
    /** 描述 */
    11: optional string description;
    /** 启用状态 0-启用 1-禁用 2-新增审核中 3-编辑审核中 4-禁用审核中 */    
    12: optional string enableStatus;    
}

/**
 * 广告位分页
 */
struct AdLocationPageVoRes {
	/** 分页对象 */
	1:Common.PageVo page;
	/** 结果集 */
	2:list<AdLocationVo> adLocationVoList;
}

/** 查询 AdLocation 列表 结果 */
struct FindAllAdLocationResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 结果集 */
	2:list<AdLocationVo> adLocationVoList;
}

/** 查询 AdLocation 分页 结果 */
struct FindPageAdLocationResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 分页结果 */
	2:AdLocationPageVoRes adLocationPageVoRes;
}

/** 查询 AdLocation 单个 结果 */
struct FindAdLocationResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 结果 */
	2:AdLocationVo adLocationVo;
}

/**
 * 广告详情
 * AdvertisingVo
 */
struct AdvertisingVo {
    /** 主键ID */
    1: optional i64 id;    
    /** 客户端ID */
    2: optional string clientId; 
    /** 标题 */   
    3: optional string title;
    /** 广告位ID */
    4: optional i64 adLocationId;
    /** 类型 0-文本 1-图片 2-flash */    
    5: optional string type;    
    /** 序号 */    
    6: optional i32 orderSn;
    /** 开始时间 */
    7: optional i64 beginTime;  
    /** 结束时间 */  
    8: optional i64 endTime;  
    /** 第一参数-内容 */
    9: optional string paramOneValue;
    /** 第二参数-内容 */
    10: optional string paramTwoValue;
    /** 第三参数-内容 */
    11: optional string paramThreeValue;  
    /** 内容 */
    12: optional string content; 
    /** 链接地址 */
    13: optional string link;
    /** 路径 */   
    14: optional string path; 
    /** 是否在新窗打开 */
    15: optional bool isBlankTarge;    
    /** 启用状态 0-启用 1-禁用 2-新增审核中 3-编辑审核中 4-禁用审核中 */
    16: optional string enableStatus;    
    /** 点击次数 */   
    17: optional i32 clickCount;    
    /** 描述 */
    18: optional string description;
    /** 广告位名称 */
    19: optional string adLocationName;
    /** 第一附加参数类型 1-地区 2-商户类型 3-商品类型 */
    20: optional string paramOneType;   
    /** 第二附加参数类型 1-地区 2-商户类型 3-商品类型 */
    21: optional string paramTwoType;
    /** 第三附加参数类型 1-地区 2-商户类型 3-商品类型 */
    22: optional string paramThreeType;  
    /**终端位置*/
    23: optional string terminalType;
    /** 页面标识 */ 
    24: optional string positionPage; 
}

/**
 * 广告分页
 */
struct AdvertisingPageVoRes {
	1:Common.PageVo page;
	2:list<AdvertisingVo> AdvertisingVoList;
}

/** 查询 Advertising 列表 结果 */
struct FindAllAdvertisingResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 结果集 */
	2:list<AdvertisingVo> advertisingVoList;
}

/** 查询 Advertising 分页 结果 */
struct FindPageAdvertisingResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 分页结果 */
	2:AdvertisingPageVoRes advertisingPageVoRes;
}

/** 查询 Advertising 单个 结果 */
struct FindAdvertisingResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 结果 */
	2:AdvertisingVo advertisingVo;
}

/** 页面优化查询 Advertising 列表的参数 */
struct FindAllAdvertisingParamVo{
	/** 终端类型 */
    1: optional string terminalType;
    /** 页面标识 */ 
    2: optional string positionPage; 
	/** 客户端ID */
    3: optional string clientId;
	/** 第一参数-内容 */
    4: optional string paramOneValue;
    /** 第二参数-内容 */
    5: optional string paramTwoValue;
    /** 第三参数-内容 */
    6: optional string paramThreeValue; 
    /** 广告位ID */
    7: optional i64 adLocationId;
}

/**
 * 物流订单
 */
struct DeliveryWayBillVo {
      /** id */
    1: optional string id;
     /** 创建时间 */
    2: optional i64 createTime;	
     /** 更新时间 */
    3: optional i64 updateTime;     
     /**子订单 */
    4: optional string subOrderId;    
    /** 物流公司编码 */
    5: optional string shippingCorpCode;  
    /** 物流公司*/
    6: optional string shippingCorp;
    /** 物流单号*/
    7: optional string shippingId;
     /** 运单状态*/
    8: optional string status;
     /**消息*/
    9: optional string message;
      /**运输时间*/
    10: optional i64 shippingTime;
      /**确认收货时间*/
    11: optional i64 arriveTime;
     /**确认收货状态  0-未收货  1-人工确认 2-系统确认*/
    12: optional i32 shippingState;
}