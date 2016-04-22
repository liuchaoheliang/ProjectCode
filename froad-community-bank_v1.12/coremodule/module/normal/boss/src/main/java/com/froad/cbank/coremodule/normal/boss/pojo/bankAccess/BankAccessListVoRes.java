package com.froad.cbank.coremodule.normal.boss.pojo.bankAccess;

/**
 * 多银行接入列表查询实体类
 * @author yfy
 * @date: 2015年9月18日 上午10:23:41
 */
public class BankAccessListVoRes {
	
	/**
	 * 编号
	 */
	private Long id;
	/**
	 * 客户端ID
	 */
	private String clientNo;
	/**
	 * 客户端
	 */
	private String clientName;
	/**
	 * 功能模块
	 */
	private String functionDesc;
	/**
	 * 支付方式
	 */
	private String paymentMethodDesc;
	/**
	 * 配置时间
	 */
	private Long createTime;
	/**
	 * 更新时间
	 */
	private Long updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getFunctionDesc() {
		return functionDesc;
	}
	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}
	public String getPaymentMethodDesc() {
		return paymentMethodDesc;
	}
	public void setPaymentMethodDesc(String paymentMethodDesc) {
		this.paymentMethodDesc = paymentMethodDesc;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	
}
