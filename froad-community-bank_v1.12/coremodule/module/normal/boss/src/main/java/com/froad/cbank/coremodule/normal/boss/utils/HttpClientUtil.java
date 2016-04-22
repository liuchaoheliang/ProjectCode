package com.froad.cbank.coremodule.normal.boss.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;

/**
 * HTTP请求
 * @author yfy
 */
public class HttpClientUtil {

	private HttpClientUtil(){
	}
	
	private static final Integer DEFAULT_CONNECTION_TIMEOUT = 10000;
	private static final Integer DEFAULT_SOCKET_TIMEOUT = 30000;
	private static final String DEFAULT_CONTENT_CHARSET_UTF8 = "UTF-8";
	
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
			entity.setContentType("application/json");
			entity.setContentEncoding(charsetName);
			post.setEntity(entity);
			
			long startTime = System.currentTimeMillis();
			HttpResponse httpResp = httpClient.execute(post);
			LogCvt.info(String.format("url：%s, 连接用时：%s, http status：%s", url, (System.currentTimeMillis() - startTime) , httpResp.getStatusLine()));
			
			if(httpResp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity httpEntity = httpResp.getEntity();
				result = EntityUtils.toString(httpEntity,charsetName);
			}
			
		}catch(Exception e){
			LogCvt.error("http post failed：", e);
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		
		return result;
	}
	/**
	 * from表单提交
	 * HTTP POST with Json 默认编码UTF-8
	 * @param url
	 * @param content
	 * @return
	 */
	public static JSONObject doPostForm(String url, String content){
		String result = doPostForm1(url, content);
		JSONObject jsonObj = null;
		try{
			jsonObj = JSON.parseObject(result);
		}catch(JSONException e){
			LogCvt.error("parse JSONObject error：", e);
		}
		return jsonObj;
	}
	
	/**
	 * from表单提交
	 * HTTP POST 默认编码UTF-8
	 * @param url
	 * @param content
	 * @return
	 */
	public static String doPostForm1(String url, String content){
		return doPostForm(url,content,DEFAULT_CONTENT_CHARSET_UTF8);
	}
	/**
	 * from表单提交
	 * HTTP POST
	 * @param url
	 * @param content
	 * @param charsetName
	 * @return
	 */
	public static String doPostForm(String url, String content, String charsetName){
		LogCvt.info(String.format("url: %s, content:%s", url, content));
		
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
		
		String result = null;
		try{
			HttpPost post = new HttpPost(url);
			StringEntity entity = new StringEntity(content, charsetName);
			entity.setContentType("application/x-www-form-urlencoded");
			entity.setContentEncoding(charsetName);
			post.setEntity(entity);
			
			long startTime = System.currentTimeMillis();
			HttpResponse httpResp = httpClient.execute(post);
			LogCvt.info(String.format("url：%s, 连接用时：%s, http status：%s", url, (System.currentTimeMillis() - startTime) , httpResp.getStatusLine()));
			
			if(httpResp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity httpEntity = httpResp.getEntity();
				result = EntityUtils.toString(httpEntity,charsetName);
			}
			
		}catch(Exception e){
			LogCvt.error("http post failed：", e);
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		
		return result;
	}
}
