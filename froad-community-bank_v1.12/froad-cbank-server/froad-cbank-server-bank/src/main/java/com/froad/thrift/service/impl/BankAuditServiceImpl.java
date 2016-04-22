package com.froad.thrift.service.impl;

import java.util.List;

import org.apache.thrift.TException;

import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.BankAuditLogic;
import com.froad.logic.impl.BankAuditLogicImpl;
import com.froad.logic.res.PreAuditNumRes;
import com.froad.po.BankAudit;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BankAuditService;
import com.froad.thrift.vo.BankAuditVo;
import com.froad.thrift.vo.BankAuditVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PreAuditNumVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;

/**
 * 
 * <p>@Title: BankAuditServiceImpl.java</p>
 * <p>Description: 描述 </p> 审核服务实现thrift
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月3日
 */
public class BankAuditServiceImpl extends BizMonitorBaseService implements BankAuditService.Iface {

	private  BankAuditLogic  bankAuditLogic = new  BankAuditLogicImpl();
	public BankAuditServiceImpl(String name, String version){
		super(name, version);
	};
	


	/**
     * 批量审核
     * @param auditFlag 审核标志 1-商品 2-商户
     * @param bankAuditVoList 审核对象
     * @return String   审核失败的审核对象id及名称(id:name)  审核成功返回OK  审核异常返回ERROR
     */
	@Override
	public BankAuditVoRes auditBatch(OriginVo originVo,int auditFlag, List<BankAuditVo> bankAuditVoList)
			throws TException {
		
		BankAuditVoRes bankAuditVoRes = new BankAuditVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			if (auditFlag == 1) {
				originVo.setDescription("审核商品");
			}else if(auditFlag == 2){
				originVo.setDescription("审核商户");
			}else if(auditFlag == 3){
				originVo.setDescription("审核秒杀商品");
			}else{
				originVo.setDescription("审核");
			}
			LogUtils.addLog(originVo);
			
			if(Checker.isEmpty(bankAuditVoList)){
				throw new FroadServerException(originVo.getDescription()+"失败,原因:bankAuditVoList不能为空!");
			}
			if(auditFlag!=1 && auditFlag!=2 && auditFlag!=3){
				throw new FroadServerException(originVo.getDescription()+"失败,原因:auditFlag不符合系统定义值!");
			}
			if(Checker.isEmpty(originVo.getOperatorId())){
				throw new FroadServerException(originVo.getDescription()+"失败,原因:源地址中的OperatorId不能为空！");
			}
			
			List<BankAudit> bankAuditList=(List<BankAudit>)BeanUtil.copyProperties(BankAudit.class, bankAuditVoList);
			
			String[] logicResult = bankAuditLogic.auditBatch(auditFlag,originVo.getOperatorId(),bankAuditList);
			if (!logicResult[0].equals("OK")) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc(logicResult[0]);
			}
			bankAuditVoRes.setIsFinalAudit(logicResult[1].equals("1")?true:false);
		} catch (FroadServerException e) {
			LogCvt.error("审核"+(auditFlag==1?"商品":"商户")+"auditBatch失败，原因:" + e.getMessage(), e);  
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("审核"+(auditFlag==1?"商品":"商户")+"auditBatch失败，原因:" + e.getMessage(), e); 
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		
		bankAuditVoRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		return bankAuditVoRes;
		
	}
	
	 /**
     * 查询待审核数量
     * @param  clientId 客户端id
     * @param orgCode 机构编号
     * @return PreAuditNumVo待审核数量
     */
	@Override
    public PreAuditNumVo getPreAuditNumRes(String clientId, String orgCode) throws org.apache.thrift.TException{
		if(Checker.isEmpty(clientId) || Checker.isEmpty(orgCode)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new PreAuditNumVo();
		}
		
		PreAuditNumRes rs = bankAuditLogic.getPreAuditNumRes(clientId, orgCode);
		PreAuditNumVo rsV=(PreAuditNumVo)BeanUtil.copyProperties(PreAuditNumVo.class, rs);
		return rsV==null?new PreAuditNumVo():rsV;
	}

    
}
