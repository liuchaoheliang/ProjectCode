package com.froad.fft.persistent.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 *<p> 类名: BaseEntityFieldValSetter.java </p>
 *<p> 描述: *-- <b>用于专门自动反射实体中的基础字段值</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014年1月3日 下午5:49:37 </p>
 ********************************************************
 */
@Component
@Aspect
public class BaseEntityFieldValSetter {

	private static Logger logger = Logger.getLogger(BaseEntityFieldValSetter.class);
	
	@Pointcut("execution(public * com.froad.fft.persistent.api.impl..*.save*(..))")
	public void pointcutSaveMethod(){}
	
	@Pointcut("execution(public * com.froad.fft.persistent.api.impl..*.update*(..))")
	public void pointcutUpdateMethod(){}
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>为保存的实体自动设置创建时间和更新时间</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 上午9:25:47 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@Before("pointcutSaveMethod()")
	public void setBaseSaveFieldVal(JoinPoint joinPoint){
		Object[] originalArgs = joinPoint.getArgs();
		if(originalArgs.length != 1){ //如果参数列表不是一个，则不符合自动反射规则
			logger.info(joinPoint.getTarget().getClass().getName() + " 方法无发进行反射初始化基础字段");
			return;
		}
		Object bean = originalArgs[0];
		Class<?> clazz = bean.getClass();
		try {
			Method methodId = clazz.getMethod("setId", Long.class);
			Method methodCreateTime = clazz.getMethod("setCreateTime", Date.class);
			Method methodUpdateTime = clazz.getMethod("setUpdateTime", Date.class);
			if(methodId != null){
				Object tempVal = null;
				methodId.invoke(bean, tempVal); //防止传入主键干扰MYSQL数据主键生成策略
				Date date = new Date();
				methodCreateTime.invoke(bean, date);
				methodUpdateTime.invoke(bean, date);
				date = null;
				methodId = null;
				methodCreateTime = null;
				methodUpdateTime = null; //销毁变量，易于垃圾回收机制处理
			}		
			return;
		} catch (SecurityException e) {
			return;
		} catch (NoSuchMethodException e) {
			return;
		} catch (IllegalArgumentException e) {
			return;
		} catch (IllegalAccessException e) {
			return;
		} catch (InvocationTargetException e) {
			return;
		}
	}
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>为更新的数据自动创建更新时间</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 上午9:26:51 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@Before("pointcutUpdateMethod()")
	public void setBaseUpdateFieldVal(JoinPoint joinPoint){
		Object[] originalArgs = joinPoint.getArgs();
		if(originalArgs.length != 1){//如果参数列表不是一个，则不符合自动反射规则
			logger.info(joinPoint.getTarget().getClass().getName() + " 方法无发进行反射初始化基础字段");
			return;
		}
		Object object = originalArgs[0];
		Class<?> clazz = object.getClass();
		try {
			Method methodUpdateTime = clazz.getMethod("setUpdateTime", Date.class);
			methodUpdateTime.invoke(object, new Date());
			methodUpdateTime = null;//销毁变量，易于垃圾回收机制处理
			return;
		} catch (SecurityException e) {
			return;
		} catch (NoSuchMethodException e) {
			return;
		} catch (IllegalArgumentException e) {
			return;
		} catch (IllegalAccessException e) {
			return;
		} catch (InvocationTargetException e) {
			return;
		}
	}
	

}
