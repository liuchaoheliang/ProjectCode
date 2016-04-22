package com.froad.logic.impl;

import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantOperateLogLogic;
import com.froad.po.MerchantOperateLog;

/**
 * 
 * <p>@Title: BankOperateLogLogicImpl.java</p>
 * <p>Description: 描述 </p> 银行用户操作日志Logic实现类<>
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月2日
 */
public class MerchantOperateLogLogicImpl implements MerchantOperateLogLogic {


    /**
     * 增加 BankOperateLog用户操作日志
     * @param bankOperator
     * @return Long    主键ID
     */
	@Override
	public Boolean addBankOperateLog(MerchantOperateLog merchantOperatorLog) {
		
		@SuppressWarnings("unchecked")
		Map<String, Object> message = new LinkedMap();
		message.put("user_id", merchantOperatorLog.getUserId());
		message.put("username", merchantOperatorLog.getUsername());
		message.put("description", merchantOperatorLog.getDescription());
		message.put("client_id", merchantOperatorLog.getClientId());
		message.put("org_code", merchantOperatorLog.getOrgCode());
		message.put("org_name", merchantOperatorLog.getOrgName());
		message.put("operator_ip", merchantOperatorLog.getOperatorIp());
		
				
		LogCvt.info(MerchantOperateLogLogic.MESSAGE_PREFIX + JSON.toJSONString(message));
		return true;

	}




    /**
     * 分页查询 BankOperateLog
     * @param page
     * @param MerchantOperateLog
     * @return List<BankOperateLog>    结果集合 
     */
	@Override
	public Page<MerchantOperateLog> findBankOperateLogByPage(Page<MerchantOperateLog> page, MerchantOperateLog merchantOperatorLog) {
		return null;

	}



	
		
		
		
		
		
}