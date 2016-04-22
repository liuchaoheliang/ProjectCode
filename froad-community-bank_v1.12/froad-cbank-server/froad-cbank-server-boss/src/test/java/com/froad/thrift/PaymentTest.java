package com.froad.thrift;
import java.util.Date;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.ForEach;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.thrift.service.ExceptionOfPaymentService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.payment.BossPaymentVo;
import com.froad.thrift.vo.payment.BossQueryConditionVo;


public class PaymentTest {

	
static{
		
		System.setProperty("CONFIG_PATH", "./config");
	}
	public static void main(String[] args) throws TException{
		
		TSocket transport = new TSocket("localhost", 16001);
		transport.open();
        TBinaryProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol,ExceptionOfPaymentService.class.getSimpleName());
        ExceptionOfPaymentService.Client service = new ExceptionOfPaymentService.Client(multiplexedProtocol);

//        BossPaymentQueryExceptionVo queryVo = new BossPaymentQueryExceptionVo();
//        PageVo pageVo = new PageVo();
//        pageVo.setPageSize(200);
//        pageVo.setPageNumber(1);
//        String clientId = "anhui";
//        BossPaymentExceptionType exceptionType = BossPaymentExceptionType.PAY_CASH_FAILD_AUTO_REFUND_POINT_FAILED;
//        BossPaymentType type = BossPaymentType.PRESENT_FROAD_POINT;
//        queryVo.setPageVo(pageVo);
//        queryVo.setClientId(clientId);
//        queryVo.setExceptionType(exceptionType);
//        queryVo.setType(type);
//        BossPaymentQueryPageVo paymentvo = service1.queryPaymentOfException(queryVo);
//        for(BossPaymentQueryVo paymentVo: paymentvo.getPaymentQueryVos()){
//        	System.out.println("支付状态,payment_status:" + paymentVo.getPaymentStatus() + "//支付原因,payment_reason：" + paymentVo.getPaymentReason()+
//        			"//支付类型,payment_type：" + paymentVo.getPaymentType() + "//remark：" + paymentVo.getRemark() + "//结果描述,desc：" + paymentVo.getResultDesc());
//        }
        
        BossQueryConditionVo condition = new BossQueryConditionVo();
        PageVo pageVo = new PageVo();
        pageVo.setPageSize(10);
        pageVo.setFirstRecordTime(new Date().getTime());
        condition.setPageVo(pageVo);
        condition.setExceptionType("3");
//        List<BossPaymentVo> vos = service.queryExceptionPaymentByPage(condition).getPayments();
//        for (BossPaymentVo bossPaymentVo : vos) {
//			System.out.println(bossPaymentVo.getCreateTime());
//		}
//        System.out.println(JSONObject.toJSONString(service.queryExceptionPaymentByPage(condition)));
        
//        System.out.println(JSONObject.toJSONString(service.queryOfRefundDetial("0FD378008000")));
        System.out.println(JSONObject.toJSONString(service.retryOfReturn("0C308B740000")));
        
        transport.close();
        
	}
}

