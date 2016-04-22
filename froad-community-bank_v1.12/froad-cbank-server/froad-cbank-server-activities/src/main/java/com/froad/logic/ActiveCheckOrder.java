package com.froad.logic;

import com.froad.po.CheckOrderReq;
import com.froad.po.CheckOrderRes;

public interface ActiveCheckOrder {
	
	/**
     * 订单校验
     * <br>
     * CheckOrderRes.Result.resultCode = 0000 成功
     * CheckOrderRes.orderResList[n] 订单的各种满减金额
     * <br>
     * CheckOrderRes.Result.resultCode = 6666 没有传递活动
     * CheckOrderRes.orderResList = null
     * <br>
     * CheckOrderRes.Result.resultCode = 9999 校验失败
     * CheckOrderRes.Result.resultDesc 失败信息
     * <br>
     */
	CheckOrderRes checkOrder(CheckOrderReq checkOrderReq); 
}
