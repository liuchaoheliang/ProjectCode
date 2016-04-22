package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.PresellBuyInfoDao;
import com.froad.CB.po.PresellBuyInfo;
import com.froad.CB.service.PresellBuyInfoService;
@WebService(endpointInterface="com.froad.CB.service.PresellBuyInfoService")
public class PresellBuyInfoServiceImpl implements PresellBuyInfoService {

	private PresellBuyInfoDao presellBuyInfoDao;
	private static final Logger logger=Logger.getLogger(PresellBuyInfoServiceImpl.class);
	
	@Override
	public Integer add(PresellBuyInfo presellBuyInfo) {
		if(presellBuyInfo==null){
			logger.info("数据为空，插入失败");
			return null;
		}
		return presellBuyInfoDao.add(presellBuyInfo);
	}

	@Override
	public Integer updateById(PresellBuyInfo presellBuyInfo) {
		if(presellBuyInfo==null || "".equals(presellBuyInfo.getId())){
			logger.info("更新数据主键为空");
			return null;
		}
			return presellBuyInfoDao.updateById(presellBuyInfo);
	}

	@Override
	public PresellBuyInfo getById(Integer id) {
		if(id==null){
			logger.info("查询主键为空");
			return null;
		}
		return presellBuyInfoDao.getById(id);
	}

	@Override
	public List<PresellBuyInfo> getByConditions(PresellBuyInfo presellBuyInfo) {
		if(presellBuyInfo==null){
			logger.info("传入数据为空");
			return null;
		}
		return presellBuyInfoDao.getByConditions(presellBuyInfo);
	}

	@Override
	public PresellBuyInfo getPresellDeliveryByPager(
			PresellBuyInfo presellBuyInfo) {
		return null;
	}

	public PresellBuyInfoDao getPresellBuyInfoDao() {
		return presellBuyInfoDao;
	}

	public void setPresellBuyInfoDao(PresellBuyInfoDao presellBuyInfoDao) {
		this.presellBuyInfoDao = presellBuyInfoDao;
	}

	@Override
	public List<PresellBuyInfo> getByTransId(List<String> transId) {
		if(transId==null){
			logger.info("传入数据为空");
			return null;
		}
		return presellBuyInfoDao.getByTransId(transId);
	}

}
