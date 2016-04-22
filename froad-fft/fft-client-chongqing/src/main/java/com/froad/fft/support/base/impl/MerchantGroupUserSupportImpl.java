
	 /**
  * 文件名：MerchantGroupUserSupportImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月6日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.MerchantGroupUserExportService;
import com.froad.fft.dto.MerchantGroupUserDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.MerchantGroupUserSupport;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月6日 下午1:54:52 
 */
@Service
public class MerchantGroupUserSupportImpl implements MerchantGroupUserSupport {
	
	private static Logger logger = Logger.getLogger(MerchantGroupUserSupportImpl.class);
	
	@Resource(name="merchantGroupUserService")
	private MerchantGroupUserExportService merchantGroupUserExportService;
	
	private final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	@Override
	public MerchantGroupUserDto getBySysUserId(Long sysUserId) {
		if(sysUserId==null){
			logger.info("传入Id为空");
			return null;
		}
		
		return merchantGroupUserExportService.findMerchantGroupUserByUserId(clientAccessType, ClientVersion.version_1_0, sysUserId);
	}

	
	@Override
	public List<MerchantGroupUserDto> getByConditions(
			MerchantGroupUserDto merchantGroupUserDto) {
		return merchantGroupUserExportService.selectByConditions(clientAccessType, ClientVersion.version_1_0, merchantGroupUserDto);
	}

	

}
