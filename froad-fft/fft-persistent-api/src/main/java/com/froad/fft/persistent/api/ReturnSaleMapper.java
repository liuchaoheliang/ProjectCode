
	 /**
  * 文件名：ReturnSaleMapper.java
  * 版本信息：Version 1.0
  * 日期：2014年4月1日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ReturnSale;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月1日 下午5:38:55 
 */
public interface ReturnSaleMapper {
	
	/**
	  * 方法描述：保存数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月1日 下午5:45:19
	  */
	public Long saveReturnSale(ReturnSale returnSale );

	/**
	*<p></p>
	* @author larry
	* @datetime 2014-4-3下午04:49:29
	* @return Page<ReturnSale>
	* @throws 
	* example<br/>
	*
	 */
	public List<ReturnSale> findReturnSaleByPage(Page<ReturnSale> page);
	
	/**
	*<p>分页总数</p>
	* @author larry
	* @datetime 2014-4-3下午04:50:40
	* @return Integer
	* @throws 
	* example<br/>
	 */
	public Integer findReturnSaleByPageCount(Page<ReturnSale> page);

	/**
	*<p></p>
	* @author larry
	* @datetime 2014-4-3下午04:49:33
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
	  * @time: 2014年4月8日 下午12:05:23
	  */
	public List<ReturnSale> selectByConditions(ReturnSale returnSale);
}
