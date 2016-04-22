package com.froad.cbank.coremodule.normal.boss.pojo.label;
/**
 * 商圈标签vo
 * @author liaopeixin
 *	@date 2015年10月22日 下午4:55:41
 */
public class BusinessZoneTagListVo {
	/**商户商圈标签ID*/
	private String  id;

	/**商户商圈名称*/
	private String  tagName;
	
	/**客户端名称*/
	private String  clientName;
	
	/**所在地区*/
	private String areaName;

	/**描述*/
	private String  desc;

	/**状态：启用1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5*/
	private String  status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
