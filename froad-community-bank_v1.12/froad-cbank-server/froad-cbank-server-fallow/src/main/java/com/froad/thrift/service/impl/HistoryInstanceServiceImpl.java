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
 * @Title: HistoryInstanceImpl.java
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
import com.froad.logic.HistoryInstanceLogic;
import com.froad.logic.impl.HistoryInstanceLogicImpl;
import com.froad.po.mysql.HistoryInstance;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.HistoryInstanceService;
import com.froad.thrift.vo.HistoryInstancePageVoRes;
import com.froad.thrift.vo.HistoryInstanceVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;


/**
 * 
 * <p>@Title: HistoryInstanceImpl.java</p>
 * <p>Description: 实现对HistoryInstance所有业务逻辑处理的外部thrift接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class HistoryInstanceServiceImpl extends BizMonitorBaseService implements HistoryInstanceService.Iface {
	private HistoryInstanceLogic historyInstanceLogic = new HistoryInstanceLogicImpl();
	public HistoryInstanceServiceImpl() {}
	public HistoryInstanceServiceImpl(String name, String version) {super(name, version);}


    /**
     * 增加 HistoryInstance
     * @param originVo 源信息对象
     * @param historyInstanceVo
     * @return long    主键ID
     */
	@Override
	public long addHistoryInstance(OriginVo originVo, HistoryInstanceVo historyInstanceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加HistoryInstance，请求参数：" + JSonUtil.toJSonString(historyInstanceVo));
		HistoryInstance historyInstance = (HistoryInstance) BeanUtil.copyProperties(HistoryInstance.class, historyInstanceVo);

		Long id = historyInstanceLogic.addHistoryInstance(historyInstance); // 调用Logic添加HistoryInstance

		return id;
	}



    /**
     * 删除 HistoryInstance
     * @param originVo 源信息对象
     * @param historyInstanceVo
     * @return boolean    
     */
	@Override
	public boolean deleteHistoryInstance(OriginVo originVo, HistoryInstanceVo historyInstanceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除HistoryInstance，请求参数：" + JSonUtil.toJSonString(historyInstanceVo));
		HistoryInstance historyInstance = (HistoryInstance) BeanUtil.copyProperties(HistoryInstance.class, historyInstanceVo);

		Boolean result = historyInstanceLogic.deleteHistoryInstance(historyInstance); // 调用Logic删除HistoryInstance

		return null == result ? false : result;
	}



    /**
     * 根据id删除单个 HistoryInstance
     * @param originVo 源信息对象
     * @param id
     * @return HistoryInstanceVo
     */
	@Override
	public boolean deleteHistoryInstanceById(OriginVo originVo, long id) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id删除单个HistoryInstance，请求参数：" + JSonUtil.toJSonString(id));

		Boolean result = historyInstanceLogic.deleteHistoryInstanceById(id); // 调用Logic删除HistoryInstance

		return null == result ? false : result;
	}



    /**
     * 修改 HistoryInstance
     * @param originVo 源信息对象
     * @param historyInstanceVo
     * @return boolean    
     */
	@Override
	public boolean updateHistoryInstance(OriginVo originVo, HistoryInstanceVo historyInstanceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改HistoryInstance，请求参数：" + JSonUtil.toJSonString(historyInstanceVo));
		HistoryInstance historyInstance = (HistoryInstance) BeanUtil.copyProperties(HistoryInstance.class, historyInstanceVo);

		Boolean result = historyInstanceLogic.updateHistoryInstance(historyInstance); // 调用Logic修改HistoryInstance

		return null == result ? false : result;
	}



    /**
     * 根据id修改单个 HistoryInstance
     * @param originVo 源信息对象
     * @param id
     * @return HistoryInstanceVo
     */
	@Override
	public boolean updateHistoryInstanceById(OriginVo originVo, HistoryInstanceVo historyInstanceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id修改单个HistoryInstance，请求参数：" + JSonUtil.toJSonString(historyInstanceVo));
		HistoryInstance historyInstance = (HistoryInstance) BeanUtil.copyProperties(HistoryInstance.class, historyInstanceVo);

		Boolean result = historyInstanceLogic.updateHistoryInstanceById(historyInstance); // 调用Logic修改HistoryInstance

		return null == result ? false : result;
	}



    /**
     * 根据id查询单个 HistoryInstance
     * @param id
     * @return HistoryInstanceVo
     */
	@Override
	public HistoryInstanceVo getHistoryInstanceById(long id) throws TException {
		LogCvt.info("根据id查询单个HistoryInstance，请求参数：" + JSonUtil.toJSonString(id));

		HistoryInstance historyInstance = historyInstanceLogic.findHistoryInstanceById(id); // 调用Logic查询单个HistoryInstance

		HistoryInstanceVo historyInstanceVo = (HistoryInstanceVo) BeanUtil.copyProperties(HistoryInstanceVo.class, historyInstance);
		return historyInstanceVo;
	}



    /**
     * 根据条件查询一个 HistoryInstance
     * @param historyInstanceVo
     * @return HistoryInstanceVo
     */
	@Override
	public HistoryInstanceVo getOneHistoryInstance(HistoryInstanceVo historyInstanceVo) throws TException {
		LogCvt.info("根据条件查询一个HistoryInstance，请求参数：" + JSonUtil.toJSonString(historyInstanceVo));
		HistoryInstance historyInstance = (HistoryInstance) BeanUtil.copyProperties(HistoryInstance.class, historyInstanceVo);

		historyInstance = historyInstanceLogic.findOneHistoryInstance(historyInstance); // 调用Logic查询单个HistoryInstance

		historyInstanceVo = (HistoryInstanceVo) BeanUtil.copyProperties(HistoryInstanceVo.class, historyInstance);
		return historyInstanceVo;
	}



    /**
     * 根据条件统计 HistoryInstance
     * @param historyInstanceVo
     * @return int
     */
	@Override
	public int countHistoryInstance(HistoryInstanceVo historyInstanceVo) throws TException {
		LogCvt.info("根据条件统计HistoryInstance，请求参数：" + JSonUtil.toJSonString(historyInstanceVo));
		HistoryInstance historyInstance = (HistoryInstance) BeanUtil.copyProperties(HistoryInstance.class, historyInstanceVo);

		return historyInstanceLogic.countHistoryInstance(historyInstance); // 调用Logic统计HistoryInstance

	}



    /**
     * 查询 HistoryInstance
     * @param historyInstanceVo
     * @return List<HistoryInstanceVo>
     */
	@Override
	public List<HistoryInstanceVo> getHistoryInstance(HistoryInstanceVo historyInstanceVo) throws TException {
		LogCvt.info("查询HistoryInstance，请求参数：" + JSonUtil.toJSonString(historyInstanceVo));
		HistoryInstance historyInstance = (HistoryInstance) BeanUtil.copyProperties(HistoryInstance.class, historyInstanceVo);

		List<HistoryInstance> historyInstanceList = historyInstanceLogic.findHistoryInstance(historyInstance); // 调用Logic查询HistoryInstance

		List<HistoryInstanceVo> historyInstanceVoList = (List<HistoryInstanceVo>) BeanUtil.copyProperties(HistoryInstanceVo.class, historyInstanceList);
		return historyInstanceVoList;
	}



    /**
     * 分页查询 HistoryInstance
     * @param historyInstanceVo
     * @return HistoryInstancePageVoRes
     */
	@Override
	public HistoryInstancePageVoRes getHistoryInstanceByPage(PageVo pageVo, HistoryInstanceVo historyInstanceVo) throws TException {
		LogCvt.info("分页查询HistoryInstance，请求参数：PageVo==>" + JSonUtil.toJSonString(pageVo) + "\t historyInstanceVo==>" + JSonUtil.toJSonString(historyInstanceVo));
		Page<HistoryInstance> page = (Page<HistoryInstance>) BeanUtil.copyProperties(Page.class, pageVo);
		HistoryInstance historyInstance = (HistoryInstance) BeanUtil.copyProperties(HistoryInstance.class, historyInstanceVo);

		page = historyInstanceLogic.findHistoryInstanceByPage(page, historyInstance); // 调用Logic分页查询HistoryInstance

		List<HistoryInstanceVo> historyInstanceVoList = (List<HistoryInstanceVo>) BeanUtil.copyProperties(HistoryInstanceVo.class, page.getResultsContent());
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);

		HistoryInstancePageVoRes historyInstancePageVoRes = new HistoryInstancePageVoRes();
		historyInstancePageVoRes.setPage(pageVo);
		historyInstancePageVoRes.setHistoryInstanceVoList(historyInstanceVoList);
		return historyInstancePageVoRes;
	}


}
