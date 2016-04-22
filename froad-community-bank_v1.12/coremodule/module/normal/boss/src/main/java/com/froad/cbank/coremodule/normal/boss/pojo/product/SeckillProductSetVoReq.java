package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;
import java.util.List;

/**
 * 秒杀商品批量设置请求
 * @author wangzhangxu
 * @date 2015-5-6 上午10:51:06
 */
public class SeckillProductSetVoReq implements Serializable {
	
	private static final long serialVersionUID = -7773576794520607144L;
	
	private String clientId;
	
	private List<SeckillProductVoReq> productList;
	
	private String startDate;
	
	private String endDate;
	
	private String orgCode;
	
	public SeckillProductSetVoReq(){}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public List<SeckillProductVoReq> getProductList() {
		return productList;
	}

	public void setProductList(List<SeckillProductVoReq> productList) {
		this.productList = productList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	
}
