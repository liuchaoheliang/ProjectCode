package com.froad.po;

import java.io.Serializable;

import com.froad.db.mysql.bean.Page;

public class FindPageAdLocationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result; // Result结果
	private Page page; // 分页结果
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}

}
