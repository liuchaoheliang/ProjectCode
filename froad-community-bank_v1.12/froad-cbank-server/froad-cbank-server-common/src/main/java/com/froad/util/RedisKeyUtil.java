/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: RedisKeyUtil.java
 * @Package com.froad.util
 * @Description: TODO
 * @author vania
 * @date 2015年3月20日
 */

package com.froad.util;

import java.text.MessageFormat;

import org.apache.commons.lang.ObjectUtils;

/**
 * <p>
 * Title: RedisKeyUtil.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年3月20日 下午3:00:08
 */

public class RedisKeyUtil {
	private static final String DEFAULT_PADDING = "*";
	
	
	/* 银行模块 */
	//客户端数据<key结构cbbank:client:client_id>
	public static String cbbank_client_client_id(String client_id) {
		return MessageFormat.format("cbbank:client:{0}", ObjectUtils.toString(client_id, DEFAULT_PADDING));
	}
	//客户端支付渠道<key结构cbbank:client_channel:client_id:payment_channel_id>
	public static String cbbank_client_channel_client_id_payment_channel_id(String client_id,String payment_channel_id) {
		return MessageFormat.format("cbbank:client_channel:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING),ObjectUtils.toString(payment_channel_id, DEFAULT_PADDING));
	}
	//客户端支付渠道信息Set集合 <key结构为cbbank:client_channels:client_id>
	public static String cbbank_client_channels_client_id(String clientId){
		return MessageFormat.format("cbbank:client_channels:{0}", ObjectUtils.toString(clientId, DEFAULT_PADDING));
	}
	//客户端功能模块<key结构cbbank:function_module:client_id:type>
	public static String cbbank_function_module_client_id_type(String client_id,String type) {
		return MessageFormat.format("cbbank:function_module:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING),ObjectUtils.toString(type, DEFAULT_PADDING));
	}
	//客户端功能模块<key结构cbbank:function_module:client_id>
	public static String cbbank_function_module_client_id(String client_id) {
		return MessageFormat.format("cbbank:function_module:{0}", ObjectUtils.toString(client_id, DEFAULT_PADDING));
	}
	
	//银行用户<key结构cbbank:bank_user:client_id:user_id>
	public static String cbbank_bank_user_client_id_user_id(String client_id,Long user_id) {
		return MessageFormat.format("cbbank:bank_user:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING),ObjectUtils.toString(user_id, DEFAULT_PADDING));
	}
	//帐号登录次数<key结构cbbank:bank_user_login:client_id:user_id>
	public static String cbbank_bank_user_login_client_id_user_id(String client_id,Long user_id) {
		return MessageFormat.format("cbbank:bank_user_login:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING),ObjectUtils.toString(user_id, DEFAULT_PADDING));
	}
	//联合登录帐号错误次数<key结构cbbank:bank_user_login:client_id:username>与系统内部用户登录次数key一致
	public static String cbbank_bank_user_login_client_id_username(String client_id,String username) {
		return MessageFormat.format("cbbank:bank_user_login:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING),ObjectUtils.toString(username, DEFAULT_PADDING));
	}
	//银行机构等级<key结构cbbank:bank_level_role:client_id:org_level>
	public static String cbbank_bank_level_role_client_id_org_level(String client_id,String org_level){
		return MessageFormat.format("cbbank:bank_level_role:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_level, DEFAULT_PADDING));
	}
	//银行机构用户<key结构cbbank:bank_level_role:client_id:org_code:username>
	public static String cbbank_bank_level_role_client_id_org_code_username(String client_id,String org_code,String username){
		return MessageFormat.format("cbbank:bank_level_role:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_code, DEFAULT_PADDING), ObjectUtils.toString(username, DEFAULT_PADDING));
	}
	//银行联合登录/银行用户登录key分别为token与userid的value值对应关系<key结构cbbank:bank_token:token或cbbank:bank_token:userid>
	public static String cbbank_bank_token_token(String key){
		return MessageFormat.format("cbbank:bank_token:{0}", ObjectUtils.toString(key, DEFAULT_PADDING));
	}
	//机构信息：商户id对应的1-2-3-4级关系<key结构cbbank:merchant_org_level:merchant_id>
	public static String cbbank_merchant_org_level_merchant_id(String merchant_id) {
		return MessageFormat.format("cbbank:merchant_org_level:{0}", ObjectUtils.toString(merchant_id, DEFAULT_PADDING));
	}
	//根据client_id+org_code查询机构信息<key结构cbbank:org:client_id_org_code>
	public static String cbbank_org_client_id_org_code(String client_id,String org_code) {
		return MessageFormat.format("cbbank:org:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_code, DEFAULT_PADDING));
	}
	//根据client_id+outlet_id查询机构信息<key结构cbbank:outlet_org:client_id:outlet_id>
	public static String cbbank_outlet_org_client_id_outlet_id(String client_id,String outlet_id) {
		return MessageFormat.format("cbbank:outlet_org:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(outlet_id, DEFAULT_PADDING));
	}
	//根据client_id+merchant_id查询机构信息<key结构cbbank:merchant_org:client_id:merchant_id>
	public static String cbbank_merchant_org_client_id_merchant_id(String client_id,String merchant_id) {
		return MessageFormat.format("cbbank:merchant_org:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_id, DEFAULT_PADDING));
	}
	//商户审核配置<key结构cbbank:merchant_audit:client_id:org_level:type>
	public static String cbbank_merchant_audit_client_id_org_level_type(String client_id,String org_level, String type) {
		return MessageFormat.format("cbbank:merchant_audit:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_level, DEFAULT_PADDING),ObjectUtils.toString(type, DEFAULT_PADDING));
	}
	//商品审核配置<key结构cbbank:product_audit:client_id:org_level:product_type>
	public static String cbbank_product_audit_client_id_org_level_product_type(String client_id,String org_level, String product_type) {
		return MessageFormat.format("cbbank:product_audit:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_level, DEFAULT_PADDING),ObjectUtils.toString(product_type, DEFAULT_PADDING));
	}
	
	
	
	
	/* 银行模块 */
	
	
	
	
	/* 商户门店模块 */
//	public static String cbank_preaudit_merchant_count_client_id_org_code(String client_id, String org_code) {
//		return MessageFormat.format("cbbank:preaudit:merchant:count:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_code, DEFAULT_PADDING));
//	}
	public static String cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id) {
		return MessageFormat.format("cbbank:merchant:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_id, DEFAULT_PADDING));
	}
	
	public static String cbbank_bank_merchant_client_id_org_code(String client_id, String org_code) {
		return MessageFormat.format("cbbank:bank_merchant:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_code, DEFAULT_PADDING));
	}
	
	public static String cbbank_bank_outlet_client_id_org_code(String client_id, String org_code) {
		return MessageFormat.format("cbbank:bank_outlet:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_code, DEFAULT_PADDING));
	}

	public static String cbbank_area_merchant_client_id_area_id(String client_id, Long area_id) {
		return MessageFormat.format("cbbank:area_merchant:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(area_id, DEFAULT_PADDING));
	}

	public static String cbbank_top_merchant_client_id(String client_id) {
		return MessageFormat.format("cbbank:top_merchant:{0}", ObjectUtils.toString(client_id, DEFAULT_PADDING));
	}
	
//	public static String cbbank_outlet_client_id_merchant_id_outlet_id(String clientId, String merchantId, String outletId) {
//		// TODO Auto-generated method stub
//		return MessageFormat.format("cbbank:outlet:{0}:{1}:{2}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(merchantId, DEFAULT_PADDING), ObjectUtils.toString(outletId, DEFAULT_PADDING));
//	}
	//个人h5用到
	public static String cbbank_merchant_category_h5_client_id_category_id(String client_id, Long category_id,Long area_id) {
		return MessageFormat.format("cbbank:merchant_category_h5:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING),ObjectUtils.toString(area_id, DEFAULT_PADDING));
	}
	
	public static String cbbank_merchant_category_detail_client_id_category_id(String client_id, Long category_id) {
		return MessageFormat.format("cbbank:merchant_category_detail:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING));
	}
	
	public static String cbbank_merchant_category_all_client_id_merchant_category_id(String client_id, Long merchant_category_id) {
		return MessageFormat.format("cbbank:merchant_category_all:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_category_id, DEFAULT_PADDING));
	}
	
	public static String cbbank_merchant_type_merchant_type_id(String clientId) {
		return MessageFormat.format("cbbank:merchant_type:{0}", ObjectUtils.toString(clientId, DEFAULT_PADDING));
	}
	
	public static String cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String outlet_id) {
		return MessageFormat.format("cbbank:merchant_outlet_account:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_id, DEFAULT_PADDING), ObjectUtils.toString(outlet_id, DEFAULT_PADDING));
	}
	
	public static String cbbank_fft_account_client_id(String client_id) {
		return MessageFormat.format("cbbank:fft_account:{0}", ObjectUtils.toString(client_id, DEFAULT_PADDING));
	}
	
	public static String cbbank_merchant_outlet_client_id_merchant_id(String client_id, String merchant_id) {
		return MessageFormat.format("cbbank:merchant_outlet:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_id, DEFAULT_PADDING));
	}
	
	public static String cbbank_outlet_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String oulet_id) {
		return MessageFormat.format("cbbank:outlet:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_id, DEFAULT_PADDING), ObjectUtils.toString(oulet_id, DEFAULT_PADDING));
	}
	
	/* 附近搜索门店  同步锁(个人h5用到) */
	public static String cbbank_near_outlet_lock_index_condition(int index, String condition) {
		return MessageFormat.format("cbbank:near_outlet_lock:{0}:{1}", String.valueOf(index), ObjectUtils.toString(condition, DEFAULT_PADDING));
	}
	/* 附近搜索门店(个人h5用到) */
	public static String cbbank_near_outlet_index_condition(int index, String condition) {
		return MessageFormat.format("cbbank:near_outlet:{0}",  ObjectUtils.toString(condition, DEFAULT_PADDING));
	}
	/* 分页查询最热门店详情   同步锁(个人h5用到) */
	public static String cbbank_hottest_outlet_lock_index_condition(int index, String condition) {
		return MessageFormat.format("cbbank:hottest_outlet_lock:{0}:{1}", String.valueOf(index), ObjectUtils.toString(condition, DEFAULT_PADDING));
	}
	/* 分页查询最热门店详情(个人h5用到) */
	public static String cbbank_hottest_outlet_condition(int index,String condition) {
		return MessageFormat.format("cbbank:hottest_outlet:{0}:{1}", String.valueOf(index), ObjectUtils.toString(condition, DEFAULT_PADDING));
	}
	
	public static String cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String oulet_id) {
		return MessageFormat.format("cbbank:merchant_outlet_image:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_id, DEFAULT_PADDING), ObjectUtils.toString(oulet_id, DEFAULT_PADDING));
	}
	
	public static String cbbank_outlet_area_distance_degree(long distance) {
		return MessageFormat.format("cbbank:outlet_{0}:degree", ObjectUtils.toString(distance, DEFAULT_PADDING));
	}
	
	public static String cbbank_outlet_area_distance_client_id_longitude_latitude(long distance, String client_id, double longitude, double latitude) {
		return MessageFormat.format("cbbank:outlet_area_{0}:{1}:{2}:{3}", ObjectUtils.toString(distance, DEFAULT_PADDING), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(longitude, DEFAULT_PADDING), ObjectUtils.toString(latitude, DEFAULT_PADDING));
	}
	
	public static String cbbank_merchant_outlet_user_client_id_merchant_id_user_id(String client_id, String merchant_id, long user_id){
		return MessageFormat.format("cbbank:merchant_outlet_user:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_id, DEFAULT_PADDING), ObjectUtils.toString(user_id, DEFAULT_PADDING));
	}
	
	public static String cbbank_merchant_token_token_value(String key){
		return MessageFormat.format("cbbank:merchant_token:{0}", ObjectUtils.toString(key, DEFAULT_PADDING));
	}
	
	public static String cbbank_merchant_outlet_user_login_client_id_merchant_id_merchant_user_id(String client_id, String merchant_id, long merchant_user_id){
		return MessageFormat.format("cbbank:merchant_outlet_user_login:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_user_id, DEFAULT_PADDING));
	}
	
	/** 客户端所有的虚拟门店 新增商品修改商品配送方式为自提时有提货网点时用到*/
	public static String cbbank_bank_outlet_client_id(String client_id) {
		return MessageFormat.format("cbbank:client:bank_outlet:{0}", ObjectUtils.toString(client_id, DEFAULT_PADDING));
	}
		
	/* 商户门店模块 */
	
	
	
	
	/* 商品模块 */
	
	/*个人H5类目营销里的特惠商品列表*/
    /*个人H5特惠商品列表符合条件的商户的商品*/
	
	/*个人H5类目营销里的特惠商品列表--按距离排序*/
    /*个人H5特惠商品列表一个经纬度的符合条件的商户的商品*/
    public static String cbbank_group_product_client_id_category_id_lat_con_product_name_dis(String client_id, Long area_id, String sort, Long category_id, Double lat, Double con, String product_name, Double distance) {
        return MessageFormat.format("cbbank:group_product:groupProducts:{0}:{1}:{2}:{3}:{4}:{5}:{6}:{7}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(area_id, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(lat, DEFAULT_PADDING), ObjectUtils.toString(con, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(distance, DEFAULT_PADDING));
    }
    public static String cbbank_group_product_lock_client_id_category_id_lat_con_product_name_dis(String client_id, Long area_id, String sort, Long category_id, Double lat, Double con, String product_name, Double distance) {
        return MessageFormat.format("cbbank:group_product:groupProducts:lock:{0}:{1}:{2}:{3}:{4}:{5}:{6}:{7}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(area_id, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(lat, DEFAULT_PADDING), ObjectUtils.toString(con, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(distance, DEFAULT_PADDING));
    }
    
    /*个人H5特惠商品列表一个经纬度的有符合条件的商品的商户id*/
    public static String cbbank_group_product_merchant_id_client_id_category_id_lat_con_product_name_dis(String client_id, Long area_id, Long category_id, Double lat, Double con, String product_name, Double distance) {
        return MessageFormat.format("cbbank:group_product:merchant_ids:{0}:{1}:{2}:{3}:{4}:{5}:{6}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(area_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(lat, DEFAULT_PADDING), ObjectUtils.toString(con, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(distance, DEFAULT_PADDING));
    }
    public static String cbbank_group_product_merchant_id_lock_client_id_category_id_lat_con_product_name_dis(String client_id, Long area_id, Long category_id, Double lat, Double con, String product_name, Double distance) {
        return MessageFormat.format("cbbank:group_product:merchant_ids:lock:{0}:{1}:{2}:{3}:{4}:{5}:{6}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(area_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(lat, DEFAULT_PADDING), ObjectUtils.toString(con, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(distance, DEFAULT_PADDING));
    }
    /*个人H5类目营销里的特惠商品列表--按距离排序*/
    
    /*个人H5类目营销里的特惠商品列表--其他排序:销量排序,价格排序,折扣排序*/
    /*个人H5特惠商品列表一个地区的符合条件的商品*/
    public static String cbbank_group_product_index_client_id_area_id_category_id_sort_product_name(int index,String client_id, Long area_id, Long category_id, String sort, String product_name) {
        return MessageFormat.format("cbbank:group_product:groupProducts:{0}:{1}:{2}:{3}:{4}:{5}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(area_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING));
    }
    public static String cbbank_group_product_lock_index_client_id_area_id_category_id_sort_product_name(int index,String client_id, Long area_id, Long category_id, String sort, String product_name) {
        return MessageFormat.format("cbbank:group_product:groupProducts:lock:{0}:{1}:{2}:{3}:{4}:{5}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(area_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING));
    }
    /*个人H5类目营销里的特惠商品列表--其他排序:销量排序,价格排序,折扣排序*/
    /*个人H5类目营销里的特惠商品列表*/
    
    
	/*个人H5特惠商品列表*/
    /*个人H5特惠商品列表一个经纬度的商户*/
    public static String cbbank_product_merchant_client_id_lat_con(String client_id, Long areaId, Double lat, Double con, Double distance) {
        return MessageFormat.format("cbbank:product_merchant:outlet_merchantids:{0}:{1}:{2}:{3}:{4}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING), ObjectUtils.toString(lat, DEFAULT_PADDING), ObjectUtils.toString(con, DEFAULT_PADDING), ObjectUtils.toString(distance, DEFAULT_PADDING));
    }
    public static String cbbank_product_merchant_lock_client_id_lat_con(String client_id, Long areaId, Double lat, Double con, Double distance) {
        return MessageFormat.format("cbbank:product_merchant:outlet_merchantids:lock:{0}:{1}:{2}:{3}:{4}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING), ObjectUtils.toString(lat, DEFAULT_PADDING), ObjectUtils.toString(con, DEFAULT_PADDING), ObjectUtils.toString(distance, DEFAULT_PADDING));
    }
    
    /*个人H5特惠商品列表符合条件的商户的商品*/
    public static String cbbank_product_merchant_client_id_category_id_product_name(String client_id, Long category_id, String product_name) {
        return MessageFormat.format("cbbank:product_merchant:product_merchantids:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING));
    }
    public static String cbbank_product_merchant_lock_client_id_category_id_product_name(String client_id, Long category_id, String product_name) {
        return MessageFormat.format("cbbank:product_merchant:product_merchantids:lock:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING));
    }
    
    /*个人H5特惠商品列表一个经纬度的商户门店与符合条件的商户交集*/
    public static String cbbank_group_product_merchant_client_id_category_id_lat_con_product_name_dis(String client_id, Long areaId, Long category_id, Double lat, Double con, String product_name, Double distance) {
        return MessageFormat.format("cbbank:group_product_merchant:merchantids:{0}:{1}:{2}:{3}:{4}:{5}:{6}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(lat, DEFAULT_PADDING), ObjectUtils.toString(con, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(distance, DEFAULT_PADDING));
    }
    public static String cbbank_group_product_merchant_lock_client_id_category_id_lat_con_product_name_dis(String client_id, Long areaId, Long category_id, Double lat, Double con, String product_name, Double distance) {
        return MessageFormat.format("cbbank:group_product_merchant:merchantids:lock:{0}:{1}:{2}:{3}:{4}:{5}:{6}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(lat, DEFAULT_PADDING), ObjectUtils.toString(con, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(distance, DEFAULT_PADDING));
    }
    
    /*个人H5根据商户id去查该商户id下的特惠商品列表时缓存一个经纬度的商户门店信息*/
    public static String cbbank_group_product_merchant_outlet_merchant_id_client_id_lat_con(String merchant_id, String client_id, Long areaId, Double lat, Double con) {
        return MessageFormat.format("cbbank:group_product:merchant_outlet:{0}:{1}:{2}:{3}:{4}", ObjectUtils.toString(merchant_id, DEFAULT_PADDING), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING), ObjectUtils.toString(lat, DEFAULT_PADDING), ObjectUtils.toString(con, DEFAULT_PADDING));
    }
    /*个人H5特惠商品列表*/
    
    
	/*个人H5团购商品*/
	public static String cbbank_product_group_h5_index_client_id_category_id_product_name_areaId_sort(int index,String client_id, String category_id,String product_name,String areaId,String sort) {
		return MessageFormat.format("cbbank:product_group:h5:{0}:{1}:{2}:{3}:{4}:{5}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
	}
	/*个人H5团购商品同步锁*/
	public static String cbbank_product_group_h5_lock_index_client_id_category_id_product_name_areaId_sort(int index,String client_id, String category_id,String product_name,String areaId,String sort) {
        return MessageFormat.format("cbbank:product_group:h5:lock:{0}:{1}:{2}:{3}:{4}:{5}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
	/*个人H5团购商品按商户查询*/
	public static String cbbank_product_group_merchant_h5_index_client_id_category_id_product_name_areaId_merchantId_sort(int index,String client_id, String category_id,String product_name,String areaId,String merchantId,String sort) {
		return MessageFormat.format("cbbank:product_group_merchant:h5:{0}:{1}:{2}:{3}:{4}:{5}:{6}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING), ObjectUtils.toString(merchantId, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
	}
	/*个人H5团购商品按商户查询同步锁*/
	public static String cbbank_product_group_merchant_h5_lock_index_client_id_category_id_product_name_areaId_merchantId_sort(int index,String client_id, String category_id,String product_name,String areaId,String merchantId,String sort) {
        return MessageFormat.format("cbbank:product_group_merchant:h5:lock:{0}:{1}:{2}:{3}:{4}:{5}:{6}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING), ObjectUtils.toString(merchantId, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
	/*个人h5预售商品*/
    public static String cbbank_product_presell_h5_index_client_id_presellNum_areaId_product_name_sort(int index, String client_id, String presellNum,String areaId,String product_name,String sort) {
        return MessageFormat.format("cbbank:product_presell:h5:{0}:{1}:{2}:{3}:{4}:{5}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(presellNum, DEFAULT_PADDING),  ObjectUtils.toString(areaId, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING),ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
    /*个人h5预售商品同步锁*/
    public static String cbbank_product_presell_h5_lock_index_client_id_presellNum_areaId_product_name_sort(int index, String client_id, String presellNum,String areaId,String product_name, String sort) {
        return MessageFormat.format("cbbank:product_presell:h5:lock:{0}:{1}:{2}:{3}:{4}:{5}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(presellNum, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING),ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
    
	/*个人H5名优特惠商品*/
    public static String cbbank_product_special_h5_index_client_id_product_name_sort(int index,String client_id,String product_name,String sort) {
        return MessageFormat.format("cbbank:product_special:h5:{0}:{1}:{2}:{3}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
    /*个人H5名优特惠商品同步锁*/
    public static String cbbank_product_special_h5_lock_index_client_id_product_name_sort(int index,String client_id,String product_name,String sort) {
        return MessageFormat.format("cbbank:product_special:h5:lock:{0}:{1}:{2}:{3}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
    
    /*个人h5在线积分兑换商品*/
    public static String cbbank_product_onlinePoint_h5_index_client_id_sort(int index,String client_id,String sort) {
        return MessageFormat.format("cbbank:product_onlinePoint:h5:{0}:{1}:{2}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
    /*个人h5在线积分兑换商品同步锁*/
    public static String cbbank_product_onlinePoint_h5_lock_index_client_id_sort(int index,String client_id,String sort) {
        return MessageFormat.format("cbbank:product_onlinePoint:h5:lock:{0}:{1}:{2}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
	
	/*个人h5线下积分兑换商品*/
    public static String cbbank_product_dotgift_h5_index_client_id_sort(int index,String client_id,String sort) {
        return MessageFormat.format("cbbank:product_dotgift:h5:{0}:{1}:{2}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
    /*个人h5线下积分兑换商品同步锁*/
    public static String cbbank_product_dotgift_h5_lock_index_client_id_sort(int index,String client_id,String sort) {
        return MessageFormat.format("cbbank:product_dotgift:h5:lock:{0}:{1}:{2}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
    /*商品评论同步锁*/
    public static String cbbank_product_add_comment_lock_client_id_product_id_order_id(String client_id,String product_id,String order_id) {
        return MessageFormat.format("cbbank:product_add_comment:lock:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(product_id, DEFAULT_PADDING), ObjectUtils.toString(order_id, DEFAULT_PADDING));
    }
    
    /*个人h5精品商城商品*/
    public static String cbbank_product_boutique_h5_index_client_id_category_id_product_name_good_flag_sort(int index, String client_id, long category_id, String product_name, String good_flag, String sort) {
        return MessageFormat.format("cbbank:product_boutique:h5:{0}:{1}:{2}:{3}:{4}:{5}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(good_flag, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
    /*个人h5精品商城商品同步锁*/
    public static String cbbank_product_boutique_h5_lock_index_client_id_category_id_product_name_good_flag_sort(int index, String client_id, long category_id, String product_name, String good_flag, String sort) {
        return MessageFormat.format("cbbank:product_boutique:h5:lock:{0}:{1}:{2}:{3}:{4}:{5}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(category_id, DEFAULT_PADDING), ObjectUtils.toString(product_name, DEFAULT_PADDING), ObjectUtils.toString(good_flag, DEFAULT_PADDING), ObjectUtils.toString(sort, DEFAULT_PADDING));
    }
    /*个人h5精品商城商品每条商品对应的缓存key值*/
    public static String cbbank_product_boutique_h5_keys_product_id(String product_id) {
        return MessageFormat.format("cbbank:product_boutique:h5:keys:product_id:{0}", ObjectUtils.toString(product_id, DEFAULT_PADDING));
    }
    /*个人h5精品商城商品每个客户端下的缓存key值*/
    public static String cbbank_product_boutique_h5_keys_client_id(String client_id) {
        return MessageFormat.format("cbbank:product_boutique:h5:keys:client_id:{0}", ObjectUtils.toString(client_id, DEFAULT_PADDING));
    }
    
	//商品基本信息cbbank:product:client_id:merchant_id:product_id
	public static String cbbank_product_client_id_merchant_id_product_id(String client_id, String merchant_id,String product_id) {
		return MessageFormat.format("cbbank:product:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_id, DEFAULT_PADDING), ObjectUtils.toString(product_id, DEFAULT_PADDING));
	}
	//存储该商品在每个门店最大提货数量cbbank:product_presell_max:product_id
	public static String cbbank_product_presell_max_product_id(String product_id) {
        return MessageFormat.format("cbbank:product_presell_max:{0}", ObjectUtils.toString(product_id, DEFAULT_PADDING));
    }
	//存储该商品在该门店已经提货数量cbbank:product_presell_token:org_code:product_id
	public static String cbbank_product_presell_token_org_code_product_id(String client_id, String org_code,String product_id) {
		return MessageFormat.format("cbbank:product_presell_token:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING),ObjectUtils.toString(org_code, DEFAULT_PADDING), ObjectUtils.toString(product_id, DEFAULT_PADDING));
	}
	
	
	// 用户订单限购数量统计
	public static String cbbank_product_limit_client_id_member_code_product_id(String clientId, long memberCode,String productId) {
		return MessageFormat.format("cbbank:product_limit:{0}:{1}:{2}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(memberCode, DEFAULT_PADDING), ObjectUtils.toString(productId, DEFAULT_PADDING));
	}
	
	// 商品库存
	public static String cbbank_product_store_client_id_merchant_id_product_id(String client_id, String merchant_id,String product_id) {
		return MessageFormat.format("cbbank:product_store:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_id, DEFAULT_PADDING), ObjectUtils.toString(product_id, DEFAULT_PADDING));
	}
	
	//商品销售数量
	public static String cbbank_product_sellcount_client_id_merchant_id_product_id(String client_id, String merchant_id,String product_id) {
		return MessageFormat.format("cbbank:product_sellcount:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(merchant_id, DEFAULT_PADDING), ObjectUtils.toString(product_id, DEFAULT_PADDING));
	}
	//待审核预售商品数cbbank:preaudit:presell:count
//	public static String cbbank_preaudit_presell_count_client_id_org_code(String client_id, String org_code) {
//        return MessageFormat.format("cbbank:preaudit:presell:count:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_code, DEFAULT_PADDING));
//    }
	//待审核团购商品数cbbank:preaudit:group:count
//	public static String cbbank_preaudit_group_count_client_id_org_code(String client_id, String org_code) {
//        return MessageFormat.format("cbbank:preaudit:group:count:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_code, DEFAULT_PADDING));
//    }
	//待审核名优特惠cbbank:preaudit:mingyou:count
//	public static String cbbank_preaudit_mingyou_count_client_id_org_code(String client_id, String org_code) {
//        return MessageFormat.format("cbbank:preaudit:mingyou:count:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_code, DEFAULT_PADDING));
//    }
	//待审核积分兑换商品(网店礼品)cbbank:preaudit:duihuan:count
//	public static String cbbank_preaudit_duihuan_count_client_id_org_code(String client_id, String org_code) {
//        return MessageFormat.format("cbbank:preaudit:duihuan:count:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_code, DEFAULT_PADDING));
//    }
	//待审核秒杀商品cbbank:preaudit:seckill:count
//	public static String cbbank_preaudit_seckill_count_client_id_org_code(String client_id, String org_code) {
//        return MessageFormat.format("cbbank:preaudit:seckill:count:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(org_code, DEFAULT_PADDING));
//    }
	
	/* 商品分类模块 */
	//商品分类基本信息cbbank:product_category:client_id:product_category_id
	public static String cbbank_product_category_client_id_product_category_id(String client_id, Long product_category_id) {
        return MessageFormat.format("cbbank:product_category:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(product_category_id, DEFAULT_PADDING));
    }
	public static String cbbank_product_category_all_client_id_product_category_id(String client_id, Long product_category_id) {
		return MessageFormat.format("cbbank:product_category_all:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(product_category_id, DEFAULT_PADDING));
	}
	
	
	/* 查询商品条件下该商品分类是否有商品数据 */
	public static String cbbank_product_category_product_client_id_product_category_id_type_area_id(String client_id, Long product_category_id, String type, Long area_id) {
        return MessageFormat.format("cbbank:product_category:product:{0}:{1}:{2}:{3}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(product_category_id, DEFAULT_PADDING), ObjectUtils.toString(type, DEFAULT_PADDING), ObjectUtils.toString(area_id, DEFAULT_PADDING));
    }
	public static String cbbank_product_category_product_lock_client_id_product_category_id_type_area_id(String client_id, Long product_category_id, String type, Long area_id) {
        return MessageFormat.format("cbbank:product_category:product:lock:{0}:{1}:{2}:{3}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(product_category_id, DEFAULT_PADDING), ObjectUtils.toString(type, DEFAULT_PADDING), ObjectUtils.toString(area_id, DEFAULT_PADDING));
    }
	
	/* 面对面商品二维码 */
    public static String cbbank_outlet_product_clientId_qrcode(String clientId,String qrCode) {
        return MessageFormat.format("cbbank:outlet:product:qrcode:{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(qrCode, DEFAULT_PADDING));
    }
	
	
	/* 交易模块 */
  	public static String cbbank_time_payment_key(){
  		return "cbbank:time_payment";
  	}
//	public static String cbbank_payment_payment_id(String paymentId){
//		return MessageFormat.format("cbbank:payment:payment_id:{0}", ObjectUtils.toString(paymentId, DEFAULT_PADDING));
//	}
//	public static String cbbank_payemnt_order_id(String orderId){
//		return MessageFormat.format("cbbank:payment:order_id:{0}", ObjectUtils.toString(orderId, DEFAULT_PADDING));
//	}
	
	//订单表缓存    key为cbbank:order:client_id:order_id
	/*public static String cbbank_order_client_id_order_id(String clientId, String orderId) {
		return MessageFormat.format("cbbank:order:{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(orderId, DEFAULT_PADDING));
	}*/
	//订单-创建时间缓存    key为cbbank:time_order
	public static String cbbank_time_order_key() {
		return "cbbank:time_order";
	}
	//订单-创建时间缓存   value
	public static String cbbank_time_order_value(String clientId,String orderId) {
		return MessageFormat.format("{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(orderId, DEFAULT_PADDING));
	}
	
	public static String cbank_good_ip_port(){
		return "cbbank:product_module";
	}
	
	//用户订单 key为cbbank:history_vip:client_id:member_code
	public static String cbbank_history_vip_client_id_member_code_key(String clientId, Long memberCode){
		return MessageFormat.format("cbbank:history_vip:{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(memberCode, DEFAULT_PADDING));
	}
	
	//用户赠送积分缓存，返回    0： 未赠送（赠送失败 ），1： 已赠送（赠送成功）
	public static String cbbank_give_points_client_id_member_code_product_id(String clientId, Long memberCode,String productId){
		return MessageFormat.format("cbbank:give_points:{0}:{1}:{2}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(memberCode, DEFAULT_PADDING), ObjectUtils.toString(productId, DEFAULT_PADDING));
	}
	
	//用户VIP订单购买（开通VIP）记录，返回    0： 未购买/未开通（购买失败 /开通失败），1： 已购买/已开通（购买成功 /开通成功）
	public static String cbbank_vip_order_client_id_member_code(String clientId, Long memberCode){
		return MessageFormat.format("cbbank:vip_order:{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(memberCode, DEFAULT_PADDING));
	}
	
	/* 交易模块 */
	
	
	
	/* 系统外围模块 */
	
	/* 地区表 */
	public static String cbbank_area_client_id_area_id(Long area_id) {
		return MessageFormat.format("cbbank:area:{0}", ObjectUtils.toString(area_id, DEFAULT_PADDING));
	}
	public static String cbbank_area_area_id(Long area_id) {
		return MessageFormat.format("cbbank:area:{0}", ObjectUtils.toString(area_id, DEFAULT_PADDING));
	}
	
	/* 物流公司 */
	public static String cbbank_deliver_company_client_id_delivery_corp_id(String client_id, Long delivery_corp_id) {
		return MessageFormat.format("cbbank:deliver_company:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(delivery_corp_id, DEFAULT_PADDING));
	}
	
	/* 短信模板 */
	public static String cbbank_sms_template_client_id_sms_type(String client_id, int sys_type) {
		return MessageFormat.format("cbbank:sms_template:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(sys_type, DEFAULT_PADDING));
	}
	
	/* 活动模板 */
	public static String cbbank_activities_client_id_activities_id(String client_id, Long activities_id) {
		return MessageFormat.format("cbbank:activities:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(activities_id, DEFAULT_PADDING));
	}
	
	/* 广告位表 */
	public static String cbbank_adpos_client_id_ad_position_id(String client_id, Long ad_position_id) {
		return MessageFormat.format("cbbank:adpos:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(ad_position_id, DEFAULT_PADDING));
	}
	public static String cbbank_adLocation_ad_location_id(Long ad_location_id){
		return MessageFormat.format("cbbank:adLocation:{0}", ObjectUtils.toString(ad_location_id, DEFAULT_PADDING));
	}
	/* 广告 */
	public static String cbbank_advertising_client_id_advertising_id(String client_id, Long advertising_id){
		return MessageFormat.format("cbbank:advertising:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(advertising_id, DEFAULT_PADDING));
	}
	/* 终端启动页 */
	public static String cbbank_terminal_start_client_id_app_type_terminal_type(String client_id, String app_type, String terminal_type){
		return MessageFormat.format("cbbank:terminal_start:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(app_type, DEFAULT_PADDING), ObjectUtils.toString(terminal_type, DEFAULT_PADDING));
	}
	/* 系统外围模块 */
	
	/* 秒杀模块 */
	/** 秒杀商品配置表 */
	public static String cbbank_seckill_product_client_id_product_id(String client_id, String product_id) {
		return MessageFormat.format("cbbank:seckill_product:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(product_id, DEFAULT_PADDING));
	}
	/** 用户是否参加了秒杀 */
	public static String cbbank_seckill_join_member_code_product_id(String member_code, String product_id) {
		return MessageFormat.format("cbbank:seckill_join:{0}:{1}", ObjectUtils.toString(member_code, DEFAULT_PADDING), ObjectUtils.toString(product_id, DEFAULT_PADDING));
	}
	/** 秒杀下单结果 */
	public static String cbbank_seckill_res_req_id(String req_id){
		return MessageFormat.format("cbbank:seckill_res:{0}", ObjectUtils.toString(req_id, DEFAULT_PADDING));
	}
	/** 秒杀用户购买数量 */
	public static String cbbank_seckill_memcnt_member_code_product_id_end_date(String member_code, String product_id, String endDateYYMMdd){
		return MessageFormat.format("cbbank:seckill_memcnt:{0}:{1}:{2}", ObjectUtils.toString(member_code, DEFAULT_PADDING), ObjectUtils.toString(product_id, DEFAULT_PADDING), ObjectUtils.toString(endDateYYMMdd, DEFAULT_PADDING));
	}
	/** 秒杀商品库存 */
	public static String cbbank_seckill_product_store_client_id_product_id(String client_id, String product_id) {
		return MessageFormat.format("cbbank:seckill_product_store:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(product_id, DEFAULT_PADDING));
	}
	/** 秒杀商品的销售数量 */
	public static String cbbank_seckill_product_soldcnt_client_id_product_id(String client_id, String product_id){
		return MessageFormat.format("cbbank:seckill_product:soldcnt:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(product_id, DEFAULT_PADDING));
	}
	/** 秒杀商品队列的数量 */
	public static String cbbank_seckill_product_queue_count(){
		return "cbbank:seckill_product:queue:count";
	}
	/** 秒杀受理队列worker限速 */
	public static String cbbank_seckill_worker_count_persecond_local_ip_time(String local_ip, String time){
		return MessageFormat.format("cbbank:seckill_worker:count:persecond:{0}:{1}", ObjectUtils.toString(local_ip, DEFAULT_PADDING), ObjectUtils.toString(time, DEFAULT_PADDING));
	}
	/** boss用户登录 */
	public static String cbbank_boos_user_login_user_id(long user_id){
		return MessageFormat.format("cbbank:boss_user:login:{0}", ObjectUtils.toString(user_id, DEFAULT_PADDING));
	}
	public static String cbbank_boss_user_token_token_value(String keyV){
		return MessageFormat.format("cbbank:boss_user_token:{0}", ObjectUtils.toString(keyV));
	}
	/* 秒杀模块 */
	
	
	/* VIP规则 每个客户端只有一条*/
    public static String cbbank_vip_product_client_id(String client_id) {
        return MessageFormat.format("cbbank:vip_product:{0}", ObjectUtils.toString(client_id, DEFAULT_PADDING));
    }
    /* VIP规则 */
    
	/*个人H5热词查询*/
	public static String cbbank_hotword_h5_index_client_id_areaId_type_categoryType_sort(int index,String client_id,String areaId,String type,String categoryType,String sort) {
		return MessageFormat.format("cbbank:hotword:h5:{0}:{1}:{2}:{3}:{4}:{5}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING),  
				ObjectUtils.toString(type, DEFAULT_PADDING),ObjectUtils.toString(categoryType, DEFAULT_PADDING),ObjectUtils.toString(sort, DEFAULT_PADDING));
	}
	/*个人H5热词查询同步锁*/
	public static String cbbank_hotword_h5_lock_index_client_id_areaId_type_categoryType_sort(int index,String client_id,String areaId,String type,String categoryType,String sort) {
		return MessageFormat.format("cbbank:hotword:h5:lock:{0}:{1}:{2}:{3}:{4}:{5}", String.valueOf(index), ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(areaId, DEFAULT_PADDING),  
				ObjectUtils.toString(type, DEFAULT_PADDING),ObjectUtils.toString(categoryType, DEFAULT_PADDING),ObjectUtils.toString(sort, DEFAULT_PADDING));
	}

	/* 审核流程缓存 */
	public static String cbbank_fallow_audit_process_client_id_process_id(String client_id, String process_id) {
		return MessageFormat.format("cbbank:fallow:audit:process:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(process_id, DEFAULT_PADDING));
	}
	/* 审核流程缓存 */

	/* 审核流程节点缓存 */
	public static String cbbank_fallow_audit_processnode_client_id_process_id_node_id(String client_id, String process_id, String node_id) {
		return MessageFormat.format("cbbank:fallow:audit:processnode:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(process_id, DEFAULT_PADDING), ObjectUtils.toString(node_id, DEFAULT_PADDING));
	}
	/* 审核流程节点缓存 */

	/* 审核lock */
	public static String cbbank_fallow_audit_instance_lock_client_id_instance_id(String client_id, String instance_id) {
		return MessageFormat.format("cbbank:fallow:audit:instance:lock:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(instance_id, DEFAULT_PADDING));
	}
	/* 审核lock */
	
    /* 审核mq通知 */
    public static String cbbank_fallow_audit_mq_audit_type(String audit_type) {
    	return MessageFormat.format("cbbank:fallow:audit:mq:{0}", ObjectUtils.toString(audit_type, DEFAULT_PADDING));
    }
    /* 审核mq通知 */
    
	/* 角色资源关系同步任务是否执行 */
    public static String cbbank_finity_role_resource_do_task() {
        return "cbbank:finity_role_resource_do_task";
    }
    public static String cbbank_finity_user_resource_do_task_merchant() {
        return "cbbank:finity_user_resource_do_task_merchant";
    }
    /*角色资源数据*/
    public static String cbbank_finity_role_resource_relation(Long role){ 
    	 return MessageFormat.format("cbbank:cbbank_finity_role_resource_relation:{0}", ObjectUtils.toString(role));
    }

	/* boss-推荐活动标签 */
    public static String cbbank_recommend_activity_tag_clientId_id(String clientId, Long id){
    	return MessageFormat.format("cbbank:recommend:activity:tag:{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(id, DEFAULT_PADDING));

    }

    
    /* boss-推荐活动关联 */
    public static String cbbank_weight_activity_tag_clientId_id(String clientId, Long id){
    	return MessageFormat.format("cbbank:weight:activity:tag:{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(id, DEFAULT_PADDING));
    }
    
    /* boss-推荐活动类型缓存所有活动ID */
    public static String cbbank_recommend_activity_tag_clientId_activityType(String clientId, String activityType){
    	return MessageFormat.format("cbbank:recommend:activity:tag:{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(activityType, DEFAULT_PADDING));
    }
    
    /** 商圈标签 */
    //商圈标签模块<key结构cbbank:function_module:client_id:type>
  	public static String cbbank_business_zone_tag_client_id_id(String client_id,String id) {
  		return MessageFormat.format("cbbank:business_zone_tag:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING),ObjectUtils.toString(id, DEFAULT_PADDING));
  	}
  	//商圈标签模块<key结构cbbank:function_module:client_id>
  	public static String cbbank_business_zone_tag_client_id(String client_id) {
  		return MessageFormat.format("cbbank:business_zone_tag:{0}", ObjectUtils.toString(client_id, DEFAULT_PADDING));
  	}
  	//活动编号客户端锁
  	public static String cbbank_activity_no_lock_client_id_type(String client_id, String activity_type) {
  		return MessageFormat.format("cbbank:activity_no_lock:{0}:{1}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(activity_type, DEFAULT_PADDING));
  	}
  	//活动编辑客户端锁
  	public static String cbbank_activity_no_lock_client_id_type_activity_no(String client_id, String activity_type, String activity_no) {
  		return MessageFormat.format("cbbank:activity_no_lock:{0}:{1}:{2}", ObjectUtils.toString(client_id, DEFAULT_PADDING), ObjectUtils.toString(activity_type, DEFAULT_PADDING), ObjectUtils.toString(activity_no, DEFAULT_PADDING));
  	}
  	
    
    /** 营销模块***start*/
    
    // 商品id（单个）对应下面的活动id（多个）
 	public static String cbbank_active_product_product_id (String productId) {
 		return MessageFormat.format("cbbank:active_product:{0}", ObjectUtils.toString(productId, DEFAULT_PADDING));	
 	}
 	
 	// 活动id（单个）对应下面的商品id（多个）
 	public static String cbbank_active_product_info_active_id (String activeId) {
 		return MessageFormat.format("cbbank:active_product_info:{0}", ObjectUtils.toString(activeId, DEFAULT_PADDING));	
 	}
 	
 	 // 商户id（单个）对应下面的活动id（多个）
 	public static String cbbank_active_merchant_merchant_id (String merchantId) {
 		return MessageFormat.format("cbbank:active_merchant:{0}", ObjectUtils.toString(merchantId, DEFAULT_PADDING));	
 	}
 	
 	// 活动id（单个）对应下面的商户id（多个）
 	public static String cbbank_active_merchant_info_active_id (String activeId) {
 		return MessageFormat.format("cbbank:active_merchant_info:{0}", ObjectUtils.toString(activeId, DEFAULT_PADDING));	
 	}
 	
 	//满减细则表
	public static String cbbank_full_cut_active_client_id_active_id (String clientId,String activeId) {
 		return MessageFormat.format("cbbank:full_cut_active:{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING),ObjectUtils.toString(activeId, DEFAULT_PADDING));	
 	}
	
	//满减规则活动全局控制
	public static String cbbank_active_base_global_limit_active_id (String activeId) {
	 	return MessageFormat.format("cbbank:active_base_global_limit:{0}", ObjectUtils.toString(activeId, DEFAULT_PADDING));	
	}
		
	//满减规则活动每日限制
	public static String cbbank_active_global_limit_active_id (String activeId) {
	 	return MessageFormat.format("cbbank:active_global_limit:{0}", ObjectUtils.toString(activeId, DEFAULT_PADDING));	
	}
	
	//满减规则活动个人限制
	public static String cbbank_active_person_limit_active_id_member_code (String activeId,String memberCode) {
	 	return MessageFormat.format("cbbank:active_person_limit:{0}:{1}", ObjectUtils.toString(activeId, DEFAULT_PADDING),ObjectUtils.toString(memberCode, DEFAULT_PADDING));	
	}
	
	//满减规则活动个人cookie
	public static String cbbank_active_products_cookie_cookie_id (String cookieId) {
	 	return MessageFormat.format("cbbank:active_products_cookie:{0}", ObjectUtils.toString(cookieId, DEFAULT_PADDING));	
	}
		
	// 满减规则redis计数器
	public static String cbbank_active_products_cookie_time_cookie_id (String cookieId) {
		return MessageFormat.format("cbbank:active_products_cookie_time:{0}", ObjectUtils.toString(cookieId, DEFAULT_PADDING));	
	}
	
	// 满减规则活动缓存商品Set
	public static String cbbank_active_products_cookie_product_id_set (String cookieId) {
		return MessageFormat.format("cbbank:active_products_cookie_product_id_set:{0}", ObjectUtils.toString(cookieId, DEFAULT_PADDING));	
	}
	
	//代金券相关
	//会员验码失败(不存在)的次数
	public static String cbbank_vouchers_check_failure(Long memberCode) {
		 return MessageFormat.format("cbbank:vouchers_check_failure:{0}", ObjectUtils.toString(memberCode));
	}
	//代金券活动个人限制
	public static String cbbank_vouchers_person_limit_active_id_member_code (String activeId,String memberCode) {
	 	return MessageFormat.format("cbbank:vouchers_person_limit:{0}:{1}", ObjectUtils.toString(activeId, DEFAULT_PADDING),ObjectUtils.toString(memberCode, DEFAULT_PADDING));	
	}
	
 	//代金券细则表
	public static String cbbank_vouchers_client_id_active_id (String clientId,String activeId) {
 		return MessageFormat.format("cbbank:vouchers_active:{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING),ObjectUtils.toString(activeId, DEFAULT_PADDING));	
 	}
	
	//代金券活动全局控制
	public static String cbbank_vouchers_base_global_limit_active_id (String activeId) {
	 	return MessageFormat.format("cbbank:vouchers_base_global_limit:{0}", ObjectUtils.toString(activeId, DEFAULT_PADDING));	
	}
		
	//代金券活动每日限制
	public static String cbbank_vouchers_global_limit_active_id (String activeId) {
	 	return MessageFormat.format("cbbank:vouchers_global_limit:{0}", ObjectUtils.toString(activeId, DEFAULT_PADDING));	
	}

    
	// 代金券-支持的其他促销活动
	public static String cbbank_vouchers_active_sustain_client_id_active_id (String clientId, String activeId) {
		return MessageFormat.format("cbbank:vouchers_active_sustain:{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(activeId, DEFAULT_PADDING));	
	} 
    
	//注册送活动
	public static String cbbank_full_regist_active_client_id_active_id (String clientId, String activeId) {
		return MessageFormat.format("cbbank:regist_active:{0}:{1}", ObjectUtils.toString(clientId, DEFAULT_PADDING), ObjectUtils.toString(activeId, DEFAULT_PADDING));	
	} 
	
	//注册送活动全局控制
	public static String cbbank_register_base_global_limit_active_id (String activeId) {
	 	return MessageFormat.format("cbbank:register_base_global_limit:{0}", ObjectUtils.toString(activeId, DEFAULT_PADDING));	
	}	
	
	//注册送活动时间段控制
	public static String cbbank_register_global_limit_active_id(String activeId){
		return MessageFormat.format("cbbank:register_global_limit:{0}", ObjectUtils.toString(activeId, DEFAULT_PADDING));
	}
	
	// TODO 2016-07-01 00:00:00 去掉此方法
	// 券码使用次数
	public static String cbbank_vouchers_use_number_vouchers_id(String vouchersId){
		return MessageFormat.format("cbbank:vouchers_use_number:{0}", ObjectUtils.toString(vouchersId, DEFAULT_PADDING));
	}
	
	// TODO 2016-07-01 00:00:00 去掉此方法
	// 代金券使用人
	public static String cbbank_vouchers_users_vouchers_id(String vouchersId){
		return MessageFormat.format("cbbank:vouchers_users:{0}", ObjectUtils.toString(vouchersId, DEFAULT_PADDING));
	}
	
    /** 营销模块***end*/
	
	/***精品商城*/
	/* boss-供应商 (filed:merchant_name,address,phone,status,description) */
    public static String cbbank_provider_merchant_Id(String merchantId){
    	return MessageFormat.format("cbbank:provider:{0}", ObjectUtils.toString(merchantId, DEFAULT_PADDING));
    }

    
    /* 哪些客户端要校验会员白名单 */
    public static String cbbank_check_VIPWhiteList_client(){ 
    	return "cbbank:cbbank:check:VIPWhiteList:client";
    }

	/* 客户端的白名单会员编号(memberCode) */
    public static String cbbank_VIPWhiteList_client_id(String clientId){ 
    	return MessageFormat.format("cbbank:cbbank:VIPWhiteList:{0}", ObjectUtils.toString(clientId, DEFAULT_PADDING));
    }
    
    
}
