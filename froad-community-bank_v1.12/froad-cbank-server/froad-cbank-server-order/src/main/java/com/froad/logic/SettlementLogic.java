package com.froad.logic;

import java.util.List;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.SettlementStatus;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.settlement.Settlement;
import com.froad.po.settlement.SettlementReq;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.settlement.SettlementPage;

/**
 *  结算交易接口
  * @ClassName: SettlementLogic
  * @Description: TODO
  * @author share 2015年3月26日
  * @modify share 2015年3月26日
 */
public interface SettlementLogic {

	/**
	 *  支付结算
	 *  1、面对面进行实时结算
	 *  2、名优特惠生成待结算计量
	  * @Title: cashSettlement
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param orderId
	  * @param @return    
	  * @return ResultBean    
	  * @throws
	 */
	public abstract ResultBean paySettlement(OrderMongo order);
	
	/**
	 *  团购验卷结算
	  * @Title: groupSettlement
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param orderId
	  * @param @param subOrderId
	  * @param @param merchantId
	  * @param @param outletId
	  * @param @param merchantUserId
	  * @param @return    
	  * @return ResultBean    
	  * @throws
	 */
	public abstract ResultBean groupSettlement(List<String> tickets,String merchantId,String outletId);
	
	/**
	 *  名优特惠结算
	  * @Title: specialSettlement
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param memberCode
	  * @param @param subOrderId
	  * @param @param source 1-用户确认结算 2-系统批确认结算
	  * @param @return    
	  * @return ResultBean    
	  * @throws
	 */
	public abstract ResultBean specialSettlement(String subOrderId,int source);
	
	/**
	 *  结算记录设置无效
	  * @Title: specialSettlementUninvalid
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param subOrderId
	  * @param @param remark
	  * @param @return    
	  * @return ResuleBean    
	  * @throws
	 */
	public abstract ResultBean settlementUninvalid(String subOrderId,String remark);
	
	
	/**
	 *  更新结算记录
	  * @Title: notifySettlement
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param settlementId
	  * @param @param type
	  * @param @param remark
	  * @param @return    
	  * @return ResultBean    
	  * @throws
	 */
	public abstract ResultBean notitySettlement(String paymentId, SettlementStatus type, String remark);
	
	/**
	 *  更新状态
	  * @Title: updateSettlement
	  * @Description: TODO
	  * @author: share 2015年3月30日
	  * @modify: share 2015年3月30日
	  * @param @param id
	  * @param @param type
	  * @param @param remark
	  * @param @return    
	  * @return ResultBean    
	  * @throws
	 */
	public abstract ResultBean updateSettlement(String id, SettlementStatus type, String remark);
	
	/**
	 *  结算记录分页查询
	  * @Title: getSettlement
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @return    
	  * @return List<Settlement>    
	  * @throws
	 */
	public abstract MongoPage querySettlementByPage(SettlementReq req);
	
	public abstract MongoPage querySettlementByPage(SettlementPage page);
	
	/**
	 * 
	 * exportSettlementByPage:结算记录分页导出Excel
	 *
	 * @author vania
	 * 2015年9月2日 上午11:44:17
	 * @param page
	 * @return
	 *
	 */
	public abstract ExportResultRes exportSettlementByPage(SettlementPage page);
	
	/**
	 *  报表导出查询
	  * @Title: querySettlement
	  * @Description: TODO
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param req
	  * @param @return    
	  * @return List<Settlement>    
	  * @throws
	 */
	public abstract List<Settlement> querySettlement(SettlementReq req);
	
	/**
	 *  结算详情查询
	  * @Title: querySettlementById
	  * @Description: TODO
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param settlementId
	  * @param @return    
	  * @return Settlement    
	  * @throws
	 */
	public abstract Settlement querySettlementById(String settlementId);
	
	/**
	 *  查询结算中的结算记录
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
	 *  按支付ID更新结算状态
	  * @Title: updateByPaymentId
	  * @Description: TODO
	  * @author: share 2015年4月9日
	  * @modify: share 2015年4月9日
	  * @param @param paymengId
	  * @param @param code
	  * @param @return    
	  * @return ResultBean    
	  * @throws
	 */
	public abstract boolean updateByPaymentId(String paymentId,String code);
	
	/**
	 * 查询券结算状态
	 * @Title: getTicketIdSettlementList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param ticketIdList
	 * @return
	 * @return List<Settlement>    返回类型 
	 * @throws
	 */
	public List<Settlement> getTicketIdSettlementList(List<String> ticketIdList);
	
}

