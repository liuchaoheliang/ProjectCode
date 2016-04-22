package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

/**
 * 父级分类响应对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年11月5日 下午7:52:24
 */
public class parentCategoryRes {
	public String categoryId;//分类id
	public String name;//分类名称
	public String treePaht;//tree path
	public String isDelete;//是否禁用:1:禁用；0：启用
  
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTreePaht() {
		return treePaht;
	}
	public void setTreePaht(String treePaht) {
		this.treePaht = treePaht;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
}
