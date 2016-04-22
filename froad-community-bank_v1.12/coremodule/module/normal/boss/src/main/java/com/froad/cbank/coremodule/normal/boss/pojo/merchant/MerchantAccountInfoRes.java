package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：商户账户返回实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-3下午5:06:57 
 */
public class MerchantAccountInfoRes implements Serializable{

	/**
	 * UID
	 */
	private static final long serialVersionUID = -3273084977726264536L;
	/**
	   * 主键ID
	   */
	  private String id; // optional
	  
	  private String merchantAccountId;//商户账号ID
	  /**
	   * 创建时间
	   */
	  private String createTime; // optional
	  /**
	   * 客户端ID
	   */
	  private String clientId; // optional
	  /**
	   * 商户ID
	   */
	  private String merchantId; // optional
	  /**
	   * 门店ID
	   */
	  private String outletId; // optional
	  /**
	   * 账户名称
	   */
	  private String accountName; // optional
	  /**
	   * 账户号
	   */
	  private String accountNum; // optional
	  /**
	   * 账户类型
	   */
	  private String acctType; // optional
	  /**
	   * 开户行
	   */
	  private String openingBank; // optional
	  /**
	   * 是否删除
	   */
	  private boolean isDelete; // optional
	  
	  private MerchantAccountInfoVo merchantAccountInfoVo;
	  
	  private List<MerchantAccountInfoVo> MerchantAccountInfoVoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerchantAccountId() {
		return merchantAccountId;
	}

	public void setMerchantAccountId(String merchantAccountId) {
		this.merchantAccountId = merchantAccountId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public MerchantAccountInfoVo getMerchantAccountInfoVo() {
		return merchantAccountInfoVo;
	}

	public void setMerchantAccountInfoVo(MerchantAccountInfoVo merchantAccountInfoVo) {
		this.merchantAccountInfoVo = merchantAccountInfoVo;
	}

	public List<MerchantAccountInfoVo> getMerchantAccountInfoVoList() {
		return MerchantAccountInfoVoList;
	}

	public void setMerchantAccountInfoVoList(
			List<MerchantAccountInfoVo> merchantAccountInfoVoList) {
		MerchantAccountInfoVoList = merchantAccountInfoVoList;
	}
	    
}
