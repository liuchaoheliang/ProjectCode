package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.common.BusinessException;
import com.froad.CB.common.constant.ExceptionTransType;
import com.froad.CB.po.Trans;
import com.froad.util.Result;


	
@WebService
public interface TransService {
	
	
	/**
	  * 方法描述：添加交易(只添加交易相关记录，不做支付)
	  * @param: Trans
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:34:04 PM
	  */
	public Trans addTrans(Trans trans)throws AppException;
	
	
	/**
	  * 方法描述：添加并下推交易(积分兑换、团购、收款)
	  * @param: Trans
	  * @return: Trans
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 7, 2013 8:53:39 PM
	  */
	public Trans doTrans(Trans trans)throws BusinessException;
	
	
	/**
	  * 方法描述：支付交易(积分兑换、团购、收款)
	  * @param: transId
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 26, 2013 10:23:18 PM
	  */
	public Result doPay(Integer transId);
	
	
	/**
	  * 方法描述：代扣(收银台赠送积分前的操作)
	  * @param: transId
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 15, 2013 11:11:37 AM
	  */
	public boolean doDeduct(Integer transId)throws BusinessException;
	
	
	/**
	  * 方法描述：代收(收银台收款)
	  * @param: transId 交易号
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 21, 2013 11:25:31 AM
	  */
	public boolean doCollect(Integer transId)throws BusinessException;
	
	
	/**
	  * 方法描述：返利积分(只返积分，不做代扣)
	  * @param: transId
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 21, 2013 3:51:12 PM
	  */
	public boolean rebatePoints(Integer transId)throws BusinessException;
	
	
	/**
	  * 方法描述：积分提现(需要异步通知结果，最新接口)
	  * @param: transId 交易编号
	  * @return: boolean 成功|失败
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 4, 2014 10:02:53 AM
	  */
	public boolean applyWithdrawPoints(Integer transId)throws BusinessException;
	
	
	/**
	  * 方法描述：积分提现(提现申请，扣提现用户积分)
	  * @param: transId
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 13, 2013 10:18:07 PM
	  */
	public boolean deductPoints(Integer transId) throws BusinessException;
	
	
	/**
	  * 方法描述：退积分(仅用于交易失效或交易失败积分也退失败了的情况)
	  * @param: transId
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 21, 2013 4:26:54 PM
	  */
	public boolean refundPoints(Integer transId)throws BusinessException;
	
	
	/**
	  * 方法描述：退款
	  * @param: transId 交易号
	  * @param：reason 退款原因
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 21, 2013 4:36:30 PM
	  */
	public boolean refund(Integer transId,String reason)throws BusinessException;
	
	
	/**
	  * 方法描述：积分提现(给提现用户转账)
	  * @param: transId
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 7, 2013 11:25:04 PM
	  */
	public boolean withdraw(Integer transId)throws BusinessException;
	
	
	/**
	  * 方法描述：积分提现(拒绝提现)
	  * @param: transId 交易号
	  * @param: reason 拒绝提现的原因
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 23, 2013 3:27:42 PM
	  */
	public boolean refuse(Integer transId,String reason)throws BusinessException;
	
	
	/**
	  * 方法描述：查询交易
	  * @param: id
	  * @return: Trans
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:34:24 PM
	  */
	public Trans getTransById(Integer id);
	
	
	/**
	  * 方法描述：查询交易
	  * @param: refundOrderId 退款订单号
	  * @return: Trans
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 17, 2014 9:16:08 PM
	  */
	public Trans getTransByRefundOrderId(String refundOrderId);
	
	
	/**
	  * 方法描述：按第三方彩票交易号查询交易
	  * @param: lotteryBillNo
	  * @return: Trans
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 27, 2013 1:30:21 PM
	  */
	public Trans getTransByLotteryBillNo(String lotteryBillNo);
	
	
	/**
	  * 方法描述：查询指定买家的交易
	  * @param: buyerId
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 20, 2013 2:38:36 PM
	  */
	public List<Trans> getTransByBuyerId(String buyerId);
	
	
	/**
	  * 方法描述：查询指定卖家的交易
	  * @param: sellerId
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 20, 2013 2:39:54 PM
	  */
	public List<Trans> getTransBySellerId(String sellerId);
	
	
	/**
	  * 方法描述：按商户编号查询交易列表
	  * @param: merchantId
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 6, 2013 10:31:55 AM
	  */
	public List<Trans> getTransByMerchantId(String merchantId);
	
	
	/**
	  * 方法描述：分页多条件查询交易
	  * @param: Trans
	  * @return: Trans
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 20, 2013 4:12:07 PM
	  */
	public Trans getTransByPager(Trans trans);
	
	/**
	  * 方法描述：分页多条件查询团购、兑换交易
	  * @param: Trans
	  * @return: Trans
	  * @version: 1.0
	  * @author: lqp
	  * @time: Feb 20, 2013 4:12:07 PM
	  */
	public Trans getGroupOrExchangeTransByPager(Trans trans);
	/**
	  * 方法描述：查询交易状态
	  * @param: transId
	  * @return: 交易状态
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 18, 2013 11:32:45 AM
	  */
	public String getTransStateById(Integer transId);
	
	
	/**
	  * 方法描述：修改交易状态
	  * @param: id,state
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:34:42 PM
	  */
	public void updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：修改交易信息
	  * @param: Trans
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:34:58 PM
	  */
	public void updateById(Trans trans);
	
	
	/**
	  * 方法描述：删除交易
	  * @param: id
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:35:14 PM
	  */
	public void deleteById(Integer id);
	
	
	/**
	  * 方法描述：查询异常交易(用于数据冲正)
	  * @param: ExceptionTransType
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 23, 2013 6:48:02 PM
	  */
	public List<Trans> getExceptionTrans(ExceptionTransType exceptionTransType);
	
	
	/**
	  * 方法描述：精品预售自然成团(由定时任务执行)
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 10:35:18 AM
	  */
	public void cluster();
	
	
	/**
	  * 方法描述：精品预售手动成团(由管理平台调用,做成团操作)
	  * @param: presellGoodsId 预售商品id
	  * @return: Result 成团操作的结果(success|fail)
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 1:46:50 PM
	  */
	public Result clusterByManager(Integer presellGoodsId);
	
	
	/**
	  * 方法描述：精品预售不成团(由管理平台调用,做成团失败操作)
	  * @param: presellGoodsId 预售商品id
	  * @return: Result 成团操作的结果(success|fail)
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 1:46:50 PM
	  */
	public Result clusterFailedByManager(Integer presellGoodsId);
	
	
	/**
	 * *******************************************************
	 * @函数名: getDataToRepExcel  
	 * @功能描述: 获取需要到处的excel数据
	 * @输入参数: @param trans
	 * @输入参数: @param transType 交易类型
	 * @输入参数: @return <说明>
	 * @返回类型: Trans
	 * @作者: 赵肖瑶 
	 * @日期: 2013-6-17 上午11:26:50
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public Trans getDataToRepExcel(Trans trans);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>财务查询积分返利</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-11-22 下午05:02:27 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Trans getTransByPagerFinance(Trans trans);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>财务查询积分兑换或者团购</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-11-26 上午10:03:55 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Trans getGroupOrExchangeTransByPagerFinance(Trans trans);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>mgmt平台新加交易查询接口</b> --* </p>
	 *<p> 作者: 刘超 </p>
	 *<p> 时间: 2013-12-2 上午10:03:55 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Trans getTransByPagerMgmt(Trans trans);
	
	
	
	/**
	  * 方法描述：根据商品Id查询该商品交易信息
	  * @param: payState  支付类型
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-15 下午08:16:29
	  */
	public List<Trans> getclusterFailedTrans(Integer rackId,List<String> payState);
	
	
	
	/**
	  * 方法描述：执行预售商品不成团之后，手动执行退分退款
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-17 下午07:04:13
	  */
	public void doPresellTask();
	
	
	
	/**
	  * 方法描述：根据预售商品的ID查询所有支付成功的预售商品的交易
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-24 下午06:02:12
	  */
	public List<Trans> findPaidPresellTrans(Integer rackId);
	
	
	
	/**
	  * 方法描述：关闭预售交易超时订单（纯现金、纯积分、积分+现金）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-4-15 下午03:10:11
	  */
	public void closePresellTransOutofTime();
}
