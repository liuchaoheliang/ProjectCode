package com.froad.db.mongo.test;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MainDongHu {

	protected static MongoClient readMongoClient = null;
	protected static DB readMongoDB = null;
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		FileWriter fw = new FileWriter("C:\\Users\\FQ\\Desktop\\青峰\\支付成功用户订单.csv");
		String header = "创建时间,订单号,状态,memberCode\r\n";
		fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
		fw.write(header);
		
		DBCollection ord_dbConnection = getReadDBCollection("cb_order");
		BasicDBObject where = new BasicDBObject();
		where.put("order_status", "6"); //订单支付完成
		
		List<DBObject> orderList = ord_dbConnection.find(where).toArray();
		System.out.println("数量："+orderList.size());
		
		int a=1;
        for(DBObject order : orderList){
        	System.out.println("查询"+a);
        	JSONObject ord_json = JSON.parseObject(order.toString());
        	
        	//订单创建时间
        	String creatTime = ord_json.get("create_time").toString();
        	Date date = new Date(Long.parseLong(creatTime.trim()));  
    	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	    String createTime = formatter.format(date); 
    	    
    	    String orderId = ord_json.get("_id").toString();
    	    String orderStatus =  ord_json.get("order_status").toString();
        	String memberCode = ord_json.get("member_code").toString();
        	
        	StringBuffer str = new StringBuffer();
            str.append(createTime+",");
            str.append(orderId+",");
            str.append(orderStatus+",");
            str.append(memberCode+",");
            
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
	
	private static DBCollection getReadDBCollection(String connName) throws Exception{
		readMongoClient=new MongoClient(new ServerAddress("10.24.3.53", 6330));
		readMongoDB = readMongoClient.getDB("cbank");
		readMongoDB.authenticate("cbank", "123456".toCharArray());
		return readMongoDB.getCollection(connName);
	}

}
