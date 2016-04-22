package com.froad.util.validation.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldVerify {

	public String errorMsg() default "";
	
	
	/**
	 * 是否为非空 默认否
	* <p>Function: required</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-2 下午1:25:38
	* @version 1.0
	* @return
	 */
	public boolean required() default false; 
	
	/**
	 * 使用正则表达式进行
	* <p>Function: regular</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-2 下午1:25:45
	* @version 1.0
	* @return
	 */
	public String regular() default "";
	
	/**
	 * 最大长度
	* <p>Function: maxLength</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-2 下午1:28:40
	* @version 1.0
	* @return
	 */
	public int maxLength() default -1;
	
	/**
	 * 最小长度
	* <p>Function: minLength</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-2 下午1:28:58
	* @version 1.0
	* @return
	 */
	public int minLength() default -1;
	
}
