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
 * @Title: InstanceImpl.java
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
import com.froad.logic.InstanceLogic;
import com.froad.logic.impl.InstanceLogicImpl;
import com.froad.po.mysql.Instance;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.InstanceService;
import com.froad.thrift.vo.InstancePageVoRes;
import com.froad.thrift.vo.InstanceVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;


/**
 * 
 * <p>@Title: InstanceImpl.java</p>
 * <p>Description: 实现对Instance所有业务逻辑处理的外部thrift接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class InstanceServiceImpl extends BizMonitorBaseService implements InstanceService.Iface {
	private InstanceLogic instanceLogic = new InstanceLogicImpl();
	public InstanceServiceImpl() {}
	public InstanceServiceImpl(String name, String version) {super(name, version);}


    /**
     * 增加 Instance
     * @param originVo 源信息对象
     * @param instanceVo
     * @return long    主键ID
     */
	@Override
	public long addInstance(OriginVo originVo, InstanceVo instanceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加Instance，请求参数：" + JSonUtil.toJSonString(instanceVo));
		Instance instance = (Instance) BeanUtil.copyProperties(Instance.class, instanceVo);

		Long id = instanceLogic.addInstance(instance); // 调用Logic添加Instance

		return id;
	}



    /**
     * 删除 Instance
     * @param originVo 源信息对象
     * @param instanceVo
     * @return boolean    
     */
	@Override
	public boolean deleteInstance(OriginVo originVo, InstanceVo instanceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除Instance，请求参数：" + JSonUtil.toJSonString(instanceVo));
		Instance instance = (Instance) BeanUtil.copyProperties(Instance.class, instanceVo);

		Boolean result = instanceLogic.deleteInstance(instance); // 调用Logic删除Instance

		return null == result ? false : result;
	}



    /**
     * 根据id删除单个 Instance
     * @param originVo 源信息对象
     * @param id
     * @return InstanceVo
     */
	@Override
	public boolean deleteInstanceById(OriginVo originVo, long id) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id删除单个Instance，请求参数：" + JSonUtil.toJSonString(id));

		Boolean result = instanceLogic.deleteInstanceById(id); // 调用Logic删除Instance

		return null == result ? false : result;
	}



    /**
     * 根据instanceId删除单个 Instance
     * @param originVo 源信息对象
     * @param instanceId
     * @return InstanceVo
     */
	@Override
	public boolean deleteInstanceByInstanceId(OriginVo originVo, String instanceId) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据instanceId删除单个Instance，请求参数：" + JSonUtil.toJSonString(instanceId));

		Boolean result = instanceLogic.deleteInstanceByInstanceId(instanceId); // 调用Logic删除Instance

		return null == result ? false : result;
	}



    /**
     * 修改 Instance
     * @param originVo 源信息对象
     * @param instanceVo
     * @return boolean    
     */
	@Override
	public boolean updateInstance(OriginVo originVo, InstanceVo instanceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改Instance，请求参数：" + JSonUtil.toJSonString(instanceVo));
		Instance instance = (Instance) BeanUtil.copyProperties(Instance.class, instanceVo);

		Boolean result = instanceLogic.updateInstance(instance); // 调用Logic修改Instance

		return null == result ? false : result;
	}



    /**
     * 根据id修改单个 Instance
     * @param originVo 源信息对象
     * @param id
     * @return InstanceVo
     */
	@Override
	public boolean updateInstanceById(OriginVo originVo, InstanceVo instanceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id修改单个Instance，请求参数：" + JSonUtil.toJSonString(instanceVo));
		Instance instance = (Instance) BeanUtil.copyProperties(Instance.class, instanceVo);

		Boolean result = instanceLogic.updateInstanceById(instance); // 调用Logic修改Instance

		return null == result ? false : result;
	}



    /**
     * 根据id修改单个 Instance
     * @param originVo 源信息对象
     * @param instanceId
     * @return InstanceVo
     */
	@Override
	public boolean updateInstanceByInstanceId(OriginVo originVo, InstanceVo instanceVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据instanceId修改单个Instance，请求参数：" + JSonUtil.toJSonString(instanceVo));
		Instance instance = (Instance) BeanUtil.copyProperties(Instance.class, instanceVo);

		Boolean result = instanceLogic.updateInstanceByInstanceId(instance); // 调用Logic修改Instance

		return null == result ? false : result;
	}



    /**
     * 根据id查询单个 Instance
     * @param id
     * @return InstanceVo
     */
	@Override
	public InstanceVo getInstanceById(long id) throws TException {
		LogCvt.info("根据id查询单个Instance，请求参数：" + JSonUtil.toJSonString(id));

		Instance instance = instanceLogic.findInstanceById(id); // 调用Logic查询单个Instance

		InstanceVo instanceVo = (InstanceVo) BeanUtil.copyProperties(InstanceVo.class, instance);
		return instanceVo;
	}



    /**
     * 根据instanceId查询单个 Instance
     * @param instanceId
     * @return InstanceVo
     */
	@Override
	public InstanceVo getInstanceByInstanceId(String instanceId) throws TException {
		LogCvt.info("根据instanceId查询单个Instance，请求参数：" + JSonUtil.toJSonString(instanceId));

		Instance instance = instanceLogic.findInstanceByInstanceId(instanceId); // 调用Logic查询单个Instance

		InstanceVo instanceVo = (InstanceVo) BeanUtil.copyProperties(InstanceVo.class, instance);
		return instanceVo;
	}



    /**
     * 根据条件查询一个 Instance
     * @param instanceVo
     * @return InstanceVo
     */
	@Override
	public InstanceVo getOneInstance(InstanceVo instanceVo) throws TException {
		LogCvt.info("根据条件查询一个Instance，请求参数：" + JSonUtil.toJSonString(instanceVo));
		Instance instance = (Instance) BeanUtil.copyProperties(Instance.class, instanceVo);

		instance = instanceLogic.findOneInstance(instance); // 调用Logic查询单个Instance

		instanceVo = (InstanceVo) BeanUtil.copyProperties(InstanceVo.class, instance);
		return instanceVo;
	}



    /**
     * 根据条件统计 Instance
     * @param instanceVo
     * @return int
     */
	@Override
	public int countInstance(InstanceVo instanceVo) throws TException {
		LogCvt.info("根据条件统计Instance，请求参数：" + JSonUtil.toJSonString(instanceVo));
		Instance instance = (Instance) BeanUtil.copyProperties(Instance.class, instanceVo);

		return instanceLogic.countInstance(instance); // 调用Logic统计Instance

	}



    /**
     * 查询 Instance
     * @param instanceVo
     * @return List<InstanceVo>
     */
	@Override
	public List<InstanceVo> getInstance(InstanceVo instanceVo) throws TException {
		LogCvt.info("查询Instance，请求参数：" + JSonUtil.toJSonString(instanceVo));
		Instance instance = (Instance) BeanUtil.copyProperties(Instance.class, instanceVo);

		List<Instance> instanceList = instanceLogic.findInstance(instance); // 调用Logic查询Instance

		List<InstanceVo> instanceVoList = (List<InstanceVo>) BeanUtil.copyProperties(InstanceVo.class, instanceList);
		return instanceVoList;
	}



    /**
     * 分页查询 Instance
     * @param instanceVo
     * @return InstancePageVoRes
     */
	@Override
	public InstancePageVoRes getInstanceByPage(PageVo pageVo, InstanceVo instanceVo) throws TException {
		LogCvt.info("分页查询Instance，请求参数：PageVo==>" + JSonUtil.toJSonString(pageVo) + "\t instanceVo==>" + JSonUtil.toJSonString(instanceVo));
		Page<Instance> page = (Page<Instance>) BeanUtil.copyProperties(Page.class, pageVo);
		Instance instance = (Instance) BeanUtil.copyProperties(Instance.class, instanceVo);

		page = instanceLogic.findInstanceByPage(page, instance); // 调用Logic分页查询Instance

		List<InstanceVo> instanceVoList = (List<InstanceVo>) BeanUtil.copyProperties(InstanceVo.class, page.getResultsContent());
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);

		InstancePageVoRes instancePageVoRes = new InstancePageVoRes();
		instancePageVoRes.setPage(pageVo);
		instancePageVoRes.setInstanceVoList(instanceVoList);
		return instancePageVoRes;
	}


}
