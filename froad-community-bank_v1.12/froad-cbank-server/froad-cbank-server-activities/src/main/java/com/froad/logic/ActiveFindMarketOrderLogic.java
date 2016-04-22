package com.froad.logic;

import com.froad.po.FindMarketOrderReq;
import com.froad.po.FindMarketOrderRes;

/**
  * @ClassName: ActiveFindMarketOrderLogic
  * @Description: 查询订单
  * @author froad-zengfanting 2015年11月11日
  * @modify froad-lf 2015年12月06日
 */
public interface ActiveFindMarketOrderLogic {
	//查询订单
	public  FindMarketOrderRes findMarketOrder(FindMarketOrderReq findMarketOrderReq);
	
}
