package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.ClientGoodsGatherRackImagesDao;
import com.froad.CB.po.ClientGoodsGatherRackImages;
import com.froad.CB.service.ClientGoodsGatherRackImagesService;

/**
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.ClientGoodsGatherRackImagesService")
public class ClientGoodsGatherRackImagesServiceImpl implements
		ClientGoodsGatherRackImagesService {

	private ClientGoodsGatherRackImagesDao clientGoodsGatherRackImagesDao;
	private static final Logger log = Logger.getLogger(PointCashRuleServiceImpl.class);

	public ClientGoodsGatherRackImagesDao getClientGoodsGatherRackImagesDao() {
		return clientGoodsGatherRackImagesDao;
	}
	
	public void setClientGoodsGatherRackImagesDao(
			ClientGoodsGatherRackImagesDao clientGoodsGatherRackImagesDao) {
		this.clientGoodsGatherRackImagesDao = clientGoodsGatherRackImagesDao;
	}

	@Override
	public Integer addClientGoodsGatherRackImages(ClientGoodsGatherRackImages cgcri) {
		Integer returnInt = null;
		
		try {
			returnInt = clientGoodsGatherRackImagesDao.addClientGoodsGatherRackImages(cgcri);
		} catch (Exception e) {
			log.error("添加对象时，数据库异常！", e);
		}
		return returnInt;
	}

	@Override
	public boolean deleteClientGoodsGatherRackImages(ClientGoodsGatherRackImages cgcri) {		
		boolean result=false;
		if(cgcri == null || cgcri.getId() == null) return result;
		
		try {
			result = clientGoodsGatherRackImagesDao.deleteClientGoodsGatherRackImages(cgcri);
		} catch (Exception e) {
			log.error("删除对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public boolean updateClientGoodsGatherRackImages(ClientGoodsGatherRackImages cgcri) {
		boolean result=false;
		if(cgcri.getId() == null || cgcri == null) return result;		
		try {
			result = clientGoodsGatherRackImagesDao.updateClientGoodsGatherRackImages(cgcri);
		} catch (Exception e) {
			log.error("更新对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public ClientGoodsGatherRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		ClientGoodsGatherRackImages cgcri = null;
		try {			
			cgcri = (ClientGoodsGatherRackImages) clientGoodsGatherRackImagesDao.getByPrimaryKey(id);
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<ClientGoodsGatherRackImages> getAllClientGoodsGatherRackImages() {
		List<ClientGoodsGatherRackImages> cgcri = null;
		try {
			cgcri = (List<ClientGoodsGatherRackImages>)clientGoodsGatherRackImagesDao.getAllClientGoodsGatherRackImages();
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<ClientGoodsGatherRackImages> getByClientGoodsGatherRackId(String rackId) {
		if(rackId == null) return null;
		List<ClientGoodsGatherRackImages> cgcri = null;
		cgcri = (List<ClientGoodsGatherRackImages>) clientGoodsGatherRackImagesDao.getByClientGoodsGatherRackId(rackId);
        return cgcri;
	}

}
