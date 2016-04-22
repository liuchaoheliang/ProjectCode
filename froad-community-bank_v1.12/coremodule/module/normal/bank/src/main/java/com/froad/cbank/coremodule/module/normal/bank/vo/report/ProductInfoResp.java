/**
 * Project Name:froad-cbank-server-coremodule
 * File Name:ProductInfoResp.java
 * Package Name:com.froad.po.resp
 * Date:2015年12月2日下午3:22:10
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo.report;

import java.io.Serializable;

/**
 * ClassName:ProductInfoResp Reason: TODO ADD REASON. Date: 2015年12月2日 下午3:22:10
 * 
 * @author chenmingcan@froad.com.cn
 * @version
 * @see
 */
public class ProductInfoResp  implements Serializable  {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 8989035782737883170L;
	/**
	 * 商品数量
	 */
	public int productCount; // optional
	/**
	 * 累计商品数量
	 */
	public int productCumulation; // optional
	/**
	 * 下架商品数量
	 */
	public int productDownSum; // optional
	/**
	 * 累计下架商品数量
	 */
	public int productDownComulation; // optional

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getProductCumulation() {
		return productCumulation;
	}

	public void setProductCumulation(int productCumulation) {
		this.productCumulation = productCumulation;
	}

	public int getProductDownSum() {
		return productDownSum;
	}

	public void setProductDownSum(int productDownSum) {
		this.productDownSum = productDownSum;
	}

	public int getProductDownComulation() {
		return productDownComulation;
	}

	public void setProductDownComulation(int productDownComulation) {
		this.productDownComulation = productDownComulation;
	}

}
