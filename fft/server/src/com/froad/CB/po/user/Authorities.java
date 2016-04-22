package com.froad.CB.po.user;

import java.io.Serializable;


	/**
	 * 类描述：用户授权实体类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:48:33 PM 
	 */
public class Authorities implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -2949627482487024488L;
	private String authorities_id;
	private String authorities_name;
	private String p_authorities_id;
	private String p_authorities_name;

	public String getAuthorities_id() {
		return authorities_id;
	}

	public void setAuthorities_id(String authorities_id) {
		this.authorities_id = authorities_id;
	}

	public String getAuthorities_name() {
		return authorities_name;
	}

	public void setAuthorities_name(String authorities_name) {
		this.authorities_name = authorities_name;
	}

	public String getP_authorities_id() {
		return p_authorities_id;
	}

	public void setP_authorities_id(String p_authorities_id) {
		this.p_authorities_id = p_authorities_id;
	}

	public String getP_authorities_name() {
		return p_authorities_name;
	}

	public void setP_authorities_name(String p_authorities_name) {
		this.p_authorities_name = p_authorities_name;
	}
}
