package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

public class BankAuditProductResVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7277841123895906832L;
	private String auditNumber;// 审核流水号
	private String productId;// 商品编号
	private String auditStatus;// 审核状态
	private String creater;// 创建人
	private Long createTime;// 创建时间
	private String orgCode;// 所属机构
	private String productName;// 商品名称
	private String oldProductName;//
	private String productFullName;// 商品全称
	private String oldProductFullName;
	private String description;// 副标题
	private String oldDescription;
	private int storeNum;// 库存量
	private int oldStoreNum;
	private Long startDate;// 团购开始时间
	private Long oldStartDate;
	private Long endDate;// 团购结束时间
	private Long oldEndDate;
	private Long startCodeTime;// 验证码有效时间(开始)
	private Long oldStartCodeTime;// 验证码有效时间
	private Long endCodeTime;// 验证码有效时间(结束)
	private Long oldEndCodeTime;
	private double marketPrice;// 市场价
	private double oldMarketPrice;
	private double groupPrice;// 团购价
	private double oldGroupPrice;
	private String productCategory;// 商品分类
	private String oldProductCategory;
	private String productKnow;// 购买须知
	private String oldProductKnow;
	private String productDetails;// 商品详情
	private List<String> photos;// 商品图片
	private List<BankTaskResVo> taskList;// 任务单列表
    private int max;//限购数量
    private int oldMax;
	public int getOldMax() {
		return oldMax;
	}

	public void setOldMax(int oldMax) {
		this.oldMax = oldMax;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getAuditNumber() {
		return auditNumber;
	}

	public void setAuditNumber(String auditNumber) {
		this.auditNumber = auditNumber;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOldProductName() {
		return oldProductName;
	}

	public void setOldProductName(String oldProductName) {
		this.oldProductName = oldProductName;
	}

	public String getProductFullName() {
		return productFullName;
	}

	public void setProductFullName(String productFullName) {
		this.productFullName = productFullName;
	}

	public String getOldProductFullName() {
		return oldProductFullName;
	}

	public void setOldProductFullName(String oldProductFullName) {
		this.oldProductFullName = oldProductFullName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOldDescription() {
		return oldDescription;
	}

	public void setOldDescription(String oldDescription) {
		this.oldDescription = oldDescription;
	}

	public int getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}

	public int getOldStoreNum() {
		return oldStoreNum;
	}

	public void setOldStoreNum(int oldStoreNum) {
		this.oldStoreNum = oldStoreNum;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getOldStartDate() {
		return oldStartDate;
	}

	public void setOldStartDate(Long oldStartDate) {
		this.oldStartDate = oldStartDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Long getOldEndDate() {
		return oldEndDate;
	}

	public void setOldEndDate(Long oldEndDate) {
		this.oldEndDate = oldEndDate;
	}

	public Long getStartCodeTime() {
		return startCodeTime;
	}

	public void setStartCodeTime(Long startCodeTime) {
		this.startCodeTime = startCodeTime;
	}

	public Long getOldStartCodeTime() {
		return oldStartCodeTime;
	}

	public void setOldStartCodeTime(Long oldStartCodeTime) {
		this.oldStartCodeTime = oldStartCodeTime;
	}

	public Long getEndCodeTime() {
		return endCodeTime;
	}

	public void setEndCodeTime(Long endCodeTime) {
		this.endCodeTime = endCodeTime;
	}

	public Long getOldEndCodeTime() {
		return oldEndCodeTime;
	}

	public void setOldEndCodeTime(Long oldEndCodeTime) {
		this.oldEndCodeTime = oldEndCodeTime;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public double getOldMarketPrice() {
		return oldMarketPrice;
	}

	public void setOldMarketPrice(double oldMarketPrice) {
		this.oldMarketPrice = oldMarketPrice;
	}

	public double getGroupPrice() {
		return groupPrice;
	}

	public void setGroupPrice(double groupPrice) {
		this.groupPrice = groupPrice;
	}

	public double getOldGroupPrice() {
		return oldGroupPrice;
	}

	public void setOldGroupPrice(double oldGroupPrice) {
		this.oldGroupPrice = oldGroupPrice;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getOldProductCategory() {
		return oldProductCategory;
	}

	public void setOldProductCategory(String oldProductCategory) {
		this.oldProductCategory = oldProductCategory;
	}

	public String getProductKnow() {
		return productKnow;
	}

	public void setProductKnow(String productKnow) {
		this.productKnow = productKnow;
	}

	public String getOldProductKnow() {
		return oldProductKnow;
	}

	public void setOldProductKnow(String oldProductKnow) {
		this.oldProductKnow = oldProductKnow;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}

	public List<BankTaskResVo> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<BankTaskResVo> taskList) {
		this.taskList = taskList;
	}

}
