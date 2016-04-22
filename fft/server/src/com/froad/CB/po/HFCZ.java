package com.froad.CB.po;

import java.math.BigDecimal;


public class HFCZ {
	private String CZNo;// 充值号码
	private BigDecimal money;// 充值金额
	private BigDecimal salePrice;//售价金额
	private String respCode;//响应码,0表示交易成功,非0表示交易失败
	private String respMsg;//响应信息
	private String area;//区域
	private String operater;//运营商
	private String tranID;//方付通商城订单号
    private String SPID;//接入方订单号
    private String merchantNo;//SP商户号
    private String merchantPwd;//SP接入密码
    private String md5_str;//MD5后字符串
    private String version;//版本信息：1.0
    private String tranDate;//交易时间
	public String getCZNo() {
		return CZNo;
	}
	public void setCZNo(String no) {
		CZNo = no;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public String getTranID() {
		return tranID;
	}
	public void setTranID(String tranID) {
		this.tranID = tranID;
	}
	public String getSPID() {
		return SPID;
	}
	public void setSPID(String spid) {
		SPID = spid;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getMerchantPwd() {
		return merchantPwd;
	}
	public void setMerchantPwd(String merchantPwd) {
		this.merchantPwd = merchantPwd;
	}
	public String getMd5_str() {
		return md5_str;
	}
	public void setMd5_str(String md5_str) {
		this.md5_str = md5_str;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
}
