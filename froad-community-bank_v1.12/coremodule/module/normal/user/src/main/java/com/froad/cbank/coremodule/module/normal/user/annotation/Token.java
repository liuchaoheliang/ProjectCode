package com.froad.cbank.coremodule.module.normal.user.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *********************************************** 
 * @Copyright (c) by froad All right reserved.
 * @Create Date: 2014-12-10 下午8:50:07
 * @Create Author: hjz
 * @File Name: Token
 * @Function: 会员登录Token检验，注解到需要检验Token的方法上
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
    /**
     * 创建token
     * @Title: createKey 
     * @Project Froad Cbank Coremodule Framework Expand Token
     * @Package com.froad.cbank.coremodule.framework.expand.token.annotation
     * @Description 是否向Cookie中添加Token，默认false
     * @author hjz
     * @date 2015-3-19 下午2:32:52
     * @return
     */
    boolean createKey() default false;
}
