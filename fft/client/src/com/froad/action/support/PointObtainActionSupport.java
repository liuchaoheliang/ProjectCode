package com.froad.action.support;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.hfcz.HFCZService;
import com.froad.client.hfcz.Hfcz;
import com.froad.client.lottery.AppException_Exception;
import com.froad.client.lottery.Lottery;
import com.froad.client.lottery.LotteryService;

public class PointObtainActionSupport {
	private static Logger logger = Logger.getLogger(PointObtainActionSupport.class);
	private LotteryService lotteryService;
	private HFCZService hfczService;
	private ClientGoodsExchangeRackActionSupport actionSupport; 
	
	
	/**
	  * 方法描述：查询彩票期次信息
	  * @param: lotteryNo 彩种
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	 * @throws AppException_Exception 
	  * @update:
	  * @time: 2013-3-1 上午10:20:15
	  */
	public List<Lottery> queryLotteryPeridListNow() throws AppException_Exception{
		Lottery lottery = new Lottery();
		return lotteryService.queryPeridListNow(lottery);
	}
	
	
	/**
	  * 方法描述：查询彩票期次信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2013-3-1 下午5:27:12
	  */
	public Lottery queryLotteryPeridListNow(String lotteryNo){
		Lottery lottery = null;
		List<Lottery> list=null;
		
		try{
			lottery = new Lottery();
			lottery.setLotteryNo(lotteryNo);
			list = lotteryService.queryPeridListNow(lottery);
		}
		catch(Exception e){
			logger.error("彩票期次查询失败", e);
			return null;
		}
		
		if(list!=null && !list.isEmpty()){
			lottery = list.get(0);
		}
		
		return lottery;
	}
	
	
	/**
	  * 方法描述：手机号码可充值验证 归属地查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	 * @throws com.froad.client.hfcz.AppException_Exception 
	  * @update:
	  * @time: 2013-3-1 上午10:34:21
	  */
	public Hfcz checkParaCZNo(String mobilephone,BigDecimal money) throws com.froad.client.hfcz.AppException_Exception{
		Hfcz hfcz = new Hfcz();
		hfcz.setCZNo(mobilephone);
		hfcz.setMoney(money);
		return hfczService.checkParaCZNo(hfcz);
	}
	
	
	/**
	  * 方法描述：手机号码可充值验证 归属地查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	 * @throws com.froad.client.hfcz.AppException_Exception 
	  * @update:
	  * @time: 2013-3-1 上午10:38:58
	  */
	public Hfcz checkParaCZNo(String mobilephone) throws com.froad.client.hfcz.AppException_Exception{
		return checkParaCZNo(mobilephone, null);
	}
	
	
	
	
	
	public LotteryService getLotteryService() {
		return lotteryService;
	}
	public void setLotteryService(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}
	public HFCZService getHfczService() {
		return hfczService;
	}
	public void setHfczService(HFCZService hfczService) {
		this.hfczService = hfczService;
	}
}
