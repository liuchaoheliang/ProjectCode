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

import java.util.List;

public class RemoveVIPWhiteListReq {
	/**
	 * 源对象信息
	 */
	private Origin origin; // required
	/**
	 * 会员编号
	 */
	private List<Long> memberCode; // required

	public RemoveVIPWhiteListReq() {
		super();
	}

	public RemoveVIPWhiteListReq(Origin origin, List<Long> memberCode) {
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

	public List<Long> getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(List<Long> memberCode) {
		this.memberCode = memberCode;
	}

}
