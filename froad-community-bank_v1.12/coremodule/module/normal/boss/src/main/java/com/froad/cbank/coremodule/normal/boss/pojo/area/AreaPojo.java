package com.froad.cbank.coremodule.normal.boss.pojo.area;

/**
 * 地区转换实体
 * @author liuchao
 *
 */
public class AreaPojo {
	private String id;
	private String clientId ;
	private String name;
	private String vagueLetter;
	private String treePath;
	private String treePathName;
	private String parentId;
	private String areaCode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVagueLetter() {
		return vagueLetter;
	}
	public void setVagueLetter(String vagueLetter) {
		this.vagueLetter = vagueLetter;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public String getTreePathName() {
		return treePathName;
	}
	public void setTreePathName(String treePathName) {
		this.treePathName = treePathName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
