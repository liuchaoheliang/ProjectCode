package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月29日 上午9:45:47
 * @desc 关联商户信息分页查询
 */
public class MerchantRelatePageVoReq extends Page{
	
	 /**
	   * 活动标签id *
	   */
	@NotEmpty(value="活动标签id不能为空")
	  private long activityId; // optional
	  /**
	   * 客户端ID *
	   */
	@NotEmpty(value="客户端不能为空")
	  private String clientId; // optional
	  /**
	   * 活动编号 *
	   */
	@NotEmpty(value="活动编号不能为空")
	  private String activityNo; // optional
	  
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
