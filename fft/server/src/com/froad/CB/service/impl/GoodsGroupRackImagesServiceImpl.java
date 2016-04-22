package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.GoodsGroupRackImagesDao;
import com.froad.CB.po.GoodsGroupRackImages;
import com.froad.CB.service.GoodsGroupRackImagesService;

/**
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsGroupRackImagesService")
public class GoodsGroupRackImagesServiceImpl implements
		GoodsGroupRackImagesService {

	private GoodsGroupRackImagesDao goodsGroupRackImagesDao;
	private static final Logger log = Logger.getLogger(PointCashRuleServiceImpl.class);

	public GoodsGroupRackImagesDao getGoodsGroupRackImagesDao() {
		return goodsGroupRackImagesDao;
	}
	
	public void setGoodsGroupRackImagesDao(
			GoodsGroupRackImagesDao goodsGroupRackImagesDao) {
		this.goodsGroupRackImagesDao = goodsGroupRackImagesDao;
	}

	@Override
	public Integer addGoodsGroupRackImages(GoodsGroupRackImages cgcri) {
		Integer returnInt = null;
		
		try {
			returnInt = goodsGroupRackImagesDao.addGoodsGroupRackImages(cgcri);
		} catch (Exception e) {
			log.error("添加对象时，数据库异常！", e);
		}
		return returnInt;
	}

	@Override
	public boolean deleteGoodsGroupRackImages(GoodsGroupRackImages cgcri) {		
		boolean result=false;
		if(cgcri == null)
			return result;
		
		try {
			result = goodsGroupRackImagesDao.deleteGoodsGroupRackImages(cgcri);
		} catch (Exception e) {
			log.error("删除对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public boolean updateGoodsGroupRackImages(GoodsGroupRackImages cgcri) {
		boolean result=false;
		if(cgcri == null) return result;		
		try {
			result = goodsGroupRackImagesDao.updateGoodsGroupRackImages(cgcri);
		} catch (Exception e) {
			log.error("更新对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public GoodsGroupRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		GoodsGroupRackImages cgcri = null;
		try {			
			cgcri = (GoodsGroupRackImages) goodsGroupRackImagesDao.getByPrimaryKey(id);
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<GoodsGroupRackImages> getAllGoodsGroupRackImages() {
		List<GoodsGroupRackImages> cgcri = null;
		try {
			cgcri = (List<GoodsGroupRackImages>)goodsGroupRackImagesDao.getAllGoodsGroupRackImages();
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<GoodsGroupRackImages> getByGoodsGroupRackId(String rackId) {
		if(rackId == null) return null;
		List<GoodsGroupRackImages> cgcri = null;
		cgcri = (List<GoodsGroupRackImages>) goodsGroupRackImagesDao.getByGoodsGroupRackId(rackId);
        return cgcri;
	}

}
