package com.froad.CB.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.GoodsPresellRackDao;
import com.froad.CB.po.GoodsPresellRack;
import com.froad.CB.service.GoodsPresellRackService;
@WebService(endpointInterface="com.froad.CB.service.GoodsPresellRackService")
public class GoodsPresellRackServiceImpl implements GoodsPresellRackService {
	
	private GoodsPresellRackDao goodsPresellRackDao;
	
	private Logger log=Logger.getLogger(GoodsPresellRackServiceImpl.class);
	
	@Override
	public Integer add(GoodsPresellRack goodsPresellRack) {
		if(goodsPresellRack==null){
			log.info("传入参数有误");
			return 0;
		}
		return goodsPresellRackDao.add(goodsPresellRack);
	}

	@Override
	public boolean updateById(GoodsPresellRack goodsPresellRack) {
		if(goodsPresellRack ==null || "".equals(goodsPresellRack.getId())){
			log.info("传入参数有误");
			return false;
		}
		return goodsPresellRackDao.updateById(goodsPresellRack);
	}

	@Override
	public GoodsPresellRack getById(Integer id) {
		if(id==null){
			log.info("传入参数为空");
			return null;
		}
		return goodsPresellRackDao.getById(id);
	}

	@Override
	public List<GoodsPresellRack> getByConditions(
			GoodsPresellRack goodsPresellRack) {
		if(goodsPresellRack==null){
			log.info("传入参数为空");
			return null;
		}
		return goodsPresellRackDao.getByConditions(goodsPresellRack);
	}

	@Override
	public GoodsPresellRack getByPager(GoodsPresellRack goodsPresellRack) {
		if(goodsPresellRack==null){
			log.info("传入参数为空");
			return null;
		}
		return goodsPresellRackDao.getByPager(goodsPresellRack);

	}

	public GoodsPresellRackDao getGoodsPresellRackDao() {
		return goodsPresellRackDao;
	}

	public void setGoodsPresellRackDao(GoodsPresellRackDao goodsPresellRackDao) {
		this.goodsPresellRackDao = goodsPresellRackDao;
	}

	@Override
	public Date getServerNowTime() {
		return new Date();
	}

	@Override
	public List<GoodsPresellRack> getHistory(GoodsPresellRack goodsPresellRack) {
		if(goodsPresellRack==null){
			log.info("传入参数为空");
			return null;
		}
		return goodsPresellRackDao.getHistory(goodsPresellRack);
	}
	
	

}
