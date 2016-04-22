/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:FallowExecuteLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年10月14日下午7:01:03
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.db.mongo.AuditInstanceDetailMongo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.redis.AuditRedis;
import com.froad.enums.AuditState;
import com.froad.enums.AuditType;
import com.froad.enums.AuditTypeDetail;
import com.froad.enums.InstanceState;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.PlatTypeEnum;
import com.froad.enums.ProcessNodeType;
import com.froad.enums.ResultCode;
import com.froad.enums.TaskState;
import com.froad.exceptions.FroadServerException;
import com.froad.handler.HistoryInstanceHandler;
import com.froad.handler.HistoryTaskActorHandler;
import com.froad.handler.HistoryTaskHandler;
import com.froad.handler.InstanceHandler;
import com.froad.handler.ProcessConfigHandler;
import com.froad.handler.ProcessHandler;
import com.froad.handler.ProcessNodeHandler;
import com.froad.handler.TaskActorHandler;
import com.froad.handler.TaskHandler;
import com.froad.handler.impl.HistoryInstanceHandlerImpl;
import com.froad.handler.impl.HistoryTaskActorHandlerImpl;
import com.froad.handler.impl.HistoryTaskHandlerImpl;
import com.froad.handler.impl.InstanceHandlerImpl;
import com.froad.handler.impl.ProcessConfigHandlerImpl;
import com.froad.handler.impl.ProcessHandlerImpl;
import com.froad.handler.impl.ProcessNodeHandlerImpl;
import com.froad.handler.impl.TaskActorHandlerImpl;
import com.froad.handler.impl.TaskHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.FallowExecuteLogic;
import com.froad.po.ExecuteCreateInstanceReq;
import com.froad.po.ExecuteCreateInstanceRes;
import com.froad.po.ExecuteTaskReq;
import com.froad.po.ExecuteTaskRes;
import com.froad.po.Org;
import com.froad.po.Origin;
import com.froad.po.Result;
import com.froad.po.mongo.Actor;
import com.froad.po.mongo.AuditInstanceDetail;
import com.froad.po.mq.AuditMQ;
import com.froad.po.mysql.HistoryInstance;
import com.froad.po.mysql.HistoryTask;
import com.froad.po.mysql.HistoryTaskActor;
import com.froad.po.mysql.Instance;
import com.froad.po.mysql.Process;
import com.froad.po.mysql.ProcessConfig;
import com.froad.po.mysql.ProcessNode;
import com.froad.po.mysql.Task;
import com.froad.po.mysql.TaskActor;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.JSonUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SerialNumberUtil;
import com.froad.util.ValidatorUtil;

/**
 * ClassName:FallowExecuteLogicImpl Reason: ADD REASON. Date: 2015年10月14日
 * 下午7:01:03
 * 
 * @author vania
 * @version
 * @see
 */
public class FallowExecuteLogicImpl implements FallowExecuteLogic {
	private ProcessHandler processHandler = new ProcessHandlerImpl();
	private ProcessNodeHandler processNodeHandler = new ProcessNodeHandlerImpl();
	private InstanceHandler instanceHandler = new InstanceHandlerImpl();
	private TaskHandler taskHandler = new TaskHandlerImpl();
	private HistoryTaskHandler historyTaskHandler = new HistoryTaskHandlerImpl();
	private HistoryInstanceHandler historyInstanceHandler = new HistoryInstanceHandlerImpl();
	private HistoryTaskActorHandler historyTaskActorHandler = new HistoryTaskActorHandlerImpl();
	private TaskActorHandler taskActorHandler = new TaskActorHandlerImpl();
	private ProcessConfigHandler processConfigHandler = new ProcessConfigHandlerImpl();
	private AuditInstanceDetailMongo auditInstanceDetailMongo = new AuditInstanceDetailMongo();
	private CommonLogic commonLogic = new CommonLogicImpl();

	@Override
	public ExecuteTaskRes executeTask(ExecuteTaskReq req) {
		return executeTask(req, null, null, null);
	}
	
	@Override
	public ExecuteTaskRes executeTask(ExecuteTaskReq req, Instance instance,  HistoryInstance historyInstance, AuditInstanceDetail auditInstanceDetail) {
		ExecuteTaskRes res = new ExecuteTaskRes(); 
		Result result = new Result(ResultCode.failed);

		/********add wangyan start***/
		String operRemark = null;// 审核人填写的审核备注
		if(req!=null && Checker.isNotEmpty(req.getRemark())) {
			operRemark = req.getRemark();// 审核备注
		}
		String auditMqType = null;//AuditType.merchant || type == AuditType.outlet || type == AuditType.group_product
		/********add wangyan end***/
		
		boolean checked = false;
		try {
			this.checkExecuteTaskReq(req); // 检查请求参数
			checked = true;
		} catch (FroadServerException e) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			LogCvt.error("执行审核任务-检查请求参数失败，原因:" + e.getMessage(), e);
		} catch (Exception e) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("检查请求参数[系统异常]!");
			LogCvt.error("执行审核任务-检查请求参数[系统异常]，原因:" + e.getMessage(), e);
		} finally {
			if (!checked) {
				res.setResult(result);
				return res;
			}
		}

		Origin origin = req.getOrigin(); // 源对象
		PlatTypeEnum platType = origin.getPlatType(); // 平台代码

		Long operatorId = origin.getOperatorId(); // 审核人id
		String operatorUserName = origin.getOperatorUserName(); // 审核人用户名
		String orgId = origin.getOrgId(); // 操作人所属组织(说明:如果是个人操作则传空;如果是银行操作则传银行操作员所在orgCode;如果是商户用户操作则传商户id;如果是门店用户操作则传门店id;)
		String orgCode = req.getOrgCode(); // 所属商户的发展机构(现在不取web后台送过来的了,直接取cb_audit_instance_detail表的org_code就行了)
		String auditState = req.getAuditState(); // 审核状态:1审核通过;2审核不通过

		final StringBuffer mq_key = new StringBuffer();
		String clientId = origin.getClientId();// 客户端id
		String businessId = req.getBessId();// 业务id
		String instanceId = req.getInstanceId();// 审核实例id
		String taskId = req.getTaskId(); // 客户端上送的taskId
		String remark = req.getRemark();// 审核备注
		// String name = ""; // 任务名称
		// String displayName = ""; // 任务显示名称
		String performType = "0"; // 参与类型:0-普通,1-参与者会签
		String taskType = "0"; // 任务类型:0-主办,1-协办
		Date finishTime = null;

		boolean isStartAudit = false; // 是否是最开始审核
		boolean isFinalAudit = false; // 本次执行的审核任务是否流程定义的最后一次审核(中途审核不通过则不算终审)
		boolean isArchive = false; // 本次执行完审核任务是否归档(需要归档的情况:审核不通过,强行终止审核任务,最后一级审核)
		boolean isSendMQ = false; // 是否发送MQ消息给业务系统
		final AuditMQ mq = new AuditMQ(result, System.currentTimeMillis(), clientId, businessId, instanceId, auditState, operatorUserName, remark, isFinalAudit, isArchive, null); // 异步通知对象
		SqlSession sqlSession = null; //
		try {
			// this.checkExecuteTaskReq(req); // 检查请求参数

			if (!AuditRedis.lockInstance(clientId, instanceId)) {
				throw new FroadServerException("审核流水号为:" + instanceId + "已经有人在操作, 请稍后再试!");
			}
			
			if (null == auditInstanceDetail) // 传入的AuditInstanceDetail为空再通过clientId和instanceId查询
				auditInstanceDetail = findAuditInstanceDetail(clientId, instanceId);
			
			orgCode = auditInstanceDetail.getOrgCode(); // 所属商户的发展机构

			if(null == instance)
				instance = getInstanceByInstanceId(clientId, instanceId);
			
			isSendMQ = true; // 从这以后的都需要发送MQ消息给业务系统

			String processId = instance.getProcessId();
			Process process = this.getProcessById(clientId, processId, true);
			AuditType auditType = AuditType.getByType(process.getType());
			if (null == auditType) {
				throw new FroadServerException("不存在的审核类型:" + process.getType());
			}
			/********add wangyan start***/
			auditMqType = auditType.getType();
			/********add wangyan end***/
			
			mq_key.append(getMQkey(auditType)); // 生成MQ的key
			
			AuditTypeDetail auditTypeDetail = AuditTypeDetail.getByType(process.getTypeDetail());
			if (null == auditTypeDetail) {
				throw new FroadServerException("不存在的审核类型详情:" + process.getTypeDetail());
			}

			if (StringUtils.isNotBlank(taskId)) { // taskId不为空校验审核task
				HistoryTask historyTaskQuery = new HistoryTask();
				historyTaskQuery.setTaskId(taskId);
				HistoryTask historyTaskValid = historyTaskHandler.findOneHistoryTask(historyTaskQuery);
				if (null != historyTaskValid && StringUtils.equals(historyTaskValid.getTaskState(), TaskState.FINISH.getCode())) {// 任务状态:0-结束,1-活动
					throw new FroadServerException("审核任务id为:" + taskId + "对应的审核任务已经结束且已归档");
				}
			}

			if(null == historyInstance)
				historyInstance = getHistoryInstance(clientId, instanceId); // 现在暂时没用到这个对象

			Task currTask = null; // 当前审核任务
			Actor currActor = null;// 当前审核人信息
			Actor nextActor = null;// 下一个审核人信息

			ProcessNode currProcessNode = null; // 当前流程节点
			ProcessNode nextProcessNode = null; // 下一个流程节点

			ProcessNode processNodeQuery = new ProcessNode(); // 查询条件
			if (StringUtils.isBlank(taskId)) { // taskId为空说明是刚开始审核
				isStartAudit = true; // 刚开始审核
				processNodeQuery = new ProcessNode(); // 查询条件
				processNodeQuery.setClientId(clientId); // 客户端ID
				processNodeQuery.setType(ProcessNodeType.node_start.getCode());
				processNodeQuery.setProcessId(processId);

				currProcessNode = getProcessNode(processNodeQuery);

				currTask = new Task(new Date(), new Date(), clientId, instanceId, currProcessNode.getNodeId(), SerialNumberUtil.getTaskIdSerialNumber(), null, "开始任务", "开始任务", performType, taskType, operatorId.toString(), finishTime, orgId);
				ValidatorUtil.valid(currTask); // 校验bean
			} else { // 非开始审核任务
				currTask = findTask(clientId, taskId);
				if (null == currTask) {
					throw new FroadServerException("审核任务id:" + taskId + ", 对应的审核任务不存在");
				}
				if (StringUtils.isNotBlank(currTask.getOperator()) && !StringUtils.equals(currTask.getOperator(), operatorId.toString())) {
					throw new FroadServerException("用户非审核任务指定人");
				} else if (!StringUtils.equals(currTask.getOrgId(), orgId)) {
					throw new FroadServerException("用户所属机构或组织非审核任务对应机构或组织");
				}
				currTask.setOperator(operatorId.toString());

				currProcessNode = getProcessNode(clientId, processId, currTask.getNodeId()); // 查询当前流程节点
			}

			String logPreStr = "executeTask:【审核类型:" + auditTypeDetail.getDescribe() + "-" + auditType.getDescribe() + "】, [" + "instanceId=" + instanceId + ";taskId=" + taskId + ";bessId=" + businessId + "]";

			LogCvt.info(logPreStr + "【" + ProcessNodeType.getType(currProcessNode.getType()).getDescribe() + "】...");

			nextProcessNode = getProcessNode(clientId, processId, currProcessNode.getNextNodeId()); // 查询下一个流程节点

			isArchive = StringUtils.equals(auditState, AuditState.failAudit.getCode()); // 审核不通过需要归档
			
			if (StringUtils.equals(currProcessNode.getType(), ProcessNodeType.node_end.getCode()) || null == nextProcessNode || StringUtils.equals(nextProcessNode.getType(), ProcessNodeType.node_end.getCode())) { // 结束节点
				if (StringUtils.equals(currProcessNode.getType(), ProcessNodeType.node_start.getCode())) { // 如果当前流程是开始流程,下一流程直接是结束流程,则直接审核通过,且归档
					auditState = AuditState.passAudit.getCode();
					LogCvt.info(logPreStr + "流程一开始下一流程节点为结束节点, 默认为[" + AuditState.getType(auditState).getDescribe() + "]直接归档且结束...");
				}
				
				Process processQuery = new Process();
				processQuery.setClientId(clientId);
				processQuery.setParentProcessId(processId);
				processQuery.setStatus("1");// 状态0-禁用1-启用
				List<Process> subProcess = processHandler.findProcess(processQuery);
				if (null != subProcess) { // 还有子流程
					LogCvt.info("审核流水号:" + instanceId + "执行子流程...");
					// req.setTaskId(null); // 重置TaskId
					// this.executeTask(req);
				}

				/******************** 所有流程执行完毕-进行归档操作 ********************/
				isFinalAudit = true; // 终审
				isArchive = true; // 终审需要归档
			}
			
			currActor = new Actor(operatorId, operatorUserName, orgId, currTask.getTaskId(), auditState);// 当前审核信息
			this.setOrg(currActor, commonLogic.getOrgByOrgCode(origin.getPlatType() == PlatTypeEnum.bank ? orgId : orgCode, clientId)); // 如果是银行端登录则orgId就是本次操作人所属的机构码, 反之orgCode才是本次操作人所属商户的机构码
			
			logPreStr += "【审核状态:" + auditState + "-" + AuditState.getType(auditState).getDescribe() + "】";
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			currTask.setUpdateTime(new Date()); // 设置当前任务修改时间
			currTask.setFinishTime(new Date()); // 设置当前任务完成时间

			HistoryTask historyTask = (HistoryTask) BeanUtil.copyProperties(HistoryTask.class, currTask);
			historyTask.setAuditState(auditState);
			historyTask.setRemark(remark);
			historyTask.setFinishTime(new Date()); // 任务完成时间

			/* 修改历史审核流水 */
			HistoryInstance historyInstanceUpdate = new HistoryInstance();
			historyInstanceUpdate.setClientId(clientId);
			historyInstanceUpdate.setInstanceId(instanceId);
			historyInstanceUpdate.setUpdateTime(new Date());
			historyInstanceUpdate.setLastUpdator(operatorId.toString());

			/* 修改mongodb的审核详情 */
			AuditInstanceDetail updateAuditInstanceDetail = new AuditInstanceDetail();
			updateAuditInstanceDetail.setClientId(clientId); // 作为条件
			updateAuditInstanceDetail.setInstanceId(instanceId); // 作为条件
			updateAuditInstanceDetail.setAuditState(auditState); // 当前审核审核流水的审核状态
			if (!isStartAudit) { // 非创建审核
				List<Actor> auditActor = new ArrayList<Actor>();
				auditActor.add(currActor);
				updateAuditInstanceDetail.setAuditActor(auditActor);
			} else {
				Actor createActor = auditInstanceDetail.getCreateActor();
				createActor.setTaskId(currTask.getTaskId());
				updateAuditInstanceDetail.setCreateActor(createActor);
			}
			updateAuditInstanceDetail.setNextActor(nextActor);
			// updateAuditInstanceDetail.setAuditState(auditState); // 设置当前审核状态

			if (isArchive) { // 如果是最后一个流程节点则把历史审核流水归档
				LogCvt.info("审核流水号:" + instanceId + "执行完所有审核任务, 进行归档操作...");

				/* 修改历史审核任务 */
				historyTask.setTaskState(TaskState.FINISH.getCode()); // 任务状态:0-结束,1-活动

				/* 删除当前审核流水 */
				LogCvt.info("审核流水号:" + instanceId + "执行当前审核任务结束, 删除当前审核流水数据...");
				instanceHandler.deleteInstanceByInstanceId(sqlSession, instanceId);

				/* 修改历史审核流水 */
				historyInstanceUpdate.setInstanceState(InstanceState.FINISH.getCode()); // 实例状态:0-结束,1-活动

				/* 修改审核详情 */
				updateAuditInstanceDetail.setInstanceState(InstanceState.FINISH.getCode()); // 审核流水状态:0-结束,1-活动
				long finishTime1 = System.currentTimeMillis(); // 归档时间
				updateAuditInstanceDetail.setFinishTime(finishTime1); // 归档时间
				mq.setFinishTime(finishTime1); // 设置mq归档时间
			} else { // 非终审 还有下一个任务
				LogCvt.info("审核流水号:" + instanceId + "执行当前审核任务, 扭转到下一流程节点...");
				/* 历史审核任务 */
				historyTask.setTaskState(TaskState.ACTIVE.getCode()); // 任务状态:0-结束,1-活动

				/* 修改审核详情 */
				updateAuditInstanceDetail.setInstanceState(InstanceState.ACTIVE.getCode()); // 审核流水状态:0-结束,1-活动
				updateAuditInstanceDetail.setAuditState(AuditState.noAudit.getCode()); // 当前审核审核流水的审核状态为"待审核"

				/* 修改历史审核流水 */
				historyInstanceUpdate.setInstanceState(InstanceState.ACTIVE.getCode()); // 实例状态:0-结束,1-活动

				/* 根据流程节点生成下一个Task */
				Long nextOperatorId = nextProcessNode.getRunnerUserId(); // 执行用户ID(计算下一个任务的操作人id)
				String nextOperatorUserName = null; // 计算下一个任务的操作人用户名
				String nextOrgId = null; // 计算下一个任务的操作机构
				String currNodeNextRunnerOrg = currProcessNode.getNextRunnerOrg(); // 后续执行机构:0-自身机构系统,1-发展机构系统
				String nextNodeRunnerFlag = nextProcessNode.getRunnerFlag(); // 下一个流程节点的执行类型:0-用户,1-岗位,2-部门,3-用户组,4-机构级别
				String nextNodeRunnerOrgLevel = nextProcessNode.getRunnerOrgLevel(); // 下一个流程节点的执行机构等级
				if (StringUtils.equals("0", currNodeNextRunnerOrg)) { // 0-自身机构系统
					if (platType == PlatTypeEnum.bank) { // 如果是银行
					// nextOrgId = getNextOrgCode(orgId, clientId,
					// nextNodeRunnerOrgLevel); // 根据登录人所在的机构进行计算下一个审核的机构
						nextOrgId = getNextOrgCode(orgCode, clientId, nextNodeRunnerOrgLevel); // 所属商户的发展机构进行计算下一个审核的机构
					} else if (platType == PlatTypeEnum.merchant_h5 || platType == PlatTypeEnum.merchant_pc) { // 商户
						nextOrgId = orgId; // 根据orgId算下一次审核的机构或者部门
					}
				} else { // 1-发展机构系统(跨机构系统了)
					LogCvt.debug("下一个流程节点的RunnerOrgLevel==>" + nextNodeRunnerOrgLevel);
					if (platType == PlatTypeEnum.merchant_h5 || platType == PlatTypeEnum.merchant_pc) { // 商户
						nextOrgId = getNextOrgCode(orgCode, clientId, nextNodeRunnerOrgLevel); // 根据发展机构算下一次审核的机构
					} else { // 应该没有银行跨到商户去审核吧

					}
				}
				Task nextTask = new Task(new Date(), new Date(), clientId, instanceId, nextProcessNode.getNodeId(), SerialNumberUtil.getTaskIdSerialNumber(), currTask.getTaskId(), "审核任务", "审核任务", performType, taskType, null == nextOperatorId ? null : nextOperatorId.toString(), finishTime, nextOrgId);
				ValidatorUtil.valid(nextTask); // 校验bean

				nextActor = new Actor(nextOperatorId, nextOperatorUserName, nextOrgId, nextTask.getTaskId(), AuditState.noAudit.getCode());
				this.setOrg(nextActor, commonLogic.getOrgByOrgCode(nextOrgId, clientId)); 
				
				updateAuditInstanceDetail.setNextActor(nextActor);

				/* 插入新的当前任务表 */
				taskHandler.addTask(sqlSession, nextTask);
				res.setTaskId(nextTask.getTaskId());

				/* 插入新的当前任务参与人表 */
				// TaskActor nextTaskActor = new TaskActor(nextTask.getTaskId(),
				// nextOperator);
				// taskActorHandler.addTaskActor(nextTaskActor);
			}

			HistoryTaskActor historyTaskActor = new HistoryTaskActor(currTask.getTaskId(), operatorId.toString());
			/* 把当前表转移到历史表 */
			LogCvt.info("审核流水号:" + instanceId + "执行当前审核任务结束, 把当前任务数据转移到历史表...");
			historyTaskHandler.addHistoryTask(sqlSession, historyTask);
			LogCvt.info("审核流水号:" + instanceId + "执行当前审核任务结束, 把当前任务参与者转移到历史表...");
			historyTaskActorHandler.addHistoryTaskActor(sqlSession, historyTaskActor);

			if (!isStartAudit) { // 非首次创建任务则删除当前表
				/* 删除当前表 */
				LogCvt.info("审核流水号:" + instanceId + "执行当前审核任务结束, 删除当前任务数据...");
				Task delTask = new Task();
				delTask.setClientId(clientId);
				delTask.setTaskId(currTask.getTaskId());
				taskHandler.deleteTask(sqlSession, delTask);
//				taskHandler.deleteTaskByTaskId(sqlSession, currTask.getTaskId());
				taskActorHandler.deleteTaskActor(sqlSession, new TaskActor(currTask.getTaskId(), null));
			}
			/* 修改历史审核流水 */
//			historyInstanceHandler.updateHistoryInstanceByInstanceId(sqlSession, historyInstanceUpdate);
			historyInstanceHandler.updateHistoryInstance(sqlSession, historyInstanceUpdate);

			if (isArchive) { // 如果是最后一个流程节点则把历史审核流水归档
				/* 结束时修改instanceId所有历史task的taskState为0-结束 */
				HistoryTask updateHistoryTask = new HistoryTask();
				updateHistoryTask.setClientId(clientId); // 作为条件
				updateHistoryTask.setInstanceId(instanceId);// 作为条件
				
				updateHistoryTask.setTaskState(TaskState.FINISH.getCode()); //把所有历史审核任务修改成0-结束
				historyTaskHandler.updateHistoryTask(sqlSession, updateHistoryTask);

				/* 结束时添加结束task */
				HistoryTask endHistoryTask = (HistoryTask) BeanUtil.copyProperties(HistoryTask.class, historyTask); // 结束task
				endHistoryTask.setCreateTime(new Date());
				endHistoryTask.setUpdateTime(new Date());
				endHistoryTask.setNodeId(nextProcessNode.getNodeId());
				endHistoryTask.setParentTaskId(historyTask.getTaskId());
				endHistoryTask.setTaskId(SerialNumberUtil.getTaskIdSerialNumber());
				endHistoryTask.setName("结束任务");
				endHistoryTask.setDisplayName("结束任务");
				endHistoryTask.setRemark("结束审核任务");
				endHistoryTask.setFinishTime(new Date());

				historyTaskHandler.addHistoryTask(sqlSession, endHistoryTask); // 结束时添加结束task

				HistoryTaskActor endHistoryTaskActor = new HistoryTaskActor(endHistoryTask.getTaskId(), endHistoryTask.getOperator());// 结束时添加结束TaskActor
				historyTaskActorHandler.addHistoryTaskActor(sqlSession, endHistoryTaskActor);

				LogCvt.info("*************" +logPreStr + "【" + ProcessNodeType.getType(currProcessNode.getType()).getDescribe() + "】执行完成所有审核任务, 归档完成*************");
//				LogCvt.info("审核流水号:" + instanceId + "执行审核任务完成, 生成结束任务...");
//				LogCvt.info("*************" + logPreStr + "归档完成*************");
			}

			auditInstanceDetailMongo.updateAuditInstanceDetailByClientIdAndInstanceId(updateAuditInstanceDetail, isArchive); // 修改审核详细信息

			// 操作成功提交事务
			sqlSession.commit(true);

			result.setResult(ResultCode.success);
			isSendMQ = true; // 正常结束才发送MQ
		} catch (FroadServerException e) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			LogCvt.error("执行审核任务失败，原因:" + e.getMessage(), e);
		} catch (Exception e) {
			if (null != sqlSession)
				sqlSession.rollback(true);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("执行审核任务[系统异常]!");
			LogCvt.error("执行审核任务[系统异常]，原因:" + e.getMessage(), e);
		} finally {
			LogCvt.info("执行当前审核任务结束, 释放审核流程锁");
			AuditRedis.unLockInstance(clientId, instanceId); // 释放审核流程锁
			if (null != sqlSession)
				sqlSession.close();
			if (isSendMQ) {
				mq.setAuditState(auditState); // 重新设置审核状态
				mq.setIsFinalAudit(isFinalAudit);
				mq.setIsArchive(isArchive);
				mq.setResult(result);
				LogCvt.info("执行当前审核任务结束, MQ异步通知业务系统,MQ:"+JSON.toJSONString(mq));
				/* 异步调用redis队列通知给client */
				/********add wangyan start***/
				if(AuditType.group_product.getType().equals(auditMqType)) {
					mq.setAuditComment(operRemark);
					LogCvt.info("MQ的审核备注为审核人所填写的审核备注,MQ:"+JSON.toJSONString(mq));
				}
				/********add wangyan end***/
				AuditRedis.sentMQ(mq_key.toString(), mq);
			} else {
				LogCvt.info("执行当前审核任务结束, [isSendMQ=" + isSendMQ + "]不发送MQ给业务系统");				
			}
		}
		res.setResult(result);
		return res;
	}

	private AuditInstanceDetail findAuditInstanceDetail(String clientId, String instanceId) {
		AuditInstanceDetail auditInstanceDetail = auditInstanceDetailMongo.findAuditInstanceDetailByClientIdInstanceId(clientId, instanceId);
		if (null == auditInstanceDetail) {
			throw new FroadServerException("审核流水号为:" + instanceId + "对应的审核流水不存在");
		}
		if (StringUtils.equals(auditInstanceDetail.getInstanceState(), InstanceState.FINISH.getCode())) {
			throw new FroadServerException("审核流水号为:" + instanceId + "对应的审核流水已经结束且已归档");
		}
		return auditInstanceDetail;
	}
	
	private Task findTask(String clientId, String taskId) throws Exception{		
		Task taskQuery = new Task();
		taskQuery.setClientId(clientId);
		taskQuery.setTaskId(taskId);
		return taskHandler.findOneTask(taskQuery );
	}
	
	private void setOrg(Actor actor, Org org){
		if (null == org)
			return;
		actor.setProOrgCode(org.getProvinceAgency());// 省级机构
		actor.setCityOrgCode(org.getCityAgency());// 市级机构
		actor.setCountyOrgCode(org.getCountyAgency());// 区级机构	
		Map<OrgLevelEnum, String> map = commonLogic.setOrgMap(org); // 获取机构  对应的 1 2 3 4级机构
		for (Map.Entry<OrgLevelEnum,String> kv : map.entrySet()) {
			OrgLevelEnum orgLeveEnum = kv.getKey();
			String superOrgCode = kv.getValue();
			switch (orgLeveEnum) {
			case orgLevel_one: //1 省级机构
				actor.setProOrgCode(superOrgCode);
				break;
			case orgLevel_two: //2 市级机构
				actor.setCityOrgCode(superOrgCode);
				break;
			case orgLevel_three: //3 区级机构
				actor.setCountyOrgCode(superOrgCode);
				break;
			case orgLevel_four: //4 网点
				actor.setOrgCode(superOrgCode);
				break;
			default: // 默认
//				merchant.setOrgCode(levelOrg);
				break;
			}
		}
		actor.setOrgCode(org.getOrgCode());	
	}

	private String getNextOrgCode(String orgCode, String clientId, String nextNodeRunnerOrgLevel) {
		String nextOrgId;
		Org org = commonLogic.getOrgByOrgCode(orgCode, clientId);
		if (null == org) {
			throw new FroadServerException("客户端id为:" + clientId + ", 对应的机构代码:" + orgCode + "不存在");
		}
		if (StringUtils.equals(org.getOrgLevel(), nextNodeRunnerOrgLevel)) { // 如果本身就是该机构等级,
																				// 直接返回
			nextOrgId = org.getOrgCode();
		} else if (StringUtils.equals("1", nextNodeRunnerOrgLevel)) { // 一级org_code
			nextOrgId = org.getProvinceAgency();//
		} else if (StringUtils.equals("2", nextNodeRunnerOrgLevel)) { // 二级org_code
			nextOrgId = org.getCityAgency();//
		} else if (StringUtils.equals("3", nextNodeRunnerOrgLevel)) { // 三级org_code
			nextOrgId = org.getCountyAgency();//
		} else {
			nextOrgId = org.getOrgCode();
		}
		return nextOrgId;
	}

	/**
	 * 
	 * archiveAuditTask: 归档审核
	 *
	 * @author vania 2015年10月21日 下午8:28:10
	 *
	 */
	private void archiveAudit() {

	}

	private ProcessNode getProcessNode(ProcessNode processNodeQuery) throws FroadServerException, Exception {
		ProcessNode nextProcessNode = processNodeHandler.findOneProcessNode(processNodeQuery);
		this.checkProcessNode(nextProcessNode);
		return nextProcessNode;
	}

	private ProcessNode getProcessNode(String clientId, String processId, String nodeId) throws FroadServerException, Exception {
		ProcessNode nextProcessNode = processNodeHandler.findOneProcessNode(clientId, processId, nodeId);
		this.checkProcessNode(nextProcessNode);
		return nextProcessNode;
	}

	private void checkProcessNode(ProcessNode processNode) {
		if (null == processNode) {
			throw new FroadServerException("找不到审核流程节点");
		}
		if (StringUtils.equals(processNode.getStatus(), "0")) {
			throw new FroadServerException("流程节点id:" + processNode.getNodeId() + "已被禁用");
		}
	}

	/**
	 * 
	 * checkExecuteTaskReq:检查executeTask的请求参数
	 * 
	 * @author vania 2015年10月20日 下午5:30:46
	 * @param req
	 * 
	 */
	private void checkExecuteTaskReq(ExecuteTaskReq req) {
		Origin origin = req.getOrigin();
		if (null == origin) {
			throw new FroadServerException("源对象不能为空");
		}
		if (null == origin.getOperatorId()) {
			throw new FroadServerException("审核人id不能为空");
		}
		if (null == origin.getPlatType()) {
			throw new FroadServerException("平台代码不能为空");
		}
		if (StringUtils.isBlank(origin.getOperatorUserName())) {
			throw new FroadServerException("审核人用户名不能为空");
		}
		if (StringUtils.isBlank(origin.getClientId())) {
			throw new FroadServerException("客户端id不能为空");
		}
		if (StringUtils.isBlank(origin.getOrgId())) {
			throw new FroadServerException("审核人所属机构不能为空");
		}
		// if (StringUtils.isBlank(req.getOrgCode())) {
		// throw new FroadServerException("发展机构不能为空");
		// }
		if (StringUtils.isNotBlank(req.getTaskId())) {
			if (StringUtils.isBlank(req.getAuditState())) {
				throw new FroadServerException("审核状态不能为空");
			}
			if (!StringUtils.equals(req.getAuditState(), AuditState.passAudit.getCode()) && !StringUtils.equals(req.getAuditState(), AuditState.failAudit.getCode())) {
				throw new FroadServerException("审核状态不合法");
			}
		} else { // 默认待审核
			req.setAuditState(AuditState.noAudit.getCode());
			req.setRemark("创建审核任务");
		}
		if (StringUtils.isBlank(req.getBessId())) {
			throw new FroadServerException("业务id不能为空");
		}
		if (StringUtils.isBlank(req.getInstanceId())) {
			throw new FroadServerException("审核实例id不能为空");
		}
	}

	/**
	 * 
	 * getInstanceByInstanceId: 根据instanceId查询Instance
	 * 
	 * @author vania 2015年10月19日 下午4:37:36
	 * @param instanceId
	 * @return
	 * 
	 */
	private Instance getInstanceByInstanceId(String clientId, String instanceId) throws Exception {
		Instance instanceQuery = new Instance();
		instanceQuery.setClientId(clientId);
		instanceQuery.setInstanceId(instanceId);
		Instance instance = instanceHandler.findOneInstance(instanceQuery); // 根据审核实例id查询实例
		if (null == instance) {
			throw new FroadServerException("找不到id为:" + instanceId + "审核的实例");
		}
		if (null != instance.getExpireTime() && new Date().compareTo(instance.getExpireTime()) > 0) {
			throw new FroadServerException("审核流水号为:" + instanceId + "对应的审核流水已经过期");
		}
		return instance;
	}

	/**
	 * 
	 * getHistoryInstanceByInstanceId:根据instanceId查询HistoryInstance
	 * 
	 * @author vania 2015年10月20日 下午2:33:03
	 * @param instanceId
	 * @return
	 * @throws Exception
	 * 
	 */
	private HistoryInstance getHistoryInstance(String clientId, String instanceId) throws Exception {
		HistoryInstance historyInstanceQuery = new HistoryInstance();
		historyInstanceQuery.setClientId(clientId);
		historyInstanceQuery.setInstanceId(instanceId);
		HistoryInstance historyInstance = historyInstanceHandler.findOneHistoryInstance(historyInstanceQuery); // 根据审核实例id查询历史实例
		if (null != historyInstance && StringUtils.equals(historyInstance.getInstanceState(), InstanceState.FINISH.getCode())) {
			throw new FroadServerException("审核流水号为:" + instanceId + "对应的审核流水已经结束且已归档");
		}
		return historyInstance;
	}

	/**
	 * 
	 * getMQkey:根据审核类型获取MQ的key
	 * 
	 * @author vania 2015年10月16日 下午6:37:10
	 * @param type
	 * @return
	 * 
	 */
	private String getMQkey(AuditType type) {
		String t = "";
		if (type == AuditType.merchant || type == AuditType.outlet || type == AuditType.group_product) {
			t = type.name();
		} else {
			t = "product";
		}
		return RedisKeyUtil.cbbank_fallow_audit_mq_audit_type(t);
	}

	/**
	 * 
	 * stopTask:终止任务
	 * 
	 * @author vania 2015年10月20日 下午11:02:27
	 * 
	 */
	public void stopTask() {
	}

	/**
	 * 创建流程实例
	 */
	@Override
	public ExecuteCreateInstanceRes createInstance(ExecuteCreateInstanceReq req) {
		LogCvt.info("开始创建流程实例-->!");
		Result resut = new Result();
		Origin origin = null;
		String clientId = null;
		String processId = null;
		ExecuteCreateInstanceRes res = new ExecuteCreateInstanceRes();
		SqlSession sqlSession = null;

		boolean checked = false;
		try {
			origin = req.getOrigin();
			this.checkExecuteCreateInstanceReq(req); // 检查请求参数
			checked = true;
		} catch (FroadServerException e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(e.getMessage());
		} catch (Exception e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc("检查请求参数[系统异常]!");
		} finally {
			if (!checked) {
				res.setResult(resut);
				return res;
			}
		}

		String instanceId = null;
		try {
			clientId = origin.getClientId();
			processId = getProcessId(req, clientId); // 查询流程配置表
			LogCvt.info("--找到匹配的审核流程ID:" + processId);
			JSONObject bessDate = JSON.parseObject(req.getBessData());
			if (null == bessDate || bessDate.isEmpty()) {
				throw new FroadServerException("业务对象bessData: " + req.getBessData() + "不能为空！ ");
			}
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			LogCvt.info("创建流程实例请求参数：  " + "originVo: " + JSonUtil.toJSonString(origin) + JSON.toJSONString("processId: " + processId + "bessData: " + bessDate));

			AuditInstanceDetail auditDetail = auditInstanceDetailMongo.findAuditInstanceDetailByBessId(req.getBessId());
			// 任务状态:0-结束,1-活动
			if (null != auditDetail && StringUtils.equals(InstanceState.ACTIVE.getCode(), auditDetail.getInstanceState())) {
				throw new FroadServerException("业务id: " + req.getBessId() + ", 还在审核中不能重复重复创建审核流程！ ");
			}
			Process process = getProcessById(clientId, processId, true);

			instanceId = SerialNumberUtil.getAuditIdSerialNumber();
			res.setInstanceId(instanceId);

			Instance instance = new Instance(); // 实例对象
			instance.setCreateTime(new Date()); // 创建时间
			instance.setUpdateTime(new Date()); // 更新时间
			instance.setClientId(origin.getClientId()); // 客户端Id
			instance.setInstanceId(instanceId); // 实例Id
			instance.setProcessId(processId); // 流程Id
			instance.setCreator(origin.getOperatorId().toString()); // 创建人
			// instance.setExpireTime(""); //期望完成时间
			// instance.setLastUpdator(""); //最后一次更新时间
			instance.setOrderValue(1); // 优先级
			// instance.setVersion(version); //版本号
			LogCvt.info("实例流程对象 ：  " + JSON.toJSONString(instance));
			/* 复制历史实例对象 */
			HistoryInstance ht = (HistoryInstance) BeanUtil.copyProperties(HistoryInstance.class, instance);
			ht.setInstanceState(InstanceState.ACTIVE.getCode()); // 实例状态:0-结束,1-活动
			ht.setOrgId(origin.getOrgId()); // 创建实例所属机构代码

			/** 新增实例对象 */
			instanceHandler.addInstance(sqlSession, instance);
			/** 新增历史实例对象 */
			historyInstanceHandler.addHistoryInstance(sqlSession, ht);
			
			AuditInstanceDetail bia = new AuditInstanceDetail();			
			bia.setClientId(origin.getClientId());
			bia.setCreateTime(new Date().getTime());
			bia.setInstanceId(instanceId);
			bia.setAuditState(AuditState.noAudit.getCode());// 初始化审核流水当前的审核状态

			Actor actor = new Actor();
			actor.setActorId(origin.getOperatorId());
			actor.setActorUserName(origin.getOperatorUserName());
			actor.setOrgId(origin.getOrgId());
			this.setOrg(actor, commonLogic.getOrgByOrgCode(req.getOrgCode(), clientId)); // 如果是银行端登录则orgId就是本次操作人所属的机构码, 反之orgCode才是本次操作人所属商户的机构码
			
			bia.setCreateActor(actor);
			bia.setBessId(req.getBessId());
			bia.setProcessType(process.getType());// 流程类型:1-商户,2-门店,3-团购商品,4-预售商品
			bia.setBessData(bessDate);
			bia.setInstanceState(InstanceState.ACTIVE.getCode());// 实例状态:0-结束,1-活动
			bia.setOrgCode(req.getOrgCode()); // 发展机构
			bia.setProcessTypeDetail(process.getTypeDetail());
			/** 保存业务对象 */
			boolean boolRest = auditInstanceDetailMongo.addAuditInstanceDetail(bia);
			if (!boolRest) {
				if (null != sqlSession)
					sqlSession.rollback(true);
				resut.setResultCode(ResultCode.failed.getCode());
				resut.setResultDesc("保存业务数据异常！");
				res.setResult(resut);
				LogCvt.error("保存业务数据异常！bizInstanceAttrMongo.addBizInstanceAttr.");
				return res;
			}
			
			/** 操作成功提交事务 */
			if (null != sqlSession)
				sqlSession.commit(true);
			try {
				ExecuteTaskReq taskReq = new ExecuteTaskReq();
				taskReq.setOrigin(origin);
				taskReq.setInstanceId(instanceId);
//				taskReq.setTaskId("");
				taskReq.setBessId(req.getBessId());
//				 taskReq.setOrgCode(req.getOrgCode());
				/** 执行审核任务 */
				ExecuteTaskRes taskRes = executeTask(taskReq, instance, ht, bia); //FIXME 数据库读写数据同步问题:写完之后立刻去读由于读写还没完成同步会导致读取不到的问题. 所以直接把相关对象传进去
				if (taskRes.getResult().getResultCode().equals(ResultCode.success.getCode())) {
					resut.setResultCode(ResultCode.success.getCode());
					resut.setResultDesc(ResultCode.success.getMsg());
				} else {
					LogCvt.info("调用执行审核任务接口返回失败, 删除数据!");
					this.removeInstance(sqlSession, clientId, instanceId); // 回滚审核流水
					if (null != sqlSession)
						sqlSession.commit(true);
					resut.setResultCode(ResultCode.take_error.getCode());
					resut.setResultDesc(ResultCode.take_error.getMsg());
					LogCvt.error("创建实例接口异常；异常接口：executeTask");
				}
			} catch (Exception e) {
				LogCvt.error("调用执行审核任务接口异常, 删除数据!");
				this.removeInstance(sqlSession, clientId, instanceId); // 回滚审核流水
				if (null != sqlSession)
					sqlSession.commit(true);
//				if (null != sqlSession)
//					sqlSession.rollback(true);
				resut.setResultCode(ResultCode.failed.getCode());
				resut.setResultDesc("启动流程实例失败！");
				LogCvt.error("创建实例接口异常！[系统异常]，原因:" + e.getMessage(), e);
			}
		} catch (FroadServerException e) {
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc(e.getMessage());
			LogCvt.error("启动流程实例失败！原因:" + e.getMessage(), e);
		} catch (Exception e) {
			if (null != sqlSession)
				sqlSession.rollback(true);
			resut.setResultCode(ResultCode.failed.getCode());
			resut.setResultDesc("启动流程实例失败！");
			LogCvt.error("启动流程实例失败！[系统异常]，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		res.setResult(resut);
		LogCvt.info("创建流程实例返回参数：  " + JSON.toJSONString(res));
		return res;
	}

	/**
	 * 
	 * getProcessId:查询流程配置表的流程id
	 *
	 * @author vania
	 * 2015年11月12日 下午7:11:55
	 * @param req
	 * @param clientId
	 * @return
	 * @throws Exception
	 *
	 */
	private String getProcessId(ExecuteCreateInstanceReq req, String clientId) throws FroadServerException,Exception {
		ProcessConfig processConfigQuery = new ProcessConfig();
		processConfigQuery.setClientId(clientId);
		processConfigQuery.setType(req.getProcessType());
		processConfigQuery.setTypeDetail(req.getProcessTypeDetail());
		String orgCode = req.getOrgCode();
		Org org = commonLogic.getOrgByOrgCode(orgCode , clientId);
		if (null == org) {
			throw new FroadServerException("客户端id为:" + clientId + ", 对应的机构代码:" + orgCode + "不存在");
		}
		processConfigQuery.setOrgCodeLeve(org.getOrgLevel());
		ProcessConfig config = processConfigHandler.findOneProcessConfig(processConfigQuery);
		if (null == config) {
			throw new FroadServerException("找不到匹配的流程id！");
		}
		return config.getProcessId();
	}

	private boolean removeInstance(SqlSession sqlSession, String clientId, String instanceId) throws Exception {
		Instance is = new Instance();
		is.setClientId(clientId);
		is.setInstanceId(instanceId);
		instanceHandler.deleteInstance(sqlSession, is);
		
		HistoryInstance his = new HistoryInstance();
		his.setClientId(clientId);
		his.setInstanceId(instanceId);
		historyInstanceHandler.deleteHistoryInstance(sqlSession, his);
		
		Task tk = new Task();
		tk.setClientId(clientId);
		tk.setInstanceId(instanceId);
		taskHandler.deleteTask(sqlSession, tk);
		
		HistoryTask htk = new HistoryTask();
		htk.setClientId(clientId);
		htk.setInstanceId(instanceId);
		historyTaskHandler.deleteHistoryTask(sqlSession, htk);
		
		auditInstanceDetailMongo.removeAuditInstanceDetail(clientId, instanceId);
		return true;
	}

	/**
	 * 
	 * getProcessById:根据客户端id和流程ID查询流程信息
	 *
	 * @author wm 2015年10月29日 下午2:03:26
	 * @param clientId
	 * @param processId
	 * @param checkStatus
	 *            是否检查流程状态
	 * @return
	 * @throws Exception
	 *
	 */
	private Process getProcessById(String clientId, String processId, boolean checkStatus) throws Exception {
		Process process = processHandler.findOneProcess(clientId, processId);
		if (null == process) {
			throw new FroadServerException("未找到流程Id为: " + processId + "的流程信息！");
		}
		if (checkStatus) {
			// 状态0-禁用1-启用
			if (StringUtils.equals("0", process.getStatus())) {
				throw new FroadServerException("流程Id为: " + processId + "的流程已被禁用！");
			}
		}
		return process;
	}

	private void checkExecuteCreateInstanceReq(ExecuteCreateInstanceReq req) {
		Origin origin = req.getOrigin();
		if (null == origin) {
			throw new FroadServerException("源对象不能为空！");
		}
		if (StringUtils.isBlank(req.getBessId())) {
			throw new FroadServerException("业务Id不能为空！");
		}
		if (StringUtils.isBlank(req.getOrgCode())) {
			throw new FroadServerException("发展机构不能为空！");
		}
		if (StringUtils.isBlank(req.getProcessType())) {
			throw new FroadServerException("流程类型不能为空！");
		}
		if (StringUtils.isBlank(req.getProcessTypeDetail())) {
			throw new FroadServerException("流程类型详情不能为空！");
		}
		if (null == req.getBessData()) {
			throw new FroadServerException("业务数据对象不能为空！");
		}
		if (null == req.getOrigin().getOperatorUserName()) {
			throw new FroadServerException("[Origin]源对象的OperatorUserName【用户名】不能为空！");
		}
		if (null == req.getOrigin().getOrgId()) {
			throw new FroadServerException("[Origin]源对象的orgId【审核机构】不能为空！");
		}
		if (null == req.getOrigin().getClientId()) {
			throw new FroadServerException("客服端Id不能为空！");
		}
		if (null == origin.getOperatorId()) {
			throw new FroadServerException("审核人id不能为空");
		}
		if (null == origin.getPlatType()) {
			throw new FroadServerException("平台代码不能为空");
		}
		if (StringUtils.isBlank(origin.getOperatorUserName())) {
			throw new FroadServerException("创建人用户名不能为空");
		}
		if (StringUtils.isBlank(origin.getOperatorId().toString())) {
			throw new FroadServerException("创建人用Id不能为空");
		}
	}
}
