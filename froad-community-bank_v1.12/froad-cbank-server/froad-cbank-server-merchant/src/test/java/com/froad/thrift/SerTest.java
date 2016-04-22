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
 * @Title: SerTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年5月8日
 */

package com.froad.thrift;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.OutletDetailSimpleInfo;
import com.froad.util.JSonUtil;
import com.froad.util.ObjectSerializeUtil;


/**    
 * <p>Title: SerTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年5月8日 下午2:02:01   
 */

public class SerTest {

	

	/**
	 * 方法说明
	 * @author lmq0382 
	 * @date 2012-6-11
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
//		serTest();
		
		OutletDetailSimpleInfo info = new OutletDetailSimpleInfo();
		info.setId("10000002");
		info.setDefaultImage("www.baidu.com/aaa/bbb");
		info.setAddress("上海,浦东");
		List<CategoryInfo> categoryInfo = new ArrayList<CategoryInfo>();
		CategoryInfo ci = new CategoryInfo();
		ci.setCategoryId(100000000l);
		ci.setName("大保健");
		categoryInfo.add(ci);
		info.setCategoryInfo(categoryInfo);
		info.setDis(512.3666);
		info.setOutletName("帆哥大保健责任有限公司");
		
		
		String serStr = ObjectSerializeUtil.serializeObject(info);
		System.out.println("java ser ===>" + serStr);
		System.out.println("java ser length===>" + serStr.length());
		
		System.out.println("-----------------------------");
		String jsonStr = JSonUtil.toJSonString(info);
		System.out.println("json ser ===>" + jsonStr);
		System.out.println("json ser length===>" + jsonStr.length());
	}

	
	/** 
	 * @Title: serTest 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws ClassNotFoundException
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public static void serTest() throws IOException, UnsupportedEncodingException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("number", "123");
		map.put("name", "test");
		list.add(map);
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("number", "1232");
		map2.put("name", "test2");
		list.add(map2);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(list);  
		String serStr = byteArrayOutputStream.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		
		objectOutputStream.close();
		byteArrayOutputStream.close();
		
		System.out.println(serStr);
				
		String redStr = java.net.URLDecoder.decode(serStr, "UTF-8");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream); 
		List<HashMap<String, String>> newList = (List<HashMap<String, String>>)objectInputStream.readObject(); 
		objectInputStream.close();
		byteArrayInputStream.close();
		

		for(Map m : newList) {
			System.out.println(m.get("number") + " " + m.get("name"));
		}
	}


}
