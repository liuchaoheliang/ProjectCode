package com.froad.po;

/**
 * 组织po
 * @author froad-ll
 * @createtime 20160104 
 *
 */
public class FFTOrg implements java.io.Serializable {

    
    private Long id;                    //id(自增主键)
	private String orgId;				//组织Id
	private Long parentId;			//父级组织代码，顶级组织的父级资源Id为0
	private Integer level;			    //组织级别
	private String platform;			//平台名称(boss、bank、merchant)
	private String clientId;			//所属客户端
	private String treePath;			//树路径(从顶级到自己本身的ID，逗号分隔)
	private String code;				//组织代码(boss对应主键ID，bank对应机构号，merchant对应商户Id)
	private String orgName;				//组织名(boss对应方付通部门，bank对应机构名，merchant对应商户名)
	private String phone;				//电话号码 
	private Long areaId;				//区域Id
	private String address;				//地址信息
	private Boolean isDelete;			//是否删除
	
	private Integer quantity;			//组织下级数量
	
	public FFTOrg(){}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
    
}