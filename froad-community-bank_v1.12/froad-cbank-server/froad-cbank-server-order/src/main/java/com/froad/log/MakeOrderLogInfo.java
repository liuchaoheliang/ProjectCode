package com.froad.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.enums.RefundState;
import com.froad.enums.SettlementStatus;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.po.Ticket;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.PaymentMongo;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundPaymentInfo;
import com.froad.po.refund.RefundProduct;
import com.froad.po.settlement.Settlement;
import com.froad.thirdparty.util.DateUtil;
import com.froad.util.Checker;
import com.froad.util.EmptyChecker;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-11-4下午12:46:12 
 */
public class MakeOrderLogInfo {

	public static void main(String[] args) {
//		PropertiesUtil.load();
		
		Map<String, String> proMap = null;	
		String logPath = null;
		proMap = PropertiesUtil.loadProperties("init");
		logPath = new StringBuffer(proMap.get("log.path")).append("historylog/order/").toString();
		makeOrderLog(args[1],args[2],args[0],logPath);
		makePayLog(args[1],args[2],args[0],logPath);
		makeRefundLog(args[1],args[2],args[0],logPath);
		makeTicketLog(args[1],args[2],args[0],logPath);
		makeSettlementLog(args[1],args[2],args[0],logPath);
	//	makePayLog("2015-09-29","2015-11-23","chongqing","");
//		System.out.println("time1="+DateUtil.parse("yyyy-MM-dd HH:mm:ss", "2015-10-01 00:00:00").getTime());//1443628800000
//		System.out.println("time1="+DateUtil.parse("yyyy-MM-dd HH:mm:ss", "2015-10-31 23:59:59").getTime());//1446307199000
//		String t14 = DateUtil.formatDateTime("yyyy-MM-dd",1448509109241l);
	}
	
	public static void makePayLog(String startTime,String endTime,String clientId,String logPath){
		Long start_time = DateUtil.parse("yyyy-MM-dd HH:mm:ss", startTime+" 00:00:00").getTime();
		Long end_time = DateUtil.parse("yyyy-MM-dd HH:mm:ss", endTime+" 23:59:59").getTime();
//		Long start_time = 1443628800000l;
//		Long end_time = 1446307199000l;
		BufferedWriter addPayWriter = null;
		BufferedWriter updatePayWriter = null;
		BufferedWriter takePayWriter = null;
		long totalChunk = 0;
		long tmpStartTime = 0;
		long tmpEndTime = 0;
		long daysPerChunk = 7;
		List<OrderMongo> orderList = null;
		
		totalChunk = ((end_time + 1 - start_time) / 86400000) / daysPerChunk + 1;
		
		DBObject where = new BasicDBObject();
//		List<Object> listTypes = new ArrayList<Object>();
//		listTypes.add(new BasicDBObject("client_id", clientId));
//		where.put(QueryOperators.OR, listTypes);
//		BasicDBList values = new BasicDBList();
//		values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,start_time)));
//		values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,end_time)));
//		where.put(QueryOperators.AND,values);
		
		where.put("client_id", clientId);
		where.put("create_time", new BasicDBObject(QueryOperators.GTE,start_time).append(QueryOperators.LTE,end_time));
		MongoManager mongoManager = new MongoManager(); 
//		where.put("_id", "0D821EE18001");
		try {
//			addPayWriter = new BufferedWriter(new FileWriter(new File("E:\\log\\new\\pay_success.log")));
//			updatePayWriter = new BufferedWriter(new FileWriter(new File("E:\\log\\new\\pay_fail.log")));
//			takePayWriter = new BufferedWriter(new FileWriter(new File("E:\\log\\new\\pay_add.log")));
			addPayWriter = new BufferedWriter(new FileWriter(new File(logPath+"pay_success"+startTime+"_"+endTime+".log")));
			updatePayWriter = new BufferedWriter(new FileWriter(new File(logPath+"pay_fail"+startTime+"_"+endTime+".log")));
			takePayWriter = new BufferedWriter(new FileWriter(new File(logPath+"pay_add"+startTime+"_"+endTime+".log")));
			StringBuffer addBigPayStr=null;
			StringBuffer updateBigPayStr=null;
			StringBuffer takeBigPayStr=null;
			SubOrderMongo subOrder = null;
			OrderMongo order = null;
			
			tmpStartTime = start_time;
			int num = 0;
			for (long n = 0; n < totalChunk; n++){
				// 每次处理7天数据
				tmpEndTime = start_time + (n + 1) * 86400000 * daysPerChunk - 1;
				if (tmpEndTime > end_time){
					tmpEndTime = end_time;
				}
				where.put("create_time", new BasicDBObject(QueryOperators.GTE,tmpStartTime).append(QueryOperators.LTE,tmpEndTime));
				orderList=(List<OrderMongo>)mongoManager.findAll(where, MongoTableName.CB_ORDER,OrderMongo.class);
				num +=orderList.size();
				System.out.println("=============================================num="+num);
				tmpStartTime = tmpEndTime + 1;
				
				if (Checker.isEmpty(orderList)){
					continue;
				}
				
				for (int i = 0; i < orderList.size(); i++){
					order = orderList.get(i);
					if(order!=null && order.getIsQrcode()==1){//面对面订单
						//支付日志
						//面对面订单
						DBObject whereSubOrder = new BasicDBObject();
						whereSubOrder.put("order_id",order.getOrderId());
						whereSubOrder.put("payment_reason","2");//支付记录（不包括退款）
						whereSubOrder.put("is_enable",true);//有效支付记录(不包括历史支付记录)
						List<PaymentMongo> paymentList=(List<PaymentMongo>)mongoManager.findAll(whereSubOrder, MongoTableName.CB_PAYMENT, PaymentMongo.class);
						String paymentType = "";
						String paymentStatus = null;
						String billNo=null;
						String paymentValue="";
						String paymentReason=null;
						boolean paymentSuccess=false;
						boolean paymentFail=false;
						PaymentMongo paymentMongo=null;
	//支付方式:1-方付通积分支付，2-银行积分支付，3-快捷支付，4-贴膜卡支付，5-方付通积分+快捷支付，6-方付通积分+贴膜卡支付，7-银行积分+快捷支付，8-银行积分+贴膜卡支付
//						paymentType  Integer
//						1-方付通积分
//						2-现金
//						3-银行积分
//						paymentTypeDetails  Integer
//						0-积分支付
//						20-贴膜卡支付
//						51-快捷支付
//						55-即时支付（用于结算）
						if(paymentList!=null && paymentList.size()>0){
							if(paymentList.size()==1){
								PaymentMongo paytmp = paymentList.get(0);
								if("1".equals(String.valueOf(paytmp.getPaymentType()))){
									paymentType="1";
									//paymentValue=String.valueOf(paytmp.getPaymentValue()==null||"".equals(paytmp.getPaymentValue())?0:paytmp.getPaymentValue())+"+"+"0";
								}else if("3".equals(String.valueOf(paytmp.getPaymentType()))){
									paymentType="2";
									//paymentValue=String.valueOf(paytmp.getPaymentValue()==null||"".equals(paytmp.getPaymentValue())?0:paytmp.getPaymentValue())+"+"+"0";
								}else if("2".equals(String.valueOf(paytmp.getPaymentType())) && "51".equals(String.valueOf(paytmp.getPaymentTypeDetails()))){
									paymentType="3";
									//paymentValue="0"+"+"+String.valueOf(paytmp.getPaymentValue()==null||"".equals(paytmp.getPaymentValue())?0:paytmp.getPaymentValue());
								}else if("2".equals(String.valueOf(paytmp.getPaymentType())) && "20".equals(String.valueOf(paytmp.getPaymentTypeDetails()))){
									paymentType="4";
									//paymentValue="0"+"+"+String.valueOf(paytmp.getPaymentValue()==null||"".equals(paytmp.getPaymentValue())?0:paytmp.getPaymentValue());
								}
								if("4".equals(paytmp.getPaymentStatus())){//支付成功
									paymentSuccess = true;
								}
								if("5".equals(paytmp.getPaymentStatus())){//支付失败
									paymentFail = true;
								}
							}else{
								PaymentMongo paytmp1 = paymentList.get(0);
								PaymentMongo paytmp2 = paymentList.get(1);
//								for(PaymentMongo pay:paymentList){ 
//									//pay.getPaymentType()
//								}	
								if("1".equals(String.valueOf(paytmp1.getPaymentType())) || "3".equals(String.valueOf(paytmp1.getPaymentType()))){
									//paymentValue=String.valueOf(paytmp1.getPaymentValue()==null||"".equals(paytmp1.getPaymentValue())?0:paytmp1.getPaymentValue())+"+"+String.valueOf(paytmp2.getPaymentValue()==null||"".equals(paytmp2.getPaymentValue())?0:paytmp2.getPaymentValue());
								}else{
									//paymentValue=String.valueOf(paytmp2.getPaymentValue()==null||"".equals(paytmp2.getPaymentValue())?0:paytmp2.getPaymentValue())+"+"+String.valueOf(paytmp1.getPaymentValue()==null||"".equals(paytmp1.getPaymentValue())?0:paytmp1.getPaymentValue());
								}
								if(("1".equals(String.valueOf(paytmp1.getPaymentType())) && "51".equals(String.valueOf(paytmp2.getPaymentTypeDetails()))) ||
										("1".equals(String.valueOf(paytmp2.getPaymentType())) && "51".equals(String.valueOf(paytmp1.getPaymentTypeDetails())))){
									paymentType="5";
								}else if(("1".equals(String.valueOf(paytmp1.getPaymentType())) && "20".equals(String.valueOf(paytmp2.getPaymentTypeDetails()))) ||
										("1".equals(String.valueOf(paytmp2.getPaymentType())) && "20".equals(String.valueOf(paytmp1.getPaymentTypeDetails())))){
									paymentType="6";
								}else if(("3".equals(String.valueOf(paytmp1.getPaymentType())) && "51".equals(String.valueOf(paytmp2.getPaymentTypeDetails()))) ||
										("3".equals(String.valueOf(paytmp2.getPaymentType())) && "51".equals(String.valueOf(paytmp1.getPaymentTypeDetails())))){
									paymentType="7";
								}else if(("3".equals(String.valueOf(paytmp1.getPaymentType())) && "20".equals(String.valueOf(paytmp2.getPaymentTypeDetails()))) ||
										("3".equals(String.valueOf(paytmp2.getPaymentType())) && "20".equals(String.valueOf(paytmp1.getPaymentTypeDetails())))){
									paymentType="8";
								}
								if("4".equals(paytmp1.getPaymentStatus()) && "4".equals(paytmp2.getPaymentStatus())){//支付成功
									paymentSuccess = true;
								}
								if("5".equals(paytmp1.getPaymentStatus()) || "5".equals(paytmp2.getPaymentStatus())){//支付失败
									paymentFail = true;
								}
							}
							
//							if("1".equals(order.getOrderStatus())){
								takeBigPayStr = new StringBuffer();
								takeBigPayStr.append("{");
								takeBigPayStr.append("\"key\": { \"order_id\":\"");
								takeBigPayStr.append(order.getOrderId());
								takeBigPayStr.append("\"},");
								takeBigPayStr.append("\"action\": \"PAYMENTADD\",");
								takeBigPayStr.append("\"client_id\": \"");
								takeBigPayStr.append(order.getClientId());
								takeBigPayStr.append("\",");
								takeBigPayStr.append("\"time\": ");
								takeBigPayStr.append(paymentList.get(0).getCreateTime().getTime());
								takeBigPayStr.append(",");
								takeBigPayStr.append("\"data\": {");
								takeBigPayStr.append("\"order_id\":\"");
								takeBigPayStr.append(order.getOrderId());
								takeBigPayStr.append("\",");
								takeBigPayStr.append("\"sub_orders\": [");	
								for (int j = 0; j < 1; j++){
									if(j==0){
										takeBigPayStr.append("{");
									}else{
										takeBigPayStr.append(",{");
									}
									takeBigPayStr.append("\"sub_order_id\":\"");
									takeBigPayStr.append(order.getOrderId());//paymentId默认空
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"order_type\":\"");
									takeBigPayStr.append("0");
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"org_code\":\"");
									String orgCode = "";
								      if(orgCode==null || "".equals(orgCode)){
										  orgCode=order.getLorgCode();
								      }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=order.getTorgCode();
									  }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=order.getSorgCode();
									  }
									takeBigPayStr.append(orgCode);
									takeBigPayStr.append("\"}");
								}	
								takeBigPayStr.append("],");
								takeBigPayStr.append("\"payments\": [");	
								for (int j = 0; j < paymentList.size(); j++){
									paymentMongo = paymentList.get(j);
									if(j==0){
										takeBigPayStr.append("{");
									}else{
										takeBigPayStr.append(",{");
									}
									takeBigPayStr.append("\"payment_id\":\"");
									takeBigPayStr.append(paymentMongo.getPaymentId());//paymentId默认空
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"order_id\":\"");
									takeBigPayStr.append(order.getOrderId());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_time\":\"");
									takeBigPayStr.append(paymentMongo.getCreateTime().getTime());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_status\":\"");
									takeBigPayStr.append(paymentMongo.getPaymentStatus());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"bill_no\":\"");
									takeBigPayStr.append(paymentMongo.getBillNo());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_org_no\":\"");
									takeBigPayStr.append(paymentMongo.getPaymentOrgNo());
									takeBigPayStr.append("\",");//TODO org_name
									takeBigPayStr.append("\"payment_reason\":\"");
									takeBigPayStr.append(paymentMongo.getPaymentReason());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_type\":\"");
									takeBigPayStr.append(paymentType);
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_type_details\": \"");
									takeBigPayStr.append(paymentMongo.getPaymentTypeDetails());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_value\": \"");
									takeBigPayStr.append(String.valueOf(paymentMongo.getPaymentValue()==null||"".equals(paymentMongo.getPaymentValue())?0:paymentMongo.getPaymentValue()));
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"point_rate\": \"");
									takeBigPayStr.append(paymentMongo.getPointRate());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"from_account_no\": \"");
									takeBigPayStr.append(paymentMongo.getFromAccountNo()==null || "".equals(paymentMongo.getFromAccountNo())?"":paymentMongo.getFromAccountNo());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"from_user_name\": \"");
									takeBigPayStr.append(paymentMongo.getFromUserName()==null || "".equals(paymentMongo.getFromUserName())?"":paymentMongo.getFromUserName());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"to_account_no\": \"");
									takeBigPayStr.append(paymentMongo.getSettleToAccountNo()==null || "".equals(paymentMongo.getSettleToAccountNo())?"":paymentMongo.getSettleToAccountNo());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"to_account_name\": \"");
									takeBigPayStr.append(paymentMongo.getSettleToAccountName()==null || "".equals(paymentMongo.getSettleToAccountName())?"":paymentMongo.getSettleToAccountName());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"result_code\": \"");
									takeBigPayStr.append(paymentMongo.getResultCode());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"remark\": \"");
									takeBigPayStr.append(paymentMongo.getRemark());//subOrder
									takeBigPayStr.append("\"}");
								}	
								takeBigPayStr.append("]");
								takeBigPayStr.append("}");
								takeBigPayStr.append("}");
								takeBigPayStr.append("\n");
							takePayWriter.write(takeBigPayStr.toString());
//							}else 
							if(paymentSuccess){
								addBigPayStr = new StringBuffer();
								addBigPayStr.append("{");
								addBigPayStr.append("\"key\": { \"order_id\":\"");
								addBigPayStr.append(order.getOrderId());
								addBigPayStr.append("\"},");
								addBigPayStr.append("\"action\": \"PAYMENTSUCCESS\",");
								addBigPayStr.append("\"client_id\": \"");
								addBigPayStr.append(order.getClientId());
								addBigPayStr.append("\",");
								addBigPayStr.append("\"time\": ");
								addBigPayStr.append(paymentList.get(0).getCreateTime().getTime());
								addBigPayStr.append(",");
								addBigPayStr.append("\"data\": {");
								addBigPayStr.append("\"order_id\":\"");
								addBigPayStr.append(order.getOrderId());
								addBigPayStr.append("\",");
								addBigPayStr.append("\"sub_orders\": [");	
								for (int j = 0; j < 1; j++){
									if(j==0){
										addBigPayStr.append("{");
									}else{
										addBigPayStr.append(",{");
									}
									addBigPayStr.append("\"sub_order_id\":\"");
									addBigPayStr.append(order.getOrderId());//paymentId默认空
									addBigPayStr.append("\",");
									addBigPayStr.append("\"order_type\":\"");
									addBigPayStr.append("0");
									addBigPayStr.append("\",");
									addBigPayStr.append("\"org_code\":\"");
									String orgCode = "";
								      if(orgCode==null || "".equals(orgCode)){
										  orgCode=order.getLorgCode();
								      }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=order.getTorgCode();
									  }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=order.getSorgCode();
									  }
									addBigPayStr.append(orgCode);
									addBigPayStr.append("\"}");
								}	
								addBigPayStr.append("],");
								addBigPayStr.append("\"payments\": [");	
								for (int j = 0; j < paymentList.size(); j++){
									paymentMongo = paymentList.get(j);
									if(j==0){
										addBigPayStr.append("{");
									}else{
										addBigPayStr.append(",{");
									}
									addBigPayStr.append("\"payment_id\":\"");
									addBigPayStr.append(paymentMongo.getPaymentId());//paymentId默认空
									addBigPayStr.append("\",");
									addBigPayStr.append("\"order_id\":\"");
									addBigPayStr.append(order.getOrderId());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_time\":\"");
									addBigPayStr.append(paymentMongo.getCreateTime().getTime());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_status\":\"");
									addBigPayStr.append(paymentMongo.getPaymentStatus());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"bill_no\":\"");
									addBigPayStr.append(paymentMongo.getBillNo());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_org_no\":\"");
									addBigPayStr.append(paymentMongo.getPaymentOrgNo());
									addBigPayStr.append("\",");//TODO org_name
									addBigPayStr.append("\"payment_reason\":\"");
									addBigPayStr.append(paymentMongo.getPaymentReason());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_type\":\"");
									addBigPayStr.append(paymentType);
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_type_details\": \"");
									addBigPayStr.append(paymentMongo.getPaymentTypeDetails());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_value\": \"");
									addBigPayStr.append(String.valueOf(paymentMongo.getPaymentValue()==null||"".equals(paymentMongo.getPaymentValue())?0:paymentMongo.getPaymentValue()));
									addBigPayStr.append("\",");
									addBigPayStr.append("\"point_rate\": \"");
									addBigPayStr.append(paymentMongo.getPointRate());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"from_account_no\": \"");
									addBigPayStr.append(paymentMongo.getFromAccountNo()==null || "".equals(paymentMongo.getFromAccountNo())?"":paymentMongo.getFromAccountNo());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"from_user_name\": \"");
									addBigPayStr.append(paymentMongo.getFromUserName()==null || "".equals(paymentMongo.getFromUserName())?"":paymentMongo.getFromUserName());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"to_account_no\": \"");
									addBigPayStr.append(paymentMongo.getSettleToAccountNo()==null || "".equals(paymentMongo.getSettleToAccountNo())?"":paymentMongo.getSettleToAccountNo());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"to_account_name\": \"");
									addBigPayStr.append(paymentMongo.getSettleToAccountName()==null || "".equals(paymentMongo.getSettleToAccountName())?"":paymentMongo.getSettleToAccountName());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"result_code\": \"");
									addBigPayStr.append(paymentMongo.getResultCode());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"remark\": \"");
									addBigPayStr.append(paymentMongo.getRemark());//subOrder
									addBigPayStr.append("\"}");
								}	
								addBigPayStr.append("]");
								addBigPayStr.append("}");
								addBigPayStr.append("}");
								addBigPayStr.append("\n");
								addPayWriter.write(addBigPayStr.toString());
							}
							if(paymentFail){
								updateBigPayStr = new StringBuffer();
								updateBigPayStr.append("{");
								updateBigPayStr.append("\"key\": { \"order_id\":\"");
								updateBigPayStr.append(order.getOrderId());
								updateBigPayStr.append("\"},");
								updateBigPayStr.append("\"action\": \"PAYMENTFAIL\",");
								updateBigPayStr.append("\"client_id\": \"");
								updateBigPayStr.append(order.getClientId());
								updateBigPayStr.append("\",");
								updateBigPayStr.append("\"time\": ");
								updateBigPayStr.append(paymentList.get(0).getCreateTime().getTime());
								updateBigPayStr.append(",");
								updateBigPayStr.append("\"data\": {");
								updateBigPayStr.append("\"order_id\":\"");
								updateBigPayStr.append(order.getOrderId());
								updateBigPayStr.append("\",");
								updateBigPayStr.append("\"sub_orders\": [");	
								for (int j = 0; j < 1; j++){
									if(j==0){
										updateBigPayStr.append("{");
									}else{
										updateBigPayStr.append(",{");
									}
									updateBigPayStr.append("\"sub_order_id\":\"");
									updateBigPayStr.append(order.getOrderId());//paymentId默认空
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"order_type\":\"");
									updateBigPayStr.append("0");
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"org_code\":\"");
									String orgCode = "";
								      if(orgCode==null || "".equals(orgCode)){
										  orgCode=order.getLorgCode();
								      }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=order.getTorgCode();
									  }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=order.getSorgCode();
									  }
									updateBigPayStr.append(orgCode);
									updateBigPayStr.append("\"}");
								}	
								updateBigPayStr.append("],");
								updateBigPayStr.append("\"payments\": [");	
								for (int j = 0; j < paymentList.size(); j++){
									paymentMongo = paymentList.get(j);
									if(j==0){
										updateBigPayStr.append("{");
									}else{
										updateBigPayStr.append(",{");
									}
									updateBigPayStr.append("\"payment_id\":\"");
									updateBigPayStr.append(paymentMongo.getPaymentId());//paymentId默认空
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"order_id\":\"");
									updateBigPayStr.append(order.getOrderId());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_time\":\"");
									updateBigPayStr.append(paymentMongo.getCreateTime().getTime());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_status\":\"");
									updateBigPayStr.append(paymentMongo.getPaymentStatus());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"bill_no\":\"");
									updateBigPayStr.append(paymentMongo.getBillNo());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_org_no\":\"");
									updateBigPayStr.append(paymentMongo.getPaymentOrgNo());
									updateBigPayStr.append("\",");//TODO org_name
									updateBigPayStr.append("\"payment_reason\":\"");
									updateBigPayStr.append(paymentMongo.getPaymentReason());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_type\":\"");
									updateBigPayStr.append(paymentType);
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_type_details\": \"");
									updateBigPayStr.append(paymentMongo.getPaymentTypeDetails());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_value\": \"");
									updateBigPayStr.append(String.valueOf(paymentMongo.getPaymentValue()==null||"".equals(paymentMongo.getPaymentValue())?0:paymentMongo.getPaymentValue()));
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"point_rate\": \"");
									updateBigPayStr.append(paymentMongo.getPointRate());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"from_account_no\": \"");
									updateBigPayStr.append(paymentMongo.getFromAccountNo()==null || "".equals(paymentMongo.getFromAccountNo())?"":paymentMongo.getFromAccountNo());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"from_user_name\": \"");
									updateBigPayStr.append(paymentMongo.getFromUserName()==null || "".equals(paymentMongo.getFromUserName())?"":paymentMongo.getFromUserName());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"to_account_no\": \"");
									updateBigPayStr.append(paymentMongo.getSettleToAccountNo()==null || "".equals(paymentMongo.getSettleToAccountNo())?"":paymentMongo.getSettleToAccountNo());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"to_account_name\": \"");
									updateBigPayStr.append(paymentMongo.getSettleToAccountName()==null || "".equals(paymentMongo.getSettleToAccountName())?"":paymentMongo.getSettleToAccountName());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"result_code\": \"");
									updateBigPayStr.append(paymentMongo.getResultCode());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"remark\": \"");
									updateBigPayStr.append(paymentMongo.getRemark());//subOrder
									updateBigPayStr.append("\"}");
								}	
								updateBigPayStr.append("]");
								updateBigPayStr.append("}");
								updateBigPayStr.append("}");
								updateBigPayStr.append("\n");
								updatePayWriter.write(updateBigPayStr.toString());
							}
						}
					}else{


						//面对面之外的订单类型的日志
						DBObject whereSubOrder1 = new BasicDBObject();
						whereSubOrder1.put("order_id",order.getOrderId());
						List<SubOrderMongo> subOrderList2=(List<SubOrderMongo>)mongoManager.findAll(whereSubOrder1, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
						DBObject whereSubOrder2 = new BasicDBObject();
						whereSubOrder2.put("order_id",order.getOrderId());
						whereSubOrder2.put("payment_reason","2");
						whereSubOrder2.put("is_enable",true);
						List<PaymentMongo> paymentList=(List<PaymentMongo>)mongoManager.findAll(whereSubOrder2, MongoTableName.CB_PAYMENT, PaymentMongo.class);
						String paymentType = "";
						String paymentValue="";
						boolean paymentSuccess2=false;
						boolean paymentFail2=false;
						SubOrderMongo suborder=null;
						PaymentMongo paymentMongo=null;
						if(paymentList!=null && paymentList.size()>0){
							if(paymentList.size()==1){
								PaymentMongo paytmp = paymentList.get(0);
								if("1".equals(String.valueOf(paytmp.getPaymentType()))){
									paymentType="1";
									//paymentValue=String.valueOf(paytmp.getPaymentValue()==null||"".equals(paytmp.getPaymentValue())?0:paytmp.getPaymentValue())+"+"+"0";
								}else if("3".equals(String.valueOf(paytmp.getPaymentType()))){
									paymentType="2";
									//paymentValue=String.valueOf(paytmp.getPaymentValue()==null||"".equals(paytmp.getPaymentValue())?0:paytmp.getPaymentValue())+"+"+"0";
								}else if("2".equals(String.valueOf(paytmp.getPaymentType())) && "51".equals(String.valueOf(paytmp.getPaymentTypeDetails()))){
									paymentType="3";
									//paymentValue="0"+"+"+String.valueOf(paytmp.getPaymentValue()==null||"".equals(paytmp.getPaymentValue())?0:paytmp.getPaymentValue());
								}else if("2".equals(String.valueOf(paytmp.getPaymentType())) && "20".equals(String.valueOf(paytmp.getPaymentTypeDetails()))){
									paymentType="4";
									//paymentValue="0"+"+"+String.valueOf(paytmp.getPaymentValue()==null||"".equals(paytmp.getPaymentValue())?0:paytmp.getPaymentValue());
								}
								if("4".equals(paytmp.getPaymentStatus())){//支付成功
									paymentSuccess2 = true;
								}
								if("5".equals(paytmp.getPaymentStatus())){//支付失败
									paymentFail2 = true;
								}
							}else{
								PaymentMongo paytmp1 = paymentList.get(0);
								PaymentMongo paytmp2 = paymentList.get(1);
//								for(PaymentMongo pay:paymentList){ 
//									//pay.getPaymentType()
//								}		
								if("1".equals(String.valueOf(paytmp1.getPaymentType())) || "3".equals(String.valueOf(paytmp1.getPaymentType()))){
									//paymentValue=String.valueOf(paytmp1.getPaymentValue()==null||"".equals(paytmp1.getPaymentValue())?0:paytmp1.getPaymentValue())+"+"+String.valueOf(paytmp2.getPaymentValue()==null||"".equals(paytmp2.getPaymentValue())?0:paytmp2.getPaymentValue());
								}else{
									//paymentValue=String.valueOf(paytmp2.getPaymentValue()==null||"".equals(paytmp2.getPaymentValue())?0:paytmp2.getPaymentValue())+"+"+String.valueOf(paytmp1.getPaymentValue()==null||"".equals(paytmp1.getPaymentValue())?0:paytmp1.getPaymentValue());
								}
								if(("1".equals(String.valueOf(paytmp1.getPaymentType())) && "51".equals(String.valueOf(paytmp2.getPaymentTypeDetails()))) ||
										("1".equals(String.valueOf(paytmp2.getPaymentType())) && "51".equals(String.valueOf(paytmp1.getPaymentTypeDetails())))){
									paymentType="5";
								}else if(("1".equals(String.valueOf(paytmp1.getPaymentType())) && "20".equals(String.valueOf(paytmp2.getPaymentTypeDetails()))) ||
										("1".equals(String.valueOf(paytmp2.getPaymentType())) && "20".equals(String.valueOf(paytmp1.getPaymentTypeDetails())))){
									paymentType="6";
								}else if(("3".equals(String.valueOf(paytmp1.getPaymentType())) && "51".equals(String.valueOf(paytmp2.getPaymentTypeDetails()))) ||
										("3".equals(String.valueOf(paytmp2.getPaymentType())) && "51".equals(String.valueOf(paytmp1.getPaymentTypeDetails())))){
									paymentType="7";
								}else if(("3".equals(String.valueOf(paytmp1.getPaymentType())) && "20".equals(String.valueOf(paytmp2.getPaymentTypeDetails()))) ||
										("3".equals(String.valueOf(paytmp2.getPaymentType())) && "20".equals(String.valueOf(paytmp1.getPaymentTypeDetails())))){
									paymentType="8";
								}
								
								if("4".equals(paytmp1.getPaymentStatus()) && "4".equals(paytmp2.getPaymentStatus())){//支付成功
									paymentSuccess2 = true;
								}
								if("5".equals(paytmp1.getPaymentStatus()) || "5".equals(paytmp2.getPaymentStatus())){//支付失败
									paymentFail2 = true;
								}
							}
							
//							if(subOrderList2!=null && subOrderList2.size() >0){						
//								for(SubOrderMongo suborder:subOrderList2){}
//							}

//							if("1".equals(order.getOrderStatus())){
								takeBigPayStr = new StringBuffer();
								takeBigPayStr.append("{");
								takeBigPayStr.append("\"key\": { \"order_id\":\"");
								takeBigPayStr.append(order.getOrderId());
								takeBigPayStr.append("\"},");
								takeBigPayStr.append("\"action\": \"PAYMENTADD\",");
								takeBigPayStr.append("\"client_id\": \"");
								takeBigPayStr.append(order.getClientId());
								takeBigPayStr.append("\",");
								takeBigPayStr.append("\"time\": ");
								takeBigPayStr.append(paymentList.get(0).getCreateTime().getTime());
								takeBigPayStr.append(",");
								takeBigPayStr.append("\"data\": {");
								takeBigPayStr.append("\"order_id\":\"");
								takeBigPayStr.append(order.getOrderId());
								takeBigPayStr.append("\",");
								takeBigPayStr.append("\"sub_orders\": [");	
								for (int j = 0; j < subOrderList2.size(); j++){
									suborder = subOrderList2.get(j);
									if(j==0){
										takeBigPayStr.append("{");
									}else{
										takeBigPayStr.append(",{");
									}
									takeBigPayStr.append("\"sub_order_id\":\"");
									takeBigPayStr.append(suborder.getSubOrderId());//paymentId默认空
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"order_type\":\"");
									takeBigPayStr.append(suborder.getType());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"org_code\":\"");
									String orgCode = "";
								      if(orgCode==null || "".equals(orgCode)){
										  orgCode=suborder.getLorgCode();
								      }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=suborder.getTorgCode();
									  }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=suborder.getSorgCode();
									  }
									takeBigPayStr.append(orgCode);
									takeBigPayStr.append("\"}");
								}	
								takeBigPayStr.append("],");
								takeBigPayStr.append("\"payments\": [");	
								for (int j = 0; j < paymentList.size(); j++){
									paymentMongo = paymentList.get(j);
									if(j==0){
										takeBigPayStr.append("{");
									}else{
										takeBigPayStr.append(",{");
									}
									takeBigPayStr.append("\"payment_id\":\"");
									takeBigPayStr.append(paymentMongo.getPaymentId());//paymentId默认空
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"order_id\":\"");
									takeBigPayStr.append(order.getOrderId());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_time\":\"");
									takeBigPayStr.append(paymentMongo.getCreateTime().getTime());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_status\":\"");
									takeBigPayStr.append(paymentMongo.getPaymentStatus());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"bill_no\":\"");
									takeBigPayStr.append(paymentMongo.getBillNo());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_org_no\":\"");
									takeBigPayStr.append(paymentMongo.getPaymentOrgNo());
									takeBigPayStr.append("\",");//TODO org_name
									takeBigPayStr.append("\"payment_reason\":\"");
									takeBigPayStr.append(paymentMongo.getPaymentReason());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_type\":\"");
									takeBigPayStr.append(paymentType);
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_type_details\": \"");
									takeBigPayStr.append(paymentMongo.getPaymentTypeDetails());
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"payment_value\": \"");
									takeBigPayStr.append(String.valueOf(paymentMongo.getPaymentValue()==null||"".equals(paymentMongo.getPaymentValue())?0:paymentMongo.getPaymentValue()));
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"point_rate\": \"");
									takeBigPayStr.append(paymentMongo.getPointRate());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"from_account_no\": \"");
									takeBigPayStr.append(paymentMongo.getFromAccountNo()==null || "".equals(paymentMongo.getFromAccountNo())?"":paymentMongo.getFromAccountNo());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"from_user_name\": \"");
									takeBigPayStr.append(paymentMongo.getFromUserName()==null || "".equals(paymentMongo.getFromUserName())?"":paymentMongo.getFromUserName());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"to_account_no\": \"");
									takeBigPayStr.append(paymentMongo.getSettleToAccountNo()==null || "".equals(paymentMongo.getSettleToAccountNo())?"":paymentMongo.getSettleToAccountNo());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"to_account_name\": \"");
									takeBigPayStr.append(paymentMongo.getSettleToAccountName()==null || "".equals(paymentMongo.getSettleToAccountName())?"":paymentMongo.getSettleToAccountName());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"result_code\": \"");
									takeBigPayStr.append(paymentMongo.getResultCode());//subOrder
									takeBigPayStr.append("\",");
									takeBigPayStr.append("\"remark\": \"");
									takeBigPayStr.append(paymentMongo.getRemark());//subOrder
									takeBigPayStr.append("\"}");
								}	
								takeBigPayStr.append("]");
								takeBigPayStr.append("}");
								takeBigPayStr.append("}");
								takeBigPayStr.append("\n");
							takePayWriter.write(takeBigPayStr.toString());
//							}else 
							if(paymentSuccess2){
								addBigPayStr = new StringBuffer();
								addBigPayStr.append("{");
								addBigPayStr.append("\"key\": { \"order_id\":\"");
								addBigPayStr.append(order.getOrderId());
								addBigPayStr.append("\"},");
								addBigPayStr.append("\"action\": \"PAYMENTSUCCESS\",");
								addBigPayStr.append("\"client_id\": \"");
								addBigPayStr.append(order.getClientId());
								addBigPayStr.append("\",");
								addBigPayStr.append("\"time\": ");
								addBigPayStr.append(paymentList.get(0).getCreateTime().getTime());
								addBigPayStr.append(",");
								addBigPayStr.append("\"data\": {");
								addBigPayStr.append("\"order_id\":\"");
								addBigPayStr.append(order.getOrderId());
								addBigPayStr.append("\",");
								addBigPayStr.append("\"sub_orders\": [");	
								for (int j = 0; j < subOrderList2.size(); j++){
									suborder = subOrderList2.get(j);
									if(j==0){
										addBigPayStr.append("{");
									}else{
										addBigPayStr.append(",{");
									}
									addBigPayStr.append("\"sub_order_id\":\"");
									addBigPayStr.append(suborder.getSubOrderId());//paymentId默认空
									addBigPayStr.append("\",");
									addBigPayStr.append("\"order_type\":\"");
									addBigPayStr.append(suborder.getType());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"org_code\":\"");
									String orgCode = "";
								      if(orgCode==null || "".equals(orgCode)){
										  orgCode=suborder.getLorgCode();
								      }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=suborder.getTorgCode();
									  }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=suborder.getSorgCode();
									  }
									addBigPayStr.append(orgCode);
									addBigPayStr.append("\"}");
								}	
								addBigPayStr.append("],");
								addBigPayStr.append("\"payments\": [");	
								for (int j = 0; j < paymentList.size(); j++){
									paymentMongo = paymentList.get(j);
									if(j==0){
										addBigPayStr.append("{");
									}else{
										addBigPayStr.append(",{");
									}
									addBigPayStr.append("\"payment_id\":\"");
									addBigPayStr.append(paymentMongo.getPaymentId());//paymentId默认空
									addBigPayStr.append("\",");
									addBigPayStr.append("\"order_id\":\"");
									addBigPayStr.append(order.getOrderId());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_time\":\"");
									addBigPayStr.append(paymentMongo.getCreateTime().getTime());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_status\":\"");
									addBigPayStr.append(paymentMongo.getPaymentStatus());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"bill_no\":\"");
									addBigPayStr.append(paymentMongo.getBillNo());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_org_no\":\"");
									addBigPayStr.append(paymentMongo.getPaymentOrgNo());
									addBigPayStr.append("\",");//TODO org_name
									addBigPayStr.append("\"payment_reason\":\"");
									addBigPayStr.append(paymentMongo.getPaymentReason());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_type\":\"");
									addBigPayStr.append(paymentType);
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_type_details\": \"");
									addBigPayStr.append(paymentMongo.getPaymentTypeDetails());
									addBigPayStr.append("\",");
									addBigPayStr.append("\"payment_value\": \"");
									addBigPayStr.append(String.valueOf(paymentMongo.getPaymentValue()==null||"".equals(paymentMongo.getPaymentValue())?0:paymentMongo.getPaymentValue()));
									addBigPayStr.append("\",");
									addBigPayStr.append("\"point_rate\": \"");
									addBigPayStr.append(paymentMongo.getPointRate());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"from_account_no\": \"");
									addBigPayStr.append(paymentMongo.getFromAccountNo()==null || "".equals(paymentMongo.getFromAccountNo())?"":paymentMongo.getFromAccountNo());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"from_user_name\": \"");
									addBigPayStr.append(paymentMongo.getFromUserName()==null || "".equals(paymentMongo.getFromUserName())?"":paymentMongo.getFromUserName());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"to_account_no\": \"");
									addBigPayStr.append(paymentMongo.getSettleToAccountNo()==null || "".equals(paymentMongo.getSettleToAccountNo())?"":paymentMongo.getSettleToAccountNo());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"to_account_name\": \"");
									addBigPayStr.append(paymentMongo.getSettleToAccountName()==null || "".equals(paymentMongo.getSettleToAccountName())?"":paymentMongo.getSettleToAccountName());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"result_code\": \"");
									addBigPayStr.append(paymentMongo.getResultCode());//subOrder
									addBigPayStr.append("\",");
									addBigPayStr.append("\"remark\": \"");
									addBigPayStr.append(paymentMongo.getRemark());//subOrder
									addBigPayStr.append("\"}");
								}	
								addBigPayStr.append("]");
								addBigPayStr.append("}");
								addBigPayStr.append("}");
								addBigPayStr.append("\n");
								addPayWriter.write(addBigPayStr.toString());
							}
							if(paymentFail2){
								updateBigPayStr = new StringBuffer();
								updateBigPayStr.append("{");
								updateBigPayStr.append("\"key\": { \"order_id\":\"");
								updateBigPayStr.append(order.getOrderId());
								updateBigPayStr.append("\"},");
								updateBigPayStr.append("\"action\": \"PAYMENTFAIL\",");
								updateBigPayStr.append("\"client_id\": \"");
								updateBigPayStr.append(order.getClientId());
								updateBigPayStr.append("\",");
								updateBigPayStr.append("\"time\": ");
								updateBigPayStr.append(paymentList.get(0).getCreateTime().getTime());
								updateBigPayStr.append(",");
								updateBigPayStr.append("\"data\": {");
								updateBigPayStr.append("\"order_id\":\"");
								updateBigPayStr.append(order.getOrderId());
								updateBigPayStr.append("\",");
								updateBigPayStr.append("\"sub_orders\": [");	
								for (int j = 0; j < subOrderList2.size(); j++){
									suborder = subOrderList2.get(j);
									if(j==0){
										updateBigPayStr.append("{");
									}else{
										updateBigPayStr.append(",{");
									}
									updateBigPayStr.append("\"sub_order_id\":\"");
									updateBigPayStr.append(suborder.getSubOrderId());//paymentId默认空
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"order_type\":\"");
									updateBigPayStr.append(suborder.getType());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"org_code\":\"");
									String orgCode = "";
								      if(orgCode==null || "".equals(orgCode)){
										  orgCode=suborder.getLorgCode();
								      }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=suborder.getTorgCode();
									  }
									  if(orgCode==null || "".equals(orgCode)){
										  orgCode=suborder.getSorgCode();
									  }
									updateBigPayStr.append(orgCode);
									updateBigPayStr.append("\"}");
								}	
								updateBigPayStr.append("],");
								updateBigPayStr.append("\"payments\": [");	
								for (int j = 0; j < paymentList.size(); j++){
									paymentMongo = paymentList.get(j);
									if(j==0){
										updateBigPayStr.append("{");
									}else{
										updateBigPayStr.append(",{");
									}
									updateBigPayStr.append("\"payment_id\":\"");
									updateBigPayStr.append(paymentMongo.getPaymentId());//paymentId默认空
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"order_id\":\"");
									updateBigPayStr.append(order.getOrderId());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_time\":\"");
									updateBigPayStr.append(paymentMongo.getCreateTime().getTime());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_status\":\"");
									updateBigPayStr.append(paymentMongo.getPaymentStatus());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"bill_no\":\"");
									updateBigPayStr.append(paymentMongo.getBillNo());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_org_no\":\"");
									updateBigPayStr.append(paymentMongo.getPaymentOrgNo());
									updateBigPayStr.append("\",");//TODO org_name
									updateBigPayStr.append("\"payment_reason\":\"");
									updateBigPayStr.append(paymentMongo.getPaymentReason());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_type\":\"");
									updateBigPayStr.append(paymentType);
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_type_details\": \"");
									updateBigPayStr.append(paymentMongo.getPaymentTypeDetails());
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"payment_value\": \"");
									updateBigPayStr.append(String.valueOf(paymentMongo.getPaymentValue()==null||"".equals(paymentMongo.getPaymentValue())?0:paymentMongo.getPaymentValue()));
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"point_rate\": \"");
									updateBigPayStr.append(paymentMongo.getPointRate());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"from_account_no\": \"");
									updateBigPayStr.append(paymentMongo.getFromAccountNo()==null || "".equals(paymentMongo.getFromAccountNo())?"":paymentMongo.getFromAccountNo());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"from_user_name\": \"");
									updateBigPayStr.append(paymentMongo.getFromUserName()==null || "".equals(paymentMongo.getFromUserName())?"":paymentMongo.getFromUserName());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"to_account_no\": \"");
									updateBigPayStr.append(paymentMongo.getSettleToAccountNo()==null || "".equals(paymentMongo.getSettleToAccountNo())?"":paymentMongo.getSettleToAccountNo());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"to_account_name\": \"");
									updateBigPayStr.append(paymentMongo.getSettleToAccountName()==null || "".equals(paymentMongo.getSettleToAccountName())?"":paymentMongo.getSettleToAccountName());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"result_code\": \"");
									updateBigPayStr.append(paymentMongo.getResultCode());//subOrder
									updateBigPayStr.append("\",");
									updateBigPayStr.append("\"remark\": \"");
									updateBigPayStr.append(paymentMongo.getRemark());//subOrder
									updateBigPayStr.append("\"}");
								}	
								updateBigPayStr.append("]");
								updateBigPayStr.append("}");
								updateBigPayStr.append("}");
								updateBigPayStr.append("\n");
								updatePayWriter.write(updateBigPayStr.toString());
							}
						
						}
					
					}
				}
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				addPayWriter.flush();
			} catch (IOException e) {
			}
			try {
				addPayWriter.close();
			} catch (IOException e) {
			}
			
			try {
				updatePayWriter.flush();
			} catch (IOException e) {
			}
			try {
				updatePayWriter.close();
			} catch (IOException e) {
			}
			
			try {
				takePayWriter.flush();
			} catch (IOException e) {
			}
			try {
				takePayWriter.close();
			} catch (IOException e) {
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public static void makeOrderLog(String startTime,String endTime,String clientId,String logPath){
		Long start_time = DateUtil.parse("yyyy-MM-dd HH:mm:ss", startTime+" 00:00:00").getTime();
		Long end_time = DateUtil.parse("yyyy-MM-dd HH:mm:ss", endTime+" 23:59:59").getTime();
		Long tmpStartTime = 0L;
		Long tmpEndTime = 0L;
		BufferedWriter addWriter = null;
		BufferedWriter updateWriter = null;
		BufferedWriter takeWriter = null;
		long totalChunk = 0;
		long daysPerChunk = 7;
		List<OrderMongo> orderList = null;
		List<SubOrderMongo> subOrderList = null;
		
		MongoManager mongoManager = new MongoManager(); 
		
		DBObject orderQuery = new BasicDBObject();
		orderQuery.put("client_id", clientId);
		orderQuery.put("create_time", new BasicDBObject(QueryOperators.GTE,start_time).append(QueryOperators.LTE,end_time));
		orderQuery.put("is_qrcode", 1);
		
		DBObject subOrderQuery = new BasicDBObject();
		subOrderQuery.put("client_id", clientId);
		subOrderQuery.put("create_time", new BasicDBObject(QueryOperators.GTE,start_time).append(QueryOperators.LTE,end_time));
		
		totalChunk = ((end_time + 1 - start_time) / 86400000) / daysPerChunk + 1;
		
		SubOrderMongo subOrder = null;
		OrderMongo order = null;
		List<ProductMongo> productMongoList = null;	
		ProductMongo productMongo=null;
		ProductDetail productDetail1=null;
		ProductDetail productDetail2=null;
		ProductDetail productDetail3=null;		
		
		try {
			addWriter = new BufferedWriter(new FileWriter(new File(logPath+"order_success"+startTime+"_"+endTime+".log")));
			updateWriter = new BufferedWriter(new FileWriter(new File(logPath+"order_modify"+startTime+"_"+endTime+".log")));
			takeWriter = new BufferedWriter(new FileWriter(new File(logPath+"order_add"+startTime+"_"+endTime+".log")));
			StringBuffer addStr=null;
			StringBuffer updateStr=null;
			StringBuffer takeStr=null;
			StringBuffer addBigStr=null;
			StringBuffer updateBigStr=null;
			StringBuffer takeBigStr=null;
			
			tmpStartTime = start_time;
			for (long n = 0; n < totalChunk; n ++){
				// 每次只拉取7天日志
				tmpEndTime = start_time + (n + 1) * 86400000 * daysPerChunk - 1;
				if (tmpEndTime > end_time){
					tmpEndTime = end_time;
				}
				orderQuery.put("create_time", new BasicDBObject(QueryOperators.GTE,tmpStartTime).append(QueryOperators.LTE,tmpEndTime));
				orderList=(List<OrderMongo>)mongoManager.findAll(orderQuery, MongoTableName.CB_ORDER,OrderMongo.class);
				tmpStartTime = tmpEndTime + 1;
				
				if (EmptyChecker.isEmpty(orderList)){
					continue;
				}
				
				for (int i = 0; i < orderList.size(); i++){
					order = orderList.get(i);
					if(order!=null && order.getIsQrcode()==1){//面对面订单
						//面对面订单
						DBObject queryObj3 = new BasicDBObject();
						queryObj3.put("_id", order.getMerchantId());
//						MerchantDetail merchantDetail2 = mongoManager.findOne(queryObj3,MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
//						if("1".equals(order.getOrderStatus())){
							takeBigStr = new StringBuffer();
							takeBigStr.append("{");
							takeBigStr.append("\"key\": { \"id\":\"");
							takeBigStr.append(order.getOrderId());
							takeBigStr.append("\"},");
							takeBigStr.append("\"action\": \"ORDERADD\",");
							takeBigStr.append("\"client_id\": \"");
							takeBigStr.append(order.getClientId());
							takeBigStr.append("\",");
							takeBigStr.append("\"time\": ");
							takeBigStr.append(order.getCreateTime());
							takeBigStr.append(",");
							takeBigStr.append("\"data\": {");
							takeBigStr.append("\"id\":\"");
							takeBigStr.append(order.getOrderId());
							takeBigStr.append("\",");
							takeBigStr.append("\"order_id\":\"");
							takeBigStr.append(order.getOrderId());
							takeBigStr.append("\",");
							takeBigStr.append("\"sub_order_id\":\"");
							takeBigStr.append(order.getOrderId());
							takeBigStr.append("\",");
							takeBigStr.append("\"order_type\":\"");
							takeBigStr.append("0");
							takeBigStr.append("\",");
							takeBigStr.append("\"order_status\":\"");
							takeBigStr.append(order.getOrderStatus());
							takeBigStr.append("\",");
							takeBigStr.append("\"org_code\":\"");
							takeBigStr.append(order.getTorgCode());
							//takeBigStr.append(merchantDetail2==null || merchantDetail2.getOrgCode()==null||"".equals(merchantDetail2.getOrgCode())?"":merchantDetail2.getOrgCode());
							takeBigStr.append("\",");
							takeBigStr.append("\"member_code\":\"");
							takeBigStr.append(order.getMemberCode());
							takeBigStr.append("\",");//TODO org_name
							takeBigStr.append("\"is_seckill\":\"");
							takeBigStr.append(order.getIsSeckill());
							takeBigStr.append("\",");
							takeBigStr.append("\"is_vip_order\":\"");
							takeBigStr.append(order.getIsVipOrder());
							takeBigStr.append("\",");
							takeBigStr.append("\"total_price\": \"");
							takeBigStr.append(String.valueOf(order.getTotalPrice()));
							takeBigStr.append("\",");
							takeBigStr.append("\"fft_points\": \"");
							takeBigStr.append(order.getFftPoints());
							takeBigStr.append("\",");
							takeBigStr.append("\"bank_points\": \"");
							takeBigStr.append(order.getBankPoints());
							takeBigStr.append("\",");
							takeBigStr.append("\"cash_price\": \"");
							takeBigStr.append(order.getCashDiscount());
							takeBigStr.append("\",");
							takeBigStr.append("\"payment_method\": \"");
							takeBigStr.append(order.getPaymentMethod());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"payment_time\": \"");
							takeBigStr.append(order.getPaymentTime());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"create_time\": \"");
							takeBigStr.append(order.getCreateTime());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"vip_discount\": \"");
							takeBigStr.append(order.getVipDiscount());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"merchant_id\": \"");
							takeBigStr.append(order.getMerchantId());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"outlet_id\": \"");
							takeBigStr.append(order.getOutletId());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"merchant_user_id\": \"");
							takeBigStr.append(order.getMerchantUserId());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"create_source\": \"");
							takeBigStr.append(order.getCreateSource());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"f_org_code\": \"");
							takeBigStr.append(order.getForgCode());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"s_org_code\": \"");
							takeBigStr.append(order.getSorgCode());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"t_org_code\": \"");
							takeBigStr.append(order.getTorgCode());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"l_org_code\": \"");
							takeBigStr.append(order.getLorgCode());//subOrder
							takeBigStr.append("\",");
							takeBigStr.append("\"recv_id\": \"");
							takeBigStr.append(order.getRecvId());//Order
							takeBigStr.append("\",");
							takeBigStr.append("\"products\": [");	
							takeBigStr.append("]");
							takeBigStr.append("}");
							takeBigStr.append("}");
							takeBigStr.append("\n");
						takeWriter.write(takeBigStr.toString());
//						}else 
						if("6".equals(order.getOrderStatus())){
							addBigStr = new StringBuffer();
							addBigStr.append("{");
							addBigStr.append("\"key\": { \"id\":\"");
							addBigStr.append(order.getOrderId());
							addBigStr.append("\"},");
							addBigStr.append("\"action\": \"ORDERPAYSUCCESS\",");
							addBigStr.append("\"client_id\": \"");
							addBigStr.append(order.getClientId());
							addBigStr.append("\",");
							addBigStr.append("\"time\": ");
							addBigStr.append(order.getCreateTime());
							addBigStr.append(",");
							addBigStr.append("\"data\": {");
							addBigStr.append("\"id\":\"");
							addBigStr.append(order.getOrderId());
							addBigStr.append("\",");
							addBigStr.append("\"order_id\":\"");
							addBigStr.append(order.getOrderId());
							addBigStr.append("\",");
							addBigStr.append("\"sub_order_id\":\"");
							addBigStr.append(order.getOrderId());
							addBigStr.append("\",");
							addBigStr.append("\"order_type\":\"");
							addBigStr.append("0");
							addBigStr.append("\",");
							addBigStr.append("\"order_status\":\"");
							addBigStr.append(order.getOrderStatus());
							addBigStr.append("\",");
							addBigStr.append("\"org_code\":\"");
							addBigStr.append(order.getTorgCode());
							//addBigStr.append(merchantDetail2==null || merchantDetail2.getOrgCode()==null||"".equals(merchantDetail2.getOrgCode())?"":merchantDetail2.getOrgCode());
							addBigStr.append("\",");
							addBigStr.append("\"member_code\":\"");
							addBigStr.append(order.getMemberCode());
							addBigStr.append("\",");//TODO org_name
							addBigStr.append("\"is_seckill\":\"");
							addBigStr.append(order.getIsSeckill());
							addBigStr.append("\",");
							addBigStr.append("\"is_vip_order\":\"");
							addBigStr.append(order.getIsVipOrder());
							addBigStr.append("\",");
							addBigStr.append("\"total_price\": \"");
							addBigStr.append(String.valueOf(order.getTotalPrice()));
							addBigStr.append("\",");
							addBigStr.append("\"fft_points\": \"");
							addBigStr.append(order.getFftPoints());
							addBigStr.append("\",");
							addBigStr.append("\"bank_points\": \"");
							addBigStr.append(order.getBankPoints());
							addBigStr.append("\",");
							addBigStr.append("\"cash_price\": \"");
							addBigStr.append(order.getCashDiscount());
							addBigStr.append("\",");
							addBigStr.append("\"payment_method\": \"");
							addBigStr.append(order.getPaymentMethod());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"payment_time\": \"");
							addBigStr.append(order.getPaymentTime());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"create_time\": \"");
							addBigStr.append(order.getCreateTime());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"vip_discount\": \"");
							addBigStr.append(order.getVipDiscount());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"merchant_id\": \"");
							addBigStr.append(order.getMerchantId());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"outlet_id\": \"");
							addBigStr.append(order.getOutletId());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"merchant_user_id\": \"");
							addBigStr.append(order.getMerchantUserId());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"create_source\": \"");
							addBigStr.append(order.getCreateSource());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"f_org_code\": \"");
							addBigStr.append(order.getForgCode());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"s_org_code\": \"");
							addBigStr.append(order.getSorgCode());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"t_org_code\": \"");
							addBigStr.append(order.getTorgCode());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"l_org_code\": \"");
							addBigStr.append(order.getLorgCode());//subOrder
							addBigStr.append("\",");
							addBigStr.append("\"recv_id\": \"");
							addBigStr.append(order.getRecvId());//Order
							addBigStr.append("\",");
							addBigStr.append("\"products\": [");	
							addBigStr.append("]");
							addBigStr.append("}");
							addBigStr.append("}");
							addBigStr.append("\n");
							addWriter.write(addBigStr.toString());
						}
						if(!"6".equals(order.getOrderStatus()) && !"1".equals(order.getOrderStatus())){
							updateBigStr = new StringBuffer();
							updateBigStr.append("{");
							updateBigStr.append("\"key\": { \"id\":\"");
							updateBigStr.append(order.getOrderId());
							updateBigStr.append("\"},");
							updateBigStr.append("\"action\": \"ORDERMODIFY\",");
							updateBigStr.append("\"client_id\": \"");
							updateBigStr.append(order.getClientId());
							updateBigStr.append("\",");
							updateBigStr.append("\"time\": ");
							updateBigStr.append(order.getCreateTime());
							updateBigStr.append(",");
							updateBigStr.append("\"data\": {");
							updateBigStr.append("\"id\":\"");
							updateBigStr.append(order.getOrderId());
							updateBigStr.append("\",");
							updateBigStr.append("\"order_id\":\"");
							updateBigStr.append(order.getOrderId());
							updateBigStr.append("\",");
							updateBigStr.append("\"sub_order_id\":\"");
							updateBigStr.append(order.getOrderId());
							updateBigStr.append("\",");
							updateBigStr.append("\"order_type\":\"");
							updateBigStr.append("0");
							updateBigStr.append("\",");
							updateBigStr.append("\"order_status\":\"");
							updateBigStr.append(order.getOrderStatus());
							updateBigStr.append("\",");
							updateBigStr.append("\"org_code\":\"");
							updateBigStr.append(order.getTorgCode());
							//updateBigStr.append(merchantDetail2==null || merchantDetail2.getOrgCode()==null||"".equals(merchantDetail2.getOrgCode())?"":merchantDetail2.getOrgCode());
							updateBigStr.append("\",");
							updateBigStr.append("\"member_code\":\"");
							updateBigStr.append(order.getMemberCode());
							updateBigStr.append("\",");//TODO org_name
							updateBigStr.append("\"is_seckill\":\"");
							updateBigStr.append(order.getIsSeckill());
							updateBigStr.append("\",");
							updateBigStr.append("\"is_vip_order\":\"");
							updateBigStr.append(order.getIsVipOrder());
							updateBigStr.append("\",");
							updateBigStr.append("\"total_price\": \"");
							updateBigStr.append(String.valueOf(order.getTotalPrice()));
							updateBigStr.append("\",");
							updateBigStr.append("\"fft_points\": \"");
							updateBigStr.append(order.getFftPoints());
							updateBigStr.append("\",");
							updateBigStr.append("\"bank_points\": \"");
							updateBigStr.append(order.getBankPoints());
							updateBigStr.append("\",");
							updateBigStr.append("\"cash_price\": \"");
							updateBigStr.append(order.getCashDiscount());
							updateBigStr.append("\",");
							updateBigStr.append("\"payment_method\": \"");
							updateBigStr.append(order.getPaymentMethod());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"payment_time\": \"");
							updateBigStr.append(order.getPaymentTime());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"create_time\": \"");
							updateBigStr.append(order.getCreateTime());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"vip_discount\": \"");
							updateBigStr.append(order.getVipDiscount());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"merchant_id\": \"");
							updateBigStr.append(order.getMerchantId());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"outlet_id\": \"");
							updateBigStr.append(order.getOutletId());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"merchant_user_id\": \"");
							updateBigStr.append(order.getMerchantUserId());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"create_source\": \"");
							updateBigStr.append(order.getCreateSource());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"f_org_code\": \"");
							updateBigStr.append(order.getForgCode());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"s_org_code\": \"");
							updateBigStr.append(order.getSorgCode());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"t_org_code\": \"");
							updateBigStr.append(order.getTorgCode());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"l_org_code\": \"");
							updateBigStr.append(order.getLorgCode());//subOrder
							updateBigStr.append("\",");
							updateBigStr.append("\"recv_id\": \"");
							updateBigStr.append(order.getRecvId());//Order
							updateBigStr.append("\",");
							updateBigStr.append("\"products\": [");	
							updateBigStr.append("]");
							updateBigStr.append("}");
							updateBigStr.append("}");
							updateBigStr.append("\n");
							updateWriter.write(updateBigStr.toString());
						}
							
					}
				}
			}
			
			//面对面之外的其他订单日志生成
			tmpStartTime = start_time;
			for (long n = 0; n < totalChunk; n ++){
				// 每次只拉取7天日志
				tmpEndTime = start_time + (n + 1) * 86400000 * daysPerChunk - 1;
				if (tmpEndTime > end_time){
					tmpEndTime = end_time;
				}
				subOrderQuery.put("create_time", new BasicDBObject(QueryOperators.GTE,tmpStartTime).append(QueryOperators.LTE,tmpEndTime));
				subOrderList=(List<SubOrderMongo>)mongoManager.findAll(subOrderQuery, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
				tmpStartTime = tmpEndTime + 1;
				
				if (EmptyChecker.isEmpty(subOrderList)){
					continue;
				}
				
				for (int i = 0; i < subOrderList.size(); i++){
					subOrder = subOrderList.get(i);
					DBObject queryObj = new BasicDBObject();
					queryObj.put("_id", subOrder.getOrderId());
					order = mongoManager.findOne(queryObj,MongoTableName.CB_ORDER, OrderMongo.class);
					DBObject queryObj2 = new BasicDBObject();
					queryObj2.put("_id", subOrder.getMerchantId());
					
					productMongoList = subOrder.getProducts();
					//if(productMongoList!=null && productMongoList.size()>0 && merchantDetail!=null){
					// 订单支付成功
//					if(order!=null && order.getIsQrcode()==1){
//						
//					}else 
					// 支付完成，退款导致系统关单
					if(subOrder!=null && ("6".equals(subOrder.getOrderStatus()) || ("3".equals(subOrder.getOrderStatus()) && "3".equals(subOrder.getRefundState())) )){
						addStr = new StringBuffer();
						addStr.append("{");
						addStr.append("\"key\": { \"id\":\"");
						addStr.append(subOrder.getOrderId());
						addStr.append("\"},");
						addStr.append("\"action\": \"ORDERPAYSUCCESS\",");
						addStr.append("\"client_id\": \"");
						addStr.append(subOrder.getClientId());
						addStr.append("\",");
						addStr.append("\"time\": ");
						addStr.append(subOrder.getCreateTime());
						addStr.append(",");
						addStr.append("\"data\": {");
						addStr.append("\"id\":\"");
						addStr.append(subOrder.getId());
						addStr.append("\",");
						addStr.append("\"order_id\":\"");
						addStr.append(subOrder.getOrderId());
						addStr.append("\",");
						addStr.append("\"sub_order_id\":\"");
						addStr.append(subOrder.getSubOrderId());
						addStr.append("\",");
						addStr.append("\"order_type\":\"");
						addStr.append(subOrder.getType());
						addStr.append("\",");
						addStr.append("\"order_status\":\"");
						addStr.append(subOrder.getOrderStatus());
						addStr.append("\",");
						addStr.append("\"org_code\":\"");
						addStr.append(subOrder.getTorgCode());
						//addStr.append(merchantDetail==null || merchantDetail.getOrgCode()==null||"".equals(merchantDetail.getOrgCode())?"":merchantDetail.getOrgCode());
						addStr.append("\",");
						addStr.append("\"member_code\":\"");
						addStr.append(subOrder.getMemberCode());
						addStr.append("\",");//TODO org_name
						addStr.append("\"is_seckill\":\"");
						addStr.append(order.getIsSeckill());
						addStr.append("\",");
						addStr.append("\"is_vip_order\":\"");
						addStr.append(order.getIsVipOrder());
						addStr.append("\",");
						addStr.append("\"total_price\": \"");
						addStr.append(String.valueOf(order.getTotalPrice()));
						addStr.append("\",");
						addStr.append("\"fft_points\": \"");
						addStr.append(order.getFftPoints());
						addStr.append("\",");
						addStr.append("\"bank_points\": \"");
						addStr.append(order.getBankPoints());
						addStr.append("\",");
						addStr.append("\"cash_price\": \"");
						addStr.append(order.getCashDiscount());
						addStr.append("\",");
						addStr.append("\"payment_method\": \"");
						addStr.append(order.getPaymentMethod());//subOrder
						addStr.append("\",");
						addStr.append("\"payment_time\": \"");
						addStr.append(order.getPaymentTime());//subOrder
						addStr.append("\",");
						addStr.append("\"create_time\": \"");
						addStr.append(order.getCreateTime());//subOrder
						addStr.append("\",");
						addStr.append("\"vip_discount\": \"");
						addStr.append(order.getVipDiscount());//subOrder
						addStr.append("\",");
						addStr.append("\"merchant_id\": \"");
						addStr.append(subOrder.getMerchantId());//subOrder
						addStr.append("\",");
						addStr.append("\"outlet_id\": \"");
						addStr.append(subOrder.getOrderId());//subOrder
						addStr.append("\",");
						addStr.append("\"merchant_user_id\": \"");
						addStr.append(order.getMerchantUserId());//subOrder
						addStr.append("\",");
						addStr.append("\"recv_id\": \"");
						addStr.append(order.getRecvId());//Order
						addStr.append("\",");
						addStr.append("\"create_source\": \"");
						addStr.append(order.getCreateSource());//subOrder
						addStr.append("\",");					
						addStr.append("\"f_org_code\": \"");
						addStr.append(subOrder.getForgCode());//subOrder
						addStr.append("\",");
						addStr.append("\"s_org_code\": \"");
						addStr.append(subOrder.getSorgCode());//subOrder
						addStr.append("\",");
						addStr.append("\"t_org_code\": \"");
						addStr.append(subOrder.getTorgCode());//subOrder
						addStr.append("\",");
						addStr.append("\"l_org_code\": \"");
						addStr.append(subOrder.getLorgCode());//subOrder
						addStr.append("\",");					
						addStr.append("\"products\": [");
						DBObject queryObj3 = null;
						for (int j = 0; j < productMongoList.size(); j++){
							productMongo = productMongoList.get(j);
							if(j==0){
								addStr.append("{");
							}else{
								addStr.append(",{");
							}
							queryObj3 = new BasicDBObject();
							queryObj3.put("_id", productMongo.getProductId());
							productDetail1 = mongoManager.findOne(queryObj3,MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
							addStr.append("\"product_id\": \"");
							addStr.append(productMongo.getProductId());
							addStr.append("\",\"product_name\": \"");
							addStr.append(productMongo.getProductName());
							addStr.append("\",\"category_id\": \"");
							addStr.append(productDetail1==null||productDetail1.getProductCategoryInfo()==null||productDetail1.getProductCategoryInfo().size()==0?"":productDetail1.getProductCategoryInfo().get(0).getProductCategoryId());
							addStr.append("\",\"money\": \"");
							addStr.append(String.valueOf(productMongo.getMoney()));
							addStr.append("\",\"vip_money\": \"");
							addStr.append(String.valueOf(productMongo.getVipMoney()));
							addStr.append("\",\"quantity\": \"");
							addStr.append(String.valueOf(productMongo.getQuantity()));
							addStr.append("\",\"type\": \"");
							addStr.append(String.valueOf(subOrder.getType()));
							addStr.append("\",\"vip_quantity\": \"");
							addStr.append(String.valueOf(productMongo.getVipQuantity()));
							addStr.append("\",\"take_org_code\": \"");
							addStr.append(productMongo.getOrgCode());
							addStr.append("\",\"take_org_name\": \"");
							addStr.append(productMongo.getOrgName());
							addStr.append("\",\"delivery_option\": \"");
							addStr.append(productMongo.getDeliveryOption());
							addStr.append("\",\"delivery_state\": \"");
							addStr.append(productMongo.getDeliveryState());
							addStr.append("\",\"delivery_money\": \"");
							addStr.append(productMongo.getDeliveryMoney());
							addStr.append("\"}");
						}
						addStr.append("]");
						addStr.append("}");
						addStr.append("}");
						addStr.append("\n");
						addWriter.write(addStr.toString());
					}
						
					if(subOrder!=null && !"6".equals(subOrder.getOrderStatus()) && !"1".equals(subOrder.getOrderStatus())){//订单修改
							updateStr = new StringBuffer();
							updateStr.append("{");
							updateStr.append("\"key\": { \"id\":\"");
							updateStr.append(subOrder.getOrderId());
							updateStr.append("\"},");
							updateStr.append("\"action\": \"ORDERMODIFY\",");
							updateStr.append("\"client_id\": \"");
							updateStr.append(subOrder.getClientId());
							updateStr.append("\",");
							updateStr.append("\"time\": ");
							updateStr.append(subOrder.getCreateTime());
							updateStr.append(",");
							updateStr.append("\"data\": {");
							updateStr.append("\"id\":\"");
							updateStr.append(subOrder.getId());
							updateStr.append("\",");
							updateStr.append("\"order_id\":\"");
							updateStr.append(subOrder.getOrderId());
							updateStr.append("\",");
							updateStr.append("\"sub_order_id\":\"");
							updateStr.append(subOrder.getSubOrderId());
							updateStr.append("\",");
							updateStr.append("\"order_type\":\"");
							updateStr.append(subOrder.getType());
							updateStr.append("\",");
							updateStr.append("\"order_status\":\"");
							updateStr.append(subOrder.getOrderStatus());
							updateStr.append("\",");
							updateStr.append("\"org_code\":\"");
							updateStr.append(subOrder.getTorgCode());
							//updateStr.append(merchantDetail==null || merchantDetail.getOrgCode()==null||"".equals(merchantDetail.getOrgCode())?"":merchantDetail.getOrgCode());
							updateStr.append("\",");
							updateStr.append("\"member_code\":\"");
							updateStr.append(subOrder.getMemberCode());
							updateStr.append("\",");//TODO org_name
							updateStr.append("\"is_seckill\":\"");
							updateStr.append(order.getIsSeckill());
							updateStr.append("\",");
							updateStr.append("\"is_vip_order\":\"");
							updateStr.append(order.getIsVipOrder());
							updateStr.append("\",");
							updateStr.append("\"total_price\": \"");
							updateStr.append(String.valueOf(order.getTotalPrice()));
							updateStr.append("\",");
							updateStr.append("\"fft_points\": \"");
							updateStr.append(order.getFftPoints());
							updateStr.append("\",");
							updateStr.append("\"bank_points\": \"");
							updateStr.append(order.getBankPoints());
							updateStr.append("\",");
							updateStr.append("\"cash_price\": \"");
							updateStr.append(order.getCashDiscount());
							updateStr.append("\",");
							updateStr.append("\"payment_method\": \"");
							updateStr.append(order.getPaymentMethod());//subOrder
							updateStr.append("\",");
							updateStr.append("\"payment_time\": \"");
							updateStr.append(order.getPaymentTime());//subOrder
							updateStr.append("\",");
							updateStr.append("\"create_time\": \"");
							updateStr.append(order.getCreateTime());//subOrder
							updateStr.append("\",");
							updateStr.append("\"vip_discount\": \"");
							updateStr.append(order.getVipDiscount());//subOrder
							updateStr.append("\",");
							updateStr.append("\"merchant_id\": \"");
							updateStr.append(subOrder.getMerchantId());//subOrder
							updateStr.append("\",");
							updateStr.append("\"outlet_id\": \"");
							updateStr.append(subOrder.getOrderId());//subOrder
							updateStr.append("\",");
							updateStr.append("\"merchant_user_id\": \"");
							updateStr.append(order.getMerchantUserId());//subOrder
							updateStr.append("\",");
							updateStr.append("\"create_source\": \"");
							updateStr.append(order.getCreateSource());//order
							updateStr.append("\",");
							updateStr.append("\"f_org_code\": \"");
							updateStr.append(subOrder.getForgCode());//subOrder
							updateStr.append("\",");
							updateStr.append("\"s_org_code\": \"");
							updateStr.append(subOrder.getSorgCode());//subOrder
							updateStr.append("\",");
							updateStr.append("\"t_org_code\": \"");
							updateStr.append(subOrder.getTorgCode());//subOrder
							updateStr.append("\",");
							updateStr.append("\"l_org_code\": \"");
							updateStr.append(subOrder.getLorgCode());//subOrder
							updateStr.append("\",");
							updateStr.append("\"recv_id\": \"");
							updateStr.append(order.getRecvId());//Order
							updateStr.append("\",");
							updateStr.append("\"products\": [");	
							DBObject queryObj4 = null;
							for (int j = 0; j < productMongoList.size(); j++){
								productMongo = productMongoList.get(j);
								if(j==0){
									updateStr.append("{");
								}else{
									updateStr.append(",{");
								}
								queryObj4 = new BasicDBObject();
								queryObj4.put("_id", productMongo.getProductId());
								productDetail2 = mongoManager.findOne(queryObj4,MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
								updateStr.append("\"product_id\": \"");
								updateStr.append(productMongo.getProductId());
								updateStr.append("\",\"product_name\": \"");
								updateStr.append(productMongo.getProductName());
								updateStr.append("\",\"category_id\": \"");
								updateStr.append(productDetail2==null||productDetail2.getProductCategoryInfo()==null||productDetail2.getProductCategoryInfo().size()==0?"":productDetail2.getProductCategoryInfo().get(0).getProductCategoryId());
								updateStr.append("\",\"money\": \"");
								updateStr.append(String.valueOf(productMongo.getMoney()));
								updateStr.append("\",\"vip_money\": \"");
								updateStr.append(String.valueOf(productMongo.getVipMoney()));
								updateStr.append("\",\"type\": \"");
								updateStr.append(String.valueOf(subOrder.getType()));
								updateStr.append("\",\"quantity\": \"");
								updateStr.append(String.valueOf(productMongo.getQuantity()));
								updateStr.append("\",\"vip_quantity\": \"");
								updateStr.append(String.valueOf(productMongo.getVipQuantity()));
								updateStr.append("\",\"take_org_code\": \"");
								updateStr.append(productMongo.getOrgCode());
								updateStr.append("\",\"take_org_name\": \"");
								updateStr.append(productMongo.getOrgName());
								updateStr.append("\",\"delivery_option\": \"");
								updateStr.append(productMongo.getDeliveryOption());
								updateStr.append("\",\"delivery_state\": \"");
								updateStr.append(productMongo.getDeliveryState());
								updateStr.append("\",\"delivery_money\": \"");
								updateStr.append(productMongo.getDeliveryMoney());
								updateStr.append("\"}");
							}	
							updateStr.append("]");
							updateStr.append("}");
							updateStr.append("}");
							updateStr.append("\n");
							updateWriter.write(updateStr.toString());
						}
					//if(subOrder!=null && ("1".equals(subOrder.getOrderStatus()) || "6".equals(subOrder.getOrderStatus()))){// 订单创建
							takeStr = new StringBuffer();
							takeStr.append("{");
							takeStr.append("\"key\": { \"id\":\"");
							takeStr.append(subOrder.getOrderId());
							takeStr.append("\"},");
							takeStr.append("\"action\": \"ORDERADD\",");
							takeStr.append("\"client_id\": \"");
							takeStr.append(subOrder.getClientId());
							takeStr.append("\",");
							takeStr.append("\"time\": ");
							takeStr.append(subOrder.getCreateTime());
							takeStr.append(",");
							takeStr.append("\"data\": {");
							takeStr.append("\"id\":\"");
							takeStr.append(subOrder.getId());
							takeStr.append("\",");
							takeStr.append("\"order_id\":\"");
							takeStr.append(subOrder.getOrderId());
							takeStr.append("\",");
							takeStr.append("\"sub_order_id\":\"");
							takeStr.append(subOrder.getSubOrderId());
							takeStr.append("\",");
							takeStr.append("\"order_type\":\"");
							takeStr.append(subOrder.getType());
							takeStr.append("\",");
							takeStr.append("\"order_status\":\"");
							takeStr.append(subOrder.getOrderStatus());
							takeStr.append("\",");
							takeStr.append("\"org_code\":\"");
							takeStr.append(subOrder.getTorgCode());
							//takeStr.append(merchantDetail==null || merchantDetail.getOrgCode()==null||"".equals(merchantDetail.getOrgCode())?"":merchantDetail.getOrgCode());
							takeStr.append("\",");
							takeStr.append("\"member_code\":\"");
							takeStr.append(subOrder.getMemberCode());
							takeStr.append("\",");//TODO org_name
							takeStr.append("\"is_seckill\":\"");
							takeStr.append(order.getIsSeckill());
							takeStr.append("\",");
							takeStr.append("\"is_vip_order\":\"");
							takeStr.append(order.getIsVipOrder());
							takeStr.append("\",");
							takeStr.append("\"total_price\": \"");
							takeStr.append(String.valueOf(order.getTotalPrice()));
							takeStr.append("\",");
							takeStr.append("\"fft_points\": \"");
							takeStr.append(order.getFftPoints());
							takeStr.append("\",");
							takeStr.append("\"bank_points\": \"");
							takeStr.append(order.getBankPoints());
							takeStr.append("\",");
							takeStr.append("\"cash_price\": \"");
							takeStr.append(order.getCashDiscount());
							takeStr.append("\",");
							takeStr.append("\"payment_method\": \"");
							takeStr.append(order.getPaymentMethod());//subOrder
							takeStr.append("\",");
							takeStr.append("\"payment_time\": \"");
							takeStr.append(order.getPaymentTime());//subOrder
							takeStr.append("\",");
							takeStr.append("\"create_time\": \"");
							takeStr.append(order.getCreateTime());//subOrder
							takeStr.append("\",");
							takeStr.append("\"vip_discount\": \"");
							takeStr.append(order.getVipDiscount());//subOrder
							takeStr.append("\",");
							takeStr.append("\"merchant_id\": \"");
							takeStr.append(subOrder.getMerchantId());//subOrder
							takeStr.append("\",");
							takeStr.append("\"outlet_id\": \"");
							takeStr.append(subOrder.getOrderId());//subOrder
							takeStr.append("\",");
							takeStr.append("\"merchant_user_id\": \"");
							takeStr.append(order.getMerchantUserId());//subOrder
							takeStr.append("\",");
							takeStr.append("\"create_source\": \"");
							takeStr.append(order.getCreateSource());//order
							takeStr.append("\",");
							takeStr.append("\"f_org_code\": \"");
							takeStr.append(subOrder.getForgCode());//subOrder
							takeStr.append("\",");
							takeStr.append("\"s_org_code\": \"");
							takeStr.append(subOrder.getSorgCode());//subOrder
							takeStr.append("\",");
							takeStr.append("\"t_org_code\": \"");
							takeStr.append(subOrder.getTorgCode());//subOrder
							takeStr.append("\",");
							takeStr.append("\"l_org_code\": \"");
							takeStr.append(subOrder.getLorgCode());//subOrder
							takeStr.append("\",");
							takeStr.append("\"recv_id\": \"");
							takeStr.append(order.getRecvId());//Order
							takeStr.append("\",");
							takeStr.append("\"products\": [");	
							DBObject queryObj5 =null;
								for (int j = 0; j < productMongoList.size(); j++){
									productMongo = productMongoList.get(j);
									if(j==0){
										takeStr.append("{");
									}else{
										takeStr.append(",{");
									}
									queryObj5 = new BasicDBObject();
									queryObj5.put("_id", productMongo.getProductId());
									productDetail3 = mongoManager.findOne(queryObj5,MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
									takeStr.append("\"product_id\": \"");
									takeStr.append(productMongo.getProductId());
									takeStr.append("\",\"product_name\": \"");
									takeStr.append(productMongo.getProductName());
									takeStr.append("\",\"category_id\": \"");
									takeStr.append(productDetail3==null||productDetail3.getProductCategoryInfo()==null||productDetail3.getProductCategoryInfo().size()==0?"":productDetail3.getProductCategoryInfo().get(0).getProductCategoryId());
									takeStr.append("\",\"money\": \"");
									takeStr.append(String.valueOf(productMongo.getMoney()));
									takeStr.append("\",\"vip_money\": \"");
									takeStr.append(String.valueOf(productMongo.getVipMoney()));
									takeStr.append("\",\"quantity\": \"");
									takeStr.append(String.valueOf(productMongo.getQuantity()));
									takeStr.append("\",\"type\": \"");
									takeStr.append(String.valueOf(subOrder.getType()));
									takeStr.append("\",\"vip_quantity\": \"");
									takeStr.append(String.valueOf(productMongo.getVipQuantity()));
									takeStr.append("\",\"take_org_code\": \"");
									takeStr.append(productMongo.getOrgCode());
									takeStr.append("\",\"take_org_name\": \"");
									takeStr.append(productMongo.getOrgName());
									takeStr.append("\",\"delivery_option\": \"");
									takeStr.append(productMongo.getDeliveryOption());
									takeStr.append("\",\"delivery_state\": \"");
									takeStr.append(productMongo.getDeliveryState());
									takeStr.append("\",\"delivery_money\": \"");
									takeStr.append(productMongo.getDeliveryMoney());
									takeStr.append("\"}");
								}
								takeStr.append("]");
								takeStr.append("}");
								takeStr.append("}");
								takeStr.append("\n");
							takeWriter.write(takeStr.toString());
						//}
						
					//}
					
				}
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				addWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				addWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				updateWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				updateWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				takeWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				takeWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}

	
	@SuppressWarnings("unchecked")
	public static void makeRefundLog(String startTime,String endTime,String clientId,String logPath){
		MongoManager mongoManager = null;
		DBObject queryObj = null;
		long dbStartTime = 0l;
		long dbEndTime = 0l;
		List<RefundHistory> refundHisList = null;
		RefundHistory his = null;
		StringBuffer str = null;
		int refundPrice = 0;
		RefundPaymentInfo payInfo = null;
		RefundProduct refundProduct = null;
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(new File(new StringBuffer(logPath).append("refund_history_").append(startTime).append("_").append(endTime).append(".log").toString())));
			
			dbStartTime = DateUtil.parse("yyyy-MM-dd HH:mm:ss", startTime+" 00:00:00").getTime();
			dbEndTime = DateUtil.parse("yyyy-MM-dd HH:mm:ss", endTime+" 23:59:59").getTime();
			
			queryObj = new BasicDBObject();
			queryObj.put("client_id", clientId);
			queryObj.put("refund_state", RefundState.REFUND_SUCCESS.getCode());
			queryObj.put("refund_time", new BasicDBObject(QueryOperators.GTE, dbStartTime).append(QueryOperators.LTE, dbEndTime));
			
			mongoManager = new MongoManager();
			
			refundHisList = (List<RefundHistory>) mongoManager.findAll(queryObj, "cb_order_refunds", RefundHistory.class);
			
			if (Checker.isNotEmpty(refundHisList)){
				for (int i = 0; i < refundHisList.size(); i++){
					str = new StringBuffer();
					his = refundHisList.get(i);
					str.append("{");
					str.append("\"key\": { \"refund_id\": \"");
					str.append(his.get_id());
					str.append("\"},");
					str.append("\"action\": \"REFUND\",");
					str.append("\"client_id\": \"");
					str.append(clientId);
					str.append("\",");
					str.append("\"time\": ");
					str.append(his.getRefundTime());
					str.append(",");
					str.append("\"data\": {");
					
					str.append("\"refund_id\": \"").append(his.get_id()).append("\",");
					str.append("\"create_time\": ").append(his.getCreateTime()).append(",");
					str.append("\"member_code\": \"").append(his.getMemberCode()).append("\",");
					str.append("\"order_id\": \"").append(his.getOrderId()).append("\",");
					str.append("\"refund_resource\": \"").append(his.getRefundResource()).append("\",");
					str.append("\"refund_state\": \"").append(his.getRefundState()).append("\",");
					str.append("\"refund_time\": ").append(his.getRefundTime()).append(",");
					str.append("\"merchant_id\": \"").append(his.getShoppingInfo().get(0).getMerchantId()).append("\",");
					str.append("\"merchant_name\": \"").append(his.getShoppingInfo().get(0).getMerchantName()).append("\",");
					str.append("\"sub_order_id\": \"").append(his.getShoppingInfo().get(0).getSubOrderId()).append("\",");
					str.append("\"type\": \"").append(his.getShoppingInfo().get(0).getType()).append("\",");
					str.append("\"payment_info\": [");
					refundPrice = 0;
					if (Checker.isNotEmpty(his.getPaymentInfo())){
						payInfo = his.getPaymentInfo().get(0);
						str.append("{");
						str.append("\"payment_id\": \"").append(payInfo.getPaymentId()).append("\",");
						refundPrice = payInfo.getRefundValue();
						str.append("\"refund_value\": ").append(payInfo.getRefundValue()).append(",");
						str.append("\"result_code\": \"").append(payInfo.getResultCode()).append("\",");
						str.append("\"result_desc\": \"").append(payInfo.getResultDesc()).append("\",");
						str.append("\"type\": \"").append(payInfo.getType()).append("\"");
						str.append("}");
						
						if (his.getPaymentInfo().size() > 1){
							payInfo = his.getPaymentInfo().get(1);
							str.append(",{");
							str.append("\"payment_id\": \"").append(payInfo.getPaymentId()).append("\",");
							refundPrice += payInfo.getRefundValue();
							str.append("\"refund_value\": \"").append(payInfo.getRefundValue()).append("\",");
							str.append("\"result_code\": \"").append(payInfo.getResultCode()).append("\",");
							str.append("\"result_desc\": \"").append(payInfo.getResultDesc()).append("\",");
							str.append("\"type\": \"").append(payInfo.getType()).append("\"");
							str.append("}");
						}
						
						if (his.getPaymentInfo().size() > 2){
							payInfo = his.getPaymentInfo().get(2);
							str.append(",{");
							str.append("\"payment_id\": \"").append(payInfo.getPaymentId()).append("\",");
							refundPrice += payInfo.getRefundValue();
							str.append("\"refund_value\": ").append(payInfo.getRefundValue()).append(",");
							str.append("\"result_code\": \"").append(payInfo.getResultCode()).append("\",");
							str.append("\"result_desc\": \"").append(payInfo.getResultDesc()).append("\",");
							str.append("\"type\": \"").append(payInfo.getType()).append("\"");
							str.append("}");
						}
					}
					str.append("],");
					str.append("\"refund_price\": \"").append(refundPrice).append("\",");
					str.append("\"products\": [");
					if (Checker.isNotEmpty(his.getShoppingInfo().get(0).getProducts()) && his.getShoppingInfo().get(0).getProducts().size() > 0){
						refundProduct = his.getShoppingInfo().get(0).getProducts().get(0);
						str.append("{");
						str.append("\"price\": ").append(refundProduct.getPrice()).append(",");
						str.append("\"product_id\": \"").append(refundProduct.getProductId()).append("\",");
						str.append("\"product_name\": \"").append(refundProduct.getProductName()).append("\",");
						str.append("\"quantity\": ").append(refundProduct.getQuantity()).append("");
						str.append("}");
						
						for (int n = 1; n < his.getShoppingInfo().get(0).getProducts().size(); n++){
							refundProduct = his.getShoppingInfo().get(0).getProducts().get(n);
							str.append(",{");
							str.append("\"price\": ").append(refundProduct.getPrice()).append(",");
							str.append("\"product_id\": \"").append(refundProduct.getProductId()).append("\",");
							str.append("\"product_name\": \"").append(refundProduct.getProductName()).append("\",");
							str.append("\"quantity\": ").append(refundProduct.getQuantity()).append("");
							str.append("}");
						}
					}
					str.append("]");
					str.append("}");
					str.append("}");
					str.append("\n");
					
					writer.write(str.toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void makeTicketLog(String startTime,String endTime,String clientId,String logPath){
		MongoManager mongoManager = null;
		DBObject queryObj = null;
		long dbStartTime = 0l;
		long dbEndTime = 0l;
		StringBuffer addStr = null;
		BufferedWriter addWriter = null;
		StringBuffer updateStr = null;
		BufferedWriter updateWriter = null;
		List<Ticket> ticketList = null;
		Ticket ticket = null;
		
		try {
			addWriter = new BufferedWriter(new FileWriter(new File(new StringBuffer(logPath).append("ticket_add_history_").append(startTime).append("_").append(endTime).append(".log").toString())));
			updateWriter = new BufferedWriter(new FileWriter(new File(new StringBuffer(logPath).append("ticket_modify_history_").append(startTime).append("_").append(endTime).append(".log").toString())));
			
			dbStartTime = DateUtil.parse("yyyy-MM-dd HH:mm:ss", startTime+" 00:00:00").getTime();
			dbEndTime = DateUtil.parse("yyyy-MM-dd HH:mm:ss", endTime+" 23:59:59").getTime();
			
			queryObj = new BasicDBObject();
			queryObj.put("client_id", clientId);
			queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, dbStartTime).append(QueryOperators.LTE, dbEndTime));
			
			mongoManager = new MongoManager();
			
			ticketList = (List<Ticket>) mongoManager.findAll(queryObj, "cb_ticket", Ticket.class);
			
			if (Checker.isNotEmpty(ticketList)){
				for (int i = 0; i < ticketList.size(); i++){
					ticket = ticketList.get(i);
					addStr = new StringBuffer();
					addStr.append("{");
					addStr.append("\"key\": { \"ticket_id\": \"").append(ticket.getTicketId()).append("\"},");
					addStr.append("\"action\": \"TICKETADD\",");
					addStr.append("\"client_id\": \"").append(clientId).append("\",");
					addStr.append("\"time\": ").append(ticket.getCreateTime()).append(",");
					addStr.append("\"data\": {");
					addStr.append("\"id\": \"").append(ticket.getTicketId()).append("\",");
					addStr.append("\"ticket_id\": \"").append(ticket.getTicketId()).append("\",");
					addStr.append("\"order_id\": \"").append(ticket.getOrderId()).append("\",");
					addStr.append("\"sub_order_id\": \"").append(ticket.getSubOrderId()).append("\",");
					addStr.append("\"create_time\": ").append(ticket.getCreateTime()).append(",");
					addStr.append("\"expire_time\": ").append(ticket.getExpireTime()).append(",");
					addStr.append("\"type\": \"").append(ticket.getType()).append("\",");
					addStr.append("\"member_code\": ").append(ticket.getMemberCode()).append(",");
					addStr.append("\"member_name\": \"").append(ticket.getMemberName()).append("\",");
					addStr.append("\"merchant_id\": \"").append(ticket.getMerchantId()).append("\",");
					addStr.append("\"merchant_name\": \"").append(ticket.getMerchantName()).append("\",");
					addStr.append("\"mobile\": \"").append(ticket.getMobile()).append("\",");
					addStr.append("\"org_code\": \"").append("").append("\",");
					addStr.append("\"org_name\": \"").append("").append("\",");
					addStr.append("\"forg_code\": \"").append("").append("\",");
					addStr.append("\"sorg_code\": \"").append("").append("\",");
					addStr.append("\"torg_code\": \"").append("").append("\",");
					addStr.append("\"lorg_code\": \"").append("").append("\",");
					addStr.append("\"product_id\": \"").append(ticket.getProductId()).append("\",");
					addStr.append("\"product_name\": \"").append(ticket.getProductName()).append("\",");
					addStr.append("\"image\": \"").append(ticket.getImage()).append("\",");
					addStr.append("\"price\": ").append(ticket.getPrice()).append(",");
					addStr.append("\"quantity\": ").append(ticket.getQuantity()).append(",");
					addStr.append("\"status\": \"").append(ticket.getStatus()).append("\",");
					addStr.append("\"consume_time\": ").append(ticket.getConsumeTime() == null ? 0 : ticket.getConsumeTime()).append(",");
					addStr.append("\"outlet_id\": \"").append(ticket.getOutletId()).append("\",");
					addStr.append("\"outlet_name\": \"").append(ticket.getOutletName()).append("\",");
					addStr.append("\"merchant_user_id\": \"").append(ticket.getMerchantUserId()).append("\",");
					addStr.append("\"merchant_user_name\": \"").append(ticket.getMerchantUserName()).append("\",");
					addStr.append("\"refund_id\": \"").append(ticket.getRefundId()).append("\",");
					addStr.append("\"refund_time\": ").append(ticket.getRefundTime() == null ? 0 : ticket.getRefundTime()).append("");					
					addStr.append("}");
					addStr.append("}");
					addStr.append("\n");
					
					addWriter.write(addStr.toString());
					
					if ((ticket.getType().equals(SubOrderType.group_merchant.getCode()) && !ticket.getStatus().equals(TicketStatus.sent.getCode()))
							|| (ticket.getType().equals(SubOrderType.presell_org.getCode()) && !ticket.getStatus().equals(TicketStatus.init.getCode()))) {
						updateStr = new StringBuffer();
						updateStr.append("{");
						updateStr.append("\"key\": { \"ticket_id\": \"").append(ticket.getTicketId()).append("\"},");
						updateStr.append("\"action\": \"TICKETMODIFY\",");
						updateStr.append("\"client_id\": \"").append(clientId).append("\",");
						updateStr.append("\"time\": ")
								.append(ticket.getConsumeTime() != null ? ticket
										.getConsumeTime() : (ticket
										.getRefundTime() != null ? ticket
										.getRefundTime() : ticket
										.getCreateTime())).append(",");
						updateStr.append("\"data\": {");
						updateStr.append("\"id\": \"").append(ticket.getTicketId()).append("\",");
						updateStr.append("\"ticket_id\": \"").append(ticket.getTicketId()).append("\",");
						updateStr.append("\"order_id\": \"").append(ticket.getOrderId()).append("\",");
						updateStr.append("\"sub_order_id\": \"").append(ticket.getSubOrderId()).append("\",");
						updateStr.append("\"create_time\": ").append(ticket.getCreateTime()).append(",");
						updateStr.append("\"expire_time\": ").append(ticket.getExpireTime()).append(",");
						updateStr.append("\"type\": \"").append(ticket.getType()).append("\",");
						updateStr.append("\"member_code\": ").append(ticket.getMemberCode()).append(",");
						updateStr.append("\"member_name\": \"").append(ticket.getMemberName()).append("\",");
						updateStr.append("\"merchant_id\": \"").append(ticket.getMerchantId()).append("\",");
						updateStr.append("\"merchant_name\": \"").append(ticket.getMerchantName()).append("\",");
						updateStr.append("\"mobile\": \"").append(ticket.getMobile()).append("\",");
						updateStr.append("\"org_code\": \"").append("").append("\",");
						updateStr.append("\"org_name\": \"").append("").append("\",");
						updateStr.append("\"forg_code\": \"").append("").append("\",");
						updateStr.append("\"sorg_code\": \"").append("").append("\",");
						updateStr.append("\"torg_code\": \"").append("").append("\",");
						updateStr.append("\"lorg_code\": \"").append("").append("\",");
						updateStr.append("\"product_id\": \"").append(ticket.getProductId()).append("\",");
						updateStr.append("\"product_name\": \"").append(ticket.getProductName()).append("\",");
						updateStr.append("\"image\": \"").append(ticket.getImage()).append("\",");
						updateStr.append("\"price\": ").append(ticket.getPrice()).append(",");
						updateStr.append("\"quantity\": ").append(ticket.getQuantity()).append(",");
						updateStr.append("\"status\": \"").append(ticket.getStatus()).append("\",");
						updateStr.append("\"consume_time\": ").append(ticket.getConsumeTime() == null ? 0 : ticket.getConsumeTime()).append(",");
						updateStr.append("\"outlet_id\": \"").append(ticket.getOutletId()).append("\",");
						updateStr.append("\"outlet_name\": \"").append(ticket.getOutletName()).append("\",");
						updateStr.append("\"merchant_user_id\": \"").append(ticket.getMerchantUserId()).append("\",");
						updateStr.append("\"merchant_user_name\": \"").append(ticket.getMerchantUserName()).append("\",");
						updateStr.append("\"refund_id\": \"").append(ticket.getRefundId()).append("\",");						
						updateStr.append("\"refund_time\": ").append(ticket.getRefundTime() == null ? 0 : ticket.getRefundTime()).append("");
						updateStr.append("}");
						updateStr.append("}");
						updateStr.append("\n");
						
						updateWriter.write(updateStr.toString());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				addWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				addWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				updateWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				updateWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void makeSettlementLog(String startTime,String endTime,String clientId,String logPath){
		MongoManager mongoManager = null;
		DBObject queryObj = null;
		long dbStartTime = 0l;
		long dbEndTime = 0l;
		StringBuffer addStr = null;
		BufferedWriter addWriter = null;
		StringBuffer updateStr = null;
		BufferedWriter updateWriter = null;
		List<Settlement> settleList = null;
		Settlement settlement = null;
		
		try {
			addWriter = new BufferedWriter(new FileWriter(new File(new StringBuffer(logPath).append("settlement_add_history_").append(startTime).append("_").append(endTime).append(".log").toString())));
			updateWriter = new BufferedWriter(new FileWriter(new File(new StringBuffer(logPath).append("settlement_modify_history_").append(startTime).append("_").append(endTime).append(".log").toString())));
			
			dbStartTime = DateUtil.parse("yyyy-MM-dd HH:mm:ss", startTime+" 00:00:00").getTime();
			dbEndTime = DateUtil.parse("yyyy-MM-dd HH:mm:ss", endTime+" 23:59:59").getTime();
			
			queryObj = new BasicDBObject();
			queryObj.put("client_id", clientId);
			queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, dbStartTime).append(QueryOperators.LTE, dbEndTime));
			
			mongoManager = new MongoManager();
			
			settleList = (List<Settlement>) mongoManager.findAll(queryObj, "cb_settlement", Settlement.class);
			
			if (Checker.isNotEmpty(settleList)){
				for (int i = 0; i < settleList.size(); i++){
					settlement = settleList.get(i);
					addStr = new StringBuffer();
					addStr.append("{");
					addStr.append("\"key\": { \"settlement_id\": \"").append(settlement.getSettlementId()).append("\"},");
					addStr.append("\"action\": \"SETTLEMENTADD\",");
					addStr.append("\"client_id\": \"").append(clientId).append("\",");
					addStr.append("\"time\": ").append(settlement.getCreateTime()).append(",");
					addStr.append("\"data\": {");
//					addStr.append("\"id\": \"").append(settlement.getSettlementId()).append("\",");
					addStr.append("\"settlement_id\": \"").append(settlement.getSettlementId()).append("\",");
					addStr.append("\"create_time\": ").append(settlement.getCreateTime()).append(",");
					addStr.append("\"merchant_id\": \"").append(settlement.getMerchantId()).append("\",");
					addStr.append("\"merchant_name\": \"").append(settlement.getMerchantName()).append("\",");
					addStr.append("\"merchant_user_id\": \"").append(settlement.getMerchantUserId()).append("\",");
					addStr.append("\"money\": ").append(settlement.getMoney()).append(",");
					addStr.append("\"order_id\": \"").append(settlement.getOrderId()).append("\",");
					addStr.append("\"outlet_id\": \"").append(settlement.getOutletId()).append("\",");
					addStr.append("\"outlet_name\": \"").append(settlement.getOutletName()).append("\",");
					addStr.append("\"payment_id\": \"").append(settlement.getPaymentId()).append("\",");
					addStr.append("\"product_count\": ").append(settlement.getProductCount()).append(",");
					addStr.append("\"product_id\": \"").append(settlement.getProductId()).append("\",");
					addStr.append("\"product_name\": \"").append(settlement.getProductName()).append("\",");
					addStr.append("\"settle_state\": \"").append(settlement.getSettleState()).append("\",");
					addStr.append("\"sub_order_id\": \"").append(settlement.getSubOrderId()).append("\",");
					addStr.append("\"tickets\": [");
					if (Checker.isNotEmpty(settlement.getTickets())){
						for (int k = 0; k < settlement.getTickets().size(); k++){
							if (k > 0) {
								addStr.append(",\"").append(settlement.getTickets().get(k)).append("\"");
							} else {
								addStr.append("\"").append(settlement.getTickets().get(k)).append("\"");
							}
						}
					}
					addStr.append("],");
					addStr.append("\"type\": \"").append(settlement.getType()).append("\"");
					addStr.append("}");
					addStr.append("}");
					addStr.append("\n");
					
					addWriter.write(addStr.toString());
					
					if (!settlement.getSettleState().equals(SettlementStatus.unsettlemnt.getCode())) {
						updateStr = new StringBuffer();
						updateStr = new StringBuffer();
						updateStr.append("{");
						updateStr.append("\"key\": { \"settlement_id\": \"").append(settlement.getSettlementId()).append("\"},");
						updateStr.append("\"action\": \"SETTLEMENTMODIFY\",");
						updateStr.append("\"client_id\": \"").append(clientId).append("\",");
						updateStr.append("\"time\": ").append(settlement.getCreateTime()).append(",");
						updateStr.append("\"data\": {");
//						updateStr.append("\"id\": \"").append(settlement.getSettlementId()).append("\",");
						updateStr.append("\"settlement_id\": \"").append(settlement.getSettlementId()).append("\",");
						updateStr.append("\"create_time\": ").append(settlement.getCreateTime()).append(",");
						updateStr.append("\"merchant_id\": \"").append(settlement.getMerchantId()).append("\",");
						updateStr.append("\"merchant_name\": \"").append(settlement.getMerchantName()).append("\",");
						updateStr.append("\"merchant_user_id\": \"").append(settlement.getMerchantUserId()).append("\",");
						updateStr.append("\"money\": ").append(settlement.getMoney()).append(",");
						updateStr.append("\"order_id\": \"").append(settlement.getOrderId()).append("\",");
						updateStr.append("\"outlet_id\": \"").append(settlement.getOutletId()).append("\",");
						updateStr.append("\"outlet_name\": \"").append(settlement.getOutletName()).append("\",");
						updateStr.append("\"payment_id\": \"").append(settlement.getPaymentId()).append("\",");
						updateStr.append("\"product_count\": ").append(settlement.getProductCount()).append(",");
						updateStr.append("\"product_id\": \"").append(settlement.getProductId()).append("\",");
						updateStr.append("\"product_name\": \"").append(settlement.getProductName()).append("\",");
						updateStr.append("\"settle_state\": \"").append(settlement.getSettleState()).append("\",");
						updateStr.append("\"sub_order_id\": \"").append(settlement.getSubOrderId()).append("\",");
						updateStr.append("\"tickets\": [");
						if (Checker.isNotEmpty(settlement.getTickets())){
							for (int k = 0; k < settlement.getTickets().size(); k++){
								if (k > 0) {
									updateStr.append(",\"").append(settlement.getTickets().get(k)).append("\"");
								} else {
									updateStr.append("\"").append(settlement.getTickets().get(k)).append("\"");
								}
							}
						}
						updateStr.append("],");
						updateStr.append("\"type\": \"").append(settlement.getType()).append("\"");
						updateStr.append("}");
						updateStr.append("}");
						updateStr.append("\n");
						
						updateWriter.write(updateStr.toString());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				addWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				addWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				updateWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				updateWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
