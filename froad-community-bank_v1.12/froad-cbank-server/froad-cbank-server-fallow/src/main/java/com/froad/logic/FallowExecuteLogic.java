/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:FallowExecuteLogic.java
 * Package Name:com.froad.logic
 * Date:2015年10月14日下午7:00:12
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.logic;

import com.froad.po.ExecuteCreateInstanceReq;
import com.froad.po.ExecuteCreateInstanceRes;
import com.froad.po.ExecuteTaskReq;
import com.froad.po.ExecuteTaskRes;
import com.froad.po.Result;
import com.froad.po.mongo.AuditInstanceDetail;
import com.froad.po.mysql.HistoryInstance;
import com.froad.po.mysql.Instance;
import com.froad.thrift.vo.CreateInstanceResVo;
import com.froad.thrift.vo.OriginVo;

/**
 * ClassName:FallowExecuteLogic Reason: TODO ADD REASON. Date: 2015年10月14日
 * 下午7:00:12
 * 
 * @author vania
 * @version
 * @see
 */
public interface FallowExecuteLogic {
	
	/**
	 * 启动流程实例
	 * @param originVo   源对象信息
	 * @param processId  流程Id
	 * @param bessData   业务数据对象json对象
	 * @return
	 */
	public ExecuteCreateInstanceRes createInstance(ExecuteCreateInstanceReq req);

	/**
	 * 
	 * executeTask:执行任务
	 *
	 * @author vania
	 * 2015年10月16日 下午4:28:44
	 * @param req
	 * @return
	 *
	 */
	public ExecuteTaskRes executeTask(ExecuteTaskReq req);
	
	/**
	 * 
	 * executeTask:执行任务
	 *
	 * @author vania
	 * 2015年12月1日 上午11:16:49
	 * @param req
	 * @param instance
	 * @param historyInstance
	 * @param auditInstanceDetail
	 * @return
	 *
	 */
	public ExecuteTaskRes executeTask(ExecuteTaskReq req, Instance instance,  HistoryInstance historyInstance, AuditInstanceDetail auditInstanceDetail) ;
}
