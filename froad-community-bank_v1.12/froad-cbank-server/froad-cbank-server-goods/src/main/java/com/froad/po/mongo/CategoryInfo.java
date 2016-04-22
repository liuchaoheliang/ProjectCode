/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: CategoryInfo.java
 * @Package com.froad.po.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年3月21日
 */

package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>
 * Title: CategoryInfo.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年3月21日 上午10:11:47
 */

public class CategoryInfo implements java.io.Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = 4406699676105503280L;
	@JSONField(name = "category_id", serialize = true, deserialize = true)
	private Long categoryId;
	@JSONField(name = "name", serialize = true, deserialize = true)
	private String name;

	public CategoryInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryInfo(Long categoryId, String name) {
		super();
		this.categoryId = categoryId;
		this.name = name;
	}
	
	
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
