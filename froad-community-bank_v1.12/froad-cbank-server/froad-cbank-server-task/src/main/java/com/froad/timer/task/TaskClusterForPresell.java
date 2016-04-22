package com.froad.timer.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.SubOrderMongoService;
//import com.froad.db.mongo.ProductDetailMongoService;
import com.froad.db.mongo.TicketMongoService;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.db.redis.ProductRedisService;
import com.froad.enums.ClusterState;
import com.froad.enums.ClusterType;
import com.froad.enums.DeliveryType;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.ResultCode;
import com.froad.enums.SmsType;
import com.froad.enums.TicketStatus;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.PresellProduct;
import com.froad.po.Product;
import com.froad.po.Ticket;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
//import com.froad.po.mongo.ProductDetail;
//import com.froad.po.mongo.ProductOutlet;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.SmsMessageResponseVo;
import com.froad.thrift.vo.SmsMessageVo;
import com.froad.util.DateUtil;
import com.froad.util.ServiceFactory;
import com.froad.util.ThriftConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 
 * @ClassName: TaskClusterForPresell 
 * @Description: 预售商品定时成团、发券
 * @author: huangyihao
 * @date: 2015年3月24日
 */
public class TaskClusterForPresell implements Runnable {
	
	private TicketMongoService ticketMongoService = ServiceFactory.getTicketMongoService();
	private ProductRedisService productRedisService = ServiceFactory.getProductRedisService();
	private static SubOrderMongoService subOrderMongoService = ServiceFactory.getSubOrderMongoService();
//	private ProductDetailMongoService productDetailMongoService = ServiceFactory.getProductDetailMongoService();
	/***********************Mongo KEY 常量************************/
	private static final String PRODUCTID="product_id";
	private static final String CLIENTID="client_id";
	private static final String STATUS="status";
	/***********************Mongo KEY 常量************************/
	/**
	 * 监控服务
	 * */
	private MonitorService monitorService = new MonitorManager();
	private final String COMMAND_SET = "$set";
	
	@Override
	public void run() {
		LogCvt.debug("定时任务: 预售商品定时成团、发券------开始------");
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
			
			// 连接support模块
			SmsMessageService.Iface smsClient = 
					(SmsMessageService.Iface)ThriftClientProxyFactory.createIface(SmsMessageService.class.getName(), SmsMessageService.class.getSimpleName(), ThriftConfig.SUPPORT_SERVICE_HOST, ThriftConfig.SUPPORT_SERVICE_PORT);
			
			List<Product> plist = productMapper.selectShouldClusterProductPresell();
			if (CollectionUtils.isEmpty(plist)) {
				LogCvt.debug("定时任务: 预售商品定时成团、发券------完成(无需要成团的预售商品)------");
				return;
			}
			
			LogCvt.debug("定时任务: 预售商品定时成团、发券------需要成团的预售商品 "+plist.size()+" 条------");
			
			for(Product product : plist){
				
				String productId = product.getProductId();
				String clientId = product.getClientId();
				String merchantId = product.getMerchantId();
				
				LogCvt.debug("定时任务: 预售商品定时成团、发券------预售商品 "+productId+" 处理开始------");
				
				Map<String, String> redisMap = productRedisService.getProductInfo(clientId, merchantId, productId);
				
				PresellProduct temp = new PresellProduct();
				temp.setProductId(productId);
				temp.setClientId(clientId);
				temp.setClusterType(BooleanUtils.toBooleanObject(ClusterType.auto.getCode(), "1", "0", "")); //自动成团
				temp.setClusterState(BooleanUtils.toBooleanObject(ClusterState.success.getCode(), "1", "0", "")); //成团成功
				try {
					// 2. 预售商品自动成团(更新需成团预售商品的成团类型和成团状态)
					boolean flag1 = productMapper.updatePresellByProductId(temp);
					// 更新缓存中预售商品的成团状态
					boolean flag2 = productRedisService.updateClusterState(clientId, merchantId, productId, ClusterState.success.getCode());
					LogCvt.debug("预售商品[productId="+productId+"]自动成团"+(flag1&&flag2?"成功":"失败"));
					if(flag1&&flag2){
						sqlSession.commit(true);
					}
				} catch (Exception e) {
					LogCvt.error("预售商品[productId="+productId+"]自动成团异常");
					LogCvt.error(e.getMessage(), e);
					sqlSession.rollback(true);
					continue;
				}
				if(StringUtils.equals(DeliveryType.take.getCode(), product.getDeliveryOption()) ||
						StringUtils.equals(DeliveryType.home_or_take.getCode(), product.getDeliveryOption())){
					
					DBObject queryObj = new BasicDBObject();
					queryObj.put(PRODUCTID, productId);
					queryObj.put(CLIENTID, clientId);
					queryObj.put(STATUS, TicketStatus.init.getCode());
					// 获取成团预售商品的所有券信息
					List<Ticket> tickets = ticketMongoService.findByCondition(queryObj);
					if(CollectionUtils.isEmpty(tickets)){
						LogCvt.debug("预售商品[productId="+productId+"] 无待发送券");
						continue;
					}
					LogCvt.debug("预售商品[productId="+productId+"] 待发送券 "+tickets.size()+" 条");
					
					// 发券
					for(Ticket t : tickets){
						// 会员名、券号、提货时间、提货地点
//						String memberName = t.getMemberName();
						String ticketId = t.getTicketId();
						// 在缓存中时间格式以毫秒数储存, 则需要把时间格式转换
						String startTime = DateUtil.formatDateTime(DateUtil.DATE_FORMAT3, new Date(Long.valueOf(redisMap.get("delivery_start_time"))));
						String endTime = DateUtil.formatDateTime(DateUtil.DATE_FORMAT3, new Date(Long.valueOf(redisMap.get("delivery_end_time"))));
//						String deliverTime = startTime+"-"+endTime;
//						StringBuffer tempAddress = new StringBuffer();
//						ProductDetail detail = productDetailMongoService.findByProductId(productId);
//						List<ProductOutlet> outlets = detail.getOutletInfo();
//						if(CollectionUtils.isNotEmpty(outlets)){
//							for(int i = 0; i < outlets.size(); i++){
//								ProductOutlet outlet = outlets.get(i);
//								if(i == outlets.size()-1){
//									tempAddress.append(outlet.getOutletName());
//								}else{
//									tempAddress.append(outlet.getOutletName()+"、");
//								}
//							}
//						}
						String address = getAddressBySubOrderIdAndProductId(t.getSubOrderId(), t.getProductId());
						//尊敬的{会员名}用户，您的精品预售提货码为: {提货码}，请您在提货期: {YYYY.MM.DD-YYYY.MM.DD}内前往提货点{提货点名称}提货，谢谢。
						// 您预订的{商品名称}商品数量{商品数量}个，提货码为{提货码}，请在{开始时间}-{结束时间}期间，到{提货点名称}凭预售提货码提货，具体请登陆网站查询订单详情。
						List<String> contents = new ArrayList<String>();
						// contents.add(memberName); 	//{会员名}
						contents.add(t.getProductName());
						contents.add(t.getQuantity().toString());
						contents.add(ticketId);		//{提货码}
						// contents.add(deliverTime);	//{YYYY.MM.DD-YYYY.MM.DD}
						contents.add(startTime);
						contents.add(endTime);
						contents.add(address);		//{提货点名称}
						SmsMessageVo smsMessageVo = new SmsMessageVo();
						smsMessageVo.setMobile(t.getMobile());	// 手机号
						smsMessageVo.setSmsType(SmsType.presellDelivery.getCode());	// 短信类型
						smsMessageVo.setClientId(t.getClientId());	//客户端号
						smsMessageVo.setValues(contents);	// 替换内容
						LogCvt.debug("预售商品[productId="+productId+"]|券号[ticketId="+t.getTicketId()+"]------准备发券------");
						SmsMessageResponseVo responseVo =  smsClient.sendSMS(smsMessageVo);
						if(ResultCode.success.getCode().equals(responseVo.getResultCode())){
							LogCvt.debug("预售商品[productId="+productId+"]|券号[ticketId="+t.getTicketId()+"]------发券成功------");
							DBObject oldTicket = new BasicDBObject();
							oldTicket.put("ticket_id", t.getTicketId());
							oldTicket.put("status", TicketStatus.init.getCode());
							DBObject value = new BasicDBObject();
							value.put("status", TicketStatus.sent.getCode());
							Boolean result = ticketMongoService.updateTicket(value, oldTicket, COMMAND_SET);
							LogCvt.debug("预售商品[productId="+productId+"]|券号[ticketId="+t.getTicketId()+"]------券状态更新"+(result?"成功":"失败")+"------");
//							// TODO 预售商品发券处理成功 - 发送监控
//							monitorService.send(MonitorPointEnum.Timertask_Cluster_Presell_Success);
						}else{
							LogCvt.error("预售商品[productId="+productId+"]|券号[ticketId="+t.getTicketId()+"]------发券失败------");
							// TODO 预售商品发券处理不成功 - 发送监控
							monitorService.send(MonitorPointEnum.Timertask_Cluster_Presell_Failure);
						}
					}
				}
				
				LogCvt.debug("定时任务: 预售商品定时成团、发券------预售商品 "+productId+" 处理结束------");
			}
		} catch (Exception e) {
			LogCvt.error("定时任务: 预售商品定时成团、发券------系统异常------");
			LogCvt.error(e.getMessage(), e);
			// TODO 预售商品发券处理不成功 - 发送监控
			monitorService.send(MonitorPointEnum.Timertask_Cluster_Presell_Failure);
		} finally {
			if(sqlSession != null) sqlSession.close();
			LogCvt.debug("定时任务: 预售商品定时成团、发券------结束------");
		}
	}
	
	private static String getAddressBySubOrderIdAndProductId(String subOrderId, String productId){
		String address = "";
		SubOrderMongo subOrderMongo = subOrderMongoService.findSubOrderMongo(subOrderId);
		if( subOrderMongo != null ){
			List<ProductMongo> pl = subOrderMongo.getProducts();
			if( pl != null && pl.size() > 0 ){
				for( ProductMongo pm : pl ){
					if( StringUtils.equals(pm.getProductId(),  productId) ){
						address = pm.getOrgName();
						break;
					}
				}
				return address;
			}
			return address;
		}
		return address;
	}
	
}
