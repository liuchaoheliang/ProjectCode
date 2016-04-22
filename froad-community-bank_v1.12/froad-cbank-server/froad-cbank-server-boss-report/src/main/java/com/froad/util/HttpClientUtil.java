package com.froad.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.froad.logback.LogCvt;

public class HttpClientUtil {

	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>负责发送第三方系统XML和响应XML的工具处理</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:21:09 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String  httpPost(String url, String requestXmlStr) {

		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 90000);
		
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
			}
		} catch (UnsupportedEncodingException e) {
			LogCvt.error("请求连接发生异常 url 编码格式不支持:" + url, e);
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			LogCvt.error("请求连接发生异常 url 请求状态非法:" + url, e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LogCvt.error("请求连接发生异常 url I/O异常:" + url, e);
			throw new RuntimeException(e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		
		String xml = responseXmlStr.toString();
		
		if (responseXmlStr == null || StringUtils.isEmpty((xml.trim()))) {
			LogCvt.error("URL:" + url + "响应报文为空");
			throw new RuntimeException("响应报文为空");
		}
		
		// 接收响应信息
		LogCvt.info("第三方系统响应 xml:\n " + xml);
		return xml;

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
	private static HttpGet getHttpGet(String url, Map<String, String> params, String encode) {
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
					LogCvt.error("URLEncoder Error,encode=" + encode + ",param=" + params.get(name), e);
				}
				flag = "&";
			}
		}
		HttpGet httpGet = new HttpGet(buf.toString());
		return httpGet;
	}

	/**
	 * 下载文件保存到本地
	 * 
	 * @param path
	 *            文件保存位置
	 * @param url
	 *            文件地址
	 * @throws IOException
	 */
	public static void downloadFile(String path, String url) throws IOException {
		LogCvt.debug("path:" + path);
		LogCvt.debug("url:" + url);
		HttpClient client = null;
		try {
			// 创建HttpClient对象
			client = new DefaultHttpClient();
			// 获得HttpGet对象
			HttpGet httpGet = getHttpGet(url, null, null);
			// 发送请求获得返回结果
			HttpResponse response = client.execute(httpGet);
			// 如果成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				byte[] result = EntityUtils.toByteArray(response.getEntity());
				BufferedOutputStream bw = null;
				try {
					// 创建文件对象
					File f = new File(path);
					// 创建文件路径
					if (!f.getParentFile().exists())
						f.getParentFile().mkdirs();
					// 写入文件
					bw = new BufferedOutputStream(new FileOutputStream(path));
					bw.write(result);
				} catch (Exception e) {
					LogCvt.error("保存文件错误,path=" + path + ",url=" + url, e);
				} finally {
					try {
						if (bw != null)
							bw.close();
					} catch (Exception e) {
						LogCvt.error("finally BufferedOutputStream shutdown close", e);
					}
				}
			}
			// 如果失败
			else {
				StringBuffer errorMsg = new StringBuffer();
				errorMsg.append("httpStatus:");
				errorMsg.append(response.getStatusLine().getStatusCode());
				errorMsg.append(response.getStatusLine().getReasonPhrase());
				errorMsg.append(", Header: ");
				Header[] headers = response.getAllHeaders();
				for (Header header : headers) {
					errorMsg.append(header.getName());
					errorMsg.append(":");
					errorMsg.append(header.getValue());
				}
				LogCvt.error("HttpResonse Error:" + errorMsg);
			}
		} catch (ClientProtocolException e) {
			LogCvt.error("下载文件保存到本地,http连接异常,path=" + path + ",url=" + url, e);
			throw e;
		} catch (IOException e) {
			LogCvt.error("下载文件保存到本地,文件操作异常,path=" + path + ",url=" + url, e);
			throw e;
		} finally {
			try {
				client.getConnectionManager().shutdown();
			} catch (Exception e) {
				LogCvt.error("finally HttpClient shutdown error", e);
			}
		}
	}

}
