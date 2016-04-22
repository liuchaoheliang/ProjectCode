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
 * @Title: ProcessNodeImpl.java
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
import com.froad.logic.ProcessNodeLogic;
import com.froad.logic.impl.ProcessNodeLogicImpl;
import com.froad.po.mysql.ProcessNode;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ProcessNodeService;
import com.froad.thrift.vo.ProcessNodePageVoRes;
import com.froad.thrift.vo.ProcessNodeVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;


/**
 * 
 * <p>@Title: ProcessNodeImpl.java</p>
 * <p>Description: 实现对ProcessNode所有业务逻辑处理的外部thrift接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProcessNodeServiceImpl extends BizMonitorBaseService implements ProcessNodeService.Iface {
	private ProcessNodeLogic processNodeLogic = new ProcessNodeLogicImpl();
	public ProcessNodeServiceImpl() {}
	public ProcessNodeServiceImpl(String name, String version) {super(name, version);}


    /**
     * 增加 ProcessNode
     * @param originVo 源信息对象
     * @param processNodeVo
     * @return long    主键ID
     */
	@Override
	public long addProcessNode(OriginVo originVo, ProcessNodeVo processNodeVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加ProcessNode，请求参数：" + JSonUtil.toJSonString(processNodeVo));
		ProcessNode processNode = (ProcessNode) BeanUtil.copyProperties(ProcessNode.class, processNodeVo);

		Long id = processNodeLogic.addProcessNode(processNode); // 调用Logic添加ProcessNode

		return id;
	}



    /**
     * 删除 ProcessNode
     * @param originVo 源信息对象
     * @param processNodeVo
     * @return boolean    
     */
	@Override
	public boolean deleteProcessNode(OriginVo originVo, ProcessNodeVo processNodeVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除ProcessNode，请求参数：" + JSonUtil.toJSonString(processNodeVo));
		ProcessNode processNode = (ProcessNode) BeanUtil.copyProperties(ProcessNode.class, processNodeVo);

		Boolean result = processNodeLogic.deleteProcessNode(processNode); // 调用Logic删除ProcessNode

		return null == result ? false : result;
	}



    /**
     * 根据id删除单个 ProcessNode
     * @param originVo 源信息对象
     * @param id
     * @return ProcessNodeVo
     */
	@Override
	public boolean deleteProcessNodeById(OriginVo originVo, long id) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id删除单个ProcessNode，请求参数：" + JSonUtil.toJSonString(id));

		Boolean result = processNodeLogic.deleteProcessNodeById(id); // 调用Logic删除ProcessNode

		return null == result ? false : result;
	}



    /**
     * 修改 ProcessNode
     * @param originVo 源信息对象
     * @param processNodeVo
     * @return boolean    
     */
	@Override
	public boolean updateProcessNode(OriginVo originVo, ProcessNodeVo processNodeVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改ProcessNode，请求参数：" + JSonUtil.toJSonString(processNodeVo));
		ProcessNode processNode = (ProcessNode) BeanUtil.copyProperties(ProcessNode.class, processNodeVo);

		Boolean result = processNodeLogic.updateProcessNode(processNode); // 调用Logic修改ProcessNode

		return null == result ? false : result;
	}



    /**
     * 根据id修改单个 ProcessNode
     * @param originVo 源信息对象
     * @param id
     * @return ProcessNodeVo
     */
	@Override
	public boolean updateProcessNodeById(OriginVo originVo, ProcessNodeVo processNodeVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id修改单个ProcessNode，请求参数：" + JSonUtil.toJSonString(processNodeVo));
		ProcessNode processNode = (ProcessNode) BeanUtil.copyProperties(ProcessNode.class, processNodeVo);

		Boolean result = processNodeLogic.updateProcessNodeById(processNode); // 调用Logic修改ProcessNode

		return null == result ? false : result;
	}



    /**
     * 根据id查询单个 ProcessNode
     * @param id
     * @return ProcessNodeVo
     */
	@Override
	public ProcessNodeVo getProcessNodeById(long id) throws TException {
		LogCvt.info("根据id查询单个ProcessNode，请求参数：" + JSonUtil.toJSonString(id));

		ProcessNode processNode = processNodeLogic.findProcessNodeById(id); // 调用Logic查询单个ProcessNode

		ProcessNodeVo processNodeVo = (ProcessNodeVo) BeanUtil.copyProperties(ProcessNodeVo.class, processNode);
		return processNodeVo;
	}



    /**
     * 根据条件查询一个 ProcessNode
     * @param processNodeVo
     * @return ProcessNodeVo
     */
	@Override
	public ProcessNodeVo getOneProcessNode(ProcessNodeVo processNodeVo) throws TException {
		LogCvt.info("根据条件查询一个ProcessNode，请求参数：" + JSonUtil.toJSonString(processNodeVo));
		ProcessNode processNode = (ProcessNode) BeanUtil.copyProperties(ProcessNode.class, processNodeVo);

		processNode = processNodeLogic.findOneProcessNode(processNode); // 调用Logic查询单个ProcessNode

		processNodeVo = (ProcessNodeVo) BeanUtil.copyProperties(ProcessNodeVo.class, processNode);
		return processNodeVo;
	}



    /**
     * 根据条件统计 ProcessNode
     * @param processNodeVo
     * @return int
     */
	@Override
	public int countProcessNode(ProcessNodeVo processNodeVo) throws TException {
		LogCvt.info("根据条件统计ProcessNode，请求参数：" + JSonUtil.toJSonString(processNodeVo));
		ProcessNode processNode = (ProcessNode) BeanUtil.copyProperties(ProcessNode.class, processNodeVo);

		return processNodeLogic.countProcessNode(processNode); // 调用Logic统计ProcessNode

	}



    /**
     * 查询 ProcessNode
     * @param processNodeVo
     * @return List<ProcessNodeVo>
     */
	@Override
	public List<ProcessNodeVo> getProcessNode(ProcessNodeVo processNodeVo) throws TException {
		LogCvt.info("查询ProcessNode，请求参数：" + JSonUtil.toJSonString(processNodeVo));
		ProcessNode processNode = (ProcessNode) BeanUtil.copyProperties(ProcessNode.class, processNodeVo);

		List<ProcessNode> processNodeList = processNodeLogic.findProcessNode(processNode); // 调用Logic查询ProcessNode

		List<ProcessNodeVo> processNodeVoList = (List<ProcessNodeVo>) BeanUtil.copyProperties(ProcessNodeVo.class, processNodeList);
		return processNodeVoList;
	}



    /**
     * 分页查询 ProcessNode
     * @param processNodeVo
     * @return ProcessNodePageVoRes
     */
	@Override
	public ProcessNodePageVoRes getProcessNodeByPage(PageVo pageVo, ProcessNodeVo processNodeVo) throws TException {
		LogCvt.info("分页查询ProcessNode，请求参数：PageVo==>" + JSonUtil.toJSonString(pageVo) + "\t processNodeVo==>" + JSonUtil.toJSonString(processNodeVo));
		Page<ProcessNode> page = (Page<ProcessNode>) BeanUtil.copyProperties(Page.class, pageVo);
		ProcessNode processNode = (ProcessNode) BeanUtil.copyProperties(ProcessNode.class, processNodeVo);

		page = processNodeLogic.findProcessNodeByPage(page, processNode); // 调用Logic分页查询ProcessNode

		List<ProcessNodeVo> processNodeVoList = (List<ProcessNodeVo>) BeanUtil.copyProperties(ProcessNodeVo.class, page.getResultsContent());
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);

		ProcessNodePageVoRes processNodePageVoRes = new ProcessNodePageVoRes();
		processNodePageVoRes.setPage(pageVo);
		processNodePageVoRes.setProcessNodeVoList(processNodeVoList);
		return processNodePageVoRes;
	}


}
