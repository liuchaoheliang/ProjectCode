package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.DataState;

/**
 * 广告位
 * 
 * @author FQ
 * 
 */
public class AdPosition extends BaseEntity {

	private String name;// 名称
	private Integer width;// 宽度
	private Integer height;// 高度
	private String description;// 描述
	private String positionStyle;// 样式

	private DataState dataState;// 数据状态：有效、删除
	private Long clientId;// 所属客户端

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPositionStyle() {
		return positionStyle;
	}

	public void setPositionStyle(String positionStyle) {
		this.positionStyle = positionStyle;
	}

	public DataState getDataState() {
		return dataState;
	}

	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
}
