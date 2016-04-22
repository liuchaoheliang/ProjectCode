package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.lottery.Lottery;

@WebService
public interface LotteryService {
	
	/**
	  * 查询当前期数
	  * @param: 选填Lottery（lotteryNo）
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	  * @time: Feb 28, 2013 3:18:24 PM
	  */
	public List<Lottery> queryPeridListNow(Lottery lottery) throws AppException;
	
	
	/**
	  * 方法描述：计算彩票注数
	  * @param: Lottery(lotteryNo,playType,numType,content)
	  * @param: 选填(buyamount)
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	 * @throws AppException 
	  * @time: Feb 19, 2013 4:11:19 PM
	  */
	public Lottery calOrder(Lottery lottery) throws AppException;
	
	
	/**
	  * 方法描述：查询足彩信息
	  * @param: Lottery(lotteryNo)
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	  * @time: Feb 19, 2013 4:14:21 PM
	  */
	public List<Lottery>  queryZCinfos(Lottery lottery) throws AppException;
	
	
	/**
	  * 方法描述：查询上期中奖情况
	  * @param: 选填Lottery(lotteryNo)
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	  * @time: Feb 27, 2013 2:29:05 PM
	  */
	public List<Lottery> queryLastPeridReward(Lottery lottery) throws AppException;
	
	
	/**
	  * 方法描述：创建订单
	  * @param:Lottery(lotteryNo,username,period,playType,numType,content,buyamount,amount,numCount,mobilephone)
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	  * @time: Feb 27, 2013 2:30:11 PM
	  */
	public Lottery createOrder(Lottery lottery) throws AppException;
	
	
	/**
	  * 方法描述：通过TRANID查询彩票中奖情况
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	  * @time: Feb 27, 2013 2:30:27 PM
	  */
	public Lottery queryRewardByTranID(Lottery lottery) throws AppException;
}
