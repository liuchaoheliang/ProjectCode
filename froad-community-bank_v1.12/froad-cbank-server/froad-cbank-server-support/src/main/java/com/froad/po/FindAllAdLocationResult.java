package com.froad.po;

import java.io.Serializable;
import java.util.List;


public class FindAllAdLocationResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result; // Result结果
	private List<AdLocation> adLocationList; // 结果集
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public List<AdLocation> getAdLocationList() {
		return adLocationList;
	}
	public void setAdLocationList(List<AdLocation> adLocationList) {
		this.adLocationList = adLocationList;
	}

}
