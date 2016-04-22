package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.Pay;

@WebService
public interface PayService {
	

	/**
	  * 方法描述：添加支付信息
	  * @param: Pay
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:26:25 PM
	  */
	public Integer addPay(Pay pay);
	
	
	/**
	  * 方法描述：查询支付信息
	  * @param: id
	  * @return: Pay
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:26:39 PM
	  */
	public Pay getPayById(Integer id);
	
	
	/**
	  * 方法描述：查询指定交易的所有pay记录
	  * @param: transId 交易号
	  * @return: List<Pay>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 8, 2013 11:44:40 AM
	  */
	public List<Pay> getPayByTransId(String transId);
	
	
	/**
	  * 方法描述：更新支付状态
	  * @param: id,state
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:26:52 PM
	  */
	public void updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：修改支付信息
	  * @param: Pay
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:27:09 PM
	  */
	public void updateById(Pay pay);
	
	
	/**
	  * 方法描述：删除支付信息
	  * @param: id
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:27:23 PM
	  */
	public void deleteById(Integer id);
	
	
	/**
	  * 方法描述：以transId,type作条件，修改state,resultCode
	  * @param: Pay(transId,type,state,[resultCode])
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 7, 2013 3:53:58 PM
	  */
	public void updatePay(Pay pay);

    /**
     * 支付宝支付结果检查
     * @param transId 交易单号
     * @return 检查结果
     */
    public String aliPayResultCheck(String transId);
}
