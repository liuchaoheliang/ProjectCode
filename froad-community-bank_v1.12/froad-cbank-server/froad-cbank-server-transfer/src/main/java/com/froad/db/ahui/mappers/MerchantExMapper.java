package com.froad.db.ahui.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.ahui.entity.MerchantEx;
import com.froad.fft.persistent.api.MerchantMapper;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.OutleBank;

public interface MerchantExMapper extends MerchantMapper {
	
	public Merchant selectById(@Param("id")Long id);
	
	public OutleBank selectOrgCodeOfPresell(Long id);
	
	
	public int findMerchantById(Long id);
	
	
	public List<MerchantEx> findAllMerchantLicense();
}

