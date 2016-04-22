include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "BankCardVo.thrift"
include "MemberInformationVo.thrift"
include "MemberSecurityService.thrift"
namespace java com.froad.thrift.service

service MemberInformationService extends BizMonitor.BizMonitorService {
	
    /**
    * 通过会员编号查询会员信息，包含VIP信息
    * Common.ResultVo.Data MemberInfo
    */
    MemberInformationVo.MemberInfoVo selectUserByMemberCode(1:i64 memberCode,2:string clientId);

    /**
    * 通过手机号码、账号、Email查询会员信息，包含VIP信息
    * Common.ResultVo.Data MemberInfo
    */
    MemberInformationVo.MemberInfoVo selectUserByLoginID(1:string loginID,2:string clientId);

    /**
    * 通过账号(用户名)查询会员积分信息
    * Common.ResultVo
    */
    MemberInformationVo.UserEnginePointsVo selectMemberPointsInfoByLoginID(1:string clientId, 2:string loginID);
    
    /**
     * 修改用户手机号
    * Common.ResultVo
    */
    Common.ResultVo updateUserMobile(1:i64 memberCode, 2:string mobile);
    
    /**
     * 发送签约验证短信（银行发送）
     * Common.ResultVo
     */
    MemberInformationVo.PeAcctVo sendSignBankCardPhoneToken(1:string clientId, 2:string phone, 3:string cardNo);
    
    /**
     * 发送快捷银行手机验证码
     * 
     * Common.ResultVo
     */
    MemberInformationVo.PeAcctVo fastPayMoblieToken(1:string clientId, 2:string bankCardNo,3:string phone,4:string remark);

    /**
     * 检验手机号是否为有效的贴膜卡用户
     * Common.ResultVo
     */
    Common.ResultVo validateFilmMobile(1:string clientId, 2:string filmMobile);
    
    /**
     * 用户消费积分查询 
     * Common.ResultVo<br>
     * protocolType=com.froad.thirdparty.enums.ProtocolType<br>
     * pointType=com.froad.enums.PointsType
     */
    MemberInformationVo.QueryProtocolVo getPointTransBypage(1:string clientId, 2:string userName,3:i64 fromTime,4:i64 toTime,5:string protocolType,6:string pointType,7:i32 pageSize,8:i32 pageNum );

    /**
     * 方法描述：发送验证码（由积分平台发送）
     */
    Common.ResultVo sendCheckCode(1:string clientId, 2:string mobile, 3:string points);

    /**
     * 方法描述：按手机号查询银行积分
     */
    Common.ResultVo findBankPointsByMobile(1:string clientId, 2:string mobile);	
    
    /**
    * 通过银行卡号查询银行积分（安徽）
    */
    MemberInformationVo.PointsAccountVo queryBankPointsByBankNo(1:string clientId,2:string bankNo);
    
 	/**
 	* 通过手机号消费积分
 	*/   
 	Common.ResultVo payPointsByMobileNo(1:string clientId,2:string orderId,3:string payReason,4:string remark,5:string outletOrgNo,6:string loginId,7:i32 points);
 	
 	Common.ResultVo employeeCodeVerify(1:string clientId, 2:string verifyOrg, 3:string employeeCode, 4:string password,5:string clientNo);
}