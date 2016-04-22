package com.froad.CB.thirdparty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.froad.CB.common.SysCommand;
import com.froad.CB.common.constant.FroadMallCommand;
import com.froad.CB.common.constant.HFCZCommand;
import com.froad.CB.po.FroadMallXMLSystem;
import com.froad.CB.po.HFCZ;
import com.froad.util.MD5;
import com.froad.util.String2Xml;

public class HFCZFunc {
	private static final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger logger=Logger.getLogger(HFCZFunc.class);
	
	
	/**
	 * 方法描述：查询号码是否可以充值
	 * 
	 * @param:HFCZ(CZNo)
	 * @param:选填参数(money)
	 * @return: HFCZ hfczRes
	 * @version: 1.0
	 * @time: Feb 7, 2013 2:03:29 PM
	 */
	public static HFCZ checkParaCZNo(HFCZ hfcz) {
		HFCZ hfczRes = new HFCZ();
		HttpPost post = new HttpPost(SysCommand.FROADMALL_URL);
		HttpClient httpClient = new DefaultHttpClient();
		StringBuffer buffer = new StringBuffer();
		String requestXML = null;
		String responseXML = null;
		MD5 md5 = new MD5();
		String2Xml bodyXml = new String2Xml("FroadMallApiRequest");
		bodyXml.addElementName("system");
		bodyXml.append("version", FroadMallCommand.VERSION);
		bodyXml.append("merchantNo", FroadMallCommand.MERCHANTNO);
		bodyXml.append("merchantPwd",md5.getMD5ofStr(FroadMallCommand.MERCHANTPWD));
		bodyXml.append("signType", "1");
		bodyXml.append(
				"signMsg",
				md5.getMD5ofStr(FroadMallCommand.KEY
						+ FroadMallCommand.MERCHANTNO
						+ FroadMallCommand.MERCHANTPWD + hfcz.getCZNo()));
		bodyXml.append("requestTime", dateFormat.format(new Date()));
		bodyXml.append("busiCode", HFCZCommand.APICODE_QUERY_CZNO);
		bodyXml.addElementName("body");
		if (hfcz.getCZNo() == null) {
			logger.error("查询号码是否可以充值，缺少必填字段.");
			hfczRes.setRespCode(HFCZCommand.CODE_FAIL);
			hfczRes.setRespMsg("话费查询失败,缺少必填字段");
			return hfczRes;
		}
		if (hfcz.getMoney() != null) {
			bodyXml.append("money", hfcz.getMoney().toString());
		}
		bodyXml.append("CZNo", hfcz.getCZNo());
		requestXML = bodyXml.toXMLString();
		logger.info("查询号码是否可以充值,请求报文：" + requestXML);
		StringEntity entity;
		try {
			entity = new StringEntity(requestXML, "UTF-8");
			post.setEntity(entity);
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			logger.info("发送话费查询请求,连接用时"
					+ (System.currentTimeMillis() - startTime) + "ms");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				reader.close();
				responseXML = buffer.toString();
				logger.info("查询号码是否可以充值，响应报文：" + responseXML);
				Document document = DocumentHelper.parseText(responseXML);
				List<?> systemList = document
						.selectNodes("/FroadMallApiResponse/system/*");
				List<?> bodyList = document
						.selectNodes("/FroadMallApiResponse/body/*");
				if (systemList == null) {
					logger.error("查询号码是否可以充值，解析报文异常,响应报文：" + responseXML);
					throw new AppException("查询号码是否可以充值，解析报文异常,响应报文："+ responseXML);
				}
				Element element = null;
				String elementName = null;
				FroadMallXMLSystem systemXml = new FroadMallXMLSystem();
				for (int i = 0; i < systemList.size(); i++) {
					element = (Element)systemList.get(i);
					elementName = element.getName();
					if ("merchantNo".equals(elementName)) {
						systemXml.setMerchantNo(element.getTextTrim());
					} else if ("signType".equals(elementName)) {
						systemXml.setSignType(element.getTextTrim());
					} else if ("signMsg".equals(elementName)) {
						systemXml.setSignMsg(element.getTextTrim());
					} else if ("busiCode".equals(elementName)) {
						systemXml.setBusiCode(element.getText());
					} else if ("version".equals(elementName)) {
						systemXml.setVersion(element.getText());
					} else if ("responseTime".equals(elementName)) {
						systemXml.setRequestTime(element.getText());
					} else if ("respCode".equals(elementName)) {
						systemXml.setRespCode(element.getText());
					} else if ("respMsg".equals(elementName)) {
						systemXml.setRespMsg(element.getText());
					}
				}
				if ("0000".equals(systemXml.getRespCode()) && bodyList != null) {
					String signMsgRes = systemXml.getSignMsg();
					String signMsgVa = md5
							.getMD5ofStr(FroadMallCommand.KEY
									+ FroadMallCommand.MERCHANTNO
									+ FroadMallCommand.MERCHANTPWD);
					if (!signMsgRes.equals(signMsgVa)) {
						logger.error("查询号码是否可以充值，返回报文校验失败,响应报文：" + responseXML);
						throw new AppException("查询号码是否可以充值，返回报文校验失败,响应报文："
								+ responseXML);
					}
					Element element1 = null;
					String elementName1 = null;
					for (int j = 0; j < bodyList.size(); j++) {
						element1 = (Element)bodyList.get(j);
						elementName1 = element1.getName();
						if ("area".equals(elementName1)) {
							hfczRes.setArea(element1.getTextTrim());
						} else if ("operater".equals(elementName1)) {
							hfczRes.setOperater(element1.getTextTrim());
						} else if ("CZNo".equals(elementName1)) {
							hfczRes.setCZNo(element1.getTextTrim());
						} else if ("money".equals(elementName1)) {
							hfczRes.setMoney(new BigDecimal(element1
									.getTextTrim()));
						} else if ("salePrice".equals(elementName1)) {
							hfczRes.setSalePrice(new BigDecimal(element1
									.getTextTrim()));
						}
					}
					hfczRes.setRespCode(HFCZCommand.CODE_SUCCESS);
					hfczRes.setRespMsg("话费费率查询成功.");
				} else {
					logger.error("查询号码是否可以充值，查询手机号码失败,响应报文：" + responseXML);
					hfczRes.setRespCode(HFCZCommand.CODE_FAIL);
					hfczRes.setRespMsg("话费费率查询失败,请稍后查询.");
				}
			}
			else{
				hfczRes.setRespCode(HFCZCommand.CODE_FAIL);
				hfczRes.setRespMsg("话费费率查询失败,请稍后查询.");
			}
		} catch (AppException e) {
			logger.error("查询手机号码时出现异常!" + e.getMessage() + e);
			hfczRes.setRespCode(HFCZCommand.CODE_EXCEPTION);
			hfczRes.setRespMsg("话费费率查询失败,请稍后查询.");
		} catch (Exception e) {
			logger.error("查询手机号码时出现异常！请求报文：" + requestXML + "响应报文："
					+ responseXML, e);
			hfczRes.setRespCode(HFCZCommand.CODE_EXCEPTION);
			hfczRes.setRespMsg("话费费率查询失败,请稍后查询.");
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return hfczRes;
	}

	/**
	 * 方法描述：立即充值
	 * 
	 * @param: HFCZ(CZNo,money,SPID,tranDate)
	 * @param: SPID组成：fbu+transId+x+payId
	 * @param: 选填(salePrice)
	 * @return: HFCZ hfczRes
	 * @version: 1.0
	 * @time: Feb 7, 2013 2:03:56 PM
	 */
	public static HFCZ onLine(HFCZ hfcz) {
		HFCZ hfczRes = new HFCZ();
		if (hfcz.getCZNo() == null || hfcz.getMoney() == null
				|| hfcz.getSPID() == null || hfcz.getTranDate() == null) {
			logger.error("立即充值，缺少必填字段.");
			hfczRes.setRespCode(HFCZCommand.CODE_FAIL);
			hfczRes.setRespMsg("话费充值失败,缺少必填字段");
			return hfczRes;
		}
		HttpPost post = new HttpPost(SysCommand.FROADMALL_URL);
		HttpClient httpClient = new DefaultHttpClient();
		StringBuffer buffer = new StringBuffer();
		String requestXML = null;
		String responseXML = null;
		MD5 md5 = new MD5();
		String2Xml bodyXml = new String2Xml("FroadMallApiRequest");
		bodyXml.addElementName("system");
		bodyXml.append("version", FroadMallCommand.VERSION);
		bodyXml.append("merchantNo", FroadMallCommand.MERCHANTNO);
		bodyXml.append("merchantPwd",md5.getMD5ofStr(FroadMallCommand.MERCHANTPWD));
		bodyXml.append("signType", "1");
		bodyXml.append("signMsg",md5.getMD5ofStr(FroadMallCommand.KEY
						+ FroadMallCommand.MERCHANTNO
						+ FroadMallCommand.MERCHANTPWD + hfcz.getCZNo()
						+ hfcz.getSPID()));
		bodyXml.append("requestTime", dateFormat.format(new Date()));
		bodyXml.append("busiCode", HFCZCommand.APICODE_ONLINE);
		bodyXml.addElementName("body");
		if (hfcz.getSalePrice() != null) {
			bodyXml.append("salePrice", hfcz.getSalePrice().toString());
		}
		bodyXml.append("CZNo", hfcz.getCZNo());
		bodyXml.append("money", hfcz.getMoney().toString() == null ? "0" : hfcz
				.getMoney().toString());
		bodyXml.append("SPID", hfcz.getSPID());
		bodyXml.append("tranDate", hfcz.getTranDate());
		bodyXml.append("merchantReturnUrl", SysCommand.HFCZ_NOTICE_URL);
		requestXML = bodyXml.toXMLString();
		logger.info("立即充值,请求报文：" + requestXML);
		StringEntity entity;
		try {
			entity = new StringEntity(requestXML, "UTF-8");
			post.setEntity(entity);
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			logger.info("发送立即充值请求,连接用时"
					+ (System.currentTimeMillis() - startTime) + "ms");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				reader.close();
				responseXML = buffer.toString();
				logger.info("立即充值,响应报文：" + responseXML);
				Document document = DocumentHelper.parseText(responseXML);
				List<?> systemList = document
						.selectNodes("/FroadMallApiResponse/system/*");
				List<?> bodyList = document
						.selectNodes("/FroadMallApiResponse/body/*");
				if (systemList == null) {
					logger.error("立即充值，解析报文异常,响应报文：" + responseXML);
					throw new AppException("立即充值，解析报文异常,响应报文：" + responseXML);
				}
				Element element = null;
				String elementName = null;
				FroadMallXMLSystem systemXml = new FroadMallXMLSystem();
				for (int i = 0; i < systemList.size(); i++) {
					element = (Element)systemList.get(i);
					elementName = element.getName();
					if ("merchantNo".equals(elementName)) {
						systemXml.setMerchantNo(element.getTextTrim());
					} else if ("signType".equals(elementName)) {
						systemXml.setSignType(element.getTextTrim());
					} else if ("signMsg".equals(elementName)) {
						systemXml.setSignMsg(element.getTextTrim());
					} else if ("busiCode".equals(elementName)) {
						systemXml.setBusiCode(element.getText());
					} else if ("version".equals(elementName)) {
						systemXml.setVersion(element.getText());
					} else if ("responseTime".equals(elementName)) {
						systemXml.setRequestTime(element.getText());
					} else if ("respCode".equals(elementName)) {
						systemXml.setRespCode(element.getText());
					} else if ("respMsg".equals(elementName)) {
						systemXml.setRespMsg(element.getText());
					}
				}
				if ("0000".equals(systemXml.getRespCode()) && bodyList != null) {
					Element element1 = null;
					String elementName1 = null;
					for (int j = 0; j < bodyList.size(); j++) {
						element1 = (Element)bodyList.get(j);
						elementName1 = element1.getName();
						if ("tranID".equals(elementName1)) {
							hfczRes.setTranID(element1.getTextTrim());
						}
					}

					hfczRes.setRespCode(HFCZCommand.CODE_SUCCESS);
					hfczRes.setRespMsg("话费充值成功,请稍后查询.");
					return hfczRes;
				} else if ("0004".equals(systemXml.getRespCode())) {
					logger.error("立即充值，充值失败,响应报文：" + responseXML);
					hfczRes.setRespCode(HFCZCommand.CODE_FAIL);
					hfczRes.setRespMsg("话费充值失败,请重新操作");
					return hfczRes;
				} else {
					logger.error("立即充值，充值异常,响应报文：" + responseXML);
					throw new AppException("立即充值，充值异常,响应报文：" + responseXML);
				}
			} else {
				logger.error("========HTTP响应异常，HTTP响应信息："+ response.getStatusLine());
				throw new AppException("立即充值，充值异常,响应报文：" + responseXML);
			}
		} catch (Exception e) {
			logger.error("立即充值时出现异常！请求报文：" + requestXML + "响应报文：" + responseXML, e);
			hfczRes.setRespCode(HFCZCommand.CODE_EXCEPTION);
			hfczRes.setRespMsg("话费充值失败,请重新操作");
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		
		return hfczRes;
	}

	/**
	 * 方法描述：话费交易查询
	 * 
	 * @param: HFCZ(CZNo,money,SPID,tranDate)
	 * @param: 选填(salePrice)
	 * @return: HFCZ hfczRes
	 * @version: 1.0
	 * @throws AppException
	 * @time: Feb 7, 2013 2:03:56 PM
	 */
	public static HFCZ queryTrans(HFCZ hfcz) {
		HFCZ hfczRes = new HFCZ();
		HttpPost post = new HttpPost(SysCommand.FROADMALL_URL);
		HttpClient httpClient = new DefaultHttpClient();
		StringBuffer buffer = new StringBuffer();
		String requestXML = null;
		String responseXML = null;
		MD5 md5 = new MD5();
		String2Xml bodyXml = new String2Xml("FroadMallApiRequest");
		bodyXml.addElementName("system");
		bodyXml.append("version", FroadMallCommand.VERSION);
		bodyXml.append("merchantNo", FroadMallCommand.MERCHANTNO);
		bodyXml.append("merchantPwd",
				md5.getMD5ofStr(FroadMallCommand.MERCHANTPWD));
		bodyXml.append("signType", "1");
		bodyXml.append("signMsg",
				md5.getMD5ofStr(FroadMallCommand.KEY
						+ FroadMallCommand.MERCHANTNO
						+ FroadMallCommand.MERCHANTPWD + hfcz.getSPID()));
		bodyXml.append("requestTime", dateFormat.format(new Date()));
		bodyXml.append("busiCode", HFCZCommand.APICODE_QUERY);
		bodyXml.addElementName("body");

		bodyXml.append("SPID", hfcz.getSPID());
		requestXML = bodyXml.toXMLString();
		logger.info("话费充值查询,请求报文：" + requestXML);
		StringEntity entity;
		try {
			entity = new StringEntity(requestXML, "UTF-8");
			post.setEntity(entity);
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			logger.info("发送查询话费交易请求,连接用时"
					+ (System.currentTimeMillis() - startTime) + "ms");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				reader.close();
				responseXML = buffer.toString();
				logger.info("话费充值查询,响应报文：" + responseXML);
				Document document = DocumentHelper.parseText(responseXML);
				List<?> systemList = document
						.selectNodes("/FroadMallApiResponse/system/*");
				List<?> bodyList = document
						.selectNodes("/FroadMallApiResponse/body/*");
				if (systemList == null) {
					logger.error("话费交易查询，解析报文异常,响应报文：" + responseXML);
					throw new AppException("话费交易查询，解析报文异常,响应报文：" + responseXML);
				}

				Element element = null;
				String elementName = null;
				FroadMallXMLSystem systemXml = new FroadMallXMLSystem();
				for (int i = 0; i < systemList.size(); i++) {
					element = (Element)systemList.get(i);
					elementName = element.getName();
					if ("merchantNo".equals(elementName)) {
						systemXml.setMerchantNo(element.getTextTrim());
					} else if ("signType".equals(elementName)) {
						systemXml.setSignType(element.getTextTrim());
					} else if ("signMsg".equals(elementName)) {
						systemXml.setSignMsg(element.getTextTrim());
					} else if ("busiCode".equals(elementName)) {
						systemXml.setBusiCode(element.getText());
					} else if ("version".equals(elementName)) {
						systemXml.setVersion(element.getText());
					} else if ("responseTime".equals(elementName)) {
						systemXml.setRequestTime(element.getText());
					} else if ("respCode".equals(elementName)) {
						systemXml.setRespCode(element.getText());
					} else if ("respMsg".equals(elementName)) {
						systemXml.setRespMsg(element.getText());
					}
				}
				if ("0000".equals(systemXml.getRespCode()) && bodyList != null) {
					Element element1 = null;
					String elementName1 = null;
					String status = "2";
					for (int j = 0; j < bodyList.size(); j++) {
						element1 = (Element)bodyList.get(j);
						elementName1 = element1.getName();
						if ("status".equals(elementName1)) {
							status = element1.getTextTrim();
						}
					}
					if ("0".equals(status)) {
						hfczRes.setRespCode(HFCZCommand.CODE_SUCCESS);
						hfczRes.setRespMsg("话费充值成功");
					} else if ("3".equals(status)) {
						hfczRes.setRespCode(HFCZCommand.CODE_FAIL);
						hfczRes.setRespMsg("话费充值失败,请重新操作");
					} else {
						hfczRes.setRespCode(HFCZCommand.CODE_GOING);
						hfczRes.setRespMsg("话费充值正在处理中");
					}
					return hfczRes;
				} else {
					logger.error("话费充值查询异常,响应报文：" + responseXML);
					throw new AppException("话费充值查询异常,响应报文：" + responseXML);
				}
			} else {
				logger.error("话费充值查询HTTP响应异常，HTTP响应信息："+ response.getStatusLine());
				throw new AppException("话费充值查询异常,响应报文：" + responseXML);
			}
		} catch (Exception e) {
			logger.error("话费充值查询异常！请求报文：" + requestXML + "响应报文：" + responseXML,e);
			hfczRes.setRespCode(HFCZCommand.CODE_EXCEPTION);
			hfczRes.setRespMsg("话费交易查询失败,请稍后查询");
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return hfczRes;
	}

	public static void main(String[] args) throws AppException {
		HFCZ hfcz = new HFCZ();
		hfcz.setCZNo("18616136190");
		hfcz.setMoney(new BigDecimal("50"));
		hfcz.setSPID("ffttttt32132142324REWREW32");
		hfcz.setTranDate("2013-03-18 21:01:18");
		hfcz.setSalePrice(new BigDecimal("50"));
		hfcz=onLine(hfcz);
//		hfcz=checkParaCZNo(hfcz);
		
		System.out.println("code:"+hfcz.getRespCode()+"  msg:"+hfcz.getRespMsg());
		
		System.out.println("=============完成===========");
	}
}
