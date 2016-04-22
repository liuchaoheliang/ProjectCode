package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.HFCZ;

@WebService
public interface HFCZService {
	/**
	  * 方法描述：查询号码是否可以充值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	  * @time: Feb 28, 2013 4:01:37 PM
	  */
	public HFCZ checkParaCZNo(HFCZ hfcz) throws AppException;
	
	
	/**
	  * 方法描述：立即充值
	  * @param: HFCZ(CZNo,money,SPID,tranDate)
	  * @param: 选填(salePrice)
	  * @return: HFCZ hfczRes 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @throws AppException 
	  * @time: Feb 7, 2013 2:03:56 PM
	  */
	public HFCZ onLine(HFCZ hfcz) throws AppException;
}
