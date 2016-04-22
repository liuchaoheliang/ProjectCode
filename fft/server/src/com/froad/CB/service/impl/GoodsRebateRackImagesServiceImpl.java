package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.GoodsRebateRackImagesDao;
import com.froad.CB.po.GoodsRebateRackImages;
import com.froad.CB.service.GoodsRebateRackImagesService;

/**
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsRebateRackImagesService")
public class GoodsRebateRackImagesServiceImpl implements
		GoodsRebateRackImagesService {

	private GoodsRebateRackImagesDao goodsRebateRackImagesDao;
	private static final Logger log = Logger.getLogger(PointCashRuleServiceImpl.class);

	public GoodsRebateRackImagesDao getGoodsRebateRackImagesDao() {
		return goodsRebateRackImagesDao;
	}
	
	public void setGoodsRebateRackImagesDao(
			GoodsRebateRackImagesDao goodsRebateRackImagesDao) {
		this.goodsRebateRackImagesDao = goodsRebateRackImagesDao;
	}

	@Override
	public Integer addGoodsRebateRackImages(GoodsRebateRackImages cgcri) {
		Integer returnInt = null;
		
		try {
			returnInt = goodsRebateRackImagesDao.addGoodsRebateRackImages(cgcri);
		} catch (Exception e) {
			log.error("添加对象时，数据库异常！", e);
		}
		return returnInt;
	}

	@Override
	public boolean deleteGoodsRebateRackImages(GoodsRebateRackImages cgcri) {		
		boolean result=false;
		if(cgcri == null)
			return result;
		
		try {
			result = goodsRebateRackImagesDao.deleteGoodsRebateRackImages(cgcri);
		} catch (Exception e) {
			log.error("删除对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public boolean updateGoodsRebateRackImages(GoodsRebateRackImages cgcri) {
		boolean result=false;
		if(cgcri == null) return result;		
		try {
			result = goodsRebateRackImagesDao.updateGoodsRebateRackImages(cgcri);
		} catch (Exception e) {
			log.error("更新对象时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public GoodsRebateRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		GoodsRebateRackImages cgcri = null;
		try {			
			cgcri = (GoodsRebateRackImages) goodsRebateRackImagesDao.getByPrimaryKey(id);
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<GoodsRebateRackImages> getAllGoodsRebateRackImages() {
		List<GoodsRebateRackImages> cgcri = null;
		try {
			cgcri = (List<GoodsRebateRackImages>)goodsRebateRackImagesDao.getAllGoodsRebateRackImages();
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return cgcri;
	}

	@Override
	public List<GoodsRebateRackImages> getByGoodsRebateRackId(String rackId) {
		if(rackId == null) return null;
		List<GoodsRebateRackImages> cgcri = null;
		cgcri = (List<GoodsRebateRackImages>) goodsRebateRackImagesDao.getByGoodsRebateRackId(rackId);
        return cgcri;
	}

}
