package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.bps.mappers.RoleResourceMapper;
import com.froad.db.chonggou.entity.BankResource;
import com.froad.db.chonggou.entity.BankUserResource;
import com.froad.db.chonggou.entity.ResourcesInfo;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.BankResourceMapper;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class BankRoleResourceProcess extends AbstractProcess {
	
	private RoleResourceMapper roleResourceMapper;
	private TransferMapper transferMapper;
	private BankResourceMapper bankResourceMapper;

	public BankRoleResourceProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}
	
	
	@Override
	public void before() {
		// 删除重庆旧数据
		DBObject dbObject = new BasicDBObject();
		Pattern pattern = Pattern.compile("^.*" + Constans.clientId+ ".*$", Pattern.CASE_INSENSITIVE);
		dbObject.put("_id", pattern);
		mongo.remove(dbObject, MongoTableName.CB_BANK_USER_RESOURCE);
	}
	
	
	/*
	 * 角色-资源因为新社区银行的资源有重新规划，所以
	 * 需要首先从旧库中把用户角色和资源的关系load下来，然后根据最新的角色资源规划用代码
	 * 初始化新库
	 */
	
	@Override
	public void process() {
		LogCvt.info("角色资源关系mongo表cb_bank_user_resource 数据迁移开始");
		roleResourceMapper = bpsSqlSession.getMapper(RoleResourceMapper.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		bankResourceMapper = sqlSession.getMapper(BankResourceMapper.class);
		
		List<Long> roleIds = roleResourceMapper.dinstinctRoleId();
		if(Checker.isEmpty(roleIds)){
			ConsoleLogger.info("原安徽bps表tr_role_resource 无数据需迁移");
			return;
		}
		LogCvt.info("原安徽bps表tr_role_resource 有"+roleIds.size()+"条数据需迁移");
		boolean result = false;
		
		Map<Long, Long> roleInfo = getRoleTransfer();
		Map<Long, String> resourceInfo = getResourceTransfer();
		// key-resource_id, value-ResourcesInfo
		Map<Long, ResourcesInfo> bankResourceInfo = getAllResourceInfo();
		
		for(Long roleId : roleIds){
			// 根据旧role_id找出新role_id
			if(Checker.isEmpty(roleInfo.get(roleId))) continue;
			
			List<String> resourceIds = roleResourceMapper.queryResourcesByRoleId(roleId);
			List<ResourcesInfo> resourcesInfo = new ArrayList<ResourcesInfo>();
			Map<Long, ResourcesInfo> addInfo = new HashMap<Long, ResourcesInfo>();
			for(String resourceId : resourceIds){
				// 根据旧resource_id找出新resource_id
				if(Checker.isEmpty(resourceInfo.get(Long.valueOf(resourceId)))) continue;
				String[] newResourceIds = resourceInfo.get(Long.valueOf(resourceId)).split(",");
				for(String newId : newResourceIds){
					Long newRId = Long.valueOf(newId);
					ResourcesInfo resource = bankResourceInfo.get(newRId);
					if(Checker.isEmpty(resource)) continue;
					addInfo.put(newRId, resource);
				}
			}
			Set<Long> set = addInfo.keySet();
			for(Long l : set){
				ResourcesInfo r = addInfo.get(l);
				resourcesInfo.add(r);
			}
			
			result = addBankRoleResource(roleInfo.get(roleId), resourcesInfo);
			LogCvt.info("目标mongo表cb_bank_user_resource 数据迁移"+(result?"成功":"失败"));
		}
		ConsoleLogger.info("角色资源关系mongo表cb_bank_user_resource 数据迁移结束");
	}
	
	private Map<Long, String> getResourceTransfer(){
		List<Transfer> list = transferMapper.queryGroupList(TransferTypeEnum.bank_resource_id);
		Map<Long, String> info = new HashMap<Long, String>();
		for(Transfer transfer : list){
			info.put(Long.valueOf(transfer.getOldId()), transfer.getNewId());
		}
		return info;
	}
	
	private Map<Long, Long> getRoleTransfer(){
		List<Transfer> list = transferMapper.queryGroupList(TransferTypeEnum.bank_role_id);
		Map<Long, Long> info = new HashMap<Long, Long>();
		for(Transfer transfer : list){
			info.put(Long.valueOf(transfer.getOldId()), Long.valueOf(transfer.getNewId()));
		}
		return info;	
	}
	
	private boolean addBankRoleResource(Long roleId, List<ResourcesInfo> resourcesInfo){
		int result = -1;
		String id = getBankRoleResourceId(roleId);
		BankUserResource b = mongo.findOneById(id, MongoTableName.CB_BANK_USER_RESOURCE, BankUserResource.class);
		if(b == null){
			BankUserResource bankUserResource = new BankUserResource();
			bankUserResource.setId(getBankRoleResourceId(roleId));
			bankUserResource.setResources(resourcesInfo);
			result = mongo.add(bankUserResource, MongoTableName.CB_BANK_USER_RESOURCE);
		}
		return result != -1;
	}
	
	
	private String getBankRoleResourceId(Long roleId){
		return Constans.clientId+"_"+roleId;
	}
	
	
	private ResourcesInfo changeToResource(BankResource bankResource){
		ResourcesInfo resource = new ResourcesInfo();
		resource.setResourceId(bankResource.getId());
		resource.setResourceName(ObjectUtils.toString(bankResource.getResourceName()));
		resource.setApi(ObjectUtils.toString(bankResource.getApi()));
		resource.setResourceType(BooleanUtils.toString(bankResource.getResourceType(), "1", "0", ""));
		resource.setParentResourceId(bankResource.getParentResourceId());
		resource.setResourceIcon(ObjectUtils.toString(bankResource.getResourceIcon()));
		resource.setTreePath(ObjectUtils.toString(bankResource.getTreePath()));
		resource.setResourceUrl(ObjectUtils.toString(bankResource.getResourceUrl()));
		resource.setOrderValue(bankResource.getOrderValue());
		return resource;
	}
	
	
	private Map<Long, ResourcesInfo> getAllResourceInfo(){
		Map<Long, ResourcesInfo> map = new TreeMap<Long, ResourcesInfo>();
		List<BankResource> list = bankResourceMapper.findAllResources();
		for(BankResource b : list){
			ResourcesInfo resource = changeToResource(b);
			map.put(b.getId(), resource);
		}
		return map;
	}
	
}
