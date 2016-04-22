package com.froad.CB.service.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.TransDetailsDao;
import com.froad.CB.po.TransDetails;
import com.froad.CB.service.TransDetailsService;

@WebService(endpointInterface="com.froad.CB.service.TransDetailsService")
public class TransDetailsServiceImpl implements TransDetailsService{

	private static final Logger logger=Logger.getLogger(TransDetailsServiceImpl.class);
	
	private TransDetailsDao transDetailsDao;
	
	@Override
	public Integer addTransDetails(TransDetails transDetails) {
		if(transDetails==null){
			logger.error("交易详情为空，添加失败");
			return null;
		}
		return transDetailsDao.addTransDetails(transDetails);
	}

	@Override
	public void deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除交易详情失败");
			return;
		}
		transDetailsDao.deleteById(id);
	}

	@Override
	public TransDetails getTransDetailsById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询交易详情失败");
			return null;
		}
		return transDetailsDao.getTransDetailsById(id);
	}

	@Override
	public void updateById(TransDetails transDetails) {
		if(transDetails==null||transDetails.getId()==null){
			logger.error("参数为空，更新失败");
			return;
		}
		transDetailsDao.updateById(transDetails);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，更新状态失败");
			return;
		}
		transDetailsDao.updateStateById(id, state);
	}

	public void setTransDetailsDao(TransDetailsDao transDetailsDao) {
		this.transDetailsDao = transDetailsDao;
	}

}
