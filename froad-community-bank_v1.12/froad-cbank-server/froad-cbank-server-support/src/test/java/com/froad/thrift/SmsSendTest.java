package com.froad.thrift;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.froad.util.HttpClientUtil;
/**
 * 测试短信发送
 * @author h
 *
 */
public class SmsSendTest {
		// TODO Auto-generated method stub
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
			String url="http://10.43.2.1:8085/message-service/sms";
			 JSONObject jsonObject = new JSONObject(); 
			  JSONArray jsonArray = new JSONArray();   
			  jsonArray.add(0, "13676086313"); 
			  jsonObject.put("mobiles", jsonArray); //手机号码
			  jsonObject.put("type", "SMS");
			  jsonObject.put("smsType", "VERIFICATION");
			  jsonObject.put("templateId", 0);//
			  jsonObject.put("bizCode", "1301");//短信业务编码
			  jsonObject.put("bizId",  "1222222211");
			  jsonObject.put("merchantCode", "802");
			Map<String, String> map = new HashMap<String, String>();
				map.put("value", "欢迎您的注册，您的注册短信验证码为:2623，请尽快使用");
			jsonObject.put("data", map);
			//-------------------------调用 第三方接口发送短信------------------------------
			JSONObject 	resp= HttpClientUtil.post(url, jsonObject);
			
			System.out.println("==========="+resp.toJSONString());
			System.out.println("=========="+resp.get("returnCode").toString());
		
	}

}
