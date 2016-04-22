package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.ClientGoodsRebateRackImagesDao;
import com.froad.CB.po.ClientGoodsRebateRackImages;
import com.froad.CB.service.ClientGoodsRebateRackImagesService;

/**
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.ClientGoodsRebateRackImagesService")
public class ClientGoodsRebateRackImagesServiceImpl implements
		ClientGoodsRebateRackImagesService {

	private ClientGoodsRebateRackImagesDao clientGoodsRebateRackImagesDao;
	private static final Logger log = Logger.getLogger(PointCashRuleServiceImpl.class);

	public ClientGoodsRebateRackImagesDao getClientGoodsRebateRackImagesDao() {
		return clientGoodsRebateRackImagesDao;
	}
	
	public void setClientGoodsRebateRackImagesDao(
			ClientGoodsRebateRackImagesDao clientGoodsRebateRackImagesDao) {
		this.clientGoodsRebateRackImagesDao = clientGoodsRebateRackImagesDao;
	}

	@Override
	public Integer addClientGoodsRebateRackImages(ClientGoodsRebateRackImages cgcri) {
		Integer returnInt = null;
		
		try {
			returnInt = clientGoodsRebateRackImagesDao.addClientGoodsRebateRackImages(cgcri);
		} catch (Exception e) {
			log.error("添加对象时，数据库异常！", e);
		}
		return returnInt;
	}

	@Override
	public boolean deleteClientGoodsRebateRackImages(ClientGoodsRebateRackImages cgcri) {		
		boolean result=false;
		if(cgcri.getId() == null || cgcri == null)
			return result;
		
		try {
			result = clientGoodsRebateRackImagesDao.deleteClientGoodsRebateRackImages(cgcri);
		} catch (Exception e) {
			log.error("删除对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public boolean updateClientGoodsRebateRackImages(ClientGoodsRebateRackImages cgcri) {
		boolean result=false;
		if(cgcri == null || cgcri.getId() == null) return result;		
		try {
			result = clientGoodsRebateRackImagesDao.updateClientGoodsRebateRackImages(cgcri);
		} catch (Exception e) {
			log.error("更新对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public ClientGoodsRebateRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		ClientGoodsRebateRackImages cgcri = null;
		try {			
			cgcri = (ClientGoodsRebateRackImages) clientGoodsRebateRackImagesDao.getByPrimaryKey(id);
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<ClientGoodsRebateRackImages> getAllClientGoodsRebateRackImages() {
		List<ClientGoodsRebateRackImages> cgcri = null;
		try {
			cgcri = (List<ClientGoodsRebateRackImages>)clientGoodsRebateRackImagesDao.getAllClientGoodsRebateRackImages();
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<ClientGoodsRebateRackImages> getByClientGoodsRebateRackId(String rackId) {
		if(rackId == null) return null;
		List<ClientGoodsRebateRackImages> cgcri = null;
		cgcri = (List<ClientGoodsRebateRackImages>) clientGoodsRebateRackImagesDao.getByClientGoodsRebateRackId(rackId);
        return cgcri;
	}

}
