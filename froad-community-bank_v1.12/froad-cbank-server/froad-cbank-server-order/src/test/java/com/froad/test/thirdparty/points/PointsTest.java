package com.froad.test.thirdparty.points;


import com.alibaba.fastjson.JSONObject;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.thirdparty.request.points.PointsApiFunc;
import com.froad.thirdparty.request.points.impl.PointsApiFuncImpl;
import com.froad.util.PropertiesUtil;

public class PointsTest {

	public static void main(String[] args) {
		PropertiesUtil.load();
		
		
		PointsApiFunc func = new PointsApiFuncImpl();
//		func.queryParAccountPoints(new PointsReq(null, "u130814101", "1", "80160000011"));
		
//		PointsReq req = new PointsReq(
//				"100010002",//消耗方付通积分机构号的积分
//				"sadfasdf", 
//				99315d, //points
//				"T哪有女流氓",//accountMarked 
//				"80160000011",  //partnerNo
//				"由社区银行发起的方付通积分消费信息", //businessType
//				null,
//				""//accountId
//				);
////		req.setOrgPoints("10");
//		func.consumePoints(req);
//		
//		PointsReq req = new PointsReq("afads51545454", "11407151406530135", "1", "liuchao", "50326101441455", "80160000011", "", "退积分");
//		func.refundPoints(req);
		func.presentPoints(new PointsReq("100010002", "dvip52", "1", "65666", "赠送积分", "", "赠送积分", "10", "80160000011", ""));
		PointsReq req = new PointsReq();
//		req.setOrgNo("100010043");
//		req.setMobileNum("6229538106502396018");
//		req.setPartnerNo("80160000011");
//		System.out.println(JSONObject.toJSON(func.queryBankPointsByMobile(req)));
		
		
//		PointsReq req = new PointsReq();
//		req.setOrgNo("100010043");
//		req.setObjectNo("线下积分兑换");
//		req.setObjectDes("线下积分兑换");
//		req.setObjectType("1");
//		req.setBusinessType("ah_mobilepay_340101");
//		req.setMobileNum("11144444444");
//		req.setPartnerNo("80160000011");
//		req.setAccountMarkedType("1");
//		req.setAccountMarked("fft");
//		
//		
//		func.payPointsByMobile(req);
		
	}
}