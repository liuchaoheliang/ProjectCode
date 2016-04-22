package com.froad.cbank.coremodule.module.normal.bank.util;

import java.util.HashMap;
import java.util.List;

import com.froad.cbank.coremodule.framework.common.util.bus.SpringApplicationContextUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.thrift.service.BankRoleResourceService;
import com.froad.thrift.vo.BankUserResourceVo;
import com.froad.thrift.vo.ResourcesInfoVo;

/**
 * 权限信息
 * @ClassName RoleConstant
 * @author zxl
 * @date 2015年4月29日 上午11:44:03
 */
public class RoleConstant {
	
	private static HashMap<String,List<ResourcesInfoVo>> role = new HashMap<String, List<ResourcesInfoVo>>();
	
	private static void load(){
		try {
			LogCvt.info("权限加载开始...");
			BankRoleResourceService.Iface bankRoleResourceService
				= (BankRoleResourceService.Iface) SpringApplicationContextUtil.getBean("bankRoleResourceService");
			
			List<BankUserResourceVo> list = bankRoleResourceService.getBankRoleResourceAll();
			
			if(list!=null&&list.size()>0){
				HashMap<String,List<ResourcesInfoVo>> tmp = new HashMap<String, List<ResourcesInfoVo>>();
				for(BankUserResourceVo vo : list){
					tmp.put(vo.getId(), vo.getResources());
				}
				role = tmp;
				LogCvt.info("权限加载成功...");
			}else{
				LogCvt.error("权限加载失败,无数据...");
			}
			
		} catch (Exception e) {
			LogCvt.error("权限加载失败..."+e.getMessage());
		}
	}
	
	public static synchronized void reload(){
		load();
	}

	public static HashMap<String,List<ResourcesInfoVo>> getRole() {
		if(role.isEmpty()){
			reload();
		}
		return role;
	}
	
}
