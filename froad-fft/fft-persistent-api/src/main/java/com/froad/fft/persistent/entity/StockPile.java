package com.froad.fft.persistent.entity;

import java.util.Date;
import java.util.List;

/**
 * 库存
 * @author FQ
 *
 */
public class StockPile extends BaseEntity {
	
	public enum WarnType{
		/**
		 * 固定
		 */
		fixed,
		
		/**
		 * 比例
		 */
		scale
	}
	
	private Long productId;//商品ID
	private Long merchantOutletId;//名店ID
	private Integer quantity;//库存时时数量

	
	private Date lastIncomeTime;//最后入库时间
	private Date lastOutcomeTime;//最后一次出库时间
	
	private WarnType warnType;//库存警告类型
	private String warnValue;//警告值
	private Integer totalQuantity;//总库存
	
	private String remark;


	private List<Long> merchantOutletIds;//根据id集合查询门店库存
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getMerchantOutletId() {
		return merchantOutletId;
	}

	public void setMerchantOutletId(Long merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getLastIncomeTime() {
		return lastIncomeTime;
	}

	public void setLastIncomeTime(Date lastIncomeTime) {
		this.lastIncomeTime = lastIncomeTime;
	}

	public Date getLastOutcomeTime() {
		return lastOutcomeTime;
	}

	public void setLastOutcomeTime(Date lastOutcomeTime) {
		this.lastOutcomeTime = lastOutcomeTime;
	}

	public WarnType getWarnType() {
		return warnType;
	}

	public void setWarnType(WarnType warnType) {
		this.warnType = warnType;
	}

	public String getWarnValue() {
		return warnValue;
	}

	public void setWarnValue(String warnValue) {
		this.warnValue = warnValue;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Long> getMerchantOutletIds() {
		return merchantOutletIds;
	}

	public void setMerchantOutletIds(List<Long> merchantOutletIds) {
		this.merchantOutletIds = merchantOutletIds;
	}

}
