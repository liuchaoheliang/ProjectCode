package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.thrift.vo.PlatType;

public class BasePojo {
	
	private String clientId; 
	
	private MerchantUser merchantUser;
	
	private String ip;
	
	private PlatType platType;
	
	private int pageNumber = 1;
	
	private int pageSize = 10;
	
	private int lastPageNumber;
	
	private long firstRecordTime;
	
	private long lastRecordTime;
	
	
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

	public int getLastPageNumber() {
		return lastPageNumber;
	}

	public void setLastPageNumber(int lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	public long getFirstRecordTime() {
		return firstRecordTime;
	}

	public void setFirstRecordTime(long firstRecordTime) {
		this.firstRecordTime = firstRecordTime;
	}

	public long getLastRecordTime() {
		return lastRecordTime;
	}

	public void setLastRecordTime(long lastRecordTime) {
		this.lastRecordTime = lastRecordTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public PlatType getPlatType() {
		return platType;
	}

	public void setPlatType(PlatType platType) {
		this.platType = platType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public MerchantUser getMerchantUser() {
		return merchantUser;
	}

	public void setMerchantUser(MerchantUser merchantUser) {
		this.merchantUser = merchantUser;
	}

}
