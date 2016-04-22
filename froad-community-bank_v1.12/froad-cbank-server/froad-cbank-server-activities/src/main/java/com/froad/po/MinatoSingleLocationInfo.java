package com.froad.po;

import java.io.Serializable;

 /**
  * @ClassName: MinatoSingleLocationInfo
  * @Description: 经纬度信息PO
  * @author froad-shenshaocheng 2015年11月9日
  * @modify froad-shenshaocheng 2015年11月9日
 */
@SuppressWarnings("serial")
public class MinatoSingleLocationInfo implements Serializable {
	/**
	 * 经度
	 */
	private double longitude;
	/**
	 * 纬度
	 */
	private double latitude;

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
