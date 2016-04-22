package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.entity.Pay;

public interface PayMapper {

	
	/**
	  * 方法描述：数据插入
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月1日 上午11:14:05
	  */
	public Long savePay(Pay pay);
	
	/**
	  * 方法描述：根据Id更新
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月1日 上午11:14:07
	  */
	public Boolean updatePayById(Pay pay);
	
	/**
	  * 方法描述：根据ID查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月1日 上午11:14:09
	  */
	public Pay selectPayById(Long id);
	
	/**
	  * 方法描述：根据Sn查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月1日 上午11:14:11
	  */
	public Pay selectPayBySn(String sn);
	
	
	/**
	  * 方法描述：查询支付记录
	  * @param: refundOrderId
	  * @return: 支付记录
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月18日 下午5:38:42
	  */
	public Pay selectPayByRefundOrderId(String refundOrderId);
	
	
	/**
	  * 方法描述：根据transId查找数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月1日 上午11:15:42
	  */
	public  List<Pay> selectPayByTransId(Long transId);
	
	
	/**
	 * 批量保存
	 * @param payList
	 */
	public void saveBatchPay(List<Pay> payList);
}
