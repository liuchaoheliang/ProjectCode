/**
 * 文件名称:FundsChannelDto.java
 * 文件描述: 资金渠道dto
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-2
 * 历史修改:  
 */
package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

import com.froad.fft.enums.trans.TransPayChannel;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class FundsChannelDto implements Serializable {

	private Long id;
	private Date createTime;

	private String shortName;// 资金机构名称
	private String fullName;// 资金机构全名
	private TransPayChannel channelType;// 渠道类型
	private String payOrg;// 资金机构编号
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
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public TransPayChannel getChannelType() {
		return channelType;
	}
	public void setChannelType(TransPayChannel channelType) {
		this.channelType = channelType;
	}
	public String getPayOrg() {
		return payOrg;
	}
	public void setPayOrg(String payOrg) {
		this.payOrg = payOrg;
	}
}
