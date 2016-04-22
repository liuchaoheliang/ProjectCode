package com.froad.action.api;
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
 
import com.froad.action.api.command.Encrypt;
import com.froad.action.api.user.UserAction;
import com.froad.util.MD5;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
 

public class TestUser {
	private static Logger logger = Logger.getLogger(TestUser.class);
	protected String mac;//对bodyMac校验
	protected String no;//接口序列号
	protected String body;//主体
	protected String count;//请求标识号
	public static final String KEY = "00CBA177EA725E7C";

	
	static String serverURL = "http://localhost:9090/communityBusiness_client/";
	//static String serverURL = "http://116.228.153.46:8686/communityBusiness_client/";
	static String serverURL_querySuggestionReply=serverURL+"user_querySuggestionReply.action" ;
	static String serverURL_addSuggestion=serverURL+"user_addSuggestion.action" ;
	static String serverURL_querySuggestion=serverURL+"user_querySuggestion.action" ;
	static String serverURL_queryPoints =serverURL+"user_queryPointAccount.action" ;
	static String serverURL_favoritedel =serverURL+"user_favoritedel.action" ;
	static String serverURL_favoriteadd =serverURL+"user_favoriteadd.action" ;
	static String serverURL_favorite =serverURL+"user_favorite.action" ;
	static String serverURL_shortMsg =serverURL+"user_shortmsg.action" ;
	static String serverURL_changeUserPhone=serverURL+"user_changeUserPhone.action" ;
	static String serverURL_changeUserInfo=serverURL+"user_changeUserInfo.action" ;
	static String serverURL_changePwd =serverURL+"user_changePwd.action" ;
	static String serverURL_registered =serverURL+"user_registered.action" ;
	static String serverURL_login ="http://localhost:8080/communityBusiness_client/user_login.action" ;
	
//	static String server ="http://192.168.2.172:8686/communityBusiness_client/client_api.action";
	static String server ="http://localhost:8080/communityBusiness_client/client_api.action";
	/**
	 * 方法描述：
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: guoming guoming@f-road.com.cn
	 * @throws Exception 
	 * @time: 2013-2-21 下午02:32:46
	 */
	public static void main(String[] args) throws Exception {
 		TestUser test = new TestUser();
// 	 	test.test_sendmsg();
   	   	 test.test_login();
//   	   	 test.test_register();
//	  	test.test_changePwd();
//  	 	test.test_queryPoints();
//  		test.test_changeUserPhone();
//   		test.test_changeUserInfo();
// 		test.test_queryUserInfo();
//    	 	 test.test_querySuggestion();
  		// test.test_addSuggestion();
// 		 test.test_querySuggestionReply();
 //		 test.test_favorite();
   	//	 test.test_favoriteAdd();
  	//	 test.test_favoriteDel();
  		  
	}

	
	public void test_sendmsg() throws Exception{
		JSONObject json = new JSONObject();
		
		json.put("type", "02");
		json.put("mobilephone","18523286541" );
		body = json.toString();
		no="0001";
		sendRequest(serverURL_shortMsg);
	}
	
	public void test_login() throws Exception{
		JSONObject json = new JSONObject();
		json.put("type", "10");
		json.put("user", "13038389991");
//		json.put("pin", "586856");
		json.put("password","111110");
		json.put("os", "300");		 
		body = json.toString();
		no="0102";
		 body = new Encrypt().clientEncrypt(no,body );
		 sendRequest(serverURL_login);
	} 
	
	public void test_register() throws Exception{
		JSONObject json = new JSONObject();
		json.put("mobilephone", "13038389991");
		json.put("password","111111" );
//		json.put("code", "434914");		 
		body = json.toString();
		no="0101";
		 body = new Encrypt().clientEncrypt(no,body );
		 sendRequest(serverURL_registered);
	} 
	
	public void test_changePwd() throws Exception{
		JSONObject json = new JSONObject();
		json.put("oldpw", "000000");
		json.put("newpw", "111111");
		json.put("userId","50000000186" );
		no="0103";
		body = json.toString();
		body = new Encrypt().clientEncrypt(no,body );
		 sendRequest(serverURL_changePwd);
	}
	
	public void test_queryPoints() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","50000000186" );
	//	json.put("orgNo","10001001" );
		body = json.toString();
		no="0104";
		sendRequest(serverURL_queryPoints);
	}
	public void test_changeUserPhone() throws Exception{
		JSONObject json = new JSONObject();
	 	json.put("oldphone", "13038389991");
	 	json.put("newphone", "18621838056");
	 	json.put("userId","50000000186" );
		 no="0105";
		body = json.toString();
		body = new Encrypt().clientEncrypt(no,body );
		sendRequest(serverURL_changeUserPhone);
	}
	
	public void test_changeUserInfo() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","50000000186" );
		json.put("uname", "admin");
		json.put("email", "liuchao@sina.com");
		json.put("age", "22");
		json.put("sex", "0");
		body = json.toString();
		 no="0106";
		 body = new Encrypt().clientEncrypt(no,body );
		sendRequest(serverURL_changeUserInfo);
	}
	public void test_queryUserInfo() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","50000000186" );
		 
		body = json.toString();
		 no="0113";
		 body = new Encrypt().clientEncrypt(no,body );
		sendRequest(serverURL_changeUserInfo);
	}
	
	
	public void test_querySuggestion() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","1001001" );
		 json.put("suggestionId","100001006" );
		  json.put("page","1" );
		body = json.toString();
		no="0107";
		sendRequest(serverURL_querySuggestion);
	}
	
	public void test_addSuggestion() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","1001001" );
		json.put("title","186218180sdgfgdsfd56" );
		json.put("content","dsfdfdfdsfdd" );
		body = json.toString();
		no="0108";
		sendRequest(serverURL_addSuggestion);
	}
	public void test_querySuggestionReply() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","1001001" );
		json.put("suggestionId","100001001" );
		json.put("content","dsfdfdfdsfdd" );
		body = json.toString();
		no="0109";
		sendRequest(serverURL_querySuggestionReply);
	}
	public void test_favorite() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","1001001" );
		 // json.put("collectId", "100001405");
		 
		 no="0110";
		body = json.toString();
		sendRequest(serverURL_favorite);
	}
	public void test_favoriteAdd() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","eca94879-aab5-4114-a032-150fe9e8ee98" );
		json.put("merchantId", "100001042");
		 no="0111";
		body = json.toString();
		sendRequest(serverURL_favoriteadd);
	}
	
	public void test_favoriteDel() throws Exception{
		JSONObject json = new JSONObject();
		json.put("userId","1001001" );
		json.put("collectId", "100001399");
		 no="0112"; 
		body = json.toString();
		sendRequest(serverURL_favoritedel);
	}
	public  void sendRequest(String url) throws Exception{
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
