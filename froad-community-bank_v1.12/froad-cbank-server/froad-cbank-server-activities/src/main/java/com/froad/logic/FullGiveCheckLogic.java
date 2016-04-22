package com.froad.logic;

import com.froad.po.FullGiveCheckReq;
import com.froad.po.Result;

public interface FullGiveCheckLogic {

	/**
	 * 满赠的支付前资格检查
	 * */
	Result fullGiveCheck(FullGiveCheckReq fullGiveCheckReq);
}
