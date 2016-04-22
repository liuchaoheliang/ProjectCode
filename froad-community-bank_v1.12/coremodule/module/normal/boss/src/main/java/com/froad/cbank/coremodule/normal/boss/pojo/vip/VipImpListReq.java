package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;


/**
 * VIP批量导入列表请求类
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月29日 下午2:02:43
 */
public class VipImpListReq extends Page {
	private Long beginTime;
	private Long endTime;
	private String keyword;
	private String bankOrg;//银行渠道
	private Boolean pendingSort = false;
	
	public Long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getBankOrg() {
		return bankOrg;
	}
	public void setBankOrg(String bankOrg) {
		this.bankOrg = bankOrg;
	}
	public Boolean getPendingSort() {
		return pendingSort;
	}
	public void setPendingSort(Boolean pendingSort) {
		this.pendingSort = pendingSort;
	}
}
