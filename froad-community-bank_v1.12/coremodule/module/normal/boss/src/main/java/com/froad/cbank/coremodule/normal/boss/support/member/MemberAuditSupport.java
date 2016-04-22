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
import com.froad.cbank.coremodule.normal.boss.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.normal.boss.enums.SmsTypeEnum;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.member.MemberAuditVo;
import com.froad.cbank.coremodule.normal.boss.pojo.member.MemberAuditVoRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.SmsMessageVo;
import com.pay.user.dto.CustomerInfoSpecDto;
import com.pay.user.dto.Result;
import com.pay.user.service.UserOuterSpecService;
import com.pay.user.service.UserSpecService;

/**
 * 待审核
 * @author yfy
 *
 */
@Service
public class MemberAuditSupport {

	@Resource
	private UserSpecService userSpecService;
	@Resource
	private UserOuterSpecService userOuterSpecService;
	@Resource
	private SmsMessageService.Iface  smsMessageService;
	@Resource
	private ClientSupport clientSupport;
	/**
	 * 待审核列表
	 * @param voReq
	 * @return
	 * @throws TException 
	 */
	public HashMap<String, Object> queryList(Integer pageSize,Integer pageNumber) throws TException {
		 HashMap<String, Object> resMap = new HashMap<String, Object>();
		 Page page = new Page();
		 List<MemberAuditVoRes> list = new ArrayList<MemberAuditVoRes>();

		 com.pay.user.dto.Page<CustomerInfoSpecDto> pageRes = userOuterSpecService.queryCustomerAudit(
				 pageSize, pageNumber);
		 LogCvt.info(">>从会员系统API返回会员待审核分页列表结果：" + JSON.toJSONString(pageRes));
		 List<CustomerInfoSpecDto> customerAuditSpecDtoList = pageRes.getResult();
		 if(customerAuditSpecDtoList != null && customerAuditSpecDtoList.size() > 0) {
			 for(CustomerInfoSpecDto customerAuditSpecDto : customerAuditSpecDtoList) {
				 MemberAuditVoRes memberAuditVoRes = new MemberAuditVoRes();
				 BeanUtils.copyProperties(memberAuditVoRes, customerAuditSpecDto);
				 ClientRes clientRes = null;
				 if(StringUtil.isNotBlank(customerAuditSpecDto.getBankOrgNo())){
					 clientRes = clientSupport.getClientByBankOrg(customerAuditSpecDto.getBankOrgNo());
					 if(clientRes != null){
						 memberAuditVoRes.setClientId(clientRes.getClientId());
						 memberAuditVoRes.setClientChannelName(clientRes.getClientName());
					 }
				 }
				 if(StringUtil.isNotBlank(customerAuditSpecDto.getCreateTime())){
					 Date createTime = customerAuditSpecDto.getCreateTime();
					 memberAuditVoRes.setCreateTime(DateUtil.longToString(createTime.getTime()));
				 }
				 if(StringUtil.isNotBlank(customerAuditSpecDto.getAuditTime())){
					 Date auditTime = customerAuditSpecDto.getAuditTime();
					 memberAuditVoRes.setAuditTime(DateUtil.longToString(auditTime.getTime()));
				 }
				 list.add(memberAuditVoRes);
			 }
		 }
		 //封装分页参数
		 page.setTotalCount(pageRes.getTotalRecord());
		 page.setPageCount(pageRes.getTotalPage());
		 page.setPageNumber(pageRes.getPageNo());
		 
		 resMap.put("list", list);
		 resMap.put("page", page);
		
		return resMap;
	}
	
	/**
	 * 待审核列表审核
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String, Object> audit(MemberAuditVo memberAuditVo) throws TException, BossException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		Result result = userOuterSpecService.auditCustomer(memberAuditVo.getMsgID(),
				memberAuditVo.getState(),memberAuditVo.getAuditor(),
				memberAuditVo.getSmsContent());//默认0：未审核，1通过，2不通过
		LogCvt.info(">>从会员系统API返回会员待审核分页列表结果：" + JSON.toJSONString(result));
		if(result.getResult()){
			if(StringUtil.equals(memberAuditVo.getState()+"", AuditFlagEnum.ACCEPTED.getCode()) 
					&& StringUtil.isNotBlank(memberAuditVo.getClientId()) 
					&& StringUtil.isNotBlank(memberAuditVo.getMobile())){
				//向解绑手机号发送短信
				List<String> str = new ArrayList<String>();
				SmsMessageVo smsMsg = new SmsMessageVo(); 
				smsMsg.setClientId(memberAuditVo.getClientId());
				smsMsg.setMobile(memberAuditVo.getMobile());
				str.add(memberAuditVo.getMobile());
				smsMsg.setValues(str);
				smsMsg.setSmsType(SmsTypeEnum.bossAbsolvedMobile.getValue());
				smsMessageService.sendSMS(smsMsg);
				LogCvt.info(">>解绑短信发送" + JSON.toJSONString(smsMsg));
				if(StringUtil.isNotBlank(memberAuditVo.getBindMobile())){
					//新绑定手机号发送短信
					smsMsg.setMobile(memberAuditVo.getBindMobile());
					str.clear();
					str.add(memberAuditVo.getBindMobile());
					smsMsg.setValues(str);
					smsMsg.setSmsType(SmsTypeEnum.bossBandMobile.getValue());
					smsMessageService.sendSMS(smsMsg);
					LogCvt.info(">>绑定短信发送" + JSON.toJSONString(smsMsg));
				}
			}
			resMap.put("code", Constants.RESULT_SUCCESS);
			resMap.put("message", "审核成功");
		}else{
			throw new BossException(result.getMessage());
		}
		return resMap;
	}
	
}
