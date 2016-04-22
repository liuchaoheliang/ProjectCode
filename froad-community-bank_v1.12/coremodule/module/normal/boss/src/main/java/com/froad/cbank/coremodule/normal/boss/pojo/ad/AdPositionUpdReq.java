package com.froad.cbank.coremodule.normal.boss.pojo.ad;

import com.froad.cbank.coremodule.framework.common.valid.Regular;


/**
 * 广告位修改请求对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月21日 下午3:44:53
 */
public class AdPositionUpdReq {
	private long id;//广告位ID
	private int sizeLimit;//大小限制-单位为K
	private int width;//宽度
	private int height;//高度
	@Regular(reg = "^[\\s\\S]{0,100}$", value = "描述限100字内")
	private String description;//描述
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getSizeLimit() {
		return sizeLimit;
	}
	public void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
