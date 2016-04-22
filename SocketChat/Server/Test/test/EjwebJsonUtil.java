package test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.log4j.Logger;


public class EjwebJsonUtil {
	private static Logger log = Logger.getLogger(EjwebJsonUtil.class);
	public static String entityToStrJson(Object object,String[] rejectField){
		StringBuffer sb = new StringBuffer();		
		boolean isFilter = true;//是否需要过滤字段
		ArrayList<Integer> filterField = new ArrayList<Integer>();//实际能够过滤的字段下标		
		//获取实体类的Class<?>对象
		Class<?> entiryClass = object.getClass();		
		//获取实体类的字段
		Field[] entityField = entiryClass.getDeclaredFields();		
		//过滤数组为空，则不需要过滤字段
		if(rejectField == null){
			isFilter = false;
		}else if(rejectField.length == 0){//过滤数组长度为空
			isFilter = false;
		}else {
			//存在需要过滤的字段
			int entityFileldLength = entityField.length;
			for(int i = 0 ; i < entityFileldLength ; i ++){
				for(int j = 0 ; j < rejectField.length ; j ++){
					if(rejectField[j].equals(entityField[i].getName())){						
						filterField.add(i);
					}
				}
			}			
		}
		
		if(filterField.size() == 0){
			isFilter = false;//如果真实需要过滤的字段为空，则不需要过滤
		}else if(filterField.size() == entityField.length){			
			return null;
		}
		
		sb.append("{");
		int count = 0 ;
		if(isFilter){
			String index = "_";
			for(int i = 0 ;  i < entityField.length ; i ++){
				index += i+"_";
			}
			for(Integer filterIndex : filterField){
				index = index.replace("_"+filterIndex+"_", "_");
			}
			index = index.substring(1, index.length() - 1);
			String[] strIndex= index.split("_");
			for(int i = 0 ; i < strIndex.length ; i ++){				
				Field field = entityField[Integer.parseInt(strIndex[i])];
				field.setAccessible(true);
				try {
					sb.append("'"+field.getName()+"':'"+field.get(object)+"'");
				} catch (IllegalArgumentException e) {
					log.error("json error", e);
				} catch (IllegalAccessException e) {
					log.error("json error", e);
				}
				if(count != strIndex.length - 1){
					sb.append(",");
				}
				count ++ ;
			}
		}else{
			for(Field field : entityField){
				field.setAccessible(true);
				try {
					sb.append("'"+field.getName()+"':'"+field.get(object)+"'");
				} catch (IllegalArgumentException e) {
					log.error("json error", e);
				} catch (IllegalAccessException e) {
					log.error("json error", e);
				}
				if(count != entityField.length - 1){
					sb.append(",");
				}
				count ++ ;
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
	public static String entityToStrJson(Object object){
		ArrayList<Integer> filterField = new ArrayList<Integer>();//实际能够过滤的字段下标		
		//获取实体类的Class<?>对象
		Class<?> entiryClass = object.getClass();		
		//获取实体类的字段
		Field[] entityField = entiryClass.getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		int count = 0 ;
		String index = "_";
		for(int i = 0 ;  i < entityField.length ; i ++){
			index += i+"_";
		}
		for(Integer filterIndex : filterField){
			index = index.replace("_"+filterIndex+"_", "_");
		}
		index = index.substring(1, index.length() - 1);
		String[] strIndex= index.split("_");
		for(int i = 0 ; i < strIndex.length ; i ++){				
			Field field = entityField[Integer.parseInt(strIndex[i])];
			field.setAccessible(true);
			try {
				sb.append("'"+field.getName()+"':'"+field.get(object)+"'");
			} catch (IllegalArgumentException e) {
				log.error("json error", e);
			} catch (IllegalAccessException e) {
				log.error("json error", e);
			}
			if(count != strIndex.length - 1){
				sb.append(",");
			}
			count ++ ;
		}
		sb.append("}");
		return sb.toString();
	}
}
