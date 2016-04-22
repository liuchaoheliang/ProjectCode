
package com.froad.support;

import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.pay.user.dto.UserResult;

/**
 * 类描述：银行卡相关操作
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: 蔡世璨 caishican@f-road.com.cn
 * @time: 2015年4月3日 上午9:38:59
 */
public interface BankCardSupport {

    /**
    * 设置快捷支付限额
    * <p>Function: updateFastPayLimitOnOpenAPI</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月3日 上午9:35:53
    * @version 1.0
    * @param cardNo
    * @param singlePenLimit
    * @param dailyLimit
    * @param monthlyLimit
    * @param easyPayGroupId
    * @param openApiPartnerId
    * @return
    * OpenApiRes
    */
    OpenApiRes setFastPayLimitOnOpenAPI(String clientId, String cardNo, String singlePenLimit, String dailyLimit, String monthlyLimit);

    /**
    * 设置快捷支付限额
    * <p>Function: setFastPayLimitOnUserEngine</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月3日 上午9:38:51
    * @version 1.0
    * @param cardNo
    * @param singlePenLimit
    * @param dailyLimit
    * @param monthlyLimit
    * @return
    * UserResult
    */
    UserResult setFastPayLimitOnUserEngine(String cardNo, String singlePenLimit, String dailyLimit, String monthlyLimit);

    /**
    * 取消签约银行卡
    * <p>Function: cancelSignedBankCardOnOpenAPI</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月3日 上午9:39:28
    * @version 1.0
    * @param memberCode
    * @param cardNo
    * @param easyPayGroupId
    * @param openApiPartnerId
    * @return
    * OpenApiRes
    */
    OpenApiRes cancelSignedBankCardOnOpenAPI(String clientId,Long memberCode, String cardNo);

    /**
    * 发送签约卡验证码短信
    * <p>Function: sendSignBankCardMobileTokenUseOpenAPI</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月3日 上午9:39:39
    * @version 1.0
    * @param cardNo
    * @param phone
    * @param clientId
    * @param easyPayGroupId
    * @param openApiPartnerId
    * @return
    * OpenApiRes
    */
    OpenApiRes sendSignBankCardMobileTokenUseOpenAPI(String clientId, String cardNo, String phone);

    /**
    * 将银行卡签约到指定的Client
    * <p>Function: signBankCardByClientId</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月3日 上午9:39:57
    * @version 1.0
    * @param memberCode
    * @param uname
    * @param cardNo
    * @param idcard
    * @param phone
    * @param mobileToken
    * @param easyPayGroupId
    * @param openApiPartnerId
    * @param singlePenLimit
    * @param dayLimit
    * @param monthLimit
    * @return
    * OpenApiRes
    */
    OpenApiRes signBankCardByClientIdOnOpenAPI(String clientId,Long memberCode, String uname, String cardNo, String idcard, String phone, String mobileToken, String singlePenLimit, String dayLimit, String monthLimit);

    /**
    * 通过会员编号获得所有签约卡信息
    * <p>Function: getAllSignCardByMemberCode</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月3日 上午9:40:18
    * @version 1.0
    * @param clientId
    * @param memberCode
    * @return
    * UserResult
    */
    UserResult getAllSignCardByMemberCode(String clientId, Long memberCode);

    /**
    * 签约银行卡
    * <p>Function: signCard</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月3日 上午9:40:36
    * @version 1.0
    * @param cardNo
    * @param uname
    * @param idcard
    * @param phone
    * @param memberCode
    * @param signNo
    * @param easyPayGroupId
    * @param singlePenLimit
    * @param dailyLimit
    * @param monthlyLimit
    * @return
    * UserResult
    */
    UserResult signCardOnUserEngine(String clientId, Long memberCode, String cardNo, String uname, String idcard, String phone, String signNo, String singlePenLimit, String dailyLimit,String monthlyLimit);

    /**
    * 设置默认卡
    * <p>Function: bindDefaultCard</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月3日 上午9:40:48
    * @version 1.0
    * @param cardId
    * @param memberCode
    * @return
    * UserResult
    */
    UserResult bindDefaultCard(Long memberCode, Long cardId);

    /**
    * 通过ID取消绑定的银行卡
    * <p>Function: cancelCardByDataID</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月3日 上午9:40:57
    * @version 1.0
    * @param memberBankId
    * @return
    * UserResult
    */
    UserResult cancelCardByDataID(Long memberBankId);

    /**
     * 设置商户白名单
    * <p>Function: setMerchantWhiteList</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-16 上午10:09:31
    * @version 1.0
    * @param merchantId
    * @param merchantName
    * @param accountNo
    * @param mac
    * @param optionType
    * @return
     */
    OpenApiRes setMerchantWhiteList(String clientId,String merchantId,String merchantName, String accountNo,String mac, String optionType,String accountName);

    /**
     * auditStatusQuery:审核状态查询
     *
     * @author vania
     * 2015年9月15日 上午11:44:44
     * @param bankGroup
     * @param accountName
     * @param accountNo
     * @param partnerID
     * @return
     *
     */
	public OpenApiRes auditStatusQuery(String bankGroup, String accountName, String accountNo,String partnerID);

    com.pay.user.dto.Result synchBankLabel(String bankLabelID, String bankLabelName, int state,String clientId);
    
    public OpenApiRes bankCardAccountCheck(String checkOrg, String accountName,String accountNo, String certificateType, String certificateNo,String partnerID);
}
