package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.MerchantGroupUserEx;
import com.froad.db.ahui.mappers.MerchantUserMapper;
import com.froad.db.chonggou.entity.MerchantUser;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.process.AbstractProcess;

public class MerchantUserUtil extends AbstractProcess{

	public MerchantUserUtil(String name, ProcessServiceConfig config) {
		super(name, config);
	}
	
	private final Map<String,String> newIdMap = new HashMap<String,String>();
	private TransferMapper transferMapper = null;
	private MerchantUserMapper merchantUserMapper = null;
	private com.froad.db.ahui.mappers.MerchantGroupUserMapper mapper1 = null;
	
	
	@Override
	public void process() {
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);
		mapper1 = ahSqlSession.getMapper(com.froad.db.ahui.mappers.MerchantGroupUserMapper.class);
		
		List<Transfer> listUser = transferMapper.queryGroupList(TransferTypeEnum.merchant_user_id);
		for (Transfer t : listUser) {
			newIdMap.put(t.getOldId(), t.getNewId());
		}
		
		
		List<MerchantGroupUserEx> list = mapper1.findAllMerchantGroupUserAll();
		
		String id = "";
		
		for (MerchantGroupUserEx obj : list) {
			id = newIdMap.get(obj.getId()+"") == null ? "0" : newIdMap.get(obj.getId()+"");
			
			MerchantUser user = merchantUserMapper.findMerchantUserById(Long.valueOf(id));
			
			if(user != null){
				user.setRealname(obj.getAlias());
				merchantUserMapper.updateMerchantUser2(user);
			}
			
			
		}
	}

}

