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
 * @Title: SetOrg.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年5月5日
 */

package com.froad.thrift;

import java.util.List;

import com.froad.db.mongo.MongoService;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.MerchantLogicImpl;
import com.froad.po.Merchant;
import com.froad.po.mongo.MerchantDetail;
import com.froad.util.PropertiesUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * <p>
 * Title: SetOrg.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年5月5日 下午8:35:26
 */

public class SetOrg {
	{
//		PropertiesUtil.load();
	}

	/**
	 * @Title: main
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param args
	 * @return void 返回类型
	 * @throws
	 */
	public static void main(String[] args) {
		PropertiesUtil.load();
		CommonLogic commonLogic = new CommonLogicImpl();
//		commonLogic.queryByOrgCode(merchantDetail.getClientId(),merchantDetail.getOrgCode());
		
		MerchantLogic merchantLogic = new MerchantLogicImpl(); 

		MongoService mongo = new MongoManager();
		MongoPage page = new MongoPage();
		page.setPageSize(10);
		DBObject dbObject = new BasicDBObject();
		dbObject.put("city_org_code", "");
		dbObject.put("county_org_code", "");
		dbObject.put("org_code", new BasicDBObject().append("$ne", "111111"));
		System.out.println("where=[" + dbObject + "]");
		
		List<MerchantDetail> list = (List<MerchantDetail>) mongo.findByPage(page, dbObject, "cb_merchant_detail", MerchantDetail.class).getItems();
		int count = 0;
		while (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				MerchantDetail merchantDetail = list.get(i);
//				Org org = orgLogic.findOrgById(merchantDetail.getClientId(), merchantDetail.getOrgCode());
				
				Merchant merchant = new Merchant();
				merchant.setClientId(merchantDetail.getClientId());
				merchant.setMerchantId(merchantDetail.getId());
				List<Merchant> ms =merchantLogic.findMerchant(merchant);
				
				if(ms == null || ms.size()<1) {
					continue;
				}
				Merchant mv = ms.get(0);

				DBObject value = new BasicDBObject();
				DBObject where = new BasicDBObject();
				where.put("org_code", mv.getOrgCode());

				value.put("city_org_code", mv.getCityOrgCode());
				value.put("county_org_code", mv.getCountyOrgCode());
				System.out.println("where=[" + where + "],   value=[" + value + "]");
				mongo.update(value, where, "cb_merchant_detail", "$set");
				
				System.out.println("修改数据:" + (count++));
			}
			list = (List<MerchantDetail>) mongo.findByPage(page, dbObject,  "cb_merchant_detail", MerchantDetail.class).getItems(); // 重新查询一把

		}
	}

}
