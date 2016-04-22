package com.froad.process.impl;

import java.util.Date;
import java.util.List;


import com.froad.common.redis.MerchantUserRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.MerchantGroupUserEx;
import com.froad.db.ahui.mappers.MerchantUserMapper;
import com.froad.db.chonggou.entity.MerchantUser;
import com.froad.db.chonggou.entity.OutletCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantMapperCG;
import com.froad.db.chonggou.mappers.OutletMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.po.Merchant;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;

public class MerchantUserProcess  extends AbstractProcess{

	private MerchantUserMapper merchantUserMapper = null;
	public MerchantUserProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		
		merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);
		
		com.froad.db.ahui.mappers.MerchantGroupUserMapper mapper1 = ahSqlSession.getMapper(com.froad.db.ahui.mappers.MerchantGroupUserMapper.class);
		List<MerchantGroupUserEx> list = mapper1.findAllMerchantGroupUserAll();
		
		MerchantMapperCG mapper2 = sqlSession.getMapper(MerchantMapperCG.class);
		OutletMapperCG mapper3 = sqlSession.getMapper(OutletMapperCG.class);
		
		
		
		
		TransferMapper mapper = sqlSession.getMapper(TransferMapper.class);
//		
		MerchantUser user = null;
		Transfer transfer = null;
		Transfer transfer2 = null;
		OutletCG outlet = null;
		Merchant ex = null;
		Transfer tf = null;
		for (MerchantGroupUserEx obj : list) {
			user = new MerchantUser();
			user.setClientId(Constans.clientId);
			user.setCreateTime(obj.getCreateTime());
			user.setEmail(obj.getEmail());
			if(obj.isEnabled())
				user.setIsDelete(false);
			else
				user.setIsDelete(true);
			user.setIsRest(true);
			
			transfer = mapper.queryNewId(obj.getMerchantId().toString(), TransferTypeEnum.merchant_id);
			user.setMerchantId(transfer.getNewId());
			
			if(ex != null){
				ex = mapper2.findMerchantByMerchantId(user.getMerchantId().toString());
				user.setMerchantName(ex.getMerchantName());
			}else{
				user.setMerchantName("");
			}
			
			if(obj.getMerchantOutletId() != null){
				transfer2 = mapper.queryNewId(obj.getMerchantOutletId().toString(), TransferTypeEnum.outlet_id);
				
				if(transfer2 != null)
					user.setOutletId(transfer2.getNewId());
				else
					user.setOutletId(obj.getMerchantOutletId().toString());
				
				outlet = mapper3.findOutletByOutletId(user.getOutletId());
				if(outlet != null)
					user.setOutletName(outlet.getOutletName());
			}else{
//				ConsoleLogger.info("商户用户因原始数据MerchantOutletId为空，所以无法获取outlet对象 ，故outletId置为0");
				user.setOutletId("0");
				user.setOutletName("");
			}
			
			user.setPassword(obj.getPassWord());
			
			user.setOrgCode("");//无法获取
			
			
//			transfer = mapper.queryNewId(String.valueOf(obj.getRoleId()), TransferTypeEnum.merchant_role_id);
			
			user.setMerchantRoleId(obj.getRoleId());
			
			user.setPhone(obj.getPhone());
			user.setUsername(obj.getUserName());
			user.setRealname(obj.getUserName());
			
			try {
				addMerchantUser(user);
			} catch (Exception e) {
				LogCvt.error("添加user失败，原因:" + e.getMessage() + " 失败 new outletId-->"+user.getOutletId() + " old outletId->"+obj.getMerchantOutletId(), e);
			}
			
			
			tf = new Transfer();
			tf.setNewId(user.getId().toString());
			tf.setOldId(obj.getId().toString());
			tf.setType(TransferTypeEnum.merchant_user_id);
			mapper.insert(tf);
			
		}
//		LogCvt.info("商户用户数据专一完成");
	}
	
    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return Long    主键ID
     * @throws Exception 
     */
	public void addMerchantUser(MerchantUser merchantUser) throws Exception {

		
		try { 
//			String addResult = checkAdd(merchantUser);
//			if( addResult != null ){
//				LogCvt.error("新增商户用户 参数有误!");
//			}
			/**********************操作MySQL数据库**********************/
			
			if( merchantUser.getCreateTime() == null ){
				merchantUser.setCreateTime(new Date());
			}
			if (merchantUserMapper.addMerchantUser(merchantUser) > -1) { 
				
				/**********************操作Redis缓存**********************/
				MerchantUserRedis.set_cbbank_merchant_outlet_user_client_id_merchant_id_user_id(merchantUser);
				ConsoleLogger.info("商户用户redis操作完成");
			}

		} catch (Exception e) { 
//			e.printStackTrace();
			LogCvt.error("添加MerchantUser失败，原因:" + e.getMessage(), e); 
			throw e;
		}

	}
	
	
	private static final String CLIENT_ID_NOT_NULL = "添加或更新MerchantUser失败,客户端编号clientId不能为空";
	private static final String MERCHANT_ID_NOT_NULL = "添加或更新MerchantUser失败,商户编号merchantId不能为空";
	private static final String USERNAME_NOT_NULL = "添加或更新MerchantUser失败,用户名称username不能为空";
	private static final String USERNAME_NOT_PASS = "添加或更新MerchantUser失败,用户名称username不符合要求";
	private static final String USERNAME_NOT_ONLY = "添加或更新MerchantUser失败,用户名称username不是唯一值";
	// 检查 - 新增前
	private String checkAdd(MerchantUser merchantUser){
		if( merchantUser.getClientId() == null || merchantUser.getClientId().length() <= 0 ){
			LogCvt.info(CLIENT_ID_NOT_NULL);
			return CLIENT_ID_NOT_NULL;
		}
		if( merchantUser.getMerchantId() == null || merchantUser.getMerchantId().length() <= 0 ){
			LogCvt.info(MERCHANT_ID_NOT_NULL);
			return MERCHANT_ID_NOT_NULL;
		}
		String username = merchantUser.getUsername();
		if( username == null || username.length() <= 0 ){
			LogCvt.info(USERNAME_NOT_NULL);
			return USERNAME_NOT_NULL;
		}
		if( !Checker.isUsername(username) ){
			LogCvt.info(USERNAME_NOT_PASS);
			return USERNAME_NOT_PASS;
		}
		try{
			if( null != merchantUserMapper.findMerchantUserByUsername(username) ){
				LogCvt.info(USERNAME_NOT_ONLY);
				return USERNAME_NOT_ONLY;
			}
		}catch(Exception e){
			LogCvt.error("MerchantUser新增 检查商户用户名是否唯一失败，原因:" + e.getMessage(), e); 
		}
		
		return null;
	}

}

