package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.cbank.persistent.entity.Merchant;
import com.froad.cbank.persistent.entity.MerchantUser;
import com.froad.cbank.persistent.entity.MerchantUserRole;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.MerchantUserCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantUserMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.MerchantMapper;
import com.froad.db.chongqing.mappers.MerchantUserMapper;
import com.froad.db.chongqing.mappers.MerchantUserRoleMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.RedisKeyUtil;

public class MerchantUserProcess extends AbstractProcess {

	private MerchantUserMapper merUserMapper = null;
	private MerchantUserMapperCG meUserMapperCG = null;
	private TransferMapper transferMapper = null;
	private MerchantUserRoleMapper mUserRoleMapper = null;
	
	private MerchantMapper merchantMapper = null;
	
	//门店
	private final Map<String, String> outletMap = new HashMap<String, String>();
	
	//商户
	private final Map<String, String> merchantMap = new HashMap<String, String>();
	
	//角色中间表
	private final Map<String, String> userRoleMap = new HashMap<String, String>();

	public MerchantUserProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void before() {
		merUserMapper = cqSqlSession.getMapper(MerchantUserMapper.class);
		meUserMapperCG = sqlSession.getMapper(MerchantUserMapperCG.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		merchantMapper = cqSqlSession.getMapper(MerchantMapper.class);
		mUserRoleMapper = cqSqlSession.getMapper(MerchantUserRoleMapper.class);
		
		
		List<MerchantUserRole> listUserRole = mUserRoleMapper.findMerchantUserRoleAll();
		for (MerchantUserRole merchantUserRole : listUserRole) {
			userRoleMap.put(merchantUserRole.getMerchantUserId()+"", merchantUserRole.getMerchantRoleId()+"");
		}
		
		
		List<Transfer> listOutlet = transferMapper.queryGroupList(TransferTypeEnum.outlet_id);
		for (Transfer t : listOutlet) {
			outletMap.put(t.getOldId(), t.getNewId());
		}
		
		List<Transfer> listMerchant = transferMapper.queryGroupList(TransferTypeEnum.merchant_id);
		for (Transfer t : listMerchant) {
			merchantMap.put(t.getOldId(), t.getNewId());
		}
		
		boolean b = meUserMapperCG.deleteMerchantUser();
		if(b){
			LogCvt.info("重庆迁移数据商户用户Mysql删除完成!");
		}
	}

	@Override
	public void process() {
		
		List<MerchantUser> listUser = merUserMapper.selectByCondition(new MerchantUser());
		
		MerchantUserCG user = null;
		Transfer transfer = null;
		String roleId = null;
		Merchant m = null;
		for (MerchantUser obj : listUser) {
			user = new MerchantUserCG();
			user.setCreateTime(obj.getCreateTime());
			user.setClientId(Constans.clientId);
			user.setMerchantId(merchantMap.get(String.valueOf(obj.getMerchant().getId())) == null ? 
					obj.getMerchant().getId()+"" : merchantMap.get(String.valueOf(obj.getMerchant().getId())));
			
			if(obj.getMerchantOutletId() == null){
				user.setOutletId("0");
			}else{
				user.setOutletId(outletMap.get(obj.getMerchantOutletId().toString()));
			}
			
			roleId = userRoleMap.get(String.valueOf(obj.getId()));
			
//			if("100000000".equals(roleId)){
//				user.setMerchantRoleId(100000003l);
//			}else if("100000001".equals(roleId)){
//				user.setMerchantRoleId(100000004l);
//			}else if("100000002".equals(roleId)){
//				user.setMerchantRoleId(100000005l);
//			}else{
//				user.setMerchantRoleId(0l);
//			}
			user.setMerchantRoleId(Long.valueOf(roleId));
			
			user.setUsername(obj.getUsername());
			user.setRealname(obj.getAlias());
			user.setPassword(obj.getPassword());
			user.setEmail(obj.getEmail());
			user.setPhone(obj.getPhone());
			if( obj.getIsFirstLogin() == null || obj.getIsFirstLogin() ){
				user.setIsRest(false);
			}else{
				user.setIsRest(true);
			}
//			user.setIsRest(obj.getIsFirstLogin() == null ? false : obj.getIsFirstLogin());
			if(obj.getIsEnabled()){
				user.setIsDelete(false);
			}else{
				user.setIsDelete(true);
			}
//			m = merchantMapper.selectById(obj.getMerchant().getId());
//			if(m != null && !m.getIsDelete() 
//					&& "100000000".equals(roleId)
//					&& !obj.getIsEnabled()){
//				user.setIsDelete(false);
//			}
//			user.setIsDelete(obj.getIsDelete());
			
			addMerchantUser(user);
			
			transfer = new Transfer();
			transfer.setNewId(user.getId().toString());
			transfer.setOldId(obj.getId().toString());
			transfer.setType(TransferTypeEnum.merchant_user_id);
			transferMapper.insert(transfer);
			
		}

	}

	public void addMerchantUser(MerchantUserCG merchantUser){

		if (meUserMapperCG.addMerchantUser(merchantUser) > -1) {
			boolean b = set_cbbank_merchant_outlet_user_client_id_merchant_id_user_id(merchantUser);
			if(!b){
				LogCvt.error("重庆迁移数据商户用户存储redis失败!");
			}
		}
	}

	public boolean set_cbbank_merchant_outlet_user_client_id_merchant_id_user_id(
			MerchantUserCG merchantUser) {
		String key = RedisKeyUtil
				.cbbank_merchant_outlet_user_client_id_merchant_id_user_id(
						merchantUser.getClientId(),
						merchantUser.getMerchantId(), merchantUser.getId());
		
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("username",ObjectUtils.toString(merchantUser.getUsername(), ""));
		valueMap.put("password",ObjectUtils.toString(merchantUser.getPassword(), ""));
		valueMap.put("outlet_id",ObjectUtils.toString(merchantUser.getOutletId(), ""));
		valueMap.put("merchant_role_id",ObjectUtils.toString(merchantUser.getMerchantRoleId(), ""));
		String result = redis.putMap(key, valueMap);
		return "OK".equals(result);
	}

}
