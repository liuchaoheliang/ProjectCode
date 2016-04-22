/**
 * @Title: PutFullCutReq.java
 * @Package com.froad.po
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月25日
 * @version V1.0
 **/

package com.froad.po;

import java.io.Serializable;
import java.util.Set;

/**
 * @ClassName: PutFullCutReq
 * @Description: TODO
 * @author froad-Joker 2015年12月25日
 * @modify froad-Joker 2015年12月25日
 */

public class PutFullCutReq implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = 1L;

	/** 客户端ID */
	private String clientId;
	/** 商品ID */
	private Set<String> productIds;
	/** 标签类型 */
	private String itemType;
	/** 标签id */
	private String itemId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Set<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(Set<String> productIds) {
		this.productIds = productIds;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
