package com.froad.po;

import java.io.Serializable;

public class FullGiveActive implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 活动id */
    private String activeId;
    /** 活动类型 1红包 2联盟积分 */
    private String activeType;
    /** 金额值 */
    private Double monry;
    
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getActiveType() {
		return activeType;
	}
	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}
	public Double getMonry() {
		return monry;
	}
	public void setMonry(Double monry) {
		this.monry = monry;
	}
    
    
}
