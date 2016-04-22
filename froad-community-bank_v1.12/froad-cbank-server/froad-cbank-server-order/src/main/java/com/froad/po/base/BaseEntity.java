package com.froad.po.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础数据属性
* <p>Function: BaseEntity</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2014年12月22日 下午4:33:49
* @version 1.0
 */
public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 数据ID，不需要主动维护
	 */
	private Long id;
	
	/**
	 * 数据创建时间，不需要主动维护
	 */
	private Date createTime;
	
	/**
	 * 数据更新时间，不需要主动维护
	 */
	private Date updateTime;
	
	/**
	 * 数据所属客户端ID
	 */
	private String clientId;
	
	/**
	 *  开始时间
	 */
	private Date begDate;
	
	/**
	 *  结束时间
	 */
	private Date endDate;
	
	/**
	 *  比较时间 字段
	 */
	private String dateProperty = "create_time";
	
	
	private String[] dataProperties = new String[]{"create_time","payment_time","refund_time"};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getBegDate() {
		return begDate;
	}

	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	

	public String getDateProperty() {
		return dateProperty;
	}

	public String[] getDataProperties() {
		return dataProperties;
	}
	
	public void setDateProperty(String dateProperty) {
		try {
			if(dateProperty == null){
				return;
			}
			for(String str : dataProperties){
				if(str.equals(dateProperty.trim())){
					this.dateProperty = dateProperty;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("检查日期值属性异常...");
		}
	}
}
