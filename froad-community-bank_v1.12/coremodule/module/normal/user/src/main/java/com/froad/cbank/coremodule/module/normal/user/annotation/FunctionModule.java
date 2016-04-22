package com.froad.cbank.coremodule.module.normal.user.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 功能模块注解
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年10月14日 下午6:16:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FunctionModule {

}
