package com.froad.util;

import com.froad.enums.AuditType;

/**
 * <p>
 * Title: RedisMQKeys.java
 * </p>
 * <p>
 * Description: 描述 redis阻塞队列key
 * </p>
 * 
 * @author ll
 * @version 1.4
 * @created 2015年10月14日 上午11:30:08
 */
public class RedisMQKeys {
	
	/**商户审核，阻塞队列key **/
	public static final String    merchant_audit_mq_key 	= RedisKeyUtil.cbbank_fallow_audit_mq_audit_type(AuditType.merchant.name());
	
	/**门店审核，阻塞队列定义Key **/
	public static final String    outlet_audit_mq_key       = RedisKeyUtil.cbbank_fallow_audit_mq_audit_type(AuditType.outlet.name());
	
	/**商品审核，阻塞队列定义Key **/
	public static final String    product_audit_mq_key       = RedisKeyUtil.cbbank_fallow_audit_mq_audit_type(AuditType.group_product.getType());
	
	/**清理图片数据统一RedisKey **/
	public static final String    cbbank_undelete_images     = "cbbank:undelete:images";
}