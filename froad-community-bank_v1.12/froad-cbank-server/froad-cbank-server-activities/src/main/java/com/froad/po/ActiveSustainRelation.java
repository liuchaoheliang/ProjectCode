package com.froad.po;

import java.util.Date;


/**
 * @ClassName: ActiveSustainRelation
 * @Description: 支持参与的促销活动
 * @author froad-shenshaocheng 2015年11月26日
 * @modify froad-shenshaocheng 2015年11月26日
 */
public class ActiveSustainRelation {
	/** 主键ID */
	private Long id;
	/** 客户端ID */
	private String clientId;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 活动ID */
	private String activeId;
	/** 支持活动类型 */
	private String sustainActiveType;
	/** 支持活动ID */
	private String sustainActiveId;
	/** 支持活动名称 */
	private String sustainActiveName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getSustainActiveType() {
		return sustainActiveType;
	}

	public void setSustainActiveType(String sustainActiveType) {
		this.sustainActiveType = sustainActiveType;
	}

	public String getSustainActiveId() {
		return sustainActiveId;
	}

	public void setSustainActiveId(String sustainActiveId) {
		this.sustainActiveId = sustainActiveId;
	}

	public String getSustainActiveName() {
		return sustainActiveName;
	}

	public void setSustainActiveName(String sustainActiveName) {
		this.sustainActiveName = sustainActiveName;
	}

}
