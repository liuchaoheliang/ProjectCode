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
 * @Title: SetMerchantCache.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年4月13日
 */

package com.froad.thrift;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.MerchantDetailMongo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.db.redis.MerchantCategoryRedis;
import com.froad.db.redis.MerchantRedis;
import com.froad.logback.LogCvt;
import com.froad.po.Merchant;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.TypeInfo;
import com.froad.util.PropertiesUtil;


/**    
 * <p>Title: SetMerchantCache.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年4月13日 下午8:35:50   
 */

public class SetMerchantCache {

	/** 
	 * @Title: main 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param args
	 * @return void    返回类型 
	 * @throws 
	 */
	public static void main(String[] args) throws Exception {
		PropertiesUtil.load();
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		merchantMapper = sqlSession.getMapper(MerchantMapper.class);
		// TODO Auto-generated method stub
		
		Merchant m=new Merchant();
//		m.setMerchantId("0266990F0000");
		List<Merchant> list = merchantMapper.findMerchant(m);
		for (Merchant merchant : list) {
//			Merchant merchant = merchantMapper.findMerchantByMerchantId(merchant.getMerchantId());
			System.out.println(JSON.toJSONString(merchant, true));
			
//			merchant.setMerchantId("oooooooooooooo");
//			System.out.println("修改结果:"+merchantMapper.updateMerchant(merchant));
//			System.out.println("删除结果:"+merchantMapper.deleteMerchant(merchant));
//			if(1==1)
//			continue;
			
			if(merchant.getMerchantStatus()) { // 为银行商户才向Redis中插入数据
				LogCvt.info("缓存该银行虚拟商户所有信息");
				MerchantRedis.set_cbbank_bank_merchant_client_id_org_code(merchant);
			}
			
			/* 缓存全部商户 */
			MerchantRedis.set_cbbank_merchant_client_id_merchant_id(merchant);
			
			/* 缓存该客户端下改地区所有商户ID */
			MerchantRedis.set_cbbank_area_merchant_client_id_area_id(merchant);
			
			/* 缓存该客户端下面所有置顶商户 */
			if (merchant.getIsTop()) { // 如果是置顶商户
				MerchantRedis.set_cbbank_top_merchant_client_id(merchant);
			}
			
			/* 修改待审核商户数量 */
//			if (merchant.getAuditState() == null || merchant.getAuditState() == 0) { // 如果商户未通过审核   则缓存中待审核数量+1
//				MerchantRedis.set_cbank_preaudit_merchant_count_client_id_org_code(merchant.getClientId(), merchant.getOrgCode(), 1);
//			}
			
			MerchantDetailMongo mongo =new MerchantDetailMongo();
			MerchantDetail md = mongo.findMerchantDetailById(merchant.getMerchantId());
			if(md != null){
				List<CategoryInfo> categoryInfoList = md.getCategoryInfo();
				
				List<TypeInfo> typeInfoList = md.getTypeInfo();
				/* 缓存商户分类下的商户id */
				if(CollectionUtils.isNotEmpty(categoryInfoList)) {
					for (CategoryInfo categoryInfo : categoryInfoList) {
						MerchantCategoryRedis.set_cbbank_merchant_category_all_client_id_merchant_category_id(merchant.getClientId(), categoryInfo.getCategoryId(), merchant.getMerchantId());
					}
				}
			}
		}

	}

}
