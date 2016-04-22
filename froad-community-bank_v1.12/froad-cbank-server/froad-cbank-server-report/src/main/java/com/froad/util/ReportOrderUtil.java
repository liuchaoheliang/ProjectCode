package com.froad.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.rp_mappers.ReportOrderMapper;
import com.froad.po.ReportOrder;

public class ReportOrderUtil {
	private SqlSession sqlSession = null;
	private Map<String, List<ReportOrder>> orderMap = null;
	
	// clientId + memberCode + orderType
	public static final char KEY_MEMBER_CODE_ORDER_TYPE = '1';
	
	// clientId + memberCode
	public static final char KEY_MEMBER_CODE = '2';
	
	// clientId + merchantId + orderType
	public static final char KEY_MERCHANT_ID_ORDER_TYPE = '3';
	
	/**
	 * constructor
	 * 
	 * @param sqlSession
	 * @param batchDate
	 */
	public ReportOrderUtil (SqlSession sqlSession, Integer batchDate, char keyType) {
		this.sqlSession = sqlSession;
		orderMap = getOrderMap(batchDate, keyType);
	}
	
	/**
	 * get report order list by clientId + memberCode + orderType
	 * 
	 * @param clientId
	 * @param memberCode
	 * @param orderType
	 * @return
	 */
	public List<ReportOrder> getOrderList(String clientId, Long memberCode, String orderType){
		List<ReportOrder> orderList = null;
		StringBuffer key = null;
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(memberCode);
		key.append(orderType);
		
		orderList = orderMap.get(key.toString());
		
		return orderList;
	}
	
	/**
	 * get order list by clientId + memberCode
	 * 
	 * @param clientId
	 * @param memberCode
	 * @return
	 */
	public List<ReportOrder> getOrderList(String clientId, Long memberCode){
		List<ReportOrder> orderList = null;
		StringBuffer key = null;
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(memberCode);
		
		orderList = orderMap.get(key.toString());
		
		return orderList;
	}
	
	/**
	 * get order list by merchantId + orderType
	 * 
	 * @param clientId
	 * @param merchantId
	 * @param orderType
	 * @return
	 */
	public List<ReportOrder> getOrderList(String clientId, String merchantId, String orderType){
		List<ReportOrder> orderList = null;
		StringBuffer key = null;
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(merchantId);
		key.append(orderType);
		
		orderList = orderMap.get(key.toString());
		
		return orderList;
	}
	
	/**
	 * get report order map
	 * 
	 * 	key = clientId + memberCode + orderType
	 * 
	 * @param batchDate
	 * @return
	 */
	private Map<String, List<ReportOrder>> getOrderMap(Integer batchDate, char keyType){
		Map<String, List<ReportOrder>> orderMap = null;
		List<ReportOrder> orderList = null;
		List<ReportOrder> innerList = null;
		ReportOrder order = null;
		StringBuffer key = null;
		
		orderMap = new HashMap<String, List<ReportOrder>>();
		orderList = getOrderList(batchDate);
		for (int i = 0; i < orderList.size(); i++){
			order = orderList.get(i);
			key = new StringBuffer();
			if (keyType == KEY_MEMBER_CODE_ORDER_TYPE){
				key.append(order.getClientId());
				key.append(order.getMemberCode());
				key.append(order.getOrderType());
			} else if (keyType == KEY_MEMBER_CODE) {
				key.append(order.getClientId());
				key.append(order.getMemberCode());
			} else if (keyType == KEY_MERCHANT_ID_ORDER_TYPE) {
				key.append(order.getClientId());
				key.append(order.getMerchantId());
				key.append(order.getOrderType());
			}
			innerList = orderMap.get(key.toString());
			if (null == innerList){
				innerList = new ArrayList<ReportOrder>();
			}
			innerList.add(order);
			orderMap.put(key.toString(), innerList);
		}
		
		return orderMap;
	}
	
	/**
	 * get order list
	 * 
	 * @param batchDate
	 * @return
	 */
	private List<ReportOrder> getOrderList(Integer batchDate){
		List<ReportOrder> orders = null;
		ReportOrderMapper mapper = null;
		List<ReportOrder> orderList = null;
		Page<ReportOrder> page = null;
		int pageNumber = 1;
		int pageSize = Page.MAX_PAGE_SIZE;
		
		mapper = sqlSession.getMapper(ReportOrderMapper.class);
		page = new Page<ReportOrder>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		
		orderList = mapper.findByConditionByPage(batchDate, page);
		orders = new ArrayList<ReportOrder>();
		while (null != orderList && orderList.size() > 0){
			orders.addAll(orderList);
			if (orderList.size() < pageSize){
				break;
			}
			pageNumber += 1;
			page.setPageNumber(pageNumber);
			
			sqlSession.commit(false);
			orderList = mapper.findByConditionByPage(batchDate, page);
		}
		
		return orders;
	}
}
