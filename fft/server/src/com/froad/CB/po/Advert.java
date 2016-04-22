package com.froad.CB.po;

import java.io.Serializable;

import com.froad.CB.common.Pager;

/** 
 * @author FQ 
 * @date 2013-2-4 上午09:47:29
 * @version 1.0
 * 广告
 */
public class Advert  extends Pager implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;//主键 自增100001001开始
	private String type;//类型 1-商户 2-商品 3-活动
	private String position;//位置 1-顶部 2-中间 3-底部
	private String name;//名称
	private String images;//图片地址
	private String link;//链接地址
	private String isBlankTarge;//是否在新窗口中打开 0-否 1-是
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String clickCount;//点击次数
	
	private String state;//状态
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private	String remark;//备注
	private String module;//所属模块 (1-积分返利 2-积分兑换)
	
	
	private Integer priority;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getIsBlankTarge() {
		return isBlankTarge;
	}
	public void setIsBlankTarge(String isBlankTarge) {
		this.isBlankTarge = isBlankTarge;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getClickCount() {
		return clickCount;
	}
	public void setClickCount(String clickCount) {
		this.clickCount = clickCount;
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
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	
}
