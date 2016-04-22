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
import com.froad.logic.MerchantLogic;
import com.froad.logic.impl.MerchantLogicImpl;
import com.froad.po.Area;
import com.froad.po.Merchant;
import com.froad.po.mongo.MerchantDetail;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.vo.AreaVo;
import com.froad.util.BeanUtil;
import com.froad.util.PropertiesUtil;
import com.froad.util.ThriftConfig;
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

public class SetOutletDetailParentAreaId {
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
	public static void main(String[] args) throws Exception{
		PropertiesUtil.load();
		MongoService mongo = new MongoManager();
		
		
		AreaService.Iface client = (AreaService.Iface) ThriftClientProxyFactory.createIface(AreaService.class, ThriftConfig.SUPPORT_SERVICE_HOST, ThriftConfig.SUPPORT_SERVICE_PORT);
		List<AreaVo> list = client.getArea(new AreaVo());
		List<Area> areaList = (List<Area>)BeanUtil.copyProperties(Area.class, list);
		for (Area area : areaList) {
			DBObject value = new BasicDBObject();
			value.put("parent_area_id", area.getParentId());
			DBObject where = new BasicDBObject();
			where.put("area_id", area.getId());
			mongo.update(value, where, "cb_outlet_detail", "$set",true,true);
		}
		
	}

}
