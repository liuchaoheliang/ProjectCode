namespace java com.froad.thrift.vo.bankcard

/**
*银行卡信息
*/
struct BankCardInfo{
	1:i64 id;
	/** 会员号*/
	2:i64   memberCode;
	/** 银行组号*/ 
	3:string bankGroupId;
	/** 开户行名称*/ 
	4:string bankName;
	/** 开户名*/
	5:string cardHostName;
	/** 卡号*/
	6:string cardNo;
	/** 银行卡种类 1:借记卡，2：贷计卡,3:书香卡*/ 
	7:string cardType; 
	/** 预留手机号*/ 
	8:string mobile;
	/** 协议号*/
	9:string agreementNo;
	/** 证件类型*/
	10:string identifyType;
	/** 身份证号*/  
	11:string identifyNo;
	/** 开户行地址*/
	12:string bankAddress;
	/** 是否签约0：未签约 或认证，1已签约或认证,2已删除*/
	13:i32 validStatus;
	/** 是否默认账户*/
	14:bool isDefault;
	/** 创建时间*/
	15:string createTimeStr;
	/** 修改时间*/
	16:string updateTimeStr;
	/** 登录名*/
	17:string loginID;
	/** 类别*/
	18:string type;
	/** 单笔限额*/
	19:string orderLimit;
	/** 日限额*/
	20:string dayLimit;
	/** 月限额*/
	21:string monthLimit;
	/** 支付宝账户*/
	22:string alipayAccount;
}

/**
* 积分平台请求参数封装
*/
struct PointApiReq{
	/**合作伙伴编号ID*/
	01:string partnerNo;		
	/**积分机构号*/
	02:string orgNo;		
	/**会员标识*/
	03:string accountMarked;		
	/**会员标识类型*/
	04:string accountMarkedType;		
	/**消费积分的一个标识ID 如：交易号，商品ID*/
	05:string objectNo;		
	/**消费积分的具体描述信息*/
	06:string objectDes;		
	/**消费积分的一个东西的类型，如：订单，订单中的某商品,若是订单单，其值为1*/
	07:string objectType;		
	/**具体积分数*/
	08:string points;		
	/**积分帐号*/
	09:string accountId;		
	/**验证码*/
	10:string checkCode;		
	/**备注信息*/
	11:string remark;		
	/**成功消费积分的消费积分单编号*/
	12:string payPointsNo;		
	/**积分类型编号*/
	13:string pointsCateNo;		
	/**积分机构的积分额*/
	14:string orgPoints;		
	/**客户在积分机构的手机号*/
	15:string phone;		
	/**银行实名信息*/
	16:string realName;		
	/**银行编号*/
	17:string bankId;		
	/**银行名称*/
	18:string bankName;		
	/**银行卡号*/
	19:string bankCard;		
	/**证件类型*/
	20:string certType;		
	/**证件号*/
	21:string certNo;		
	/**业务类型*/
	22:string businessType;		
	/**身份证号*/
	23:string identityNo;		
	/**用户预留手机号*/
	24:string mobileNum;		
	/**银行卡卡种*/
	25:string cardType;		
	/**银行协议号*/
	26:string protocolNo;		
	/**充值卡卡密*/
	27:string cardPassword;		
	/**充值卡卡号*/
	28:string cardNo;		
	/**协议类型*/
	29:string protocolType;		
	/**分页容量*/
	30:i32 pageSize;		
	/**查询页码*/
	31:i32 pageNum;		
	/**开始时间*/
	32:string fromTime;		
	/**结束时间*/
	33:string toTime;		
}

/**
* 积分平台请求响应参数封装
*/
struct PointApiRes{

	/** 结果 */
	1:string resultCode;
	/** 结果描述*/
	2:string resultDesc;
	/** 积分消费号
	3:string payPointsNo;
	/** 赠送积分号*/
	4:string presentPointsNo;
	/** 积分提现号 */
	5:string cashPointsNo;
	/** 退积分好 */
	6:string refundPointsNo;
	/** 积分兑换比例 */
	7:string exchangeRate;
}

/**
* 银行卡相关信息查询响应封装
*/
struct BankCardResponse{
	1:string resultCode;
	2:string resultDesc;
	3:list<BankCardInfo> cardList;
}