package com.froad.thirdparty.bean;

import java.io.Serializable;

public class QueryInfoDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pageNum;
	private String totalPageNum;
	private String pageSize;
	private String totalCount;
	private String accountMarked;
	private String orgNo;
	private String protocolType;
	
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getTotalPageNum() {
		return totalPageNum;
	}
	public void setTotalPageNum(String totalPageNum) {
		this.totalPageNum = totalPageNum;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
    
    public String getAccountMarked() {
        return accountMarked;
    }
    
    public void setAccountMarked(String accountMarked) {
        this.accountMarked = accountMarked;
    }
    
    public String getOrgNo() {
        return orgNo;
    }
    
    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }
    
    public String getProtocolType() {
        return protocolType;
    }
    
    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }


}
