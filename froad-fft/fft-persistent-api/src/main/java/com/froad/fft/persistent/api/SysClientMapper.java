package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.entity.SysClient;

public interface SysClientMapper {
	
	/**
	 * 查询所有客户端信息
	 * @return
	 */
	public List<SysClient> selectAllSysClient();
	
	/**
	 * 根据ID 查询客户端信息
	 * @param id
	 * @return
	 */
	public SysClient selectSysClientById(Long id);
	
	
	/**
	  * 方法描述：查询客户端信息
	  * @param: number 客户端号
	  * @return: SysClient
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月28日 下午12:06:55
	  */
	public SysClient selectByNumber(String number);
}
