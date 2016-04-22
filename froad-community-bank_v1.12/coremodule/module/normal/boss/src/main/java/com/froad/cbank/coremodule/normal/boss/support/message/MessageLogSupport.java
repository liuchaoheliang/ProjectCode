package com.froad.cbank.coremodule.normal.boss.support.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.message.MessagesVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.message.MessagesVoRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.thrift.service.SmsLogService;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsLogPageVoRes;
import com.froad.thrift.vo.SmsLogVo;


@Service
public class MessageLogSupport {
	
	@Resource
	SmsLogService.Iface smsLogService;
	@Resource
	SmsMessageService.Iface smsMessageService;
	
	@Resource
	ClientSupport clientSupport;
	/**
	 * 
	 * <p>功能简述：短信日志列表查询</p> 
	 * <p>使用说明：条件查询短信日志列表</p> 
	 * <p>创建时间：2015年5月11日上午11:49:17</p>
	 * <p>作者: 陈明灿</p>
	 * @param convertMap
	 * @return
	 * @throws TException 
	 */
	public Map<String, Object> list(MessagesVoReq convertMap) throws TException {
		// 分页信息
		PageVo pageReq = new PageVo();
		pageReq.setPageNumber(convertMap.getPageNumber());
		pageReq.setPageSize(convertMap.getPageSize());
		// 查询条件封装
		SmsLogVo smsLogVo = new SmsLogVo();
		if (StringUtil.isNotBlank(convertMap.getClientId())) {
			smsLogVo.setClientId(convertMap.getClientId());
		}
		
		if (StringUtil.isNotBlank(convertMap.getSmsType())) {
			smsLogVo.setSmsTypeMark(convertMap.getSmsType());
		}
		if (StringUtil.isNotBlank(convertMap.getMobile())) {
			smsLogVo.setMobile(convertMap.getMobile());
		}
		// 返回数据对象
		Map<String, Object> map = new HashMap<String, Object>();
		List<MessagesVoRes> list = null;
		MessagesVoRes messagesVoRes = null;

		SmsLogPageVoRes voRes = smsLogService.getSmsLogByPage(pageReq, smsLogVo);
		LogCvt.info("短信日志列表查询请求返回结果:", JSON.toJSONString(voRes));
		//查询所有客户端
		List<ClientRes>clientList=clientSupport.getClient();
		// 分页信息封装
		PageVo pageRes = voRes.getPage();
		Page page = new Page();
		BeanUtils.copyProperties(page, pageRes);
		map.put("page", page);
		// 短信日志信息封装
		List<SmsLogVo> logVoList = voRes.getSmsLogVoList();
		if (null != logVoList && logVoList.size() > 0) {
			list = new ArrayList<MessagesVoRes>();
			// Date date = null;
			for (SmsLogVo smsLogVo2 : logVoList) {
				messagesVoRes = new MessagesVoRes();
				BeanUtils.copyProperties(messagesVoRes, smsLogVo2);
				//循环设置客户端
				for (ClientRes client : clientList) {
					if(client.getClientId().equals(messagesVoRes.getClientId())){
						messagesVoRes.setClientName(client.getClientName());
					}
				}
				
				if(smsLogVo2.getContent()!=null && smsLogVo2.getContent().indexOf("短信验证码")>0){
					messagesVoRes.setContent(smsLogVo2.getContent().replaceAll("\\d", "*"));
				}
				
				messagesVoRes.setSmsType(smsLogVo2.getSmsTypeMark());
				messagesVoRes.setMessageSn(smsLogVo2.getId());// 设置id
				// 转化时间,封装创建时间
				if(StringUtil.isNotBlank(smsLogVo2.getCreateTime()) && smsLogVo2.getCreateTime() != 0){
					messagesVoRes.setCreateTime(DateUtil.longToString(smsLogVo2.getCreateTime()));
				}
				// 设置发送帐号:发送人
				messagesVoRes.setSendAccounts(smsLogVo2.getSendUser());
				list.add(messagesVoRes);
			}
		}
		map.put("list", list);
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：短信重发</p> 
	 * <p>使用说明：根据id重发短信</p> 
	 * <p>创建时间：2015年5月11日下午3:17:52</p>
	 * <p>作者: 陈明灿</p>
	 * @param id
	 * @return
	 * @throws BossException 
	 * @throws TException 
	 */
	public Map<String, Object> sendAgain(String id) throws BossException, TException {
		Map<String, Object> map = new HashMap<String, Object>();
		//数据类型转化
		Long smsId = Long.parseLong(id);
		ResultVo resultVo = smsMessageService.reSendSMSForManageBOSS(smsId);
		LogCvt.info("短信重发请求返回结果:", JSON.toJSONString(resultVo));
		if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", resultVo.getResultDesc());
		} else {
			throw new BossException(resultVo.getResultCode(),  resultVo.getResultDesc());
		}
		return map;
	}
	
	public static void main(String[] args) {
		String msg = "尊敬的用户，您现在进行重置登录密码，短信验证码为379049。【重庆农商行江渝惠】";
		System.err.println(msg.replaceAll("\\d", "*"));
	}

}