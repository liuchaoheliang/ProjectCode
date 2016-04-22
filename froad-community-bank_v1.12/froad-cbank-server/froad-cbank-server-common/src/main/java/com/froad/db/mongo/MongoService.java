package com.froad.db.mongo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.froad.Constants;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.Sort;
import com.froad.logback.LogCvt;
import com.mongodb.BasicDBList;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

/**
 * mongo 操作接口
 * @ClassName: MongoService 
 * @author FQ 
 * @date 2015年3月13日 下午1:44:25 
 *
 */
public abstract class MongoService {
	/**
	 * 写操作
	 */
	protected static MongoClient writeMongoClient = null;
	protected static DB writeMongoDB = null;
	
	/**
	 * 读操作
	 */
	protected static MongoClient readMongoClient = null;
	protected static DB readMongoDB = null;

	/**
	 * 分页临界值
	 */
	protected static Long minRedisPageCount = 0l;
	
	// 初始化Mongo连接池
	static {
		FileReader fr = null;
		try {
			Properties props = new Properties();
			fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+"mongo.properties"));
			props.load(fr);

			MongoClientOptions.Builder build = new MongoClientOptions.Builder();
			build.connectionsPerHost(Integer.valueOf(props.getProperty("mongo.pool.connectionsPerHost")));
			build.threadsAllowedToBlockForConnectionMultiplier(Integer.valueOf(props.getProperty("mongo.pool.threadsAllowedToBlockForConnectionMultiplier")));
			build.maxWaitTime(Integer.valueOf(props.getProperty("mongo.pool.maxWaitTime")));
			build.connectTimeout(Integer.valueOf(props.getProperty("mongo.pool.connectTimeout")));
			MongoClientOptions myOptions = build.build();
			
			boolean isAuth = false;
			String databaseName = props.getProperty("mongo.read.databaseName");
			// 写数据库连接实例
			String writeUserName = props.getProperty("mongo.write.userName");
			String writePwd = props.getProperty("mongo.write.password");
			MongoCredential credentialWrite = MongoCredential.createCredential(writeUserName, databaseName, writePwd.toCharArray());
			if(writeUserName == null || "".equals(writeUserName.trim()) || writePwd == null || "".equals(writePwd.trim())){
				writeMongoClient = new MongoClient(new ServerAddress(props.getProperty("mongo.write.host"), Integer.valueOf(props.getProperty("mongo.write.port"))), myOptions);
			}else{
				writeMongoClient = new MongoClient(new ServerAddress(props.getProperty("mongo.write.host"), Integer.valueOf(props.getProperty("mongo.write.port"))),Arrays.asList(credentialWrite), myOptions);
				isAuth = true;
			}
			writeMongoDB = writeMongoClient.getDB(props.getProperty("mongo.write.databaseName"));
			if(isAuth){
				writeMongoDB.authenticate(writeUserName, writePwd.toCharArray());
			}
			// writeConcern(保障write operation的可靠性)
			final String WRITE_WRITECONCERNY = "mongo.write.writeConcern";
			String writeConcern = props.getProperty(WRITE_WRITECONCERNY);
			String defWCMsgStr = " writeConcern将使用默认的:" + writeMongoDB.getWriteConcern().getWString();
			if (writeConcern != null && !(writeConcern = writeConcern.trim()).equals("")) {
				WriteConcern wc = WriteConcern.valueOf(writeConcern);
				if (null != wc) {
					writeMongoDB.setWriteConcern(wc);
				} else {
					LogCvt.warn("配置" + WRITE_WRITECONCERNY + "有误, WriteConcern类找不到" + writeConcern + "属性!" + defWCMsgStr);
				}
			} else {
				LogCvt.warn("未配置" + WRITE_WRITECONCERNY + "!" + defWCMsgStr);
			}
			
			// 读数据库连接实例
			isAuth = false;
			String readUserName = props.getProperty("mongo.read.userName");
			String readPwd = props.getProperty("mongo.read.password");
			MongoCredential credentialRead = MongoCredential.createCredential(readUserName, databaseName, readPwd.toCharArray());
			if(writeUserName == null || "".equals(writeUserName.trim()) || writePwd == null || "".equals(writePwd.trim())){
				readMongoClient = new MongoClient(new ServerAddress(props.getProperty("mongo.read.host"), Integer.valueOf(props.getProperty("mongo.read.port"))), myOptions);
			}else{
				readMongoClient = new MongoClient(new ServerAddress(props.getProperty("mongo.read.host"), Integer.valueOf(props.getProperty("mongo.read.port"))), Arrays.asList(credentialRead), myOptions);
				isAuth = true;
			}
			readMongoDB = readMongoClient.getDB(props.getProperty("mongo.read.databaseName"));
			if(isAuth){
				readMongoDB.authenticate(readUserName, readPwd.toCharArray());
			}
			readMongoDB.setReadPreference(ReadPreference.secondaryPreferred());
			
			minRedisPageCount = Long.valueOf(props.getProperty("mongo.redisPageCount.min"));
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
	 * 
	 * @Description:获取集合（表）
	 * @param connName
	 * @return DBCollection
	 * 
	 * @author: FQ
	 * @date:2015年3月12日 下午3:15:05
	 */
	public DBCollection getReadDBCollection(String connName) {
		return readMongoDB.getCollection(connName);
	}
	
	/**
	 * 
	 * @Description:获取集合（表）
	 * @param connName
	 * @return DBCollection
	 * 
	 * @author: FQ
	 * @date:2015年3月12日 下午3:15:05
	 */
	public DBCollection getWriteDBCollection(String connName) {
		return writeMongoDB.getCollection(connName);
	}
	
	/**
	 * 关闭
	 * 
	 * @author: FQ
	 * @date:2015年3月12日 下午2:45:17
	 */
	public static void closeConnection() {
//		if (mongoClient != null) {
//			mongoClient.close();
//		}
	}
	
	/**
	 * @return the writeMongoDB
	 */
	public static DB getWriteMongoDB() {
		return writeMongoDB;
	}

	/**
	 * @param writeMongoDB the writeMongoDB to set
	 */
	public static void setWriteMongoDB(DB writeMongoDB) {
		MongoService.writeMongoDB = writeMongoDB;
	}

	/**
	 * @return the readMongoDB
	 */
	public static DB getReadMongoDB() {
		return readMongoDB;
	}

	/**
	 * @param readMongoDB the readMongoDB to set
	 */
	public static void setReadMongoDB(DB readMongoDB) {
		MongoService.readMongoDB = readMongoDB;
	}

	/**
	 * 
	 * @param obj 增加的对象
	 * @param connName 表连接名称
	 * @return 影响结果
	 * int
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:39:56
	 */
	public abstract int add(Object obj,String connName);
	
	/**
	 * 
	 * @param dbObject 封装对象的数据
	 * @param connName 表连接名称
	 * @return 影响结果
	 * int
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:47:04
	 */
	public abstract int add(DBObject dbObject,String connName);
	
	/**
	 * 
	 * add:批量添加
	 *
	 * @author vania
	 * 2015年9月16日 上午10:47:11
	 * @param dbObjects
	 * @param connName
	 * @return
	 *
	 */
	public abstract int add(DBObject[] dbObjects,String connName);
	
	/**
	 * 
	 * add:批量添加
	 *
	 * @author vania
	 * 2015年9月16日 上午10:47:27
	 * @param dbObjects
	 * @param connName
	 * @return
	 *
	 */
	public abstract int add(List<DBObject> dbObjects,String connName);
	/**
	 * 
	 * @param value 修改的数据
	 * @param where 修改条件
	 * @param connName 表连接名称
	 * @param modifier $set,$rename,$inc,$push,$addToSet,$each,$pop,$pull
	 * @return 影响结果
	 * int
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:48:28
	 */
	public abstract int update(DBObject value,DBObject where,String connName,String modifier);
	
	/**
	 * 
	 * @param value
	 * @param where
	 * @param connName
	 * @return
	 * int
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:50:27
	 */
	public abstract int update(Object value,Object where,String connName,String modifier);
	
	/**
	 * 更新所有符合条件文档
	 * @Title: update 
	 * @author vania
	 * @version 1.0
	 * @param value
	 * @param where
	 * @param connName
	 * @param modifier
	 * @param upsert
	 * @param multi
	 * @return
	 * @return int    返回类型 
	 * @throws
	 */
	public abstract int update(DBObject value, DBObject where, String connName,String modifier,boolean upsert ,boolean multi ) ;
	
	/**
	 * 
	 * update: 修改方法
	 *
	 * @author vania
	 * 2015年10月27日 下午1:47:11
	 * @param q 查询条件
	 * @param o 需要修改的值
	 * @param connName 表名
	 * @param upsert 如不存在是否添加
	 * @param multi 是否修改多条
	 * @return
	 *
	 */
	public abstract int update(DBObject q, DBObject o, String connName, boolean upsert, boolean multi) ;

	/**
	 * 
	 * update:修改一个
	 *
	 * @author vania
	 * 2015年10月27日 下午1:55:38
	 * @param q 查询条件
	 * @param o 需要修改的值
	 * @param connName 表名
	 * @return
	 *
	 */
	public abstract int update(DBObject q, DBObject o, String connName) ;
	
	/**
	 *  查找并更新
	  * @Title: findAndModify
	  * @author: share 2015年3月18日
	  * @modify: share 2015年3月18日
	  * @param @param value
	  * @param @param where
	  * @param @param connName
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public abstract <T> T findAndModify(DBObject value,DBObject where,String connName,boolean upsert,Class<T> clazz);
	
	
	/**
	 * 
	 * @param obj
	 * @param connName
	 * @return
	 * int
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:44:56
	 */
	public abstract int remove(Object obj,String connName);
	
	/**
	 * 
	 * @param dbObject
	 * @param connName
	 * @return
	 * int
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:51:03
	 */
	public abstract int remove(DBObject dbObject,String connName);
	
	/**
	 *  查找并删除
	  * @Title: findAndRemove
	  * @author: share 2015年3月18日
	  * @modify: share 2015年3月18日
	  * @param @param dbObject
	  * @param @param connName
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public abstract int findAndRemove(DBObject dbObject,String connName);
	
	/**
	 * 
	 * @Description:根据条件得到数据总和
	 * @param dbObject
	 * @param connName
	 * @return
	 * int
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:51:33
	 */
	public abstract int getCount(DBObject dbObject,String connName);
	
	/**
	 * 
	 * @param obj
	 * @param connName
	 * @return
	 * int
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:52:15
	 */
	public abstract int getCount(Object obj,String connName);
	
	/**
	 * 
	 * findOneById:根据id查询
	 *
	 * @author vania
	 * 2015年8月28日 上午10:18:33
	 * @param id
	 * @param connName
	 * @return
	 *
	 */
	public abstract DBObject findOneById(Object id, String connName);
	public abstract DBObject findOneById(Object id, Collection<String> keysColl, String connName);
	public abstract DBObject findOneById(Object id, DBObject keys, String connName);
	
	/**
	 * 
	 * @param id
	 * @param connName
	 * @param className
	 * @return
	 * Object
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:53:41
	 */
	public abstract <T> T findOneById(Object id,String connName,Class<T> clazz);
	
	/**
	 * 
	 * @param object
	 * @param connName
	 * @param className
	 * @return
	 * Object
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:54:33
	 */
	public abstract <T> T findOne(Object object,String connName,Class<T> clazz);
	
	public abstract DBObject findOne(Object object, String connName) ;
	public abstract DBObject findOne(DBObject dbObject,String connName);
	/**
	 * 
	 * @param dbObject
	 * @param connName
	 * @param className
	 * @return
	 * Object
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:55:10
	 */
	public abstract <T> T findOne(DBObject dbObject,String connName,Class<T> clazz);

	public abstract <T> T findOne(DBObject dbObject, Collection<String> keysColl, String connName, Class<T> clazz);

	public abstract <T> T findOne(DBObject dbObject, DBObject keys, String connName, Class<T> clazz);
	
	public abstract DBObject findOne(DBObject dbObject, Collection<String> keysColl, String connName);
	public abstract DBObject findOne(DBObject dbObject, DBObject keys, String connName);
	
	public abstract  List<?> findAll(Object obj, Sort sort, String connName, Class<?> clazz);
	
	public abstract List<?> findAll(Object obj, DBObject sort, String connName, Class<?> clazz);
	/**
	 * 
	 * @param object
	 * @param connName
	 * @param className
	 * @return
	 * List
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:55:38
	 */
	public abstract List<?> findAll(Object obj,String connName,Class<?> clazz);
	
	/**
	 * 
	 * @param dbObject
	 * @param connName
	 * @param className
	 * @return
	 * List
	 * 
	 * @author: FQ
	 * @date:2015年3月17日 上午11:56:14
	 */
	public abstract List<?> findAll(DBObject dbObject,String connName,Class<?> clazz);
	
	/**
	 *  排序查询全部
	  * @Title: findAll
	  * @Description: TODO
	  * @author: share 2015年5月5日
	  * @modify: share 2015年5月5日
	  * @param @param dbObject
	  * @param @param orderBy
	  * @param @param connName
	  * @param @param clazz
	  * @param @return    
	  * @return List<?>    
	  * @throws
	 */
	public abstract List<?> findAll(DBObject dbObject, DBObject orderBy, String connName, Class<?> clazz);
	
	public abstract List<?> findAll(DBObject dbObject, Collection<String> keysColl, String connName, Class<?> clazz);
	
	public abstract List<?> findAll(DBObject dbObject, Collection<String> keysColl, DBObject orderBy, String connName, Class<?> clazz);

	public abstract List<?> findAll(DBObject dbObject, DBObject keys, DBObject orderBy, String connName, Class<?> clazz);

	public abstract List<DBObject> findAll(DBObject dbObject, Collection<String> keysColl, DBObject orderBy, String connName);

	public abstract List<DBObject> findAll(DBObject dbObject, Collection<String> keysColl, String connName);

	public abstract List<DBObject> findAll(DBObject dbObject, DBObject keys, String connName);

	public abstract List<DBObject> findAll(DBObject dbObject, DBObject keys, DBObject orderBy, String connName);
	
	/**
	 *  分页查询
	  * @Title: findByPage
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param page
	  * @param @param dbObject
	  * @param @param connName
	  * @param @param clazz
	  * @param @return    
	  * @return MongoPage    
	  * @throws
	 */
	public abstract MongoPage findByPage(MongoPage page,DBObject dbObject,String connName,Class<?> clazz);
	
	/**
	 * 分页查询
	 * @Title: findByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param page
	 * @param dbObject
	 * @param keysColl
	 * @param connName
	 * @param clazz
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	public abstract MongoPage findByPage(MongoPage page, DBObject dbObject,Collection<String> keysColl,String connName, Class<?> clazz) ;

	/**
	 * 分页查询
	 * @Title: findByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param page
	 * @param dbObject
	 * @param keys
	 * @param connName
	 * @param clazz
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	public abstract MongoPage findByPage(MongoPage page, DBObject dbObject,DBObject keys,String connName, Class<?> clazz) ;
	
	@Deprecated
	public abstract List<?> findAllByFieldNames(DBObject dbObject, String connName, Collection<String> fieldNames, Class<?> clazz);
	/**
	 * 执行db command
	 * @Title: command 
	 * @author vania
	 * @version 1.0
	 * @param dbObject
	 * @return
	 * @return CommandResult    返回类型 
	 * @throws
	 */
	public abstract CommandResult command(DBObject dbObject);
	
	/**
	 *  distinct 查询
	  * @Title: distinct
	  * @author: share 2015年4月9日
	  * @modify: share 2015年4月9日
	  * @param @param connName
	  * @param @param key
	  * @param @param query
	  * @param @return    
	  * @return List    
	  * @throws
	 */
	@SuppressWarnings("rawtypes")
	public abstract List distinct(String connName,String key,DBObject query);
	
	/**
	 * 分页查找文档里面数组
	 * 
	 * @param collectionName
	 * @param queryObj
	 * @param arrayName
	 * @param page
	 * @return
	 */
	public abstract MongoPage findArrayByPage(String collectionName,
			DBObject queryObj, String arrayName, MongoPage page);
	
	/**
	 * Pipeline方式查询
	 * @Title: findByPipeline 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param pipeline
	 * @param collectionName
	 * @return
	 * @return Iterator<DBObject>    返回类型 
	 * @throws
	 */
	public abstract Iterator<DBObject> findByPipeline(List<DBObject> pipeline, String collectionName) ;
	
	/**
	 * Pipeline方式查询
	 * @Title: findByPipeline 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param pipeline
	 * @param collectionName
	 * @param clazz
	 * @return
	 * @return List<T>    返回类型 
	 * @throws
	 */
	public abstract <T> List<T> findByPipeline(List<DBObject> pipeline, String collectionName, Class<T> clazz) ;
	
	/**
	 * 分页查找文档里面数组所有元素并去重
	 * 
	 * @param collectionName
	 * @param queryObj
	 * @param arrayName
	 * @param page
	 * @return
	 */
	public abstract MongoPage findDistinctArrayByPage(String collectionName,
			DBObject queryObj, String arrayName, MongoPage page);
	
	/**
	 * 分页查找文档里面unwind的数组
	 * 
	 * @param collectionName
	 * @param queryObj
	 * @param arrayName
	 * @param page
	 * @return
	 */
	public abstract MongoPage findUnwindArrayByPage(String collectionName,
			DBObject queryObj, String arrayName, MongoPage page);
	
	/**
	 * 更新所有符合条件文档
	 * 
	 * @param value
	 * @param where
	 * @param connName
	 * @param modifier
	 * @return
	 */
	public abstract int updateMulti(DBObject value, DBObject where, String connName,String modifier);
	
	/**
	 * 更新所有符合条件文档
	 * 
	 * @param value
	 * @param where
	 * @param connName
	 * @param modifier
	 * @return
	 */
	public abstract int updateMulti(Object value, Object where, String connName,String modifier);
	
	
	/**
	 * 分页查询
	 * @Title: findByPageWithRedis 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param page
	 * @param dbObject
	 * @param keysColl
	 * @param connName
	 * @param clazz
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	public abstract MongoPage findByPageWithRedis(MongoPage page, DBObject dbObject,Collection<String> keysColl, String connName,Class<?> clazz) ;
	
	/**
	 * 分页查询
	 * @Title: findByPageWithRedis 
	 * @author 
	 * @version 1.0
	 * @see: TODO
	 * @param page
	 * @param dbObject
	 * @param connName
	 * @param clazz
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	public abstract MongoPage findByPageWithRedis(MongoPage page, DBObject dbObject, String connName, Class<?> clazz) ;
	/**
	 * 分页查询
	 * @Title: findByPageWithRedis 
	 * @author 
	 * @version 1.0
	 * @see: TODO
	 * @param page
	 * @param dbObject
	 * @param keys
	 * @param connName
	 * @param clazz
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	public abstract MongoPage findByPageWithRedis(MongoPage page, DBObject dbObject,DBObject keys, String connName,Class<?> clazz) ;
	
	/**
	 * 分页查询
	* <p>Function: findByPageWithSortObject</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-7-13 下午11:45:13
	* @version 1.0
	* @param page
	* @param dbObject
	* @param keys
	* @param connName
	* @param clazz
	* @return
	 */
	public abstract MongoPage findByPageWithSortObject(MongoPage page, DBObject dbObject,DBObject keys, String connName, Class<?> clazz) ;
	
	/**
	 *  管道分页方式查询
	  * @Title: findByPageAggregate
	  * @Description: TODO
	  * @author: share 2015年6月11日
	  * @modify: share 2015年6月11日
	  * @param @param page
	  * @param @param aggregateDbObject
	  * @param @param countQueryDbObj
	  * @param @param connName
	  * @param @param clazz
	  * @param @return    
	  * @return MongoPage    
	  * @throws
	 */
	public abstract MongoPage findByPageAggregate(MongoPage page,List<DBObject> pipeline,DBObject countQueryDbObj,String connName,Class<?> clazz);
	
	/**
	 * 
	 * findOneOfListResult:
	 *
	 * @author vania
	 * 2015年8月28日 上午10:26:52
	 * @param querys
	 * @param connName
	 * @return
	 *
	 */
	public abstract List<DBObject> findOneOfListResult(List<DBObject> querys,String connName);
	public abstract List<DBObject> findOneOfListResult(List<DBObject> querys,Collection<String> keysColl,String connName);
	public abstract List<DBObject> findOneOfListResult(List<DBObject> querys,DBObject keys,String connName);
	
	/**
	 * 
	* <p>Function: findOneOfListResult</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-7-8 下午1:44:30
	* @version 1.0
	* @param querys
	* @param connName
	* @param clazz
	* @return
	 */
	public abstract <T> List<T> findOneOfListResult(List<DBObject> querys,String connName,Class<T> clazz);
	
	/**
	 * findByPageForDBCursor:分页查询
	 * 不能跨页
	 *
	 * @author vania
	 * 2015年9月17日 上午11:27:03
	 * @param page
	 * @param dbObject
	 * @param keys
	 * @param connName
	 * @return
	 *
	 */
	public abstract DBCursor findByPageForDBCursor(MongoPage page, DBObject dbObject, DBObject keys, String connName) ;
	
	/**
	 * 
	 * findByPageForIterator:分页查询
	 * 不能跨页
	 * @author vania
	 * 2015年9月17日 上午11:27:12
	 * @param page
	 * @param dbObject
	 * @param keys
	 * @param connName
	 * @return
	 *
	 */
	public abstract Iterator<DBObject> findByPageForIterator(MongoPage page, DBObject dbObject, DBObject keys, String connName);
	/**
	 * 报表导出专用分页
	 * findByPageForExport:(这里用一句话描述这个方法的作用).
	 *
	 * @author zhouzhiwei
	 * 2015年9月11日 下午2:46:33
	 * @return
	 *
	 */
	public abstract MongoPage findByPageForExport(MongoPage page, DBObject dbObject, DBObject keys, String connName, Class<?> clazz) ;
	
	/**
	 * 做group统计的操作
	 * Function : group<br/>
	 * 2015年12月29日 下午3:17:48 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param connName
	 * @param where
	 * @param dimMap 分组字段,示例：Map<String,String> dimMap = new HashMap<String,String>();dimMap.put("A","A");dimMap.put("B","B");
	 * @param forIdxMap 统计的结果字段，示例：Map<String,String> forIdxMap = new HashMap<String,String>();forIdxMap.put("X1","sum(C1)");dimMap.put("X2","sum(C2)/count(C2)");
	 * @param indexMap	统计公式中用到的字段:Map<String,String> indexMap = new HashMap<String,String>();indexMap.put("C1","C1");indexMap.put("C2","C2");
	 * @return
	 */
	public abstract BasicDBList group(String connName,DBObject where,Map<String,String> dimMap,Map<String,String> forIdxMap,Map<String,String> indexMap);
}
