package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class ProductListVo extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 商品名称 */
	private String name;
	/** 客户端id */
	private String clientId;
	/** 商品类型 "1","团购", "2","预售", "3","名优特惠", "4","在线积分兑换", "5","网点礼品"*/
	private String type;
	/** 商品分类 */
	private String productCotegory;
	/** 是否上架 0未上架,1已上架,2已下架,3已删除 */
	private Integer isMarketable;
	/** 积分范围 默认全部:0, 100以下:1, 100-500:2, 500-1000:3, 1000以上:4*/
	private Integer scoreScope;
	/** 审核状态 全部：-1,待审核：0,审核通过：1,审核未通过：2,未提交审核 */
	private Integer auditState;
	/** 录入渠道 ：1- 社区银行运营支撑平台,2- 银行管理平台,3-商户PC,4-商户h5,5-个人PC,6-个人h5*/
	private String platType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getProductCotegory() {
		return productCotegory;
	}

	public void setProductCotegory(String productCotegory) {
		this.productCotegory = productCotegory;
	}

	public Integer getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Integer isMarketable) {
		this.isMarketable = isMarketable;
	}

	public Integer getScoreScope() {
		return scoreScope;
	}

	public void setScoreScope(Integer scoreScope) {
		this.scoreScope = scoreScope;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public String getPlatType() {
		return platType;
	}

	public void setPlatType(String platType) {
		this.platType = platType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
