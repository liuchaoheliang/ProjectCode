package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.TransSecurityTicket;

public interface TransSecurityTicketMapper {

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
	  * @time: 2014年4月8日 下午11:08:16
	  */
	public List<TransSecurityTicket> selectByPage(Page page);
	
	public Integer selectByPageCount(Page page);
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>条件查询券数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月11日 下午4:49:04 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public List<TransSecurityTicket> selectByCondition(TransSecurityTicket transSecurityTicket);
	
	
	/**
	  * 方法描述：查询券信息
	  * @param: transId 交易编号
	  * @return: List<TransSecurityTicket>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月14日 下午3:47:39
	  */
	public List<TransSecurityTicket> selectByTransId(Long transId);
}