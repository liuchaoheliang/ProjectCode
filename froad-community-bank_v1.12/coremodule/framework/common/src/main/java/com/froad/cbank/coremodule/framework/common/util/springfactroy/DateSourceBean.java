package com.froad.cbank.coremodule.framework.common.util.springfactroy;


/************************************************
 * Copyright (c)  by soap
 * All right reserved.
 * Create Date: 2013-8-16
 * Create Author: hujz
 * File Name:  数据源 注册的属性集合
 * Last version:  1.0
 * Function:
 * Last Update Date:
 * Change Log:
**************************************************/	
 
public class DateSourceBean{
	/**
	 * dataresource属性
	 */
	private SpringRegBean resourceSpringRegBean;
	/**
	 * sessionFactory属性
	 */
	private SpringRegBean sessionFactorySpringRegBean;
	/**
	 * baseTransaction属性
	 */
	private SpringRegBean baseTransactionRegBean;
	/**
	 * transactionManager属性
	 */
	private SpringRegBean transactionManagerSpringRegBean;
	public SpringRegBean getResourceSpringRegBean() {
		return resourceSpringRegBean;
	}
	public void setResourceSpringRegBean(SpringRegBean resourceSpringRegBean) {
		this.resourceSpringRegBean = resourceSpringRegBean;
	}
	public SpringRegBean getSessionFactorySpringRegBean() {
		return sessionFactorySpringRegBean;
	}
	public void setSessionFactorySpringRegBean(SpringRegBean sessionFactorySpringRegBean) {
		this.sessionFactorySpringRegBean = sessionFactorySpringRegBean;
	}
	public SpringRegBean getBaseTransactionRegBean() {
		return baseTransactionRegBean;
	}
	public void setBaseTransactionRegBean(SpringRegBean baseTransactionRegBean) {
		this.baseTransactionRegBean = baseTransactionRegBean;
	}
	public SpringRegBean getTransactionManagerSpringRegBean() {
		return transactionManagerSpringRegBean;
	}
	public void setTransactionManagerSpringRegBean(SpringRegBean transactionManagerSpringRegBean) {
		this.transactionManagerSpringRegBean = transactionManagerSpringRegBean;
	}
}
