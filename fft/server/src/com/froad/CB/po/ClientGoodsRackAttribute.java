package com.froad.CB.po;


	/**
	 * 类描述：上架商品的专有属性(手机客户端)
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 1, 2013 4:47:35 PM 
	 */
public class ClientGoodsRackAttribute {

	private Integer id;

	private String name;

	private String rackType;

	private String isRequired;

	private String goodsRackId;

	private String state;

	private String createTime;

	private String updateTime;

	private String remark;
	
	private String aliasCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired == null ? null : isRequired.trim();
	}

	public String getGoodsRackId() {
		return goodsRackId;
	}

	public void setGoodsRackId(String goodsRackId) {
		this.goodsRackId = goodsRackId == null ? null : goodsRackId.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getAliasCode() {
		return aliasCode;
	}

	public void setAliasCode(String aliasCode) {
		this.aliasCode = aliasCode;
	}

	public String getRackType() {
		return rackType;
	}

	public void setRackType(String rackType) {
		this.rackType = rackType;
	}
}