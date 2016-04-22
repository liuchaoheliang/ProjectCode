package com.froad.cbank.coremodule.module.normal.merchant.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.RandomUtils;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsMessageResponseVo;
import com.froad.thrift.vo.SmsMessageVo;
/**
 * 短信
 * @ClassName SmsService
 * @author zxl
 * @date 2015年4月8日 上午11:44:40
 */
@Service
public class Sms_Service {
	
	@Resource
	SmsMessageService.Iface smsMessageService;
	
	@Resource
	MerchantUserService.Iface merchantUserService;
	
	/**
	 * 短信发送
	 * @tilte smsSend
	 * @author zxl
	 * @date 2015年3月23日 上午11:04:29
	 * @param map
	 * @return
	 * @throws TException 
	 */
	public HashMap<String,Object> smsSend(String mobile,int type,String clientId,String userName) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		MerchantUserVo vo=new MerchantUserVo();
		vo.setUsername(userName);
		List<MerchantUserVo> al = merchantUserService.getMerchantUser(vo);
		if(al.size()>0){
			MerchantUserVo resp = al.get(0);
			if(!resp.getPhone().equals(mobile)){
				throw new MerchantException(EnumTypes.fail.getCode(), "手机号不匹配");
			}
			SmsMessageVo svo = new SmsMessageVo();
			svo.setClientId(clientId);
			svo.setMobile(mobile);
			svo.setSmsType(1001);
			svo.setCode(RandomUtils.getRandomValue(RandomUtils.KEY1, 6));
			SmsMessageResponseVo sresp = smsMessageService.sendSMS(svo);
			if(EnumTypes.success.getCode().equals(sresp.getResultCode())){
				reMap.put("codeId", sresp.getToken());
				reMap.put("userId", resp.getId());
			}else{
				throw new MerchantException(sresp.getResultCode(), sresp.getResultDesc());
			}
			return reMap;
		}else{
			throw new MerchantException(EnumTypes.fail.getCode(), "用户不存在");
		}
		
	}
	
	/**
	 * 短信校验
	 * @tilte smsValid
	 * @author zxl
	 * @date 2015年3月23日 上午11:04:51
	 * @param map
	 * @return
	 * @throws TException 
	 */
	public HashMap<String,Object> smsValid(String codeId,String code) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		ResultVo resp = smsMessageService.verifyMobileToken(codeId, code);
		if(EnumTypes.success.getCode().equals(resp.getResultCode())){
		}else{
			throw new MerchantException(resp.getResultCode(), resp.getResultDesc());
		}
		return reMap;	
	}

}
