/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: MerchantMongo.java
 * @Package com.froad.db.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年3月25日
 */

package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.enums.FieldMapping;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.Actor;
import com.froad.po.mongo.AuditInstanceDetail;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;

/**
 * <p>
 * Title: BizInstanceAttrMongo.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年3月25日 下午8:47:31
 */

public class AuditInstanceDetailMongo {
	MongoManager manager = new MongoManager();

	/**
	 * 添加AuditInstanceDetail
	 * 
	 * @Title: AuditInstanceDetail
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param auditInstanceDetail
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean addAuditInstanceDetail(AuditInstanceDetail auditInstanceDetail) {
		LogCvt.debug(MongoTableName.CB_AUDIT_INSTANCE_DETAIL + "添加 ------->" + com.alibaba.fastjson.JSON.toJSONString(auditInstanceDetail));
		return manager.add(auditInstanceDetail, MongoTableName.CB_AUDIT_INSTANCE_DETAIL) > -1; // 向mongodb插入数据
	}

	/**
	 * 物理删除审核信息详情
	 * 
	 * @Title: removeAuditInstanceDetail
	 * @author vania
	 * @version 1.0
	 * @see:
	 * @param clientId
	 * @param instanceId
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean removeAuditInstanceDetail(String clientId, String instanceId) {
		DBObject where = new BasicDBObject();
		where.put(FieldMapping.CLIENT_ID.getMongoField(), clientId);
		where.put(FieldMapping.INSTANCE_ID.getMongoField(), instanceId);
		return manager.remove(where, MongoTableName.CB_AUDIT_INSTANCE_DETAIL) != -1;
	}
	
	/**
	 * 
	 * removeAuditInstanceDetail:物理删除审核信息详情
	 *
	 * @author vania
	 * 2015年12月1日 上午9:42:26
	 * @param auditInstanceDetail
	 * @return
	 *
	 */
	public boolean removeAuditInstanceDetail(AuditInstanceDetail auditInstanceDetail) {
		return manager.remove(auditInstanceDetail, MongoTableName.CB_AUDIT_INSTANCE_DETAIL) != -1;
	}

	/**
	 * 
	 * updateAuditInstanceDetailByClientIdAndInstanceId:修改AuditInstanceDetail
	 *
	 * @author vania 2015年10月28日 上午10:59:26
	 * @param auditInstanceDetail
	 * @param isArchive 本次执行完是否归档 要把next_actor给删除
	 * @return
	 *
	 */
	public boolean updateAuditInstanceDetailByClientIdAndInstanceId(AuditInstanceDetail auditInstanceDetail, boolean isArchive) {
		BasicDBObject q = new BasicDBObject(FieldMapping.CLIENT_ID.getMongoField(), auditInstanceDetail.getClientId()).append(FieldMapping.INSTANCE_ID.getMongoField(), auditInstanceDetail.getInstanceId());
		BasicDBObject o = new BasicDBObject();
		List<Actor> auditActor = auditInstanceDetail.getAuditActor();
		if (CollectionUtils.isNotEmpty(auditActor)) {
			o.put("$pushAll", new BasicDBObject(FieldMapping.AUDIT_ACTOR.getMongoField(), JSONObject.toJSON(auditActor)));
			auditInstanceDetail.setAuditActor(null); // 重置AuditActor
		}
		auditInstanceDetail.setInstanceId(null); // 重置InstanceId
		o.put("$set", (BasicDBObject) JSON.parse(JSONObject.toJSONString(auditInstanceDetail)));
		if(isArchive) {
			o.put("$unset", new BasicDBObject(FieldMapping.NEXT_ACTOR.getMongoField(), 1));
		}
		return manager.update(q, o, MongoTableName.CB_AUDIT_INSTANCE_DETAIL) != -1;
	}

	/**
	 * 
	 * mergerAuditInstanceDetailByInstanceId:覆盖AuditInstanceDetail
	 *
	 * @author vania 2015年10月28日 下午1:33:21
	 * @param auditInstanceDetail
	 * @return
	 *
	 */
	public boolean mergerAuditInstanceDetailByInstanceId(AuditInstanceDetail auditInstanceDetail) {
		BasicDBObject q = new BasicDBObject(FieldMapping.INSTANCE_ID.getMongoField(), auditInstanceDetail.getInstanceId());
		BasicDBObject o = new BasicDBObject();
		o.append("$set", (BasicDBObject) JSON.parse(JSONObject.toJSONString(auditInstanceDetail)));
		return manager.update(q, o, MongoTableName.CB_AUDIT_INSTANCE_DETAIL) != -1;
	}

	/**
	 * 根据instanceId查询审核信息详情
	 * 
	 * @Title: findAuditInstanceDetailByInstanceId
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param instanceId
	 * @return
	 * @return BizInstanceAttr 返回类型
	 * @throws
	 */
	public AuditInstanceDetail findAuditInstanceDetailByInstanceId(String instanceId) {
		DBObject dbObj = new BasicDBObject();
		dbObj.put(FieldMapping.INSTANCE_ID.getMongoField(), instanceId);
		return manager.findOne(dbObj, MongoTableName.CB_AUDIT_INSTANCE_DETAIL, AuditInstanceDetail.class);
	}
	
	/**
	 * findAuditInstanceDetailByClientIdInstanceId:根据clientId和instanceId查询审核信息详情
	 *
	 * @author vania
	 * 2015年11月14日 下午1:30:51
	 * @param clientId
	 * @param instanceId
	 * @return
	 *
	 */
	public AuditInstanceDetail findAuditInstanceDetailByClientIdInstanceId(String clientId, String instanceId) {
		DBObject dbObj = new BasicDBObject();
		dbObj.put(FieldMapping.CLIENT_ID.getMongoField(), clientId);
		dbObj.put(FieldMapping.INSTANCE_ID.getMongoField(), instanceId);
		return manager.findOne(dbObj, MongoTableName.CB_AUDIT_INSTANCE_DETAIL, AuditInstanceDetail.class);
	}

	/**
	 * 根据bessId查询最近一个审核信息详情
	 * 
	 * @Title: findAuditInstanceDetailByBessId
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param bessId
	 * @return
	 * @return BizInstanceAttr 返回类型
	 * @throws
	 */
	public AuditInstanceDetail findAuditInstanceDetailByBessId(String bessId) {
		DBObject dbObj = new BasicDBObject();
		dbObj.put(FieldMapping.BESS_ID.getMongoField(), bessId);
		return manager.findOne(dbObj, MongoTableName.CB_AUDIT_INSTANCE_DETAIL, AuditInstanceDetail.class);
	}

	/**
	 * 查询属性信息详情
	 * 
	 * @Title: findAuditInstanceDetailByInstanceId
	 * @author vania
	 * @version 1.0
	 * @see:
	 * @param auditInstanceDetail
	 * @return
	 * @return List<BizInstanceAttr> 返回类型
	 * @throws
	 */
	public List<AuditInstanceDetail> findAuditInstanceDetail(AuditInstanceDetail auditInstanceDetail) {
		DBObject dbObj = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(auditInstanceDetail));
		BasicDBObject orderBy= new BasicDBObject(FieldMapping.CREATE_TIME.getMongoField(), -1);
		return (List<AuditInstanceDetail>) manager.findAll(dbObj, orderBy, MongoTableName.CB_AUDIT_INSTANCE_DETAIL, AuditInstanceDetail.class);
	}

	/**
	 * 查询一个属性信息详情
	 * 
	 * @Title: findOneAuditInstanceDetail
	 * @author vania
	 * @version 1.0
	 * @see:
	 * @param auditInstanceDetail
	 * @return
	 * @return AuditInstanceDetail 返回类型
	 * @throws
	 */
	public AuditInstanceDetail findOneAuditInstanceDetail(AuditInstanceDetail auditInstanceDetail) {
		DBObject dbObj = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(auditInstanceDetail));
		return manager.findOne(dbObj, MongoTableName.CB_AUDIT_INSTANCE_DETAIL, AuditInstanceDetail.class);
	}

	/**
	 * 
	 * findInstanceIdByBessId:查询最近一个bessId对应的审核流水号
	 *
	 * @author vania 2015年10月21日 下午3:09:33
	 * @return
	 *
	 */
	public String findInstanceIdByBessId(String bessId) {
		BasicDBObject result = (BasicDBObject) manager.findOne(new BasicDBObject(FieldMapping.BESS_ID.getMongoField(), bessId), new BasicDBObject(FieldMapping.INSTANCE_ID.getMongoField(), 1), MongoTableName.CB_AUDIT_INSTANCE_DETAIL);
		if (null != result) {
			return result.getString(FieldMapping.INSTANCE_ID.getMongoField());
		}
		return null;
	}

	/**
	 * 
	 * findBessDataByBessId:查询最近一个bessId对应的业务数据
	 *
	 * @author vania 2015年10月28日 上午7:51:25
	 * @param bessId
	 * @return
	 *
	 */
	public JSONObject findBessDataByBessId(String bessId) {
		BasicDBObject result = (BasicDBObject) manager.findOne(new BasicDBObject(FieldMapping.BESS_ID.getMongoField(), bessId), new BasicDBObject(FieldMapping.BESS_DATA.getMongoField(), 1), MongoTableName.CB_AUDIT_INSTANCE_DETAIL);
		if (null != result) {
			return JSONObject.parseObject(result.getString(FieldMapping.BESS_DATA.getMongoField()));
		}
		return null;
	}

	/**
	 * 
	 * findBessDataByInstanceId:查询instanceId对应的业务数据
	 *
	 * @author vania 2015年10月28日 上午7:50:52
	 * @param instanceId
	 * @return
	 *
	 */
	public JSONObject findBessDataByInstanceId(String instanceId) {
		BasicDBObject result = (BasicDBObject) manager.findOne(new BasicDBObject(FieldMapping.INSTANCE_ID.getMongoField(), instanceId), new BasicDBObject(FieldMapping.BESS_DATA.getMongoField(), 1), MongoTableName.CB_AUDIT_INSTANCE_DETAIL);
		if (null != result) {
			return JSONObject.parseObject(result.getString(FieldMapping.BESS_DATA.getMongoField()));
		}
		return null;
	}

	/**
	 * 
	 * findBessDataByInstanceId:查询instanceId集合对应的业务数据集合
	 *
	 * @author vania 2015年10月28日 上午8:01:18
	 * @param instanceId
	 * @return
	 *
	 */
	public List<DBObject> findBessDataByInstanceId(List<String> instanceId) {
		return manager.findAll(new BasicDBObject(FieldMapping.INSTANCE_ID.getMongoField(), new BasicDBObject(QueryOperators.IN, instanceId)), new BasicDBObject(FieldMapping.BESS_DATA.getMongoField(), 1).append(FieldMapping.INSTANCE_ID.getMongoField(), 1), MongoTableName.CB_AUDIT_INSTANCE_DETAIL);
	}

	/**
	 * 
	 * findBessDataByInstanceIdOfMap:查询instanceId集合对应的业务数据Map
	 *
	 * @author vania 2015年10月28日 上午8:12:57
	 * @param instanceId
	 * @return key为instanceId; value为BessData
	 *
	 */
	public Map<String, JSONObject> findBessDataByInstanceIdOfMap(List<String> instanceId) {
		Map<String, JSONObject> result = null;
		List<DBObject> list = this.findBessDataByInstanceId(instanceId);
		if (null != list && CollectionUtils.isNotEmpty(list)) {
			result = new HashMap<String, JSONObject>();
			for (DBObject dbObj : list) {
				String key = dbObj.get(FieldMapping.INSTANCE_ID.getMongoField()).toString();
				dbObj.removeField(FieldMapping.INSTANCE_ID.getMongoField()); // 把instance_id移除掉
				JSONObject value = JSONObject.parseObject(dbObj.toString()); // BessData
				result.put(key, value);
			}
		}
		return result;
	}
}
