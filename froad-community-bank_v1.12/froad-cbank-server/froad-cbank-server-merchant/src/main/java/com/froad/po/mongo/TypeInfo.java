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
 * @Title: TypeInfo.java
 * @Package com.froad.po.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年3月21日
 */

package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>
 * Title: TypeInfo.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年3月21日 上午10:28:28
 */

public class TypeInfo implements java.io.Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = -1216934775793528276L;
	@JSONField(name = "merchant_type_id",serialize = true,deserialize = true)
	private Long merchantTypeId;
	
	@JSONField(name = "type_name",serialize = true,deserialize = true)
	private String typeName;
	
	@JSONField(name = "type",serialize = true,deserialize = true)
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TypeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TypeInfo(Long merchantTypeId, String typeName) {
		super();
		this.merchantTypeId = merchantTypeId;
		this.typeName = typeName;
	}
	public Long getMerchantTypeId() {
		return merchantTypeId;
	}
	public void setMerchantTypeId(Long merchantTypeId) {
		this.merchantTypeId = merchantTypeId;
	}

	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
