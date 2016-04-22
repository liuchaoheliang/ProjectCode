package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.bps.entity.Resource;
import com.froad.db.bps.mappers.ResourceMapper;
import com.froad.db.chonggou.entity.BankResource;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.BankResourceMapper;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;

public class BankResourceProcess extends AbstractProcess {
	
	private BankResourceMapper bankResourceMapper;
	private ResourceMapper resourceMapper;
	private TransferMapper transferMapper;
	
	public BankResourceProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}
	
	/*
	 * 角色-资源因为新社区银行的资源有重新规划，所以
	 * 需要首先从旧库中把用户角色和资源的关系load下来，然后根据最新的角色资源规划用代码
	 * 初始化新库
	 */
	
	@Override
	public void process() {
		resourceMapper = bpsSqlSession.getMapper(ResourceMapper.class);
		bankResourceMapper = sqlSession.getMapper(BankResourceMapper.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		transfer();
		
	}
	
	
	private void transfer(){
		LogCvt.info("资源表cb_resource 数据迁移开始");
		
		List<Resource> resources = resourceMapper.selectAllResources();
		if(Checker.isEmpty(resources)){
			LogCvt.info("原安徽bps表tm_resource 无数据需迁移");
			return;
		}
		LogCvt.info("原安徽bps表tm_resource 共有"+resources.size()+"条数据需迁移");
		for(Resource resource : resources){
			BankResource bankResource = new BankResource();
			bankResource.setClientId(Constans.clientId);
			bankResource.setResourceName(resource.getResourceName());
			bankResource.setResourceType(BooleanUtils.toBooleanObject(resource.getResourceType(), "H", "M", ""));////M:菜单H:按钮
			bankResource.setParentResourceId(Long.valueOf(resource.getFatherResource()));
			bankResource.setStatus(BooleanUtils.toBooleanObject(resource.getStatus(), "1", "0", ""));
			bankResource.setResourceUrl(ObjectUtils.toString(resource.getMenuUrl(), ""));
			bankResource.setTreePath("");
			bankResource.setIsDelete(false);
			Long result = bankResourceMapper.addBankResource(bankResource);
			
			Transfer transfer = new Transfer();
			transfer.setOldId(ObjectUtils.toString(resource.getResourceId()));
			transfer.setNewId(ObjectUtils.toString(bankResource.getId()));
			transfer.setType(TransferTypeEnum.bank_resource_id);
			transferMapper.insert(transfer);
			
			LogCvt.info("银行资源[resourceId:"+resource.getResourceId()+"] 数据迁移"+(result > 0?"成功":"失败"));
		}
		
		Map<Long, Long> info = getBankResourceInfo();
		
		for(Resource resource : resources){
			BankResource bankResource = new BankResource();
			
			// 获取新资源ID
			bankResource.setId(info.get(resource.getResourceId()));
			
			// 获取对应父级资源ID
			if("0".equals(resource.getFatherResource())){
				bankResource.setParentResourceId(0l);
				bankResource.setTreePath("0");
			}else{
				Long parentResourceId = info.get(Long.valueOf(resource.getFatherResource()));
				bankResource.setParentResourceId(parentResourceId);
				String treePath = combineTreePath(resource, info);
				bankResource.setTreePath(treePath);
			}
			
			// 更新父级资源ID/树级路径tree_path
			boolean result = bankResourceMapper.updateBankResource(bankResource);
			
			LogCvt.info("银行资源[resourceId:"+resource.getResourceId()+"] 数据更新"+(result?"成功":"失败"));
		}
		ConsoleLogger.info("资源表cb_resource 数据迁移结束");
	}
	
	private Map<Long, Long> getBankResourceInfo(){
		List<Transfer> transfers = transferMapper.queryGroupList(TransferTypeEnum.bank_resource_id);
		Map<Long, Long> info = new HashMap<Long, Long>();
		for(Transfer transfer : transfers){
			info.put(Long.valueOf(transfer.getOldId()), Long.valueOf(transfer.getNewId()));
		}
		return info;
	}
	
	private String combineTreePath(Resource resource, Map<Long, Long> resourceMap){
		StringBuilder sb = new StringBuilder();
		if(Checker.isNotEmpty(resource)){
			if("0".equals(resource.getFatherResource())){
				Long id = resourceMap.get(resource.getResourceId());
				sb.append(id+" ");
			}else{
				Long fatherId = Long.valueOf(resource.getFatherResource());
				Resource parent = resourceMapper.selectByResourceId(fatherId);
				sb.append(combineTreePath(parent, resourceMap)).append(resourceMap.get(resource.getResourceId())+" ");
			}
		}
		return sb.toString();
	}
	
	
	private void initResource(){
		List<Resource> resources = resourceMapper.selectAllResources();
		if(Checker.isEmpty(resources)){
			LogCvt.info("原安徽bps表tm_resource 无数据需迁移");
			return;
		}
		LogCvt.info("原安徽bps表tm_resource 共有"+resources.size()+"条数据需迁移");
		
		
	}
	
}
