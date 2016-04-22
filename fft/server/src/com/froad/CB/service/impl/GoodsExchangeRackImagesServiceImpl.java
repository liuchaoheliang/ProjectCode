package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.GoodsExchangeRackImagesDao;
import com.froad.CB.po.GoodsExchangeRackImages;
import com.froad.CB.service.GoodsExchangeRackImagesService;

/**
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsExchangeRackImagesService")
public class GoodsExchangeRackImagesServiceImpl implements
		GoodsExchangeRackImagesService {

	private GoodsExchangeRackImagesDao goodsExchangeRackImagesDao;
	private static final Logger log = Logger.getLogger(PointCashRuleServiceImpl.class);

	public GoodsExchangeRackImagesDao getGoodsExchangeRackImagesDao() {
		return goodsExchangeRackImagesDao;
	}
	
	public void setGoodsExchangeRackImagesDao(
			GoodsExchangeRackImagesDao goodsExchangeRackImagesDao) {
		this.goodsExchangeRackImagesDao = goodsExchangeRackImagesDao;
	}

	@Override
	public Integer addGoodsExchangeRackImages(GoodsExchangeRackImages cgcri) {
		Integer returnInt = null;
		
		try {
			returnInt = goodsExchangeRackImagesDao.addGoodsExchangeRackImages(cgcri);
		} catch (Exception e) {
			log.error("添加对象时，数据库异常！", e);
		}
		return returnInt;
	}

	@Override
	public boolean deleteGoodsExchangeRackImages(GoodsExchangeRackImages cgcri) {		
		boolean result=false;
		if(cgcri == null)
			return result;
		
		try {
			result = goodsExchangeRackImagesDao.deleteGoodsExchangeRackImages(cgcri);
		} catch (Exception e) {
			log.error("删除对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public boolean updateGoodsExchangeRackImages(GoodsExchangeRackImages cgcri) {
		boolean result=false;
		if(cgcri == null) return result;		
		try {
			result = goodsExchangeRackImagesDao.updateGoodsExchangeRackImages(cgcri);
		} catch (Exception e) {
			log.error("更新对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public GoodsExchangeRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		GoodsExchangeRackImages cgcri = null;
		try {			
			cgcri = (GoodsExchangeRackImages) goodsExchangeRackImagesDao.getByPrimaryKey(id);
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<GoodsExchangeRackImages> getAllGoodsExchangeRackImages() {
		List<GoodsExchangeRackImages> cgcri = null;
		try {
			cgcri = (List<GoodsExchangeRackImages>)goodsExchangeRackImagesDao.getAllGoodsExchangeRackImages();
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<GoodsExchangeRackImages> getByGoodsExchangeRackId(String rackId) {
		if(rackId == null) return null;
		List<GoodsExchangeRackImages> cgcri = null;
		cgcri = (List<GoodsExchangeRackImages>) goodsExchangeRackImagesDao.getByGoodsExchangeRackId(rackId);
        return cgcri;
	}

}
