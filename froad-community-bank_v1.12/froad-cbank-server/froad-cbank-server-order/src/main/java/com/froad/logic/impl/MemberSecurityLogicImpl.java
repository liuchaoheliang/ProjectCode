
package com.froad.logic.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.enums.CreateSource;
import com.froad.enums.PointsType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.MemberSecurityLogic;
import com.froad.support.MemberSecuritySupport;
import com.froad.support.impl.MemberSecuritySupportImpl;
import com.froad.thirdparty.dto.response.pe.MemberSecurityQuestion;
import com.froad.thirdparty.util.PasswordUtils;
import com.froad.thirdparty.util.PaymentControlsRsa;
import com.pay.user.helper.BankOrg;
import com.pay.user.helper.CreateChannel;

public class MemberSecurityLogicImpl implements MemberSecurityLogic {

	/**
	 * 支付密码设置的校验规则
	 */
	private String regEx = "(\\d|[a-z]|[A-Z]){6}";
	
    private static final MemberSecuritySupport support = new MemberSecuritySupportImpl();

    @Override
    public ResultBean updateMemberPwd(Long memberCode, String oldPwd, String newPwd) {
        if (memberCode == null || StringUtils.isBlank(oldPwd) || StringUtils.isBlank(newPwd)) {
            return new ResultBean(ResultCode.failed, "请求参数为空");
        }
        return support.updateMemberPwd(memberCode, oldPwd, newPwd);
    }

    @Override
    public ResultBean resetMemberPwd(Long memberCode, String pwdNew) {
        if (memberCode == null) {
            return new ResultBean(ResultCode.failed, "请求参数会员编号为空");
        }

        if (StringUtils.isBlank(pwdNew)) {
            return new ResultBean(ResultCode.failed, "请求参数新密码为空");
        }
        return support.resetUserPwd(memberCode, pwdNew);
    }

    @Override
    public ResultBean loginUnion(BankOrg bankOrg, String mobile, String idnetifyNo, String userBankId, CreateChannel createChannel, String identifyType,String clientId) {
        return support.loginUnion(mobile, idnetifyNo, userBankId, createChannel.getValue(), bankOrg, identifyType,clientId);
    }

    @Override
    public ResultBean isMemberSetPayPwd(Long memberCode) {
        if (memberCode == null) {
            return new ResultBean(ResultCode.failed, "请求参数会员编号为空");
        }
        return support.isPayPasswordExists(memberCode);
    }

    @Override
    // 解密校验支付密码
    public ResultBean verifyMemberPayPwd(Long memberCode, String ciphertextPwd, CreateSource createSource) {

        if (memberCode == null) {
            return new ResultBean(ResultCode.failed, "请求参数会员编号为空");
        }

        if (StringUtils.isBlank(ciphertextPwd)) {
            return new ResultBean(ResultCode.failed, "支付密码不能为空");
        }
        
        if(createSource ==null){
        	return new ResultBean(ResultCode.failed,"请传入创建渠道参数");
        }

        String payPwd = decodePayPwd(ciphertextPwd, createSource);
        return support.verifyPayPassword(memberCode, payPwd);
    }

    @Override
    public ResultBean isMemberSetQuestion(Long memberCode) {
        if (memberCode == null) {
            return new ResultBean(ResultCode.failed, "请求参数会员编号为空");
        }
        return support.isSafeQuestionExists(memberCode);
    }

    @Override
    public ResultBean selectPreinstallQuestion(int questionAmount) {

//        if (questionAmount > 10) {
//            questionAmount = 10;
//        }

        if (questionAmount <= 0) {
            questionAmount = 1;
        }

        // if (questionAmount < 3) {
        // questionAmount = 3;
        // }

        return support.selectPreinstallQuestion(questionAmount);

    }

    @Override
    public ResultBean selectMemberSetQuestion(Long memberCode) {
        if (memberCode == null) {
            return new ResultBean(ResultCode.failed, "请求参数会员编号为空");
        }
        return support.selectMemberSetQuestion(memberCode);
    }

    @Override
    public ResultBean veryfyMemberQuestion(Long memberCode, List<MemberSecurityQuestion> questions) {
        if (memberCode == null) {
            return new ResultBean(ResultCode.failed, "请求参数会员编号为空");
        }
        if (questions == null || questions.size() == 0) {
            LogCvt.info("校验安全问题未传入有效的安全问题数据");
            return new ResultBean(ResultCode.failed, "请求参数安全问题为空");
        }
        return support.veryfyMemberQuestion(memberCode, questions);
    }

    @Override
    public ResultBean setFirstMemberPayPwd(Long memberCode, String ciphertextPwd, String ciphertextPwdTemp, CreateSource createSource) {
        if (memberCode == null) {
            return new ResultBean(ResultCode.failed, "请求参数会员编号为空");
        }
        if(createSource ==null){
        	return new ResultBean(ResultCode.failed,"请传入创建渠道参数");
        }
        
        if (isSuccess(support.isPayPasswordExists(memberCode))) {
            return new ResultBean(ResultCode.failed, "用户已设置支付密码");
        }
        String payPwd = decodePayPwd(ciphertextPwd, createSource);
        String payPwdTemp = decodePayPwd(ciphertextPwdTemp, createSource);
        if (!payPwd.equals(payPwdTemp)) {
            return new ResultBean(ResultCode.failed, "两次支付密码不一致");
        }
//        if (payPwdTemp.length() != 6) {
//            return new ResultBean(ResultCode.failed, "请输入6位的支付密码");
//        }
        
        if(!payPwdTemp.matches(regEx)){
        	return new ResultBean(ResultCode.failed, "支付密码只能为6位数字或字母");
        }
        return support.setUserPayPwd(memberCode, payPwdTemp);
    }

    @Override
    public ResultBean updateMemberPayPwd(Long memberCode, String ciphertextPwdOld, String ciphertextPwd, String ciphertextPwdTemp, CreateSource createSource) {
        if (memberCode == null) {
            return new ResultBean(ResultCode.failed, "请求参数会员编号为空");
        }
        if(createSource ==null){
        	return new ResultBean(ResultCode.failed,"请传入创建渠道参数");
        }
        
        if (!isSuccess(support.isPayPasswordExists(memberCode))) {
            return new ResultBean(ResultCode.failed, "用户未设置支付密码");
        }
        String payPwdOld = decodePayPwd(ciphertextPwdOld, createSource);
        String payPwd = decodePayPwd(ciphertextPwd, createSource);
        String payPwdTemp = decodePayPwd(ciphertextPwdTemp, createSource);
        if (!payPwd.equals(payPwdTemp)) {
            return new ResultBean(ResultCode.failed, "新支付密码不一致");
        }
        if (payPwdOld.equals(payPwdTemp)) {
            return new ResultBean(ResultCode.failed, "新支付密码不能和原密码相同");
        }
        ResultBean resultBean = support.verifyPayPassword(memberCode, payPwdOld);
        if (!isSuccess(resultBean)) {
            return new ResultBean(ResultCode.failed, "原支付密码错误");
        }
//        if (payPwdTemp.length() != 6) {
//        	return new ResultBean(ResultCode.failed, "请输入6位的支付密码");
//        }
        if(!payPwdTemp.matches(regEx)){
        	return new ResultBean(ResultCode.failed, "支付密码只能为6位数字或字母");
        }
        
        return support.setUserPayPwd(memberCode, payPwdTemp);
    }

    @Override
    public ResultBean resetMemberPayPwd(Long memberCode, String ciphertextPwd, String ciphertextPwdTemp, CreateSource createSource) {
        if (memberCode == null) {
            return new ResultBean(ResultCode.failed, "请求参数为空");
        }
        
        if(createSource ==null){
        	return new ResultBean(ResultCode.failed,"请传入创建渠道参数");
        }
        
        if (!isSuccess(support.isPayPasswordExists(memberCode))) {
            return new ResultBean(ResultCode.failed, "用户未设置支付密码");
        }

        // ResultBean checkResult = this.verifyMemberPayPwd(memberCode, ciphertextPwdTemp, createSource);
        // if (!isSuccess(checkResult)) {
        // return new ResultBean(ResultCode.failed, "找回的密码不能和当前密码相同");
        // }
        //
        String payPwd = decodePayPwd(ciphertextPwd, createSource);
        String payPwdTemp = decodePayPwd(ciphertextPwdTemp, createSource);

        if (!payPwd.equals(payPwdTemp)) {
            return new ResultBean(ResultCode.failed, "新支付密码不一致");
        }
//        if (payPwdTemp.length() != 6) {
//        	return new ResultBean(ResultCode.failed, "请输入6位的支付密码");
//        }
        if(!payPwdTemp.matches(regEx)){
        	return new ResultBean(ResultCode.failed, "支付密码只能为6位数字或字母");
        }
        return support.setUserPayPwd(memberCode, payPwdTemp);
    }

    @Override
    public ResultBean setFirstMemberQuestion(Long memberCode, List<MemberSecurityQuestion> questions) {
        if (memberCode == null) {
            return new ResultBean(ResultCode.failed, "请求参数为空");
        }
        if (questions == null || questions.size() == 0) {
            LogCvt.info("校验安全问题未传入有效的安全问题数据");
            return new ResultBean(ResultCode.failed, "请求参数为空");
        }
//        ResultBean checkResult = support.isSafeQuestionExists(memberCode);
//        if (isSuccess(checkResult)) {
//            return new ResultBean(ResultCode.failed, "用户已设置安全问题");
//        }
        return support.setMemberQuestion(memberCode, questions);
    }

    @Override
    public ResultBean selectPageOfMemberPointsRecordHistory(String clientId, String longID, Integer pageSize, Integer pageNum, Date startTime, Date endTime,
            PointsType userEnginePointsRecordType) {

        return support.selectPageOfMemberPointsRecordHistory(clientId, longID, pageSize, pageNum, startTime, endTime, userEnginePointsRecordType);
    }

    @Override
    public ResultBean sendExchangeCodeByPointsAPI(String clientId, String mobile, Integer points) {
        if (StringUtils.isEmpty(mobile)) {
            return new ResultBean(ResultCode.failed, "手机号码不能为空");
        }

        if (points <= 0) {
            return new ResultBean(ResultCode.success, "积分必须为可正整数");
        }

        return support.sendExchangeCodeByPointsAPI(clientId, mobile, points);
    }

    @Override
    public ResultBean sendExchangeCodeByBank(String clientId, String mobile, Integer points) {
        if (StringUtils.isEmpty(mobile)) {
            return new ResultBean(ResultCode.failed, "手机号码不能为空");
        }

        if (points <= 0) {
            return new ResultBean(ResultCode.success, "积分必须为可正整数");
        }

        return support.sendExchangeCodeByBank(clientId, mobile, points);
    }

    @Override
    public ResultBean verifyExchangeCodeOfPointsAPI(String clientId, String mobile, String code) {
        if (StringUtils.isEmpty(mobile)) {
            return new ResultBean(ResultCode.failed, "token不能为空");
        }

        if (com.alibaba.druid.util.StringUtils.isEmpty(code)) {
            return new ResultBean(ResultCode.failed, "验证码不能为空");
        }

        return support.verifyExchangeCodeOfPointsAPI(clientId, mobile, code);
    }

    @Override
    public ResultBean verifyExchangeCodeOfBankChannel(String clientId, String token, String code) {
        if (StringUtils.isEmpty(token)) {
            return new ResultBean(ResultCode.failed, "token不能为空");
        }

        if (com.alibaba.druid.util.StringUtils.isEmpty(code)) {
            return new ResultBean(ResultCode.failed, "验证码不能为空");
        }
        return support.verifyExchangeCodeOfBankChannel(clientId, token, code);
    }

    private boolean isSuccess(ResultBean result) {
        return result.getCode().equals(ResultCode.success.getCode());
    }

    private String decodePayPwd(String ciphertextPwd, CreateSource createSource) {
        String payPwd = null;
        // 根据不同渠道解密
        try {
            if (CreateSource.pc.equals(createSource)) {
                LogCvt.info("使用PC解密方式");
                payPwd = PaymentControlsRsa.decodeRSAString(ciphertextPwd);
            } else {
                LogCvt.info("使用非PC统一解密方式");
                payPwd = PasswordUtils.decrypt(ciphertextPwd);
            }
        } catch (Exception e) {
            LogCvt.error("解密支付密码出现异常 " + createSource.toString(), e);
        }

        if (payPwd == null) {
            LogCvt.error("解密支付密码失败，解密内容为null");
        }
        return payPwd;
    }

	@Override
	public ResultBean deleteUserSettedQuestion(long memberCode) {
		return support.deleteUserSettedQuestion(memberCode);
	}

}
