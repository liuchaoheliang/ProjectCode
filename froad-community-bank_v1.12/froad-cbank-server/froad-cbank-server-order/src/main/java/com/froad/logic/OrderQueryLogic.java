package com.froad.logic;

import java.util.List;
import java.util.Map;

import com.froad.common.beans.ResultBean;
import com.froad.exceptions.FroadBusinessException;
import com.froad.po.OrderQueryByBankCondition;
import com.froad.po.OrderQueryByBossCondition;
import com.froad.po.OrderQueryByMerchantPhoneCondition;
import com.froad.po.OrderQueryMerchantManageCondition;
import com.froad.po.QueryBoutiqueOrderByBankDto;
import com.froad.po.order.OrderExportData;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.order.QueryBoutiqueOrderByBankManageVo;
import com.froad.thrift.vo.order.QueryBoutiqueOrderByBankManageVoReq;
import com.froad.thrift.vo.order.QueryOrderByBankManageVo;
import com.froad.thrift.vo.order.QueryOrderByBankManageVoReq;
import com.froad.thrift.vo.order.QueryOrderByMerchantManageVo;
import com.froad.thrift.vo.order.QueryOrderByMerchantManageVoReq;

/**
 * 订单查询相关交易
 * 
 * @author Arron
 * 
 */
public interface OrderQueryLogic {

    /**
     * 查询面对面订单列表
     * 
     * @param order
     *            面对面订单查询条件
     * @param page
     *            分页数据
     * @return List<Order> -- 订单列表
     * @exception FroadBusinessException
     *                自定义异常
     * 
     *                <pre>
     * 
     * @Description: TODO  查询面的吗订单 
     * @version V1.0 修改人：Arron 日期：2015年4月8日 上午11:50:26
     * 
     * </pre>
     */
    public abstract ResultBean queryOrderByFacetface(OrderQueryByMerchantPhoneCondition condition, PageVo page) throws FroadBusinessException;

    /**
     * 查询团购子订单
     * 
     * @param condition
     *            子订单查询条件
     * @param page
     *            分页数据
     * @return <pre>
     * 
     * @Description: TODO 团购订单查询 
     * @version V1.0 修改人：Arron 日期：2015年4月8日 下午1:50:49
     * 
     * </pre>
     */
    public abstract ResultBean queryOrderByGroup(OrderQueryByMerchantPhoneCondition condition, PageVo page) throws FroadBusinessException;

    /**
     * 获取面对面订单详细
     * 
     * @param orderId
     * @return {@link ResultBean} 实体对象
     * @throws FroadBusinessException
     *             自定义异常
     * 
     *             <pre>
     * 
     * @Description: 面对面订单详细
     * @version V1.0 修改人：Arron 日期：2015年4月9日 上午9:46:38
     * 
     * </pre>
     */
    public abstract ResultBean getFacetfaceOrderDetailByOrderId(String clientId, String orderId) throws FroadBusinessException;

    /**
     * 根据子订单编号和订单类型查询订单详细
     * 
     * @param subOrderId
     *            子订单ID
     * @return {@link ResultBean}
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description: 查询子订单详细 
     * @version V1.0 修改人：Arron 日期：2015年4月9日 上午9:56:41
     * 
     * </pre>
     */
    public abstract ResultBean getSubOrderDetailByOrderIdAndType(String clientId, String subOrderId) throws FroadBusinessException;

    /**
     * 银行PC预售订单，团购订单查询
     * 
     * @param condition
     *            查询条件
     * @param pageVo
     *            分页数据
     * @return {@link ResultBean}
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description:  银行端订单查询 
     * @version V1.0 修改人：Arron 日期：2015年4月9日 下午4:28:42
     * 
     * </pre>
     */
    public abstract ResultBean querySubOrderByBank(OrderQueryByBankCondition condition, PageVo pageVo) throws FroadBusinessException;
    
    /**
     * 银行PC预售订单，精品商城订单查询
     * 
     * @param condition
     *            查询条件
     * @param pageVo
     *            分页数据
     * @return {@link ResultBean}
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description:  银行端订单查询 
     * @version V1.0 修改人：Arron 日期：2015年4月9日 下午4:28:42
     * 
     * </pre>
     */
    public abstract ResultBean queryBoutiqueSubOrderByBank(QueryBoutiqueOrderByBankDto reqDto, PageVo pageVo) throws FroadBusinessException;
    
    
    /**
     * 银行PC，团购/预售/积分兑换/名优特惠-订单查询
     * 
     * @param condition
     *            查询条件
     * @param pageVo
     *            分页数据
     * @return {@link ResultBean}
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description:  银行端订单查询 
     * @version V1.0 修改人：张开 日期：2015年9月1日 下午4:28:42
     * 
     * </pre>
     */
    public ResultBean queryBoutiqueSubOrderByBankForExport(QueryBoutiqueOrderByBankDto reqDto, PageVo pageVo) throws FroadBusinessException;

    
    
    /**
     * 银行PC，团购/预售/积分兑换/名优特惠-订单查询
     * 
     * @param condition
     *            查询条件
     * @param pageVo
     *            分页数据
     * @return {@link ResultBean}
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description:  银行端订单查询 
     * @version V1.0 修改人：张开 日期：2015年9月1日 下午4:28:42
     * 
     * </pre>
     */
    public ResultBean querySubOrderByBankForExport(OrderQueryByBankCondition condition, PageVo pageVo) throws FroadBusinessException;

    /**
     * 银行PC面对面订单查询
     * 
     * @param condition
     *            查询条件
     * @param pageVo
     *            分页数据
     * @return {@link ResultBean}
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description: 银行端-面对面订单查询 
     * @version V1.0 修改人：Arron 日期：2015年4月9日 下午4:29:28
     * 
     * </pre>
     */
    public abstract ResultBean queryOrderOfFacetfacByBank(OrderQueryByBankCondition condition, PageVo pageVo) throws FroadBusinessException;
    
    /**
     * 银行PC面对面订单查询
     * 
     * @param condition
     *            查询条件
     * @param pageVo
     *            分页数据
     * @return {@link ResultBean}
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description: 银行端-面对面订单查询 
     * @version V1.0 修改人：张开 日期：2015年9月1日 下午4:29:28
     * 
     * </pre>
     */
    public ResultBean queryQrOrderByBankForExport(OrderQueryByBankCondition condition, PageVo pageVo) throws FroadBusinessException;

    /**
     * 根据订单号查找订单详细内容
     * 
     * @param orderId
     *            订单号
     * @return {@link ResultBean}
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description: 面对面订单详细查询 
     * @version V1.0 修改人：Arron 日期：2015年4月10日 下午2:58:14
     * 
     * </pre>
     */
    public abstract ResultBean getFacetfaceOrderDetailOfBankByOrderId(String clientId, String orderId) throws FroadBusinessException;

    /**
     * 银行PC订单详细查询
     * 
     * @param subOrderId
     *            订单编号
     * @return {@link ResultBean}
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description: 订单详细 
     * @version V1.0 修改人：Arron 日期：2015年4月10日 下午2:59:13
     * 
     * </pre>
     */
    public abstract ResultBean getSubOrderOfBankByOrderIdAndType(String clientId, String subOrderId, String type,String deOption) throws FroadBusinessException;

    /**
     * 商户管理查询面对面订单
     * 
     * @param condition
     *            筛选条件
     * @param pageVo
     *            分页对象
     * @return {@link ResultBean}
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description: 商户面对面订单查询 
     * @version V1.0 修改人：Arron 日期：2015年4月11日 上午10:41:30
     * 
     * </pre>
     */
    public abstract ResultBean queryFacetfaceOrderByMerchantManage(OrderQueryMerchantManageCondition condition, PageVo pageVo) throws FroadBusinessException;
    
    
    /**
     * 商户管理平台订单导出-面对面订单
     * @param condition
     * @param pageVo
     * @return
     * @throws FroadBusinessException
     */
    public ResultBean queryQrOrderByMerchantForExport(OrderQueryMerchantManageCondition condition, PageVo pageVo) throws FroadBusinessException;

    
    /**
     * 商户管理平台订单导出-团购、名优特惠
     * @param condition
     * @param pageVo
     * @return
     * @throws FroadBusinessException
     */
    public ResultBean querySubOrderByMerchantForExport(OrderQueryMerchantManageCondition condition, PageVo pageVo) throws FroadBusinessException;
    
    /**
     * 
     * 商户管理查询
     * 
     * @param condition
     *            查询条件
     * @param pageVo
     *            分页对象
     * @return {@link ResultBean}
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description:  商户管理查询名优特惠，团购订单
     * @version V1.0 修改人：Arron 日期：2015年4月11日 上午10:42:19
     * 
     * </pre>
     */
    public abstract ResultBean querySubOrderByMerchantManage(OrderQueryMerchantManageCondition condition, PageVo pageVo) throws FroadBusinessException;
    
    
    /**
     * 查询面对面订单详细内容
     * @param orderId  订单编号
     * @return ResultBean.GetOrderDetailByMerchantManageVoRes
     * @throws FroadBusinessException
     *<pre>
     *
     * @Description: 查询面对面订单详细内容 
     * @version V1.0 修改人：Arron 日期：2015年4月17日 上午11:14:14 
     *
     *</pre>
     */
    public abstract ResultBean getOrderDetailByMerchantManage(String clientId, String orderId) throws FroadBusinessException;
    
    /**
     * 查询面对面订单详细内容-优化
     * @param orderId  订单编号
     * @return ResultBean.GetOrderDetailByMerchantManageVoRes
     * @throws FroadBusinessException
     *<pre>
     *
     * @Description: 查询面对面订单详细内容 
     * @version V1.0 修改人：Arron 日期：2015年4月17日 上午11:14:14 
     *
     *</pre>
     */
    public abstract ResultBean getOrderDetailByMerchantManageNew(String clientId, String orderId) throws FroadBusinessException;
    
    /**
     * 查询团购，名优特惠等订单的详细内容
     * @param subOrderId 子订单编号
     * @param type 订单类型
     * @return ResultBean.GetOrderDetailByMerchantManageVoRes
     * @throws FroadBusinessException
     *<pre>
     *
     * @Description: 查询订单详细内容 
     * @version V1.0 修改人：Arron 日期：2015年4月17日 上午11:14:25 
     *
     *</pre>
     */
    public abstract ResultBean getSubOrderDetailByMerchantManage(String clientId, String subOrderId, String type) throws FroadBusinessException;

    /**
     * 查询团购，名优特惠等订单的详细内容-优化
     * @param subOrderId 子订单编号
     * @param type 订单类型
     * @return ResultBean.GetOrderDetailByMerchantManageVoRes
     * @throws FroadBusinessException
     *<pre>
     *
     * @Description: 查询订单详细内容 
     * @version V1.0 修改人：Arron 日期：2015年4月17日 上午11:14:25 
     *
     *</pre>
     */
    public abstract ResultBean getSubOrderDetailByMerchantManageNew(String clientId, String subOrderId, String type) throws FroadBusinessException;
    
    /**
     * Boss查询订单列表
     * @param condition 查询条件 {@link OrderQueryByBossCondition}
     * @param pageVo 分页数据 {@link PageVo}
     * @return 返回结果 {@link ResultBean} 
     *<pre>
     *
     * @Description: Boss查询订单表订单数据 
     * @version V1.0 修改人：Arron 日期：2015年4月28日 下午3:39:16 
     *
     *</pre>
     */
    public abstract ResultBean queryOrderByBoss(OrderQueryByBossCondition condition, PageVo pageVo) throws FroadBusinessException;

    /**
     * boss平台查询订单详细
     * @param orderId 订单ID
     * @return {@link ResultBean} List<SubOrderVo>
     * @throws FroadBusinessException
     *<pre>
     *
     * @Description: 通过订单号查询子订单列表 
     * @version V1.0 修改人：Arron 日期：2015年4月29日 上午11:51:38 
     *
     *</pre>
     */
    public abstract ResultBean getSubOrderDetailOfBossByOrderId(String clientId, String orderId) throws FroadBusinessException;

    /**
     * 查询大订单内容
     * @param orderId 大订单号
     * @return {@link ResultBean}
     * @throws FroadBusinessException e
     *<pre>
     *
     * @Description: 根据大订单号， 查询所有的子订单内容 
     * @version V1.0 修改人：Arron 日期：2015年5月2日 下午3:04:55 
     *
     *</pre>
     */
    public abstract ResultBean getOrderDetailByOrderId(String clientId, String orderId) throws FroadBusinessException;

	/**
	 * 商品送积分明细查询
	 * @param req
	 * @return
	 */
	public abstract ResultBean queryGivePointsProductByBoss(OrderQueryByBossCondition condition, PageVo pageVo);

	/**
	 * 查询预售配送商品所有相关订单的发货人信息
	 * @param condition
	 * @param pageVo
	 * @return
	 */
	public abstract ResultBean queryRecvInfoForProductByBoss(OrderQueryByBossCondition condition, PageVo pageVo);
	
	/**
	 * 根据面对面订单查询结果查询商户用户名Map
	 * @param list
	 * @return
	 */
	public Map<Long,String> getMerchantUserNameMap(List<Long> list);

	/**
	 * 银行管理平台导出订单
	 * @param list 银行管理平台订单列表查询结果
	 * @param orderType 订单类型 OrderType枚举
	 * @return url
	 */
	public abstract OrderExportData exportOrderByBankManage(List<QueryOrderByBankManageVo> list,QueryOrderByBankManageVoReq req)  throws FroadBusinessException;

	
	
	/**
	 * 银行管理平台精品商城导出订单
	 * @param list 银行管理平台精品商城订单列表查询结果
	 * @param orderType 订单类型 OrderType枚举
	 * @return url
	 */
	public abstract OrderExportData exportBoutiqueOrderByBankManage(List<QueryBoutiqueOrderByBankManageVo> list,QueryBoutiqueOrderByBankManageVoReq req)  throws FroadBusinessException;

	
	
	/**
	 * 商户管理平台导出订单
	 * @param list
	 * @param req
	 * @return
	 * @throws FroadBusinessException
	 */
	public abstract OrderExportData exportOrderByMerchantManage(List<QueryOrderByMerchantManageVo> list,QueryOrderByMerchantManageVoReq req) throws FroadBusinessException;
    
}
