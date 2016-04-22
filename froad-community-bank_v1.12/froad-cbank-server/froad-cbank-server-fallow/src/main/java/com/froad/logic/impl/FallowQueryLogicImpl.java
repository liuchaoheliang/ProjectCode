/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:FallowQueryLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年10月14日下午7:01:23
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.froad.db.mongo.AuditInstanceDetailMongo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.enums.FieldMapping;
import com.froad.enums.InstanceState;
import com.froad.enums.RestrictionsEnum;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.handler.HistoryInstanceHandler;
import com.froad.handler.HistoryTaskHandler;
import com.froad.handler.TaskHandler;
import com.froad.handler.impl.HistoryInstanceHandlerImpl;
import com.froad.handler.impl.HistoryTaskHandlerImpl;
import com.froad.handler.impl.TaskHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.FallowQueryLogic;
import com.froad.po.Origin;
import com.froad.po.QueryAlreadyAuitListReq;
import com.froad.po.QueryApplyAuitListReq;
import com.froad.po.QueryAttrReq;
import com.froad.po.QueryAttrRes;
import com.froad.po.QueryMongoPageRes;
import com.froad.po.QueryPendAuitCountReq;
import com.froad.po.QueryPendAuitCountRes;
import com.froad.po.QueryPendAuitListReq;
import com.froad.po.QueryTaskListRes;
import com.froad.po.QueryTaskOverviewReq;
import com.froad.po.QueryTaskOverviewRes;
import com.froad.po.QueryTaskReq;
import com.froad.po.QueryTaskRes;
import com.froad.po.Result;
import com.froad.po.mongo.AuditInstanceDetail;
import com.froad.po.mysql.HistoryInstance;
import com.froad.po.mysql.HistoryTask;
import com.froad.po.mysql.Task;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.froad.util.PramasUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * ClassName:FallowQueryLogicImpl Reason:  ADD REASON. Date: 2015年10月14日
 * 下午7:01:23
 * 
 * @author wm
 * @version
 * @see
 */
public class FallowQueryLogicImpl implements FallowQueryLogic {

	private TaskHandler taskHandler = new TaskHandlerImpl();
	private HistoryTaskHandler HistoryTaskHandler = new HistoryTaskHandlerImpl();
	private HistoryInstanceHandler historyInstanceHandler = new HistoryInstanceHandlerImpl();
	private AuditInstanceDetailMongo bizInstanceAttrMongo = new AuditInstanceDetailMongo();

	private MongoManager manager = new MongoManager();

	@Override
	public QueryTaskOverviewRes getTaskOverview(QueryTaskOverviewReq req) {

		String instanceId = req.getInstanceId();
		LogCvt.info("查询当前审核概要信息请求参数： " + JSonUtil.toJSonString("originVo: " + req.getOrigin()) + "instanceId: " + instanceId);
		QueryTaskOverviewRes res = new QueryTaskOverviewRes();
		Result resut = new Result();
		try {
			if (StringUtils.isBlank(instanceId)) {
				throw new FroadServerException("instanceId（审核流水号）不能为空！");
			}
			Origin origin = req.getOrigin();
			String clientId = origin.getClientId();
			if (StringUtils.isBlank(clientId)) {
				throw new FroadServerException("clientId（客户端id）不能为空！");
			}

			Task task = new Task();
			task.setInstanceId(instanceId);
			task.setClientId(clientId);
			/* 获取当前审核信息 */
			Task newTask = taskHandler.findOneTask(task);

			HistoryTask ht = new HistoryTask();
			ht.setInstanceId(instanceId);
			ht.setClientId(clientId);
			/* 获取历史审核信息集合 */
			List<HistoryTask> list = HistoryTaskHandler.findHistoryTask(ht, "id DESC");
			HistoryTask newHt = null;
			if(null != list){
				/* 获取最新一条审核信息 */
				newHt = list.get(0);
				if (null == newTask && null == newHt) {
					resut.setResultCode(ResultCode.failed.getCode());
					resut.setResultDesc("未找到instanceId（审核流水号）为:" + instanceId + "的审核任务。");
					throw new FroadServerException("未找到instanceId（审核流水号）为:" + instanceId + "的审核任务。");
				}
			}
			HistoryInstance queryHistoryInstance = new HistoryInstance();
			queryHistoryInstance.setClientId(clientId);
			queryHistoryInstance.setInstanceId(instanceId);
			/* 获取流程审核实例信息 */			
//			HistoryInstance hi = historyInstanceHandler.findHistoryInstanceByInstanceId(req.getInstanceId());
			HistoryInstance hi = historyInstanceHandler.findOneHistoryInstance(queryHistoryInstance);
			if(null == hi) {
				throw new FroadServerException("未找到instanceId（审核流水号）为:" + instanceId + "的审核流水信息。");
			}
			res.setAuditState(newHt.getAuditState());
			res.setCreateTime(hi.getCreateTime());
			res.setCreator(hi.getCreator());
			res.setInstanceId(instanceId);
			res.setOrgId(hi.getOrgId());
			/* 获取流程业务信息对象 */
//			AuditInstanceDetail auditDetail = bizInstanceAttrMongo.findAuditInstanceDetailByInstanceId(req.getInstanceId());
			AuditInstanceDetail auditDetail = bizInstanceAttrMongo.findAuditInstanceDetailByClientIdInstanceId(clientId, instanceId);
			if(null != auditDetail){
				res.setBessId(auditDetail.getBessId());
			}

			resut.setResultCode(ResultCode.success.getCode());
			resut.setResultDesc(ResultCode.success.getMsg());
		} catch (FroadServerException e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(e.getMessage());
			LogCvt.error("查询当前审核概要信息失败！原因:" + e.getMessage(), e);
		} catch (Exception e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc("查询当前审核概要信息失败");
			LogCvt.error("查询当前审核概要信息[系统异常]！原因:" + e.getMessage(), e);
		}
		res.setResult(resut);
		return res;
	}

	@Override
	public QueryTaskRes getTaskList(QueryTaskReq req) {

		LogCvt.info("查询审核任务信息列表接口信息请求参数:" + JSonUtil.toJSonString(req));

		Result resut = new Result();
		QueryTaskRes res = new QueryTaskRes();
		try {
			if (StringUtils.isBlank(req.getInstanceId())) {
				throw new FroadServerException("instanceId（审核流水号）不能为空！");
			}
			Origin origin = req.getOrigin();
			List<QueryTaskListRes> htaskList = new ArrayList<QueryTaskListRes>();

			/* 获取流程审核实例信息 */
//			HistoryInstance hi = historyInstanceHandler.findHistoryInstanceByInstanceId(req.getInstanceId());
//			if (null == hi) {
//				throw new FroadServerException("未找到instanceId（审核流水号）为:" + req.getInstanceId() + "的流程审核实例信息。");
//			}

			HistoryTask ht = new HistoryTask();
			ht.setInstanceId(req.getInstanceId());
			ht.setClientId(origin.getClientId());
			/* 获取历史任务信息信息 */
//			List<HistoryTask> list = HistoryTaskHandler.findHistoryTask(ht, "create_time");
			List<HistoryTask> list = HistoryTaskHandler.findHistoryTaskOfExecute(ht);
			if (!CollectionUtils.isEmpty(list)) {
				for (HistoryTask nht : list) {
					QueryTaskListRes htask = new QueryTaskListRes();
					htask.setAuditor(nht.getOperator());
					htask.setAuditState(nht.getAuditState());
					htask.setAuditTime(nht.getFinishTime() != null ? nht.getFinishTime().getTime() : null);
					htask.setCreateTime(nht.getCreateTime().getTime());
					htask.setOrgId(nht.getOrgId());
					htask.setRemark(nht.getRemark());
					htask.setTaskId(nht.getTaskId());
					htaskList.add(htask);
				}
			}
			res.setTaskListRes(htaskList);
			resut.setResultCode(ResultCode.success.getCode());
			resut.setResultDesc(ResultCode.success.getMsg());
		} catch (FroadServerException e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(e.getMessage());
			LogCvt.error("查询审核任务信息列表失败！原因:" + e.getMessage(), e);
		} catch (Exception e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc("查询审核任务信息列表失败");
			LogCvt.error("查询审核任务信息列表[系统异常]！原因:" + e.getMessage(), e);
		}
		res.setResult(resut);
		return res;
	}

	@Override
	public QueryAttrRes getInstanceIdByAttr(QueryAttrReq req) {
		Result resut = new Result();
		QueryAttrRes res = new QueryAttrRes();
		try {
			Origin origin = req.getOrigin();
			if (null == origin) {
				throw new FroadServerException("源对象origin不能为空！");
			}
			if (StringUtils.isBlank(origin.getClientId())) {
				throw new FroadServerException("客户端ID:clientId不能为空！");
			}
			if (StringUtils.isBlank(req.getBessId())) {
				throw new FroadServerException("业务Id(商户、门店、商品)ID:bessId不能为空！");
			}
			AuditInstanceDetail auditDetail = new AuditInstanceDetail();
			auditDetail.setClientId(origin.getClientId());
			auditDetail.setBessId(req.getBessId());
			
			List<AuditInstanceDetail> bizAttrList = bizInstanceAttrMongo.findAuditInstanceDetail(auditDetail);
			if (null == bizAttrList) {
				throw new FroadServerException("未找到bessId（业务id）为:" + req.getBessId() + "的审核流水号。");
			}
			AuditInstanceDetail bizAttr = bizAttrList.get(0);
//			if(bizAttrList.size()>=2){
//				bizAttr = bizAttrList.get((bizAttrList.size()-1));
//			}else{
//				bizAttr = bizAttrList.get(0);
//			}
			res.setInstanceId(bizAttr.getInstanceId());
			res.setProcessTypeDetail(bizAttr.getProcessTypeDetail());
			
			resut.setResultCode(ResultCode.success.getCode());
			resut.setResultDesc(ResultCode.success.getMsg());
		} catch (FroadServerException e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(e.getMessage());
			LogCvt.error("根据业务(商户、门店、商品)ID查询审核流水号失败！原因:" + e.getMessage(), e);
		} catch (Exception e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc("根据业务(商户、门店、商品)ID查询审核流水号失败!");
			LogCvt.error("根据业务(商户、门店、商品)ID查询审核流水号[系统异常]！原因:" + e.getMessage(), e);
		}
		res.setResult(resut);
		return res;
	}

	@Override
	public QueryMongoPageRes getPendAuitListByPage(MongoPage page, QueryPendAuitListReq req) {
		QueryMongoPageRes mongoRes = new QueryMongoPageRes();
		Result resut = new Result();
		try {
			/********************** 操作Mongodb数据库 **********************/
//			String bessData = req.getBessData();
			Origin origin = req.getOrigin();
			if (null == origin) {
				throw new FroadServerException("请求源对象(origin)不能为空！");
			}
			if (StringUtils.isBlank(origin.getOrgId())) {
				throw new FroadServerException("操作人所在OrgId不能为空！");
			}
			if (StringUtils.isBlank(req.getProcessType())) {
				throw new FroadServerException("请求审核业务类型（processType）不能为空！");
			}

			DBObject dbObject = new BasicDBObject();
			buildBessDataQuery(req.getAndBessData(),req.getOrBessData(), dbObject);

			dbObject.put(FieldMapping.PROCESS_TYPE.getMongoField(), req.getProcessType());

			if (StringUtils.isNotBlank(req.getProcessTypeDetail())) {
				dbObject.put(FieldMapping.PROCESS_TYPE_DETAIL.getMongoField(), req.getProcessTypeDetail());
			}

			if (StringUtils.isNotBlank(origin.getClientId())) {
				dbObject.put(FieldMapping.CLIENT_ID.getMongoField(), origin.getClientId());
			}
			if (StringUtils.isNotBlank(origin.getOrgId())) {
				dbObject.put("next_actor.org_id", origin.getOrgId());
			}
			if(StringUtils.isNotBlank(req.getUserOrgCode())){
				String userOrgCode = req.getUserOrgCode();
				if(userOrgCode.toString().indexOf(",")!=-1){
					List<DBObject> dbUserOrgList = new ArrayList<DBObject>();
					String[] arr = userOrgCode.toString().split(",");
			        for(int i = 0; i < arr.length; i++){
			        	dbUserOrgList.add(new BasicDBObject("bess_data.userOrgCode", arr[i]));
			        }
			        dbObject.put(QueryOperators.OR, dbUserOrgList);
				}
			}
			dbObject.put(FieldMapping.INSTANCE_STATE.getMongoField(), InstanceState.ACTIVE.getCode());// 审核中
			if(req.getProcessType().equals("3")){
				if (null != req.getStarTime() && null != req.getEndTime()) {
					dbObject.put("bess_data.startTime", new BasicDBObject(QueryOperators.GTE, req.getStarTime()));
					dbObject.put("bess_data.endTime", new BasicDBObject(QueryOperators.LTE, req.getEndTime()));
				}
			}else{
				if (null != req.getStarTime() && null != req.getEndTime()) {
					dbObject.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject(QueryOperators.GTE, req.getStarTime()).append(QueryOperators.LTE, req.getEndTime()));
				}
			}
			/** 获取业务数据分页 */
			page = manager.findByPageWithRedis(page, dbObject, MongoTableName.CB_AUDIT_INSTANCE_DETAIL, AuditInstanceDetail.class);
		} catch (FroadServerException e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(e.getMessage());
			LogCvt.error("查询【待办箱】getPendAuitListByPage失败！原因:" + e.getMessage(), e);
		} catch (Exception e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(ResultCode.failed.getMsg());
			LogCvt.error("查询【待办箱】getPendAuitListByPage失败，原因:" + e.getMessage(), e);
		}
		mongoRes.setResult(resut);
		mongoRes.setPage(page);
		return mongoRes;
	}

	/**
	 * 
	 * buildBessDataQuery:构建bessData查询参数
	 *
	 * @author vania 2015年11月9日 下午2:51:07
	 * @param bessData
	 * @param dbObject
	 *
	 */
	private void buildBessDataQuery(String bessData, DBObject dbObject) {
		if (StringUtils.isNotBlank(bessData)) {
			try {
				Map<String, Object> map = (Map<String, Object>) JSON.parse(bessData);
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object value = map.get(key);
					if (null != value)
						if ((value instanceof String && StringUtils.isNotBlank((String) value)) && (!("license").equals(key) && !("outletId").equals(key))) {
							Pattern pattern = Pattern.compile("^.*" + value + ".*$", Pattern.CASE_INSENSITIVE);
							dbObject.put("bess_data." + key, pattern);
						} else {
							dbObject.put("bess_data." + key, value);
						}
				}
			} catch (JSONException e) {
				throw new FroadServerException("bessData 类型不是json对象！");
			}
		}
	}
	private BasicDBObject buildBessDataQuery(RestrictionsEnum oper, String bessData) {
		BasicDBObject dbObject = null; 
		if (StringUtils.isNotBlank(bessData)) {
			dbObject = new BasicDBObject();
			try {
				Map<String, Object> map = (Map<String, Object>) JSON.parse(bessData);
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object value = map.get(key);
					switch (oper) {
					case EQ:
						dbObject.append("bess_data." + key, value);
						break;
					case NE:
						dbObject.append("bess_data." + key, new BasicDBObject(QueryOperators.NE, value));
						break;
					case GT:
						dbObject.append("bess_data." + key, new BasicDBObject(QueryOperators.GT, value));
						break;
					case GTE:
						dbObject.append("bess_data." + key, new BasicDBObject(QueryOperators.GTE, value));
						break;
					case LT:
						dbObject.append("bess_data." + key, new BasicDBObject(QueryOperators.LT, value));
						break;
					case LTE:
						dbObject.append("bess_data." + key, new BasicDBObject(QueryOperators.LTE, value));
						break;
					case IN:
						dbObject.append("bess_data." + key, new BasicDBObject(QueryOperators.IN, value));
						break;
					case NIN:
						dbObject.append("bess_data." + key, new BasicDBObject(QueryOperators.NIN, value));
						break;
					case LIKE:
						if ((value instanceof String && StringUtils.isNotBlank((String) value)) && (!("license").equals(key) && !("outletId").equals(key))) {
							Pattern pattern = Pattern.compile("^.*" + value + ".*$", Pattern.CASE_INSENSITIVE);
							dbObject.put("bess_data." + key, pattern);
						}
						break;
					case LLIKE:
						if ((value instanceof String && StringUtils.isNotBlank((String) value)) && (!("license").equals(key) && !("outletId").equals(key))) {
							Pattern pattern = Pattern.compile("^.*" + value, Pattern.CASE_INSENSITIVE);
							dbObject.put("bess_data." + key, pattern);
						}
						break;
					case RLIKE:
						if ((value instanceof String && StringUtils.isNotBlank((String) value)) && (!("license").equals(key) && !("outletId").equals(key))) {
							Pattern pattern = Pattern.compile(value + ".*", Pattern.CASE_INSENSITIVE);
							dbObject.put("bess_data." + key, pattern);
						}
						break;
					case ISNULL:
						dbObject.append("bess_data." + key, null);
						break;
					case ISNOTNULL:
						dbObject.append("bess_data." + key, new BasicDBObject(QueryOperators.NE, null));
						break;
					case EXISTS:
						dbObject.append("bess_data." + key, new BasicDBObject(QueryOperators.EXISTS, value));
						break;

					default:
						break;
					}
					
				}
			} catch (JSONException e) {
				throw new FroadServerException("bessData 类型不是json对象！");
			}
		}
		return dbObject;
	}

	private void buildBessDataQuery(Map<RestrictionsEnum, String> andCondition, Map<RestrictionsEnum, String> orCondition, DBObject dbObject) {
		if (MapUtils.isNotEmpty(andCondition)) {
			BasicDBList and = (BasicDBList) dbObject.get(QueryOperators.AND);
			if (null == and) 
				and = new BasicDBList();
			
			for (Map.Entry<RestrictionsEnum, String> kv : andCondition.entrySet()) {
				RestrictionsEnum oper = kv.getKey();
				String bessData = kv.getValue();
				and.add(buildBessDataQuery(oper, bessData));
			}
			if(!and.isEmpty())
				dbObject.put(QueryOperators.AND, and);
		}
		if (MapUtils.isNotEmpty(orCondition)) {
			BasicDBList or = (BasicDBList) dbObject.get(QueryOperators.OR);
			if (null == or) 
				or = new BasicDBList();
			List<DBObject> dbOrList = new ArrayList<DBObject>();
			String bessData="";
			for (Map.Entry<RestrictionsEnum, String> kv : orCondition.entrySet()) {
				bessData = kv.getValue();
				Map<String, Object> map = (Map<String, Object>) JSON.parse(bessData);
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object value = map.get(key);
						if(key.indexOf("merchantFullName")!=-1){
							String newValue = PramasUtil.escapeExprSpecialWord(value.toString());
							Pattern pattern = Pattern.compile("^.*" + newValue + ".*$", Pattern.CASE_INSENSITIVE);
							dbOrList.add(new BasicDBObject().append(QueryOperators.OR,new BasicDBObject[] { new BasicDBObject("bess_data.merchantName", pattern), new BasicDBObject("bess_data.merchantFullName", pattern) }));
						}
						if(key.indexOf("productFullName")!=-1){
							String newValue = PramasUtil.escapeExprSpecialWord(value.toString());
							Pattern pattern = Pattern.compile("^.*" + newValue + ".*$", Pattern.CASE_INSENSITIVE);
							dbOrList.add(new BasicDBObject().append(QueryOperators.OR,new BasicDBObject[] { new BasicDBObject("bess_data.productName", pattern), new BasicDBObject("bess_data.productFullName", pattern) }));	
						}
						if(key.indexOf("outletFullName")!=-1){
							String newValue = PramasUtil.escapeExprSpecialWord(value.toString());
							Pattern pattern = Pattern.compile("^.*" + newValue + ".*$", Pattern.CASE_INSENSITIVE);
							dbOrList.add(new BasicDBObject().append(QueryOperators.OR,new BasicDBObject[] { new BasicDBObject("bess_data.outletName", pattern), new BasicDBObject("bess_data.outletFullName", pattern) }));
						}
						
						if(key.indexOf("categoryInfo")!=-1){
							Pattern pattern = Pattern.compile("^.*" + value + ".*$", Pattern.CASE_INSENSITIVE);
							dbOrList.add(new BasicDBObject().append("bess_data.categoryInfo", pattern));
						}
					
				}
			}
			if (!dbOrList.isEmpty())
				dbObject.put(QueryOperators.AND, dbOrList);
		}
	}

	@Override
	public QueryPendAuitCountRes getPendAuitCount(QueryPendAuitCountReq req) {
		QueryPendAuitCountRes mongoRes = new QueryPendAuitCountRes();
		Result resut = new Result();
		try {
			/********************** 操作Mongodb数据库 **********************/
//			String bessData = req.getBessData();
			Origin origin = req.getOrigin();
			if (null == origin) {
				throw new FroadServerException("请求源对象(origin)不能为空！");
			}
			if (StringUtils.isBlank(origin.getOrgId())) {
				throw new FroadServerException("操作人所在OrgId不能为空！");
			}
			if (StringUtils.isBlank(req.getProcessType())) {
				throw new FroadServerException("请求审核业务类型（processType）不能为空！");
			}

			DBObject dbObject = new BasicDBObject();

			buildBessDataQuery(req.getAndBessData(),req.getOrBessData(), dbObject);

			dbObject.put(FieldMapping.PROCESS_TYPE.getMongoField(), req.getProcessType());

			if (StringUtils.isNotBlank(req.getProcessTypeDetail())) {
				dbObject.put(FieldMapping.PROCESS_TYPE_DETAIL.getMongoField(), req.getProcessTypeDetail());
			}

			if (StringUtils.isNotBlank(origin.getClientId())) {
				dbObject.put(FieldMapping.CLIENT_ID.getMongoField(), origin.getClientId());
			}
			if (StringUtils.isNotBlank(origin.getOrgId())) {
				dbObject.put("next_actor.org_id", origin.getOrgId());
			}
			dbObject.put(FieldMapping.INSTANCE_STATE.getMongoField(), InstanceState.ACTIVE.getCode());// 审核中
			if (null != req.getStarTime() && null != req.getEndTime()) {
				dbObject.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject(QueryOperators.GTE, req.getStarTime()).append(QueryOperators.LTE, req.getEndTime()));
			}
			int count = manager.getCount(dbObject, MongoTableName.CB_AUDIT_INSTANCE_DETAIL);
			mongoRes.setCount(count);
		} catch (FroadServerException e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(e.getMessage());
			LogCvt.error("查询【待审核数量】getPendAuitCount失败！原因:" + e.getMessage(), e);
		} catch (Exception e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(ResultCode.failed.getMsg());
			LogCvt.error("查询【待审核数量】getPendAuitCount失败，原因:" + e.getMessage(), e);
		}
		mongoRes.setResult(resut);
		return mongoRes;
	}

	@Override
	public QueryMongoPageRes getApplyAuitListByPage(MongoPage page, QueryApplyAuitListReq req) {
		QueryMongoPageRes mongoRes = new QueryMongoPageRes();
		Result resut = new Result();
		try {
			/********************** 操作Mongodb数据库 **********************/
//			String bessData = req.getBessData();
			Origin origin = req.getOrigin();
			if (null == origin) {
				throw new FroadServerException("请求源对象(origin)不能为空！");
			}
			if (StringUtils.isBlank(req.getProcessType())) {
				throw new FroadServerException("请求审核业务类型（processType）不能为空！");
			}
			DBObject dbObject = new BasicDBObject();
			buildBessDataQuery(req.getAndBessData(),req.getOrBessData(), dbObject);
			
			if (StringUtils.isNotBlank(req.getInstanceId())) {
				dbObject.put(FieldMapping.INSTANCE_ID.getMongoField(), req.getInstanceId());
			}
			if (StringUtils.isNotBlank(origin.getClientId())) {
				dbObject.put(FieldMapping.CLIENT_ID.getMongoField(), origin.getClientId());
			}
			dbObject.put(FieldMapping.PROCESS_TYPE.getMongoField(), req.getProcessType());
			if (StringUtils.isNotBlank(req.getProcessTypeDetail())) {
				dbObject.put(FieldMapping.PROCESS_TYPE_DETAIL.getMongoField(), req.getProcessTypeDetail());
			}
//			if (StringUtils.isNotBlank(origin.getOrgId().toString())) {
//				String orgId = origin.getOrgId();
//				List<DBObject> dbList = new ArrayList<DBObject>();
//				dbList.add(new BasicDBObject("create_actor.org_id", orgId));
//				dbList.add(new BasicDBObject("next_actor.org_id", orgId));
//				dbObject.put(QueryOperators.OR, dbList);
//			} else {
//				dbObject.put("create_actor.actor_id", origin.getOperatorId());
//			}
			String orgId = origin.getOrgId();
			List<DBObject> dbList = new ArrayList<DBObject>();
			
			String auditOrgCode = req.getAuditOrgCode();
			//如果根据机构查询
			if(StringUtils.isBlank(auditOrgCode)){
				dbList.add(new BasicDBObject("next_actor.pro_org_code", orgId));
				dbList.add(new BasicDBObject("next_actor.city_org_code", orgId));
				dbList.add(new BasicDBObject("next_actor.county_org_code", orgId));
				dbList.add(new BasicDBObject("next_actor.org_code", orgId));
				dbList.add(new BasicDBObject("next_actor.org_id", orgId));
				dbList.add(new BasicDBObject("create_actor.org_code", orgId));
			}
//			else{
//				String[] arr = auditOrgCode.split(",");
//				BasicDBObject prodb = new BasicDBObject("next_actor.pro_org_code",new BasicDBObject(QueryOperators.IN, arr));
//				dbList.add(prodb);
//				BasicDBObject citydb = new BasicDBObject("next_actor.city_org_code",new BasicDBObject(QueryOperators.IN, arr));
//				dbList.add(citydb);
//				BasicDBObject countydb = new BasicDBObject("next_actor.county_org_code",new BasicDBObject(QueryOperators.IN, arr));
//				dbList.add(countydb);
//				BasicDBObject orgdb = new BasicDBObject("next_actor.org_code",new BasicDBObject(QueryOperators.IN, arr));
//				dbList.add(orgdb);
//				BasicDBObject orgIddb = new BasicDBObject("next_actor.org_id",new BasicDBObject(QueryOperators.IN, arr));
//				dbList.add(orgIddb);
//			}
			
			dbObject.put(QueryOperators.OR, dbList);
			
			dbObject.put(FieldMapping.INSTANCE_STATE.getMongoField(), InstanceState.ACTIVE.getCode());// 审核中
			if (null != req.getStarTime() && null != req.getEndTime()) {
				dbObject.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject(QueryOperators.GTE, req.getStarTime()).append(QueryOperators.LTE, req.getEndTime()));
			}
			/** 获取业务数据分页 */
			page = manager.findByPageWithRedis(page, dbObject, MongoTableName.CB_AUDIT_INSTANCE_DETAIL, AuditInstanceDetail.class);
		} catch (FroadServerException e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(e.getMessage());
			LogCvt.error("查询【待办箱】getPendAuitListByPage失败！原因:" + e.getMessage(), e);
		} catch (Exception e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(ResultCode.failed.getMsg());
			LogCvt.error("查询【待办箱】getPendAuitListByPage失败，原因:" + e.getMessage(), e);
		}
		mongoRes.setResult(resut);
		mongoRes.setPage(page);
		return mongoRes;

	}

	@Override
	public QueryMongoPageRes getAlreadyAuitListByPage(MongoPage page, QueryAlreadyAuitListReq req) {
		QueryMongoPageRes mongoRes = new QueryMongoPageRes();
		Result resut = new Result();
		try {
			/********************** 操作Mongodb数据库 **********************/
//			String bessData = req.getBessData();
			Origin origin = req.getOrigin();

			if (null == origin) {
				throw new FroadServerException("请求源对象(origin)不能为空！");
			}
			if (StringUtils.isBlank(req.getProcessType())) {
				throw new FroadServerException("请求审核业务类型（processType）不能为空！");
			}
			DBObject dbObject = new BasicDBObject();

			buildBessDataQuery(req.getAndBessData(),req.getOrBessData(), dbObject);
			
			String type = req.getType(); // 查询类型：1:查在途 2:查归档

			if (StringUtils.isNotBlank(origin.getClientId())) {
				dbObject.put(FieldMapping.CLIENT_ID.getMongoField(), origin.getClientId());
			}
			if (StringUtils.isNotBlank(req.getProcessType())) {
				dbObject.put(FieldMapping.PROCESS_TYPE.getMongoField(), req.getProcessType());
			}
			Sort sort = new Sort(FieldMapping.CREATE_TIME.getMongoField(), OrderBy.DESC);
			String actor = "";
			if (StringUtils.isNotBlank(req.getProcessType())) {
				// 查询类型：1:查在途 2:查归档
				if ("1".equals(type)) {
					actor = "next_actor";
					dbObject.put(FieldMapping.INSTANCE_STATE.getMongoField(), InstanceState.ACTIVE.getCode());// 审核中
				} else if ("2".equals(type)) {
					actor = "audit_actor.0";
					sort = new Sort(FieldMapping.FINISH_TIME.getMongoField(), OrderBy.DESC); // 根据完成时间排序
					dbObject.put(FieldMapping.INSTANCE_STATE.getMongoField(), InstanceState.FINISH.getCode());// 审核完成
				}
			} else {
				throw new FroadServerException("查在途or查归档,二者必选其一，！");
			}
			
			//C.数据权限：根据当前登录用户所能查看范围与审核单机构匹配；商户审核则为创建机构，门店审核则为一级审核机构。
			String orgId = origin.getOrgId();
			List<DBObject> dbList = new ArrayList<DBObject>();
//			if(StringUtils.equals("2", req.getProcessType()) && StringUtils.isNotBlank(orgId.toString())){
//				dbList.add(new BasicDBObject("audit_actor.0.org_id", orgId));
//				dbList.add(new BasicDBObject("next_actor.org_id", orgId));
//				dbObject.put(QueryOperators.OR, dbList);
//			} else if(StringUtils.equals("1", req.getProcessType()) && StringUtils.isNotBlank(origin.getOrgId().toString())){
//				dbList.add(new BasicDBObject("create_actor.org_id", orgId));
//				dbList.add(new BasicDBObject("audit_actor.0.org_id", orgId));
//				dbList.add(new BasicDBObject("next_actor.org_id", orgId));
//				dbObject.put(QueryOperators.OR, dbList);
//			}
			
			String auditOrgCode = req.getAuditOrgCode();
			//如果根据机构查询
			if(StringUtils.isBlank(auditOrgCode)){
				dbList.add(new BasicDBObject(actor+".pro_org_code", orgId));
				dbList.add(new BasicDBObject(actor+".city_org_code", orgId));
				dbList.add(new BasicDBObject(actor+".county_org_code", orgId));
				dbList.add(new BasicDBObject(actor+".org_code", orgId));
				dbList.add(new BasicDBObject(actor+".org_id", orgId));
				dbList.add(new BasicDBObject("create_actor.org_code", orgId));
			}else{
				String[] arr = auditOrgCode.split(",");
				BasicDBObject prodb = new BasicDBObject(actor+".pro_org_code",new BasicDBObject(QueryOperators.IN, arr));
				dbList.add(prodb);
				BasicDBObject citydb = new BasicDBObject(actor+".city_org_code",new BasicDBObject(QueryOperators.IN, arr));
				dbList.add(citydb);
				BasicDBObject countydb = new BasicDBObject(actor+".county_org_code",new BasicDBObject(QueryOperators.IN, arr));
				dbList.add(countydb);
				BasicDBObject orgdb = new BasicDBObject(actor+".org_code",new BasicDBObject(QueryOperators.IN, arr));
				dbList.add(orgdb);
				BasicDBObject orgIddb = new BasicDBObject(actor+".org_id",new BasicDBObject(QueryOperators.IN, arr));
				dbList.add(orgIddb);
				BasicDBObject creatdb = new BasicDBObject("create_actor.org_code",new BasicDBObject(QueryOperators.IN, arr));
				dbList.add(creatdb);
			}
			
			dbObject.put(QueryOperators.OR, dbList);

			if (StringUtils.isNotBlank(req.getInstanceId())) {
				dbObject.put(FieldMapping.INSTANCE_ID.getMongoField(), req.getInstanceId());
			}
			if (null != req.getStarTime() && null != req.getEndTime()) {
				dbObject.put(FieldMapping.CREATE_TIME.getMongoField(), new BasicDBObject(QueryOperators.GTE, req.getStarTime()).append(QueryOperators.LTE, req.getEndTime()));
			}
			if (null != req.getFinishStarTime() && null != req.getFinishEndTime()) {
				dbObject.put(FieldMapping.FINISH_TIME.getMongoField(), new BasicDBObject(QueryOperators.GTE, req.getFinishStarTime()).append(QueryOperators.LTE, req.getFinishEndTime()));
			}
			String auditState = req.getAuditState();
			if(StringUtils.isNotBlank(auditState)) {
				// 查询类型：1:查在途 2:查归档
				if ("1".equals(type)) {
					dbObject.put(FieldMapping.INSTANCE_STATE.getMongoField(), InstanceState.ACTIVE.getCode());// 活动
				} else {
					dbObject.put(FieldMapping.INSTANCE_STATE.getMongoField(), InstanceState.FINISH.getCode());// 审核完成
				}
				dbObject.put(FieldMapping.AUDIT_STATE.getMongoField(), auditState);// 审核状态
			}
			/** 获取业务数据分页 */
//			page = manager.findByPageWithRedis(page, dbObject, MongoTableName.CB_AUDIT_INSTANCE_DETAIL, AuditInstanceDetail.class);
			
			page.setSort(sort);
			page = manager.findByPageWithSortObject(page, dbObject, new BasicDBObject(), MongoTableName.CB_AUDIT_INSTANCE_DETAIL, AuditInstanceDetail.class);
		} catch (FroadServerException e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(e.getMessage());
			LogCvt.error("查询【待办箱】getPendAuitListByPage失败！原因:" + e.getMessage(), e);
		} catch (Exception e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(ResultCode.failed.getMsg());
			LogCvt.error("查询【待办箱】getPendAuitListByPage失败，原因:" + e.getMessage(), e);
		}
		mongoRes.setResult(resut);
		mongoRes.setPage(page);
		return mongoRes;
	}

}
