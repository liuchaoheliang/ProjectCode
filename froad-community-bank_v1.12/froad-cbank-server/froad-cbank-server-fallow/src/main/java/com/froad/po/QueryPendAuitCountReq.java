/**
 * Project Name:froad-cbank-server-fallow
 * File Name:QueryPendAuitListPageReq.java
 * Package Name:com.froad.po
 * Date:2015年10月22日下午6:18:10
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.po;

import java.util.Map;

import com.froad.enums.RestrictionsEnum;

/**
 * ClassName:QueryPendAuitListPageReq Reason: TODO ADD REASON. Date: 2015年10月22日
 * 下午6:18:10
 * 
 * @author wm
 * @version
 * @see
 */
public class QueryPendAuitCountReq {

	/**
	 * 源信息
	 */
	private Origin origin;
	/**
	 * 开始时间
	 */
	private Long starTime;
	/**
	 * 结束时间
	 */
	private Long endTime;
	/**
	 * 流程类型:1-商户,2-门店,3-团购商品,4-预售商品
	 */
	private String processType;
	/** 类型详情:0-新增,1-更新 */
	private String processTypeDetail;
	private Map<RestrictionsEnum, String> andBessData;
	private Map<RestrictionsEnum, String> orBessData;

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public Long getStarTime() {
		return starTime;
	}

	public void setStarTime(Long starTime) {
		this.starTime = starTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getProcessTypeDetail() {
		return processTypeDetail;
	}

	public void setProcessTypeDetail(String processTypeDetail) {
		this.processTypeDetail = processTypeDetail;
	}

	public Map<RestrictionsEnum, String> getAndBessData() {
		return andBessData;
	}

	public void setAndBessData(Map<RestrictionsEnum, String> andBessData) {
		this.andBessData = andBessData;
	}

	public Map<RestrictionsEnum, String> getOrBessData() {
		return orBessData;
	}

	public void setOrBessData(Map<RestrictionsEnum, String> orBessData) {
		this.orBessData = orBessData;
	}

}
