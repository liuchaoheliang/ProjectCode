package com.froad.util.validation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.froad.util.validation.anno.ExpandVerify;
import com.froad.util.validation.anno.FieldVerify;
import com.froad.util.validation.core.MatchingRule;
import com.froad.util.validation.entity.VerifyException;
import com.froad.util.validation.entity.VerifyRegular;


public class Verify {
	
	public static boolean verifyThrowException(Object entity){
		
		if(entity == null){
			throw new VerifyException("校验原始数据为空");
		}
		
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		Map<Field, VerifyRegular> regular = new HashMap<Field, VerifyRegular>();
		
		for (Field field : fields) {
			regular.put(field, new VerifyRegular(field.getAnnotation(FieldVerify.class), field.getAnnotation(ExpandVerify.class)));			
		}
		
		return MatchingRule.verify(regular,entity);
		
	}
}
