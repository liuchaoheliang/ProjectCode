package com.froad.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class PropCopyUtils {
    public static <T> T createNewInst(Class<T> dest,Object orig){
        T newInst = null;
        try {
            newInst = dest.newInstance();
            copyProperties(newInst,orig);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return newInst;
    }
    
    public static <T> List<T> copyProperties(Class<T> clazz,Object collectionOrObject){
    	if(collectionOrObject==null||clazz==null){
    		return null;
    	}
    	
    	Object temp = null;
    	if(collectionOrObject instanceof Collection){
    		List resultList = new ArrayList();
    		for(Object obj : (List)collectionOrObject){
    			temp = createNewInstance(clazz);
    			resultList.add(temp);
    			copyProperties(temp, obj);
    		}
    		return resultList;
    	}else{
    		temp = createNewInstance(clazz);
    		copyProperties(temp, collectionOrObject);
    	}
    	return null;
    }

	private static <T> T createNewInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public static <T> T copyProperties(T dest,Object orig){
        if(dest==null||orig==null){
            return dest;
        }
        Field[] fields = orig.getClass().getDeclaredFields();
        Class origClass = orig.getClass();
        Class destClass = dest.getClass();
        Field destField = null;
        Object sourceValue = null;
        
        for(Field field : fields){
            try {
                destField = destClass.getDeclaredField(field.getName());
                destField.setAccessible(true);
                field.setAccessible(true);
                sourceValue = field.get(orig);
                if(sourceValue==null){
                	continue;
                }
            } catch (Exception e) {
            	StringBuilder getterName = new StringBuilder();
            	getterName.append("get").append(field.getName().charAt(0)).append(field.getName().substring(1));
            	try {
					Method m = origClass.getMethod(getterName.toString());
					sourceValue = m.invoke(orig);
					if(sourceValue==null){
						continue;
					}
				} catch (SecurityException e1) {
					e1.printStackTrace();
					return dest;
				} catch (Exception e1) {
					continue;
				}
                return dest;
            }
            
            copyFieldValue(dest,destField,sourceValue);
        }
        
        return dest;
    }
    
    public static void copyFieldValue(Object dest,Field dField,Object sourceValue){
        Class targetType = dField.getType();
        Class sourceType = sourceValue.getClass();
        
        if(targetType.equals(sourceType)){
        	BaseConverter.setValue(dest, dField, sourceValue);
        	return;
        }
        
        Converter converter = ConvertRegister.findConverter(sourceType,targetType);
        if(converter!=null){
        	Object targetValue = converter.convert(sourceValue);
        	BaseConverter.setValue(dest, dField, targetValue);
        	return;
        }
        
        Object targetValue = null;
        if(targetType.isEnum()){
        	targetValue = EnumConverter.convertToEnum(sourceValue, targetType);
        	BaseConverter.setValue(dest, dField, targetValue);
        	return;
        }
        
        
        if(targetType.getClassLoader()!=null){
        	try {
        		targetValue = BaseConverter.getValue(dest, dField);
        		if(targetValue==null){
        			targetValue = targetType.newInstance();
        		}
				copyProperties(targetValue, sourceValue);
			} catch (Exception e) {
				e.printStackTrace();
			} 
        }
        
    }
    
}
