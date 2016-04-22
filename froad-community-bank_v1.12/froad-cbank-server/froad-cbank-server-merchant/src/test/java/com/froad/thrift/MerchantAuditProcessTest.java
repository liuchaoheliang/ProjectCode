package com.froad.thrift;

import com.froad.db.redis.impl.RedisManager;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisMQKeys;

public class MerchantAuditProcessTest {
	



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			PropertiesUtil.load();
			System.err.println(RedisMQKeys.merchant_audit_mq_key);
			String msg = "{\"auditComment\":\"FFFFFF通过\",\"auditId\":\"2015112600000006\",\"auditStaff\":\"3210003\",\"auditState\":\"1\","
					+ "\"businessId\":\"12B676948000\",\"clientId\":\"chongqing\",\"isArchive\":true,\"isFinalAudit\":true,\"result\":{\"resultCode\":\"0000\",\"resultDesc\":\"操作成功\"}}";
			RedisManager reids = new RedisManager();
			reids.rpush(RedisMQKeys.merchant_audit_mq_key, msg);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

