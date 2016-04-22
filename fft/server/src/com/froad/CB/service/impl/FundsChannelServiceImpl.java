package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.FundsChannelDao;
import com.froad.CB.po.FundsChannel;
import com.froad.CB.service.FundsChannelService;

@WebService(endpointInterface = "com.froad.CB.service.FundsChannelService")
public class FundsChannelServiceImpl implements FundsChannelService {
	
	private static final Logger logger=Logger.getLogger(FundsChannelServiceImpl.class);
	
	private FundsChannelDao fundsChannelDao;

	@Override
	public List<FundsChannel> getFundsChannels(FundsChannel queryCon) {
		return fundsChannelDao.selectFundsChannels(queryCon);
	}

	public FundsChannelDao getFundsChannelDao() {
		return fundsChannelDao;
	}

	public void setFundsChannelDao(FundsChannelDao fundsChannelDao) {
		this.fundsChannelDao = fundsChannelDao;
	}

	@Override
	public List<FundsChannel> getAll() {
		return fundsChannelDao.getAll();
	}

	@Override
	public FundsChannel getFundsChannelById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return fundsChannelDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean isExist(Integer id) {
		if(id==null){
			return false;
		}
		return fundsChannelDao.isExist(id);
	}

}
