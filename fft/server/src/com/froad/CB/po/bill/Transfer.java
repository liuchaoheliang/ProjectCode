package com.froad.CB.po.bill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.froad.util.Util;


	/**
	 * 类描述：转账实体
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jan 6, 2013 4:17:59 PM 
	 */
public class Transfer implements Serializable{

		
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	
	private String transferID;
	private String resultCode;
	private String remark;
	private String signType;
	private String signMsg;
	
	private String transferAmount;//转账金额
	private String payerAccountNum;//付款方账号
	private String payerAccountName;//付款方账户名
	private String payeeAccountNum;//收款方账号
	private String payeeAccountName;//收款方账户名
	
	
	public String getPayerAccountNum() {
		return payerAccountNum;
	}

	public void setPayerAccountNum(String payerAccountNum) {
		this.payerAccountNum = payerAccountNum;
	}

	public String getPayerAccountName() {
		return payerAccountName;
	}

	public void setPayerAccountName(String payerAccountName) {
		this.payerAccountName = payerAccountName;
	}

	public String getPayeeAccountNum() {
		return payeeAccountNum;
	}

	public void setPayeeAccountNum(String payeeAccountNum) {
		this.payeeAccountNum = payeeAccountNum;
	}

	public String getPayeeAccountName() {
		return payeeAccountName;
	}

	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}

	public Transfer(){}

	public String getTransferID() {
		return transferID;
	}

	public void setTransferID(String transferID) {
		this.transferID = transferID;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getTransferAmount() {
		return transferAmount;
	}
	
	public String getFormatAmount(){
		if(this.transferAmount==null||"".equals(this.transferAmount.trim())){
			return transferAmount;
		}
		return Util.formatMoney(transferAmount.trim()).toString();
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	public String getPayerStr(String separator){
		StringBuffer buffer=new StringBuffer();
		buffer.append(this.payerAccountNum);
		buffer.append(separator);
		buffer.append(this.payerAccountName);
		if("<".equals(separator)){
			buffer.append(">");
		}
		return buffer.toString();
	}
	
	public String getPayeeStr(String separator){
		StringBuffer buffer=new StringBuffer();
		buffer.append(this.payeeAccountNum);
		buffer.append(separator);
		buffer.append(this.payeeAccountName);
		if("<".equals(separator)){
			buffer.append(">");
		}
		return buffer.toString();
	}
}
