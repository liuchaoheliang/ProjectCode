package com.froad.db.mongo.test;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class ExportBillNo {
	private static final String NEWLINE = System.getProperty("line.separator", "\n");
	private static final String USER_HOME = System.getProperty("user.home"); //
	private static final String WORK_DIR = System.getProperty("user.dir");
	private static final String FILE_SEPARATOR = System.getProperty("file.separator", "/");

	private static final String SEPARATOR_CHAR = ",";

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	

//	static Map<String,String> orderStatusMap=new HashMap<String,String>();
//	static{
//		orderStatusMap.put("1", "1-订单创建");
//		orderStatusMap.put("2", "2-订单用户关闭");
//		orderStatusMap.put("3", "3-订单系统关闭");
//		orderStatusMap.put("4", "4-订单支付中");
//		orderStatusMap.put("5", "5-订单支付失败");
//		orderStatusMap.put("6", "6-订单支付完成");
//	}
	
	public static void main(String[] args) throws Exception{
		FileWriter fw = null;
		try{
			
			int days = 100; // 默认100天
			if (ArrayUtils.isNotEmpty(args)) {
				days = Integer.parseInt(args[0].trim());
			}
			
			String fileName = "export_"+days+"_billno.txt";
			String filePath = USER_HOME + fileName;
			filePath = "d:\\" + fileName;
			
			System.out.println("导出" + days +"天之前的数据");
	//		long currTime = System.currentTimeMillis();
			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.DAY_OF_YEAR, -days);
			long beforeTime = ca.getTimeInMillis();
			String beforeTimeStr = sdf.format(new Date(beforeTime));
//			System.out.println("导出[" + beforeTimeStr +"]之前的数据");
			
			
			
			
			
			// 商户ID、商户名称、商品ID、商品名称、订单号、团购券、券状态、过期时间、下单时间、验码时间、是否结算
			System.out.println("*****************************导出" + beforeTimeStr + "之前的订单到[" + filePath + "]开始*****************************");
			fw = new FileWriter(filePath, false);
	//		String header = "商户ID,商户名称,商品ID,商品名称,订单号,团购券,券状态,过期时间,下单时间,验码时间,是否结算" + NEWLINE;
	//		fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
	//		fw.write(header);	
			
			DBCollection ticket_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_ticket");
			DBCollection payment_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_payment");
			BasicDBObject where = new BasicDBObject();
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.LTE, beforeTime)));
			values.add(new BasicDBObject("status", "1"));
			values.add(new BasicDBObject("type", "1"));
			values.add(new BasicDBObject("refund_id", new BasicDBObject(QueryOperators.EXISTS, false))); // 未退过款的
			where.put(QueryOperators.AND, values);
			
			System.out.println("where====>" + where);
			List<DBObject> orderIdList = ticket_dbConnection.find(where, new BasicDBObject("order_id", 1)).toArray();
			int size = orderIdList.size();
			if (orderIdList != null && size > 0) {
				for (int j = 0; j < size; j++) {
					System.out.println(beforeTimeStr + "未消费的团购券总共有:" + size + "个, 正在导出第[" + (j + 1) + "]个订单, 导出进度:" + String.format("%.2f", (j + 1.0) / size * 100.0) + "%");
					
					BasicDBObject dbObject = (BasicDBObject)orderIdList.get(j);
					String orderId = dbObject.getString("order_id");
					
					DBObject where_payment = new BasicDBObject();
					where_payment.put("order_id", orderId);
					where_payment.put("is_enable", true);
					where_payment.put("payment_reason", "2");
					where_payment.put("payment_status", "4");
					where_payment.put("payment_type", 2);
					
					BasicDBObject payment = (BasicDBObject) payment_dbConnection.findOne(where_payment, new BasicDBObject("bill_no", 1));
					if (null != payment && payment.containsField("bill_no")) {
						String billNo = payment.getString("bill_no");
						fw.write(billNo);
						fw.write(NEWLINE);
					}
					fw.flush();
				}
			}
			
			
			System.out.println("*****************************导出" + beforeTimeStr + "之前的订单到[" + filePath + "]完毕*****************************");
			System.out.println(NEWLINE);
			
			
			
	//		fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
		    fw.flush();
	        
	        System.out.println("end");
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			if(fw!=null)fw.close();
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
