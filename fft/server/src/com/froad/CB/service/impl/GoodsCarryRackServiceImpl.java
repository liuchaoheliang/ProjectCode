package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.GoodsCarryRackDao;
import com.froad.CB.po.GoodsCarryRack;
import com.froad.CB.service.GoodsCarryRackService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-1  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsCarryRackService")
public class GoodsCarryRackServiceImpl implements GoodsCarryRackService {
	private static final Logger logger = Logger.getLogger(GoodsCarryRackServiceImpl.class);
	private GoodsCarryRackDao goodsCarryRackDao;
	
	@Override
	public Integer addGoodsCarryRack(GoodsCarryRack goodsCarryRack) {
		if(goodsCarryRack==null){
			logger.info("增加商品提现架参数不能为空！");
			return null;
		}
		return goodsCarryRackDao.insert(goodsCarryRack);
	}

	@Override
	public Integer updateById(GoodsCarryRack goodsCarryRack) {
		if(goodsCarryRack==null){
			logger.info("更新商品提现架参数不能为空！");
			return null;
		}
		return goodsCarryRackDao.updateById(goodsCarryRack);
	}

	@Override
	public GoodsCarryRack selectById(Integer id) {
		if(id==null){
			logger.info("查询商品提现架参数id不能为空！");
			return null;
		}
		return goodsCarryRackDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteById(String id) {
		if(id==null){
			logger.info("删除商品提现架参数id不能为空！");
			return null;
		}
		return goodsCarryRackDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(id==null){
			logger.info("删除商品提现架参数id不能为空！");
			return null;
		}
		return goodsCarryRackDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public List<GoodsCarryRack> getGoodsCarryRackByGoodsId(String goodsId) {
		if(goodsId==null){
			logger.info("查询商品提现架参数goodsId不能为空！");
			return null;
		}
		return goodsCarryRackDao.getGoodsCarryRackByGoodsId(goodsId);
	}

	@Override
	public List<GoodsCarryRack> getGoodsCarryRack(GoodsCarryRack goodsCarryRack) {
		if(goodsCarryRack==null){
			logger.info("查询商品提现架参数不能为空！");
			return null;
		}
		return goodsCarryRackDao.getGoodsCarryRack(goodsCarryRack);
	}

	@Override
	public GoodsCarryRack getGoodsCarryRackListByPager(
			GoodsCarryRack goodsCarryRack) {
		if(goodsCarryRack==null){
			logger.info("分页查询商品提现架参数不能为空！");
			return null;
		}
		return goodsCarryRackDao.getGoodsCarryRackListByPager(goodsCarryRack);
	}

	public GoodsCarryRackDao getGoodsCarryRackDao() {
		return goodsCarryRackDao;
	}

	public void setGoodsCarryRackDao(GoodsCarryRackDao goodsCarryRackDao) {
		this.goodsCarryRackDao = goodsCarryRackDao;
	}

}
