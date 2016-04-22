package com.froad.cbank.coremodule.framework.common.util.springfactroy;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.PropertiesPropertySource;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;

/************************************************
 * Copyright (c) by soap All right reserved. Create Date: 2013-8-16 Create
 * Author: hujz File Name: 基于SPRING动态注册BEAN Last version: 1.0 Function: Last
 * Update Date: Change Log:
 **************************************************/

public class DynamicUtil implements BeanFactoryAware, ApplicationListener<ContextRefreshedEvent> {
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * SPRING工厂
	 */
	public static DefaultListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		DynamicUtil.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
	}

	/**
	 * 注销FACTROY的BEAN
	 * 
	 * @param beanid
	 */
	public void destroySingleton(String beanid) {
		DynamicUtil.beanFactory.destroySingleton(beanid);
	}

	/**
	 * 注销FACTROY的BEAN
	 * 
	 * @param beanid
	 */
	public void removeBeanDefinition(String beanid) {
		DynamicUtil.beanFactory.removeBeanDefinition(beanid);
	}

	/**
	 * 注销FACTROY的BEAN
	 * 
	 * @param beanid
	 * @param beanInstance
	 */
	public void destroyBean(String beanid, Object beanInstance) {
		DynamicUtil.beanFactory.destroyBean(beanid, beanInstance);
	}

	/**
	 * 注册BEAN
	 * 
	 * @param beanid
	 * @param beanClassName
	 */
	public void addBean(String beanId, String beanClassName) {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClassName);
		makeRegisterBean(beanDefinitionBuilder, null);
		DynamicUtil.beanFactory.registerBeanDefinition(beanId, beanDefinitionBuilder.getRawBeanDefinition());
		logger.info("===>"+beanId /*+ DynamicUtil.beanFactory.getBean(beanId)*/);
	}

	/**
	 * 注册有事务BEAN
	 * 
	 * @param beanid
	 * @param beanClassName
	 * @param baseTransactionProxy
	 */
	public void addTransactionBean(String beanId, String beanClassName, String baseTransactionProxy) {
		BeanDefinitionBuilder childBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClassName);
		makeRegisterBean(childBeanDefinitionBuilder, null);
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.childBeanDefinition(baseTransactionProxy);
		SpringRegBean springRegBean = new SpringRegBean();
		ManagedProperties propertyPro = new ManagedProperties();
		propertyPro.put("target", new BeanDefinitionHolder(childBeanDefinitionBuilder.getRawBeanDefinition(), beanClassName));
		springRegBean.setPropertyPro(propertyPro);
		makeRegisterBean(beanDefinitionBuilder, springRegBean);
		DynamicUtil.beanFactory.registerBeanDefinition(beanId, beanDefinitionBuilder.getRawBeanDefinition());
		logger.info("===>"+beanId /*+ DynamicUtil.beanFactory.getBean(beanId)*/);
	}

	/**
	 * 注册DAO
	 * 
	 * @param beanid
	 * @param beanClassName
	 * @param sessionFactory
	 */
	public void addDao(String beanId, String beanClassName, String sessionFactory) {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClassName);
		SpringRegBean springRegBean = new SpringRegBean();
		ManagedProperties propertyRefPro = new ManagedProperties();
		propertyRefPro.put("sessionFactory", sessionFactory);
		springRegBean.setPropertyRefPro(propertyRefPro);
		makeRegisterBean(beanDefinitionBuilder, springRegBean);
		DynamicUtil.beanFactory.registerBeanDefinition(beanId, beanDefinitionBuilder.getRawBeanDefinition());
		logger.info("===>"+beanId /*+ DynamicUtil.beanFactory.getBean(beanId)*/);
	}

	/**
	 * 功能说明：根据DataSource创建bean并注册到容器中
	 * 
	 * @param mapCustom
	 */
	public void addDateSource(Map<String, DateSourceBean> mapCustom) {
		if (null == mapCustom)
			return;
		DateSourceBean dateSourceBean;
		String beanId;
		for (String beanKey : mapCustom.keySet()) {
			logger.info(beanKey + "===>start");
			dateSourceBean = mapCustom.get(beanKey);
			if (null == dateSourceBean) {
				logger.info(beanKey + "===>dateSourceBean is null");
				continue;
			}
			//默认注册顺序 datasource factroy transactionManager proy
			regDataSource(beanKey, dateSourceBean.getResourceSpringRegBean());
			beanId = regSessionFactory(beanKey, dateSourceBean.getSessionFactorySpringRegBean());
			beanId = regTransactionManager(beanId, dateSourceBean.getTransactionManagerSpringRegBean());
			regBaseTransactionProxy(beanId, dateSourceBean.getBaseTransactionRegBean());
			logger.info(beanKey + "===>end");
		}
	}

	public String regDataSource(String beanId, SpringRegBean resourceSpringRegBean) {
		if (null == resourceSpringRegBean || null == resourceSpringRegBean.getBeanPro()) {
			logger.info(beanId + "===>resourceSpringRegBean is null");
			return "";
		}
		PropertiesPropertySource pro = resourceSpringRegBean.getBeanPro();
		// 得到Bean定义，并添加到容器中
		BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition((String) pro
				.getProperty(SpringRegBean.BEAN_CLASS));
		makeRegisterBean(beanDefinition, resourceSpringRegBean);
		// 注意：必须先注册到容器中，再得到Bean进行修改，否则数据源属性不能有效修改
		DynamicUtil.beanFactory.registerBeanDefinition(beanId, beanDefinition.getRawBeanDefinition());
		logger.info("===>"+beanId /*+ DynamicUtil.beanFactory.getBean(beanId)*/);
		return beanId;
	}

	/**
	 * 注册 sessionFactory
	 * 
	 * @param dateSourceId
	 * @param sessionFactorySpringRegBean
	 * @return sessionFactory id
	 */
	public String regSessionFactory(String dateSourceId, SpringRegBean sessionFactorySpringRegBean) {
		if (null == sessionFactorySpringRegBean || null == sessionFactorySpringRegBean.getBeanPro()) {
			logger.info(dateSourceId + "===>sessionFactorySpringRegBean is null");
			return "";
		}
		PropertiesPropertySource pro = sessionFactorySpringRegBean.getBeanPro();
		// 得到Bean定义，并添加到容器中
		BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition((String) pro
				.getProperty(SpringRegBean.BEAN_CLASS));
		// 处理dataSource引入
		ManagedProperties propertyRefPro = sessionFactorySpringRegBean.getPropertyRefPro();
		if (null == propertyRefPro)
			propertyRefPro = new ManagedProperties();
		propertyRefPro.put("dataSource", dateSourceId);
		sessionFactorySpringRegBean.setPropertyRefPro(propertyRefPro);
		makeRegisterBean(beanDefinition, sessionFactorySpringRegBean);
		//
		String beanId = (String) pro.getProperty(SpringRegBean.BEAN_ID);
		DynamicUtil.beanFactory.registerBeanDefinition(beanId, beanDefinition.getRawBeanDefinition());
		logger.info( "===>"+beanId /*+ DynamicUtil.beanFactory.getBean(beanId)*/);
		return beanId;
		// addTransactionManager(beanId,
		// dateSourceBean.getSessionFactorySpringRegBean());
	}

	/**
	 * 注册 TransactionManager
	 * 
	 * @param sessionFactoryId
	 * @param transactionManagerSpringRegBean
	 * @return TransactionManager ID
	 */
	public String regTransactionManager(String sessionFactoryId, SpringRegBean transactionManagerSpringRegBean) {
		if (null == transactionManagerSpringRegBean || null == transactionManagerSpringRegBean.getBeanPro()) {
			logger.info(sessionFactoryId + "===>transactionManagerSpringRegBean is null");
			return "";
		}
		PropertiesPropertySource pro = transactionManagerSpringRegBean.getBeanPro();
		// 得到Bean定义，并添加到容器中
		BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition((String) pro
				.getProperty(SpringRegBean.BEAN_CLASS));
		// 处理sessionFactory引入
		ManagedProperties propertyRefPro = transactionManagerSpringRegBean.getPropertyRefPro();
		if (null == propertyRefPro)
			propertyRefPro = new ManagedProperties();
		propertyRefPro.put("sessionFactory", sessionFactoryId);
		transactionManagerSpringRegBean.setPropertyRefPro(propertyRefPro);
		makeRegisterBean(beanDefinition, transactionManagerSpringRegBean);
		//
		String beanId = (String) pro.getProperty(SpringRegBean.BEAN_ID);
		DynamicUtil.beanFactory.registerBeanDefinition(beanId, beanDefinition.getRawBeanDefinition());
		logger.info("===>"+beanId /*+ DynamicUtil.beanFactory.getBean(beanId)*/);
		return beanId;
	}

	/**
	 * 注册 BaseTransactionProxy
	 * 
	 * @param transactionManagerId
	 * @param baseTransactionRegBean
	 * @return BaseTransactionProxy ID
	 */
	public String regBaseTransactionProxy(String transactionManagerId, SpringRegBean baseTransactionRegBean) {
		if (null == baseTransactionRegBean || null == baseTransactionRegBean.getBeanPro()) {
			logger.info(transactionManagerId + "===>baseTransactionRegBean is null");
			return "";
		}
		PropertiesPropertySource pro = baseTransactionRegBean.getBeanPro();
		// 得到Bean定义，并添加到容器中
		BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition((String) pro
				.getProperty(SpringRegBean.BEAN_CLASS));
		// 处理transactionManager引入
		ManagedProperties propertyRefPro = baseTransactionRegBean.getPropertyRefPro();
		if (null == propertyRefPro)
			propertyRefPro = new ManagedProperties();
		propertyRefPro.put("transactionManager", transactionManagerId);
		baseTransactionRegBean.setPropertyRefPro(propertyRefPro);
		makeRegisterBean(beanDefinition, baseTransactionRegBean);
		//
		String beanId = (String) pro.getProperty(SpringRegBean.BEAN_ID);
		DynamicUtil.beanFactory.registerBeanDefinition(beanId, beanDefinition.getRawBeanDefinition());
		logger.info(beanId + "===>");
		return beanId;
	}
	/**
	 * 注册SPRING BEAN	
	 * @param springRegBean
	 * @return BEANID
	 */
	public String regBean(SpringRegBean springRegBean){
		if(null==springRegBean)return "";
		PropertiesPropertySource pro = springRegBean.getBeanPro();
		if(null==pro)return "";
		String beanId = (String) pro.getProperty(SpringRegBean.BEAN_ID);
		if(StringUtil.isBlank(beanId))return "";
//		String beanClassName=(String) pro.getProperty(SpringRegBean.BEAN_CLASS);
//		if(StringUtil.isBlank(beanClassName))return "";
//		String parentName=(String) pro.getProperty(SpringRegBean.BEAN_PARENTNAME);
		// 得到Bean定义，并添加到容器中
		BeanDefinitionBuilder beanDefinitionBuilder=makeBuilder(null, springRegBean) ;
//		if(StringUtil.isBlank(parentName)){
//			beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClassName);
//			makeRegisterBean(beanDefinitionBuilder, springRegBean);
//		}else{
//			beanDefinitionBuilder = BeanDefinitionBuilder.childBeanDefinition(parentName);
//			BeanDefinitionBuilder childBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClassName);
//			makeRegisterBean(childBeanDefinitionBuilder, springRegBean);
//			ManagedProperties propertyPro = new ManagedProperties();
//			propertyPro.put("target", new BeanDefinitionHolder(childBeanDefinitionBuilder.getRawBeanDefinition(), beanClassName));
//			SpringRegBean parentSpringRegBean =new SpringRegBean();
//			parentSpringRegBean.setPropertyPro(propertyPro);
//			makeRegisterBean(beanDefinitionBuilder,parentSpringRegBean );
//		}
		//注册
		DynamicUtil.beanFactory.registerBeanDefinition(beanId, beanDefinitionBuilder.getRawBeanDefinition());
//		System.out.println(DynamicUtil.beanFactory.getBean(beanId));
		logger.info("===>"+beanId /*+ DynamicUtil.beanFactory.getBean(beanId)*/); //+ DynamicUtil.beanFactory.getBean(beanId));
		return beanId;
	}
	
	
	/**
	 * 初始、封装 BeanDefinitionBuilder
	 * @param beanDefinitionBuilder
	 * @param springRegBean
	 * @return
	 */
	public BeanDefinitionBuilder makeBuilder(BeanDefinitionBuilder beanDefinitionBuilder,SpringRegBean springRegBean){
		if(null==springRegBean)return null;
		PropertiesPropertySource pro = springRegBean.getBeanPro();
		if(null!=pro){
			String beanClassName=(String) pro.getProperty(SpringRegBean.BEAN_CLASS);
			String parentName=(String) pro.getProperty(SpringRegBean.BEAN_PARENTNAME);
			//两种初始方式  通过beanClassName 通过 parentName
			if(null==beanDefinitionBuilder&&StringUtil.isNotBlank(beanClassName)){
				beanDefinitionBuilder= BeanDefinitionBuilder.genericBeanDefinition(beanClassName);
			}else if(null==beanDefinitionBuilder&&StringUtil.isNotBlank(parentName)){
				beanDefinitionBuilder= BeanDefinitionBuilder.childBeanDefinition(parentName);
			}
			
			
		}
		// 得到Bean定义，并添加到容器中
		makeRegisterBean(beanDefinitionBuilder, springRegBean);
		return beanDefinitionBuilder;
	}
	/**
	 * 设置BEAN属性
	 * 默认按NAME依赖注入  默认延迟加载，解决 BEAN加载顺序问题
	 * @param springRegBean
	 */
	private void makeRegisterBean(BeanDefinitionBuilder beanDefinition, SpringRegBean springRegBean) {
		if (null == beanDefinition)
			return;
		// 默认按NAME依赖注入
		beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
		// 默认延迟加载，解决 BEAN加载顺序问题
		beanDefinition.setLazyInit(true);
		if (null == springRegBean)
			return;
		// 处理其它属性
		if (null != springRegBean.getBeanPro()) {
			setBeanPro(beanDefinition, springRegBean.getBeanPro());
		}
		// 处理SET注入属性
		if (null != springRegBean.getPropertyPro()) {
			for (Object key : springRegBean.getPropertyPro().keySet()) {
				beanDefinition.addPropertyValue((String) key, springRegBean.getPropertyPro().get(key));
			}
		}
		// 处理SET引用属性
		if (null != springRegBean.getPropertyRefPro()) {
			for (Object key : springRegBean.getPropertyRefPro().keySet()) {
				beanDefinition.addPropertyReference((String) key, (String) springRegBean.getPropertyRefPro().get(key));
			}
		}
		// 处理构造值
		if (null != springRegBean.getConstructorList()) {
			for (ConstructorBean constructorBean : springRegBean.getConstructorList()) {
				if (null != constructorBean && ConstructorBean.TYPE_REF.equals(constructorBean.getType())) {
					beanDefinition.addConstructorArgReference((String) constructorBean.getObj());
				} else if (null != constructorBean && ConstructorBean.TYPE_OBJECT.equals(constructorBean.getType())) {
					beanDefinition.addConstructorArgValue(constructorBean.getObj());

				}
			}
		}
	}

	/**
	 * 根据配置设置BEAN的属性
	 * 
	 * @param beanDefinition
	 * @param beanPro
	 */
	private void setBeanPro(BeanDefinitionBuilder beanDefinition, PropertiesPropertySource beanPro) {
		if (null == beanDefinition || null == beanPro)
			return;
		// ABSTRACT
		String value = (String) beanPro.getProperty(SpringRegBean.BEAN_ABSTRACT);
		if (StringUtil.isNotBlank(value)) {
			if ("TRUE".equals(value.toUpperCase())) {
				beanDefinition.setAbstract(true);
			} else {
				beanDefinition.setAbstract(false);
			}
		}
		// scope
		value = (String) beanPro.getProperty(SpringRegBean.BEAN_SCOPE);
		if (StringUtil.isNotBlank(value)) {
			beanDefinition.setScope(value);
		}
		/*// singleton
		value = (String) beanPro.getProperty(SpringRegBean.BEAN_SINGLETON);
		if (StringUtil.isNotBlank(value)) {
			if ("TRUE".equals(value.toUpperCase())) {
				beanDefinition.setSingleton(true);
			} else {
				beanDefinition.setSingleton(false);
			}
		}*/
		// LazyInit
		value = (String) beanPro.getProperty(SpringRegBean.BEAN_LAZYINIT);
		if (StringUtil.isNotBlank(value)) {
			if ("TRUE".equals(value.toUpperCase())) {
				beanDefinition.setLazyInit(true);
			} else {
				beanDefinition.setLazyInit(false);
			}
		}
		// autowireMode
		value = (String) beanPro.getProperty(SpringRegBean.BEAN_AUTOWIREMODE);
		if (StringUtil.isNotBlank(value)) {
			beanDefinition.setAutowireMode(Integer.valueOf(value));
		}
		// dependencyCheck
		value = (String) beanPro.getProperty(SpringRegBean.BEAN_DEPENDENCYCHECK);
		if (StringUtil.isNotBlank(value)) {
			beanDefinition.setDependencyCheck(Integer.valueOf(value));
		}
		// role
		value = (String) beanPro.getProperty(SpringRegBean.BEAN_ROLE);
		if (StringUtil.isNotBlank(value)) {
			beanDefinition.setRole(Integer.valueOf(value));
		}
		// source
		/*Object obj = beanPro.getProperty(SpringRegBean.BEAN_SOURCE);
		if (StringUtil.isNotBlank(obj)) {
			beanDefinition.setSource(obj);
		}
		// resourceDescription
		value = (String) beanPro.getProperty(SpringRegBean.BEAN_RESOURCEDESCRIPTION);
		if (StringUtil.isNotBlank(value)) {
			beanDefinition.setResourceDescription(value);
		}*/
		// destroyMethodName
		value = (String) beanPro.getProperty(SpringRegBean.BEAN_DESTROYMETHODNAME);
		if (StringUtil.isNotBlank(value)) {
			beanDefinition.setDestroyMethodName(value);
		}
		// initMethodName
		value = (String) beanPro.getProperty(SpringRegBean.BEAN_INITMETHODNAME);
		if (StringUtil.isNotBlank(value)) {
			beanDefinition.setInitMethodName(value);
		}
		// factoryMethod
		value = (String) beanPro.getProperty(SpringRegBean.BEAN_FACTORYMETHOD);
		if (StringUtil.isNotBlank(value)) {
			beanDefinition.setFactoryMethod(value);
		}
		// parentName
		value = (String) beanPro.getProperty(SpringRegBean.BEAN_PARENTNAME);
		if (StringUtil.isNotBlank(value)) {
			beanDefinition.setParentName(value);
		}
	}
}
