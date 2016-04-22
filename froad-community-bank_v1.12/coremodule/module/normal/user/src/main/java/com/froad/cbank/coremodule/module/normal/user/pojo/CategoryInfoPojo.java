package com.froad.cbank.coremodule.module.normal.user.pojo;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月16日 下午9:28:35
 * @version 1.0
 * @desc 商户分类POJO
 */
public class CategoryInfoPojo {
	private String categoryId; 
	private String name;
	  
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
}
