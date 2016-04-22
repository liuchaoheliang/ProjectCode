package com.froad.fft.test.thirdparty;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.froad.fft.common.AppException;
import com.froad.fft.thirdparty.common.PointsCommand;
import com.froad.fft.thirdparty.dto.request.points.PointsReq;
import com.froad.fft.thirdparty.dto.request.points.PointsRes;
import com.froad.fft.thirdparty.request.points.impl.PointsFuncImpl;

public class PointsFunc_Test {

	private static Logger logger = Logger.getLogger(PointsFunc_Test.class);
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:context/**/*.xml");
		PointsFuncImpl pointsFuncImpl = (PointsFuncImpl) context.getBean("pointsFuncImpl");
		
		PointsRes pointRes;
		
//		try {
//			logger.info("\n--------------------------------查询积分接口测试 开始");
//			pointRes = pointsFuncImpl.queryParAccountPoints(new PointsReq(null, "fenfentong", PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME));
//			System.out.println("账户数："+pointRes.getResultDesc()+" "+pointRes.getAccountPointsInfoList().size());
//			logger.info("\n--------------------------------查询积分接口测试 结束");
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//		
//		logger.info("\n--------------------------------消费积分接口测试 开始");
//		try {
//			PointsReq req = new PointsReq(null, "fenfentong", PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME, "111", "ddsfds", "hh", "31216151116103", "jj", "10");
//			System.out.println(pointsFuncImpl.consumePoints(req).getResultDesc());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//		logger.info("\n--------------------------------消费积分接口测试 结束");
		
//		logger.info("\n--------------------------------退还积分接口测试 开始");
//		try {
//			PointsReq req = new PointsReq(null, "fenfentong", PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME, "5111389348124785", "ddsfds", "1", "31009095647461", "jj", "10","40120161102244");
//			System.out.println(pointsFuncImpl.refundPoints(req).getResultDesc());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//		logger.info("\n--------------------------------退还积分接口测试 结束");
//		
//		logger.info("\n--------------------------------赠送积分接口测试 开始");
//		try {
//			PointsReq req = new PointsReq(null, "larry", PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME, "123", "测试", "1", "备注", "8888","10000004021");
//			System.out.println(pointsFuncImpl.donatePoints(req).getResultDesc());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//		logger.info("\n--------------------------------赠送积分接口测试 结束");
//		
//		logger.info("\n--------------------------------兑充积分接口测试 开始");
//		try {
//			PointsReq req = new PointsReq(null, "123", "10", "10001", "测试", "1", "备注", "fenfentong", PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME, 13527459070L);
//			System.out.println(pointsFuncImpl.fillPoints(req).getResultDesc());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//		logger.info("\n--------------------------------兑充积分接口测试 结束");
//		
//		logger.info("\n--------------------------------提现申请接口测试 开始");
//		try {
//			PointsReq req = new PointsReq("100010002", "fenfentong", PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME, "张三", "101", "珠海农商银行", "8888888888888888",
//					"100010002", "Desc", "objectType", "10", "dfas", "beizhu");
//			System.out.println(pointsFuncImpl.applyForPointsToCash(req).getResultDesc());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//		logger.info("\n--------------------------------提现申请接口测试 结束");
		
	}
}
