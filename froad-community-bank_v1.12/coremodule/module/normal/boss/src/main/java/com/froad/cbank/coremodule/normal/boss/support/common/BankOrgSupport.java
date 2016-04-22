package com.froad.cbank.coremodule.normal.boss.support.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.normal.boss.pojo.common.BankOrgVo;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.vo.OrgVo;

/**
 * 获取机构信息
 * @author yfy
 * @date 2015-03-25 上午 9:35:31
 */
@Service
public class BankOrgSupport {
	
	@Resource
	OrgService.Iface orgService;
	
	/**
	 * 银行机构信息联动查询网点
	 * @param orgCode
	 * @return
	 * @throws TException
	 */
	public List<BankOrgVo> selectOrgCode(String clientId,String orgCode,String orgLevel) throws TException{
		List<BankOrgVo> bankOrgList = new ArrayList<BankOrgVo>();
		List<OrgVo> orgList = new ArrayList<OrgVo>(); 
		
		if(StringUtils.isNotEmpty(orgLevel)){
			OrgVo orgVo = new OrgVo(); 
			orgVo.setClientId(clientId);
			orgVo.setOrgLevel(orgLevel);
			orgVo.setIsEnable(true);
			orgList = orgService.getOrg(orgVo);
		}else{
			if(StringUtils.isNotEmpty(orgCode)){
				orgList = orgService.getSubOrgs(clientId,orgCode);
			}
		}
		if(orgList != null && orgList.size() > 0){
			for(OrgVo org : orgList){
				BankOrgVo bankOrg = new BankOrgVo();
				bankOrg.setOrgCode(org.getOrgCode());
				bankOrg.setOrgName(org.getOrgName());
				bankOrgList.add(bankOrg);
			}
		}
		
		return bankOrgList;
	}
	
}
