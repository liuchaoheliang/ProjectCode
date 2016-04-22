package com.froad.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapStringUtil {

	private static final String containCodeBegin="{";
	private static final String containCodeEnd="}";
	private static final String separator=", ";
	private static final String joiner="=";

	/**
	  * 方法描述：把Map转换成String
	  * @param: Map<String,String> dataMap
	  * @return: String
	  * @version: 1.0
	  * @time: 2011-12-14 上午9:59:50
	  */
	public static String mapToString(Map<String,String> dataMap){
		StringBuffer buffer = new StringBuffer();
		buffer.append(containCodeBegin);
		Iterator<?> iter = dataMap.entrySet().iterator(); 
		while (iter.hasNext()) {
			 Map.Entry<String,Object> entry =(Map.Entry<String,Object>)iter.next(); 
			 Object key = entry.getKey();
			 Object val = entry.getValue();
			 buffer.append(key);
			 buffer.append(joiner);
			 buffer.append(val==null?"":val);
			 buffer.append(separator);
		}
		buffer.delete(buffer.length()-separator.length(), buffer.length());
		buffer.append(containCodeEnd);
		return buffer.toString();
	}
	
	
	/**
	  * 方法描述：把String转换成Map
	  * @param: String dataStr
	  * @return: Map<String,String>
	  * @version: 1.0
	  * @time: 2011-12-14 上午10:00:46
	  */
	public static Map<String,String> stringToMap(String dataStr){
		Map<String,String> dataMap = new HashMap<String, String>();
		StringBuffer buffer = new StringBuffer(dataStr);
		buffer.delete(0, buffer.indexOf(containCodeBegin)+1);
		buffer.delete(buffer.lastIndexOf(containCodeEnd), buffer.length());
		
		dataStr = buffer.toString();
		
		String[] dataStrs=dataStr.split(separator);
		for(String str:dataStrs){
			String[] kv = str.split(joiner,2);
			dataMap.put(kv[0], kv[1].equals("null")?"":kv[1]);
		}
		
		return dataMap;
	}
	
	public static void main(String args[]){
		Map<String,String> dataMap = new HashMap<String, String>();
		String str = "   {signType=RSA, currencyType=163, type=Pay, transAmt=123.0, signValue=nsBDJsxDLreGqGdk63DVzOq+6ThTLLIpWnatMrGLg9Hu7ZxOsIIHo09j5iRAHmWwXMpKEygq+aZ7F8xnG2+wn/xL7wyt+/KxPQ4rESN5C33DpXaAha/dH2xRp7u/s2I+jjm6DD/yBVIX1+HNwVvTmqx9kZagGzNsHM/06Sef+h0=, serviceName=Pay, merchantName=openApi, transMerDateTime=20111214105433, merchantId=000010, view=210/MobilePayRedirect.jsp, payUrl=https://222.178.68.122:8885/cqsd/3G/loginpay.jsp, bankGroup=210, transRemark2=, backurl=http://localhost, orderId=1101323831219350, billSeqNo=1101323831219350, transRemark1=null}   ";
		dataMap = stringToMap(str);
		System.out.println(dataMap);
		for(String s:dataMap.keySet()){
			System.out.println(s+"    ---    "+dataMap.get(s));
		}
	}
}
