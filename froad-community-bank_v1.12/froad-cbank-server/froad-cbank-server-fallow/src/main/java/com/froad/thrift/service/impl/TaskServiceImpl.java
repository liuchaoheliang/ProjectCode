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
 * @Title: TaskImpl.java
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
import com.froad.logic.TaskLogic;
import com.froad.logic.impl.TaskLogicImpl;
import com.froad.po.mysql.Task;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.TaskService;
import com.froad.thrift.vo.TaskPageVoRes;
import com.froad.thrift.vo.TaskVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;


/**
 * 
 * <p>@Title: TaskImpl.java</p>
 * <p>Description: 实现对Task所有业务逻辑处理的外部thrift接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class TaskServiceImpl extends BizMonitorBaseService implements TaskService.Iface {
	private TaskLogic taskLogic = new TaskLogicImpl();
	public TaskServiceImpl() {}
	public TaskServiceImpl(String name, String version) {super(name, version);}


    /**
     * 增加 Task
     * @param originVo 源信息对象
     * @param taskVo
     * @return long    主键ID
     */
	@Override
	public long addTask(OriginVo originVo, TaskVo taskVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加Task，请求参数：" + JSonUtil.toJSonString(taskVo));
		Task task = (Task) BeanUtil.copyProperties(Task.class, taskVo);

		Long id = taskLogic.addTask(task); // 调用Logic添加Task

		return id;
	}



    /**
     * 删除 Task
     * @param originVo 源信息对象
     * @param taskVo
     * @return boolean    
     */
	@Override
	public boolean deleteTask(OriginVo originVo, TaskVo taskVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除Task，请求参数：" + JSonUtil.toJSonString(taskVo));
		Task task = (Task) BeanUtil.copyProperties(Task.class, taskVo);

		Boolean result = taskLogic.deleteTask(task); // 调用Logic删除Task

		return null == result ? false : result;
	}



    /**
     * 根据id删除单个 Task
     * @param originVo 源信息对象
     * @param id
     * @return TaskVo
     */
	@Override
	public boolean deleteTaskById(OriginVo originVo, long id) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id删除单个Task，请求参数：" + JSonUtil.toJSonString(id));

		Boolean result = taskLogic.deleteTaskById(id); // 调用Logic删除Task

		return null == result ? false : result;
	}



    /**
     * 根据taskId删除单个 Task
     * @param originVo 源信息对象
     * @param taskId
     * @return TaskVo
     */
	@Override
	public boolean deleteTaskByTaskId(OriginVo originVo, String taskId) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据taskId删除单个Task，请求参数：" + JSonUtil.toJSonString(taskId));

		Boolean result = taskLogic.deleteTaskByTaskId(taskId); // 调用Logic删除Task

		return null == result ? false : result;
	}



    /**
     * 修改 Task
     * @param originVo 源信息对象
     * @param taskVo
     * @return boolean    
     */
	@Override
	public boolean updateTask(OriginVo originVo, TaskVo taskVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改Task，请求参数：" + JSonUtil.toJSonString(taskVo));
		Task task = (Task) BeanUtil.copyProperties(Task.class, taskVo);

		Boolean result = taskLogic.updateTask(task); // 调用Logic修改Task

		return null == result ? false : result;
	}



    /**
     * 根据id修改单个 Task
     * @param originVo 源信息对象
     * @param id
     * @return TaskVo
     */
	@Override
	public boolean updateTaskById(OriginVo originVo, TaskVo taskVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id修改单个Task，请求参数：" + JSonUtil.toJSonString(taskVo));
		Task task = (Task) BeanUtil.copyProperties(Task.class, taskVo);

		Boolean result = taskLogic.updateTaskById(task); // 调用Logic修改Task

		return null == result ? false : result;
	}



    /**
     * 根据id修改单个 Task
     * @param originVo 源信息对象
     * @param taskId
     * @return TaskVo
     */
	@Override
	public boolean updateTaskByTaskId(OriginVo originVo, TaskVo taskVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据taskId修改单个Task，请求参数：" + JSonUtil.toJSonString(taskVo));
		Task task = (Task) BeanUtil.copyProperties(Task.class, taskVo);

		Boolean result = taskLogic.updateTaskByTaskId(task); // 调用Logic修改Task

		return null == result ? false : result;
	}



    /**
     * 根据id查询单个 Task
     * @param id
     * @return TaskVo
     */
	@Override
	public TaskVo getTaskById(long id) throws TException {
		LogCvt.info("根据id查询单个Task，请求参数：" + JSonUtil.toJSonString(id));

		Task task = taskLogic.findTaskById(id); // 调用Logic查询单个Task

		TaskVo taskVo = (TaskVo) BeanUtil.copyProperties(TaskVo.class, task);
		return taskVo;
	}



    /**
     * 根据taskId查询单个 Task
     * @param taskId
     * @return TaskVo
     */
	@Override
	public TaskVo getTaskByTaskId(String taskId) throws TException {
		LogCvt.info("根据taskId查询单个Task，请求参数：" + JSonUtil.toJSonString(taskId));

		Task task = taskLogic.findTaskByTaskId(taskId); // 调用Logic查询单个Task

		TaskVo taskVo = (TaskVo) BeanUtil.copyProperties(TaskVo.class, task);
		return taskVo;
	}



    /**
     * 根据条件查询一个 Task
     * @param taskVo
     * @return TaskVo
     */
	@Override
	public TaskVo getOneTask(TaskVo taskVo) throws TException {
		LogCvt.info("根据条件查询一个Task，请求参数：" + JSonUtil.toJSonString(taskVo));
		Task task = (Task) BeanUtil.copyProperties(Task.class, taskVo);

		task = taskLogic.findOneTask(task); // 调用Logic查询单个Task

		taskVo = (TaskVo) BeanUtil.copyProperties(TaskVo.class, task);
		return taskVo;
	}



    /**
     * 根据条件统计 Task
     * @param taskVo
     * @return int
     */
	@Override
	public int countTask(TaskVo taskVo) throws TException {
		LogCvt.info("根据条件统计Task，请求参数：" + JSonUtil.toJSonString(taskVo));
		Task task = (Task) BeanUtil.copyProperties(Task.class, taskVo);

		return taskLogic.countTask(task); // 调用Logic统计Task

	}



    /**
     * 查询 Task
     * @param taskVo
     * @return List<TaskVo>
     */
	@Override
	public List<TaskVo> getTask(TaskVo taskVo) throws TException {
		LogCvt.info("查询Task，请求参数：" + JSonUtil.toJSonString(taskVo));
		Task task = (Task) BeanUtil.copyProperties(Task.class, taskVo);

		List<Task> taskList = taskLogic.findTask(task); // 调用Logic查询Task

		List<TaskVo> taskVoList = (List<TaskVo>) BeanUtil.copyProperties(TaskVo.class, taskList);
		return taskVoList;
	}



    /**
     * 分页查询 Task
     * @param taskVo
     * @return TaskPageVoRes
     */
	@Override
	public TaskPageVoRes getTaskByPage(PageVo pageVo, TaskVo taskVo) throws TException {
		LogCvt.info("分页查询Task，请求参数：PageVo==>" + JSonUtil.toJSonString(pageVo) + "\t taskVo==>" + JSonUtil.toJSonString(taskVo));
		Page<Task> page = (Page<Task>) BeanUtil.copyProperties(Page.class, pageVo);
		Task task = (Task) BeanUtil.copyProperties(Task.class, taskVo);

		page = taskLogic.findTaskByPage(page, task); // 调用Logic分页查询Task

		List<TaskVo> taskVoList = (List<TaskVo>) BeanUtil.copyProperties(TaskVo.class, page.getResultsContent());
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);

		TaskPageVoRes taskPageVoRes = new TaskPageVoRes();
		taskPageVoRes.setPage(pageVo);
		taskPageVoRes.setTaskVoList(taskVoList);
		return taskPageVoRes;
	}


}
