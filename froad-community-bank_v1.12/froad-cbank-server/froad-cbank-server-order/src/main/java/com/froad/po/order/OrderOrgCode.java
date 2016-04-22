package com.froad.po.order;

import java.util.List;

public class OrderOrgCode {
	/**
	 * 一级机构
	 */
	private List<String> forgCodeList;
	
	/**
	 * 二级机构
	 */
	private List<String> sorgCodeList;
	
	/**
	 * 三级机构
	 */
	private List<String> torgCodeList;
	
	/**
	 * 四级机构
	 */
	private List<String> lorgCodeList;

	public List<String> getForgCodeList() {
		return forgCodeList;
	}

	public void setForgCodeList(List<String> forgCodeList) {
		this.forgCodeList = forgCodeList;
	}

	public List<String> getSorgCodeList() {
		return sorgCodeList;
	}

	public void setSorgCodeList(List<String> sorgCodeList) {
		this.sorgCodeList = sorgCodeList;
	}

	public List<String> getTorgCodeList() {
		return torgCodeList;
	}

	public void setTorgCodeList(List<String> torgCodeList) {
		this.torgCodeList = torgCodeList;
	}

	public List<String> getLorgCodeList() {
		return lorgCodeList;
	}

	public void setLorgCodeList(List<String> lorgCodeList) {
		this.lorgCodeList = lorgCodeList;
	}
	
}
