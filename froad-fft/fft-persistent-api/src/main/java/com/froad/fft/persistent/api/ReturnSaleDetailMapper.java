
	 /**
  * 文件名：ReturnSaleDetailMapper.java
  * 版本信息：Version 1.0
  * 日期：2014年4月1日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.entity.ReturnSaleDetail;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月1日 下午5:39:12 
 */
public interface ReturnSaleDetailMapper {
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月1日 下午5:44:37
	  */
	public Long saveReturnSaleDetail(ReturnSaleDetail returnSaleDetail );

	/**
	*<p>根据ID查询</p>
	* @author larry
	* @datetime 2014-4-3下午04:44:35
	* @return ReturnSaleDetail
	* @throws 
	* example<br/>
	*
	 */
	public ReturnSaleDetail getReturnSaleDetailById(Long id);
	
	/**
	*<p>根据退换货ID查询退换货详情</p>
	* @author larry
	* @datetime 2014-4-3下午08:01:46
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
	  * @time: 2014年4月10日 下午8:16:58
	  */
	public List<ReturnSaleDetail> selectByConditions(ReturnSaleDetail returnSaleDetail);
}
