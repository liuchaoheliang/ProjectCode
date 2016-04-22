package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.ClientGoodsCarryRackImagesDao;
import com.froad.CB.po.ClientGoodsCarryRackImages;
import com.froad.CB.service.ClientGoodsCarryRackImagesService;

/**
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.ClientGoodsCarryRackImagesService")
public class ClientGoodsCarryRackImagesServiceImpl implements
		ClientGoodsCarryRackImagesService {

	private ClientGoodsCarryRackImagesDao clientGoodsCarryRackImagesDao;
	private static final Logger log = Logger.getLogger(PointCashRuleServiceImpl.class);

	public ClientGoodsCarryRackImagesDao getClientGoodsCarryRackImagesDao() {
		return clientGoodsCarryRackImagesDao;
	}
	
	public void setClientGoodsCarryRackImagesDao(
			ClientGoodsCarryRackImagesDao clientGoodsCarryRackImagesDao) {
		this.clientGoodsCarryRackImagesDao = clientGoodsCarryRackImagesDao;
	}

	@Override
	public Integer addClientGoodsCarryRackImages(ClientGoodsCarryRackImages cgcri) {
		Integer returnInt = null;
		
		try {
			returnInt = clientGoodsCarryRackImagesDao.addClientGoodsCarryRackImages(cgcri);
		} catch (Exception e) {
			log.error("添加对象时，数据库异常！", e);
		}
		return returnInt;
	}

	@Override
	public boolean deleteClientGoodsCarryRackImages(ClientGoodsCarryRackImages cgcri) {		
		boolean result=false;
		if(cgcri == null || cgcri.getId() == null) return result;
		
		try {
			result = clientGoodsCarryRackImagesDao.deleteClientGoodsCarryRackImages(cgcri);
		} catch (Exception e) {
			log.error("删除对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public boolean updateClientGoodsCarryRackImages(ClientGoodsCarryRackImages cgcri) {
		boolean result=false;
		if(cgcri.getId() == null || cgcri == null) return result;		
		try {
			result = clientGoodsCarryRackImagesDao.updateClientGoodsCarryRackImages(cgcri);
		} catch (Exception e) {
			log.error("更新对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public ClientGoodsCarryRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		ClientGoodsCarryRackImages cgcri = null;
		try {			
			cgcri = (ClientGoodsCarryRackImages) clientGoodsCarryRackImagesDao.getByPrimaryKey(id);
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<ClientGoodsCarryRackImages> getAllClientGoodsCarryRackImages() {
		List<ClientGoodsCarryRackImages> cgcri = null;
		try {
			cgcri = (List<ClientGoodsCarryRackImages>)clientGoodsCarryRackImagesDao.getAllClientGoodsCarryRackImages();
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<ClientGoodsCarryRackImages> getByClientGoodsCarryRackId(String rackId) {
		if(rackId == null) return null;
		List<ClientGoodsCarryRackImages> cgcri = null;
		cgcri = (List<ClientGoodsCarryRackImages>)clientGoodsCarryRackImagesDao.getByClientGoodsCarryRackId(rackId);
        return cgcri;
	}

}
