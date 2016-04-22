package com.froad.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class ProductBeanUtil {

    /**
     * 简单的字段赋值
     * @param dest
     * @param orig
     * @return void
     */
    public static void copyProperties(Object dest,Object orig){
        Class destClass = dest.getClass();
        Class origClass = orig.getClass();
        
        Field[] origFields = origClass.getDeclaredFields();
        Method[] origMethods = origClass.getDeclaredMethods();
        Method[] destMethods = destClass.getDeclaredMethods();
        
        String fidleName = null;
        Method setMethod = null;
        Method getMethod = null;
        Method getDestMethod = null;
        Method isSetMethod = null;
        
        Class<?> origTypes = null;
        Class<?> testTpes = null;
        boolean isset = false;
        for (Field origField : origFields) {
            try {
                isset = true;
                
                fidleName = origField.getName();
                origTypes = origField.getType();
                
                setMethod = getMethod(destMethods,fidleName,"set");
                getMethod = getMethod(origMethods,fidleName,"get");
                
                getDestMethod = getMethod(destMethods,fidleName,"get");
                if(getDestMethod==null && (origTypes.toString().equals("boolean") || origTypes.toString().equals("class java.lang.Boolean"))){
                    getDestMethod = getMethod(destMethods,fidleName,"is");
                }
                
                if(getMethod==null && (origTypes.toString().equals("boolean") || origTypes.toString().equals("class java.lang.Boolean"))){
                    getMethod = getMethod(origMethods,fidleName,"is");
                }
                
                isSetMethod = getMethod(origMethods, fidleName, "isSet");
                
                if(setMethod!=null  && getMethod!=null && getDestMethod!=null){
                    if(isSetMethod!=null ){//vo-->
                        isset = (Boolean) isSetMethod.invoke(orig, new Object[0]);
                    }
                    if(isset==false){
                        continue;
                    }
                    Object o = getMethod.invoke(orig, new Object[0]);
                    if(o!=null){
                        testTpes = getDestMethod.getReturnType();
                        if (isJavaClass(origTypes) && isJavaClass(testTpes)) { // 如果是java的class
                            if((origTypes.toString().equals("int") || origTypes.toString().equals("class java.lang.Integer")) && testTpes.toString().equals("short")){
                                Integer d =(Integer)o;
                                if(d!=null){
                                    setMethod.invoke(dest, d.shortValue());
                                }
                            } else if(origTypes.toString().equals("short") && (testTpes.toString().equals("int") || testTpes.toString().equals("class java.lang.Integer"))){
                                Short ss = (Short)o;
                                if(ss!=null){
                                    setMethod.invoke(dest, ss.intValue());
                                }
                            } else if(Date.class.isAssignableFrom(testTpes) && (long.class.isAssignableFrom(origField.getType()) || Long.class.isAssignableFrom(origField.getType()))) { 
                                // 如果po是日期类型 并且vo是long或者Long类型
                                if((Long)o>0){
                                    setMethod.invoke(dest, new Date((Long)o));
                                }
                            } else if(Date.class.isAssignableFrom(origField.getType()) && (long.class.isAssignableFrom(testTpes) || Long.class.isAssignableFrom(testTpes))) { 
                                // 如果po是日期类型 并且vo是long或者Long类型
                                setMethod.invoke(dest, ((Date)o).getTime());
                            } else if(Date.class.isAssignableFrom(testTpes) && (long.class.isAssignableFrom(origTypes) || Long.class.isAssignableFrom(origTypes))) { // 如果po是日期类型 并且vo是long或者Long类型
                                if((Long)o>0){
                                    setMethod.invoke(dest, new Date((Long)o));
                                }
                            } else if(Date.class.isAssignableFrom(origTypes) && (long.class.isAssignableFrom(testTpes) || Long.class.isAssignableFrom(testTpes))) { // 如果po是日期类型 并且vo是long或者Long类型
                                setMethod.invoke(dest, ((Date)o).getTime());
                            } else {
                                setMethod.invoke(dest, o);
                            }
                        }
                    }
                }
            } catch (SecurityException e) {
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
    
    /**
     * 判断一个类是JAVA类型还是用户定义类型
     * @param clazz
     * @return
     */
    public static boolean isJavaClass(Class<?> clazz) {   
        return clazz != null && clazz.getClassLoader() == null;   
    }  
    
    /**
     * 支持double int转换乘倍数
     * @param dest
     * @param orig
     * @param scale double转成int是乘以多少 int转成double是除多少
     * @return void
     */
    public static void copyPropertiesScale(Object dest,Object orig,int scale){
        Class destClass = dest.getClass();
        Class origClass = orig.getClass();
        
        Field[] origFields = origClass.getDeclaredFields();
        Method[] origMethods = origClass.getDeclaredMethods();
        Method[] destMethods = destClass.getDeclaredMethods();
        
        String fidleName = null;
        Method getDestMethod = null;
        Method setMethod = null;
        Method getMethod = null;
        Method isSetMethod = null;
        boolean isset = false;
        
        for (Field origField : origFields) {
            getDestMethod = null;
            setMethod = null;
            getMethod = null;
            try {
                isset = true;
                
                fidleName = origField.getName();
                
                Class<?> origTypes = origField.getType();
                getDestMethod = getMethod(destMethods,fidleName,"get");
                if(getDestMethod==null && (origTypes.toString().equals("boolean") || origTypes.toString().equals("class java.lang.Boolean"))){
                    getDestMethod = getMethod(destMethods,fidleName,"is");
                }
                setMethod = getMethod(destMethods,fidleName,"set");
                getMethod = getMethod(origMethods,fidleName,"get");
                if(getMethod==null && (origTypes.toString().equals("boolean") || origTypes.toString().equals("class java.lang.Boolean"))){
                    getMethod = getMethod(origMethods,fidleName,"is");
                }
                
                if(setMethod!=null  && getMethod!=null && getDestMethod!=null){
                    if(isSetMethod!=null ){//vo-->
                        isset = (Boolean) isSetMethod.invoke(orig, new Object[0]);
                    }
                    if(isset==false){
                        continue;
                    }
                    
                    Object o = getMethod.invoke(orig, new Object[0]);  
                    if(o!=null){
                        Class<?> testTpes = getDestMethod.getReturnType();
                        if (isJavaClass(origTypes) && isJavaClass(testTpes)) { // 如果是java的class
                            if((origTypes.toString().equals("double") || origTypes.toString().equals("class java.lang.Double")) && testTpes.toString().equals("class java.lang.Integer")){
                                Double d =(Double)o;
                                if(d!=null){
                                    setMethod.invoke(dest, Arith.mul(d, scale));
                                }
                            } else if(origTypes.toString().equals("class java.lang.Integer") && (testTpes.toString().equals("class java.lang.Double") || testTpes.toString().equals("double"))){
                                Integer i =(Integer)o;
                                if(i!=null){
                                    Double di = 0.0+i;
                                    setMethod.invoke(dest, Arith.div(di, scale));
                                }
                            } else if((origTypes.toString().equals("int") || origTypes.toString().equals("class java.lang.Integer")) && testTpes.toString().equals("short")){
                                Integer id =(Integer)o;
                                if(id!=null){
                                    setMethod.invoke(dest, id.shortValue());
                                }
                            } else if(origTypes.toString().equals("short") && (testTpes.toString().equals("int") || testTpes.toString().equals("class java.lang.Integer"))){
                                Short ss = (Short)o;
                                if(ss!=null){
                                    setMethod.invoke(dest, ss.intValue());
                                }
                            }  else if(Date.class.isAssignableFrom(testTpes) && (long.class.isAssignableFrom(origField.getType()) || Long.class.isAssignableFrom(origField.getType()))) { 
                                // 如果po是日期类型 并且vo是long或者Long类型
                                if((Long)o>0){
                                    setMethod.invoke(dest, new Date((Long)o));
                                }
                            } else if(Date.class.isAssignableFrom(origField.getType()) && (long.class.isAssignableFrom(testTpes) || Long.class.isAssignableFrom(testTpes))) { 
                                // 如果po是日期类型 并且vo是long或者Long类型
                                setMethod.invoke(dest, ((Date)o).getTime());
                            }  else if(origTypes.toString().equals("boolean") && testTpes.toString().equals("class java.lang.Integer")){
                            	if((Boolean)o){
                            		setMethod.invoke(dest, 1);
                            	} else {
                            		setMethod.invoke(dest, 0);
                            	}
                            } else {
                                setMethod.invoke(dest, o);
                            }
                        }
                    }
                }
            } catch (SecurityException e) {
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

    private static Method getMethod(Method[] methods,String fieldName,String methodPrix){
        Method m = null;
        StringBuilder methodName = new StringBuilder();
        methodName.append(methodPrix);
        methodName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1, fieldName.length()));
        for(Method method : methods){
            if(method.getName().equals(methodName.toString())){
                m = method;
                break;
            }
        }
        return m;
    }
    
}
