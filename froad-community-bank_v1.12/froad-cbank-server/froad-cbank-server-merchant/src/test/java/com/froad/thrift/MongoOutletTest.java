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
 * @Title: MongoOutletTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年3月31日
 */

package com.froad.thrift;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.froad.db.mongo.OutletDetailMongo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.po.mongo.Location;
import com.froad.po.mongo.OutletDetail;
import com.froad.po.mongo.TypeInfo;
import com.froad.thrift.vo.OutletDetailVo;
import com.froad.util.BeanUtil;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;


/**    
 * <p>Title: MongoOutletTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月31日 下午4:56:42   
 */

public class MongoOutletTest {
	static {
		PropertiesUtil.load();
	}

	/**
	 * @throws Exception  
	 * @Title: main 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param args
	 * @return void    返回类型 
	 * @throws 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(System.currentTimeMillis());
		OutletDetailMongo odm = new OutletDetailMongo();
//		List<OutletDetail> result = odm.searchNearbyOutlet(116.325986265656, 26.569789419, 1000);
		MongoPage page = new MongoPage();
		page.setPageSize(10);
		page.setPageNumber(1);
		
		
		OutletDetail outletDetail = new OutletDetail ();
		Location location = new Location();
//		116.325986265656, latitude:26.567787412
		location.setLongitude(116.325986265656);
		location.setLatitude(26.56778741);
		outletDetail.setLocation(location);
//		outletDetail.setLocation(new Double[]{116.325986265656,26.56778741});
		outletDetail.setOutletName("");
//		page = odm.findNearbyOutlet(page,outletDetail, 500);
		
//		
		TypeInfo type = new TypeInfo();
		type.setMerchantTypeId(100000000l);
		List<TypeInfo> typeInfo = new ArrayList<TypeInfo>();
		typeInfo.add(type);
		
		outletDetail.setTypeInfo(typeInfo);
		
		outletDetail.setMerchantName("丽");
		
		List<OutletDetail> result  =null; 
//		result= (List<OutletDetail>)page.getItems();
		
		System.out.println("------->"+JSON.toJSONString(location));
		
		System.out.println("------->"+JSON.toJSONString(outletDetail));
//		System.exit(0);
//		result = odm.findNearbyOutlet(outletDetail, 10, 100, -1);
		
		List<OutletDetailVo> odv = (List<OutletDetailVo>) BeanUtil.copyProperties(OutletDetailVo.class, result);
		
//		OutletDetail od = result.get(0);
//		System.out.println(JSON.toJSONString(page, true));
		System.out.println("返回结果PO====>"+JSON.toJSONString(result, true));
		System.out.println("返回结果VO====>"+JSON.toJSONString(odv, true));
//		System.out.println(JSON.toJSONString(od, true));
		System.exit(0);
		
		
		// TODO Auto-generated method stub
		MongoManager manager = new MongoManager();
		
		BasicDBObject where = new BasicDBObject();
//		where.put("location", "{ $geoWithin : { $center : [[0.5, 0.5], 20], $uniqueDocs : true }");
//		where.put("location", "{ '$near' : [116.325986265656, 26.569789419], '$maxDistance':10000, '$uniqueDocs' : true }");
//		where.append("location", new BasicDBObject("$geoWithin", new BasicDBObject("$center","{ 0.5,0.5 }").append("$uniqueDocs", true)));
//		double[] dd = { 0.5,0.5 } ;
		//new double[] { 0.5,0.5 } 
		//new double[][]{new double[] { 0.5,0.5 },20.}
		
//		where.append("location", new BasicDBObject("$near", new double[] { 116.325986265656, 26.569789419 }).append("$spherical", true).append("$distanceMultiplier", 6378137).append("$maxDistance", 2000./6378137.));
//		where.append("location", new BasicDBObject("$near", new double[] { 116.325986265656, 26.569789419 }).append("$maxDistance", 100));
		List list = new ArrayList();
		list.add(new double[] { 116.325986265656, 26.569789419 });
		list.add((double) 1 / 111);
		where.append("location", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", list)));
//		where.append("location", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere","[ [ 116.325986265656, 26.569789419 ] , 1/111 ]")));
		
		BigDecimal bg = new BigDecimal(100/6378137);
		System.out.println(new BigDecimal((double)100/6378137).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue());
		
		java.text.DecimalFormat df = new java.text.DecimalFormat();
		df.setMaximumFractionDigits(4);// 设置最大小数位
		System.out.println(df.format(1000./6378137.));
		
		NumberFormat nf = NumberFormat.getInstance();   
		nf.setGroupingUsed(false);
		System.out.println(nf.format(1000./6378137.));
		
//		where.append("location", new BasicDBObject("$near", new double[] { 116.325986265656, 26.569789419  }).append("$maxDistance",  1.567855942887398));
		
		where.put("category_info.category_id", 100025);
		System.out.println(where);
		System.out.println(JSON.toJSONString(manager.findAll(where, MongoTableName.CB_OUTLET_DETAIL, Map.class), true));
		
		System.exit(0);
		
		DB db = manager.getWriteMongoDB();
		BasicDBObject myCmd = new BasicDBObject();
		myCmd.put("geoNear",  MongoTableName.CB_OUTLET_DETAIL);
		double[] loc = { 116.325986265656, 26.569789419 };
		myCmd.put("near", loc);
		myCmd.put("spherical", true);
		myCmd.put("distanceMultiplier", 6378137);
		myCmd.put("maxDistace", (double) 100 / 6378137); // 查2500米以内
		myCmd.put("num", 100);
		myCmd.put("limit", 2);
		myCmd.put("query", "{outlet_name:'黄焖鸡米饭'}");
		myCmd.put("outlet_name", "黄焖鸡米饭");
		//outlet_name
		
		System.out.println(myCmd);
		
//		CommandResult result = db.command(myCmd);
		manager.command(myCmd);
//		result.toMap()
//		System.out.println(result.toString());
		Map map = manager.command(myCmd);
		System.out.println(JSON.toJSONString(map, true));
//		System.out.println(result.keySet());
//		System.out.println(result.get("results"));
//		System.out.println(result.toString());
		
		
	}

}
