
	 /**
  * 文件名：WithdrawPointsRuleMapper.java
  * 版本信息：Version 1.0
  * 日期：2014年3月26日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api;

import com.froad.fft.persistent.entity.PresentPointsRule;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.WithdrawPointsRule;


	/**
 * 类描述：：积分提现规则
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月26日 下午8:26:58 
 */
public interface WithdrawPointsRuleMapper {
	/**
	  * 方法描述：保存数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月26日 下午8:08:54
	  */
	public Long saveWithdrawPointsRule(WithdrawPointsRule withdrawPointsRule);
	
	
	/**
	  * 方法描述：根据Id更新数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月26日 下午8:08:58
	  */
	public Boolean updateWithdrawPointsRuleById(WithdrawPointsRule withdrawPointsRule);
	
	
	/**
	  * 方法描述：根据Id查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月26日 下午8:09:00
	  */
	public WithdrawPointsRule selectWithdrawPointsRuleById(Long id);
	
	
	/**
	  * 方法描述：分页查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月26日 下午8:09:02
	  */
	public WithdrawPointsRule findWithdrawPointsRuleByPage(WithdrawPointsRule withdrawPointsRule);

}
