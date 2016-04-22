package com.froad.CB.po.tag;

import java.io.Serializable;


	/**
	 * 类描述：一级分类标签
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:38:09 PM 
	 */
public class TagClassifyA implements Serializable {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String tagValue;//标签值
	private String tagClassifyAId;
	private String siteValue;//位置值，例如：1001000  一级类别二级类别
	private String sortValue;//排序值
	private String description;//描述
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;
	private String updateTime;
	private String remark;

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

	public String getTagClassifyAId() {
		return tagClassifyAId;
	}

	public void setTagClassifyAId(String tagClassifyAId) {
		this.tagClassifyAId = tagClassifyAId;
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

	@Override
	public String toString() {
		return "TagClassifyA [createTime=" + createTime + ", description="
				+ description + ", id=" + id + ", remark=" + remark
				+ ", siteValue=" + siteValue + ", sortValue=" + sortValue
				+ ", state=" + state + ", tagClassifyAId=" + tagClassifyAId
				+ ", tagValue=" + tagValue + ", updateTime=" + updateTime + "]";
	}

}