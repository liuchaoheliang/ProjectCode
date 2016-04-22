
package com.froad.logic.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.enums.PointsType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.MemberInformationLogic;
import com.froad.support.MemberInformationSupport;
import com.froad.support.impl.MemberInformationSupportImpl;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.Client;
import com.froad.thirdparty.dto.response.pe.MemberInfo;
import com.froad.thirdparty.dto.response.pe.MemberVipInfo;
import com.froad.thirdparty.enums.ProtocolType;

public class MemberInformationLogicImpl implements MemberInformationLogic {

    private static MemberInformationSupport memberInformationSupport = new MemberInformationSupportImpl();

    @Override
    public ResultBean queryMemberInfoByMemberCode(long memberCode,String clientId) {
        ResultBean userInfo = memberInformationSupport.queryByMemberCode(memberCode,clientId);
        return checkAndInitVipInfo(userInfo,clientId);
    }

    @Override
    public ResultBean queryMemberInfoByLoginID(String loginID,String clientId) {
        if (StringUtils.isBlank(loginID)) {
            return new ResultBean(ResultCode.success, "请求参数为空");
        }
        ResultBean userInfo = memberInformationSupport.queryByLoginID(loginID);
        return checkAndInitVipInfo(userInfo,clientId);
    }
    
    
    private ResultBean checkAndInitVipInfo(ResultBean userInfo,String clientId){
        if (ResultCode.success.getCode().equals(userInfo.getCode())) {// 请求成功
            MemberInfo user = (MemberInfo) userInfo.getData();
            if (user != null) {
                ResultBean vipResult = memberInformationSupport.queryVIPInfoByMemberCode(user.getMemberCode(),clientId);
                if (ResultCode.success.getCode().equals(userInfo.getCode())){
                    user.setUserVipInfo((MemberVipInfo) vipResult.getData());
                }else{
                    LogCvt.error("查询会员VIP信息错误："+vipResult.getMsg());
                }
                return new ResultBean(ResultCode.success, "查询成功", user);
            }
        }
        LogCvt.error("查询会员信息失败：" + userInfo.toString());
        return new ResultBean(ResultCode.failed, userInfo.getMsg());
    }
    
    @Override
    public ResultBean queryMemberPointsInfoByLoginID(String clientId, String loginID) {
        if(StringUtils.isBlank(loginID)){
            return new ResultBean(ResultCode.failed, "请求参数为空");
        }
        
        return memberInformationSupport.queryUserPoints(clientId,loginID);
    }

    @Override
    public ResultBean updateMemberBindMobile(long memberCode, String mobile) {
        return memberInformationSupport.updateUserMobile(memberCode, mobile);
    }

    @Override
    public ResultBean sendSignBankCardPhoneToken(String clientId, String phone, String cardNo) {
        return memberInformationSupport.sendSignBankCardPhoneToken(clientId, phone, cardNo);
    }

    @Override
    public ResultBean sendFastPayMoblieToken(String clientId, String bankCardNo, String phone, String remark) {
        return memberInformationSupport.sendFastPayMoblieToken(clientId, bankCardNo, phone, remark);
    }

    @Override
    public ResultBean validateFilmMobile(String clientId, String filmMobile) {
        return memberInformationSupport.validateFilmMobile(clientId, filmMobile);
    }

    @Override
    public ResultBean queryPointsTradeHistory(String clientId, String userName, Date fromTime, Date toTime, ProtocolType protocolType,PointsType pointsType, Integer pageSize, Integer pageNum) {
        return memberInformationSupport.queryPointsTradeHistory(clientId, userName, fromTime, toTime, protocolType,pointsType, pageSize, pageNum);
    }

    @Override
    public ResultBean sendExchangeCheckCode(String clientId, String mobile, String points) {
        if (StringUtils.isEmpty(mobile)) {
            return new ResultBean(ResultCode.failed, "请输入手机号");
        }
        if (StringUtils.isEmpty(points)) {
            return new ResultBean(ResultCode.failed, "消费积分数为空");
        }
        return memberInformationSupport.sendExchangeCheckCode(clientId, mobile, points);
    }

    @Override
    public ResultBean queryBankPointsByMobile(String clientId, String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return new  ResultBean(ResultCode.failed,"请输入手机号");
        }
        return memberInformationSupport.queryBankPointsByMobile(clientId, mobile);
    }

	@Override
	public ResultBean queryBankPointsByBankCardNo(String clientId,String bankNo) {
		if (StringUtils.isEmpty(bankNo)){
			return new ResultBean(ResultCode.failed, "银行卡号不能为空");
		}
		
		return memberInformationSupport.queryBankPointsByBankCardNo(clientId, bankNo);
	}
	
	@Override 
	public ResultBean payPointsByMobile(String clientId,String orderId,String payReason,String remark,String outletOrgNo,String loginId,int points,String bankNo){
		return memberInformationSupport.payPointsByMobile(clientId, orderId, payReason,remark,outletOrgNo, loginId, points,bankNo);
	}
	@Override 
	public ResultBean processVIPOrder(Long memeberCode, String level, Integer availableDays, Long labelID, Integer bankLevel){
		if(memeberCode==null){
			return new ResultBean(ResultCode.failed,"会员编号不能为空");
		}
		if(StringUtils.isEmpty(level)){
			return new ResultBean(ResultCode.failed,"会员等级CODE不能为空");
		}
		if(availableDays==null|| availableDays==0){
			availableDays = 365;
		}
		
		if(labelID==null){
			return new ResultBean(ResultCode.failed,"请输入会员标签ID");
		}
		
		if(bankLevel==null){
			return new ResultBean(ResultCode.failed,"请输入会员标签ID的等级");
		}
		
		return memberInformationSupport.processVIPOrder(memeberCode, level, availableDays, labelID, bankLevel);
	}
	@Override 
	public ResultBean queryMemberVIPLabelByClientID(String clientId){
		return memberInformationSupport.queryMemberVIPLabelByClientID(clientId);
	}

	@Override
	public ResultBean employeeCodeVerify(String clientId, String verifyOrg, String employeeCode, String password,String clientNo) {
		if(StringUtils.isEmpty(verifyOrg)){
			return new ResultBean(ResultCode.failed,"验证机构不能为空");
		}
		if(StringUtils.isEmpty(employeeCode)){
			return new ResultBean(ResultCode.failed,"员工号不能为空");
		}
		if(StringUtils.isEmpty(password)){
			return new ResultBean(ResultCode.failed,"密码不能为空");
		}
		if(StringUtils.isEmpty(clientNo)){
			return new ResultBean(ResultCode.failed,"客户端类型不能为空");
		}
		Client client = null;
		Client[] clints = Client.values();
		for (Client c : clints) {
			if(c.getCode().equals(clientNo)){
				client = c;
				break;
			}
		}
		if(client == null){
			return new ResultBean(ResultCode.failed,"客户端类型指定无效");
		}
		return memberInformationSupport.employeeCodeVerify(clientId, verifyOrg, employeeCode, password, client);
	}
}
