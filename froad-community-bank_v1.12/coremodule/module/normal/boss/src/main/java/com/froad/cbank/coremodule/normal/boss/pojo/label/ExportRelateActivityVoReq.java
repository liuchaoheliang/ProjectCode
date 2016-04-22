package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * @ClassName: ExportRelateActivityVoReq
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2016年1月12日 下午4:17:04 
 * @desc <p>关联标签导出voReq</p>
 */
public class ExportRelateActivityVoReq {
	
	 /**
	   * 活动标签id *
	   */
	@NotEmpty(value="活动标签id不能为空")
	  private long activityId; // required
	  /**
	   * 客户端ID *
	   */
	@NotEmpty(value="客户端id不能为空")
	  private String clientId; // required
	  /**
	   * 活动编号 *
	   */
	@NotEmpty(value="活动编号不能为空")
	  private String activityNo; // required
	  
	public long getActivityId() {
		return activityId;
	}
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	  
	  
}
