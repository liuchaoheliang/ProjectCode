/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.froad.po;

public class CheckVIPExistWhiteListReq {
	/**
	 * 源对象信息
	 */
	private Origin origin; // required
	/**
	 * 会员编号
	 */
	private Long memberCode; // required

	public CheckVIPExistWhiteListReq() {
		super();
	}

	public CheckVIPExistWhiteListReq(Origin origin, Long memberCode) {
		super();
		this.origin = origin;
		this.memberCode = memberCode;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

}
