package com.froad.db.mongo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.froad.db.mongo.GroupUtil;
import com.froad.db.mongo.MongoService;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.redis.RedisService;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.FieldMapping;
import com.froad.logback.LogCvt;
import com.froad.util.JSonUtil;
import com.froad.util.MD5Util;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;

/**
 * 
 * @ClassName: MongoManager
 * @author FQ
 * @date 2015年3月12日 下午2:03:00
 *
 */
public class MongoManager extends MongoService {
	/**
	 * 
	 * buildFieldDBObject:构建需要查询的字段DBObject.
	 *
	 * @author vania
	 * 2015年8月28日 上午10:07:31
	 * @param keysColl 需要查询的字段集合
	 * @return DBObject 需要查询的字段DBObject
	 *
	 */
	public static DBObject buildFieldDBObject(Collection<String> keysColl) {
		BasicDBObject keys = new BasicDBObject();
		if (keysColl != null && keysColl.size() > 0) {
			for (String fieldName : keysColl) {
				keys.put(fieldName, 1);
			}
		}
		return keys;
	}
	
	/**
	 * 
	 * reverseSort:反转排序条件 （原来是升序的字段反转为降序，反之亦然）.
	 *
	 * @author vania
	 * 2015年9月16日 上午10:34:21
	 * @param srot
	 *
	 */
	public static void reverseSort(DBObject srot) {
		Map<String, Integer> map = (Map<String, Integer>) srot.toMap();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			map.put(key, 0 - map.get(key));
		}
		srot.putAll(map);
		// return srot;
	}
	
	@Override
	public int add(Object obj, String connName) {
		return this.add((BasicDBObject) JSON.parse(JSonUtil.toJSonString(obj)), connName);
	}

	@Override
	public int add(DBObject dbObject, String connName) {
		int result = -1;
		DBCollection dbCollection = null;
		try {
			dbCollection = getWriteDBCollection(connName);
			result = dbCollection.insert(dbObject).getN();
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer("mongo add: collection=").append(connName).append(",SQL=[").append(JSonUtil.toJSonString(dbObject)).append("]").append(",Updates=").append(result).toString());
			closeConnection();
		}
		return result;
	}
	
	@Override
	public int add(DBObject[] dbObjects, String connName) {
		return this.add(Arrays.asList(dbObjects), connName);
	}
	
	@Override
	public int add(List<DBObject> dbObjects, String connName) {
		int result = -1;
		DBCollection dbCollection = null;
		try {
			dbCollection = getWriteDBCollection(connName);
			result = dbCollection.insert(dbObjects).getN();
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer("mongo add: collection=").append(connName).append(",SQL=[").append(JSonUtil.toJSonString(dbObjects)).append("]").append(",Updates=").append(result).toString());
			closeConnection();
		}
		return result;
	}

	/**
	 * 更新所有符合条件第一个文档
	 * 
	 * @param value
	 * @param where
	 * @param connName
	 * @param modifier
	 * @return
	 */
	public int update(DBObject value, DBObject where, String connName,String modifier) {
		int result=-1;
		DBCollection dbCollection = null;
	       try {
	    	   dbCollection = getWriteDBCollection(connName);
	    	   if (null == modifier){
	    		   result=dbCollection.update(where,value).getN();
	    	   } else {
	    		   result=dbCollection.update(where,new BasicDBObject(modifier, value)).getN();
	    	   }
	       }catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       }finally{
//	    	   LogCvt.debug(":where="+where.toString()+";value="+value.toString());
	    	   LogCvt.debug(new StringBuffer("mongo update: collection=").append(connName).append(",SQL=[where=").append(where).append("modifier=").append(modifier).append(",value=").append(value).append("]").append(",Updates=").append(result).toString());
	    	   closeConnection();
	       }
	       return result;
	}

	/**
	 * 更新所有符合条件第一个文档
	 * 
	 * @param value
	 * @param where
	 * @param connName
	 * @param modifier
	 * @return
	 */
	public int update(Object value, Object where, String connName,String modifier) {
		  int result=-1;
		  DBCollection dbCollection = null;
		  DBObject whereObj = null;
		  DBObject setValueObj = null;
		 
	       try {
	    	   dbCollection = getWriteDBCollection(connName);
	    	   whereObj = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(where));
	    	   if (null == modifier){
	    		   setValueObj = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(value));
	    	   } else {
	    		   setValueObj = new BasicDBObject(modifier, (BasicDBObject) JSON.parse(JSonUtil.toJSonString(value)));
	    	   }
	           result=dbCollection.update(whereObj,setValueObj).getN();
	       }catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       }finally{
	    	   LogCvt.debug(new StringBuffer("mongo update: collection=").append(connName).append(",SQL=[where=").append(JSonUtil.toJSonString(where)).append("modifier=").append(modifier).append(",value=").append(JSonUtil.toJSonString(value)).append("]").append(",Updates=").append(result).toString());
	    	   closeConnection();
	       }
	       return result;
	}
	
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
	public int update(DBObject value, DBObject where, String connName,String modifier,boolean upsert ,boolean multi ) {
		int result=-1;
		DBCollection dbCollection = null;
	       try {
	    	   dbCollection = getWriteDBCollection(connName);
	    	   if (null == modifier){
	    		   result=dbCollection.update(where,value,upsert,multi).getN();
	    	   } else {
	    		   result=dbCollection.update(where,new BasicDBObject(modifier, value),upsert,multi).getN();
	    	   }
	       }catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       }finally{
	    	   LogCvt.debug(new StringBuffer("mongo update: collection=").append(connName).append(",SQL=[where=").append(where).append("modifier=").append(modifier).append(",value=").append(value).append("]").append(",Updates=").append(result).toString());
	    	   closeConnection();
	       }
	       return result;
	}
	
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
	public int update(DBObject q, DBObject o, String connName, boolean upsert, boolean multi) {
		int result = -1;
		DBCollection dbCollection = null;
		try {
			dbCollection = getWriteDBCollection(connName);
			result = dbCollection.update(q, o, upsert, multi).getN();
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer("mongo update: collection=").append(connName).append(",SQL=[where=").append(q).append(",value=").append(o).append(",upsert=").append(upsert).append(",multi=").append(multi).append("]").append(",Updates=").append(result).toString());
			closeConnection();
		}
		return result;
	}

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
	public int update(DBObject q, DBObject o, String connName) {
		return this.update(q, o, connName, false, false);
	}
	
	
	/**
	 * 更新所有符合条件文档
	 * 
	 * @param value
	 * @param where
	 * @param connName
	 * @param modifier
	 * @return
	 */
	public int updateMulti(DBObject value, DBObject where, String connName,String modifier) {
		int result=-1;
		DBCollection dbCollection = null;
	       try {
	    	   dbCollection = getWriteDBCollection(connName);
	    	   if (null == modifier){
	    		   result=dbCollection.update(where,value,false,true).getN();
	    	   } else {
	    		   result=dbCollection.update(where,new BasicDBObject(modifier, value),false,true).getN();
	    	   }
	       }catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       }finally{
	    	   LogCvt.debug(new StringBuffer("mongo updateMulti: collection=").append(connName).append(",SQL=[where=").append(where).append("modifier=").append(modifier).append(",value=").append(value).append("]").append(",Updates=").append(result).toString());
	    	   closeConnection();
	       }
	       return result;
	}

	/**
	 * 更新所有符合条件文档
	 * 
	 * @param value
	 * @param where
	 * @param connName
	 * @param modifier
	 * @return
	 */
	public int updateMulti(Object value, Object where, String connName,String modifier) {
		 int result=-1;
		 DBCollection dbCollection = null;
		 DBObject whereObj = null;
		 DBObject setValueObj = null;
		 
	       try {
	    	   dbCollection = getWriteDBCollection(connName);
	    	   whereObj = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(where));
	    	   if (null == modifier){
	    		   setValueObj = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(value));
	    	   } else {
	    		   setValueObj = new BasicDBObject(modifier, (BasicDBObject) JSON.parse(JSonUtil.toJSonString(value)));
	    	   }
	           result=dbCollection.update(whereObj,setValueObj,false,true).getN();
	       }catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       }finally{
	    	   LogCvt.debug(new StringBuffer("mongo update: collection=").append(connName).append(",SQL=[where=").append(JSonUtil.toJSonString(where)).append("modifier=").append(modifier).append(",value=").append(JSonUtil.toJSonString(value)).append("]").append(",Updates=").append(result).toString());
	    	   closeConnection();
	       }
	       return result;
	}

	public int remove(Object obj, String connName) {
		int result=-1;
		DBCollection dbCollection = null;
	       try {
	    	   dbCollection = getWriteDBCollection(connName);
	           result=dbCollection.remove((BasicDBObject)JSON.parse(JSonUtil.toJSonString(obj))).getN();
	       }catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       }finally{
	    	   LogCvt.debug(new StringBuffer("mongo remove: collection=").append(connName).append(",SQL=[").append(JSonUtil.toJSonString(obj)).append("]").append(",Updates=").append(result).toString());
	    	   closeConnection();
	       }
	       return result;
	}

	public int remove(DBObject dbObject, String connName) {
	   int result=-1;
	   DBCollection dbCollection = null;
       try {
    	   dbCollection = getWriteDBCollection(connName);
           result=dbCollection.remove(dbObject).getN();
       }catch(Exception e) {
           LogCvt.error(e.getMessage(), e);
       }finally{
    	   LogCvt.debug(new StringBuffer("mongo remove: collection=").append(connName).append(",SQL=[").append(dbObject).append("]").append(",Updates=").append(result).toString());
    	   closeConnection();
       }
       return result;
	}

	public int getCount(DBObject dbObject, String connName) {
		 int result=0;
		 DBCollection dbCollection = null;
		 long beginTime = System.currentTimeMillis();
	       try {
	    	   dbCollection = getReadDBCollection(connName);
	    	   dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);//读写分离，读从服务器，写主服务器
	           result=dbCollection.find(dbObject).count();
	       }catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       }finally{
	    	   LogCvt.debug(new StringBuffer("mongo getCount: collection=").append(connName).append(",SQL=[").append(dbObject).append("]").append(",Count=").append(result).append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
	    	   closeConnection();
	       }
	       return result;
	}

	public int getCount(Object obj, String connName) {
		 int result=0;
		 DBCollection dbCollection = null;
		 long beginTime = System.currentTimeMillis();
	       try {
	    	   dbCollection = getReadDBCollection(connName);
	    	   dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);//读写分离，读从服务器，写主服务器
	           result=dbCollection.find((BasicDBObject)JSON.parse(JSonUtil.toJSonString(obj))).count();
	       }catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       }finally{
	    	   LogCvt.debug(new StringBuffer("mongo getCount: collection=").append(connName).append(",SQL=[").append(JSonUtil.toJSonString(obj)).append("]").append(",Count=").append(result).append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
	    	   closeConnection();
	       }
	       return result;
	}
	
	@Override
	public DBObject findOneById(Object id, Collection<String> keysColl, String connName) {
		return this.findOneById(id, this.buildFieldDBObject(keysColl), connName);
	}
	
	@Override
	public DBObject findOneById(Object id, DBObject keys, String connName) {
		DBObject queryResult = null;
		DBCollection dbCollection = null;
		long beginTime = System.currentTimeMillis();
		try {
			dbCollection = getReadDBCollection(connName);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);//读写分离，读从服务器，写主服务器
			queryResult = dbCollection.findOne(new BasicDBObject("_id",id), keys);
			
		}catch(Exception e) {
			LogCvt.error(e.getMessage(), e);
		}finally{
			LogCvt.debug(new StringBuffer("mongo findOneById: collection=").append(connName).append(",SQL=[").append(id).append("]").append(",keys=[").append(keys).append("]").append(",Records=").append(queryResult==null?0:1).append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
			closeConnection();
		}
		return queryResult;
	}
	
	@Override
	public DBObject findOneById(Object id, String connName) {
		return findOneById(id, new BasicDBObject(), connName);
	}
	
	@Override
	public <T> T findOneById(Object id, String connName,Class<T> clazz) {
		T entity = null;
		DBObject queryResult = this.findOneById(id, new BasicDBObject(), connName);
		if (null != queryResult) {
			// Convert DBObject to JSON string
			String jsonStr = JSON.serialize(queryResult);

			// Convert JSON string to entity
			entity = JSonUtil.toObject(jsonStr, clazz);
		}
		return entity;
	}
	
	@Override
	public <T> List<T> findOneOfListResult(List<DBObject> querys,String connName,Class<T> clazz){
		List<T> dataResult = new ArrayList<T>();
		List<DBObject> dbbjectResults = this.findOneOfListResult(querys, new BasicDBObject(), connName);
		if (null != dbbjectResults && dbbjectResults.size() > 0) {
			for (DBObject queryResult : dbbjectResults) {
				dataResult.add(JSonUtil.toObject(JSON.serialize(queryResult), clazz));
			}
		}		
		return dataResult;
	}
	
	@Override
	public List<DBObject> findOneOfListResult(List<DBObject> querys,String connName){
		return this.findOneOfListResult(querys, new BasicDBObject(), connName);
	}
	
	@Override
	public List<DBObject> findOneOfListResult(List<DBObject> querys, Collection<String> keysColl, String connName) {
		return findOneOfListResult(querys, this.buildFieldDBObject(keysColl), connName);
	}
	
	@Override
	public List<DBObject> findOneOfListResult(List<DBObject> querys, DBObject keys, String connName) {
		List<DBObject> dataResult = new ArrayList<DBObject>();
		DBCollection dbCollection = null;
		long beginTime = System.currentTimeMillis();
		try {
    	   dbCollection = getReadDBCollection(connName);
    	   dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);//读写分离，读从服务器，写主服务器
    	  
    	   for (DBObject dbObject : querys) {
        	   DBObject queryResult = dbCollection.findOne(dbObject, keys);
        	   dataResult.add(queryResult);
    	   }
		}catch(Exception e) {
           LogCvt.error(e.getMessage(), e);
		}finally{
			LogCvt.debug(new StringBuffer("mongo findOneOfListResult: collection=").append(connName).append(",SQL=[").append(querys).append("]").append(",keys=[").append(keys).append("]").append(",Records=").append(dataResult.size()).append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
    	    closeConnection();
		}
		return dataResult;
	}
	
	@Override
	public <T> T findOne(Object object, String connName, Class<T> clazz) {
		return this.findOne((BasicDBObject)JSON.parse(JSonUtil.toJSonString(object)), connName, clazz);
	}
	
	@Override
	public <T> T findOne(DBObject dbObject, String connName, Class<T> clazz) {
		return this.findOne(dbObject, new BasicDBObject(), connName, clazz);
	}
	
	@Override
	public <T> T findOne(DBObject dbObject, Collection<String> keysColl, String connName, Class<T> clazz) {
		return this.findOne(dbObject, this.buildFieldDBObject(keysColl), connName, clazz);
	}
	
	@Override
	public <T> T findOne(DBObject dbObject,DBObject keys, String connName, Class<T> clazz) {
		T entity = null;
		DBObject queryResult = this.findOne(dbObject, keys, connName);
		if (null != queryResult) {
			// Convert DBObject to JSON string
			String jsonStr = JSON.serialize(queryResult);
			
			// Convert JSON string to entity
			entity = JSonUtil.toObject(jsonStr, clazz);
		}
		
		return entity;
	}

	@Override
	public DBObject findOne(Object object, String connName) {
		return this.findOne((BasicDBObject)JSON.parse(JSonUtil.toJSonString(object)), connName);
	}

	public DBObject findOne(DBObject dbObject, String connName) {
		return this.findOne(dbObject, new BasicDBObject(), connName);
	}
	
	public DBObject findOne(DBObject dbObject, Collection<String> keysColl, String connName) {
		return this.findOne(dbObject, this.buildFieldDBObject(keysColl), connName);
	}
	
	public DBObject findOne(DBObject dbObject, DBObject keys, String connName) {
		DBCollection dbCollection = null;
		DBObject queryResult = null;
		long beginTime = System.currentTimeMillis();
		try {
			dbCollection = getReadDBCollection(connName);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);// 读写分离，读从服务器，写主服务器
			queryResult = dbCollection.findOne(dbObject, keys);
			
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer("mongo findOne: collection=").append(connName).append(",SQL=[").append(dbObject).append("]").append(",keys=[").append(keys).append("]").append(",Records=").append(queryResult == null ? 0 : 1).append(",Time=").append(System.currentTimeMillis() - beginTime).append("ms").toString());
			closeConnection();
		}
		return queryResult;
	}

	@Override
	public List<?> findAll(Object obj, Sort sort, String connName, Class<?> clazz) {
		return findAll(obj, sort.getSortObject(), connName, clazz);
	}

	@Override
	public List<?> findAll(Object obj, DBObject sort, String connName, Class<?> clazz) {
		return this.findAll((BasicDBObject) JSON.parse(JSonUtil.toJSonString(obj)), sort, connName, clazz);
	}

	@Override
	public List<?> findAll(Object obj, String connName, Class<?> clazz) {
		return findAll((BasicDBObject) JSON.parse(JSonUtil.toJSonString(obj)), connName, clazz);
	}

	@Override
	public List<?> findAll(DBObject dbObject, String connName, Class<?> clazz) {
		return findAll(dbObject, (DBObject) null, connName, clazz);
	}

	@Override
	public List<?> findAll(DBObject dbObject, DBObject orderBy, String connName, Class<?> clazz) {
		return this.findAll(dbObject, new BasicDBObject(), orderBy, connName, clazz);
	}
	
	@Override
	public List<?> findAll(DBObject dbObject, Collection<String> keysColl, String connName, Class<?> clazz) {
		return this.findAll(dbObject, this.buildFieldDBObject(keysColl), null, connName,clazz);
	}
	
	@Override
	public List<?> findAll(DBObject dbObject, Collection<String> keysColl, DBObject orderBy, String connName, Class<?> clazz) {
		return this.findAll(dbObject, this.buildFieldDBObject(keysColl), orderBy, connName,clazz);
	}
	
	@Override
	public List<?> findAll(DBObject dbObject, DBObject keys, DBObject orderBy, String connName, Class<?> clazz) {
		List<Object> resultList = new ArrayList<Object>();
		List<DBObject> list = this.findAll(dbObject, keys, orderBy, connName);
		if (list != null && list.size() > 0) {
			for (DBObject result : list) {
				String jsonStr = JSON.serialize(result);
				Object entity = JSonUtil.toObject(jsonStr, clazz);
				resultList.add(entity);
			}
		}
		return resultList;
	}
	
	@Override
	public List<DBObject> findAll(DBObject dbObject, Collection<String> keysColl, DBObject orderBy, String connName) {
		return findAll(dbObject, this.buildFieldDBObject(keysColl), orderBy, connName);
	}
	
	@Override
	public List<DBObject> findAll(DBObject dbObject, DBObject keys, String connName) {
		return findAll(dbObject, keys, null, connName);
	}
	
	@Override
	public List<DBObject> findAll(DBObject dbObject, Collection<String> keysColl, String connName) {
		return findAll(dbObject, this.buildFieldDBObject(keysColl), null, connName);
	}
	
	@Override
	public List<DBObject> findAll(DBObject dbObject,DBObject keys,DBObject orderBy, String connName) {
		List<DBObject> resultList=new ArrayList<DBObject>();
		DBCollection dbCollection = null;
		long beginTime = System.currentTimeMillis();
		try {
			dbCollection = getReadDBCollection(connName);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);//读写分离，读从服务器，写主服务器
			DBCursor cursor = dbCollection.find(dbObject, keys);
			if (null != orderBy) {
				cursor.sort(orderBy);
			}
			resultList = cursor.toArray();
			
		}catch(Exception e) {
			LogCvt.error(e.getMessage(), e);
		}finally{
			LogCvt.debug(new StringBuffer("mongo findAll: collection=").append(connName).append(",SQL=[").append(dbObject).append("]").append(",keys=[").append(keys).append("]").append(",sort=[").append(orderBy).append("]").append(",Records=").append(resultList==null?0:resultList.size()).append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
			closeConnection();
		}
		return resultList;
	}
	
	/**
	 * 查询结果不做处理，返回游标
	 * @param dbObject
	 * @param keys
	 * @param orderBy
	 * @param connName
	 * @return
	 */
	public DBCursor findAllToCursor(DBObject dbObject,DBObject keys,DBObject orderBy, String connName) {
		DBCursor cursor = null;
		DBCollection dbCollection = null;
		long beginTime = System.currentTimeMillis();
		try {
			dbCollection = getReadDBCollection(connName);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);//读写分离，读从服务器，写主服务器
			cursor = dbCollection.find(dbObject, keys);
			if (null != orderBy) {
				cursor.sort(orderBy);
			}
			
		}catch(Exception e) {
			LogCvt.error(e.getMessage(), e);
		}finally{
			LogCvt.debug(new StringBuffer("mongo findAll: collection=").append(connName).append(",SQL=[").append(dbObject).append("]").append(",keys=[").append(keys).append("]").append(",sort=[").append(orderBy).append("]").append(",Records=").append(cursor==null?0:cursor.count()).append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
			closeConnection();
		}
		return cursor;
	}
	
	public <T> T findAndModify(DBObject value,DBObject where,String connName,boolean upsert,Class<T> clazz) {
	   DBCollection dbCollection = null;
	   T entity = null;
	   long beginTime = System.currentTimeMillis();
       try {
    	   dbCollection = getWriteDBCollection(connName);
    	   DBObject queryResult = dbCollection.findAndModify(where, null, null, false, value, true, upsert);
           if (null != queryResult){
 				//Convert DBObject to JSON string
 				String jsonStr = JSON.serialize(queryResult);
 			
 				//Convert JSON string to entity
 				entity = JSonUtil.toObject(jsonStr, clazz);
 			}
       }catch(Exception e) {
           LogCvt.error(e.getMessage(), e);
       }finally{
    	   LogCvt.debug(new StringBuffer("mongo findAndModify: collection=").append(connName).append(",SQL=[where=").append(where).append(",value=").append(value).append("]").append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
    	   closeConnection();
       }
       return entity;
	}
	

	@Override
	public int findAndRemove(DBObject dbObject, String connName) {
	   int result=-1;
	   DBCollection dbCollection = null;
	   long beginTime = System.currentTimeMillis();
       try {
    	   dbCollection = getWriteDBCollection(connName);
           dbCollection.findAndRemove(dbObject);
           result = 0;
       }catch(Exception e) {
           LogCvt.error(e.getMessage(), e);
       }finally{
    	   LogCvt.debug(new StringBuffer("mongo findAndRemove: collection=").append(connName).append(",SQL=[").append(dbObject).append("]").append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
    	   closeConnection();
       }
       return result;
	}
	
	/***
	 *  分页查询
	  * @Title: findByPage
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param page
	  * @param @param dbObject
	  * @param @param connName
	  * @param @param clazz
	  * @param @return
	  * @throws
	  * @see com.froad.db.mongo.MongoService#findByPage(com.froad.db.mongo.page.MongoPage, com.mongodb.DBObject, java.lang.String, java.lang.Class)
	 */
	@Override
	public MongoPage findByPage(MongoPage page, DBObject dbObject,String connName, Class<?> clazz) {
	   return this.findByPage(page, dbObject, new BasicDBObject(), connName, clazz);
	}
	
	/**
	 * 分页查询
	 * @Title: findByPage 
	 * @author vania
	 * @version 1.0
	 * @param page
	 * @param dbObject
	 * @param keysColl
	 * @param connName
	 * @param clazz
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	public MongoPage findByPage(MongoPage page, DBObject dbObject,Collection<String> keysColl,String connName, Class<?> clazz) {
		return this.findByPage(page, dbObject, this.buildFieldDBObject(keysColl), connName, clazz);
	}
	
	/**
	 * 分页查询
	 * @Title: findByPage 
	 * @author vania
	 * @version 1.0
	 * @param page
	 * @param dbObject
	 * @param keys
	 * @param connName
	 * @param clazz
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	public MongoPage findByPage(MongoPage page, DBObject dbObject,DBObject keys,String connName, Class<?> clazz) {
		List<Object> resultList=new ArrayList<Object>();
		   DBCollection dbCollection = null;
		   int count = 0;
		   long beginTime = System.currentTimeMillis();
	       try {
	    	   dbCollection = getReadDBCollection(connName);
	    	   dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);//读写分离，读从服务器，写主服务器
	    	   // 总记录数
	    	   if (null == page.getTotalCount() || page.getTotalCount() == 0){
	    		   count = dbCollection.find(dbObject, new BasicDBObject().append("_id", 1)).count();
	               page.setTotalCount(count);
	    	   } else {
	    		   count = page.getTotalCount();
	    	   }
	           
	           int skip = (page.getPageNumber() - 1)* page.getPageSize();
	           
	           List<DBObject> list = null;
	           if(page.getSort() != null){
	        	   list = dbCollection.find(dbObject,keys).sort(page.getSort().getSortObject()).skip(skip).limit(page.getPageSize()).toArray();   
	           }else{
	        	   list = dbCollection.find(dbObject,keys).skip(skip).limit(page.getPageSize()).toArray();
	           }
	           
	           try {
	        	   if(list != null && list.size() > 0){
	        		   page.setFirstRecordTime((Long)list.get(0).get(FieldMapping.CREATE_TIME.getMongoField()));
			           page.setLastRecordTime((Long)list.get(list.size() - 1).get(FieldMapping.CREATE_TIME.getMongoField()));
	        	   }
	           }catch(Exception e){
	        	   LogCvt.error(e.getMessage(), e);	        	   
	           }
	           
	           for(DBObject result : list){
	        	   String jsonStr = JSON.serialize(result);
	 			   Object entity = JSonUtil.toObject(jsonStr, clazz);
	               resultList.add(entity);
	           }
	           page.build(resultList);
	           
	           page.setHasNext(page.getPageNumber() < page.getPageCount()); // 设置是否有下一页
	       } catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       } finally{
	    	   LogCvt.debug(new StringBuffer("mongo findByPage: collection=").append(connName).append(",SQL=[").append(dbObject).append("]").append(",keys=[").append(keys).append("]").append(",sort=[").append(page.getSort()!=null?page.getSort().getSortObject():"").append("]").append(",Records=").append(count).append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
	    	   closeConnection();
	       }
	       
	       return page;
	}
	
	/**
	 * 根据字段数值列表查找mongodb
	 * 
	 * @param collectionName
	 * @param queryObj
	 * @param fieldName
	 * @param queryList
	 * @param clazz
	 * @return
	 */
	public <T> List<T> findByList(String collectionName, DBObject queryObj,
			String fieldName, List<?> queryList, Class<T> clazz) {
		List<T> resultList = null;
		DBCollection dbCollection = null;
		DBCursor cursor = null;
		String jsonStr = null;
		long beginTime = System.currentTimeMillis();
		try {
			dbCollection = getReadDBCollection(collectionName);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);// 读写分离，读从服务器，写主服务器

			// 如queryObj为null，则无其他查找条件
			if (null == queryObj){
				queryObj = new BasicDBObject();
			}
			queryObj.put(fieldName, new BasicDBObject("$in", queryList));
			cursor = dbCollection.find(queryObj);

			resultList = new ArrayList<T>();
			while (cursor.hasNext()) {
				jsonStr = JSON.serialize(cursor.next());
				resultList.add(JSonUtil.toObject(jsonStr, clazz));
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer().append("mongo findByList: where=").append(queryObj).append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
			closeConnection();
		}

		return resultList;
	}
	
	@Override
	@Deprecated
	public List<?> findAllByFieldNames(DBObject dbObject, String connName, Collection<String> fieldNames, Class<?> clazz) {
		return this.findAll(dbObject, this.buildFieldDBObject(fieldNames), null, connName, clazz);
	}
	
	/**
	 * 执行db command
	 * @param dbObject
	 * @return    
	 * @see com.froad.db.mongo.MongoService#command(com.mongodb.DBObject)
	 */
	public CommandResult command(DBObject dbObject){
		LogCvt.debug("mongo command: "+dbObject.toString());
		return writeMongoDB.command(dbObject);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List distinct(String connName,String key,DBObject query) {
	   DBCollection dbCollection = null;
	   long beginTime = System.currentTimeMillis();
       try {
    	   dbCollection = getReadDBCollection(connName);
    	   dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);//读写分离，读从服务器，写主服务器
           return dbCollection.distinct(key, query);
       }catch(Exception e) {
           LogCvt.error(e.getMessage(), e);
       }finally{
    	   LogCvt.debug(new StringBuffer().append("mongo distinct: where=").append(query.toString()).append(";key=").append(key).append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
    	   closeConnection();
       }
       return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MongoPage findArrayByPage(String collectionName,
			DBObject queryObj, String arrayName, MongoPage page) {
		List<String> resultList = new ArrayList<String>();
		List<Integer> sliceList = null;
		DBCollection dbCollection = null;
		DBObject fieldObj = null;
		DBObject resultObj = null;
		DBObject matchObj = null;
		DBObject prjObj = null;
		DBObject prjFieldObj = null;
		List<DBObject> pipeline = null;
		int totalCount = 0;
		int skip = 0;
		String countField = "array_count";
		AggregationOutput aggOutput = null;
		long beginTime = System.currentTimeMillis();
		try {
			dbCollection = getReadDBCollection(collectionName);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);// 读写分离，读从服务器，写主服务器
			
			matchObj = new BasicDBObject();
			matchObj.put("$match", queryObj);
			
			prjFieldObj = new BasicDBObject();
			prjFieldObj.put("_id", 0);// 不返回_id字段
			prjFieldObj.put(countField, new BasicDBObject("$size",
					new StringBuffer("$").append(arrayName).toString()));// 返回数组大小字段
			prjObj = new BasicDBObject();
			prjObj.put("$project", prjFieldObj);
			
			pipeline = new ArrayList<DBObject>();
			pipeline.add(matchObj);
			pipeline.add(prjObj);

			// 总记录数
			if (page.getTotalCount() <= 0) {
				aggOutput = dbCollection.aggregate(pipeline);
				if (null != aggOutput && null != aggOutput.results() && aggOutput.results().iterator().hasNext()){
					totalCount = Integer.parseInt(aggOutput.results().iterator().next().get(countField).toString());
					page.setTotalCount(totalCount);
				}
				
				// 找不到相应记录
				if (totalCount == 0) {
					return page;
				}
			}

			skip = (page.getPageNumber() - 1) * page.getPageSize();

			fieldObj = new BasicDBObject();
			sliceList = new ArrayList<Integer>();
			sliceList.add(skip);// 开始条数
			sliceList.add(page.getPageSize());// 每页记录数
			fieldObj.put(arrayName, new BasicDBObject("$slice", sliceList));

			resultObj = dbCollection.findOne(queryObj, fieldObj);
			resultList = (List<String>) resultObj.get(arrayName);

			page.build(resultList);

		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer("mongo findArrayByPage: collection=")
					.append(collectionName).append(",SQL=[").append(queryObj)
					.append("]").append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
			closeConnection();
		}

		return page;
	}
	
	public Iterator<DBObject> findByPipeline(List<DBObject> pipeline, String collectionName) {
		DBCollection dbCollection = null;
		Iterator<DBObject> it = null;
		long beginTime = System.currentTimeMillis();
		try {
			dbCollection = getReadDBCollection(collectionName);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);// 读写分离，读从服务器，写主服务器

			AggregationOutput aggOutput = dbCollection.aggregate(pipeline);

			if (null != aggOutput && null != aggOutput.results()) {
				it = aggOutput.results().iterator();
//				return (Iterable<DBObject>) it;
				
//				list = new ArrayList<T>();
//				
//				it = aggOutput.results().iterator();
//				while (it.hasNext()) {
//					DBObject obj = it.next();
//					list.add(JSonUtil.toObject(obj.toString()));
//				}
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer("mongo findByPipeline: collection=").append(collectionName).append(", pipeline=[").append(pipeline).append("]").append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
			closeConnection();
		}
		return it;
	}
	
	public <T> List<T> findByPipeline(List<DBObject> pipeline, String collectionName, Class<T> clazz) {
//		DBCollection dbCollection = null;
		List<T> list = null;
		long beginTime = System.currentTimeMillis();
		try {
//			dbCollection = getReadDBCollection(collectionName);
//			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);// 读写分离，读从服务器，写主服务器
//
//			AggregationOutput aggOutput = dbCollection.aggregate(pipeline);

			Iterator<DBObject> it = this.findByPipeline(pipeline, collectionName);
			if (null != it) {
				list = new ArrayList<T>();
				
//				it = aggOutput.results().iterator();
				while (it.hasNext()) {
					DBObject obj = it.next();
					list.add(JSonUtil.toObject(obj.toString(), clazz));
				}
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer("mongo findByPipeline: collection=").append(collectionName).append(", pipeline=[").append(pipeline).append("]").append(",Records=").append(list==null?0:list.size()).append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
//			LogCvt.debug(new StringBuffer("mongo findByPipeline: ").append("Records=").append(list==null?0:list.size()).toString());
			closeConnection();
		}
		return list;
	}
	
	@Override
	public MongoPage findDistinctArrayByPage(String collectionName,
			DBObject queryObj, String arrayName, MongoPage page) {
		
		DB db = MongoService.getReadMongoDB();
		List<String> resultList = new ArrayList<String>();
		int totalCount = 0;
		int start = 0;
		int end = 0;
		long beginTime = System.currentTimeMillis();
		try {
		    //构造查询以及过滤条件
            BasicDBObject searchCmd = new BasicDBObject();      
            searchCmd.put("distinct", collectionName);
            searchCmd.put("key", arrayName);
            searchCmd.append("query", queryObj);
            CommandResult commandResult = db.command(searchCmd);
            BasicDBList arr = (BasicDBList)commandResult.get("values");
        
            //获取结果集长度
            totalCount = arr.size();
            page.setTotalCount(totalCount);
            
            // 找不到相应记录
		    if (totalCount == 0) {
		    	return page;
		    }
        
		    start = (page.getPageNumber() - 1) * page.getPageSize();
		    end = page.getPageNumber() * page.getPageSize();
		
		    //结果集小于预期
		    if(start >= totalCount){
		    	return page;
		    }
		    
		    //结果集最后一页
		    if(end > totalCount){
		    	end = totalCount;
		    }
		
		    Object[] resArr = arr.toArray();
		    for(int i = start; i < end; i++){
			    resultList.add(resArr[i].toString());
		    }
		 
		    page.build(resultList);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer("mongo findDistinctArrayByPage: collection=")
					.append(collectionName).append(",SQL=[").append(queryObj)
					.append("]").append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
			closeConnection();
		}
		return page;
	}
	
	@Override
	public MongoPage findUnwindArrayByPage(String collectionName,
			DBObject queryObj, String arrayName, MongoPage page) {
		List<String> resultList = new ArrayList<String>();
		DBCollection dbCollection = null;
		DBObject matchObj = null;
		DBObject projectObj = null;
		DBObject unwindObj = null;
		DBObject groupObj = null;
		DBObject skipObj = null;
		DBObject limitObj = null;
		DBObject projectField = null;
		List<DBObject> pipeline = null;
		int totalCount = 0;
		int skip = 0;
		String countField = "array_count";
		String resultField = "result";
		AggregationOutput aggOutput = null;
		Iterator<DBObject> it = null;
		long beginTime = System.currentTimeMillis();
		try {
			dbCollection = getReadDBCollection(collectionName);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);// 读写分离，读从服务器，写主服务器
			
			// $match - 查找条件
			matchObj = new BasicDBObject();
			matchObj.put("$match", queryObj);
			
			// $project - 返回字段
			projectField = new BasicDBObject();
			projectField.put("_id", 0);// 不返回_id字段
			projectField.put(arrayName, 1);// 返回数组大小字段
			projectObj = new BasicDBObject();
			projectObj.put("$project", projectField);
			
			// $unwind - 分割数组
			unwindObj = new BasicDBObject();
			unwindObj.put("$unwind", new StringBuffer("$").append(arrayName).toString());
			
			// $group - 获取数组大小
			groupObj = new BasicDBObject();
			groupObj.put("$group", new BasicDBObject("_id", resultField).append(countField, new BasicDBObject("$sum", 1)));
			
			pipeline = new ArrayList<DBObject>();
			pipeline.add(matchObj);
			pipeline.add(projectObj);
			pipeline.add(unwindObj);
			pipeline.add(groupObj);

			// 总记录数
			if (page.getTotalCount() <= 0) {
				aggOutput = dbCollection.aggregate(pipeline);
				if (null != aggOutput && null != aggOutput.results() && aggOutput.results().iterator().hasNext()){
					totalCount = Integer.parseInt(aggOutput.results().iterator().next().get(countField).toString());
					page.setTotalCount(totalCount);
				}
				
				// 找不到相应记录
				if (totalCount == 0) {
					return page;
				}
			}

			skip = (page.getPageNumber() - 1) * page.getPageSize();

			// $skip - 分页查找开始条数
			skipObj = new BasicDBObject();
			skipObj.put("$skip", skip);
			
			// $limit - 每页返回条数
			limitObj = new BasicDBObject();
			limitObj.put("$limit", page.getPageSize());
			
			pipeline = new ArrayList<DBObject>();
			pipeline.add(matchObj);
			pipeline.add(projectObj);
			pipeline.add(unwindObj);
			pipeline.add(skipObj);
			pipeline.add(limitObj);
			
			aggOutput = dbCollection.aggregate(pipeline);
			if (null != aggOutput && null != aggOutput.results()){
				it = aggOutput.results().iterator();
				while (it.hasNext()){
					resultList.add(it.next().get(arrayName).toString());
				}
			}

			page.build(resultList);

		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer("mongo findUnwindArrayByPage: collection=")
					.append(collectionName).append(",SQL=[").append(queryObj)
					.append("]").append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
			closeConnection();
		}

		return page;
	}
	
	/**
	 * 分页查询
	 * @Title: findByPageWithRedis 
	 * @param page
	 * @param dbObject
	 * @param connName
	 * @param clazz
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */

	public MongoPage findByPageWithRedis(MongoPage page, DBObject dbObject,Collection<String> keysColl, String connName,Class<?> clazz) {
		return this.findByPageWithRedis(page, dbObject, this.buildFieldDBObject(keysColl), connName, clazz);
	}
	
	/**
	 * 分页查询
	 * @Title: findByPageWithRedis 
	 * @param page
	 * @param dbObject
	 * @param connName
	 * @param clazz
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	public MongoPage findByPageWithRedis(MongoPage page, DBObject dbObject, String connName, Class<?> clazz) {
		return this.findByPageWithRedis(page, dbObject, new BasicDBObject(), connName,clazz);
	}
	
	/**
	 * 分页查询
	 * @Title: findByPageWithRedis 
	 * @param page
	 * @param dbObject
	 * @param connName
	 * @param clazz
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public MongoPage findByPageWithRedis(MongoPage page, DBObject dbObject, DBObject keys, String connName, Class<?> clazz) {
		page.setHasNext(false); // 默认没有下一页
		List<Object> resultList = new ArrayList<Object>();
		DBCollection dbCollection = null;
		int count = 0;
		StringBuffer key = null;
		String md5Key = null;
		DBObject sortObj = null;
		RedisService redisService = null;
		List<DBObject> list = null;
		int listSize = 0; // 数据库返回的list的size
		int fetchCount = 0;// 向mongodb查询多少条数据
		int fetchPage = 0;// 与上一次查询的页数的差值
		int totalPage = 0;
//		int returnCount = 0;
		int startIndex = 0;
		Integer pageNumber = page.getPageNumber(); // 当前查询页码
		if (pageNumber == null || pageNumber < 1)
			pageNumber = 1;
		Integer pageSize = page.getPageSize(); // 每页查询多少条数据
		Map<String, Map<String, Long>> keyMap = null;
		Map<String, Long> fieldMap = null;
		Long firstRecordTime = page.getFirstRecordTime(), lastRecordTime = page.getLastRecordTime();
		long beginTime = System.currentTimeMillis();
		try {
			dbCollection = getReadDBCollection(connName);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);// 读写分离，读从服务器，写主服务器
			
			sortObj = new BasicDBObject(FieldMapping.CREATE_TIME.getMongoField(), OrderBy.DESC.getCode());
			
			// 如非初次查找，firstRecordTime为必传
			if (pageNumber > 1 && null != firstRecordTime && firstRecordTime <= 0) {
				return page;
			}
			
			// 如非初次查找，lastRecordTime为必传
			if (pageNumber > 1 && null != lastRecordTime && lastRecordTime <= 0) {
				return page;
			}
			
			// 总记录数
			if (null == page.getTotalCount() || page.getTotalCount() == 0) {
				redisService = new RedisManager();
				key = new StringBuffer();
				key.append(connName);
				key.append(dbObject.toString());
				key.append(sortObj);
				md5Key = MD5Util.getMD5Str(key.toString());
				// 从redis缓存获取分页查找总页数
				count = redisService.getPageQueryTotalCount(md5Key);
				
				// 如缓存不存在分页查找信息，查找mongo，并把结果存放到redis中
				if (count == 0) {
					count = (int) dbCollection.getCount(dbObject, new BasicDBObject().append("_id", 1));
					
					// 如查找不到数据，直接返回
					if (count == 0) {
						return page;
					}
					
					// 如分页查找总数量超过临界值，添加到缓存
					if (count >= minRedisPageCount) {
						redisService.setPageQueryTotalCount(md5Key, count);
					}
				}
				
				page.setTotalCount(count);
				
			} else {
				count = page.getTotalCount();
			}
			totalPage = (int) Math.ceil(count / pageSize.doubleValue());// 计算总页数
			page.setPageCount(totalPage); // 设置总页数
			
			fetchPage = pageNumber - page.getLastPageNumber(); // 与上一次查询的页数的差值

			if (pageNumber <= 1) { // 查询第一页
				fetchCount = pageSize; // 最后一页只查询n条数据
			} else if (pageNumber >= totalPage) { // 最后一页
				reverseSort(sortObj); // 反转排序条件 （原来是升序的字段反转为降序，反之亦然）
				int mol = count % pageSize; // 取摩尔
				fetchCount = mol == 0 ? pageSize : mol; // 计算最后一页只查询的数据条数
			} else if (fetchPage == 0) { // 与上次查询是同一页
				fetchCount = pageSize; // 

				if (firstRecordTime > 0 && lastRecordTime > 0) {
					if (dbObject.containsField(FieldMapping.CREATE_TIME.getMongoField())) {
						dbObject.removeField(FieldMapping.CREATE_TIME.getMongoField());
					}
					dbObject.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject(QueryOperators.GTE, lastRecordTime).append(QueryOperators.LTE, firstRecordTime));
//					BasicDBList subValues2 = new BasicDBList();
//					subValues2.add(new BasicDBObject(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject(QueryOperators.LTE, firstRecordTime)));
//					subValues2.add(new BasicDBObject(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject(QueryOperators.GTE, lastRecordTime)));
//					dbObject.put(QueryOperators.AND, subValues2);
				}
			} else if (fetchPage < 0) { // 往上翻页(包含往上跨页翻)
				fetchCount = Math.abs(fetchPage) * pageSize;
				reverseSort(sortObj); // 反转排序条件

				if (firstRecordTime > 0) {
					// 往上分页查找
					if (dbObject.containsField(FieldMapping.CREATE_TIME.getMongoField())) {
						// 重组create_time查询条件
						keyMap = dbObject.toMap();
						fieldMap = keyMap.get(FieldMapping.CREATE_TIME.getMongoField());
						fieldMap.put(QueryOperators.GT, firstRecordTime);
						keyMap.put(FieldMapping.CREATE_TIME.getMongoField(), fieldMap);

						dbObject.removeField(FieldMapping.CREATE_TIME.getMongoField());
						dbObject.putAll(keyMap);
					} else {
						dbObject.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject(QueryOperators.GT, firstRecordTime));
					}
				}
			} else { // 往下页翻(包含往下跨页翻)
				fetchCount = Math.abs(fetchPage) * pageSize;

				if (lastRecordTime > 0) {
					// 往下分页查找
					if (dbObject.containsField(FieldMapping.CREATE_TIME.getMongoField())) {
						// 重组create_time查询条件
						keyMap = dbObject.toMap();
						fieldMap = keyMap.get(FieldMapping.CREATE_TIME.getMongoField());
						fieldMap.put(QueryOperators.LT, lastRecordTime);
						keyMap.put(FieldMapping.CREATE_TIME.getMongoField(), fieldMap);

						dbObject.removeField(FieldMapping.CREATE_TIME.getMongoField());
						dbObject.putAll(keyMap);
					} else {
						dbObject.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject(QueryOperators.LT, lastRecordTime));
					}
				}
			}	
			
			list = dbCollection.find(dbObject, keys).sort(sortObj).limit(fetchCount).toArray(); // 查询mongodb
			listSize = CollectionUtils.isEmpty(list) ? 0 : list.size();
			if (listSize == 0) {
				return page;
			}
			
			if (pageNumber <= 1) { // 查询第一页
				// 第一页不做任何处理
			} else if (pageNumber >= totalPage) { // 最后一页的结果集要反转
				Collections.reverse(list); // 反转查询结果集
			} else if (fetchPage == 0) { // 与上次查询是同一页
				// 与上次查询是同一页也不做任何处理			
			} else if (fetchPage < 0) { // 往上翻页(包含往上跨页翻)
				list = list.subList((listSize - pageSize), listSize); // 一定要先截取, 然后再反转list
				Collections.reverse(list); // 一定要先截取, 然后再反转截取后的结果集
			} else { // 往下翻页(包含往下跨页翻)
				startIndex = (fetchPage - 1) * pageSize;
				list = list.subList(startIndex, (listSize < pageSize + startIndex ? listSize : pageSize + startIndex));
			}

			// firstRecordTime, lastRecordTime
			try {
				page.setFirstRecordTime((Long) list.get(0).get(FieldMapping.CREATE_TIME.getMongoField()));
//				page.setLastRecordTime((Long) list.get(listSize - 1).get(FieldMapping.CREATE_TIME.getMongoField()));
				page.setLastRecordTime((Long) list.get(list.size() - 1).get(FieldMapping.CREATE_TIME.getMongoField()));
			} catch (Exception e) {
				LogCvt.error(e.getMessage(), e);
			}
			
			for (DBObject result : list) {				
				String jsonStr = JSON.serialize(result);
				Object entity = JSonUtil.toObject(jsonStr, clazz);
				resultList.add(entity);
			}
			
			page.build(resultList);
			page.setLastPageNumber(pageNumber); // 设置上一次查询的页码
			page.setHasNext(pageNumber < page.getPageCount()); // 设置是否有下一页
			
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer("mongo findByPageWithRedis: collection=").append(connName).append(",SQL=[").append(dbObject).append("]").append(",keys=[").append(keys).append("]").append(",sort=[").append(sortObj).append("]").append(",limit=[").append(fetchCount).append("]").append(",Records=").append(listSize).append(",Count=").append(count).append(",Time=").append(System.currentTimeMillis() - beginTime).append("ms").toString());
			closeConnection();
		}
		
		return page;
	}

	public MongoPage findByPageWithSortObject(MongoPage page, DBObject dbObject, DBObject keys, String connName, Class<?> clazz) {
		page.setHasNext(false); // 默认没有下一页
		List<Object> resultList = new ArrayList<Object>();
		DBCollection dbCollection = null;
		int count = 0;
		StringBuffer key = null;
		String md5Key = null;
		DBObject sortObj = null;
		RedisService redisService = null;
		List<DBObject> list = null;
		int listSize = 0; // 数据库返回的list的size
		int fetchCount = 0;
		int fetchPage = 0;
		int totalPage = 0;
//		int returnCount = 0;
		int startIndex = 0;
		Integer pageNumber = page.getPageNumber(); // 当前查询页码
		Integer pageSize = page.getPageSize(); // 每页查询多少条数据
		Map<String, Map<String, Long>> keyMap = null;
		Map<String, Long> fieldMap = null;
		Long firstRecordTime = page.getFirstRecordTime(), lastRecordTime = page.getLastRecordTime();
		long beginTime = System.currentTimeMillis();
		try {
			dbCollection = getReadDBCollection(connName);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);// 读写分离，读从服务器，写主服务器

			String firstSortField = null; // 第一个排序字段名
			Sort sort = page.getSort();
			if (sort != null) {
				BasicDBObject s = (BasicDBObject) sort.getSortObject();

				if (s != null && !s.isEmpty()) {
					sortObj = s;
					LinkedHashMap<String, Integer> map = (LinkedHashMap<String, Integer>) s.toMap();
					for (Map.Entry<String, Integer> kv : (Set<Map.Entry<String, Integer>>) map.entrySet()) {
						firstSortField = kv.getKey();
						// Integer v = kv.getValue(); // 排序值

						// 只取第一个排序字段
						break;
					}
				}
			}
			if (null == firstSortField) {
				firstSortField = FieldMapping.CREATE_TIME.getMongoField(); // 默认用创建时间排序
			}
			if (null == sortObj) {
				sortObj = new BasicDBObject(firstSortField, OrderBy.DESC.getCode());
			}
//			if (FieldMapping.CREATE_TIME.getMongoField().equals(firstSortField)) {
//				sortObj.put(FieldMapping.CREATE_TIME.getMongoField(), OrderBy.DESC.getCode()); // 再按照create_time降序排列
//			}
			
			int firstOrderBy = (Integer) sortObj.get(firstSortField); // 第一个字段的排序条件

			// 如非初次查找，firstRecordTime为必传
			if (pageNumber > 1 && null != firstRecordTime && firstRecordTime <= 0) {
				return page;
			}

			// 如非初次查找，lastRecordTime为必传
			if (pageNumber > 1 && null != lastRecordTime && lastRecordTime <= 0) {
				return page;
			}

			// 总记录数
			if (null == page.getTotalCount() || page.getTotalCount() == 0) {
				redisService = new RedisManager();
				key = new StringBuffer();
				key.append(connName);
				key.append(dbObject.toString());
				key.append(sortObj);
				md5Key = MD5Util.getMD5Str(key.toString());
				// 从redis缓存获取分页查找总页数
				count = redisService.getPageQueryTotalCount(md5Key);

				// 如缓存不存在分页查找信息，查找mongo，并把结果存放到redis中
				if (count == 0) {
					count = (int) dbCollection.getCount(dbObject, new BasicDBObject().append("_id", 1));

					// 如查找不到数据，直接返回
					if (count == 0) {
						return page;
					}

					// 如分页查找总数量超过临界值，添加到缓存
					if (count >= minRedisPageCount) {
						redisService.setPageQueryTotalCount(md5Key, count);
					}
				}

				page.setTotalCount(count);

			} else {
				count = page.getTotalCount();
			}
			totalPage = (int) Math.ceil(count / pageSize.doubleValue());// 计算总页数
			page.setPageCount(totalPage); // 设置总页数

			fetchPage = pageNumber - page.getLastPageNumber(); // 与上一次查询的页数的差值

			BasicDBObject pageQueryDBObject = null;
			if (pageNumber <= 1) { // 查询第一页
				fetchCount = pageSize; // 最后一页只查询n条数据
			} else if (pageNumber >= totalPage) { // 最后一页
				reverseSort(sortObj); // 反转排序条件 （原来是升序的字段反转为降序，反之亦然）
				int mol = count % pageSize; // 取摩尔
				fetchCount = mol == 0 ? pageSize : mol; // 计算最后一页只查询的数据条数
			} else if (fetchPage == 0) { // 与上次查询是同一页
				fetchCount = pageSize; // 

				if (firstRecordTime > 0 && lastRecordTime > 0) {
					if (firstOrderBy < 0) { // 降序
						pageQueryDBObject = new BasicDBObject(firstSortField, new BasicDBObject(QueryOperators.GTE, lastRecordTime).append(QueryOperators.LTE, firstRecordTime));
					} else { // 升序	
						pageQueryDBObject = new BasicDBObject(firstSortField, new BasicDBObject(QueryOperators.GTE, firstRecordTime).append(QueryOperators.LTE, lastRecordTime));
					}	
					
				}
			} else if (fetchPage < 0) { // 往上翻页(包含往上跨页翻)
				fetchCount = Math.abs(fetchPage) * pageSize;
				reverseSort(sortObj); // 反转排序条件
				// 往上分页查找
				
				if (firstOrderBy < 0) { // 降序
					pageQueryDBObject = new BasicDBObject(firstSortField, new BasicDBObject(QueryOperators.GT, firstRecordTime));
				} else {// 升序	
					pageQueryDBObject = new BasicDBObject(firstSortField, new BasicDBObject(QueryOperators.LT, firstRecordTime));
				}				
			} else { // 往下页翻(包含往下跨页翻)
				fetchCount = Math.abs(fetchPage) * pageSize;
				// 往下分页查找
				if (firstOrderBy < 0) { // 降序
					pageQueryDBObject = new BasicDBObject(firstSortField, new BasicDBObject(QueryOperators.LT, lastRecordTime));
				} else {
					pageQueryDBObject = new BasicDBObject(firstSortField, new BasicDBObject(QueryOperators.GT, lastRecordTime));
				}
			}
			
			if (null != pageQueryDBObject && !pageQueryDBObject.isEmpty()) {
				BasicDBList and = (BasicDBList) dbObject.get(QueryOperators.AND);
				if (null == and || and.size() < 1) {
					and = new BasicDBList();
				}
				and.add(pageQueryDBObject);
				dbObject.put(QueryOperators.AND, and);
			}
			
			list = dbCollection.find(dbObject, keys).sort(sortObj).limit(fetchCount).toArray(); // 查询mongodb
			listSize = CollectionUtils.isEmpty(list) ? 0 : list.size();
			if (listSize == 0) {
				return page;
			}
			
			if (pageNumber <= 1) { // 查询第一页
				// 第一页不做任何处理
			} else if (pageNumber >= totalPage) { // 最后一页的结果集要反转
				Collections.reverse(list); // 反转查询结果集
			} else if (fetchPage == 0) { // 与上次查询是同一页
				// 与上次查询是同一页也不做任何处理			
			} else if (fetchPage < 0) { // 往上翻页(包含往上跨页翻)
				list = list.subList((listSize - pageSize), listSize); // 一定要先截取, 然后再反转list
				Collections.reverse(list); // 一定要先截取, 然后再反转截取后的结果集
			} else { // 往下翻页(包含往下跨页翻)
				startIndex = (fetchPage - 1) * pageSize;
				list = list.subList(startIndex, (listSize < pageSize + startIndex ? listSize : pageSize + startIndex));
			}

			// firstRecordTime, lastRecordTime
			try {
				page.setFirstRecordTime((Long) list.get(0).get(firstSortField));
//				page.setLastRecordTime((Long) list.get(listSize - 1).get(firstSortField));
				page.setLastRecordTime((Long) list.get(list.size() - 1).get(firstSortField));
			} catch (Exception e) {
				LogCvt.error(e.getMessage(), e);
			}

			for (DBObject result : list) {
				String jsonStr = JSON.serialize(result);
				Object entity = JSonUtil.toObject(jsonStr, clazz);
				resultList.add(entity);
			}

			page.build(resultList);
			page.setLastPageNumber(pageNumber); // 设置上一次查询的页码
			page.setHasNext(pageNumber < page.getPageCount()); // 设置是否有下一页

		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			LogCvt.debug(new StringBuffer("mongo findByPageWithSortObject: collection=").append(connName).append(",SQL=[").append(dbObject).append("]").append(",keys=[").append(keys).append("]").append(",sort=[").append(sortObj).append("]").append(",limit=[").append(fetchCount).append("]").append(",Records=").append(listSize).append(",Count=").append(count).append(",Time=").append(System.currentTimeMillis() - beginTime).append("ms").toString());
			closeConnection();
		}

		return page;
	}
	

	@Override
	public MongoPage findByPageAggregate(MongoPage page,List<DBObject> pipeline, DBObject countQueryDbObj,String connName, Class<?> clazz) {
		 List list = new ArrayList();
		 long beginTime = System.currentTimeMillis();
		  try {
			  DBCollection dbCollection = dbCollection = getReadDBCollection(connName);
	    	  dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);//读写分离，读从服务器，写主服务器
	    	  // 总记录数
	    	  int count = 0;
	    	  if (null == page.getTotalCount() || page.getTotalCount() == 0){
	    		  count = dbCollection.find(countQueryDbObj, new BasicDBObject().append("_id", 1)).count();
	              page.setTotalCount(count);
	    	  } else {
	    		  count = page.getTotalCount();
	    	  }
	    	  
	    	  int skip = (page.getPageNumber() - 1)* page.getPageSize();
	    	  pipeline.add(new BasicDBObject("$skip",skip));
	    	  pipeline.add(new BasicDBObject("$limit",page.getPageSize()));
	    	  AggregationOutput aggOutput = dbCollection.aggregate(pipeline);
	    	 
			  if (null != aggOutput && null != aggOutput.results()) {
				  Iterable<DBObject> itResult = aggOutput.results();
					Iterator<DBObject> it = itResult.iterator();
					while (it.hasNext()) {
						DBObject obj = it.next();
						list.add(JSonUtil.toObject(JSonUtil.toJSonString(obj),clazz));
					}
				}
			  page.build(list);
			  
		  }catch(Exception e) {
	           LogCvt.error(e.getMessage(), e);
	       } finally{
	    	   LogCvt.debug(new StringBuffer("mongo findByPageWithRedis: collection=").append(connName).append(",SQL=[").append(JSonUtil.toJSonString(pipeline)).append("]").append(",Time=").append(System.currentTimeMillis()-beginTime).append("ms").toString());
	    	   closeConnection();
	       }
		return page;
	}
	
	public DBCursor findByPageForDBCursor(MongoPage page, DBObject dbObject, DBObject keys, String connName) {
		page.setHasNext(false); // 默认没有下一页
//		List<Object> resultList = new ArrayList<Object>();
		DBCursor cursor = null;
		DBCollection dbCollection = null;
		int count = 0;
		StringBuffer key = null;
		String md5Key = null;
		DBObject sortObj = null;
		RedisService redisService = null;
//		int fetchCount = 0;// 向mongodb查询多少条数据
//		int fetchPage = 0;// 与上一次查询的页数的差值
		int totalPage = 0;
//		int returnCount = 0;
//		int startIndex = 0;
		Integer pageNumber = page.getPageNumber(); // 当前查询页码
		Integer pageSize = page.getPageSize(); // 每页查询多少条数据
		Map<String, Map<String, Long>> keyMap = null;
		Map<String, Long> fieldMap = null;
		long beginTime = System.currentTimeMillis();
		try {
			dbCollection = getReadDBCollection(connName);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);// 读写分离，读从服务器，写主服务器

			sortObj = new BasicDBObject(FieldMapping.CREATE_TIME.getMongoField(), OrderBy.DESC.getCode());

			// 如非初次查找，firstRecordTime为必传
			if (pageNumber > 1 && null != page.getFirstRecordTime() && page.getFirstRecordTime() <= 0) {
				return cursor;
			}

			// 如非初次查找，lastRecordTime为必传
			if (pageNumber > 1 && null != page.getLastRecordTime() && page.getLastRecordTime() <= 0) {
				return cursor;
			}

			// 总记录数
			if (null == page.getTotalCount() || page.getTotalCount() == 0) {
				redisService = new RedisManager();
				key = new StringBuffer();
				key.append(connName);
				key.append(dbObject.toString());
				key.append(sortObj);
				md5Key = MD5Util.getMD5Str(key.toString());
				// 从redis缓存获取分页查找总页数
				count = redisService.getPageQueryTotalCount(md5Key);

				// 如缓存不存在分页查找信息，查找mongo，并把结果存放到redis中
				if (count == 0) {
					count = (int) dbCollection.getCount(dbObject, new BasicDBObject().append("_id", 1));

					// 如查找不到数据，直接返回
					if (count == 0) {
						return cursor;
					}

					// 如分页查找总数量超过临界值，添加到缓存
					if (count >= minRedisPageCount) {
						redisService.setPageQueryTotalCount(md5Key, count);
					}
				}

				page.setTotalCount(count);

			} else {
				count = page.getTotalCount();
			}
			totalPage = (int) Math.ceil(count / pageSize.doubleValue());// 计算总页数
			page.setPageCount(totalPage); // 设置总页数
			page.setHasNext(pageNumber < page.getPageCount()); // 设置是否有下一页

//			fetchPage = pageNumber - page.getLastPageNumber();
//			// 页码相同
//			if (fetchPage == 0) { // 与上次查询是同一页
//				fetchPage = pageNumber;
//			} else if (fetchPage < 0) { // 往上翻
//				fetchCount = Math.abs(pageNumber) * pageSize;
//			} else if (fetchPage > 1) { // 往下跨页翻
//				fetchCount = Math.abs(pageNumber) * pageSize;
//			} else { // 下一页
//				fetchCount = Math.abs(fetchPage) * pageSize;
//			}

			if (pageNumber > page.getLastPageNumber() && page.getLastRecordTime() > 0) {
				// 往下分页查找
				if (dbObject.get(FieldMapping.CREATE_TIME.getMongoField()) != null) {
					// 重组create_time查询条件
					keyMap = dbObject.toMap();
					fieldMap = keyMap.get(FieldMapping.CREATE_TIME.getMongoField());
					fieldMap.put(QueryOperators.LT, page.getLastRecordTime());
					keyMap.put(FieldMapping.CREATE_TIME.getMongoField(), fieldMap);

					dbObject.removeField(FieldMapping.CREATE_TIME.getMongoField());
					dbObject.putAll(keyMap);
				} else {
					dbObject.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject(QueryOperators.LT, page.getLastRecordTime()));
				}
			} else if (pageNumber < page.getLastPageNumber() && page.getFirstRecordTime() > 0) {
				// 往上分页查找
				if (dbObject.get(FieldMapping.CREATE_TIME.getMongoField()) != null) {
					// 重组create_time查询条件
					keyMap = dbObject.toMap();
					fieldMap = keyMap.get(FieldMapping.CREATE_TIME.getMongoField());
					fieldMap.put(QueryOperators.GT, page.getFirstRecordTime());
					keyMap.put(FieldMapping.CREATE_TIME.getMongoField(), fieldMap);

					dbObject.removeField(FieldMapping.CREATE_TIME.getMongoField());
					dbObject.putAll(keyMap);
				} else {
					dbObject.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject(QueryOperators.GT, page.getFirstRecordTime()));
				}
			}

//			DBCursor cursor = dbCollection.find(dbObject, keys).sort(sortObj).limit(fetchCount);
			cursor = dbCollection.find(dbObject, keys).sort(sortObj).limit(pageSize);
			
//			int size = null == cursor ? 0 : cursor.size();
//			if (size == 0) {
//				return cursor;
//			}
//			totalPage = (int) Arith.round(Arith.div((double) count, (double) pageSize) + 0.5, 0);
//			page.setPageCount(totalPage);

			// 返回记录数
//			if (pageNumber == totalPage) {
//				returnCount = count - (pageSize * (pageNumber - 1));
//			} else {
//				returnCount = pageSize;
//			}

			// firstRecordTime, lastRecordTime
//			try {
				// page.setFirstRecordTime((Long)list.get(0).get(FieldMapping.CREATE_TIME.getMongoField()));
				// page.setLastRecordTime((Long)list.get(list.size() -
				// 1).get(FieldMapping.CREATE_TIME.getMongoField()));
//			} catch (Exception e) {
//				LogCvt.error(e.getMessage(), e);
//			}
			page.setLastPageNumber(pageNumber); // 设置上一次查询的页码
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			page.setHasNext(false); // 设置是否有下一页
		} finally {
			LogCvt.debug(new StringBuffer("mongo findByPageForDBCursor: collection=").append(connName).append(",SQL=[").append(dbObject).append("]").append(",keys=[").append(keys).append("]").append(",sort=[").append(sortObj).append("]").append(",limit=[").append(pageSize).append("]").append(",Records=").append(count).append(",Time=").append(System.currentTimeMillis() - beginTime).append("ms").toString());
			closeConnection();
		}
		return cursor;
	}
	
	public Iterator<DBObject> findByPageForIterator(MongoPage page, DBObject dbObject, DBObject keys, String connName) {
		Iterator<DBObject> it = null;

		DBCursor cursor = this.findByPageForDBCursor(page, dbObject, keys, connName);
		if (cursor != null)
			it = cursor.iterator();
		return it;
	}

	/**
	 * 报表导出专用分页
	 * findByPageForExport:(这里用一句话描述这个方法的作用).
	 * 不能跨页
	 * @author zhouzhiwei
	 * 2015年9月11日 下午2:46:33
	 * @return
	 *
	 */
	public MongoPage findByPageForExport(MongoPage page, DBObject dbObject, DBObject keys, String connName, Class<?> clazz) {
		List<Object> resultList = new ArrayList<Object>();
		Integer pageNumber = page.getPageNumber();
		DBCursor cursor = this.findByPageForDBCursor(page, dbObject, keys, connName);
		int size = null == cursor ? 0 : cursor.size();
		if (size == 0) {
			return page;
		}
		try {
			Iterator<DBObject> it = cursor.iterator();

			int ct = 1;
			while (it.hasNext()) {
				DBObject obj = it.next();
				if (ct == 1) {
					try {
						page.setFirstRecordTime(Long.valueOf(obj.get(FieldMapping.CREATE_TIME.getMongoField()).toString()));
					} catch (Exception e) {
						LogCvt.error(e.getMessage(), e);
					}
				} else if (ct == size) {
					try {
						page.setLastRecordTime(Long.valueOf(obj.get(FieldMapping.CREATE_TIME.getMongoField()).toString()));
					} catch (Exception e) {
						LogCvt.error(e.getMessage(), e);
					}
				}
				ct++;
				String jsonStr = JSON.serialize(obj);
				Object entity = JSonUtil.toObject(jsonStr, clazz);
				resultList.add(entity);
			}
			page.build(resultList);
			page.setLastPageNumber(pageNumber); // 设置上一次查询的页码
			page.setHasNext(pageNumber < page.getPageCount()); // 设置是否有下一页
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return page;
	}
	
	public BasicDBList group(String connName,DBObject where,Map<String,String> dimMap,Map<String,String> forIdxMap,Map<String,String> indexMap) {
		   DBCollection dbCollection = null;
	       
	       dbCollection = getReadDBCollection(connName);
    	   dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);//读写分离，读从服务器，写主服务器
	      
    	   //聚合查询
	       BasicDBList basicDBList = (BasicDBList) dbCollection.group(GroupUtil.generateFormulaKeyObject(dimMap),
	    		   					where,
	                               GroupUtil.generateFormulaInitObject(indexMap),
	                                GroupUtil.generateFormulaReduceObject(indexMap),
	                                 GroupUtil.generateFormulaFinalizeObject(forIdxMap, indexMap));
	       
	       return basicDBList;
		}
}