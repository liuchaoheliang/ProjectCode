package com.froad.cbank.coremodule.module.normal.bank.vo.report;

/**
 * 用户指标
 * @author wufei
 *
 */
public class MemberQuotaResp { 

	private Long memberCount;  //消费用户数
	
	private Long memberComulationSum; //累计消费用户数

	public Long getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Long memberCount) {
		this.memberCount = memberCount;
	}

	public Long getMemberComulationSum() {
		return memberComulationSum;
	}

	public void setMemberComulationSum(Long memberComulationSum) {
		this.memberComulationSum = memberComulationSum;
	}

	

	
	
	
}
