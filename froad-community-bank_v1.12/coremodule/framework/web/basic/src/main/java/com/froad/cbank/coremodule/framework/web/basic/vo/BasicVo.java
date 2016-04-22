package com.froad.cbank.coremodule.framework.web.basic.vo;

import java.io.Serializable;


public abstract class BasicVo implements Serializable{
	/**
	 * @Title serialVersionUID
	 * @type long
	 * @Create Author: hjz
	 * @date 2015年4月11日 下午4:07:56
	 * 含义 TODO
	 */
	private static final long serialVersionUID = 5103796910819657638L;
	
	private String clientId = "";
	private Long memberCode = 0L;
	
	/**
	 * 当前页
	 * @Title pageNumber
	 * @type int
	 * @Create Author: hjz
	 * @date 2015年4月13日 下午1:46:01
	 * 含义 TODO
	 */
	private int pageNumber;
	
	/**
	 * 查询条数
	 * @Title pageSize
	 * @type int
	 * @Create Author: hjz
	 * @date 2015年4月13日 下午1:46:08
	 * 含义 TODO
	 */
	private int pageSize;
	
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
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
