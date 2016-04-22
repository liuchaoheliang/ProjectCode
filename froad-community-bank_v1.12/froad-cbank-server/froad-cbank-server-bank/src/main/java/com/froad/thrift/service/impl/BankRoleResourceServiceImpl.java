package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mongo.bean.BankUserResource;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.BankRoleResourceLogic;
import com.froad.logic.impl.BankRoleResourceLogicImpl;
import com.froad.po.BankResource;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BankRoleResourceService;
import com.froad.thrift.vo.BankResourceVo;
import com.froad.thrift.vo.BankUserResourceVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;


/**
 * 
 * <p>@Title: BankRoleResourceImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BankRoleResourceServiceImpl extends BizMonitorBaseService implements BankRoleResourceService.Iface {
	private BankRoleResourceLogic bankRoleResourceLogic = new BankRoleResourceLogicImpl();
	public BankRoleResourceServiceImpl(String name, String version) {
		super(name, version);
	}





    /**
     * 查询 BankRoleResource
     * @param bankRoleResource
     * @return List<BankRoleResourceVo>
     */
	@Override
	public List<BankResourceVo> getBankRoleResource(String clientId,long roleId) throws TException {
		if(Checker.isEmpty(clientId) || Checker.isEmpty(roleId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<BankResourceVo>();
		}
		
		List<BankResource> bankResourceList = bankRoleResourceLogic.findBankRoleResource(clientId,roleId);
		List<BankResourceVo> bankResourceVoList=(List<BankResourceVo>)BeanUtil.copyProperties(BankResourceVo.class, bankResourceList);
		
		return bankResourceVoList==null?new ArrayList<BankResourceVo>():bankResourceVoList;
	}


	/**
     * 查询 全部角色资源关系BankRoleResource
     * @return List<BankRoleResourceVo>
     */
	@Override
	public List<BankUserResourceVo> getBankRoleResourceAll() throws TException {
		
		List<BankUserResource> bankResourceList = bankRoleResourceLogic.findBankRoleResourceAll();
		List<BankUserResourceVo> bankResourceVoList=(List<BankUserResourceVo>)BeanUtil.copyProperties(BankUserResourceVo.class, bankResourceList);
		
		return bankResourceVoList==null?new ArrayList<BankUserResourceVo>():bankResourceVoList;
	}
	
	
	

}
