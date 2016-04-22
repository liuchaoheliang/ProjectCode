/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

package com.froad.db.chonggou.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;






import com.froad.db.chonggou.entity.AdPositionCG;
import com.froad.db.chonggou.entity.AreaCG;
import com.froad.db.chonggou.entity.DeliveryCorpCG;
import com.froad.db.redis.impl.RedisManager;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: MerchantRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author longyunbo      
 * @version 1.0    
 * @created 2015年3月20日 上午10:11:55   
 */
public class SupportRedis {
	
	private static final RedisManager redis = new RedisManager();
	
	/** 
	 * 缓存广告位表
	* @Title: set_cbbank_adpos_client_id_ad_position_id 
	* @Description: 
	* @author longyunbo
	* @param  adpositions
	* @return Boolean
	* @throws 
	*/
	public static Boolean set_cbbank_adpos_client_id_ad_position_id(AdPositionCG adposition){
			String key = RedisKeyUtil.cbbank_adpos_client_id_ad_position_id(adposition.getClientId(), adposition.getId());
			
			Map<String,String> hash = new HashMap<String, String>();
			hash.put("height", ObjectUtils.toString(adposition.getHeight()));
			hash.put("width", ObjectUtils.toString(adposition.getWidth()));
			hash.put("position_style", ObjectUtils.toString(adposition.getPositionStyle()));
			hash.put("name", ObjectUtils.toString(adposition.getName()));
			hash.put("description", ObjectUtils.toString(adposition.getDescription()));
			hash.put("position_page", ObjectUtils.toString(adposition.getPositionPage()));
			hash.put("position_point", ObjectUtils.toString(adposition.getPositionPoint()));
			hash.put("is_enable", BooleanUtils.toString(adposition.getIsEnable(), "1", "0", ""));
			String result=redis.putMap(key, hash);
			return "OK".equals(result);
	}
	
	

	/**
	 * 
	 * 缓存地区表
	 * @Title: set_cbbank_area_client_id_area_id
	 * @author longyunbo
	 * @version 1.0
	 * @see: TODO
	 * @param area
	 * @return
	 * @return Boolean 返回类型
	 * @throws
	 */
	public static Boolean set_cbbank_area_client_id_area_id(AreaCG area) {
			/* 缓存全部地区 */
			String key = RedisKeyUtil.cbbank_area_client_id_area_id(area.getId()) ;
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("name", ObjectUtils.toString(area.getName()));
			hash.put("tree_path", ObjectUtils.toString(area.getTreePath()));
			hash.put("tree_path_name", ObjectUtils.toString(area.getTreePathName()));
			hash.put("area_code", ObjectUtils.toString(area.getAreaCode()));
			hash.put("vague_letter", ObjectUtils.toString(area.getVagueLetter()));
			hash.put("parent_id", ObjectUtils.toString(area.getParentId()));
			hash.put("is_enable", BooleanUtils.toString(area.getIsEnable(), "1", "0", ""));
			String result=redis.putMap(key, hash);
			return "OK".equals(result);
//			return true;
	}
	
	
	
	
	
	/** 
	 * 缓存物流公司 
	* @Title: set_cbbank_deliver_company_client_id_delivery_id 
	* @Description: 
	* @author longyunbo
	* @param  deliverycorps
	* @return Boolean
	* @throws 
	*/
	public static Boolean set_deliver_company_client_id_delivery_corp_id(DeliveryCorpCG deliverycorp) {
		
			/* 缓存全部物流公司 */
			String key = RedisKeyUtil.cbbank_deliver_company_client_id_delivery_corp_id(deliverycorp.getClientId(), deliverycorp.getId()) ;

			Map<String, String> hash = new HashMap<String, String>();
			hash.put("name", ObjectUtils.toString(deliverycorp.getName()));
			hash.put("url", ObjectUtils.toString(deliverycorp.getUrl()));
			hash.put("orderValue", ObjectUtils.toString(deliverycorp.getOrderValue()));
			hash.put("is_enable", BooleanUtils.toString(deliverycorp.getIsEnable(), "1", "0", ""));
			String result=redis.putMap(key, hash);
			return "OK".equals(result);
	}
	
	
	
}
