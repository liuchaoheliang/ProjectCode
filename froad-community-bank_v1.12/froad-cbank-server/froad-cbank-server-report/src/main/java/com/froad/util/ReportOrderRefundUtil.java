package com.froad.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.rp_mappers.ReportOrderRefundMapper;
import com.froad.po.ReportOrderRefund;

public class ReportOrderRefundUtil {
	private SqlSession sqlSession = null;
	private Map<String, List<ReportOrderRefund>> refundMap = null;
	
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
	 * @param keyType
	 */
	public ReportOrderRefundUtil (SqlSession sqlSession, Integer batchDate, char keyType) {
		this.sqlSession = sqlSession;
		refundMap = getRefundMap(batchDate, keyType);
	}
	
	/**
	 * get report order refund list by clientId + memberCode + orderType
	 * 
	 * @param clientId
	 * @param memberCode
	 * @param orderType
	 * @return
	 */
	public List<ReportOrderRefund> getRefundList(String clientId, Long memberCode, String orderType){
		List<ReportOrderRefund> refundList = null;
		StringBuffer key = null;
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(memberCode);
		key.append(orderType);
		
		refundList = refundMap.get(key.toString());
		
		return refundList;
	}
	
	/**
	 * get refundList by clientId + memberCode
	 * 
	 * @param clientId
	 * @param memberCode
	 * @return
	 */
	public List<ReportOrderRefund> getRefundList(String clientId, Long memberCode){
		List<ReportOrderRefund> refundList = null;
		StringBuffer key = null;
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(memberCode);
		
		refundList = refundMap.get(key.toString());
		
		return refundList;
	}
	
	/**
	 * get refundList by clientId + merchantId + orderType
	 * 
	 * @param clientId
	 * @param merchantId
	 * @param orderType
	 * @return
	 */
	public List<ReportOrderRefund> getRefundList(String clientId, String merchantId, String orderType){
		List<ReportOrderRefund> refundList = null;
		StringBuffer key = null;
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(merchantId);
		key.append(orderType);
		
		refundList = refundMap.get(key.toString());
		
		return refundList;
	}
	
	/**
	 * get report order refund map
	 * 
	 * 	key = clientId + memberCode + orderType
	 * 
	 * @param batchDate
	 * @return
	 */
	private Map<String, List<ReportOrderRefund>> getRefundMap(Integer batchDate, char keyType){
		Map<String, List<ReportOrderRefund>> refundMap = null;
		List<ReportOrderRefund> refundList = null;
		List<ReportOrderRefund> innerList = null;
		ReportOrderRefund refund = null;
		StringBuffer key = null;
		
		refundMap = new HashMap<String, List<ReportOrderRefund>>();
		refundList = getRefundList(batchDate);
		for (int i = 0; i < refundList.size(); i++){
			refund = refundList.get(i);
			key = new StringBuffer();
			if (keyType == KEY_MEMBER_CODE_ORDER_TYPE){
				key.append(refund.getClientId());
				key.append(refund.getMemberCode());
				key.append(refund.getOrderType());
			} else if (keyType == KEY_MEMBER_CODE) {
				key.append(refund.getClientId());
				key.append(refund.getMemberCode());
			} else if (keyType == KEY_MERCHANT_ID_ORDER_TYPE) {
				key.append(refund.getClientId());
				key.append(refund.getMerchantId());
				key.append(refund.getOrderType());
			}
			innerList = refundMap.get(key.toString());
			if (null == innerList){
				innerList = new ArrayList<ReportOrderRefund>();
			}
			innerList.add(refund);
			refundMap.put(key.toString(), innerList);
		}
		
		return refundMap;
	}
	
	/**
	 * get order refund list
	 * 
	 * @param batchDate
	 * @return
	 */
	private List<ReportOrderRefund> getRefundList(Integer batchDate){
		List<ReportOrderRefund> refunds = null;
		ReportOrderRefundMapper mapper = null;
		List<ReportOrderRefund> refundList = null;
		Page<ReportOrderRefund> page = null;
		int pageNumber = 1;
		int pageSize = Page.MAX_PAGE_SIZE;
		
		mapper = sqlSession.getMapper(ReportOrderRefundMapper.class);
		page = new Page<ReportOrderRefund>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		
		refundList = mapper.findByMemberCodeByPage(batchDate, page);
		refunds = new ArrayList<ReportOrderRefund>();
		while (null != refundList && refundList.size() > 0){
			refunds.addAll(refundList);
			if (refundList.size() < pageSize){
				break;
			}
			pageNumber += 1;
			page.setPageNumber(pageNumber);
			
			sqlSession.commit(false);
			refundList = mapper.findByMemberCodeByPage(batchDate, page);
		}
		
		return refunds;
	}
}
