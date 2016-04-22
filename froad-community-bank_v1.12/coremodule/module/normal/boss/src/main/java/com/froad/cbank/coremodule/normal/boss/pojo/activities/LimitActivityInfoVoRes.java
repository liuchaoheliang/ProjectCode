package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：限购活动查询返回实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-23下午1:21:46 
 */
public class LimitActivityInfoVoRes implements Serializable{

	/**
	 * UID
	 */
	private static final long serialVersionUID = -1251386160992217889L;
	
	private String clientId;//所属客户端
	private String actStatu;//限购活动状态
	private String actName;//活动名称
	private List<LimitActivityInfoVo> limitActivityInfoVoList;//限购活动列表
	private List<LimitActivityProduct> limitProductList;//限购活动商品列表
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getActStatu() {
		return actStatu;
	}
	public void setActStatu(String actStatu) {
		this.actStatu = actStatu;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public List<LimitActivityInfoVo> getLimitActivityInfoVoList() {
		return limitActivityInfoVoList;
	}
	public void setLimitActivityInfoVoList(
			List<LimitActivityInfoVo> limitActivityInfoVoList) {
		this.limitActivityInfoVoList = limitActivityInfoVoList;
	}
	public List<LimitActivityProduct> getLimitProductList() {
		return limitProductList;
	}
	public void setLimitProductList(List<LimitActivityProduct> limitProductList) {
		this.limitProductList = limitProductList;
	}
	
}
