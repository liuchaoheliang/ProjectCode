package com.froad.test.thirdparty.openapi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.dto.request.openapi.OpenApiOrderDetail;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.BillOrderType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.CheckType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.Client;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.PayDirect;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.PayType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.RefundType;
import com.froad.thirdparty.dto.request.openapi.QueryParamApiReq;
import com.froad.thirdparty.request.openapi.OpenApiFunc;
import com.froad.thirdparty.request.openapi.impl.OpenApiFuncImpl;
import com.froad.util.DateUtil;
import com.froad.util.PropertiesUtil;

public class OpenApiTest {
	
	public static void main(String[] args) {
		PropertiesUtil.load();
		
		OpenApiFunc api = new OpenApiFuncImpl();
		
//		List<OpenApiOrderDetail> orderDetails = new ArrayList<OpenApiOrderDetail>();
//		OpenApiOrderDetail detail = new OpenApiOrderDetail(new Date().getTime() + "", "1", "80160000018", "社区银行订单");
//		orderDetails.add(detail);
//		OpenApiReq req = new OpenApiReq(
//				orderDetails, 
//				BillOrderType.COMBINE,
//				"1", 
//				PayType.FAST_PAY, 
//				Client.ANDROID,
//				"643", 
//				"13100000132", 
//				PayDirect.F_MERCHANT,
//				"这区银行订单", 
//				"80160000018", 
//				"orderRemark", 
//				"", 
//				"",//TODO 第三方支付处理完毕的跳转地址 
//				DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, new Date()), 
//				"",
//				"10" //消耗积分的是会员
//				);
//		api.combineOrder(req);
	
	
//		OpenApiReq req = new OpenApiReq("581294781998055624","581294781998055424","5",
//				"5", RefundType.ALL, "80160000018",
//				"系统自动退款", "80160000018","http://192.168.19.123:7002/TestForWar/refund.api");
//		try {
//			
//			api.refund(req);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		
		
//		QueryParamApiReq req = new QueryParamApiReq(OpenApiCommand.QUERY_SINGLE, OpenApiCommand.ORDER_TYPE_TRANS,"581294781998055424", "",
//				"80160000018", "100"// 代表PC
//         );
//		api.query(req);
		
//		
//		OpenApiReq req = new OpenApiReq("264", "22413464", "测试上海", "王尼玛", "6666666666666666666", "0", Client.PC,"80160000011");
//		api.whiteListSet(req);
		
		OpenApiReq req = new OpenApiReq("", "", "",Client.ANDROID,"10000004800");
//		api.employeeCodeVerify(req);
		
		req = new OpenApiReq("664", "隔壁老王", "6222150014589625478","10000004800");
		System.out.println("auditStatusQuery返回结果: " + JSON.toJSONString(api.auditStatusQuery(req), true));
		
		
	}
}
