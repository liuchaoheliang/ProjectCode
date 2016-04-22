package com.froad.db.mysql.mapper;

import java.util.List;

import com.froad.po.SmsLog;

/**
 * 短信日志操作
 * 
 * @author lf 2015.03.21
 * @modify lf 2015.03.21
 * 
 * */
public interface SmsLogMapper {
	
	/**
	 * 
	 * @Title: selectByExpired 
	 * @Description: 查询过期的短信日志
	 * @author: froad-lf 2015年3月21日
	 * @modify: froad-lf 2015年3月21日
	 * @return List<SmsLog>
	 * @throws
	 */
	public List<SmsLog> selectByExpired();
	
	/**
	 * 
	 * @Title: deleteById 
	 * @Description: 根据主键删除短信日志
	 * @author: froad-lf 2015年3月21日
	 * @modify: froad-lf 2015年3月21日
	 * @return 
	 * @throws
	 */
	public boolean deleteById(long id);
}
