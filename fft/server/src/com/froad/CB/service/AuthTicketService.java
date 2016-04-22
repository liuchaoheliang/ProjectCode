package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.AuthTicket;
import com.froad.CB.po.Trans;
import com.froad.util.Result;

@WebService
public interface AuthTicketService {


	/**
	  * 方法描述：添加认证券信息
	  * @param: AuthTicket
	  * @return: AuthTicket
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 11:00:04 AM
	  */
	public AuthTicket addAuthTicket(AuthTicket authTicket)throws AppException;
	
	
	/**
	  * 方法描述：查询认证券信息
	  * @param: AuthTicket
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 11:00:39 AM
	  */
	public AuthTicket getAuthTicketById(Integer id);
	
	
	/**
	  * 方法描述：删除认证券
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 11:01:00 AM
	  */
	public boolean deleteById(Integer id);
	
	
	/**
	  * 方法描述：修改认证券
	  * @param: AuthTicket
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 11:01:28 AM
	  */
	public boolean updateById(AuthTicket authTicket);
	
	
	/**
	  * 方法描述：修改认证券状态
	  * @param: id,state
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 11:01:50 AM
	  */
	public boolean updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：多条件查询认证券
	  * @param: AuthTicket
	  * @return: List<AuthTicket>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 2, 2013 5:50:32 PM
	  */
	public List<AuthTicket> getAuthTickBySelective(AuthTicket authTicket);
	
	
	/**
	  * 方法描述：认证券是否不存在
	  * @param: securitiesNo
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2013 6:01:26 PM
	  */
	public boolean isNotExist(String securitiesNo);
	
	/**
	 * *******************************************************
	 * @函数名: getListAuthTickById  
	 * @功能描述: 通过订证券id查询重发证券
	 * @输入参数:  transId 订单号
	 * @输入参数: @return <说明>
	 * @返回类型: List<AuthTicket>
	 * @作者: 赵肖瑶 
	 * @日期: 2013-5-6 下午02:23:21
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public Result getAuthTickByAuthId(Integer transId,Integer authId);
	
	
	/**
	 * *******************************************************
	 * @函数名: getAuthTickByTransId  
	 * @功能描述: 通过交易id获取所有的认证券
	 * @输入参数: @param transId
	 * @输入参数: @return <说明>
	 * @返回类型: List<AuthTicket>
	 * @作者: 赵肖瑶 
	 * @日期: 2013-5-21 下午05:27:28
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public List<AuthTicket> getAuthTickByTransId(Integer transId);
}
