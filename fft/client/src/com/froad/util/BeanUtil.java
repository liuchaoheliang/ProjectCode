package com.froad.util;

import java.lang.reflect.Field;

public class BeanUtil {
	public static boolean ISNOTNULL(String para) {
		if (para != null && !"".equalsIgnoreCase(para)) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean ISNOTNULL(String para1,String para2) {
		if (para1 != null && !"".equalsIgnoreCase(para1) && para2!=null && !"".equalsIgnoreCase(para2)) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean ISNOTNULL(String para1,String para2,String para3) {
		if (para1 != null && !"".equalsIgnoreCase(para1) && para2!=null && !"".equalsIgnoreCase(para2) && para3!=null && !"".equalsIgnoreCase(para3)) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean ISNOTNULL(String para1,String para2,String para3,String para4) {
		if (para1 != null && !"".equalsIgnoreCase(para1) && para2!=null && !"".equalsIgnoreCase(para2) && para3 !=null && !"".equalsIgnoreCase(para3) && para4!=null && !"".equalsIgnoreCase(para4)) {
			return true;
		} else {
			return false ;
		}
	}
	
	
	public static void copy(Object obj,Object object) throws Exception{
		         Field [] fieldobject = object.getClass().getDeclaredFields();
		        Field [] fieldob = obj.getClass().getDeclaredFields();
		         
		         for(int i=0; i<fieldobject.length;i++){
		             String objectname = fieldobject[i].getName();
	            String objDataType = (fieldobject[i].getGenericType()).toString();
	           for(int j=0; j<fieldob.length;j++){
		                 String objname = fieldob[j].getName();
		                String objectDataType = (fieldob[j].getGenericType()).toString();
	              if(objname.equals(objectname)&&objDataType.equals(objectDataType)){
		                    fieldobject[i].setAccessible(true);
	                    fieldob[j].setAccessible(true);
	                    Object objs = fieldobject[i].get(object);
		          fieldob[j].set(obj, objs);
	               }
		            }
		         }
		    }
}
