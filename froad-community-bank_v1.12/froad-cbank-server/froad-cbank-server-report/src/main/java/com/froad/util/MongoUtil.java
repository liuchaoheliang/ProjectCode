package com.froad.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.froad.Constants;
import com.froad.logback.LogCvt;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

@SuppressWarnings("deprecation")
public class MongoUtil {

	protected static MongoClient mongoClient = null;
	protected static DB mongoDB = null;
	
	// 初始化Mongo连接池
	static {
		FileReader fr = null;
		try {
			Properties props = new Properties();
			fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+"rp_mongo.properties"));
			props.load(fr);

			MongoClientOptions.Builder build = new MongoClientOptions.Builder();
			build.connectionsPerHost(Integer.valueOf(props.getProperty("mongo.pool.connectionsPerHost")));
			build.threadsAllowedToBlockForConnectionMultiplier(Integer.valueOf(props.getProperty("mongo.pool.threadsAllowedToBlockForConnectionMultiplier")));
			build.maxWaitTime(Integer.valueOf(props.getProperty("mongo.pool.maxWaitTime")));
			build.connectTimeout(Integer.valueOf(props.getProperty("mongo.pool.connectTimeout")));
			MongoClientOptions myOptions = build.build();
			
			boolean isAuth = false;
			String databaseName = props.getProperty("mongo.databaseName");
			// 写数据库连接实例
			String writeUserName = props.getProperty("mongo.userName");
			String writePwd = props.getProperty("mongo.password");
			MongoCredential credentialWrite = MongoCredential.createCredential(writeUserName, databaseName, writePwd.toCharArray());
			if(writeUserName == null || "".equals(writeUserName.trim()) || writePwd == null || "".equals(writePwd.trim())){
				mongoClient = new MongoClient(new ServerAddress(props.getProperty("mongo.host"), Integer.valueOf(props.getProperty("mongo.port"))), myOptions);
			}else{
				mongoClient = new MongoClient(new ServerAddress(props.getProperty("mongo.host"), Integer.valueOf(props.getProperty("mongo.port"))),Arrays.asList(credentialWrite), myOptions);
				isAuth = true;
			}
			mongoDB = mongoClient.getDB(databaseName);
			if(isAuth){
				mongoDB.authenticate(writeUserName, writePwd.toCharArray());
			}
			
		} catch (IOException e) {
			LogCvt.error(e.getMessage(), e);
		}finally{
			try {
				if(fr != null){
					fr.close();
				}
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * 添加mongo文档
	 * 
	 * @param dbObject
	 * @param collection
	 */
	public void insert(List<DBObject> list, String collection) {
		DBCollection dbCollection = null;
		
		try {
			dbCollection = mongoDB.getCollection(collection);
			dbCollection.insert(list);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 分页查找
	 * @param <T>
	 * @param dbObject
	 * @param collection
	 * @param limit
	 * @param clazz
	 * @return
	 */
	public <T> List<T> findByLimit(DBObject dbObject, String collection, int limit, Class<T> clazz) {
		 List<T> resultList=new ArrayList<T>();
		 DBCollection dbCollection = null;
		 long beginTime = System.currentTimeMillis();
	       try {
	    	   dbCollection = mongoDB.getCollection(collection);
	    	   List<DBObject> list = dbCollection.find(dbObject).limit(limit).toArray();
	           for(DBObject result : list){
	        	   String jsonStr = JSON.serialize(result);
	 			   T entity = JSonUtil.toObject(jsonStr, clazz);
	               resultList.add(entity);
	           }
	       }catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       }finally{
			LogCvt.debug(new StringBuffer("mongo findByLimit: collection=")
					.append(collection).append(",SQL=[").append(dbObject)
					.append("]").append(",Records=").append(resultList.size())
					.append(",Time=")
					.append(System.currentTimeMillis() - beginTime)
					.append("ms").toString());
	       }
	       return resultList;
	}
	
	/**
	 * 删除集合
	 * 
	 * @param collection
	 */
	public void dropCollection(String collection){
		 DBCollection dbCollection = null;
	       try {
	    	   dbCollection = mongoDB.getCollection(collection);
	    	   dbCollection.drop();
	       }catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       }finally{
			LogCvt.debug(new StringBuffer("collection=").append(collection).append(" is dropped").toString());
	       }
	}
}
