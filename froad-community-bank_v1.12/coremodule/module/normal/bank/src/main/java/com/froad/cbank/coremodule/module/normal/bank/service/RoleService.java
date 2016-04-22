package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.ResourceVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.RoleReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.RoleRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.RoleVo;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.BankResourceService;
import com.froad.thrift.service.BankRoleResourceService;
import com.froad.thrift.service.BankRoleService;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.BankResourceVo;
import com.froad.thrift.vo.BankRolePageVoRes;
import com.froad.thrift.vo.BankRoleVo;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;

/**
 * 角色Service接口
 * 
 * @author ylchu
 *
 */
@Service
public class RoleService {

	@Resource
	BankRoleService.Iface bankRoleService;
	@Resource
	BankRoleResourceService.Iface bankRoleResourceService;
	@Resource
	BankResourceService.Iface bankResourceService;
	@Resource
	private LoginService loginService;
	@Resource
	private FinitRoleService frService;
	@Resource
	BankOperatorService.Iface bankOperatorService;
	/**
	 * 分页列表条件查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws TException
	 */
	public HashMap<String,Object> findPageByConditions(RoleReq role) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		PageVo page = new PageVo();
		page.setPageNumber(role.getPageNumber());
		page.setPageSize(role.getPageSize());
		page.setLastPageNumber(role.getLastPageNumber());
		page.setFirstRecordTime(role.getFirstRecordTime());
		page.setLastRecordTime(role.getLastRecordTime());
		
		BankRoleVo bankRole = new BankRoleVo();
		bankRole.setClientId(role.getClientId());
		bankRole.setOrgCode(role.getOrgCode());
		bankRole.setRemark(role.getRemark());
		bankRole.setRoleName(role.getRoleName());
		bankRole.setTag("1");// 过滤管理员角色
		bankRole.setStatus(true);
		bankRole.setIsDelete(false);
		BankRolePageVoRes bankRolePage = bankRoleService.getBankRoleByPage(page,bankRole);
		
		List<RoleRes> resList = new ArrayList<RoleRes>();
		List<BankRoleVo> bankRoleList = bankRolePage.getBankRoleVoList();
		if(bankRoleList != null && bankRoleList.size() > 0){
			for (BankRoleVo bankRoleVo : bankRoleList) {
				RoleRes res = new RoleRes();
				res.setRoleName(bankRoleVo.getRoleName());
				res.setRemark(bankRoleVo.getRemark());
				res.setRoleId(bankRoleVo.getId());
				resList.add(res);
			}
		}
		reMap.put("roleList", resList);
		if(bankRolePage.getPage() != null){
			reMap.put("pageCount", bankRolePage.getPage().getPageCount());
			reMap.put("totalCount", bankRolePage.getPage().getTotalCount());
			reMap.put("pageNumber", bankRolePage.getPage().getPageNumber());
			reMap.put("lastPageNumber", bankRolePage.getPage().getLastPageNumber());
			reMap.put("firstRecordTime", bankRolePage.getPage().getFirstRecordTime());
			reMap.put("lastRecordTime", bankRolePage.getPage().getLastRecordTime());
		}else{
			reMap.put("pageCount", 0);
			reMap.put("totalCount",0);
			reMap.put("pageNumber", 1);
			reMap.put("lastPageNumber", 0);
			reMap.put("firstRecordTime", 0);
			reMap.put("lastRecordTime", 0);
		}
		return reMap;
	}
	
	/**
	 * 获取全部角色
	 * 
	 * @param role
	 * @return
	 * @throws TException
	 */
	public HashMap<String,Object> getRoleList(RoleReq role) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		BankRoleVo bankRole = new BankRoleVo();
		bankRole.setClientId(role.getClientId());
		bankRole.setOrgCode(role.getOrgCode());
		bankRole.setRemark(role.getRemark());
		bankRole.setRoleName(role.getRoleName());
		bankRole.setTag("1");// 过滤管理员角色
		bankRole.setIsDelete(false);
		List<BankRoleVo> roleList = bankRoleService.getBankRoleInCurrentOrg(bankRole);
		if(role.getOperUserId()!=null){//如果是获取
			BankOperatorVo bankOperatorVo = bankOperatorService.getBankOperatorById(role.getClientId(), role.getOperUserId());
			BankRoleVo bankRoleVo=new BankRoleVo();
			bankRoleVo.setId(bankOperatorVo.getRoleId());
			List<BankRoleVo> currentroleVo = bankRoleService.getBankRole(bankRoleVo);
			if(roleList==null && currentroleVo!=null){
				roleList=currentroleVo;
			}else if(roleList!=null && currentroleVo!=null && !roleList.contains(currentroleVo.get(0))){
				roleList.add(0,currentroleVo.get(0));
			}
		}
		if(roleList != null && roleList.size() > 0){
			reMap.put("roleList", roleList);
		}
		return reMap;
	}
	
	/**
	 * 角色新增
	 * 
	 * @param role
	 * @return
	 * @throws TException
	 */
	public HashMap<String,Object> roleAdd(RoleVo role,HttpServletRequest req) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		BankRoleVo bankRoleVo = new BankRoleVo();
		bankRoleVo.setRoleName(role.getRoleName());
		bankRoleVo.setOrgCode(role.getOrgCode());
		bankRoleVo.setRemark(role.getRemark());
		bankRoleVo.setClientId(role.getClientId());
		bankRoleVo.setStatus(true);
		bankRoleVo.setTag(role.getTag());
		
		CommonAddVoRes resultVo = bankRoleService.addBankRole(loginService.getOriginVo(req),bankRoleVo,role.getResourceList());
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
	 * 
	 * @param role
	 * @return
	 * @throws TException
	 */
	public HashMap<String,Object> roleUpdate(RoleVo role,HttpServletRequest req) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		BankRoleVo bankRoleVo = new BankRoleVo();
		bankRoleVo.setClientId(role.getClientId());
		bankRoleVo.setId(role.getRoleId());
		List<BankRoleVo> bankRoleList = bankRoleService.getBankRole(bankRoleVo);
		if(bankRoleList != null && bankRoleList.size() > 0){
			bankRoleVo = bankRoleList.get(0);
		}
		if (role.getRoleName() != null) { // 角色名
			bankRoleVo.setRoleName(role.getRoleName());
		}
		if (role.getRemark() != null) { // 备注
			bankRoleVo.setRemark(role.getRemark());
		}
		ResultVo resultVo = bankRoleService.updateBankRole(loginService.getOriginVo(req),bankRoleVo, role.getResourceList());
		
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
	 * 角色详情
	 * 
	 * @param roleId
	 * @return
	 * @throws TException
	 */
	public HashMap<String,Object> roleDetail(RoleReq role) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();		
		
		BankRoleVo bankRole = new BankRoleVo();
		bankRole.setClientId(role.getClientId());
		bankRole.setId(role.getRoleId());
		List<BankRoleVo> bankRoleList = bankRoleService.getBankRole(bankRole);
		
		if(bankRoleList != null && bankRoleList.size() > 0){
//			reMap.put("resourceList",  this.getResourceList(role,"1"));  
			reMap.putAll(frService.getResourceList(role));
			reMap.put("role", bankRoleList.get(0));
			reMap.put("code", EnumTypes.success.getCode());
			reMap.put("message", "角色详情查询成功");
		}else{
			reMap.put("code", EnumTypes.fail.getCode());
			reMap.put("message", "角色详情查询失败");
		}
		return reMap;
	}
	
	/**
	 * 角色删除
	 * 
	 * @param roleId
	 * @return
	 * @throws TException
	 */
	public HashMap<String,Object> roleDelete(RoleReq role,HttpServletRequest req) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();	

		BankRoleVo bankRole = new BankRoleVo();
		bankRole.setClientId(role.getClientId());
		bankRole.setId(role.getRoleId());
		ResultVo resultVo = bankRoleService.deleteBankRole(loginService.getOriginVo(req),bankRole);
		if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
			reMap.put("code", resultVo.getResultCode());
			reMap.put("message", "角色删除成功");
		}else{
			reMap.put("code", resultVo.getResultCode());
			reMap.put("message", resultVo.getResultDesc());
		}
		return reMap;
	}
	
	
	/**
	 * 获取所有角色
	 * 
	 * @return
	 * @throws TException
	 */
	public List<RoleVo> getRoleList(String clientId) throws TException{
		List<RoleVo> roleList = new ArrayList<RoleVo>();
		BankRoleVo bankRole = new BankRoleVo();
		bankRole.setClientId(clientId);
		bankRole.setIsDelete(false);
		List<BankRoleVo> bankRoleList = bankRoleService.getBankRole(bankRole);
		if(bankRoleList != null && bankRoleList.size() > 0){
			for(BankRoleVo bankRoleVo : bankRoleList){
				RoleVo roleVo = new RoleVo();
				roleVo.setRoleId(bankRoleVo.getId());
				roleVo.setRoleName(bankRoleVo.getRoleName());
				roleList.add(roleVo);
			}
		}
		return roleList;
	}
	
	
	/**
	 * 获取全部资源
	 * 
	 * @return
	 * @throws TException
	 */
	public HashMap<String,Object> getResourceList(RoleReq role,String tag) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		List<ResourceVo> afterList = new ArrayList<ResourceVo>();
		BankResourceVo bankResource = new BankResourceVo();
		bankResource.setClientId(role.getClientId());
		bankResource.setIsDelete(false);
		// 罗丽:查询未被禁用的资源
		bankResource.setStatus(true);
		List<BankResourceVo> beforeList = new ArrayList<BankResourceVo>();
		if(StringUtil.equals(tag, "1")){
			beforeList = bankRoleResourceService.getBankRoleResource(role.getClientId(), role.getRoleId());
		}else{
			beforeList = bankResourceService.getBankResource(bankResource);
		}
		if(beforeList != null && beforeList.size() > 0){
			for(int i=0;i<beforeList.size();i++){
					ResourceVo r = new ResourceVo();
					r.setResourceId(beforeList.get(i).getId());
					r.setResourceName(beforeList.get(i).getResourceName());
					r.setParentResourceId(beforeList.get(i).getParentResourceId());
					r.setResourceUrl(beforeList.get(i).getResourceUrl());
					r.setResourceIcon(beforeList.get(i).getResourceIcon());
					r.setTreePath(beforeList.get(i).getTreePath());
					r.setClientId(beforeList.get(i).getClientId());
					r.setOrderValue(beforeList.get(i).getOrderValue());
					afterList.add(r);
				}
		}
		for(int i=0;i<beforeList.size();i++){
			if(beforeList.get(i).getParentResourceId()>0){
				for(int j=0;j<afterList.size();j++){
					if(beforeList.get(i).getParentResourceId()==afterList.get(j).getResourceId()){
						ResourceVo r = new ResourceVo();
						r.setResourceId(beforeList.get(i).getId());
						r.setResourceName(beforeList.get(i).getResourceName());
						r.setParentResourceId(beforeList.get(i).getParentResourceId());
						r.setResourceUrl(beforeList.get(i).getResourceUrl());
						r.setResourceIcon(beforeList.get(i).getResourceIcon());
						r.setTreePath(beforeList.get(i).getTreePath());
						r.setClientId(beforeList.get(i).getClientId());
						r.setOrderValue(beforeList.get(i).getOrderValue());
						afterList.get(j).getrList().add(r);
					}
				}
			}
		}
		reMap.put("resourceList",  afterList);
		return reMap;
	}
	
	
	/**
	 * 获取当前角色资源
	 * 
	 * @return
	 * @throws TException
	 */
	public HashMap<String,Object> getRoleResourceList(RoleReq role) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		List<ResourceVo> afterList = new ArrayList<ResourceVo>();
		BankResourceVo bankResource = new BankResourceVo();
		bankResource.setClientId(role.getClientId());
		bankResource.setIsDelete(false);
		List<BankResourceVo> beforeList = bankRoleResourceService.getBankRoleResource(role.getClientId(), role.getRoleId());
		if(beforeList != null && beforeList.size() > 0){
			for(int i=0;i<beforeList.size();i++){
				if(beforeList.get(i).getParentResourceId() == 0){
					ResourceVo r = new ResourceVo();
					r.setResourceId(beforeList.get(i).getId());
					r.setResourceName(beforeList.get(i).getResourceName());
					r.setParentResourceId(beforeList.get(i).getParentResourceId());
					r.setResourceUrl(beforeList.get(i).getResourceUrl());
					r.setResourceIcon(beforeList.get(i).getResourceIcon());
					r.setTreePath(beforeList.get(i).getTreePath());
					r.setClientId(beforeList.get(i).getClientId());
					r.setOrderValue(beforeList.get(i).getOrderValue());
					afterList.add(r);
				}
			}
		}
		for(int i=0;i<beforeList.size();i++){
			if(beforeList.get(i).getParentResourceId()>0){
				for(int j=0;j<afterList.size();j++){
					if(beforeList.get(i).getParentResourceId()==afterList.get(j).getResourceId()){
						ResourceVo r = new ResourceVo();
						r.setResourceId(beforeList.get(i).getId());
						r.setResourceName(beforeList.get(i).getResourceName());
						r.setParentResourceId(beforeList.get(i).getParentResourceId());
						r.setResourceUrl(beforeList.get(i).getResourceUrl());
						r.setResourceIcon(beforeList.get(i).getResourceIcon());
						r.setTreePath(beforeList.get(i).getTreePath());
						r.setClientId(beforeList.get(i).getClientId());
						r.setOrderValue(beforeList.get(i).getOrderValue());
						afterList.get(j).getrList().add(r);
					}
				}
			}
		}
		// 排序
//		List<BeanComparator> sortFields = new ArrayList<BeanComparator>();
//		sortFields.add(new BeanComparator("orderValue"));
//		ComparatorChain multiSort = new ComparatorChain(sortFields);
//		Collections.sort(afterList, multiSort);
		reMap.put("resourceList",  afterList);
		return reMap;
	}
}
