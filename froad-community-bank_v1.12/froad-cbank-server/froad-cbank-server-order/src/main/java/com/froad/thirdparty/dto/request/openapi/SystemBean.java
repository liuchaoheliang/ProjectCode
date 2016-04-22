/**
 * 文件名称:SystemBean.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-9
 * 历史修改:  
 */
package com.froad.thirdparty.dto.request.openapi;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class SystemBean {
	
	private String reqID;

	private String versions;

	private String client;

	private String partnerID;

	private String charset;

	private String signType;

	public String getReqID() {
		return reqID;
	}

	public void setReqID(String reqID) {
		this.reqID = reqID;
	}

	public String getVersions() {
		return versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
}
