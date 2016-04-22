package com.froad.CB.service.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.GoodsRebateRackDao;
import com.froad.CB.po.GoodsRebateRack;
import com.froad.CB.service.GoodsRebateRackService;

@WebService(endpointInterface="com.froad.CB.service.GoodsRebateRackService")
public class GoodsRebateRackServiceImpl implements GoodsRebateRackService{
	
	private static final Logger logger=Logger.getLogger(GoodsRebateRackServiceImpl.class);

	private GoodsRebateRackDao goodsRebateRackDao;
	
	@Override
	public Integer addGoodsRebateRack(GoodsRebateRack goodsRebateRack) {
		if(goodsRebateRack==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return goodsRebateRackDao.addGoodsRebateRack(goodsRebateRack);
	}

	@Override
	public void deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败");
			return;
		}
		goodsRebateRackDao.deleteById(id);
	}

	@Override
	public GoodsRebateRack getGoodsRebateRackById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return goodsRebateRackDao.getGoodsRebateRackById(id);
	}

	@Override
	public void updateById(GoodsRebateRack goodsRebateRack) {
		if(goodsRebateRack==null||goodsRebateRack.getId()==null){
			logger.error("参数为空，更新失败");
			return;
		}
		goodsRebateRackDao.updateById(goodsRebateRack);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，更新状态失败");
			return;
		}
		goodsRebateRackDao.updateStateById(id, state);
	}

	public void setGoodsRebateRackDao(GoodsRebateRackDao goodsRebateRackDao) {
		this.goodsRebateRackDao = goodsRebateRackDao;
	}

	@Override
	public GoodsRebateRack getGoodsRebateRackByPager(
			GoodsRebateRack goodsRebateRack) {
		if(goodsRebateRack==null){
			logger.error("参数为空，查询失败");
			return null;
		}
		return goodsRebateRackDao.getGoodsRebateRackByPager(goodsRebateRack);
	}

}
