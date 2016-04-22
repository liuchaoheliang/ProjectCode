/**
 * Project Name:coremodule-bank
 * File Name:ProcessVoReq.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015-11-6上午11:21:13
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * ClassName:ProcessVoReq
 * Reason:	审核流程请求实体
 * Date:     2015-11-6 上午11:21:13
 * @author   wufei
 * @version  
 * @see 	 
 */
public class ProcessVoReq implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	
	private String clientId;
	
	/** 流程类型:1-商户,2-门店,3-团购商品,4-预售商品  */
	private String type; 
	
	/** 类型详情:0-新增,1-更新 */
	private String typeDetail;
	
	/**状态0-禁用1-启用*/
	private String status;
	

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	

}
