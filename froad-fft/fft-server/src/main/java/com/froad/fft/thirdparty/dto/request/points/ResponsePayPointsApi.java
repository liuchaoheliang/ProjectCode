package com.froad.fft.thirdparty.dto.request.points;

public class ResponsePayPointsApi {

	private PartnerAccount partnerAccount;

	private PayPoints payPoints;
	
	private System system;

	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

	public PayPoints getPayPoints() {
		return payPoints;
	}

	public void setPayPoints(PayPoints payPoints) {
		this.payPoints = payPoints;
	}
	
	
}
