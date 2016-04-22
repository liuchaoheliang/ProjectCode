package com.froad.CB.thirdparty;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

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
import com.froad.CB.common.constant.PayCommand;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.po.bill.*;
import com.froad.util.DateUtil;
import com.froad.util.RsaUtil;
import com.froad.util.String2Xml;
import com.froad.util.Util;
import com.froad.util.DES.DESUtil;

//import com.sun.xml.internal.ws.util.*;

/**
 * 类描述：与账单平台交互的工具类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Jan 6, 2013 2:20:51 PM
 */
public class BillFunc {

	private static final Logger logger = Logger.getLogger(BillFunc.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	// private static DecimalFormat decimalFormat=new DecimalFormat("0.##");
	// static{
	// decimalFormat.setMinimumFractionDigits(2);
	// }

	public static void main(String[] args) {
		/** 转账接口测试* */
		// Transfer transfer=new Transfer();
		// transfer.setTransferID("100001002");
		// transfer.setTransferAmount("1");
		// transfer.setPayerAccountNum("0000053868464012");
		// transfer.setPayerAccountName("上海方付通商务服务有限公司珠海分公司");
		// transfer.setPayeeAccountNum("0000053868464011");
		// transfer.setPayeeAccountName("小二");
		// try {
		// transfer(transfer);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		/** 校验接口测试* */
		// AccountCheck account=new AccountCheck();
		// account.setCheckType(TranCommand.CHECK_CARD);
		// account.setCheckContent("66601234|张三");
		// account.setCheckType(TranCommand.CHECK_PHONE);
		// account.setCheckContent("13111111111");
		// try {
		// System.out.println("======开始测试======");
		// AccountCheck checkRes=checkAccount(account);
		// System.out.println("======结束测试======");
		// System.out.println("是否有效："+checkRes);
		// if(checkRes!=null){
		// System.out.println("content: "+checkRes.getCheckResultContent());
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		/** 代收|代扣接口测试* */
		// PayRequest reqBean=new PayRequest();
		// reqBean.setPayOrg("000");
		// reqBean.setFromAccountName("张三");
		// reqBean.setFromAccountNum("0000053868464010");
		// reqBean.setToAccountName("上海方付通商务服务有限公司珠海分公司");
		// reqBean.setToAccountNum("0000053868464012");
		// reqBean.setPayerAmount("1");
		// reqBean.setPayeeAmount("1");
		// reqBean.setPayerType("1");//付款方为商户
		// reqBean.setPayerWay("2");//银行卡支付
		// reqBean.setPayeeType("3");//收款方为方付通
		// reqBean.setPayeeWay("2");//银行卡收款
		// reqBean.setOrderID("100009001");
		// reqBean.setRequestURL(PayCommand.DEDUCT_URL);
		// reqBean.setNoticeUrl(PayCommand.POINTS_NOTICE_URL);
		// try {
		// requestPay(reqBean);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		/** ******请求退款接口测试****** */
		RefundRequest req = new RefundRequest();
		req.setOrderId("1fulong051318401219");
		req.setRefundOrderID("1fulong051318401219");
		req.setRefundAmount("9991.00");
		req.setOrderAmount("9991.00");
		req.setRefundType(PayCommand.REFUND_ALL);
		req.setRefundReason("测试退款接口");
		req.setCombinePay(false);
		try {
			requestRefund(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 方法描述：转账接口
	 * 
	 * @param: Transfer(transferID,transferAmount,
	 *         payerAccountNum,payerAccountName,payeeAccountNum,payeeAccountName)
	 * @return: Transfer
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @throws Exception
	 * @time: Jan 6, 2013 3:01:11 PM
	 */
	public static Transfer transfer(Transfer transferBean) throws AppException,
			Exception {
		String transferID = transferBean.getTransferID();
		String partnerID = TranCommand.PARTNER_ID;
		String transferSubmitTime = DateUtil.currentTimeToString();
		String reqID = transferID + "fbu";
		String transferAmount = transferBean.getFormatAmount();
		String payer_m = transferBean.getPayerStr("|");
		String payer_msg = transferBean.getPayerStr("<");
		String payee_m = transferBean.getPayeeStr("|");
		String payee_msg = transferBean.getPayeeStr("<");

		Map<String, String> map = new TreeMap<String, String>();
		map.put("transferID", transferID);
		map.put("transferAmount", transferAmount);
		map.put("transferSubmitTime", transferSubmitTime);
		map.put("reqID", reqID);
		map.put("partnerID", partnerID);

		PrivateKey prikey = RsaUtil.initPrivateKey(TranCommand.RSA_PRIVATE_KEY,
				PayCommand.RSAPWD);
		String signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);

		// 15000
		// 0000053868464012<上海方付通商务服务有限公司珠海分公司>|150000000053868464011<珠海市农村信用合作社>
		DESUtil des = new DESUtil("froad");// 自定义密钥

		String requestXML = "<requestFroadApi>" + "<transfer>" + "<transferID>"
				+ transferID + "</transferID>" + "<transferType>"
				+ PayCommand.TRANSFER_TYPE_SINGLE + "</transferType>"
				+ "<transferOrg>" + TranCommand.TRANSFER_ORG + "</transferOrg>"
				+ "<transferAmount>" + transferAmount + "</transferAmount>"
				+ "<payerMember>" + payer_m + "</payerMember>"
				+ "<payerMemberMsg>" + des.encrypt(payer_msg)
				+ "</payerMemberMsg>" + "<payeeMember>" + payee_m
				+ "</payeeMember>" + "<payeeMemberMsg>"
				+ des.encrypt(payee_msg) + "</payeeMemberMsg>"
				+ "<transferCurrency>" + PayCommand.CURRENCY
				+ "</transferCurrency>"
				+ "<transferDisplay>方付通|生活平台</transferDisplay>"
				+ "<transferSubmitTime>" + transferSubmitTime
				+ "</transferSubmitTime>" + "<transferRemark></transferRemark>"
				+ "</transfer>" + "<system><reqID>" + reqID + "</reqID>"
				+ "<version>1</version><client>" + PayCommand.CLIENT_PC
				+ "</client>" + "<partnerID>" + partnerID + "</partnerID>"
				+ "<charset>" + PayCommand.CHARSET_UTF8 + "</charset>"
				+ "<signType>" + PayCommand.SIGNTYPE_RSA + "</signType>"
				+ "<signMsg>" + signMsg + "</signMsg>"
				+ "</system></requestFroadApi>";
		requestXML = requestXML.replaceAll(">null<", "><");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(SysCommand.TRANSFER_URL);
		post.setEntity(new StringEntity(requestXML, "UTF-8"));
		HttpResponse response = httpClient.execute(post);
		Transfer transfer = new Transfer();
		String responseXML = null;
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"));
			String line = null;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			responseXML = buffer.toString();
			logger.info("转账的响应报文：" + responseXML);
			Document doc = DocumentHelper.parseText(responseXML);
			Element root = doc.getRootElement();
			Iterator<?> firstIter = root.elementIterator();
			Element element = null;
			String name = null;
			while (firstIter.hasNext()) {
				element = (Element) firstIter.next();
				name = element.getName();
				if (name.equals("transferID")) {
					transfer.setTransferID(element.getTextTrim());
				} else if (name.equals("resultCode")) {
					transfer.setResultCode(element.getTextTrim());
				} else if (name.equals("remark")) {
					transfer.setRemark(element.getTextTrim());
				} else if (name.equals("signType")) {
					transfer.setSignType(element.getTextTrim());
				} else if (name.equals("signMsg")) {
					transfer.setSignMsg(element.getTextTrim());
				} else if (name.equals("remark")) {
					transfer.setRemark(element.getTextTrim());
				} else {
					logger.error("==========未知元素==========");
				}
			}
			Map<String, String> signMap = new TreeMap<String, String>();
			signMap.put("transferID", transfer.getTransferID());
			signMap.put("resultCode", transfer.getResultCode());

			PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY);
			boolean flag = RsaUtil.verifyPublicKey(signMap.toString(), transfer
					.getSignMsg(), pk);
			logger.info("校验结果:" + flag);
			if (flag) {
				if (PayCommand.RESPCODE_SUCCESS
						.equals(transfer.getResultCode())) {
					logger.info("转账成功！转账编号：" + transferID + "，金额："
							+ transferAmount);
					return transfer;
				} else {
					logger.error("转账失败，转账标识为：" + transferID + "，返回的结果码为："
							+ transfer.getResultCode() + "remark: "
							+ transfer.getRemark());
					return transfer;
				}
			} else {
				logger.error("=======转账的响应报文校验失败，转账标识为：" + transferID
						+ "，响应报文如下：\n" + responseXML);
				throw new AppException("响应报文校验不通过");
			}
		} else {
			logger.error("账单平台响应失败，转账标识为：" + transferID + "，响应信息："
					+ response.getStatusLine());
			throw new AppException("openapi响应失败，响应码："
					+ response.getStatusLine().getStatusCode());
		}
	}

	/**
	 * 方法描述：校验手机号或账户是否有效
	 * 
	 * @param: AccountCheck( checkType{1:校验手机号 2:校验银行账户}, checkContent{
	 *         checkType=1时,传 手机号码 如：15026409159 checkType=2时,传 账户号|账户名
	 *         如：66601234|张三 } )
	 * @return: AccountCheck
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jan 9, 2013 7:13:43 PM
	 */
	public static AccountCheck checkAccount(AccountCheck check)
			throws AppException, Exception {
		String reqTime = dateFormat.format(new Date());
		String reqID = "fbu" + reqTime;
		Map<String, String> map = new TreeMap<String, String>();
		map.put("checkType", check.getCheckType());
		map.put("checkContent", check.getCheckContent());
		map.put("checkTime", reqTime);
		map.put("reqID", reqID);
		map.put("partnerID", TranCommand.PARTNER_ID);

		PrivateKey prikey = RsaUtil.initPrivateKey(TranCommand.RSA_PRIVATE_KEY,
				PayCommand.RSAPWD);
		String signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
		String requestXML = "<requestFroadApi>" + "<checkParam>" + "<checkOrg>"
				+ check.getCheckOrg() + "</checkOrg>" + "<checkType>"
				+ check.getCheckType() + "</checkType>" + "<checkContent>"
				+ check.getCheckContent() + "</checkContent>" + "<checkTime>"
				+ reqTime + "</checkTime>" + "<checkRemark></checkRemark>"
				+ "</checkParam><system>" + "<reqID>" + reqID + "</reqID>"
				+ "<version>" + TranCommand.VERSION + "</version>" + "<client>"
				+ PayCommand.CLIENT_PC + "</client>" + "<partnerID>"
				+ TranCommand.PARTNER_ID + "</partnerID>" + "<charset>"
				+ PayCommand.CHARSET_UTF8 + "</charset>" + "<signType>"
				+ PayCommand.SIGNTYPE_RSA + "</signType>" + "<signMsg>"
				+ signMsg + "</signMsg></system></requestFroadApi>";
		requestXML = requestXML.replaceAll(">null<", "><");
		logger.info("请求报文:" + requestXML);

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(SysCommand.CHECK_URL);
		post.setEntity(new StringEntity(requestXML, "UTF-8"));
		long startTime = System.currentTimeMillis();
		HttpResponse response = httpClient.execute(post);
		long endTime = System.currentTimeMillis();
		logger.info("校验用时" + (endTime - startTime) + "ms");
		String responseXML = null;
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String line = null;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			responseXML = buffer.toString();
			logger.info("响应报文:" + responseXML);
			Document doc = DocumentHelper.parseText(responseXML);
			Element rootElm = doc.getRootElement();
			AccountCheck checkRes = new AccountCheck();
			Iterator<?> firstIter = rootElm.elementIterator();
			Element element = null;
			String name = null;
			while (firstIter.hasNext()) {
				element = (Element) firstIter.next();
				name = element.getName();
				if (name.equals("checkResultContent")) {
					checkRes.setCheckResultContent(element.getTextTrim());
				} else if (name.equals("resultCode")) {
					checkRes.setResultCode(element.getTextTrim());
				} else if (name.equals("checkResultCode")) {
					checkRes.setCheckResultCode(element.getTextTrim());
				} else if (name.equals("signType")) {
					checkRes.setSignType(element.getTextTrim());
				} else if (name.equals("signMsg")) {
					checkRes.setSignMsg(element.getTextTrim());
				} else if (name.equals("remark")) {
					checkRes.setRemark(element.getTextTrim());
				}
			}
			Map<String, String> signMap = new TreeMap<String, String>();
			signMap.put("resultCode", checkRes.getResultCode());
			PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY);
			boolean flag = RsaUtil.verifyPublicKey(signMap.toString(), checkRes
					.getSignMsg(), pk);
			if (flag) {
				if (TranCommand.SUCCESS.equals(checkRes.getCheckResultCode())) {
					logger.info("校验成功，有效手机号或账户！");
					return checkRes;
				} else {
					logger.error("无效手机号或账户，响应报文：" + responseXML);
					return checkRes;
				}
			} else {
				logger.error("响应报文校验不通过，响应报文：" + responseXML);
				throw new AppException("响应报文校验不通过");
			}
		} else {
			logger.error("与账单平台的HTTP通信异常！HTTP响应信息：" + response.getStatusLine());
			throw new AppException("openapi响应失败，响应码："
					+ response.getStatusLine().getStatusCode());
		}
	}

	/**
	 * 方法描述：请求支付(代收|代扣 二者的区别是requestURL不同)
	 * 
	 * @param: PayRequest(orderID,fromAccountName,fromAccountNum,toAccountName,
	 *         toAccountNum,fromPhone,toPhone,payerAmount,
	 *         payeeAmount,payerType,payerWay,payeeType,
	 *         payeeWay,payOrg,requestURL,noticeUrl)
	 * @param:银行卡支付时:必填账户名和账户号 贴膜卡支付时:必填手机号
	 * @param:orderID的组成：fbu+transId+x+payId 如：fbu1000001x2000001
	 * @return: PayResponse 请求成功返回此对象,否则返回null
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jan 11, 2013 10:40:14 AM
	 */
	public static PayResponse requestPay(PayRequest reqBean)
			throws AppException, Exception {
		DESUtil des = new DESUtil(PayCommand.DES_KEY);
		String payerMsg = des.encrypt(reqBean.getPayerMemberMsg());
		String payeeMsg = des.encrypt(reqBean.getPayeeMemberMsg());
		String payerAmount = Util.formatMoney(reqBean.getPayerAmount())
				.toString();
		String payeeAmount = Util.formatMoney(reqBean.getPayeeAmount())
				.toString();
		String remark = reqBean.getOrderRemark();
		String orderId = reqBean.getOrderID();
		String orderDisplay = reqBean.getOrderDisplay();
		if (orderDisplay == null || "".equals(orderDisplay)) {
			orderDisplay = "分分通消费";
		}
		String payersBody = "<payer>" + "<payerType>" + reqBean.getPayerType()
				+ "</payerType>" + "<payerWay>" + reqBean.getPayerWay()
				+ "</payerWay>" + "<payerMember>" + reqBean.getPayerMember()
				+ "</payerMember>" + "<payerAmount>" + payerAmount
				+ "</payerAmount>" + "<payerMemberMsg>" + payerMsg
				+ "</payerMemberMsg>" + "</payer>";
		String payeesBody = "<payee>" + "<payeeType>" + reqBean.getPayeeType()
				+ "</payeeType>" + "<payeeWay>" + reqBean.getPayeeWay()
				+ "</payeeWay>" + "<payeeMember>" + reqBean.getPayeeMember()
				+ "</payeeMember>" + "<payeeAmount>" + payeeAmount
				+ "</payeeAmount>" + "<payeeMemberMsg>" + payeeMsg
				+ "</payeeMemberMsg>" + "</payee>";

		String orderSubmitTime = DateUtil.currentTimeToString();
		String reqID = "fbu" + orderSubmitTime;
		Map<String, String> map = new TreeMap<String, String>();
		map.put("orderID", orderId);
		map.put("orderAmount", payerAmount);
		map.put("orderSubmitTime", orderSubmitTime);
		map.put("reqID", reqID);
		map.put("partnerID", TranCommand.PARTNER_ID);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(reqBean.getRequestURL());
		PayResponse payRes = new PayResponse();
		String requestXML = null;
		String responseXML = null;
		try {
			PrivateKey prikey = RsaUtil.initPrivateKey(
					TranCommand.RSA_PRIVATE_KEY, PayCommand.RSAPWD);
			String signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			requestXML = "<requestFroadApi><order><orderID>" + orderId
					+ "</orderID><orderType>" + PayCommand.ORDER_TYPE_CASH
					+ "</orderType><orderAmount>" + payerAmount
					+ "</orderAmount><orderCurrency>" + PayCommand.CURRENCY
					+ "</orderCurrency><orderDisplay>"
					+ PayCommand.MERCHANTNAME + "|" + orderDisplay
					+ "</orderDisplay><orderSubmitTime>" + orderSubmitTime
					+ "</orderSubmitTime><orderFailureTime></orderFailureTime>"
					+ "<orderRemark>" + remark + "</orderRemark>"
					+ "</order><pay><payType>" + PayCommand.PAY_TYPE_FILM
					+ "</payType>" + "<payOrg>" + reqBean.getPayOrg()
					+ "</payOrg><payers>" + payersBody + "</payers><payees>"
					+ payeesBody + "</payees>" + "</pay>" + "<notification>"
					+ "<noticeUrl>" + reqBean.getNoticeUrl() + "</noticeUrl>"
					+ "<returnUrl></returnUrl>"
					+ "</notification><system><reqID>" + reqID
					+ "</reqID><version>" + PayCommand.VERSION + "</version>"
					+ "<client>" + PayCommand.CLIENT_PC + "</client>"
					+ "<partnerID>" + TranCommand.PARTNER_ID + "</partnerID>"
					+ "<charset>" + PayCommand.CHARSET_UTF8 + "</charset>"
					+ "<signType>" + PayCommand.SIGNTYPE_RSA + "</signType>"
					+ "<signMsg>" + signMsg + "</signMsg>"
					+ "</system></requestFroadApi>";
			requestXML = requestXML.replaceAll(">null<", "><");
			logger.info("支付请求报文： " + requestXML);
			post.setEntity(new StringEntity(requestXML, "UTF-8"));
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			long endTime = System.currentTimeMillis();
			logger.info("向支付系统发送支付请求,连接用时" + (endTime - startTime) + "ms");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent(), "UTF-8"));
				String line = null;
				StringBuffer buffer = new StringBuffer();
				while ((line = br.readLine()) != null) {
					buffer.append(line);
				}
				responseXML = buffer.toString();
				logger.info("支付请求响应报文:" + responseXML);
				Document doc = DocumentHelper.parseText(responseXML);
				Element root = doc.getRootElement();
				Element element = null;
				String elementName = null;
				Iterator<?> iter = root.elementIterator();
				while (iter.hasNext()) {
					element = (Element) iter.next();
					elementName = element.getName();
					if (elementName.equals("orderID")) {
						payRes.setOrderId(element.getTextTrim());
					} else if (elementName.equals("froadBillNo")) {
						payRes.setFroadBillNo(element.getTextTrim());
					} else if (elementName.equals("resultCode")) {
						payRes.setResultCode(element.getTextTrim());
					} else if (elementName.equals("signType")) {
						payRes.setSignType(element.getTextTrim());
					} else if (elementName.equals("signMsg")) {
						payRes.setSignMsg(element.getTextTrim());
					} else if (elementName.equals("remark")) {
						payRes.setRemark(element.getTextTrim());
					}

				}

				Map<String, String> signMap = new TreeMap<String, String>();
				signMap.put("orderID", payRes.getOrderId());
				signMap.put("resultCode", payRes.getResultCode());

				PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY);
				boolean flag = RsaUtil.verifyPublicKey(signMap.toString(),
						payRes.getSignMsg(), pk);
				logger.info("响应报文校验结果:" + flag);
				if (flag) {// 响应报文校验通过
					if (PayCommand.RESPCODE_SUCCESS.equals(payRes
							.getResultCode())) {// 支付请求成功
						logger.info("支付请求发送成功，等待支付......");
						return payRes;
					} else {
						logger.error("支付请求失败，结果码：" + payRes.getResultCode());
						logger.error("支付请求报文:" + requestXML + " 响应报文："
								+ responseXML);
						return payRes;
					}
				} else {
					logger.error("响应报文校验不通过，支付请求失败，请求报文:" + requestXML
							+ " 响应报文：" + responseXML);
					throw new AppException("响应报文校验不通过");
				}
			} else {// 发送支付请求失败
				logger.error("支付请求发送失败，HTTP响应信息： " + response.getStatusLine());
				logger.error("支付请求报文： " + requestXML);
				throw new AppException("openapi响应失败，响应码:"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			logger.error("发送支付请求时出现异常，支付请求报文：" + requestXML, e);
			throw new AppException("响应报文解析失败");
		} finally {// 释放连接
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 方法描述：发送退款请求
	 * 
	 * @param: RefundRequest(orderId,refundOrderID,refundAmount,refundType,refundReason,isCombinePay,[orderSupplier])
	 * @param: orderId的组成：fbu+transId+x+payId 如：fbu1000001x2000001
	 * @param: refundOrderID的组成：KeyGenerator生成
	 * @return: RefundResponse
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 23, 2013 1:32:35 PM
	 */
	public static RefundResponse requestRefund(RefundRequest req)
			throws AppException, Exception {
		String refundOrderID = req.getRefundOrderID();
		String orderId = req.getOrderId();
		String refundAmount = req.getRefundAmount();
		String orderAmount = req.getOrderAmount();
		String refundType = req.getRefundType();
		String refundReason = req.getRefundReason();
		Boolean combinePay=req.getCombinePay();
		String orderSupplier=req.getOrderSupplier();
		String refundUrl=combinePay?SysCommand.REFUND_COMBINE_URL:SysCommand.REFUND_URL;
		String refundOrderSubmitTime = DateUtil.currentTimeToString();
		String reqID = "fbu" + refundOrderSubmitTime;

		logger.info("退款订单号:" + refundOrderID + "\t 退款金额:" + refundAmount);

		Map<String, String> map = new TreeMap<String, String>();
		map.put("refundOrderID", refundOrderID);
		map.put("orderID", orderId);
		map.put("orderAmount", orderAmount);
		map.put("refundAmount", refundAmount);
		map.put("refundType", refundType);
		map.put("noticeUrl", SysCommand.REFUND_NOTICE_URL);
		map.put("reqID", reqID);
		map.put("partnerID", TranCommand.PARTNER_ID);
		map.put("signType", PayCommand.SIGNTYPE_RSA);
		if(combinePay){
			map.put("orderSupplier", orderSupplier);
		}

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(refundUrl);
		String requestXML = null;
		String responseXML = null;
		try {
			PrivateKey prikey = RsaUtil.initPrivateKey(
					TranCommand.RSA_PRIVATE_KEY, PayCommand.RSAPWD);
			String signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);

			String2Xml bodyXml = new String2Xml("requestFroadApi");
			bodyXml.addElementName("refund");
			bodyXml.append("refundOrderID", refundOrderID);
			bodyXml.append("orderID", orderId);
			bodyXml.append("orderAmount", orderAmount);
			bodyXml.append("refundAmount", refundAmount);
			bodyXml.append("refundType", refundType);
			bodyXml.append("orderSupplier", orderSupplier);
			bodyXml.append("refundReason", refundReason);
			bodyXml.addElementName("notification");
			bodyXml.append("noticeUrl", SysCommand.REFUND_NOTICE_URL);
			bodyXml.addElementName("system");
			bodyXml.append("reqID", reqID);
			bodyXml.append("version", PayCommand.VERSION);
			bodyXml.append("partnerID", TranCommand.PARTNER_ID);
			bodyXml.append("charset", PayCommand.CHARSET_UTF8);
			bodyXml.append("signType", PayCommand.SIGNTYPE_RSA);
			bodyXml.append("signMsg", signMsg);
			requestXML = bodyXml.toXMLString();
			logger.info("退款请求地址： "+refundUrl+" 退款请求报文: " + requestXML);
			post.setEntity(new StringEntity(requestXML, "UTF-8"));
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			long endTime = System.currentTimeMillis();
			logger.info("发送退款请求用时" + (endTime - startTime) + "ms");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				String line = null;
				StringBuffer buffer = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				responseXML = buffer.toString();
				logger.info("退款的响应报文:" + responseXML);
				Document doc = DocumentHelper.parseText(responseXML);
				Element root = doc.getRootElement();
				Iterator<?> iter = root.elementIterator();
				Element child = null;
				String name = null;
				RefundResponse refundRes = new RefundResponse();
				while (iter.hasNext()) {
					child = (Element) iter.next();
					name = child.getName();
					if (name.equals("refundOrderID")) {
						refundRes.setRefundOrderID(child.getTextTrim());
					} else if (name.equals("resultCode")) {
						refundRes.setResultCode(child.getTextTrim());
					} else if (name.equals("trackNo")) {
						refundRes.setTrackNo(child.getTextTrim());
					} else if (name.equals("signType")) {
						refundRes.setSignType(child.getTextTrim());
					} else if (name.equals("signMsg")) {
						refundRes.setSignMsg(child.getTextTrim());
					} else if (name.equals("remark")) {
						refundRes.setRemark(child.getTextTrim());
					}
				}

				Map<String, String> signMap = new TreeMap<String, String>();
				signMap.put("refundOrderID", refundRes.getRefundOrderID());
				signMap.put("resultCode", refundRes.getResultCode());

				PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY);
				boolean flag = RsaUtil.verifyPublicKey(signMap.toString(),
						refundRes.getSignMsg(), pk);
				logger.info("响应报文校验结果:" + flag);
				if (flag) {// 响应报文校验通过
					if (PayCommand.RESPCODE_SUCCESS.equals(refundRes
							.getResultCode())) {// 退款请求成功
						logger.info("退款请求发送成功，等待退款......");
						return refundRes;
					} else {
						logger.error("退款请求失败，结果码：" + refundRes.getResultCode());
						logger.error("退款请求报文:" + requestXML);
						logger.error("退款响应报文：" + responseXML);
						return refundRes;
					}
				} else {
					logger.error("响应报文校验不通过，退款请求失败，请求报文:" + requestXML
							+ " 响应报文：" + responseXML);
					throw new AppException("响应报文校验不通过");
				}
			} else {// 发送退款请求失败
				logger.error("退款请求发送失败，HTTP响应信息： " + response.getStatusLine());
				logger.error("退款请求报文： " + requestXML);
				throw new AppException("openapi响应失败，响应码："
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			logger.error("发送退款请求时出现异常，请求报文：" + requestXML, e);
			throw new AppException("报文解析失败");
		} finally {// 释放连接
			httpClient.getConnectionManager().shutdown();
		}
	}

	
	
	/**
	  * 方法描述：发起合并支付请求
	  * @param: orderDetailList(orderID,orderAmount,orderSupplier,orderDisplay)、
	            orderType、totalAmount、orderRemark、payType、client、payOrg、
	            payerMember、payDirect、orderDisplay
	            orderID的组成：transId+x+payId 如：fbu1000001x2000001
	  * @return: CombineResponse 成功时返回此对象，否则抛出AppException
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 15, 2014 2:50:27 PM
	  */
	public static CombineResponse combineOrder(CombineRequest reqBean)
			throws AppException {
		String reqId = "fbu" + dateFormat.format(new Date());

		String orderSubmitTime = DateUtil.currentTimeToString();

		String2Xml bodyXml = new String2Xml("requestFroadApi");

		StringBuilder orderIdEnc = new StringBuilder();
		StringBuilder orderAmountEnc = new StringBuilder();
		StringBuilder orderSupplierEnc = new StringBuilder();
		StringBuilder detailsBuilder=new StringBuilder();

		//组装订单明细参数
		for (OrderDetail order : reqBean.getOrderDetailList()) {
			String2Xml details = new String2Xml("orderDetail");
			String orderAmount = Util.formatMoney(order.getOrderAmount())
					.toString();

			details.append("orderID", order.getOrderID());
			details.append("orderAmount", orderAmount);
			details.append("orderSupplier", order.getOrderSupplier());
			String orderDisplay = order.getOrderDisplay();
			if (orderDisplay == null || "".equals(orderDisplay)) {
				orderDisplay = "分分通消费";
			}
			details.append("orderDisplay", orderDisplay);
			orderIdEnc.append(order.getOrderID());
			orderAmountEnc.append(orderAmount);
			orderSupplierEnc.append(order.getOrderSupplier());
			
			detailsBuilder.append(details.toXMLString());
		}
		bodyXml.append("orderDetails", detailsBuilder.toString());
		//组装订单参数
		bodyXml.addElementName("order");
		bodyXml.append("orderType", reqBean.getOrderType());
		String totalAmount = Util.formatMoney(reqBean.getTotalAmount()).toString();
		bodyXml.append("totalAmount", totalAmount);
		bodyXml.append("orderCurrency", PayCommand.CURRENCY);
		bodyXml.append("orderSubmitTime", orderSubmitTime);
		bodyXml.append("orderFailureTime", "");
		bodyXml.append("orderRemark", reqBean.getOrderRemark());
		bodyXml.append("orderDisplay", reqBean.getOrderDisplay());
		//组装支付参数
		bodyXml.addElementName("pay");
		bodyXml.append("payType", reqBean.getPayType());
		bodyXml.append("mobileToken", reqBean.getMobileToken());
		bodyXml.append("fftSignNo", reqBean.getFftSignNo());
		bodyXml.append("payOrg", reqBean.getPayOrg());
		bodyXml.append("payerMember", reqBean.getPayerMember());
		bodyXml.append("payerIdentity", reqBean.getPayerIdentity());
		bodyXml.append("payeeMember", reqBean.getPayeeMember());
		bodyXml.append("payeeIdentity", reqBean.getPayeeIdentity());
		bodyXml.append("payDirect", reqBean.getPayDirect());
		//组装通知参数
		bodyXml.addElementName("notification");
		bodyXml.append("noticeUrl", SysCommand.COMBINE_ORDER_NOTICE_URL);
		bodyXml.append("returnUrl", "");
		//组装系统参数数据
		bodyXml.addElementName("system");
		bodyXml.append("reqID", reqId);
		bodyXml.append("partnerID", TranCommand.PARTNER_ID);
		bodyXml.append("charset", PayCommand.CHARSET_UTF8);
		bodyXml.append("signType", PayCommand.SIGNTYPE_RSA);
		bodyXml.append("client", reqBean.getClient());
		bodyXml.append("version", PayCommand.VERSION);
		
		//组装加签map
		Map<String, String> encMap = new TreeMap<String, String>();
		encMap.put("orderID", orderIdEnc.toString());
		encMap.put("orderAmount", orderAmountEnc.toString());
		encMap.put("orderSupplier", orderSupplierEnc.toString());
		encMap.put("orderType", reqBean.getOrderType());
		encMap.put("totalAmount", totalAmount);
		encMap.put("orderSubmitTime", orderSubmitTime);
		encMap.put("mobileToken", reqBean.getMobileToken());
		encMap.put("fftSignNo", reqBean.getFftSignNo());
		encMap.put("payOrg", reqBean.getPayOrg());
		encMap.put("payerMember", reqBean.getPayerMember());
		encMap.put("payeeMember", "");
		encMap.put("payDirect", reqBean.getPayDirect());
		encMap.put("reqID", reqId);
		encMap.put("noticeUrl", SysCommand.COMBINE_ORDER_NOTICE_URL);
		encMap.put("partnerID", TranCommand.PARTNER_ID);
		encMap.put("charset", PayCommand.CHARSET_UTF8);
		encMap.put("signType", PayCommand.SIGNTYPE_RSA);
		//将map中的null值转化为空字符串
		Iterator<String> encIter=encMap.keySet().iterator();
		String key=null,value=null;
		while(encIter.hasNext()){
			key=encIter.next();
			value=encMap.get(key);
			if(value==null){
				encMap.put(key, "");
			}
		}

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(SysCommand.COMBINE_ORDER_REQUEST_URL);
		CombineResponse combineResponse = new CombineResponse();
		String requestXML = null;
		String responseXML = null;
		try {
			PrivateKey prikey = RsaUtil.initPrivateKey(
					TranCommand.RSA_PRIVATE_KEY, PayCommand.RSAPWD);
			String signMsg = RsaUtil.signPrivateKey(encMap.toString(), prikey);
			bodyXml.append("signMsg", signMsg);
			
			requestXML = bodyXml.toXMLString().replaceAll(">null<", "><");
			logger.info("订单合并请求报文： " + requestXML);
			
			post.setEntity(new StringEntity(requestXML, "UTF-8"));
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			long endTime = System.currentTimeMillis();
			logger.info("向支付系统发送合并支付请求,连接用时" + (endTime - startTime) + "ms");
			if (response.getStatusLine().getStatusCode() == org.apache.commons.httpclient.HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent(), "UTF-8"));
				String line = null;
				StringBuffer buffer = new StringBuffer();
				while ((line = br.readLine()) != null) {
					buffer.append(line);
				}
				responseXML = buffer.toString();
				logger.info("合并订单支付响应报文:" + responseXML);
				Document doc = DocumentHelper.parseText(responseXML);
				Element root = doc.getRootElement();
				Element element = null;
				String elementName = null;
				Iterator<?> iter = root.elementIterator();
				while (iter.hasNext()) {
					element = (Element) iter.next();
					elementName = element.getName();
					if (elementName.equals("orderID")) {
						combineResponse.setOrderID(element.getTextTrim());
					} else if (elementName.equals("froadBillNo")) {
						combineResponse.setFroadBillNo(element.getTextTrim());
					} else if (elementName.equals("resultCode")) {
						combineResponse.setResultCode(element.getTextTrim());
					} else if (elementName.equals("signType")) {
						combineResponse.setSignType(element.getTextTrim());
					} else if (elementName.equals("signMsg")) {
						combineResponse.setSignMsg(element.getTextTrim());
					} else if (elementName.equals("paymentURL")) {
						combineResponse.setPaymentURL(element.getTextTrim());
					}

				}

				Map<String, String> signMap = new TreeMap<String, String>();
				signMap.put("orderID", combineResponse.getOrderID());
				signMap.put("paymentURL", combineResponse.getPaymentURL());
				signMap.put("froadBillNo", combineResponse.getFroadBillNo());
				signMap.put("resultCode", combineResponse.getResultCode());

				PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY);
				boolean flag = RsaUtil.verifyPublicKey(signMap.toString(),
						combineResponse.getSignMsg(), pk);
				logger.info("合并订单响应报文校验结果:" + flag);

				if (flag) {// 响应报文校验通过
					if (PayCommand.RESPCODE_SUCCESS.equals(combineResponse
							.getResultCode())) {// 支付请求成功
						logger.info("合并订单请求成功，等待回调......");
						return combineResponse;
					} else {
						logger.error("合并订单请求失败，结果码："
								+ combineResponse.getResultCode());
						logger.error("合并订单请求报文:" + requestXML + " 响应报文："
								+ responseXML);
						return combineResponse;
					}
				} else {
					logger.error("响应报文校验不通过，合并订单请求失败，请求报文:" + requestXML
							+ " 响应报文：" + responseXML);
					throw new AppException("响应报文校验不通过");
				}
			} else {// 发送支付请求失败
				logger.error("合并订单请求发送失败，HTTP响应信息： "+ response.getStatusLine());
				logger.error("合并订单请求报文： " + requestXML);
				throw new AppException("openapi响应失败，响应码:"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			logger.error("发送合并订单请求时出现异常，支付请求报文：" + requestXML, e);
			throw new AppException("响应报文解析失败");
		} finally {// 释放连接
			httpClient.getConnectionManager().shutdown();
		}
	}

}
