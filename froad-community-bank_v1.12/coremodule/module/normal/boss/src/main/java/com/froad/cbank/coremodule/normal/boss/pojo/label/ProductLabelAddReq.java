package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 商品标签新增,修改请求
 * 
 * @ClassName ProductLabelRes
 * @author zxl
 * @date 2015年10月28日 下午2:47:40
 */
public class ProductLabelAddReq {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 客户端ID
	 */
	@NotEmpty(value="客户端不能为空")
	private String clientId;
	/**
	 * 活动名称
	 */
	@NotEmpty(value="活动名称不能为空")
	private String activityName;
	/**
	 * 活动编号
	 */
	private String activityNo;
	/**
	 * 描述
	 */
	@NotEmpty(value="描述不能为空")
	private String activityDesc;
	/**
	 * 活动logo_url
	 */
	private String logoUrl;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	
}
