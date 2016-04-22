package com.froad.cbank.coremodule.normal.boss.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导入，导出从request.getParameter取token并检查
 * @ClassName ImpExp
 * @author zxl
 * @date 2015年8月21日 下午1:44:35
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImpExp {
	String value() default "";
}
