package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.FundsChannel;

@WebService
public interface FundsChannelService {
	
	
	/**
	  * 方法描述：多条件查询资金渠道
	  * @param: FundsChannel
	  * @return: List<FundsChannel>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 3, 2013 10:16:17 PM
	  */
	public List<FundsChannel> getFundsChannels(FundsChannel queryCon);
	
	
	/**
	  * 方法描述：查询所有资金渠道
	  * @return: List<FundsChannel>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 4, 2013 2:03:37 PM
	  */
	public List<FundsChannel> getAll();
	
	
	/**
	  * 方法描述：按主键查询资金渠道
	  * @param: id
	  * @return: FundsChannel
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 3, 2013 10:17:14 PM
	  */
	public FundsChannel getFundsChannelById(Integer id);
	
	
	/**
	  * 方法描述：指定id的资金渠道是否存在
	  * @param: id
	  * @return: boolean true:存在 false:不存在
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 17, 2014 3:03:36 PM
	  */
	public boolean isExist(Integer id);
}
