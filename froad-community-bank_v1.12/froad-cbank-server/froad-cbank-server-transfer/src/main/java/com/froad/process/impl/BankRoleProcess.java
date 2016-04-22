package com.froad.process.impl;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.bps.entity.Role;
import com.froad.db.bps.mappers.RoleMapper;
import com.froad.db.chonggou.entity.BankRole;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.BankRoleMapper;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;

public class BankRoleProcess extends AbstractProcess {
	
	private RoleMapper roleMapper;
	private BankRoleMapper bankRoleMapper;
	private TransferMapper transferMapper;

	public BankRoleProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		LogCvt.info("角色表cb_bank_role 数据迁移开始");
		roleMapper = bpsSqlSession.getMapper(RoleMapper.class);
		bankRoleMapper = sqlSession.getMapper(BankRoleMapper.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		List<Role> roles = roleMapper.selectAllRoles();
		if(Checker.isEmpty(roles)){
			LogCvt.info("原安徽表tm_role 无数据需迁移");
			return;
		}
		LogCvt.info("原安徽表tm_role 共有"+roles.size()+"条数据需迁移");
		for(Role role : roles){
			if("1".equals(role.getIsAdmin())){ //管理员角色不用迁移
				continue;
			}
			BankRole bankRole = new BankRole();
			bankRole.setClientId(Constans.clientId);
			bankRole.setRoleName(role.getRoleName());
			bankRole.setRemark(role.getRemark());
			bankRole.setStatus(BooleanUtils.toBooleanObject(role.getStatus(), "1", "0", ""));	//0.不可用1.可用
			bankRole.setIsDelete(false);
			bankRole.setTag("1");	//旧:是否管理员角色：1是，0否	新:标明角色身份，身份比如管理员、操作员(管理员暂时定为0)
			bankRole.setOrgCode(Checker.isNotEmpty(role.getOrgCode())?Constans.filterOrg(role.getOrgCode()):"");
			Long result = bankRoleMapper.addBankRole(bankRole);
			
			Transfer transfer = new Transfer();
			transfer.setOldId(ObjectUtils.toString(role.getRoleId()));
			transfer.setNewId(ObjectUtils.toString(bankRole.getId()));
			transfer.setType(TransferTypeEnum.bank_role_id);
			transferMapper.insert(transfer);
			
			LogCvt.info("银行角色[roleId:"+role.getRoleId()+"] 数据迁移"+(result > 0?"成功":"失败"));
		}
		
		ConsoleLogger.info("角色表cb_bank_role 数据迁移结束");
	}

}
