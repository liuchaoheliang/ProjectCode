package com.froad.db.chonggou.entity;

import java.util.Date;


/**
 * 客户端信息表po
 * CbClient entity. 
 */

public class Client  implements java.io.Serializable {


    // Fields    

     private Long id; 				//自增主键id
     private Date createTime; 		//创建时间
     private String clientId; 		//客户端id
     private String uri;			//客户端uri
     private String name; 			//客户端名称
     private String pointPartnerNo; //积分平台商户号
     private String openapiPartnerNo; //支付平台商户号
     private String orderDisplay; 	//订单显示名
     private String appkey; 		//APPKEY（手机客户端）
     private String appsecret; 		//私钥（手机客户端）
     private String bankType;		//银行类型
     private String bankName; 		//银行名
     private String qrLogo; 		//银行logo图片地址
     private String returnUrl; 		//支付结果通知地址
     private String remark; 		//备注
     private String bankId; 		//积分平台银行代码
     private String settlePayOrg;


    // Constructors

    /** default constructor */
    public Client() {
    }

	/** minimal constructor */
    public Client(Date createTime, String clientId,String uri,String name, String pointPartnerNo, String openapiPartnerNo, String orderDisplay, String bankType) {
        this.createTime = createTime;
        this.clientId = clientId;
        this.uri=uri;
        this.name = name;
        this.pointPartnerNo = pointPartnerNo;
        this.openapiPartnerNo = openapiPartnerNo;
        this.orderDisplay = orderDisplay;
        this.bankType = bankType;
    }
    
    /** full constructor */
    public Client(Date createTime, String clientId,String uri,String name, String pointPartnerNo, String openapiPartnerNo, String orderDisplay, String appkey, String appsecret, String bankType, String bankName, String qrLogo, String returnUrl, String remark,String bankId) {
        this.createTime = createTime;
        this.clientId = clientId;
        this.uri=uri;
        this.name = name;
        this.pointPartnerNo = pointPartnerNo;
        this.openapiPartnerNo = openapiPartnerNo;
        this.orderDisplay = orderDisplay;
        this.appkey = appkey;
        this.appsecret = appsecret;
        this.bankType = bankType;
        this.bankName = bankName;
        this.qrLogo = qrLogo;
        this.returnUrl = returnUrl;
        this.remark = remark;
        this.bankId = bankId;
    }

   
    // Property accessors
    
    
    
    
    public String getClientId() {
		return clientId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

    

    public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    

    public String getPointPartnerNo() {
        return this.pointPartnerNo;
    }
    
    public void setPointPartnerNo(String pointPartnerNo) {
        this.pointPartnerNo = pointPartnerNo;
    }
    
    

    public String getOpenapiPartnerNo() {
        return this.openapiPartnerNo;
    }
    
    public void setOpenapiPartnerNo(String openapiPartnerNo) {
        this.openapiPartnerNo = openapiPartnerNo;
    }
    
    

    public String getOrderDisplay() {
        return this.orderDisplay;
    }
    
    public void setOrderDisplay(String orderDisplay) {
        this.orderDisplay = orderDisplay;
    }
    
    

    public String getAppkey() {
        return this.appkey;
    }
    
    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
    
    

    public String getAppsecret() {
        return this.appsecret;
    }
    
    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
    
    

    public String getBankType() {
        return this.bankType;
    }
    
    public void setBankType(String bankType) {
        this.bankType = bankType;
    }
    
    

    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    

    public String getQrLogo() {
        return this.qrLogo;
    }
    
    public void setQrLogo(String qrLogo) {
        this.qrLogo = qrLogo;
    }
    
    

    public String getReturnUrl() {
        return this.returnUrl;
    }
    
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    
    

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getSettlePayOrg() {
		return settlePayOrg;
	}

	public void setSettlePayOrg(String settlePayOrg) {
		this.settlePayOrg = settlePayOrg;
	}
   








}