/**
 * Project Name : froad-cbank-server-common
 * File Name : LogActionConstent.java
 * Package Name : com.froad.log
 * Date : 2015年11月9日下午5:07:29
 * Copyright (c) 2015, i2Finance Software All Rights Reserved
 *
 */
package com.froad.log;

/**
 * ClassName : LogActionConstent <br/>
 * Function : 定义LogAction常量<br/>
 * Date : 2015年11月9日 下午5:07:29 <br/>
 * @author KaiweiXiang
 * @version  
 * @see 	 
 */
public class LogActionConstent {

	public static final String ACTION_PAYMENT_ADD = "PAYMENTADD"; //⽀付创建
	
	public static final String ACTION_PAYMENT_SUCCESS = "PAYMENTSUCCESS"; //⽀付动作(成功)
	
	public static final String ACTION_PAYMENT_FAIL = "PAYMENTFAIL"; //⽀付动作(失败)
	
	public static final String ACTION_REFUND = "REFUND"; //退款成功
	
	public static final String ACTION_TICKET_ADD = "TICKETADD"; //券码创建
	
	public static final String ACTION_TICKET_MODIFY = "TICKETMODIFY"; //券码修改
	
	public static final String ACTION_SETTLEMENT_ADD = "SETTLEMENTADD"; //结算创建
	
	public static final String ACTION_SETTLEMENT_MODIFY = "SETTLEMENTMODIFY"; //结算修改
	
}
