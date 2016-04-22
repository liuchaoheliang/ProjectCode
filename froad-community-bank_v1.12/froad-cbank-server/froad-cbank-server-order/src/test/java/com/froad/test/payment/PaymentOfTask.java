package com.froad.test.payment;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.alibaba.fastjson.JSONObject;
import com.froad.logic.MemberInformationLogic;
import com.froad.logic.impl.MemberInformationLogicImpl;
import com.froad.thrift.service.PaymentService;
import com.froad.util.PropertiesUtil;
import com.froad.util.payment.Logger;

public class PaymentOfTask {

private final static int PORT = 8899;
	
	public static void main(String[] args) {
		PropertiesUtil.load();
		System.out.println("测试--> 连接支付服务  端口 : " + PORT);
		
		PaymentOfTask client = new PaymentOfTask();
		client.testDoPayOrder();
	}
	
	
	public void testDoPayOrder() {
		TTransport transport;
		try {
			transport = new TSocket("localhost", PORT);
			TProtocol protocol = new TBinaryProtocol(transport);
			PaymentService.Client client = new PaymentService.Client(protocol);
			transport.open();
			
			client.noticePaymentResult("<noticeFroadApi>	<order>		<orderID>054435540000</orderID>		<orderAmount>248.0</orderAmount>		<orderCurrency>1</orderCurrency>		<orderRemark><![CDATA[社区银行用户支付订单]]></orderRemark>		<stateCode>1</stateCode>		<orderAcquiringTime>20150617101945</orderAcquiringTime>		<orderCompleteTime>20150617101948</orderCompleteTime>		<remark><![CDATA[余额不足]]></remark>	</order>	<pay>		<payType>20</payType>		<payOrg>264</payOrg>		<payOrgNo>5061710194589321</payOrgNo>		<payAmount>248.0</payAmount>		<froadBillNo>2150617101945893</froadBillNo>	</pay>	<system>		<resultCode>0000</resultCode>		<partnerID>80160000011</partnerID>		<charset>1</charset>		<signType>1</signType>		<signMsg>xZ27zZKAOOrKiQ2YboG4vUa4o32Jg74kdUtq1O7CPZepTGQkc42x3oFy/BD0NZE0lZYrMvZWjGz44FN5XWJWvgRKS4nBGYXY1gXDDQjzIE6LM4Q1pegKhLOq6mfEImYMgUwPckR6nkgc4VUDz9GG95juUiUAFTmB3+mUORfG94I=</signMsg>	</system></noticeFroadApi>");
//			System.out.println(client.verifyPaymentResultForTask("013D268A0000"));
			
			transport.close();
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
}
