package com.froad.process.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.common.redis.MerchantAccountRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.MerchantEx;
import com.froad.db.ahui.mappers.MerchantExMapper;
import com.froad.db.chonggou.entity.MerchantAccount;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantAccountMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;

public class MerchantAccountProcess  extends AbstractProcess{

	private MerchantExMapper merchantExMapper = null;
	public MerchantAccountProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		merchantAccountMapper = sqlSession.getMapper(MerchantAccountMapperCG.class);
		
		merchantExMapper = ahSqlSession.getMapper(MerchantExMapper.class);
		com.froad.db.ahui.mappers.MerchantAccountMapper mapper = ahSqlSession.getMapper(com.froad.db.ahui.mappers.MerchantAccountMapper.class);
		TransferMapper tmapper = sqlSession.getMapper(TransferMapper.class);
		List<com.froad.fft.persistent.entity.MerchantAccount> list = mapper.selectMerchantAccountAll();
		
		MerchantAccount mat = null;
		Transfer transfer = null;
		
		MerchantEx ex = null;
		
		for (com.froad.fft.persistent.entity.MerchantAccount obj : list) {
			mat = new MerchantAccount();
			mat.setClientId(Constans.clientId);
			
			
			transfer = tmapper.queryNewId(obj.getMerchantId().toString(), TransferTypeEnum.merchant_id);
			if(transfer != null)
				mat.setMerchantId(transfer.getNewId());
			else{
//				ConsoleLogger.info("根据旧merchantid查询新id对象为null,填入原始值");
				mat.setMerchantId(obj.getMerchantId().toString());
			}
			
			mat.setOutletId("0");
			mat.setAcctName(obj.getAcctName());
			mat.setAcctNumber(obj.getAcctNumber());
			mat.setAcctType("1");//预留 还未解决
			mat.setIsDelete(obj.getIsEnabled());
			mat.setCreateTime(obj.getCreateTime());
			
			ex = (MerchantEx)merchantExMapper.selectById(obj.getMerchantId());
			
			if(ex != null){
				if(ex.getLatticPoint() != null && !"".equals(ex.getLatticPoint())){
					mat.setOpeningBank(Constans.filterOrg(ex.getLatticPoint()));
				}else{
					mat.setOpeningBank(Constans.filterOrg(ex.getTravelAgency()));
				}
			}else{
				mat.setOpeningBank("");
			}
			
			
			
			try {
				addMerchantAccount(mat);
			} catch (Exception e) {
				LogCvt.error("添加MerchantAccountProcess失败，原因:" + e.getMessage() + " 失败 new merchantId-->"+mat.getMerchantId() + " old merchanId->"+obj.getMerchantId(), e);
			}
			
			
		}
		
	}
	
	private MerchantAccountMapperCG merchantAccountMapper = null;
	 /**
     * 增加 MerchantAccount
     * @param merchantAccount
     * @return Long    主键ID
	 * @throws Exception 
     */
	public void addMerchantAccount(MerchantAccount merchantAccount) throws Exception {

		Long result = null; 
		
		try { 
			/**********************操作MySQL数据库**********************/
			
			
			MerchantAccount mat = new MerchantAccount();
			mat.setMerchantId(merchantAccount.getMerchantId());
			mat.setOutletId(merchantAccount.getOutletId());
			mat.setIsDelete(merchantAccount.getIsDelete());
			
			List<MerchantAccount> list = merchantAccountMapper.findMerchantAccount(mat);
			
			//商户有且只有一个账号
			if(!list.isEmpty() && list.size() > 0){
				LogCvt.error("商户/门店账号已存在!");
			}
			if (merchantAccountMapper.addMerchantAccount(merchantAccount) > -1) { 
				result = merchantAccount.getId(); 
				LogCvt.info("MySQL添加商户/门店账号成功-new Id->" + result);
			}


			/**********************操作Redis缓存**********************/
			
			if(result > 0){
			/* 设置全部缓存 */
				if(StringUtils.isBlank(merchantAccount.getClientId()) 
						|| StringUtils.isBlank(merchantAccount.getMerchantId())
						|| StringUtils.isBlank(merchantAccount.getOutletId())){
					LogCvt.error("设置MerchantAccount缓存数据时clientID/merchanId/outletId不能为空!");
				}
				boolean flag  = MerchantAccountRedis.set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(merchantAccount);
				if(!flag){
					LogCvt.error("设置MerchantAccount缓存数据失败!");
				}
//				LogCvt.info("Redis添加商户/门店账号成功");
			} 
		} catch (Exception e) { 
			throw e;
		} 

	}



}

