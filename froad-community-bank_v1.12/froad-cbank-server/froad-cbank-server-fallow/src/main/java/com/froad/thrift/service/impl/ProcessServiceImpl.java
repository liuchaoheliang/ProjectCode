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
 * @Title: ProcessImpl.java
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
import com.froad.logic.ProcessLogic;
import com.froad.logic.impl.ProcessLogicImpl;
import com.froad.po.mysql.Process;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ProcessService;
import com.froad.thrift.vo.ProcessPageVoRes;
import com.froad.thrift.vo.ProcessVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;


/**
 * 
 * <p>@Title: ProcessImpl.java</p>
 * <p>Description: 实现对Process所有业务逻辑处理的外部thrift接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProcessServiceImpl extends BizMonitorBaseService implements ProcessService.Iface {
	private ProcessLogic processLogic = new ProcessLogicImpl();
	public ProcessServiceImpl() {}
	public ProcessServiceImpl(String name, String version) {super(name, version);}


    /**
     * 增加 Process
     * @param originVo 源信息对象
     * @param processVo
     * @return long    主键ID
     */
	@Override
	public long addProcess(OriginVo originVo, ProcessVo processVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加Process，请求参数：" + JSonUtil.toJSonString(processVo));
		Process process = (Process) BeanUtil.copyProperties(Process.class, processVo);

		Long id = processLogic.addProcess(process); // 调用Logic添加Process

		return id;
	}



    /**
     * 删除 Process
     * @param originVo 源信息对象
     * @param processVo
     * @return boolean    
     */
	@Override
	public boolean deleteProcess(OriginVo originVo, ProcessVo processVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除Process，请求参数：" + JSonUtil.toJSonString(processVo));
		Process process = (Process) BeanUtil.copyProperties(Process.class, processVo);

		Boolean result = processLogic.deleteProcess(process); // 调用Logic删除Process

		return null == result ? false : result;
	}



    /**
     * 根据id删除单个 Process
     * @param originVo 源信息对象
     * @param id
     * @return ProcessVo
     */
	@Override
	public boolean deleteProcessById(OriginVo originVo, long id) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id删除单个Process，请求参数：" + JSonUtil.toJSonString(id));

		Boolean result = processLogic.deleteProcessById(id); // 调用Logic删除Process

		return null == result ? false : result;
	}



    /**
     * 根据processId删除单个 Process
     * @param originVo 源信息对象
     * @param processId
     * @return ProcessVo
     */
	@Override
	public boolean deleteProcessByProcessId(OriginVo originVo, String processId) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据processId删除单个Process，请求参数：" + JSonUtil.toJSonString(processId));

		Boolean result = processLogic.deleteProcessByProcessId(processId); // 调用Logic删除Process

		return null == result ? false : result;
	}



    /**
     * 修改 Process
     * @param originVo 源信息对象
     * @param processVo
     * @return boolean    
     */
	@Override
	public boolean updateProcess(OriginVo originVo, ProcessVo processVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改Process，请求参数：" + JSonUtil.toJSonString(processVo));
		Process process = (Process) BeanUtil.copyProperties(Process.class, processVo);

		Boolean result = processLogic.updateProcess(process); // 调用Logic修改Process

		return null == result ? false : result;
	}



    /**
     * 根据id修改单个 Process
     * @param originVo 源信息对象
     * @param id
     * @return ProcessVo
     */
	@Override
	public boolean updateProcessById(OriginVo originVo, ProcessVo processVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id修改单个Process，请求参数：" + JSonUtil.toJSonString(processVo));
		Process process = (Process) BeanUtil.copyProperties(Process.class, processVo);

		Boolean result = processLogic.updateProcessById(process); // 调用Logic修改Process

		return null == result ? false : result;
	}



    /**
     * 根据id修改单个 Process
     * @param originVo 源信息对象
     * @param processId
     * @return ProcessVo
     */
	@Override
	public boolean updateProcessByProcessId(OriginVo originVo, ProcessVo processVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据processId修改单个Process，请求参数：" + JSonUtil.toJSonString(processVo));
		Process process = (Process) BeanUtil.copyProperties(Process.class, processVo);

		Boolean result = processLogic.updateProcessByProcessId(process); // 调用Logic修改Process

		return null == result ? false : result;
	}



    /**
     * 根据id查询单个 Process
     * @param id
     * @return ProcessVo
     */
	@Override
	public ProcessVo getProcessById(long id) throws TException {
		LogCvt.info("根据id查询单个Process，请求参数：" + JSonUtil.toJSonString(id));

		Process process = processLogic.findProcessById(id); // 调用Logic查询单个Process

		ProcessVo processVo = (ProcessVo) BeanUtil.copyProperties(ProcessVo.class, process);
		return processVo;
	}



    /**
     * 根据processId查询单个 Process
     * @param processId
     * @return ProcessVo
     */
	@Override
	public ProcessVo getProcessByProcessId(String processId) throws TException {
		LogCvt.info("根据processId查询单个Process，请求参数：" + JSonUtil.toJSonString(processId));

		Process process = processLogic.findProcessByProcessId(processId); // 调用Logic查询单个Process

		ProcessVo processVo = (ProcessVo) BeanUtil.copyProperties(ProcessVo.class, process);
		return processVo;
	}



    /**
     * 根据条件查询一个 Process
     * @param processVo
     * @return ProcessVo
     */
	@Override
	public ProcessVo getOneProcess(ProcessVo processVo) throws TException {
		LogCvt.info("根据条件查询一个Process，请求参数：" + JSonUtil.toJSonString(processVo));
		Process process = (Process) BeanUtil.copyProperties(Process.class, processVo);

		process = processLogic.findOneProcess(process); // 调用Logic查询单个Process

		processVo = (ProcessVo) BeanUtil.copyProperties(ProcessVo.class, process);
		return processVo;
	}



    /**
     * 根据条件统计 Process
     * @param processVo
     * @return int
     */
	@Override
	public int countProcess(ProcessVo processVo) throws TException {
		LogCvt.info("根据条件统计Process，请求参数：" + JSonUtil.toJSonString(processVo));
		Process process = (Process) BeanUtil.copyProperties(Process.class, processVo);

		return processLogic.countProcess(process); // 调用Logic统计Process

	}



    /**
     * 查询 Process
     * @param processVo
     * @return List<ProcessVo>
     */
	@Override
	public List<ProcessVo> getProcess(ProcessVo processVo) throws TException {
		LogCvt.info("查询Process，请求参数：" + JSonUtil.toJSonString(processVo));
		Process process = (Process) BeanUtil.copyProperties(Process.class, processVo);

		List<Process> processList = processLogic.findProcess(process); // 调用Logic查询Process

		List<ProcessVo> processVoList = (List<ProcessVo>) BeanUtil.copyProperties(ProcessVo.class, processList);
		return processVoList;
	}



    /**
     * 分页查询 Process
     * @param processVo
     * @return ProcessPageVoRes
     */
	@Override
	public ProcessPageVoRes getProcessByPage(PageVo pageVo, ProcessVo processVo) throws TException {
		LogCvt.info("分页查询Process，请求参数：PageVo==>" + JSonUtil.toJSonString(pageVo) + "\t processVo==>" + JSonUtil.toJSonString(processVo));
		Page<Process> page = (Page<Process>) BeanUtil.copyProperties(Page.class, pageVo);
		Process process = (Process) BeanUtil.copyProperties(Process.class, processVo);

		page = processLogic.findProcessByPage(page, process); // 调用Logic分页查询Process

		List<ProcessVo> processVoList = (List<ProcessVo>) BeanUtil.copyProperties(ProcessVo.class, page.getResultsContent());
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);

		ProcessPageVoRes processPageVoRes = new ProcessPageVoRes();
		processPageVoRes.setPage(pageVo);
		processPageVoRes.setProcessVoList(processVoList);
		return processPageVoRes;
	}


}
