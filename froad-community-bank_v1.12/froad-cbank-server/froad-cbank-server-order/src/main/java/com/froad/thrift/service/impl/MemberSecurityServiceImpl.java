
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.common.beans.ResultBean;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.CreateSource;
import com.froad.enums.PointsType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.MemberSecurityLogic;
import com.froad.logic.impl.MemberSecurityLogicImpl;
import com.froad.thirdparty.dto.request.points.UserEnginePointsRecord;
import com.froad.thirdparty.dto.response.pe.MemberSecurityQuestion;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MemberSecurityService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.member.UserEnginQuestionResultVo;
import com.froad.thrift.vo.member.UserEnginePageVoRes;
import com.froad.thrift.vo.member.UserEnginePointsRecordVo;
import com.froad.thrift.vo.member.UserEngineQuestionVo;
import com.froad.thrift.vo.member.UserEngineVo;
import com.froad.util.BeanUtil;
import com.froad.util.DateUtil;
import com.pay.user.helper.BankOrg;
import com.pay.user.helper.CreateChannel;

@SuppressWarnings("unchecked")
public class MemberSecurityServiceImpl extends BizMonitorBaseService implements MemberSecurityService.Iface {
	public MemberSecurityServiceImpl() {}
	public MemberSecurityServiceImpl(String name, String version) {
		super(name, version);
	}
    private static final MemberSecurityLogic secureLogic = new MemberSecurityLogicImpl();

    @Override
    public ResultVo updateMemberPwd(long memberCode, String oldPwd, String newPwd) throws TException {
        return convert(secureLogic.updateMemberPwd(memberCode, oldPwd, newPwd));
    }

    @Override
    public ResultVo resetMemberPwd(long memberCode, String pwdNew) throws TException {
        return convert(secureLogic.resetMemberPwd(memberCode, pwdNew));
    }

    @Override
    public UserEngineVo loginUnion(String bankOrg, String mobile, String idnetifyNo, String userBankId, String createChannel, String identifyType) throws TException {
        BankOrg bankOrgEnum = convertStringToBankOrg(bankOrg);
        CreateChannel createChannelEnum = convertStringToCreateChannel(createChannel);
//        ResultBean result = secureLogic.loginUnion(bankOrgEnum, mobile, idnetifyNo, userBankId, createChannelEnum, identifyType);
//        if(result.getCode().equals(ResultCode.success.getCode())){
//            return (UserEngineVo) BeanUtil.copyProperties(UserEngineVo.class, result.getData());
//        }
        return new UserEngineVo();
    }

    @Override
    public ResultVo isMemberSetPayPwd(long memberCode) throws TException {
        return convert(secureLogic.isMemberSetPayPwd(memberCode));
    }

    @Override
    public ResultVo verifyMemberPayPwd(long memberCode, String ciphertextPwd, String createSource) throws TException {
    	ResultBean resultBean =secureLogic.verifyMemberPayPwd(memberCode, ciphertextPwd, convertStringToCreateSource(createSource));
    	LogCvt.info("支付密码校验: memberCode: " + memberCode + " 校验结果: " + resultBean.toString());
    	return convert(resultBean);
    }

    @Override
    public ResultVo isMemberSetQuestion(long memberCode) throws TException {
        return convert(secureLogic.isMemberSetQuestion(memberCode));
    }

    @Override
    public UserEnginQuestionResultVo selectPreinstallQuestion(long questionAmount) throws TException {
        ResultBean result = secureLogic.selectPreinstallQuestion((int) questionAmount);
        List<MemberSecurityQuestion> dataList = (List<MemberSecurityQuestion>) result.getData();
        UserEnginQuestionResultVo resultVo = new UserEnginQuestionResultVo();
        resultVo.setResult(result.getCode().equals("0000"));
        resultVo.setResultDesc(result.getMsg());
        resultVo.setData((List<UserEngineQuestionVo>) BeanUtil.copyProperties(UserEngineQuestionVo.class, dataList));
        return resultVo;
    }

    @Override
    public UserEnginQuestionResultVo selectMemberSetQuestion(long memberCode) throws TException {
        ResultBean result = secureLogic.selectMemberSetQuestion(memberCode);
        List<MemberSecurityQuestion> dataList = (List<MemberSecurityQuestion>) result.getData();
        UserEnginQuestionResultVo resultVo = new UserEnginQuestionResultVo();
        resultVo.setResult(result.getCode().equals("0000"));
        resultVo.setResultDesc(result.getMsg());
        resultVo.setData((List<UserEngineQuestionVo>) BeanUtil.copyProperties(UserEngineQuestionVo.class, dataList));
        return resultVo;
    }

    @Override
    public ResultVo veryfyMemberQuestion(long memberCode, List<UserEngineQuestionVo> questions) throws TException {
        List<MemberSecurityQuestion> qlist = new ArrayList<MemberSecurityQuestion>();
        MemberSecurityQuestion q;
        for(UserEngineQuestionVo ueq: questions){
            q = new MemberSecurityQuestion();
            q.setQuestionID((int)(ueq.getQuestionID()));
            q.setAnswer(ueq.getAnswer());
            qlist.add(q);
        }
        return convert(secureLogic.veryfyMemberQuestion(memberCode,qlist));
    }

    @Override
    public ResultVo setFirstMemberQuestion(long memberCode, List<UserEngineQuestionVo> questions) throws TException {
        List<MemberSecurityQuestion> qlist = new ArrayList<MemberSecurityQuestion>();
        MemberSecurityQuestion q;
        for(UserEngineQuestionVo ueq: questions){
            q = new MemberSecurityQuestion();
            q.setQuestionID((int)(ueq.getQuestionID()));
            q.setAnswer(ueq.getAnswer());
            qlist.add(q);
        }
        return convert(secureLogic.setFirstMemberQuestion(memberCode,qlist));
    }

    @Override
    public ResultVo setFirstMemberPayPwd(long memberCode, String ciphertextPwd, String ciphertextPwdTemp, String createSource) throws TException {
        return convert(secureLogic.setFirstMemberPayPwd(memberCode, ciphertextPwd, ciphertextPwdTemp, convertStringToCreateSource(createSource)));
    }

    @Override
    public ResultVo updateMemberPayPwd(long memberCode, String ciphertextPwdOld, String ciphertextPwd, String ciphertextPwdTemp, String createSource) throws TException {
        return convert(secureLogic.updateMemberPayPwd(memberCode, ciphertextPwdOld, ciphertextPwd, ciphertextPwdTemp, convertStringToCreateSource(createSource)));
    }

    @Override
    public ResultVo resetMemberPayPwd(long memberCode, String ciphertextPwd, String ciphertextPwdTemp, String createSource) throws TException {
        return convert(secureLogic.resetMemberPayPwd(memberCode, ciphertextPwd, ciphertextPwdTemp, convertStringToCreateSource(createSource)));
    }

    @Override
    public UserEnginePageVoRes selectPageOfMemberPointsRecordHistory(String clientId, String longID, long pageSize, long pageNum, long startTime, long endTime,
            String userEnginePointsRecordType) throws TException {
        Date start = DateUtil.parse(DateUtil.DATE_TIME_FORMAT5, String.valueOf(startTime));
        Date end = DateUtil.parse(DateUtil.DATE_TIME_FORMAT5, String.valueOf(endTime));
        //查询积分列表数据
        ResultBean result = secureLogic.selectPageOfMemberPointsRecordHistory(clientId, longID, (int)pageSize, (int)pageNum, start, end, convertStringTo(userEnginePointsRecordType));
        //转换result对象
        Page<UserEnginePointsRecord> pageData = (Page<UserEnginePointsRecord>)result.getData();
        //获取结果集
        List<UserEnginePointsRecordVo> pointsRecordList = (List<UserEnginePointsRecordVo>)BeanUtil.copyProperties(UserEnginePointsRecordVo.class,  pageData.getResultsContent());
        PageVo pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, pageData);
        UserEnginePageVoRes data = new UserEnginePageVoRes();   
        data.setPage(pageVo);
        data.setPointsRecordList(pointsRecordList);

        return data;
    }
    

    @Override
    public ResultVo sendExchangeCode(String clientId, String mobile, int points, boolean useBankChannel) throws TException {
        ResultBean result = null;
        if(useBankChannel){
            result = secureLogic.sendExchangeCodeByBank(clientId, mobile, points);
        }else{
            result = secureLogic.sendExchangeCodeByPointsAPI(clientId, mobile, points);
        }
        return convert(result);
    }

    @Override
    public ResultVo verifyExchangeCode(String clientId, String mobileOrToken, String code, boolean useBankChannel) throws TException {
        ResultBean result = null;
        if(useBankChannel){
            result = secureLogic.verifyExchangeCodeOfBankChannel(clientId, mobileOrToken, code);
        }else{
            result = secureLogic.verifyExchangeCodeOfPointsAPI(clientId, mobileOrToken, code);
        }
        return convert(result);
    }
    

    private ResultVo convert(ResultBean result) {
        return new ResultVo(result.getCode(), result.getMsg());
    }

    private CreateChannel convertStringToCreateChannel(String code) {
        for (CreateChannel ch : CreateChannel.values()) {
            if (ch.getValue().equals(code)) {
                return ch;
            }
        }
        return null;
    }
    
    private CreateSource convertStringToCreateSource(String code) {
        for (CreateSource cs : CreateSource.values()) {
            if (cs.getCode().equals(code)) {
                return cs;
            }
        }
        return CreateSource.pc;
    }
    
    private BankOrg convertStringToBankOrg(String code){
        for(BankOrg bank : BankOrg.values()){
            if(bank.getBankOrg().equals(code)){
                return bank;
            }
        }
        
        return null;
    }
    
    private PointsType convertStringTo(String code){
        for (PointsType cs : PointsType.values()) {
            if (cs.getCode().equals(code)) {
                return cs;
            }
        }
        return null;
    }
	@Override
	public ResultVo deleteUserSettedQuestion(long memberCode,String clientId) throws TException {
		return convert(secureLogic.deleteUserSettedQuestion(memberCode));
	}
    
}
