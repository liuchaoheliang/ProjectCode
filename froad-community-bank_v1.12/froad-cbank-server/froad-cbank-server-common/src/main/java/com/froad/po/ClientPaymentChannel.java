package com.froad.po;



/**
 * 客户端支付渠道po
 * CbClientPaymentChannel entity. 
 */

public class ClientPaymentChannel  implements java.io.Serializable {


    // Fields    

     private Long id; //自增主键id
     private String clientId;  //客户端id
     private String paymentChannelId;  //支付渠道id
     private String name;	//资金机构名称
     private String fullName; //资金机构全名
     private String type;	//渠道类型
     private String icoUrl;	//支付方式的图标
     private String paymentOrgNo; //资金机构代号(支付系统)
     private Boolean isFroadCheckToken; //是否方付通验码
     private String pointRate; //积分兑换比较(实时获取)
     private Boolean isDelete; //是否删除0-未删除1-删除


    // Constructors


	/** default constructor */
    public ClientPaymentChannel() {
    }

	/** minimal constructor */
    public ClientPaymentChannel(String clientId, String paymentChannelId,String name, String fullName, String type, boolean isFroadCheckToken, String pointRate,boolean isDelete) {
        this.clientId = clientId;
        this.paymentChannelId=paymentChannelId;
        this.name = name;
        this.fullName = fullName;
        this.type = type;
        this.isFroadCheckToken = isFroadCheckToken;
        this.pointRate = pointRate;
        this.isDelete = isDelete;
    }
    
    /** full constructor */
    public ClientPaymentChannel(String clientId, String paymentChannelId,String name, String fullName, String type, String icoUrl, String paymentOrgNo, boolean isFroadCheckToken, String pointRate,boolean isDelete) {
        this.clientId = clientId;
        this.paymentChannelId=paymentChannelId;
        this.name = name;
        this.fullName = fullName;
        this.type = type;
        this.icoUrl = icoUrl;
        this.paymentOrgNo = paymentOrgNo;
        this.isFroadCheckToken = isFroadCheckToken;
        this.pointRate = pointRate;
        this.isDelete = isDelete;
    }

   
    // Property accessors
    
    
    


	public String getPaymentChannelId() {
		return paymentChannelId;
	}

	public void setPaymentChannelId(String paymentChannelId) {
		this.paymentChannelId = paymentChannelId;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    

    public String getClientId() {
        return this.clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    

    public String getFullName() {
        return this.fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    

    public String getIcoUrl() {
        return this.icoUrl;
    }
    
    public void setIcoUrl(String icoUrl) {
        this.icoUrl = icoUrl;
    }
    
    

    public String getPaymentOrgNo() {
        return this.paymentOrgNo;
    }
    
    public void setPaymentOrgNo(String paymentOrgNo) {
        this.paymentOrgNo = paymentOrgNo;
    }
    
    

    public Boolean getIsFroadCheckToken() {
        return this.isFroadCheckToken;
    }
    
    public void setIsFroadCheckToken(Boolean isFroadCheckToken) {
        this.isFroadCheckToken = isFroadCheckToken;
    }
    
    

    public String getPointRate() {
        return this.pointRate;
    }
    
    public void setPointRate(String pointRate) {
        this.pointRate = pointRate;
    }
   








}