package com.froad.CB.service.activity.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.activity.SpringFestivalCouponDao;
import com.froad.CB.po.activity.SpringFestivalCoupon;
import com.froad.CB.service.activity.SpringFestivalCouponService;
@WebService(endpointInterface="com.froad.CB.service.activity.SpringFestivalCouponService")
public class SpringFestivalCouponServiceImpl implements
		SpringFestivalCouponService {
	
	private SpringFestivalCouponDao springFestivalCouponDao;
	private static final Logger logger=Logger.getLogger(SpringFestivalCouponServiceImpl.class);
	@Override
	public SpringFestivalCoupon getSpringFestivalCouponById(String ID) {
		if("".equals(ID) || ID==null){
			logger.info("传入查询的springFestivalCoupon：ID为空");
			return new SpringFestivalCoupon();
		}else{
			return springFestivalCouponDao.getById(ID);
		}
		
	}

	@Override
	public boolean addSpringFestivalCoupon(
			SpringFestivalCoupon springFestivalCoupon) {
		if(springFestivalCoupon==null){
			logger.info("传入springFestivalCoupon参数为空");
			return false;
		}else{
			return springFestivalCouponDao.add(springFestivalCoupon);			
		}
		
	}

	@Override
	public boolean updateSpringFestivalCouponById(
			SpringFestivalCoupon springFestivalCoupon) {
		if(springFestivalCoupon.getId()==null || "".equals(springFestivalCoupon.getId())){
			return false;
		}else{
			return springFestivalCouponDao.updateById(springFestivalCoupon);
		}
	}

	@Override
	public List<SpringFestivalCoupon> getSpringFestivalCouponByCndition(
			SpringFestivalCoupon springFestivalCoupon) {
		if(springFestivalCoupon==null){
			logger.info("传入springFestivalCoupon参数为空");
			return null;
		}
		return springFestivalCouponDao.getSpringFestivalCouponByCndition(springFestivalCoupon);
	}

	@Override
	public SpringFestivalCoupon getSpringFestivalCouponByPager(
			SpringFestivalCoupon springFestivalCoupon) {
		if(springFestivalCoupon==null){
			logger.info("传入springFestivalCoupon参数为空");
			return new SpringFestivalCoupon();			
		}
		return springFestivalCouponDao.getSpringFestivalCouponByPager(springFestivalCoupon);
	}

	public SpringFestivalCouponDao getSpringFestivalCouponDao() {
		return springFestivalCouponDao;
	}

	public void setSpringFestivalCouponDao(
			SpringFestivalCouponDao springFestivalCouponDao) {
		this.springFestivalCouponDao = springFestivalCouponDao;
	}

	
	
}
