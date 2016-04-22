package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.SysClientMapper;
import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.service.SysClientService;

@Service("sysClientServiceImpl")
public class SysClientServiceImpl implements SysClientService {

	private static Logger logger = Logger.getLogger(SysClientServiceImpl.class);

	@Resource
	private SysClientMapper sysClientMapper;
	
	@Override
	@Cacheable("sysClient")
	public List<SysClient> findAllSysClient() {
		return sysClientMapper.selectAllSysClient();
	}

	@Override
	@Cacheable("sysClient")
	public SysClient findSysClientById(Long id) {
		if(id==null){
			logger.info("传入参数为空");
			return null;
		}
		return sysClientMapper.selectSysClientById(id);
	}

	

	@Override
	@Cacheable("sysClient")
	public SysClient findSysClientByNumber(String number) {
		if(number==null){
			logger.info("传入参数为空");
			return null;
		}
		return sysClientMapper.selectByNumber(number);
	}

}
