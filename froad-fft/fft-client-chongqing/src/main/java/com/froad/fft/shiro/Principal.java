package com.froad.fft.shiro;

import java.io.Serializable;

/**
 * 身份信息
 * @author FQ
 *
 */
public class Principal implements Serializable {
	
	private Long id;//ID
	private String username;//用户名
	
	/**
	 * @param id ID
	 * @param username 用户名
	 */
	public Principal(Long id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return username;
	}
	
}
