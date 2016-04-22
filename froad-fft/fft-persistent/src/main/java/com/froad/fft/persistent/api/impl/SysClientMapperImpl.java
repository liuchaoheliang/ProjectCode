package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.SysClientMapper;
import com.froad.fft.persistent.entity.SysClient;

public class SysClientMapperImpl implements SysClientMapper {

	@Resource
	private SysClientMapper sysClientMapper;
	
	@Override
	public List<SysClient> selectAllSysClient() {
		return sysClientMapper.selectAllSysClient();
	}

	@Override
	public SysClient selectSysClientById(Long id) {
		return sysClientMapper.selectSysClientById(id);
	}

	
	@Override
	public SysClient selectByNumber(String number) {
		return sysClientMapper.selectByNumber(number);
	}

}
