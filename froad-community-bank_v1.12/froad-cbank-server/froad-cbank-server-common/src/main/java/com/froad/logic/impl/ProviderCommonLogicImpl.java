/**
 * Project Name:froad-cbank-server-common-1.8.0-SNAPSHOT
 * File Name:ProviderCommonLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年11月26日上午10:57:05
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ProviderCommonMapper;
import com.froad.db.redis.ProviderMerchantRedis;
import com.froad.enums.FieldMapping;
import com.froad.logback.LogCvt;
import com.froad.logic.ProviderCommonLogic;
import com.froad.po.Provider;
import com.froad.util.Checker;

/**
 * ClassName:ProviderCommonLogicImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月26日 上午10:57:05
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ProviderCommonLogicImpl implements ProviderCommonLogic {

	@Override
	public Provider findByMerchantId(String merchantId) {
		SqlSession session = null;
		Provider provider = null;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			ProviderCommonMapper providerCommonMapper = session.getMapper(ProviderCommonMapper.class);
			
			Map<String, String> vauleMap = ProviderMerchantRedis.getAll_cbbank_provider_merchant_Id(merchantId);
			provider = convertToProvider(vauleMap);
			
			if(Checker.isEmpty(provider)){
				provider = providerCommonMapper.findByMerchantId(merchantId);
				if(Checker.isNotEmpty(provider)){
					ProviderMerchantRedis.set_cbbank_provider_merchant_Id(provider);
				}
			}
			
		} catch (Exception e) {
			LogCvt.error("根据供应商ID查询信息异常", e);
		} finally {
			if(session != null) session.close();
		}
		return provider;
	}
	
	
	private Provider convertToProvider(Map<String, String> vauleMap){
		Provider p = null;
		if(Checker.isEmpty(vauleMap)){
			return p;
		}
		
		p = new Provider();
		p.setMerchantId(vauleMap.get(FieldMapping.MERCHANT_ID.getMongoField()));;
		p.setMerchantName(vauleMap.get(FieldMapping.MERCHANT_NAME.getMongoField()));
		p.setAddress(vauleMap.get("address"));
		p.setPhone(vauleMap.get("phone"));
		p.setStatus(vauleMap.get("status"));
		p.setDescription(vauleMap.get("description"));
		
		return p;
	}
	
	

}
