package com.froad.fft.support.thirdparty.o2oraffle.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.fft.bean.ThirdParty;
import com.froad.fft.support.thirdparty.o2oraffle.O2ORaffleSupport;
import com.o2obill.api.service.ActiveService;
import com.o2obill.dto.AwardRes;
import com.o2obill.dto.CheckLotteryReadyReq;
import com.o2obill.dto.CheckLotteryReadyRes;
import com.o2obill.dto.CouponReq;
import com.o2obill.dto.CouponRes;
import com.o2obill.dto.FindPageRes;
import com.o2obill.dto.LoadLotterysReq;
import com.o2obill.dto.LoadLotterysRes;
import com.o2obill.dto.LotteryDto;
import com.o2obill.dto.LotteryRandomReq;
import com.o2obill.dto.LotteryRandomRes;
import com.o2obill.dto.LotteryWinnerDto;
import com.o2obill.enums.IssueType;

@Service("o2ORaffleSupportImpl")
public class O2ORaffleSupportImpl implements O2ORaffleSupport {

	private static HessianProxyFactory factory = new HessianProxyFactory();
	private static Logger logger = Logger.getLogger(O2ORaffleSupportImpl.class);
	
	private static String O2O_RAFFLE_URL = ThirdParty.O2O_RAFFLE_URL;
	
	public static void main(String[] args) {
//		O2ORaffleSupportImpl test = new O2ORaffleSupportImpl();
		
//		test.checkLotteryReadyRes("fenfentong", 50000033379L);

//		System.out.println(JSONObject.toJSONString(test.loadWinners()));
		
//		LotteryRandomReq req = new LotteryRandomReq("fenfentong", "50000033379",null);
//		test.lotteryRandom(req);
		
//		test.getWinnerByWinnerID("123");
		
//		LotteryWinnerDto dto = test.getWinnerByWinnerID("123");
//		dto.setAddress("中华人民共和国");
//		test.award(dto);
		
		
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>获取远程o2o抽奖模块hessian服务</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月25日 上午11:46:36 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	private static ActiveService getServer(){
		try {
			return (ActiveService)factory.create(ActiveService.class,O2O_RAFFLE_URL);
		} catch (MalformedURLException e) {
			logger.error("连接O2O_RAFFLE_URL：" + O2O_RAFFLE_URL + "异常", e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>获取o2o活动ID</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月25日 上午11:46:28 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	private static String getAvailableLotteryID(){
		ActiveService server = getServer();
		if(server == null){
			return null;
		}
		LoadLotterysReq req = new LoadLotterysReq();
		req.setIssueType(IssueType.CQYS);
		LoadLotterysRes res = server.loadLotterysBy(req);
		List<LotteryDto> list = res.getLotteryList();
		if(list == null || list.size() == 0){
			logger.info("未能成功获取适用于：" + req.getIssueType().toString() + " 环境的抽奖活动ID");
			return null;
		}else{
			logger.info("成功获取适用于：" + req.getIssueType().toString() + " 环境的抽奖活动" + list.size() + "个");
			//TODO:暂时获取第0个			
			List<LotteryDto> ld = new ArrayList<LotteryDto>();
			String state;
			for (int i=0 ; i < list.size() ; i++) {				
				state = list.get(i).getStatus().toString();
				if("effect".equals(state)){
					ld.add(list.get(i));
				}
			}
			logger.info("处理后状态为：effect" + "的活动数为："  + ld.size() + "个");
			if(ld.size() == 0){
				logger.info("没有状态可用的活动信息");
				return null;
			}
			String lotteryID = ld.get(0).getId().toString();
			logger.info("获取排位为第一个的活动，活动ID为：" + lotteryID);
			return lotteryID;
		}
	}
	
	@Override
	public CheckLotteryReadyRes checkLotteryReadyRes(String loginID,Long memberCode) {
		ActiveService server = getServer();
		if(server == null){
			return null;
		}
		CheckLotteryReadyReq req = new CheckLotteryReadyReq(loginID,memberCode.toString(), getAvailableLotteryID(), null);
		CheckLotteryReadyRes res = server.checkLotteryReady(req);
		logger.info("活动校验 res：" + JSONObject.toJSONString(res));
		return res;
	}
	
	@Override
	public List<LotteryWinnerDto> loadWinners() {
		ActiveService server = getServer();
		if(server == null){
			return null;
		}
		return server.loadWinners(getAvailableLotteryID());
	}

	@Override
	public LotteryRandomRes lotteryRandom(LotteryRandomReq lotteryRandomReq) {
		ActiveService server = getServer();
		if(server == null){
			return null;
		}
		logger.info("开始抽奖，请求数据：" + JSONObject.toJSONString(lotteryRandomReq));
		lotteryRandomReq.setLotteryId(getAvailableLotteryID());
		LotteryRandomRes res = server.lotteryRandom(lotteryRandomReq);
		logger.info("开始抽奖，返回数据： "+ JSONObject.toJSONString(res));
		return res;
	}

	@Override
	public LotteryWinnerDto getWinnerByWinnerID(String winnerID) {
		ActiveService server = getServer();
		if(server == null){
			return null;
		}
		LotteryWinnerDto lotteryWinnerDto = server.getWinnerBy(winnerID);
		logger.info("开始通过winnerID查询中奖信息，返回数据： "+ JSONObject.toJSONString(lotteryWinnerDto));
		return lotteryWinnerDto;
	}

	@Override
	public AwardRes award(LotteryWinnerDto lotteryWinnerDto) {
		ActiveService server = getServer();
		if(server == null){
			return null;
		}
		AwardRes awardRes = server.award(lotteryWinnerDto);
		logger.info("派奖返回数据：" + JSONObject.toJSONString(awardRes));
		return awardRes;
	}

	@Override
	public CouponRes loadCouponsBy(CouponReq couponReq) {
		ActiveService server = getServer();
		if(server == null){
			return null;
		}
		CouponRes res = server.loadCouponsBy(couponReq);
		logger.info("查询指定用户中奖信息返回数据：" + res);
		return res;
	}
	
	public FindPageRes findBypage(String memberCode,int pageNumber,int pageSize){
		ActiveService server = getServer();
		if(server == null){
			return null;
		}
		return server.findPage(IssueType.CQYS, memberCode, pageNumber, pageSize);
	}
	
}
