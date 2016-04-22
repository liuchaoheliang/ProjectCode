package com.froad.log.vo;




public class Head {
	private HeadKey key;
	private String action;
	private String client_id;
	private Long time;
	public HeadKey getKey() {
		return key;
	}
	public void setKey(HeadKey key) {
		this.key = key;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}

	
}
