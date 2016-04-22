package com.froad.po;

import java.io.Serializable;

public class OrderProductAllCutMoney implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 普通单个满减金额 */
	private Double generalSingleCutMoney;
    /** 普通最后满减金额 */
	private Double generalAtLastCutMoney;
    /** VIP单个满减金额 */
	private Double vipSingleCutMoney;
    /** VIP最后满减金额 */
	private Double vipAtLastCutMoney;
	
	public Double getGeneralSingleCutMoney() {
		return generalSingleCutMoney;
	}
	public void setGeneralSingleCutMoney(Double generalSingleCutMoney) {
		this.generalSingleCutMoney = generalSingleCutMoney;
	}
	public Double getGeneralAtLastCutMoney() {
		return generalAtLastCutMoney;
	}
	public void setGeneralAtLastCutMoney(Double generalAtLastCutMoney) {
		this.generalAtLastCutMoney = generalAtLastCutMoney;
	}
	public Double getVipSingleCutMoney() {
		return vipSingleCutMoney;
	}
	public void setVipSingleCutMoney(Double vipSingleCutMoney) {
		this.vipSingleCutMoney = vipSingleCutMoney;
	}
	public Double getVipAtLastCutMoney() {
		return vipAtLastCutMoney;
	}
	public void setVipAtLastCutMoney(Double vipAtLastCutMoney) {
		this.vipAtLastCutMoney = vipAtLastCutMoney;
	}

}
