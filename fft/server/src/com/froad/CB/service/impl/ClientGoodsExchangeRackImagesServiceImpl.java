package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.ClientGoodsExchangeRackImagesDao;
import com.froad.CB.po.ClientGoodsExchangeRackImages;
import com.froad.CB.service.ClientGoodsExchangeRackImagesService;

/**
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.ClientGoodsExchangeRackImagesService")
public class ClientGoodsExchangeRackImagesServiceImpl implements
		ClientGoodsExchangeRackImagesService {

	private ClientGoodsExchangeRackImagesDao clientGoodsExchangeRackImagesDao;
	private static final Logger log = Logger.getLogger(PointCashRuleServiceImpl.class);

	public ClientGoodsExchangeRackImagesDao getClientGoodsExchangeRackImagesDao() {
		return clientGoodsExchangeRackImagesDao;
	}
	
	public void setClientGoodsExchangeRackImagesDao(
			ClientGoodsExchangeRackImagesDao clientGoodsExchangeRackImagesDao) {
		this.clientGoodsExchangeRackImagesDao = clientGoodsExchangeRackImagesDao;
	}

	@Override
	public Integer addClientGoodsExchangeRackImages(ClientGoodsExchangeRackImages cgcri) {
		Integer returnInt = null;
		
		try {
			returnInt = clientGoodsExchangeRackImagesDao.addClientGoodsExchangeRackImages(cgcri);
		} catch (Exception e) {
			log.error("添加对象时，数据库异常！", e);
		}
		return returnInt;
	}

	@Override
	public boolean deleteClientGoodsExchangeRackImages(ClientGoodsExchangeRackImages cgcri) {		
		boolean result=false;
		if(cgcri == null || cgcri.getId() == null) return result;
		
		try {
			result = clientGoodsExchangeRackImagesDao.deleteClientGoodsExchangeRackImages(cgcri);
		} catch (Exception e) {
			log.error("删除对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public boolean updateClientGoodsExchangeRackImages(ClientGoodsExchangeRackImages cgcri) {
		boolean result=false;
		if(cgcri.getId() == null || cgcri == null) return result;		
		try {
			result = clientGoodsExchangeRackImagesDao.updateClientGoodsExchangeRackImages(cgcri);
		} catch (Exception e) {
			log.error("更新对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public ClientGoodsExchangeRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		ClientGoodsExchangeRackImages cgcri = null;
		try {			
			cgcri = (ClientGoodsExchangeRackImages) clientGoodsExchangeRackImagesDao.getByPrimaryKey(id);
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<ClientGoodsExchangeRackImages> getAllClientGoodsExchangeRackImages() {
		List<ClientGoodsExchangeRackImages> cgcri = null;
		try {
			cgcri = (List<ClientGoodsExchangeRackImages>)clientGoodsExchangeRackImagesDao.getAllClientGoodsExchangeRackImages();
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<ClientGoodsExchangeRackImages> getByClientGoodsExchangeRackId(String rackId) {
		if(rackId == null) return null;
		List<ClientGoodsExchangeRackImages> cgcri = null;
		cgcri = (List<ClientGoodsExchangeRackImages>) clientGoodsExchangeRackImagesDao.getByClientGoodsExchangeRackId(rackId);
        return cgcri;
	}

}
