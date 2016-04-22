package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.cbank.persistent.entity.Merchant;
import com.froad.cbank.persistent.entity.MerchantAccount;
import com.froad.common.redis.MerchantAccountRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.MerchantAccountCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantAccountMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.MerchantAccountMapper;
import com.froad.db.chongqing.mappers.MerchantMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;

public class MerchantAccountProcess extends AbstractProcess {

	private MerchantAccountMapperCG merMapperCG = null;

	private MerchantAccountMapper mAccountMapper = null;
	
	private MerchantAccountRedis mRedis = null;
	
	private TransferMapper transferMapper = null;
	
	private MerchantMapper merchantMapper = null;
	
	private final Map<String,String> merchantMap = new HashMap<String,String>();

	public MerchantAccountProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void before() {
		merMapperCG = sqlSession.getMapper(MerchantAccountMapperCG.class);
		mAccountMapper = cqSqlSession.getMapper(MerchantAccountMapper.class);
		mRedis = new MerchantAccountRedis(redis);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		merchantMapper = cqSqlSession.getMapper(MerchantMapper.class);
		
		List<Transfer> list = transferMapper.queryGroupList(TransferTypeEnum.merchant_id);
		for (Transfer t : list) {
			merchantMap.put(t.getOldId(), t.getNewId());
		}

		boolean b = merMapperCG.removeMerchantAccount(Constans.clientId);
		if (b) {
			LogCvt.info("重庆迁移数据商户账号Mysql删除完成!");
		}
	}

	@Override
	public void process() {
		transferMerchantAccount();
	}
	
	
	public void transferMerchantAccount(){
		
		List<MerchantAccount> listAccount = mAccountMapper.selectByCondition(new MerchantAccount());
		MerchantAccountCG mAccountCG = null;
		Merchant merchant = null;
		
		for (MerchantAccount obj : listAccount) {
			mAccountCG = new MerchantAccountCG();
			mAccountCG.setCreateTime(obj.getCreateTime());
			mAccountCG.setClientId(Constans.clientId);
			mAccountCG.setMerchantId(merchantMap.get(obj.getMerchantId()+""));
			mAccountCG.setOutletId("0");
			mAccountCG.setAcctName(obj.getAcctName());
			mAccountCG.setAcctNumber(obj.getAcctNumber());
			mAccountCG.setAcctType(obj.getAcctType().getCode());
			if(obj.getIsEnabled()){
				mAccountCG.setIsDelete(false);
			}else{
				mAccountCG.setIsDelete(true);
			}
			merchant = merchantMapper.selectById(obj.getMerchantId());
			if(merchant != null){
				if(StringUtils.isNotBlank(merchant.getLatticePoint())){
					mAccountCG.setOpeningBank(Constans.filterOrg(merchant.getLatticePoint()));
				}else{
					if(StringUtils.isNotBlank(merchant.getTravelAgency())){
						mAccountCG.setOpeningBank(Constans.filterOrg(merchant.getTravelAgency()));
					}else{
						mAccountCG.setOpeningBank("000000");
					}
				}
			}else{
				mAccountCG.setOpeningBank("000000");
			}
			
			addMerchantAccount(mAccountCG);
		}
	}

	public void addMerchantAccount(MerchantAccountCG merchantAccount) {

		MerchantAccountCG mat = new MerchantAccountCG();
		mat.setMerchantId(merchantAccount.getMerchantId());
		mat.setOutletId(merchantAccount.getOutletId());
		mat.setIsDelete(merchantAccount.getIsDelete());

		List<MerchantAccountCG> list = merMapperCG.findMerchantAccount(mat);

		// 商户有且只有一个账号
		if (!list.isEmpty() && list.size() > 0) {
			LogCvt.error("商户/门店账号已存在!");
		}
		if (merMapperCG.addMerchantAccount(merchantAccount) > -1) {

			/* 设置全部缓存 */
			if (StringUtils.isBlank(merchantAccount.getClientId())
					|| StringUtils.isBlank(merchantAccount.getMerchantId())
					|| StringUtils.isBlank(merchantAccount.getOutletId())) {
				LogCvt.error("设置MerchantAccount缓存数据时clientID/merchanId/outletId不能为空!");
			}
			boolean flag = mRedis.set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(merchantAccount);
			if (!flag) {
				LogCvt.error("设置MerchantAccount缓存数据失败!");
			}
		}
	}

}
