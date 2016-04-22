/**
 * @Title: ProductOfFindUse.java
 * @Package com.froad.po
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月8日
 * @version V1.0
 **/

package com.froad.po;

import java.io.Serializable;

/**
 * @ClassName: ProductOfFindUse
 * @Description: TODO
 * @author froad-Joker 2015年12月8日
 * @modify froad-Joker 2015年12月8日
 */

public class ProductOfFindUse implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = 1L;
	/** 商品id */
	private String productId;
	/** 商品名称 */
	private String productName;
	/** 普通金额 */
	private Double generalMoney;
	/** 普通数量 */
	private Integer generalCount;
	/** vip金额 */
	private Double vipMoney;
	/** vip数量 */
	private Integer vipCount;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getGeneralCount() {
		return generalCount;
	}

	public void setGeneralCount(Integer generalCount) {
		this.generalCount = generalCount;
	}

	public Double getGeneralMoney() {
		return generalMoney;
	}

	public void setGeneralMoney(Double generalMoney) {
		this.generalMoney = generalMoney;
	}

	public Double getVipMoney() {
		return vipMoney;
	}

	public void setVipMoney(Double vipMoney) {
		this.vipMoney = vipMoney;
	}

	public Integer getVipCount() {
		return vipCount;
	}

	public void setVipCount(Integer vipCount) {
		this.vipCount = vipCount;
	}

}
