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
 * @Title: Location.java
 * @Package com.froad.po.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年3月31日
 */

package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;


/**    
 * <p>Title: Location.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月31日 下午2:13:38   
 */
@JSONType(orders = {"longitude", "latitude"})
public class Location {
	private static final long serialVersionUID = -1216935775793538278L;
	
	@JSONField(name = "longitude", serialize = true, deserialize = true)
	private Double longitude;

	@JSONField(name = "latitude", serialize = true, deserialize = true)
	private Double latitude;
	
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Location(Double longitude, Double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	
}
