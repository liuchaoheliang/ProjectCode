package com.froad.action.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import com.froad.action.api.command.Encrypt;
import com.froad.util.MD5;

import net.sf.json.JSONObject;

public class TestMerhcant {
	protected String mac;//对bodyMac校验
	protected String no;//接口序列号
	protected String body;//主体
	protected String count;//请求标识号
	public static final String KEY = "00CBA177EA725E7C";
//	static String server ="http://192.168.2.172:8686/communityBusiness_client/client_api.action";
	static String server ="http://localhost:8080/communityBusiness_client/client_api.action";
//	static String server ="http://www.fenfentong.com.cn/client_api.action"; 
	public static void main(String[] args) throws Exception {
 		TestMerhcant test = new TestMerhcant();
// 		test.test_merchantlogin();
// 		test.test_queryRule();
// 	 	test.test_getGoodsExchange();
// 		test.test_sendTran();
   //	test.test_presentPoints();
//   	test.test_queryGroup();
//		test.test_inUseGroup();
// 		test.test_getStroesByMerchantId();
// 		////////tran//////////
    	test.test_queryGroupTran();
// 		test.test_queryReceiveTran();
// 		test.test_queryPointsTran();
//  	test.test_queryExchangeTran();
 		
	}
	
	public void test_getGoodsExchange() throws Exception{
		JSONObject json = new JSONObject();
		json.put("startdate", "2013-04-18 20:37:00");
		json.put("enddate", "2013-04-18 21:37:00");
		json.put("userId","991e4607-80da-4ae5-9048-942d7a6a78e1" );
		 no="0502";
		body = json.toString();
	
		 sendRequest();
	}
	
	
	public void test_queryRule() throws Exception{
		JSONObject json = new JSONObject();
		json.put("ruleNo", "01");
		json.put("userId","77676c8a-bebf-47b3-bce0-8d4af9989227" );
		 no="0002";
		body = json.toString();
		
		
		
		 sendRequest();
	}
	
	public void test_merchantlogin() throws Exception{
		JSONObject json = new JSONObject();
		json.put("beCode", "1017");
		json.put("beCodePWD", "111111");
		json.put("merchantName","user15992680397" );
		json.put("os","200");
		 no="10011";
		body = json.toString();
		body = new Encrypt().clientEncrypt(no,body );
		 sendRequest();
	}
	public void test_sendTran() throws Exception{
		JSONObject json = new JSONObject();
		json.put("tel","u1304111" );
		json.put("amount","10" );
		json.put("reason","111" );
		json.put("beCode","1000" );
		json.put("userId","" );
		json.put("os","200" );
		 no="10021";
		 body = json.toString();
		body = new Encrypt().clientEncrypt(no,body );
		 sendRequest();
	}
	public void test_confirmTran() throws Exception{
		JSONObject json = new JSONObject();
		 
		json.put("beCode","1000" );
		json.put("userId","" );
		json.put("transId","200" );
		 no="10022";
		 body = json.toString();
		body = new Encrypt().clientEncrypt(no,body );
		 sendRequest();
	}
///////////////////////////////////////////	
	
	public void test_presentPoints() throws Exception{
		JSONObject json = new JSONObject();
		json.put("tel","13162416106" );
		json.put("amount","10" );
		json.put("reason","111" );
		json.put("beCode","1000" );
		json.put("userId","77676c8a-bebf-47b3-bce0-8d4af9989227" );
		json.put("os","200" );
		 no="10031";
		 body = json.toString();
		body = new Encrypt().clientEncrypt(no,body );
		 sendRequest();
	}
	
	public void test_queryGroup() throws Exception{
		JSONObject json = new JSONObject();
		json.put("credentialsNo", "37543433079292");//02674866397886  68695306535726
		json.put("type", "groupBuy");
		json.put("userId","77676c8a-bebf-47b3-bce0-8d4af9989227" );
		 
		 no="10041";
		 body = json.toString();
		 
		 sendRequest();
	}
	
	public void test_inUseGroup() throws Exception{
		JSONObject json = new JSONObject();
		json.put("credentialsNo", "17587370938396");
		json.put("beCode","1009" );
		json.put("userId","10000006585" );
		json.put("transId", "100002424");
		json.put("type", "groupBuy");
		 no="10042";
		 body = json.toString();
		body = new Encrypt().clientEncrypt(no,body );
		 sendRequest();
	}
	
///////////////////////////交易
	
	
	
	public void test_queryGroupTran() throws Exception{
		JSONObject json = new JSONObject();
		//json.put("startdate", "2013-04-24|15:47:36");
		//json.put("enddate", "2013-04-24|18:47:36");
		json.put("userId","10000002435" );
		json.put("beCode","1000");
		 no="10051";
		body = json.toString();
	
		 sendRequest();
	}
	public void test_queryReceiveTran() throws Exception{
		JSONObject json = new JSONObject();
//		json.put("startdate", "2013-04-24|15:47:36");
//		json.put("enddate", "2013-04-24|18:47:36");
		json.put("userId","10000006668" );
		json.put("beCode","1000");
		 no="10052";
		body = json.toString();
	
		 sendRequest();
	}
	public void test_queryPointsTran() throws Exception{
		JSONObject json = new JSONObject();
		json.put("startdate", "1970-01-01|08:30:00");
		json.put("enddate", "");
		json.put("userId","77676c8a-bebf-47b3-bce0-8d4af9989227" );
		 no="10053";
		body = json.toString();
	
		 sendRequest();
	}
	public void test_queryExchangeTran() throws Exception{
		JSONObject json = new JSONObject();
		json.put("startdate", "2013-04-24|15:47:36");
		json.put("enddate", "2013-11-24|18:47:36");
		json.put("userId","10000002435" );
		json.put("beCode","1000");
		 no="10054";
		body = json.toString();
	
		 sendRequest();
	}
	  
public void test_getStroesByMerchantId() throws Exception{
	try {
		JSONObject json = new JSONObject();
		json.put("merchantId", "100002687");
		 no="0904";
		body = json.toString();
		 sendRequest();
	} catch (Exception e) {
		e.printStackTrace();
	}
		
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
