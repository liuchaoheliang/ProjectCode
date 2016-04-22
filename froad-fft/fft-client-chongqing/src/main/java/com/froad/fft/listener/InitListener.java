package com.froad.fft.listener;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.froad.fft.support.expand.StaticSupport;

@Component("initListener")
public class InitListener implements ServletContextAware,ApplicationListener<ContextRefreshedEvent> {

	private ServletContext servletContext;
	
	@Resource
	private StaticSupport staticSupport;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	//当spring容器初始化完成后就会执行该方法
	//ContextRefreshedEvent:当ApplicationContext初始化或者刷新时触发该事件
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		
		/**
		 * spring mvc 系统会存在两个容器
		 * 一个是root application context
		 * 另一个就是我们自己的 projectName-servlet  context（作为root application context的子容器）
		 * 这种情况下，就会造成onApplicationEvent方法被执行两次
		 * root application context 没有parent，他就是老大
		 */
		if(servletContext != null && contextRefreshedEvent.getApplicationContext().getParent() == null){
			//静态化首页
			staticSupport.buildIndex();
			staticSupport.buildOther();
			staticSupport.buildSitemap();
		}  
	}

}
