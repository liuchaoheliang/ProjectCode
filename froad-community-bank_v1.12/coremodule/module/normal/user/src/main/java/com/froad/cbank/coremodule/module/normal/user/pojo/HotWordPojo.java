/**
 * Project Name:coremodule-user
 * File Name:HotWordPojo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.support
 * Date:2015年9月18日下午3:58:25
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.pojo;
/**
 * ClassName:HotWordPojo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月18日 下午3:58:25
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */
public class HotWordPojo {
	
	  public String clientId; // required
	  /**
	   * 热词
	   */
	  public String hotWord; // required
	  /**
	   * 地区id
	   */
	  public Long areaId; // required
	  /**
	   * 搜索总次数
	   */
	  //public Integer searchCount; // required
	  /**
	   * 搜索有结果次数
	   */
	  //public Integer searchCountResul; // required
	  /**
	   * 类型0 全部1 商品2商户
	   */
	  public Integer categoryType; // required
	  /**
	   * 0 全部1手工插入2系统统计
	   */
	 // public Integer type; // required
	  
	  
	  
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getHotWord() {
		return hotWord;
	}
	public void setHotWord(String hotWord) {
		this.hotWord = hotWord;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
//	public Integer getSearchCount() {
//		return searchCount;
//	}
//	public void setSearchCount(Integer searchCount) {
//		this.searchCount = searchCount;
//	}
//	public Integer getSearchCountResul() {
//		return searchCountResul;
//	}
//	public void setSearchCountResul(Integer searchCountResul) {
//		this.searchCountResul = searchCountResul;
//	}
	public Integer getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}
//	public Integer getType() {
//		return type;
//	}
//	public void setType(Integer type) {
//		this.type = type;
//	}
	
	

}
