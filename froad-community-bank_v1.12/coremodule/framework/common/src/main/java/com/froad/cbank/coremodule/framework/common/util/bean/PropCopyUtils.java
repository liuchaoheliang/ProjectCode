package com.froad.cbank.coremodule.framework.common.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
        	//跳过静态属性
        	if(Modifier.isStatic(field.getModifiers())){
				continue;
			}
        	//跳过自定义类型
			if(!isJavaBasicType(field.getType())){
				continue;
			}
            try {
                destField = destClass.getDeclaredField(field.getName());
                destField.setAccessible(true);
                field.setAccessible(true);
                sourceValue = field.get(orig);
                if(sourceValue==null){
                	continue;
                }
            } catch (Exception e) {
            	continue;
            }
            
            copyFieldValue(dest,destField,sourceValue,field);
        }
        
        return dest;
    }
    
    public static void copyFieldValue(Object dest,Field dField,Object sourceValue, Field oField){
        Class targetType = dField.getType();
        Class origType = oField.getType();
        Class sourceType = sourceValue.getClass();
        
        if(targetType.equals(origType)){
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
    
    
    /**
     * 判断是否为Java基础类型
     * @param clazzType
     * @return
     */
    public static boolean isJavaBasicType(Class<?> clazzType){
		if(clazzType == String.class){
			return true;
		}
		if(clazzType == int.class || clazzType == Integer.class){
			return true;
		}
		if(clazzType == long.class || clazzType == Long.class){
			return true;
		}
		if(clazzType == boolean.class || clazzType == Boolean.class){
			return true;
		}
		if(clazzType == double.class || clazzType == Double.class){
			return true;
		}
		if(clazzType == float.class || clazzType == Float.class){
			return true;
		}
		/*if(clazzType == char.class || clazzType == Character.class){
			return true;
		}
		if(clazzType == short.class || clazzType == Short.class){
			return true;
		}
		if(clazzType == byte.class || clazzType == Byte.class){
			return true;
		}*/
		if(clazzType == Date.class){
			return true;
		}
		return false;
		
	}
    
}
