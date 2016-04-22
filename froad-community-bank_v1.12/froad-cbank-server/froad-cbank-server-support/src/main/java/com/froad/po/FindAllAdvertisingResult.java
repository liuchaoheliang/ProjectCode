package com.froad.po;

import java.io.Serializable;
import java.util.List;


public class FindAllAdvertisingResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result; // Result结果
	private List<Advertising> advertisingList; // 结果集
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public List<Advertising> getAdvertisingList() {
		return advertisingList;
	}
	public void setAdvertisingList(List<Advertising> advertisingList) {
		this.advertisingList = advertisingList;
	}
	

}
