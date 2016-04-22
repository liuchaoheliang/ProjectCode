package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.froad.cbank.persistent.entity.MerchantUser;
import com.froad.cbank.persistent.entity.Order;
import com.froad.cbank.persistent.entity.Shipping;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.ShippingCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.MerchantUserMapper;
import com.froad.db.chongqing.mappers.OrderMapper;
import com.froad.db.chongqing.mappers.ShippingMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class ShippingProcess extends AbstractProcess{

	public ShippingProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}
	private ShippingMapper spMapper = null;
	private TransferMapper transferMapper = null;
	private MerchantUserMapper mUserMapper = null;
	private OrderMapper orderMapper = null;
	
	final Map<String,String> map = new HashMap<String,String>();
	final Map<String, String> orderMap = new HashMap<String, String>();
	final Map<String, String> userMap = new HashMap<String, String>();

	
	@Override
	public void before() {
		spMapper = cqSqlSession.getMapper(ShippingMapper.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		mUserMapper = cqSqlSession.getMapper(MerchantUserMapper.class);
		orderMapper = cqSqlSession.getMapper(OrderMapper.class);
		
		
		boolean b = deleteShippingByClientId(Constans.clientId);
		if(b){
			LogCvt.info("订单发货表重庆迁移数据清除完成!");
		}
		
		//记录新旧id
		List<Transfer> orderList = transferMapper.queryGroupList(TransferTypeEnum.order_id);
		if(orderList != null && orderList.size() >0) {
			for(Transfer tf : orderList) {
				orderMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		
		List<Transfer> userList = transferMapper.queryGroupList(TransferTypeEnum.merchant_user_id);
		if(userList != null && userList.size() >0) {
			for(Transfer tf : userList) {
				userMap.put(tf.getOldId() , tf.getNewId());
			}
		}
	}

	
	@Override
	public void process() {

		boolean result = false;
		
		
		
		List<Shipping> listShip = spMapper.selectByCondition(new Shipping());
		
		
		//商户用户Id
		String merchantUserId = "";
		MerchantUser user = null;
		Order order = null;
		String remark = "";
		for(Shipping sh : listShip){
			ShippingCG cgShipping = new ShippingCG();

			String newid =  orderMap.get(String.valueOf(sh.getOrderId()));
			String a[] = newid.split(",");
			String order_id = a[0];
			String sub_order_id = a[1];
			
			cgShipping.setId(order_id+"_"+sub_order_id);
			
			user = mUserMapper.selectByUserName(sh.getClientId(), sh.getOperator());
			
			if(user == null){
				merchantUserId = "";
				LogCvt.error("该用户不存在，Name:"+sh.getOperator());
			}else{
				merchantUserId = userMap.get(String.valueOf(user.getId()));
			}
			cgShipping.setMerchantUserId(merchantUserId);
			
			
			order = orderMapper.selectById(sh.getOrderId());
			if(null != order){
				remark = order.getRemark();
				if(remark.endsWith("确认收货")){
					cgShipping.setShippingStatus("2");
				}
			}else{
				cgShipping.setShippingStatus("");
			}
			cgShipping.setDeliveryCorpId(sh.getDeliveryCorpId().toString());
			cgShipping.setDeliveryCorpName(sh.getDeliveryCorpName());
			cgShipping.setTrackingNo(sh.getTrackingNo());
			cgShipping.setRemark(sh.getRemark());
			if(sh.getShippingTime() !=null){
				cgShipping.setShippingTime(sh.getShippingTime().getTime());
			}else{
				cgShipping.setShippingTime(null);
			}
			if(sh.getReceiptTime() != null){
				cgShipping.setReceiptTime(sh.getReceiptTime().getTime());
			}else{
				cgShipping.setReceiptTime(null);
			}
			
			result = addShipping(cgShipping);
			if(result){
				Transfer transfer = new Transfer();
				transfer.setOldId(String.valueOf(sh.getId()));
				transfer.setNewId(String.valueOf(cgShipping.getId()));
				transfer.setType(TransferTypeEnum.shipping_id);
				int i = transferMapper.insert(transfer);
				if(i != -1){
					LogCvt.info("cb_shipping转移成功");
					updateSubOrder(sub_order_id,cgShipping.getShippingStatus());
				}
			}else{
				LogCvt.info("转移失败,ID:"+ sh.getId());
			}
		}
	
	}
	
	
	public boolean addShipping(ShippingCG cgShipping){
		boolean result = false;
		long addresult = -1;
		String order_id = "";
		String sub_order_id = "";
			//检查重构数据库中与安徽数据库是否有相同数据
			String newid = cgShipping.getId();
			String a[] = newid.split("_");
			order_id = a[0];
			sub_order_id = a[1];
			
			if(map.containsKey(order_id+sub_order_id)){
				LogCvt.error("该子订单发货信息已存在,ID:"+cgShipping.getId());
				return false;
			}
			
			
			//重构mysql数据库
			addresult = addShipping(order_id, sub_order_id, cgShipping);
			if(addresult != -1){
				map.put(order_id+sub_order_id,"order_id");
				result = true;
			}
		
		return result;
	}


	public int addShipping(String order_id, String sub_order_id,ShippingCG shipping){
		String id=order_id+"_"+sub_order_id;
		
		ShippingCG shp = new ShippingCG();
		shp.setId(id);
		shp.setClientId(Constans.clientId);
		shp.setShippingStatus(shipping.getShippingStatus());
		shp.setDeliveryCorpId(shipping.getDeliveryCorpId());
		shp.setDeliveryCorpName(shipping.getDeliveryCorpName());
		shp.setTrackingNo(shipping.getTrackingNo());
		shp.setRemark(shipping.getRemark());
		shp.setShippingTime(shipping.getShippingTime());
		shp.setShippingStatus(shipping.getShippingStatus());
		shp.setMerchantUserId(shipping.getMerchantUserId());
		return mongo.add(shp, MongoTableName.CB_SHIPPING);
	}
	
	public boolean deleteShippingByClientId(String clientId) {
		DBObject where = new BasicDBObject();
		where.put("client_id", clientId);

		
		LogCvt.info(MongoTableName.CB_SHIPPING + "表删除client_id为"
				+ clientId + "的数据------->");
		
		int result = mongo.remove(where, MongoTableName.CB_SHIPPING);
		return result != -1;
	}
	
	
	public void updateSubOrder(String subOrderId,String status){
		
		DBObject where = new BasicDBObject();
		where.put("sub_order_id", subOrderId);
		
		SubOrderMongo subOrder = mongo.findOne(where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		where.put("products.product_id", subOrder.getProducts().get(0).getProductId());
		
		DBObject value = new BasicDBObject();
		value.put("products.$.delivery_state", status);
		
		mongo.update(value, where, MongoTableName.CB_SUBORDER_PRODUCT, "$set");
	}

}

