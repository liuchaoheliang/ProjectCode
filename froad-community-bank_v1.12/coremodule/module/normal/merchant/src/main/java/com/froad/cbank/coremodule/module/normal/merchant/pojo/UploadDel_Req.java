package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.io.Serializable;

public class UploadDel_Req extends BasePojo implements Serializable {
	private static final long serialVersionUID = -8679965791719132706L;
	private int appId;
	private String appKey;
	private String fileKey;// 裁切结果文件key
	private String merchantId;
	private String outletId;
    private String productId;
    private String type;
	private Boolean isDefault=false;// 默认查询显示图片
    /**
     * 审核状态   0=待审核 ,1=审核通过 , 2=审核不通过 , 3=未提交 , 4=审核通过待同步
     */
    private int auditState=3; // optional
    /**
     * 编辑审核状态  0=待审核 ,1=审核通过 ,2=审核不通过 ,3=未提交 ,4=审核通过待同步
     */
    private int editAuditState=1; // optional  
    
	public int getAuditState() {
		return auditState;
	}

	public void setAuditState(int auditState) {
		this.auditState = auditState;
	}

	public int getEditAuditState() {
		return editAuditState;
	}

	public void setEditAuditState(int editAuditState) {
		this.editAuditState = editAuditState;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}


	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}


}
