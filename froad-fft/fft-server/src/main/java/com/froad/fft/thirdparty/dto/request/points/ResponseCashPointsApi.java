package com.froad.fft.thirdparty.dto.request.points;

public class ResponseCashPointsApi {

	private PartnerAccount partnerAccount;
	
	private CashPointsInfo cashPointsInfo;
	
	private System system;

	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public CashPointsInfo getCashPointsInfo() {
		return cashPointsInfo;
	}

	public void setCashPointsInfo(CashPointsInfo cashPointsInfo) {
		this.cashPointsInfo = cashPointsInfo;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}
	
	
}
