package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.ClientGoodsGroupRackImagesDao;
import com.froad.CB.po.ClientGoodsGroupRackImages;
import com.froad.CB.service.ClientGoodsGroupRackImagesService;

/**
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.ClientGoodsGroupRackImagesService")
public class ClientGoodsGroupRackImagesServiceImpl implements
		ClientGoodsGroupRackImagesService {

	private ClientGoodsGroupRackImagesDao clientGoodsGroupRackImagesDao;
	private static final Logger log = Logger.getLogger(PointCashRuleServiceImpl.class);

	public ClientGoodsGroupRackImagesDao getClientGoodsGroupRackImagesDao() {
		return clientGoodsGroupRackImagesDao;
	}
	
	public void setClientGoodsGroupRackImagesDao(
			ClientGoodsGroupRackImagesDao clientGoodsGroupRackImagesDao) {
		this.clientGoodsGroupRackImagesDao = clientGoodsGroupRackImagesDao;
	}

	@Override
	public Integer addClientGoodsGroupRackImages(ClientGoodsGroupRackImages cgcri) {
		Integer returnInt = null;
		
		try {
			returnInt = clientGoodsGroupRackImagesDao.addClientGoodsGroupRackImages(cgcri);
		} catch (Exception e) {
			log.error("添加对象时，数据库异常！", e);
		}
		return returnInt;
	}

	@Override
	public boolean deleteClientGoodsGroupRackImages(ClientGoodsGroupRackImages cgcri) {		
		boolean result=false;
		if(cgcri == null || cgcri.getId() == null) return result;
		
		try {
			result = clientGoodsGroupRackImagesDao.deleteClientGoodsGroupRackImages(cgcri);
		} catch (Exception e) {
			log.error("删除对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public boolean updateClientGoodsGroupRackImages(ClientGoodsGroupRackImages cgcri) {
		boolean result=false;
		if(cgcri.getId() == null || cgcri == null) return result;		
		try {
			result = clientGoodsGroupRackImagesDao.updateClientGoodsGroupRackImages(cgcri);
		} catch (Exception e) {
			log.error("更新对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public ClientGoodsGroupRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		ClientGoodsGroupRackImages cgcri = null;
		try {			
			cgcri = (ClientGoodsGroupRackImages) clientGoodsGroupRackImagesDao.getByPrimaryKey(id);
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<ClientGoodsGroupRackImages> getAllClientGoodsGroupRackImages() {
		List<ClientGoodsGroupRackImages> cgcri = null;
		try {
			cgcri = (List<ClientGoodsGroupRackImages>)clientGoodsGroupRackImagesDao.getAllClientGoodsGroupRackImages();
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<ClientGoodsGroupRackImages> getByClientGoodsGroupRackId(String rackId) {
		if(rackId == null) return null;
		List<ClientGoodsGroupRackImages> cgcri = null;
		cgcri = (List<ClientGoodsGroupRackImages>) clientGoodsGroupRackImagesDao.getByClientGoodsGroupRackId(rackId);
        return cgcri;
	}

}
