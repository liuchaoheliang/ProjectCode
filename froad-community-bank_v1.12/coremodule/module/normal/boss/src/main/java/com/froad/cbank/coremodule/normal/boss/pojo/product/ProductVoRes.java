package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;
import java.util.List;

import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;

public class ProductVoRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productId;// 主键Id
	private String orgName;// 所属机构
	private String clientId;// 客户端id
	private String name;// 商品名称
	private String type;// 商品类型
	private String productType;// 商品分类
	private String productSn;// 商品编号
	// private String status;// 启用状态
	private String isMarketable;// 上下架状态
	private String createTime;// 创建时间
	private String creater;// 创建人
	private String price;// 积分/价格
	private Integer store;// 库存量
	private String briefIntroduction;// 商品简介(副标题)
	private String introduction;// 商品详情
	private String buyKnow;// 需知
	private String auditComment;// 审核备注
	private String auditState;// 审核状态
	private String beginTime;// 兑换开始时间
	private String endTime;// 兑换结束时间
	private Integer max;// 最大限购量
	private List<FileVo> files;// 图片

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getProductSn() {
		return productSn;
	}
	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
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

	public String getBuyKnow() {
		return buyKnow;
	}

	public void setBuyKnow(String buyKnow) {
		this.buyKnow = buyKnow;
	}

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public List<FileVo> getFiles() {
		return files;
	}

	public void setFiles(List<FileVo> files) {
		this.files = files;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
