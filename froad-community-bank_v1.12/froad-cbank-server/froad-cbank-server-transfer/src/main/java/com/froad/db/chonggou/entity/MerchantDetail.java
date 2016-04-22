package com.froad.db.chonggou.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * MerchantDetail entity.
 */


/**    
 * <p>Title: MerchantDetail.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月21日 上午10:30:30   
 */   
public class MerchantDetail implements java.io.Serializable {

	// Fields

	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = -1758990848435753650L;
	@JSONField(name = "_id", serialize = true, deserialize = true)
	private String id;
	@JSONField(name = "create_time", serialize = true, deserialize = true)
	private Long createTime;
	@JSONField(name = "client_id", serialize = true, deserialize = true)
	private String clientId;
	@JSONField(name = "pro_org_code", serialize = true, deserialize = true)
	private String proOrgCode;
	@JSONField(name = "city_org_code", serialize = true, deserialize = true)
	private String cityOrgCode;
	@JSONField(name = "county_org_code", serialize = true, deserialize = true)
	private String countyOrgCode;
	@JSONField(name = "org_code", serialize = true, deserialize = true)
	private String orgCode;
	@JSONField(name = "is_enable", serialize = true, deserialize = true)
	private Boolean isEnable;
	@JSONField(name = "is_top", serialize = true, deserialize = true)
	private Boolean isTop;
	@JSONField(name = "merchant_name", serialize = true, deserialize = true)
	private String merchantName;
	@JSONField(name = "merchant_fullname", serialize = true, deserialize = true)
	private String merchantFullname;
	@JSONField(name = "logo", serialize = true, deserialize = true)
	private String logo;
	@JSONField(name = "address", serialize = true, deserialize = true)
	private String address;
	@JSONField(name = "phone", serialize = true, deserialize = true)
	private String phone;
	@JSONField(name = "introduced", serialize = true, deserialize = true)
	private String introduced;

	@JSONField(name = "area_id", serialize = true, deserialize = true)
	private Long areaId;
	@JSONField(name = "tree_path_name", serialize = true, deserialize = true)
	private String treePathName;
//	@JSONField(name = "location", serialize = true, deserialize = true)
//	private Location location;
	@JSONField(name = "outlet_info", serialize = true, deserialize = true)
	private List<String> outletInfo;
	@JSONField(name = "category_info", serialize = true, deserialize = true)
	private List<CategoryInfo> categoryInfo;
	@JSONField(name = "type_info", serialize = true, deserialize = true)
	private List<TypeInfo> typeInfo;
	
	/**
	 * 评论星级
	 */
	@JSONField(name = "star_level", serialize = true, deserialize = true)
	private Integer starLevel;

	public MerchantDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public MerchantDetail(String id, Long createTime, String clientId, String orgCode, Boolean isEnable, Boolean isTop, String merchantName, String logo, String address, String phone, String introduced, Long areaId,Location location, List<String> outletInfo, List<CategoryInfo> categoryInfo, List<TypeInfo> typeInfo) {
//		super();
//		this.id = id;
//		this.createTime = createTime;
//		this.clientId = clientId;
////		this.merchantId = merchantId;
//		this.orgCode = orgCode;
//		this.isEnable = isEnable;
//		this.isTop = isTop;
//		this.merchantName = merchantName;
//		this.logo = logo;
//		this.address = address;
//		this.phone = phone;
//		this.introduced = introduced;
//		this.areaId = areaId;
//		this.location = location;
//		this.outletInfo = outletInfo;
//		this.categoryInfo = categoryInfo;
//		this.typeInfo = typeInfo;
//	}

	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public Long getCreateTime() {
		return createTime;
	}

	
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	
	public String getClientId() {
		return clientId;
	}

	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	
	public String getProOrgCode() {
		return proOrgCode;
	}

	public void setProOrgCode(String proOrgCode) {
		this.proOrgCode = proOrgCode;
	}

	public String getCityOrgCode() {
		return cityOrgCode;
	}

	public void setCityOrgCode(String cityOrgCode) {
		this.cityOrgCode = cityOrgCode;
	}

	public String getCountyOrgCode() {
		return countyOrgCode;
	}

	public void setCountyOrgCode(String countyOrgCode) {
		this.countyOrgCode = countyOrgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	
	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String name) {
		this.merchantName = name;
	}


	
	public String getMerchantFullname() {
		return merchantFullname;
	}

	public void setMerchantFullname(String merchantFullname) {
		this.merchantFullname = merchantFullname;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	

	
	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}



	
	public String getTreePathName() {
		return treePathName;
	}

	public void setTreePathName(String treePathName) {
		this.treePathName = treePathName;
	}

	public List<String> getOutletInfo() {
		return outletInfo;
	}

	public void setOutletInfo(List<String> outletInfo) {
		this.outletInfo = outletInfo;
	}

	
	public List<CategoryInfo> getCategoryInfo() {
		return categoryInfo;
	}

	public void setCategoryInfo(List<CategoryInfo> categoryInfo) {
		this.categoryInfo = categoryInfo;
	}

	
	public List<TypeInfo> getTypeInfo() {
		return typeInfo;
	}

	public void setTypeInfo(List<TypeInfo> typeInfo) {
		this.typeInfo = typeInfo;
	}

	public Integer getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}

}