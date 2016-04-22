package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.GoodsDao;
import com.froad.CB.po.Goods;
import com.froad.CB.service.GoodsService;

@WebService(endpointInterface="com.froad.CB.service.GoodsService")
public class GoodsServiceImpl implements GoodsService{

	private static final Logger logger=Logger.getLogger(GoodsServiceImpl.class);
	
	private GoodsDao goodsDao;
	
	@Override
	public Integer addGoods(Goods goods) {
		if(goods==null){
			logger.error("参数为空，商品添加失败");
			return null;
		}
		return goodsDao.addGoods(goods);
	}

	@Override
	public void deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除商品失败");
			return;
		}
		goodsDao.deleteById(id);
	}

	@Override
	public Goods getGoodsById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return goodsDao.getGoodsById(id);
	}

	@Override
	public void updateById(Goods goods) {
		if(goods==null||goods.getId()==null){
			logger.error("参数为空，修改商品信息失败");
			return;
		}
		goodsDao.updateById(goods);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，更新状态失败");
			return;
		}
		goodsDao.updateStateById(id, state);
	}

	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	@Override
	public List<Goods> getGoodsByMerchantId(String merchantId) {
		if(merchantId==null||"".equals(merchantId)){
			logger.error("商户编号为空");
			return null;
		}
		return goodsDao.getGoodsByMerchantId(merchantId);
	}

	@Override
	public Goods getGoodsByPager(Goods goods) {
		if(goods==null){
			logger.error("参数为空，查询失败");
			return null;
		}
		return goodsDao.getGoodsByPager(goods);
	}

	@Override
	public int getStoreById(Integer id) {
		if(id==null){
			return 0;
		}
		return goodsDao.getStoreById(id);
	}

}
