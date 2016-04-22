package com.froad.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.enums.MonitorPointEnum;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;

/**
 * http
 * 
 * @author FQ
 * 
 */
public class HttpClientUtil {
	
	/**
	 * 默认编码为 UTF-8
	 */
	private static final String HTTP_CONTENT_CHARSET_UTF_8 = "UTF-8";

	/**
	 * 监控服务
	 * */
	private static MonitorService monitorService = new MonitorManager();


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
		LogCvt.info("url："+url+" parameterMap："+JSON.toJSONString(parameterMap));
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 90000);
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
			long startTime = System.currentTimeMillis();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			LogCvt.info("URL:" + url + ",连接用时: "
					+ (System.currentTimeMillis() - startTime) + "ms,响应信息: "
					+ httpResponse.getStatusLine());
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
		LogCvt.info("result："+result);
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
		LogCvt.info("url："+url+" parameterMap："+JSON.toJSONString(parameterMap));
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 90000);
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
	public static JSONObject post(String url, JSONObject json) {
		LogCvt.info("url："+url+" json："+json.toJSONString());
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 90000);
		HttpPost post = new HttpPost(url);
		JSONObject response = null;
		try {
			StringEntity s = new StringEntity(json.toString(),HTTP_CONTENT_CHARSET_UTF_8);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			
			long startTime = System.currentTimeMillis();
			HttpResponse res = httpClient.execute(post);
			LogCvt.info("URL:" + url + ",连接用时: "
					+ (System.currentTimeMillis() - startTime) + "ms,响应信息: "
					+ res.getStatusLine());
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(res.getEntity());
				response = JSON.parseObject(result);  
				// TODO 短信发送正常 - 发送监控
				monitorService.send(MonitorPointEnum.Support_Sms_Send_Success);
			}else{
				// TODO 短信发送失败 - 发送监控
				monitorService.send(MonitorPointEnum.Support_Sms_Send_Failure);
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			// TODO 短信发送异常 - 发送监控
			monitorService.send(MonitorPointEnum.Support_Sms_Send_Failure);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return response;
	}
	
}
