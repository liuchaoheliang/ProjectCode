package com.froad.fft.service;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ReturnSale;

public interface ReturnSaleService {

	
	/**
	  * 方法描述：添加退货记录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午10:22:01
	  */
	public Long saveReturnSale(ReturnSale returnSale);
	
	/**
	*<p>分页查询</p>
	* @author larry
	* @datetime 2014-4-3下午04:39:00
	* @return Page<ReturnSale>
	* @throws 
	* example<br/>
	*
	 */
	public Page<ReturnSale> findReturnSaleByPage(
			Page<ReturnSale> page);

	/**
	*<p>根据ID查询</p>
	* @author larry
	* @datetime 2014-4-3下午04:39:10
	* @return ReturnSale
	* @throws 
	* example<br/>
	*
	 */
	public ReturnSale getReturnSaleById(Long id);
	
	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月8日 下午12:18:07
	  */
	public List<ReturnSale> selectByConditions(ReturnSale returnSale);
}
