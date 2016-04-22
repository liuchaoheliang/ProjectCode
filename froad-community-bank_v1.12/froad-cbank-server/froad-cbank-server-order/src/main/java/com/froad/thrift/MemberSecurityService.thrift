namespace java com.froad.thrift.service

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "MemberSecurityVo.thrift"


service MemberSecurityService extends BizMonitor.BizMonitorService {
	/**
    * 修改会员登录密码
    */
    Common.ResultVo updateMemberPwd(1:i64 memberCode, 2:string oldPwd, 3:string newPwd);

    /**
    * 重置会员登录密码
    */
    Common.ResultVo resetMemberPwd(1:i64 memberCode, 2:string pwdNew);

    /**
    * 会员联合登录
    * 引用枚举：com.pay.user.helper.BankOrg <br> 枚举值：bankOrg.getBankOrg()
    * 引用枚举：com.pay.user.helper.CreateChannel <br> 
    */
    MemberSecurityVo.UserEngineVo loginUnion(1:string bankOrg, 2:string mobile, 3:string idnetifyNo, 4:string userBankId, 5:string createChannel, 6:string identifyType);

    /**
    * 查询会员是否设置支付密码
    */
    Common.ResultVo isMemberSetPayPwd(1:i64 memberCode);

    /**
    * 验证会员支付密码
    * 引用枚举：com.froad.enums.CreateSource
    */
    Common.ResultVo verifyMemberPayPwd(1:i64 memberCode, 2:string ciphertextPwd, 3:string createSource);

    /**
    * 查询会员是否设置安全问题
    */
    Common.ResultVo isMemberSetQuestion(1:i64 memberCode);

    /**
    * 查询预设安全问题
    */
    MemberSecurityVo.UserEnginQuestionResultVo selectPreinstallQuestion(1:i64 questionAmount);

    /**
    * 查询会员设置的安全问题
    */
    MemberSecurityVo.UserEnginQuestionResultVo selectMemberSetQuestion(1:i64 memberCode);

    /**
     * 校验会员的安全问题
     */
    Common.ResultVo veryfyMemberQuestion(1:i64 memberCode, 2:list<MemberSecurityVo.UserEngineQuestionVo> questions);

    /**
    * 设置会员支付密码
    * 引用枚举：com.froad.enums.CreateSource = createSource
    */
    Common.ResultVo setFirstMemberPayPwd(1:i64 memberCode, 2:string ciphertextPwd, 3:string ciphertextPwdTemp, 4:string createSource);

    /**
    * 修改会员支付密码
    * 引用枚举：com.froad.enums.CreateSource = createSource
    */
    Common.ResultVo updateMemberPayPwd(1:i64 memberCode, 2:string ciphertextPwdOld, 3:string ciphertextPwd, 4:string ciphertextPwdTemp, 5:string createSource);

    /**
    * 重置会员支付密码
    * 引用枚举：com.froad.enums.CreateSource = createSource
    */
    Common.ResultVo resetMemberPayPwd(1:i64 memberCode, 2:string ciphertextPwd, 3:string ciphertextPwdTemp, 4:string createSource);

    /**
    * 设置会员安全问题
    */
    Common.ResultVo setFirstMemberQuestion(1:i64 memberCode, 2:list<MemberSecurityVo.UserEngineQuestionVo> questions);

	/**
	 * 查询用户积分流水历史记录
	 * 引用枚举：com.froad.enums.PointsType = userEnginePointsRecordType
	 */
    MemberSecurityVo.UserEnginePageVoRes selectPageOfMemberPointsRecordHistory(1:string clientId, 2:string longID, 3:i64 pageSize, 4:i64 pageNum, 5:i64 startTime, 7:i64 endTime, 8:string userEnginePointsRecordType);
    
    /**
     * 发送积分兑换码
     */
    Common.ResultVo sendExchangeCode(1:string clientId, 2:string mobile, 3:i32 points, 4:bool useBankChannel);
    
    /**
     * 校验积分兑换码
     */
    Common.ResultVo verifyExchangeCode(1:string clientId, 2:string mobileOrToken, 3:string code, 4:bool useBankChannel);
	
    Common.ResultVo deleteUserSettedQuestion(1:i64 memberCode,2:string clientId);
}