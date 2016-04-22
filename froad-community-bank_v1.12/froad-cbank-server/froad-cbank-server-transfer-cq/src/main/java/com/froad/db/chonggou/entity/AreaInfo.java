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
 * @Title: ContactInfo.java
 * @Package com.froad.po.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年3月21日
 */

package com.froad.db.chonggou.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>
 * Title: ContactInfo.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年3月21日 上午10:11:47
 */

public class AreaInfo implements java.io.Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = -5481962280813087970L;
	@JSONField(name = "area_id", serialize = true, deserialize = true)
	private Long areaId;
	@JSONField(name = "longitude", serialize = true, deserialize = true)
	private String longitude;
	@JSONField(name = "latitude", serialize = true, deserialize = true)
	private String latitude;

	public AreaInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AreaInfo(Long areaId, String longitude, String latitude) {
		super();
		this.areaId = areaId;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}
