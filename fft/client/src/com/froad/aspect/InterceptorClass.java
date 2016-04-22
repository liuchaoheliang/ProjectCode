package com.froad.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;




/*******************************************************************************
 * 定义切面
 * 
 ******************************************************************************/
@Aspect
// 将类声明为切面
public class InterceptorClass {
	/** 定义切入点 */
	
//	@Pointcut("execution(* com.froad.API..*(..))")
	// 定义切入点表达式，表示将要对那些方法类中的方法进行拦截
	// 声明切入点的名称，声明方法和定义一个方法类似,切入点的名称myPoint
	@SuppressWarnings("unused")
	private void myPoint() {}

	/**
	 * 定义前置通知
	 */
//	@Before("myPoint()")
	public void before() {
		System.out.println("前置通知");
	}

	/** 后置通知* *//*
	@AfterReturning("myPoint()")
	public void afterReturning() {
		System.out.println("后置通知");
	}

	*//** *最终通知 *//*
	@After("myPoint()")
	public void after() {
		System.out.println("最终通知");
	}

	*//** 例外通知 *//*
	@AfterThrowing("myPoint()")
	public void afterThrowing() {
		System.out.println("例外通知=======================");
	}*/

/*	*//**
	 * 获得输入参数的通知 注解中的参数名称和方法声明的参数名称一致， 而且这时只能拦截到存在一个参数并且参数类型为String类型的方法
	 *//*
	@Before("myPoint()&&args(user)")
	public void doBefore(User user) {
		System.out.println("前置通知获得输入参数：" + user.getUsername());
	}*/

/*	*//***************************************************************************
	 * 获得返回值的通知
	 * 
	 **************************************************************************//*
	@AfterReturning(pointcut = "myPoint()", returning = "result")
	public void doAfterReturning(User result) {
		System.out.println("获得返回值的后置通知：" + result.getUsername());
	}*/

	/**
	 * 获得异常信息的通知
	 */
//	@AfterThrowing(pointcut = "myPoint()", throwing = "ae")
//	public void doAfterThrowing(AppException_Exception ae) {
//		ae.printStackTrace();
//		System.out.println("获取客户端 AppException异常======================");
//	}

	/**
	 * 环绕通知 环绕通知的方法声明部分是固定的，只有方法名和访问权限可以改变
	 * 
	 * @throws Throwable
	 */
/*	@Around("myPoint()")
	private Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		System.out.println("开始执行环绕通知");
		if (1 == 1) {
			System.out.println("执行方法");
			result = pjp.proceed();// 调用方法
			System.out.println("结束方法");
		}
		System.out.println("结束执行环绕通知");
		return result;
	}*/
}
