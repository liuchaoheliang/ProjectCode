package com.froad.thirdparty.bean;

import java.io.Serializable;

import com.froad.thirdparty.enums.ProtocolType;

public class TotalPointsInfosDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProtocolType protocolType;

	private String totalPoints;

	public ProtocolType getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(ProtocolType protocolType) {
		this.protocolType = protocolType;
	}

	public String getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}
}
