package com.froad.support;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.froad.db.mongo.page.MongoPage;
import com.froad.po.settlement.Settlement;
import com.froad.po.settlement.SettlementReq;
import com.mongodb.DBObject;

/**
 *  结算数据API
  * @ClassName: SettlementSupport
  * @Description: TODO
  * @author share 2015年3月26日
  * @modify share 2015年3月26日
 */
public interface SettlementSupport {
	
	/**
	 *  根据OrderId查询结算记录
	  * @Title: querySettlement
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param orderId
	  * @param @return    
	  * @return List<Settlement>    
	  * @throws
	 */
	public abstract List<Settlement> querySettlement(String orderId);
	
	/**
	 *  获取结算记录统计值
	  * @Title: countSettlement
	  * @Description: TODO
	  * @author: share 2015年5月16日
	  * @modify: share 2015年5月16日
	  * @param @param orderId
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public abstract int countSettlement(String orderId);
	
	/**
	 *  根据卷ID查询团购结算记录
	  * @Title: querySettlement
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param orderId
	  * @param @param subOrderId
	  * @param @param ticketId
	  * @param @return    
	  * @return List<Settlement>    
	  * @throws
	 */
	public abstract int querySettlement(String orderId,String subOrderId,String ticketId);

	/**
	 *  添加面对面结算记录
	  * @Title: createQrcideSettlement
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param settlement
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public abstract boolean createSettlement(Settlement settlement);
	
	/**
	 *  删除结算记录
	  * @Title: createQrcideSettlement
	  * @Description: TODO
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param settlement
	  * @param @param type
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public abstract boolean deleteSettlement(String settlementId);

	/**
	 *  更新结算状态
	  * @Title: upateSettlement
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param code
	  * @param @param paymentId    
	  * @return void    
	  * @throws
	 */
	public abstract boolean upateSettlement(String settlementId,String code, String paymentId,String ...remark);
 
	/**
	 *  根据结算ID查询结算记录
	  * @Title: queryBySettlementId
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param settlementId
	  * @param @return    
	  * @return Settlement    
	  * @throws
	 */
	public abstract Settlement queryBySettlementId(String settlementId);

	/**
	 *  根据结算ID更新结算信息
	  * @Title: upateBySettlementId
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param settlementId
	  * @param @param code
	  * @param @param remark    
	  * @return void    
	  * @throws
	 */
	public abstract Settlement upateSettlementing(String settlementId,String remark);
	
	/**
	 *  根据支付ID修改结算状态
	  * @Title: upateByPaymentId
	  * @Description: TODO
	  * @author: share 2015年4月11日
	  * @modify: share 2015年4月11日
	  * @param @param paymentId
	  * @param @param code
	  * @param @param remark
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public abstract boolean upateByPaymentId(String paymentId, String code,String remark);

	/**
	 *  分页查询
	  * @Title: queryByPage
	  * @Description: TODO
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param req
	  * @param @return    
	  * @return MongoPage    
	  * @throws
	 */
	public abstract MongoPage queryByPage(SettlementReq req);

	/**
	 * 
	 * exportByPage:分页导出
	 *
	 * @author vania
	 * 2015年9月16日 下午7:38:50
	 * @param req
	 * @return
	 *
	 */
	public MongoPage exportByPage(SettlementReq req) ;
	
	/**
	 *  按条件查询
	  * @Title: queryList
	  * @Description: TODO
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param req
	  * @param @return    
	  * @return List<Settlement>    
	  * @throws
	 */
	public abstract List<Settlement> queryList(SettlementReq req);

	
	/**
	 *  查询详情
	  * @Title: queryById
	  * @Description: TODO
	  * @author: share 2015年3月30日
	  * @modify: share 2015年3月30日
	  * @param @param id
	  * @param @return    
	  * @return Settlement    
	  * @throws
	 */
	public Settlement queryById(String id);
	
	/**
	 *  根据商品ID和订单ID获取结算信息
	  * @Title: queryByProductId
	  * @Description: TODO
	  * @author: share 2015年4月14日
	  * @modify: share 2015年4月14日
	  * @param @param id
	  * @param @return    
	  * @return Settlement    
	  * @throws
	 */
	public Settlement queryByProductId(String orderId,String productId);
	
	/**
	 * 根据子订单号和商品编号查找结算信息
	 * @param subOrderId 子订单号
	 * @param productId 商品ID
	 * @return
	 *<pre>
	 *
	 * @version V1.0 修改人：Arron 日期：2015年4月17日 下午1:29:26 
	 *
	 *</pre>
	 */
	public List<Settlement> querySettlement(String subOrderId, String productId);
	
	/***
	 * 根据子订单查询结算信息
	 * @param subOrdeId 子订单号
	 * @return
	 *<pre>
	 *
	 * @Description: 根据子订单查询结算信息 
	 * @version V1.0 修改人：Arron 日期：2015年5月2日 下午12:44:55 
	 *
	 *</pre>
	 */
	public List<Settlement> queryListBySubOrderId(String subOrdeId);
	
	/**
	 *  更新结算ID
	  * @Title: upateBySettlementId
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param settlementId
	  * @param @param code
	  * @param @param remark
	  * @throws
	  * @see com.froad.support.SettlementSupport#upateBySettlementId(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean upateById(String id, String code,String remark);

	/**
	 *  查询待结算记录按子订单ID
	  * @Title: querySettlementBySubOrderId
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param subOrderId
	  * @param @return    
	  * @return List<Settlement>    
	  * @throws
	 */
	public abstract List<Settlement> querySettlementBySubOrderId(String subOrderId);

	/**
	 *  根据子订单ID修改结算记录
	  * @Title: updateSettlementBySubOrderId
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param subOrderId
	  * @param @param code
	  * @param @param remark
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public abstract boolean updateSettlementBySubOrderId(String subOrderId,String code, String remark);
	
	/**
	 *  查询结算中的支付订单号
	  * @Title: querySettlementing
	  * @Description: TODO
	  * @author: share 2015年4月9日
	  * @modify: share 2015年4月9日
	  * @param @return    
	  * @return List<String>    
	  * @throws
	 */
	public abstract List<String> querySettlementing();
	
	/**
	 * 提供批量查询券状态
	* <p>Function: querySettlementStatusList</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-7-8 下午1:52:16
	* @version 1.0
	* @param ticketIds
	* @return
	 */
	public List<Settlement> querySettlementStatusList(List<String> ticketIds);

	/**
	 * 查询券状态
	 * querySettlementStatusMap:
	 *
	 * @author vania
	 * 2015年9月1日 下午6:01:40
	 * @param tickets
	 * @return key 券id  value  结算状态
	 *
	 */
	public Map<String, String> querySettlementStatusMap(List<String> tickets) ;
	
	/**
	 * find settlement information by pipe line
	 * 
	 * @param pipeline
	 * @return
	 */
	public Iterator<DBObject> findByPipeline(List<DBObject> pipeline);
	
}

