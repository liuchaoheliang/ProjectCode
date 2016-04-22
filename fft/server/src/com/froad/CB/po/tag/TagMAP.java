package com.froad.CB.po.tag;

import java.io.Serializable;

/**
 * 类描述：标签映射商户
	 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2012 
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Dec 13, 2012 5:39:19 PM 
 */
public class TagMAP implements Serializable {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String merchantId;//关联 FBU_Merchant
	private String siteValue;//位置值，例如：1001000  一级类别二级类别
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
    private String tagClassifyAId;
    private String tagClassifyBId;
    private String tagDistrictAId;
    private String tagDistrictBId;
	private TagClassifyA tagClassifyA;
	private TagClassifyB tagClassifyB;
	private TagDistrictA tagDistrictA;
	private TagDistrictB tagDistrictB;
	private String tagValue;	
	private String createTime;
	private String updateTime;
	private String remark;	
	/**tmp field**/
	private String merchantName;

	public TagClassifyA getTagClassifyA() {
		return tagClassifyA;
	}

	public void setTagClassifyA(TagClassifyA tagClassifyA) {
		this.tagClassifyA = tagClassifyA;
	}

	public TagClassifyB getTagClassifyB() {
		return tagClassifyB;
	}

	public void setTagClassifyB(TagClassifyB tagClassifyB) {
		this.tagClassifyB = tagClassifyB;
	}

	public TagDistrictA getTagDistrictA() {
		return tagDistrictA;
	}

	public void setTagDistrictA(TagDistrictA tagDistrictA) {
		this.tagDistrictA = tagDistrictA;
	}

	public TagDistrictB getTagDistrictB() {
		return tagDistrictB;
	}

	public void setTagDistrictB(TagDistrictB tagDistrictB) {
		this.tagDistrictB = tagDistrictB;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSiteValue() {
		return siteValue;
	}

	public void setSiteValue(String siteValue) {
		this.siteValue = siteValue;
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

	public String getTagClassifyAId() {
		return tagClassifyAId;
	}

	public void setTagClassifyAId(String tagClassifyAId) {
		this.tagClassifyAId = tagClassifyAId;
	}

	public String getTagClassifyBId() {
		return tagClassifyBId;
	}

	public void setTagClassifyBId(String tagClassifyBId) {
		this.tagClassifyBId = tagClassifyBId;
	}

	public String getTagDistrictAId() {
		return tagDistrictAId;
	}

	public void setTagDistrictAId(String tagDistrictAId) {
		this.tagDistrictAId = tagDistrictAId;
	}

	public String getTagDistrictBId() {
		return tagDistrictBId;
	}

	public void setTagDistrictBId(String tagDistrictBId) {
		this.tagDistrictBId = tagDistrictBId;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	@Override
	public String toString() {
		return "TagMAP [createTime=" + createTime + ", id=" + id
				+ ", merchantId=" + merchantId + ", remark=" + remark
				+ ", siteValue=" + siteValue + ", state=" + state
				+ ", tagClassifyA=" + tagClassifyA + ", tagClassifyAId="
				+ tagClassifyAId + ", tagClassifyB=" + tagClassifyB
				+ ", tagClassifyBId=" + tagClassifyBId + ", tagDistrictA="
				+ tagDistrictA + ", tagDistrictAId=" + tagDistrictAId
				+ ", tagDistrictB=" + tagDistrictB + ", tagDistrictBId="
				+ tagDistrictBId + ", updateTime=" + updateTime + ", tagValue="
				+ tagValue + "]";
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}