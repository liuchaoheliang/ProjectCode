package com.froad.fft.support.base.impl;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.UserEngineExportService;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.UserEngineSupport;

@Service
public class UserEngineSupportImpl implements UserEngineSupport{

	private static Logger logger = Logger.getLogger(UserEngineSupportImpl.class);
	
	final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	@Resource(name = "userEngineService")
	private UserEngineExportService userEngineService;
	
	@Override
	public UserEngineDto registerUser(UserEngineDto userEngineDto) {
		return userEngineService.registerUser(clientAccessType,ClientVersion.version_1_0,userEngineDto);
	}
	
	@Override
	public UserEngineDto login(UserEngineDto userEngineDto,String loginIP){
		return userEngineService.login(clientAccessType, ClientVersion.version_1_0, userEngineDto.getLoginID(), userEngineDto.getLoginPwd(), loginIP);
	}

	@Override
	public UserEngineDto updatePwd(Long memberCode, String oldPwd, String newPwd) {
		return userEngineService.updatePwd(clientAccessType, ClientVersion.version_1_0, memberCode, oldPwd, newPwd);
	}

	@Override
	public UserEngineDto updateMemberInfo(UserEngineDto userEngineDto) {
		return userEngineService.updateMemberInfo(clientAccessType, ClientVersion.version_1_0, userEngineDto);
	}
	
	@Override
	public UserEngineDto queryByLoginId(String LoginID) {
		return userEngineService.queryByLoginID(clientAccessType, ClientVersion.version_1_0, LoginID);
	}

	@Override
	public UserEngineDto updateUserPoints(String loginID) {
		return userEngineService.updateUserPoints(clientAccessType, ClientVersion.version_1_0, loginID);
	}

	@Override
	public UserEngineDto resetUserPwd(Long memCode, String password) {
		return userEngineService.resetUserPwd(clientAccessType,ClientVersion.version_1_0, memCode,password);
	}

	@Override
	public UserEngineDto updateUserMobile(Long memCode, String mobile) {
		return userEngineService.updateUserMobile(clientAccessType, ClientVersion.version_1_0, memCode, mobile);
	}
}
