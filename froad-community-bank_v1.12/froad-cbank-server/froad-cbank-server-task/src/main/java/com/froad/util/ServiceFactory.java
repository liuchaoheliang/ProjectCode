package com.froad.util;

import com.froad.db.mongo.OrderMongoService;
import com.froad.db.mongo.ProductDetailMongoService;
import com.froad.db.mongo.ShippingMongoService;
import com.froad.db.mongo.SmsLogMongoService;
import com.froad.db.mongo.SubOrderMongoService;
import com.froad.db.mongo.TicketMongoService;
import com.froad.db.mongo.impl.OrderMongoServiceImpl;
import com.froad.db.mongo.impl.ProductDetailMongoServiceImpl;
import com.froad.db.mongo.impl.ShippingMongoServiceImpl;
import com.froad.db.mongo.impl.SmsLogMongoServiceImpl;
import com.froad.db.mongo.impl.SubOrderMongoServiceImpl;
import com.froad.db.mongo.impl.TicketMongoServiceImpl;
import com.froad.db.redis.MemberHistoryVipRedisService;
import com.froad.db.redis.MemberProductLimitRedisService;
import com.froad.db.redis.ProductPresellTokenRedisService;
import com.froad.db.redis.ProductRedisService;
import com.froad.db.redis.ProductSeckillRedisService;
import com.froad.db.redis.ProductStoreRedisService;
import com.froad.db.redis.TimeOrderRedisService;
import com.froad.db.redis.TimePaymentRedisService;
import com.froad.db.redis.impl.MemberHistoryVipRedisServiceImpl;
import com.froad.db.redis.impl.MemberProductLimitRedisServiceImpl;
import com.froad.db.redis.impl.ProductPresellTokenRedisServiceImpl;
import com.froad.db.redis.impl.ProductRedisServiceImpl;
import com.froad.db.redis.impl.ProductSeckillRedisServiceImpl;
import com.froad.db.redis.impl.ProductStoreRedisServiceImpl;
import com.froad.db.redis.impl.TimeOrderRedisServiceImpl;
import com.froad.db.redis.impl.TimePaymentRedisServiceImpl;

/**
 * 接口工厂类
 * @ClassName: ServiceFactory 
 * @Description: TODO
 * @author: huangyihao
 * @date: 2015年4月24日
 */
public class ServiceFactory {
	
	/*================================MongoService================================*/
	
	public static OrderMongoService getOrderMongoService(){
		return new OrderMongoServiceImpl();
	}
	
	public static ProductDetailMongoService getProductDetailMongoService(){
		return new ProductDetailMongoServiceImpl();
	}
	
	public static ShippingMongoService getShippingMongoService(){
		return new ShippingMongoServiceImpl();
	}
	
	public static SmsLogMongoService getSmsLogMongoService(){
		return new SmsLogMongoServiceImpl();
	}
	
	public static SubOrderMongoService getSubOrderMongoService(){
		return new SubOrderMongoServiceImpl();
	}
	
	public static TicketMongoService getTicketMongoService(){
		return new TicketMongoServiceImpl();
	}
	
	
	/*================================RedisService================================*/
	
	public static MemberProductLimitRedisService getMemberProductLimitRedisService(){
		return new MemberProductLimitRedisServiceImpl();
	}
	
	public static ProductRedisService getProductRedisService(){
		return new ProductRedisServiceImpl();
	}
	
	public static TimeOrderRedisService getTimeOrderRedisService(){
		return new TimeOrderRedisServiceImpl();
	}
	
	public static TimePaymentRedisService getTimePaymentRedisService(){
		return new TimePaymentRedisServiceImpl();
	}
	
	public static MemberHistoryVipRedisService getMemberHistoryVipRedisService(){
		return new MemberHistoryVipRedisServiceImpl();
	}
	
	public static ProductPresellTokenRedisService getProductPresellTokenRedisService(){
		return new ProductPresellTokenRedisServiceImpl();
	}
	
	public static ProductSeckillRedisService getProductSeckillRedisService(){
		return new ProductSeckillRedisServiceImpl();
	}
	
	public static ProductStoreRedisService getProductStoreRedisService(){
		return new ProductStoreRedisServiceImpl();
	}
}
