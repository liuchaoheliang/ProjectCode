package com.froad.cbank.coremodule.normal.boss.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不检查token
 * @ClassName NoToken
 * @author zxl
 * @date 2015年8月21日 下午1:44:21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoToken {
	String value() default "";
}
