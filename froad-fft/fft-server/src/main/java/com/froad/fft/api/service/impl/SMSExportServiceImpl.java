package com.froad.fft.api.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.SMSExportService;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SmsDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.common.enums.SmsType;
import com.froad.fft.service.SysClientService;
import com.froad.fft.thirdparty.dto.request.sms.SmsBean;
import com.froad.fft.thirdparty.request.sms.impl.SMSMessageFuncImpl;
import com.froad.fft.util.NullValueCheckUtil;

@Service("smsExportServiceImpl")
public class SMSExportServiceImpl implements SMSExportService {

	private static Logger logger = Logger.getLogger(SMSExportServiceImpl.class);
	
	@Resource(name = "smsMessageFuncImpl")
	private SMSMessageFuncImpl smsMessageFuncImpl;
	
	
	@Resource
	private SysClientService sysClientService;
	
	@Override
	public SmsDto sendSms(ClientAccessType clientAccessType, ClientVersion clientVersion,SmsDto smsDto)throws FroadException {
		Long clientId = sysClientService.findSysClientByNumber(clientAccessType.getCode()).getId();
		if(NullValueCheckUtil.isStrEmpty(smsDto.getToPhoneNumber())){
			logger.info("手机号码为空");
			return new SmsDto(false, "请输入手机号码");
		}
		SmsBean smsBean = new SmsBean(SmsType.valueOf(smsDto.getSmsType().name()), clientId, smsDto.getToPhoneNumber(), smsDto.getArgs(), smsDto.getSendUser(),smsDto.getSendIP(),smsDto.isCheck());
		return smsMessageFuncImpl.sendSMSMessage(smsBean);
	}

}
