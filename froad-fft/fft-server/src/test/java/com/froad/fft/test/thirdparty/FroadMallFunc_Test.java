package com.froad.fft.test.thirdparty;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.froad.fft.persistent.common.enums.LotteryNo;
import com.froad.fft.thirdparty.dto.request.froadmall.FroadMallReq;
import com.froad.fft.thirdparty.request.froadmall.impl.FroadMallFuncImpl;

public class FroadMallFunc_Test {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:context/**/*.xml");
		FroadMallFuncImpl froadMallFuncImpl = (FroadMallFuncImpl) context.getBean("froadMallFuncImpl");
		
//		System.out.println("充值手机号码测试");
//		System.out.println(froadMallFuncImpl.onlineRecharge(new FroadMallReq("50", "13527459070", "50", "201301020", "2013-03-18 21:01:18")).getResultDesc());
//		System.out.println("充值手机号码测试");
		
		System.out.println("查询彩票期号测试");
		System.out.println(froadMallFuncImpl.queryLotteryPeriods(new FroadMallReq(LotteryNo.FC_SSQ)).getResultDesc());
		System.out.println("查询彩票期号测试");
//		
//		System.out.println("购买彩票测试");
//		System.out.println(froadMallFuncImpl.createLotteryTrans(new FroadMallReq(
//				LotteryNo.FC_SSQ, 
//				"20130103", 
//				LotteryPlayType.directly,
//				LotteryNumType.simplex, 
//				"01,02,03,04,05,06|07", 
//				"1",
//				"2",
//				"1",
//				"13527459070",
//				"16525425df4")).getResultDesc());
//		System.out.println("购买彩票测试");
	}
}
