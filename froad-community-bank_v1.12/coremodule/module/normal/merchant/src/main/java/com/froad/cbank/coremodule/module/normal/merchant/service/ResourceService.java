/**   
 * @author liuhuangle@f-road.com.cn  
 * @date 2015年10月16日 下午2:19:02 
 */
package com.froad.cbank.coremodule.module.normal.merchant.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.module.normal.merchant.enums.UserType;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.FinitResourceVo;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.thrift.service.FinityResourceService;
import com.froad.thrift.vo.finity.FinityResourceVo;

/**
 * 权限资源
 * @author liuhuangle@f-road.com.cn
 */
@Service
public class ResourceService {
	
	@Resource
	FinityResourceService.Iface finityResourceService;
	
	/**
	 * @throws TException    
	 * 根据用户查询资源
	 * @description 
	 * @author liuhuangle@f-road.com.cn  
	 * @date 2015年10月15日 下午6:56:30  
	 * @param resource
	 * @param userId
	 * @return  
	 * @throws  
	 */
	public HashMap<String,Object> findResourceByUser(FinitResourceVo reqVo, Long userId,String merchantRoleId) throws TException {
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		FinityResourceVo finityResourceVo = new FinityResourceVo();
		finityResourceVo.setIsDelete(false);
 		finityResourceVo.setStatus(true);
 		finityResourceVo.setPlatform("merchant");//设置获取银行平台资源 
 		
 		List<FinityResourceVo> beforeList = finityResourceService.getFinityResourceByUser(finityResourceVo, userId,1); 
		List<FinitResourceVo> afterList = new ArrayList<FinitResourceVo>();  
		if(beforeList==null || beforeList.size()<1){
			reMap.put("rootResources",  null);
			return reMap;
		}
		FinitResourceVo tyPo=null;
		for(FinityResourceVo tyVo:beforeList){//thrift 到 coremodule实体转换，避免thrift一些临时属性 
			tyPo=new FinitResourceVo(); 
			if(UserType.admin.getCode().equals(merchantRoleId) && !tyVo.isSystem){//如果是超级管理员只能查看到系统资源
				continue;
			}
			BeanUtils.copyProperties(tyPo, tyVo);
			tyPo.setIsCheck(true);
			afterList.add(tyPo);
		} 
		List<FinitResourceVo> rootResources = new ArrayList<FinitResourceVo>();
		for (FinitResourceVo tree : afterList) {//资源树整理 
            if(tree.getParentResourceId() == 200000000){
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
		reMap.put("rootResources",  rootResources);
		return reMap;
 		 
	}
	
	
	 
	/**   
	 * 根据用户查询资源--筛选被查看用户资源
	 * @description 
	 * @author liuhuangle@f-road.com.cn  
	 * @date 2015年10月16日 下午2:49:18  
	 * @param reqVo
	 * @param loginUserId  当前登录用户
	 * @param currentUserId  需要查看用户
	 * @return
	 * @throws TException  
	 * @throws  
	 */
	public HashMap<String,Object> findResourceByUser(FinitResourceVo reqVo, Long loginUserId,Long currentUserId) throws TException {
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		FinityResourceVo finityResourceVo = new FinityResourceVo();
		finityResourceVo.setIsDelete(false);
 		finityResourceVo.setStatus(true);
 		finityResourceVo.setPlatform("merchant");//设置获取银行平台资源 
 		
 		List<FinityResourceVo> beforeList = finityResourceService.getFinityResourceByUser(finityResourceVo, loginUserId,1); 
 		
 		List<FinityResourceVo> currUserList= finityResourceService.getFinityResourceByUser(finityResourceVo, currentUserId,1); 
 		
		List<FinitResourceVo> afterList = new ArrayList<FinitResourceVo>();  
		if(beforeList==null || beforeList.size()<1 || currUserList==null){
			reMap.put("rootResources",  null);
			return reMap;
		}
		FinitResourceVo tyPo=null;
		for(FinityResourceVo tyVo:beforeList){//thrift 到 coremodule实体转换，避免thrift一些临时属性 
			tyPo=new FinitResourceVo(); 
			BeanUtils.copyProperties(tyPo, tyVo);  
			
			for(FinityResourceVo curVo:currUserList){//如果是查看用户已有权限则标记check
				if(tyPo.getId()==curVo.getId()){
					tyPo.setIsCheck(true);
				}
			}	
			//如果是当前用户查询自己的权限列表，默认全部checked
			if(loginUserId.intValue()==currentUserId.longValue()){
				tyPo.setIsCheck(true);
			}
			afterList.add(tyPo);
		} 
		List<FinitResourceVo> rootResources = new ArrayList<FinitResourceVo>();
		for (FinitResourceVo tree : afterList) {//资源树整理 
            if(tree.getParentResourceId() == 200000000){
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
		reMap.put("rootResources",  rootResources);
		return reMap;
 		 
	}

}
