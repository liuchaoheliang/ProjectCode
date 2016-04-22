package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.logback.LogCvt;
import com.froad.logic.BankOperateLogLogic;
import com.froad.logic.impl.BankOperateLogLogicImpl;
import com.froad.logic.impl.BankRoleLogicImpl;
import com.froad.po.BankOperateLog;
import com.froad.po.BankRole;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BankOperateLogService;
import com.froad.thrift.vo.BankOperateLogPageVoRes;
import com.froad.thrift.vo.BankOperateLogVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: BankOperateLogServiceImpl.java</p>
 * <p>Description: 描述 </p> 银行用户操作日志
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月2日
 */
public class BankOperateLogServiceImpl extends BizMonitorBaseService implements BankOperateLogService.Iface {

	private BankOperateLogLogic bankOperateLogLogic = new BankOperateLogLogicImpl();
	public BankOperateLogServiceImpl(String name, String version){
		super(name, version);
	};
	
	

	/**
     * 分页查询 bankOperateLog
     * @parm pageVo 分页信息page
     * @param bankOperateLog 查询条件
     * @return BankOperateLogPageVoRes
     */
	@Override
	public BankOperateLogPageVoRes getBankOperateLogByPage(PageVo pageVo,BankOperateLogVo bankOperateLogVo) throws TException {
			LogCvt.info("分页查询BankOperateLog");
			Page<BankOperateLog> page = null;
			BankOperateLog bankOperateLog = null;
			List<BankOperateLogVo> bankOperateLogVoList=null;
			
			//判断orgCode有值时，clientId不可为空
			if(Checker.isNotEmpty(bankOperateLogVo)){
				if(Checker.isNotEmpty(bankOperateLogVo.getOrgCode())){
					if(Checker.isEmpty(bankOperateLogVo.getClientId())){
						LogCvt.error("所属机构有值，clientId不可为空");
						return new BankOperateLogPageVoRes();
					}
				}
			}
			
			
			/*vo转po*/
			page=(Page<BankOperateLog>)BeanUtil.copyProperties(Page.class, pageVo);
			bankOperateLog=(BankOperateLog)BeanUtil.copyProperties(BankOperateLog.class, bankOperateLogVo);

			page = bankOperateLogLogic.findBankOperateLogByPage(page, bankOperateLog);
			
			
			/******************set角色名称**************************/
			//查询后得出的日志list
			List<BankOperateLog> resultLog = new ArrayList<BankOperateLog>();
			List<BankOperateLog> bankOperateLogList = page.getResultsContent();
			if(Checker.isNotEmpty(bankOperateLogList)){
				//查出client下所有角色对象查出角色名称
				BankRole bankRole=new BankRole();
				bankRole.setClientId(bankOperateLogVo.getClientId());
				List<BankRole> bankRoleList = new BankRoleLogicImpl().findBankRole(bankRole);
				
				if(Checker.isNotEmpty(bankRoleList)){
					//循环匹配相同的roleId设置角色名称
					for(BankOperateLog b:bankOperateLogList){
						if(Checker.isEmpty(b.getRoleId())){
							resultLog.add(b);
							continue;
						}
						//取角色名称
						for(BankRole r:bankRoleList){
							if(r.getId().equals(b.getRoleId())){
								b.setRoleName(r.getRoleName());
								resultLog.add(b);
							}
						}
						
					}
					
				}
				
				
			}
			
			
			/*po转vo*/
			bankOperateLogVoList=(List<BankOperateLogVo>)BeanUtil.copyProperties(BankOperateLogVo.class, resultLog);
			pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
			
			//返回对象set值
			BankOperateLogPageVoRes bankOperateLogPageVoRes = new BankOperateLogPageVoRes();
			bankOperateLogPageVoRes.setBankOperateLogVoList(bankOperateLogVoList);
			bankOperateLogPageVoRes.setPage(pageVo);
			
			
			return bankOperateLogPageVoRes;
	}

}
