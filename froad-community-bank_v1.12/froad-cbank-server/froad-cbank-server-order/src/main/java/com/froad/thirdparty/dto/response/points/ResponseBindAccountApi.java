package com.froad.thirdparty.dto.response.points;

import com.froad.thirdparty.dto.request.points.PartnerAccount;

public class ResponseBindAccountApi {

	private PartnerAccount partnerAccount;
	private BindAccountInfo bindAccountInfo;
	private System system;

	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public BindAccountInfo getBindAccountInfo() {
		return bindAccountInfo;
	}

	public void setBindAccountInfo(BindAccountInfo bindAccountInfo) {
		this.bindAccountInfo = bindAccountInfo;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

}
