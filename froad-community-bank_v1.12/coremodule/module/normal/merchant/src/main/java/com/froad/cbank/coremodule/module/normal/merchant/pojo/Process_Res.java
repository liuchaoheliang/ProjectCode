package com.froad.cbank.coremodule.module.normal.merchant.pojo;

/**
 * @author Administrator
 *
 */
public class Process_Res {

	/**
	 * id
	 */
	private long id; // optional
	/**
	 * createTime
	 */
	private long createTime; // optional
	/**
	 * updateTime
	 */
	private long updateTime; // optional
	/**
	 * clientId
	 */
	private String clientId; // optional
	/**
	 * processId
	 */
	private String processId; // optional
	/**
	 * parentProcessId
	 */
	private String parentProcessId; // optional
	/**
	 * name
	 */
	private String name; // optional
	/**
	 * displayName
	 */
	private String displayName; // optional
	/**
	 * type
	 */
	private String type; // optional
	/**
	 * typeDetail
	 */
	private String typeDetail; // optional
	/**
	 * status
	 */
	private String status; // optional
	/**
	 * version
	 */
	private int version; // optional
	/**
	 * creator
	 */
	private String creator; // optional

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getParentProcessId() {
		return parentProcessId;
	}

	public void setParentProcessId(String parentProcessId) {
		this.parentProcessId = parentProcessId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeDetail() {
		return typeDetail;
	}

	public void setTypeDetail(String typeDetail) {
		this.typeDetail = typeDetail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

}
