package com.froad.po.mongo;

import com.froad.po.ProductSaleDetail;

public class ProductSaleDetailMongo {
	private Integer index = null;
	private ProductSaleDetail productSaleDetail = null;
	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}
	/**
	 * @return the productSaleDetail
	 */
	public ProductSaleDetail getProductSaleDetail() {
		return productSaleDetail;
	}
	/**
	 * @param productSaleDetail the productSaleDetail to set
	 */
	public void setProductSaleDetail(ProductSaleDetail productSaleDetail) {
		this.productSaleDetail = productSaleDetail;
	}
}
