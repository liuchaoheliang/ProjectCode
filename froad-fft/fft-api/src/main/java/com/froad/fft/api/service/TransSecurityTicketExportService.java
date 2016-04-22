
	 /**
  * 文件名：TransSecurityTicketExportService.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.TransSecurityTicketDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午1:41:12 
 */
public interface TransSecurityTicketExportService {
	
	/**
	  * 方法描述：添加认证券信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午1:44:28
	  */
	public Long addTransSecurityTicket(ClientAccessType clientAccessType, ClientVersion clientVersion,TransSecurityTicketDto transSecurityTicketDto)throws FroadException;
	
	
	/**
	  * 方法描述：更新券信息（根据ID）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午1:44:31
	  */
	public Boolean updateById(ClientAccessType clientAccessType, ClientVersion clientVersion,TransSecurityTicketDto transSecurityTicketDto)throws FroadException;

	
	/**
	  * 方法描述：根据Id查询认证券信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午1:44:33
	  */
	public TransSecurityTicketDto selectById(ClientAccessType clientAccessType, ClientVersion clientVersion,Long id)throws FroadException;

	/**
	  * 方法描述：根据券号查询券信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午2:08:41
	  */
	public TransSecurityTicketDto selectBySecurityNo(ClientAccessType clientAccessType, ClientVersion clientVersion,String SecurityNo)throws FroadException;;

	
	/**
	  * 方法描述：分页查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月8日 下午11:20:52
	  */
	public PageDto<TransSecurityTicketDto> findByPage(ClientAccessType clientAccessType, ClientVersion clientVersion,PageDto<TransSecurityTicketDto> pageDto)throws FroadException;


	/**
	*<p>条件查询</p>
	* @author larry
	* @datetime 2014-4-11下午06:58:47
	* @return TransSecurityTicketDto
	* @throws 
	* @example<br/>
	*
	 */
	public List<TransSecurityTicketDto> selectByCondition(ClientAccessType clientAccessType, ClientVersion clientVersion,TransSecurityTicketDto transSecurityTicketDto)throws FroadException;
}
