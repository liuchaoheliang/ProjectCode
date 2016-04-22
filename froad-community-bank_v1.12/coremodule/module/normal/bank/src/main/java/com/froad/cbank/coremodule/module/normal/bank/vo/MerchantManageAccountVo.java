package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 商户账户
 * @author ylchu
 *
 */
public class MerchantManageAccountVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5678347394728601469L;

	private Long id;
	private String acctName;	//打款账户名
	private String acctNumber;	//打款账户号
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getAcctNumber() {
		return acctNumber;
	}
	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}
	
}
