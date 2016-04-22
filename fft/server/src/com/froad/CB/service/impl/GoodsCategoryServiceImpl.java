package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.GoodsCategoryDao;
import com.froad.CB.po.GoodsCategory;
import com.froad.CB.service.GoodsCategoryService;

/** 
 * @author FQ 
 * @date 2013-1-29 下午05:17:26
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsCategoryService")
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
	
	private static final Logger logger=Logger.getLogger(GoodsCategoryServiceImpl.class);
	
	private GoodsCategoryDao goodsCategoryDao;
	

	@Override
	public Integer addGoodsCategory(GoodsCategory goodsCategory) {
		if(goodsCategory==null){
			logger.info("增加商品分类参数不能为空!");
			return null;
		}
		
		Integer returnId=goodsCategoryDao.addGoodsCategory(goodsCategory);
		
		GoodsCategory gc=new GoodsCategory();
		gc.setId(returnId);
		
		if(goodsCategory.getParentId()!=null && !"".equals(goodsCategory.getParentId())){
			GoodsCategory goodsCategory_parent=goodsCategoryDao.getGoodsCategoryById(Integer.parseInt(goodsCategory.getParentId()));
			String parentPath = goodsCategory_parent.getPath();
			gc.setPath(parentPath + GoodsCategory.PATH_SEPARATOR + returnId);
		}
		else{
			gc.setPath(returnId+"");
		}
		goodsCategoryDao.updateGoodsCategory(gc);
		return returnId;
	}

	@Override
	public List<GoodsCategory> getAllGoodsCategory() {
		return goodsCategoryDao.getAllGoodsCategory();
	}

	@Override
	public List<GoodsCategory> getChildrenGoodsCategoryList(
			GoodsCategory goodsCategory) {
		if(goodsCategory==null){
			logger.info("条件 GoodsCategory 查询子集合参数不能为空!");
			return null;
		}
		return goodsCategoryDao.getChildrenGoodsCategoryList(goodsCategory);
	}

	@Override
	public GoodsCategory getGoodsCategoryById(Integer id) {
		if(id==null){
			logger.info("查询商品分类ID参数不能为空!");
			return null;
		}
		return goodsCategoryDao.getGoodsCategoryById(id);
	}

	@Override
	public List<GoodsCategory> getRootGoodsCategoryList() {
		return goodsCategoryDao.getRootGoodsCategoryList();
	}
	
	@Override
	public boolean updateGoodsCategory(GoodsCategory goodsCategory) {
		if(goodsCategory==null){
			logger.info("更新商品分类参数不能为空!");
			return false;
		}
		return goodsCategoryDao.updateGoodsCategory(goodsCategory);
	}

	public GoodsCategoryDao getGoodsCategoryDao() {
		return goodsCategoryDao;
	}

	public void setGoodsCategoryDao(GoodsCategoryDao goodsCategoryDao) {
		this.goodsCategoryDao = goodsCategoryDao;
	}

	
}
