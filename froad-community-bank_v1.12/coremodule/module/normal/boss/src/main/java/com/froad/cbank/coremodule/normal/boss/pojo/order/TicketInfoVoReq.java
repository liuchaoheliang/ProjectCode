package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 类描述：团购、预售券查询实体请求参数类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road.com.cn
 * @time: 2015-5-13下午4:47:51
 */
public class TicketInfoVoReq extends Page implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -55780428022738550L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 客户端ID
	 */
	private String clientId; // required
	/**
	 * 请求来源，1-银行端，2-商户端，3-个人端
	 */
	private String resource; // required
	/**
	 * 查找选项
	 */
	private String source; // required
	/**
	 * 会员编号(可null)
	 */
	private String memberCode; // required
	/**
	 * 商户ID(可null)
	 */
	private String merchantId; // required
	private String merchantName;
	/**
	 * 门店ID(可null)
	 */
	private String outletId; // required
	/**
	 * 机构码
	 */
	private String orgCode; // required
	/**
	 * 子订单号
	 */
	private String subOrderId; // required
	// /**
	// * 开始时间(如source !=2，可传null；否则传long时间值)
	// */
	// private String startDate; // required
	// /**
	// * 结束时间 (如source !=2，可null；否则传long时间值)
	// */
	// private String endDate; // required
	/**
	 * 类型
	 */
	private String type; // required
	/**
	 * 券状态
	 */
	private String ticketStatu; // required
	// /**
	// * 当前页码(如非分页查找，传null；否则传int值)
	// */
	// private String pageNumber; // required
	// /**
	// * 每页记录数(如非分页查找，传null；否则传int值)
	// */
	// private String pageSize; // required
	/**
	 * 根据提货码查找相关券
	 */
	private String ticketNo; // required
	/**
	 * 根据操作员券
	 */
	private String merchantUserName; // required
	private String userName;
	private String phone;
	private String orderCode;
	private String token;
	private String userId;

	private long begDate;
	private long startDate;
	private long endDate;
	private String beginTime;
	private String endTime;
	private String productName; //商品名称
	private String settlementStatus;
	
	private String orgCodes;
	
	
	public String getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(String orgCodes) {
		this.orgCodes = orgCodes;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTicketStatu() {
		return ticketStatu;
	}

	public void setTicketStatu(String ticketStatu) {
		this.ticketStatu = ticketStatu;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 结算状态
	 * @return
	 */
	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
