/**
 * <p>Project: ubank</p>
 * <p>module: coremouule</p>
 * <p>@version: Copyright © 2015 F-Road All Rights Reserved</p>
 */
package com.froad.cbank.coremodule.normal.boss.pojo.ad;

import java.io.Serializable;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 
 * <p>标题: —— 标题</p>
 * <p>说明: —— 简要描述职责、应用范围、使用注意事项等</p>
 * <p>创建时间：2015-5-11下午2:39:57</p>
 * <p>作者: 高峰</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public class AdPositionRes extends Page implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long id;
	
	@NotEmpty(value = "银行端ID不能为空")
	public String clientId;
	
	@NotEmpty(value = "广告位名称不能为空")
	public String name;
	
	@NotEmpty(value = "宽度不能为空")
	public int width;
	
	@NotEmpty(value = "高度不能为空")
	public int height;
	public String positionStyle;
	public String positionPage;
	public int positionPoint;
	
	public String description;
	public boolean isEnable;
	
	@NotEmpty(value = "终端类型不能为空")
	public String terminalType;
	
	@NotEmpty(value = "大小限制不能为空")
	public int sizeLimit;

	
	public int getSizeLimit() {
		return sizeLimit;
	}
	public void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getPositionStyle() {
		return positionStyle;
	}
	public void setPositionStyle(String positionStyle) {
		this.positionStyle = positionStyle;
	}
	public String getPositionPage() {
		return positionPage;
	}
	public void setPositionPage(String positionPage) {
		this.positionPage = positionPage;
	}
	public int getPositionPoint() {
		return positionPoint;
	}
	public void setPositionPoint(int positionPoint) {
		this.positionPoint = positionPoint;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	
}
