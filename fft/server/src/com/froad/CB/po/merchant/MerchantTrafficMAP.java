package com.froad.CB.po.merchant;

import java.io.Serializable;


	/**
	 * 类描述：商户地图信息
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:33:45 PM 
	 */
public class MerchantTrafficMAP implements Serializable{
    
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;
    private String merchantId;//关联 FBU_Merchant
    private String serviceProvider;//服务提供商，例如：百度
    private String coordinate;//坐标值
    private String staffgauge;//标尺数据
    private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
    private String createTime;
    private String updateTime;
    private String remark;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getStaffgauge() {
		return staffgauge;
	}

	public void setStaffgauge(String staffgauge) {
		this.staffgauge = staffgauge;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

   
}