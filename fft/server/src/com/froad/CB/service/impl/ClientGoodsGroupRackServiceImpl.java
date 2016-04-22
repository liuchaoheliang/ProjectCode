package com.froad.CB.service.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.ClientGoodsGroupRackDao;
import com.froad.CB.po.ClientGoodsGroupRack;
import com.froad.CB.service.ClientGoodsGroupRackService;

@WebService(endpointInterface="com.froad.CB.service.ClientGoodsGroupRackService")
public class ClientGoodsGroupRackServiceImpl implements ClientGoodsGroupRackService{

	private static final Logger logger=Logger.getLogger(GoodsGroupRackServiceImpl.class);
	
	private ClientGoodsGroupRackDao clientGoodsGroupRackDao;
	
	@Override
	public Integer addGoodsGroupRack(ClientGoodsGroupRack goodsGroupRack) {
		if(goodsGroupRack==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return clientGoodsGroupRackDao.addGoodsGroupRack(goodsGroupRack);
	}

	@Override
	public void deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败");
			return;
		}
		clientGoodsGroupRackDao.deleteById(id);
	}

	@Override
	public ClientGoodsGroupRack getGoodsGroupRackById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return clientGoodsGroupRackDao.getGoodsGroupRackById(id);
	}

	@Override
	public void updateById(ClientGoodsGroupRack goodsGroupRack) {
		if(goodsGroupRack==null||goodsGroupRack.getId()==null){
			logger.error("参数为空，更新失败");
			return;
		}
		clientGoodsGroupRackDao.updateById(goodsGroupRack);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，更新状态失败");
			return;
		}
		clientGoodsGroupRackDao.updateStateById(id, state);
	}

	public void setClientGoodsGroupRackDao(
			ClientGoodsGroupRackDao clientGoodsGroupRackDao) {
		this.clientGoodsGroupRackDao = clientGoodsGroupRackDao;
	}

	@Override
	public ClientGoodsGroupRack getClientGoodsGroupRackByPager(ClientGoodsGroupRack clientGoodsGroupRack) {
		if(clientGoodsGroupRack==null){
			logger.error("参数为空，分页查询失败");
			return null;
		}
		return clientGoodsGroupRackDao.getClientGoodsGroupRackByPager(clientGoodsGroupRack);
	}
}
