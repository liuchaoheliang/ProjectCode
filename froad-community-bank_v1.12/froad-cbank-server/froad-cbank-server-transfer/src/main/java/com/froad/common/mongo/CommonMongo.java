package com.froad.common.mongo;

import com.alibaba.fastjson.JSON;
import com.froad.db.chonggou.entity.OutletProduct;
import com.froad.db.chonggou.entity.RecvInfo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.util.Constans;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *  门店虚拟商品
  * @ClassName: OutletProductSupportImpl
  * @Description: TODO
  * @author share 2015年3月26日
  * @modify share 2015年3月26日
 */
public class CommonMongo {

	/**
	 *  根据商品ID获取虚拟商品
	  * @Title: queryOutletProduct
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param productId
	  * @param @return
	  * @throws
	  * @see com.froad.support.OutletProductSupport#queryOutletProduct(java.lang.String)
	 */
	public static OutletProduct queryOutletProduct(String merchantId,String outletId,int money) {
		// TODO Auto-generated method stub
		MongoManager mongo = new MongoManager();
		DBObject dbo = new BasicDBObject();
		dbo.put("client_id", Constans.clientId);
		dbo.put("merchant_id", merchantId);
		if(outletId != null){
			dbo.put("outlet_id", outletId);
		}
		dbo.put("cost", money);
		return mongo.findOne(dbo, MongoTableName.CB_OUTLET_PRODUCT, OutletProduct.class);
	}
	
	/**
	 * 新增门店商品信息到mongo
	 * @param outletProduct
	 */
    public static void addOutletProduct(OutletProduct outletProduct) {
    	MongoManager mongo = new MongoManager();
 	   DBObject opdbObject = new BasicDBObject();
        opdbObject.put("_id", outletProduct.getProductId());
        opdbObject.put("client_id", outletProduct.getClientId());
        opdbObject.put("merchant_id", outletProduct.getMerchantId());
        opdbObject.put("outlet_id", outletProduct.getOutletId());
        opdbObject.put("cost", outletProduct.getCost());
        
        boolean isSuccess = mongo.add(opdbObject, MongoTableName.CB_OUTLET_PRODUCT) !=-1;// 向mongodb插入数据
        if(!isSuccess) {
        	throw new RuntimeException();
        }
    }
    
    
    /** 追加收货信息
	* @Title: removeRecvInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param recvId
	* @param @return
	* @return int
	* @throws 
	*/
	public static int appedRecvInfo(String clientId, long memberCode, RecvInfo recvInfo) {
		MongoManager mongo = new MongoManager();
		DBObject valueObj =null;
		int result = 0;
		String id=clientId+"_"+memberCode;
		valueObj = new BasicDBObject();
		// 条件
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id",id );
		valueObj.put("recv_info", JSON.parse(JSonUtil.toJSonString(recvInfo)));
		result = mongo.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE,"$push");
		return result;
	}

}

