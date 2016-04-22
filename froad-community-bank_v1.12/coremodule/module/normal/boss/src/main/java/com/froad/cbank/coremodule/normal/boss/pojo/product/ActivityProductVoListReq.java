package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class ActivityProductVoListReq extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 活动id */
	@NotEmpty(value = "活动id不能为空!")
	private String id;
	/** 查询标识:0:查询已经绑定的商品列表;1:查询可以绑定的商品列表 */
	@NotEmpty(value = "查询标识不能为空!")
	private String flag;
	/** 商品名称,模糊查询 */
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
