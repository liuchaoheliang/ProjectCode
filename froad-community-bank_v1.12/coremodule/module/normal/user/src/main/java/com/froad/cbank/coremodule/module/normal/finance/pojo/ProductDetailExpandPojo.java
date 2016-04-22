package com.froad.cbank.coremodule.module.normal.finance.pojo;

/**   
 * 商品详情扩展信息
 * @author liuhuangle@f-road.com.cn   
 * com.froad.cbank.coremodule.module.normal.finance.pojo
 * @date 2015-6-12 下午5:31:06
 */
public class ProductDetailExpandPojo {
	
	/** 产品销售代理行机构名称 **/
	private String agentOrgName;
	/** 当前用户签约状态 **/
	private int signBankInfo; 
	/** 当前用户可购买额度**/
	private double canBuyLimit;
	/** 风险评估等级**/
	private int inRiskLevel;
	
	public String getAgentOrgName() {
		return agentOrgName;
	}
	public void setAgentOrgName(String agentOrgName) {
		this.agentOrgName = agentOrgName;
	}
	public int getSignBankInfo() {
		return signBankInfo;
	}
	public void setSignBankInfo(int signBankInfo) {
		this.signBankInfo = signBankInfo;
	}
	public double getCanBuyLimit() {
		return canBuyLimit;
	}
	public void setCanBuyLimit(double canBuyLimit) {
		this.canBuyLimit = canBuyLimit;
	}
	public int getInRiskLevel() {
		return inRiskLevel;
	}
	public void setInRiskLevel(int inRiskLevel) {
		this.inRiskLevel = inRiskLevel;
	}
}
