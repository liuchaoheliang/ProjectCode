package com.froad.po.settlement;

import java.util.List;

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
	
	private SettlementStatus settleState;	// 结算状态
	
	private String billNo;	// 账单编号
	
	private String type; // 结算类型
	
	private String ticketId; // 券号
	
	private List<String> inSettleState; //结算状态[等于](同settleState字段)
	
	private List<String> notInSettleState; //结算状态[不等于](同settleState字段)

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

	public SettlementStatus getSettleState() {
		return settleState;
	}

	public void setSettleState(SettlementStatus settleState) {
		this.settleState = settleState;
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

	/**
	 * type.
	 *
	 * @return  the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * type.
	 *
	 * @param   type    the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * ticketId.
	 *
	 * @return  the ticketId
	 */
	public String getTicketId() {
		return ticketId;
	}

	/**
	 * ticketId.
	 *
	 * @param   ticketId    the ticketId to set
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * inSettleState.
	 *
	 * @return  the inSettleState
	 */
	public List<String> getInSettleState() {
		return inSettleState;
	}

	/**
	 * inSettleState.
	 *
	 * @param   inSettleState    the inSettleState to set
	 */
	public void setInSettleState(List<String> inSettleState) {
		this.inSettleState = inSettleState;
	}

	/**
	 * notInSettleState.
	 *
	 * @return  the notInSettleState
	 */
	public List<String> getNotInSettleState() {
		return notInSettleState;
	}

	/**
	 * notInSettleState.
	 *
	 * @param   notInSettleState    the notInSettleState to set
	 */
	public void setNotInSettleState(List<String> notInSettleState) {
		this.notInSettleState = notInSettleState;
	}

}

