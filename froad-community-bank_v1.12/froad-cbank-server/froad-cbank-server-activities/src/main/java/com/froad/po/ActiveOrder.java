package com.froad.po;

import java.util.Date;



/**
 * CbActiveOrder entity. 
 */
public class ActiveOrder  implements java.io.Serializable {


    // Fields    

     /**
	 * @Fields serialVersionUID : TODO
	 */
	
	
	private static final long serialVersionUID = 1L;
	private String id;
     private String clientId;
     private Date createTime;
     private Date updateTime;
     private String orderId;
     private Boolean status;
     private Integer orderOriMoney;
     private Integer orderMoney;
     private Integer orderOriModifyMoney;
     private Integer orderModifyMoney;
     private Date payTime;
     private Long memberCode;
     private String memberName;
     private String phone;
     private String detail;
     private String payBillNo;
     private String returnBillNo;
     private Double returnMoney;


    // Constructors

    /** default constructor */
    public ActiveOrder() {
    }

	/** minimal constructor */
    public ActiveOrder(String clientId, Date createTime, Date updateTime, String orderId, Boolean status, Integer orderOriMoney, Integer orderMoney, Integer orderOriModifyMoney, Integer orderModifyMoney, Date payTime, Long memberCode, String phone, String detail) {
        this.clientId = clientId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.orderId = orderId;
        this.status = status;
        this.orderOriMoney = orderOriMoney;
        this.orderMoney = orderMoney;
        this.orderOriModifyMoney = orderOriModifyMoney;
        this.orderModifyMoney = orderModifyMoney;
        this.payTime = payTime;
        this.memberCode = memberCode;
        this.phone = phone;
        this.detail = detail;
    }
    
    /** full constructor */
    public ActiveOrder(String clientId, Date createTime, Date updateTime, String orderId, Boolean status, Integer orderOriMoney, Integer orderMoney, Integer orderOriModifyMoney, Integer orderModifyMoney, Date payTime, Long memberCode, String memberName, String phone, String detail) {
        this.clientId = clientId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.orderId = orderId;
        this.status = status;
        this.orderOriMoney = orderOriMoney;
        this.orderMoney = orderMoney;
        this.orderOriModifyMoney = orderOriModifyMoney;
        this.orderModifyMoney = orderModifyMoney;
        this.payTime = payTime;
        this.memberCode = memberCode;
        this.memberName = memberName;
        this.phone = phone;
        this.detail = detail;
    }

   
    // Property accessors
    
    
    

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    

    public String getClientId() {
        return this.clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    

    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    

    public String getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    

    public Boolean getStatus() {
        return this.status;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    

    public Integer getOrderOriMoney() {
        return this.orderOriMoney;
    }
    
    public void setOrderOriMoney(Integer orderOriMoney) {
        this.orderOriMoney = orderOriMoney;
    }
    
    

    public Integer getOrderMoney() {
        return this.orderMoney;
    }
    
    public void setOrderMoney(Integer orderMoney) {
        this.orderMoney = orderMoney;
    }
    
    

    public Integer getOrderOriModifyMoney() {
        return this.orderOriModifyMoney;
    }
    
    public void setOrderOriModifyMoney(Integer orderOriModifyMoney) {
        this.orderOriModifyMoney = orderOriModifyMoney;
    }
    
    

    public Integer getOrderModifyMoney() {
        return this.orderModifyMoney;
    }
    
    public void setOrderModifyMoney(Integer orderModifyMoney) {
        this.orderModifyMoney = orderModifyMoney;
    }
    
    

    public Date getPayTime() {
        return this.payTime;
    }
    
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
    
    

    public Long getMemberCode() {
        return this.memberCode;
    }
    
    public void setMemberCode(Long memberCode) {
        this.memberCode = memberCode;
    }
    
    

    public String getMemberName() {
        return this.memberName;
    }
    
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    
    

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    

    public String getDetail() {
        return this.detail;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }

	public String getPayBillNo() {
		return payBillNo;
	}

	public void setPayBillNo(String payBillNo) {
		this.payBillNo = payBillNo;
	}

	public String getReturnBillNo() {
		return returnBillNo;
	}

	public void setReturnBillNo(String returnBillNo) {
		this.returnBillNo = returnBillNo;
	}

	public Double getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(Double returnMoney) {
		this.returnMoney = returnMoney;
	}
   








}