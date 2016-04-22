package com.froad.po;

import java.io.Serializable;

public class ReturnSubOrderProductReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 商品id */
	private String productId;
    /** vip数量 */
	private Integer vipCount;
    /** 普通数量 */
	private Integer normalCount;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getVipCount() {
		return vipCount;
	}
	public void setVipCount(Integer vipCount) {
		this.vipCount = vipCount;
	}
	public Integer getNormalCount() {
		return normalCount;
	}
	public void setNormalCount(Integer normalCount) {
		this.normalCount = normalCount;
	}
	
}
