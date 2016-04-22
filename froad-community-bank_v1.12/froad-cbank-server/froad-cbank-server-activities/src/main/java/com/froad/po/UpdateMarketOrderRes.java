package com.froad.po;

import java.io.Serializable;
import java.util.List;

public class UpdateMarketOrderRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 响应结果 */
    private Result result;
    /** 满赠活动响应 - 列表 */
    private List<FullGiveActive> fullGiveActiveList;
    
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public List<FullGiveActive> getFullGiveActiveList() {
		return fullGiveActiveList;
	}
	public void setFullGiveActiveList(List<FullGiveActive> fullGiveActiveList) {
		this.fullGiveActiveList = fullGiveActiveList;
	}

}
