package com.froad.test.refund;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.froad.thrift.service.RefundService;
import com.froad.thrift.vo.refund.RefundProductVo;
import com.froad.thrift.vo.refund.RefundRequestVo;
import com.froad.thrift.vo.refund.RefundResponseVo;

public class RefundClient {
	
	private final static int PORT = 8899;
	
	public static void main(String args[]) {
		System.out.println("【测试连接退款服务 端口 : " + PORT + "】");
		RefundClient reClt = new RefundClient();
		RefundService.Client client = reClt.init();
		 
		reClt.testDoOrderRefund(client);
	}
	
	public RefundService.Client init() {
		RefundService.Client client = null;
		try {
			TTransport transport = new TSocket("localhost", PORT);
			TProtocol protocol = new TBinaryProtocol(transport);
			client = new RefundService.Client(protocol);
			transport.open();	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("【启动客户端失败】");
		}
		return client;
	}
	
	
	public void testDoOrderRefund(RefundService.Client client) {
		RefundRequestVo req = new RefundRequestVo();
		
		List<RefundProductVo> proList = new ArrayList<RefundProductVo>();	
		RefundProductVo pro1 = new RefundProductVo();
		pro1.setProductId("007e0d0f0000");
		pro1.setProductName("网店兑换1");
		pro1.setQuantity(3);
		proList.add(pro1);
		req.setProductList(proList);
		req.setClientId("1000");
		req.setOrderId("644810047488");
		req.setSubOrderId("644810506240");
		try {
			RefundResponseVo  res = client.doOrderRefund(req);
			System.out.println("请求退款服务接口响应码：" + res.getResultVo().getResultCode());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("【请求退款服务接口失败】");
		}
	}
}