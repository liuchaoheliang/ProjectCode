
	 /**
  * 文件名：PresentPointsRuleMapper.java
  * 版本信息：Version 1.0
  * 日期：2014年3月26日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api;

import com.froad.fft.persistent.entity.MerchantAccount;
import com.froad.fft.persistent.entity.PresentPointsRule;
import com.froad.fft.persistent.entity.Product;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月26日 下午8:11:20 
 */
public interface PresentPointsRuleMapper {
	/**
	  * 方法描述：保存数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月26日 下午8:08:54
	  */
	public Long savePresentPointsRule(PresentPointsRule presentPointsRule );
	
	
	/**
	  * 方法描述：根据Id更新数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月26日 下午8:08:58
	  */
	public Boolean updatePresentPointsRuleById(PresentPointsRule presentPointsRule);
	
	
	/**
	  * 方法描述：根据Id查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月26日 下午8:09:00
	  */
	public PresentPointsRule selectPresentPointsRuleById(Long id);
	
	
	/**
	  * 方法描述：分页查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月26日 下午8:09:02
	  */
	public PresentPointsRule findPresentPointsRuleByPage(PresentPointsRule PresentPointsRule);

}
