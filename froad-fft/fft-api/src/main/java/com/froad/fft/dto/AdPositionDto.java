package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告位
 * @author FQ
 *
 */
public class AdPositionDto implements Serializable{
	
	private Long id;
	private Date createTime;
	private String name;//名称
	private Integer width;//宽度 
	private Integer height;//高度 
	private String description;//描述
	private String positionStyle;//样式 
	private Long clientId;//所属客户端
	
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
	
	public Long getClientId()
    {
        return clientId;
    }

    public void setClientId(Long clientId)
    {
        this.clientId = clientId;
    }
}
