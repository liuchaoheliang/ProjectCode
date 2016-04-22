package com.froad.thirdparty.dto.request.points;

public class ResponseFillPointsApi {

	private PartnerAccount partnerAccount;
	
	private System system;
	
	private OrgMember orgMember;
	
	private FillPointsInfo fillPointsInfo;

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

	public OrgMember getOrgMember() {
		return orgMember;
	}

	public void setOrgMember(OrgMember orgMember) {
		this.orgMember = orgMember;
	}

	public FillPointsInfo getFillPointsInfo() {
		return fillPointsInfo;
	}

	public void setFillPointsInfo(FillPointsInfo fillPointsInfo) {
		this.fillPointsInfo = fillPointsInfo;
	}
	
	
}
