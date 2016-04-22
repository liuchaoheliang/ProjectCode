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
 * @Title: ObjectSerializeUtil.java
 * @Package com.froad.util
 * @Description: TODO
 * @author vania
 * @date 2015年5月8日
 */

package com.froad.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;


/**    
 * <p>Title: ObjectSerializeUtil.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年5月8日 下午2:05:42   
 */

public class ObjectSerializeUtil {
	/**
	 * 序列化
	 * @Title: serializeObject 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param obj
	 * @return
	 * @return String    返回类型 
	 * @throws
	 */
	public static String serializeObject(Object obj) throws Exception{
		String serStr = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			serStr = byteArrayOutputStream.toString("ISO-8859-1");
			serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			if (null != objectOutputStream)
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (null != byteArrayOutputStream)
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return serStr;
	}

	/**
	 * 反序列化
	 * @Title: deserializeObject 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param serStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @return T    返回类型 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserializeObject(String serStr, Class<T> clazz) throws Exception {
		T readObject = null;
		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;
		String redStr;
		try {
			redStr = java.net.URLDecoder.decode(serStr, "UTF-8");
			byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			readObject = (T) objectInputStream.readObject();
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != objectInputStream)
				try {
					objectInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (null != byteArrayInputStream)
				try {
					byteArrayInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return readObject;
	}
}
