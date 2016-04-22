package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.lottery.Lottery;
import com.froad.CB.service.LotteryService;
import com.froad.CB.thirdparty.LotteryFunc;

@WebService(endpointInterface="com.froad.CB.service.LotteryService")
public class LotteryServiceImpl implements LotteryService{

	
	/**
	  * 方法描述：查询当前期数
	  * @param: 选填Lottery（lotteryNo）
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	  * @time: Feb 28, 2013 3:07:41 PM
	  */
	public List<Lottery> queryPeridListNow(Lottery lottery) throws AppException{
		return LotteryFunc.queryPeridListNow(lottery);
	}


	@Override
	public Lottery calOrder(Lottery lottery) throws AppException {
		return LotteryFunc.calOrder(lottery);
	}


	@Override
	public Lottery createOrder(Lottery lottery) throws AppException {
		return LotteryFunc.createOrder(lottery);
	}


	@Override
	public List<Lottery> queryLastPeridReward(Lottery lottery)
			throws AppException {
		return LotteryFunc.queryLastPeridReward(lottery);
	}


	@Override
	public Lottery queryRewardByTranID(Lottery lottery) throws AppException {
		return LotteryFunc.queryRewardByTranID(lottery);
	}


	@Override
	public List<Lottery> queryZCinfos(Lottery lottery) throws AppException {
		return LotteryFunc.queryZCinfos(lottery);
	}
}
