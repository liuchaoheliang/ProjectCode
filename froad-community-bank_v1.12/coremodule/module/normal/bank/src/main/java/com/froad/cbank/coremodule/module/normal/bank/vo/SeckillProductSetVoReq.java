package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 秒杀商品批量设置请求
 * @author wangzhangxu
 * @date 2015-5-6 上午10:51:06
 */
public class SeckillProductSetVoReq extends BaseVo implements Serializable {
	
	private static final long serialVersionUID = -7773576794520607144L;
	
	private List<SeckillProductVoReq> productList;
	
	private String orgCode;
	
	public SeckillProductSetVoReq(){}

	public List<SeckillProductVoReq> getProductList() {
		return productList;
	}

	public void setProductList(List<SeckillProductVoReq> productList) {
		this.productList = productList;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	
}
