
package com.froad.logic;

import com.froad.common.beans.ResultBean;

public interface BankCardLogic {

    /**
    * 查询指定用户下指定客户端下已签约的银行卡
    * 
    * <p>Function: selectSignedBankCardByClientId</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月2日 下午5:09:01
    * @version 1.0
    * @param clientId
    * @param memberCode
    * @return
    * ResultBean
    */
    ResultBean selectSignedBankCardByClientId(String clientId,long memberCode);

    /**
    * 签约指定客户端快捷银行卡
    * 
    * <p>Function: signBankCardByClientId</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月2日 下午5:09:28
    * @version 1.0
    * @param clientId
    * @param cardNo
    * @param uname
    * @param idcard
    * @param phone
    * @param memberCode
    * @param singlePenLimit
    * @param dayLimit
    * @param monthLimit
    * @param mobileToken
    * @param openApiPartnerId
    * @return
    * ResultBean
    */
    ResultBean signBankCardByClientId(String clientId,long memberCode, String cardNo, String uname, String idcard, String phone, String singlePenLimit, String dayLimit, String monthLimit,String mobileToken,String pointCardNo);

    /**
    * 设置默认的签约快捷银行卡
    * 
    * <p>Function: setDefaultSignerBankCard</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月2日 下午5:09:49
    * @version 1.0
    * @param cardId
    * @param memberCode
    * @return
    * ResultBean
    */
    ResultBean setDefaultSignerBankCard(long memberCode,long cardId);

    /**
    * 使用银行提供的签约短信验证码接口（需银行支持）
    * 
    * <p>Function: sendSignBankCardMobileToken</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月2日 下午5:10:07
    * @version 1.0
    * @param clientId
    * @param phone
    * @param cardNo
    * @param openApiPartnerId
    * @return
    * ResultBean
    */
    ResultBean sendSignBankCardMobileToken(String clientId, String phone, String cardNo);

    /**
    * 解约已绑定的银行卡
    * 
    * <p>Function: cancelSignedBankCard</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月2日 下午5:10:14
    * @version 1.0
    * @param clientId
    * @param memberCode
    * @param cardNo
    * @param openApiPartnerId
    * @return
    * ResultBean
    */
    ResultBean cancelSignedBankCard(String clientId, long memberCode, String cardNo);

    /**
    * 设置已签约的银行卡限额
    * <p>Function: updateSignBankCardLimitCash</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月2日 下午5:10:34
    * @version 1.0
    * @param clientId
    * @param cardNo
    * @param singlePenLimit
    * @param dailyLimit
    * @param monthlyLimit
    * @param openApiPartnerId
    * @return
    * ResultBean
    */
    ResultBean updateSignBankCardLimitCash(String clientId, String cardNo, String singlePenLimit, String dailyLimit, String monthlyLimit);

    
    /**
     * 设置商户银行卡白名单
    * <p>Function: setMerchantWhiteList</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-16 上午10:07:51
    * @version 1.0
    * @param merchantId
    * @param merchantName
    * @param accountNo
    * @param mac
    * @param optionType
    * @return
     */
    ResultBean setMerchantWhiteList(String clientId,String merchantId,String merchantName, String accountNo, String mac, String optionType,String accountName);
    
    ResultBean synchBankLabel(String bankLabelID, String bankLabelName, int state,String clientId) ;
    
    /**
     * 查询白名单审核结果
     * auditStatusQuery:(这里用一句话描述这个方法的作用).
     *
     * @author Zxy
     * 2015-9-15 下午1:26:33
     * @param clientId
     * @param accountName
     * @param accountNo
     * @return
     *
     */
    ResultBean auditStatusQuery(String clientId, String accountName, String accountNo);
    
    ResultBean bankCardAccountCheck (String clientId,String accountName,String accountNo, String certificateType, String certificateNo);
    
    String getSignPointCardNo(Long memberCode);

}
