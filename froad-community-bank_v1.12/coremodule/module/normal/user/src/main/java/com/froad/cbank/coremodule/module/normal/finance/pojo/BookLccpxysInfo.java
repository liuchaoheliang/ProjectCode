package com.froad.cbank.coremodule.module.normal.finance.pojo;

import java.util.Date;

/**   
 * 理财产品协议书基础信息
 * @author liuhuangle@f-road.com.cn   
 * com.froad.cbank.coremodule.module.normal.finance.pojo
 * @date 2015-6-15 下午3:19:04
 */
public class BookLccpxysInfo {
	
	/**理财产品 产品编号*/
	private String bankProductCode;
	/**用户名(甲方)*/
	private String userName;
	/**购买金额*/
	private double amount;
	/**协议时间*/
	private long createDate;
	
	public String getBankProductCode() {
		return bankProductCode;
	}
	public void setBankProductCode(String bankProductCode) {
		this.bankProductCode = bankProductCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	} 
}
