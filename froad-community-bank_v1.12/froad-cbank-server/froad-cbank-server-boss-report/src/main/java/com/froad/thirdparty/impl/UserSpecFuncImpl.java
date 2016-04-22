package com.froad.thirdparty.impl;

import java.util.Date;
import java.util.List;

import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.logback.LogCvt;
import com.froad.thirdparty.UserSpecFunc;
import com.froad.util.DateUtil;
import com.froad.util.HessianUrlUtil;
import com.froad.util.JSonUtil;
import com.pay.user.dto.MemberSignSpecDto;
import com.pay.user.dto.Page;
import com.pay.user.dto.Result;
import com.pay.user.dto.VIPSpecDto;
import com.pay.user.helper.BankOrg;
import com.pay.user.helper.ClientChannel;
import com.pay.user.service.UserSpecService;
import com.pay.user.service.VIPSpecService;

public class UserSpecFuncImpl implements UserSpecFunc {

	private static HessianProxyFactory factory = new HessianProxyFactory();

	@Override
	public Result queryVIPSpecList(Date beginTime, Date endTime,
			BankOrg bankOrg, String labelId, ClientChannel clientChannel,
			int pageSize, int pageNum) {
		Result result = null;
		try {
			result = ((VIPSpecService) factory.create(VIPSpecService.class, HessianUrlUtil.VIP_SPEC_URL)).queryVIPSpecList(beginTime, endTime, bankOrg, labelId, clientChannel, pageSize, pageNum);
		} catch (Exception e) {
			LogCvt.error("hessian连接异常", e);
		}
		return result;
	}
	

	@Override
	public Result queryMemberByCardBin(String cardBin, String bankId, Date begDate, Date endDate) {
		Result result = null;
		try {
			result = ((UserSpecService)factory.create(UserSpecService.class, HessianUrlUtil.USER_URL)).queryMemberByCardBin(cardBin, bankId, begDate, endDate);
		} catch (Exception e) {
			LogCvt.error("hessian连接异常", e);
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		UserSpecFuncImpl v = new UserSpecFuncImpl();
		Date beginTime = new Date(0l);
		Date endTime = new Date();
//		
//		Result result = v.queryVIPSpecList(beginTime, endTime, BankOrg.BANK_AH, "340000", null, 1000, 1);
//		System.out.println(JSonUtil.toJSonString((Page<VIPSpecDto>)result.getData()));
		
//		for(VIPSpecDto vs : page.getResult()){
//			System.out.println(JSonUtil.toJSonString(vs));
//		}
		
		
		Result result = v.queryMemberByCardBin("01", BankOrg.BANK_AH.getBankID(), beginTime, endTime);
		List<MemberSignSpecDto> signs = (List<MemberSignSpecDto>) result.getData();
		for(MemberSignSpecDto sign : signs){
			System.out.println("memberCode:"+sign.getMemberCode()+", loginId:"+sign.getLoginID()+", username:"+sign.getCardHostName());
		}
	}
}
