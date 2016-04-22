package com.froad.CB.thirdparty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.froad.CB.AppException.AppException;
import com.froad.CB.common.Command;
import com.froad.CB.common.SysCommand;
import com.froad.CB.common.constant.PayCommand;
import com.froad.CB.common.constant.PointCommand;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.po.transaction.Points;
import com.froad.CB.po.transaction.PointsAccount;
import com.froad.CB.po.transaction.Rebate;
import com.froad.CB.po.transaction.WithdrawPoints;
import com.froad.util.DateUtil;
import com.froad.util.FreemarkerUtils;
import com.froad.util.HttpClientUtils;
import com.froad.util.RsaUtil;
import com.froad.util.String2Xml;


	/**
	 * 类描述：与积分平台交互工具类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jan 6, 2013 2:09:06 PM 
	 */
public class PointsFunc {

	private static final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final Logger logger=Logger.getLogger(PointsFunc.class);
	
	private static final Random rand=new Random();
	
	
	public static void main(String[] args) {
		/**接口测试数据**/
		Points points=new Points();
		points.setOrgNo(TranCommand.FFT_ORG_NO);
		points.setObjectNo("orderRealT110001");
		points.setObjectDes("04182201积分测试");
		points.setObjectType(PointCommand.OBJECT_TYPE_CONSUME);
		points.setAccountId("34");
		points.setPoints("0");
		points.setAccountMarkedType(PointCommand.ACCOUNT_MARKED_TYPE);
		points.setAccountMarked("u1304046");
		
		/**消费积分**/
//		try {
//			payPoints(points);
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
		
		/**退还积分**/
//		points.setPayPointsNo("713");
//		refundPoints(points);
		
//		/**增送积分**/
//		try {
//			presentPoints(points);
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
		
		/**积分返利**/
//		Rebate rebateBean=new Rebate();
//		rebateBean.setPoints("1");
//		rebateBean.setObjectNo("100009001");
//		rebateBean.setAccountMarkedFrom("");
//		rebateBean.setAccountMarkedTo("");
//		rebateBean.setFromPhone("");
//		rebateBean.setToPhone("");
//		try {
//			rebatePoints(rebateBean);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	
//		/**查询会员积分**/
//		Points params=new Points();
//		params.setPartnerNo(TranCommand.PARTNER_ID);
//		params.setOrgNo(TranCommand.FFT_ORG_NO);
//		params.setAccountMarked("user68");
//		params.setAccountMarkedType(PointCommand.ACCOUNT_MARKED_TYPE);
//		try {
//			queryPoints(params);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		/**test兑充珠海银行积分**/
//		Points point = new Points();
//		point.setAccountMarked("u1304046");
//		point.setMobilePhone("15000092732");
//		point.setOrgPoints("22"); 
//		try {
//			fillPoints(point);
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
		
		try {
			points.setOrgNo(TranCommand.FFT_ORG_NO);
			points.setAccountMarked("fenfentong");
			points.setAccountMarkedType(PointCommand.ACCOUNT_MARKED_TYPE);
			points.setRealName("李金魁");
			points.setBankId("203");
			points.setBankName("珠海农商银行");
			points.setBankCard("6228590101011262633");
			points.setBusinessType("分分通积分提现测试");
			points.setPoints("20");
			points.setObjectNo("fft100350120");
			points.setCertNo("430811199910101234");
			withdrawPoints(points);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static String getRandom(){
		return "fbu"+System.currentTimeMillis()+rand.nextInt(100000);
	}
	
	
	/**
	 * 方法描述：兑充申请接口
	 * 
	 * @param:	Point   username , mobilePhone ,orgPoints,
	 * @return:	Point
	 * @version: 1.0
	 * @author: liqiaopeng@f-road.com.cn
	 * @time: 2012-5-31 
	 */
	@SuppressWarnings("unchecked")
	public static Points fillPoints(Points point) throws AppException {
		Points pointreq = new Points();
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String requestTime = df2.format(new Date());
		 String partnerNO = TranCommand.PARTNER_ID;
		 String pointsCateNo = PointCommand.POINTS_ZH_CATE_NO;		 
		 String orgNo = TranCommand.BANK_ORG_NO;
		 String orgPoints = point.getOrgPoints()==null?"":point.getOrgPoints();
		 String timeNo = String.valueOf(System.currentTimeMillis());//当前时间，精确到毫秒
		 String requestNo = timeNo;	//最大120位由数字英文(仅小写)混合组成。(为了商城可以过来查询充积分是否成功)
		 String phone = point.getMobilePhone()==null?"":point.getMobilePhone();
		 String accountMarked = point.getAccountMarked()==null?"":point.getAccountMarked();
		 String accountMarkedType = PointCommand.ACCOUNT_MARKED_TYPE;
		 
		 logger.info("申请兑充积分商户的会员标识:" + accountMarked + "   兑充积分的机构编号：orgNo " + orgNo);
		 
		 Map map = new TreeMap();
		 map.put("orgNo", orgNo);
		 map.put("pointsCateNo", pointsCateNo);
		 map.put("orgPoints", orgPoints);
		 map.put("phone", phone);
		 map.put("requestNo", requestNo);
		 map.put("partnerNo", partnerNO);
		 
		 
		 HttpClient httpClient = new DefaultHttpClient();
		 HttpPost postMethod=new HttpPost(SysCommand.FILL_POINTS_URL);
		 StringBuffer buffer=new StringBuffer();
		 try {
			 PrivateKey prikey = RsaUtil.initPrivateKey(TranCommand.RSA_PRIVATE_KEY, PayCommand.RSAPWD);//线上密钥地址
//			 PrivateKey prikey = RsaUtil.initPrivateKey("E:/document/work/key/point/partner/rsa_private_key.key", PayCommand.RSAPWD);//测试密钥地址
			 String signMsg;
			
			 signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);			
			 logger.info("signMsg:  " + signMsg);	
			 
			 String2Xml bodyXml = new String2Xml("requestFillPointsApi");
			 bodyXml.addElementName("fillPointsInfo");
			 bodyXml.append("orgNo", orgNo);
			 bodyXml.append("pointsCateNo", pointsCateNo);
			 bodyXml.append("orgPoints", orgPoints);
			 bodyXml.append("businessType", "fbu"+requestNo);
			 bodyXml.append("remark", "分分通积分兑充");
			 bodyXml.addElementName("orgMember");
			 bodyXml.append("phone", phone);
			 bodyXml.addElementName("partnerAccount");
			 bodyXml.append("accountMarked", accountMarked);
			 bodyXml.append("accountMarkedType", accountMarkedType);
			// bodyXml.append("", "");	//积分机构的账户标识信息参数（这个要和具体的积分机构进行确定）
//			 bodyXml.addElementName("notification");
//			 bodyXml.append("noticeUrl", noticeUrl);
			 bodyXml.addElementName("system");
			 bodyXml.append("requestNo", requestNo);
			 bodyXml.append("partnerNo", partnerNO);
			 bodyXml.append("charset", PayCommand.CHARSET_UTF8);
			 bodyXml.append("signType", PayCommand.SIGNTYPE_RSA);
			 bodyXml.append("signMsg", signMsg);
			 bodyXml.append("requestTime", requestTime);
			 String body = bodyXml.toXMLString();
			 StringEntity entity=new StringEntity(body,"UTF-8");			
			 logger.info("请求体:" + body);
			 postMethod.setEntity(entity);;
			 long startTime = System.currentTimeMillis();
			 HttpResponse response  = httpClient.execute(postMethod);
			 long endTime = System.currentTimeMillis();
			 
			 logger.info("发送申请兑充积分请求,连接用时" + (endTime - startTime) + "ms");
			 logger.info("http返回状态:" + response.getStatusLine().getStatusCode());
			 if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				 BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				 String line=null;
				 while ((line = reader.readLine()) != null) {
					 buffer.append(line);
				 }
				 reader.close();
				 
				 logger.info("响应内容："+buffer.toString());
				 
				 Document doc=DocumentHelper.parseText(buffer.toString());	
				 Element rootElm = doc.getRootElement();				
				 for (Iterator it = rootElm.elementIterator(); it.hasNext();) {
					 Element element = (Element) it.next();					 
					 if(element.getQName().getName().equals("partnerAccount"))	{	
						 for (Iterator it2 = element.elementIterator(); it2.hasNext();) {
							 Element element1 = (Element) it2.next();
							 if (element1.getQName().getName().equals("accountMarked")) {
								 pointreq.setAccountMarked(element1.getText());				
							 }
							 if (element1.getQName().getName().equals("accountMarkedType")) {
								 pointreq.setAccountMarkedType(element1.getText());				
							 }				
						 }
					 }	
					 if(element.getQName().getName().equals("orgMember"))	{	
						 for (Iterator it2 = element.elementIterator(); it2.hasNext();) {
							 Element element1 = (Element) it2.next();
							 if (element1.getQName().getName().equals("phone")) {
								 pointreq.setMobilePhone(element1.getText());				
							 }				
						 }
					 }	
					 if(element.getQName().getName().equals("fillPointsInfo"))	{	
						 for (Iterator it2 = element.elementIterator(); it2.hasNext();) {
							 Element element1 = (Element) it2.next();
							 if (element1.getQName().getName().equals("exchPointsNo")) {
								 pointreq.setExchPointsNo(element1.getText());				
							 }
							 if (element1.getQName().getName().equals("orgNo")) {
								 pointreq.setOrgNo(element1.getText());				
							 }
							 if (element1.getQName().getName().equals("pointsCateNo")) {
								 pointreq.setPointsCateNo(element1.getText());				
							 }
							 if (element1.getQName().getName().equals("orgPoints")) {
								 pointreq.setOrgPoints(element1.getText());				
							 }				
							 if (element1.getQName().getName().equals("points")) {
								 pointreq.setPoints(element1.getText());				
							 }
							 if (element1.getQName().getName().equals("accountId")) {
								 pointreq.setAccountId(element1.getText());				
							 }		
//							 if (element1.getQName().getName().equals("currentPoints")) {
//								 pointreq.setCurrentPoints(element1.getText());				
//							 }	
						 }
					 }					 	
					 if(element.getQName().getName().equals("system")){
						 for (Iterator it2 = element.elementIterator(); it2.hasNext();) {
							 Element element1 = (Element) it2.next();
							 if (element1.getQName().getName().equals("resultCode")) {
								 pointreq.setResultCode(element1.getText());				
							 }
							 if (element1.getQName().getName().equals("requestNo")) {
								 pointreq.setRequestNo(element1.getText());				
							 }
							 if (element1.getQName().getName().equals("partnerNo")) {
								 pointreq.setPartnerNo(element1.getText());				
							 }
							 if (element1.getQName().getName().equals("charset")) {
								 pointreq.setCharset(element1.getText());				
							 }				
							 if (element1.getQName().getName().equals("signType")) {
								 pointreq.setSignType(element1.getText());				
							 }
							 if (element1.getQName().getName().equals("signMsg")) {
								 pointreq.setSignMsg(element1.getText());				
							 }		
							 if (element1.getQName().getName().equals("receiveReqTime")) {
								 pointreq.setReceiveReqTime(element1.getText());				
							 }	
						 }
					 }
				 }		
				 Map m = new TreeMap();
				 m.put("accountMarked", pointreq.getAccountMarked()==null?"":pointreq.getAccountMarked());
				 m.put("phone", pointreq.getMobilePhone()==null?"":pointreq.getMobilePhone());
				 m.put("partnerNo", pointreq.getPartnerNo()==null?"":pointreq.getPartnerNo());
				
				 PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY_POINTS);//线上公钥
				 boolean flag = RsaUtil.verifyPublicKey(m.toString(),pointreq.getSignMsg(),pk);
				 logger.info("校验结果" + flag);				 
				 if (flag == true && PayCommand.RESPCODE_SUCCESS.equals(pointreq.getResultCode())) {
					 pointreq.setRespCode(Command.respCode_SUCCESS);
					 pointreq.setRespMsg("申请兑充积分成功...");
					 logger.info("响应码:" + pointreq.getRespCode() + "响应信息:" + pointreq.getRespMsg() + ".商户会员标识:" + pointreq.getAccountMarked() +"积分平台中充积分记录的Id："+pointreq.getExchPointsNo());			 
				 } else {
					 //退会员积分请求失败
					 pointreq.setRespCode(Command.respCode_FAIL);
					 pointreq.setRespMsg("发送申请兑充积分请求失败,返回报文校验失败" + "商户会员标识:" + pointreq.getAccountMarked());
				 }
		
			 } else {
				 //退会员积分请求失败
				 pointreq.setRespCode(Command.respCode_FAIL);
				 pointreq.setRespMsg("发送申请兑充积分请求失败,HTTP通信失败," + "商户会员标识:" + pointreq.getAccountMarked());
			 }			 
		 } catch (HttpException e) {
			 pointreq.setRespCode(Command.respCode_FAIL);
			 pointreq.setRespMsg("发生Http异常!");
			 logger.info(pointreq.getRespMsg() + "\t具体异常信息:" + e.getMessage());
		 } catch (java.net.NoRouteToHostException e) {
			 pointreq.setRespCode(Command.respCode_FAIL);
			 pointreq.setRespMsg("本机没联网:No route to host: connect!");
			 logger.info(pointreq.getRespMsg() + "\t具体异常信息:" + e.getMessage());
		 } catch (java.net.ConnectException e) {
			 pointreq.setRespCode(Command.respCode_FAIL);
			 pointreq.setRespMsg("连接不上积分平台：Connect time out!");
			 logger.info(pointreq.getRespMsg() + "\t具体异常信息:" + e.getMessage());
		 } catch (IOException e) {
			 pointreq.setRespCode(Command.respCode_FAIL);
			 pointreq.setRespMsg("与第三方通信异常!");
			 logger.info(pointreq.getRespMsg() + "\t具体异常信息:" + e.getMessage());
		 } catch (DocumentException e) {
			 pointreq.setRespCode(Command.respCode_FAIL);
			 pointreq.setRespMsg("解析报文异常!");
			 logger.info(pointreq.getRespMsg() + "\t具体异常信息:" + e.getMessage());
		 } catch (Exception e) {
			 pointreq.setRespCode(Command.respCode_FAIL);
			 pointreq.setRespMsg("异常!");
			 logger.error("MPayImpl.query(Bill) SignatureException异常", e);		
		 } finally {
			 // 释放连接
			 httpClient.getConnectionManager().shutdown();
		 }
		return pointreq;
	}
	
	
	/**
	  * 方法描述：消费积分
	  * @param: objectNo,accountId,points,accountMarked,objectType
	  * @return: 积分消费成功时返回Points，否则返回null
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 5, 2013 5:58:53 PM
	  */
	public static Points payPoints(Points point)throws AppException{
		String requestTime = dateFormat.format(new Date());
		 String partnerNO = TranCommand.PARTNER_ID;
		 String accountMarkedType = PointCommand.ACCOUNT_MARKED_TYPE;
		 
		 String objectNo = "fbu"+point.getObjectNo();
		 String objectType = point.getObjectType();
		 String objectDes ="订单号为："+objectNo;
		 String accountId = point.getAccountId()==null?"":point.getAccountId();
		 String points = point.getPoints();
		 String accountMarked =point.getAccountMarked();
		 logger.info("消费积分的会员标识:" + accountMarked + " objectNo: " + objectNo);
		 
		 Map<String,String> map = new TreeMap<String,String>();
		 map.put("accountId", accountId);
		 map.put("objectNo", objectNo);
		 map.put("partnerNo", partnerNO);
		 
		 HttpClient httpClient = new DefaultHttpClient();
		 HttpPost post=new HttpPost(SysCommand.PAY_POINTS_URL);
		 String requestXML =null;
		 StringBuffer buffer=new StringBuffer();
		 String responseXML=null;
		 try {
			 PrivateKey prikey = RsaUtil.initPrivateKey(TranCommand.RSA_PRIVATE_KEY, PayCommand.RSAPWD);//线上密钥地址
			 String signMsg=RsaUtil.signPrivateKey(map.toString(), prikey);			
			 String2Xml bodyXml = new String2Xml("requestPayPointsApi");
			 bodyXml.addElementName("partnerAccount");
			 bodyXml.append("accountMarked", accountMarked);
			 bodyXml.append("accountMarkedType", accountMarkedType);
			 bodyXml.addElementName("payPoints");
			 bodyXml.append("objectNo", objectNo);
			 bodyXml.append("objectDes", objectDes);
			 bodyXml.append("objectType", objectType);
			 bodyXml.append("businessType", objectNo);
			 bodyXml.append("remark", "分分通积分消费");
			 bodyXml.append("accountId", accountId);
			 bodyXml.append("points", points);
			 bodyXml.addElementName("system");
			 bodyXml.append("partnerNo", partnerNO);
			 bodyXml.append("charset", PayCommand.CHARSET_UTF8);
			 bodyXml.append("signType", PayCommand.SIGNTYPE_RSA);
			 bodyXml.append("signMsg", signMsg);
			 bodyXml.append("requestTime", requestTime);
			 requestXML = bodyXml.toXMLString();
			 StringEntity entity=new StringEntity(requestXML,"UTF-8");
			 post.setEntity(entity);
			 long startTime = System.currentTimeMillis();
			 HttpResponse response = httpClient.execute(post);
			 logger.info("发送消费会员积分请求,连接用时"+(System.currentTimeMillis()-startTime) + "ms");
			 if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				 BufferedReader reader = new BufferedReader(new InputStreamReader(
				 response.getEntity().getContent(), "UTF-8"));
				 String line=null;
				 while ((line = reader.readLine()) != null) {
					 buffer.append(line);
				 }
				 reader.close();
				 responseXML=buffer.toString();
				 logger.info("消费积分的响应报文： "+responseXML);
				 Document doc=DocumentHelper.parseText(responseXML);
				 Element root=doc.getRootElement();
				 Iterator<?> firstIter=root.elementIterator();
				 Element first=null;
				 String firstName=null;
				 Iterator<?> secondIter=null;
				 Element second=null;
				 String secondName=null;
				 Points pointsRes=new Points();
				 while(firstIter.hasNext()){
					 first=(Element)firstIter.next();
					 firstName=first.getName();
					 secondIter=first.elementIterator();
					 if(firstName.equals("partnerAccount")){
						 while(secondIter.hasNext()){
							 second=(Element)secondIter.next();
							 secondName=second.getName();
							 if(secondName.equals("accountMarked")){
								 pointsRes.setAccountMarked(second.getTextTrim());
							 }else if(secondName.equals("accountMarkedType")){
								 pointsRes.setAccountMarkedType(second.getTextTrim());
							 }
						 }
					 }else if(firstName.equals("payPoints")){
						 while(secondIter.hasNext()){
							 second=(Element)secondIter.next();
							 secondName=second.getName();
							 if(secondName.equals("payPointsNo")){
								 pointsRes.setPayPointsNo(second.getTextTrim());
							 }else if(secondName.equals("objectNo")){
								 pointsRes.setObjectNo(second.getTextTrim());
							 }else if(secondName.equals("objectDes")){
								 pointsRes.setObjectDes(second.getTextTrim());
							 }else if(secondName.equals("objectType")){
								 pointsRes.setObjectType(second.getTextTrim());
							 }else if(secondName.equals("accountId")){
								 pointsRes.setAccountId(second.getTextTrim());
							 }else if(secondName.equals("points")){
								 pointsRes.setPoints(second.getTextTrim());
							 }
						 }
					 }else if(firstName.equals("system")){
						 while(secondIter.hasNext()){
							 second=(Element)secondIter.next();
							 secondName=second.getName();
							 if(secondName.equals("partnerNo")){
								 pointsRes.setPartnerNo(second.getTextTrim());
							 }else if(secondName.equals("resultCode")){
								 pointsRes.setResultCode(second.getTextTrim());
							 }else if(secondName.equals("receiveReqTime")){
								 pointsRes.setReceiveReqTime(second.getTextTrim());
							 }else if(secondName.equals("signType")){
								 pointsRes.setSignType(second.getTextTrim());
							 }else if(secondName.equals("signMsg")){
								 pointsRes.setSignMsg(second.getTextTrim());
							 }else if(secondName.equals("charset")){
								 pointsRes.setCharset(second.getTextTrim());
							 }
						 }
					 }else{
						 logger.error("=============未知元素=============");
					 }
				 }
				 Map<String,String> signMap = new TreeMap<String,String>();
				 signMap.put("partnerNo", pointsRes.getPartnerNo()==null?"":pointsRes.getPartnerNo());
				 signMap.put("accountMarked", pointsRes.getAccountMarked()==null?"":pointsRes.getAccountMarked());
				 signMap.put("objectNo", pointsRes.getObjectNo()==null?"":pointsRes.getObjectNo());
				 signMap.put("accountId", pointsRes.getAccountId()==null?"":pointsRes.getAccountId());
				 signMap.put("points", pointsRes.getPoints()==null?"":pointsRes.getPoints());
				 
				 PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY_POINTS);//线上公钥
				 boolean flag = RsaUtil.verifyPublicKey(signMap.toString(),pointsRes.getSignMsg(),pk);
				 logger.info("校验结果" + flag);				 
				 if (flag){
					 if(PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())){
						 logger.info("积分消费成功！");
						 return pointsRes;
					 }else{
						 logger.error("积分消费失败，结果码是："+pointsRes.getResultCode());
						 logger.info("积分消费响应报文： "+responseXML);
						 return pointsRes;
					 }
				 }else{
					 logger.error("积分消费的响应报文校验不通过，报文内容如下："+responseXML);
					 throw new AppException("积分消费的响应报文校验不通过");
				 }
			 } else {
				 logger.error("积分消费请求发送失败，HTTP响应信息："+response.getStatusLine());
				 throw new AppException("HTTP通信失败，HTTP状态码："+response.getStatusLine().getStatusCode());
			 }			 
		 } catch (Exception e) {
			 logger.error("消费积分时出现异常！请求报文："+requestXML,e);
			 logger.error("响应报文："+responseXML);
			 throw new AppException(e);
		 } finally {
			 httpClient.getConnectionManager().shutdown();
		 }
	}

	
	/**
	  * 方法描述：积分退还接口
	  * @param: Points(payPointsNo,objectNo,accountId,points,accountMarked)
	  * @return: Points 积分退还成功时返回Points，否则返回null
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 7, 2013 4:14:33 PM
	  */
	public static Points refundPoints(Points point)throws AppException{
		 String requestTime = dateFormat.format(new Date());
		 String partnerNO = TranCommand.PARTNER_ID;
		 String accountMarkedType =PointCommand.ACCOUNT_MARKED_TYPE;
		 String objectType = PointCommand.OBJECT_TYPE_CONSUME;
		 String payPointsNo = point.getPayPointsNo()==null?"":point.getPayPointsNo();
		 String objectNo = point.getObjectNo()==null?"":"fbu"+point.getObjectNo();
		 String objectDes ="退还积分的订单号为："+objectNo;
		 String accountId = point.getAccountId()==null?"":point.getAccountId();
		 String points = point.getPoints()==null?"":point.getPoints();
		 String accountMarked = point.getAccountMarked()==null?"":point.getAccountMarked();
		 
		 logger.info("会员标识:" + accountMarked + " 退积分的标识：payPointsNo " + payPointsNo);
		 Map<String,String> map = new TreeMap<String,String>();
		 map.put("objectNo", objectNo);
		 map.put("accountId", accountId);
		 map.put("partnerNo", partnerNO);

		 HttpClient httpClient = new DefaultHttpClient();
		 HttpPost post=new HttpPost(SysCommand.REFUND_POINTS_URL);
		 Points pointsRes=new Points();
		 String requestXML=null;
		 StringBuffer buffer=new StringBuffer();
		 try {
			 PrivateKey prikey = RsaUtil.initPrivateKey(TranCommand.RSA_PRIVATE_KEY, PayCommand.RSAPWD);//线上密钥地址
			 String signMsg=RsaUtil.signPrivateKey(map.toString(), prikey);			
			 
			 String2Xml bodyXml = new String2Xml("requestRefundPointsApi");
			 bodyXml.addElementName("partnerAccount");
			 bodyXml.append("accountMarked", accountMarked);
			 bodyXml.append("accountMarkedType", accountMarkedType);
			 bodyXml.addElementName("refundPoints");
			 bodyXml.append("payPointsNo", payPointsNo);
			 bodyXml.append("objectNo", objectNo);
			 bodyXml.append("objectDes", objectDes);
			 bodyXml.append("objectType", objectType);
			 bodyXml.append("businessType", objectNo);
			 bodyXml.append("remark", "分分通退积分");
			 bodyXml.append("accountId", accountId);
			 bodyXml.append("points", points);
			 bodyXml.addElementName("system");
			 bodyXml.append("partnerNo", partnerNO);
			 bodyXml.append("charset", PayCommand.CHARSET_UTF8);
			 bodyXml.append("signType", PayCommand.SIGNTYPE_RSA);
			 bodyXml.append("signMsg", signMsg);
			 bodyXml.append("requestTime", requestTime);
			 requestXML = bodyXml.toXMLString();

			 post.setEntity(new StringEntity(requestXML,"UTF-8"));
			 long startTime = System.currentTimeMillis();
			 HttpResponse response=httpClient.execute(post);
			 logger.info("发送退会员积分请求,连接用时"+(System.currentTimeMillis()-startTime) + "ms");
			 if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				 BufferedReader br = new BufferedReader(new InputStreamReader(
				response.getEntity().getContent(), "UTF-8"));
				 String line =null;
				 while ((line = br.readLine()) != null) {
					 buffer.append(line);
				 }
				 Document doc=DocumentHelper.parseText(buffer.toString());				
				 Element root = doc.getRootElement();
				 Iterator<?> firstIter = root.elementIterator();
				 Element element=null;
				 String name=null;
				 Iterator<?> secondIter=null;
				 Element second=null;
				 String secondName=null;
				 while(firstIter.hasNext()) {
					 element=(Element)firstIter.next();
					 name=element.getName();
					 secondIter=element.elementIterator();
					 if(name.equals("partnerAccount"))	{	
						 while(secondIter.hasNext()) {
							 second=(Element)secondIter.next();
							 secondName=second.getName();
							 if (secondName.equals("accountMarked")) {
								 pointsRes.setAccountMarked(second.getTextTrim());				
							 }else if (secondName.equals("accountMarkedType")) {
								 pointsRes.setAccountMarkedType(second.getTextTrim());				
							 }				
						 }
					 }else if(name.equals("refundPoints"))	{	
						 while(secondIter.hasNext()) {
							 second=(Element)secondIter.next();
							 secondName=second.getName();
							 if (secondName.equals("payPointsNo")) {
								 pointsRes.setPayPointsNo(second.getTextTrim());				
							 }else if (secondName.equals("refundPointsNo")) {
								 pointsRes.setRefundPointsNo(second.getTextTrim());				
							 }else if (secondName.equals("objectNo")) {
								 pointsRes.setObjectNo(second.getTextTrim());				
							 }else if (secondName.equals("objectDes")) {
								 pointsRes.setObjectDes(second.getTextTrim());				
							 }else if (secondName.equals("objectType")) {
								 pointsRes.setObjectType(second.getTextTrim());				
							 }else if (secondName.equals("accountId")) {
								 pointsRes.setAccountId(second.getTextTrim());				
							 }else if (secondName.equals("points")) {
								 pointsRes.setPoints(second.getTextTrim());				
							 }	
						 }
					 }else if(name.equals("system")){
						 while(secondIter.hasNext()) {
							 second=(Element)secondIter.next();
							 secondName=second.getName();
							 if (secondName.equals("resultCode")) {
								 pointsRes.setResultCode(second.getTextTrim());				
							 }else if (secondName.equals("partnerNo")) {
								 pointsRes.setPartnerNo(second.getTextTrim());				
							 }else if (secondName.equals("charset")) {
								 pointsRes.setCharset(second.getTextTrim());				
							 }else if (secondName.equals("signType")) {
								 pointsRes.setSignType(second.getTextTrim());				
							 }else if (secondName.equals("signMsg")) {
								 pointsRes.setSignMsg(second.getTextTrim());				
							 }else if (secondName.equals("receiveReqTime")) {
								 pointsRes.setReceiveReqTime(second.getTextTrim());				
							 }	
						 }
					 }
				 }		
				 Map<String,String> signMap = new TreeMap<String,String>();
				 signMap.put("accountMarked", pointsRes.getAccountMarked()==null?"":pointsRes.getAccountMarked());
				 signMap.put("objectNo", pointsRes.getObjectNo()==null?"":pointsRes.getObjectNo());
				 signMap.put("accountId", pointsRes.getAccountId()==null?"":pointsRes.getAccountId());
				 signMap.put("points", pointsRes.getPoints()==null?"":pointsRes.getPoints());
				 signMap.put("partnerNo", pointsRes.getPartnerNo()==null?"":pointsRes.getPartnerNo());
				
				 PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY_POINTS);//线上公钥
				 boolean flag = RsaUtil.verifyPublicKey(signMap.toString(),pointsRes.getSignMsg(),pk);
				 logger.info("校验结果" + flag);				 
				 if (flag){
					 if(PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())){
						 logger.info("======积分退还成功！======");
						 return pointsRes;
					 }else{
						 
						 logger.error("积分退还失败，结果码："+pointsRes.getResultCode()+
								"请求报文："+requestXML+ "响应报文："+buffer.toString());
						 return pointsRes;
					 }
				 }else{
					 logger.error("退积分的响应报文校验不通过，报文内容："+buffer.toString());
					 throw new AppException("退积分的响应报文校验不通过");
				 }
			 }else{
				logger.error("退积分请求失败，HTTP响应信息："+response.getStatusLine());
				throw new AppException("退积分请求失败，HTTP响应信息："+response.getStatusLine());
			 }			 
		 } catch(Exception e){
			 logger.error("退积分时出现异常！请求报文："+requestXML,e);
			 logger.error("响应报文："+buffer.toString());
			 throw new AppException(e);
		 }finally {
			 httpClient.getConnectionManager().shutdown();
		 }
	}

	
	/**
	  * 方法描述：返利积分
	  * @param: Rebate(points,objectNo,accountMarkedFrom,
	  *                accountMarkedTo,fromPhone,toPhone)
	  * @return: Rebate
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 14, 2013 5:39:36 PM
	  */
	public static Rebate rebatePoints(Rebate rebateBean)throws Exception{
		/**********组装请求报文**********/
		String requestNo="fbu"+DateUtil.currentTimeToString();
		String requestTime=dateFormat.format(new Date());
		
		Map<String,String> map = new TreeMap<String,String>();
		map.put("orgNo", TranCommand.FFT_ORG_NO);
		map.put("pointsCateNo", PointCommand.POINTS_CATE_NO);
		map.put("orgPoints",rebateBean.getPoints());
		map.put("partnerNo", TranCommand.PARTNER_ID);
		map.put("requestNo", requestNo);
		
		PrivateKey prikey = RsaUtil.initPrivateKey(TranCommand.RSA_PRIVATE_KEY,PayCommand.RSAPWD);
		String signMsg=RsaUtil.signPrivateKey(map.toString(), prikey);
		
		String requestXML="<requestFillAndPresendApi>"+
			"<partnerAccountFrom>"+
			"	<accountMarked>"+rebateBean.getAccountMarkedFrom()+"</accountMarked>"+
			"	<accountMarkedType>"+PointCommand.ACCOUNT_MARKED_TYPE+"</accountMarkedType>"+
			"</partnerAccountFrom>"+
			"<partnerAccountTo>"+
			"	<accountMarked>"+rebateBean.getAccountMarkedTo()+"</accountMarked>"+
			"	<accountMarkedType>"+PointCommand.ACCOUNT_MARKED_TYPE+"</accountMarkedType>"+
			"</partnerAccountTo>"+
			"<fillAndPresendPointsInfo>"+
			"	<orgNo>"+TranCommand.FFT_ORG_NO+"</orgNo>"+
			"	<pointsCateNo>"+PointCommand.POINTS_CATE_NO+"</pointsCateNo>"+
			"	<orgPoints>"+rebateBean.getPoints()+"</orgPoints>"+
			"   <objectNo>"+rebateBean.getObjectNo()+"</objectNo>"+
			"   <objectType>"+PointCommand.OBJECT_TYPE_CONSUME+"</objectType>"+
			"</fillAndPresendPointsInfo>"+
			"<orgMemberFrom>"+
			"	<phone>"+rebateBean.getFromPhone()+"</phone>"+
			"</orgMemberFrom>"+
			"<orgMemberTo>"+
			"	<phone>"+rebateBean.getToPhone()+"</phone>"+
			"</orgMemberTo>"+
			"<system>"+
			"	<partnerNo>"+TranCommand.PARTNER_ID+"</partnerNo>"+
			"	<requestNo>"+requestNo+"</requestNo>"+
			"	<charset>"+PayCommand.CHARSET+"</charset>"+
			"	<signType>"+PayCommand.SIGNTYPE_RSA+"</signType>"+
			"	<signMsg>"+signMsg+"</signMsg>"+
			"	<requestTime>"+requestTime+"</requestTime>"+
			"</system>"+
			"</requestFillAndPresendApi>";
		requestXML=requestXML.replaceAll(">null<", "><");
		logger.info("请求报文如下：\n"+requestXML);
		/**********发httpclient请求**********/
		HttpClient httpClient=new DefaultHttpClient();
		HttpPost post=new HttpPost(SysCommand.REBATE_POINTS_URL);
		StringEntity param=new StringEntity(requestXML,PayCommand.CHARSET);
		post.setEntity(param);
		String line=null;
		StringBuffer buffer=new StringBuffer();
		try {
			HttpResponse response=httpClient.execute(post);
			HttpEntity entity=response.getEntity();
			InputStream inputStream=entity.getContent();
			BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,PayCommand.CHARSET));
			while((line=reader.readLine())!=null){
				buffer.append(line);
			}
			reader.close();
			inputStream.close();
		}catch (Exception e) {
			throw e;
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		System.out.println("响应报文如下：\n"+buffer.toString());
		/**********解析响应报文**********/
		Element firstNode=null,childNode=null;
		String name=null,childName=null;
		Iterator<?> childIter=null;
		Rebate rebate=new Rebate();//响应实体
		Document document=DocumentHelper.parseText(buffer.toString());
		Element root=document.getRootElement();
		Iterator<?> firstIter=root.elementIterator();
		while(firstIter.hasNext()){
			firstNode=(Element)firstIter.next();
			name=firstNode.getName();
			childIter=firstNode.elementIterator();
			if("partnerAccountFrom".equals(name)){
				while(childIter.hasNext()){
					childNode=(Element)childIter.next();
					childName=childNode.getName();
					if("accountMarked".equals(childName)){
						rebate.setAccountMarkedFrom(childNode.getTextTrim());
					}else if("accountMarkedType".equals(childName)){
						rebate.setAccountMarkedTypeFrom(childNode.getTextTrim());
					}
				}
			}else if("partnerAccountTo".equals(name)){
				while(childIter.hasNext()){
					childNode=(Element)childIter.next();
					childName=childNode.getName();
					if("accountMarked".equals(childName)){
						rebate.setAccountMarkedTo(childNode.getTextTrim());
					}else if("accountMarkedType".equals(childName)){
						rebate.setAccountMarkedTypeTo(childNode.getTextTrim());
					}
				}
			}else if("fillAndPresendPointsInfo".equals(name)){
				while(childIter.hasNext()){
					childNode=(Element)childIter.next();
					childName=childNode.getName();
					if("fillAndPresendInfoNo".equals(childName)){
						rebate.setFillAndPresendInfoNo(childNode.getTextTrim());
					}else if("objectNo".equals(childName)){
						rebate.setObjectNo(childNode.getTextTrim());
					}else if("orgNo".equals(childName)){
						rebate.setOrgNo(childNode.getTextTrim());
					}else if("pointsCateNo".equals(childName)){
						rebate.setPointsCateNo(childNode.getTextTrim());
					}else if("orgPoints".equals(childName)){
						rebate.setOrgPoints(childNode.getTextTrim());
					}else if("points".equals(childName)){
						rebate.setPoints(childNode.getTextTrim());
					}else if("fromAccountId".equals(childName)){
						rebate.setFromAccountId(childNode.getTextTrim());
					}else if("toAccountId".equals(childName)){
						rebate.setToAccountId(childNode.getTextTrim());
					}
				}
			}else if("system".equals(name)){
				while(childIter.hasNext()){
					childNode=(Element)childIter.next();
					childName=childNode.getName();
					if("partnerNo".equals(childName)){
						rebate.setPartnerNo(childNode.getTextTrim());
					}else if("requestNo".equals(childName)){
						rebate.setRequestNo(childNode.getTextTrim());
					}else if("resultCode".equals(childName)){
						rebate.setResultCode(childNode.getTextTrim());
					}else if("charset".equals(childName)){
						rebate.setCharset(childNode.getTextTrim());
					}else if("signType".equals(childName)){
						rebate.setSignType(childNode.getTextTrim());
					}else if("signMsg".equals(childName)){
						rebate.setSignMsg(childNode.getTextTrim());
					}else if("receiveReqTime".equals(childName)){
						rebate.setReceiveReqTime(childNode.getTextTrim());
					}
				}
			}
		}
		/******校验响应报文******/
		Map<String,String> signMap = new TreeMap<String,String>();
		signMap.put("accountMarked", rebate.getAccountMarkedTo());
		signMap.put("points", rebate.getPoints());
		signMap.put("partnerNo",rebate.getPartnerNo());
		signMap.put("resultCode", rebate.getResultCode());

		PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY_POINTS);
		boolean flag=RsaUtil.verifyPublicKey(signMap.toString(),rebate.getSignMsg(),pk);
		logger.info("响应报文校验结果:" + flag);
		/**********验签通过,后处理**********/
		if(flag){
			if(Command.RESP_CODE_SUCCESS.equals(rebate.getResultCode())){//积分增送成功
				logger.info("积分增送成功......");
				return rebate;
			}else{
				logger.error("积分增送失败，结果码："+rebate.getResultCode());
				logger.error("请求报文： "+requestXML);
				logger.error("响应报文："+buffer.toString());
				return rebate;
			}
		}else{
			logger.error("赠送积分的响应报文校验不通过");
			logger.error("请求报文： "+requestXML);
			logger.error("响应报文："+buffer.toString());
			throw new AppException("赠送积分的响应报文校验不通过");
		}

	}
	
	
	/**
	  * 方法描述：增送积分
	  * @param: Points(orgNo,objectNo,objectDes,objectType,
	  *                point,accountMarked,accountMarkedType)
	  * @return: Points(resultCode为0000表示赠送成功，否则失败)
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 6, 2013 4:09:24 PM
	  */
	public static Points presentPoints(Points points) throws AppException {
		String requestTime = dateFormat.format(new Date());
		 String partnerNO = TranCommand.PARTNER_ID;
		 
		 String orgNo = points.getOrgNo();
		 String objectNo = points.getObjectNo();
		 String objectDes = points.getObjectDes();
		 String objectType = points.getObjectType();
		 String point = points.getPoints();
		 String accountMarked = points.getAccountMarked();
		 String accountMarkedType = points.getAccountMarkedType();

		 logger.info("会员:"+accountMarked+" 积分机构(orgNo): " +orgNo+"赠送的积分额："+point);
		 
		 Map<String,String> map = new TreeMap<String,String>();
		 map.put("orgNo", orgNo);
		 map.put("points", point);
		 map.put("accountMarked", accountMarked);
		 map.put("partnerNo", partnerNO);
		 HttpClient httpClient = new DefaultHttpClient();
		 HttpPost post = new HttpPost(SysCommand.PRESENT_POINTS_URL);
		 try {
			 PrivateKey prikey = RsaUtil.initPrivateKey(TranCommand.RSA_PRIVATE_KEY, PayCommand.RSAPWD);//测试密钥地址
			 String signMsg=RsaUtil.signPrivateKey(map.toString(), prikey);			
			 
			 String2Xml bodyXml = new String2Xml("requestPresentPointsApi");
			 bodyXml.addElementName("presentPointsInfo");
			 bodyXml.append("orgNo", orgNo);
			 bodyXml.append("points", point);
			 bodyXml.append("objectNo", objectNo);
			 bodyXml.append("objectDes", objectDes);
			 bodyXml.append("objectType", objectType);
			 bodyXml.append("businessType", objectNo);
			 bodyXml.append("remark", "分分通积分增送");
			 bodyXml.addElementName("partnerAccount");
			 bodyXml.append("accountMarked", accountMarked);
			 bodyXml.append("accountMarkedType", accountMarkedType);
			 bodyXml.addElementName("system");
			 bodyXml.append("requestNo", getRandom());
			 bodyXml.append("partnerNo", partnerNO);
			 bodyXml.append("charset", PayCommand.CHARSET_UTF8);
			 bodyXml.append("signType", PayCommand.SIGNTYPE_RSA);
			 bodyXml.append("signMsg", signMsg);
			 bodyXml.append("requestTime", requestTime);
			 String requestXML = bodyXml.toXMLString();
			 requestXML=requestXML.replaceAll(">null<", "><");
			 logger.info("请求体:" + requestXML);
			 post.setEntity(new StringEntity(requestXML,"UTF-8"));
			 long startTime = System.currentTimeMillis();
			 HttpResponse response=httpClient.execute(post);
			 long endTime = System.currentTimeMillis();
			 logger.info("发送赠送会员积分请求,连接用时" + (endTime - startTime) + "ms");
			 if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				 BufferedReader reader = new BufferedReader(new InputStreamReader(
				 response.getEntity().getContent(),"UTF-8"));
				 String line=null;
				 StringBuffer buffer = new StringBuffer();
				 while ((line = reader.readLine()) != null) {
					 buffer.append(line);
				 }
				 logger.info("响应报文:" + buffer.toString());
				 Document doc = DocumentHelper.parseText(buffer.toString());			
				 Element root = doc.getRootElement();
				 Iterator<?> firstIter=root.elementIterator();
				 Element element=null;
				 String name=null;
				 Points pointsRes=new Points();
				 while(firstIter.hasNext()){
					 element=(Element)firstIter.next();	
					 name=element.getName();
					 if(name.equals("partnerAccount")){	
						 Iterator<?> childIter=element.elementIterator();
						 Element child=null;
						 String childName=null;
						 while(childIter.hasNext()){
							child=(Element)childIter.next();
							childName=child.getName();
							 if(childName.equals("accountMarked")) {
								 pointsRes.setAccountMarked(child.getTextTrim());				
							 }else if (childName.equals("accountMarkedType")) {
								 pointsRes.setAccountMarkedType(child.getTextTrim());				
							 }				
						  }
					 }else if(name.equals("presentPointsInfo"))	{	
						 Iterator<?> childIter=element.elementIterator();
						 Element child=null;
						 String childName=null;
						 while(childIter.hasNext()){
							child=(Element)childIter.next();
							childName=child.getName();
							 if (childName.equals("presentPointsNo")) {
								 pointsRes.setPresentPointsNo(child.getTextTrim());				
							 }else if (childName.equals("objectNo")) {
								 pointsRes.setObjectNo(child.getTextTrim());				
							 }else if (childName.equals("orgNo")) {
								 pointsRes.setOrgNo(child.getTextTrim());				
							 }else if (childName.equals("accountId")) {
								 pointsRes.setAccountId(child.getTextTrim());				
							 }else if (childName.equals("points")) {
								 pointsRes.setPoints(child.getTextTrim());				
							 }	
						 }
					 }else if(name.equals("system")){
						 Iterator<?> childIter=element.elementIterator();
						 Element child=null;
						 String childName=null;
						 while(childIter.hasNext()){
							child=(Element)childIter.next();
							childName=child.getName();
							 if (childName.equals("resultCode")) {
								 pointsRes.setResultCode(child.getTextTrim());				
							 }else if (childName.equals("partnerNo")) {
								 pointsRes.setPartnerNo(child.getTextTrim());				
							 }else if (childName.equals("requestNo")) {
								 pointsRes.setRequestNo(child.getTextTrim());				
							 }else if (childName.equals("charset")) {
								 pointsRes.setCharset(child.getTextTrim());				
							 }else if (childName.equals("signType")) {
								 pointsRes.setSignType(child.getTextTrim());				
							 }else if (childName.equals("signMsg")) {
								 pointsRes.setSignMsg(child.getTextTrim());				
							 }else if (childName.equals("receiveReqTime")) {
								 pointsRes.setReceiveReqTime(child.getTextTrim());				
							 }	
						 }
					 }
				 }		
				 Map<String,String> signMap = new TreeMap<String,String>();
				 signMap.put("partnerNo", pointsRes.getPartnerNo()==null?"":pointsRes.getPartnerNo());
				 signMap.put("accountMarked", pointsRes.getAccountMarked()==null?"":pointsRes.getAccountMarked());
				 signMap.put("orgNo", pointsRes.getOrgNo()==null?"":pointsRes.getOrgNo());
				 signMap.put("resultCode", pointsRes.getResultCode()==null?"":pointsRes.getResultCode());
				 signMap.put("points", pointsRes.getPoints()==null?"":pointsRes.getPoints());
				 
				 PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY_POINTS);//线上公钥
				 boolean flag = RsaUtil.verifyPublicKey(signMap.toString(),pointsRes.getSignMsg(),pk);
				 logger.info("校验结果" + flag);
				 if(flag){
					 if(PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())){
						 logger.info("=========积分增送成功==========");
					 }else{
						 logger.error("积分增送失败，结果码为："+pointsRes.getResultCode());
					 }
					 return pointsRes;
				 }else{
					 logger.error("增送积分的响应报文校验不通过，报文如下：\n"+buffer.toString());
					 throw new AppException("增送积分的响应报文校验不通过");
				 }
			 } else {
				 //会员积分赠送请求失败
				 logger.error("HTTP请求发送失败，响应信息："+response.getStatusLine());
				 throw new AppException("HTTP请求发送失败，响应信息："+response.getStatusLine());
			 }			 
		 } catch (Exception e) {
			 logger.error("增送积分出现异常",e);
			 throw new AppException(e);
		 } finally {
			 httpClient.getConnectionManager().shutdown();
		 }
	}
	
	
	/**
	  * 方法描述：查询会员积分
	  * @param: Points(orgNo,accountMarked)
	  * @return: Points
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 5, 2013 5:55:26 PM
	  */
	public static Points queryPoints(Points points)throws AppException{
		Points pointsRes=new Points();
		String requestTime = dateFormat.format(new Date());
		String partnerNO = TranCommand.PARTNER_ID;
		String orgNo = points.getOrgNo();
		String accountMarkedType = PointCommand.ACCOUNT_MARKED_TYPE;
		String accountMarked = points.getAccountMarked();
		
		logger.info("查询积分的会员是:" + accountMarked);
		Map<String, String> map = new TreeMap<String, String>();
		map.put("accountMarked", accountMarked);
		map.put("accountMarkedType", accountMarkedType);
		map.put("partnerNo", partnerNO);

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(SysCommand.QUERY_POINTS_URL);
		try {
			PrivateKey prikey = RsaUtil.initPrivateKey(
					TranCommand.RSA_PRIVATE_KEY, PayCommand.RSAPWD);
			String signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);

			String2Xml bodyXml = new String2Xml("requestParAccountPointsApi");
			bodyXml.append("orgNo", orgNo);
			bodyXml.addElementName("partnerAccount");
			bodyXml.append("accountMarked", accountMarked);
			bodyXml.append("accountMarkedType", accountMarkedType);
			bodyXml.addElementName("system");
			bodyXml.append("partnerNo", partnerNO);
			bodyXml.append("charset", PayCommand.CHARSET_UTF8);
			bodyXml.append("signType", PayCommand.SIGNTYPE_RSA);
			bodyXml.append("signMsg", signMsg);
			bodyXml.append("requestTime", requestTime);
			String requestXML = bodyXml.toXMLString();
			requestXML=requestXML.replaceAll(">null<", "><");
			logger.info("请求报文:" + requestXML);
			StringEntity entity = new StringEntity(requestXML, "UTF-8");
			post.setEntity(entity);
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			long endTime = System.currentTimeMillis();
			logger.info("发送查询会员积分请求,连接用时" + (endTime - startTime) + "ms，响应信息："+response.getStatusLine());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent(), "UTF-8"));
				String line = null;
				StringBuffer buffer = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				reader.close();
				logger.info("查询积分的响应报文:" + buffer.toString());
				Document doc = DocumentHelper.parseText(buffer.toString());
				Element root = doc.getRootElement();
				Iterator<?> firstIter=root.elementIterator();
				Element element=null;
				String name=null;
				while(firstIter.hasNext()){
					element=(Element)firstIter.next();
					name=element.getName();
					if(name.equals("orgNo")){
						pointsRes.setOrgNo(element.getTextTrim());
					}else if(name.equals("partnerAccount")){
						Iterator<?> childIter=element.elementIterator();
						Element child=null;
						String childName=null;
						while(childIter.hasNext()){
							child=(Element)childIter.next();
							childName=child.getName();
							if(childName.equals("accountMarked")){
								pointsRes.setAccountMarked(child.getTextTrim());
							}else if(childName.equals("accountMarkedType")){
								pointsRes.setAccountMarkedType(child.getTextTrim());
							}
						}
					}else if(name.equals("accountPointsInfos")){
						Iterator<?> childIter=element.elementIterator();
						Element child=null;
						String childName=null;
						List<PointsAccount> accountList=new ArrayList<PointsAccount>();
						while(childIter.hasNext()){
							child=(Element)childIter.next();
							childName=child.getName();
							if(childName.equals("accountPointsInfo")){
								PointsAccount account=new PointsAccount();
								Iterator<?> thirdIter=child.elementIterator();
								Element third=null;
								String thirdName=null;
								while(thirdIter.hasNext()){
									third=(Element)thirdIter.next();
									thirdName=third.getName();
									if(thirdName.equals("accountId")){
										account.setAccountId(third.getTextTrim());
									}else if(thirdName.equals("orgNo")){
										account.setOrgNo(third.getTextTrim());
									}else if(thirdName.equals("orgName")){
										account.setOrgName(third.getTextTrim());
									}else if(thirdName.equals("points")){
										account.setPoints(third.getTextTrim());
									}else if(thirdName.equals("frozenPoints")){
										account.setFrozenPoints(third.getTextTrim());
									}
								}
								accountList.add(account);
							}
						}
						pointsRes.setPointsAccountList(accountList);
					}else if(name.equals("system")){
						Iterator<?> childIter=element.elementIterator();
						Element child=null;
						String childName=null;
						while(childIter.hasNext()){
							child=(Element)childIter.next();
							childName=child.getName();
							if(childName.equals("partnerNo")){
								pointsRes.setPartnerNo(child.getTextTrim());
							}else if(childName.equals("resultCode")){
								pointsRes.setResultCode(child.getTextTrim());
							}else if(childName.equals("receiveReqTime")){
								pointsRes.setReceiveReqTime(child.getTextTrim());
							}else if(childName.equals("signType")){
								pointsRes.setSignType(child.getTextTrim());
							}else if(childName.equals("signMsg")){
								pointsRes.setSignMsg(child.getTextTrim());
							}else if(childName.equals("charset")){
								pointsRes.setCharset(child.getTextTrim());
							}
						}
					}else{
						logger.error("=========未知元素=========");
					}
				}
				
				
				 Map<String,String> signMap = new TreeMap<String,String>();
				 signMap.put("accountMarked",
				 pointsRes.getAccountMarked()==null?"":pointsRes.getAccountMarked());
				 signMap.put("partnerNo", pointsRes.getPartnerNo()==null?"":pointsRes.getPartnerNo());
								
				 PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY_POINTS);
				 boolean flag =
				 RsaUtil.verifyPublicKey(signMap.toString(),pointsRes.getSignMsg(),pk);
				 logger.info("响应报文校验结果：" + flag);
				 if(flag){
					 if(PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())){
						 logger.info("===========积分查询响应成功==========");
						 if(pointsRes.getPointsAccountList()==null||
								 pointsRes.getPointsAccountList().size()==0){
							 logger.error("该会员没有积分账户，会员标识为： "+accountMarked);
							 pointsRes.setResultCode(PointCommand.RESP_CODE_NO_EXIST);
							 pointsRes.setRespMsg("该会员没有积分账户");
						 }
						 logger.info("积分账户size=="+pointsRes.getPointsAccountList().size());
						 return pointsRes;
					 }else{
						 logger.error("积分查询失败，结果码为："+pointsRes.getResultCode());
						 return pointsRes;
					 }
				 }else{
					 logger.error("积分查询的响应报文校验不通过，报文内容如下：\n"+buffer.toString());
					 throw new AppException("积分查询的响应报文校验不通过");
				 }
			}else{
				logger.error("积分平台响应异常！响应信息： "+response.getStatusLine());
				throw new AppException("积分平台响应异常");
			}
		} catch (Exception e) {
			logger.error("查询会员积分时出现异常！会员标识："+accountMarked,e);
			throw new AppException(e);
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
	}

	
	
	/**
	  * 方法描述：积分提现
	  * @param: Points(orgNo,accountMarked,accountMarkedType,realName,
			bankId,bankName,bankCard,businessType,points,objectNo,certNo)
	  * @return: Points
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 4, 2014 10:14:10 AM
	  */
	public static Points withdrawPoints(Points pointsReq) throws AppException {
		logger.info("开始执行<积分提现申请>,交互系统:积分平台");
		Points pointsRes = new Points();
		
		String orgNo = pointsReq.getOrgNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = pointsReq.getAccountMarkedType();
		
		String realName = pointsReq.getRealName();
		String bankId = pointsReq.getBankId(); 
		String bankName = pointsReq.getBankName();
		String bankCard = pointsReq.getBankCard(); 
		String businessType = pointsReq.getBusinessType();
		String points = pointsReq.getPoints();
		String objectNo = pointsReq.getObjectNo();
		
		String partnerNO = TranCommand.PARTNER_ID;
		String noticeUrl = SysCommand.WITHDRAW_POINTS_NOTICE_URL;
		String objectDes = "用户申请积分提现";
		String objectType = PointCommand.OBJECT_TYPE_WITHDRAW;
		String certType=PointCommand.CERT_TYPE;
		String certNo=pointsReq.getCertNo();
		String remark = "用户申请积分提现";
		PrivateKey prikey = RsaUtil.initPrivateKey(TranCommand.RSA_PRIVATE_KEY, PayCommand.RSAPWD);//线上密钥地址
		Map<String, String> map=new TreeMap<String, String>();
		map.put("accountMarked", accountMarked);
		map.put("realName", realName);
		map.put("bankId", bankId);
		map.put("bankCard", bankCard);
		map.put("objectNo", objectNo);
		map.put("orgNo", orgNo);
		map.put("partnerNo", partnerNO);
		try {
			String signMsg=RsaUtil.signPrivateKey(map.toString(), prikey);
			
			Map<String, String> model = new HashMap<String,String>();
			model.put("accountMarked", accountMarked);
			model.put("accountMarkedType", accountMarkedType);
			model.put("orgNo",orgNo);
			
			model.put("realName",realName);
			model.put("bankId", bankId);
			model.put("bankName", bankName);
			model.put("bankCard",bankCard);
			model.put("certType", certType);
			model.put("certNo", certNo);
			model.put("businessType", businessType);
			model.put("noticeUrl", noticeUrl);
			model.put("points", points);		
			model.put("objectNo",objectNo);
			model.put("objectDes", objectDes);
			model.put("objectType", objectType);
			model.put("remark", remark);
			model.put("partnerNo", partnerNO);
			model.put("charset", PayCommand.CHARSET_UTF8);
			model.put("signType", PayCommand.SIGNTYPE_RSA);
			model.put("signMsg", signMsg);
			model.put("requestTime", DateUtil.formatDate2Str(new Date()));
			String prefix=PointsFunc.class.getPackage().getName().replaceAll("\\.", "/");
			String requestXML = FreemarkerUtils.processFromClassPath(prefix+"/withdraw_points.ftl", model);
			logger.info("积分提现请求报文：\n" + requestXML);
			// 发送请求
			String responseXML = null;
			try {
				responseXML = HttpClientUtils.doPost(SysCommand.WITHDRAW_POINTS_URL, requestXML);
			} catch (Exception e) {
				logger.error("发送提现申请时出现异常", e);
				pointsRes.setResultCode(TranCommand.EXCEPTION);
				pointsRes.setRespMsg("发送提现申请时出现异常");
				return pointsRes;
			}
			Document doc = DocumentHelper.parseText(responseXML);
			Element root = doc.getRootElement();
			Iterator<?> firstIter=root.elementIterator();
			Element element=null;
			String name=null;
			while(firstIter.hasNext()){
				element=(Element)firstIter.next();
				name=element.getName();
				if(name.equals("partnerAccount")){
					Iterator<?> childIter=element.elementIterator();
					Element child=null;
					String childName=null;
					while(childIter.hasNext()){
						child=(Element)childIter.next();
						childName=child.getName();
						if(childName.equals("accountMarked")){
							pointsRes.setAccountMarked(child.getTextTrim());
						}else if(childName.equals("accountMarkedType")){
							pointsRes.setAccountMarkedType(child.getTextTrim());
						}
					}
				}else if(name.equals("cashPointsInfo")){
					Iterator<?> childIter=element.elementIterator();
					Element child=null;
					String childName=null;
					WithdrawPoints withdraw=new WithdrawPoints();
					while(childIter.hasNext()){
						child=(Element)childIter.next();
						childName=child.getName();
						if(childName.equals("cashPointsNo")){
							withdraw.setCashPointsNo(child.getTextTrim());
						}else if(childName.equals("objectNo")){
							withdraw.setObjectNo(child.getTextTrim());
						}else if(childName.equals("objectDes")){
							withdraw.setObjectDes(child.getTextTrim());
						}else if(childName.equals("objectType")){
							withdraw.setObjectType(child.getTextTrim());
						}else if(childName.equals("points")){
							withdraw.setPoints(child.getTextTrim());
						}
					}
					pointsRes.setWithdrawPoints(withdraw);
				}else if(name.equals("system")){
					Iterator<?> childIter=element.elementIterator();
					Element child=null;
					String childName=null;
					while(childIter.hasNext()){
						child=(Element)childIter.next();
						childName=child.getName();
						if(childName.equals("partnerNo")){
							pointsRes.setPartnerNo(child.getTextTrim());
						}else if(childName.equals("resultCode")){
							pointsRes.setResultCode(child.getTextTrim());
						}else if(childName.equals("receiveReqTime")){
							pointsRes.setReceiveReqTime(child.getTextTrim());
						}else if(childName.equals("signType")){
							pointsRes.setSignType(child.getTextTrim());
						}else if(childName.equals("signMsg")){
							pointsRes.setSignMsg(child.getTextTrim());
						}else if(childName.equals("charset")){
							pointsRes.setCharset(child.getTextTrim());
						}
					}
				}else{
					logger.error("=========未知元素=========");
				}
			}
			
			 WithdrawPoints withdraw=pointsRes.getWithdrawPoints();
			 if(withdraw==null){
				 logger.error("积分提现响应报文验签不通过，响应报文："+responseXML);
				 pointsRes.setResultCode(TranCommand.INVALID_RES_XML);
				 pointsRes.setRespMsg("响应报文验签不通过");
				 return pointsRes;
			 }
			 Map<String,String> signMap = new TreeMap<String,String>();
			 signMap.put("partnerNo", pointsRes.getPartnerNo()==null?"":pointsRes.getPartnerNo());
			 signMap.put("accountMarked", pointsRes.getAccountMarked()==null?"":pointsRes.getAccountMarked());
			 signMap.put("objectNo", withdraw.getObjectNo()==null?"":withdraw.getObjectNo());
			 signMap.put("points", withdraw.getPoints()==null?"":withdraw.getPoints());
							
			 PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY_POINTS);
			 boolean flag=RsaUtil.verifyPublicKey(signMap.toString(),pointsRes.getSignMsg(),pk);
			 logger.info("积分提现响应报文校验结果：" + flag);
			
			if(flag){
				String resultCode =pointsRes.getResultCode();
				if(Command.RESP_CODE_SUCCESS.equals(resultCode)){
					pointsRes.setResultCode(resultCode);
					pointsRes.setRespMsg("提现申请成功");
				}else{
					pointsRes.setResultCode(resultCode);
					pointsRes.setRespMsg("提现申请失败");
				}
				return pointsRes;
			}else{
				logger.error("积分提现响应报文验签不通过，响应报文："+responseXML);
				pointsRes.setResultCode(TranCommand.INVALID_RES_XML);
				pointsRes.setRespMsg("响应报文验签不通过");
				return pointsRes;
			}
		} catch (Exception e) {
			logger.error("积分提现异常",e);
			throw new AppException(e);
		}
		
	}
	
	
}
