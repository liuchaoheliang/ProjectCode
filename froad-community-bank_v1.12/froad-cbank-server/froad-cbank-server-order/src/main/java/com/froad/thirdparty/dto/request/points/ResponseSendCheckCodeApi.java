package com.froad.thirdparty.dto.request.points;

/**
 * 类描述：发送验证码的完整响应
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: 2014年10月3日 上午9:53:49
 */
public class ResponseSendCheckCodeApi {

	private PartnerAccount partnerAccount;

	private CheckResult checkResult;

	private System system;

	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public CheckResult getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(CheckResult checkResult) {
		this.checkResult = checkResult;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}
}
