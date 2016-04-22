package com.froad.util.validation.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.froad.util.validation.anno.FieldVerify;
import com.froad.util.validation.entity.FieldVerfyType;
import com.froad.util.validation.entity.SortResult;
import com.froad.util.validation.entity.VerifyRegular;




public class SortRegular {

	
	public static Map<FieldVerfyType, SortResult> sort(VerifyRegular regular){
		
		FieldVerify fieldVerfy = regular.getFieldVerify();
//		ExpandVerify expandVerify = regular.getExpandVerify();

		Map<FieldVerfyType, SortResult> sortRegular = new HashMap<FieldVerfyType, SortResult>();
		
		if(fieldVerfy != null){
			Class<?> clazz = fieldVerfy.getClass();
			FieldVerfyType[] types = FieldVerfyType.values();
			
			Method method = null;
			String errorMsg = null;
			
			try {
				errorMsg = (String) clazz.getMethod("errorMsg").invoke(fieldVerfy);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			
			SortResult sortResult = null;
			for (FieldVerfyType t : types) {
				try {
					method = clazz.getMethod(t.name());
					if(method == null){
						continue;
					}
					sortResult = new SortResult(errorMsg, method.invoke(fieldVerfy));
					sortRegular.put(t,sortResult);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return sortRegular;
	}
	
	
}


