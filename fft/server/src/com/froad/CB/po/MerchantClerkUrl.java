package com.froad.CB.po;
/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-23  
 * @version 1.0
 */
public class MerchantClerkUrl {
	private Integer id;
	private String urlType;//资源类型   0：不许普通操作员访问url 1：普通操作员和管理员都可访问url
	private String url;//资源
	private String state;//状态
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private	String remark;//备注
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrlType() {
		return urlType;
	}
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
