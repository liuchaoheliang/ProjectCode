package com.froad.CB.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import cn.com.froad.jms.notification.base.IRequestSender;
import cn.com.froad.jms.notification.base.request.BizEnum;
import cn.com.froad.jms.notification.base.request.SMSNotifyRequest;

import com.caucho.hessian.client.HessianProxyFactory;

/** 
 * @author FQ 
 * @date 2013-6-18 下午02:26:24
 * @version 1.0
 * 
 */
public class SmsTest {
	
	private static HessianProxyFactory factory = new HessianProxyFactory();

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
//		String smsUrl="http://192.168.2.177:9100/if-notification-war/notify/requestSender";
//		
//		IRequestSender sender=null;
//		try {
//			sender=(IRequestSender) factory.create(IRequestSender.class, smsUrl);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		
//		SMSNotifyRequest sms=new SMSNotifyRequest();
//		
//		sms.setBizId("1000");
//		sms.setBizCode(BizEnum.FFT.getCode());
//		sms.addMobile("15026409159");
//		
//		Map<String,String> map=new HashMap<String,String>();
//		map.put("value", "测试发送短信");
//		
//		sms.setData(map);
//		
//		System.out.println("结果："+sender.send(sms));
		
		String URL="http://localhost:8989/communityBusiness/LotteryWinServlet";
		String res="?tranID=201304200000002&awdAmount=1000&prizeGrade=3&prizeCount=1&status=0";
		String request=URL+res;
		System.out.println(request);
		
		DefaultHttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(request);
		post.setEntity(new StringEntity( request,"UTF-8"));
		
		if(client.execute(post) != null){
			System.out.println("ok");
		}else{
			System.out.println("no");
		}
	}
	
	

}
