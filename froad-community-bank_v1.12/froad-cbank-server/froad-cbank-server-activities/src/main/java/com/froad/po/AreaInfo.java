package com.froad.po;

/**
 * 地区表（凑单排序专用）
 */
public class AreaInfo implements Comparable<AreaInfo>{

	// Fields

	private Long id;//自增主键ID (地区ID)
	private String clientId; // 客户端id
	private String name;//名称
	private String vagueLetter;//简拼
	private String treePath;//树路径
	private String  treePathName;//树路径名称
	private Long parentId;//上级地区ID
	private Boolean isEnable;//是否启用
	private String areaCode;//地图code,供前端交互地区时候使用
	private Double order;// 凑单排序特殊字段
	private String longitude;//经度
	private String latitude;// 纬度
	
	// Constructors

	public String getAreaCode() {
		return areaCode;
	}

	public Long getId() {
		return id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTreePathName() {
		return treePathName;
	}

	public void setTreePathName(String treePathName) {
		this.treePathName = treePathName;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/** default constructor */
	public AreaInfo() {
	}

	/** minimal constructor */
	public AreaInfo(String name, String treePath) {
		this.name = name;
		this.treePath = treePath;
	}

	/** full constructor */
	public AreaInfo(String name, String vagueLetter, String treePath, Long parentId) {
		this.name = name;
		this.vagueLetter = vagueLetter;
		this.treePath = treePath;
		this.parentId = parentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVagueLetter() {
		return this.vagueLetter;
	}

	public void setVagueLetter(String vagueLetter) {
		this.vagueLetter = vagueLetter;
	}

	public String getTreePath() {
		return this.treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Double getOrder() {
		return order;
	}

	public void setOrder(Double order) {
		this.order = order;
	}
	
	

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public int compareTo(AreaInfo o) {
		 return this.getOrder().compareTo(o.getOrder());
	}

}