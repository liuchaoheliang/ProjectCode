package com.froad.cbank.coremodule.module.normal.user.utils;

import static com.froad.cbank.coremodule.module.normal.user.utils.Constants.IP_LOCATION;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;

/**
 * 
 * @Description: IP查询定位功能
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年10月18日 下午1:43:20
 */
public class IpSeekUtils {
	
	/**
	 * @Description: 使用百度api定位ip所在市，获取city_code
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月18日 下午2:48:05
	 * @param ip
	 * @return
	 */
	public static String getIpCityCode(String ip){
		//根据IP查询百度定位city_code
		String result = getIpAddress(IP_LOCATION.API,ip,IP_LOCATION.AK);
		JSONObject json;
		String cityCode = "";
		if(StringUtil.isNotBlank(result)){
			try {
				json = JSON.parseObject(result);
				if((Integer)json.get("status") == 0){
					JSONObject content = (JSONObject) json.get("content");
					JSONObject addressDetail = (JSONObject)content.get("address_detail");
					cityCode = addressDetail.get("city_code").toString();
				}
			} catch (JSONException e) {
				LogCvt.error("IP定位结果解析异常, result:"+ result,e);
			}
		}
		return cityCode;
	}
	
	/**
	 * 
	 * @Description: 请求公共api，获取ip定位信息
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月18日 下午2:23:17
	 * @param api 百度/淘宝 ip定位api
	 * @param ip IP地址
	 * @param ak  请求key
	 * @note 淘宝api 支持10qps，百度api 每个key支持100万次/天 ;使用百度api需要申请密钥并调整api.properties中的api.baidu.ak参数
	 * @return
	 */
	private  static String getIpAddress(String api,String ip,String ak){
		String apiAddress=api+"?ip="+ip;
		if(StringUtil.isNotBlank(ak)){
			apiAddress+="&ak="+ak;
		}
		String result="";
		try {
			HttpClient client =new DefaultHttpClient();
			HttpGet get=new HttpGet(apiAddress);
			HttpResponse response=client.execute(get);
			byte[]data=EntityUtils.toByteArray(response.getEntity());
			result=new String(data);
		} catch (IOException e) {
			LogCvt.error("IP定位请求发送异常",e);
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//美国ip 需要获取country region获取为空
	//	String result=getIpAddressByTaobao("216.35.169.232");
		//北京ip
	//	String result=getIpAddress("218.30.64.194");
	//	String result=getIpAddress("59.53.63.18");
		//String ip="218.30.64.194";
		String ip = "60.167.225.106";
		System.out.println("baidu--->");
		String result = getIpAddress(IP_LOCATION.API,ip,IP_LOCATION.AK);
		JSONObject json = JSONObject.parseObject(result);
		System.out.println(json.toString());
		
		/*
		System.out.println("taobao-->");
		String tbresult = getIpAddress(API.TAOBAO,ip,null);
		JSONObject tbjson = JSONObject.parseObject(tbresult);
		System.out.println(tbjson.toString());*/
	}

}
