package com.froad.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import com.froad.logback.LogCvt;

public class HttpClientManager {
  
	private static PoolingClientConnectionManager cm;
    
	static {
		cm = new PoolingClientConnectionManager();
		// 增加最大连接到200
		cm.setMaxTotal(200);
//		// 增加每个路由的默认最大连接到20
		cm.setDefaultMaxPerRoute(20);
		
	}
	
	
	public static HttpClient getHttpClient(){
		HttpClient httpClient = new DefaultHttpClient(cm);
		return httpClient;
	}
	
	public static void close(HttpClient httpClient){
		httpClient.getConnectionManager().shutdown();
		
		cm.closeExpiredConnections();
	}
	
	
	/** 
     * 获得HttpGet对象 
     *  
     * @param url 
     *            请求地址 
     * @param params 
     *            请求参数 
     * @param encode 
     *            编码方式 
     * @return HttpGet对象 
     */
	public static HttpGet getHttpGet(String url, Map<String, String> params,  
            String encode) {  
        StringBuffer buf = new StringBuffer(url);  
        if (params != null) {  
            // 地址增加?或者&  
            String flag = (url.indexOf('?') == -1) ? "?" : "&";  
            // 添加参数  
            for (String name : params.keySet()) {  
                buf.append(flag);  
                buf.append(name);  
                buf.append("=");  
                try {  
                    String param = params.get(name);  
                    if (param == null) {  
                        param = "";  
                    }  
                    buf.append(URLEncoder.encode(param, encode));  
                } catch (UnsupportedEncodingException e) {  
                    LogCvt.error("URLEncoder Error,encode=" + encode + ",param="  
                            + params.get(name), e);  
                }  
                flag = "&";  
            }  
        }  
        HttpGet httpGet = new HttpGet(buf.toString());  
        return httpGet;  
    }  
	
	
	/** 
     * 获得HttpPost对象 
     *  
     * @param url 
     *            请求地址 
     * @param params 
     *            请求参数 
     * @param encode 
     *            编码方式 
     * @return HttpPost对象 
     */  
    public static HttpPost getHttpPost(String url, Map<String, String> params,  
            String encode) {  
        HttpPost httpPost = new HttpPost(url);  
        if (params != null) {  
            List<NameValuePair> form = new ArrayList<NameValuePair>();  
            for (String name : params.keySet()) {  
                form.add(new BasicNameValuePair(name, params.get(name)));  
            }  
            try {  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form,  
                        encode);  
                httpPost.setEntity(entity);  
            } catch (UnsupportedEncodingException e) {  
            	LogCvt.error("UrlEncodedFormEntity Error,encode=" + encode  
                        + ",form=" + form, e);  
            }  
        }  
        return httpPost;  
    } 
}
