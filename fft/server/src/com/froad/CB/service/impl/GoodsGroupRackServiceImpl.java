package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.GoodsGroupRackDao;
import com.froad.CB.po.GoodsGroupRack;
import com.froad.CB.service.GoodsGroupRackService;

@WebService(endpointInterface="com.froad.CB.service.GoodsGroupRackService")
public class GoodsGroupRackServiceImpl implements GoodsGroupRackService{

	private static final Logger logger=Logger.getLogger(GoodsGroupRackServiceImpl.class);
	
	private GoodsGroupRackDao goodsGroupRackDao;
	
	@Override
	public Integer addGoodsGroupRack(GoodsGroupRack goodsGroupRack) {
		if(goodsGroupRack==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return goodsGroupRackDao.addGoodsGroupRack(goodsGroupRack);
	}

	@Override
	public boolean deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败");
			return false;
		}
		try {
			goodsGroupRackDao.deleteById(id);
			return true;
		} catch (Exception e) {
			logger.error("删除操作异常",e);
			return false;
		}
	}

	@Override
	public GoodsGroupRack getGoodsGroupRackById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return goodsGroupRackDao.getGoodsGroupRackById(id);
	}

	@Override
	public boolean updateById(GoodsGroupRack goodsGroupRack) {
		if(goodsGroupRack==null||goodsGroupRack.getId()==null){
			logger.error("参数为空，更新失败");
			return false;
		}
		try {
			goodsGroupRackDao.updateById(goodsGroupRack);
			return true;
		} catch (Exception e) {
			logger.error("更新操作异常",e);
			return false;
		}
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，更新状态失败");
			return false;
		}
		try {
			goodsGroupRackDao.updateStateById(id, state);
			return true;
		} catch (Exception e) {
			logger.error("更新状态异常",e);
			return false;
		}
		
	}

	public void setGoodsGroupRackDao(GoodsGroupRackDao goodsGroupRackDao) {
		this.goodsGroupRackDao = goodsGroupRackDao;
	}

	@Override
	public GoodsGroupRack getGoodsGroupRackByPager(GoodsGroupRack goodsGroupRack) {
		if(goodsGroupRack==null){
			logger.error("参数为空，分页查询失败");
			return null;
		}
		return goodsGroupRackDao.getGoodsGroupRackByPager(goodsGroupRack);
	}

	@Override
	public List<GoodsGroupRack> getIndexGoodsRack() {
		return goodsGroupRackDao.getIndexGoodsRack();
	}

}
