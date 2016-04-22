package com.froad.util;

import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.BankOperateLog;
import com.froad.po.BankOperator;
import com.froad.po.Org;
import com.froad.thread.FroadThreadPool;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.util.Checker;

public class LogUtils {

	/**
	 * thrift中添加操作日志
	 * @param originVo
	 * @return
	 */
	public static void addLog(final OriginVo originVo) {
		// 用线程池  添加日志
		FroadThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				
				if(Checker.isEmpty(originVo) || Checker.isEmpty(originVo.getOperatorId())){
					return ;
				}
				PlatType paltType = originVo.getPlatType(); // 平台类型
				try{
					switch (paltType) {
					case boss: // boss

						break;
					case bank: // 银行端
						CommonLogic commonLogic = new CommonLogicImpl();
						String clientId = originVo.getClientId();
						
						//添加日志记录
						Long userId = originVo.getOperatorId();
						BankOperateLog bankOperatorLog = new BankOperateLog();
						bankOperatorLog.setOperatorIp(originVo.getOperatorIp());
						bankOperatorLog.setUserId(userId);
						bankOperatorLog.setDescription(originVo.getDescription());
						bankOperatorLog.setPlatType(originVo.getPlatType().getValue());
						bankOperatorLog.setLogType(false);//是否登录日志
						bankOperatorLog.setClientId(clientId);
						
						//根据userid去查clientId和roleId
						BankOperator bankOperator = commonLogic.getBankOperatorById(clientId, userId);
						if(Checker.isNotEmpty(bankOperator)){
							String orgCode = bankOperator.getOrgCode();
							
							bankOperatorLog.setUsername(bankOperator.getUsername());
							bankOperatorLog.setRoleId(bankOperator.getRoleId());
							bankOperatorLog.setOrgCode(orgCode);
							
							//根据orgcode去查机构对象
							Org org = new CommonLogicImpl().queryByOrgCode(clientId, orgCode);
							if(Checker.isNotEmpty(org)){
								bankOperatorLog.setOrgName(org.getOrgName());
							}
						}			
						
						commonLogic.addBankOperateLog(bankOperatorLog);
						
						
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
					
					LogCvt.info("添加操作日志记录完成");
				} catch (Exception e) {
					LogCvt.error("添加操作日志记录失败"+e.getMessage(),e);
				}
			}
		});
		
		
	}
}
