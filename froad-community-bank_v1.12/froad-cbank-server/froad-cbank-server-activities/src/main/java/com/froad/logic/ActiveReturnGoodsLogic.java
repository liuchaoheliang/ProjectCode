package com.froad.logic;

import com.froad.po.Result;
import com.froad.po.ReturnMarketOrderBackReq;
import com.froad.po.ReturnMarketOrderReq;
import com.froad.po.ReturnMarketOrderRes;

/**
  * @ClassName: ActiveReturnGoodsLogic
  * @Description: 商户退款/退款回退交易
  * @author froad-zengfanting 2015年11月9日
  * @modify froad-zengfanting 2015年11月9日
 */
public interface ActiveReturnGoodsLogic {
	/**
	  * @Title: returnOrderGoods
	  * @Description: 订单退货
	  * @author: zengfanting 2015年11月13日
	  * @modify: lf 2015年12月06日
	  * @param returnMarketOrderReq
	  * @return
	 */
	public ReturnMarketOrderRes returnOrderGoods(ReturnMarketOrderReq returnMarketOrderReq);

	/**
	  * @Title: returnMarketOrderBack
	  * @Description: 订单退货回退
	  * @author: zengfanting 2015年11月13日
	  * @modify: zengfanting 2015年11月13日
	  * @param returnMarketOrderReqVo
	  * @return
	 */
	public Result returnMarketOrderBack(ReturnMarketOrderBackReq returnMarketOrderBackReq);

}
