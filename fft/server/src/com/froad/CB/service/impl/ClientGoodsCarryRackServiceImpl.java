package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.ClientGoodsCarryRackDao;
import com.froad.CB.po.ClientGoodsCarryRack;
import com.froad.CB.service.ClientGoodsCarryRackService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.ClientGoodsCarryRackService")
public class ClientGoodsCarryRackServiceImpl implements
		ClientGoodsCarryRackService {
	private static final Logger logger = Logger.getLogger(ClientGoodsCarryRackServiceImpl.class);
	private ClientGoodsCarryRackDao clientGoodsCarryRackDao;
	
	@Override
	public Integer addClientGoodsCarryRack(ClientGoodsCarryRack clientGoodsCarryRack) {
		if(clientGoodsCarryRack==null){
			logger.info("增加商品提现架参数不能为空！");
			return null;
		}
		return clientGoodsCarryRackDao.insert(clientGoodsCarryRack);
	}

	@Override
	public Integer updateById(ClientGoodsCarryRack clientGoodsCarryRack) {
		if(clientGoodsCarryRack==null){
			logger.info("更新商品提现架参数不能为空！");
			return null;
		}
		return clientGoodsCarryRackDao.updateById(clientGoodsCarryRack);
	}

	@Override
	public ClientGoodsCarryRack selectById(Integer id) {
		if(id==null){
			logger.info("查询商品提现架参数id不能为空！");
			return null;
		}
		return clientGoodsCarryRackDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteById(String id) {
		if(id==null){
			logger.info("删除商品提现架参数id不能为空！");
			return null;
		}
		return clientGoodsCarryRackDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(id==null){
			logger.info("删除商品提现架参数id不能为空！");
			return null;
		}
		return clientGoodsCarryRackDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public List<ClientGoodsCarryRack> getClientGoodsCarryRackByGoodsId(String goodsId) {
		if(goodsId==null){
			logger.info("查询商品提现架参数goodsId不能为空！");
			return null;
		}
		return clientGoodsCarryRackDao.getClientGoodsCarryRackByGoodsId(goodsId);
	}

	@Override
	public List<ClientGoodsCarryRack> getClientGoodsCarryRack(ClientGoodsCarryRack clientGoodsCarryRack) {
		if(clientGoodsCarryRack==null){
			logger.info("查询商品提现架参数不能为空！");
			return null;
		}
		return clientGoodsCarryRackDao.getClientGoodsCarryRack(clientGoodsCarryRack);
	}

	@Override
	public ClientGoodsCarryRack getClientGoodsCarryRackListByPager(
			ClientGoodsCarryRack clientGoodsCarryRack) {
		if(clientGoodsCarryRack==null){
			logger.info("分页查询商品提现架参数不能为空！");
			return null;
		}
		return clientGoodsCarryRackDao.getClientGoodsCarryRackListByPager(clientGoodsCarryRack);
	}

	public ClientGoodsCarryRackDao getClientGoodsCarryRackDao() {
		return clientGoodsCarryRackDao;
	}

	public void setClientGoodsCarryRackDao(ClientGoodsCarryRackDao clientGoodsCarryRackDao) {
		this.clientGoodsCarryRackDao = clientGoodsCarryRackDao;
	}

}
