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
 * 
 * @Title: TaskActorImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.List;

import org.apache.thrift.TException;
import com.alibaba.fastjson.JSON;

import com.froad.db.mysql.bean.Page;
import com.froad.logback.LogCvt;
import com.froad.logic.TaskActorLogic;
import com.froad.logic.impl.TaskActorLogicImpl;
import com.froad.po.mysql.TaskActor;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.TaskActorService;
import com.froad.thrift.vo.TaskActorPageVoRes;
import com.froad.thrift.vo.TaskActorVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;


/**
 * 
 * <p>@Title: TaskActorImpl.java</p>
 * <p>Description: 实现对TaskActor所有业务逻辑处理的外部thrift接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class TaskActorServiceImpl extends BizMonitorBaseService implements TaskActorService.Iface {
	private TaskActorLogic taskActorLogic = new TaskActorLogicImpl();
	public TaskActorServiceImpl() {}
	public TaskActorServiceImpl(String name, String version) {super(name, version);}


    /**
     * 增加 TaskActor
     * @param originVo 源信息对象
     * @param taskActorVo
     * @return String    主键ID
     */
	@Override
	public String addTaskActor(OriginVo originVo, TaskActorVo taskActorVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加TaskActor，请求参数：" + JSonUtil.toJSonString(taskActorVo));
		TaskActor taskActor = (TaskActor) BeanUtil.copyProperties(TaskActor.class, taskActorVo);

		String taskId = taskActorLogic.addTaskActor(taskActor); // 调用Logic添加TaskActor

		return taskId;
	}



    /**
     * 删除 TaskActor
     * @param originVo 源信息对象
     * @param taskActorVo
     * @return boolean    
     */
	@Override
	public boolean deleteTaskActor(OriginVo originVo, TaskActorVo taskActorVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除TaskActor，请求参数：" + JSonUtil.toJSonString(taskActorVo));
		TaskActor taskActor = (TaskActor) BeanUtil.copyProperties(TaskActor.class, taskActorVo);

		Boolean result = taskActorLogic.deleteTaskActor(taskActor); // 调用Logic删除TaskActor

		return null == result ? false : result;
	}



    /**
     * 根据taskId删除单个 TaskActor
     * @param originVo 源信息对象
     * @param taskId
     * @return TaskActorVo
     */
	@Override
	public boolean deleteTaskActorByTaskId(OriginVo originVo, String taskId) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据taskId删除单个TaskActor，请求参数：" + JSonUtil.toJSonString(taskId));

		Boolean result = taskActorLogic.deleteTaskActorByTaskId(taskId); // 调用Logic删除TaskActor

		return null == result ? false : result;
	}



    /**
     * 修改 TaskActor
     * @param originVo 源信息对象
     * @param taskActorVo
     * @return boolean    
     */
	@Override
	public boolean updateTaskActor(OriginVo originVo, TaskActorVo taskActorVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改TaskActor，请求参数：" + JSonUtil.toJSonString(taskActorVo));
		TaskActor taskActor = (TaskActor) BeanUtil.copyProperties(TaskActor.class, taskActorVo);

		Boolean result = taskActorLogic.updateTaskActor(taskActor); // 调用Logic修改TaskActor

		return null == result ? false : result;
	}



    /**
     * 根据taskId修改单个 TaskActor
     * @param originVo 源信息对象
     * @param taskId
     * @return TaskActorVo
     */
	@Override
	public boolean updateTaskActorByTaskId(OriginVo originVo, TaskActorVo taskActorVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据taskId修改单个TaskActor，请求参数：" + JSonUtil.toJSonString(taskActorVo));
		TaskActor taskActor = (TaskActor) BeanUtil.copyProperties(TaskActor.class, taskActorVo);

		Boolean result = taskActorLogic.updateTaskActorByTaskId(taskActor); // 调用Logic修改TaskActor

		return null == result ? false : result;
	}



    /**
     * 根据taskId查询单个 TaskActor
     * @param taskId
     * @return TaskActorVo
     */
	@Override
	public TaskActorVo getTaskActorByTaskId(String taskId) throws TException {
		LogCvt.info("根据taskId查询单个TaskActor，请求参数：" + JSonUtil.toJSonString(taskId));

		TaskActor taskActor = taskActorLogic.findTaskActorByTaskId(taskId); // 调用Logic查询单个TaskActor

		TaskActorVo taskActorVo = (TaskActorVo) BeanUtil.copyProperties(TaskActorVo.class, taskActor);
		return taskActorVo;
	}



    /**
     * 根据条件查询一个 TaskActor
     * @param taskActorVo
     * @return TaskActorVo
     */
	@Override
	public TaskActorVo getOneTaskActor(TaskActorVo taskActorVo) throws TException {
		LogCvt.info("根据条件查询一个TaskActor，请求参数：" + JSonUtil.toJSonString(taskActorVo));
		TaskActor taskActor = (TaskActor) BeanUtil.copyProperties(TaskActor.class, taskActorVo);

		taskActor = taskActorLogic.findOneTaskActor(taskActor); // 调用Logic查询单个TaskActor

		taskActorVo = (TaskActorVo) BeanUtil.copyProperties(TaskActorVo.class, taskActor);
		return taskActorVo;
	}



    /**
     * 根据条件统计 TaskActor
     * @param taskActorVo
     * @return int
     */
	@Override
	public int countTaskActor(TaskActorVo taskActorVo) throws TException {
		LogCvt.info("根据条件统计TaskActor，请求参数：" + JSonUtil.toJSonString(taskActorVo));
		TaskActor taskActor = (TaskActor) BeanUtil.copyProperties(TaskActor.class, taskActorVo);

		return taskActorLogic.countTaskActor(taskActor); // 调用Logic统计TaskActor

	}



    /**
     * 查询 TaskActor
     * @param taskActorVo
     * @return List<TaskActorVo>
     */
	@Override
	public List<TaskActorVo> getTaskActor(TaskActorVo taskActorVo) throws TException {
		LogCvt.info("查询TaskActor，请求参数：" + JSonUtil.toJSonString(taskActorVo));
		TaskActor taskActor = (TaskActor) BeanUtil.copyProperties(TaskActor.class, taskActorVo);

		List<TaskActor> taskActorList = taskActorLogic.findTaskActor(taskActor); // 调用Logic查询TaskActor

		List<TaskActorVo> taskActorVoList = (List<TaskActorVo>) BeanUtil.copyProperties(TaskActorVo.class, taskActorList);
		return taskActorVoList;
	}



    /**
     * 分页查询 TaskActor
     * @param taskActorVo
     * @return TaskActorPageVoRes
     */
	@Override
	public TaskActorPageVoRes getTaskActorByPage(PageVo pageVo, TaskActorVo taskActorVo) throws TException {
		LogCvt.info("分页查询TaskActor，请求参数：PageVo==>" + JSonUtil.toJSonString(pageVo) + "\t taskActorVo==>" + JSonUtil.toJSonString(taskActorVo));
		Page<TaskActor> page = (Page<TaskActor>) BeanUtil.copyProperties(Page.class, pageVo);
		TaskActor taskActor = (TaskActor) BeanUtil.copyProperties(TaskActor.class, taskActorVo);

		page = taskActorLogic.findTaskActorByPage(page, taskActor); // 调用Logic分页查询TaskActor

		List<TaskActorVo> taskActorVoList = (List<TaskActorVo>) BeanUtil.copyProperties(TaskActorVo.class, page.getResultsContent());
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);

		TaskActorPageVoRes taskActorPageVoRes = new TaskActorPageVoRes();
		taskActorPageVoRes.setPage(pageVo);
		taskActorPageVoRes.setTaskActorVoList(taskActorVoList);
		return taskActorPageVoRes;
	}


}
