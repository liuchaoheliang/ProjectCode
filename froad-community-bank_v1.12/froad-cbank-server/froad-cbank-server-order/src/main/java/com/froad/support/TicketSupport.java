package com.froad.support;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.froad.db.mongo.page.MongoPage;
import com.froad.po.Ticket;
import com.mongodb.DBObject;

public interface TicketSupport {
	/**
	 * 根据券ID获取券列表(所有状态)
	 * 
	 * @param ticketId
	 * @return
	 */
	public List<Ticket> getTicketByTicketId(String ticketId);
	
	/**
	 * 获取唯一券
	 * 
	 * @param ticketId
	 * @param status
	 * @return
	 */
	public Ticket getUniqueTicket(String ticketId, String status);
	
	/**
	 * 查找券表: 
	 * 
	 * @param orderId
	 * @param subOrderId
	 * @param productId
	 * @return
	 */
	public List<Ticket> getTickets(String orderId, String subOrderId, String productId);
	
	/**
	 * 更新券状态
	 * @param ticket
	 */
	public void updateTicketState(Ticket ticket) ;
	
	/**
	 * 添加券
	 * 
	 * @param ticket
	 */
	public void addTicket(Ticket ticket);
	
	/**
	 * 根据券ID获取有效券券详细列表
	 * 
	 * @param ticketIdList
	 * @return
	 */
	public List<Ticket> getValidTicketList(List<String> ticketIdList);
	
	/**
	 * 获取券详细列表
	 * @Title: getTicketList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param ticket
	 * @return
	 * @return List<Ticket>    返回类型 
	 * @throws
	 */
	public List<Ticket> getTicketList(Ticket ticket);
	
	/**
	 * 根据子订单id,提货人id,商户id,门店id获取有效券券详细列表
	 * @Title: getValidTicketList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param memberCode
	 * @param subOrderId
	 * @param merchantId
	 * @param outletId
	 * @return
	 * @return List<Ticket>    返回类型 
	 * @throws
	 */
	public List<Ticket> getValidTicketList(String memberCode, String subOrderId, String merchantId, String outletId);
	
	/**
	 * 根据DBObject分页查找券列表
	 * 
	 * @param queryObj
	 * @return
	 */
	public List<Ticket> getListByDBObject(DBObject queryObj);
	
	/**
	 * 根据DBObject分页查找券列表
	 * 
	 * @param queryObj
	 * @param page
	 * @return
	 */
	public MongoPage getTicketPageByDBObject(DBObject queryObj, MongoPage page);
	
	/**
	 * 
	 * exportTicketPageByDBObject:(这里用一句话描述这个方法的作用).
	 *
	 * @author vania
	 * 2015年9月16日 下午8:21:56
	 * @param queryObj
	 * @param page
	 * @return
	 *
	 */
	public MongoPage exportTicketPageByDBObject(DBObject queryObj, MongoPage page);
	
	/**
	 * 根据消费时间排序分页查询
	* <p>Function: getTicketPageByConsumeTime</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-7-13 下午11:50:27
	* @version 1.0
	* @param queryObj
	* @param page
	* @return
	 */
	public MongoPage getTicketPageByConsumeTime(DBObject queryObj, MongoPage page);
	
	/**
	 *  根据DBObject分页查找券列表(不使用redis,  可自定义排序字段)
	 * @Title: getTicketPageByDBObjectNoRedis 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param queryObj
	 * @param page
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	public MongoPage getTicketPageByDBObjectNoRedis(DBObject queryObj, MongoPage page) ;
	
	/**
	 * 根据条件查询金额
	 * @Title: getTicketTotalAmountDBObject 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param queryObj
	 * @return
	 * @return int    返回类型 
	 * @throws
	 */
	public int getTicketTotalAmountDBObject(DBObject queryObj);
	
	/**
	 * 根据券ID列表更新券状态
	 * 
	 * @param valueObj - 待更新的对象
	 * @param ticketIdList - 券ID列表
	 */
	public void updateByTicketIdList(DBObject valueObj, List<String> ticketIdList);
	
	/**
	 * 根据id更新券
	 * 
	 * @param valueObj - 待更新的对象
	 * @param id - mongo id
	 */
	public void updateById(DBObject valueObj, ObjectId id);
	
	/**
	 * 修改券
	 * @Title: update 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param valueObj
	 * @param where
	 * @return void    返回类型 
	 * @throws
	 */
	public void update(DBObject valueObj, DBObject where) ;
	/**
	 * 查找并更新
	 * 
	 * @param queryObj
	 * @param updateObj
	 * @return
	 */
	public Ticket findAndModifyByDBObject(DBObject queryObj, DBObject updateObj);
	
	/**
	 * 更新
	 * 
	 * @param queryObj
	 * @param updateObj
	 */
	public void updateMultiByDBObject(DBObject queryObj, DBObject updateObj);
	
	/**
	 * 获取券总数量
	 * 
	 * @param queryObj
	 * @return
	 */
	public int getTotalCount(DBObject queryObj);
	
	/**
	 * 回滚提货码
	 * 
	 * @param queryObj
	 */
	public void removeByDBObject(DBObject queryObj);
	
	/**
	 * find ticket information by pipe line
	 * 
	 * @param pipeline
	 * @return
	 */
	public Iterator<DBObject> findByPipeline(List<DBObject> pipeline);
}
