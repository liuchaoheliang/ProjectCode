/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: MerchantAccountLogic.java
 * @Package com.froad.util
 * @see: 属性拷贝类 
 * @author f-road
 * @date 2015年3月29日
 */
package com.froad.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.froad.logback.LogCvt;



/**    
 * <p>Title: BeanUtil.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月29日 下午9:44:51   
 */   
public class BeanUtil {
	/**
	 * 拷贝属性
	 * @Title: copyProperties 
	 * @author vania
	 * @version 1.0
	 * @see: 拷贝属性
	 * @param dest 目标对象
	 * @param orig 源对象
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 * @return void    返回类型 
	 * @throws
	 */
	@Deprecated
	public static void copyProperties(Object dest, Object orig) {
		copyProperties(dest, orig, null);
	}
	
	/**
	 * 拷贝属性
	 * @Title: copyProperties 
	 * @author vania
	 * @version 1.0
	 * @see: 拷贝属性
	 * @param dest 目标对象
	 * @param orig 源对象
	 * @param excludeTargetField 指定目标排除的FiendName ， 不会为指定的排除字段copy值
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return void    返回类型 
	 * @throws
	 */
    @SuppressWarnings("unused")
    @Deprecated
	public static void copyProperties(Object dest, Object orig, String[] excludeTargetField) {
//		if (null == orig) {
//			dest = null;
//			return;
//		}
        if (dest == null) {
        	throw new IllegalArgumentException("目标对象不能为空！！！");
        }
        if (orig == null) {
        	throw new IllegalArgumentException("源对象不能为空！！！");
        }
        Class<?> destClass = dest.getClass();
        Class<?> origClass = orig.getClass();
        
        Method[] origMethods = origClass.getDeclaredMethods();  // 获取全部方法
        Method[] destMethods = destClass.getDeclaredMethods();  // 获取全部方法
        
//        String[] origFieldNames = getFieldNames(origClass);// 获取全部属性
//        String[] destFieldNames = getFieldNames(destClass);// 获取全部属性
        
//        String[] intersectFieldNames = intersect(origFieldNames, destFieldNames);// 取两个类相同的属性名
        
//        origMethods = intersect(origMethods, intersectFieldNames); // 只获取与字段名有关的方法
//        destMethods = intersect(destMethods, intersectFieldNames); // 只获取与字段名有关的方法
        
        Method setMethod = null;
        Method getMethod = null;
        /*
		if (orig instanceof org.apache.thrift.TBase && !(dest instanceof org.apache.thrift.TBase)) {// vo -----> po
			for (String FieldName : intersectFieldNames) {
				if (isExists(excludeTargetField, FieldName))
					continue; // 过滤掉属性
				setMethod = getMethod(destMethods, FieldName, "set");
				getMethod = getMethod(origMethods, FieldName, "get");
				if(null == getMethod){
					getMethod = getMethod(origMethods, FieldName, "is"); // 针对于Boolean类型的字段					
				}
//				Method isSetMethod = origClass.getMethod("isSet" + firstUpperCase(FieldName), new Class<?>[0]);
				Method isSetMethod = getMethod(origMethods, FieldName, "isSet");
				boolean isset = (Boolean) isSetMethod.invoke(orig, new Object[0]);
				if(isset) { // 如果vo设置了值  则拷贝该属性
					if (setMethod != null && getMethod != null) {
						Object o = getMethod.invoke(orig, new Object[0]);
						setMethod.invoke(dest, o);
					}
				} else { // 不拷贝该属性
					continue;
				}
				
			}
		} else if (dest instanceof org.apache.thrift.TBase && !(orig instanceof org.apache.thrift.TBase)) {// po -----> vo
			for (String FieldName : intersectFieldNames) {
				getMethod = getMethod(origMethods, FieldName, "get");
				if(null == getMethod){
					getMethod = getMethod(origMethods, FieldName, "is"); // 针对于Boolean类型的字段					
				}
				Object o = getMethod.invoke(orig, new Object[0]); 
				if(isExists(excludeTargetField, FieldName) || null == o) {// 如果过滤改属性或者po没设置值   则vo中对应的属性也不设置值
					Method unsetMethod = getMethod(destMethods, FieldName, "unset"); // 设置该字段为unsetxxx 
					unsetMethod.invoke(dest);
				} else { // 如果po设置值   则vo设置值
					setMethod = getMethod(destMethods, FieldName, "set");	
					if (setMethod != null && getMethod != null) {
						Object ov = getMethod.invoke(orig, new Object[0]);
						setMethod.invoke(dest, ov);
					}
				}
			}
		} else { // 其他类型转换直接调用Apache的组件   这里可以考虑用重庆的
			BeanUtils.copyProperties(dest, orig);
		}
		
		*/
//        if(List.class.isAssignableFrom(origClass) && List.class.isAssignableFrom(destClass)){ // 集合
//        	List<?> origList = (List<?>)orig;
//        	List<?> destList = (List<?>)dest;
//        	for (Object origObject : origList) {
//        		origClass;
//        		Object destObject = origList.;
//        		copyProperties(destObject, origObject);
//        		origList.add(destObject);
//        	}
//        }
        
        Field[] destFields = destClass.getDeclaredFields();
        Field[] origFields = origClass.getDeclaredFields();
//		for (String FieldName : intersectFieldNames) {
		for (Field destField : destFields) {
			Field origField = getField(origFields, destField.getName());
			if (origField != null) {
				try {
					String FieldName = destField.getName();
					origField.setAccessible(true); // 设置可访问属性为true
					destField.setAccessible(true); // 设置可访问属性为true
					if (isJavaClass(origField.getType()) && isJavaClass(destField.getType())) { // 如果是java的class
						if (Collection.class.isAssignableFrom(origField.getClass())) { // 如果是集合
							Object origObject = origField.get(orig);
							if(origObject == null)continue;
//							Collection list = new ArrayList();
							Collection origList = (Collection) origObject;
							
//							Type targetType = destField.getGenericType();
//							Class<?> genericClazz = getActualTypeArguments(targetType)[0];
							
							Collection list = null;
							list = (Collection) origField.getType().newInstance();
							
							Object destObject = null;
							for (Object origObj : origList) {
								try {
									destObject = destClass.newInstance();
								} catch (InstantiationException e) {
									e.printStackTrace();
								}
//								copyProperties(dest, origObj, excludeTargetField);
//								list.add(dest);
//								Object oo = copyProperties(genericClazz, origObject, null);
//								list.add(oo);
								
//								copyProperties(dest, origObj, excludeTargetField);
								
								copyProperties(destObject, origObj, null);
								list.add(destObject);
							}
//							origField.set(dest, list);
							setMethod = getMethod(destMethods, FieldName, "set");
							if (setMethod != null)
								setMethod.invoke(destObject, list);
							else
								origField.set(destObject, list);
							continue;
						}
						
						
						setMethod = getMethod(destMethods, FieldName, "set");
						getMethod = getMethod(origMethods, FieldName, "get");
						if (null == getMethod) {
							getMethod = getMethod(origMethods, FieldName, "is"); // 针对于Boolean类型的字段
						}

						// if(isExists(excludeTargetField, FieldName)) {
						// 		continue;
						// }

						Method isSetMethod = getMethod(origMethods, FieldName, "isSet");
						if (isSetMethod != null && getMethod(destMethods, FieldName, "isSet") == null) { // vo------>po
							if (isExists(excludeTargetField, FieldName))
								continue; // 过滤掉属性
							boolean isset = (Boolean) isSetMethod.invoke(orig, new Object[0]);
							if (isset) { // 如果vo设置了值 则拷贝该属性
								if (setMethod != null && getMethod != null) {
									Object o = getMethod.invoke(orig, new Object[0]);
//									setMethod.invoke(dest, o);	
									if(Date.class.isAssignableFrom(destField.getType()) && (long.class.isAssignableFrom(origField.getType()) || Long.class.isAssignableFrom(origField.getType()))) { // 如果po是日期类型 并且vo是long或者Long类型
										setMethod.invoke(dest, new Date((Long)o));
									} else {
										setMethod.invoke(dest, o);										
									}
								}
							} else { // 不拷贝该属性
								continue;
							}
						} else if (isSetMethod == null && getMethod(destMethods, FieldName, "isSet") != null) {// po  -----> vo
							Object o = getMethod.invoke(orig, new Object[0]);
							if (isExists(excludeTargetField, FieldName) || null == o) {// 如果过滤改属性或者po没设置值  则vo中对应的属性也不设置值
								Method unsetMethod = getMethod(destMethods, FieldName, "unset"); // 设置该字段为unsetxxx
								unsetMethod.invoke(dest);
							} else { // 如果po设置值 则vo设置值
								setMethod = getMethod(destMethods, FieldName, "set");
								if (setMethod != null && getMethod != null) {
									Object ov = getMethod.invoke(orig, new Object[0]);
//									setMethod.invoke(dest, ov);									
									if(Date.class.isAssignableFrom(origField.getType()) && (long.class.isAssignableFrom(destField.getType()) || Long.class.isAssignableFrom(destField.getType()))) { // 如果po是日期类型 并且vo是long或者Long类型
										setMethod.invoke(dest, ((Date)origField.get(orig)).getTime());
									} else {
										setMethod.invoke(dest, ov);								
									}
								}
							}
						} else {
							if(!isExists(excludeTargetField, destField.getName())) 
								BeanUtils.copyProperty(dest, destField.getName(), origField.get(orig));
							else
								continue;
						}
					} else { // 自定义的类型用递归调用
						// origField
//						Class<?> destFieldClazz = destField.getType();
//						Object destFieldObject = destFieldClazz.newInstance();
//						Class<?> origFieldClazz = origField.getType();
//						Object origFieldObject = origFieldClazz.newInstance();
//						origFieldObject =  origField.get(orig); // 获得源数据
//						copyProperties(destFieldObject, origFieldObject, null); // 递归copy
//						destField.set(dest, destFieldObject);
						
						if(!isExists(excludeTargetField, destField.getName())) 
							copyProperties(destField.get(dest), origField.get(orig), null); // 递归copy
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			} else {
				continue;
			}
		}
    }
    
    /**
     * 拷贝属性
     * @Title: copyProperties 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param destClass
     * @param origObject
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @return Object    返回类型 
     * @throws
     */
	public static <DestClass, OrigObject> Object copyProperties(Class<DestClass> destClass, OrigObject origObject) {
		return copyProperties(destClass, origObject, null);
	}

	/**
	 * 拷贝属性
	 * @Title: copyProperties 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param destClass
	 * @param origObject
	 * @param excludeTargetField
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return Object    返回类型 
	 * @throws
	 */
	public static <DestClass, OrigObject> Object copyProperties(Class<DestClass> destClass, OrigObject origObject, String[] excludeTargetField) {
		if (null == origObject)
			return null;

		Class<?> origClass = origObject.getClass();
		if (destClass == origClass) {
			return origObject;
		}
		DestClass destObject = null;
		if(isJavaClass(origClass)) {			
		
			if (Collection.class.isAssignableFrom(origClass)) { // 如果是集合
				Collection<?> list = (Collection)origObject;
				if(list == null)return null;
				
	//			Collection<DestClass> destList = new ArrayList<DestClass>();
				Collection<DestClass> destList = null;
				try {
					destList = (Collection<DestClass>) origClass.newInstance();
				} catch (Exception e) {
					LogCvt.debug("属性拷贝出错", e);
					return null;
				}
				for (Object origobj : list) {
					DestClass destObj = null;
					destObj = (DestClass)copyProperties(destClass, origobj);
					destList.add(destObj);
				}
				return destList;
			} else {
				return origObject;
			}		
		}
		
//		try {
//			destObject = destClass.newInstance();
//		} catch (Exception e) {
//			e.printStackTrace();
//			LogCvt.error("属性拷贝出错", e);
//			return null;
//		}
		
//		  Field[] destFields = destClass.getDeclaredFields();
//        Field[] origFields = origClass.getDeclaredFields();
		Field[] origFields = getClassFields(origClass, true).toArray(new Field[] {});
		Field[] destFields = getClassFields(destClass, true).toArray(new Field[] {});
        
//        Method[] origMethods = origClass.getDeclaredMethods();  // 获取全部方法
//        Method[] destMethods = destClass.getDeclaredMethods();  // 获取全部方法
        Method[] origMethods = getClassMothds(origClass, true).toArray(new Method[] {});  // 获取全部方法
        Method[] destMethods = getClassMothds(destClass, true).toArray(new Method[] {});  // 获取全部方法
        
//        String[] origFieldNames = getFieldNames(origClass);// 获取全部属性
//        String[] destFieldNames = getFieldNames(destClass);// 获取全部属性
        
//        String[] intersectFieldNames = intersect(origFieldNames, destFieldNames);// 取两个类相同的属性名
        
//        origMethods = intersect(origMethods, intersectFieldNames); // 只获取与字段名有关的方法
//        destMethods = intersect(destMethods, intersectFieldNames); // 只获取与字段名有关的方法
        
        Method setMethod = null;
        Method getMethod = null;
        for (Field destField : destFields) {

        	String destFieldName = destField.getName();
			Field origField = getField(origFields, destFieldName);
			if (origField != null) {
				if ("serialVersionUID".equals(destFieldName) || isExists(excludeTargetField, destFieldName)) continue; // 跳过序列号和不拷贝属性的字段
				try {
					origField.setAccessible(true); // 设置可访问属性为true
					destField.setAccessible(true); // 设置可访问属性为true
					Class<?> origFieldType = origField.getType();// 源字段的类型
					Class<?> destFieldType = destField.getType();// 目标字段的类型
					
					if (isJavaClass(origFieldType) && isJavaClass(destFieldType)) { // 如果是java的class
						
						if (Collection.class.isAssignableFrom(origFieldType)) { // 如果是集合
//							Class<?> origFieldGenericClazz = (Class<?>)getActualTypeArguments(origField.getGenericType())[0]; // 源字段的泛型参数类型
							Class<?> destFieldGenericClazz = (Class<?>)getActualTypeArguments(destField.getGenericType())[0]; // 目标字段的泛型参数类型
							
							Collection origFieldValue = (Collection<?>) origField.get(origObject);
							if(origFieldValue == null) continue;
//							Collection<OrigObject> origList = (Collection<OrigObject>) origObject;
														
							Collection list = null;
//							list = (Collection<DestClass>) origFieldType.newInstance();
							
							if(List.class.isAssignableFrom(origFieldType)) {
								list = new ArrayList();
							} else if(Set.class.isAssignableFrom(origFieldType)) {
								list = new HashSet();
							}			
							for (Object origObj : origFieldValue) {
//								copyProperties(dest, origObj, excludeTargetField);
//								list.add(dest);
//								Object oo = copyProperties(genericClazz, origObject, null);
//								list.add(oo);
								
//								copyProperties(dest, origObj, excludeTargetField);
//								if(isJavaClass(destFieldGenericClazz))
//									list.add(origObj);
//								else
									list.add(copyProperties(destFieldGenericClazz, origObj));
							}
//							origField.set(destObject, list);
							
							setMethod = getMethod(destMethods, destFieldName, "set");
							if (setMethod != null)
								setMethod.invoke(destObject, list);
							else
								origField.set(destObject, list);
							continue;
						}
						
						if(Map.class.isAssignableFrom(origFieldType)) { // 如果是map
							Object originListObject;
							try {
								originListObject = origField.get(origObject);
								if(originListObject == null){
									continue;
								}
								Map<?, ?> origin = (Map<?, ?>)originListObject;
								if (origin != null && origin.size() != 0) {
									Type targetType = destField.getGenericType();
//									ParameterizedType parameterizedType = (ParameterizedType) targetType;  
									Type[] types = getActualTypeArguments(targetType);
									Class<?> genericClazzKey = (Class<?>)types[0];
									Class<?> genericClazzVal = (Class<?>)types[1];
//									for (Map.Entry<?,?> originKV: origin.entrySet()) {
//										Object destKey = copyProperties(genericClazzKey, originKV.getKey());
//										Object destVal = copyProperties(genericClazzKey, originKV.getValue());
//									
//									}
									Map dest = null ;
									if(isJavaClass(genericClazzKey) && isJavaClass(genericClazzVal)){
//										destField.set(destObject , originListObject);
										dest = (Map)originListObject ;
										
									} else {
										dest = new HashMap(); 
										Object key = null, value = null;
										for (Map.Entry<?, ?> originKV : origin.entrySet()) {
											Object destKey = copyProperties(genericClazzKey, originKV.getKey(),null);
											Object destVal = copyProperties(genericClazzVal, originKV.getValue(),null);
											dest.put(destKey, destVal);
										}
									}
									setMethod = getMethod(destMethods, destFieldName, "set");
									if (setMethod != null)
										setMethod.invoke(destObject, dest);
									else
										destField.set(destObject, dest);
									continue;
									
//									LinkedHashMap<Object, Object> targetMap = new LinkedHashMap<Object,Object>();
//									for (Map.Entry<?, ?> originEntity : origin.entrySet()) {
//										//-------------------未实现
//									}
									
								}
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							continue;
						}
						
						if(destObject == null)
						try {
							destObject = destClass.newInstance();
						} catch (Exception e) {
							LogCvt.debug("属性拷贝出错", e);
							continue;
						}
						
						setMethod = getMethod(destMethods, destFieldName, "set");
						getMethod = getMethod(origMethods, destFieldName, "get");
						if (null == getMethod) {
							getMethod = getMethod(origMethods, destFieldName, "is"); // 针对于Boolean类型的字段
						}

						// if(isExists(excludeTargetField, FieldName)) {
						// 		continue;
						// }

						Method isSetMethod = getMethod(origMethods, destFieldName, "isSet");
						if (isSetMethod != null && getMethod(destMethods, destFieldName, "isSet") == null) { // vo------>po
//							if (isExists(excludeTargetField, destFieldName))
//								continue; // 过滤掉属性
							boolean isset = (Boolean) isSetMethod.invoke(origObject, new Object[0]);
							if (isset) { // 如果vo设置了值 则拷贝该属性
								if (setMethod != null && getMethod != null) {
									Object o = getMethod.invoke(origObject, new Object[0]);
//									setMethod.invoke(dest, o);	origFieldType
									if(Date.class.isAssignableFrom(destFieldType) && (long.class.isAssignableFrom(origFieldType) || Long.class.isAssignableFrom(origFieldType))) { // 如果po是日期类型 并且vo是long或者Long类型
										setMethod.invoke(destObject, new Date((Long) o));
									} else {
//										setMethod.invoke(destObject, o);
										setValue(destObject, setMethod, origFieldType, destFieldType, o);
									}
								}
							} else { // 不拷贝该属性
								continue;
							}
						} else if (isSetMethod == null && getMethod(destMethods, destFieldName, "isSet") != null) {// po  -----> vo
							Object o = getMethod.invoke(origObject, new Object[0]);
//							if (isExists(excludeTargetField, destFieldName) || null == o) {// 如果过滤改属性或者po没设置值  则vo中对应的属性也不设置值
							if (null == o) {// 如果过滤改属性或者po没设置值  则vo中对应的属性也不设置值
								Method unsetMethod = getMethod(destMethods, destFieldName, "unset"); // 设置该字段为unsetxxx
								unsetMethod.invoke(destObject);
							} else { // 如果po设置值 则vo设置值
								setMethod = getMethod(destMethods, destFieldName, "set");
								if (setMethod != null && getMethod != null) {
//									Object ov = getMethod.invoke(origObject, new Object[0]);
//									setMethod.invoke(dest, o);									
									if(Date.class.isAssignableFrom(origFieldType) && (long.class.isAssignableFrom(destFieldType) || Long.class.isAssignableFrom(destFieldType))) { // 如果po是日期类型 并且vo是long或者Long类型
										setMethod.invoke(destObject, ((Date)o).getTime());
									} else {
//										setMethod.invoke(destObject, o);	
										setValue(destObject, setMethod, origFieldType, destFieldType, o);
									}
								}
							}
						} else {
//							if(!isExists(excludeTargetField, destField.getName())) 
								BeanUtils.copyProperty(destObject, destField.getName(), origField.get(origObject));
//							else
//								continue;
						}

					} else if (Enum.class.isAssignableFrom(origFieldType) && Enum.class.isAssignableFrom(destFieldType)) {// 枚举类型
						Enum<?> origObjectEnum = null;
						Enum<?> destObjectEnum = null;
						if (destClass.isEnum() && origClass.isEnum()) {
							origObjectEnum = (Enum<?>)origObject;
							return destObjectEnum = Enum.valueOf((Class<Enum>) destFieldType, origObjectEnum.name());
						}
						origObjectEnum = (Enum<?>) origField.get(origObject);
						if (null != origObjectEnum) {
							destObjectEnum = Enum.valueOf((Class<Enum>) destFieldType, origObjectEnum.name());
						}
						setMethod = getMethod(destMethods, destFieldName, "set");
						getMethod = getMethod(origMethods, destFieldName, "get");
						if(destObject == null)
							try {
								destObject = destClass.newInstance();
							} catch (Exception e) {
								LogCvt.debug("属性拷贝出错", e);
								continue;
							}	
						Method isSetMethod = getMethod(origMethods, destFieldName, "isSet");
						if (isSetMethod != null && getMethod(destMethods, destFieldName, "isSet") == null) { // vo------>po
							boolean isset = (Boolean) isSetMethod.invoke(origObject, new Object[0]);
							if (isset) { // 如果vo设置了值 则拷贝该属性
								destField.set(destObject, destObjectEnum);								
							}else{
								continue;
							}
						} else if (isSetMethod == null && getMethod(destMethods, destFieldName, "isSet") != null) { // po------>vo
							if(destObjectEnum == null){
								Method unsetMethod = getMethod(destMethods, destFieldName, "unset"); // 设置该字段为unsetxxx
								unsetMethod.invoke(destObject);								
							} else {
								setMethod = getMethod(destMethods, destFieldName, "set");
								setMethod.invoke(destObject, destObjectEnum);
							}
						}
						
					} else { // 自定义的类型用递归调用
						// origField
//						Class<?> destFieldClazz = destField.getType();
//						Object destFieldObject = destFieldClazz.newInstance();
//						Class<?> origFieldClazz = origField.getType();
//						Object origFieldObject = origFieldClazz.newInstance();
//						origFieldObject =  origField.get(orig); // 获得源数据
//						copyProperties(destFieldObject, origFieldObject, null); // 递归copy
//						destField.set(dest, destFieldObject);
						
//						if(!isExists(excludeTargetField, destField.getName())) {
//							copyProperties(destField.get(dest), origField.get(orig), null); // 递归copy
							
//							Class<?> origFieldClazz = origField.getType();
//							Object origFieldObject = origFieldClazz.newInstance();
//							origFieldObject =  origField.get(orig); // 获得源数据
//							copyProperties(destFieldClazz, origFieldObject, null); // 递归copy
							if(destObject == null)
								try {
									destObject = destClass.newInstance();
								} catch (Exception e) {
									LogCvt.debug("属性拷贝出错", e);
									continue;
								}
							destField.set(destObject, copyProperties(destFieldType, origField.get(origObject), null));
//						}
					}
				} catch (Exception e) {
					LogCvt.debug("属性拷贝出错!跳过" + destFieldName + "该属性", e);
					continue;
				}
			} else {
				continue;
			}
		
        }
		
//		if (List.class.isAssignableFrom(origObject.getClass())) {
//			List<DestClass> list = new ArrayList<DestClass>();
//			List<OrigObject> origList = (List<OrigObject>) origObject;
//			for (OrigObject origObj : origList) {
//				try {
//					destObject = destClass.newInstance();
//				} catch (InstantiationException e) {
//					e.printStackTrace();
//				}
//				copyProperties(destObject, origObj, excludeTargetField);
//				list.add(destObject);
//			}
//			return list;
//		} else {
//			try {
//				destObject = destClass.newInstance();
//			} catch (InstantiationException e) {
//				e.printStackTrace();
//			}
//			copyProperties(destObject, origObject, excludeTargetField);
//			return destObject;
//		}
        
        return destObject;
	}
    
    
    /**
     * 根据字段名以及前置获取方法
     * @Title: getMethod 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param methods
     * @param fieldName
     * @param methodPrix
     * @return
     * @return Method    返回类型 
     * @throws
     */
	private static Method getMethod(Method[] methods, String fieldName, String methodPrix) {
        Method m = null;
        StringBuilder methodName = new StringBuilder();
        methodName.append(methodPrix);
        methodName.append(toUpperCaseFirstOne(fieldName));
        for (int i = 0; i < methods.length; i++) {
        	Method method = methods[i];
            if(method.getName().equals(methodName.toString())){
                m = method;
//                methods = (Method[])ArrayUtils.remove(methods, i); // 得到method之后移除数组中对应的method
                break;
            }
        }
        return m;
    }

	/**
	 * 获取一个类中所有属性的名字
	 * @Title: getFieldNames 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clazz
	 * @return
	 * @return String[]    返回类型 
	 * @throws
	 */
	public static String[] getFieldNames(Class<?> clazz) {
		if (null == clazz)
			return null;
		Field[] fields = clazz.getDeclaredFields();
		if (fields.length <= 0)
			return null;
		String[] names = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			names[i] = fields[i].getName();
		}
		return names;
	}
	
	public static Field getField(Field[] fields,String fieldName){
		if (fields == null)
			return null;
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}
	
	/**
	 * 获取泛型参数类型
	 * @Title: getActualTypeArguments 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param targetType
	 * @return
	 * @return Class[]    返回类型 
	 * @throws
	 */
	private static Type[] getActualTypeArguments(Type targetType){
		ParameterizedType parameterizedType = (ParameterizedType) targetType;  
		return  (Type[])parameterizedType.getActualTypeArguments();
	}
    
    /**
     * 求两个数组的交集
     * @Title: intersect 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param arr1
     * @param arr2
     * @return
     * @return String[]    返回类型 
     * @throws
     */
	public static String[] intersect(String[] arr1, String[] arr2) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		List<String> list = new ArrayList<String>();
		for (String str : arr1) {
			if (!map.containsKey(str)) {
				map.put(str, Boolean.FALSE);
			}
		}
		for (String str : arr2) {
			if (map.containsKey(str)) {
				map.put(str, Boolean.TRUE);
			}
		}

		for (Iterator<Entry<String, Boolean>> it = map.entrySet().iterator(); it.hasNext();) {
			Entry<String, Boolean> e = (Entry<String, Boolean>) it.next();
			if (e.getValue().equals(Boolean.TRUE)) {
				list.add(e.getKey());
			}
		}
		return list.toArray(new String[] {});
	}
	
	/**
	 * 获取属性名字相关的方法
	 * @Title: intersect 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param methods
	 * @param intersectFieldNames
	 * @return
	 * @return Method[]    返回类型 
	 * @throws
	 */
	public static Method[] intersect(Method[] methods, String[] intersectFieldNames) {
		List<Method> ms = new ArrayList<Method>();
		for (int i = 0; i < intersectFieldNames.length; i++) {
			for (int j = 0; j < methods.length; j++) {
				if (methods[j].getName().matches(".*(?i)" + intersectFieldNames[i] + ".*")) {
					ms.add(methods[j]);
				}
			}
		}
		return ms.toArray(new Method[0]);
	}
    
	/**
	 * 首字母小写
	 * @Title: toLowerCaseFirstOne 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param name
	 * @return
	 * @return String    返回类型 
	 * @throws
	 */
    public static String toLowerCaseFirstOne(String name) {
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		return  name;
	}
    
    /**
     * 首字母大写
     * @Title: toUpperCaseFirstOne 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param name
     * @return
     * @return String    返回类型 
     * @throws
     */
	public static String toUpperCaseFirstOne(String name) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return  name;
//         char[] cs=name.toCharArray();
//         cs[0]-=32;
//         return String.valueOf(cs);
		
	}
	
	/**
	 * 字符串是否存在于数组中
	 * @Title: isExists 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param arr
	 * @param str
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean isExists(String [] arr,String str){
		boolean result = false;
		if(null == arr || null == str)
			return result;
		for (String s : arr) {
			if(s.equals(str)) {
				result = true;
				break;
			}
		}
		return result;
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
	* 获取类实例的属性值
	* @param clazz
	*            类名
	* @param includeParentClass
	*            是否包括父类的属性值
	* @return 类名.属性名=属性类型
	*/
	public static List<Field> getClassFields(Class<?> clazz, boolean includeParentClass) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			list.add(field);
		}
		if (includeParentClass)
			getParentClassFields(list, clazz.getSuperclass());
		return list;
	}


	/**
	* 获取类实例的父类的属性值
	* @param list
	*            类实例的属性值Map
	* @param clazz
	*            类名
	* @return 类名.属性名=属性类型
	*/
	private static List<Field> getParentClassFields(List<Field> list, Class<?> clazz) {
		if (clazz == Object.class) {
			return list;
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			list.add(field);
		}
//		if (clazz.getSuperclass() == null) {
//			return list;
//		}
		getParentClassFields(list, clazz.getSuperclass());
		return list;
	}
	
	/**
	* 获取类实例的方法
	* @param clazz
	*            类名
	* @param includeParentClass
	*            是否包括父类的方法
	* @return List
	*/
	public static List<Method> getClassMothds(Class<?> clazz, boolean includeParentClass) {
		List<Method> list = new ArrayList<Method>();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			list.add(method);
		}
		if (includeParentClass) {
			getParentClassMothds(list, clazz.getSuperclass());
		}
		return list;
	}

	/**
	* 获取类实例的父类的方法
	* @param list
	*            类实例的方法List
	* @param clazz
	*            类名
	* @return List
	*/
	private static List<Method> getParentClassMothds(List<Method> list, Class<?> clazz) {
		if (clazz == Object.class) {
			return list;
		}
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			list.add(method);
		}
		getParentClassMothds(list, clazz.getSuperclass());
		return list;
	}

	
	/**
	 * 设置属性
	 * @Title: setValue 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param destObject
	 * @param setMethod
	 * @param origFieldType
	 * @param destFieldType
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return void    返回类型 
	 * @throws
	 */
	private static void setValue(Object destObject, Method setMethod, Class<?> origFieldType, Class<?> destFieldType, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (!origFieldType.isAssignableFrom(destFieldType)) { // 如果不是同一类型
			if (CharSequence.class.isAssignableFrom(destFieldType)) {
				value = (CharSequence) String.valueOf(value);
			} else if (CharSequence.class.isAssignableFrom(origFieldType)) {
				if (null == value || ((String) value).trim().equals(""))return;
				if (Integer.class.isAssignableFrom(destFieldType) || int.class.isAssignableFrom(destFieldType)) {
					value = Integer.parseInt((String) value);
				} else if (Long.class.isAssignableFrom(destFieldType) || long.class.isAssignableFrom(destFieldType)) {
					value = Long.parseLong((String) value);
				} else if (Double.class.isAssignableFrom(destFieldType) || double.class.isAssignableFrom(destFieldType)) {
					value = Double.parseDouble((String) value);
				} else if (boolean.class.isAssignableFrom(destFieldType) || Boolean.class.isAssignableFrom(destFieldType)) {
					value = Boolean.parseBoolean((String) value);
				}
			}
		}
		setMethod.invoke(destObject, value);
	}
}
