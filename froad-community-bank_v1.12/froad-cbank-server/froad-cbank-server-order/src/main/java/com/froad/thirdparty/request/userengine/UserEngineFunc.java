package com.froad.thirdparty.request.userengine;

import java.util.Date;
import java.util.List;

import com.pay.pe.dto.AccountResult;
import com.pay.pe.dto.QuestionSpecDto;
import com.pay.user.dto.MemberBankSpecDto;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.BankOrg;
import com.pay.user.helper.VIPLevel;

/**
 * 账户引擎系统
* <p>Function: UserEngineFunc</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2014年12月24日 下午3:47:45
* @version 1.0
 */
public interface UserEngineFunc {
	
	
	/**
	 * 通过MemberCode查询用户信息
	* <p>Function: queryByMemberCode</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:48:37
	* @version 1.0
	* @param memberCode
	* @return
	 */
	public UserResult queryByMemberCode(Long memberCode);
	
	/**
	 * 通过登录帐号获取用户信息
	* <p>Function: queryByLoginID</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:48:50
	* @version 1.0
	* @param loginID
	* @return
	 */
	public UserResult queryByLoginID(String loginID);
	
	/**
	 * 查询会员VIP信息
	* <p>Function: queryVIPInfoByMemberCode</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月25日 下午4:47:01
	* @version 1.0
	* @param memberCode
	* @return
	 */
	public Result queryVIPInfoByMemberCode(Long memberCode,String clientId);
	
	/**
	 * 开通用户VIP
	* <p>Function: processVIPOrder</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-15 上午10:32:07
	* @version 1.0
	* @param memberCode
	* @param vipLevel
	* @param availableDays
	* @param labelID
	* @param bankLevel
	* @return
	 */
	public Result processVIPOrder(Long memberCode, VIPLevel vipLevel,Date vipExpirationTime, String bankLabelID, String bankOrg,String clientChannel);
	
	/**
	 * VIP退款接口
	 * cancelVIPOrder:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年12月7日 下午2:58:07
	 * @param memberCode
	 * @param bankOrg
	 * @return
	 *
	 */
	public Result cancelVIPOrder(Long memberCode,String bankOrg);
	
	/**
	 * 同步银行标签
	* <p>Function: synchBankLabel</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年7月16日 上午11:38:33
	* @version 1.0
	* @param bankLabelID
	* @param bankLabelName
	* @param state
	 */
	public Result synchBankLabel(String bankLabelID,String bankLabelName,Integer state,String clientId);
	
	/**
	 * 校验支付密码是否正确
	* <p>Function: verifyPayPwd</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:53:37
	* @version 1.0
	* @param memberCode
	* @param payPwd
	* @return
	 */
	public AccountResult verifyPayPwd(Long memberCode, String payPwd);
	
	/**
	 * 校验用户是否设置支付密码
	* <p>Function: isUserSetPayPwd</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:52:34
	* @version 1.0
	* @param memberCode
	* @return
	 */
	public AccountResult isUserSetPayPwd(Long memberCode);
	
	/**
	 * 检查用户是否设置安全问题
	* <p>Function: isUserSetQuestion</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:53:45
	* @version 1.0
	* @param memberCode
	* @return
	 */
	public AccountResult isUserSetQuestion(Long memberCode);
	
	/**
	 * 获取系统预设问题列表
	* <p>Function: selectPreinstallQuestion</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年1月6日 下午4:05:28
	* @version 1.0
	* @param questionAmount
	* @return
	 */
	public AccountResult selectPreinstallQuestion(int questionAmount);
	
	/**
	 * 获取用户设置的安全问题
	* <p>Function: selectMemberSetQuestion</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年1月6日 下午4:25:18
	* @version 1.0
	* @param memberCode
	* @return
	 */
	public AccountResult selectMemberSetQuestion(Long memberCode);
	
	
	/**
	 * 校验用户安全问题
	* <p>Function: veryfyMemberQuestion</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:54:54
	* @version 1.0
	* @param memberCode
	* @param questions
	* @return
	 */
	public AccountResult veryfyMemberQuestion(Long memberCode, List<QuestionSpecDto> questions);
	
	
	/**
	 * 设置支付密码
	* <p>Function: setUserPayPwd</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年1月7日 上午10:20:00
	* @version 1.0
	* @param memberCode
	* @param payPwd
	* @return
	 */
	public AccountResult setUserPayPwd(Long memberCode, String payPwd);
	
	/**
	 * 设置安全问题
	* <p>Function: setUserQuestion</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:54:16
	* @version 1.0
	* @param memberCode
	* @param questions
	* @return
	 */
	public AccountResult setUserQuestion(Long memberCode, List<QuestionSpecDto> questions);
	
	/**
	 * 通过memberCode查询所有绑定的快捷支付银行卡
	* <p>Function: getAllSignCardByMemberCode</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:51:51
	* @version 1.0
	* @param memberCode
	* @return
	 */
	public UserResult getAllSignCardByMemberCode(Long memberCode);
	
	/**
	 * 账户账务快捷签约
	* <p>Function: signCard</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:51:35
	* @version 1.0
	* @param cardNo
	* @param uname
	* @param idcard
	* @param phone
	* @param memberCode
	* @param agreementNo
	* @param easyPayGroupId
	* @param singlePenLimit
	* @param dayLimit
	* @param monthLimit
	* @return
	 */
	public UserResult signCard(String cardNo,String bankName, String uname, String idcard, String phone, Long memberCode, String agreementNo, String easyPayGroupId, String singlePenLimit, String dayLimit, String monthLimit);

	/**
	 * 通过memberbankID解绑该卡的快捷支付（逻辑删除）
	* <p>Function: cancelCardByDataID</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:52:02
	* @version 1.0
	* @param memberBankID
	* @return
	 */
	public UserResult cancelCardByDataID(Long memberBankID);
	
	
	
	/**
	 * 删除用户已设置的安全问题
	* <p>Function: deleteUserSettedQuestion</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-15 上午10:27:28
	* @version 1.0
	* @param memberCode
	* @return
	 */
	public AccountResult deleteUserSettedQuestion(long memberCode);
	
	/**
	 * 设置或者修改快捷支付限额
	* <p>Function: setLimitCash</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:54:27
	* @version 1.0
	* @param dto
	* @return
	 */
	public UserResult setLimitCash(MemberBankSpecDto dto);
	
	/**
	 * 更改用户密码
	* <p>Function: updatePwd</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:49:44
	* @version 1.0
	* @param memberCode
	* @param oldPwd
	* @param newPwd
	* @return
	 */
	public UserResult updateMemberPwd(Long memberCode, String oldPwd, String newPwd);
	
	/**
	 * 重置用户密码
	* <p>Function: resetUserPwd</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:50:26
	* @version 1.0
	* @param memCode
	* @param password
	* @return
	 */
	public UserResult resetUserPwd(Long memCode, String password);
	
	/**
	 * 联合登录
	* <p>Function: loginUnion</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年1月11日 下午3:06:10
	* @version 1.0
	* @param mobile
	* @param identifyType
	* @param idnetifyNo
	* @param userBankId
	* @param createChannel
	* @param bankOrg
	* @return
	 */
	public Result loginUnion(String mobile,String idnetifyNo,String userBankId,String createChannel,BankOrg bankOrg,String identifyType);
	
	/**
	 * 设置快捷默认卡
	* <p>Function: bindDefaultCard</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年1月11日 下午4:05:26
	* @version 1.0
	* @param cardId
	* @param memberCode
	* @return
	 */
	public UserResult bindDefaultCard(Long cardId,Long memberCode);
	
	
	
	//--------------------------新版使用中
	
	
	
	
	
	
	
	
	
	
	/**
	 * 将会员信息关联第三方信息
	* <p>Function: oauth</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:55:01
	* @version 1.0
	* @param openId
	* @param nickName
	* @param oauthType
	* @param memberCode
	* @return
	 */
	Result oauth(String openId, String nickName, String oauthType, Long memberCode);

	/**
	 * 会员注册
	* <p>Function: registerMember</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:48:07
	* @version 1.0
	* @param userSpecDto
	* @return
	 */
	public UserResult registerMember(UserSpecDto userSpecDto);

	/**
	 * 通过MemberCode更新User
	* <p>Function: updateMemberInfoByMemberCode</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:48:20
	* @version 1.0
	* @param userSpecDto
	* @return
	 */
	public UserResult updateMemberInfoByMemberCode(UserSpecDto userSpecDto);

	/**
	 * 用户登录
	* <p>Function: login</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:49:14
	* @version 1.0
	* @param loginID
	* @param loginPWD
	* @param loginIP
	* @return
	 */
	public UserResult login(String loginID, String loginPWD, String loginIP);

	/**
	 * 通过memberCode找回密码
	* <p>Function: forgetPwd</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:49:33
	* @version 1.0
	* @param memberCode
	* @return
	 */
	public UserResult forgetPwd(Long memberCode);

	/**
	 * 绑定手机号码
	* <p>Function: bindMobile</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:49:54
	* @version 1.0
	* @param memberCode
	* @param mobile
	* @return
	 */
	public UserResult bindMobile(Long memberCode, String mobile);
	
	/**
	 * 绑定会员邮箱
	 * @Title: bindMail 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param memberCode
	 * @param email
	 * @return
	 * @return UserResult    返回类型 
	 * @throws
	 */
	public UserResult bindMail(Long memberCode, String email);
	
	/**
	 * 解绑会员手机
	 * @Title: unBindMobile 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param memberCode
	 * @return
	 * @return UserResult    返回类型 
	 * @throws
	 */
	public UserResult unBindMobile(Long memberCode);

	/**
	 * 解绑会员邮箱
	 * @Title: unBindMail 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param memberCode
	 * @return
	 * @return UserResult    返回类型 
	 * @throws
	 */
	public UserResult unBindMail(Long memberCode);
	
	/**
	 * 修改用户信息
	* <p>Function: updateMemberInfo</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:50:04
	* @version 1.0
	* @param userSpecDto
	* @return
	 */
	public UserResult updateMemberInfo(UserSpecDto userSpecDto);

	

	/**
	 * 银行卡认证
	* <p>Function: bankCardAuth</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:50:36
	* @version 1.0
	* @param memberBank
	* @return
	 */
	public UserResult bankCardAuth(MemberBankSpecDto memberBank);

	/**
	 * 银行信息绑定
	* <p>Function: bindMemberBank</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:50:51
	* @version 1.0
	* @param memberBank
	* @return
	 */
	public UserResult bindMemberBank(MemberBankSpecDto memberBank);

	/**
	 * 查询会员银行绑定信息列表
	* <p>Function: queryMemberBankInfo</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:51:01
	* @version 1.0
	* @param memberCode
	* @return
	 */
	public UserResult queryMemberBankInfo(Long memberCode);

	/**
	 * 解除银行信息绑定
	* <p>Function: removeMemberBank</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:51:21
	* @version 1.0
	* @param id
	* @param memberCode
	* @return
	 */
	public UserResult removeMemberBank(Long id, Long memberCode);

	/**
	 * 重置用户密码
	* <p>Function: forgetPwdToReset</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:52:59
	* @version 1.0
	* @param userSpecDto
	* @return
	 */
	public UserResult forgetPwdToReset(UserSpecDto userSpecDto);

	/**
	 * 修该支付密码
	* <p>Function: updateUserPayPwd</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:53:20
	* @version 1.0
	* @param memberCode
	* @param payPwd
	* @param payPwdNew
	* @return
	 */
	public AccountResult updateUserPayPwd(Long memberCode, String payPwd, String payPwdNew);


	/**
	 * 解除第三方信息关联
	* <p>Function: disableOauth</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:55:12
	* @version 1.0
	* @param openId
	* @param oauthType
	* @return
	 */
	Result disableOauth(String openId, String oauthType);

	/**
	 * 查询会员第三方登录信息
	* <p>Function: queryMemberOauth</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:55:23
	* @version 1.0
	* @param memberCode
	* @param oauthType
	* @return
	 */
	UserResult queryMemberOauth(Long memberCode, String oauthType);

	/**
	 * 同步昵称
	* <p>Function: synchroName</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:55:38
	* @version 1.0
	* @param memberCode
	* @param oauthType
	* @param nickNamee
	* @return
	 */
	Result synchroName(Long memberCode, String oauthType, String nickNamee);
	
	
	/**
	 * 新用户重置密码（自动重置密码，并且发送短信通知）
	* <p>Function: forgetPwdNew</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年12月24日 下午3:55:47
	* @version 1.0
	* @param memberCode
	* @param SMSSuffix
	* @return
	 */
	UserResult forgetPwdNew(Long memberCode,String SMSSuffix);

    /**
    * 根据银行卡号和银行组号，查询银行卡信息和会员信息
    * <p>Function: queryMemberAndCardInfo</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月22日 下午5:34:53
    * @version 1.0
    * @param cardNo
    * @param bankOrgNo
    * @return
    * UserResult
    */
    UserResult queryMemberAndCardInfo(String cardNo, String bankOrgNo);

	Result processVIPOrder(Long memeberCode, VIPLevel level,
			Integer availableDays, Long labelID, Integer bankLevel);

	/**
	 * 通过ID查询下属二级法人行社
	 * @param bankID
	 * @return List&lt;CascadeNode&gt;
	 */
	Result querySecondaryBankListByBankID(Long bankID);

}
