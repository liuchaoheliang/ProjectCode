
	 /**
  * 文件名：RefundsMapper.java
  * 版本信息：Version 1.0
  * 日期：2014年3月27日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Refunds;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 下午2:55:24 
 */
public interface RefundsMapper {
	
	/**
	  * 方法描述：添加数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月27日 下午2:58:32
	  */
	public Long saveRefunds( Refunds refunds );
	
	
	/**
	  * 方法描述：根据Id更新数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月27日 下午2:58:30
	  */
	public Boolean updateRefundsById( Refunds refunds);
	
	
	/**
	  * 方法描述：根据Id查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月27日 下午2:58:28
	  */
	public Refunds selectRefundsById(Long id);
	
	
	/**
	  * 方法描述：分页查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月27日 下午3:02:06
	  */
	public List<Refunds> selectRefundsByPage(Page page);
	
	
	
	/**
	  * 方法描述：查询分页总条数
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月27日 下午3:13:51
	  */
	public Integer selectRefundsByPageCount(Page page);
}
