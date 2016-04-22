package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.ClientGoodsGatherRackDao;
import com.froad.CB.po.ClientGoodsGatherRack;
import com.froad.CB.service.ClientGoodsGatherRackService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.ClientGoodsGatherRackService")
public class ClientGoodsGatherRackServiceImpl implements
		ClientGoodsGatherRackService {
	private static final Logger logger = Logger.getLogger(ClientGoodsGatherRackServiceImpl.class);
	private ClientGoodsGatherRackDao clientGoodsGatherRackDao;
	
	@Override
	public Integer addClientGoodsGatherRack(ClientGoodsGatherRack clientGoodsGatherRack) {
		if(clientGoodsGatherRack==null){
			logger.info("增加商品收款架参数不能为空！");
			return null;
		}
		return clientGoodsGatherRackDao.insert(clientGoodsGatherRack);
	}

	@Override
	public Integer updateById(ClientGoodsGatherRack clientGoodsGatherRack) {
		if(clientGoodsGatherRack==null){
			logger.info("更新商品收款架参数不能为空！");
			return null;
		}
		return clientGoodsGatherRackDao.updateById(clientGoodsGatherRack);
	}

	@Override
	public ClientGoodsGatherRack selectById(Integer id) {
		if(id==null){
			logger.info("查询商品收款架参数id不能为空！");
			return null;
		}
		return clientGoodsGatherRackDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteById(String id) {
		if(id==null){
			logger.info("删除商品收款架参数id不能为空！");
			return null;
		}
		return clientGoodsGatherRackDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(id==null){
			logger.info("删除商品收款架参数id不能为空！");
			return null;
		}
		return clientGoodsGatherRackDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public List<ClientGoodsGatherRack> getClientGoodsGatherRackByGoodsId(String goodsId) {
		if(goodsId==null){
			logger.info("查询商品收款架参数goodsId不能为空！");
			return null;
		}
		return clientGoodsGatherRackDao.getClientGoodsGatherRackByGoodsId(goodsId);
	}

	@Override
	public List<ClientGoodsGatherRack> getClientGoodsGatherRack(ClientGoodsGatherRack clientGoodsGatherRack) {
		if(clientGoodsGatherRack==null){
			logger.info("查询商品收款架参数不能为空！");
			return null;
		}
		return clientGoodsGatherRackDao.getClientGoodsGatherRack(clientGoodsGatherRack);
	}

	@Override
	public ClientGoodsGatherRack getClientGoodsGatherRackListByPager(
			ClientGoodsGatherRack clientGoodsGatherRack) {
		if(clientGoodsGatherRack==null){
			logger.info("分页查询商品收款架参数不能为空！");
			return null;
		}
		return clientGoodsGatherRackDao.getClientGoodsGatherRackListByPager(clientGoodsGatherRack);
	}

	public ClientGoodsGatherRackDao getClientGoodsGatherRackDao() {
		return clientGoodsGatherRackDao;
	}

	public void setClientGoodsGatherRackDao(ClientGoodsGatherRackDao ClientGoodsGatherRackDao) {
		this.clientGoodsGatherRackDao = clientGoodsGatherRackDao;
	}

}
