package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.PresentPointRule;
@WebService
public interface PresentPointRuleService {
	/**
	  * 方法描述：添加积分兑换规则
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-25 上午10:49:31
	  */
	public Integer addPresentPointRule(PresentPointRule presentPointRule);
	
	
	/**
	  * 方法描述：根据id更新规则状态
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-25 上午10:49:45
	  */
	public boolean updatePresentPointRuleById(PresentPointRule presentPointRule);
	
	
	
	/**
	  * 方法描述：根据ID查找数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-25 上午11:17:01
	  */
	public PresentPointRule getById(Integer id);
	
	/**
	  * 方法描述：根据传入参数查询对象
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-26 下午02:01:21
	  */
	public List<PresentPointRule> getByConditions(PresentPointRule presentPointRule);
	
	/**
	  * 方法描述：自动赠送积分的分页接口
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-25 上午10:50:18
	  */
	public PresentPointRule getPresentPointRuleByPager(PresentPointRule presentPointRule);

}
