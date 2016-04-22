package com.froad.db.mongo.test;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class MainMerchantTicket {
	private static final String NEWLINE = System.getProperty("line.separator", "\n");
	private static final String USER_HOME = System.getProperty("user.home"); // 
	private static final String WORK_DIR = System.getProperty("user.dir");
	private static final String FILE_SEPARATOR= System.getProperty("file.separator","/");
	
	private static final String SEPARATOR_CHAR= ",";
	
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	
	
	public static void main(String[] args) throws Exception{
		FileWriter fw = null;
		try{
		String fileName = "C:\\Users\\FQ\\Desktop\\青峰\\merchant_tiket.csv";
		
		Set<String> merchantIdSet = new HashSet<String>();
		merchantIdSet.add("073B8B5200A9");
		merchantIdSet.add("073B8B920095");
		merchantIdSet.add("073B8B920092");
		merchantIdSet.add("073B8BA200A4");
		merchantIdSet.add("073B8B620011");
		merchantIdSet.add("073B8BC2002E");
		
		// 商户ID、商户名称、商品ID、商品名称、订单号、团购券、券状态、过期时间、下单时间、验码时间、是否结算
		
		fw = new FileWriter(fileName, false);
		String header = "商户ID,商户名称,商品ID,商品名称,订单号,支付账单号,团购券,券状态,过期时间,下单时间,验码时间,是否结算" + NEWLINE;
		fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
		fw.write(header);	
		
		DBCollection ticket_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_ticket");
		DBCollection payment_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_payment");
		DBCollection settlement_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_settlement");
		BasicDBObject where = new BasicDBObject();
		
		String temp = null;
		for (String merchantId : merchantIdSet) {
			merchantId = merchantId.trim();

				System.out.println("正在导出商户id为:" + merchantId + "的券");
	
				BasicDBList values = new BasicDBList();
				values.add(new BasicDBObject("merchant_id", merchantId));
				where.put(QueryOperators.AND,values);
				List<DBObject> ticketList = ticket_dbConnection.find(where).toArray();
				
				StringBuilder sb = new StringBuilder();
				int size =ticketList.size();
				if (ticketList != null && size > 0) {
					System.out.println("商户id为:" + merchantId + "的总共有券:" + ticketList.size() + "个");
					for (int j = 0; j < ticketList.size(); j++) {
						DBObject dbObject = ticketList.get(j);
						BasicDBObject paymentObject = (BasicDBObject) payment_dbConnection.findOne(new BasicDBObject("order_id", dbObject.get("order_id").toString()).append("payment_status", "4").append("payment_reason", "2").append("is_enable", true));
						
						System.out.println("商户id为:" + merchantId + "总共有券:" + ticketList.size() + "个, 正在导出第[" + (j + 1) + "]个, 当前商户导出进度:" + String.format("%.2f", (j + 1.0) / size * 100.0) + "%");
	
						JSONObject ticket_json = JSON.parseObject(dbObject.toString());
						
						if (ticket_json.containsKey("merchant_id"))
							sb.append(ticket_json.get("merchant_id"));
						sb.append(SEPARATOR_CHAR);
						
						if (ticket_json.containsKey("merchant_name"))
							sb.append(ticket_json.get("merchant_name"));
						sb.append(SEPARATOR_CHAR);
						
						if (ticket_json.containsKey("product_id"))
							sb.append(ticket_json.get("product_id"));
						sb.append(SEPARATOR_CHAR);
						
						if (ticket_json.containsKey("product_name"))
							temp = ticket_json.get("product_name").toString().replace(",", "");
							sb.append(temp);
						sb.append(SEPARATOR_CHAR);
						
						if (ticket_json.containsKey("order_id"))
							sb.append(ticket_json.get("order_id"));
						sb.append(SEPARATOR_CHAR);
						
						if (paymentObject.containsField("bill_no"))
							sb.append(paymentObject.get("bill_no"));
						sb.append(SEPARATOR_CHAR);
						
						if (ticket_json.containsKey("ticket_id"))
							sb.append(ticket_json.get("ticket_id"));
						sb.append(SEPARATOR_CHAR);
						
						if (ticket_json.containsKey("status"))
							sb.append(ticket_json.get("status"));
						sb.append(SEPARATOR_CHAR);
						
						if (ticket_json.containsKey("expire_time"))
							sb.append(sdf.format(new Date(ticket_json.getLongValue("expire_time"))));
						sb.append(SEPARATOR_CHAR);
						
						if (ticket_json.containsKey("create_time"))
							sb.append(sdf.format(new Date(ticket_json.getLongValue("create_time"))));
						sb.append(SEPARATOR_CHAR);
						
						if (ticket_json.containsKey("consume_time"))
							sb.append(sdf.format(new Date(ticket_json.getLongValue("consume_time"))));
						sb.append(SEPARATOR_CHAR);
						
						DBObject where_stt = new BasicDBObject();
						where_stt.put("tickets", ticket_json.getString("ticket_id"));
						BasicDBObject settlement = (BasicDBObject)settlement_dbConnection.findOne(where_stt);
						
						if(null!=settlement && settlement.containsKey("settle_state")){
							sb.append(settlement.get("settle_state"));
						} else {
							sb.append(0);						
						}
						
						/**********************************************************/
						sb.append(NEWLINE);
						fw.write(sb.toString());
						fw.flush();
						
						sb.delete(0, sb.length());
					}
				} else {
					System.err.println("商户id为:" + merchantId + "无对应的券");
				}
				System.out.println("*****************************导出商户id为:" + merchantId + "的券完毕*****************************");
				System.out.println(NEWLINE);
			}
			
			
			fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
		    fw.flush();
	        
	        
	        System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fw != null)fw.close();
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
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
