package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.dao.*;
import com.froad.CB.po.*;
import org.apache.log4j.Logger;

import com.froad.CB.dao.impl.PresellDeliveryDaoImpl;
import com.froad.CB.service.PresellDeliveryService;
@WebService(endpointInterface="com.froad.CB.service.PresellDeliveryService")

public class PresellDeliveryServiceImpl implements PresellDeliveryService {
	
	
	private PresellDeliveryDao presellDeliveryDao;
	private static final Logger logger=Logger.getLogger(PresellDeliveryServiceImpl.class);
	
	@Override
	public Integer add(PresellDelivery presellDelivery) {
		if(presellDelivery==null){
			logger.info("数据为空，插入失败");
			return null;
		}
		return presellDeliveryDao.add(presellDelivery);
	}

	@Override
	public Integer updateById(PresellDelivery presellDelivery) {
		if(presellDelivery==null || "".equals(presellDelivery.getId())){
			logger.info("更新数据主键为空");
			return null;
		}
			return presellDeliveryDao.updateById(presellDelivery);
	}

	@Override
	public PresellDelivery getById(Integer id) {
		if(id==null){
			logger.info("查询主键为空");
			return null;
		}
		return presellDeliveryDao.getById(id);
	}

	@Override
	public List<PresellDelivery> getByConditions(PresellDelivery presellDelivery) {
		if(presellDelivery==null){
			logger.info("传入数据为空");
		}
		return presellDeliveryDao.getByConditions(presellDelivery);
	}
	
	@Override
	public PresellDelivery getByPager(PresellDelivery presellDelivery) {
		if(presellDelivery==null){
			logger.info("传入数据为空");
		}		
		return presellDeliveryDao.getByPager(presellDelivery);
	}
    @Override
    public List<PresellDelivery> getByRackId(Integer rackId)
    {
        if(rackId==null){
        			logger.info("查询主键为空");
        			return null;
        		}
       return presellDeliveryDao.getByRackId(rackId);
    }


	public PresellDeliveryDao getPresellDeliveryDao() {
		return presellDeliveryDao;
	}

	public void setPresellDeliveryDao(PresellDeliveryDao presellDeliveryDao) {
		this.presellDeliveryDao = presellDeliveryDao;
	}

}
