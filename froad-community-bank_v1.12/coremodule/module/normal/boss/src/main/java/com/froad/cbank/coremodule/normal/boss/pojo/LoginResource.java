package com.froad.cbank.coremodule.normal.boss.pojo;

import java.util.List;

public class LoginResource{
	
	private Long id;
	private Long pid;
	private String name;
	private String url;
	private String icon;
	private List<LoginResource> sublist;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<LoginResource> getSublist() {
		return sublist;
	}
	public void setSublist(List<LoginResource> sublist) {
		this.sublist = sublist;
	}
	
	
}
