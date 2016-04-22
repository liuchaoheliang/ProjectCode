package com.froad.support;

import java.util.Date;

import com.froad.common.beans.ResultBean;
import com.froad.enums.PointsType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.Client;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.response.pe.MemberInfo;
import com.froad.thirdparty.enums.ProtocolType;
import com.pay.user.helper.VIPLevel;


public interface MemberInformationSupport {

    /**
    * 通过会员编号查询会员信息
    * <p>Function: queryByMemberCode</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午2:53:04
    * @version 1.0
    * @param memberCode
    * @return
    * ResultBean.Data MemberInfo
    */
    ResultBean queryByMemberCode(long memberCode,String clientId);

    /**
    * 通过会员编号查询VIP信息
    * <p>Function: queryVIPInfoByMemberCode</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午2:53:43
    * @version 1.0
    * @param memberCode
    * @return
    * ResultBean.Data MemberVipInfo 
    */
    ResultBean queryVIPInfoByMemberCode(long memberCode,String clientId);

    /**
    * 通过手机号码、账号、Email查询会员信息
    * <p>Function: queryByLoginID</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午2:54:40
    * @version 1.0
    * @param loginID
    * @return
    * ResultBean.Data MemberInfo
    */
    ResultBean queryByLoginID(String loginID);

    /**
    * 查询会员积分相关信息
    * <p>Function: queryUserPoints</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午2:55:16
    * @version 1.0
    * @param clientID
    * @param loginID
    * @return
    * ResultBean MemberPointsInfo
    */
    ResultBean queryUserPoints(String clientId, String loginID);
    
    /**
     * 修改用户手机号
    * <p>Function: updateUserMobile</p>
    * <p>Description: </p>
    * @author dongr@f-road.com.cn
    * @date 2015年4月10日 下午12:02:23
    * @version 1.0
    * @param memberCode
    * @param mobile
    * @return
    * ResultBean
    */
    ResultBean updateUserMobile(long memberCode, String mobile);
    
    
    /**
	 * 绑定会员邮箱
	 * @Title: bindMail 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param memberCode
	 * @param email
	 * @return
	 * @return ResultBean    返回类型 
	 * @throws
	 */
	public ResultBean bindMail(Long memberCode, String email);
	
	/**
	 * 解绑会员手机
	 * @Title: unBindMobile 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param memberCode
	 * @return
	 * @return ResultBean    返回类型 
	 * @throws
	 */
	public ResultBean unBindMobile(Long memberCode);

	/**
	 * 解绑会员邮箱
	 * @Title: unBindMail 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param memberCode
	 * @return
	 * @return ResultBean    返回类型 
	 * @throws
	 */
	public ResultBean unBindMail(Long memberCode);
    
    /**
     * 发送签约验证短信（银行发送）
     * 
     * <p>Function: sendSignBankCardPhoneToken</p>
     * <p>Description: </p>
     * @author dongr@f-road.com.cn
     * @date 2015年4月10日 下午1:41:27
     * @version 1.0
     * @param clientID
     * @param phone
     * @param cardNo
     * @return
     * ResultBean
     */
    ResultBean sendSignBankCardPhoneToken(String clientId, String phone, String cardNo);
    
    /**
     * 发送快捷银行手机验证码
     * 
     * <p>Function: fastPayMoblieToken</p>
     * <p>Description: </p>
     * @author dongr@f-road.com.cn
     * @date 2015年4月10日 下午2:16:25
     * @version 1.0
     * @param clientID
     * @param bankCardNo
     * @param phone
     * @param remark
     * @return
     * ResultBean
     */
    ResultBean sendFastPayMoblieToken(String clientId, String bankCardNo,String phone,String remark);

    /**
     * 检验手机号是否为有效的贴膜卡用户
     * 
     * <p>Function: validateFilmMobile</p>
     * <p>Description: </p>
     * @author dongr@f-road.com.cn
     * @date 2015年4月10日 下午2:27:58
     * @version 1.0
     * @param clientID
     * @param filmMobile
     * @return
     * ResultBean
     */
    ResultBean validateFilmMobile(String clientId, String filmMobile);
    
    /**
     * 用户消费积分查询
     * 
     * <p>Function: getPointTransBypage</p>
     * <p>Description: </p>
     * @author dongr@f-road.com.cn
     * @date 2015年4月10日 下午2:36:32
     * @version 1.0
     * @param clientAccessType
     * @param clientVersion
     * @param userName
     * @param fromTime
     * @param toTime
     * @param protocolType
     * @param pageSize
     * @param pageNum
     * @return
     * ResultBean
     */
    ResultBean queryPointsTradeHistory(String clientId, String userName,Date fromTime,Date toTime,ProtocolType protocolType,PointsType pointsType ,Integer pageSize,Integer pageNum );

    /**
     * 发送验证码（由积分平台发送）
     * 
     * <p>Function: sendCheckCode</p>
     * <p>Description: </p>
     * @author dongr@f-road.com.cn
     * @date 2015年4月10日 下午4:21:46
     * @version 1.0
     * @param clientID
     * @param mobile
     * @param points
     * @return
     * ResultBean
     */
    ResultBean sendExchangeCheckCode(String clientId, String mobile, String points);
    
    /**
     * 方法描述：按手机号查询银行积分
     * @param: mobile
     * @return: Result<String>
     * @version: 1.0
     * @author: dongr@f-road.com.cn
     * @time: 2014年10月2日 上午11:23:49
     */
    ResultBean queryBankPointsByMobile(String clientId, String mobile);
    
    /**
     * Copyright © 2015 F-Road. All rights reserved.
     * @Title: queryBankPointsByBankNo
     * @Description: 通过银行卡号查询银行积分
     * @Author: zhaoxiaoyao@f-road.com.cn
     * @param bankNo
     * @return
     * @Return: ResultBean
     */
    ResultBean queryBankPointsByBankCardNo(String clientId,String bankNo);

    /**
    * 通过手机号消费积分
    * <p>Function: payPointsByMobileNo</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月20日 下午1:51:07
    * @version 1.0
    * @param clientId
    * @param points
    * @return
    * ResultBean
    */
    ResultBean payPointsByMobile(String clientId,String orderId,String payReason, String remark,String outletOrgNo,String loginId,int points,String bankNo);

    /**
    * 通过银行卡号查询会员信息与银行卡信息
    * <p>Function: queryMemberAndCardInfo</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月22日 下午7:18:15
    * @version 1.0
    * @param clientId
    * @param cardNo
    * @return
    * ResultBean.data UserResult
    */
    ResultBean queryMemberAndCardInfo(String clientId, String cardNo);
    
	/**
	 * 注册会员
	 * @param memberInfo
	 * @return
	 */
	ResultBean registerMember(MemberInfo memberInfo);

	/**
	 * 注册会员VIP
	 * @param memeberCode
	 * @param level
	 * @param availableDays
	 * @param labelID
	 * @param bankLevel
	 * @return
	 */
	ResultBean processVIPOrder(Long memeberCode, String level, Integer availableDays, Long labelID, Integer bankLevel);

	/**
	 * 查询会员标签
	 * @param clientId
	 * @return
	 */
	ResultBean queryMemberVIPLabelByClientID(String clientId);

	public ResultBean employeeCodeVerify(String clientId, String verifyOrg, String employeeCode, String password,Client client);
}
