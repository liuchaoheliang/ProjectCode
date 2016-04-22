package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.entity.TransDetails;

public interface TransDetailsMapper {

	
	/**
	  * 方法描述：插入数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月31日 下午5:55:53
	  */
	public Long saveTransDetails(TransDetails transDetail);
	
	/**
	  * 方法描述：根据Id修改
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月31日 下午5:55:55
	  */
	public Boolean updateTransDetailsById(TransDetails transDetails);
	
	/**
	  * 方法描述：根据Id查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月31日 下午5:55:57
	  */
	public TransDetails selectTransDetailsById(Long id);
	
	/**
	  * 方法描述：批量插入
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月31日 下午5:55:50
	  */
	public void saveBatchTransDetails(List<TransDetails> list);

    /**
   	  * 方法描述：根据交易Id查询
   	  * @param:
   	  * @return:
   	  * @version: 1.0
   	  * @author: 侯国权
   	  * @time: 2014年4月3日 下午15:55:57
   	  */
   	public List<TransDetails> selectTransDetailsByTransId(Long transId);
}
