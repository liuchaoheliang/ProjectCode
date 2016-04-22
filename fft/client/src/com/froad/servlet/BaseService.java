package com.froad.servlet;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class BaseService implements ApplicationContextAware {
	
	private static final Logger logger=Logger.getLogger(BaseService.class);

	private static ApplicationContext ctx;
	
	//private static MUserImpl muserImpl;

	private static BaseService instance = new BaseService();

	/**
	 * 
	 * @return 返回 静态类
	 * 
	 */
	public static BaseService getInstance() {
		return instance;
	}

	/**
	 * 
	 * @return 返回 初始化BaseService,存于内存
	 * 
	 */
	public void init(ApplicationContext ctx) {
		
		logger.info(" 初始化 BaseService...... ");

		setApplicationContext(ctx);

		setMUserImpl(ctx);

		logger.info(" 初始化 BaseService 成功...... ");
	}

	public void setMUserImpl(ApplicationContext ctx) {
		logger.info(" 初始化 MUserImpl ......");
		//muserImpl = (MUserImpl) ctx.getBean("MUserImpl");
	}
	
//	public MUserImpl getInstanceMUserImpl(){
//		return this.muserImpl;
//	}
	
	/**
	 * 
	 * 通过spring注入ApplicationContext
	 * 
	 * @param ApplicationContext
	 * 
	 * @return null
	 * 
	 */
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        logger.info(" 初始化 ApplicationContext ......");
		ctx = arg0;
	}

	public static Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}
}
