package com.froad.thirdparty.util;

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
import org.apache.http.params.CoreConnectionPNames;

import com.froad.thirdparty.exception.AppException;
import com.froad.util.payment.EsyT;
import com.froad.enums.MonitorPointEnum;
import com.froad.logback.LogCvt;

public class XMLStrHttpClientUtil {

	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>负责发送第三方系统XML和响应XML的工具处理</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:21:09 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String  httpPost(String url, String requestXmlStr) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 130000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 160000);
		
		HttpPost post = new HttpPost(url);

		StringBuffer responseXmlStr = new StringBuffer();

		try {
			StringEntity entity = new StringEntity(requestXmlStr, "UTF-8");
			post.setEntity(entity);

			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);

			LogCvt.info("URL:" + url + ",连接用时: "
					+ (System.currentTimeMillis() - startTime) + "ms,响应信息: "
					+ response.getStatusLine());

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					responseXmlStr.append(line + "\n\t");
				}
				reader.close();
			} else {
				LogCvt.error("请求HTTP状态异常: " + response.getStatusLine() + " url: " + url);
				throw new AppException("HTTP响应状态异常");
			}
		} catch (UnsupportedEncodingException e) {
			LogCvt.error("请求连接发生异常 url 编码格式不支持:" + url, e);
			EsyT.sendPoint(MonitorPointEnum.order_pay_thridparty_mutual_failed);
			throw new AppException(e);
		} catch (IllegalStateException e) {
			LogCvt.error("请求连接发生异常 url 请求状态非法:" + url, e);
			EsyT.sendPoint(MonitorPointEnum.order_pay_thridparty_mutual_failed);
			throw new AppException(e);
		} catch (IOException e) {
			LogCvt.error("请求连接发生异常 url I/O异常:" + url, e);
			EsyT.sendPoint(MonitorPointEnum.order_pay_thridparty_mutual_failed);
			throw new AppException(e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		
		String xml = responseXmlStr.toString();
		
		if (responseXmlStr == null || StringUtils.isEmpty((xml.trim()))) {
			LogCvt.error("URL:" + url + "响应报文为空");
			throw new AppException("响应报文为空");
		}
		
		// 接收响应信息
		LogCvt.info("第三方系统响应 xml:\n " + xml);
		return xml;

	}
}
