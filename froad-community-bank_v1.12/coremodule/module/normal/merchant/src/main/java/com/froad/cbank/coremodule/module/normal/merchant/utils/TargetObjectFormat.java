package com.froad.cbank.coremodule.module.normal.merchant.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;

public class TargetObjectFormat {

	/**
	 * 将一个对象的属性值直接赋给另一个类似的对象
	 * 
	 * @param getInfo
	 * @param setVo
	 */
	public static void copyProperties(Object source, Object target) {
		try {
			Field[] source_fields = source.getClass().getDeclaredFields();
			Field[] target_fields = target.getClass().getDeclaredFields();
			for (java.lang.reflect.Field source_field : source_fields) {
				for (java.lang.reflect.Field target_field : target_fields) {
					if (source_field.getName().trim().equalsIgnoreCase(target_field.getName().trim())) {
						String sourceType=source_field.getType().toString();
						String targetType=target_field.getType().toString();
						Object ob=getFieldValueByName(sourceType,source_field.getName(),source);
						if(ob == null) continue;
						setFieldValueByName(target_field.getName(),target,sourceType,targetType,ob);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	
	/**
	 * 判断两个对象是否属性值一样
	 * 
	 * @param getInfo
	 * @param setVo
	 */
	public static boolean isEquals(Object source, Object target) {
		boolean isEqual=true;
		try {
			String type="String,boolean,long,Long,Boolean,short,Short,int,Integer,double,Double,float,Float,Date";
			Field[] source_fields = source.getClass().getDeclaredFields();
			Field[] target_fields = target.getClass().getDeclaredFields();
			for (java.lang.reflect.Field source_field : source_fields) {
				for (java.lang.reflect.Field target_field : target_fields) {
					if (source_field.getName().trim().equalsIgnoreCase(target_field.getName().trim())) {
						String sourceType=source_field.getType().toString();
						String targetType=target_field.getType().toString();
						String source_name=source_field.getName();
						String target_name=target_field.getName();
						if(sourceType.equals(targetType)&&source_name.equals(target_name)&&!(source_name.equals("id"))){
							Object source_ob=getFieldValueByName(sourceType,source_name,source);
							Object target_ob=getFieldValueByName(targetType,target_name,target);
							LogCvt.info("匹配是哦负一样-----》 11111111： "+source_ob+"-============>"+target_ob);
							//判断null
							
							if((source_ob==null&&target_ob!=null)||(source_ob!=null&&target_ob==null)){
								isEqual=false;
							    break;
							}
							//不为null判断（正常类型）
							if((type.indexOf(sourceType)!=-1&&type.indexOf(targetType)!=-1)){
								if((source_ob!=null&&target_ob!=null)&&!(source_ob.equals(target_ob))){
									isEqual=false;
									break;
								}
							}
						}
						
					}
					
				}
				if(!isEqual)break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isEqual;
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
//			e.printStackTrace();
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
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static Object setFieldValueByName(String fieldName, Object target,String sourceType,String targetType,Object value) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String setter = "set" + firstLetter + fieldName.substring(1);
			Class[] cazz = null;
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
			if(cazz==null) return null;
			Method method = target.getClass().getMethod(setter, cazz);
			return method.invoke(target, new Object[] {value});
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 删除图片
	 * 
	 * @param oldPicName
	 */
	public static void deleteFile(String oldPicName) {
		File fileDir = new File(Constants.get("merchant.outlet.photo.url")
				+ oldPicName);
		if (fileDir.exists()) {
			// 把修改之前的图片删除 以免太多没用的图片占据空间
			fileDir.delete();
		}

	}

	/**
	 * 图片保存
	 * 
	 * @param newFileName
	 * @param filedata
	 */
	public static void saveFile(String newFileName, MultipartFile filedata)
			throws MerchantException {
		try {
			String saveFilePath = Constants.getImgLocalUrl();
			/* 构建文件目录 */
			File fileDir = new File(saveFilePath);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			FileOutputStream out = new FileOutputStream(saveFilePath +"/"+ newFileName);
			out.write(filedata.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new MerchantException("99991", "图片保存失败");
		}

	}

	/**
	 * 获取客户端ip
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    if(ip == null){
	    	ip = "";
	    }
	    return ip;  
	} 

}
