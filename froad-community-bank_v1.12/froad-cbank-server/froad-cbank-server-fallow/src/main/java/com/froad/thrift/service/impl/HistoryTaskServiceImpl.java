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
 * @Title: HistoryTaskImpl.java
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
import com.froad.logic.HistoryTaskLogic;
import com.froad.logic.impl.HistoryTaskLogicImpl;
import com.froad.po.mysql.HistoryTask;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.HistoryTaskService;
import com.froad.thrift.vo.HistoryTaskPageVoRes;
import com.froad.thrift.vo.HistoryTaskVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;


/**
 * 
 * <p>@Title: HistoryTaskImpl.java</p>
 * <p>Description: 实现对HistoryTask所有业务逻辑处理的外部thrift接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class HistoryTaskServiceImpl extends BizMonitorBaseService implements HistoryTaskService.Iface {
	private HistoryTaskLogic historyTaskLogic = new HistoryTaskLogicImpl();
	public HistoryTaskServiceImpl() {}
	public HistoryTaskServiceImpl(String name, String version) {super(name, version);}


    /**
     * 增加 HistoryTask
     * @param originVo 源信息对象
     * @param historyTaskVo
     * @return long    主键ID
     */
	@Override
	public long addHistoryTask(OriginVo originVo, HistoryTaskVo historyTaskVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加HistoryTask，请求参数：" + JSonUtil.toJSonString(historyTaskVo));
		HistoryTask historyTask = (HistoryTask) BeanUtil.copyProperties(HistoryTask.class, historyTaskVo);

		Long id = historyTaskLogic.addHistoryTask(historyTask); // 调用Logic添加HistoryTask

		return id;
	}



    /**
     * 删除 HistoryTask
     * @param originVo 源信息对象
     * @param historyTaskVo
     * @return boolean    
     */
	@Override
	public boolean deleteHistoryTask(OriginVo originVo, HistoryTaskVo historyTaskVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除HistoryTask，请求参数：" + JSonUtil.toJSonString(historyTaskVo));
		HistoryTask historyTask = (HistoryTask) BeanUtil.copyProperties(HistoryTask.class, historyTaskVo);

		Boolean result = historyTaskLogic.deleteHistoryTask(historyTask); // 调用Logic删除HistoryTask

		return null == result ? false : result;
	}



    /**
     * 根据id删除单个 HistoryTask
     * @param originVo 源信息对象
     * @param id
     * @return HistoryTaskVo
     */
	@Override
	public boolean deleteHistoryTaskById(OriginVo originVo, long id) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id删除单个HistoryTask，请求参数：" + JSonUtil.toJSonString(id));

		Boolean result = historyTaskLogic.deleteHistoryTaskById(id); // 调用Logic删除HistoryTask

		return null == result ? false : result;
	}



    /**
     * 修改 HistoryTask
     * @param originVo 源信息对象
     * @param historyTaskVo
     * @return boolean    
     */
	@Override
	public boolean updateHistoryTask(OriginVo originVo, HistoryTaskVo historyTaskVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改HistoryTask，请求参数：" + JSonUtil.toJSonString(historyTaskVo));
		HistoryTask historyTask = (HistoryTask) BeanUtil.copyProperties(HistoryTask.class, historyTaskVo);

		Boolean result = historyTaskLogic.updateHistoryTask(historyTask); // 调用Logic修改HistoryTask

		return null == result ? false : result;
	}



    /**
     * 根据id修改单个 HistoryTask
     * @param originVo 源信息对象
     * @param id
     * @return HistoryTaskVo
     */
	@Override
	public boolean updateHistoryTaskById(OriginVo originVo, HistoryTaskVo historyTaskVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id修改单个HistoryTask，请求参数：" + JSonUtil.toJSonString(historyTaskVo));
		HistoryTask historyTask = (HistoryTask) BeanUtil.copyProperties(HistoryTask.class, historyTaskVo);

		Boolean result = historyTaskLogic.updateHistoryTaskById(historyTask); // 调用Logic修改HistoryTask

		return null == result ? false : result;
	}



    /**
     * 根据id查询单个 HistoryTask
     * @param id
     * @return HistoryTaskVo
     */
	@Override
	public HistoryTaskVo getHistoryTaskById(long id) throws TException {
		LogCvt.info("根据id查询单个HistoryTask，请求参数：" + JSonUtil.toJSonString(id));

		HistoryTask historyTask = historyTaskLogic.findHistoryTaskById(id); // 调用Logic查询单个HistoryTask

		HistoryTaskVo historyTaskVo = (HistoryTaskVo) BeanUtil.copyProperties(HistoryTaskVo.class, historyTask);
		return historyTaskVo;
	}



    /**
     * 根据条件查询一个 HistoryTask
     * @param historyTaskVo
     * @return HistoryTaskVo
     */
	@Override
	public HistoryTaskVo getOneHistoryTask(HistoryTaskVo historyTaskVo) throws TException {
		LogCvt.info("根据条件查询一个HistoryTask，请求参数：" + JSonUtil.toJSonString(historyTaskVo));
		HistoryTask historyTask = (HistoryTask) BeanUtil.copyProperties(HistoryTask.class, historyTaskVo);

		historyTask = historyTaskLogic.findOneHistoryTask(historyTask); // 调用Logic查询单个HistoryTask

		historyTaskVo = (HistoryTaskVo) BeanUtil.copyProperties(HistoryTaskVo.class, historyTask);
		return historyTaskVo;
	}



    /**
     * 根据条件统计 HistoryTask
     * @param historyTaskVo
     * @return int
     */
	@Override
	public int countHistoryTask(HistoryTaskVo historyTaskVo) throws TException {
		LogCvt.info("根据条件统计HistoryTask，请求参数：" + JSonUtil.toJSonString(historyTaskVo));
		HistoryTask historyTask = (HistoryTask) BeanUtil.copyProperties(HistoryTask.class, historyTaskVo);

		return historyTaskLogic.countHistoryTask(historyTask); // 调用Logic统计HistoryTask

	}



    /**
     * 查询 HistoryTask
     * @param historyTaskVo
     * @return List<HistoryTaskVo>
     */
	@Override
	public List<HistoryTaskVo> getHistoryTask(HistoryTaskVo historyTaskVo) throws TException {
		LogCvt.info("查询HistoryTask，请求参数：" + JSonUtil.toJSonString(historyTaskVo));
		HistoryTask historyTask = (HistoryTask) BeanUtil.copyProperties(HistoryTask.class, historyTaskVo);

		List<HistoryTask> historyTaskList = historyTaskLogic.findHistoryTask(historyTask); // 调用Logic查询HistoryTask

		List<HistoryTaskVo> historyTaskVoList = (List<HistoryTaskVo>) BeanUtil.copyProperties(HistoryTaskVo.class, historyTaskList);
		return historyTaskVoList;
	}



    /**
     * 分页查询 HistoryTask
     * @param historyTaskVo
     * @return HistoryTaskPageVoRes
     */
	@Override
	public HistoryTaskPageVoRes getHistoryTaskByPage(PageVo pageVo, HistoryTaskVo historyTaskVo) throws TException {
		LogCvt.info("分页查询HistoryTask，请求参数：PageVo==>" + JSonUtil.toJSonString(pageVo) + "\t historyTaskVo==>" + JSonUtil.toJSonString(historyTaskVo));
		Page<HistoryTask> page = (Page<HistoryTask>) BeanUtil.copyProperties(Page.class, pageVo);
		HistoryTask historyTask = (HistoryTask) BeanUtil.copyProperties(HistoryTask.class, historyTaskVo);

		page = historyTaskLogic.findHistoryTaskByPage(page, historyTask); // 调用Logic分页查询HistoryTask

		List<HistoryTaskVo> historyTaskVoList = (List<HistoryTaskVo>) BeanUtil.copyProperties(HistoryTaskVo.class, page.getResultsContent());
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);

		HistoryTaskPageVoRes historyTaskPageVoRes = new HistoryTaskPageVoRes();
		historyTaskPageVoRes.setPage(pageVo);
		historyTaskPageVoRes.setHistoryTaskVoList(historyTaskVoList);
		return historyTaskPageVoRes;
	}


}
