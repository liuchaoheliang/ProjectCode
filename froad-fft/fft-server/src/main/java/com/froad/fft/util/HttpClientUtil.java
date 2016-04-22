package com.froad.fft.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;

/**
 * http
 * 
 * @author FQ
 * 
 */
public class HttpClientUtil {
	
	final static Logger logger = Logger.getLogger(HttpClientUtil.class);

	/**
	 * 默认编码为 UTF-8
	 */
	private static final String HTTP_CONTENT_CHARSET_UTF_8 = "UTF-8";



	/**
	 * POST请求
	 * 
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            请求参数
	 * @return 返回结果
	 */
	public static String post(String url, Map<String, Object> parameterMap) {
		Assert.hasText(url);
		logger.info("url："+url+" parameterMap："+JSON.toJSONString(parameterMap));
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameterMap != null) {
				for (Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP_CONTENT_CHARSET_UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            请求参数
	 * @return 返回结果
	 */
	public static String get(String url, Map<String, Object> parameterMap) {
		Assert.hasText(url);
		logger.info("url："+url+" parameterMap："+JSON.toJSONString(parameterMap));
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameterMap != null) {
				for (Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, HTTP_CONTENT_CHARSET_UTF_8)));
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String url="http://172.18.3.12:8210/froad_fft_client_cq/buildStatic.api";
		Map<String, Object> paramVal = new HashMap<String, Object>();
		paramVal.put("buildType", "index");
		
		String result=post(url,paramVal);
		
		System.out.println(result);
	}
}
