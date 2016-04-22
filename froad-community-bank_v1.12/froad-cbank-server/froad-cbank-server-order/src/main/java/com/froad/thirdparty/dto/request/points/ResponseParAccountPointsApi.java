package com.froad.thirdparty.dto.request.points;

import java.util.List;

public class ResponseParAccountPointsApi {

	private String orgNo;// 积分机构编号
	private PartnerAccount partnerAccount;
	private List<PointsAccount> accountPointsInfos;
	private System system;

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public List<PointsAccount> getAccountPointsInfos() {
		return accountPointsInfos;
	}

	public void setAccountPointsInfos(List<PointsAccount> accountPointsInfos) {
		this.accountPointsInfos = accountPointsInfos;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}
}
