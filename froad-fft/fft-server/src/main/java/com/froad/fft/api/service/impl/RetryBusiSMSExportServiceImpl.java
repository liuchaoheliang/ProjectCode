package com.froad.fft.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.froad.fft.api.service.RetryBusiSMSExportService;
import com.froad.fft.bean.Result;
import com.froad.fft.dto.SmsDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.api.PresellDeliveryMapper;
import com.froad.fft.persistent.api.ProductPresellMapper;
import com.froad.fft.persistent.api.TransSecurityTicketMapper;
import com.froad.fft.persistent.common.enums.SmsType;
import com.froad.fft.persistent.entity.PresellDelivery;
import com.froad.fft.persistent.entity.ProductPresell;
import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.persistent.entity.Trans;
import com.froad.fft.persistent.entity.TransDetails;
import com.froad.fft.persistent.entity.TransSecurityTicket;
import com.froad.fft.service.SysClientService;
import com.froad.fft.service.impl.TransServiceImpl;
import com.froad.fft.thirdparty.dto.request.sms.SmsBean;
import com.froad.fft.thirdparty.request.sms.impl.SMSMessageFuncImpl;
import com.froad.fft.util.NullValueCheckUtil;

public class RetryBusiSMSExportServiceImpl implements RetryBusiSMSExportService {

	private static Logger logger = Logger.getLogger(RetryBusiSMSExportServiceImpl.class);
	
	private static final SimpleDateFormat displayFormat = new SimpleDateFormat("MM月dd日 HH时mm分");
	
	/**最多发送次数*/
	private static final int maxSmsTimes = 5;
	
	@Resource
	private TransServiceImpl transServiceImpl;
	@Resource
	private SysClientService sysClientService;
	@Resource
	private ProductPresellMapper productPresellMapper;
	@Resource
	private TransSecurityTicketMapper transSecurityTicketMapper;
	@Resource
	private PresellDeliveryMapper presellDeliveryMapper;
	@Resource
	private SMSMessageFuncImpl sMSMessageFuncImpl;
	
	
	@Override
	public Result retryPresell(ClientAccessType clientAccessType,ClientVersion clientVersion, Long transId,String ip) {
		SysClient client = sysClientService.findSysClientByNumber(clientAccessType.getCode());
		
		logger.info(clientAccessType.getDescribe() + " 重发预售短信");

		if(transId == null){
			logger.info("重发短信申请transId为空");
			return new Result("false", "重发短信申请transId为空");
		}
		
		Trans trans = transServiceImpl.findTransById(transId);
		if(trans == null){
			logger.info("重发短信transId" + transId + " 交易不存在");
			return new Result("false", "交易不存在");
		}
		//重庆预售短信---------------------------------------------------------------------------------------CQ
		if(ClientAccessType.chongqing.equals(clientAccessType)){
			
			TransSecurityTicket transSecurityTicket = new TransSecurityTicket();
			transSecurityTicket.setTransId(trans.getId());
			List<TransSecurityTicket> transSecurityTikets = transSecurityTicketMapper.selectByCondition(transSecurityTicket);
			
			if(NullValueCheckUtil.isListArrayEmpty(transSecurityTikets)){
				return new Result("false", "验证券不存在");
			}
			transSecurityTicket = transSecurityTikets.get(0);
			if(transSecurityTicket.getIsConsume()){
				return new Result("false", "该验证券已消费");
			}
			Integer sendTimes = transSecurityTicket.getSmsNumber() == null ? 1 : transSecurityTicket.getSmsNumber();
			if(maxSmsTimes < sendTimes){
				return new Result("false", "抱歉，验证券已达重发次数上限");
			}
			Date expDate = transSecurityTicket.getExpireTime();
			Date now = new Date();
			if(now.after(expDate)){
				logger.info("验证券已过期");
				return new Result("false", "该验证券已过期");
			}
			TransDetails transDetails = trans.getDetailsList().get(0);
			
			ProductPresell presell = productPresellMapper.selectByProductId(transDetails.getProductId());
			PresellDelivery delivery=presellDeliveryMapper.selectPresellDeliveryById(trans.getDeliveryId());
			String beginTime = displayFormat.format(presell.getDeliveryStartTime());
			String endTime = displayFormat.format(presell.getDeliveryEndTime());
			String productName=transDetails.getProductName();
			String quantity=transDetails.getQuantity()+"";
			String ticket=transSecurityTicket.getSecuritiesNo();
			String[] msg = {productName,quantity,
					ticket,beginTime,endTime,delivery.getAddress()};
			SmsBean smsBean = new SmsBean(SmsType.presellDelivery, client.getId(), trans.getPhone(), msg, null,ip,false);
			SmsDto smsDto = sMSMessageFuncImpl.sendSMSMessage(smsBean);
			if(!smsDto.isFlag()){
				return new Result("false", "抱歉系统繁忙");
			}
			Long securityId = transSecurityTicket.getId();
			transSecurityTicket = new TransSecurityTicket();
			transSecurityTicket.setId(securityId);
			transSecurityTicket.setSmsNumber(sendTimes + 1);
			logger.info("更改券信息：" + transSecurityTicketMapper.updateTransSecurityTicketById(transSecurityTicket));//更新券发送次数
		}
		return new Result("true", "短信发送成功");
	}

}
