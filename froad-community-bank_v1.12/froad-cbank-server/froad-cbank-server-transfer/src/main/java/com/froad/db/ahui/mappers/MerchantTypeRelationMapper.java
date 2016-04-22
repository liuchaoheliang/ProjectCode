package com.froad.db.ahui.mappers;

import java.util.List;

import com.froad.fft.persistent.entity.MerchantTypeRelation;

public interface MerchantTypeRelationMapper {
	
	List<Long> distinctMerchantTypeId();
	
	
	List<Long> findMerchantByTypeId(Long typeId);
	
	
	List<MerchantTypeRelation> findMerchantByMerchantId(Long merchantId);
}
