package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.GoodsAttributeDao;
import com.froad.CB.po.GoodsAttribute;
import com.froad.CB.service.GoodsAttributeService;

/** 
 * @author FQ 
 * @date 2013-1-29 下午11:29:14
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsAttributeService")
public class GoodsAttributeServiceImpl implements GoodsAttributeService {

	private static final Logger logger=Logger.getLogger(GoodsAttributeServiceImpl.class);
	
	private GoodsAttributeDao goodsAttributeDao;
	

	@Override
	public Integer addGoodsAttribute(GoodsAttribute goodsAttribute) {
		if(goodsAttribute==null){
			logger.info("增加商品属性参数不能为空");
			return null;
		}
		return goodsAttributeDao.addGoodsAttribute(goodsAttribute);
	}

	@Override
	public GoodsAttribute getGoodsAttributeById(Integer id) {
		if(id==null){
			logger.info("查询商品属性ID不能为空");
			return null;
		}
		return goodsAttributeDao.getGoodsAttributeById(id);
	}

	@Override
	public List<GoodsAttribute> getGoodsAttributeList(
			GoodsAttribute goodsAttribute) {
		if(goodsAttribute==null){
			logger.info("条件 GoodsAttribute 查询商品参数不能为空");
			return null;
		}
		return goodsAttributeDao.getGoodsAttributeList(goodsAttribute);
	}

	@Override
	public boolean updateGoodsAttribute(GoodsAttribute goodsAttribute) {
		if(goodsAttribute==null){
			logger.info("查询商品属性ID不能为空");
			return false;
		}
		return goodsAttributeDao.updateGoodsAttribute(goodsAttribute);
	}

	public GoodsAttributeDao getGoodsAttributeDao() {
		return goodsAttributeDao;
	}

	public void setGoodsAttributeDao(GoodsAttributeDao goodsAttributeDao) {
		this.goodsAttributeDao = goodsAttributeDao;
	}
}
