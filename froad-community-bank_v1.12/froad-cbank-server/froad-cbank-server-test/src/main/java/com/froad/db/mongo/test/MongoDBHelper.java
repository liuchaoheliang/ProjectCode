package com.froad.db.mongo.test;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

public class MongoDBHelper {
	
	//生产
//	private static String ip = "";
//	private static int port = 0 ;
	
	//测试
	private static String ip = "10.43.1.9";
	private static int port = 6330 ;

	/**
	 * MongoClient的实例代表数据库连接池，是线程安全的，可以被多线程共享，客户端在多线程条件下仅维持一个实例即可
	 * Mongo是非线程安全的，目前mongodb API中已经建议用MongoClient替代Mongo
	 */
	private MongoClient mongoClient = null;
	private DB mongoDB = null;

	private MongoDBHelper() {
		if (mongoClient == null) {
			MongoClientOptions.Builder build = new MongoClientOptions.Builder();
			build.connectionsPerHost(50); // 与目标数据库能够建立的最大connection数量为50
			build.autoConnectRetry(true); // 自动重连数据库启动
			build.threadsAllowedToBlockForConnectionMultiplier(50); // 如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
			/*
			 * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟
			 * 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception
			 * 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败
			 */
			build.maxWaitTime(1000 * 60 * 2);
			build.connectTimeout(1000 * 60 * 1); // 与数据库建立连接的timeout设置为1分钟

			MongoClientOptions myOptions = build.build();
			try {
				// 数据库连接实例
				mongoClient = new MongoClient(new ServerAddress(ip,
						port), myOptions);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (MongoException e) {
				e.printStackTrace();
			}

		}
	}

	// 类初始化时，自行实例化，饿汉式单例模式
	private static final MongoDBHelper mongoDBHelper = new MongoDBHelper();

	public static MongoDBHelper getMongoDBHelperInstance() {
		return mongoDBHelper;
	}

	public DB getDb(String dbName) {
		return mongoClient.getDB(dbName);
	}

	public DBCollection getCollection(String dbName, String collectionName) {
		return mongoClient.getDB(dbName).getCollection(collectionName);
	}

	/**
	 * 生产
	 */
	public DBCollection getCollection( String collectionName) {
		mongoDB = mongoClient.getDB("cbank");
		mongoDB.authenticate("cbank", "123456".toCharArray());
		return mongoDB.getCollection(collectionName);
	}

}
