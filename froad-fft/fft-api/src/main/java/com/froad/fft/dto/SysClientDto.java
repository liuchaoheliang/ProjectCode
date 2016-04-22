package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author larry
 * <p>客户端信息</p>
 */
public class SysClientDto implements Serializable{
	private Long id;
	private Date createTime;//创建时间
	private String name;//资金机构名称
	private String number;//客户端编号
	private String shortLetter;//字母简称
	private Boolean isEnabled;//是否启用
	private String area;//客户端所属地
	private String remark;//备注
	
	
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getShortLetter() {
		return shortLetter;
	}
	public void setShortLetter(String shortLetter) {
		this.shortLetter = shortLetter;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
