package com.froad.test.payment;

import java.util.Date;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.alibaba.fastjson.JSONObject;
import com.froad.enums.CashType;
import com.froad.enums.PaymentMethod;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.vo.payment.DoPayOrdersVoReq;
import com.froad.thrift.vo.payment.PaymentExceptionType;
import com.froad.thrift.vo.payment.PaymentQueryExcetionVo;
import com.froad.thrift.vo.payment.PaymentQueryVo;
import com.froad.util.PropertiesUtil;


public class PaymentClient {

	private final static int PORT = 8899;
	
	public static void main(String[] args) {
		PropertiesUtil.load();
		System.out.println("测试--> 连接支付服务  端口 : " + PORT);
		PaymentClient client = new PaymentClient();
		client.testDoPayOrder();
	}
	
	public void testDoPayOrder() {
		TTransport transport;
		try {
			transport = new TSocket("localhost", PORT);
			TProtocol protocol = new TBinaryProtocol(transport);
			PaymentService.Client client = new PaymentService.Client(protocol);
			transport.open();
				
			
			client.noticePaymentResult("<noticeFroadApi>	<order>		<orderID>041E02868000</orderID>		<orderAmount>248.0</orderAmount>		<orderCurrency>1</orderCurrency>		<orderRemark><![CDATA[??????????]]></orderRemark>		<stateCode>0</stateCode>		<orderAcquiringTime>20150527150426</orderAcquiringTime>		<orderCompleteTime>20150527150429</orderCompleteTime>		<remark><![CDATA[????]]></remark>	</order>	<pay>		<payType>20</payType>		<payOrg>264</payOrg>		<payOrgNo>5052715042630921</payOrgNo>		<payAmount>248.0</payAmount>		<froadBillNo>2150527150426309</froadBillNo>	</pay>	<system>		<resultCode>0000</resultCode>		<partnerID>80160000011</partnerID>		<charset>1</charset>		<signType>1</signType>		<signMsg>awK3KzmjLpeQ4xHlY/f8q7MBHFzIIgN6lDaClUAFb/fJa2m+h3hjottcMXWeeKrwSpGbUmPZYggn5ol0dB41L3a7IrZJM+/4rkm0+QZijCKkdFy3eRkCMWE/LIK1JV7GZoMRQqe3VSDbu+gv7y6vs+R9muBnH9+9BGyCk3f3DGw=</signMsg>	</system></noticeFroadApi>");
			
			transport.close();
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
}
