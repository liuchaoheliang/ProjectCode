package com.froad.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.froad.CB.AppException.AppException;



	/**
	 * 类描述：发送http请求的工具类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Feb 28, 2014 3:05:20 PM 
	 */
public class HttpClientUtils {

		private static Logger logger = Logger.getLogger(HttpClientUtils.class);
		
		private static final String charset="UTF-8";
		
		public static String  doPost(String url, String requestXML) throws Exception {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			StringBuffer responseXML = new StringBuffer();
			try {
				StringEntity entity = new StringEntity(requestXML, charset);
				post.setEntity(entity);
				long startTime = System.currentTimeMillis();
				HttpResponse response = httpClient.execute(post);
				logger.info("请求地址:" + url + ",连接用时: "
						+ (System.currentTimeMillis() - startTime) + "ms,响应信息: "
						+ response.getStatusLine());
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), charset));
					String line = null;
					while ((line = reader.readLine()) != null) {
						responseXML.append(line);
					}
					reader.close();
				} else {
					logger.error("http请求发送失败，请求地址："+url+"响应信息: " + response.getStatusLine());
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("http请求异常，请求地址：" + url, e);
				throw new AppException(e);
			} catch (IllegalStateException e) {
				logger.error("http请求异常，请求地址：" + url, e);
				throw new AppException(e);
			} catch (IOException e) {
				logger.error("http请求异常，请求地址：" + url, e);
				throw new AppException(e);
			} finally {
				httpClient.getConnectionManager().shutdown();
			}

			if (StringUtils.isEmpty(responseXML.toString())) {
				logger.error("URL:" + url + "响应报文为空");
				throw new AppException("响应报文为空");
			}
			
			return responseXML.toString();
		}
}
