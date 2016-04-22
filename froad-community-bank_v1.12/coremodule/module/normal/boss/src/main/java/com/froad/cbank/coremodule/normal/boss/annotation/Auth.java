package com.froad.cbank.coremodule.normal.boss.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限key检查
 * @ClassName Auth
 * @author zxl
 * @date 2015年12月29日 上午9:24:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	String[] keys() default {};
}
