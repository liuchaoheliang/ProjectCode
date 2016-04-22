package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.GoodsTypeDao;
import com.froad.CB.po.GoodsType;
import com.froad.CB.service.GoodsTypeService;

/** 
 * @author FQ 
 * @date 2013-1-29 下午11:30:10
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService {

	private static final Logger logger=Logger.getLogger(GoodsTypeServiceImpl.class);
	
	private GoodsTypeDao goodsTypeDao;
	

	@Override
	public Integer addGoodsType(GoodsType goodsType) {
		if(goodsType==null){
			logger.info("增加商品类型参数不能为空！");
		}
		return goodsTypeDao.addGoodsType(goodsType);
	}

	@Override
	public List<GoodsType> getGoodsTypeList(GoodsType goodsType) {
		if(goodsType==null){
			logger.info("条件 goodsType 查询商品类型参数不能为空！");
			return null;
		}
		return goodsTypeDao.getGoodsTypeList(goodsType);
	}

	@Override
	public GoodsType getGoodsTypeById(Integer id) {
		if(id==null){
			logger.info("查询商品类型ID参数不能为空！");
			return null;
		}
		return goodsTypeDao.getGoodsTypeById(id);
	}

	@Override
	public boolean updateGoodsType(GoodsType goodsType) {
		if(goodsType==null){
			logger.info("更新商品类型参数不能为空！");
			return false;
		}
		return goodsTypeDao.updateGoodsType(goodsType);
	}
	
	@Override
	public List<GoodsType> getAllGoodsType() {
		return goodsTypeDao.getAllGoodsType();
	}
	
	public GoodsTypeDao getGoodsTypeDao() {
		return goodsTypeDao;
	}

	public void setGoodsTypeDao(GoodsTypeDao goodsTypeDao) {
		this.goodsTypeDao = goodsTypeDao;
	}

}
