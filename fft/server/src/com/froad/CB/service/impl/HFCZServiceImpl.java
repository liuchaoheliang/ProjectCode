package com.froad.CB.service.impl;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.HFCZ;
import com.froad.CB.service.HFCZService;
import com.froad.CB.thirdparty.HFCZFunc;

@WebService(endpointInterface="com.froad.CB.service.HFCZService")
public class HFCZServiceImpl implements HFCZService{
	
	/**
	  * 方法描述：查询号码是否可以充值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	  * @time: Feb 28, 2013 4:01:37 PM
	  */
	public HFCZ checkParaCZNo(HFCZ hfcz) throws AppException{
		return HFCZFunc.checkParaCZNo(hfcz);
	}

	@Override
	public HFCZ onLine(HFCZ hfcz) throws AppException {
		return HFCZFunc.onLine(hfcz);
	}
}
