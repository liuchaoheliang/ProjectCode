package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.enums.ProductAuditStage;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.AuditProcessLogic;
import com.froad.logic.impl.AuditProcessLogicImpl;
import com.froad.po.AuditProcess;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.AuditProcessService;
import com.froad.thrift.vo.AuditProcessVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;



/**
 * 
 * <p>@Title: AuditProcessServiceImpl.java</p>
 * <p>Description: 描述 </p> 审核任务Service类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年8月18日
 */
public class AuditProcessServiceImpl extends BizMonitorBaseService implements AuditProcessService.Iface {
	private AuditProcessLogic auditProcessLogic = new AuditProcessLogicImpl();
	public AuditProcessServiceImpl(String name, String version){
		super(name, version);
	}

	/**
     * 查询审核任务列表
     * @param auditId 审核流水号
     * @return List<AuditProcessVo>
     * 
     */
	@Override
	public List<AuditProcessVo> getAuditProcess(String auditId) throws TException {
		if(Checker.isEmpty(auditId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<AuditProcessVo>();
		}
		
		List<AuditProcess> auditTaskList = auditProcessLogic.findAuditProcess(auditId);
		List<AuditProcess> resultAuditTaskList = new ArrayList<AuditProcess>();
		//若复审，则将复核人放置审核人中进行返回
		for(AuditProcess po : auditTaskList){
			if(Checker.isNotEmpty(po.getAuditStage()) && po.getAuditStage().equals(ProductAuditStage.secondAudit)){
				po.setAuditStaff(po.getReviewStaff());//复核人放置auditStaff中
			}
			resultAuditTaskList.add(po);
		}
		List<AuditProcessVo> auditTaskVo = (List<AuditProcessVo>)BeanUtil.copyProperties(AuditProcessVo.class, resultAuditTaskList);
		
		return auditTaskVo==null?new ArrayList<AuditProcessVo>():auditTaskVo;
	}

	
	
	

	

}
