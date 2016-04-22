package com.froad.cbank.coremodule.framework.common.valid;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * Bean属性验证
 * @ClassName ValidBeanField
 * @author zxl
 * @date 2015年4月23日 下午3:11:02
 */
public class ValidBeanField {
	
	
	public static void valid(Object object) throws ValidException{
		
		Field[]  fields= object.getClass().getDeclaredFields();
		
		for(Field f : fields){
			
			Object ob = null;
			
			if(f.getAnnotation(NotEmpty.class)!=null){
				
				ob = getFieldValueByName(f.getType().getSimpleName(),f.getName(),object);
				
				if(ob == null || StringUtils.isBlank(ob.toString())){
					
					NotEmpty not = f.getAnnotation(NotEmpty.class);
					
					if(StringUtils.isNotBlank(not.value())){
						throw new ValidException(not.value());
					}else{
						throw new ValidException(f.getName()+" is null");
					}
					
				}
			}
			
			if(f.getAnnotation(Regular.class)!=null){
				
				if(ob == null){
					ob = getFieldValueByName(f.getType().getSimpleName(),f.getName(),object);
				}
				
				if(ob != null && StringUtils.isNotBlank(ob.toString())){
					Regular reg = f.getAnnotation(Regular.class);
					Pattern p = Pattern.compile(reg.reg());
					Matcher m = p.matcher(ob.toString());
					if(!m.matches()){
						throw new ValidException(reg.value());
					}
				}
				
			}
			
		}
		
	}
	
	public static Object getFieldValueByName(String type,String fieldName, Object object) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			if(type.equals("boolean")){
				getter = "is" + firstLetter + fieldName.substring(1);
			}
			Method method = object.getClass().getMethod(getter, new Class[] {});
			return method.invoke(object, new Object[] {});
		} catch (Exception e) {
		}
		return null;
	}
	
}
