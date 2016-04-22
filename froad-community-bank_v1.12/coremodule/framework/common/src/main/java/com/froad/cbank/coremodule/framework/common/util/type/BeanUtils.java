package com.froad.cbank.coremodule.framework.common.util.type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;

public class BeanUtils{
	
	/**
	 * 属性copy 不深层次copy
	 * @tilte copyProperties
	 * @author zxl
	 * @date 2015年6月4日 下午3:33:14
	 * @param toObj
	 * @param fromObj
	 */
	public static void copyProperties(Object toObj,Object fromObj){
		
		try {
			Field[] source_fields = fromObj.getClass().getDeclaredFields();
			Field[] target_fields = toObj.getClass().getDeclaredFields();
			for (java.lang.reflect.Field source_field : source_fields) {
				
				if(Modifier.isStatic(source_field.getModifiers())){
					continue;
				}
				
				for (java.lang.reflect.Field target_field : target_fields) {
					if (source_field.getName().trim().equalsIgnoreCase(target_field.getName().trim())) {
						String sourceType=source_field.getType().toString();
						String targetType=target_field.getType().toString();
						Object ob=getFieldValueByName(sourceType,source_field.getName(),fromObj);
						if(ob == null) continue;
						setFieldValueByName(target_field.getName(),toObj,sourceType,targetType,ob);
					}
				}
			}
		} catch (Exception e) {
		} 
	} 
	/**
	 * 获取对象属性的值
	 * @param fieldName
	 * @param source
	 * @return
	 */
	public static Object getFieldValueByName(String sourceType,String fieldName, Object source) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			if(sourceType.substring(sourceType.lastIndexOf(".")+1).equals("boolean")){
				getter = "is" + firstLetter + fieldName.substring(1);
			}
			Method method = source.getClass().getMethod(getter, new Class[] {});
			return method.invoke(source, new Object[] {});
		} catch (Exception e) {
		}
		return null;
	}

    /**
     * 给目标对象属性赋值
     * @param fieldName
     * @param target
     * @param type
     * @param value
     * @return
     */
	@SuppressWarnings("deprecation")
	public static Object setFieldValueByName(String fieldName, Object target,String sourceType,String targetType,Object value) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String setter = "set" + firstLetter + fieldName.substring(1);
			Class<?>[] cazz = null;
			targetType=targetType.substring(targetType.lastIndexOf(".")+1);
		    String rvalue=value.toString();
		    if(targetType.equals("String")){
				cazz=new Class[] {String.class};
				value=String.valueOf(rvalue);
			}else if(targetType.equals("int")){
				cazz=new Class[] {int.class};
				value=Integer.valueOf(rvalue);
			}else if(targetType.equals("Integer")){
				cazz=new Class[] {Integer.class};
				value=Integer.valueOf(rvalue);
			}else if(targetType.equals("long")){
				cazz=new Class[] {long.class};
				value=Long.valueOf(rvalue);
			}else if(targetType.equals("Long")){
				cazz=new Class[] {Long.class};
				value=Long.valueOf(rvalue);
			}else if(targetType.equals("double")){
				cazz=new Class[] {double.class};
				value=Double.valueOf(rvalue);
			}else if(targetType.equals("Double")){
				cazz=new Class[] {Double.class};
				value=Double.valueOf(rvalue);
			}else if(targetType.equals("Date")){
				cazz=new Class[] {Date.class};
				value=Date.parse(rvalue);
			}else if(targetType.equals("short")){
				cazz=new Class[] {short.class};
				value=Short.valueOf(rvalue);
			}else if(targetType.equals("Short")){
				cazz=new Class[] {Short.class};
				value=Short.valueOf(rvalue);
			}else if(targetType.equals("float")){
				cazz=new Class[] {float.class};
				value=Float.valueOf(rvalue);
			}else if(targetType.equals("Float")){
				cazz=new Class[] {Float.class};
				value=Float.valueOf(rvalue);
			}else if(targetType.equals("boolean")){
				cazz=new Class[] {boolean.class};
				value=Boolean.valueOf(rvalue);
			}else if(targetType.equals("Boolean")){
				cazz=new Class[] {Boolean.class};
				value=Boolean.valueOf(rvalue);
			}else if(targetType.equals("Object")){
				cazz=new Class[] {Object.class};
			}
		    if(cazz == null) return null;
			Method method = target.getClass().getMethod(setter, cazz);
			return method.invoke(target, new Object[] {value});
		} catch (Exception e) {
		}
		return null;
	}
	
}

