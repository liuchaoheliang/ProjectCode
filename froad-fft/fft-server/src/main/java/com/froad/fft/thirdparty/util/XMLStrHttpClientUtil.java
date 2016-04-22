package com.froad.fft.thirdparty.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.froad.fft.bean.SystemConfig;
import com.froad.fft.common.AppException;
import com.froad.fft.util.NullValueCheckUtil;
import com.froad.fft.util.SystemConfigUtil;
import com.froad.fft.util.XmlFormatUtil;

@SuppressWarnings("deprecation")
public class XMLStrHttpClientUtil {

	private static Logger logger = Logger.getLogger(XMLStrHttpClientUtil.class);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>负责发送第三方系统XML和响应XML的工具处理</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:21:09 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String  httpPost(String url, String requestXmlStr) throws Exception {

		@SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		StringBuffer responseXmlStr = new StringBuffer();

		try {
			StringEntity entity = new StringEntity(requestXmlStr, "UTF-8");
			post.setEntity(entity);

			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);

			logger.info("URL:" + url + ",连接用时: "
					+ (System.currentTimeMillis() - startTime) + "ms,响应信息: "
					+ response.getStatusLine());

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					responseXmlStr.append(line);
				}
				reader.close();
			} else {
				logger.error("响应信息: " + response.getStatusLine());
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("请求连接发生异常 url :" + url, e);
			throw new AppException(e);
		} catch (IllegalStateException e) {
			logger.error("请求连接发生异常 url :" + url, e);
			throw new AppException(e);
		} catch (IOException e) {
			logger.error("请求连接发生异常 url :" + url, e);
			throw new AppException(e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		if (responseXmlStr == null || NullValueCheckUtil.isStrEmpty(responseXmlStr.toString().trim())) {
			logger.error("URL:" + url + "响应报文为空");
			throw new AppException("响应报文为空");
		}
		
		// 接收响应信息
		SystemConfig systemConfig=SystemConfigUtil.getSystemConfig();
		if(systemConfig.getIsSystemDebug()){
			logger.info("第三方系统响应 xml:\n " + XmlFormatUtil.format(responseXmlStr.toString()));
		}else{
			logger.info("第三方系统响应 xml：\n" + responseXmlStr.toString());
		}
		
		return responseXmlStr.toString();

	}
}
