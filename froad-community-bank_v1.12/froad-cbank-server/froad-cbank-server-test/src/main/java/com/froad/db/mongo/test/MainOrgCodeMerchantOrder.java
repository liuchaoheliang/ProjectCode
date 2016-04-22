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
 * @Title: MainOrgCodeMerchantOrder.java
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

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * <p>
 * Title: MainOrgCodeMerchantOrder.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年8月4日 下午4:24:39
 */

public class MainOrgCodeMerchantOrder {

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
//			340822	安庆农村商业银行
			String orgCode = "340822";
			String fileName = "C:\\Users\\FQ\\Desktop\\青峰\\安庆农商行.csv";

			Set<String> merchantIdSet = new HashSet<String>();
			// merchant_id=073B8C220006 outlet_id=073B8ED20048


			fw = new FileWriter(fileName, false);
			String header = "序号,订单号,创建时间,订单金额,商品名称,价格,购买数量,所属商户,订单状态,提货状态,提货人姓名,提货人手机号" + NEWLINE;
			fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }));
			fw.write(header);

			DBCollection merchant_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_merchant_detail");
			DBCollection order_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_order");
			DBCollection suborder_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_suborder_product");
			DBCollection ticket_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_ticket");
			DBCollection settlement_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_settlement");
			
			BasicDBObject merchant_where = new BasicDBObject();
			BasicDBList merchant_values = new BasicDBList();
			merchant_values.add(new BasicDBObject("city_org_code", orgCode));
			merchant_values.add(new BasicDBObject("county_org_code", orgCode));
			merchant_values.add(new BasicDBObject("pro_org_code", orgCode));
			merchant_where.put(QueryOperators.OR, merchant_values);
			
			System.out.println("find cb_merchant_detail where====>" + merchant_where);
			List<DBObject> merchantList = merchant_dbConnection.find(merchant_where, new BasicDBObject("_id", 1)).toArray();
			int merchantListsize = merchantList == null ? 0 : merchantList.size();
			System.out.println("机构号为:" + orgCode + "的商户共有:" + merchantListsize + "个");
			
			int seq = 1;
			if (merchantListsize > 0) {
				for (int k = 0; k < merchantListsize; k++) {
					double globalPlan = (k + 1.0) / merchantListsize * 100.0;
					String globalPlanOfPercent = String.format("%.2f", globalPlan) + "%";
					
					BasicDBObject merchantObject = (BasicDBObject) merchantList.get(k);
					String merchantId = merchantObject.getString("_id");
					if(StringUtils.isEmpty(merchantId))
						continue;
					System.out.println("机构号为:" + orgCode + ", 正在导出[" + (k + 1) + "/" + merchantListsize + "], 当前机构导出进度:" + globalPlanOfPercent);
					

					BasicDBObject where = new BasicDBObject();

//					long startTime = 1433088001000l;// 2015-06-01 00:00:01   
//					long endTime = 1438358399000l; // 2015-07-31 23:59:59
					BasicDBList values = new BasicDBList();
					values.add(new BasicDBObject("merchant_id", merchantId));
//					values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime))); 
					where.put(QueryOperators.AND, values);
					
					System.out.println("find cb_order where====>" + where);
					List<DBObject> orderList = order_dbConnection.find(where).sort(new BasicDBObject("create_time", -1)).toArray();

					int orderListsize = orderList == null ? 0 : orderList.size();
					System.out.println("商户id为:" + merchantId + "的面对面订单共有:" + orderListsize + "个");
					if (orderListsize > 0) {
						for (int i = 0; i < orderListsize; i++) {
							StringBuilder sb = new StringBuilder();

							System.out.println("商户id为:" + merchantId + ", 正在导出[" + (i + 1) + "/" + orderListsize + "], 当前商户导出进度:" + String.format("%.2f", (i + 1.0) / orderListsize * 100.0) + "%" + ", 总进度:" + globalPlanOfPercent);

							BasicDBObject orderObject = (BasicDBObject) orderList.get(i);
							int is_qrcode = orderObject.getInt("is_qrcode"); // 是否是面对面
							String orderId = orderObject.getString("_id");
							System.out.println("orderId===========>" + orderId + "\tis_qrcode=======>" + is_qrcode);
							if (1 == is_qrcode) {// 面对面
//								序号,订单号,创建时间,订单金额,商品名称,价格,购买数量,所属商户,订单状态,提货状态,提货人姓名,提货人手机号
								sb.append(seq++);
								sb.append(SEPARATOR_CHAR);
								
								sb.append(orderId);
								sb.append(SEPARATOR_CHAR);

								if (orderObject.containsField("create_time"))
									sb.append(sdf.format(new Date(orderObject.getLong("create_time"))));
								sb.append(SEPARATOR_CHAR);

								if (orderObject.containsField("total_price")) {
									double price = orderObject.getDouble("total_price");
									sb.append(getMoney(price));
								}
								sb.append(SEPARATOR_CHAR);

								sb.append(SEPARATOR_CHAR);
								
								sb.append(SEPARATOR_CHAR);
								
								sb.append(SEPARATOR_CHAR);
								
								if (orderObject.containsField("merchant_name"))
									sb.append(orderObject.getString("merchant_name"));
								sb.append(SEPARATOR_CHAR);
								
								if (orderObject.containsField("order_status"))
									sb.append(orderObject.get("order_status"));
								sb.append(SEPARATOR_CHAR);

								sb.append(SEPARATOR_CHAR);

								if (orderObject.containsField("member_name"))
									sb.append(orderObject.get("member_name"));
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
					BasicDBObject ticket_where = new BasicDBObject("merchant_id", merchantId);
					System.out.println("find cb_ticket where====>" + ticket_where);
					List<DBObject> ticketList = ticket_dbConnection.find(ticket_where).sort(new BasicDBObject("create_time", -1)).toArray();
					int ticketListsize = ticketList == null ? 0 : ticketList.size();
					if (ticketListsize > 0) {
						System.out.println("商户id为:" + merchantId + "的团购券共有:" + ticketListsize + "个");
						for (int j = 0; j < ticketListsize; j++) {
							System.out.println("商户id为:" + merchantId + ", 正在导出[" + (j + 1) + "/" + ticketListsize + "], 当前商户订单导出进度:" + String.format("%.2f", (j + 1.0) / ticketListsize * 100.0) + "%" + ", 总进度:" + globalPlanOfPercent);
							BasicDBObject ticketObject = (BasicDBObject)ticketList.get(j);
							
							BasicDBObject orderObject = (BasicDBObject) order_dbConnection.findOne(new BasicDBObject("_id", ticketObject.get("order_id")));
							BasicDBObject subOrderObject = (BasicDBObject) suborder_dbConnection.findOne(new BasicDBObject("order_id", ticketObject.get("order_id")));
//							序号,订单号,创建时间,订单金额,商品名称,价格,购买数量,所属商户,订单状态,提货状态,提货人姓名,提货人手机号
							sb.append(seq++);
							sb.append(SEPARATOR_CHAR);
							
							if (ticketObject.containsField("order_id"))
								sb.append(ticketObject.get("order_id"));
							sb.append(SEPARATOR_CHAR);

							if (ticketObject.containsField("create_time"))
								sb.append(sdf.format(new Date(ticketObject.getLong("create_time"))));
							sb.append(SEPARATOR_CHAR);
							
							if (orderObject.containsField("total_price")) {
								double price = orderObject.getDouble("total_price");
								sb.append(getMoney(price));
							}
							sb.append(SEPARATOR_CHAR);

							if (ticketObject.containsField("product_name"))
								sb.append(ticketObject.get("product_name").toString().replace(",", ""));
							sb.append(SEPARATOR_CHAR);								

							if (ticketObject.containsField("price")){
		                        double price = ticketObject.getDouble("price");
		                        sb.append(getMoney(price));
		                    }
							sb.append(SEPARATOR_CHAR);

							if (ticketObject.containsField("quantity"))
								sb.append(ticketObject.getInt("quantity"));
							sb.append(SEPARATOR_CHAR);

							if (ticketObject.containsField("merchant_name"))
								sb.append(ticketObject.get("merchant_name").toString().replace(",", ""));
							sb.append(SEPARATOR_CHAR);	
							
							if (orderObject.containsField("order_status"))
								sb.append(orderObject.get("order_status"));
							sb.append(SEPARATOR_CHAR);
							
							if (ticketObject.containsField("status"))
								sb.append(ticketObject.get("status"));
							sb.append(SEPARATOR_CHAR);

							//收货信息							
							String consignee = "";
							String phone = "";
							if(orderObject.containsField("recv_id")){
								String recv_id = orderObject.get("recv_id").toString();			            
					            DBCollection mof_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_merchant_outlet_favorite");
					            DBObject mof = (DBObject) mof_dbConnection.findOne(new BasicDBObject("recv_info.recv_id",recv_id));
					            System.out.println(mof.toString());
					            JSONArray recv_info_json = JSONArray.parseArray(mof.get("recv_info").toString());  
					            
					            if(recv_info_json.size() > 0){
					            	for(int i=0;i<recv_info_json.size();i++){
					                    JSONObject recv = recv_info_json.getJSONObject(i);  // 遍历数组，把每一个对象转成 json 对象
					                    String recvId = recv.get("recv_id").toString() ;
					                    if(recv_id.equals(recvId)){
					                    	consignee = recv.get("consignee").toString() ;
					                    	phone = recv.get("phone").toString() ;
					                    }
					                 }
					            }
							}
				            
				            sb.append(consignee);
				            sb.append(SEPARATOR_CHAR);
				            
				            sb.append(phone);
				            sb.append(SEPARATOR_CHAR);
							
							sb.append(NEWLINE);
							fw.write(sb.toString());
							fw.flush();

							sb.delete(0, sb.length());
						}
						System.out.println("*****************************导出商户id为:" + merchantId + "的[团购订单]完毕*****************************");
						System.out.println(NEWLINE);
					}
				

				}
			}
			
			System.out.println("*****************************导出机构号为:" + orgCode + "的所有[订单]完毕*****************************");
			System.out.println(NEWLINE);

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
