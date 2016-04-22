package com.froad.fft.thirdparty.dto.request.points;

public class ResponseRefundPointsApi {

	private PartnerAccount partnerAccount;

	private RefundPoints refundPoints;
	
	private System system;

	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public RefundPoints getRefundPoints() {
		return refundPoints;
	}

	public void setRefundPoints(RefundPoints refundPoints) {
		this.refundPoints = refundPoints;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

	
}
