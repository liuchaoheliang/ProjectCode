package com.froad.cbank.coremodule.normal.boss.support.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.util.DESUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.normal.boss.enums.IdentifyTypeEnum;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.member.MemberReperVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.member.MemberVoReq;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.HttpClientUtil;
import com.pay.user.dto.MemberApplySpecDto;
import com.pay.user.dto.Result;
import com.pay.user.service.UserOuterSpecService;
/**
 * 人工申诉
 * @author yfy
 *
 */
@Service
public class MemberRepreSupport {

	@Resource
	private UserOuterSpecService userOuterSpecService;
	
	/**
	 * 人工申诉列表
	 * @param voReq
	 * @return
	 * @throws TException 
	 */
	public HashMap<String, Object> queryList(MemberVoReq voReq) throws TException {
		 HashMap<String, Object> resMap = new HashMap<String, Object>();
		 List<MemberReperVoRes> list = new ArrayList<MemberReperVoRes>();
		 Page page = new Page();
		 
		 com.pay.user.dto.Page<MemberApplySpecDto> pageRes = userOuterSpecService.queryApplyByPage(
				 voReq.getMsgID(), voReq.getLoginID(),voReq.getMobile(),
				 voReq.getPageSize(), voReq.getPageNumber());
		 LogCvt.info(">>从会员系统API返回会员申诉分页列表结果：" + JSON.toJSONString(pageRes));
		 List<MemberApplySpecDto> memberApplySpecDtoList = pageRes.getResult();
		 if(memberApplySpecDtoList != null && memberApplySpecDtoList.size() > 0) {
			 for(MemberApplySpecDto memberApplySpecDto : memberApplySpecDtoList) {
				 MemberReperVoRes memberReperVoRes = new MemberReperVoRes();
				 BeanUtils.copyProperties(memberReperVoRes, memberApplySpecDto);
				 if(StringUtil.isNotBlank(memberApplySpecDto.getIdentifyType())){
					 memberReperVoRes.setIdentifyTypeName(IdentifyTypeEnum
							 .valueOfName(memberApplySpecDto.getIdentifyType()));//证件名称
				 }
				 Date dt = memberApplySpecDto.getCreateTime();
				 memberReperVoRes.setCreateTime(DateUtil.longToString(dt.getTime()));
				 list.add(memberReperVoRes);
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
	 * 个人申诉审核
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	@SuppressWarnings("static-access")
	public HashMap<String, Object> audit(Long msgID,Integer state,
			String reason,String email,String memberCode) throws TException, BossException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		Result result = userOuterSpecService.checkApply(msgID, state, reason);//默认0：未审核，1通过，2不通过
		if(result.getResult()){
			List<String> recAddress = new ArrayList<String>();
			recAddress.add(email);
			String code = System.currentTimeMillis()+memberCode;
			if(String.valueOf(state).equals(AuditFlagEnum.ACCEPTED.getCode())){//发送通过邮件
				this.sendEmail(recAddress,this.getAuditAccepted(DESUtil.encrypt(code)));
			}else if(String.valueOf(state).equals(AuditFlagEnum.REJECTED.getCode())){//发送未通过邮件
				this.sendEmail(recAddress,this.getAuditRejected());
			}
			resMap.put("code", Constants.RESULT_SUCCESS);
			resMap.put("message", result.getMessage());
		}else{
			throw new BossException(result.getMessage());
		}
		return resMap;
	}
	
	/**
	 * 发送邮件
	 * @param subject 主题
	 * @param content 内容
	 * @param recAddress 接收人地址
	 * @return
	 */
	public boolean sendEmail(List<String> recAddress,String content){
		LogCvt.info("开始发送邮件");
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("subject", Constants.SUBJECT);
		jsonObj.put("recAddress", recAddress);
		jsonObj.put("fromAddress", null);
		jsonObj.put("fromUserName", "services");
		jsonObj.put("ccAddress", null);
		jsonObj.put("attachments", null);
		jsonObj.put("type", "EMAIL");
		Map<String,String> data = new HashMap<String,String>();
		data.put("value", content.replaceAll("/", "\\/"));
		jsonObj.put("data", data);
		jsonObj.put("templateId", 0);
		jsonObj.put("bizCode", Constants.BIZCODE);
		
		JSONObject resJson = HttpClientUtil.doPost(Constants.MESSAGE_SERVICE_URL, jsonObj);
		
		if(Constants.RESULT_SUCCESS.equals(resJson.getString("returnCode"))){
			LogCvt.info("发送邮件成功");
			return true;
		}else{
			LogCvt.info("发送邮件失败：" + resJson.getString("returnMessage"));
			return false;
		}
	}
	/**
	 * 安徽版本还是现在线上的申诉链接anhui，标准版申诉链接已改为froad--上线的时候注意
	 * @param code
	 * @return
	 */
	public static String getAuditAccepted(String code){
		String MESSAGE_AUDIT_ACCEPTED = "<html><h1>亲爱的会员：<h1/><br/>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好！您已通过人工申述服务，申述成功。"
				+ " <a href='"+Constants.MESSAGE_EMAIL_URL+"?code="+code+"'>请点此链接</a>重置您的安全问题，感谢您对社区银行的关注与支持! <br/>"
				+ "如果您点击上述链接无效，请把下面的代码拷贝到浏览器的地址栏中：<br/><p>"
				+ "&nbsp;"+Constants.MESSAGE_EMAIL_URL+"?code="+code+"<br/><p>"
				+ "此信由系统发出，系统不接受回信，因此请勿直接回复。<br/><p>"
				+ "安全使用您的“账户”<br/>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请不要将您的密码使用在其他网站上。<br/>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请不要在电子邮件中输入您的“社区银行账户”密码信息，包括社区银行发送的邮件<br/>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请不要告知任何人您的“社区银行账户”密码信息，包括社区银行的工作人员。</html>";
		
		return MESSAGE_AUDIT_ACCEPTED;
	}
	
	public static String getAuditRejected(){
		String MESSAGE_AUDIT_REJECTED = "<html><h1>亲爱的会员：<h1/><br/>"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好！很遗憾通知您，您的人工申述未成功。请检查您所提供的"
			+ "信息资料是否正确无误，确认后可再次申请人工申述。<br/><p>"
			+ "此信由系统发出，系统不接受回信，因此请勿直接回复。<br/><p>"
			+ "安全使用您的“账户”<br/>"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请不要将您的密码使用在其他网站上。<br/>"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请不要在电子邮件中输入您的“社区银行账户”密码信息，包括社区银行发送的邮件<br/>"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请不要告知任何人您的“社区银行账户”密码信息，包括社区银行的工作人员。</html>";
		return MESSAGE_AUDIT_REJECTED;
	}
}
