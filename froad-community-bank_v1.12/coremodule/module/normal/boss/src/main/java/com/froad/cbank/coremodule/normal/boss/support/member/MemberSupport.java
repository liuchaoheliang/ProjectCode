package com.froad.cbank.coremodule.normal.boss.support.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.enums.IdentifyTypeEnum;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.member.MemberInfoVo;
import com.froad.cbank.coremodule.normal.boss.pojo.member.MemberVo;
import com.froad.cbank.coremodule.normal.boss.pojo.member.MemberVoRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.pay.user.dto.CustomerInfoSpecDto;
import com.pay.user.dto.MemberBankSpecDto;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.dto.VIPDto;
import com.pay.user.service.UserOuterSpecService;
import com.pay.user.service.UserSpecService;

/**
 * 用户信息
 * @author yfy
 *
 */
@Service
public class MemberSupport {
	
	@Resource
	private UserSpecService userSpecService;
	
	@Resource
	private UserOuterSpecService userOuterSpecService;

	@Resource
	private ClientSupport clientSupport;
	
	/**
	 * 会员列表
	 * @param voReq
	 * @return
	 * @throws TException 
	 */
	public HashMap<String, Object> queryList(String loginID,String mobile) throws TException {
		 HashMap<String, Object> resMap = new HashMap<String, Object>();
		 List<MemberVoRes> list = new ArrayList<MemberVoRes>();
		 
		 UserResult userResult = userOuterSpecService.queryCustomerList(loginID,mobile);
		 LogCvt.info(">>从会员系统API返回会员分页列表结果：" + JSON.toJSONString(userResult));
		 if(userResult.getResult()){
			List<UserSpecDto> userlist = userResult.getUserList();
			 if(userlist != null && userlist.size() > 0) {
				 for(UserSpecDto userSpecDto : userlist) {
					 MemberVoRes memberVoRes = new MemberVoRes();
					 BeanUtils.copyProperties(memberVoRes, userSpecDto);
					 String bankName = "";//银行名称
					 if(StringUtil.isNotBlank(userSpecDto.getBankOrgNo())){
						 ClientRes clientRes = clientSupport.getClientByBankOrg(userSpecDto.getBankOrgNo());
						 if(clientRes != null){
							 bankName = clientRes.getClientName();
						 }
					 }
					 memberVoRes.setClientChannelName(bankName);
					 if(StringUtil.isNotBlank(userSpecDto.getCreateTime())){
						 Date createTime = userSpecDto.getCreateTime();
						 memberVoRes.setCreateTime(DateUtil.longToString(createTime.getTime()));
					 }
					 if(StringUtil.isNotBlank(userSpecDto.getLastLoginTime())){
						 Date lastLoginTime = userSpecDto.getLastLoginTime();
						 memberVoRes.setLastLoginTime(DateUtil.longToString(lastLoginTime.getTime()));
					 }
					 //查询此会员vip信息
					 if(userSpecDto.getVip() != null){
						 VIPDto vipDto = userSpecDto.getVip();
						 memberVoRes.setVipStatus(vipDto.getVipStatus().getValue());
						 if(StringUtil.isNotBlank(vipDto.getVipExpirationTime())){
							 Date vipExpirationTime = vipDto.getVipExpirationTime();
							 memberVoRes.setVipExpirationTime(DateUtil.longToString(vipExpirationTime.getTime()));
						 }
					 }
					 
					 String payMode = "积分支付";//方付通所有会员都有积分支付方式
					 
					 //查询此会员的快捷卡信息，有绑定快捷卡则有快捷支付方式
					 if(userSpecDto.getMemberBankList() != null && userSpecDto.getMemberBankList().size() > 0){
						 payMode = payMode + ",快捷支付";
						 MemberBankSpecDto memberBankSpecDto = userSpecDto.getMemberBankList().get(0);
						 memberVoRes.setCardNo(memberBankSpecDto.getCardNo());
					 }
					 memberVoRes.setPayMode(payMode);
					 list.add(memberVoRes);
				 }
			 }
		 }
		 resMap.put("list", list);
		
		 return resMap;
	}
	
	/**
	 * 会员名模糊查询
	 * @param memberVo
	 * @return
	 * @throws TException
	 * @throws BossException 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectLoginID(String loginID) throws TException, BossException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<String> loginIDList = null;
		Result result = userOuterSpecService.blurQueryLoginId(loginID);//最多返回4条
		LogCvt.info(">>从会员系统API返回会员名模糊查询结果：" + JSON.toJSONString(result));
		if(result.getResult()){
			if(result.getData() != null){
				loginIDList = (List<String>) result.getData();
			}
			resMap.put("loginIDList",loginIDList);
		}else{
			throw new BossException(result.getMessage());
		}
		return resMap;
	}
	
	/**
	 * 会员信息修改提交
	 * @param memberVo
	 * @return
	 * @throws TException
	 * @throws BossException 
	 */
	public HashMap<String, Object> update(MemberVo memberVo) throws TException, BossException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		CustomerInfoSpecDto customerInfo = new CustomerInfoSpecDto();
		BeanUtils.copyProperties(customerInfo, memberVo);
		Result result = userOuterSpecService.submitCustomerInfo(customerInfo);
		if(result.getResult()){
			resMap.put("code", Constants.RESULT_SUCCESS);
			resMap.put("message", "修改提交审核成功");
		} else {
			throw new BossException(result.getMessage());
		}
		return resMap;
	}
	
	/**
	 * 会员信息详情
	 * @param memberCode
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> detail(Long memberCode) throws TException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		MemberInfoVo merberInfoVo = new MemberInfoVo();
		UserResult userResult = userSpecService.queryMemberByMemberCode(memberCode);
		LogCvt.info(">>从会员系统API返回会员("+memberCode+")信息详情结果：" + JSON.toJSONString(userResult));
		if(userResult.getResult()){
			if(userResult.getUserList() != null && userResult.getUserList().size() > 0){
				UserSpecDto userSpecDto = userResult.getUserList().get(0);
				if(userSpecDto.getLoginID() != null){
					 //会员信息
					 BeanUtils.copyProperties(merberInfoVo, userSpecDto);
					 if(StringUtil.isNotBlank(userSpecDto.getIdentityType())){
						 merberInfoVo.setIdentityTypeName(IdentifyTypeEnum
								 .valueOfName(userSpecDto.getIdentityType()));//证件名称
					 }
					 if(StringUtil.isNotBlank(userSpecDto.getCreateTime())){
						 Date createTime = userSpecDto.getCreateTime();
						 merberInfoVo.setCreateTime(DateUtil.longToString(createTime.getTime()));
					 }
					 if(StringUtil.isNotBlank(userSpecDto.getUpdateTime())){
						 Date updateTime = userSpecDto.getUpdateTime();
						 merberInfoVo.setUpdateTime(DateUtil.longToString(updateTime.getTime()));
					 }
					 if(StringUtil.isNotBlank(userSpecDto.getLastLoginTime())){
						 Date lastLoginTime = userSpecDto.getLastLoginTime();
						 merberInfoVo.setLastLoginTime(DateUtil.longToString(lastLoginTime.getTime()));
					 }
					 
					 String payMode = "积分支付";//方付通所有会员都有积分支付方式
					 
					 //查询此会员的快捷卡信息，有绑定快捷卡则有快捷支付方式(可绑定多张快捷支付、可绑定不同人)
					 if(userSpecDto.getMemberBankList() != null && userSpecDto.getMemberBankList().size() > 0){
						payMode = payMode + ",快捷支付";
						String cardNoList = "";
						String identifyNoList = "";
						List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
						for(int i = 0; i< userSpecDto.getMemberBankList().size(); i++){
							if(i+1 == userSpecDto.getMemberBankList().size()){
								cardNoList += userSpecDto.getMemberBankList().get(i).getCardNo();
								identifyNoList += userSpecDto.getMemberBankList().get(i).getIdentifyNo();
							}else{
								cardNoList += userSpecDto.getMemberBankList().get(i).getCardNo()+ ",";
								identifyNoList += userSpecDto.getMemberBankList().get(i).getIdentifyNo()+",";
							}
							HashMap<String,String> map = new HashMap<String, String>();
							map.put("cardHostName", userSpecDto.getMemberBankList().get(i).getCardHostName().trim());
							map.put("identifyNo", userSpecDto.getMemberBankList().get(i).getIdentifyNo());
							map.put("cardNo", userSpecDto.getMemberBankList().get(i).getCardNo());
							list.add(map);
						}
						merberInfoVo.setCardNo(cardNoList);
						MemberBankSpecDto memberBankSpecDto = userSpecDto.getMemberBankList().get(0);
						merberInfoVo.setIdentityType(memberBankSpecDto.getIdentifyType());//证件类型
						merberInfoVo.setIdentityTypeName(IdentifyTypeEnum
								 .valueOfName(memberBankSpecDto.getIdentifyType()));//证件名称
						merberInfoVo.setIdentityKey(identifyNoList);//证件号码
						merberInfoVo.setIdentifyList(list);
					 }
					 merberInfoVo.setPayMode(payMode);
				}
			}
		}
		resMap.put("merber", merberInfoVo);
		return resMap;
	}
	
	/**
	 * 历史绑定手机号查询
	 * @param memberCode
	 * @return
	 * @throws TException
	 * @throws BossException 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectMobile(Long memberCode) throws TException, BossException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<String> mobileList = new ArrayList<String>();
		Result result = userSpecService.queryMobileLog(memberCode);
		LogCvt.info(">>从会员系统API返回会员("+memberCode+")历史绑定手机号结果：" + JSON.toJSONString(result));
		if(result.getResult()){
			if(result.getData() != null){
				mobileList = (List<String>) result.getData();
			}
			resMap.put("mobileList",mobileList);
		}else{
			throw new BossException(result.getMessage());
		}
		return resMap;
	}
	
	/**
	 * 修改原因列表查询
	 * @param memberCode
	 * @return
	 * @throws TException
	 * @throws BossException 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectUpdateList(Long memberCode) throws TException, BossException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<String> updateReasonList = new ArrayList<String>();
		List<String> updateReasonAuditorList = new ArrayList<String>();
		List<CustomerInfoSpecDto> customerInfoSpecList =  null;
		Result result = userOuterSpecService.queryCustomerByMemberCode(memberCode);
		LogCvt.info(">>从会员系统API返回会员("+memberCode+")修改原因列表查询结果：" + JSON.toJSONString(result));
		if(result.getResult()){
			if(result.getData() != null){
				customerInfoSpecList = (List<CustomerInfoSpecDto>) result.getData();
				if(customerInfoSpecList != null && customerInfoSpecList.size() > 0){
					for(CustomerInfoSpecDto customerInfoSpec : customerInfoSpecList){
						 String auditTime = "";
						if(StringUtil.isNotBlank(customerInfoSpec.getAuditTime())){
							auditTime = DateUtil.longToString(customerInfoSpec.getAuditTime().getTime());
						 }
						//默认内容+时间
						updateReasonList.add(customerInfoSpec.getDemo()+"  "+auditTime);
						//客服审核人员：内容+时间+客服名称
						updateReasonAuditorList.add(customerInfoSpec.getDemo()+"  "+auditTime+"  "+customerInfoSpec.getCreater());
					}
				}
			}
		}else{
			throw new BossException(result.getMessage());
		}
		resMap.put("updateReasonList",updateReasonList);
		resMap.put("updateReasonCreaterList",updateReasonAuditorList);
		
		return resMap;
	}
	
	/**
	 * 短信发送列表查询
	 * @param memberCode
	 * @return
	 * @throws TException
	 * @throws BossException 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectSmsList(Long memberCode) throws TException, BossException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<String> smsSendOutList = new ArrayList<String>();
		List<CustomerInfoSpecDto> customerInfoSpecList =  null;
		Result result = userOuterSpecService.queryModifySmsRecord(memberCode,2);
		LogCvt.info(">>从会员系统API返回会员("+memberCode+")短信发送列表查询结果：" + JSON.toJSONString(result));
		if(result.getResult()){
			if(result.getData() != null){
				customerInfoSpecList = (List<CustomerInfoSpecDto>) result.getData();
				if(customerInfoSpecList != null && customerInfoSpecList.size() > 0){
					for(CustomerInfoSpecDto customerInfoSpec : customerInfoSpecList){
						 String auditTime = "";
						if(StringUtil.isNotBlank(customerInfoSpec.getAuditTime())){
							auditTime = DateUtil.longToString(customerInfoSpec.getAuditTime().getTime());
						 }
						//默认内容+时间
						String smsContent = customerInfoSpec.getSmsContent();
						if(StringUtil.isNotBlank(smsContent)){
							if(smsSendOutList.size() < 4){
								String[] content = smsContent.split("，");
								if(StringUtil.isNotBlank(content) && content.length > 1){
									if(smsSendOutList.size() < 2){
										smsSendOutList.add(content[1]+"  "+auditTime);
										smsSendOutList.add(content[0]+"  "+auditTime);
									}else{
										smsSendOutList.add(content[1]+"  "+auditTime);
									}
								}else{
									smsSendOutList.add(content[0]+"  "+auditTime);
								}
							}
						}
					}
				}
			}
		}else{
			throw new BossException(result.getMessage());
		}
		resMap.put("smsSendOutList",smsSendOutList);
		
		return resMap;
	}
	
	/**
	 * 手机号验证
	 * @tilte vmobile
	 * @author zxl
	 * @date 2015年6月26日 上午10:01:08
	 * @param memberCode
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> vmobile(String mobile) throws Exception {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		UserResult result = userSpecService.validUserMobile(mobile);
		if(result.getResult()){
			throw new BossException("手机号已存在");
		}else{
			resMap.put("code", Constants.RESULT_SUCCESS);
			resMap.put("message", "验证通过");
		}
		return resMap;
	}
	
	/**
	 * 邮箱验证
	 * @tilte vemail
	 * @author zxl
	 * @date 2015年6月26日 上午10:01:08
	 * @param memberCode
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> vemail(String email) throws Exception {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		UserResult result = userSpecService.validUserMail(email);
		if(result.getResult()){
			throw new BossException("邮箱已存在");
		}else{
			resMap.put("code", Constants.RESULT_SUCCESS);
			resMap.put("message", "验证通过");
		}
		return resMap;
	}
}
