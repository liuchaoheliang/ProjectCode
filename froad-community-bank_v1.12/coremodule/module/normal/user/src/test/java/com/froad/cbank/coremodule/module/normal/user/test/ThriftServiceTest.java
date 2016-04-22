package com.froad.cbank.coremodule.module.normal.user.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.RefundService;
import com.froad.thrift.vo.refund.RefundProductVo;
import com.froad.thrift.vo.refund.RefundRequestVo;
import com.froad.thrift.vo.refund.RefundResponseVo;


public class ThriftServiceTest {

	//迭代环境
	public static String SUPPORT_URL = "10.43.2.239";
	public static int SUPPORT_PORT = 15701;
	
	public static String ORDER_URL = "10.43.2.239";
	public static int ORDER_PORT = 15501;
	
	
	public static String BANK_URL = "10.43.2.238";
	public static int BANK_PORT = 15101;
	
	public static String Good_URL = "10.43.1.123";
	public static int Good_PORT = 15401;
	
	
	public static String XY_URL = "10.24.234.123";
	public static int XY_PORT = 15501;
	
	public static void main(String[] args) {
		try {
/*
			TSocket transport = new TSocket(ORDER_URL, ORDER_PORT);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MemberSecurityService");
			MemberSecurityService.Iface msService = new MemberSecurityService.Client(mp);
			transport.open();
			
			List<UserEngineQuestionVo> list = new ArrayList<UserEngineQuestionVo>();
			UserEngineQuestionVo vo = new UserEngineQuestionVo();
			vo.setQuestionID(33345L);
			vo.setAnswer("asd");
			list.add(vo);
			
			ResultVo result = msService.veryfyMemberQuestion(40000000893L, list);
			
			
			//返回信息处理
			String errorMessage = null;
			try{
				JSONObject json = JSON.parseObject(result.getResultDesc());
				errorMessage = json.getString("msg");
			}catch(Exception e){
				LogCvt.error("安全问题结果信息转换json异常",e);
				errorMessage = "安全问题答案错误";
			}
			System.out.println(errorMessage);
			transport.close();*/
			
			/*TSocket transport = new TSocket(BANK_URL, BANK_PORT);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"ClientService");
			ClientService.Iface clientService = new ClientService.Client(mp);
			transport.open();
			
			ClientVo  clientVo = clientService.getClientById("anhui");
			
			System.out.println(JSON.toJSONString(clientVo,true));*/
			
			
			TSocket transport = new TSocket(XY_URL, XY_PORT);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"RefundService");
			RefundService.Iface refundService = new RefundService.Client(mp);
			transport.open();
			
			List<RefundProductVo> productVos=new ArrayList<RefundProductVo>();
			
			RefundProductVo productVo = new RefundProductVo();
			productVo.setProductId("09D304F38000");
			productVo.setQuantity(3);
			productVos.add(productVo);
			
			RefundRequestVo refundRequestVo = new RefundRequestVo();
			refundRequestVo.setSubOrderId("09F8BF148000");
			refundRequestVo.setProductList(productVos);
			refundRequestVo.setClientId("anhui");
			refundRequestVo.setOption("2");
			refundRequestVo.setReason("联调");
			
			RefundResponseVo  result = refundService.doOrderRefund(refundRequestVo);
			
			System.out.println(JSON.toJSONString(result, true));
			
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
