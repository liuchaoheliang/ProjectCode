package com.froad.db.ahui.mappers;

import java.util.List;

import com.froad.fft.persistent.entity.SysClient;


public interface SysClientMapper {

	/**
	 * 查询所有客户端信息
	 * @return
	 */
	public List<SysClient> selectAllSysClient();
}
