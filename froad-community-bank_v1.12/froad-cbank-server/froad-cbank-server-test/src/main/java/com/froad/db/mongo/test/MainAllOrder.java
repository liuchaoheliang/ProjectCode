
package com.froad.db.mongo.test;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * @(#)MainAllOrder.java
 *
 * @author FQ
 * @version 1.0 2015年7月31日 下午1:56:49
 *
 * Copyright (C) 2015 , F-Road, Inc.
 */
public class MainAllOrder
{

    public static void main(String[] args) throws Exception
    {
        FileWriter fw = new FileWriter("C:\\Users\\FQ\\Desktop\\青峰\\所有成功订单-11.csv");
        String header = "创建时间,订单号,memberCode,memberName\r\n";
        fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
        fw.write(header);
        
        DBCollection ord_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_order");
        BasicDBObject where = new BasicDBObject();
        where.put("order_status", "6"); //订单支付完成
        
        List<DBObject> orderList = ord_dbConnection.find(where).toArray();
        System.out.println("数量："+orderList.size());
        
        int a=1;
        for(DBObject order : orderList){
            System.out.println("查询"+a);
            JSONObject ord_json = JSON.parseObject(order.toString());
            
            //订单创建时间
            String creatTime=ord_json.get("create_time").toString();
            Date date= new Date(Long.parseLong(creatTime.trim()));  
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            String dateString = formatter.format(date); 
            
            //订单号
            String orderId = ord_json.get("_id").toString();
            
            String memberCode = null;
            if(ord_json.containsKey("member_code")){
                memberCode = ord_json.get("member_code").toString();
            }
            
            String memberName = null;
            if(ord_json.containsKey("member_name")){
                memberName = ord_json.get("member_name").toString();
            }
            
            StringBuffer str = new StringBuffer();
            str.append(dateString+",");
            str.append(orderId+",");
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

}
 
