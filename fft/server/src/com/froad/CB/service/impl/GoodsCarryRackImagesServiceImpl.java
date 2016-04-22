package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.GoodsCarryRackImagesDao;
import com.froad.CB.po.GoodsCarryRackImages;
import com.froad.CB.service.GoodsCarryRackImagesService;

/**
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsCarryRackImagesService")
public class GoodsCarryRackImagesServiceImpl implements
		GoodsCarryRackImagesService {

	private GoodsCarryRackImagesDao goodsCarryRackImagesDao;
	private static final Logger log = Logger.getLogger(PointCashRuleServiceImpl.class);

	public GoodsCarryRackImagesDao getGoodsCarryRackImagesDao() {
		return goodsCarryRackImagesDao;
	}
	
	public void setGoodsCarryRackImagesDao(
			GoodsCarryRackImagesDao goodsCarryRackImagesDao) {
		this.goodsCarryRackImagesDao = goodsCarryRackImagesDao;
	}

	@Override
	public Integer addGoodsGroupRackImages(GoodsCarryRackImages cgcri) {
		Integer returnInt = null;
		
		try {
			returnInt = goodsCarryRackImagesDao.addGoodsCarryRackImages(cgcri);
		} catch (Exception e) {
			log.error("添加对象时，数据库异常！", e);
		}
		return returnInt;
	}

	@Override
	public boolean deleteGoodsGroupRackImages(GoodsCarryRackImages cgcri) {		
		boolean result=false;
		if(cgcri == null)
			return result;
		
		try {
			result = goodsCarryRackImagesDao.deleteGoodsCarryRackImages(cgcri);
		} catch (Exception e) {
			log.error("删除对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public boolean updateGoodsGroupRackImages(GoodsCarryRackImages cgcri) {
		boolean result=false;
		if(cgcri == null) return result;		
		try {
			result = goodsCarryRackImagesDao.updateGoodsCarryRackImages(cgcri);
		} catch (Exception e) {
			log.error("更新对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public GoodsCarryRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		GoodsCarryRackImages cgcri = null;
		try {			
			cgcri = (GoodsCarryRackImages) goodsCarryRackImagesDao.getByPrimaryKey(id);
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<GoodsCarryRackImages> getAllGoodsGroupRackImages() {
		List<GoodsCarryRackImages> cgcri = null;
		try {
			cgcri = (List<GoodsCarryRackImages>)goodsCarryRackImagesDao.getAllGoodsCarryRackImages();
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<GoodsCarryRackImages> getByGoodsCarryRackId(String rackId) {
		if(rackId == null) return null;
		List<GoodsCarryRackImages> cgcri = null;
		cgcri = (List<GoodsCarryRackImages>) goodsCarryRackImagesDao.getByGoodsCarryRackId(rackId);
        return cgcri;
	}

}
