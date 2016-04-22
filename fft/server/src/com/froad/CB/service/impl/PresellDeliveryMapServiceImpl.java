package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.PresellDeliveryDao;
import com.froad.CB.dao.PresellDeliveryMapDao;
import com.froad.CB.po.PresellDeliveryMap;
import com.froad.CB.service.PresellDeliveryMapService;
@WebService(endpointInterface="com.froad.CB.service.PresellDeliveryMapService")
public class PresellDeliveryMapServiceImpl implements PresellDeliveryMapService {
	
	private PresellDeliveryMapDao presellDeliveryMapDao;
	private static final Logger logger=Logger.getLogger(PresellDeliveryMapServiceImpl.class);
	
	@Override
	public Integer add(PresellDeliveryMap presellDeliveryMap) {
		if(presellDeliveryMap==null){
			logger.info("数据为空，插入失败");
			return null;
		}
		return presellDeliveryMapDao.add(presellDeliveryMap);
	}

	@Override
	public Integer deleteByRackId(Integer RackId) {
		if(RackId==null){
			logger.info("数据为空，删除失败");
			return null;
		}
		return presellDeliveryMapDao.deleteByRackId(RackId);
	}

	@Override
	public Integer updateById(PresellDeliveryMap presellDeliveryMap) {
		if(presellDeliveryMap==null || "".equals(presellDeliveryMap.getId())){
			logger.info("更新数据主键为空");
			return null;
		}
			return presellDeliveryMapDao.updateById(presellDeliveryMap);
	}

	@Override
	public PresellDeliveryMap getById(Integer id) {
		if(id==null){
			logger.info("查询主键为空");
			return null;
		}
		return presellDeliveryMapDao.getById(id);
	}

	@Override
	public List<PresellDeliveryMap> getByConditions(
			PresellDeliveryMap presellDeliveryMap) {
		if(presellDeliveryMap==null){
			logger.info("传入数据为空");
		}
		return presellDeliveryMapDao.getByConditions(presellDeliveryMap);
	}

	public PresellDeliveryMapDao getPresellDeliveryMapDao() {
		return presellDeliveryMapDao;
	}

	public void setPresellDeliveryMapDao(PresellDeliveryMapDao presellDeliveryMapDao) {
		this.presellDeliveryMapDao = presellDeliveryMapDao;
	}

	@Override
	public PresellDeliveryMap getBypager(PresellDeliveryMap presellDeliveryMap) {
		if(presellDeliveryMap !=null){
			return presellDeliveryMapDao.getBypager(presellDeliveryMap);
		}else{
			logger.info("传入参数为空");
			return null;
		}
	}
	
}
