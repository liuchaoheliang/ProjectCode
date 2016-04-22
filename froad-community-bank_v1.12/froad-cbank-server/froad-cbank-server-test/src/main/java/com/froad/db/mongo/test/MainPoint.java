package com.froad.db.mongo.test;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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


public class MainPoint {

	protected static MongoClient readMongoClient = null;
	protected static DB readMongoDB = null;
	
	static DBHelper db1 = null;
	static ResultSet ret1 = null;
	
	public static void main(String[] args) throws Exception{
		
		FileWriter fw = new FileWriter("C:\\Users\\FQ\\Desktop\\青峰\\积分发货6.1-7.31.csv");
		String header = "创建时间,订单号,状态,总价,提货人姓名,城市,地址,手机号,商品ID,商品名称,购买数量\r\n";
		fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
		fw.write(header);
		
		DBCollection sub_dbConnection = getReadDBCollection("cb_suborder_product");
		
		BasicDBObject where = new BasicDBObject();
		where.put("order_status", "6"); //订单支付完成
		where.put("products.type", "4");
		
		//时间
//		String start_create_time="2015-08-07 00:00:00";
//		String end_create_time="2015-08-10 23:59:59";
		
		String start_create_time="2015-06-01 00:00:00";
		String end_create_time="2015-07-31 23:59:59";
		
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    Date statrt_time=sdf.parse(start_create_time);
	    Date end_time=sdf.parse(end_create_time);
	    
	    BasicDBList values = new BasicDBList();
		values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,statrt_time.getTime())));
		values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,end_time.getTime())));
		where.put(QueryOperators.AND,values);
		List<DBObject> subOrderlist = sub_dbConnection.find(where).toArray();
		
		System.out.println("数量："+subOrderlist.size());
		
		int a=1;
        for(DBObject result : subOrderlist){
        	System.out.println("查询"+a);
        	System.out.println(result.toString());
        	JSONObject sub_json = JSON.parseObject(result.toString());
        	
        	//订单创建时间
        	String creatTime=sub_json.get("create_time").toString();
        	Date date= new Date(Long.parseLong(creatTime.trim()));  
    	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	    String dateString = formatter.format(date); 
        	
        	//订单号
            String orderId = sub_json.get("order_id").toString();
           
            //商品
            String product_id = null;
            String product_name = null;
            String product_buy_num = null;
            JSONArray products_json = JSONArray.parseArray(sub_json.get("products").toString()); 
            if(products_json.size() > 0){
            	for(int i=0;i<products_json.size();i++){
                    JSONObject product = products_json.getJSONObject(i);  // 遍历数组，把每一个对象转成 json 对象
                    product_id = product.get("product_id").toString() ;  // 得到 每个对象中的属性值
                    product_name = product.get("product_name").toString();
                    product_buy_num = product.get("quantity").toString();
                 }
            }
            
            //大订单
            DBCollection ord_dbConnection = getReadDBCollection("cb_order");
            DBObject order = (DBObject) ord_dbConnection.findOne(new BasicDBObject("_id",orderId));
        
            System.out.println(order.toString());
            JSONObject ord_json = JSON.parseObject(order.toString()); 
            
            String totalPrice = ord_json.get("total_price").toString();
            String bankPoints = ord_json.get("bank_points").toString();
            String orderStatus = ord_json.get("order_status").toString();		
            
            //收货信息
            String recv_id=ord_json.get("recv_id").toString();
            DBCollection mof_dbConnection = getReadDBCollection("cb_merchant_outlet_favorite");
            DBObject mof = (DBObject) mof_dbConnection.findOne(new BasicDBObject("recv_info.recv_id",recv_id));
            System.out.println(mof.toString());
            JSONArray recv_info_json = JSONArray.parseArray(mof.get("recv_info").toString());  
            
            String treePathName = null;
            String consignee = null;
            String address = null;
            String phone = null;
            
            if(recv_info_json.size() > 0){
            	for(int i=0;i<recv_info_json.size();i++){
                    JSONObject recv = recv_info_json.getJSONObject(i);  // 遍历数组，把每一个对象转成 json 对象
                    String recvId = recv.get("recv_id").toString() ;
                    if(recv_id.equals(recvId)){
                    	consignee = recv.get("consignee").toString() ;
                    	address = recv.get("address").toString() ;
                    	phone = recv.get("phone").toString() ;
                    	
                    	//area_id 查城市
                    	String sql = "select id,tree_path_name from cb_area where id=" + recv.get("area_id").toString();// SQL语句
                		db1 = new DBHelper(sql);// 创建DBHelper对象
                		ret1 = db1.pst.executeQuery();// 执行语句，得到结果集
                		
                		while (ret1.next()) {
                			String temp = ret1.getString("tree_path_name");
                			treePathName=temp.toString().replace(",", "");
            			}
            			ret1.close();
            			db1.close();// 关闭连接
                    }
                 }
            }
            
            
            StringBuffer str = new StringBuffer();
            str.append(dateString+",");
            str.append(orderId+",");
            str.append(orderStatus+",");
            str.append(getMoney(Double.valueOf(bankPoints))+",");
            
            str.append(consignee+",");
            str.append(treePathName+",");
            str.append(address+",");
            str.append(phone+",");
            
            str.append(product_id+",");
            str.append(product_name+",");
            str.append(product_buy_num);
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
	
	private static int mul(double v1, int v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).intValue();
	}
}
