package com.froad.process.impl;

import java.util.List;


import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mappers.SysRoleMapper;
import com.froad.db.chonggou.entity.MerchantRole;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantRoleMapper;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.SysRole;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;

public class MerchantRoleProcess  extends AbstractProcess{

	public MerchantRoleProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		merchantRoleMapper = sqlSession.getMapper(MerchantRoleMapper.class);
		
		SysRoleMapper mapper = ahSqlSession.getMapper(SysRoleMapper.class);
		List<SysRole> list = mapper.selectSysRoleAll();
		
		TransferMapper mapper1 = sqlSession.getMapper(TransferMapper.class);
	
		Transfer tf = null;
		MerchantRole role = null;
		for (SysRole obj : list) {
			role = new MerchantRole();
			
//			role.setId(obj.getId());
			role.setName(obj.getName());
			role.setDescription(obj.getDescription());
			role.setIsDelete(false);
			role.setClientId(Constans.clientId);
			
//			addMerchantRole(role);
			
//			tf = new Transfer();
//			tf.setNewId(role.getId().toString());
//			tf.setOldId(obj.getId().toString());
//			tf.setType(TransferTypeEnum.merchant_role_id);
//			mapper1.insert(tf);
////			System.out.println("---------");
			
		}
//		LogCvt.info("商户角色迁移完成");
		
		
		
	}
	private MerchantRoleMapper merchantRoleMapper = null;
	
	 /**
     * 增加 MerchantRole
     * @param merchantRole
     * @return MerchantRoleAddRes
     */
	public void addMerchantRole(MerchantRole merchantRole) {

		
		try { 
			
			merchantRoleMapper.addMerchantRole(merchantRole);
		} catch (Exception e) { 
			LogCvt.error("添加MerchantRole失败，原因:" + e.getMessage(), e); 
		}

	}
	
	

}

