package com.froad.cbank.coremodule.framework.common.util.springfactroy;

import java.util.List;

import org.springframework.beans.factory.support.ManagedProperties;
import org.springframework.core.env.PropertiesPropertySource;

/************************************************
 * Copyright (c)  by soap
 * All right reserved.
 * Create Date: 2013-8-16
 * Create Author: hujz
 * File Name:  spring 注册BEAN的属性
 * Last version:  1.0
 * Function:这里写注释
 * Last Update Date:
 * Change Log:
**************************************************/	
 
public class SpringRegBean {
	public static final String BEAN_ID="beanId";
	public static final String BEAN_CLASS="beanClassName";
	public static final String BEAN_SCOPE="scope";
	public static final String BEAN_SINGLETON="singleton";
	public static final String BEAN_ABSTRACT="abstract";
	public static final String BEAN_LAZYINIT="LazyInit";
	public static final String BEAN_AUTOWIREMODE="autowireMode";
	public static final String BEAN_DEPENDENCYCHECK="dependencyCheck";
	public static final String BEAN_ROLE="role";
	public static final String BEAN_SOURCE="source";
	public static final String BEAN_RESOURCEDESCRIPTION="resourceDescription";
	public static final String BEAN_DESTROYMETHODNAME="destroyMethodName";
	public static final String BEAN_INITMETHODNAME="initMethodName";
	public static final String BEAN_FACTORYMETHOD="factoryMethod";
	public static final String BEAN_PARENTNAME="parentName";
	/**
	 * bean 属性  beanId beanClassName abstract ...
	 * <bean id="{beanId}"
		class="{beanClassName}"
		abstract="true">
	 */
	private PropertiesPropertySource beanPro;
	/**
	 * bean通过SET注入的属性集合
	 */
	private ManagedProperties propertyPro;
	/**
	 *  bean通过SET注入的引用集合
	 */
	private ManagedProperties propertyRefPro;
	
	/**
	 * 构造注入集合
	 */
	private List<ConstructorBean> ConstructorList;

	public PropertiesPropertySource getBeanPro() {
		return beanPro;
	}

	public void setBeanPro(PropertiesPropertySource beanPro) {
		this.beanPro = beanPro;
	}

	public ManagedProperties getPropertyPro() {
		return propertyPro;
	}

	public void setPropertyPro(ManagedProperties propertyPro) {
		this.propertyPro = propertyPro;
	}

	public ManagedProperties getPropertyRefPro() {
		return propertyRefPro;
	}

	public void setPropertyRefPro(ManagedProperties propertyRefPro) {
		this.propertyRefPro = propertyRefPro;
	}

	public List<ConstructorBean> getConstructorList() {
		return ConstructorList;
	}

	public void setConstructorList(List<ConstructorBean> constructorList) {
		ConstructorList = constructorList;
	}
}
