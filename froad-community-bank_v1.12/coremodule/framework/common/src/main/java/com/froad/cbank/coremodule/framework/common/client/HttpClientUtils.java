package com.froad.cbank.coremodule.framework.common.client;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient工具类
 * 
 * @author jdkleo
 * 
 */
public class HttpClientUtils {

	/**
	 * 不带GZIP的post请求
	 * 
	 * @param url请求的地址
	 * @param nvps请求的参数
	 * @param charset请求的编码
	 * @param username验证用户名
	 * @param password验证密码
	 * @param connectTimeout连接超时时间
	 *            ，毫秒
	 * @param readTimeout
	 *            读取服务器数据超时时间，毫秒
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPostWithOutGzip(String url, List<NameValuePair> nvps, String charset, String username, String password,
			int connectTimeout, int readTimeout) throws ClientProtocolException, IOException {
		return doPost(url, nvps, charset, username, password, connectTimeout, readTimeout, false);
	}

	/**
	 * 执行POST
	 * @param url
	 * @param nvps
	 * @param charset
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPost(String url, List<NameValuePair> nvps, String charset) throws ClientProtocolException, IOException {
		return doPost(url, nvps, charset, "", "", 0, 0, false);
	}
	/**
	 * 执行POST
	 * @param url
	 * @param nvps
	 * @param charset
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doHttpsPost(String url, List<NameValuePair> nvps, String charset) throws ClientProtocolException, IOException {
		return doHttpsPost(url, nvps, charset, "", "", 0, 0, false);
	}

	
	/**
	 * 执行post请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param nvps
	 *            请求的参数
	 * @param charset
	 *            请求的编码
	 * @param username
	 *            basic验证用户名
	 * @param password
	 *            basic验证密码
	 * @param connectTimeout
	 *            连接超时时间，毫秒
	 * @param readTimeout
	 *            读取服务器数据超时时间，毫秒
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPost(String url, List<NameValuePair> nvps, String charset, String username, String password, int connectTimeout,
			int readTimeout, boolean isGzip) throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, connectTimeout);// 连接超时
		httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, readTimeout); // 读取超时
		if (isGzip) {
			httpclient.addRequestInterceptor(new RequestAcceptEncoding());
			httpclient.addResponseInterceptor(new ResponseContentEncoding());
		}
		try {

			// //设置代理对象 ip/代理名称,端口
			// httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
			// new HttpHost("127.0.0.1", 8080));
			// 实例化验证
			// CredentialsProvider credsProvider = new
			// BasicCredentialsProvider();
			// Credentials defaultcreds = new
			// UsernamePasswordCredentials("jdkleo", "123456");
			//
			// //创建验证
			// credsProvider.setCredentials(new AuthScope("127.0.0.1",8080),
			// defaultcreds);
			// httpclient.setCredentialsProvider(credsProvider);

			// httpclient.getState().setCredentials(new AuthScope("m00yhost",
			// 80, AuthScope.ANY_REALM), defaultcreds);

			HttpPost httpPost = new HttpPost(url);
//			Base64Utils base64code = new Base64Utils();
//			httpPost.setHeader("soauser", "liu");
//			httpPost.setHeader("soapw", "123");
			// httpPost.setHeader("Accept-Encoding", "gzip,deflate");
			// httpPost.setHeader("user-agent",
			// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; Alexa Toolbar; Maxthon 2.0)");
			if(null==nvps){
				nvps=new ArrayList<NameValuePair>();
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset.toUpperCase()));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String restr=httpclient.execute(httpPost, responseHandler);
			return restr;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		// other
		// HttpResponse response = httpclient.execute(httpPost);
		// HttpEntity entity = response.getEntity();

		// System.out.println("Login form get: " + response.getStatusLine());
		// 调用HttpClient实例的execute方法:
		// (1)对于执行结果为2XX的情况，BasicResponseHandler会自动把 response
		// body以String方式返回
		// (2)对于3xx的response它会在内部自动重定向
		// (3)对于没有response body的情况，他会返回null。
		// 显示结果
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(entity.getContent(), "UTF-8"));
		// StringBuffer stringBuffer = new StringBuffer();
		// String line = null;
		// while ((line = reader.readLine()) != null) {
		// stringBuffer.append(line);
		// }
		// if (entity != null) {
		// entity.consumeContent();
		// }
	}
	public static String doSignPost(String url,String parame, String charset) throws ClientProtocolException, IOException {
		return doSignPost(url, parame, charset, "", "", 0, 0, false);
	}
	public static String doSignPost(String url,String parame , String charset, String username, String password, int connectTimeout,
			int readTimeout, boolean isGzip) throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, connectTimeout);// 连接超时
		httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, readTimeout); // 读取超时
		if (isGzip) {
			httpclient.addRequestInterceptor(new RequestAcceptEncoding());
			httpclient.addResponseInterceptor(new ResponseContentEncoding());
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity reqEntity = new StringEntity(new String(parame.getBytes(charset), "ISO-8859-1"));   
		     // 设置类型   
			reqEntity.setContentType("application/xml");   
            //设置请求的数据   
			httpPost.setEntity(reqEntity);   
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String restr=httpclient.execute(httpPost, responseHandler);
			return restr;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	/**
	 * 执行post请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param nvps
	 *            请求的参数
	 * @param charset
	 *            请求的编码
	 * @param username
	 *            basic验证用户名
	 * @param password
	 *            basic验证密码
	 * @param connectTimeout
	 *            连接超时时间，毫秒
	 * @param readTimeout
	 *            读取服务器数据超时时间，毫秒
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException 
	 */
	public static String doHttpsPost(String url, List<NameValuePair> nvps, String charset, String username, String password, int connectTimeout,
			int readTimeout, boolean isGzip) throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = wrapClient(new DefaultHttpClient());
		httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, connectTimeout);// 连接超时
		httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, readTimeout); // 读取超时
		if (isGzip) {
			httpclient.addRequestInterceptor(new RequestAcceptEncoding());
			httpclient.addResponseInterceptor(new ResponseContentEncoding());
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			if(null==nvps){
				nvps=new ArrayList<NameValuePair>();
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset.toUpperCase()));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String restr=httpclient.execute(httpPost, responseHandler);
			return restr;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	/***
	 * @Description:此方法可以对原有HttpClient进行修改让其接受任意SSL证书：
	 * @param base
	 * @return
	 * @author 高达软件系统有限公司 wwb  2012-12-18 insert
	 */
	 private static DefaultHttpClient wrapClient(HttpClient base) {
         try {
        	 /**使用TLS进行加载证书访问**/
             SSLContext ctx = SSLContext.getInstance("TLS");
             /**对检查证书的类进行重写*/
             X509TrustManager tm = new X509TrustManager() {
                 public X509Certificate[] getAcceptedIssuers() {
                     return null;
                 }
                 public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
                 public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
             };
             /**初始化TLS工厂 **/
             ctx.init(null, new TrustManager[] { tm }, null);
             /**代替新的工厂类型*/
             SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
             SchemeRegistry registry = new SchemeRegistry();
             registry.register(new Scheme("https", 443, ssf));
             ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
             return new DefaultHttpClient(mgr, base.getParams());
         } catch (Exception ex) {
             ex.printStackTrace();
             return null;
         }
     }
	public static String doGet(String url,  String charset) throws ClientProtocolException, IOException {
		return doGet(url,  charset, "", "", 0, 0, false);
	}
	
	public static String doGet(String url, String charset, String username, String password, int connectTimeout,
			int readTimeout, boolean isGzip) throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, connectTimeout);// 连接超时
		httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, readTimeout); // 读取超时
		if (isGzip) {
			httpclient.addRequestInterceptor(new RequestAcceptEncoding());
			httpclient.addResponseInterceptor(new ResponseContentEncoding());
		}
		try {

			// //设置代理对象 ip/代理名称,端口
			// httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
			// new HttpHost("127.0.0.1", 8080));
			// 实例化验证
			// CredentialsProvider credsProvider = new
			// BasicCredentialsProvider();
			// Credentials defaultcreds = new
			// UsernamePasswordCredentials("jdkleo", "123456");
			//
			// //创建验证
			// credsProvider.setCredentials(new AuthScope("127.0.0.1",8080),
			// defaultcreds);
			// httpclient.setCredentialsProvider(credsProvider);

			// httpclient.getState().setCredentials(new AuthScope("m00yhost",
			// 80, AuthScope.ANY_REALM), defaultcreds);

			HttpGet httpGet = new HttpGet(url);
//			Base64Utils base64code = new Base64Utils();
//			httpGet.setHeader("WWW-Authenticate", "Basic realm=\"hujzpb@163.com\"");
//			httpGet.setHeader("authorization", "Basic " + base64code.encode(username + ":" + password));
			// httpPost.setHeader("Accept-Encoding", "gzip,deflate");
			// httpPost.setHeader("user-agent",
			// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; Alexa Toolbar; Maxthon 2.0)");
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String restr=httpclient.execute(httpGet, responseHandler);
			return restr;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		// other
		// HttpResponse response = httpclient.execute(httpPost);
		// HttpEntity entity = response.getEntity();

		// System.out.println("Login form get: " + response.getStatusLine());
		// 调用HttpClient实例的execute方法:
		// (1)对于执行结果为2XX的情况，BasicResponseHandler会自动把 response
		// body以String方式返回
		// (2)对于3xx的response它会在内部自动重定向
		// (3)对于没有response body的情况，他会返回null。
		// 显示结果
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(entity.getContent(), "UTF-8"));
		// StringBuffer stringBuffer = new StringBuffer();
		// String line = null;
		// while ((line = reader.readLine()) != null) {
		// stringBuffer.append(line);
		// }
		// if (entity != null) {
		// entity.consumeContent();
		// }
	}
	
	public static void main(String[] args) throws Exception {

		String url = "http://127.0.0.1:8070/ESB-1.0.1/restproxy/get/0001/url/intScheduler!autoRestartScheduler.htm/charset/utf-8";
		String charset = "UTF-8";
		//url+=charset;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(
				"argsjson",
				"{\"resourceGinfoVo\":{\"ginfoId\":83,\"cmemberCode\":\"0001\",\"cmemberOcode\":\"0001\",\"memberCode\":\"00000009\",\"memberName\":\"会员6ming\",\"goodsType\":\"0\",\"ginfoOrigin\":\"0\",\"ginfoConf\":\"test协议\",\"ginfoRemark\":\"团购标题测试1\",\"billtypeCode\":\"TG000001\",\"billtypeListNode\":\"0\",\"resourceGlistListVo\":[{\"glistId\":3122,\"pntreeName\":\"钢管\",\"partsnameName\":\"黑发卷\",\"productareaName\":\"钢铁\",\"goodsMaterial\":\"cz-1\",\"goodsSpec\":\"gg-1\",\"goodsNum\":10.0,\"goodsWeight\":100.0,\"goodsHangnum\":10.0,\"goodsHangweight\":100.0,\"pricesetHangprice\":1234.0,\"warehouseCode\":\"00000007\",\"warehouseName\":\"新航库\",\"areaName\":\"上海市\",\"areaCode\":\"310000\"}]}}"
				//"{\"ddVo\":{\"ddId\":null,\"billtypeCode\":\"002\",\"cmemberCode\":\"0001\",\"billtypeTable\":\"ResIn\",\"ddColumn\":\"a\",\"ddName\":\"test\",\"ddValue\":\"1\"}}"
				));
		Long d=System.currentTimeMillis();
		String result = HttpClientUtils.doGet(url,  charset);
		Long e=System.currentTimeMillis();
		System.out.println(e-d);
		System.out.println(result);
	}
	
	public static byte[] doPostToBank(String url, List<NameValuePair> nvps, String charset) throws ClientProtocolException, IOException {
		return doPostToBank(url, nvps, charset, "", "", 0, 0, false);
	}
	public static byte[] doPostStrToBank(String url,String xml, String charset){
		return doPostStrToBank(url, xml, charset, "", "", 0, 0, false);
	}
	private static byte[] doPostStrToBank(String url, String xml, String charset, String username, String password, int connectTimeout,
			int readTimeout, boolean isGzip) {
		byte[] results = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, connectTimeout);// 连接超时
		httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, readTimeout); // 读取超时
		if (isGzip) {
			httpclient.addRequestInterceptor(new RequestAcceptEncoding());
			httpclient.addResponseInterceptor(new ResponseContentEncoding());
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new  BufferedHttpEntity(new StringEntity(xml,charset.toUpperCase())));
			HttpResponse httpResponse = null;
			httpResponse = httpclient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			results = EntityUtils.toByteArray(httpEntity);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			httpclient.getConnectionManager().shutdown();
		}
		return results;
	}
	
	 
	
	public static byte[] doPostToBank(String url, List<NameValuePair> nvps, String charset, String username, String password, int connectTimeout,
			int readTimeout, boolean isGzip) {
		byte[] results = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, connectTimeout);// 连接超时
		httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, readTimeout); // 读取超时
		if (isGzip) {
			httpclient.addRequestInterceptor(new RequestAcceptEncoding());
			httpclient.addResponseInterceptor(new ResponseContentEncoding());
		}
		try {

			HttpPost httpPost = new HttpPost(url);
			if(null==nvps){
				nvps=new ArrayList<NameValuePair>();
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset.toUpperCase()));
			HttpResponse httpResponse = null;
			httpResponse = httpclient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			results = EntityUtils.toByteArray(httpEntity);
			/*if (httpEntity != null) {
				long length = httpEntity.getContentLength();
				InputStream inputStream = httpEntity.getContent();
				results = new byte[(int)length];
				inputStream.read(results);
			}*/
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			httpclient.getConnectionManager().shutdown();
		}
		return results;
	}

	// public static void main(String[] args) throws Exception {
	// DefaultHttpClient httpclient = new DefaultHttpClient(); //实例化一个HttpClient
	// HttpResponse response = null;
	// HttpEntity entity = null;
	// httpclient.getParams().setParameter(
	// ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
	// //设置cookie的兼容性
	// HttpPost httpost = new HttpPost("http://127.0.0.1:8080/pub/jsp/getInfo");
	// //引号中的参数是：servlet的地址
	// List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	// nvps.add(new BasicNameValuePair("jqm",
	// "fb1f7cbdaf2bf0a9cb5d43736492640e0c4c0cd0232da9de"));
	// // BasicNameValuePair("name", "value"), name是post方法里的属性, value是传入的参数值
	// nvps.add(new BasicNameValuePair("sqm", "1bb5b5b45915c8"));
	// httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	// //将参数传入post方法中
	// response = httpclient.execute(httpost); //执行
	// entity = response.getEntity(); //返回服务器响应
	// try{
	// System.out.println("----------------------------------------");
	// System.out.println(response.getStatusLine()); //服务器返回状态
	// Header[] headers = response.getAllHeaders(); //返回的HTTP头信息
	// for (int i=0; i<headers.length; i++) {
	// System.out.println(headers[i]);
	// }
	// System.out.println("----------------------------------------");
	// String responseString = null;
	// if (response.getEntity() != null) {
	// responseString = EntityUtils.toString(response.getEntity()); /
	// /返回服务器响应的HTML代码
	// System.out.println(responseString); //打印出服务器响应的HTML代码
	// }
	// } finally {
	// if (entity != null)
	// entity.consumeContent(); // release connection gracefully
	// }
	// System.out.println("Login form get: " + response.getStatusLine());
	// if (entity != null) {
	// entity.consumeContent();
	// }
	//
	// }
}
