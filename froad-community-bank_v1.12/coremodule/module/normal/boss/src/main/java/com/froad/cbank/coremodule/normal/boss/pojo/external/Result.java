package com.froad.cbank.coremodule.normal.boss.pojo.external;

import java.util.ArrayList;

/**
 * 回调请求参数
 * @author yfy
 */
public class Result {

	/**
	 * 消息体
	 */
	private String message;
	/**
	 * 单号
	 */
	private String nu;
	/**
	 * 是否签收，明细状态请参考state字段
	 */
	private String ischeck;
	/**
	 * 快递公司编码
	 */
	private String com;
	/**
	 * 物流进度信息
	 */
	private ArrayList<ResultItem> data = new ArrayList<ResultItem>();
	/**
	 * 快递单当前签收状态（0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态）
	 */
	private String state;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNu() {
		return nu;
	}
	public void setNu(String nu) {
		this.nu = nu;
	}
	public String getIscheck() {
		return ischeck;
	}
	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public ArrayList<ResultItem> getData() {
		return data;
	}
	public void setData(ArrayList<ResultItem> data) {
		this.data = data;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
