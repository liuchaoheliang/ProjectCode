package com.froad.CB.po.tag;

import java.io.Serializable;


	/**
	 * 类描述：二级商圈标签
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:39:06 PM 
	 */
public class TagDistrictB implements Serializable {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String tagValue;//标签值
	private String tagHyperlink;
	private String siteValue;//位置值，例如：1001000  一级类别二级类别
	private String sortValue;//排序值
	private String description;//描述
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;
	private String updateTime;
	private String remark;

	private String parentTagDistrictId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public String getTagHyperlink() {
		return tagHyperlink;
	}

	public void setTagHyperlink(String tagHyperlink) {
		this.tagHyperlink = tagHyperlink;
	}

	public String getSiteValue() {
		return siteValue;
	}

	public void setSiteValue(String siteValue) {
		this.siteValue = siteValue;
	}

	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getParentTagDistrictId() {
		return parentTagDistrictId;
	}

	public void setParentTagDistrictId(String parentTagDistrictId) {
		this.parentTagDistrictId = parentTagDistrictId;
	}

	@Override
	public String toString() {
		return "TagDistrictB [createTime=" + createTime + ", description="
				+ description + ", id=" + id + ", parentTagDistrictId="
				+ parentTagDistrictId + ", remark=" + remark + ", siteValue="
				+ siteValue + ", sortValue=" + sortValue + ", state=" + state
				+ ", tagHyperlink=" + tagHyperlink + ", tagValue=" + tagValue
				+ ", updateTime=" + updateTime + "]";
	}

}