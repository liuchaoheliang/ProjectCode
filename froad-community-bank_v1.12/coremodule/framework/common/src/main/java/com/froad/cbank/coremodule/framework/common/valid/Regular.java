package com.froad.cbank.coremodule.framework.common.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Regular {
	
	//错误信息
	String value();
	
	//正则表达式
	String reg();
	
}
