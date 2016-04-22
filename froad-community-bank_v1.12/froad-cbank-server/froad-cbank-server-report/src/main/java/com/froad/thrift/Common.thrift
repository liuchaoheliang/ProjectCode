namespace java com.froad.thrift.vo

/**
 * 平台代码
 */
enum PlatType {
	/**
 	 * boss
 	 */
	boss = 1,
	
	/**
 	 * 银行端
 	 */
    bank = 2,
    
    /**
 	 * 商户pc
 	 */
    merchant_pc = 3,
    
    /**
 	 * 商户h5
 	 */
    merchant_h5 = 4,
    
    /**
 	 * 个人pc
 	 */
    personal_pc = 5,
    
    /**
 	 * 个人h5
 	 */
    personal_h5 = 6,
    
    /**
 	 * 自动任务
 	 */
    auto_task = 7,
}


/**
 * 短信验证码调用
 */
enum SmsTypeEnum {

	/**
	 * 图片验证码
	 */
	image=-1,

	/**
	 * 注册新用户
	 */

	registerNewUser=1000,
	
	/**
	 * 设置支付密码
	 */
	setPaymentPwdSucc = 1100,
	/**
	 * 用户重置密码短信验证码
	 */
	authResetPwd =1001,

	/**
	 * 会员修改支付密码
	 */
	authUpdatePayPwd = 1002,
	
	/**
	 * 会员绑定手机号
	 */
	bindMobile=1300,
	/**
	 * 会员更换手机号码
	 */

	authBackMobile=1302,
	/**
	 * 会员找回支付密码
	 */
	authBackPayPwd=1303,
	/**
	 * 用户修改登录密码
	 */
	authcodeUpdateLoginPwd=1304,
	/**
	 * 联合登录绑定手机号
	 */
	UnionLoginBindMobile=1306,
	/**
	 * 忘记密码短信验证码
	 */
	codeforgetPwd=1307,
	/**
	 * 银行卡签约验证码
	 */
	bankcoderegister=1308,
	/**
	 * 支付验证码
	 */
	fastPaySignCode=1305,

	/**
	 * 商户找回登录密码验证码
	 */
	memberForgetLoginPwd=1301,
	/**
	 * 商户商户总店新增管理员通知
	 */
	merchantAddUser=1104,
	/**
	 * 商户重置登录密码成功提示
	 */
	merchantResetLoginPwd=1105,

	/**
	 * 预售发送提货码
	 */
	presellDelivery=1101,
	/**
	 * 到达提货期
	 */
	presellDeliveryRemind=1102,
	/**
	 * 特惠订单发送消费码
	 */
	privilegeDelivery=1103,
	/**
	 * 团购发劵提示
	 */
	groupTicket=1108,
	/**
	 * 预售预订成功提示
	 */
	presell=1109,
	/**
	 * 线下积分兑换银行验证码
	 */
	offPointsBankSecurityCode=1309,
         /**
          * boss解绑手机号提示
          */
        bossAbsolvedMobile=1310,
	  /**
           * boss绑定手机号提示
           */
        bossBandMobile=1311,
        /**
         * 开通VIP提醒
         */
        openVipReminder=1312,
        /**
         * 登录密码错误5次提示
         * */
        pwdErrorLogin=1110,
        /**
         * 支付密码错误5此提示
         * */
        pwdErrorPay=1111,




}

/**
 * PageVo 分页基础
 */
struct PageVo{
	/** 当前页码 */
	1:i32 pageNumber =1, 
	/** 每页记录数 */
	2:i32 pageSize =20,   
	/** 总记录数 */
	3:i32 totalCount =0,  
	/** 总页数 */	
	4:i32 pageCount=0,    
	/** 开始日期*/
	5:i64 begDate,		  
	/** 结束日期*/
	6:i64 endDate,		  
	/** 上次查询页码 */
	7:i32 lastPageNumber,  
	/** 当前页第一条记录时间 */
	8:i64 firstRecordTime, 
	/** 当前页最后一条记录时间 */
	9:i64 lastRecordTime, 
	/** 是否还有下一页 */ 
	10:bool hasNext
}

/**
 * 返回结果
 */
struct ResultVo {
     /**
      * 返回码 : 0000:成功 ,失败：xxxx
      */
     1:string resultCode;
     
     /**
      * 返回信息
      */
     2:string resultDesc;
}

/**
 * 源对象信息(包含平台,操作ip,操作员id等...)
 */
struct OriginVo {
	/**
	 * 平台代码
	 */
	1: PlatType platType,
	
	/**
	 * 操作员id(说明:如果是个人操作则传用户id;如果是银行操作则传银行操作员id;如果是商户用户操作则传商户用户id)
	 */
	2: i64 operatorId,
	/**
	 * 操作员username(说明:如果是个人操作则传用户名;如果是银行操作则传银行操作员用户名;如果是商户用户操作则传商户用户名)
	 */
	3: string operatorUserName,
	
	/**
	 * 终端ip
	 */
	4: string operatorIp,
	
	/**
	 * 操作说明
	 */
	5:optional string description,
	
	/**
	 * 客户端Id
	 */
	6: string clientId,
	/**
	 * 操作人角色id
	 */
	7:optional string roleId,
	
	/**
	 * 操作人所属组织(说明:如果是个人操作则传空;如果是银行操作则传银行操作员所在orgCode;如果是商户用户操作则传商户id;如果是门店用户操作则传门店id;)
	 */
	8:optional string orgId
}

/**
 * 返回结果
 */
struct ExportResultRes {
     /**
      * 结果信息
      */
     1:ResultVo resultVo;
     
     /**
      * 返回url
      */
     2:string url;
}

/**
 * 添加数据返回主键id公共响应
 */
struct CommonAddVoRes {

	/** 响应基础信息 */
	1:ResultVo result;
	
	/** 主键id */
	2:i64 id;
}