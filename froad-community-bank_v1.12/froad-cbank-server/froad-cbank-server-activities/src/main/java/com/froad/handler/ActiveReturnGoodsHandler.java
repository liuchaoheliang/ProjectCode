package com.froad.handler;

import com.froad.po.Result;
import com.froad.po.ReturnMarketOrderBackReq;
import com.froad.po.ReturnMarketOrderReq;
import com.froad.po.ReturnMarketOrderRes;

/**
 * 更新订单明细/更新订单表
 */
public interface ActiveReturnGoodsHandler {
	
	/**
	 * 订单退货
	 * */
	public ReturnMarketOrderRes updateActiveOrderAndDetail(ReturnMarketOrderReq returnMarketOrderReq) throws Exception;
	
	public Result updateActiveOrderAndDetailBack(ReturnMarketOrderBackReq returnMarketOrderBackReq) throws Exception;
	
	public ReturnMarketOrderRes selectActiveOrderAndDetail(ReturnMarketOrderReq returnMarketOrderReq) throws Exception;
	
}
