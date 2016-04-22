package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.GoodsPresellRackImagesDao;
import com.froad.CB.po.GoodsPresellRackImages;
import com.froad.CB.service.GoodsPresellRackImagesService;
@WebService(endpointInterface="com.froad.CB.service.GoodsPresellRackImagesService")
public class GoodsPresellRackImagesServiceImpl implements
		GoodsPresellRackImagesService {

	private GoodsPresellRackImagesDao goodsPresellRackImagesDao;
	private Logger log=Logger.getLogger(GoodsPresellRackImagesServiceImpl.class);
	
	@Override
	public Integer add(GoodsPresellRackImages goodsPresellRackImages) {
		if(goodsPresellRackImages==null){
			log.info("传入参数有误");	
			return null;
		}
		return goodsPresellRackImagesDao.add(goodsPresellRackImages);
	}

	@Override
	public boolean updateById(GoodsPresellRackImages goodsPresellRackImages) {
		if(goodsPresellRackImages==null || "".equals(goodsPresellRackImages.getId())){
			log.info("传入参数有误");
			return false;
		}
		return goodsPresellRackImagesDao.updateById(goodsPresellRackImages);
	}

	@Override
	public GoodsPresellRackImages getById(Integer id) {
		if(id!=null){
			return goodsPresellRackImagesDao.getById(id);
		}else{
			log.info("查询Id为空");
		}
		return null;
	}

	@Override
	public List<GoodsPresellRackImages> getByConditions(
			GoodsPresellRackImages goodsPresellRackImages) {
		if(goodsPresellRackImages!=null){
			return goodsPresellRackImagesDao.getByConditions(goodsPresellRackImages);
		}else{
			log.info("传入参数为空");
		}
		return null;
	}

	public GoodsPresellRackImagesDao getGoodsPresellRackImagesDao() {
		return goodsPresellRackImagesDao;
	}

	public void setGoodsPresellRackImagesDao(
			GoodsPresellRackImagesDao goodsPresellRackImagesDao) {
		this.goodsPresellRackImagesDao = goodsPresellRackImagesDao;
	}

	@Override
	public boolean updateByRackId(GoodsPresellRackImages goodsPresellRackImages) {
		if(goodsPresellRackImages==null || "".equals(goodsPresellRackImages.getGoodsPresellRackId())){
			log.info("传入参数有误");
			return false;
		}
		return goodsPresellRackImagesDao.updateObject(goodsPresellRackImages);
	}
	
	

}
