package com.froad.fft.service;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.TransSecurityTicket;

public interface TransSecurityTicketService {

	public Long saveTransSecurityTicket(TransSecurityTicket transSecurityTicket);
	public Boolean updateTransSecurityTicketById(TransSecurityTicket transSecurityTicket);
	public TransSecurityTicket selectTransSecurityTicketById(Long id);
	
	/**
	  * 方法描述：根据券号查询券信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午2:08:41
	  */
	public TransSecurityTicket selectBySecurityNo(String SecurityNo);
	
	
	/**
	  * 方法描述：分页查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月8日 下午11:22:43
	  */
	public Page findByPage(Page page);
	
	/**
	*<p></p>
	* @author larry
	* @datetime 2014-4-11下午07:03:17
	* @return TransSecurityTicket
	* @throws 
	* @example<br/>
	*
	 */
	public List<TransSecurityTicket> selectByCondition(TransSecurityTicket ticket);
}
