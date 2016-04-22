package com.froad.logic;

import java.util.Date;
import java.util.List;

import com.froad.common.beans.ResultBean;
import com.froad.enums.CreateSource;
import com.froad.enums.PointsType;
import com.froad.thirdparty.dto.response.pe.MemberSecurityQuestion;
import com.pay.user.helper.BankOrg;
import com.pay.user.helper.CreateChannel;


public interface MemberSecurityLogic {

    /**
    * 修改会员登录密码
    * <p>Function: updateMemberPwd</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:48:40
    * @version 1.0
    * @param memberCode
    * @param oldPwd
    * @param newPwd
    * @return
    * ResultBean
    */
    ResultBean updateMemberPwd(Long memberCode, String oldPwd, String newPwd);

    /**
    * 重置会员登录密码
    * <p>Function: resetMemberPwd</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:48:59
    * @version 1.0
    * @param memberCode
    * @param pwdNew
    * @return
    * ResultBean
    */
    ResultBean resetMemberPwd(Long memberCode, String pwdNew);

    /**
    * 会员联合登录
    * <p>Function: loginUnion</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:49:09
    * @version 1.0
    * @param mobile
    * @param idnetifyNo
    * @param userBankId
    * @param value
    * @param bankOrg
    * @param identifyType
    * @return
    * ResultBean.Data MemberInfo
    */
    ResultBean loginUnion(BankOrg bankOrg, String mobile, String idnetifyNo, String userBankId, CreateChannel createChannel, String identifyType,String clientId);

    /**
    * 查询会员是否设置支付密码
    * <p>Function: isMemberSetPayPwd</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:49:24
    * @version 1.0
    * @param memberCode
    * @return
    * ResultBean
    */
    ResultBean isMemberSetPayPwd(Long memberCode);

    /**
    * 验证会员支付密码
    * <p>Function: verifyUserPayPwd</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:49:40
    * @version 1.0
    * @param memberCode
    * @param pwd
    * @return
    * ResultBean
    */
    ResultBean verifyMemberPayPwd(Long memberCode, String ciphertextPwd, CreateSource createSource);

    /**
    * 查询会员是否设置安全问题
    * <p>Function: isMemberSetQuestion</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:49:54
    * @version 1.0
    * @param memberCode
    * @return
    * ResultBean
    */
    ResultBean isMemberSetQuestion(Long memberCode);

    /**
    * 查询预设安全问题
    * <p>Function: selectPreinstallQuestion</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:50:06
    * @version 1.0
    * @param questionAmount
    * @return
    * ResultBean.Data List&lt;MemberSecurityQuestion&gt;
    */
    ResultBean selectPreinstallQuestion(int questionAmount);

    /**
    * 查询会员设置的安全问题
    * <p>Function: selectMemberSetQuestion</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:55:03
    * @version 1.0
    * @param memberCode
    * @return
    * ResultBean.Data List&lt;MemberSecurityQuestion&gt;
    */
    ResultBean selectMemberSetQuestion(Long memberCode);

    /**
     * 校验会员的安全问题
     * <p>Function: veryfyMemberQuestion</p>
     * <p>Description: </p>
     * @author caishican@f-road.com.cn
     * @date 2015年4月8日 下午7:56:00
     * @version 1.0
     * @param memberCode
     * @param questions
     * @return
     * ResultBean
     */
    ResultBean veryfyMemberQuestion(Long memberCode, List<MemberSecurityQuestion> questions);

    /**
    * 设置会员支付密码
    * <p>Function: setFirstMemberPayPwd</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:56:12
    * @version 1.0
    * @param memberCode
    * @param payPwdTemp
    * @return
    * ResultBean
    */
    ResultBean setFirstMemberPayPwd(Long memberCode, String ciphertextPwd, String ciphertextPwdTemp, CreateSource createSource);

    /**
    * 修改会员支付密码
    * <p>Function: setUserPayPwd</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:56:12
    * @version 1.0
    * @param memberCode
    * @param payPwdTemp
    * @return
    * ResultBean
    */
    ResultBean updateMemberPayPwd(Long memberCode, String ciphertextPwdOld, String ciphertextPwd, String ciphertextPwdTemp, CreateSource createSource);

    /**
    * 重置会员支付密码
    * <p>Function: resetMemberPayPwd</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:56:12
    * @version 1.0
    * @param memberCode
    * @param payPwdTemp
    * @return
    * ResultBean
    */
    ResultBean resetMemberPayPwd(Long memberCode, String ciphertextPwd, String ciphertextPwdTemp, CreateSource createSource);

    /**
    * 设置会员安全问题
    * <p>Function: setMemberQuestion</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午7:56:21
    * @version 1.0
    * @param memberCode
    * @param questions
    * @return
    * ResultBean
    */
    ResultBean setFirstMemberQuestion(Long memberCode, List<MemberSecurityQuestion> questions);

	/**
	 * 查询用户积分流水历史记录
	* <p>Function: selectPageOfMemberPointsConsumeHistory</p>
	* <p>Description: </p>
	* @author dongr@f-road.com.cn
	* @date 2015年4月9日 下午2:34:15
	* @version 1.0
	* @param clientAccessType
	* @param clientVersion
	* @param longID 用户登录帐号
	* @param pageSize 每页多少条数据
	* @param pageNum 需要查询第几页
	* @param startTime 开始时间
	* @param endTime 结束时间
	* @return
	 */
    ResultBean selectPageOfMemberPointsRecordHistory(String clientId, String longID, Integer pageSize, Integer pageNum, Date startTime, Date endTime,
            PointsType userEnginePointsRecordType);

    /**
    * 
    * <p>Function: sendExchangeCodeByBank</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月18日 上午11:42:10
    * @version 1.0
    * @param clientId
    * @param mobile
    * @param points
    * @return
    * ResultBean
    */
    ResultBean sendExchangeCodeByBank(String clientId, String mobile, Integer points);

    /**
    * 
    * <p>Function: sendExchangeCodeByPointsAPI</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月18日 下午12:14:53
    * @version 1.0
    * @param clientId
    * @param mobile
    * @param points
    * @return
    * ResultBean
    */
    ResultBean sendExchangeCodeByPointsAPI(String clientId, String mobile, Integer points);

    /**
    * 校验积分平台积分兑换码
    * <p>Function: verifyExchangeCodeOfPointsAPI</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月18日 下午2:07:13
    * @version 1.0
    * @param clientId
    * @param mobile
    * @param code
    * @return
    * ResultBean
    */
    ResultBean verifyExchangeCodeOfPointsAPI(String clientId, String mobile, String code);

    /**
    * 校验银行发送的积分兑换码
    * <p>Function: verifyExchangeCodeOfBankChannel</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月18日 下午2:07:16
    * @version 1.0
    * @param clientId
    * @param token
    * @param code
    * @return
    * ResultBean
    */
    ResultBean verifyExchangeCodeOfBankChannel(String clientId, String token, String code);
    
    ResultBean deleteUserSettedQuestion(long memberCode);

}
