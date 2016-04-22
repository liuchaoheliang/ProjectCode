package com.froad.CB.po;

import com.froad.CB.common.Pager;

/**
 * *******************************************************
 *<p> 工程: communityBusiness </p>
 *<p> 类名: PresellDeliveryPoint.java </p>
 *<p> 描述: *-- <b>商品预设提货点</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014-2-24 上午10:17:46 </p>
 ********************************************************
 */
public class PresellDelivery extends Pager{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;        //数据主键
	private String name;//提货点名称
	private String address;    //详细地址
	private String telephone;  //电话
	private String businessTime;//营业时间
	private String coordinate;//地图坐标
	private String state;//状态  0停用，1启用，2录入
	private String createTime; //数据创建时间
	private String updateTime; //数据更新时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	
	
}
