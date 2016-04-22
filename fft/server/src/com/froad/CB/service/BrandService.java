package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.Brand;

/** 
 * @author FQ 
 * @date 2013-1-29 下午11:21:29
 * @version 1.0
 * 
 */
@WebService
public interface BrandService {
	
	/**
	 * 增加 品牌
	 * @param brand
	 * @return
	 */
	public Integer addBrand(Brand brand);
	
	/**
	 * 根据ID 查找品牌
	 * @param id
	 * @return
	 */
	public Brand getBrandById(Integer id);
	
	/**
	 * 根据 Brand条件 更新品牌
	 * @param brand
	 * @return
	 */
	public boolean updateBrand(Brand brand); 
	
	/**
	 * 根据 Brand条件 查找品牌
	 * @param brand
	 * @return
	 */
	public List<Brand> getBrandList(Brand brand);
	
	/**
	 * 查询所有品牌
	 * @return
	 */
	public List<Brand> getAllBrand();
}
