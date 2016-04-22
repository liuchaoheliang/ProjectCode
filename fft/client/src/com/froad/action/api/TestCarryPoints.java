package com.froad.action.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jettison.json.JSONObject;

import com.froad.util.MD5;
 

public class TestCarryPoints {
	protected String mac;//对bodyMac校验
	protected String no;//接口序列号
	protected String body;//主体
	protected String count;//请求标识号
	public static final String KEY = "00CBA177EA725E7C";
	//团购+推荐商品+提现

	static String server ="http://localhost:8080/communityBusiness_client/client_api.action";

	//static String server ="http://116.228.153.46:8686/communityBusiness_client/client_api.action";
//	static String server ="http://127.0.0.1:8080/communityBusiness_client/client_api.action"; 

	public static void main(String[] args) throws Exception {
		TestCarryPoints test = new TestCarryPoints(); 
//  	 		test.test_querycarryPoints();
 //		    test.test_carryPoints();
//  	 		test.test_queryTypeRecommend(); 
//  	 		test.test_queryClientGoodsGroup();
	  	    test.test_queryClientGoodsGroupDetail();
//	  	    test.test_addGoodsGroupTran();  
//		String cashPrice ="100",goodsNum="3";
//		String res =new BigDecimal(cashPrice).divide(new BigDecimal(goodsNum)).setScale(0, BigDecimal.ROUND_UP).toString();
//		System.out.println(res);
  	 		test.test_getGoodsCarry();
	}
	public String userId ="1001001";  //b55a8a71-4130-4435-bcd7-037d9c3c5686
	
	public void test_addGoodsGroupTran() throws Exception{
		JSONObject json = new JSONObject();
	 	json.put("userId",  userId );
		json.put("clientType", "200");
		json.put("goodsId","1000000015" );
		json.put("goodsNum","2.0" );
		json.put("cashPrice","50.0" );
		json.put("fftPointPrice","0.00" );
		json.put("bankPointPrice","50.00" );
		JSONObject goodsVal = new JSONObject();
		goodsVal.put("tel", "18621818056" );
		json.put("goodsVal", goodsVal);
		body = json.toString();
		no="0903";
		 sendRequest();
	}
	
	public void test_queryClientGoodsGroup() throws Exception{
		JSONObject json = new JSONObject();
		 
		json.put("userId","b743ea60-d767-4ef2-bcab-369d7607ea97" );
		body = json.toString();
		no="0901";
		 sendRequest();
	}
	public void test_queryClientGoodsGroupDetail() throws Exception{
		JSONObject json = new JSONObject();		 
		json.put("goodsId","100001020" );
		body = json.toString();
		no="0902";
		 sendRequest();
	}
	
	public void test_queryTypeRecommend() throws Exception{
		JSONObject json = new JSONObject();
		 
		json.put("userId","62320bfa-ffe2-45ee-92a4-b3f86de91209" );
		body = json.toString();
		no="0801";
		 sendRequest();
	}
	
	public void test_querycarryPoints() throws Exception{
		JSONObject json = new JSONObject();
		 
		json.put("userId","1001001" );
		body = json.toString();
		no="0701";
		 sendRequest();
	}
	public void test_carryPoints() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","1001001" );
		json.put("carryPoint","0" );
		json.put("clientType", "200");
		body = json.toString();
		no="0702";
	
		 sendRequest();
	}
	public void test_getGoodsCarry() throws Exception{
		JSONObject json = new JSONObject();
		json.put("beginTime", "111111");
		json.put("endTime", "111112");
		json.put("userId","50000033379" );
		 no="0503";
		body = json.toString();
	
		 sendRequest();
	}
	
	public  void sendRequest() throws Exception{
		MD5 md5 = new MD5();
		System.out.println("body:"+body +  KEY);
		mac = md5.getMD5ofStr(body +  KEY);
	    JSONObject login_json = new JSONObject();
		login_json.put("no", no);
		login_json.put("body",body);
		login_json.put("count", "2212");
		login_json.put("mac", mac);
		String req=login_json.toString();
		System.out.println("req:"+req);
		String back = sendPost(req.getBytes(),server);
		 System.out.println("调用返回："+back);
		 
	} 
	 
	public static String sendPost(byte[] data, String serverURL)
    throws Exception
   {
		URL url = new URL(serverURL);

		   HttpURLConnection conn = (HttpURLConnection) url.openConnection();


		   conn.setRequestMethod("POST");
		   conn.setRequestProperty("Connection", "Keep-Alive");
		   conn.setRequestProperty("content-type", "text/html");

		   conn.setDoOutput(true);
		   OutputStream out = conn.getOutputStream();
		   if (data != null) {
		     out.write(data);
		   }

		   out.flush();
		   out.close();
		  
		   String encoding = "UTF-8";
		   String content_type = conn.getContentType();
		   if(content_type!=null){
		     content_type = content_type.trim().toUpperCase();
		     if(content_type.indexOf("UTF-8")>0){
		       encoding = "UTF-8";
		     }
		   }
		   BufferedReader in = new BufferedReader(new InputStreamReader(conn.
			       getInputStream(), encoding));

			   StringBuffer sb = new StringBuffer();
			   String line;
			   while ( (line = in.readLine()) != null) {
			      sb.append(line + "\n\r");
			   } //end while
			   in.close();
			   System.out.println("调用完毕!");
    return sb.toString();
  }
	
	/**
	   * 发送一个GET方式的Http请求。 使用HttpURLConnection的方式
	   * @param urlString   目标URL
	   * @param data    要发送的数据(作为消息体)
	   * @return    服务器端返回的内容
	   * @throws java.lang.Exception
	   */
	  public    String sendGet(String serverURL) throws Exception {
	    URL url = new URL(serverURL);

	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	    conn.setRequestProperty("Connection", "Keep-Alive");
	    String encoding =   "utf-8";
	    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));

	    StringBuffer sb = new StringBuffer();
	    String line;
	    while ( (line = in.readLine()) != null) {
	      sb.append(line );   //+ "\n");
	    }
	    in.close();

	    return sb.toString();
	  }
	
	
	  public String getMac() {
			return mac;
		}

		public void setMac(String mac) {
			this.mac = mac;
		}

		public String getNo() {
			return no;
		}

		public void setNo(String no) {
			this.no = no;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}
}
