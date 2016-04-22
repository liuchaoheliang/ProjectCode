
package com.froad.support.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.froad.logback.LogCvt;
import com.froad.support.BankCardSupport;
import com.froad.support.RedisDataAccessSupport;
import com.froad.thirdparty.dto.request.openapi.CreateMobileTokenApiReq;
import com.froad.thirdparty.dto.request.openapi.LimitReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.openapi.SignApiReq;
import com.froad.thirdparty.dto.request.openapi.SignCancelApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.Client;
import com.froad.thirdparty.enums.BankCardType;
import com.froad.thirdparty.enums.CertificateType;
import com.froad.thirdparty.enums.MobileTokenType;
import com.froad.thirdparty.request.openapi.OpenApiFunc;
import com.froad.thirdparty.request.openapi.impl.OpenApiFuncImpl;
import com.froad.thirdparty.request.userengine.UserEngineFunc;
import com.froad.thirdparty.request.userengine.impl.UserEngineFuncImpl;
import com.froad.util.payment.BaseSubassembly;
import com.pay.user.dto.MemberBankSpecDto;
import com.pay.user.dto.UserResult;

public class BankCardSupportImpl implements BankCardSupport {

    private static final OpenApiFunc openApiFunc = new OpenApiFuncImpl();

    private static final UserEngineFunc userEngineFunc = new UserEngineFuncImpl();

    private static final RedisDataAccessSupport redis = new RedisDataAccessSupportImpl();
    
    @Override
    public OpenApiRes setFastPayLimitOnOpenAPI(String clientId, String cardNo, String singlePenLimit, String dailyLimit, String monthlyLimit) {
        singlePenLimit = StringUtils.isBlank(singlePenLimit) ? "" : singlePenLimit;
        dailyLimit = StringUtils.isBlank(dailyLimit) ? "" : dailyLimit;
        monthlyLimit = StringUtils.isBlank(monthlyLimit) ? "" : monthlyLimit;
        
        String bankId = redis.acquireBankId(clientId);
        String openApiPartnerId = redis.acquireOpenAPIPartnerNo(clientId);

        LimitReq req = new LimitReq(cardNo, singlePenLimit, dailyLimit, monthlyLimit, bankId, openApiPartnerId);

        LogCvt.info("==>设置快捷银行卡限额: 预请求OpenApi系统进行快捷银行卡限额设置 , 请求参数: " + JSONObject.toJSONString(req));
        OpenApiRes res = openApiFunc.setFastPayLimit(req);
        LogCvt.info("<==设置快捷银行卡限额: 请求OpenApi系统进行快捷银行卡限额设置 , 请求结果: " + JSONObject.toJSONString(res));
        return res;
    }

    @Override
    public UserResult setFastPayLimitOnUserEngine(String cardNo, String singlePenLimit, String dailyLimit, String monthlyLimit) {
        MemberBankSpecDto memberBankSpecDto = new MemberBankSpecDto();
        memberBankSpecDto.setCardNo(cardNo);
        memberBankSpecDto.setOrderLimit(singlePenLimit);
        memberBankSpecDto.setDayLimit(dailyLimit);
        memberBankSpecDto.setMonthLimit(monthlyLimit);
        LogCvt.info("==>设置快捷银行卡限额: 请求账户系统，进行对应银行卡限额调整, 请求参数：" + JSONObject.toJSONString(memberBankSpecDto));
        UserResult userResult = userEngineFunc.setLimitCash(memberBankSpecDto);
        LogCvt.info("<==设置快捷银行卡限额: 请求账户系统，进行对应银行卡限额调整 , 请求结果: " + JSONObject.toJSONString(userResult));
        return userResult;
    }

    @Override
    public OpenApiRes cancelSignedBankCardOnOpenAPI(String clientId,Long memberCode, String cardNo) {
        String bankId = redis.acquireBankId(clientId);
        String openApiPartnerId = redis.acquireOpenAPIPartnerNo(clientId);
        SignCancelApiReq req = new SignCancelApiReq(memberCode + "", cardNo, bankId, openApiPartnerId);
        LogCvt.info("==>解约已绑定快捷银行卡: 预请求OpenApi系统进行解约银行卡 , 请求参数: " + JSONObject.toJSONString(req));
        OpenApiRes res = openApiFunc.fastPaySignCancel(req);
        LogCvt.info("<==解约已绑定快捷银行卡: 请求OpenApi系统进行解约银行卡 , 请求结果: " + JSONObject.toJSONString(res));
        return res;
    }

    @Override
    public OpenApiRes sendSignBankCardMobileTokenUseOpenAPI(String clientId, String cardNo, String phone) {
    	String bankId = redis.acquireBankId(clientId);
        String openApiPartnerId = redis.acquireOpenAPIPartnerNo(clientId);

        CreateMobileTokenApiReq req = new CreateMobileTokenApiReq(cardNo, phone, MobileTokenType.SIGN, "由[clientId " + clientId + "] 发起的签约短信", bankId, openApiPartnerId);
        LogCvt.info("==>发送快捷签约银行手机验证码: 预请求OpenApi系统进行快捷签约银行手机验证码发送 , 请求参数: " + JSONObject.toJSONString(req));
        OpenApiRes res = openApiFunc.fastPayMoblieToken(req);
        LogCvt.info("<==发送快捷签约银行手机验证码: 请求OpenApi系统进行快捷签约银行手机验证码发送 , 请求结果: " + JSONObject.toJSONString(res));
        return res;
    }

    @Override
    public OpenApiRes signBankCardByClientIdOnOpenAPI(String clientId,Long memberCode, String uname, String cardNo, String idcard, String phone, String mobileToken, String singlePenLimit, String dayLimit, String monthLimit) {

    	String bankId = redis.acquireBankId(clientId);
        String openApiPartnerId = redis.acquireOpenAPIPartnerNo(clientId);
        SignApiReq req = new SignApiReq(Long.toString(memberCode), uname, // 开户人姓名
                cardNo, // 开户卡号
                BankCardType.DEBITCARD, CertificateType.IDCARD, idcard, phone, mobileToken, // 验证码 银行发送有效 平台自发可不填
                bankId, openApiPartnerId, // 系统对openapi PartnerId
                singlePenLimit, dayLimit, monthLimit);

        LogCvt.info("==>签约快捷银行卡: 预请求OpenApi系统进行签约银行认证 , 请求参数: " + JSONObject.toJSONString(req));
        OpenApiRes res = openApiFunc.fastPaySign(req);
        LogCvt.info("<==签约快捷银行卡: 请求OpenApi系统进行签约银行认证 , 请求结果: " + JSONObject.toJSONString(res));
        return res;
    }

    @Override
    public UserResult getAllSignCardByMemberCode(String clientId, Long memberCode) {
        LogCvt.info("==>查询快捷银行卡：会员编号-" + memberCode);
        String bankId = redis.acquireBankId(clientId);
        UserResult userResult = null;
        if(bankId==null){
        	userResult = new UserResult();
        	userResult.setResult(false);
        	userResult.setErrorMsg("没有找到对应的银行组织编号");
        }
        
        userResult = userEngineFunc.getAllSignCardByMemberCode(memberCode);
        List<MemberBankSpecDto> list = new ArrayList<MemberBankSpecDto>();
        
        if (bankId!=null&&userResult.getResult() && userResult.getMemberBankList()!=null && userResult.getMemberBankList().size() > 0) {
            for (MemberBankSpecDto memberBankSpecDto : userResult.getMemberBankList()) {
                if (bankId.equals(memberBankSpecDto.getBankGroupId())) {
                    list.add(memberBankSpecDto);
                }
            }
        }
        userResult.setMemberBankList(list);
        LogCvt.info("<==查询快捷银行卡：查询结果：" + JSONObject.toJSONString(list));
        return userResult;
    }

    @Override
    public UserResult signCardOnUserEngine(String clientId, Long memberCode, String cardNo, String uname, String idcard, String phone, String signNo, String singlePenLimit, String dailyLimit,String monthlyLimit) {

    	String bankId = redis.acquireBankId(clientId);
        String bankName = redis.acquireBankName(clientId);
        LogCvt.info(String.format("==>签约快捷银行卡: 请求UserEngine签约银行卡，请求参数{cardNo:'%s', uname:'%s', idcard:'%s', phone:'%s', memberCode:'%s', signNo:'%s', singlePenLimit:'%s', dailyLimit:'%s', monthlyLimit:'%s'}",
                        cardNo, uname, idcard, phone, memberCode, signNo, singlePenLimit, dailyLimit, monthlyLimit));
        UserResult result= userEngineFunc.signCard(cardNo, bankName,uname, idcard, phone, memberCode, signNo, bankId, singlePenLimit, dailyLimit, monthlyLimit);
        LogCvt.info("<==签约快捷银行卡: 请求UserEngine签约银行卡，请求参数:"+JSONObject.toJSONString(result));
        
        return result;
    }

    @Override
    public UserResult bindDefaultCard(Long memberCode, Long cardId) {
        LogCvt.info(String.format("==>设置默认支付银行卡：请求参数：{memberCode：'%s', cardId: '%s'}", memberCode, cardId));
        UserResult result = userEngineFunc.bindDefaultCard(cardId, memberCode);
        LogCvt.info("<==设置默认支付银行卡：请求结果："+JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public UserResult cancelCardByDataID(Long memberBankId) {
        LogCvt.info(String.format("==>取消签约的银行卡：请求参数：{memberBankId: '%s'}", memberBankId));
        UserResult result = userEngineFunc.cancelCardByDataID(memberBankId);
        LogCvt.info("<==取消签约的银行卡：请求参数:"+JSONObject.toJSONString(result));
        return result;
    }

	@Override
	public OpenApiRes setMerchantWhiteList(String clientId,String merchantId,String merchantName, String accountNo,String mac, String optionType,String accountName) {
		return openApiFunc.whiteListSet(new OpenApiReq(BaseSubassembly.acquireClientFromRedis(clientId).getSettlePayOrg(), merchantId, merchantName, accountName, accountNo,mac, optionType, Client.PC,BaseSubassembly.acquireOpenAPIPartnerNo(clientId)));
	}
	
	@Override
	public OpenApiRes auditStatusQuery(String bankGroup, String accountName, String accountNo,String partnerID) {
		return openApiFunc.auditStatusQuery(new OpenApiReq(bankGroup, accountName, accountNo,partnerID));
	}

	@Override
	public com.pay.user.dto.Result synchBankLabel(String bankLabelID, String bankLabelName, int state,String clientId) {
		return userEngineFunc.synchBankLabel(bankLabelID, bankLabelName, state,clientId);
	}

	@Override
	public OpenApiRes bankCardAccountCheck(String checkOrg, String accountName,	String accountNo, String certificateType, String certificateNo,String partnerID) {
		return openApiFunc.bankCardAccCheck(new OpenApiReq(checkOrg, accountName, accountNo, certificateNo, certificateType, partnerID));
	}


}
