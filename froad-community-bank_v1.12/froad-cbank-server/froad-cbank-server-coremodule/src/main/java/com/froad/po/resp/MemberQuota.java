package com.froad.po.resp;

/**
 * 用户指标
 * @author wufei
 *
 */
public class MemberQuota { 

	private Long consumptionMemberCount;  //消费用户数
	
	private Long consumptionMemberComulation; //累计消费用户数

	public Long getConsumptionMemberCount() {
		return consumptionMemberCount;
	}

	public void setConsumptionMemberCount(Long consumptionMemberCount) {
		this.consumptionMemberCount = consumptionMemberCount;
	}

	public Long getConsumptionMemberComulation() {
		return consumptionMemberComulation;
	}

	public void setConsumptionMemberComulation(Long consumptionMemberComulation) {
		this.consumptionMemberComulation = consumptionMemberComulation;
	}

	
	
	
}
