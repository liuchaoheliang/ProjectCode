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

public class TestTran {
	protected String mac;//对bodyMac校验
	protected String no;//接口序列号
	protected String body;//主体
	protected String count;//请求标识号
	public static final String KEY = "00CBA177EA725E7C";
	//static String server ="http://116.228.153.46:8686/communityBusiness_client/client_api.action";
	static String server ="http://localhost:8080/communityBusiness_client/client_api.action";
	//static String server ="http://www.fenfentong.com.cn/client_api.action"; 
	public static void main(String[] args) throws Exception {
 		TestTran test = new TestTran();
// 		 test.test_goods_0601();
		Double points_d = -1.0;
		test.test_getStroesByMerchantId();
		
		
		
//		BigDecimal decimalPrice= new BigDecimal("1.5");
//		String price= decimalPrice.setScale(0, BigDecimal.ROUND_DOWN).toString();          //.multiply(new BigDecimal("0.5")).divide(new BigDecimal("10")).setScale(0, BigDecimal.ROUND_UP).multiply(new BigDecimal("10")).setScale(2).toString();
//	    System.out.println(price);
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
	
	
	public void test_getGroupByPager() throws Exception{
		JSONObject json = new JSONObject();
//		json.put("beginTime", "111111");
//		json.put("endTime", "111112");
		json.put("userId","991e4607-80da-4ae5-9048-942d7a6a78e1" );
		 no="0501";
		body = json.toString();
	 
		 sendRequest();
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
	public void test_getGoodsCarry() throws Exception{
		JSONObject json = new JSONObject();
		json.put("beginTime", "111111");
		json.put("endTime", "111112");
		json.put("userId","716b7eaf-6fdf-4aa3-9a61-3f5217ead73b" );
		 no="0503";
		body = json.toString();
	
		 sendRequest();
	}
	
	public void test_goods_0601() throws Exception{
		JSONObject json = new JSONObject();
		json.put("merchantID", "100001001");
		no="0602";
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
