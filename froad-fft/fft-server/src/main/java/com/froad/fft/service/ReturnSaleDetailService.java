package com.froad.fft.service;

import java.util.List;

import com.froad.fft.persistent.entity.ReturnSaleDetail;

public interface ReturnSaleDetailService {
	
	public Long saveReturnSaleDetail(ReturnSaleDetail returnSaleDetail);

	/**
	*<p>根据ID查询</p>
	* @author larry
	* @datetime 2014-4-3下午04:39:49
	* @return ReturnSaleDetailDto
	* @throws 
	* example<br/>
	*
	 */
	public ReturnSaleDetail getReturnSaleDetailById(Long id);

	/**
	*<p>根据退换货ID查询退换货详情</p>
	* @author larry
	* @datetime 2014-4-3下午08:00:17
	* @return ReturnSaleDetail
	* @throws 
	* example<br/>
	*
	 */
	public ReturnSaleDetail getReturnSaleDetailByRsId(Long rsid);
	
	
	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月10日 下午8:20:14
	  */
	public List<ReturnSaleDetail> selectByConditions(ReturnSaleDetail returnSaleDetail);
}
