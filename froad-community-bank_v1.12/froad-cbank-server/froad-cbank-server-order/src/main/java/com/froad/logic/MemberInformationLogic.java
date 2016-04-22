package com.froad.logic;

import java.util.Date;

import com.froad.common.beans.ResultBean;
import com.froad.enums.PointsType;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.enums.ProtocolType;


public interface MemberInformationLogic {

    /**
    * 通过会员编号查询会员信息，包含VIP信息
    * <p>Function: selectUserByMemberCode</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午2:58:16
    * @version 1.0
    * @param memberCode
    * @return
    * ResultBean.Data MemberInfo
    */
    ResultBean queryMemberInfoByMemberCode(long memberCode ,String clientId);

    /**
    * 通过手机号码、账号、Email查询会员信息，包含VIP信息
    * <p>Function: selectUserByLoginID</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午2:58:42
    * @version 1.0
    * @param loginID
    * @return
    * ResultBean.Data MemberInfo
    */
    ResultBean queryMemberInfoByLoginID(String loginID,String clientId);

    /**
    * 通过账号(用户名)查询会员积分信息
    * <p>Function: selectMemberPointsInfoByLoginID</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月8日 下午2:59:02
    * @version 1.0
    * @param clientID
    * @param loginID
    * @return
    * ResultBean
    */
    ResultBean queryMemberPointsInfoByLoginID(String clientId, String loginID);
    
    /**
     * 修改/绑定用户手机号
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
    ResultBean updateMemberBindMobile(long memberCode, String mobile);
    
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
     * 验证登录
    * <p>Function: employeeCodeVerify</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015年7月23日 下午3:56:41
    * @version 1.0
    * @param clientId
    * @param verifyOrg 验证机构号
    * @param employeeCode 员工号
    * @param password 密码
    * @param clientNo 客户端类型PC("100"),ANDROID("200"),IPHONE("300"),IPAD("400")	
    * @return
     */
    ResultBean employeeCodeVerify(String clientId,String verifyOrg,String employeeCode,String password,String clientNo);
    
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
    ResultBean queryPointsTradeHistory(String clientId, String userName,Date fromTime,Date toTime,ProtocolType protocolType,PointsType pointsType,Integer pageSize,Integer pageNum );

    /**
     * 方法描述：发送验证码（由积分平台发送）
     * @param: mobile
     * @param: points
     * @return: ResultDto
     * @version: 1.0
     * @author: dongr@f-road.com.cn
     * @time: 2014年10月2日 上午11:24:41
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
     * @Description: 通过银行卡号查询用户银行积分
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
    * @date 2015年4月20日 下午1:51:45
    * @version 1.0
    * @param clientId
    * @param points
    * @return
    * ResultBean
    */
    ResultBean payPointsByMobile(String clientId,String orderId,String payReason,String remark,String outletOrgNo,String loginId,int points,String bankNo);

	/**
	 * vip续期/购买接口
	 * @param memeberCode
	 * @param level
	 * @param availableDays
	 * @param labelID
	 * @param bankLevel
	 * @return
	 */
	ResultBean processVIPOrder(Long memeberCode, String level, Integer availableDays, Long labelID, Integer bankLevel);

	/**
	 * 根据一级法人行社ID（PE系统配置查询二级法人行社信息
	 * @param clientId
	 * @return List<Node>
	 */
	ResultBean queryMemberVIPLabelByClientID(String clientId);



}
