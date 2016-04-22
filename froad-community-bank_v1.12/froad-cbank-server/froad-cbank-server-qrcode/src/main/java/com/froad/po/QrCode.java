package com.froad.po;

import java.util.Date;

public class QrCode {
	/**
	 * 生成时间
	 */
	private Date createTime = null;
	
	/**
	 * 关键词
	 * 	1)预售:00+product_id
	 *  2)团购:01+product_id
	 *  3)面对面:10+product_id
	 */
	private String keyword = null;
	
	/**
	 * 银行代码
	 */
	private String clientId = null;
	
	/**
	 * 二维码图片地址
	 */
	private String url = null;
	
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
