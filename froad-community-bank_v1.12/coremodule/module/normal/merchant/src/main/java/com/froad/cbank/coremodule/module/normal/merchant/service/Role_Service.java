package com.froad.cbank.coremodule.module.normal.merchant.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.PageRes;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Role_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Role_Resource_Res;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.TargetObjectFormat;
import com.froad.thrift.service.MerchantResourceService;
import com.froad.thrift.service.MerchantRoleResourceService;
import com.froad.thrift.service.MerchantRoleService;
import com.froad.thrift.vo.MerchantResourceVo;
import com.froad.thrift.vo.MerchantRolePageVoRes;
import com.froad.thrift.vo.MerchantRoleResourceVo;
import com.froad.thrift.vo.MerchantRoleVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResourceVo;

/**
 * 角色
 * @ClassName Role_Service
 * @author zxl
 * @date 2015年4月21日 上午10:41:11
 */
@Service
public class Role_Service {
	
	@Resource
	MerchantRoleResourceService.Iface merchantRoleResourceService;
	
	@Resource
	MerchantRoleService.Iface merchantRoleService;
	
	@Resource
	MerchantResourceService.Iface merchantResourceService;
	
	/**
	 * 角色列表
	 * @tilte query_list
	 * @author zxl
	 * @date 2015年4月21日 上午10:45:10
	 * @param clientId
	 * @return
	 * @throws MerchantException
	 * @throws Exception
	 */
	public Map<String,Object> query_list(String clientId) throws MerchantException, Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		PageVo page = new PageVo();
		MerchantRoleVo vo = new MerchantRoleVo();
//		vo.setClientId(clientId);
		MerchantRolePageVoRes res =merchantRoleService.getMerchantRoleByPage(page, vo);
		if(res.getMerchantRoleVoList()!=null&&res.getMerchantRoleVoList().size()>0){
			List<Query_Role_Res> list = new ArrayList<Query_Role_Res>();
			for(MerchantRoleVo v : res.getMerchantRoleVoList()){
				Query_Role_Res r = new Query_Role_Res();
				TargetObjectFormat.copyProperties(v, r);
				list.add(r);
			}
			map.put("roleList", list);
			PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(res.getPage(), pageRes);
			map.put("page",pageRes);
		}else{
			throw new MerchantException(EnumTypes.noresult);
		}
		
		return map;
	}
	
	/**
	 * 角色权限列表
	 * @tilte query_resouce_list
	 * @author zxl
	 * @date 2015年4月21日 上午10:58:30
	 * @param clientId
	 * @param merchantId
	 * @return
	 * @throws MerchantException
	 * @throws Exception
	 */
	public Map<String,Object> query_resouce_list(String clientId,String roleId) throws MerchantException, Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		
		MerchantResourceVo request = new MerchantResourceVo();
		request.setIsEnabled(true);
		request.setIsParent(true);
		request.setClientId(clientId);
		List<MerchantResourceVo>  listAll = merchantResourceService.getMerchantResource(request);
		List<Query_Role_Resource_Res> alAll = new ArrayList<Query_Role_Resource_Res>();
		if(listAll!=null&&listAll.size()>0){
			for(MerchantResourceVo vo : listAll){
				//一级菜单
				if(vo.getParentId() == 0){
					Query_Role_Resource_Res r = new Query_Role_Resource_Res();
					r.setResource_id(vo.getId());
					r.setResource_name(vo.getName());
					//二级菜单
					List<Query_Role_Resource_Res> sublist = new ArrayList<Query_Role_Resource_Res>();
					for(MerchantResourceVo sub :listAll){
						if(0!=sub.getParentId() && sub.getParentId()==vo.getId()){
							Query_Role_Resource_Res s  = new Query_Role_Resource_Res();
							s.setResource_id(sub.getId());
							s.setResource_name(sub.getName());
							sublist.add(s);
						}
					}
					r.setSub_list(sublist);
					alAll.add(r);
				}
			}
		}
		map.put("resource_all", alAll);
		
		MerchantRoleResourceVo resp = merchantRoleResourceService.getMerchantRoleResource(clientId+"_"+roleId);
		List<Query_Role_Resource_Res> al = new ArrayList<Query_Role_Resource_Res>();
		if(resp!=null&&resp.getResourcesSize()>0){
			for(ResourceVo vo :resp.getResources()){
				//一级菜单
				if(vo.getParent_id()==0){
					Query_Role_Resource_Res r = new Query_Role_Resource_Res();
					TargetObjectFormat.copyProperties(vo, r);
					//二级菜单
					List<Query_Role_Resource_Res> sublist = new ArrayList<Query_Role_Resource_Res>();
					for(ResourceVo sub :resp.getResources()){
						if(0!=sub.getParent_id() && sub.getParent_id()==vo.getResource_id()){
							Query_Role_Resource_Res s  = new Query_Role_Resource_Res();
							TargetObjectFormat.copyProperties(sub, s);
							sublist.add(s);
						}
					}
					r.setSub_list(sublist);
					al.add(r);
				}
			}
		}
		map.put("resource", al);
		return map;
	}
}
