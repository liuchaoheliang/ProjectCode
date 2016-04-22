/**
 * Project Name:froad-cbank-server-coremodule
 * File Name:ProductInfoResp.java
 * Package Name:com.froad.po.resp
 * Date:2015年12月2日下午3:22:10
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po.resp;

/**
 * ClassName:ProductInfoResp Reason:Date: 2015年12月2日 下午3:22:10
 * 
 * @author chenmingcan@froad.com.cn
 * @version
 * @see
 */
public class ProductInfoResp {
	/**
	 * 商品数量
	 */
	public Integer productCount; // optional
	/**
	 * 累计商品数量
	 */
	public Integer productCumulation; // optional
	/**
	 * 下架商品数量
	 */
	public Integer productDownSum; // optional
	/**
	 * 累计下架商品数量
	 */
	public Integer productDownComulation; // optional

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Integer getProductCumulation() {
		return productCumulation;
	}

	public void setProductCumulation(Integer productCumulation) {
		this.productCumulation = productCumulation;
	}

	public Integer getProductDownSum() {
		return productDownSum;
	}

	public void setProductDownSum(Integer productDownSum) {
		this.productDownSum = productDownSum;
	}

	public Integer getProductDownComulation() {
		return productDownComulation;
	}

	public void setProductDownComulation(Integer productDownComulation) {
		this.productDownComulation = productDownComulation;
	}

}
