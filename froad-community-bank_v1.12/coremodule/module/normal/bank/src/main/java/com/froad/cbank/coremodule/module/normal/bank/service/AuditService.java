package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.thrift.service.BankAuditService;
import com.froad.thrift.service.FallowExecuteService;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.BankAuditVo;
import com.froad.thrift.vo.BankAuditVoRes;
import com.froad.thrift.vo.ExecuteTaskReqVo;
import com.froad.thrift.vo.ExecuteTaskResVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PreAuditNumVo;

/**
 * 审核提交
 * @author yfy
 * @date 2015-03-25 上午 9:35:31
 */
@Service
public class AuditService {

	@Resource
	BankAuditService.Iface bankAuditService;
	@Resource
	SmsMessageService.Iface smsMessageService;
	@Resource
	FallowExecuteService.Iface fallowExecuteService;
	
	public MsgVo merchantAuditNew(AuditReq voReq,OriginVo originVo) throws TException{
		LogCvt.info("审核请求参数："+JSON.toJSONString(voReq));
		MsgVo msgVo = new MsgVo();
		
		ExecuteTaskReqVo executeTaskReqVo = new ExecuteTaskReqVo();
		executeTaskReqVo.setInstanceId(voReq.getAuditId());
		executeTaskReqVo.setTaskId(voReq.getTaskId());
		executeTaskReqVo.setBessId(voReq.getBessId());
		executeTaskReqVo.setAuditState(voReq.getAuditState());
		executeTaskReqVo.setRemark(voReq.getRemark());
		executeTaskReqVo.setOrgCode(voReq.getMyOrgCode());
		executeTaskReqVo.setOrigin(originVo);
		
		LogCvt.info("审核接口fallowExecuteService===参数："+JSON.toJSONString(executeTaskReqVo));
		ExecuteTaskResVo executeTaskResVo = fallowExecuteService.executeTask(executeTaskReqVo);
		LogCvt.info("审核返回数据："+JSON.toJSONString(executeTaskResVo));
		if(EnumTypes.success.getCode().equals(executeTaskResVo.getResult().getResultCode())){
			msgVo.setResult(true);
			if(voReq.getAuditState().equals("1")){
				msgVo.setMsg("审核通过操作成功");
			}else if(voReq.getAuditState().equals("2")){
				msgVo.setMsg("审核未通过操作成功");
			}
			
		}else{
			msgVo.setResult(false);
			msgVo.setMsg(executeTaskResVo.getResult().getResultDesc());
		}
		
		return msgVo;
	}
	
	
	/**
	 * 商户审核提交
	 * @param merchantId
	 * @param state
	 * @param auditReason
	 * @return
	 */
	public MsgVo merchantAudit(AuditVoReq voReq,OriginVo vo) throws TException{
		MsgVo msgVo = new MsgVo();
		try {
			List<BankAuditVo> bankAuditList = new ArrayList<BankAuditVo>();
			BankAuditVo bankAuditVo = new BankAuditVo();
			bankAuditVo.setClientId(voReq.getClientId());//客户端ID
			bankAuditVo.setAuditId(voReq.getMerchantId());//商户ID
			bankAuditVo.setAuditState(voReq.getAuditState());//审核状态(1-通过，2-未通过)
			bankAuditVo.setAuditStaff(voReq.getAuditStaff());//审核人
			if(voReq.getAuditReason() != null){
				bankAuditVo.setAuditComment(voReq.getAuditReason());//审核理由
			}
			bankAuditList.add(bankAuditVo);
			
			BankAuditVoRes resultVo = bankAuditService.auditBatch(vo,2, bankAuditList);//标识(审核标志 1-商品 2-商户)
			if(resultVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				if(voReq.getAuditState().equals(AuditFlagEnum.ACCEPTED.getCode())){
					//发短信的功能屏蔽掉
//					if(resultVo.isFinalAudit 
//							&& StringUtil.isNotEmpty(voReq.getLoginPhone())
//							&& StringUtil.isNotEmpty(voReq.getLoginName())){//最终审核通过发送短信
//						SmsMessageVo smsMessage = new SmsMessageVo();	
//						smsMessage.setClientId(voReq.getClientId());
//						smsMessage.setMobile(voReq.getLoginPhone());
//						smsMessage.setSendUser(voReq.getLoginName());
//						List<String> str = new ArrayList<String>();
//						str.add(voReq.getLoginName());
//						str.add(voReq.getLoginPhone());
//						str.add("111111");
//						smsMessage.setValues(str);
//						smsMessage.setSmsType(SmsTypeEnum.merchantAddUser.getValue());
//						LogCvt.info("======商户审核提交，最终审核状态为"+resultVo.isFinalAudit+",则发送短信:开始======");
//						smsMessageService.sendSMS(smsMessage);
//						LogCvt.info("======商户审核提交，最终审核状态为"+resultVo.isFinalAudit+",则发送短信:结束======");
//					}
					msgVo.setMsg("审核通过操作成功");
				}else if(voReq.getAuditState().equals(AuditFlagEnum.REJECTED.getCode())){
					msgVo.setMsg("审核未通过操作成功");
				}
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("商户审核提交"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("商户审核提交异常");
		}
		return msgVo;
	}
	
	/**
	 * 商品审核提交
	 * @param productId
	 * @param productType
	 * @param state
	 * @param auditReason
	 * @return
	 */
	public MsgVo productAudit(AuditVoReq voReq,OriginVo vo) throws TException{
		MsgVo msgVo = new MsgVo();
		try {
			List<BankAuditVo> bankAuditList = new ArrayList<BankAuditVo>();
			BankAuditVo bankAuditVo = new BankAuditVo();
			bankAuditVo.setClientId(voReq.getClientId());//客户端ID
			bankAuditVo.setAuditId(voReq.getProductId());//商品ID
			bankAuditVo.setAuditState(voReq.getAuditState());//审核状态(1-通过，2-未通过)
			bankAuditVo.setAuditStaff(voReq.getAuditStaff());//审核人
			if(voReq.getAuditReason() != null){
				bankAuditVo.setAuditComment(voReq.getAuditReason());//审核理由
			}
			bankAuditList.add(bankAuditVo);
			BankAuditVoRes resultVo = bankAuditService.auditBatch(vo,1, bankAuditList);//标识(审核标志 1-商品 2-商户)
			if(resultVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				if(voReq.getAuditState().equals(AuditFlagEnum.ACCEPTED.getCode())){
					msgVo.setMsg("审核通过操作成功");
				}else if(voReq.getAuditState().equals(AuditFlagEnum.REJECTED.getCode())){
					msgVo.setMsg("审核未通过操作成功");
				}
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("商品审核提交"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("商品审核提交异常");
		}
		return msgVo;
	}
	
	/**
	 * 秒杀商品审核提交
	 * @param productId
	 * @param productType
	 * @param state
	 * @param auditReason
	 * @return MsgVo
	 * 
	 * @author wangzhangxu
	 * @date 2015-05-06 上午 9:35:31
	 */
	public MsgVo seckillProductAudit(AuditVoReq voReq,OriginVo vo) throws TException{
		MsgVo msgVo = new MsgVo();
		try {
			List<BankAuditVo> bankAuditList = new ArrayList<BankAuditVo>();
			BankAuditVo bankAuditVo = new BankAuditVo();
			bankAuditVo.setClientId(voReq.getClientId());//客户端ID
			bankAuditVo.setAuditId(voReq.getProductId());//商品ID
			bankAuditVo.setAuditState(voReq.getAuditState());//审核状态(1-通过，2-未通过)
			bankAuditVo.setAuditStaff(voReq.getAuditStaff());//审核人
			if(voReq.getAuditReason() != null){
				bankAuditVo.setAuditComment(voReq.getAuditReason());//审核理由
			}
			bankAuditList.add(bankAuditVo);
			BankAuditVoRes resultVo = bankAuditService.auditBatch(vo, 3, bankAuditList);//标识(审核标志 1-商品, 2-商户, 3-秒杀商品)
			if(resultVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				if(voReq.getAuditState().equals(AuditFlagEnum.ACCEPTED.getCode())){
					msgVo.setMsg("审核通过操作成功");
				}else if(voReq.getAuditState().equals(AuditFlagEnum.REJECTED.getCode())){
					msgVo.setMsg("审核未通过操作成功");
				}
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("秒杀商品审核提交"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("秒杀商品审核提交异常");
		}
		return msgVo;
	}
	
	/**
	 * 积分礼品审核提交（批量审核）
	 * @param merchantId
	 * @param productType
	 * @param state
	 * @param auditReason
	 * @return
	 */
	public MsgVo lineProductAudit(AuditVoReq voReq,OriginVo vo) throws TException{
		MsgVo msgVo = new MsgVo();
		try {
			List<BankAuditVo> bankAuditList = new ArrayList<BankAuditVo>();
			for(String productId : voReq.getProductIdList()){
				BankAuditVo bankAuditVo = new BankAuditVo();
				bankAuditVo.setClientId(voReq.getClientId());//客户端ID
				bankAuditVo.setAuditId(productId);//商品ID
				bankAuditVo.setAuditState(voReq.getAuditState());//审核状态(1-通过，2-未通过)
				bankAuditVo.setAuditStaff(voReq.getAuditStaff());//审核人
				if(voReq.getAuditReason() != null){
					bankAuditVo.setAuditComment(voReq.getAuditReason());//审核理由
				}
				bankAuditList.add(bankAuditVo);
			}
			BankAuditVoRes resultVo = bankAuditService.auditBatch(vo,1, bankAuditList);//标识(审核标志 1-商品 2-商户)
			if(resultVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				if(voReq.getAuditState().equals(AuditFlagEnum.ACCEPTED.getCode())){
					msgVo.setMsg("审核通过操作成功");
				}else if(voReq.getAuditState().equals(AuditFlagEnum.REJECTED.getCode())){
					msgVo.setMsg("审核未通过操作成功");
				}
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("线下积分礼品审核提交"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("线下积分礼品审核提交异常");
		}
		return msgVo;
	}
	
	/**
	 * 待审核数量
	 * @param clientId
	 * @param orgCode
	 */
	public PreAuditNumVo auditCount(String clientId,String orgCode) throws TException{
		PreAuditNumVo auditNum = new PreAuditNumVo();
		try {
			auditNum = bankAuditService.getPreAuditNumRes(clientId,orgCode);
		} catch (TException e) {
			LogCvt.info("获取待审核数量"+e.getMessage(), e);
		}
		return auditNum;
	}
	
	/**
	 * 校验
	 * @param bankOrg
	 * @return
	 */
	public MsgVo verifyAuditVo(AuditReq voReq){
		MsgVo msgVo = new MsgVo();
		if(!StringUtil.isNotEmpty(voReq.getAuditState())){
			msgVo.setResult(false);
			msgVo.setMsg("审核状态不能为空");
			return msgVo;
		}
		if (voReq.getAuditState().equals("2")
				&& StringUtil.isBlank(voReq.getRemark())) {
			msgVo.setResult(false);
			msgVo.setMsg("审核备注不能为空");
			return msgVo;
		}
		if (StringUtil.isNotBlank(voReq.getRemark())
				&& voReq.getRemark().length() > 180) {
			msgVo.setResult(false);
			msgVo.setMsg("审核备注不能超过180个字符");
			return msgVo;
		}
		msgVo.setResult(true);
		return msgVo;
	}
	
	
	/**
	 * 校验
	 * @param bankOrg
	 * @return
	 */
	public MsgVo verify(AuditVoReq voReq){
		MsgVo msgVo = new MsgVo();
		if(!StringUtil.isNotEmpty(voReq.getAuditState())){
			msgVo.setResult(false);
			msgVo.setMsg("审核状态不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(voReq.getAuditStaff())){
			msgVo.setResult(false);
			msgVo.setMsg("一级机构不能为空");
			return msgVo;
		}
		if (voReq.getAuditState().equals("2")
				&& StringUtil.isBlank(voReq.getAuditState())) {
			msgVo.setResult(false);
			msgVo.setMsg("审核备注不能为空");
			return msgVo;
		}
		if (StringUtil.isNotBlank(voReq.getAuditReason())
				&& voReq.getAuditReason().length() > 180) {
			msgVo.setResult(false);
			msgVo.setMsg("审核备注不能超过180个字符");
			return msgVo;
		}
		msgVo.setResult(true);
		return msgVo;
	}
}
