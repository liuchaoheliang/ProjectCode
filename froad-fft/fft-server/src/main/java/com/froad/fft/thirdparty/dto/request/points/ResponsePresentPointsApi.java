package com.froad.fft.thirdparty.dto.request.points;

public class ResponsePresentPointsApi {
	private PartnerAccount partnerAccount;

	private PresentPointsInfo presentPointsInfo;
	
	private System system;

	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public PresentPointsInfo getPresentPointsInfo() {
		return presentPointsInfo;
	}

	public void setPresentPointsInfo(PresentPointsInfo presentPointsInfo) {
		this.presentPointsInfo = presentPointsInfo;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}
	
	
}
