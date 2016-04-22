package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.FinitResourceVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.RoleReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.RoleVo;
import com.froad.thrift.service.FinityResourceService;
import com.froad.thrift.service.FinityRoleResourceService;
import com.froad.thrift.service.FinityRoleService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.finity.FinityResourceVo;
import com.froad.thrift.vo.finity.FinityRoleVo;
/**
 * 角色Service接口
 * @author ylchu
 *
 */
@Service
public class FinitRoleService {

	@Resource
	FinityRoleService.Iface finityRoleService;
	@Resource
	FinityRoleResourceService.Iface finityRoleResourceService;
	@Resource
	FinityResourceService.Iface finityResourceService;
	@Resource
	private LoginService loginService;
	
	/**
	 * 角色新增
	 * @param role
	 * @return
	 * @throws TException 
	 */
	public HashMap<String,Object> roleAdd(RoleVo role,HttpServletRequest req) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		FinityRoleVo finityRoleVo = new FinityRoleVo();
		finityRoleVo.setRoleName(role.getRoleName());
		finityRoleVo.setOrgCode(role.getOrgCode());
		finityRoleVo.setRemark(role.getRemark());
		finityRoleVo.setClientId(role.getClientId());
		finityRoleVo.setStatus(true);
		finityRoleVo.setTag(role.getTag());
		
		CommonAddVoRes resultVo = finityRoleService.addFinityRole(loginService.getOriginVo(req), finityRoleVo, role.getResourceList());
		if(resultVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
			reMap.put("code", resultVo.getResult().getResultCode());
			reMap.put("message", "权限添加成功!");
		}else{
			reMap.put("code", resultVo.getResult().getResultCode());
			reMap.put("message", resultVo.getResult().getResultDesc());
		}
		return reMap;
	}
	
	/**
	 * 角色修改
	 * @param role
	 * @return
	 * @throws TException 
	 */
	public HashMap<String,Object> roleUpdate(RoleVo role,HttpServletRequest req) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		FinityRoleVo finityRoleVo = new FinityRoleVo();
		finityRoleVo.setClientId(role.getClientId());
		finityRoleVo.setId(role.getRoleId());
		List<FinityRoleVo> finityRoleList = finityRoleService.getFinityRole(finityRoleVo);
		if(finityRoleList != null && finityRoleList.size() > 0){
			finityRoleVo = finityRoleList.get(0);
		}
		if(role.getRoleName() != null){	//角色名
			finityRoleVo.setRoleName(role.getRoleName());
		}
		if(role.getRemark() != null){ //备注
			finityRoleVo.setRemark(role.getRemark());
		}
		ResultVo resultVo = finityRoleService.updateFinityRole(loginService.getOriginVo(req), finityRoleVo, role.getResourceList());
		
		if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
			reMap.put("code", resultVo.getResultCode());
			reMap.put("message", "角色修改成功!");
		}else{
			reMap.put("code", resultVo.getResultCode());
			reMap.put("message", resultVo.getResultDesc());
		}
		return reMap;
	}

	
	
	/**
	 * 获取全部资源
	 * @return
	 * @throws TException
	 * 
	 * tag 本参数可去掉，2015-10-09不需要区分是否获取全部资源，新权限系统都是按照角色来获取资源信息
	 * 
	 * values 可选参数，浮标0标识用户等级orgLevel;1标识接口访问来源；2roleTag 标识用户名角色roleTag=2为超级管理员
	 */
	public HashMap<String,Object> getResourceList(RoleReq role,Object... values) throws TException{
		
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		List<Long> roles = new ArrayList<Long>();
		FinityResourceVo finityResourceVo = new FinityResourceVo();
		finityResourceVo.setIsDelete(false);
 		finityResourceVo.setStatus(true);
 		finityResourceVo.setPlatform("bank");//设置获取银行平台资源
		roles.add(role.getRoleId());
		List<FinityResourceVo> beforeList = new ArrayList<FinityResourceVo>();
		List<FinitResourceVo> afterList = new ArrayList<FinitResourceVo>();
//		if(StringUtil.equals(tag, "1")){
//			beforeList = finityRoleResourceService.getFinityRoleResource(role.getClientId(), role.getRoleId(), "");
//		}else{
//			beforeList = finityResourceService.getFinityResourceByRole(finityResourceVo,roles);
//		} 
		beforeList = finityResourceService.getFinityResourceByRole(finityResourceVo,roles);
		if(beforeList==null || beforeList.size()<1){
			reMap.put("resourceList",  null);
			return reMap;
		}
		FinitResourceVo tyPo=null;
		for(FinityResourceVo tyVo:beforeList){//thrift 到 coremodule实体转换，避免thrift一些临时属性 
			tyPo=new FinitResourceVo();
			//超级管理员处理 admin、1599004(重庆超级管理员)登录过来的菜单只显示系统级别 0308015，tag=2
			if(values!=null && values.length>1  && "login".equalsIgnoreCase(values[1]+"") 
					&& "2".equalsIgnoreCase(values[2]+"") && tyVo.isIsMenu() && !tyVo.isIsSystem()){
					continue; 
			}
			BeanUtils.copyProperties(tyPo, tyVo);  
			afterList.add(tyPo);
		} 
		List<FinitResourceVo> rootResources = new ArrayList<FinitResourceVo>();
		for (FinitResourceVo tree : afterList) {//资源树整理 
            if(tree.getParentResourceId() == 100000000){
                rootResources.add(tree);
            }
            for (FinitResourceVo t : afterList) {
                if(t.getParentResourceId() == tree.getId()){
                    if(tree.getFinityResourceList() == null){
                        List<FinitResourceVo> myChildrens = new ArrayList<FinitResourceVo>();
                        myChildrens.add(t);
                        tree.setFinityResourceList(myChildrens);
                    }else{
                        tree.getFinityResourceList().add(t);
                    }
                }
            }
        }    
		reMap.put("resourceList",  rootResources);
		return reMap;
	}
	
	
}
