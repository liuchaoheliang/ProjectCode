package com.froad.cbank.coremodule.module.normal.merchant.utils;

import java.util.HashMap;
import java.util.List;

import com.froad.cbank.coremodule.framework.common.util.bus.SpringApplicationContextUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.thrift.service.MerchantRoleResourceService;
import com.froad.thrift.vo.MerchantRoleResourceVo;
import com.froad.thrift.vo.ResourceVo;

/**
 * 权限信息
 * @ClassName RoleConstant
 * @author zxl
 * @date 2015年4月29日 上午11:44:03
 */
public class RoleConstant {
	
	private static HashMap<String,List<ResourceVo>> role = new HashMap<String, List<ResourceVo>>();
	
	private static void load(){
		try {
			LogCvt.info("权限加载开始...");
			MerchantRoleResourceService.Iface merchantRoleResourceService
				= (MerchantRoleResourceService.Iface) SpringApplicationContextUtil.getBean("merchantRoleResourceService");
			
			List<MerchantRoleResourceVo> list = merchantRoleResourceService.getMerchantRoleResourceList();
			
			if(list!=null&&list.size()>0){
				HashMap<String,List<ResourceVo>> tmp = new HashMap<String, List<ResourceVo>>();
				for(MerchantRoleResourceVo vo : list){
					tmp.put(vo.get_id(), vo.getResources());
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

	public static HashMap<String,List<ResourceVo>> getRole() {
		if(role.isEmpty()){
			reload();
		}
		return role;
	}
	
}
