package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.AuditTaskLogic;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantAduitCommonLogic;
import com.froad.logic.OrgLogic;
import com.froad.logic.impl.AuditTaskLogicImpl;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.MerchantAduitCommonLogicImpl;
import com.froad.logic.impl.OrgLogicImpl;
import com.froad.po.AuditTask;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.AuditTaskService;
import com.froad.thrift.vo.AuditTaskFilterVo;
import com.froad.thrift.vo.AuditTaskPageDetailVo;
import com.froad.thrift.vo.AuditTaskPageVoRes;
import com.froad.thrift.vo.AuditTaskVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;


/**
 * 
 * <p>@Title: AuditTaskServiceImpl.java</p>
 * <p>Description: 描述 </p> 审核任务订单Service类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年8月18日
 */
public class AuditTaskServiceImpl extends BizMonitorBaseService implements AuditTaskService.Iface {
	private AuditTaskLogic auditTaskLogic = new AuditTaskLogicImpl();
	private MerchantAduitCommonLogic merchantAuditCommonLogic = new MerchantAduitCommonLogicImpl();
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	public AuditTaskServiceImpl(String name, String version){
		super(name, version);
	}

	/**
     * 查询待审核任务订单详情
     * @param thridId 若查商户待审核详情，则传商户id
     * @return AuditTaskVo
     */
	@Override
	public AuditTaskVo getAuditTaskWait(String thridId) throws TException {
		if(Checker.isEmpty(thridId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new AuditTaskVo();
		}
		
		AuditTaskVo auditTaskVo = null;
		try {
			AuditTask  auditTask = merchantAuditCommonLogic.getAuditTaskByMerchantId(thridId);
			auditTaskVo = (AuditTaskVo)BeanUtil.copyProperties(AuditTaskVo.class, auditTask);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
		}
		
		
		return auditTaskVo==null?new AuditTaskVo():auditTaskVo;
	}

	
	/**
     * 查询审核流水号详情
     * @param auditId 审核流水号
     * @return AuditTaskVo
     */
	@Override
	public AuditTaskVo getAuditTaskByAuditId(String auditId) throws TException {
		if(Checker.isEmpty(auditId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new AuditTaskVo();
		}
		
		AuditTaskVo auditTaskVo = null;
		try {
			AuditTask  auditTask = merchantAuditCommonLogic.getAuditTaskByAuditId(auditId);
			auditTaskVo = (AuditTaskVo)BeanUtil.copyProperties(AuditTaskVo.class, auditTask);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
		}
		
		
		return auditTaskVo==null?new AuditTaskVo():auditTaskVo;
	}
	
	
	/**
     * 分页查询 AuditTask
     * @param page 分页对象
     * @param auditTaskFilterVo 过滤对象
     * @param flag 查询权限标志 1-查当前机构 2-查当前机构所有下属机构
     * @return AuditTaskPageVoRes
     */
	@Override
	public AuditTaskPageVoRes getAuditTaskByPage(PageVo pageVo,AuditTaskFilterVo auditTaskFilterVo, int flag) throws TException {
		if((flag!=1 && flag!=2) || Checker.isEmpty(auditTaskFilterVo) || Checker.isEmpty(auditTaskFilterVo.getOrgCode())){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new AuditTaskPageVoRes();
		}
		
		Page<AuditTask> page = (Page<AuditTask>)BeanUtil.copyProperties(Page.class, pageVo);
		AuditTask auditTask = (AuditTask)BeanUtil.copyProperties(AuditTask.class, auditTaskFilterVo);

		page = auditTaskLogic.findAuditTaskByPage(page, auditTask, flag);

		
		List<AuditTaskVo> auditTaskVoList = (List<AuditTaskVo>)BeanUtil.copyProperties(AuditTaskVo.class, page.getResultsContent());
		//设置上级机构
		List<AuditTaskPageDetailVo> auditTaskPageDetailVoList = new ArrayList<AuditTaskPageDetailVo>();
		AuditTaskPageDetailVo detailVo = null;
		for(AuditTaskVo taskVo : auditTaskVoList){
			detailVo = new AuditTaskPageDetailVo();
			detailVo.setAuditId(taskVo.getAuditId());
			detailVo.setCreateTime(taskVo.getCreateTime());
			detailVo.setType(taskVo.getType());
			detailVo.setName(taskVo.getName());
			detailVo.setAuditState(taskVo.getAuditState());
			detailVo.setOrgName(taskVo.getOrgName());
			detailVo.setSuperOrgName(commonLogic.getSuperOrg(taskVo.getClientId(), taskVo.getOrgCode()).getOrgName());
			detailVo.setAuditTime(taskVo.getAuditTime());
			detailVo.setClientId(taskVo.getClientId());
			
			
			auditTaskPageDetailVoList.add(detailVo);
		}
		
		
		
		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
		
		//设置返回值
		AuditTaskPageVoRes auditTaskPageVoRes = new AuditTaskPageVoRes();
		auditTaskPageVoRes.setAuditTaskVoList(auditTaskPageDetailVoList);
		auditTaskPageVoRes.setPage(pageVo);
		
		
		return auditTaskPageVoRes;
	}
	
	

	

}
