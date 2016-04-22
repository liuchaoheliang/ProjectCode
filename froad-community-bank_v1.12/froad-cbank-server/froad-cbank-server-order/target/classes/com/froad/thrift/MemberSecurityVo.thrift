namespace java com.froad.thrift.vo.member

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "BankCardVo.thrift"

struct UserEnginQuestionResultVo{
	1:bool result;
	2:string resultDesc;
	3:list<UserEngineQuestionVo> data;
}

struct UserEngineQuestionVo{
	1:i64 ID;
	2:i64 questionID;
	3:string questionName;
	4:string questionDemo;
	5:string type;
	6:i64 memberCode; 
	7:string answer; 
	8:i64 state;
	9:i64 createTime;
}

struct UserEngineVo{
	1:i64 memberCode;
	2:string loginID;
	3:string loginPwd;
	4:string email;
	5:i64 status;
	6:string registerIP;
	7:string mobile;
	8:string createChannel;
	9:string froadPoints;
	10:string froadExchageRate;
	11:string bankPoints;
	12:string bankExchageRate;
	13:string uname;
	14:string introducer;
	15:string bankDesc;
	16:string mobileEncrypt;
	17:list<ReceiverVo> receiver;//收货地址
	18:bool result;
	19:i64 msgCode;
	20:string errorMsg;
	21:string demo;
	22:MemberOauthVo memberOauthDto; //联合登录信息
	23:i64 createTime;//会员注册时间
}

struct ReceiverVo{
	1:i64 id;
	2:i64 createTime;
	3:i64 updateTime;
	4:string consignee;//收货人
	5:string address;//地址
	6:string zipCode;//邮编
	7:string phone;// 电话
	8:bool isDefault;//是否默认
	9:i64 areaId;//地区 
	10:i64 memberCode;//账户服务ID
	11:string dataState;//数据状态
	12:string fullAddress;//完整地址
	13:string reveiverAddress;
	14:i64 type;//类型(1-提货人，2-收货人)
}

struct MemberOauthVo{
	1:i64 id;
	2:i64 memberCode; // 会员号
	3:string openId; // 第三方登录名
	4:string nickName; // 昵称
	5:string oauthType; // 第三方类别
	6:i64 status; // 状态
}

struct UserEnginePageVoRes {
	1:Common.PageVo page;
	2:list<UserEnginePointsRecordVo> pointsRecordList;
}

struct UserEnginePointsRecordVo{
	1:string objectNo;
	2:string accountNo;
	3:string partnerNo;
	4:string orgPoints;
	5:string points;
	6:i64 time;
	7:string protocolNo;
	8:string protocolType;
	9:string businessType;
	10:string businessInstructions;
	11:string remark;
	12:string displayName;// 积分显示名称
	13:string orgNo;// 积分机构编号
	14:string orderId;
}