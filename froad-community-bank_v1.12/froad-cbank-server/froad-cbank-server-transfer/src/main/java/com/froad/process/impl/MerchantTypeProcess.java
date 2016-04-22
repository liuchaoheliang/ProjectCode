package com.froad.process.impl;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.MerchantTypeCG;
import com.froad.db.chonggou.mappers.MerchantTypeMapperCG;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.RedisKeyUtil;

public class MerchantTypeProcess extends AbstractProcess {
	
	private MerchantTypeMapperCG merchantTypeMapperCG;
	
	public MerchantTypeProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		/*
		 * fft_merchant_type迁移到cb_merchant_type(mysql表)以及缓存, 
		 * 记录新旧商户类型ID关系 
		 */
		LogCvt.info("商户类型表cb_merchant_type 数据迁移开始");
		
//		merchantTypeMapperCG = sqlSession.getMapper(MerchantTypeMapperCG.class);
//		List<MerchantTypeCG> MerchantTypeList = merchantTypeMapperCG.findMerchantAll();
//		for(MerchantTypeCG cg : MerchantTypeList){
//			String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id();
//			Long result = redis.hset(key, ObjectUtils.toString(cg.getId()), ObjectUtils.toString(cg.getTypeName()));
//			LogCvt.info("商户类型缓存key==>"+key+"设置"+(result != -1?"成功":"失败"));
//		}
	}
	
	
}
