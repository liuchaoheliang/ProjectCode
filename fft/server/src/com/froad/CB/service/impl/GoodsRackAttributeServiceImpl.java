package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.GoodsRackAttributeDao;
import com.froad.CB.po.GoodsRackAttribute;
import com.froad.CB.service.GoodsRackAttributeService;

@WebService(endpointInterface="com.froad.CB.service.GoodsRackAttributeService")
public class GoodsRackAttributeServiceImpl implements GoodsRackAttributeService{

	private Logger logger=Logger.getLogger(GoodsRackAttributeServiceImpl.class);
	
	private GoodsRackAttributeDao goodsRackAttributeDao;
	
	@Override
	public Integer addGoodsRackAttribute(GoodsRackAttribute goodsRackAttribute) {
		if(goodsRackAttribute==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return goodsRackAttributeDao.addGoodsRackAttribute(goodsRackAttribute);
	}

	@Override
	public boolean deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败");
			return false;
		}
		return goodsRackAttributeDao.deleteById(id);
	}

	@Override
	public GoodsRackAttribute getGoodsRackAttributeById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return goodsRackAttributeDao.getGoodsRackAttributeById(id);
	}

	@Override
	public boolean updateById(GoodsRackAttribute goodsRackAttribute) {
		if(goodsRackAttribute==null||goodsRackAttribute.getId()==null){
			logger.error("参数为空，更新失败");
			return false;
		}
		return goodsRackAttributeDao.updateById(goodsRackAttribute);
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，状态更新失败");
			return false;
		}
		return goodsRackAttributeDao.updateStateById(id, state);
	}

	public void setGoodsRackAttributeDao(GoodsRackAttributeDao goodsRackAttributeDao) {
		this.goodsRackAttributeDao = goodsRackAttributeDao;
	}

	@Override
	public List<GoodsRackAttribute> getGoodsRackAttributeList() {
		return goodsRackAttributeDao.getGoodsRackAttributeList();
	}

}
