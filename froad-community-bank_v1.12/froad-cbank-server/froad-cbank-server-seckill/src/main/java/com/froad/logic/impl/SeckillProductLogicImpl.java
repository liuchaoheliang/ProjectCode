package com.froad.logic.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.redis.SeckillRedis;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.SeckillResultFlagEnum;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.SeckillProductLogic;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.mq.beanstalkd.BeanstalkdService;
import com.froad.mq.beanstalkd.SeckillBeanstalkdManager;
import com.froad.po.AcceptOrder;
import com.froad.po.SeckillProduct;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.DateUtil;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * SeckillProductLogic实现类
 * 
 * @author wangzhangxu
 * @date 2015年4月17日 下午12:53:00
 * @version v1.0
 */
public class SeckillProductLogicImpl implements SeckillProductLogic {
	
	SeckillBeanstalkdManager beanstalkManager = new SeckillBeanstalkdManager();
	
	private BeanstalkdService beanstalkdService;
	
	private MonitorService monitorService;
	
	private CommonLogic commonLogic;
	
	/**
	 *  MongoDb操作类
	 */
	private MongoManager mongo = new MongoManager();
	
	
	public SeckillProductLogicImpl(){
		beanstalkdService = beanstalkManager.getClient();
		monitorService = new MonitorManager();
		commonLogic = new CommonLogicImpl();
	}
	
	@Override
	public String getAcceptOrderId(Long memberCode, String productId) {
		return SeckillRedis.get_cbbank_seckill_join_member_code_product_id(memberCode + "", productId);
	}
	
	@Override
	public SeckillProduct getProduct(String clientId, String productId) {
		SeckillProduct product = null;
		Map<String, String> seckillProductMap = SeckillRedis.get_cbbank_seckill_product_client_id_product_id(clientId, productId);
		if (seckillProductMap != null) {
			// 从mongo中获取商品信息
			ProductDetail productDetail = getProductDetail(clientId, productId);
			product = SeckillProduct.parseMap(seckillProductMap, productDetail);
			product.setClientId(clientId);
			product.setProductId(productId);
			
			String merchantId = (String)seckillProductMap.get("merchant_id");
			// 从redis中获取运费
			Map<String, String> productMap = commonLogic.getProductRedis(clientId, merchantId, productId);
			String dm = productMap.get("delivery_money");
			Integer deliveryMoney = 0;
			if (StringUtils.isNotEmpty(dm)) {
				deliveryMoney = Integer.parseInt(dm);
			} else {
				LogCvt.info("未获取到运费信息，默认设置为0 [product redis key=cbbank:product:" + clientId + ":" + merchantId + ":" + productId + "]");
			}
			product.setDeliveryMoney(deliveryMoney);
		} else {
			LogCvt.debug("未获取到秒杀商品信息[clientId=" + clientId + ", productId=" + productId + "]");
		}
		return product;
	}

	@Override
	public boolean checkProductSoldCount(String clientId, String productId, int buyNum, int store) {
		long sold = SeckillRedis.get_cbbank_seckill_product_soldcnt_client_id_product_id(clientId, productId);
		return (sold + buyNum) <= store;
	}
	
	@Override
	public boolean updateProductSoldCount(String clientId, String productId, int buyNum, int store) {
		long sold = SeckillRedis.incr_cbbank_seckill_product_soldcnt_client_id_product_id(clientId, productId, buyNum, store);
		return sold > 0;
	}

	@Override
	public int countMemberBuyNum(Long memberCode, int buyNum, SeckillProduct product) {
		String ymd = DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, product.getEndTime());
		return (int)SeckillRedis.incr_cbbank_seckill_memcnt_member_code_product_id(memberCode, product.getProductId(), ymd, buyNum, product.getBuyLimit());
	}
	
	@Override
	public int getMemberBuyNum(Long memberCode, SeckillProduct product) {
		String ymd = DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, product.getEndTime());
		return SeckillRedis.get_cbbank_seckill_memcnt_member_code_product_id(memberCode, product.getProductId(), ymd);
	}

	@Override
	public boolean createAcceptOrder(AcceptOrder acceptOrder) {
		String data = JSonUtil.toJSonString(acceptOrder);
		long jobId = beanstalkdService.put(data);
		boolean ok = jobId > 0;
		if (ok) {
			// 队列个数加1
			addQueueJobCount();
			
			// 初始化抢购结果
			SeckillRedis.set_cbbank_seckill_res_req_id(acceptOrder.getReqId(), SeckillResultFlagEnum.accept.getCode());
			
			// 统计进入排队队列数量
			monitorService.send(MonitorPointEnum.Seckill_In_Line_Count);
		} else {
			
			// 进入队列失败次数
			monitorService.send(MonitorPointEnum.Seckill_Beanstalkd_Put_Failure);
		}
		return ok;
	}

	@Override
	public Map<String, String> getAcceptOrderById(String acceptOrderId) {
		return SeckillRedis.get_cbbank_seckill_res_req_id(acceptOrderId);
	}

	@Override
	public long getStock(String clientId, String productId) {
		long sold = SeckillRedis.get_cbbank_seckill_product_soldcnt_client_id_product_id(clientId, productId);
		long realStore = 0;
		SeckillProduct product = getProduct(clientId, productId);
		if (product == null) {
			return realStore;
		}
		
		realStore = product.getStore().longValue() - sold;
		
		if (realStore < 0) {
			realStore = 0;
		}
		
		return realStore;
	}

	@Override
	public long addQueueJobCount() {
		return SeckillRedis.incr_cbbank_seckill_product_queue_count();
	}

	@Override
	public ProductDetail getProductDetail(String clientId, String productId) {
		try{
            DBObject where = new BasicDBObject();
            where.put("client_id", clientId);
            where.put("_id", productId);
            ProductDetail productDetail = mongo.findOne(where, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            return productDetail;
        } catch (Exception e) { 
            LogCvt.error("获取ProductDetail失败，原因:" + e.getMessage(), e); 
        }
        return null;
	}
	
	@SuppressWarnings("unchecked")
	public ProductMongo getProductMongo(String orderId){
		ProductMongo productMongo = null;
		try{
			DBObject query = new BasicDBObject();
			query.put("order_id", orderId);
			List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) mongo.findAll(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
			if (subOrderList != null && subOrderList.size() != 0) {
				List<ProductMongo> productMongoList = subOrderList.get(0).getProducts();
				if (productMongoList != null && productMongoList.size() != 0) {
					productMongo = productMongoList.get(0);
				}
			}
            return productMongo;
        } catch (Exception e) { 
            LogCvt.error("获取ProductMongo失败，原因:" + e.getMessage(), e); 
        }
		return null;
	}
	
}
