/**
 * Project Name:froad-cbank-server-bank
 * File Name:BankAccessModuleLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年10月16日下午2:20:37
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.FunctionModuleMapper;
import com.froad.db.redis.ClientFunctionModuleRedis;
import com.froad.enums.ClientFunctionModule;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.BankAccessModuleLogic;
import com.froad.po.FunctionModule;
import com.froad.thrift.vo.BankAccessModuleListRes;
import com.froad.thrift.vo.BankAccessModuleListVo;
import com.froad.thrift.vo.ResultVo;

/**
 * ClassName:BankAccessModuleLogicImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月16日 下午2:20:37
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BankAccessModuleLogicImpl implements BankAccessModuleLogic {

	@Override
	public BankAccessModuleListRes getBankAccessModuleList(String clientId) {
		LogCvt.info("开始获取当前客户端列表信息");
		BankAccessModuleListRes res = new BankAccessModuleListRes();
		List<BankAccessModuleListVo>  funcVoList = new ArrayList<BankAccessModuleListVo>();
		ResultVo rv = new ResultVo();
		Date date = Calendar.getInstance().getTime();
		//从缓存中获取信息
		Set<String> typeSet = ClientFunctionModuleRedis.get_cbbank_function_module_client_id(clientId);
		
		try {
			if(typeSet != null && typeSet.size() > 0) {
				Iterator<String> it = typeSet.iterator();
				while(it.hasNext()) {
					Map<String, String> map = ClientFunctionModuleRedis.getAll_cbbank_client_function_module_client_id_type(clientId, it.next());
					BankAccessModuleListVo vo = new BankAccessModuleListVo();
					vo.setIconUrl(map.get("icon_url") );
					vo.setClientId(clientId);
					vo.setModuleAlias( map.get("module_alias") );
					vo.setModuleName( map.get("module_name") );
					vo.setSortValue( map.get("sort_value") );
					vo.setType(map.get("type"));
					funcVoList.add(vo);
				}
				res.setModuleList(funcVoList);
				rv.setResultCode(ResultCode.success.getCode());
				rv.setResultDesc(ResultCode.success.getMsg());
			} else {
				//从mysql中获取
				SqlSession sqlSession = null;
				FunctionModuleMapper funcMapper = null;
				//查询功能模块
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession(false);
				funcMapper = sqlSession.getMapper(FunctionModuleMapper.class);
				List<FunctionModule> moduleList = funcMapper.findListByCondition(clientId);
				if(moduleList != null && moduleList.size() > 0) {
					for(FunctionModule module : moduleList) {
						BankAccessModuleListVo vo = new BankAccessModuleListVo();
						vo.setIconUrl(module.getIconUrl());
						vo.setClientId(clientId);
						vo.setModuleAlias(module.getModuleAlias());
						vo.setModuleName(module.getModuleName());
						vo.setSortValue(module.getSortValue() + "");
						vo.setType(module.getType() + "");
						funcVoList.add(vo);
					}
				}
				res.setModuleList(funcVoList);
				
				//缓存添加信息
				FunctionModule[] modules = new FunctionModule[funcVoList.size()];
				for(int i = 0; i < funcVoList.size() ; i++) {
					FunctionModule fm = new FunctionModule();
					fm.setClientId(clientId);
					fm.setCreateTime(date);
					fm.setModuleAlias(funcVoList.get(i).getModuleAlias());
					fm.setModuleName(ClientFunctionModule.getByType(Integer.valueOf(funcVoList.get(i).getType())).getMsg());
					fm.setSortValue(Integer.valueOf(funcVoList.get(i).getSortValue()));
					fm.setType(Integer.valueOf(funcVoList.get(i).getType()));
					fm.setIconUrl(funcVoList.get(i).getIconUrl());
					modules[i] = fm;
				}
				
				ClientFunctionModuleRedis.set_cbbank_function_module_client_id(modules);
				ClientFunctionModuleRedis.set_cbbank_function_module_client_id_type(modules);
				
				rv.setResultCode(ResultCode.success.getCode());
				rv.setResultDesc(ResultCode.success.getMsg());
			}
		} catch (Exception e) {
			LogCvt.error("获取当前客户端功能列表失败", e);
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		}
		
		res.setResult(rv);
		return res;
	}

}
