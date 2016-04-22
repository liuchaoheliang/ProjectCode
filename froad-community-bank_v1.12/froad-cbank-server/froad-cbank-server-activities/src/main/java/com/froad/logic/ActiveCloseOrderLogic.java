package com.froad.logic;

import com.froad.po.CloseMarketOrderReq;
import com.froad.po.Result;

/**
  * @ClassName: ActiveCloseOrderLogic
  * @Description: 关闭订单（0-系统关单/取消订单 1-全部退货完毕）
  * @author froad-zengfanting 2015年11月9日
  * @modify froad-zengfanting 2015年11月9日
 */
public interface ActiveCloseOrderLogic {
	
		/**
		  * @Title: updateOrderStatusSuccess
		  * @Description: 0-系统关单/取消订单
		  * @author: zengfanting 2015年11月9日
		  * @modify: zengfanting 2015年11月9日
		  * @param orderReqVo
		  * @return
		 */
		public Result closeOrderBySystem(CloseMarketOrderReq closeMarketOrderReq);
		
		/**
		 * 
		  * @Title: updateOrderStatusFaile
		  * @Description: 1-return all goods
		  * @author: zengfanting 2015年11月9日
		  * @modify: zengfanting 2015年11月9日
		  * @param orderReqVo
		  * @return
		 */
		public Result closeOrderByReturn(CloseMarketOrderReq closeMarketOrderReq);

}
