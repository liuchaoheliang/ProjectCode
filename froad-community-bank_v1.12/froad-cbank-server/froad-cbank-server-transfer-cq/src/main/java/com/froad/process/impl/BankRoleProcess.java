package com.froad.process.impl;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.bps.entity.Role;
import com.froad.db.bps.mappers.RoleMapper;
import com.froad.db.chonggou.entity.BankRole;
import com.froad.db.chonggou.entity.OrgLevel;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.BankRoleMapper;
import com.froad.db.chonggou.mappers.OrgLevelMapper;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.Constans;

public class BankRoleProcess extends AbstractProcess {
	
	private RoleMapper roleMapper;
	private BankRoleMapper bankRoleMapper;
	private OrgLevelMapper orgLevelMapper;
	private TransferMapper transferMapper;

	public BankRoleProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}
	
	
	@Override
	public void before() {
		roleMapper = bpsSqlSession.getMapper(RoleMapper.class);
		bankRoleMapper = sqlSession.getMapper(BankRoleMapper.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		orgLevelMapper = sqlSession.getMapper(OrgLevelMapper.class);
		// 删除重庆旧数据
		bankRoleMapper.deleteByChongQing();
		orgLevelMapper.deleteOrgLevel();
	}

	@Override
	public void process() {
		LogCvt.info("角色表cb_bank_role 数据迁移开始");
		
		// 初始化管理员角色
		initAdminRole();
		
		List<Role> roles = roleMapper.selectAllRoles();
		if(Checker.isEmpty(roles)){
			LogCvt.info("银行角色无数据需迁移");
			return;
		}
		LogCvt.info("银行角色共有"+roles.size()+"条数据需迁移");
		for(Role role : roles){
			BankRole bankRole = new BankRole();
			bankRole.setClientId(Constans.clientId);
			bankRole.setRoleName(role.getRoleName());
			bankRole.setRemark(role.getRemark());
			bankRole.setStatus(BooleanUtils.toBooleanObject(role.getStatus(), "1", "0", ""));	//0.不可用1.可用
			bankRole.setIsDelete(false);
			bankRole.setTag("1");	//操作员
			bankRole.setOrgCode(Checker.isNotEmpty(role.getOrgCode())?Constans.filterOrg(role.getOrgCode()):"");
			Long result = bankRoleMapper.addBankRole(bankRole);
			
			String roleName = role.getRoleName();
			String orgLevel = "0";
			if("总行角色".equals(roleName)){
				orgLevel = "1";
			}else if("支行角色".equals(roleName)){
				orgLevel = "2";
			}else if("网点角色".equals(roleName)){
				orgLevel = "3";
			}
			
			OrgLevel level = new OrgLevel();
			level.setClientId(Constans.clientId);
			level.setRoleId(bankRole.getId());
			level.setOrgLevel(orgLevel);
			orgLevelMapper.addOrgLevel(level);
			
			Transfer transfer = new Transfer();
			transfer.setOldId(ObjectUtils.toString(role.getRoleId()));
			transfer.setNewId(ObjectUtils.toString(bankRole.getId()));
			transfer.setType(TransferTypeEnum.bank_role_id);
			transferMapper.insert(transfer);
			
			LogCvt.info("银行角色[roleId:"+role.getRoleId()+"] 数据迁移"+(result > 0?"成功":"失败"));
		}
		
		LogCvt.info("角色表cb_bank_role 数据迁移结束");
	}


	private void initAdminRole() {
		LogCvt.info("初始化总行管理角色");
		BankRole bankRole = new BankRole();
		bankRole.setClientId(Constans.clientId);
		bankRole.setRoleName("总行管理员");
		bankRole.setRemark("总行管理员");
		bankRole.setStatus(true);	//0.不可用1.可用
		bankRole.setIsDelete(false);
		bankRole.setTag("2");
		bankRole.setOrgCode(Constans.topOrgCode);
		bankRoleMapper.addBankRole(bankRole);
		
		Transfer transfer = new Transfer();
		transfer.setOldId("1");
		transfer.setNewId(ObjectUtils.toString(bankRole.getId()));
		transfer.setType(TransferTypeEnum.bank_role_id);
		transferMapper.insert(transfer);
		LogCvt.info("初始化支行管理角色");
		bankRole = new BankRole();
		bankRole.setClientId(Constans.clientId);
		bankRole.setRoleName("支行管理员");
		bankRole.setRemark("支行管理员");
		bankRole.setStatus(true);	//0.不可用1.可用
		bankRole.setIsDelete(false);
		bankRole.setTag("0");
		bankRole.setOrgCode(Constans.topOrgCode);
		bankRoleMapper.addBankRole(bankRole);
	}

}
