package com.froad.db.mongo.test;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class Main {

	public static void main(String[] args) throws Exception{
		
		DBCollection mof_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_merchant_outlet_favorite");
        DBObject mof = (DBObject) mof_dbConnection.findOne(new BasicDBObject("recv_info.recv_id","073C2DE20000"));
        
		System.out.println(mof);
	}
	

}
