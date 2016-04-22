package com.froad.CB.service.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.SmsLogDao;
import com.froad.CB.po.SmsLog;
import com.froad.CB.service.SmsLogService;

@WebService(endpointInterface="com.froad.CB.service.SmsLogService")
public class SmsLogServiceImpl implements SmsLogService{

	private static final Logger logger=Logger.getLogger(SmsLogServiceImpl.class);
	
	private SmsLogDao smsLogDao;
	
	@Override
	public Integer add(SmsLog smsLog) {
		if(smsLog==null){
			logger.error("参数为空，添加失败");
		}
		return smsLogDao.add(smsLog);
	}

	@Override
	public void deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败");
			return;
		}
		smsLogDao.deleteById(id);
	}

	@Override
	public SmsLog getById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return smsLogDao.getById(id);
	}

	@Override
	public void updateById(SmsLog smsLog) {
		if(smsLog==null||smsLog.getId()==null){
			logger.error("参数为空，修改失败");
			return;
		}
		smsLogDao.updateById(smsLog);
	}

	public void setSmsLogDao(SmsLogDao smsLogDao) {
		this.smsLogDao = smsLogDao;
	}

}
