package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.ClientGoodsExchangeRackDao;
import com.froad.CB.po.ClientGoodsExchangeRack;
import com.froad.CB.service.ClientGoodsExchangeRackService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.ClientGoodsExchangeRackService")
public class ClientGoodsExchangeRackServiceImpl implements
		ClientGoodsExchangeRackService {
	private static final Logger logger = Logger.getLogger(ClientGoodsExchangeRackServiceImpl.class);
	private ClientGoodsExchangeRackDao clientGoodsExchangeRackDao;
	
	@Override
	public Integer addClientGoodsExchangeRack(ClientGoodsExchangeRack clientGoodsExchangeRack) {
		if(clientGoodsExchangeRack==null){
			logger.info("增加商品兑换架参数不能为空！");
			return null;
		}
		return clientGoodsExchangeRackDao.insert(clientGoodsExchangeRack);
	}

	@Override
	public Integer updateById(ClientGoodsExchangeRack clientGoodsExchangeRack) {
		if(clientGoodsExchangeRack==null){
			logger.info("更新商品兑换架参数不能为空！");
			return null;
		}
		return clientGoodsExchangeRackDao.updateById(clientGoodsExchangeRack);
	}

	@Override
	public ClientGoodsExchangeRack selectById(Integer id) {
		if(id==null){
			logger.info("查询商品兑换架参数id不能为空！");
			return null;
		}
		return clientGoodsExchangeRackDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteById(String id) {
		if(id==null){
			logger.info("删除商品兑换架参数id不能为空！");
			return null;
		}
		return clientGoodsExchangeRackDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(id==null){
			logger.info("删除商品兑换架参数id不能为空！");
			return null;
		}
		return clientGoodsExchangeRackDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public List<ClientGoodsExchangeRack> getClientGoodsExchangeRackByGoodsId(String goodsId) {
		if(goodsId==null){
			logger.info("查询商品兑换架参数goodsId不能为空！");
			return null;
		}
		return clientGoodsExchangeRackDao.getClientGoodsExchangeRackByGoodsId(goodsId);
	}

	@Override
	public List<ClientGoodsExchangeRack> getClientGoodsExchangeRack(
			ClientGoodsExchangeRack clientGoodsExchangeRack) {
		if(clientGoodsExchangeRack==null){
			logger.info("查询商品兑换架参数不能为空！");
			return null;
		}
		return clientGoodsExchangeRackDao.getClientGoodsExchangeRack(clientGoodsExchangeRack);
	}

	@Override
	public ClientGoodsExchangeRack getClientGoodsExchangeRackListByPager(
			ClientGoodsExchangeRack clientGoodsExchangeRack) {
		if(clientGoodsExchangeRack==null){
			logger.info("分页查询商品兑换架参数不能为空！");
			return null;
		}
		return clientGoodsExchangeRackDao.getClientGoodsExchangeRackListByPager(clientGoodsExchangeRack);
	}

	public ClientGoodsExchangeRackDao getClientGoodsExchangeRackDao() {
		return clientGoodsExchangeRackDao;
	}

	public void setClientGoodsExchangeRackDao(ClientGoodsExchangeRackDao clientGoodsExchangeRackDao) {
		this.clientGoodsExchangeRackDao = clientGoodsExchangeRackDao;
	}

	@Override
	public List<ClientGoodsExchangeRack> getClientGoodsExchangeRackByGoodsCategoryId(
			String goodsCategoryId) {
		if(goodsCategoryId==null){
			logger.info("查询参数goodsCategoryId不能为空！");
			return null;
		}
		return clientGoodsExchangeRackDao.getClientGoodsExchangeRackByGoodsCategoryId(goodsCategoryId);
	}


}
