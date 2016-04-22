package com.froad.po.settlement;

import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.SettlementStatus;

/**
 *  结算查询条件
  * @ClassName: SettlementReq
  * @Description: TODO
  * @author share 2015年3月28日
  * @modify share 2015年3月28日
 */
public class SettlementReq {
	
	private MongoPage page;	// 分页条件
	
	private String orderId;	// 订单编号
	
	private long begDate;	// 开始日期
	
	private long endDate;	// 结束日期
	
	private String clientId;	// 银行客户端
	
	private String merchantName;	// 商户名称
	
	private String outletName;		// 门店名称
	
	private String productName;		// 商品名称
	
	private SettlementStatus type;	// 结算状态
	
	private String billNo;	// 账单编号

	public MongoPage getPage() {
		return page;
	}

	public void setPage(MongoPage page) {
		this.page = page;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public SettlementStatus getType() {
		return type;
	}

	public void setType(SettlementStatus type) {
		this.type = type;
	}

	public long getBegDate() {
		return begDate;
	}

	public void setBegDate(long begDate) {
		this.begDate = begDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	
	
}

