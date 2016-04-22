package com.froad.CB.dao;

import java.sql.SQLException;
import java.util.List;

import com.froad.CB.po.Trans;

public interface TransDao {

	
	/**
	  * 方法描述：添加交易
	  * @param: Trans
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:34:04 PM
	  */
	public Integer addTrans(Trans trans)throws SQLException;
	
	
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
	  * 方法描述：修改交易状态
	  * @param: id,state
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:34:42 PM
	  */
	public void updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：批量将交易更新为成功状态
	  * @param: idList 交易id列表
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 5:04:36 PM
	  */
	public void batchSuccessStateById(final List<Integer> idList);
	
	
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
	  * 方法描述：更新账单号
	  * @param: billNo 账单平台返回的账单号
	  * @param: transId
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 15, 2013 5:52:52 PM
	  */
	public void updateBillNo(String billNo,Integer transId);
	
	
	/**
	  * 方法描述：更新退款订单号
	  * @param: refundOrderId 退款订单号
	  * @param: transId 交易号
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Aug 20, 2013 10:05:36 AM
	  */
	public void updateRefundOrderIdById(String refundOrderId,Integer transId);
	
	
	/**
	  * 方法描述：更新积分账单号
	  * @param: pointBillNo 积分平台返回的积分账单号
	  * @param: transId
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 15, 2013 5:54:01 PM
	  */
	public void updatePointBillNo(String pointBillNo,Integer transId);
	
	
	/**
	  * 方法描述：更新第三方的彩票交易号
	  * @param: lotteryBillNo 第三方返回的彩票交易号
	  * @return: transId 分分通交易号
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 26, 2013 8:44:16 PM
	  */
	public void updateLotteryBillNo(String lotteryBillNo,Integer transId);
	
	
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
	  * 方法描述：查询退积分交易(用于积分退失败)
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 23, 2013 6:31:53 PM
	  */
	public List<Trans> getRefundPointsTrans();

	
	/**
	  * 方法描述：查询退积分交易(超出交易时间)
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 23, 2013 6:31:53 PM
	  */
	public List<Trans> getTimeoutPointsTrans();
	
	
	
	/**
	  * 方法描述：查询所有需要退积分的交易
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Aug 13, 2013 3:05:05 PM
	  */
	public List<Trans> getAllRefundPointsTrans();

	
	
	/**
	  * 方法描述：查询退款交易(用于退款失败)
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 23, 2013 6:31:53 PM
	  */
	public List<Trans> getRefundTrans();

	
	/**
	  * 方法描述：查询(代扣+返积分)交易(收银台收款时,代扣失败)
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 23, 2013 6:31:53 PM
	  */
	public List<Trans> getDeductTrans();

	
	/**
	  * 方法描述：查询返积分交易(收款或赠送积分时,返积分失败)
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 23, 2013 6:31:53 PM
	  */
	public List<Trans> getRebateTrans();
	
	
	/**
	  * 方法描述：关闭idList指定的交易
	  * @param: idList 交易编号列表
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Aug 12, 2013 4:19:33 PM
	  */
	public void closeTransByIdList(List<Integer> idList);
	
	
	/**
	  * 方法描述：按预售商品id查询正在处理中的预售类型的交易列表
	  * @param: goodsRackId 预售商品id
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 13, 2014 3:37:49 PM
	  */
	public List<Trans> queryPresellTransByGoodsRackId(Integer goodsRackId);
	
	
	/**
	  * 方法描述：查询所有状态为处理中的预售类型的交易，预售商品已经到达预售结束时间，并且已经成团
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 13, 2014 4:32:46 PM
	  */
	public List<Trans> queryPresellTrans();
	
	
	/**
	 * *******************************************************
	 * @函数名: getExcelDataToExpTrans  
	 * @功能描述: 获取用于导出excel的交易管理数据    积分返利
	 * @输入参数: @param trans
	 * @输入参数: @return <说明>
	 * @返回类型: List<Trans>
	 * @作者: 赵肖瑶 
	 * @日期: 2013-6-17 上午11:19:07
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public Trans getExcelDataToExpTrans(Trans trans);
	
	/**
	 * *******************************************************
	 * @函数名: getExcelDataToExpExchangeOrGroup  
	 * @功能描述: 获取用于导出excel的交易管理数据    团购或者兑换
	 * @输入参数: @param trans
	 * @输入参数: @return <说明>
	 * @返回类型: Trans
	 * @作者: 赵肖瑶 
	 * @日期: 2013-6-17 下午12:58:59
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public Trans getExcelDataToExpExchangeOrGroup(Trans trans);
	
	/**
	 * *******************************************************
	 * @函数名: closeTimeoutCashTrans  
	 * @功能描述: 关闭所有现金支付超时的交易
	 * @输入参数: @param trans
	 * @输入参数: @return <说明>
	 * @返回类型: Trans
	 * @作者: 刘超 
	 * @日期: 2013-6-17 下午12:58:59
	 * @修改: Lc 
	 * @日期: 
	 **********************************************************
	 */
	public Integer closeTimeoutCashTrans();
	
	/**
	 * *******************************************************
	 * @函数名: getNoNeedCloseTransId  
	 * @功能描述: 查询出自动任务时不需要关闭交易的交易ID
	 * @输入参数: @param trans
	 * @输入参数: @return <说明>
	 * @返回类型: Trans
	 * @作者: 刘超 
	 * @日期: 2013-6-17 下午12:58:59
	 * @修改: Lc 
	 * @日期: 
	 **********************************************************
	 */
	
	public List<Integer>  getNoNeedCloseTransId();
	
	
	
	/**
	  * 方法描述：关闭所有只下订单未做任何支付的交易
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-8-30 下午02:10:09
	  */
	public Integer closeWithoutPayTrans(List<String> transId);
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>财务专用 积分返利 分页查询</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-11-22 上午11:57:32 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Trans getTransByPagerFinance(Trans trans);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>积分返利或者团购（财务）</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-11-26 上午09:58:40 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Trans getGroupOrExchangeTransByPagerFinance(Trans trans);
	
	
	
	/**
	  * 方法描述：查询需要执行退款的预售商品交易
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-17 下午04:58:09
	  */
	public List<Trans> getPresellRefundTrans();
	
	
	/**
	  * 方法描述：查询需要执行退分的预售商品交易
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-17 下午04:59:02
	  */
	public List<Trans> getPresellRefundPontsTrans();
	
	
	
	/**
	  * 方法描述：根据商品Id查询预售商品交易
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-24 下午07:15:23
	  */
	public List<Trans> getPresellByRackId(String RackId);
	
	
	
	/**
	  * 方法描述：预售商品出现金和纯积分关闭的定时任务>24小时
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-4-15 下午02:23:25
	  */
	public Integer closePresellOutOfTimeCash();
	
	
	
	/**
	  * 方法描述：获取预售交易选择积分+现金支付的交易
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-4-15 下午02:29:08
	  */
	public List<Trans> getPresellOutOfTimePionts();
}
