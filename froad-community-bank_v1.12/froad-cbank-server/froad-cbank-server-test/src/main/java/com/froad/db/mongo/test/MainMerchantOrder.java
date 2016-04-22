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
 * @Title: MainMerchantOrder.java
 * @Package com.froad.db.mongo.test
 * @Description: TODO
 * @author vania
 * @date 2015年8月4日
 */

package com.froad.db.mongo.test;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * <p>
 * Title: MainMerchantOrder.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年8月4日 下午4:24:39
 */

public class MainMerchantOrder {

	private static final String NEWLINE = System.getProperty("line.separator", "\n");
	private static final String USER_HOME = System.getProperty("user.home"); //
	private static final String WORK_DIR = System.getProperty("user.dir");
	private static final String FILE_SEPARATOR = System.getProperty("file.separator", "/");

	private static final String SEPARATOR_CHAR = ",";

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * @Title: main
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param args
	 * @throws Exception
	 * @return void 返回类型
	 * @throws
	 */
	public static void main(String[] args) throws Exception {
		FileWriter fw = null;
		try {
			String fileName = "C:\\Users\\FQ\\Desktop\\青峰\\安徽旌德农商行.csv";

			Set<String> merchantIdSet = new HashSet<String>();
			// merchant_id=073B8C220006 outlet_id=073B8ED20048


			fw = new FileWriter(fileName, false);
			String header = "订单号,购买时间,商品ID,商品名称,积分,现金,订单状态,付款人姓名,团购券,提货状态,结算状态,提货时间,所属商户,所属门店" + NEWLINE;
			fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }));
			fw.write(header);

			DBCollection order_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_order");
			DBCollection ticket_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_ticket");
			DBCollection settlement_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_settlement");

			BasicDBObject where = new BasicDBObject();

			String merchantId = "073B8C220006";
//			String merchantId = "07162F82800A";
			long startTime = 1433088001000l;// 2015-06-01 00:00:01   
			long endTime = 1438358399000l; // 2015-07-31 23:59:59
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("merchant_id", merchantId));
			values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime))); 
			where.put(QueryOperators.AND, values);
			
			System.out.println("find cb_order where====>" + where);
			List<DBObject> orderList = order_dbConnection.find(where).sort(new BasicDBObject("create_time", -1)).toArray();

			int orderListsize = orderList == null ? 0 : orderList.size();
			System.out.println("商户id为:" + merchantId + "的面对面订单共有:" + orderListsize + "个");
			if (orderListsize > 0) {
				for (int i = 0; i < orderListsize; i++) {
					StringBuilder sb = new StringBuilder();

					System.out.println("商户id为:" + merchantId + ", 正在导出[" + (i + 1) + "/" + orderListsize + "], 当前商户导出进度:" + String.format("%.2f", (i + 1.0) / orderListsize * 100.0) + "%");

					BasicDBObject orderObject = (BasicDBObject) orderList.get(i);
					int is_qrcode = orderObject.getInt("is_qrcode"); // 是否是面对面
					String orderId = orderObject.getString("_id");
					System.out.println("orderId===========>" + orderId + "\tis_qrcode=======>" + is_qrcode);
					if (1 == is_qrcode) {// 面对面
						sb.append(orderId);
						sb.append(SEPARATOR_CHAR);

						if (orderObject.containsField("create_time"))
							sb.append(sdf.format(new Date(orderObject.getLong("create_time"))));
						sb.append(SEPARATOR_CHAR);

						sb.append(SEPARATOR_CHAR);

						sb.append(SEPARATOR_CHAR);

						if (orderObject.containsField("bank_points")){
						    double proint = orderObject.getDouble("bank_points");
							sb.append(getMoney(proint));
						}
						sb.append(SEPARATOR_CHAR);

						if (orderObject.containsField("real_price")) {
							double price = orderObject.getDouble("real_price");
							sb.append(getMoney(price));
						}
						sb.append(SEPARATOR_CHAR);
						if (orderObject.containsField("order_status"))
							sb.append(orderObject.get("order_status"));
						sb.append(SEPARATOR_CHAR);

						if (orderObject.containsField("member_name"))
							sb.append(orderObject.get("member_name"));
						sb.append(SEPARATOR_CHAR);

						sb.append(SEPARATOR_CHAR);
						
						sb.append(SEPARATOR_CHAR);

						sb.append(SEPARATOR_CHAR);

						sb.append(SEPARATOR_CHAR);

						if (orderObject.containsField("merchant_name"))
							sb.append(orderObject.get("merchant_name"));
						sb.append(SEPARATOR_CHAR);

						sb.append(SEPARATOR_CHAR);
					}

					/**********************************************************/
					sb.append(NEWLINE);
					fw.write(sb.toString());
					fw.flush();

					sb.delete(0, sb.length());
				}
			}
			System.out.println("*****************************导出商户id为:" + merchantId + "的[面对面]订单完毕*****************************");
			System.out.println(NEWLINE);
			
			StringBuilder sb = new StringBuilder();
			BasicDBObject ticket_where = new BasicDBObject("merchant_id", merchantId).append("create_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime));
			System.out.println("find cb_ticket where====>" + ticket_where);
			List<DBObject> ticketList = ticket_dbConnection.find(ticket_where).sort(new BasicDBObject("create_time", -1)).toArray();
			int ticketListsize = ticketList == null ? 0 : ticketList.size();
			if (ticketListsize > 0) {
				System.out.println("商户id为:" + merchantId + "的团购券共有:" + ticketListsize + "个");
				for (int j = 0; j < ticketListsize; j++) {
					System.out.println("商户id为:" + merchantId + ", 正在导出[" + (j + 1) + "/" + ticketListsize + "], 当前商户订单导出进度:" + String.format("%.2f", (j + 1.0) / ticketListsize * 100.0) + "%");
					BasicDBObject ticketObject = (BasicDBObject)ticketList.get(j);
					
					BasicDBObject orderObject = (BasicDBObject) order_dbConnection.findOne(new BasicDBObject("_id", ticketObject.get("order_id")));
//					String header = "订单号,购买时间,商品ID,商品名称,积分支付金额,银行卡支付金额,订单状态,付款人姓名,团购券,提货状态,结算状态,提货时间,所属商户,所属门店" + NEWLINE;
					if (ticketObject.containsField("order_id"))
						sb.append(ticketObject.get("order_id"));
					sb.append(SEPARATOR_CHAR);

					if (ticketObject.containsField("create_time"))
						sb.append(sdf.format(new Date(ticketObject.getLong("create_time"))));
					sb.append(SEPARATOR_CHAR);

					if (ticketObject.containsField("product_id"))
						sb.append(ticketObject.get("product_id"));
					sb.append(SEPARATOR_CHAR);

					if (ticketObject.containsField("product_name"))
						sb.append(ticketObject.get("product_name").toString().replace(",", ""));
					sb.append(SEPARATOR_CHAR);								

					if (orderObject.containsField("bank_points")){
                        double proint = orderObject.getDouble("bank_points");
                        sb.append(getMoney(proint));
                    }
					sb.append(SEPARATOR_CHAR);

					if (orderObject.containsField("real_price")) {
						double price = orderObject.getDouble("real_price");
						sb.append(getMoney(price));
					}
					sb.append(SEPARATOR_CHAR);
					if (orderObject.containsField("order_status"))
						sb.append(orderObject.get("order_status"));
					sb.append(SEPARATOR_CHAR);

					if (ticketObject.containsField("member_name"))
						sb.append(ticketObject.get("member_name")).append("-").append(ticketObject.get("sms_number"));
					sb.append(SEPARATOR_CHAR);

					if (ticketObject.containsField("ticket_id"))
						sb.append(ticketObject.get("ticket_id"));
					sb.append(SEPARATOR_CHAR);								
					
					if (ticketObject.containsField("status"))
						sb.append(ticketObject.get("status"));
					sb.append(SEPARATOR_CHAR);								

					DBObject where_stt = new BasicDBObject();
					where_stt.put("tickets", ticketObject.getString("ticket_id"));
					BasicDBObject settlement = (BasicDBObject) settlement_dbConnection.findOne(where_stt);

					if (null != settlement && settlement.containsField("settle_state")) {
						sb.append(settlement.get("settle_state"));
					} else {
						sb.append(0);
					}
					sb.append(SEPARATOR_CHAR);

					if (ticketObject.containsField("consume_time"))
						sb.append(sdf.format(new Date(ticketObject.getLong("consume_time"))));
					sb.append(SEPARATOR_CHAR);

					if (ticketObject.containsField("merchant_name"))
						sb.append(ticketObject.get("merchant_name"));
					sb.append(SEPARATOR_CHAR);
					
					if (ticketObject.containsField("outlet_name"))
						sb.append(ticketObject.get("outlet_name"));
					sb.append(SEPARATOR_CHAR);
					
					sb.append(NEWLINE);
					fw.write(sb.toString());
					fw.flush();

					sb.delete(0, sb.length());
				}
				System.out.println("*****************************导出商户id为:" + merchantId + "的[团购订单]完毕*****************************");
				System.out.println(NEWLINE);
			}
		


			fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }));
			fw.flush();

			System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null)
				fw.close();
		}
	}

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	private static double getMoney(double momey) {
		return div(momey, 1000);
	}

	private static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	private static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
