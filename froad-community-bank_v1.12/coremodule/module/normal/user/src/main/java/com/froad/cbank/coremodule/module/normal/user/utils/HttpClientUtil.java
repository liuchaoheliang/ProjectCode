package com.froad.cbank.coremodule.module.normal.user.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;

/**
 * 
 */
public class HttpClientUtil {

	private HttpClientUtil(){
	}
	
	private static final Integer DEFAULT_CONNECTION_TIMEOUT = 10000;
	private static final Integer DEFAULT_SOCKET_TIMEOUT = 30000;
	private static final String DEFAULT_CONTENT_CHARSET_UTF8 = "UTF-8";
	
	
	
	/*
	*//**
	 * HTTP POST JSON
	 * @param url
	 * @param jsonObject
	 * @return jsonObject
	 *//*
	public static JSONObject doPost(String url, JSONObject json){
		LogCvt.info(String.format("url: %s, json:%s", url, json.toString()));
		
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
		
		JSONObject resJson = null;
		try{
			HttpPost post = new HttpPost(url);
			StringEntity entity = new StringEntity(json.toString(), CONTENT_CHARSET_UTF8);
			entity.setContentType("application/json");
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			
			HttpResponse httpResp = null;
			
			long startTime = System.currentTimeMillis();
			httpResp = httpClient.execute(post);
			LogCvt.info(String.format("url：%s, 连接用时：%s", url, (System.currentTimeMillis() - startTime)));
			
			//if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity httpEntity = httpResp.getEntity();
				String result = EntityUtils.toString(httpEntity);
				resJson = JSON.parseObject(result);
			//}
		}catch(Exception e){
			LogCvt.error("http post failed：", e);
			resJson = new JSONObject();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		
		return resJson;
	}*/
	
	/**
	 * HTTP POST with Json 默认编码UTF-8
	 * @param url
	 * @param content
	 * @return
	 */
	public static JSONObject doPost(String url, JSONObject json){
		String result = doPost(url, json.toString());
		JSONObject jsonObj = null;
		try{
			jsonObj = JSON.parseObject(result);
		}catch(JSONException e){
			LogCvt.error("parse JSONObject error：", e);
		}
		return jsonObj;
	}
	
	
	/**
	 * HTTP POST 默认编码UTF-8
	 * @param url
	 * @param content
	 * @return
	 */
	public static String doPost(String url, String content){
		return doPost(url,content,DEFAULT_CONTENT_CHARSET_UTF8);
	}
	
	
	
	/**
	 * HTTP POST
	 * @param url
	 * @param content
	 * @param charsetName
	 * @return
	 */
	public static String doPost(String url, String content, String charsetName){
		LogCvt.info(String.format("url: %s, content:%s", url, content));
		
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
		
		String result = null;
		try{
			HttpPost post = new HttpPost(url);
			StringEntity entity = new StringEntity(content, charsetName);
			//entity.setContentType("application/json");
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			
			HttpResponse httpResp = null;
			
			long startTime = System.currentTimeMillis();
			httpResp = httpClient.execute(post);
			LogCvt.info(String.format("url：%s, 连接用时：%s, http status：%s", url, (System.currentTimeMillis() - startTime) , httpResp.getStatusLine()));
			
			//if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity httpEntity = httpResp.getEntity();
				result = EntityUtils.toString(httpEntity);
			//}
		}catch(Exception e){
			LogCvt.error("HTTP POST failed：", e);
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @Description: HTTP GET
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月18日 下午2:16:10
	 * @param url
	 * @return
	 */
	public static String doGet(String url){
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
		
		HttpGet get = new HttpGet(url);
		String result="";
		try {
			HttpResponse response = client.execute(get);
			byte[] data = EntityUtils.toByteArray(response.getEntity());
			result = new String(data);
		} catch (IOException e) {
			LogCvt.error("HTTP GET failed：", e);
		}finally{
			client.getConnectionManager().shutdown();
		}
		return result;
	}
	
	
	
	
}
