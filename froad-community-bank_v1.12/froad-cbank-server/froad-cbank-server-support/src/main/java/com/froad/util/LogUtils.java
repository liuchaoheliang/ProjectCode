package com.froad.util;

import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
//import com.froad.logic.BankOperateLogLogic;
//import com.froad.logic.BankOperatorLogic;
//import com.froad.logic.impl.BankOperateLogLogicImpl;
//import com.froad.logic.impl.BankOperatorLogicImpl;
//import com.froad.logic.impl.OrgLogicImpl;
import com.froad.po.BankOperateLog;
import com.froad.po.BankOperator;
import com.froad.po.Org;
import com.froad.po.Result;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.util.Checker;

public class LogUtils {
//	static BankOperateLogLogic bankOperateLogLogic = new BankOperateLogLogicImpl();
//	static BankOperatorLogic bankOperatorLogic = new BankOperatorLogicImpl();

	/**
	 * thrift中添加操作日志
	 * @param originVo
	 * @return
	 */
	public static void addLog(OriginVo originVo){
		if(Checker.isEmpty(originVo) || Checker.isEmpty(originVo.getOperatorId())){
//			LogCvt.info("添加操作日志记录失败");
			return ;
		}
		PlatType paltType = originVo.getPlatType(); // 平台类型
		try{
			switch (paltType) {
			case boss: // boss

				break;
			case bank: // 银行端
				//添加日志记录
//				Long userId = originVo.getOperatorId();
//				BankOperateLog bankOperatorLog = new BankOperateLog();
//				bankOperatorLog.setOperatorIp(originVo.getOperatorIp());
//				bankOperatorLog.setUserId(userId);
//				bankOperatorLog.setDescription(originVo.getDescription());
//				bankOperatorLog.setPlatType(originVo.getPlatType().getValue());
//				
//				//根据userid去查clientId和roleId
//				BankOperator bankOperator = bankOperatorLogic.findBankOperatorById("", userId);
//				if(Checker.isNotEmpty(bankOperator)){
//					String clientId = bankOperator.getClientId();
//					String orgCode = bankOperator.getOrgCode();
//					
//					bankOperatorLog.setClientId(clientId);
//					bankOperatorLog.setUsername(bankOperator.getUsername());
//					bankOperatorLog.setRoleId(bankOperator.getRoleId());
//					bankOperatorLog.setOrgCode(orgCode);
//					
//					//根据orgcode去查机构对象
//					Org org = new OrgLogicImpl().findOrgById(clientId, orgCode);
//					if(Checker.isNotEmpty(org)){
//						bankOperatorLog.setOrgName(org.getOrgName());
//					}
//				}			
//				
//				bankOperateLogLogic.addBankOperateLog(2,bankOperatorLog);
				
				
				break;
			case merchant_pc: // 商户pc

				break;
			case merchant_h5: // 商户h5
				break;
			case personal_pc:// 个人pc
				
				break;

			case personal_h5: // 个人h5
				
				break;
			case auto_task: // 自动任务
				
				break;

			default:
				break;
			}
			
//			LogCvt.info("添加操作日志记录完成");
		} catch (Exception e) {
			LogCvt.error("添加操作日志记录失败");
		}
		
//		return result;
	}
}
