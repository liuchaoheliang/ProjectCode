package com.froad.db.ahui.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.ahui.entity.MerchantGroupUserEx;


public interface MerchantGroupUserMapper extends com.froad.fft.persistent.api.MerchantGroupUserMapper{

	public List<MerchantGroupUserEx> findAllMerchantGroupUserAll();
	
}

