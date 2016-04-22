package com.froad.db.ahui.mappers;

import java.util.List;

import com.froad.fft.persistent.entity.SysMobileClient;

public interface SysMobileClientMapper {
	
	List<SysMobileClient> selectByCondition(SysMobileClient sysMobileClient);
}
