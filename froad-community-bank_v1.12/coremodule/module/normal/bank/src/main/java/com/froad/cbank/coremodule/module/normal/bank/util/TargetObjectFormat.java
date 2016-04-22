package com.froad.cbank.coremodule.module.normal.bank.util;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.multipart.MultipartFile;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;


public class TargetObjectFormat {

	/**
	 * 将一个对象的属性值直接赋给另一个类似的对象
	 * 
	 * @param getInfo
	 * @param setVo
	 */
	public static void getOutletVo(Object source, Object target) {
		try {
			Field[] source_fields = source.getClass().getDeclaredFields();
			Field[] target_fields = target.getClass().getDeclaredFields();
			for (java.lang.reflect.Field source_field : source_fields) {
				for (java.lang.reflect.Field target_field : target_fields) {
					if (source_field.getName().trim().equalsIgnoreCase(target_field.getName().trim())) {
						Object ob=getFieldValueByName(source_field.getName(),source);
						String sourceType=source_field.getType().toString();
						String targetType=target_field.getType().toString();
						setFieldValueByName(target_field.getName(),target,sourceType,targetType,ob);
					}
				}
			}
		} catch (Exception e) {
			LogCvt.info("将一个对象的属性值直接赋给另一个类似的对象"+e.getMessage(), e);
		} 
	}

	/**
	 * 获取对象属性的值
	 * 
	 * @param fieldName
	 * @param source
	 * @return
	 */
	public static Object getFieldValueByName(String fieldName, Object source) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = source.getClass().getMethod(getter, new Class[] {});
			return method.invoke(source, new Object[] {});
		} catch (Exception e) {
			LogCvt.info("获取对象属性的值"+e.getMessage(), e);
		}
		return null;
	}

    /**
     * 给目标对象属性赋值
	 * 
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
			Class[] cazz=new Class[] {String.class};
			targetType=targetType.substring(targetType.lastIndexOf(".")+1).toLowerCase();
		     String rvalue=value.toString();
			if(targetType.equals("int")||targetType.equals("integer")){
				cazz=new Class[] {int.class};
				value=Integer.valueOf(rvalue);
			}
			if(targetType.equals("long")){
				cazz=new Class[] {long.class};
				value=Long.valueOf(rvalue);
			}
			if(targetType.equals("double")){
				cazz=new Class[] {double.class};
				value=Double.valueOf(rvalue);
			}
			if(targetType.equals("date")){
				cazz=new Class[] {Date.class};
				value=Date.parse(rvalue);
			}
			if(targetType.equals("short")){
				cazz=new Class[] {short.class};
				value=(Short)value;
			}
			if(targetType.equals("float")){
				cazz=new Class[] {float.class};
				value=Float.valueOf(rvalue);
			}
			if(targetType.equals("boolean")){
				cazz=new Class[] {boolean.class};
				value=Boolean.valueOf(rvalue);
			}
			if(targetType.equals("string")){
				cazz=new Class[] {String.class};
				value=String.valueOf(rvalue);
			}
			if(targetType.equals("object")){
				cazz=new Class[] {Object.class};
			}
			Method method = target.getClass().getMethod(setter, cazz);
			return method.invoke(target, new Object[] {value});
		} catch (Exception e) {
			LogCvt.info("给目标对象属性赋值"+e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * 删除图片
	 * 
	 * @param oldPicName
	 */
	public static void deleteFile(String oldPicName) {
		File fileDir = new File(Constants.get("bank.product.photo.url")
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
			throws BankException {
		try {
			String saveFilePath = Constants.getImgLocalUrl();
			/* 构建文件目录 */
			File fileDir = new File(saveFilePath);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			FileOutputStream out = new FileOutputStream(saveFilePath+"/"+ newFileName);
			out.write(filedata.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new BankException("99991", "图片保存失败");
		}
	}

	/**
	 * 获取当前登录IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {      
			String ip = request.getHeader("X-Forwarded-For");
			LogCvt.info("获取当前登录IP-------------X-Forwarded-For:"+ip);
		   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		       ip = request.getHeader("Proxy-Client-IP");
		       LogCvt.info("获取当前登录IP-------------Proxy-Client-IP:"+ip);
		   }
		   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		       ip = request.getHeader("WL-Proxy-Client-IP");
		       LogCvt.info("获取当前登录IP-------------WL-Proxy-Client-IP:"+ip);
		   }
		   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		       ip = request.getHeader("HTTP_CLIENT_IP");
		       LogCvt.info("获取当前登录IP-------------HTTP_CLIENT_IP:"+ip);
		   }
		   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		       ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		       LogCvt.info("获取当前登录IP-------------HTTP_X_FORWARDED_FOR:"+ip);
		   }
		   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		      ip = request.getRemoteAddr();
		      LogCvt.info("获取当前登录IP-------------RemoteAddrIP:"+ip);
		   }
		   return ip;     
    }
	
	/**
     * double转整
	 * 
     * @param d
     * @return int
     */
    public static String getDoubleDecimalNum(double d){
    	DecimalFormat df = new DecimalFormat("0.###");
        String str = df.format(d);
        if(str != null){
        	if(str.indexOf(".") > 0){
        		String s = str.substring(str.indexOf(".")+1);
         		Integer it = Integer.valueOf(s);
         		if(it > 0){
         			str = String.format("%."+s.length()+"f", d);	
         		}else{
         			str = str.substring(0,str.indexOf("."));
         		}
        	}
        }
        return str;
    }
    
    /**
     * double 保留两位小数
	 * 
     * @param d
     * @return int
     */
    public static String getDoubleDecimalString(double d){
    	DecimalFormat df = new DecimalFormat("#0.00");
        String str = df.format(d).toString();
        return str;
    }
    
    /**
     * 密码加密(安徽加密方式)
     */
    public static String getPassword(String userPassword){
    	String password = "";
		try {
			password = Base64.encodeBase64String(Hex.decodeHex(userPassword.toCharArray()));
		} catch (DecoderException e) {
			LogCvt.info("密码加密"+e.getMessage(), e);
		}
		return password;
    }
    
}
