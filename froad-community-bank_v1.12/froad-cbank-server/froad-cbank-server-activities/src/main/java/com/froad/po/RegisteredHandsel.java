/**
 * @Title: RegisteredHandsel.java
 * @Package com.froad.po
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月8日
 * @version V1.0
 **/

package com.froad.po;

import java.io.Serializable;

import com.froad.thrift.vo.OriginVo;

/**
 * @ClassName: RegisteredHandsel
 * @Description: TODO
 * @author froad-Joker 2015年12月8日
 * @modify froad-Joker 2015年12月8日
 */

public class RegisteredHandsel implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	
	
	private static final long serialVersionUID = 1L;
	private OriginVo originVo;
	private String clientId;
	private Long memberCode;
	private String loginId;

	public OriginVo getOriginVo() {
		return originVo;
	}

	public void setOriginVo(OriginVo originVo) {
		this.originVo = originVo;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

}
