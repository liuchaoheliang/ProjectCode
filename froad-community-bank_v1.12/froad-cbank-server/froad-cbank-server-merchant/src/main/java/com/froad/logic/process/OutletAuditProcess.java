package com.froad.logic.process;

import org.apache.thrift.TException;

import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.OutletAuditLogic;
import com.froad.logic.OutletLogic;
import com.froad.logic.impl.OutletAuditLogicImpl;
import com.froad.logic.impl.OutletLogicImpl;
import com.froad.po.Outlet;
import com.froad.po.OutletTemp;
import com.froad.po.Result;
import com.froad.po.mq.AuditMQ;
import com.froad.service.AbstractProcessService;
import com.froad.util.JSonUtil;
import com.froad.support.DataPlatLogSupport;


/**
 * @author user
 *
 */
public class OutletAuditProcess extends AbstractProcessService {
	private OutletAuditLogic outletAuditLogic = new OutletAuditLogicImpl();
	private OutletLogic outletLogic = new OutletLogicImpl();
	/**
	 * 门店审核处理服务.
	 */
	@Override
	public void processMsg(String msg) {
		
		try {
			LogCvt.info("处理门店审核消息:"+msg);
			AuditMQ auditMq = JSonUtil.toObject(msg, AuditMQ.class);
			
			//如果是层级审并审核通过时，暂不处理业务，需要等终审或层级审核拒绝在处理业务
			if(ProductAuditState.passAudit.getCode().equals(auditMq.getAuditState()) && !auditMq.getIsFinalAudit()){
				return;
			}
			//如果是未提交审核状态,更改编辑审核流水号，编辑审核状态
			if(ProductAuditState.noAudit.getCode().equals(auditMq.getAuditState())){
				LogCvt.info("审核状态为待审核，开始提交审核操作");
				commitAuditOutlet(auditMq.getResult(),auditMq.getBusinessId(),auditMq.getAuditId());
			}else{
				//根据 businessId业务Id搜索门店记录，根据搜索到的记录auditState判断是录入审核还是编辑审核 */
				Outlet outlet = outletLogic.findOutletByOutletId(auditMq.getBusinessId());
				if(outlet!=null){
					LogCvt.info("审核门店信息："+outlet.toString()+",auditState="+outlet.getAuditState());
					//审核状态auditState为1代表录入审核通过，故进入编辑审核分支
					if(outlet.getAuditState().equals(ProductAuditState.passAudit.getCode())){
						if(outlet.getEditAuditState().equals(ProductAuditState.passAudit.getCode())){
							throw new FroadServerException("MQ审核结果处理异常：原因是审核已完成！");
						}
						//编辑审核
						editOutletAuditProcess(auditMq,outlet);
					}else{
						//录入审核
						if((!outlet.getAuditState().equals(ProductAuditState.noAudit.getCode()))
							&&	
							//总行提交审核直接变成审核通过，不需要在银行pc审核。 具体值以审核系统返回的为准
							(!outlet.getAuditState().equals(ProductAuditState.noCommit.getCode()))
						){
							throw new FroadServerException("MQ审核结果处理异常：审核状态异常！,AuditState="+outlet.getAuditState());
						}
						addOutletAuditProcess(auditMq,outlet);
					}
				}else{
					throw new FroadServerException("MQ审核结果处理异常：找不到门店信息！");
				}	
			}	
		} catch (FroadServerException fe) {
			LogCvt.error(fe.getMessage(), fe);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 编辑门店处理服务.
	 * 
	 * @param msg
	 */
	private void editOutletAuditProcess(AuditMQ auditMq,Outlet outlet){
		LogCvt.info("门店编辑审核队列处理开始...");
		long st = System.currentTimeMillis();
		
		try {
		    Result	result = outletAuditLogic.auditOutletEdit(auditMq,outlet);
			if(result.getResultCode().equals(ResultCode.success.getCode())){
			  LogCvt.info("门店审核处理编辑审核操作成功,auditId="+auditMq.getAuditId()+",审核状态="+auditMq.getAuditState()+",审核描述="+auditMq.getAuditComment()+"终审状态="+auditMq.getIsFinalAudit());
			}
		} catch (Exception e) {
			LogCvt.error("门店审核处理编辑审核操作失败,auditId="+auditMq.getAuditId()+",审核状态="+auditMq.getAuditState()+
					",审核描述="+auditMq.getAuditComment()+"终审状态="+auditMq.getIsFinalAudit()+", 原因:" + e.getMessage(), e);
		}
		LogCvt.info("[门店管理]-门店编辑审核队列处理-结束！总耗时（" + (System.currentTimeMillis() - st) + "）ms");
	}
	
	/**
	 * 录入门店审核处理服务.
	 * 
	 * 审核通过流程如下：
	 * 1.更新cb_outlet的auditState(审核通过=1，审核拒绝=2)
	 * 2.发送账户白名单登记，并根据登记结果修改cb_merchantAccount表的同步字段及审核字段（台州需要走银行审核落地流程，其他银行只需要登记即为审核通过）
	 * 3.当白名单登记失败时，会通过自动批业务进行补登。
	 * 
	 * 审核拒绝流程如下：
	 * 1.更新cb_outlet的auditState(审核通过=1，审核拒绝=2)
	 * 
	 * @param msg
	 */
	private void addOutletAuditProcess(AuditMQ auditMq,Outlet outlet){
		LogCvt.info("门店录入审核队列处理开始...");
		long st = System.currentTimeMillis();
		
		try {
		    Result	result = outletAuditLogic.auditOutlet(auditMq,outlet);
			if(result.getResultCode().equals(ResultCode.success.getCode())){
				new DataPlatLogSupport().auditOutletLog(outlet.getClientId(), outlet.getOutletId());
				LogCvt.info("门店录入审核操作成功，门店="+auditMq.getBusinessId()+"，审核流水="+auditMq.getAuditId()+
						"，审核状态="+auditMq.getAuditState()+",审核描述="+auditMq.getAuditComment());
			}
		} catch (Exception e) {
			LogCvt.error("门店录入审核操作异常！",e);
		}
		
		LogCvt.info("[门店管理]-门店录入审核队列处理-结束！总耗时（" + (System.currentTimeMillis() - st) + "）ms");
		
	}
	
	/**
	 * MQ内部处理审核状态为0的待审核门店(提交审核内部处理).
	 * @param result
	 * @param outletId
	 * @param auditId
	 * @throws TException
	 */
	private void commitAuditOutlet(Result result, String outletId,String auditId) throws TException {
		LogCvt.info("提交审核处理开始.... result="+result.getResultCode()+",outletId="+outletId+",auditId="+auditId);
		boolean isSucc = false;
		try {
			if (ResultCode.success.getCode().equals(result.getResultCode())) {
				// 1:查询门店状态
				Outlet outlet = outletLogic.findOutletByOutletId(outletId);
				if (outlet != null) {
					// audit_status审核状态为1 时，则为编辑审核， audit_status审核状态非1时为录入审核
					LogCvt.info("auditState="+outlet.getAuditState());
					if (outlet.getAuditState().equals(ProductAuditState.passAudit.getCode())) {
						if (outlet.getEditAuditState().equals(ProductAuditState.passAudit.getCode())) {
							throw new FroadServerException("提交审核失败，原因为审核状态为审核通过！");
						}
						// 执行编辑审核提交相关代码
						OutletTemp outletTemp = outletAuditLogic.findOutletTempByOutletId(outletId,"0");
						if (null != outletTemp) {
							outletTemp.setAuditId(auditId);// 更新审核流水号
							isSucc = outletAuditLogic.updateOutletTempByAuditId(outletTemp);// 执行更新
							LogCvt.info("更新审核流水号,auditId="+auditId+",outletId="+outletTemp.getOutletId());

							outlet.setEditAuditState(ProductAuditState.noAudit.getCode());// 更新审核状态为待审核
							isSucc = outletLogic.updateOutlet(outlet);// 执行更新
						} 
					} else {
						// 录入审核操作
						if (!outlet.getAuditState().equals(ProductAuditState.noCommit.getCode())) {
							throw new FroadServerException("提交审核失败，录入审核状态异常！");
						}
						outlet.setAuditState(ProductAuditState.noAudit.getCode());// 更新审核状态为待审核
						isSucc = outletLogic.updateOutlet(outlet);// 执行更新
					}
					if (isSucc) {
						LogCvt.info("提交审核操作成功,outletId=" + outletId+ ",auditId=" + auditId);
					} else {
						LogCvt.info("提交审核操作失败,outletId=" + outletId+ ",auditId=" + auditId);
					}
				} else {
					LogCvt.info("提交审核操作失败,未获取到门店信息！outletId=" + outletId+ ",auditId=" + auditId);
				}
			} else {
				OutletTemp outletTemp = outletAuditLogic.findOutletTempByOutletId(outletId,"0");
				if (null != outletTemp) {
					isSucc = outletAuditLogic.removeOutletTemp(outletTemp);// 执行更新
				}
			}
		} catch (FroadServerException e) {
			LogCvt.error("提示审核失败, 原因:" + e.getMessage(), e);
		} catch (Exception e) {
			LogCvt.error("提示审核时[系统异常], 原因:" + e.getMessage(), e);
		}
	}
}