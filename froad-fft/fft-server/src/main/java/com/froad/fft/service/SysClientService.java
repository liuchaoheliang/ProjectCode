package com.froad.fft.service;

import java.util.List;

import com.froad.fft.persistent.entity.SysClient;

public interface SysClientService {
	
	/**
	 * 查询所有客户端信息
	 * @return
	 */
	public List<SysClient> findAllSysClient();
	
	/**
	 * 根据ID 查询客户端信息
	 * @param id
	 * @return
	 */
	public SysClient findSysClientById(Long id);
	
	
	/**
	  * 方法描述：根据编号查询客户端信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午2:22:31
	  */
	public SysClient findSysClientByNumber(String number);
}
