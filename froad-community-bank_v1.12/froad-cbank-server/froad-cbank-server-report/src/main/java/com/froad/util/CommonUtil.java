package com.froad.util;

import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.ReportBankOrg;
import com.froad.po.ReportBankUser;

public class CommonUtil {
	
	/**
	 * 根据机构号等级设置ReportBankUser
	 * @Title: setOrgCodeByLevel 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月5日
	 * @modify: froad-huangyihao 2015年6月5日
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @throws
	 */
	public static ReportBankUser setOrgCodeByLevel(String clientId, String orgCode) throws FroadBusinessException {
		CommonLogic commonLogic = new CommonLogicImpl();
		OrgLevelEnum level = commonLogic.getOrgLevelByOrgCode(orgCode, clientId);
		if(Checker.isEmpty(level)){
			throw new FroadBusinessException(ResultCode.failed.getCode(), "机构信息不存在");
		}
		ReportBankUser user = new ReportBankUser();
		user.setClientId(clientId);
		switch (level) {
		case orgLevel_one:
			user.setForgCode(orgCode);
			break;
		case orgLevel_two:
			user.setSorgCode(orgCode);
			break;
		case orgLevel_three:
			user.setTorgCode(orgCode);
			break;
		case orgLevel_four:
			user.setLorgCode(orgCode);
			break;
		default:
			throw new FroadBusinessException(ResultCode.failed.getCode(), "机构信息不存在");
		}
		return user;
	}
	
	/**
	 * 根据机构号等级设置BankJgm
	 * @Title: setJgmByLevel 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月15日
	 * @modify: froad-huangyihao 2015年6月15日
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @throws FroadBusinessException
	 * @throws
	 */
	public static ReportBankOrg setJgmByLevel(String clientId, String orgCode) throws FroadBusinessException {
		CommonLogic commonLogic = new CommonLogicImpl();
		OrgLevelEnum level = commonLogic.getOrgLevelByOrgCode(orgCode, clientId);
		if(Checker.isEmpty(level)){
			throw new FroadBusinessException(ResultCode.failed.getCode(), "机构信息不存在");
		}
		ReportBankOrg bj = new ReportBankOrg();
		bj.setClientId(clientId);
		switch (level) {
		case orgLevel_one:
			bj.setForgCode(orgCode);
			break;
		case orgLevel_two:
			bj.setSorgCode(orgCode);
			break;
		default:
			throw new FroadBusinessException(ResultCode.failed.getCode(), "该机构无权限");
		}
		return bj;
	}
	
}
