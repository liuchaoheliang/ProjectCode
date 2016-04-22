package com.froad.logic.impl;

import com.froad.enums.ResultCode;
import com.froad.handler.ActiveReturnGoodsHandler;
import com.froad.handler.impl.ActiveReturnGoodsHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveReturnGoodsLogic;
import com.froad.po.Result;
import com.froad.po.ReturnMarketOrderBackReq;
import com.froad.po.ReturnMarketOrderReq;
import com.froad.po.ReturnMarketOrderRes;

public class ActiveReturnGoodsLogicImpl implements ActiveReturnGoodsLogic {
	ActiveReturnGoodsHandler activeReturnGoodsHandler=new ActiveReturnGoodsHandlerImpl();
	
	//订单退货
	@Override
	public ReturnMarketOrderRes returnOrderGoods(ReturnMarketOrderReq returnMarketOrderReq) {
		ReturnMarketOrderRes returnMarketOrderRes = new ReturnMarketOrderRes();
		try{
			if(returnMarketOrderReq.getIsQuery()){//退货查询
				LogCvt.info("---退货交易查询---");
				returnMarketOrderRes = activeReturnGoodsHandler.selectActiveOrderAndDetail(returnMarketOrderReq);
			}else{//退货
				LogCvt.info("---退货交易---");
				returnMarketOrderRes = activeReturnGoodsHandler.updateActiveOrderAndDetail(returnMarketOrderReq);
			}
			returnMarketOrderRes.setResult(new Result(ResultCode.success));
		}catch(Exception ex){
			returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "系统问题，退货失败"));
			LogCvt.error("系统问题，退货失败", ex);
		}
		return returnMarketOrderRes;
	}
	
	//订单退货回退
	@Override
	public Result returnMarketOrderBack(ReturnMarketOrderBackReq returnMarketOrderBackReq) {
		Result result=null;
		try{
			result=activeReturnGoodsHandler.updateActiveOrderAndDetailBack(returnMarketOrderBackReq);
		}catch(Exception ex){
			result.setResultDesc("系统问题，退货回退失败");
			result.setResultCode(ResultCode.failed.getCode());
			LogCvt.error("系统问题，退货回退失败", ex);
		}
		return result;
	}

}
