package com.froad.po;

import java.util.Date;

/**
 * 审核对象
 * @author ll 20150402
 *
 */
public class BankAudit {
    
    /**
     * 审核对象id(商品id或商户id)
     */
    private String auditId;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 待审核机构编号(完成置0)
     */
    private String auditOrgCode;
    /**
     * 商品或商户所属的机构代码
     */
    private String orgCode;
    /**
     * 商品审核状态(1审核通过,2审核未通过)
     */
    private String auditState;
    /**
     * 审核步骤(0-初审/1-复审)
     */
    private String auditStage;
    /**
     * 审核人
     */
    private String auditStaff;
    /**
     * 审核备注
     */
    private String auditComment;
    
    //添加审核时间及复核人员
    private Date auditTime;
    private String reviewStaff;
    private String merchantName;//商户名称
    private String name; //商品名称
    private String type; //商品类型
    private String merchantId;//商户id(审核商品的时候，mapper需要的)
    private Boolean isEnable;//是否有效商户
    private String auditStartOrgCode;//起始审核机构编号
    private String auditEndOrgCode;//终审机构编号
    private String isMarketable;//商品上下架标识 0未上架1-已上架2-已下架3-已删除
    private String isSeckill;//秒杀审核专用, 商品秒杀标识 0-非秒杀, 1-秒杀已上架， 2-秒杀已下架
    private Date contractBegintime;//签约时间
    private Date contractEndtime;//签约到期时间
    private Date rackTime;//上架时间

    
    
    //20150817 商户编辑审核 add 
    private String editAuditState; //编辑审核状态
    
    
	public String getAuditStartOrgCode() {
		return auditStartOrgCode;
	}
	public void setAuditStartOrgCode(String auditStartOrgCode) {
		this.auditStartOrgCode = auditStartOrgCode;
	}
	public String getAuditEndOrgCode() {
		return auditEndOrgCode;
	}
	public void setAuditEndOrgCode(String auditEndOrgCode) {
		this.auditEndOrgCode = auditEndOrgCode;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getReviewStaff() {
		return reviewStaff;
	}
	public void setReviewStaff(String reviewStaff) {
		this.reviewStaff = reviewStaff;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getAuditOrgCode() {
        return auditOrgCode;
    }
    public void setAuditOrgCode(String auditOrgCode) {
        this.auditOrgCode = auditOrgCode;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public String getAuditState() {
        return auditState;
    }
    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }
    public String getAuditStage() {
        return auditStage;
    }
    public void setAuditStage(String auditStage) {
        this.auditStage = auditStage;
    }
    public String getAuditStaff() {
        return auditStaff;
    }
    public void setAuditStaff(String auditStaff) {
        this.auditStaff = auditStaff;
    }
    public String getAuditComment() {
        return auditComment;
    }
    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public String getIsSeckill() {
		return isSeckill;
	}
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}

	public String getEditAuditState() {
		return editAuditState;
	}
	public void setEditAuditState(String editAuditState) {
		this.editAuditState = editAuditState;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public Date getContractBegintime() {
		return contractBegintime;
	}
	public void setContractBegintime(Date contractBegintime) {
		this.contractBegintime = contractBegintime;
	}
	public Date getContractEndtime() {
		return contractEndtime;
	}
	public void setContractEndtime(Date contractEndtime) {
		this.contractEndtime = contractEndtime;
	}
	public Date getRackTime() {
		return rackTime;
	}
	public void setRackTime(Date rackTime) {
		this.rackTime = rackTime;
	}
	
}
