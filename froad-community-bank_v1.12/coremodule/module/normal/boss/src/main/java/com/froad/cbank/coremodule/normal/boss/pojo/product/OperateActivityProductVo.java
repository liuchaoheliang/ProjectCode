package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;
import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class OperateActivityProductVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 活动id */
	@NotEmpty(value = "活动id不能为空!")
	private String id;
	/** 商品id */
	private String productId;
	/** 商品id集合 */
	//@NotEmpty(value = "商品id不能为空!")
	private List<String> productIds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
