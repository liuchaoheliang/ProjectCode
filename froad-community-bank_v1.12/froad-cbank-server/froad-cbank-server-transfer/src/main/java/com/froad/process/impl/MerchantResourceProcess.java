package com.froad.process.impl;

import java.util.List;


import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mappers.SysResourceMapper;
import com.froad.db.chonggou.entity.MerchantResource;
import com.froad.db.chonggou.mappers.MerchantResourceMapper;
import com.froad.fft.persistent.entity.SysResource;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;

public class MerchantResourceProcess  extends AbstractProcess{

	public MerchantResourceProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		SysResourceMapper mapper = ahSqlSession.getMapper(SysResourceMapper.class);
		
		merchantResourceMapper = sqlSession.getMapper(MerchantResourceMapper.class);
		
		List<SysResource> list = mapper.selectAllSysResource();
		
		
		
		MerchantResource mr = null;
		for (SysResource obj : list) {
			mr = new MerchantResource();
			mr.setName(obj.getName());
			mr.setIcon(obj.getIcon());
			
			mr.setUrl(obj.getCode());
			mr.setType(true);//待确认
			mr.setParentId(obj.getParentId());
			mr.setTreePath(obj.getTreePath());//待确认
			mr.setIsEnabled(obj.getIsEnabled());
			mr.setClientId(Constans.clientId);
			mr.setApi("");
			
			addMerchantResource(mr);
			
			
//			tf = new Transfer();
//			tf.setNewId(mr.getId().toString());
//			tf.setOldId(obj.getId().toString());
//			tf.setType(TransferTypeEnum.merchant_resource_id);
//			mapper1.insert(tf);
			
		}
		
//		LogCvt.info("商户菜单资源表迁移完成");
	}
	
	
	private MerchantResourceMapper merchantResourceMapper = null;
	  /**
     * 增加 MerchantResource
     * @param merchantResource
     * @return MerchantResourceAddRes
     */
	public void addMerchantResource(MerchantResource merchantResource) {

		
		try { 
			
//			merchantResourceMapper.addMerchantResource(merchantResource);
//			LogCvt.info("迁移MerchantResource成功"); 

		} catch (Exception e) { 
			LogCvt.error("添加MerchantResource失败，原因:" + e.getMessage(), e); 
		} 

	}



}

