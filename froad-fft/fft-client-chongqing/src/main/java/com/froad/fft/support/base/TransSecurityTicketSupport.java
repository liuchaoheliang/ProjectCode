
	 /**
  * 文件名：TransSecurityTicketSupport.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base;

import java.util.List;

import com.froad.fft.dto.TransSecurityTicketDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午2:05:50 
 */
public interface TransSecurityTicketSupport {
	
	
	
	/**
	  * 方法描述：根据Id查询券信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午2:06:25
	  */
	public TransSecurityTicketDto getById(Long id);
	
	/**
	  * 方法描述：根据券号查询券信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午2:06:25
	  */
	public TransSecurityTicketDto getBySecurityNo(String SecurityNo);
	
	
	
	/**
	  * 方法描述：添加券信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午2:07:19
	  */
	public Long addTransSecurityTicket(TransSecurityTicketDto ticketDto);
	
	
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午2:18:34
	  */
	public boolean updateById(TransSecurityTicketDto ticketDto); 
	
	
	/**
	*<p>根据交易ID查询券</p>
	* @author larry
	* @datetime 2014-4-11下午06:50:17
	* @return TransSecurityTicketDto
	* @throws 
	* @example<br/>
	 */
	public TransSecurityTicketDto getByTransId(Long id);
	
	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月21日 下午7:35:21
	  */
	public List<TransSecurityTicketDto> getByConditions(TransSecurityTicketDto transSecurityTicketDto);
	
}
