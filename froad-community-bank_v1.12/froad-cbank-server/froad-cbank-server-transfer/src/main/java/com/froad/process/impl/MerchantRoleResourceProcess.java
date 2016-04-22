package com.froad.process.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.MerchantResource;
import com.froad.db.chonggou.entity.MerchantRole;
import com.froad.db.chonggou.entity.MerchantRoleResource;
import com.froad.db.chonggou.entity.Resource;
import com.froad.db.chonggou.mappers.MerchantResourceMapper;
import com.froad.db.chonggou.mappers.MerchantRoleMapper;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;

public class MerchantRoleResourceProcess extends AbstractProcess{

	public MerchantRoleResourceProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		
		MerchantRoleMapper mapper2 = sqlSession.getMapper(MerchantRoleMapper.class);
		MerchantResourceMapper mapper3 = sqlSession.getMapper(MerchantResourceMapper.class);
		
		List<MerchantRole> lRole = mapper2.findMerchantRole(new MerchantRole());
		List<MerchantResource> lResource = mapper3.findMerchantResource(new MerchantResource());
		
		MerchantResource mre = null;
		MerchantRoleResource mrr = null;
		List listr = null;
		Resource resource = null;
		
		
		for (MerchantRole role : lRole) {
			
			mrr = new MerchantRoleResource();
			mrr.set_id(Constans.clientId+"_"+role.getId());
			listr = new ArrayList();
		
			for (MerchantResource obj : lResource) {
				
				
				
				resource = new Resource();
				resource.setResource_id(obj.getId());
				resource.setApi(obj.getApi());
				resource.setIcon(obj.getIcon());
				resource.setParent_id(obj.getParentId() == null ? 0l : obj.getParentId());
				resource.setResource_name(obj.getName());
				resource.setResource_url(obj.getUrl());
				resource.setTree_path(obj.getTreePath());
				resource.setResource_type(obj.getType() ? "1" : "0");
				
				listr.add(resource);
				
				
			}
//			mrr.setResources(listr);
//			addMerchantRoleResource(mrr);
			
		}
		
	}

	
	 /**
    * 增加 MerchantRoleResource
    * @param merchantRoleResource
    * @return MerchantRoleResourceAddRes
    */
	public void addMerchantRoleResource(MerchantRoleResource merchantRoleResource) {

		try { 
			mongo.add(merchantRoleResource, CB_MERCHANT_ROLE_RESOURCE);
		} catch (Exception e) { 
			LogCvt.error("添加MerchantRoleResource失败，原因:" + e.getMessage(), e); 
		} 

	}
	
	private static final String CB_MERCHANT_ROLE_RESOURCE = "cb_merchant_role_resource";
	public static final String MONGO_KEY__ID                 = "_id";
	
	  /**
     * 增加 MerchantRoleResource
     * @param merchantRoleResource
     * @return MerchantRoleResourceAddRes
     */
	public void addMerchantRoleResource2(MerchantRoleResource merchantRoleResource) {

		try { 

			
			/**********************操作Mongodb数据库**********************/
			
//			if( manager.add(merchantRoleResource, CB_MERCHANT_ROLE_RESOURCE) > -1 ){
//			}else{
//			}
			MongoManager manager = new MongoManager(); 
			manager.add(merchantRoleResource, CB_MERCHANT_ROLE_RESOURCE);		
		} catch (Exception e) { 
			LogCvt.error("添加MerchantRoleResource失败，原因:" + e.getMessage(), e); 
		} 

	}

}

