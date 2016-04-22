package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.BrandDao;
import com.froad.CB.po.Brand;
import com.froad.CB.service.BrandService;

/** 
 * @author FQ 
 * @date 2013-1-29 下午11:26:16
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.BrandService")
public class BrandServiceImpl implements BrandService {

	private static final Logger logger=Logger.getLogger(BrandServiceImpl.class);
	
	private BrandDao brandDao;
	

	@Override
	public Integer addBrand(Brand brand) {
		if(brand==null){
			logger.info("增加品牌参数不能为空！");
			return null;
		}
		return brandDao.addBrand(brand);
	}

	@Override
	public List<Brand> getAllBrand() {
		return brandDao.getAllBrand();
	}

	@Override
	public Brand getBrandById(Integer id) {
		if(id==null){
			logger.info("参数ID不能为空！");
			return null;
		}
		return brandDao.getBrandById(id);
	}

	@Override
	public List<Brand> getBrandList(Brand brand) {
		if(brand==null){
			logger.info("条件 Brand 查询品牌参数不能为空！");
			return null;
		}
		return brandDao.getBrandList(brand);
	}

	@Override
	public boolean updateBrand(Brand brand) {
		if(brand==null){
			logger.info("更新品牌参数不能为空！");
			return false;
		}
		return brandDao.updateBrand(brand);
	}
	
	public BrandDao getBrandDao() {
		return brandDao;
	}

	public void setBrandDao(BrandDao brandDao) {
		this.brandDao = brandDao;
	}

}
