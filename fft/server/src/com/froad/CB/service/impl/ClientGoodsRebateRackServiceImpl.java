package com.froad.CB.service.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.ClientGoodsRebateRackDao;
import com.froad.CB.po.ClientGoodsRebateRack;
import com.froad.CB.service.ClientGoodsRebateRackService;

@WebService(endpointInterface="com.froad.CB.service.ClientGoodsRebateRackService")
public class ClientGoodsRebateRackServiceImpl implements ClientGoodsRebateRackService{
	
	private static final Logger logger=Logger.getLogger(GoodsRebateRackServiceImpl.class);

	private ClientGoodsRebateRackDao clientGoodsRebateRackDao;
	
	@Override
	public Integer addClientGoodsRebateRack(ClientGoodsRebateRack goodsRebateRack) {
		if(goodsRebateRack==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return clientGoodsRebateRackDao.addClientGoodsRebateRack(goodsRebateRack);
	}

	@Override
	public void deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败");
			return;
		}
		clientGoodsRebateRackDao.deleteById(id);
	}

	@Override
	public ClientGoodsRebateRack getClientGoodsRebateRackById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return clientGoodsRebateRackDao.getClientGoodsRebateRackById(id);
	}

	@Override
	public void updateById(ClientGoodsRebateRack goodsRebateRack) {
		if(goodsRebateRack==null||goodsRebateRack.getId()==null){
			logger.error("参数为空，更新失败");
			return;
		}
		clientGoodsRebateRackDao.updateById(goodsRebateRack);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，更新状态失败");
			return;
		}
		clientGoodsRebateRackDao.updateStateById(id, state);
	}

	public void setClientGoodsRebateRackDao(
			ClientGoodsRebateRackDao clientGoodsRebateRackDao) {
		this.clientGoodsRebateRackDao = clientGoodsRebateRackDao;
	}

	@Override
	public ClientGoodsRebateRack getGoodsRebateRackByPager(
			ClientGoodsRebateRack clientGoodsRebateRack) {
		if(clientGoodsRebateRack==null){
			logger.error("参数为空，分页查询失败");
			return null;
		}
		return clientGoodsRebateRackDao.getGoodsRebateRackByPager(clientGoodsRebateRack);
	}

	
}
