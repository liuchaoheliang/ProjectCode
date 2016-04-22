package com.froad.thrift.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.SmsType;
import com.froad.logback.LogCvt;
import com.froad.logic.SmsContentLogic;
import com.froad.logic.SmsLogLogic;
import com.froad.logic.impl.SmsContentLogicImpl;
import com.froad.logic.impl.SmsLogLogicImpl;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.SmsContent;
import com.froad.po.SmsLog;
import com.froad.po.SmsMessage;
import com.froad.thrift.Constants;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsMessageResponseVo;
import com.froad.thrift.vo.SmsMessageVo;
import com.froad.thrift.service.SmsMessageService;
import com.froad.util.BeanUtil;
import com.froad.util.HttpClientUtil;
import com.froad.util.RandomCodeUtils;
import com.froad.util.StrUtils;
import com.froad.util.Support;
import com.froad.util.UUIDHelper;

public class SmsMessageServiceImpl extends BizMonitorBaseService implements SmsMessageService.Iface {

	public SmsMessageServiceImpl() {}
	
	public SmsMessageServiceImpl(String name, String version) {
		super(name, version);
	}
	private SmsContentLogic smsContentLogic = new SmsContentLogicImpl();
	private SmsLogLogic smsLogLogic = new SmsLogLogicImpl();
	private Support support=new Support();
	/**
	 * 监控服务
	 * */
	private MonitorService monitorService = new MonitorManager();
	/**
	 * 随机验证码长度
	 */
	private final int MOBILE_TOKEN_LENGTH = 6;
	/**
	 * 图片随机验证码长度
	 */
	private final int IMAGE_TOKEN_LENGTH = 4;
	
	/**
	 * 短信业务编码 ,默认为社区银行
	 */
	public static String BIZCODE="1301";
	
	//========================================================公共方法块==========================================
	/**
	 * 基础校验
	* <p>Function: validation</p>
	* <p>Description: </p>
	* @author wuhelian@thankjava.com
	* @date 2015年1月8日 下午4:02:05
	* @version 1.0
	* @param smsBean
	* @return
	 */
	private SmsMessageResponseVo validation(SmsMessage smsMessage){
		
		if(smsMessage == null){
			return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"参数为空","","");
		}
		if(SmsType.image.getCode() != smsMessage.getSmsType()){ //非图片验证码时，验证手机号
			if(StringUtils.isBlank(smsMessage.getMobile())){
				return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"手机号码为空","","");
			}
			if(!smsMessage.getMobile().matches("[0-9]{11}")){
				return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE, "手机号码非法: " + smsMessage.getMobile(),"","");
			}
		}
		if(smsMessage.getSmsType()== 0){
			return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE, "请指定短信类型","","");
		}
		if(smsMessage.getClientId()==null || "".equals(smsMessage.getClientId()) ){
			
			return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE, "未指定客户端ID","","");
		}
		return new SmsMessageResponseVo(Constants.NORMAL_RESPONSE,null,"","");
	}


	/**
	 * 校验短信/图片验证码
	  * @Title: verifyMobileToken
	  * @Description: TODO
	  * @author: wuhelian 2015年3月25日
	  * @modify: wuhelian 2015年3月25日
	  * @param @param token
	  * @param @param code
	  * @param @return
	  * @throws
	  * @see com.froad.thrift.service.SmsMessageService.Iface#verifyMobileToken(java.lang.String, java.lang.String)
	 */
	public ResultVo verifyMobileToken(String token, String code)throws TException  {
		if(StringUtils.isBlank(token) || StringUtils.isBlank(code)){
			return new ResultVo(Constants.ABNORMAL_RESPONSE,"请求参数为空");
		}
		SmsLog smsLog = new SmsLog();
		smsLog.setToken(token);
		List<SmsLog> smsLogs;
		try { 
			smsLogs = smsLogLogic.findSmsLog(smsLog,"");
		} catch (Exception e) {
			LogCvt.error("获取验证短信日志DB ERROR", e);
			return new ResultVo(Constants.ABNORMAL_RESPONSE,"校验失败");
		}
		if(smsLogs == null || smsLogs.size() != 1){
			LogCvt.info("验证token: " + token);
			return new ResultVo(Constants.ABNORMAL_RESPONSE,"校验失败");
		}
		smsLog = smsLogs.get(0);
		
		if(smsLog.getIsUsed()){
			LogCvt.info("该验证码已校验 token: " + token);
			return new ResultVo(Constants.ABNORMAL_RESPONSE,"该验证码已校验");
		}
		String saveCode = smsLog.getCode();
		if(new Date().before(smsLog.getExpireTime())){
			if(!saveCode.equalsIgnoreCase(code)){
				return new ResultVo(Constants.ABNORMAL_RESPONSE,"验证码错误");
			}
			SmsLog temp = new SmsLog();
			temp.setId(smsLog.getId());
			temp.setIsUsed(true);
			temp.setIsSuccess(smsLog.getIsSuccess());
			try {
			 if(!smsLogLogic.updateSmsLog(temp)){
					return new ResultVo(Constants.ABNORMAL_RESPONSE,"校验短信失败");
			 }
			 return new ResultVo(Constants.NORMAL_RESPONSE,"验证成功");
			} catch (Exception e) {
				LogCvt.error("修改短信日志状态DB ERROR,token:" + token +e.getMessage(), e);
				return new ResultVo(Constants.ABNORMAL_RESPONSE,"校验短信失败");
			}
		}
			return new ResultVo(Constants.ABNORMAL_RESPONSE,"验证失败，验证码已过期");
		
		
	}
	
	/**
	 * 发送短信
	  * @Title: sendSMS
	  * @Description: TODO
	  * @author: wuhelian 2015年3月25日
	  * @modify: wuhelian 2015年3月25日
	  * @param @param smsMessageRequestVo
	  * @param @return 返回token
	  * @throws
	  * @see com.froad.thrift.service.SmsMessageService.Iface#sendSMS(com.froad.thrift.vo.SmsMessageRequestVo)
	 */
	@Override
	public SmsMessageResponseVo sendSMS(SmsMessageVo smsMessageVo)
			throws TException {
		try {
			SmsMessage smsMessage = (SmsMessage)BeanUtil.copyProperties(SmsMessage.class, smsMessageVo);
			SmsMessageResponseVo responseVo = validation(smsMessage);
			if(!Constants.NORMAL_RESPONSE.equals(responseVo.getResultCode())){
				return responseVo;
			}
			//=============获取短信模版===============
			SmsContent smsContent = new SmsContent();
			smsContent.setClientId(smsMessage.getClientId());
			smsContent.setSmsType(smsMessage.getSmsType());
			List<SmsContent> smsContents;
			smsContents= smsContentLogic.findSmsContent(smsContent);
			LogCvt.info("接收数据: " + JSONObject.toJSONString(smsMessage));
			if(smsContents == null || smsContents.size() != 1){
				LogCvt.info("未能获取模版或者无法通过传入条件确定唯一模版");
				return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"未能获取指定模版信息","","");
			}
			smsContent = smsContents.get(0);
			
			//短信内容
			List<String>   list=smsMessage.getValues(); 
			String[] values2=null;
			if(smsMessage.getValues() !=null)
			{
				 values2 = new String[list.size()];      
				 values2 = list.toArray(values2);
			}
			String code = smsMessage.getCode();
			if(smsMessage.getCode() == null || "".equals(smsMessage.getCode()))
			{
			 code = RandomCodeUtils.makeCheckCodeAllNumber(MOBILE_TOKEN_LENGTH);
			}
		
			String content = StrUtils.getContentReg(smsContent.getContent(), values2);
			content += smsContent.getMsgSuffix();
			/**
			 *  保存短信日志
			 */
			SmsLog smsLog = saveSmsLog(smsMessage, smsContent,code,content);
			
			if(smsLog.getId() == 0){
				return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"短信日志保存失败","","");
			}
			/**
			 * 调用第三方发送短信接口
			 */
			JSONObject 	resp=sendSms(support.QRCODE_SMSURL,smsLog); 
		 	if( resp != null && Constants.NORMAL_RESPONSE.equals(resp.get("returnCode").toString())){
				/**
				 *  更新短信日志
				 */
		 		return afterUpdteSmsLog(smsLog);
			 }else{
				return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"短信发送失败","","");
			 }
		} catch (Exception e) {
			LogCvt.error("短信发送失败,"+e.getMessage(), e);
			return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"短信发送失败","","");
		}
	}

	/**
	 * 短信验证码并发送
	 * 
	 */
	@Override
	public SmsMessageResponseVo sendMobleTokenSMS(SmsMessageVo smsMessageVo)
			throws TException {
		try {
			SmsMessage smsMessage = (SmsMessage)BeanUtil.copyProperties(SmsMessage.class, smsMessageVo);
			SmsMessageResponseVo responseVo = validation(smsMessage);
			if(!Constants.NORMAL_RESPONSE.equals(responseVo.getResultCode())){
				return responseVo;
			}
			//=============获取短信模版===============
			SmsContent smsContent = new SmsContent();
			smsContent.setClientId(smsMessage.getClientId());
			smsContent.setSmsType(smsMessage.getSmsType());
			List<SmsContent> smsContents;
			smsContents= smsContentLogic.findSmsContent(smsContent);
			LogCvt.info("接收数据: " + JSONObject.toJSONString(smsMessage));
			if(smsContents == null || smsContents.size() != 1){
				LogCvt.info("未能获取模版或者无法通过传入条件确定唯一模版");
				return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"未能获取指定模版信息","","");
			}
			smsContent = smsContents.get(0);
			String code = RandomCodeUtils.makeCheckCodeAllNumber(MOBILE_TOKEN_LENGTH);
			String content ="";
			//短信内容
			content= StrUtils.getContentReg(smsContent.getContent(), new String[]{code});
			content += smsContent.getMsgSuffix();
			/**
			 *  保存短信日志
			 */
			SmsLog smsLog = saveSmsLog(smsMessage, smsContent,code,content);
			
			if(smsLog.getId() == 0){
				return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"短信日志保存失败","","");
			}
			/**
			 * 调用第三方发送短信接口
			 */
			JSONObject 	resp=sendSms(support.QRCODE_SMSURL,smsLog); 
			LogCvt.info("连接："+support.QRCODE_SMSURL);
			LogCvt.info("接收："+resp.toJSONString());
		 	if( resp != null && Constants.NORMAL_RESPONSE.equals(resp.get("returnCode").toString())){
				/**
				 *  更新短信日志
				 */
		 		return afterUpdteSmsLog(smsLog);
			 }else{
				return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"短信发送失败","","");
			 }
		} catch (Exception e) {
			LogCvt.error("短信发送失败,"+e.getMessage(), e);
			return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"短信发送失败","","");
		}
	}
	/**
	 *  短信发送完成更新短信日志记录
	  * @Title: afterUpdteSmsLog
	  * @Description: TODO
	  * @author: share 2015年3月31日
	  * @modify: share 2015年3月31日
	  * @param @param smsLog
	  * @param @return    
	  * @return SmsMessageResponseVo    
	  * @throws
	 */
	private SmsMessageResponseVo afterUpdteSmsLog(SmsLog smsLog) {
		String resultCode;
		String resultDesc;
		SmsMessageResponseVo responseVo = new SmsMessageResponseVo();
		SmsLog sms = new SmsLog();
		sms.setIsSuccess(true);
		sms.setId(smsLog.getId());
		sms.setClientId(smsLog.getClientId());
		Boolean b  = smsLogLogic.updateSmsLog(sms);
		if(!b){
			LogCvt.error("更新短信日志DB ERROR,smslog.id="+smsLog.getId());
			resultCode = Constants.ABNORMAL_RESPONSE;
			resultDesc = "更新短信状态失败";
		}else{
			resultCode = Constants.NORMAL_RESPONSE;
			resultDesc = "短信发送成功";
			responseVo.setToken(smsLog.getToken());
		}
		responseVo.setResultCode(resultCode);
		responseVo.setResultDesc(resultDesc);
		return responseVo;
	}
	
	
	
	/**
	 *  保存短信验证码
	  * @Title: saveSmsLog
	  * @Description: TODO
	  * @author: share 2015年3月31日
	  * @modify: share 2015年3月31日
	  * @param @param smsMessageVo
	  * @param @param smsContent
	  * @param @return    
	  * @return SmsLog    
	  * @throws
	 */
	private SmsLog saveSmsLog(SmsMessage smsMessage,SmsContent smsContent,	String code ,String content ){
		
		String token = null;
		SmsLog tempData = new SmsLog();
		while(true){
			token = UUIDHelper.getUUID();
			tempData.setToken(token);//查询数据库是否有该token如果有则从新生成
			List<SmsLog> list = smsLogLogic.findSmsLog(tempData,"");
			if(list == null || list.size() == 0){
				break;
			}
		}
		SmsLog smsLog = new SmsLog();
		smsLog.setClientId(smsMessage.getClientId());
		smsLog.setContent(content);
		smsLog.setCreateTime(new Date());
		smsLog.setSmsType(smsMessage.getSmsType());
		smsLog.setMobile(smsMessage.getMobile());
		smsLog.setSendUser(StringUtils.isBlank(smsMessage.getSendUser()) ? null : smsMessage.getSendUser());
		smsLog.setSendIp(StringUtils.isBlank(smsMessage.getSendIp()) ? null : smsMessage.getSendIp());
		smsLog.setIsSuccess(false);
		smsLog.setIsUsed(false);
		smsLog.setCode(code);
		smsLog.setToken(token);
		smsLog.setUrl("");
		Date validdate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(validdate);
		validdate = calendar.getTime();
		if(smsContent.getValidTime() !=0){
			calendar.add(Calendar.SECOND,smsContent.getValidTime());
		}else{
			calendar.add(Calendar.DATE,1);
		}	
		validdate = calendar.getTime();
		smsLog.setExpireTime(validdate);
		// 保存
		Long smsLogId = smsLogLogic.addSmsLog(smsLog);
		smsLog.setId(smsLogId);
		
		return smsLog;
	}
	
	public static void main(String[] args) {
		String code = RandomCodeUtils.makeCheckCode(4); //生成字母加数字的随机验证码
		System.out.println(code);
		
	}
	/**
	 * 获取图片验证码
	* <p>Function: sendSMS</p>
	* <p>Description: </p>
	* @author wuhelian
	* @date 2015年3月23日 下午3:33:05
	* @version 1.0
	* @param smsBean
	* @return
	 */
	@Override
	public SmsMessageResponseVo createImgage(SmsMessageVo smsMessageVo)
			throws TException {

		SmsMessage smsMessage = (SmsMessage)BeanUtil.copyProperties(SmsMessage.class, smsMessageVo);
		SmsMessageResponseVo smsMessageResponse = validation(smsMessage);
		if(!Constants.NORMAL_RESPONSE.equals(smsMessageResponse.getResultCode())){
			return smsMessageResponse;
		}
		if(SmsType.image.getCode() !=(smsMessage.getSmsType())){
			return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE, "请指定图片类型为-1","","");
		}
		//-------- 生成手机验证码相关 ------
		String code = RandomCodeUtils.makeCheckRandom(IMAGE_TOKEN_LENGTH); //生成字母加数字的随机验证码
		String token = null;
		SmsLog tempData = new SmsLog();
		while(true){
			token = UUIDHelper.getUUID();
			tempData.setToken(token);//查询数据库是否有该token如果有则从新生成
			List<SmsLog> list = smsLogLogic.findSmsLog(tempData,"");
			if(list == null || list.size() == 0){
				break;
			}
		}
				
		SmsLog smsLog = new SmsLog();
		smsLog.setClientId(smsMessage.getClientId());
		smsLog.setContent(code+"");//图片验证码时默认为0
		smsLog.setSmsType(smsMessage.getSmsType());
		smsLog.setCode(code);
		smsLog.setToken(token);
//		String url="http://101.231.191.148:30011/froad-wordimage/image/home/ftp/froad-wordimage/image/2015-03-26/0_1427357915523.png";
		String url="";
	/**
	 * 	调用二维码模块生成图片验证码,返回图片验证码url
	 */
		try {
			 url=support.getQrWordImage(smsLog.getContent());
			// TODO 生成图片验证码正常 - 发送监控
			 monitorService.send(MonitorPointEnum.Support_Get_Image_Verification_Success);
		}catch (Exception e) {
			LogCvt.error("获取图片验证码异常," , e);
			// TODO 生成图片验证码异常 - 发送监控
			monitorService.send(MonitorPointEnum.Support_Get_Image_Verification_Failure);
			return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"获取图片验证码异常","","");
		}
		smsLog.setMobile(smsMessage.getMobile());
		smsLog.setSendUser(StringUtils.isBlank(smsMessage.getSendUser()) ? null : smsMessage.getSendUser());
		smsLog.setSendIp(StringUtils.isBlank(smsMessage.getSendIp()) ? null : smsMessage.getSendIp());
		smsLog.setUrl(url);
		smsLog.setCreateTime(new Date());
		smsLog.setIsSuccess(true);
		smsLog.setIsUsed(false);
		
		Date validdate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(validdate);
		validdate = calendar.getTime();
		if(smsMessageVo.getValidTime() !=0){ 
		//有效期=当前日期+有效时长，有效时长以秒为单位
		calendar.add(Calendar.SECOND,smsMessageVo.getValidTime());  
		}else{
		calendar.add(Calendar.DATE,1);//有效期为在一天后
		}
		validdate = calendar.getTime();
		smsLog.setExpireTime(validdate);
		Long smsLogId;
		try {
			smsLogId = smsLogLogic.addSmsLog(smsLog);
			if(smsLogId >0){
				return new SmsMessageResponseVo(Constants.NORMAL_RESPONSE,"图片验证码获取成功",url,token);
			}
			return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"获取图片验证码异常","","");
		} catch (Exception e) {
			LogCvt.error("保存图片验证短信日志DB ERROR:" + token +"\n"+e.getMessage(), e);
			return new SmsMessageResponseVo(Constants.ABNORMAL_RESPONSE,"获取图片验证码异常","","");
		}
		
	
	}
	
	 /**
     * BOSS管理平台短信重发
     * 
     * @param smsLogId
     */
    public ResultVo reSendSMSForManageBOSS(long smsLogId) throws org.apache.thrift.TException{
    	
    	List<SmsLog> smsLogs;
    	SmsLog smsLog;
		try {
			smsLog =new SmsLog();
			smsLog.setId(smsLogId);
			smsLogs = smsLogLogic.findSmsLog(smsLog,"");
			if(smsLogs == null || smsLogs.size() != 1){
				LogCvt.info("请求重发短信失败,获取短信日志 DB ERROR");
				return new ResultVo(Constants.ABNORMAL_RESPONSE,"请求重发短信失败");
			}
			smsLog = smsLogs.get(0);
			if(smsLog == null){
				return new ResultVo(Constants.ABNORMAL_RESPONSE,"请求重发短信不存在");
			}
			/**
			 * 调用发送短信接口
			 */
			
			JSONObject 	resp=sendSms(support.QRCODE_SMSURL,smsLog);  
			LogCvt.info("连接："+support.QRCODE_SMSURL);
			LogCvt.info("接收："+resp.toJSONString());
		 	if( resp != null && Constants.NORMAL_RESPONSE.equals(resp.get("returnCode").toString())){
		 		
		 		SmsLog sms = new SmsLog();
				sms.setIsSuccess(true);
				sms.setId(smsLog.getId());
				sms.setClientId(smsLog.getClientId());
				Boolean b  = smsLogLogic.updateSmsLog(sms);
				if(!b){
					LogCvt.error("更新短信日志DB ERROR,smslog.id="+smsLog.getId());
					return new ResultVo(Constants.ABNORMAL_RESPONSE,"更新短信状态失败");
				}
				return new ResultVo(Constants.NORMAL_RESPONSE,"短信发送成功");
			 }else{
				return new ResultVo(Constants.ABNORMAL_RESPONSE,"请求重发短信失败");
			 }
			
			
		} catch (Exception e) {
			LogCvt.error("请求重发短信失败 DB ERROR", e);
			return new ResultVo(Constants.ABNORMAL_RESPONSE,"请求重发短信失败");
		}
    }

	/**
	 *  第三方短信发送
	  * @Title: saveSmsLog
	  * @Description: TODO
	  * @author: share 2015年3月31日
	  * @modify: share 2015年3月31日
	  * @param @param smsMessageVo
	  * @param @param smsContent
	  * @param @return    
	  * @return SmsLog    
	  * @throws
	 */
	private JSONObject sendSms(String sendMsgUrl,SmsLog smsLog){
		JSONObject 	resp;
			JSONObject jsonObject = new JSONObject(); 
		   JSONArray jsonArray = new JSONArray();   
		   jsonArray.add(0, smsLog.getMobile()); 
		   jsonObject.put("mobiles", jsonArray); //手机号码
		   jsonObject.put("type", "SMS");
		   jsonObject.put("smsType", SmsType.getBizType(smsLog.getSmsType()));
		   jsonObject.put("templateId", 0);//
		   jsonObject.put("bizCode", support.BizCode(smsLog.getClientId()));//短信业务编码
		   jsonObject.put("bizId",  smsLog.getId());
		   jsonObject.put("merchantCode", "802");
		   Map<String, String> map = new HashMap<String, String>();
		   map.put("value", smsLog.getContent());
		   jsonObject.put("data", map);
		   LogCvt.info("短信发送数据："+jsonObject.toJSONString());
//			-------------------------连接第三方接口发送短信------------------------------
		   resp= HttpClientUtil.post(sendMsgUrl, jsonObject);
		   LogCvt.info("接收："+resp.toJSONString());
			//--------测试用begin--------------------------
//			resp= new  JSONObject();
//			resp.put("returnCode",Constants.NORMAL_RESPONSE);
			//--------测试用end--------------------------
			return resp;
		   
		   
	}

	@Override
	public ResultVo verifyMobileAndToken(String token, String code, String mobile)
			throws TException {
		if(StringUtils.isBlank(token) || StringUtils.isBlank(code)||StringUtils.isBlank(mobile)){
			return new ResultVo(Constants.ABNORMAL_RESPONSE,"请求参数为空");
		}
		SmsLog smsLog = new SmsLog();
		smsLog.setToken(token);
		smsLog.setMobile(mobile);
		List<SmsLog> smsLogs;
		try { 
			smsLogs = smsLogLogic.findSmsLog(smsLog,"");
		} catch (Exception e) {
			LogCvt.error("获取验证短信日志DB ERROR", e);
			return new ResultVo(Constants.ABNORMAL_RESPONSE,"校验失败");
		}
		if(smsLogs == null || smsLogs.size() != 1){
			LogCvt.info("验证token: " + token);
			return new ResultVo(Constants.ABNORMAL_RESPONSE,"校验失败");
		}
		smsLog = smsLogs.get(0);
		
		if(smsLog.getIsUsed()){
			LogCvt.info("该验证码已校验 token: " + token);
			return new ResultVo(Constants.ABNORMAL_RESPONSE,"该验证码已校验");
		}
		String saveCode = smsLog.getCode();
		if(new Date().before(smsLog.getExpireTime())){
			if(!saveCode.equalsIgnoreCase(code)){
				return new ResultVo(Constants.ABNORMAL_RESPONSE,"验证码错误");
			}
			SmsLog temp = new SmsLog();
			temp.setId(smsLog.getId());
			temp.setIsUsed(true);
			temp.setIsSuccess(smsLog.getIsSuccess());
			try {
			 if(!smsLogLogic.updateSmsLog(temp)){
					return new ResultVo(Constants.ABNORMAL_RESPONSE,"校验短信失败");
			 }
			 return new ResultVo(Constants.NORMAL_RESPONSE,"验证成功");
			} catch (Exception e) {
				LogCvt.error("修改短信日志状态DB ERROR,token:" + token +e.getMessage(), e);
				return new ResultVo(Constants.ABNORMAL_RESPONSE,"校验短信失败");
			}
		}
			return new ResultVo(Constants.ABNORMAL_RESPONSE,"验证失败，验证码已过期");
		
	}
	

}
