package com.froad.cbank.coremodule.normal.boss.pojo.prodiver;

import java.io.Serializable;

/**
 * 
 * @ClassName: ProviderListRes
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年12月2日 下午5:30:35 
 * @desc <p>供应商列表查询返回实体</p>
 */
public class ProviderListRes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4922014067690248469L;
	/**
	   * id *
	   */
	  private long id; // optional
	  /**
	   * 创建时间 *
	   */
	  private String createTime; // optional
	  /**
	   * 更新时间 *
	   */
	  private String updateTime; // optional
	  /**
	   * 供应商ID *
	   */
	  private String merchantId; // optional
	  /**
	   * 供应商名称 *
	   */
	  private String merchantName; // optional
	  /**
	   * 地址 *
	   */
	  private String address; // optional
	  /**
	   * 电话 *
	   */
	  private String phone; // optional
	  /**
	   * 状态: 0禁用, 1启用 *
	   */
	  private String status; // optional
	  /**
	   * 供应商描述 *
	   */
	  private String description; // optional
	  
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	  
	  
}
