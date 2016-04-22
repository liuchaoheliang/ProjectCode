package com.froad.CB.po.searches;

import java.io.Serializable;

/** 
 * @author FQ 
 * @date 2012-12-5 下午02:27:40
 * @version 1.0
 * 热门搜索
 */
public class Searches implements Serializable{
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String mPreferentialType;//优惠类型
	private String tagClassifyAId;//一级分类ID
	private String tagDistrictAId;//一级商圈ID
	private String tagClassifyBId;//二级分类ID
	private String tagDistrictBId;//二级商圈ID
	private String tagClassifyAValue;//
	private String tagDistrictBValue;//
	private String searchKeywords;//搜索关键字
	private String searchCount;//搜索次数
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;//
	private String updateTime;//
	private String remark;//
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getmPreferentialType() {
		return mPreferentialType;
	}
	public void setmPreferentialType(String mPreferentialType) {
		this.mPreferentialType = mPreferentialType;
	}
	
	public String getTagClassifyAId() {
		return tagClassifyAId;
	}
	public void setTagClassifyAId(String tagClassifyAId) {
		this.tagClassifyAId = tagClassifyAId;
	}
	public String getTagDistrictAId() {
		return tagDistrictAId;
	}
	public void setTagDistrictAId(String tagDistrictAId) {
		this.tagDistrictAId = tagDistrictAId;
	}
	public String getTagClassifyBId() {
		return tagClassifyBId;
	}
	public void setTagClassifyBId(String tagClassifyBId) {
		this.tagClassifyBId = tagClassifyBId;
	}
	public String getTagDistrictBId() {
		return tagDistrictBId;
	}
	public void setTagDistrictBId(String tagDistrictBId) {
		this.tagDistrictBId = tagDistrictBId;
	}
	public String getSearchKeywords() {
		return searchKeywords;
	}
	public void setSearchKeywords(String searchKeywords) {
		this.searchKeywords = searchKeywords;
	}
	public String getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(String searchCount) {
		this.searchCount = searchCount;
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
	public String getTagClassifyAValue() {
		return tagClassifyAValue;
	}
	public void setTagClassifyAValue(String tagClassifyAValue) {
		this.tagClassifyAValue = tagClassifyAValue;
	}
	public String getTagDistrictBValue() {
		return tagDistrictBValue;
	}
	public void setTagDistrictBValue(String tagDistrictBValue) {
		this.tagDistrictBValue = tagDistrictBValue;
	}
	
}
