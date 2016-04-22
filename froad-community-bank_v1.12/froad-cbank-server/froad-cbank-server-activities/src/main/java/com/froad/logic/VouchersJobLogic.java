package com.froad.logic;

import com.froad.po.FindVouchersOfCenterReq;
import com.froad.po.FindVouchersRes;


public interface VouchersJobLogic {
	
	/**
	 * 查询红包详情 - 会员中心
	 * */
	FindVouchersRes findVouchersOfCenter(FindVouchersOfCenterReq findVouchersOfCenterReq);
}
