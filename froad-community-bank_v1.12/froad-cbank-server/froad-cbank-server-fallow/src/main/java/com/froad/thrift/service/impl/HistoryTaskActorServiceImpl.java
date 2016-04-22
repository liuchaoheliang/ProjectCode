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
 * @Title: HistoryTaskActorImpl.java
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
import com.froad.logic.HistoryTaskActorLogic;
import com.froad.logic.impl.HistoryTaskActorLogicImpl;
import com.froad.po.mysql.HistoryTaskActor;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.HistoryTaskActorService;
import com.froad.thrift.vo.HistoryTaskActorPageVoRes;
import com.froad.thrift.vo.HistoryTaskActorVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;


/**
 * 
 * <p>@Title: HistoryTaskActorImpl.java</p>
 * <p>Description: 实现对HistoryTaskActor所有业务逻辑处理的外部thrift接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class HistoryTaskActorServiceImpl extends BizMonitorBaseService implements HistoryTaskActorService.Iface {
	private HistoryTaskActorLogic historyTaskActorLogic = new HistoryTaskActorLogicImpl();
	public HistoryTaskActorServiceImpl() {}
	public HistoryTaskActorServiceImpl(String name, String version) {super(name, version);}


    /**
     * 增加 HistoryTaskActor
     * @param originVo 源信息对象
     * @param historyTaskActorVo
     * @return String    主键ID
     */
	@Override
	public String addHistoryTaskActor(OriginVo originVo, HistoryTaskActorVo historyTaskActorVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加HistoryTaskActor，请求参数：" + JSonUtil.toJSonString(historyTaskActorVo));
		HistoryTaskActor historyTaskActor = (HistoryTaskActor) BeanUtil.copyProperties(HistoryTaskActor.class, historyTaskActorVo);

		String taskId = historyTaskActorLogic.addHistoryTaskActor(historyTaskActor); // 调用Logic添加HistoryTaskActor

		return taskId;
	}



    /**
     * 删除 HistoryTaskActor
     * @param originVo 源信息对象
     * @param historyTaskActorVo
     * @return boolean    
     */
	@Override
	public boolean deleteHistoryTaskActor(OriginVo originVo, HistoryTaskActorVo historyTaskActorVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除HistoryTaskActor，请求参数：" + JSonUtil.toJSonString(historyTaskActorVo));
		HistoryTaskActor historyTaskActor = (HistoryTaskActor) BeanUtil.copyProperties(HistoryTaskActor.class, historyTaskActorVo);

		Boolean result = historyTaskActorLogic.deleteHistoryTaskActor(historyTaskActor); // 调用Logic删除HistoryTaskActor

		return null == result ? false : result;
	}



    /**
     * 根据taskId删除单个 HistoryTaskActor
     * @param originVo 源信息对象
     * @param taskId
     * @return HistoryTaskActorVo
     */
	@Override
	public boolean deleteHistoryTaskActorByTaskId(OriginVo originVo, String taskId) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据taskId删除单个HistoryTaskActor，请求参数：" + JSonUtil.toJSonString(taskId));

		Boolean result = historyTaskActorLogic.deleteHistoryTaskActorByTaskId(taskId); // 调用Logic删除HistoryTaskActor

		return null == result ? false : result;
	}



    /**
     * 修改 HistoryTaskActor
     * @param originVo 源信息对象
     * @param historyTaskActorVo
     * @return boolean    
     */
	@Override
	public boolean updateHistoryTaskActor(OriginVo originVo, HistoryTaskActorVo historyTaskActorVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改HistoryTaskActor，请求参数：" + JSonUtil.toJSonString(historyTaskActorVo));
		HistoryTaskActor historyTaskActor = (HistoryTaskActor) BeanUtil.copyProperties(HistoryTaskActor.class, historyTaskActorVo);

		Boolean result = historyTaskActorLogic.updateHistoryTaskActor(historyTaskActor); // 调用Logic修改HistoryTaskActor

		return null == result ? false : result;
	}



    /**
     * 根据taskId修改单个 HistoryTaskActor
     * @param originVo 源信息对象
     * @param taskId
     * @return HistoryTaskActorVo
     */
	@Override
	public boolean updateHistoryTaskActorByTaskId(OriginVo originVo, HistoryTaskActorVo historyTaskActorVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据taskId修改单个HistoryTaskActor，请求参数：" + JSonUtil.toJSonString(historyTaskActorVo));
		HistoryTaskActor historyTaskActor = (HistoryTaskActor) BeanUtil.copyProperties(HistoryTaskActor.class, historyTaskActorVo);

		Boolean result = historyTaskActorLogic.updateHistoryTaskActorByTaskId(historyTaskActor); // 调用Logic修改HistoryTaskActor

		return null == result ? false : result;
	}



    /**
     * 根据taskId查询单个 HistoryTaskActor
     * @param taskId
     * @return HistoryTaskActorVo
     */
	@Override
	public HistoryTaskActorVo getHistoryTaskActorByTaskId(String taskId) throws TException {
		LogCvt.info("根据taskId查询单个HistoryTaskActor，请求参数：" + JSonUtil.toJSonString(taskId));

		HistoryTaskActor historyTaskActor = historyTaskActorLogic.findHistoryTaskActorByTaskId(taskId); // 调用Logic查询单个HistoryTaskActor

		HistoryTaskActorVo historyTaskActorVo = (HistoryTaskActorVo) BeanUtil.copyProperties(HistoryTaskActorVo.class, historyTaskActor);
		return historyTaskActorVo;
	}



    /**
     * 根据条件查询一个 HistoryTaskActor
     * @param historyTaskActorVo
     * @return HistoryTaskActorVo
     */
	@Override
	public HistoryTaskActorVo getOneHistoryTaskActor(HistoryTaskActorVo historyTaskActorVo) throws TException {
		LogCvt.info("根据条件查询一个HistoryTaskActor，请求参数：" + JSonUtil.toJSonString(historyTaskActorVo));
		HistoryTaskActor historyTaskActor = (HistoryTaskActor) BeanUtil.copyProperties(HistoryTaskActor.class, historyTaskActorVo);

		historyTaskActor = historyTaskActorLogic.findOneHistoryTaskActor(historyTaskActor); // 调用Logic查询单个HistoryTaskActor

		historyTaskActorVo = (HistoryTaskActorVo) BeanUtil.copyProperties(HistoryTaskActorVo.class, historyTaskActor);
		return historyTaskActorVo;
	}



    /**
     * 根据条件统计 HistoryTaskActor
     * @param historyTaskActorVo
     * @return int
     */
	@Override
	public int countHistoryTaskActor(HistoryTaskActorVo historyTaskActorVo) throws TException {
		LogCvt.info("根据条件统计HistoryTaskActor，请求参数：" + JSonUtil.toJSonString(historyTaskActorVo));
		HistoryTaskActor historyTaskActor = (HistoryTaskActor) BeanUtil.copyProperties(HistoryTaskActor.class, historyTaskActorVo);

		return historyTaskActorLogic.countHistoryTaskActor(historyTaskActor); // 调用Logic统计HistoryTaskActor

	}



    /**
     * 查询 HistoryTaskActor
     * @param historyTaskActorVo
     * @return List<HistoryTaskActorVo>
     */
	@Override
	public List<HistoryTaskActorVo> getHistoryTaskActor(HistoryTaskActorVo historyTaskActorVo) throws TException {
		LogCvt.info("查询HistoryTaskActor，请求参数：" + JSonUtil.toJSonString(historyTaskActorVo));
		HistoryTaskActor historyTaskActor = (HistoryTaskActor) BeanUtil.copyProperties(HistoryTaskActor.class, historyTaskActorVo);

		List<HistoryTaskActor> historyTaskActorList = historyTaskActorLogic.findHistoryTaskActor(historyTaskActor); // 调用Logic查询HistoryTaskActor

		List<HistoryTaskActorVo> historyTaskActorVoList = (List<HistoryTaskActorVo>) BeanUtil.copyProperties(HistoryTaskActorVo.class, historyTaskActorList);
		return historyTaskActorVoList;
	}



    /**
     * 分页查询 HistoryTaskActor
     * @param historyTaskActorVo
     * @return HistoryTaskActorPageVoRes
     */
	@Override
	public HistoryTaskActorPageVoRes getHistoryTaskActorByPage(PageVo pageVo, HistoryTaskActorVo historyTaskActorVo) throws TException {
		LogCvt.info("分页查询HistoryTaskActor，请求参数：PageVo==>" + JSonUtil.toJSonString(pageVo) + "\t historyTaskActorVo==>" + JSonUtil.toJSonString(historyTaskActorVo));
		Page<HistoryTaskActor> page = (Page<HistoryTaskActor>) BeanUtil.copyProperties(Page.class, pageVo);
		HistoryTaskActor historyTaskActor = (HistoryTaskActor) BeanUtil.copyProperties(HistoryTaskActor.class, historyTaskActorVo);

		page = historyTaskActorLogic.findHistoryTaskActorByPage(page, historyTaskActor); // 调用Logic分页查询HistoryTaskActor

		List<HistoryTaskActorVo> historyTaskActorVoList = (List<HistoryTaskActorVo>) BeanUtil.copyProperties(HistoryTaskActorVo.class, page.getResultsContent());
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);

		HistoryTaskActorPageVoRes historyTaskActorPageVoRes = new HistoryTaskActorPageVoRes();
		historyTaskActorPageVoRes.setPage(pageVo);
		historyTaskActorPageVoRes.setHistoryTaskActorVoList(historyTaskActorVoList);
		return historyTaskActorPageVoRes;
	}


}
