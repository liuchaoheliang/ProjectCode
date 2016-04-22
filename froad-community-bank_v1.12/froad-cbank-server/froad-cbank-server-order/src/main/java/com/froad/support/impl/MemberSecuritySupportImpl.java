package com.froad.support.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.froad.common.beans.ResultBean;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.PaymentChannel;
import com.froad.enums.PointsType;
import com.froad.enums.ResultCode;
import com.froad.enums.SmsType;
import com.froad.logback.LogCvt;
import com.froad.logic.SmsLogic;
import com.froad.logic.impl.SmsLogicImpl;
import com.froad.po.SmsLog;
import com.froad.support.MemberInformationSupport;
import com.froad.support.MemberSecuritySupport;
import com.froad.support.RedisDataAccessSupport;
import com.froad.thirdparty.bean.QueryInfoDto;
import com.froad.thirdparty.common.PointsCommand;
import com.froad.thirdparty.common.RespCodeCommand;
import com.froad.thirdparty.common.ThirdPartyConfig;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.request.points.UserEnginePointsRecord;
import com.froad.thirdparty.dto.response.pe.MemberInfo;
import com.froad.thirdparty.dto.response.pe.MemberSecurityQuestion;
import com.froad.thirdparty.dto.response.pe.MemberVipInfo;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.thirdparty.exception.AppException;
import com.froad.thirdparty.request.points.PointsApiFunc;
import com.froad.thirdparty.request.points.impl.PointsApiFuncImpl;
import com.froad.thirdparty.request.userengine.UserEngineFunc;
import com.froad.thirdparty.request.userengine.impl.UserEngineFuncImpl;
import com.froad.thirdparty.util.CheckCodeUtils;
import com.froad.thirdparty.util.DateUtil;
import com.froad.thrift.service.SmsLogService;
import com.froad.thrift.vo.SmsLogVo;
import com.froad.thrift.vo.SmsMessageResponseVo;
import com.froad.thrift.vo.SmsMessageVo;
import com.froad.util.BeanUtil;
import com.froad.util.PropertiesUtil;
import com.pay.pe.dto.AccountResult;
import com.pay.pe.dto.QuestionSpecDto;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserResult;
import com.pay.user.helper.BankOrg;


public class MemberSecuritySupportImpl implements MemberSecuritySupport{
	
	
    private static final UserEngineFunc userEngineFunc = new UserEngineFuncImpl();
    private static final PointsApiFunc pointsFunc = new PointsApiFuncImpl();
    private static final MemberInformationSupport memberSupport = new MemberInformationSupportImpl();
    private static final RedisDataAccessSupport redisSupport = new RedisDataAccessSupportImpl();
    private static final SmsLogic smsLogic = new SmsLogicImpl();
    private static final String THRIFT_PROPERTIES_FILE = "thrift";

    /**
     * properties host key
     */
    private static final String PROPERTIES_HOST_KEY = "thrift.sms.host";
    
    /**
     * properties port key
     */
    private static final String PROPERTIES_PORT_KEY = "thrift.sms.port";
    
    private SmsLogService.Iface smsLogService;
    
    @Override
    public ResultBean updateMemberPwd(Long memberCode, String oldPwd, String newPwd) {
        UserResult userResult = userEngineFunc.updateMemberPwd(memberCode, oldPwd, newPwd);
        if(userResult.getResult()){
            return new ResultBean(ResultCode.success, "密码修改成功");
        }
        return new ResultBean(ResultCode.failed, userResult.getErrorMsg());
    }

    @Override
    public ResultBean resetUserPwd(Long memberCode, String newPwd) {
        UserResult userResult = userEngineFunc.resetUserPwd(memberCode, newPwd);
        if(userResult.getResult()){
            return new ResultBean(ResultCode.success, "密码重置成功");
        }
        return new ResultBean(ResultCode.failed, userResult.getErrorMsg());
    }

    @Override
    public ResultBean loginUnion(String mobile, String idnetifyNo, String userBankId, String createChannel, BankOrg bankOrg, String identifyType ,String clientId) {
        Result result = userEngineFunc.loginUnion(mobile, idnetifyNo, userBankId, createChannel, bankOrg,identifyType);
        if (result.getResult()&&result.getData()!=null) {// 请求成功
            MemberInfo user = (MemberInfo) BeanUtil.copyProperties(MemberInfo.class, result.getData());
            if (user != null) {
                ResultBean vipResult = memberSupport.queryVIPInfoByMemberCode(user.getMemberCode(),clientId);
                if (ResultCode.success.getCode().equals(vipResult.getCode())){
                    user.setUserVipInfo((MemberVipInfo) vipResult.getData());
                }else{
                    LogCvt.error("查询会员VIP信息错误："+vipResult.getMsg());
                }
                return new ResultBean(ResultCode.success, "查询成功", user);
            }
        }
        LogCvt.error("查询会员信息失败");
        return new ResultBean(ResultCode.failed, result.getMessage());
    }

    @Override
    public ResultBean isPayPasswordExists(Long memberCode) {
        AccountResult accountResult = userEngineFunc.isUserSetPayPwd(memberCode);
        return checkResult(accountResult, "该账户已设置支付密码");
    }

    @Override
    public ResultBean verifyPayPassword(Long memberCode,String pwd) {
        AccountResult accountResult = userEngineFunc.verifyPayPwd(memberCode, pwd);
        return checkResult(accountResult, "支付密码校验成功");
    }

    @Override
    public ResultBean isSafeQuestionExists(Long memberCode) {
        AccountResult accountResult = userEngineFunc.isUserSetQuestion(memberCode);
        return checkResult(accountResult, "该用户已设置安全问题");
    }

    @Override
    public ResultBean selectPreinstallQuestion(int questionAmount) {
        AccountResult accountResult = userEngineFunc.selectPreinstallQuestion(questionAmount);
        return checkResult(accountResult, "查询成功",BeanUtil.copyProperties(MemberSecurityQuestion.class, accountResult.getQuestionSpecDtos()));
    }

    @Override
    public ResultBean selectMemberSetQuestion(Long memberCode) {
        AccountResult accountResult = userEngineFunc.selectMemberSetQuestion(memberCode);
        return checkResult(accountResult,"查询安全问题成功", BeanUtil.copyProperties(MemberSecurityQuestion.class, accountResult.getQuestionSpecDtos()));
    }

    @Override
    public ResultBean veryfyMemberQuestion(Long memberCode, List<MemberSecurityQuestion> questions) {
        List<QuestionSpecDto> questionSpecDtos = (List<QuestionSpecDto>) BeanUtil.copyProperties(QuestionSpecDto.class,questions);
        AccountResult accountResult = userEngineFunc.veryfyMemberQuestion(memberCode, questionSpecDtos);
        if(accountResult.getResult()){
            return new ResultBean(ResultCode.success, "安全问题验证成功");
        }else{
//        	String data = accountResult.getDemo();
//        	String msg = null;
//        	if(!StringUtils.isEmpty(data)){
//        		try {
//					JSONObject m = JSONObject.parseObject(data);
//					msg = m.getString("answerError");
//				} catch (Exception e) {
//				}
//        	}
        	return new ResultBean(ResultCode.failed,accountResult.getDemo());
//        	return new ResultBean(ResultCode.failed,accountResult.getErrorMsg());
        }
    }

    @Override
    public ResultBean setUserPayPwd(Long memberCode, String payPwdTemp) {
        AccountResult accountResult = userEngineFunc.setUserPayPwd(memberCode, payPwdTemp);
        return checkResult(accountResult, "支付密码设置成功");
    }

    @Override
    public ResultBean setMemberQuestion(Long memberCode, List<MemberSecurityQuestion> questions) {
        List<QuestionSpecDto> questionSpecDtos = (List<QuestionSpecDto>) BeanUtil.copyProperties(QuestionSpecDto.class,questions);
        AccountResult accountResult = userEngineFunc.setUserQuestion(memberCode, questionSpecDtos);
        return checkResult(accountResult, "安全问题设置成功");
    }
    

    private ResultBean checkResult(AccountResult accountResult,String successMsg,Object...data) {
        if(accountResult.getResult()){
            return new ResultBean(ResultCode.success, successMsg,data!=null&&data.length>0?data[0]:null);
        }
        return new ResultBean(ResultCode.failed, accountResult.getErrorMsg());
    }

    @Override
    public ResultBean selectPageOfMemberPointsRecordHistory(String clientId, String longID, Integer pageSize, Integer pageNum, Date startTime, Date endTime,
            PointsType userEnginePointsRecordType) {
        String pointsOrg = PointsType.bankPoints.equals(userEnginePointsRecordType) ? redisSupport.acquirePaymentOrgNoByType(clientId, PaymentChannel.bank_point.getCode())
                : PointsCommand.FROAD_ORG_NO;
        
        PointsReq req = new PointsReq(redisSupport.acquirePointPartnerNo(clientId), pointsOrg, longID, "", null,// ProtocolType.consumPoints,
                pageSize, pageNum, DateUtil.formatDate2Str(startTime, DateUtil.C_TIME_PATTON_DEFAULT) , DateUtil.formatDate2Str(endTime, DateUtil.C_TIME_PATTON_DEFAULT));
        PointsRes res = pointsFunc.getMyPointsTrans(req);
        LogCvt.info("查询用户消耗历史积分,请求参数: " + req + " 请求结果: " + JSONObject.toJSONString(res));
        if (PointsCommand.RESP_CODE_SUCCESS.equals(res.getResultCode())) {
            
            Page<UserEnginePointsRecord> pageDto = new Page<UserEnginePointsRecord>();
            pageDto.setPageNumber(pageNum);
            pageDto.setPageSize(pageSize);
            QueryInfoDto queryInfoDto = res.getQueryInfo();
            pageDto.setTotalCount(Integer.parseInt(queryInfoDto.getTotalCount()));
            pageDto.setPageCount(Integer.parseInt(queryInfoDto.getTotalPageNum()));
            pageDto.setResultsContent((List<UserEnginePointsRecord>) BeanUtil.copyProperties(UserEnginePointsRecord.class, res.getProtocolInfos()));
            return new ResultBean(ResultCode.success, "查询成功", pageDto);
        } else {
            return new ResultBean(ResultCode.failed, res.getResultDesc());
        }
    }

    @Override
    public ResultBean sendExchangeCodeByPointsAPI(String clientId, String mobile, Integer points) {
        String bankOrgNo = redisSupport.acquirePaymentOrgNoByType(clientId, PaymentChannel.bank_point.getCode());
        String partnerNo = redisSupport.acquirePointPartnerNo(clientId);
        PointsReq pointsReq = new PointsReq();
        pointsReq.setAccountMarked(mobile);
        pointsReq.setOrgNo(bankOrgNo);
        pointsReq.setPoints(points.toString());
        pointsReq.setPartnerNo(partnerNo);
        String msg;
        try {
            PointsRes res = pointsFunc.sendCheckCode(pointsReq);
            if (RespCodeCommand.SUCCESS.equals(res.getResultCode())) {
                return new ResultBean(ResultCode.success);
            }
            msg = res.getResultDesc();
        } catch (AppException e) {
            LogCvt.error("发送消费积分的验证码异常，手机号：" + mobile, e);
            msg = e.getMessage();
        }
        return new ResultBean(ResultCode.failed, msg);
    }

    @Override
    public ResultBean sendExchangeCodeByBank(String clientId, String mobile, Integer points) {
        String code = CheckCodeUtils.makeCheckCode(0);
        SmsMessageVo sms = new SmsMessageVo();
        sms.setMobile(mobile);
        sms.setClientId(clientId);
        sms.setValidTime(ThirdPartyConfig.EXCHANGE_CODE_EXPIRE_TIME);
        sms.setCode(code);
        sms.setSmsType(SmsType.offPointsBankSecurityCode.getCode());
        sms.setValues( Arrays.asList(points.toString(),code));
        ResultBean result = smsLogic.sendSMS(sms);
        if(ResultCode.success.getCode().equals(result.getCode())){
            SmsMessageResponseVo vo = (SmsMessageResponseVo) result.getData();
            return new ResultBean(ResultCode.success,result.getMsg(),vo.getToken());
        }else{
            return new ResultBean(ResultCode.failed,result.getMsg(),result.getMsg());
        }
    }

    @Override
    public ResultBean verifyExchangeCodeOfPointsAPI(String clientId, String mobile, String code) {
        String bankOrgNo = redisSupport.acquirePaymentOrgNoByType(clientId, PaymentChannel.bank_point.getCode());
        String partnerNo = redisSupport.acquirePointPartnerNo(clientId);
        PointsReq pointsReq = new PointsReq();
        pointsReq.setAccountMarked(mobile);
        pointsReq.setCheckCode(code);
        pointsReq.setOrgNo(bankOrgNo);
        pointsReq.setPartnerNo(partnerNo);
        PointsRes res = null;
        try {
            res = pointsFunc.validateCheckCode(pointsReq);
            if (RespCodeCommand.SUCCESS.equals(res.getResultCode())) {
            	return new ResultBean(ResultCode.success, "成功");
            }
            return new ResultBean(ResultCode.failed, res.getResultDesc());
        } catch (AppException e) {
            LogCvt.error("校验验证码异常", e);
            return new ResultBean(ResultCode.failed, e.getMessage());
        }
    }

    @Override
    public ResultBean verifyExchangeCodeOfBankChannel(String clientId, String token, String code) {
        if(needConnectSmsLogService()){
            connectSmsLogService();
        }
        SmsLogVo smsLog = new SmsLogVo();
        smsLog.setToken(token);
        List<SmsLogVo> list = null;
        try {
            smsLogService.getSmsLog(smsLog);
        } catch (TException e) {
            LogCvt.error("校验时获取验证码信息失败:"+JSONObject.toJSONString(smsLog));
            return new ResultBean(ResultCode.failed,"校验时获取验证码信息失败");
        }
        if(list == null || list.size() == 0){
            return new ResultBean(ResultCode.failed, "短信验证码错误");
        }
        smsLog = list.get(0);
        boolean isUsed = smsLog.isUsed;
        
        //重复验证
        if(isUsed){
            LogCvt.info("该验证码已校验");
            return new ResultBean(ResultCode.failed, "短信验证码错误");
        }
        Date expireTime = new Date(smsLog.expireTime);
        String saveCode = smsLog.getCode();
        if(new Date().before(expireTime)){
            if(saveCode.equals(code)){
                SmsLog temp = new SmsLog();
                temp.setId(smsLog.getId());
                temp.setIsUsed(true);
                try {
                    smsLogService.updateSmsLog1(smsLog);
                } catch (TException e) {
                    LogCvt.error("更新验证码状态失败:"+JSONObject.toJSONString(smsLog));
                    return new ResultBean(ResultCode.failed,"更新验证码状态失败");
                }
                return new ResultBean(ResultCode.success, "校验成功");
            }else{
                return new ResultBean(ResultCode.failed, "短信验证码错误");
            }
        }else{
            return new ResultBean(ResultCode.failed, "验证码已过期，请重新获取");
        }
    }
    
    public boolean needConnectSmsLogService(){
        return this.smsLogService==null;
    }
    
    private boolean connectSmsLogService(){
        TTransport transport = null;
        TProtocol protocol = null;
        TMultiplexedProtocol multiProtocol = null;
        try {
            String host = PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_HOST_KEY);
            int port = Integer.valueOf(PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_PORT_KEY));
            
            // 设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            // 设置传输协议为 TBinaryProtocol 
            protocol = new TBinaryProtocol(transport); 
            multiProtocol = new TMultiplexedProtocol(protocol,SmsLogService.class.getSimpleName());
            this.smsLogService = new SmsLogService.Client(multiProtocol);
            return true;
        } catch (Exception e) {
            LogCvt.error("调用短信日志接口异常",e);
        }  finally {
            IOUtils.closeQuietly(transport);
        }
        return false;
    }

	@Override
	public ResultBean deleteUserSettedQuestion(long memberCode) {
		AccountResult result = userEngineFunc.deleteUserSettedQuestion(memberCode);
		if(result.getResult()){
			return new ResultBean(ResultCode.success);
		}
		return new ResultBean(ResultCode.failed,result.getErrorMsg());
	}
}
