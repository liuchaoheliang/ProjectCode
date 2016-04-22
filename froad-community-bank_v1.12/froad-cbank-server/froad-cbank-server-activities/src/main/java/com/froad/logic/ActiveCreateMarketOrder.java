package com.froad.logic;

import com.froad.po.CreateMarketOrderReq;
import com.froad.po.CreateResult;

public interface ActiveCreateMarketOrder {
	
	/**
     * 创建营销订单
     * <br>
     * CreateResult.Result.resultCode = 0000 成功
     * CreateResult.id = 营销订单编号
     * <br>
     * CreateResult.Result.resultCode != 不成功
     * CreateResult.Result.resultDesc = 失败信息
     */
	public CreateResult createMarketOrder(CreateMarketOrderReq createMarketOrderReq);

}
