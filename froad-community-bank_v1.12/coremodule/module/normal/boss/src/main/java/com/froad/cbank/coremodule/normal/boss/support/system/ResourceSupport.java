package com.froad.cbank.coremodule.normal.boss.support.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.system.FinityResourceVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.system.FinityResourceVoRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.FFTUserRoleService;
import com.froad.thrift.service.FinityResourceService;
import com.froad.thrift.service.FinityRoleService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.finity.FinityResourceVo;
import com.froad.thrift.vo.finity.FinityRoleListVoRes;
import com.froad.thrift.vo.finity.FinityRoleVo;

@Service
public class ResourceSupport {
	
	@Resource
	private FinityResourceService.Iface finityResourceService;
	
	@Resource
	private FinityRoleService.Iface finityRoleService;
	
	@Resource
	private FFTUserRoleService.Iface fftUserRoleService;
	
	/**
	 * 
	 * <p>Title: 查询资源列表</p>
	 * @author denghaoqi@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月5日 上午10:41:42 
	 * @param resourceRep
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String,Object> queryList() throws TException, BossException{
		FinityResourceVo resourceReq =new FinityResourceVo();
		//封装请求对象
		resourceReq.setIsDelete(false);
		//resourceReq.setPlatform("boss");
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<FinityResourceVoRes> resList = new ArrayList<FinityResourceVoRes>();
		List<FinityResourceVo> voList =new ArrayList<FinityResourceVo>();
		
		//调用server端查询资源接口
		voList=finityResourceService.getBossFinityResource(resourceReq);
		// 封装后台返回的数据
		for (FinityResourceVo finityResourceVo : voList) {
			FinityResourceVoRes res=new FinityResourceVoRes();
			res.setId(finityResourceVo.getId());
			res.setResourceName(finityResourceVo.getResourceName());
			res.setType(finityResourceVo.getType());
			res.setParentResourceId(finityResourceVo.getParentResourceId());
			res.setResourceUrl(finityResourceVo.getResourceUrl());
			res.setResourceIcon(finityResourceVo.getResourceIcon());
			res.setTreePath(finityResourceVo.getTreePath());
			res.setIsDelete(finityResourceVo.isIsDelete());
			res.setOrderValue(finityResourceVo.getOrderValue());
			res.setIsSystem(finityResourceVo.isIsSystem());
			res.setIsMenu(finityResourceVo.isIsMenu());
			res.setPlatform(finityResourceVo.getPlatform());
			res.setResource_key(finityResourceVo.getResourceKey());
			res.setIs_limit(finityResourceVo.isIsLimit());
//			if(StringUtil.isNotBlank(finityResourceVo.getResourceIcon())){
//				res.setIconSkin("fa "+finityResourceVo.getResourceIcon());
//			}
			resList.add(res);
		}
		map.put("list", resList);
		map.put("success", Constants.RESULT_SUCCESS);
		return map;
	}
	
	/**
	 * 
	 * <p>Title: 查询资源列表(详情)</p>
	 * @author denghaoqi@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月5日 上午10:41:42 
	 * @param resourceRep
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String,Object> detail(Long id) throws TException, BossException{
		FinityResourceVo resourceReqVo =new FinityResourceVo();
		//封装请求对象
		resourceReqVo.setIsDelete(false);//是否删除(false-未删除 true-删除)
		resourceReqVo.setId(id);//资源id
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<FinityResourceVoRes> resList = new ArrayList<FinityResourceVoRes>();
		List<FinityResourceVo> voList =new ArrayList<FinityResourceVo>();
		
		//调用server端查询资源接口
		voList=finityResourceService.getBossFinityResource(resourceReqVo);
		// 封装后台返回的数据
		for (FinityResourceVo finityResourceVo : voList) {
			FinityResourceVoRes res=new FinityResourceVoRes();
			res.setId(finityResourceVo.getId());
			res.setResourceName(finityResourceVo.getResourceName());
			res.setType(finityResourceVo.getType());
			res.setParentResourceId(finityResourceVo.getParentResourceId());
			//获取父级资源名称
			if(finityResourceVo.getParentResourceId()==0){
				res.setParentResourceName(finityResourceVo.getResourceName());
			}else{
				List<FinityResourceVo> list = new ArrayList<FinityResourceVo>();
				FinityResourceVo req =new FinityResourceVo();
				req.setIsDelete(false);
				req.setId(finityResourceVo.getParentResourceId());
				list=finityResourceService.getFinityResource(req);
				res.setParentResourceName(list.get(0).getResourceName());
			}
			
			
			res.setResourceUrl(finityResourceVo.getResourceUrl());
			res.setResourceIcon(finityResourceVo.getResourceIcon());
			res.setTreePath(finityResourceVo.getTreePath());
			res.setIsDelete(finityResourceVo.isIsDelete());
			res.setOrderValue(finityResourceVo.getOrderValue());
			res.setIsSystem(finityResourceVo.isIsSystem());
			res.setIsMenu(finityResourceVo.isIsMenu());
			res.setPlatform(finityResourceVo.getPlatform());
			res.setResource_key(finityResourceVo.getResourceKey());
			res.setIs_limit(finityResourceVo.isIsLimit());
//			if(StringUtil.isNotBlank(finityResourceVo.getResourceIcon())){
//				res.setIconSkin("fa "+finityResourceVo.getResourceIcon());
//			}
			resList.add(res);
		}
		map.put("list", resList);
		map.put("success", Constants.RESULT_SUCCESS);
		return map;
	}
	
	/**
	 * 
	 * <p>Title: 查询资源列表(用户)</p>
	 * @author denghaoqi@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月5日 上午10:41:42 
	 * @param userId
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String,Object> queryListByUser(Long userId) throws TException, BossException{
		FinityResourceVo resourceReq =new FinityResourceVo();
		//封装请求对象
		resourceReq.setIsDelete(false);
		
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<FinityResourceVoRes> resList = new ArrayList<FinityResourceVoRes>();
		List<FinityResourceVo> voList =new ArrayList<FinityResourceVo>();
		
		//根据用户查询角色,判断是否超级管理员
		List<Long> roles = new ArrayList<Long>();
		boolean isAdmin = false;
		FinityRoleListVoRes roleRes = finityRoleService.getFinityRoleByUserIdLogin(userId);
		if(Constants.RESULT_SUCCESS.equals(roleRes.getResultVo().getResultCode())){
			List<FinityRoleVo> list = roleRes.getVoList();
			if(list.size()>0){
				for(FinityRoleVo v : list){
					if(!isAdmin && v.isIsAdmin()){
						isAdmin = true;
					}
					roles.add(v.getId());
				}
			}else{
				throw new BossException("用户角色不存在!");
			}
		}else{
			throw new BossException(roleRes.getResultVo().getResultCode(),roleRes.getResultVo().getResultDesc());
		}
		if(isAdmin){
			voList=finityResourceService.getBossFinityResource(resourceReq);
		}else{
			//非超级管理员只查boss平台资源
			resourceReq.setPlatform("boss");
			//调用server端根据用户查询资源接口
			voList=finityResourceService.getFinityResourceByUser(resourceReq, userId,0);
			
		}
		// 封装后台返回的数据
		for (FinityResourceVo finityResourceVo : voList) {
			FinityResourceVoRes res=new FinityResourceVoRes();
			res.setId(finityResourceVo.getId());
			res.setResourceName(finityResourceVo.getResourceName());
			res.setType(finityResourceVo.getType());
			res.setParentResourceId(finityResourceVo.getParentResourceId());
			res.setResourceUrl(finityResourceVo.getResourceUrl());
			res.setResourceIcon(finityResourceVo.getResourceIcon());
			res.setTreePath(finityResourceVo.getTreePath());
			res.setIsDelete(finityResourceVo.isIsDelete());
			res.setOrderValue(finityResourceVo.getOrderValue());
			res.setIsSystem(finityResourceVo.isIsSystem());
			res.setIsMenu(finityResourceVo.isIsMenu());
			res.setPlatform(finityResourceVo.getPlatform());
			res.setResource_key(finityResourceVo.getResourceKey());
			res.setIs_limit(finityResourceVo.isIsLimit());
//			if(StringUtil.isNotBlank(finityResourceVo.getResourceIcon())){
//				res.setIconSkin("fa "+finityResourceVo.getResourceIcon());
//			}
			resList.add(res);
		}
		map.put("list", resList);
		map.put("success", Constants.RESULT_SUCCESS);
		return map;
	}
	
	/**
	 * 
	 * <p>Title: 查询资源列表(角色)</p>
	 * @author denghaoqi@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月5日 上午10:41:42 
	 * @param roles
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String,Object> queryListByRole(String roles,String platform) throws TException,BossException{
		FinityResourceVo resourceReq =new FinityResourceVo();
		//封装请求对象
		resourceReq.setIsDelete(false);
		resourceReq.setPlatform(platform);
		
		//拆分roles
		List<Long> roleList=new ArrayList<Long>();
		String[] role = roles.split(",");
		for (int i = 0; i < role.length; i++) {
			roleList.add(Long.parseLong(role[i]));
		}
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<FinityResourceVoRes> resList = new ArrayList<FinityResourceVoRes>();
		List<FinityResourceVo> voList =new ArrayList<FinityResourceVo>();
		
		//调用server端根据角色查询资源接口
		voList=finityResourceService.getFinityResourceByRole(resourceReq, roleList);
		// 封装后台返回的数据
		for (FinityResourceVo finityResourceVo : voList) {
			FinityResourceVoRes res=new FinityResourceVoRes();
			res.setId(finityResourceVo.getId());
			res.setResourceName(finityResourceVo.getResourceName());
			res.setType(finityResourceVo.getType());
			res.setParentResourceId(finityResourceVo.getParentResourceId());
			res.setResourceUrl(finityResourceVo.getResourceUrl());
			res.setResourceIcon(finityResourceVo.getResourceIcon());
			res.setTreePath(finityResourceVo.getTreePath());
			res.setIsDelete(finityResourceVo.isIsDelete());
			res.setOrderValue(finityResourceVo.getOrderValue());
			res.setIsSystem(finityResourceVo.isIsSystem());
			res.setIsMenu(finityResourceVo.isIsMenu());
			res.setPlatform(finityResourceVo.getPlatform());
			res.setResource_key(finityResourceVo.getResourceKey());
			res.setIs_limit(finityResourceVo.isIsLimit());
//			if(StringUtil.isNotBlank(finityResourceVo.getResourceIcon())){
//				res.setIconSkin("fa "+finityResourceVo.getResourceIcon());
//			}
			resList.add(res);
		}
		map.put("list", resList);
		map.put("success", Constants.RESULT_SUCCESS);
		return map;
		
	}
	
	/**
	 * 
	 * <p>Title: 新增资源</p>
	 * @author denghaoqi@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月5日 上午10:41:42 
	 * @param resourceRep
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String,Object> save(OriginVo reqOrg,FinityResourceVoReq resourceReq) throws TException, BossException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		FinityResourceVo req=new FinityResourceVo();
		//封装请求对象
		req.setResourceName(resourceReq.getResourceName());//资源名称
		req.setType(resourceReq.getType());//资源类型(1、平台，2、模块，3、元素)
		req.setParentResourceId(resourceReq.getParentResourceId());//父级资源Id(顶级资源的父级资源ID为0)
		req.setResourceUrl(resourceReq.getResourceUrl());//资源url
		req.setResourceIcon(resourceReq.getResourceIcon());//资源图标
		req.setTreePath(resourceReq.getTreePath());//资源路径(逗号分隔)
		req.setIsDelete(false);//是否删除(false-未删除 true-删除)
		req.setOrderValue(resourceReq.getOrderValue());//排序值
		req.setIsSystem(false);//是否系统资源true-是,false-否
		req.setIsMenu(resourceReq.getIsMenu());//是否为菜单true-是，false-否
		req.setPlatform(resourceReq.getPlatform());//平台名称（boss、bank、merchant）
		req.setResourceKey(resourceReq.getResource_key());//资源key	
		req.setIsLimit(false);//是否需要数据权限控制
		req.setStatus(true);
		//req.setUpdateTime(System.currentTimeMillis());//更新时间
		
		//调用server端资源新增接口
		CommonAddVoRes addRes=finityResourceService.addFinityResource(reqOrg,req);
		//封装返回对象
		if(Constants.RESULT_SUCCESS.equals(addRes.getResult().getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "新增资源信息成功");
		}else{
			throw new BossException(addRes.getResult().getResultCode(), addRes.getResult().getResultDesc());
		}
		
		return map;
	}
	
	/**
	 * 
	 * <p>Title: 更新资源</p>
	 * @author denghaoqi@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月5日 上午10:41:42 
	 * @param resourceRep
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String,Object> update(OriginVo reqOrg,FinityResourceVoReq resourceReq) throws TException,BossException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		FinityResourceVo req=new FinityResourceVo();
		//封装请求对象
		req.setId(resourceReq.getId());//id
		req.setResourceName(resourceReq.getResourceName());//资源名称
		req.setResourceKey(resourceReq.getResource_key());//资源key	
		if(StringUtil.isNotBlank(resourceReq.getResourceUrl())){
			req.setResourceUrl(resourceReq.getResourceUrl());//资源url
		}
		if(StringUtil.isNotBlank(resourceReq.getResourceIcon())){
			req.setResourceIcon(resourceReq.getResourceIcon());//资源图标
		}
		if(resourceReq.getIsMenu()!=null){
			req.setIsMenu(resourceReq.getIsMenu());//是否为菜单true-是，false-否
		}
		//req.setOrderValue(resourceReq.getOrderValue().intValue());//排序值
		//req.setType(resourceReq.getType().intValue());//资源类型(1、平台，2、模块，3、元素)
		//req.setIsDelete(resourceReq.getIsDelete());//是否删除(false-未删除 true-删除)
		//req.setIsSystem(resourceReq.getIsSystem());//是否系统资源true-是,false-否
		//req.setIsLimit(resourceReq.getIs_limit());//是否需要数据权限控制
		//req.setTreePath(resourceReq.getTreePath());//资源路径(逗号分隔)
		//req.setPlatform(resourceReq.getPlatform());//平台名称（boss、bank、merchant）
		//req.setParentResourceId(resourceReq.getParentResourceId());//父级资源Id(顶级资源的父级资源ID为0)
		//req.setUpdateTime(System.currentTimeMillis());//更新时间
		
		//调用server端资源更新接口
		ResultVo resultVo=finityResourceService.updateFinityResource(reqOrg,req);
		//封装返回对象
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "修改资源信息成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		
		return map;
	}
	
	/**
	 * 
	 * <p>Title: 删除资源</p>
	 * @author denghaoqi@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月5日 上午10:41:42 
	 * @param platform
	 * @param resourceId
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String,Object> delete(OriginVo reqOrg,String platform,Long resourceId) throws TException,BossException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		//调用server端资源删除接口
		ResultVo resultVo=finityResourceService.deleteFinityResource(reqOrg,platform,resourceId);
		//封装返回对象
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "删除资源信息成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 
	 * <p>Title: 调整资源顺序</p>
	 * @author denghaoqi@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月5日 上午10:41:42 
	 * @param resourceRep
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String,Object> move(OriginVo originVo,String ids,String orderValues) throws TException,BossException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		
		//封装请求对象
		List<FinityResourceVo> list = new ArrayList<FinityResourceVo>();
		//拆分ids,orderValues
		String[] id=ids.split(",");
		String[] orderValue=orderValues.split(",");
		for (int i = 0; i < id.length; i++){
			FinityResourceVo finityResourceVo=new FinityResourceVo();
			finityResourceVo.setId(Long.parseLong(id[i]));
			finityResourceVo.setOrderValue(Integer.parseInt(orderValue[i]));
			list.add(finityResourceVo);
		}
		
		//调用server端资源删除接口
		ResultVo resultVo=finityResourceService.moveMenu(originVo,list);
		//封装返回对象
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "调整资源信息顺序成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		
		return map;
	}
}
