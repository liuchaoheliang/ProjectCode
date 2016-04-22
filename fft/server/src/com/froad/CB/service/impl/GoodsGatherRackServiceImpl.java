package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.GoodsGatherRackDao;
import com.froad.CB.po.GoodsGatherRack;
import com.froad.CB.service.GoodsGatherRackService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-31 
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsGatherRackService")
public class GoodsGatherRackServiceImpl implements GoodsGatherRackService {
	private static final Logger logger = Logger.getLogger(GoodsGatherRackServiceImpl.class);
	private GoodsGatherRackDao goodsGatherRackDao;
	
	@Override
	public Integer addGoodsGatherRack(GoodsGatherRack goodsGatherRack) {
		if(goodsGatherRack==null){
			logger.info("增加商品收款架参数不能为空！");
			return null;
		}
		return goodsGatherRackDao.insert(goodsGatherRack);
	}

	@Override
	public Integer updateById(GoodsGatherRack goodsGatherRack) {
		if(goodsGatherRack==null){
			logger.info("更新商品收款架参数不能为空！");
			return null;
		}
		return goodsGatherRackDao.updateById(goodsGatherRack);
	}

	@Override
	public GoodsGatherRack selectById(Integer id) {
		if(id==null){
			logger.info("查询商品收款架参数id不能为空！");
			return null;
		}
		return goodsGatherRackDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteById(String id) {
		if(id==null){
			logger.info("删除商品收款架参数id不能为空！");
			return null;
		}
		return goodsGatherRackDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(id==null){
			logger.info("删除商品收款架参数id不能为空！");
			return null;
		}
		return goodsGatherRackDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public List<GoodsGatherRack> getGoodsGatherRackByGoodsId(String goodsId) {
		if(goodsId==null){
			logger.info("查询商品收款架参数goodsId不能为空！");
			return null;
		}
		return goodsGatherRackDao.getGoodsGatherRackByGoodsId(goodsId);
	}

	@Override
	public List<GoodsGatherRack> getGoodsGatherRack(GoodsGatherRack goodsGatherRack) {
		if(goodsGatherRack==null){
			logger.info("查询商品收款架参数不能为空！");
			return null;
		}
		return goodsGatherRackDao.getGoodsGatherRack(goodsGatherRack);
	}

	@Override
	public GoodsGatherRack getGoodsGatherRackListByPager(
			GoodsGatherRack goodsGatherRack) {
		if(goodsGatherRack==null){
			logger.info("分页查询商品收款架参数不能为空！");
			return null;
		}
		return goodsGatherRackDao.getGoodsGatherRackListByPager(goodsGatherRack);
	}

	public GoodsGatherRackDao getGoodsGatherRackDao() {
		return goodsGatherRackDao;
	}

	public void setGoodsGatherRackDao(GoodsGatherRackDao goodsGatherRackDao) {
		this.goodsGatherRackDao = goodsGatherRackDao;
	}

}
