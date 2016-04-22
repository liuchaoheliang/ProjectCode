package com.froad.cbank.coremodule.module.normal.bank.vo;

/**
 * 地区
 * @author Administrator
 *
 */
public class AreaVo {

	private Long id;          //地区ID
	private Long partenId;        //上级地区ID
	private String name;      //地区名称
	private String treePath;      //树路径
	private String treePathName;      //树路径
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPartenId() {
		return partenId;
	}
	public void setPartenId(Long partenId) {
		this.partenId = partenId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
}
