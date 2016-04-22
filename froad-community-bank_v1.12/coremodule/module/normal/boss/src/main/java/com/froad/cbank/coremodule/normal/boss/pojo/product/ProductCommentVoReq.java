package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class ProductCommentVoReq extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 客户端id */
	@NotEmpty(value = "客户端Id不能为空!")
	private String clientId;
	/** 用户名 */
	private String userName;
	/** 商品名称 */
	private String productName;
	/** 评价星级 */
	private String startLevel;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(String startLevel) {
		this.startLevel = startLevel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
