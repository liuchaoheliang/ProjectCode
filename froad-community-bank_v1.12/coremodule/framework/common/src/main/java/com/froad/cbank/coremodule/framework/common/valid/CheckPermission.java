/**
 * Project Name:framework-common
 * File Name:CheckPermission.java
 * Package Name:com.froad.cbank.coremodule.framework.common.valid
 * Date:2015年9月30日上午10:51:23
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.framework.common.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:CheckPermission
 * Reason:	 添加权限校验注解
 * Date:     2015年9月30日 上午10:51:23
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {
	String[] keys() default {};
	boolean isForce() default true;

}
