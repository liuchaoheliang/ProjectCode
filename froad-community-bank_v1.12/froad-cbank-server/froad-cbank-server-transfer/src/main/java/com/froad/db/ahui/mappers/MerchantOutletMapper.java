package com.froad.db.ahui.mappers;

import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.ibatis.annotations.Param;

import com.froad.fft.persistent.entity.MerchantOutlet;

public interface MerchantOutletMapper {

	public List<MerchantOutlet> selectByProductComment();
	
	public MerchantOutlet selectById(@Param("id")Long id);
	
	public List<MerchantOutlet> selectCommonProductOutlet(Long id);
	
	public List<MerchantOutlet> selectPresellProductOutlet(Long id);
	
}

