package com.froad.db.mysql.mapper;

public interface CbankClientMapper {
	/**
	 * 根据clientId获取银行logo url
	 * 
	 * @param clientId
	 * @return
	 */
	public String getLogoUrl(String clientId);
}
