package com.froad.logic;

import com.froad.po.Result;
import com.froad.po.UpdateMarketOrderReq;
import com.froad.po.UpdateMarketOrderRes;


/**
  * @ClassName: ActiveOrderStatusLogic
  * @Description: 1.订单支付成功，更新数据库订单状态
  *               2.订单支付失败，回退个人资格/全局资格
  * @author froad-zengfanting 2015年11月7日
  * @modify froad-lf 2015年12月04日
 */
public interface ActiveOrderStatusLogic {
	//更新订单状态成功
	public UpdateMarketOrderRes updateOrderStatusSuccess(UpdateMarketOrderReq updateMarketOrderReq);
	
	//更新订单状态失败
	public UpdateMarketOrderRes updateOrderStatusFaile(UpdateMarketOrderReq updateMarketOrderReq);
}
