package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 商户分类
 * @author ylchu
 *
 */
public class CategoryVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2574992320277532569L;

	private Long categoryId;
	private String name;
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
