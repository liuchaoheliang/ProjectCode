package com.froad.fft.service;

import com.froad.fft.bean.Result;
import com.froad.fft.persistent.entity.Pay;
import com.froad.fft.persistent.entity.Trans;
import com.froad.fft.thirdparty.dto.response.openapi.NoticeFroadApi;


	/**
	 * 类描述：交易的核心服务
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年3月28日 上午11:34:22 
	 */
public interface TransCoreService {

	
	/**
	  * 方法描述：创建交易
	  * @param: Trans
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月28日 上午11:36:25
	  */
	public Result createTrans(Trans trans);
	
	
	/**
	  * 方法描述：支付
	  * @param: sn 交易序列号
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月28日 上午11:37:25
	  */
	public Result doPay(String sn);
	
	
	/**
	  * 方法描述：处理支付通知
	  * @param: NoticeFroadApi
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月6日 下午1:38:43
	  */
	public void doNotice(NoticeFroadApi notice);
	
	
	/**
	  * 方法描述：处理退款通知
	  * @param: NoticeFroadApi
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月9日 上午11:54:53
	  */
	public void doRefundNotice(NoticeFroadApi notice);
	
	
	/**
	  * 方法描述：退积分
	  * @param: Pay 积分的pay记录
	  * @param: clientId 客户端编号
	  * @return: boolean 成功|失败
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月8日 下午2:38:31
	  */
	public boolean refundPoints(Pay pointsPay,Long clientId); 
	
	
	/**
	  * 方法描述：退款
	  * @param: Pay 资金的pay记录
	  * @param: reason 退款原因
	  * @param: clientId 客户端编号
	  * @return: boolean 成功|失败
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月8日 下午2:42:23
	  */
	public boolean refund(Pay cashPay,String reason,Long clientId);
	
	
	/**
	  * 方法描述：交易退款退分
	  * @param: Trans 交易对象
	  * @param: reason 退款原因
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月14日 上午9:25:51
	  */
	public Result doRefund(Trans trans,String reason);
	
	
	/**
	  * 方法描述：交易退款退分
	  * @param: sn 交易序号
	  * @param: reason 退款原因
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月14日 上午9:25:51
	  */
	public Result doRefund(String sn,String reason);
}
