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


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.FallowQueryLogic;
import com.froad.logic.impl.FallowQueryLogicImpl;
import com.froad.po.QueryAlreadyAuitListReq;
import com.froad.po.QueryApplyAuitListReq;
import com.froad.po.QueryAttrReq;
import com.froad.po.QueryAttrRes;
import com.froad.po.QueryMongoPageRes;
import com.froad.po.QueryPendAuitCountReq;
import com.froad.po.QueryPendAuitListReq;
import com.froad.po.QueryTaskOverviewReq;
import com.froad.po.QueryTaskReq;
import com.froad.po.QueryTaskRes;
import com.froad.po.mongo.AuditInstanceDetail;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.FallowQueryService;
import com.froad.thrift.vo.AuditInstanceDetailVo;
import com.froad.thrift.vo.GetAlreadyAuitListPageVo;
import com.froad.thrift.vo.GetAlreadyAuitListReqVo;
import com.froad.thrift.vo.GetApplyAuitListPageVo;
import com.froad.thrift.vo.GetApplyAuitListReqVo;
import com.froad.thrift.vo.GetInstanceIdByAttrReqVo;
import com.froad.thrift.vo.GetInstanceIdByAttrResVo;
import com.froad.thrift.vo.GetPendAuitCountReqVo;
import com.froad.thrift.vo.GetPendAuitCountResVo;
import com.froad.thrift.vo.GetPendAuitListPageVo;
import com.froad.thrift.vo.GetPendAuitListReqVo;
import com.froad.thrift.vo.GetTaskOverviewReqVo;
import com.froad.thrift.vo.GetTaskOverviewResVo;
import com.froad.thrift.vo.GetTaskReqVo;
import com.froad.thrift.vo.GetTaskResVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;

/**
 * 
 * <p>
 * @Title: TaskImpl.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public class FallowQueryServiceImpl extends BizMonitorBaseService implements FallowQueryService.Iface {
	private FallowQueryLogic fallowQueryLogic = new FallowQueryLogicImpl();

	public FallowQueryServiceImpl() {
	}

	public FallowQueryServiceImpl(String name, String version) {
		super(name, version);
	}


	@Override
	public GetTaskResVo getTaskList(GetTaskReqVo req) throws TException {
		LogCvt.info("查询审核任务信息列表接口信息getTaskList,请求参数:" + JSonUtil.toJSonString(req));
		QueryTaskReq queryTaskReq = (QueryTaskReq)BeanUtil.copyProperties(QueryTaskReq.class, req);
		QueryTaskRes queryTaskRes = fallowQueryLogic.getTaskList(queryTaskReq);
		GetTaskResVo resp = (GetTaskResVo)BeanUtil.copyProperties(GetTaskResVo.class, queryTaskRes);
		LogCvt.info("查询审核任务信息列表接口getTaskList,返回参数:" + JSonUtil.toJSonString(resp));
		return resp;
	}

	@Override
	public GetInstanceIdByAttrResVo getInstanceIdByAttr(GetInstanceIdByAttrReqVo req) throws TException {
		LogCvt.info("根据业务(商户、门店、商品)ID查询审核流水号getInstanceIdByAttr,请求参数:" + JSonUtil.toJSonString(req));
		QueryAttrReq attrReq = (QueryAttrReq)BeanUtil.copyProperties(QueryAttrReq.class, req);
		QueryAttrRes attrRes = fallowQueryLogic.getInstanceIdByAttr(attrReq);
		GetInstanceIdByAttrResVo resp = (GetInstanceIdByAttrResVo)BeanUtil.copyProperties(GetInstanceIdByAttrResVo.class, attrRes);
		LogCvt.info("根据业务(商户、门店、商品)ID查询审核流水号getInstanceIdByAttr,返回参数:" + JSonUtil.toJSonString(resp));
		return resp;
	}

	@Override
	public GetTaskOverviewResVo getTaskOverview(GetTaskOverviewReqVo req) throws TException {
		LogCvt.info("当前审核概要信息getTaskOverview,请求参数:" + JSonUtil.toJSonString(req));
		GetTaskOverviewResVo resp = (GetTaskOverviewResVo) BeanUtil.copyProperties(GetTaskOverviewResVo.class, fallowQueryLogic.getTaskOverview((QueryTaskOverviewReq) BeanUtil.copyProperties(QueryTaskOverviewReq.class, req)));
		LogCvt.info("当前审核概要信息接口getTaskOverview,返回参数:" + JSonUtil.toJSonString(resp));
		return resp;
	}

	@Override
	public GetPendAuitListPageVo getPendAuitList(GetPendAuitListReqVo req, PageVo pageVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("待审核查询接口[待办箱]（getPendAuitList）,请求参数:"+JSON.toJSONString(req)+",pageVo:"+JSON.toJSONString(pageVo));
		GetPendAuitListPageVo resp = new GetPendAuitListPageVo();
		QueryMongoPageRes mongoRes = null;

		QueryPendAuitListReq pendReq = (QueryPendAuitListReq) BeanUtil.copyProperties(QueryPendAuitListReq.class, req);
		MongoPage page = (MongoPage) BeanUtil.copyProperties(MongoPage.class, pageVo);
		mongoRes = fallowQueryLogic.getPendAuitListByPage(page, pendReq);
		resp = (GetPendAuitListPageVo) BeanUtil.copyProperties(GetPendAuitListPageVo.class, mongoRes);
//		resp.setAuditDetailList((List<AuditInstanceDetailVo>) BeanUtil.copyProperties(AuditInstanceDetailVo.class, mongoRes.getPage().getItems()));
			
		List<AuditInstanceDetail> listArrt = extracted(mongoRes.getPage());
		List<AuditInstanceDetailVo> auditDetailList = new ArrayList<AuditInstanceDetailVo>();
         if(null != listArrt){
         	for(AuditInstanceDetail audit : listArrt){
         		AuditInstanceDetailVo auditRes = (AuditInstanceDetailVo)BeanUtil.copyProperties(AuditInstanceDetailVo.class, audit);
         		auditRes.setBessData(ObjectUtils.toString(audit.getBessData(), "{}"));
         		auditDetailList.add(auditRes);
 			}
         	resp.setAuditDetailList(auditDetailList);
         }
			
		LogCvt.info("查询接口[待办箱](getPendAuitList),返回参数:" +JSonUtil.toJSonString(resp));
		return resp;
	}

	@Override
	public GetPendAuitCountResVo getPendAuitCount(GetPendAuitCountReqVo req) throws TException {
		LogCvt.info("待审核数量查询接口[待办箱]（getPendAuitCount）,请求参数:" + JSON.toJSONString(req));
		QueryPendAuitCountReq qreq = (QueryPendAuitCountReq) BeanUtil.copyProperties(QueryPendAuitCountReq.class, req);
		GetPendAuitCountResVo resp = (GetPendAuitCountResVo) BeanUtil.copyProperties(GetPendAuitCountResVo.class, fallowQueryLogic.getPendAuitCount(qreq));
		LogCvt.info("待审核数量查询接口[待办箱]（getPendAuitCount）,返回参数:" + JSonUtil.toJSonString(resp));
		return resp;
	}

	@Override
	public GetApplyAuitListPageVo getApplyAuitList(GetApplyAuitListReqVo req, PageVo pageVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("申请审核任务查询接口[申请箱](getApplyAuitList),请求参数:" + JSonUtil.toJSonString(req));
		QueryMongoPageRes mongoRes = null;
		GetApplyAuitListPageVo resp = new GetApplyAuitListPageVo();
		QueryApplyAuitListReq applyReq = (QueryApplyAuitListReq)BeanUtil.copyProperties(QueryApplyAuitListReq.class, req);
		MongoPage page = (MongoPage)BeanUtil.copyProperties(MongoPage.class, pageVo);
		mongoRes = fallowQueryLogic.getApplyAuitListByPage(page, applyReq);
		resp = (GetApplyAuitListPageVo) BeanUtil.copyProperties(GetApplyAuitListPageVo.class, mongoRes);
//			resp.setAuditDetailList((List<AuditInstanceDetailVo>) BeanUtil.copyProperties(AuditInstanceDetailVo.class, mongoRes.getPage().getItems()));
//			resut = (ResultVo)BeanUtil.copyProperties(ResultVo.class, mongoRes.getResult());
		List<AuditInstanceDetail> listArrt = extracted(mongoRes.getPage());
		List<AuditInstanceDetailVo> auditDetailList = new ArrayList<AuditInstanceDetailVo>();
		if (null != listArrt) {
			for (AuditInstanceDetail audit : listArrt) {
				AuditInstanceDetailVo auditRes = (AuditInstanceDetailVo) BeanUtil.copyProperties(AuditInstanceDetailVo.class, audit);
				auditRes.setBessData(ObjectUtils.toString(audit.getBessData(), "{}"));
				auditDetailList.add(auditRes);
			}
			resp.setAuditDetailList(auditDetailList);
			// LogCvt.info("查询【申请箱】Logic返回参数:" +JSonUtil.toJSonString(auditDetailList));
		}

		LogCvt.info("申请审核任务查询接口[申请箱](getApplyAuitList),返回参数:" + JSonUtil.toJSonString(resp));
		
		return resp;
	}

	@SuppressWarnings("unchecked")
	private List<AuditInstanceDetail> extracted(MongoPage mongoPage) {
		return null == mongoPage ? null : (List<AuditInstanceDetail>) mongoPage.getItems();
	}

	@Override
	public GetAlreadyAuitListPageVo getAlreadyAuitList(GetAlreadyAuitListReqVo req, PageVo pageVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("已办理审核信息查询接口[已办箱](getAlreadyAuitList),请求参数:" + JSonUtil.toJSonString(req));
		GetAlreadyAuitListPageVo resp = new GetAlreadyAuitListPageVo();
		QueryMongoPageRes mongoRes = null;
		QueryAlreadyAuitListReq alreadyReq = (QueryAlreadyAuitListReq) BeanUtil.copyProperties(QueryAlreadyAuitListReq.class, req);
		MongoPage page = (MongoPage) BeanUtil.copyProperties(MongoPage.class, pageVo);
		mongoRes = fallowQueryLogic.getAlreadyAuitListByPage(page, alreadyReq);
		resp = (GetAlreadyAuitListPageVo) BeanUtil.copyProperties(GetAlreadyAuitListPageVo.class, mongoRes);
//		resp.setAuditDetailList((List<AuditInstanceDetailVo>) BeanUtil.copyProperties(AuditInstanceDetailVo.class, mongoRes.getPage().getItems()));

		List<AuditInstanceDetail> listArrt = extracted(mongoRes.getPage());
		List<AuditInstanceDetailVo> auditDetailList = new ArrayList<AuditInstanceDetailVo>();
		if (null != listArrt) {
			for (AuditInstanceDetail audit : listArrt) {
				AuditInstanceDetailVo auditRes = (AuditInstanceDetailVo) BeanUtil.copyProperties(AuditInstanceDetailVo.class, audit);
				auditRes.setBessData(ObjectUtils.toString(audit.getBessData(), "{}"));
				auditDetailList.add(auditRes);
			}
			resp.setAuditDetailList(auditDetailList);
			// LogCvt.info("查询[已办箱]Logic返回参数:" +JSonUtil.toJSonString(auditDetailList));
		}
		LogCvt.info("已办理审核信息查询接口[已办箱](getAlreadyAuitList),返回参数:" + JSonUtil.toJSonString(resp));
		return resp;
	}

	

}
