package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.GoodsGatherRackImagesDao;
import com.froad.CB.po.GoodsGatherRackImages;
import com.froad.CB.service.GoodsGatherRackImagesService;

/**
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsGatherRackImagesService")
public class GoodsGatherRackImagesServiceImpl implements
		GoodsGatherRackImagesService {

	private GoodsGatherRackImagesDao goodsGatherRackImagesDao;
	private static final Logger log = Logger.getLogger(PointCashRuleServiceImpl.class);

	public GoodsGatherRackImagesDao getGoodsGatherRackImagesDao() {
		return goodsGatherRackImagesDao;
	}
	
	public void setGoodsGatherRackImagesDao(
			GoodsGatherRackImagesDao goodsGatherRackImagesDao) {
		this.goodsGatherRackImagesDao = goodsGatherRackImagesDao;
	}

	@Override
	public Integer addGoodsGatherRackImages(GoodsGatherRackImages cgcri) {
		Integer returnInt = null;
		
		try {
			returnInt = goodsGatherRackImagesDao.addGoodsGatherRackImages(cgcri);
		} catch (Exception e) {
			log.error("添加对象时，数据库异常！", e);
		}
		return returnInt;
	}

	@Override
	public boolean deleteGoodsGatherRackImages(GoodsGatherRackImages cgcri) {		
		boolean result=false;
		if(cgcri == null)
			return result;
		
		try {
			result = goodsGatherRackImagesDao.deleteGoodsGatherRackImages(cgcri);
		} catch (Exception e) {
			log.error("删除对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public boolean updateGoodsGatherRackImages(GoodsGatherRackImages cgcri) {
		boolean result=false;
		if(cgcri == null) return result;		
		try {
			result = goodsGatherRackImagesDao.updateGoodsGatherRackImages(cgcri);
		} catch (Exception e) {
			log.error("更新对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public GoodsGatherRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		GoodsGatherRackImages cgcri = null;
		try {			
			cgcri = (GoodsGatherRackImages) goodsGatherRackImagesDao.getByPrimaryKey(id);
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<GoodsGatherRackImages> getAllGoodsGatherRackImages() {
		List<GoodsGatherRackImages> cgcri = null;
		try {
			cgcri = (List<GoodsGatherRackImages>)goodsGatherRackImagesDao.getAllGoodsGatherRackImages();
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<GoodsGatherRackImages> getByGoodsGatherRackId(String rackId) {
		if(rackId == null) return null;
		List<GoodsGatherRackImages> cgcri = null;
		cgcri = (List<GoodsGatherRackImages>) goodsGatherRackImagesDao.getByGoodsGatherRackId(rackId);
        return cgcri;
	}

}
