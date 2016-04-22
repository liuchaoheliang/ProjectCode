package com.froad.cbank.coremodule.normal.boss.pojo.label;

/**
 * 商圈标签详情res
 * @author liaopeixin
 *	@date 2015年10月22日 下午5:17:55
 */
public class BusinessZoneTagDetailsRes {
	/**
	 * id
	 */
	private String id;
	/**客户端Id*/
	private String clientName;
	
	/**商圈标签名称*/
	private String tagName;

	/**是否启用:启用1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5*/
	private String status;

	/**标签序号*/
	private String sortValue;

	/**所在区域一级区域Id*/
	private String fareadId;

	/**所在区域二级区域Id*/
	private String sareadId;

	/**所在区域三级区域Id*/
	private String tareadId;

	/**所在区域四级区域Id*/
	private String oareadId;

	/**描述*/
	private String desc;

	private String clientId;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

	public String getFareadId() {
		return fareadId;
	}

	public void setFareadId(String fareadId) {
		this.fareadId = fareadId;
	}

	public String getSareadId() {
		return sareadId;
	}

	public void setSareadId(String sareadId) {
		this.sareadId = sareadId;
	}

	public String getTareadId() {
		return tareadId;
	}

	public void setTareadId(String tareadId) {
		this.tareadId = tareadId;
	}

	public String getOareadId() {
		return oareadId;
	}

	public void setOareadId(String oareadId) {
		this.oareadId = oareadId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	
}
