package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.BankAudit;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;

public class AuditRedis {
	
	private static RedisManager manager = new RedisManager();
	
	/**
	 * 审核商品/商户redis操作
	 * @Title: updateProductisMarketable 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param auditFlag	审核标志 1-商品 2-商户
	 * @param audit	审核对象
	 * @return
	 * @throws
	 */
	public static Boolean audit(int auditFlag, BankAudit audit){
		boolean result = false;
		switch (auditFlag) {
		case 1://商品
			result = auditProduct(audit);
			break;
		case 2://商户
			result = auditMerchant(audit);
			break;
		case 3://秒杀商品
			result = auditProductSeckill(audit);
			break;
		default:
			return false;
		}
		return result;
	}
	
	/**
	 * 更新商品缓存信息
	 * @Title: auditProduct 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param audit
	 * @return
	 * @throws
	 */
	private static Boolean auditProduct(BankAudit audit){
		String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(audit.getClientId(), audit.getMerchantId(), audit.getAuditId());
		Long result=0l;
		if(Checker.isNotEmpty(audit.getIsMarketable())){
			result = manager.hset(key, "is_marketable", audit.getIsMarketable());
		}
		
		return result != -1;
	}
	
	/**
	 * 更新商户缓存信息
	 * @Title: auditMerchant 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param audit
	 * @return
	 * @throws
	 */
	private static Boolean auditMerchant(BankAudit audit){
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(audit.getClientId(), audit.getAuditId());
		Map<String, String> valueMap = new HashMap<String, String>();
		if(Checker.isNotEmpty(audit.getAuditOrgCode())){
			valueMap.put("audit_org_code", audit.getAuditOrgCode());
		}
		if(Checker.isNotEmpty(audit.getAuditState())){
			valueMap.put("audit_state", audit.getAuditState());
		}
		if(Checker.isNotEmpty(audit.getAuditStage())){
			valueMap.put("audit_stage", audit.getAuditStage());
		}
		if(Checker.isNotEmpty(audit.getIsEnable())){
			valueMap.put("is_enable", BooleanUtils.toString(audit.getIsEnable(), "1", "0", ""));
		}
		
		
		String result = manager.putMap(key, valueMap);
		return "OK".equals(result);
	}
	
	/**
	 * 更新秒杀商品缓存信息
	 * @Title: auditProductSeckill 
	 * @Description: TODO
	 * @param audit
	 * @return
	 * @throws
	 */
	private static Boolean auditProductSeckill(BankAudit audit){
		String key = RedisKeyUtil.cbbank_seckill_product_client_id_product_id(audit.getClientId(), audit.getAuditId());
		Long result=0l;
		if(Checker.isNotEmpty(audit.getIsMarketable())){
			result = manager.hset(key, "is_marketable", audit.getIsMarketable());
			
			// 更新商品秒杀标识
			String pKey = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(audit.getClientId(), audit.getMerchantId(), audit.getAuditId());
			result = manager.hset(pKey, "is_seckill", audit.getIsSeckill());
		}
		
		return result != -1;
	}
}
