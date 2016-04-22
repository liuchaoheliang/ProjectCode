package com.froad.CB.thirdparty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.froad.CB.AppException.AppException;
import com.froad.CB.common.Command;
import com.froad.CB.common.SysCommand;
import com.froad.CB.common.constant.FroadMallCommand;
import com.froad.CB.common.constant.LotteryCommand;
import com.froad.CB.po.FroadMallXMLSystem;
import com.froad.CB.po.lottery.Ct;
import com.froad.CB.po.lottery.Lottery;
import com.froad.CB.po.lottery.Period;
import com.froad.util.MD5;
import com.froad.util.String2Xml;

public class LotteryFunc {
	private static final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger logger=Logger.getLogger(LotteryFunc.class);
	
	

	
	/**
	  * 方法描述：查询当前期数
	  * @param: 选填Lottery（lotteryNo）
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	  * @time: Feb 19, 2013 3:57:53 PM
	  */
	public static List<Lottery> queryPeridListNow(Lottery lottery) throws AppException{
		List<Lottery> listRes = new ArrayList<Lottery>();
		HttpPost post=new HttpPost(SysCommand.FROADMALL_URL);
		HttpClient httpClient = new DefaultHttpClient();
		StringBuffer buffer=new StringBuffer();
		String requestXML =null;
		String responseXML =null;
		MD5 md5 = new MD5(); 
		String2Xml bodyXml = new String2Xml("FroadMallApiRequest");
		bodyXml.addElementName("system");
		bodyXml.append("version",FroadMallCommand.VERSION);
		bodyXml.append("merchantNo", FroadMallCommand.MERCHANTNO);
		bodyXml.append("merchantPwd",md5.getMD5ofStr(FroadMallCommand.MERCHANTPWD));
		bodyXml.append("signType", "1");
		bodyXml.append("signMsg",md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD));
		bodyXml.append("requestTime",dateFormat.format(new Date()));
		bodyXml.append("busiCode",LotteryCommand.APICODE_QUERY_PERIDLISTNOW);
		bodyXml.addElementName("body");
		bodyXml.append("lotteryNo",lottery.getLotteryNo());
		requestXML = bodyXml.toXMLString();
		logger.info("查询当前期数，请求报文："+requestXML);
		StringEntity entity;
		try {
			entity = new StringEntity(requestXML,"UTF-8");
			post.setEntity(entity);
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			logger.info("发送查询当前期数请求,连接用时"+(System.currentTimeMillis()-startTime) + "ms");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				 BufferedReader reader = new BufferedReader(new InputStreamReader(
				 response.getEntity().getContent(), "UTF-8"));
				 String line=null;
				 while ((line = reader.readLine()) != null) {
					 buffer.append(line);
				 }
				 reader.close();
				 responseXML = buffer.toString();
				 logger.info("查询当前期数,响应报文："+ responseXML);
				 Document document=DocumentHelper.parseText(responseXML);
				 List<?> systemList=document.selectNodes("/FroadMallApiResponse/system/*");
				 List<?> bodyList=document.selectNodes("/FroadMallApiResponse/body/lotteryList/*");
				 if(systemList==null){
					 logger.error("查询当前期数，解析报文异常,响应报文："+ responseXML);
					 throw new AppException("查询当前期数，解析报文异常,响应报文："+ responseXML);
				 }
				 Element element=null;
				 String elementName=null;
				 FroadMallXMLSystem systemXml = new FroadMallXMLSystem();
				 for(int i=0;i<systemList.size();i++){
					 element = (Element)systemList.get(i);
					 elementName = element.getName();
					 if("merchantNo".equals(elementName)){
					    systemXml.setMerchantNo(element.getTextTrim());
					 }else if("signType".equals(elementName)){
						systemXml.setSignType(element.getTextTrim());
					 }else if("signMsg".equals(elementName)){
						systemXml.setSignMsg(element.getTextTrim());
					 }else if("busiCode".equals(elementName)){
						systemXml.setBusiCode(element.getText());
					 }else if("version".equals(elementName)){
						systemXml.setVersion(element.getText());
					 }else if("responseTime".equals(elementName)){
						systemXml.setRequestTime(element.getText());
					 }else if("respCode".equals(elementName)){
						systemXml.setRespCode(element.getText());
					 }else if("respMsg".equals(elementName)){
						systemXml.setRespMsg(element.getText());
					 }
				 }
				 if("0000".equals(systemXml.getRespCode())&&bodyList!=null){
					 Element element1=null;
					 String elementName1=null;
					 for(int j=0;j<bodyList.size();j++){
						 element1 = (Element)bodyList.get(j);
						 elementName1 = element1.getName();
						 if("lottery".equals(elementName1)){
							Lottery lotteryRes = new Lottery();
							for (Iterator<?> it2 = element1.elementIterator(); it2.hasNext();) {
								 Element element2 = (Element) it2.next();
								 String element2Name = element2.getName();
								 if("period".equals(element2Name)){
									 lotteryRes.setPeriod(element2.getTextTrim());
								 }else if("endTime".equals(element2Name)){
									 lotteryRes.setEndTime(element2.getTextTrim());
								 }else if("lotteryNo".equals(element2Name)){
									 lotteryRes.setLotteryNo(element2.getTextTrim());
								 }
							}
							listRes.add(lotteryRes); 
						 }
					 }
					 String signMsgRes = systemXml.getSignMsg();
					 String signMsgVa = md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD);
					 if(!signMsgRes.equals(signMsgVa)){
						 logger.error("查询当前期数，返回报文校验失败,响应报文："+ responseXML);
						 throw new AppException("查询当前期数，返回报文校验失败,响应报文："+ responseXML);
					 }
				 }else if("1".equals(systemXml.getRespCode())){
					 logger.error("查询当前期数，查询当前销售期失败,响应报文："+ responseXML);
					 throw new AppException("查询当前期数，查询当前销售期失败,响应报文："+ responseXML);
				 }else{
					 logger.error("查询当前期数，查询当前销售期异常,响应报文："+ responseXML);
					 throw new AppException("查询当前期数，查询当前销售期异常,响应报文："+ responseXML);
				 }
			 }
		} catch (Exception e) {
			 logger.error("查询当前期数，查询当前销售期异常："+ e.getMessage());
			 throw new AppException(e);
		 } finally {
			 httpClient.getConnectionManager().shutdown();
		 }
		return listRes;
	}
	
	
	
	
	/**
	  * 方法描述：计算彩票注数
	  * @param: Lottery(lotteryNo,playType,numType,content)
	  * @param: 选填(buyamount)
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	 * @throws AppException 
	  * @time: Feb 19, 2013 4:11:19 PM
	  */
	public static Lottery calOrder(Lottery lottery) throws AppException {
		Lottery lotteryRes = new Lottery();
		HttpPost post=new HttpPost(SysCommand.FROADMALL_URL);
		HttpClient httpClient = new DefaultHttpClient();
		StringBuffer buffer=new StringBuffer();
		String requestXML =null;
		String responseXML =null;
		MD5 md5 = new MD5(); 
		String2Xml bodyXml = new String2Xml("FroadMallApiRequest");
		bodyXml.addElementName("system");
		bodyXml.append("version",FroadMallCommand.VERSION);
		bodyXml.append("merchantNo", FroadMallCommand.MERCHANTNO);
		bodyXml.append("merchantPwd",md5.getMD5ofStr(FroadMallCommand.MERCHANTPWD));
		bodyXml.append("signType", "1");
		bodyXml.append("signMsg",md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD+lottery.getContent()));
		bodyXml.append("requestTime",dateFormat.format(new Date()));
		bodyXml.append("busiCode",LotteryCommand.APICODE_CALORDER);
		bodyXml.addElementName("body");
		if(lottery.getLotteryNo()==null||lottery.getPlayType()==null||lottery.getNumType()==null||lottery.getContent()==null){
			 logger.error("计算彩票注数，缺少必填字段!");
			 throw new AppException("计算彩票注数，缺少必填字段!");
		}
		bodyXml.append("lotteryNo",lottery.getLotteryNo());
		bodyXml.append("playType",lottery.getPlayType());
		bodyXml.append("numType",lottery.getNumType());
		bodyXml.append("content",lottery.getContent());
		bodyXml.append("buyamount",lottery.getBuyamount());
		requestXML = bodyXml.toXMLString();
		logger.info("计算彩票注数，请求报文："+requestXML);
		StringEntity entity;
		try {
			entity = new StringEntity(requestXML,"UTF-8");
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
				 responseXML = buffer.toString();
				 logger.info("计算彩票注数，响应报文："+ responseXML);
				 Document document=DocumentHelper.parseText(responseXML);
				 List<?> systemList=document.selectNodes("/FroadMallApiResponse/system/*");
				 List<?> bodyList=document.selectNodes("/FroadMallApiResponse/body/*");
				 if(systemList==null){
					 logger.error("计算彩票注数，解析报文异常,响应报文："+ responseXML);
					 throw new AppException("计算彩票注数，解析报文异常,响应报文："+ responseXML);
				 }
				 Element element=null;
				 String elementName=null;
				 FroadMallXMLSystem systemXml = new FroadMallXMLSystem();
				 for(int i=0;i<systemList.size();i++){
					 element = (Element)systemList.get(i);
					 elementName = element.getName();
					 if("merchantNo".equals(elementName)){
					    systemXml.setMerchantNo(element.getTextTrim());
					 }else if("signType".equals(elementName)){
						systemXml.setSignType(element.getTextTrim());
					 }else if("signMsg".equals(elementName)){
						systemXml.setSignMsg(element.getTextTrim());
					 }else if("busiCode".equals(elementName)){
						systemXml.setBusiCode(element.getText());
					 }else if("version".equals(elementName)){
						systemXml.setVersion(element.getText());
					 }else if("responseTime".equals(elementName)){
						systemXml.setRequestTime(element.getText());
					 }else if("respCode".equals(elementName)){
						systemXml.setRespCode(element.getText());
					 }else if("respMsg".equals(elementName)){
						systemXml.setRespMsg(element.getText());
					 }
				 }
				 if("0000".equals(systemXml.getRespCode())&&bodyList!=null){
					 Element element1=null;
					 String elementName1=null;
					 for(int j=0;j<bodyList.size();j++){
						 element1 = (Element)bodyList.get(j);
						 elementName1 = element1.getName();
						 if("period".equals(elementName1)){
							 lotteryRes.setPeriod(element1.getTextTrim());
						 }else  if("amount".equals(elementName1)){
							 lotteryRes.setAmount(element1.getTextTrim());
						 }else  if("unitAmount".equals(elementName1)){
							 lotteryRes.setUnitAmount(element1.getTextTrim());
						 }else  if("numCount".equals(elementName1)){
							 lotteryRes.setNumCount(element1.getTextTrim());
						 }
					 }
					 String signMsgRes = systemXml.getSignMsg();
					 String signMsgVa = md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD+lotteryRes.getPeriod() + lotteryRes.getAmount());
					 if(!signMsgRes.equals(signMsgVa)){
						 logger.error("计算彩票注数，返回报文校验失败,响应报文："+ responseXML);
						 throw new AppException("查询当前期数，返回报文校验失败,响应报文："+ responseXML);
					 }
				 }else if("1".equals(systemXml.getRespCode())){
					 logger.error("计算彩票注数，计算注数失败,响应报文："+ responseXML);
					 throw new AppException("查询当前期数，计算注数失败,响应报文："+ responseXML);
				 }else{
					 logger.error("计算彩票注数，计算注数异常,响应报文："+ responseXML);
					 throw new AppException("查询当前期数，计算注数异常,响应报文："+ responseXML);
				 }
			 }
		} catch (Exception e) {
			 logger.error("计算彩票注数，计算注数出现异常,响应报文："+ responseXML);
			 throw new AppException(e);
		 } finally {
			 httpClient.getConnectionManager().shutdown();
		 }
		return lotteryRes;
	}
	
	/**
	  * 方法描述：查询足彩信息
	  * @param: Lottery(lotteryNo)
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	  * @time: Feb 19, 2013 4:14:21 PM
	  */
	public static List<Lottery>  queryZCinfos(Lottery lottery) throws AppException{
		List<Lottery> listRes = new ArrayList<Lottery>();
		HttpPost post=new HttpPost(SysCommand.FROADMALL_URL);
		HttpClient httpClient = new DefaultHttpClient();
		StringBuffer buffer=new StringBuffer();
		String requestXML =null;
		String responseXML =null;
		MD5 md5 = new MD5(); 
		String2Xml bodyXml = new String2Xml("FroadMallApiRequest");
		bodyXml.addElementName("system");
		bodyXml.append("version",FroadMallCommand.VERSION);
		bodyXml.append("merchantNo", FroadMallCommand.MERCHANTNO);
		bodyXml.append("merchantPwd",md5.getMD5ofStr(FroadMallCommand.MERCHANTPWD));
		bodyXml.append("signType", "1");
		bodyXml.append("signMsg",md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD+lottery.getLotteryNo()));
		bodyXml.append("requestTime",dateFormat.format(new Date()));
		bodyXml.append("busiCode",LotteryCommand.APICODE_QUERY_ZCINFOS);
		bodyXml.addElementName("body");
		if(lottery.getLotteryNo()==null){
			 logger.error("查询足彩对阵信息，缺少必填字段。");
			 throw new AppException("查询足彩对阵信息，缺少必填字段。");
		}
		bodyXml.append("lotteryNo",lottery.getLotteryNo());
		requestXML = bodyXml.toXMLString();
		logger.info("查询足彩对阵信息，请求报文："+requestXML);
		StringEntity entity;
		try {
			entity = new StringEntity(requestXML,"UTF-8");
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
				 responseXML = buffer.toString();
				 logger.info("查询足彩对阵信息，响应报文："+ responseXML);
				 Document document=DocumentHelper.parseText(responseXML);
				 List<?> systemList=document.selectNodes("/FroadMallApiResponse/system/*");
				 List<?> bodyList=document.selectNodes("/FroadMallApiResponse/body/lotteryList/*");
				 if(systemList==null){
					 logger.error("查询足彩对阵信息，解析报文异常,响应报文："+ responseXML);
					 throw new AppException("查询足彩对阵信息，解析报文异常,响应报文："+ responseXML);
				 }
				 Element element=null;
				 String elementName=null;
				 FroadMallXMLSystem systemXml = new FroadMallXMLSystem();
				 for(int i=0;i<systemList.size();i++){
					 element = (Element)systemList.get(i);
					 elementName = element.getName();
					 if("merchantNo".equals(elementName)){
					    systemXml.setMerchantNo(element.getTextTrim());
					 }else if("signType".equals(elementName)){
						systemXml.setSignType(element.getTextTrim());
					 }else if("signMsg".equals(elementName)){
						systemXml.setSignMsg(element.getTextTrim());
					 }else if("busiCode".equals(elementName)){
						systemXml.setBusiCode(element.getText());
					 }else if("version".equals(elementName)){
						systemXml.setVersion(element.getText());
					 }else if("responseTime".equals(elementName)){
						systemXml.setRequestTime(element.getText());
					 }else if("respCode".equals(elementName)){
						systemXml.setRespCode(element.getText());
					 }else if("respMsg".equals(elementName)){
						systemXml.setRespMsg(element.getText());
					 }
				 }
				 if("0000".equals(systemXml.getRespCode())&&bodyList!=null){
					 Element element1=null;
					 String elementName1=null;
					 Period pr = new Period();
					 List<Ct> cts = new ArrayList<Ct>();
					 pr.setCt(cts);
					 for(int j=0;j<bodyList.size();j++){
						 element1 = (Element)bodyList.get(j);
						 elementName1 = element1.getName();
						 if("lottery".equals(elementName1)){
							Lottery lotteryRes = new Lottery();
							List<Period> listPer = new ArrayList<Period>();
							lotteryRes.setPList(listPer);
							Ct ct = new Ct();
							for (Iterator<?> it2 = element1.elementIterator(); it2.hasNext();) {
								 Element element2 = (Element) it2.next();
								 String element2Name = element2.getName();
								 if("period".equals(element2Name)){
									 pr.setPeriod(element2.getTextTrim());
								 }else if("zd".equals(element2Name)){
									 ct.setZd(element2.getTextTrim());
								 }else if("kd".equals(element2Name)){
									 ct.setKd(element2.getTextTrim());
								 }else if("z".equals(element2Name)){
									 ct.setZ(element2.getTextTrim());
								 }else if("k".equals(element2Name)){
									 ct.setK(element2.getTextTrim());
								 }else if("kssj".equals(element2Name)){
									 ct.setKssj(element2.getTextTrim());
								 }else if("cc".equals(element2Name)){
									 ct.setCc(element2.getTextTrim());
								 }
							}
							pr.getCt().add(ct);
							lotteryRes.getPList().add(pr);
							listRes.add(lotteryRes);
						 }
					 }
					 String signMsgRes = systemXml.getSignMsg();
					 String signMsgVa = md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD);
					 if(!signMsgRes.equals(signMsgVa)){
						 logger.error("查询足彩对阵信息，返回报文校验失败,响应报文："+ responseXML);
						 throw new AppException("查询足彩对阵信息，返回报文校验失败,响应报文："+ responseXML);
					 }
				 }else if("1".equals(systemXml.getRespCode())){
					 logger.error("查询足彩对阵信息，查询足彩对阵信息失败,响应报文："+ responseXML);
					 throw new AppException("查询足彩对阵信息，查询足彩对阵信息失败,响应报文："+ responseXML);
				 }else{
					 logger.error("查询足彩对阵信息，查询足彩对阵信息异常,响应报文："+ responseXML);
					 throw new AppException("查询足彩对阵信息，查询足彩对阵信息异常,响应报文："+ responseXML);
				 }
			 }
		} catch (Exception e) {
			 logger.error("查询足彩对阵信息时出现异常！请求报文："+requestXML+"响应报文："+ responseXML,e);
			 throw new AppException(e);
		 } finally {
			 httpClient.getConnectionManager().shutdown();
		 }
		return listRes;
	}
	
	/**
	  * 方法描述：查询上期中奖情况
	  * @param: 选填Lottery(lotteryNo)
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	  * @time: Feb 27, 2013 2:29:05 PM
	  */
	public static List<Lottery> queryLastPeridReward(Lottery lottery) throws AppException{
		List<Lottery> listRes  = new ArrayList<Lottery>();
		HttpPost post=new HttpPost(SysCommand.FROADMALL_URL);
		HttpClient httpClient = new DefaultHttpClient();
		StringBuffer buffer=new StringBuffer();
		String requestXML =null;
		String responseXML =null;
		MD5 md5 = new MD5(); 
		String2Xml bodyXml = new String2Xml("FroadMallApiRequest");
		bodyXml.addElementName("system");
		bodyXml.append("version",FroadMallCommand.VERSION);
		bodyXml.append("merchantNo", FroadMallCommand.MERCHANTNO);
		bodyXml.append("merchantPwd",md5.getMD5ofStr(FroadMallCommand.MERCHANTPWD));
		bodyXml.append("signType", "1");
		bodyXml.append("signMsg",md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD));
		bodyXml.append("requestTime",dateFormat.format(new Date()));
		bodyXml.append("busiCode",LotteryCommand.APICODE_QUERY_LASTPERIOD_REWARD);
		bodyXml.addElementName("body");
		bodyXml.append("lotteryNo",lottery.getLotteryNo());
		requestXML = bodyXml.toXMLString();
		logger.info("查询上期中奖情况，请求报文："+requestXML);
		StringEntity entity;
		try {
			entity = new StringEntity(requestXML,"UTF-8");
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
				 responseXML = buffer.toString();
				 logger.info("查询上期中奖情况,响应报文："+ responseXML);
				 Document document=DocumentHelper.parseText(responseXML);
				 List<?> systemList=document.selectNodes("/FroadMallApiResponse/system/*");
				 List<?> bodyList=document.selectNodes("/FroadMallApiResponse/body/lotteryList/*");
				 if(systemList==null){
					 logger.error("查询上期中奖情况，解析报文异常,响应报文："+ responseXML);
					 throw new AppException("查询上期中奖情况，解析报文异常,响应报文："+ responseXML);
				 }
				 Element element=null;
				 String elementName=null;
				 FroadMallXMLSystem systemXml = new FroadMallXMLSystem();
				 for(int i=0;i<systemList.size();i++){
					 element = (Element)systemList.get(i);
					 elementName = element.getName();
					 if("merchantNo".equals(elementName)){
					    systemXml.setMerchantNo(element.getTextTrim());
					 }else if("signType".equals(elementName)){
						systemXml.setSignType(element.getTextTrim());
					 }else if("signMsg".equals(elementName)){
						systemXml.setSignMsg(element.getTextTrim());
					 }else if("busiCode".equals(elementName)){
						systemXml.setBusiCode(element.getText());
					 }else if("version".equals(elementName)){
						systemXml.setVersion(element.getText());
					 }else if("responseTime".equals(elementName)){
						systemXml.setRequestTime(element.getText());
					 }else if("respCode".equals(elementName)){
						systemXml.setRespCode(element.getText());
					 }else if("respMsg".equals(elementName)){
						systemXml.setRespMsg(element.getText());
					 }
				 }
				 if("0000".equals(systemXml.getRespCode())&&bodyList!=null){
					 Element element1=null;
					 String elementName1=null;
					 for(int j=0;j<bodyList.size();j++){
						 element1 = (Element)bodyList.get(j);
						 elementName1 = element1.getName();
						 if("lottery".equals(elementName1)){
							Lottery lotteryRes = new Lottery();
							List<Period> listPer = new ArrayList<Period>();
							lotteryRes.setPList(listPer);
							Period pr = new Period();
							for (Iterator<?> it2 = element1.elementIterator(); it2.hasNext();) {
								 Element element2 = (Element) it2.next();
								 String element2Name = element2.getName();
								 if("period".equals(element2Name)){
									 pr.setPeriod(element2.getTextTrim());
								 }else if("lotteryName".equals(element2Name)){
									 pr.setLotteryName(element2.getTextTrim());
								 }else if("lotteryNo".equals(element2Name)){
									 pr.setLotteryNo(element2.getTextTrim());
								 }else if("openTime".equals(element2Name)){
									 pr.setOpenTime(element2.getTextTrim());
								 }else if("content".equals(element2Name)){
									 pr.setContent(element2.getTextTrim());
								 }else if("tznum".equals(element2Name)){
									 pr.setTznum(element2.getTextTrim());
								 }
							}
							lotteryRes.getPList().add(pr);
							listRes.add(lotteryRes);
						 }
					 }
					 String signMsgRes = systemXml.getSignMsg();
					 String signMsgVa = md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD);
					 if(!signMsgRes.equals(signMsgVa)){
						 logger.error("查询上期中奖情况，返回报文校验失败,响应报文："+ responseXML);
						 throw new AppException("查询足彩对阵信息，返回报文校验失败,响应报文："+ responseXML);
					 }
				 }else if("1".equals(systemXml.getRespCode())){
					 logger.error("查询上期中奖情况，查询上期中奖情况失败,响应报文："+ responseXML);
					 throw new AppException("查询足彩对阵信息，查询上期中奖情况失败,响应报文："+ responseXML);
				 }else{
					 logger.error("查询上期中奖情况，查询上期中奖情况异常,响应报文："+ responseXML);
					 throw new AppException("查询足彩对阵信息，查询上期中奖情况异常,响应报文："+ responseXML);
				 }
			 }
		} catch (Exception e) {
			 logger.error("查询上期中奖情况时出现异常！请求报文："+requestXML+"响应报文："+ responseXML,e);
			 throw new AppException(e);
		 } finally {
			 httpClient.getConnectionManager().shutdown();
		 }
		return listRes;
	}
	
	/**
	  * 方法描述：创建订单
	  * @param:Lottery(lotteryNo,playType,numType,buyamount,amount,
	  *                mobilephone,period,content,numCount,orderID)
	  * @return: Lottery
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	  * @time: Feb 27, 2013 2:30:11 PM
	  */
	public static Lottery createOrder(Lottery lottery) throws AppException{
		if(lottery.getLotteryNo()==null||lottery.getPeriod()==null){
			 logger.error("购买彩票，缺少必填字段：彩票编码或彩票期号");
			 throw new AppException("购买彩票，缺少必填字段：彩票编码或彩票期号");
		}
		if(lottery.getPlayType()==null||lottery.getNumType()==null){
			 logger.error("购买彩票，缺少必填字段：玩法或单复合胆");
			 throw new AppException("购买彩票，缺少必填字段：玩法或单复合胆");
		}
		if(lottery.getContent()==null||lottery.getBuyamount()==null){
			 logger.error("购买彩票，缺少必填字段：投注号码或投注倍数");
			 throw new AppException("购买彩票，缺少必填字段：投注号码或投注倍数");
		}
		if(lottery.getAmount()==null||lottery.getNumCount()==null){
			 logger.error("购买彩票，缺少必填字段：投注金额或投注注数");
			 throw new AppException("购买彩票，缺少必填字段：投注金额或投注注数");
		}
		if(lottery.getMobilephone()==null){
			 logger.error("购买彩票，缺少必填字段：联系电话");
			 throw new AppException("购买彩票，缺少必填字段：联系电话");
		}
		Lottery lotteryRes = new Lottery();
		HttpPost post=new HttpPost(SysCommand.FROADMALL_URL);
		HttpClient httpClient = new DefaultHttpClient();
		StringBuffer buffer=new StringBuffer();
		String requestXML =null;
		String responseXML =null;
		MD5 md5 = new MD5(); 
		String2Xml bodyXml = new String2Xml("FroadMallApiRequest");
		bodyXml.addElementName("system");
		bodyXml.append("version",FroadMallCommand.VERSION);
		bodyXml.append("merchantNo", FroadMallCommand.MERCHANTNO);
		bodyXml.append("merchantPwd",md5.getMD5ofStr(FroadMallCommand.MERCHANTPWD));
		bodyXml.append("signType", "1");
		bodyXml.append("signMsg",md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD+lottery.getLotteryNo()+lottery.getPeriod()+lottery.getContent()+lottery.getOrderID()));
		bodyXml.append("requestTime",dateFormat.format(new Date()));
		bodyXml.append("busiCode",LotteryCommand.APICODE_CREATEORDER);
		bodyXml.addElementName("body");
		bodyXml.append("lotteryNo",lottery.getLotteryNo());
		bodyXml.append("period",lottery.getPeriod());
		bodyXml.append("playType",lottery.getPlayType());
		bodyXml.append("numType",lottery.getNumType());
		bodyXml.append("content",lottery.getContent());
		bodyXml.append("buyamount",lottery.getBuyamount());
		bodyXml.append("amount",lottery.getAmount());
		bodyXml.append("numCount",lottery.getNumCount());
		bodyXml.append("mobilephone",lottery.getMobilephone());
		bodyXml.append("orderID",lottery.getOrderID());
		bodyXml.append("callBackAddr",SysCommand.LOTTERY_SHIPMENTS_NOTICE_URL);
		bodyXml.append("callBackAddr2",SysCommand.LOTTERY_WIN_NOTICE_URL);
		
		requestXML = bodyXml.toXMLString();
		logger.info("购买彩票时，请求报文："+requestXML);
		StringEntity entity;
		try {
			entity = new StringEntity(requestXML,"UTF-8");
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
				 responseXML = buffer.toString();
				 logger.info("购买彩票时,响应报文："+ responseXML);
				 Document document=DocumentHelper.parseText(responseXML);
				 List<?> systemList=document.selectNodes("/FroadMallApiResponse/system/*");
				 List<?> bodyList=document.selectNodes("/FroadMallApiResponse/body/*");
				 if(systemList==null){
					 logger.error("购买彩票时，解析报文异常,响应报文："+ responseXML);
					 throw new AppException("购买彩票时，查询上期中奖情况异常,响应报文："+ responseXML);
				 }
				 Element element=null;
				 String elementName=null;
				 FroadMallXMLSystem systemXml = new FroadMallXMLSystem();
				 for(int i=0;i<systemList.size();i++){
					 element = (Element)systemList.get(i);
					 elementName = element.getName();
					 if("merchantNo".equals(elementName)){
					    systemXml.setMerchantNo(element.getTextTrim());
					 }else if("signType".equals(elementName)){
						systemXml.setSignType(element.getTextTrim());
					 }else if("signMsg".equals(elementName)){
						systemXml.setSignMsg(element.getTextTrim());
					 }else if("busiCode".equals(elementName)){
						systemXml.setBusiCode(element.getText());
					 }else if("version".equals(elementName)){
						systemXml.setVersion(element.getText());
					 }else if("responseTime".equals(elementName)){
						systemXml.setRequestTime(element.getText());
					 }else if("respCode".equals(elementName)){
						systemXml.setRespCode(element.getText());
					 }else if("respMsg".equals(elementName)){
						systemXml.setRespMsg(element.getText());
					 }
				 }
				 if("0000".equals(systemXml.getRespCode())&&bodyList!=null){
					 Element element1=null;
					 String elementName1=null;
					 for(int j=0;j<bodyList.size();j++){
						 element1 = (Element)bodyList.get(j);
						 elementName1 = element1.getName();
						 if("tranID".equals(elementName1)){
							 lotteryRes.setTranID(element1.getTextTrim());
						 }else if("description".equals(elementName1)){
							 lotteryRes.setDescription(element1.getTextTrim());
						 }
					 }
					 String signMsgRes = systemXml.getSignMsg();
					 String signMsgVa = md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD+lotteryRes.getTranID());
					 if(!signMsgRes.equals(signMsgVa)){
						 logger.error("购买彩票时，返回报文校验失败,响应报文："+ responseXML);
						 throw new AppException("购买彩票时，返回报文校验失败,响应报文："+ responseXML);
					 }
					 lotteryRes.setRespCode(Command.RESP_CODE_SUCCESS);
				 }else if("1".equals(systemXml.getRespCode())){
					 logger.error("购买彩票时，购买彩票失败,响应报文："+ responseXML);
					 throw new AppException("购买彩票时，购买彩票失败,响应报文："+ responseXML);
				 }else{
					 logger.error("购买彩票时，购买彩票异常,响应报文："+ responseXML);
					 throw new AppException("购买彩票时，购买彩票异常,响应报文："+ responseXML);
				 }
			 }
		} catch (Exception e) {
			 logger.error("购买彩票时出现异常！请求报文："+requestXML+"响应报文："+ responseXML,e);
			 throw new AppException(e);
		 } finally {
			 httpClient.getConnectionManager().shutdown();
		 }
		return lotteryRes;
	}
	
	/**
	  * 方法描述：通过TRANID查询彩票中奖情况
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	  * @time: Feb 27, 2013 2:30:27 PM
	  */
	public static Lottery queryRewardByTranID(Lottery lottery) throws AppException{
		Lottery lotteryRes = new Lottery();
		HttpPost post=new HttpPost(SysCommand.FROADMALL_URL);
		HttpClient httpClient = new DefaultHttpClient();
		StringBuffer buffer=new StringBuffer();
		String requestXML =null;
		String responseXML =null;
		MD5 md5 = new MD5(); 
		String2Xml bodyXml = new String2Xml("FroadMallApiRequest");
		bodyXml.addElementName("system");
		bodyXml.append("version",FroadMallCommand.VERSION);
		bodyXml.append("merchantNo", FroadMallCommand.MERCHANTNO);
		bodyXml.append("merchantPwd",md5.getMD5ofStr(FroadMallCommand.MERCHANTPWD));
		bodyXml.append("signType", "1");
		bodyXml.append("signMsg",md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD+lottery.getTranID()));
		bodyXml.append("requestTime",dateFormat.format(new Date()));
		bodyXml.append("busiCode",LotteryCommand.APICODE_QUERY_REWARDBYTRANID);
		bodyXml.addElementName("body");
		if(lottery.getTranID()==null){
			 logger.error("查询中奖彩票，缺少必填字段！");
			 throw new AppException("查询中奖彩票，缺少必填字段！");
		}
		bodyXml.append("tranID",lottery.getTranID());
		requestXML = bodyXml.toXMLString();
		logger.info("查询中奖彩票时，请求报文："+requestXML);
		StringEntity entity;
		try {
			entity = new StringEntity(requestXML,"UTF-8");
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
				 responseXML = buffer.toString();
				 logger.info("查询中奖彩票时，响应报文："+ responseXML);
				 Document document=DocumentHelper.parseText(responseXML);
				 List<?> systemList=document.selectNodes("/FroadMallApiResponse/system/*");
				 List<?> bodyList=document.selectNodes("/FroadMallApiResponse/body/*");
				 if(systemList==null){
					 logger.error("查询中奖彩票，解析报文异常！");
					 throw new AppException("查询中奖彩票，解析报文异常！");
				 }
				 Element element=null;
				 String elementName=null;
				 FroadMallXMLSystem systemXml = new FroadMallXMLSystem();
				 for(int i=0;i<systemList.size();i++){
					 element = (Element)systemList.get(i);
					 elementName = element.getName();
					 if("merchantNo".equals(elementName)){
					    systemXml.setMerchantNo(element.getTextTrim());
					 }else if("signType".equals(elementName)){
						systemXml.setSignType(element.getTextTrim());
					 }else if("signMsg".equals(elementName)){
						systemXml.setSignMsg(element.getTextTrim());
					 }else if("busiCode".equals(elementName)){
						systemXml.setBusiCode(element.getText());
					 }else if("version".equals(elementName)){
						systemXml.setVersion(element.getText());
					 }else if("responseTime".equals(elementName)){
						systemXml.setRequestTime(element.getText());
					 }else if("respCode".equals(elementName)){
						systemXml.setRespCode(element.getText());
					 }else if("respMsg".equals(elementName)){
						systemXml.setRespMsg(element.getText());
					 }
				 }
				 if("0000".equals(systemXml.getRespCode())&&bodyList!=null){
					 Element element1=null;
					 String elementName1=null;
					 for(int j=0;j<bodyList.size();j++){
						 element1 = (Element)bodyList.get(j);
						 elementName1 = element1.getName();
						 if("tranID".equals(elementName1)){
							 lotteryRes.setTranID(element1.getTextTrim());
						 }else if("period".equals(elementName1)){
							 lotteryRes.setPeriod(element1.getTextTrim());
						 }else if("content".equals(elementName1)){
							 lotteryRes.setContent(element1.getTextTrim());
						 }else if("buyamount".equals(elementName1)){
							 lotteryRes.setBuyamount(element1.getTextTrim());
						 }else if("amount".equals(elementName1)){
							 lotteryRes.setAmount(element1.getTextTrim());
						 }else if("lotteryNo".equals(elementName1)){
							 lotteryRes.setLotteryNo(element1.getTextTrim());
						 }else if("status".equals(elementName1)){
							 lotteryRes.setStatus(element1.getTextTrim());
						 }else if("awdAmount".equals(elementName1)){
							 lotteryRes.setAwdAmount(element1.getTextTrim());
						 }else if("prizeGrade".equals(elementName1)){
							 lotteryRes.setPrizeGrade(element1.getTextTrim());
						 }else if("prizeCount".equals(elementName1)){
							 lotteryRes.setPrizeCount(element1.getTextTrim());
						 }else if("buyamount".equals(elementName1)){
							 lotteryRes.setBuyamount(element1.getTextTrim());
						 }
					 }
					 String signMsgRes = systemXml.getSignMsg();
					 String signMsgVa = md5.getMD5ofStr(FroadMallCommand.KEY+FroadMallCommand.MERCHANTNO+FroadMallCommand.MERCHANTPWD);
					 if(!signMsgRes.equals(signMsgVa)){
						 logger.error("查询中奖彩票，返回报文校验失败,响应报文："+ responseXML);
						 throw new AppException("查询中奖彩票，返回报文校验失败,响应报文："+ responseXML);
					 }
				 }else if("1".equals(systemXml.getRespCode())){
					 logger.error("查询中奖彩票，查询中奖彩票失败,响应报文："+ responseXML);
					 throw new AppException("查询中奖彩票，查询中奖彩票失败,响应报文："+ responseXML);
				 }else{
					 logger.error("查询中奖彩票，查询中奖彩票异常,响应报文："+ responseXML);
					 throw new AppException("查询中奖彩票，查询中奖彩票异常,响应报文："+ responseXML);
				 }
			 }
		} catch (Exception e) {
			 logger.error("查询中奖彩票时出现异常！请求报文："+requestXML+"响应报文："+ responseXML,e);
			 throw new AppException(e);
		 } finally {
			 httpClient.getConnectionManager().shutdown();
		 }
		return lotteryRes;
	}
	public static void main(String[] args) throws AppException {
		Lottery lottery = new Lottery();
		lottery.setLotteryNo("FC_SSQ");
		lottery.setPlayType("1");
		lottery.setNumType("1");
		lottery.setBuyamount("1");
		lottery.setAmount("2");
		lottery.setMobilephone("18616136190");
		lottery.setPeriod("2013014");//
		lottery.setContent("01,02,03,04,05,06|07");//
		lottery.setNumCount("1");//
		lottery.setOrderID("fbu10000100001");//--
		createOrder(lottery);
		
//		lottery.setLotteryNo("FC_SSQ");
//		lottery.setPlayType("1");
//		lottery.setNumType("1");
//		lottery.setContent("01,02,03,04,05,06|07");
//		calOrder(lottery);
		
//		lottery.setLotteryNo("FC_SSQ");
//		queryPeridListNow(lottery);
		
//		lottery.setTranID("201301060000020");
//		queryRewardByTranID(lottery);
	}
}
