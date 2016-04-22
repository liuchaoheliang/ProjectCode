package com.froad.CB.service.activity;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.activity.SpringFestivalCoupon;
@WebService
public interface SpringFestivalCouponService {
	
	
	/**
	  * 方法描述：根据ID查询SpringFestivalCoupon
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-19 上午10:54:43
	  */
	public SpringFestivalCoupon getSpringFestivalCouponById(String ID);
	
	
	/**
	  * 方法描述：添加一条SpringFestivalCoupon记录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-19 上午10:55:08
	  */
	public boolean addSpringFestivalCoupon(SpringFestivalCoupon springFestivalCoupon);
	
	
	/**
	  * 方法描述：根据ID更新SpringFestivalCoupon
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-19 上午10:56:07
	  */
	public boolean updateSpringFestivalCouponById(SpringFestivalCoupon springFestivalCoupon);
	
	
	
	/**
	  * 方法描述：根据条件查询SpringFestivalCoupon集合
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-19 上午11:00:25
	  */
	public  List<SpringFestivalCoupon> getSpringFestivalCouponByCndition(SpringFestivalCoupon springFestivalCoupon);
	
	/**
	  * 方法描述：分页查询SpringFestivalCoupon
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-19 上午10:57:18
	  */
	public SpringFestivalCoupon getSpringFestivalCouponByPager(SpringFestivalCoupon springFestivalCoupon);

}
