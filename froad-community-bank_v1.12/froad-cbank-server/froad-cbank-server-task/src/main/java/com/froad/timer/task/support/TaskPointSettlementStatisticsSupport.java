package com.froad.timer.task.support;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.db.mongo.OrderMongoService;
import com.froad.db.mongo.PointSettlementDetailMongoService;
import com.froad.db.mongo.SettlementMongoService;
import com.froad.db.mongo.SubOrderMongoService;
import com.froad.db.mongo.impl.OrderMongoServiceImpl;
import com.froad.db.mongo.impl.PointSettlementDetailMongoServiceImpl;
import com.froad.db.mongo.impl.SettlementMongoServiceImpl;
import com.froad.db.mongo.impl.SubOrderMongoServiceImpl;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.PaymentMethod;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.PointSettlementDetailMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.Settlement;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.Arith;
import com.froad.util.DateUtil;
import com.froad.util.PropertiesUtil;

/**
 * 积分结算统计类
 * Description : TODO<br/>
 * Project Name : froad-cbank-server-task<br/>
 * File Name : TaskPointSettlementStatisticsSupport.java<br/>
 * 
 * Date : 2015年12月23日 下午6:32:36 <br/> 
 * @author KaiweiXiang
 * @version
 */
public class TaskPointSettlementStatisticsSupport{
	
	private SettlementMongoService settlementMongoService = new SettlementMongoServiceImpl();
	private PointSettlementDetailMongoService psdmService = new PointSettlementDetailMongoServiceImpl();
	private OrderMongoService orderService = new OrderMongoServiceImpl();
	private SubOrderMongoService subOrderService = new SubOrderMongoServiceImpl();
	private final static int pageSize = 5000;//每次统计查询的条数
	/**
	 * 任务运行的入口方法
	 * Function : taskStart<br/>
	 * 2015年12月31日 上午11:34:15 <br/>
	 *  
	 * @author KaiweiXiang
	 */
	@SuppressWarnings("unchecked")
	public int taskStart() {
		//查询当前表中的最大统计时间
		long startTime = this.getStartTime();
		
		//统计查询的结束时间：T-1的23:59:59
		long endTime = this.getEndTime();
		
		LogCvt.info("积分结算明细统计-统计时间段，startTime=" + startTime + ",endTime=" + endTime);
		if(startTime >= endTime){
			LogCvt.info("积分结算明细统计-统计时间开始时间大约等于结算时间，不做统计操作...");
			return 0;
		}
		
		//记录处理的记录总数
		int statisticsCount = 0;
		
		int pageNumber = 1;
		
		long firstRecordTime =0L;
		long lastRecordTime = 0L;
				
		MongoPage mongoPage = new MongoPage();
		mongoPage.setPageNumber(pageNumber);
		mongoPage.setPageSize(pageSize);
		mongoPage.setFirstRecordTime(firstRecordTime);
		mongoPage.setLastRecordTime(lastRecordTime);
		
		//查询结算表
		MongoPage resultMongoPage = settlementMongoService.queryPointSettlementSuccByPage(mongoPage, startTime, endTime);
		List<Settlement> list = (List<Settlement>) resultMongoPage.getItems();
		
		if(list == null || list.size() == 0){
			return 0;
		}
		
		//将查询的结果进行组装入积分结算统计表
		doStatistics(list);
		statisticsCount += list.size();
		
		//如果查询结算表的记录大于一页，继续循环统计
		int totalPage = (resultMongoPage.getTotalCount()-1)/pageSize+1;
		if (totalPage > 1) {
			for (int i = 2; i <= totalPage; i++) {
				mongoPage.setPageNumber(i);
				mongoPage.setPageSize(pageSize);
				mongoPage.setFirstRecordTime(resultMongoPage.getFirstRecordTime());
				mongoPage.setLastRecordTime(resultMongoPage.getLastRecordTime());
				
				//查询结算表
				resultMongoPage = settlementMongoService.queryPointSettlementSuccByPage(mongoPage, startTime, endTime);
				list = (List<Settlement>) resultMongoPage.getItems();
				
				//将查询的结果进行组装入积分结算统计表
				if(list == null || list.size() == 0){
					break;
				}
				doStatistics(list);
				statisticsCount += list.size();
			}
		}
		return statisticsCount;
	}
	
	//处理结算记录
	private void doStatistics(List<Settlement> list){
		
		LogCvt.info("结算明细统计-开始处理一批积分结算数据，size=" + list.size());
		
		//缓存订单信息
		Map<String,OrderMongo> orderCache = new HashMap<String, OrderMongo>();
		
		PointSettlementDetailMongo tmpObject;
		OrderMongo tmpOrder = null;
		for(Settlement settlement : list){
			
			//判断是否在统计表中存在记录，避免重复(结算ID+结算时间来查询)
			tmpObject = psdmService.findBySettlementId(settlement.getSettlementId(),settlement.getCreateTime());
			if(tmpObject != null){
				continue;
			}
			
			//从订单缓存信息中获取订单，如果没有缓存则查询mongo,并缓存起来
			if(orderCache.containsKey(settlement.getOrderId())){
				tmpOrder = orderCache.get(settlement.getOrderId());
			}else{
				tmpOrder = orderService.findByOrderId(settlement.getOrderId());
				orderCache.put(settlement.getOrderId(), tmpOrder);
			}
			
			//封装持久化对象
			tmpObject = createPointSettlementDetailMongo(settlement,tmpOrder);
			
			//保存到mongodb中
			psdmService.addPointSettlementDetail(tmpObject);
		}
		LogCvt.info("结算明细统计-处理完成一批积分结算数据，size=" + list.size());
	}

	//根据结算对象以及订单信息，封装积分结算明细持久化对象
	private PointSettlementDetailMongo createPointSettlementDetailMongo(Settlement sobj,OrderMongo order) {
		PointSettlementDetailMongo obj = new PointSettlementDetailMongo();
		obj.setCreateTime(sobj.getCreateTime());
		obj.setcTime(new Date().getTime());
		
		obj.setSettlementId(sobj.getSettlementId());
		obj.setClientId(sobj.getClientId());
		obj.setOrderId(sobj.getOrderId());
		obj.setMerchantId(sobj.getMerchantId());
		obj.setMerchantName(sobj.getMerchantName());
		obj.setProductId(sobj.getProductId());
		obj.setProductName(sobj.getProductName());
		
		//商品数量：普通购买+Vip购买
		int productQuantity = 0;
		if(sobj.getProductCount() != null){
			productQuantity += sobj.getProductCount();
		}
		if(sobj.getProductVipCount() != null){
			productQuantity += sobj.getProductVipCount();
		}
		obj.setProductQuantity(productQuantity);
		obj.setSettlementTotalPrice(sobj.getMoney());
		
		if("1".equals(sobj.getDeductiblePointType())){//联盟积分
			obj.setFroadPoint(sobj.getDeductiblePointValue());
			obj.setBankPoint(0);
			obj.setBankPointRate("");
		}else if("2".equals(sobj.getDeductiblePointType())){//银行积分
			obj.setFroadPoint(0);
			obj.setBankPoint(sobj.getDeductiblePointValue());
			obj.setBankPointRate(order.getPointRate());
		}
		
		//TODO
		//若纯积分支付，现金设置为0；若积分和现金组合支付，现金=总结算金额-积分抵扣的现金
		if(order.getPaymentMethod().equals(PaymentMethod.froadPoints.getCode()) 
				|| order.getPaymentMethod().equals(PaymentMethod.bankPoints.getCode()) ){
			obj.setCash(0);
		}else{
			obj.setCash(sobj.getPayCash());
		}
		
		obj.setIsQrcode(order.getIsQrcode());
		obj.setMemberCode(order.getMemberCode());
		obj.setMemberName(order.getMemberName());
		
		//面对面订单商品单价设置0，非面对面从子订单中获取
		if(order.getIsQrcode() == 1){
			obj.setProductPrice(0);
		}else{
			obj.setProductPrice(this.queryProductPrice(sobj.getSubOrderId(), sobj.getProductId()));
		}
		
		obj.setPaymentMethod(order.getPaymentMethod());
		
		
		//商品总价=商品单价*商品数量
		obj.setProductTotalPrice(Arith.mul(obj.getProductPrice() ,obj.getProductQuantity()));
		
		return obj;
	}



	 // 获取商品的单价，从子订单中的商品中获取
	private Integer queryProductPrice(String subOrderId,String productId){
		SubOrderMongo subOrder = subOrderService.findSubOrderMongo(subOrderId);
		if(subOrder == null){
			return 0;
		}
		
		Integer price = 0;
		List<ProductMongo> products = subOrder.getProducts();
		if(products != null && products.size() > 0){
			for(ProductMongo product: products){
				if(productId.equals(product.getProductId())){
					price = product.getMoney();
					break;
				}
			}
		}
		return price;
	}


	//获取统计的startTime，取统计表中的最大时间,如果统计表中为空，取T-2的日终时间
	private long getStartTime() {
		long startTime = psdmService.findMaxSettlementTime();
		if(startTime == 0){
			//上线时间：2016-03-03 凌晨
			return DateUtil.parse(DateUtil.DATE_FORMAT1, "2016-03-03").getTime();
			//TODO 测试
//			return getDateEndTime(-5);
		}
		return startTime;
	}
	
	//获取统计的endTime，取T-1的日终时间
	private long getEndTime(){
		//TODO 测试
//		return new Date().getTime();
		return getDateEndTime(-1);
	}
	
	//获取日终时间
	private long getDateEndTime(int n){
		Date preDate =  DateUtil.skipDateTime(new Date(), n);
		String preDateStr = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, preDate);
		return DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, preDateStr + " 23:59:59").getTime();
	}
	
	//测试方法
	public static void main(String[] args) {
		PropertiesUtil.load();
		
		new TaskPointSettlementStatisticsSupport().taskStart();;
	}
}
