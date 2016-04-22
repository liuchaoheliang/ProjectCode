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
 * @Title: ActivitiesImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.enums.ActivitiesStatus;
import com.froad.enums.EnableType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.ActivitiesLogic;
import com.froad.logic.impl.ActivitiesLogicImpl;
import com.froad.po.Activities;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ActivitiesService;
import com.froad.thrift.vo.ActivitiesPageVoRes;
import com.froad.thrift.vo.ActivitiesVo;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: ActivitiesImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ActivitiesServiceImpl  extends BizMonitorBaseService implements ActivitiesService.Iface {
	private ActivitiesLogic activitiesLogic = new ActivitiesLogicImpl();
	public ActivitiesServiceImpl() {}

	public ActivitiesServiceImpl(String name, String version) {
		super(name, version);
	}
	
	
	/**
	 * 非空验证
	 * @param resultVo
	 * @param addResultVo
	 * @param activitiesVo
	 * @return
	 */
	public AddResultVo verification(ResultVo resultVo,AddResultVo addResultVo,Activities activities){
		
//		if(activities.getActivitiesType()== null  || "".equals(activities.getActivitiesType())){
//			LogCvt.error("添加活动失败,原因:活动类型不能为空!");
//			resultVo.setResultDesc("添加活动失败,原因:活动类型不能为空!");
//			resultVo.setResultCode(ResultCode.failed.getCode());
//			addResultVo.setResultVo(resultVo);
//			return addResultVo;
//		}
		if(activities.getClientId() == null || "".equals(activities.getClientId())){
			LogCvt.error("添加Activities失败,原因:ClientId不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:ClientId不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(activities.getActivitiesName()== null  || "".equals(activities.getActivitiesName())){
			LogCvt.error("添加Activities失败,原因:活动名称ActivitiesName不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:活动名称不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if( activities.getPoints() == null || activities.getPoints() <= 0 ){
			LogCvt.error("添加Activities失败,原因:赠送积分数量points不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:赠送积分数量不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if( activities.getBeginTime() ==null){
			LogCvt.error("添加Activities失败,原因:活动开始时间BeginTime不能为0!");
			resultVo.setResultDesc("添加活动失败,原因:活动开始时间不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if( activities.getEndTime() ==null){
			LogCvt.error("添加Activities失败,原因:活动结束时间EndTime不能为0!");
			resultVo.setResultDesc("添加活动失败,原因:活动结束时间不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if( activities.getCount() == null){
			
			LogCvt.error("添加Activities失败,原因:绑定商品数count不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:绑定商品数不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}
    /**
     * 增加 Activities
     * @param activities
     * @return long    主键ID
     */
	@Override
	public AddResultVo addActivities(OriginVo originVo,ActivitiesVo activitiesVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加Activities");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		Activities activities = (Activities)BeanUtil.copyProperties(Activities.class, activitiesVo);
		//非空验证
		addResultVo=verification(resultVo,addResultVo,activities);
		if(!StringUtils.equals(ResultCode.success.getCode(),addResultVo.getResultVo().getResultCode())){
			return addResultVo;
		}
		if(activities.getStatus() == null)
		{
			activities.setStatus(ActivitiesStatus.no.getCode());
		}
		activities.setCreateTime(new Date());
		ResultBean result = activitiesLogic.addActivities(activities);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加活动信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId((Long)result.getData());
		return addResultVo;
	}



    /**
     * 删除 Activities
     * @param long activitiesId
     * @return boolean    
     */
	@Override
	public ResultVo deleteActivities(OriginVo originVo, String clientId, long activitiesId) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除Activities");
//		Activities activities = (Activities)BeanUtil.copyProperties(Activities.class, activitiesVo);
		ResultBean result = activitiesLogic.deleteActivities(clientId, activitiesId);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"删除活动成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 修改 Activities
     * @param activities
     * @return boolean    
     */
	@Override
	public ResultVo updateActivities(OriginVo originVo,ActivitiesVo activitiesVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改Activities");
		Activities activities = (Activities)BeanUtil.copyProperties(Activities.class, activitiesVo);
		ResultBean result = activitiesLogic.updateActivities(activities);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"修改活动成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}


   
    /**
     * 查询 Activities
     * @param activities
     * @return List<ActivitiesVo>
     */
	@Override
	public List<ActivitiesVo> getActivities(ActivitiesVo activitiesVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询Activities");
		Activities activities = (Activities)BeanUtil.copyProperties(Activities.class, activitiesVo);
		List<Activities> activitiesList = activitiesLogic.findActivities(activities);
		
		List<ActivitiesVo> activitiesVoList = new ArrayList<ActivitiesVo>();
		for (Activities po : activitiesList) {
			ActivitiesVo vo = (ActivitiesVo)BeanUtil.copyProperties(ActivitiesVo.class, po);
			activitiesVoList.add(vo);
		}
		return activitiesVoList;
	}



    /**
     * 分页查询 Activities
     * @param activities
     * @return ActivitiesPageVoRes
     */
//	@Override
	@Override
	public ActivitiesPageVoRes getActivitiesByPage(PageVo pageVo, ActivitiesVo activitiesVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询");
		Page<Activities> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		Activities activities = (Activities)BeanUtil.copyProperties(Activities.class, activitiesVo);
		page = activitiesLogic.findActivitiesByPage(page, activities);

		ActivitiesPageVoRes activitiesPageVoRes = new ActivitiesPageVoRes();
		List<ActivitiesVo> activitiesVoList = new ArrayList<ActivitiesVo>();
		for (Activities po : page.getResultsContent()) {
			ActivitiesVo vo = (ActivitiesVo)BeanUtil.copyProperties(ActivitiesVo.class, po);
			activitiesVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		activitiesPageVoRes.setPage(pageVo);
		activitiesPageVoRes.setActivitiesVoList(activitiesVoList);
		return activitiesPageVoRes;
	}

	/**
     * 查询 Activities
     * @return ActivitiesVo
     * 
     * @param clientId
     * @param id
     */
	@Override
	public ActivitiesVo getActivitiesById(String clientId, long id)
			throws TException {
		// TODO Auto-generated method stub
		
		Activities activities = activitiesLogic.getActivitiesById(clientId, id);
		ActivitiesVo activitiesVo = (ActivitiesVo)BeanUtil.copyProperties(ActivitiesVo.class, activities);
		return activitiesVo;
	}
	
	
}
