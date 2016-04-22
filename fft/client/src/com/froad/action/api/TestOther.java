package com.froad.action.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.froad.util.MD5;
 

public class TestOther {
	private static Logger logger = Logger.getLogger(TestOther.class);
	protected String mac;//对bodyMac校验
	protected String no;//接口序列号
	protected String body;//主体
	protected String count;//请求标识号
	public static final String KEY = "00CBA177EA725E7C";
	//签到+直接优惠+积分获取
	//static String server ="http://116.228.153.46:8686/communityBusiness_client/client_api.action";
	static String server ="http://localhost:8080/communityBusiness_client/client_api.action";
	//static String server ="http://www.fenfentong.com.cn/client_api.action";
	public static void main(String[] args) throws Exception {
 		TestOther test = new TestOther(); 
		//积分兑换
    	//       test.test_queryHFCZ();
     	 //       test.test_queryPointsGoods();
		//    test.test_queryLottery();
	   //  test.test_addHFCZ();
//	    test.test_task();
	  	//  	test.test_addLottery();
		 //  test.test_addPointGoodsTran();
		//直接优惠
 		// test.test_queryMerchantByType();
 		// test.test_queryMerchant();
//		  test.test_reSend();
 		//   test.test_version();
 		  test.test_TypeRecommend();
		//积分获取
 	  //	test.test_queryMerchant();
 	  //	test.test_queryMerchantId();
		//System.out.println(System.getProperty("file.encoding"));
    	     //  System.Text.RegularExpressions.Regex regex1 = new System.Text.RegularExpressions.Regex(@"<script[\s\S]+</script *>", System.Text.RegularExpressions.RegexOptions.IgnoreCase);
    	       
 		//System.out.println(  getTxtWithoutHTMLElement("<html>dfds<sdf>dddddddd东方舵手v<sdf></html>"));
	}
	
	 
	
	public void test_reSend() throws Exception{   
		JSONObject json = new JSONObject();
		 
		json.put("ticketId","10000803" );
		 
		
		body = json.toString();
		no="0003";
		 sendRequest();
	}
	
	public void test_version() throws Exception{   
		JSONObject json = new JSONObject();
		 
		json.put("clientKey","zrcb_fft_iphone" );
		json.put("version","1.0" );
		json.put("userId","b743ea60-d767-4ef2-bcab-369d7607ea97" );
		
		body = json.toString();
		no="0000";
		 sendRequest();
	}
	
	public void test_addPointGoodsTran() throws Exception{
		JSONObject json = new JSONObject();
		//addTran_goods]body:{"goodsId":100001005,"userId":"","goodsNum":"2","bankPointPrice":"0.00","fftPointPrice":"53.00","clientType":"300","cashPrice":"147.00"
		json.put("userId","b55a8a71-4130-4435-bcd7-037d9c3c5686" );
		json.put("goodsId","100001005" );
		json.put("goodsNum","2.0" );
		json.put("cashPrice","50.00" );
		json.put("fftPointPrice","100.00" );
		json.put("bankPointPrice","0.00" );
		json.put("clientType", "200");
		body = json.toString();
		no="0306";
		 sendRequest();
	}
	//积分获取  商户
	public void test_TypeRecommend() throws Exception{
		JSONObject json = new JSONObject();
		 
		json.put("merchantID","100001004" );
		body = json.toString();
		no="0801";   //0401
		 sendRequest();
	}
	//积分兑换
	public void test_addHFCZ() throws Exception{
		JSONObject json = new JSONObject();
		json.put("goodsId","100001006" );
		json.put("cashPrice","30" );
		json.put("fftPointPrice","0.00" );
		json.put("bankPointPrice","0" );
		json.put("userId","1001001" );
		json.put("clientType", "200");
	//	    {"bankPointPrice":"0","goodsVal":{"czno":"13918140393"},"goodsId":"100001006","cashPrice":"30.0","clientType":"200","fftPointPrice":"0","userId":"1001001"}] 
		JSONObject goodsVal = new JSONObject();
		goodsVal.put("czno","18621818056" );
		json.put("goodsVal",goodsVal);
		
		body = json.toString();
		no="0304";
		 sendRequest();
	}
	public void test_addLottery() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","73690af6-e63c-45c3-b4c1-e3e4ad1943c4" );
		json.put("goodsId","100001007" );
		json.put("cashPrice","0.0" );
		json.put("bankPointPrice","0" );
		json.put("fftPointPrice","2" );
		json.put("clientType", "200");
		
		JSONObject goodsVal = new JSONObject();
		goodsVal.put("content","04,05,06,12,13,14|05;" );
		goodsVal.put("period","2112" );
		json.put("goodsVal",goodsVal);
		
		body = json.toString();
		no="0305";
		 sendRequest();
	}
	
	 /////////////直接优惠
	public void test_queryMerchantByType() throws Exception{
		JSONObject json = new JSONObject();
		 
		json.put("czno","18621818056" );
		body = json.toString();
		no="0402";
		 sendRequest();
	}
	//积分获取  商户
	public void test_queryMerchant() throws Exception{
		JSONObject json = new JSONObject();
		 
		json.put("merchantID","100001004" );
		body = json.toString();
		no="0602";   //0401
		 sendRequest();
	}
	//积分获取   商品
	public void test_queryMerchantId() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","991e4607-80da-4ae5-9048-942d7a6a78e1" );
		json.put("merchantID","100001102" );
		body = json.toString();
		no="0502";
		 sendRequest();
	}
	
	 /////////////直接优惠
	/////签到
	public void test_task() throws Exception{
		JSONObject json = new JSONObject();
		 
		json.put("userId","c9b83d2a-b626-4057-ae8c-4aaee9a5c6d2");
		body = json.toString();
		no="0201";
		 sendRequest();
	}
	
	public void test_queryPointsGoods() throws Exception{
		JSONObject json = new JSONObject();
		 
		json.put("czno","18621818056" );
		body = json.toString();
		no="0303";
		 sendRequest();
	}
	public void test_queryHFCZ() throws Exception{
		JSONObject json = new JSONObject();
		 
		json.put("czno","18621818056" );
		body = json.toString();
		no="0301";
		 sendRequest();
	}
	public void test_queryLottery() throws Exception{
		JSONObject json = new JSONObject();
		 
		json.put("czno","18621818056" );
		body = json.toString();
		no="0302";
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
