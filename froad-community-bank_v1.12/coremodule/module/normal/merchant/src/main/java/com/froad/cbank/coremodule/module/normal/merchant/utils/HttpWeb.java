package com.froad.cbank.coremodule.module.normal.merchant.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.froad.pojo.ARountePojo;
import com.froad.udp.Client;

/**
 * @date 2013-5-4 下午10:18:56 yiping.wang
 * @description
 */
public class HttpWeb {

	/**
	 * 获取指定链接的响应信息，POST方式
	 * 
	 * @param pUrl
	 * @param pParams
	 *            参数列表
	 * @return
	 */
	public static String sendPostRequest(String url, String params) {
		HttpClient lvClient = new DefaultHttpClient();
		HttpPost lvHttpPost = new HttpPost(url);
		String lvResponseString = null;
		try {
			lvHttpPost.setEntity(new StringEntity(params));
			HttpResponse response = lvClient.execute(lvHttpPost);
			lvResponseString = EntityUtils.toString(response.getEntity(),
					"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lvResponseString;
	}

	/**
	 * 获取指定链接的响应信息，GET方式
	 * 
	 * @param pUrl
	 * @return
	 */
	public static String sendGetRequest(String url, String params) {
		HttpClient lvClient = new DefaultHttpClient();
		HttpGet lvRequest = new HttpGet(url + params);
		String lvResponseString = null;
		try {
			HttpResponse response = lvClient.execute(lvRequest);
			lvResponseString = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lvResponseString;
	}
	
	/**
	 * 调l5-api 通用接口
	 * @param channel
	 * @param module
	 * @param command
	 * @return
	 */
	public static ARountePojo geturl(String channel,String module,String command){
		Client client = new Client();	
		ARountePojo ss=client.apiGetARounte(channel,module,command);
		
		return ss;
	}
	
	/**
	 * 说明   l5接口调用返回结果
	 * 创建日期  2015年12月1日  上午11:34:20
	 * 作者  artPing
	 * 参数  @param routeId
	 * 参数  @param result
	 * 参数  @param consumeTime
	 */
	public static void  setResult(long routeId, int result, long consumeTime){
		Client client = new Client();	
		client.apiRouteResult(routeId, result, consumeTime);
	}

}
