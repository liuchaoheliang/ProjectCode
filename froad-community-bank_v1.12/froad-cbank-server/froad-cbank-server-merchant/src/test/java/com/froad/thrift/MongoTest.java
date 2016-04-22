package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.froad.db.mongo.MerchantOutletFavoriteMongo;
import com.froad.db.mysql.bean.Page;
import com.froad.logic.MerchantOutletFavoriteLogic;
import com.froad.logic.impl.MerchantOutletFavoriteLogicImpl;
import com.froad.po.mongo.DefaultRecvInfo;
import com.froad.po.mongo.DeliverInfo;
import com.froad.po.mongo.RecvInfo;
import com.froad.po.mongo.StoreOutletInfo;
import com.froad.po.mongo.StoreProductInfo;
import com.froad.thrift.vo.DeliverInfoVo;
import com.froad.util.PropertiesUtil;

public class MongoTest {
	public static void main(String[] args) {
		PropertiesUtil.load();
		MerchantOutletFavoriteMongo merchantOutletFavoriteMongo = new MerchantOutletFavoriteMongo();
		
//		
//		StoreProductInfo storeProductInfo = new StoreProductInfo();
//		storeProductInfo.setProductId("101");
//		storeProductInfo.setProductName("test1");
//		storeProductInfo.setProductImage("test1");
////		
//		StoreOutletInfo storeOutletInfo = new StoreOutletInfo();
//		storeOutletInfo.setOutletId("006c49910001");
//		storeOutletInfo.setMerchantId("101");
//		storeOutletInfo.setOutletImage("tes1");
//		storeOutletInfo.setOutletName("test1");
		
		
		RecvInfo recvInfo = new RecvInfo();
		recvInfo.setRecvId("00E910758000");
		recvInfo.setAddress("上海XXXXXXXOOO");
		recvInfo.setConsignee("张三三XXXXXOOO");
		recvInfo.setPhone("13144440000");
		recvInfo.setIsDefault("1");
		
		
//		DeliverInfo  deliverInfo = new DeliverInfo();
//		deliverInfo.setDeliveryId("0156A7630000");
//		deliverInfo.setConsignee("陆万全");
//		deliverInfo.setAreaId((long) 100000013);
//		deliverInfo.setIsDefault("0");
//		deliverInfo.setPhone("17785315231");	
		List<RecvInfo> stoList = new ArrayList<RecvInfo>();
		stoList.add(recvInfo);
//		DefaultDeliverInfo deliverInfo = new DefaultDeliverInfo();
//		deliverInfo.setDeliveryId((long)1004);
//		deliverInfo.setConsignee("湖南");
//		deliverInfo.setPhone("13100003344");
		String clientId = "anhui";
		String id = "50000000225";
		long memberCode = Long.parseLong(id);
		merchantOutletFavoriteMongo.addRecvInfo2(clientId, memberCode, stoList);
		merchantOutletFavoriteMongo.updateDefaultRecvInfo(clientId, memberCode, recvInfo);
//		merchantOutletFavoriteMongo.findStoreOutletInfoVoByPage(page, clientId, memberCode);
		
//		String clientId = "100";
//		long memberCode = 100;
//		long productId = 100;
//		Map<String,Integer> map = merchantOutletFavoriteMongo.countProductStoreOutletInfo(1000, 2000000001);
//		System.out.println(map.get("productcount"));
//		System.out.println(map.get("outletcount"));
//		MerchantOutletFavoriteLogic m = new MerchantOutletFavoriteLogicImpl();
//		m.isExitsRecvInfo(clientId, memberCode, "00E910758000");
//		List<RecvInfo> list= m.queryRecvInfo(clientId, memberCode, "0");
//		m.countProductStoreOutletInfo(clientId, memberCode);
//		for(RecvInfo r : list){
//			System.out.println(r.getAddress());
//		}
//		m.updateRecvInfo(clientId, memberCode, recvInfo);
//		m.addStoreOutletInfo(clientId, memberCode, storeOutletInfo);
//		System.out.println("************"+merchantOutletFavoriteMongo.addStoreOutletInfos(clientId, memberCode, storeOutletInfo));
//		System.out.println(merchantOutletFavoriteMongo.removeStoreOutletInfo(clientId, memberCode, "006c49910000"));
//		System.out.println("-----------"+merchantOutletFavoriteMongo.addStoreOutletInfo(clientId, memberCode, storeOutletInfo));
//		System.out.println(merchantOutletFavoriteMongo.addStoreProductInfo(clientId, memberCode, storeProductInfo));
//		merchantOutletFavoriteMongo.removeStoreProductInfo(clientId, memberCode, 102);
//		List<StoreProductInfo> list = merchantOutletFavoriteMongo.queryStoreProductInfo(clientId, memberCode);
//		System.out.println(list.size());
//		System.out.println(StoreProductInfo.getProductName());
//		int  i = merchantOutletFavoriteMongo.isExitsDeliverInfo(clientId, memberCode, 1000);
//		System.out.println(i);
//		merchantOutletFavoriteMongo.addDefaultDeliverInfo(clientId, memberCode, deliverInfo);
//		merchantOutletFavoriteMongo.addDefaultRecvInfo(clientId, memberCode, recvInfo);
//		merchantOutletFavoriteMongo.updateDeliverInfo(clientId, memberCode, deliverInfo);
//		merchantOutletFavoriteMongo.removeDeliverInfo(clientId, memberCode, 1000);
		
		
		
		
		
//		Page<StoreProductInfo> page = new Page<StoreProductInfo>();
//		page.setPageSize(1);
//		page.setPageNumber(1);
////		S clientId = 100;
////		long memberCode = 100;
//		String clientId = 100;
//		long memberCode = 100;
//		page = merchantOutletFavoriteMongo.findStoreOutletInfoVoByPage(page,clientId,memberCode);
//		
//		List<StoreProductInfo> list =  page.getResultsContent();
//		for(int i =0;i<list.size();i++){
//			System.out.println("----------"+list.get(i).getProductName());
//		}
	}
}
