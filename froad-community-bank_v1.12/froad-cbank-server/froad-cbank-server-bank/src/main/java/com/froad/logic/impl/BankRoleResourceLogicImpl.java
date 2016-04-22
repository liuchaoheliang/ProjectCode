package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;

import com.froad.db.mongo.BankUserResourceMongo;
import com.froad.db.mongo.bean.BankUserResource;
import com.froad.db.mongo.bean.ResourcesInfo;
import com.froad.logic.BankRoleResourceLogic;
import com.froad.po.BankResource;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: BankRoleResourceLogic.java</p>
 * <p>Description: 描述 </p> 角色资源关系表-若要查询从mongo中查
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BankRoleResourceLogicImpl implements BankRoleResourceLogic {


	/**
     * 查询 BankResource资源列表
     * @param clientId
     * @param roleId 
     * @return List<BankResource>    结果集合 
     */
	@Override
	public List<BankResource> findBankRoleResource(String clientId,Long roleId) {

		BankUserResource bankResource=BankUserResourceMongo.findById(clientId, roleId);
		List<BankResource> resultList=new ArrayList<BankResource>();
		
		
		if(Checker.isNotEmpty(bankResource)){
			List<ResourcesInfo> resources= bankResource.getResources();
			if(resources!=null && resources.size()>0){
				BankResource resultBankResource =null;
				for(ResourcesInfo r:resources){
					resultBankResource =new BankResource();
					resultBankResource.setId(r.getResourceId());
					resultBankResource.setResourceName(r.getResourceName());
					resultBankResource.setResourceType(BooleanUtils.toBooleanObject(r.getResourceType(),"1","0",""));
					resultBankResource.setTreePath(r.getTreePath());
					resultBankResource.setResourceIcon(r.getResourceIcon());
					resultBankResource.setResourceUrl(r.getResourceUrl());
					resultBankResource.setParentResourceId(r.getParentResourceId());
					resultBankResource.setApi(r.getApi());
					resultBankResource.setOrderValue(r.getOrderValue());
					
					resultList.add(resultBankResource);
				}
				//对List中的orderValue排序
				Collections.sort(resultList, new Comparator<Object>() {
					public int compare(Object o1, Object o2) {  
						BankResource p1 = (BankResource) o1;  
						BankResource p2 = (BankResource) o2;  
						if(Checker.isEmpty(p1.getOrderValue()) || Checker.isEmpty(p2.getOrderValue())){
							return 0;
						}
		                //根据orderValue进行升序
		                int flag = p1.getOrderValue().compareTo(p2.getOrderValue());  
		                return flag;  
		            }  
				});
				
			}
		}
		
		
		
		return resultList; 

	}

	
	
	/**
     * 查询 全部BankResource角色资源列表
     * @return List<BankResource>    结果集合 
     */
	public List<BankUserResource> findBankRoleResourceAll(){
		
		return BankUserResourceMongo.findByCondition(new HashMap<String, Object>());
	}
	
	

}