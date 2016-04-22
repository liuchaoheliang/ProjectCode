package com.froad.fft.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Component
@Aspect
public class GateKeeper {

	final static Logger logger = Logger.getLogger(GateKeeper.class);

	@Pointcut("execution (* com.froad.fft.api..*.add*(..))")
	public void pointcutSaveApi() {

	}
	
	@Pointcut("execution (* com.froad.fft.api..*.update*(..))")
	public void pointcutUpadateApi() {

	}

	/**
	 * 对外接口保存方法时
	 * @param joinPoint
	 */
	@Before("pointcutSaveApi()")
	public void apiSaveBefore(JoinPoint joinPoint) {
		logger.info("[API SaveBefore 进入] " + joinPoint.getTarget().getClass().getName()
				+ " 方法名：" + joinPoint.getSignature().getName() + " 参数："
				+ JSON.toJSONString(joinPoint.getArgs(),SerializerFeature.WriteDateUseDateFormat));
	}
	
	/**
	 * 对外接口更新方法时
	 * @param joinPoint
	 */
	@Before("pointcutUpadateApi()")
	public void apiUpdateBefore(JoinPoint joinPoint) {
		logger.info("[API UpdateBefore 进入] " + joinPoint.getTarget().getClass().getName()
				+ " 方法名：" + joinPoint.getSignature().getName() + " 参数："
				+ JSON.toJSONString(joinPoint.getArgs(),SerializerFeature.WriteDateUseDateFormat));
	}

}
