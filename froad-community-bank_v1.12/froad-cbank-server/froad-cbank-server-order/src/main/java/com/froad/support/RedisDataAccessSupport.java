package com.froad.support;

import com.froad.po.ClientPaymentChannel;


public interface RedisDataAccessSupport {
    

	String acquireOpenAPIPartnerNo(String clientId);

	/**
	 * Type:支付渠道类型 1-方付通积分 2-银行积分 
	* <p>Function: acquirePaymentOrgNoByType</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-4-29 上午11:29:32
	* @version 1.0
	* @param clientId
	* @param type
	* @return
	 */
    String acquirePaymentOrgNoByType(String clientId,int type);

    String acquirePointPartnerNo(String clientId);
    
    String acquirePointsRate(String clientId); 
    
    String acquireClientName(String clientId);

    String acquireBankName(String clientId);
    
    String acquireBankId(String clientId);

	boolean setPaymentChannel(ClientPaymentChannel channel);
}
