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

public class MainOrder {

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
		String header = "创建时间,订单号,状态,memberCode,memberName,总价,姓名,手机号,商品信息\r\n";
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
        	
        	String totalPrice = ord_json.get("total_price").toString();
        	
        	//个人信息
        	String consignee = null;
            String phone = null;
            
            //
            String recvOrDel = null;
            //收货
            String recv_id = null;
            //提货
            String del_id = null;
            
            //商户 商品
            String mer_pro_str = null;
            
            if("1".equals(ord_json.get("is_qrcode").toString())){
            	
            }
            else{
            	//收货
                if(ord_json.containsKey("recv_id")){
                	
                	recvOrDel = "收";
                	recv_id=ord_json.get("recv_id").toString();
                	
                	if(recv_id !=null && !"".equals(recv_id)){
                    	DBCollection mof_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_merchant_outlet_favorite");
                        DBObject mof = (DBObject) mof_dbConnection.findOne(new BasicDBObject("recv_info.recv_id",recv_id));
                        
                        if(mof != null){
                        	JSONArray recv_info_json = JSONArray.parseArray(mof.get("recv_info").toString());  
                            System.out.println("recv== "+mof.toString());
                            
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
                    }
                }
                else if(ord_json.containsKey("deliver_id")){//提货
                	
                	recvOrDel = "提";
                	del_id=ord_json.get("deliver_id").toString();
                	
                	if(del_id !=null && !"".equals(del_id)){
                    	DBCollection mof_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_merchant_outlet_favorite");
                        DBObject mof = (DBObject) mof_dbConnection.findOne(new BasicDBObject("recv_info.recv_id",del_id));
                        
                        if(mof != null){
                        	JSONArray del_info_json = JSONArray.parseArray(mof.get("recv_info").toString());  
                            System.out.println("del== "+mof.toString());
                            
                            if(del_info_json.size() > 0){
                            	for(int i=0;i<del_info_json.size();i++){
                                    JSONObject del = del_info_json.getJSONObject(i);  // 遍历数组，把每一个对象转成 json 对象
                                    String delId = del.get("recv_id").toString() ;
                                    if(del_id.equals(delId)){
                                    	consignee = del.get("consignee").toString() ;
                                    	phone = del.get("phone").toString() ;
                                    }
                                 }
                            }
                        }
                    }
                }
                else{
                	System.out.println("=====异常=====");
                }
               
                
                
              //查询子订单
            	DBCollection sub_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_suborder_product");
        		BasicDBObject sub_where = new BasicDBObject();
        		sub_where.put("order_id",orderId);
        		List<DBObject> subOrderlist = sub_dbConnection.find(sub_where).toArray();
        		
        		StringBuffer str1 = new StringBuffer();
        		//存在子订单
        		if(subOrderlist != null && subOrderlist.size() > 0){
        			
        			for(DBObject subOrder : subOrderlist){
        				System.out.println("subOrder== "+subOrder.toString());
        				JSONObject sub_json = JSON.parseObject(subOrder.toString());
        				
        				//商户
        				String merchant_id = sub_json.get("merchant_id").toString();
        				String merchant_name = sub_json.get("merchant_name").toString() ;
        				
        				str1.append(merchant_id+"|"+merchant_name+"  ");
        				
        				//商品
        				StringBuffer proStr = new StringBuffer();
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
        	                    
        	                    proStr.append(product_id+"|"+product_name+"|"+product_buy_num);
        	                    proStr.append("◇");
        	                 }
        	            }
        	            str1.append(proStr);
        	            str1.append("◆");
        			}
        		}
        		else{
        			System.out.println("orderId："+orderId+" 没有子订单");
        		}
        		mer_pro_str=str1.toString().replace(",", "");
        		mer_pro_str=mer_pro_str.substring(0, mer_pro_str.length()-2);
            }
    		
    		StringBuffer str = new StringBuffer();
            str.append(createTime+",");
            str.append(orderId+",");
            str.append(orderStatus+",");
            str.append(memberCode+",");
            str.append(memberName+",");
            str.append(getMoney(Double.valueOf(totalPrice))+",");
            str.append(consignee+",");
            str.append(phone+",");
            str.append(mer_pro_str);
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
