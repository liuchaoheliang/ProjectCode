package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.DataState;

/**
 * 预售提货点
 * @author FQ
 *
 */
public class PresellDelivery extends BaseEntity {
	
	private String name;//提货点名称
	private String address;    //详细地址
	private String telephone;  //电话
	private String businessTime;//营业时间
	private String coordinate;//地图坐标
	private Integer orderValue;//排序
	
	private Long businessCircleId;//所属商圈ID
	private Long clientId;//所属客户端
	private DataState dataState;//状态

    private String director;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(String businessTime) {
		this.businessTime = businessTime;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	
	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public DataState getDataState() {
		return dataState;
	}

	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}

	public Long getBusinessCircleId() {
		return businessCircleId;
	}

	public void setBusinessCircleId(Long businessCircleId) {
		this.businessCircleId = businessCircleId;
	}
	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

    public String getDirector()
    {
        return director;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }
}
