package com.froad.db.mongo.test;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryOperators;
import com.mongodb.ServerAddress;

/**
 * 朱博统计
 * ClassName: ZBMainOrder
 * Function: TODO ADD FUNCTION
 * date: 2015年8月3日 下午5:28:06
 *
 * @author FQ
 * @version
 */
public class ZBMainOrder {

	static Map<String,String> orderStatusMap=new HashMap<String,String>();
	static{
		orderStatusMap.put("1", "1-订单创建");
		orderStatusMap.put("2", "2-订单用户关闭");
		orderStatusMap.put("3", "3-订单系统关闭");
		orderStatusMap.put("4", "4-订单支付中");
		orderStatusMap.put("5", "5-订单支付失败");
		orderStatusMap.put("6", "6-订单支付完成");
	}
	
	public static void main(String[] args) throws Exception{
		
		FileWriter fw = new FileWriter("C:\\Users\\FQ\\Desktop\\青峰\\朱博-订单.csv");
		String header = "memberCode,memberName\r\n";
		fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
		fw.write(header);
		
		DBCollection ord_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_order");
		BasicDBObject where = new BasicDBObject();
		
		//时间 2015-07-26 06:38:24
		String start_create_time="2015-07-01 00:00:00";
		String end_create_time="2015-07-31 23:59:59";
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    Date statrt_time= sdf.parse(start_create_time);
	    Date end_time= sdf.parse(end_create_time);
	    
	    BasicDBList values = new BasicDBList();
	    where.put("order_status", "6"); //订单支付完成
		values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,statrt_time.getTime())));
		values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,end_time.getTime())));
		where.put(QueryOperators.AND,values);
		List<DBObject> orderList = ord_dbConnection.find(where).toArray();
		
		System.out.println("数量："+orderList.size());
		
		int a=1;
        for(DBObject order : orderList){
        	System.out.println("查询"+a);
        	System.out.println("order== "+order.toString());
        	JSONObject ord_json = JSON.parseObject(order.toString());
        	
        	//订单创建时间
        	String creatTime = ord_json.get("create_time").toString();
        	Date date = new Date(Long.parseLong(creatTime.trim()));  
    	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	    String createTime = formatter.format(date); 
    	    
    	    String orderId = ord_json.get("_id").toString();
    	    String orderStatus =  orderStatusMap.get(ord_json.get("order_status").toString());
        	String memberCode = null;
        	if(ord_json.containsKey("member_code")){
        		memberCode = ord_json.get("member_code").toString();
        	}
        	
        	String memberName = null;
        	if(ord_json.containsKey("member_name")){
        		memberName = ord_json.get("member_name").toString();
        	}
        	
        	
    		
    		StringBuffer str = new StringBuffer();
            str.append(memberCode+",");
            str.append(memberName+",");
            str.append("\r\n");
            
            fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
			fw.write(str.toString());
		    fw.flush();
		    
		    System.out.println(" ");
		    a++;
        }
        
        fw.close();
        System.out.println("end");
		
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
