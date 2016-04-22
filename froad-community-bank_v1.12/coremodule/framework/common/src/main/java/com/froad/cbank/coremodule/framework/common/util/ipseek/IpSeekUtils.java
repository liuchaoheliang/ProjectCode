package com.froad.cbank.coremodule.framework.common.util.ipseek;

import java.io.IOException;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.Constants;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;

/**
 * @author Tom
 * 提供淘宝api和百度api查询ip所在省份
 * 淘宝api 支持10qps，百度api 每个key支持100万次/天 
 * 使用百度api需要申请密钥并调整api.properties中的api.baidu.ak参数
 * 为使用方便首选淘宝api
 */
public class IpSeekUtils {
	/**
	 * 查询ip所在省份，返回空值ip在国外或者发生错误
	 * @param ip
	 * @return
	 */
	public static String getIpProvinceByTaobao(String ip){
		String result=getIpAddress(Constants.API.TAOBAO,ip,null);
		JSONObject json;
		String province="";
		if(StringUtil.isNotBlank(result)){
			try {
				json = JSONObject.fromObject(result);
				if((Integer)json.get("code")==1){
					return "";
				}
				JSONObject data= (JSONObject) json.get("data");
				province=(String) data.get("region");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return province;
	}
	
	public static String getIpProvinceByBaidu(String ip){
		String result=getIpAddress(Constants.API.BAIDU,ip,Constants.API.BAIDU_AK);
		JSONObject json;
		String province="";
		if(StringUtil.isNotBlank(result)){
			try {
				json = JSONObject.fromObject(result);
				if((Integer)json.get("status")!=0){
					return "";
				}
				JSONObject content= (JSONObject) json.get("content");
				JSONObject addressDetail= (JSONObject)content.get("address_detail");
				province=(String) addressDetail.get("province");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return province;
	}
	
	private  static String getIpAddress(String api,String ip,String ak){
		String apiAddress=api+"?ip="+ip;
		if(StringUtil.isNotBlank(ak)){
			apiAddress+="&ak="+ak;
		}
		HttpClient client =new DefaultHttpClient();
		HttpGet get=new HttpGet(apiAddress);
		String result="";
		try {
			HttpResponse response=client.execute(get);
			byte[]data=EntityUtils.toByteArray(response.getEntity());
			result=new String(data);
		} catch (IOException e) {
			//e.printStackTrace();
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
		String result = getIpAddress(Constants.API.BAIDU,ip,Constants.API.BAIDU_AK);
		JSONObject json = JSONObject.fromObject(result);
		System.out.println(json.toString());
		
		
		System.out.println("taobao-->");
		String tbresult = getIpAddress(Constants.API.TAOBAO,ip,null);
		JSONObject tbjson = JSONObject.fromObject(tbresult);
		System.out.println(tbjson.toString());
	}

}
