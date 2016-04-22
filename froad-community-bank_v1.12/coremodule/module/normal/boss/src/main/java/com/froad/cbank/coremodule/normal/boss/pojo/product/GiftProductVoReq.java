package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;
import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class GiftProductVoReq extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 客户端id
	 */
	@NotEmpty(value = "客户端id不能为空!")
	private String clientId;
	/**
	 * 商户id
	 */
	private String merchantId;
	/**
	 * 商品id
	 */
	private String productId;
	/**
	 * 商品类型
	 */
	@NotEmpty(value = "商品类型不能为空!")
	private String type;
	/**
	 * 商品分类
	 */
	private String productCategory;
	/**
	 * 商品名称
	 */
	@NotEmpty(value = "商品名称不能为空!")
	private String name;
	/**
	 * 上下架状态
	 */
	private Integer isMarketable;
	/**
	 * 积分
	 */
	@NotEmpty(value = "兑换积分不能为空!")
	private String price;
	/**
	 * 库存量
	 */
	@NotEmpty(value = "库存量不能为空!")
	private String store;
	/**
	 * 最大限购量
	 */
	private Integer max;
	/**
	 * 商品简介(副标题)
	 */
	private String briefIntroduction;
	/**
	 * 商品详情
	 */
	private String introduction;
	/**
	 * 所属机构
	 */
	@NotEmpty(value = "所属机构不能为空!")
	private String orgCode;
	/**
	 * 商品图片
	 */
	@NotEmpty(value = "商品图片不能为空!")
	private List<FileVo> files;
	/**
	 * 积分范围
	 */
	private Integer scoreScope;
	/**
	 * 购买须知
	 */
	private String buyKnow;
	/**
	 * 审核状态
	 */
	private Integer auditState;// 审核状态

	private long begDate;
	private long startDate;
	private long endDate;
	private Long beginTime;
	private Long endTime;

	public long getBegDate() {
		return begDate;
	}

	public void setBegDate(long begDate) {
		this.begDate = begDate;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Integer isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public List<FileVo> getFiles() {
		return files;
	}

	public void setFiles(List<FileVo> files) {
		this.files = files;
	}

	public Integer getScoreScope() {
		return scoreScope;
	}

	public void setScoreScope(Integer scoreScope) {
		this.scoreScope = scoreScope;
	}

	public String getBuyKnow() {
		return buyKnow;
	}

	public void setBuyKnow(String buyKnow) {
		this.buyKnow = buyKnow;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
