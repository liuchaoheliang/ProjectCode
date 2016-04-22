package com.froad.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class O2oAreaClient {

	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>传入服务地址（可以带键值对参数），已获取Json服务端字符串</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-20 上午11:17:14 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String getAreaJson(String serverUrl) {
		String path = serverUrl;
		URL url;
		try {
			url = new URL(path);			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(5 * 1000); // 5s超时
			InputStream in = conn.getInputStream();
			byte[] by = new byte[in.available()];
			in.read(by);
			return new String(by,"UTF-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
