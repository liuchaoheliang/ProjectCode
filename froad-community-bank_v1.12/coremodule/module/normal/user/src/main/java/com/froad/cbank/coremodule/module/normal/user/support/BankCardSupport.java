package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.enums.ResultCode;
import com.froad.cbank.coremodule.module.normal.user.pojo.BankCardInfoPojo;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.bankcard.BankCardInfo;
import com.froad.thrift.vo.bankcard.BankCardResponse;

@Service
public class BankCardSupport extends BaseSupport {

	@Resource
	private BankCardService.Iface bankCardService;
	
	
	
	/**
	 * 银行卡列表
	 *@description 
	 *@author Liebert
	 *@date 2015年4月27日 上午10:10:42
	 */
	public Map<String,Object> getSignCardList(String clientId, long memberCode){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		BankCardResponse res = new BankCardResponse();
		try {
			res = bankCardService.selectSignedBankCardByClientId(clientId, memberCode);
		} catch (TException e) {
			LogCvt.error("查询签约银行卡列表异常",e);
			res = new BankCardResponse();
			res.setResultCode("9999");
			res.setResultDesc("查询银行卡列表失败");
		}
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultCode())){
			List<BankCardInfoPojo> bankCardList = new ArrayList<BankCardInfoPojo>();
			if(res.isSetCardList() && res.getCardListSize() > 0){
				Iterator<BankCardInfo> bankIter = res.getCardListIterator();
				while(bankIter.hasNext()){
					BankCardInfo bankCardInfo = bankIter.next();
					BankCardInfoPojo bankCardPojo = new BankCardInfoPojo();
					
					BeanUtils.copyProperties(bankCardPojo, bankCardInfo);
					
					//加密敏感信息
					String cardNo = bankCardPojo.getCardNo().substring(0,6) + "******" +  bankCardPojo.getCardNo().substring(bankCardPojo.getCardNo().length()-4);
					String identifyNo = bankCardPojo.getIdentifyNo().substring(0,1) + "****************" + bankCardPojo.getIdentifyNo().substring(bankCardPojo.getIdentifyNo().length()-1);
					String cardHostName = bankCardPojo.getCardHostName().substring(0,1) + "**";
					
					bankCardPojo.setCardNo(cardNo);
					bankCardPojo.setIdentifyNo(identifyNo);
					bankCardPojo.setCardHostName(cardHostName);
					
					bankCardList.add(bankCardPojo);
				}
			}
			resMap.put("bankCardList", bankCardList);
			
		}else{
			resMap.put(Results.code, res.getResultCode());
			resMap.put(Results.msg, res.getResultDesc());
		}
		return resMap;
	}
	
	
	
	/**
	 * 银行卡签约
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 上午11:13:18
	 */
	public Map<String,Object> signBankCard(String clientId, long memberCode, String cardNo, String uname, String idcard, String phone, String singlePenLimit, String dayLimit, String monthLimit, String mobileToken, String pointCardNo){
		Map<String,Object> resMap = new HashMap<String,Object>();
		ResultVo result = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
		try {
			result = bankCardService.signBankCardByClientId(clientId, memberCode, cardNo, uname, idcard, phone, singlePenLimit, dayLimit, monthLimit, mobileToken,pointCardNo);
		} catch (TException e) {
			LogCvt.error("银行卡绑定接口调用异常",e);
			resMap.put(Results.code,"9999");
			resMap.put(Results.msg,"银行卡绑定失败");
			return resMap;
		}
		if(Constants.RESULT_CODE_SUCCESS.equals(result.getResultCode())){
			LogCvt.info("银行卡绑定成功");
			resMap.put(Results.code,"0000");
			resMap.put(Results.msg,"银行卡绑定成功");
			
		}else{
			LogCvt.info("银行卡绑定失败：" + result.getResultDesc());
			resMap.put(Results.code,result.getResultCode());
			resMap.put(Results.msg,result.getResultDesc());
		}
		return resMap;
	}
	
	
	/**
	 * 银行卡解约
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 上午11:13:31
	 */
	public Map<String,Object> cancelSignBankCard(String clientId, long memberCode){
		Map<String,Object> resMap = new HashMap<String,Object>();
		ResultVo result = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
		try {
			BankCardResponse res = bankCardService.selectSignedBankCardByClientId(clientId, memberCode);
			result = bankCardService.cancelSignedBankCard(clientId, memberCode, res.getCardList().get(0).getCardNo());
		} catch (TException e) {
			LogCvt.error("银行卡解绑接口调用异常",e);
			resMap.put(Results.code,"9999");
			resMap.put(Results.msg,"银行卡解绑失败");
			return resMap;
		}
		
		if(Constants.RESULT_CODE_SUCCESS.equals(result.getResultCode())){
			LogCvt.info("银行卡解绑成功");
			resMap.put(Results.code,"0000");
			resMap.put(Results.msg,"银行卡解绑成功");
		}else{
			LogCvt.info("银行卡解绑失败："+result.getResultDesc());
			resMap.put(Results.code,result.getResultCode());
			resMap.put(Results.msg,result.getResultDesc());
		}
		
		return resMap;
	}
	
	
	
	/**
	 * 积分卡修改签约
	 *@description 
	 *@author Liebert
	 *@date 2015年10月9日 下午18:13:18
	 */
	public Map<String,Object> editPointCard(String clientId, Long memberCode, String cardNo, String uname, String idcard, String phone, String pointCardNo){
		LogCvt.info(String.format("积分卡修改>> memberCode:%s, cardNo:%s", memberCode, cardNo));
		Map<String,Object> resMap = new HashMap<String,Object>();

		if(StringUtil.isBlank(cardNo) && StringUtil.isBlank(uname) && StringUtil.isBlank(idcard) && StringUtil.isBlank(phone) && StringUtil.isBlank(pointCardNo)){
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "请编辑需要修改的信息");
			return resMap;
		}
		
		try {

			BankCardResponse res = bankCardService.selectSignedBankCardByClientId(clientId, memberCode);
			if(res.getCardList() == null && res.getCardList().size() == 0){
				resMap.put(Results.code,"9999");
				resMap.put(Results.msg,"查询原积分卡失败");
				return resMap;
			}
			
			//原签约信息
			BankCardInfo oldBankCardInfo = res.getCardList().get(0);
			
			//将原签约的解约
			ResultVo cancelResult = bankCardService.cancelSignedBankCard(clientId, memberCode, oldBankCardInfo.getCardNo());
			if(!Constants.RESULT_CODE_SUCCESS.equals(cancelResult.getResultCode())){
				resMap.put(Results.code,"9999");
				resMap.put(Results.msg,cancelResult.getResultDesc());
				return resMap;
			}	
				
			//更新签约信息
			String u_cardNo = StringUtil.isNotBlank(cardNo) ? cardNo : oldBankCardInfo.getCardNo();
			String u_uname = StringUtil.isNotBlank(uname) ? uname : oldBankCardInfo.getCardHostName();
			String u_idcard = StringUtil.isNotBlank(idcard) ? idcard : oldBankCardInfo.getIdentifyNo();
			String u_phone = StringUtil.isNotBlank(phone) ? phone : oldBankCardInfo.getMobile();
			String u_pointCardNo = StringUtil.isNotBlank(pointCardNo) ? pointCardNo : oldBankCardInfo.getPointCardNo();
			ResultVo updateResult = bankCardService.signBankCardByClientId(clientId, memberCode, u_cardNo, u_uname, u_idcard, u_phone, null, null, null, null,u_pointCardNo);
			
			if(Constants.RESULT_CODE_SUCCESS.equals(updateResult.getResultCode())){
				LogCvt.info("积分卡绑定成功");
				resMap.put(Results.code,"0000");
				resMap.put(Results.msg,"积分卡绑定成功");
				return resMap;
				
			}else{
				LogCvt.info("积分卡绑定失败：" + updateResult.getResultDesc());
				
				//修改失败，重新签回
				ResultVo signBackResult = bankCardService.signBankCardByClientId(clientId, memberCode, oldBankCardInfo.getCardNo(), oldBankCardInfo.getCardHostName(), oldBankCardInfo.getIdentifyNo(), oldBankCardInfo.getMobile(), null, null, null, null,oldBankCardInfo.getPointCardNo());
				
				if(Constants.RESULT_CODE_SUCCESS.equals(signBackResult.getResultCode())){
					resMap.put(Results.code,"9999");
					resMap.put(Results.msg,"修改签约积分卡失败:"+updateResult.getResultDesc());
					return resMap;
				}else{
					resMap.put(Results.code,"9999");
					resMap.put(Results.msg,"修改签约积分卡失败,原积分卡需重新签约");
					return resMap;
				}
				
			}
			
		} catch (TException e) {
			LogCvt.error("积分卡修改异常",e);
			resMap.put(Results.code,"9999");
			resMap.put(Results.msg,"积分卡绑定异常");
			return resMap;
		}
		
	}
	
	
	/**
	 * 设置默认银行卡
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 上午11:13:44
	 */
	public Map<String,Object> setDefaultBankCard(long memberCode, long cardId){
		Map<String,Object> resMap = new HashMap<String,Object>();
		ResultVo result = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
		try {
			result = bankCardService.setDefaultSignerBankCard(memberCode, cardId);
		} catch (TException e) {
			LogCvt.error("银行卡设置默认卡接口调用异常",e);
			resMap.put(Results.code,"9999");
			resMap.put(Results.msg,"银行卡设置默认卡接口调用异常");
			return resMap;
		}
		if(Constants.RESULT_CODE_SUCCESS.equals(result.getResultCode())){
			resMap.put(Results.code,"0000");
			resMap.put(Results.msg,"设置成功");
			
		}else{
			resMap.put(Results.code,result.getResultCode());
			resMap.put(Results.msg,result.getResultDesc());
		}
		return resMap;
	}
	
	/*
	
	*//**
	 * 修改银行卡限额
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 上午11:17:26
	 *//*
	public Map<String,Object> updateBankCardLimitCash(String clientId, String cardNo, String singlePenLimit, String dailyLimit, String monthlyLimit){
		Map<String,Object> resMap = new HashMap<String,Object>();
		ResultVo result = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
		try {
			result = bankCardService.updateSignBankCardLimitCash(clientId, cardNo, singlePenLimit, dailyLimit, monthlyLimit);
		} catch (TException e) {
			LogCvt.error("银行卡修改限额接口调用异常",e);
		}
		if(Constants.RESULT_CODE_SUCCESS.equals(result.getResultCode())){
			resMap.put(Results.code,"0000");
			resMap.put(Results.msg,"设置成功");
			
		}else{
			resMap.put(Results.code,result.getResultCode());
			resMap.put(Results.msg,result.getResultDesc());
		}
		return resMap;
	}
	
	
	
	*//**
	 * 发送签约银行卡短信验证码
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 上午11:24:20
	 *//*
	public Map<String,Object> sendSignBankCardMobileToken(String clientId, String phone, String cardNo){
		Map<String,Object> resMap = new HashMap<String,Object>();
		ResultVo result = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
		try {
			result = bankCardService.sendSignBankCardMobileToken(clientId, phone, cardNo);
		} catch (TException e) {
			LogCvt.error("发送签约银行卡短信验证码接口调用异常",e);
		}
		if(Constants.RESULT_CODE_SUCCESS.equals(result.getResultCode())){
			resMap.put(Results.code,"0000");
			resMap.put(Results.msg,"发送成功");
			
		}else{
			resMap.put(Results.code,result.getResultCode());
			resMap.put(Results.msg,result.getResultDesc());
		}
		return resMap;
	}
	*/
}
