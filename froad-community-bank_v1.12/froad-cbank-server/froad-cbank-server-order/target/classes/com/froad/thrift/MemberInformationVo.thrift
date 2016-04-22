namespace java com.froad.thrift.vo.member

include "MemberSecurityVo.thrift"
include "BankCardVo.thrift"


struct MemberInfoVo{
		/**会员号**/
	01:i64 memberCode;	
		/** 会员类型 1：个人会员，2：企业会员**/
	02:i32 memberType;	
		/** 登录ID**/
	03:string loginID;	
		/** 密码**/
	04:string loginPwd;	
		/** 登录类别**/
	05:i32 loginType;	
		/** 用户姓名**/
	06:string uname;	
		/** 邮箱**/
	07:string email;	
		/** 用户活动状态1正常状态(已激活)，2.未激活，3已冻结**/
	08:i32 status;	
		/** 电话号码**/
	09:string mobile;	
		/** 身份证号**/
	10:string identityKey;	
		/** 性别0：男 1:女性2：保密**/
	11:string sex;	
		/** 年龄**/
	12:i32 age;	
		/** 注册时间**/
	13:i64 createTime;	
		/** 注册时间**/
	14:i64 beginTime;	
		/** 注册时间**/
	15:i64 endTime;	
		/** 修改时间**/
	16:i64 updateTime;	
		/** 创建渠道：FFT 分分通,MALL 商城，FFT_MC 分分通手机客户端，MAll_MC 商城手机客户端**/
	17:string createChannel;	
		/** ADDRESS**/
	18:string address;	
		/** 邮编**/
	19:string zipCode;	
		/** 部门**/
	20:string orgCode;	
		/** 职位**/
	21:string dutyCode;	
		/** 服务级别**/
	22:string serviceLevelCode;	
		/** 最近登录IP地址**/
	23:string lastLoginIP;	
		/** 最近登录时间**/
	24:i64 lastLoginTime;	
		/** 登陆失败限制次数，默认为0**/
	25:i32 loginFailureCount;	
		/** 账号冻结日期**/
	26:i64 lockDate;	
		/** 允许登录的IP**/
	27:string allowIP;	
		/** 用户ID**/
	28:string userID;	
		/** 生日**/
	29:i64 birthday;	
		/** 首次失败时间**/
	30:i64 firstTryTime;	
		/** 连续登陆失败次数，默认为0**/
	31:i32 tryLoginCount;	
		/** 注册IP**/
	32:string registerIP;	
		/** 成功登录标识**/
	33:string loginFlag;	
		/** 个人介绍**/
	34:string introduce;	
		/** 是否绑定邮箱 0：未绑定，1：已绑定**/
	35:bool isBindEmail;	
		/** 是否绑定手机 0：未绑定，1：已绑定**/
	36:bool isBindMobile;	
		/** 是否绑定银行 0：未绑定，1：已绑定**/
	37:bool isBindBank;	
		/** 是否验证绑定的银行 0：未验证，1：已验证**/
	38:bool isValidBank;	
		/** 批次号**/
	39:string batchNo;	
		/** 用户跟踪号**/
	40:string userBankID;	
		/** 银行组号**/
	41:string bankGroupId;	
		/** 银行名称**/
	42:string bankName;	
		/** 银行标识**/
	43:string bankOrgNo;	
		/** 介绍人login_id..**/
	44:string introducer;	
		/** 介绍人MemberCode**/
	45:i64 introducerMemberCode;	
		/** 密码**/
	46:string pwd;	
	
	47:string resultMessage;
	
	48:bool resultSuccess;
	
	49:MemberVIPInfoVo memberVIPInfoVo;
	
}

/**VIP信息*/
struct MemberVIPInfoVo{
	1:string bankOrg;
	2:i32 expiratioinDays;
	3:string vipLevel;
	4:string vipStatus;
	5:string bankLabelName;
}

struct UserEnginePointsVo{
	1:string froadPoints;
	2:string bankPoints;
	3:string froadPointsExchageRate;
	4:string bankPointsExchageRate;
	5:string froadOrgNo;
	6:string resultMessage;
	7:bool resultSuccess;
	8:string bankOrgNO;
}

struct PeAcctVo{
	1:bool flag;//交互后的结果 与业务相关，可以代表 是否存在，是否成功等 正向逻辑true
	2:string msg;//对交互结果的文字描述
	3:list<MemberSecurityVo.UserEngineQuestionVo> questions;
	4:list<BankCardVo.BankCardInfo> memberBanks;
	5:string resultMessage;
	6:bool resultSuccess;
}

struct QueryProtocolVo{
	1:list<MemberSecurityVo.UserEnginePointsRecordVo> pointInfos;
	2:QueryInfoVo queryInfo;
	3:list<TotalPointsInfosVo> totalPointsInfosDtos;
	4:string resultMessage;
	5:bool resultSuccess;
}

struct QueryInfoVo{
	1:string pageNum;
	2:string totalPageNum;
	3:string pageSize;
	4:string totalCount;
}

struct TotalPointsInfosVo{
	1:string protocolType;
	2:string totalPoints;
}

/**
*用户通过卡号查询银行积分返回体
*/
struct PointsAccountVo{
	1:string orgNo;
	2:string userId;
	3:string exchageRate;
	4:string frozenPoints;
	5:string points;
	6:string orgName;
	7:string displayName;
	8:string resultMessage;
	9:bool resultSuccess;
}