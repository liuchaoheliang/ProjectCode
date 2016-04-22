package com.froad.cbank.coremodule.module.normal.user.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.enums.ClientChannelEnum;
import com.froad.cbank.coremodule.module.normal.user.pojo.UnionLoginPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.UserEnginePojo;
import com.froad.cbank.coremodule.module.normal.user.utils.DateUtils;
import com.froad.cbank.coremodule.module.normal.user.utils.PasswordProcessor;
import com.froad.cbank.coremodule.module.normal.user.utils.RSAUtils;
import com.froad.cbank.coremodule.module.normal.user.utils.TripleDesUtil;
import com.froad.cbank.coremodule.module.normal.user.utils.UnionLoginConstans;
import com.froad.cbank.coremodule.module.normal.user.vo.UnionLoginVo;
import com.pay.pe.helper.ErrorCodeType;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.BankOrg;
import com.pay.user.service.ThirdPartySpecService;

/**
 * @Description: 联合登陆
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年9月18日 下午4:53:14
 */
@Service
public class UnionLoginSupport extends BaseSupport {

	@Resource
	private ThirdPartySpecService thirdPartySpecService;
	
	@Resource
	private UserEngineSupport userEngineSupport;


	
	private final static SimpleDateFormat loginTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private final static String ULMobile = "p";
	private final static String ULIdentifyNo = "i";
	private final static String ULBankOrgNo = "b";
	private final static String ULCreateChannel = "c";
	private final static String ULLoginTime = "d";
	private final static String ULIdentifyType = "t";
	private final static String ULUserBankId = "u";
	
	
	
	/**
	 * 联合登陆信息解析
	 * @Description: 根据各银行客户端不同配置
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月18日 下午9:10:09
	 * @param pojo
	 * @param clientChannel
	 * @return
	 * @throws Exception
	 */
	public UnionLoginVo extractUnionLoginInfo(UnionLoginPojo pojo, ClientChannelEnum clientChannel) throws Exception{
		UnionLoginVo loginVo = new UnionLoginVo();
		String extractData = null;
		
		switch (clientChannel) {
		case anhui:
			/**
			 * 安徽联合登陆
			 * <br>标准版解密之后的信息为json串 <br>
			 * 
			 * <br>安徽农信IOS解密之后的信息：（以"_"分隔） <br>
			 * <br>Mobile_IdentifyNo_IdentifyType_CreateChanel_LoginTime <br>
			 *
			 *	<br>mobile:手机号 <br>
			 *	<br>userBankId:证件号 <br>
			 *	<br>IdentifyType:证件类型 <br>
			 *	<br>bankOrgNo:银行机构号 <br> 安徽0001
			 *	<br>createChannel:登录渠道 <br>
			 *	<br>loginTime:登录时间 <br>
			 */
			
			//1.RSA解密
			extractData = PasswordProcessor.decrypt(pojo.getMac());
			LogCvt.debug("解密结果 : " + extractData);
			
			//针对加密原文格式不同的处理
			try{
				//标准版 - json格式
				JSONObject loginJson = JSON.parseObject(extractData);
				loginVo.setMobile(loginJson.getString(ULMobile));
				loginVo.setIdentifyNo(loginJson.getString(ULIdentifyNo));
				loginVo.setIdentifyType(loginJson.getString(ULIdentifyType));
				loginVo.setBankOrgNo(loginJson.getString(ULBankOrgNo));
				loginVo.setCreateChannel(loginJson.getString(ULCreateChannel));
				loginVo.setLoginTime(loginJson.getString(ULLoginTime));
			}catch(JSONException e){
				//旧版 - 安徽客户端IOS版
				LogCvt.info("联合登录信息转换JSON失败，以安徽客户端IOS版的规则解析>>");
				String[] loginInfos = extractData.split("_");
				if(loginInfos.length == 7){
					loginVo.setMobile(loginInfos[0]);
					loginVo.setIdentifyNo(loginInfos[1]);
					loginVo.setIdentifyType(loginInfos[2]);
					loginVo.setBankOrgNo(BankOrg.BANK_AH.getBankOrg());//安徽农信银行机构号0001
					loginVo.setCreateChannel(loginInfos[3]+"_"+loginInfos[4]+"_"+loginInfos[5]);
					loginVo.setLoginTime(loginInfos[6]);
				}else{
					LogCvt.error(String.format("安徽联合登陆>> 登录信息解析失败：extractData:%s ", extractData));
					throw new Exception("联合登陆信息解析失败");
				}
				
			}
			
			break;

		case chongqing:
			/**
			 * 重庆联合登陆
			 * 旧版是明文传送信息，mac为null，直接从loginPojo中提取，并增加机构号
			 * 新版mac为密文，RSA解密后为json串
			 */
			//1.RSA解密
			extractData = PasswordProcessor.decrypt(pojo.getMac());
			LogCvt.debug("解密结果 :" + extractData);
					
			if(StringUtil.isBlank(extractData)){
				LogCvt.info("未获取到加密登录信息，以重庆旧版本客户端数据格式解析");
				loginVo.setBankOrgNo(BankOrg.BANK_CQ.getBankOrg());//重庆农商行银行机构号
				loginVo.setUserBankId(pojo.getUserBankId());
				loginVo.setMobile(pojo.getMobile());
				loginVo.setCreateChannel(pojo.getCreateChannel());
				loginVo.setLoginTime(pojo.getLoginTime());
			}else{
				//标准版 - json格式
				JSONObject loginJSONObject = JSON.parseObject(extractData);
				loginVo.setUserBankId(loginJSONObject.getString(ULUserBankId));
				loginVo.setMobile(loginJSONObject.getString(ULMobile));
				loginVo.setBankOrgNo(loginJSONObject.getString(ULBankOrgNo));
				loginVo.setCreateChannel(loginJSONObject.getString(ULCreateChannel));
				loginVo.setLoginTime(loginJSONObject.getString(ULLoginTime));
			}
			
			break;
			
		case taizhou:
	
			//解析json获取数字签名和加密串
			JSONObject tzJson = JSON.parseObject(pojo.getMac());
			String sign = tzJson.getString("paramSign");
			String encryptStr = tzJson.getString("encryptParamStr");
			
			//RSA验签
			boolean verify = RSAUtils.verify(encryptStr.getBytes("UTF-8"), UnionLoginConstans.TAIZHOU_UNIONLOGIN_RSAPUBKEY, sign);
			if(!verify){
				LogCvt.error(String.format("台州联合登陆>> 数据签名验签不通过：sign:%s | encryptStr:%s ", sign, encryptStr));
				throw new Exception("联合登陆验签失败");
			}
			
			//3DES解密
			extractData = TripleDesUtil.decrypt(encryptStr, UnionLoginConstans.TAIZHOU_UNIONLOGIN_3DESKEY);
			LogCvt.debug("解密结果 : " + extractData);
			
			//标准版 - json格式
			JSONObject loginJSONObject = JSON.parseObject(extractData);
			loginVo.setUserBankId(loginJSONObject.getString(ULUserBankId));
			loginVo.setMobile(loginJSONObject.getString(ULMobile));
			loginVo.setBankOrgNo(loginJSONObject.getString(ULBankOrgNo));
			loginVo.setCreateChannel(loginJSONObject.getString(ULCreateChannel));
			loginVo.setLoginTime(loginJSONObject.getString(ULLoginTime));

			break;
			
		default:
			LogCvt.error("联合登陆解析登录信息错误：未知渠道:" + clientChannel);
			break;
		}
		
		
		return loginVo;
	}
	
	
	
	
	
	/**
	 * 
	 * @Description: 客户端联合登陆逻辑控制
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月9日 上午10:45:06
	 * @param loginVo
	 * @param clientChannel
	 * @param loginIP
	 * @return
	 * @throws Exception
	 */
	public Result handlerUnionLoginLogic(UnionLoginVo loginVo, ClientChannelEnum clientChannel, String loginIP) throws Exception{
		
		Result result = null;
		
		switch (clientChannel) {
		case anhui:
			
			result = this.loginJoin(loginVo.getMobile(), loginVo.getIdentifyType(), loginVo.getIdentifyNo(), loginIP, loginVo.getCreateChannel(), loginVo.getBankOrgNo(), loginVo.getLoginTime());
			
			break;

		default:
			
			Date loginTime = loginTimeFormat.parse(loginVo.getLoginTime());
			
			result = this.unionLogin(loginVo.getUserBankId(), loginVo.getBankOrgNo(), loginVo.getMobile(), loginTime, loginVo.getCreateChannel(), loginIP);
			
			break;
		}
		
		
		return result;
	}
	
	
	/**
	 * 重构联合登录-登录信息解析
	 * 标准版数据格式json
	 * @param decryptMsg
	 * @param loginPojo
	 * @param clientId
	 * @param loginIP
	 * @return
	 * @throws ParseException 
	 */
	public Map<String,Object> standardUnionLoginLogic(UnionLoginPojo loginPojo, String decryptMsg, String clientId, String loginIP){
		
		Assert.hasText(decryptMsg, "加密信息为空");
		//标准版 - json格式
		JSONObject loginJSONObject = JSON.parseObject(decryptMsg);
		loginPojo.setUserBankId(loginJSONObject.getString(ULUserBankId));
		loginPojo.setMobile(loginJSONObject.getString(ULMobile));
		loginPojo.setBankOrgNo(loginJSONObject.getString(ULBankOrgNo));
		loginPojo.setCreateChannel(loginJSONObject.getString(ULCreateChannel));
		loginPojo.setLoginTime(loginJSONObject.getString(ULLoginTime));
		
		return refactorUnionLoginHandle(loginPojo, clientId, loginIP);

	}

	
	
	
	
	/**
	 * 重构联合登陆
	 * @param loginPojo
	 * @param clientId
	 * @return
	 */
	public Map<String, Object> refactorUnionLoginHandle(UnionLoginPojo loginPojo, String clientId, String loginIP){
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		LogCvt.info(String.format("联合登录信息解析完成>> \n %s", JSON.toJSONString(loginPojo,true)));
		
		Date loginTime = DateUtils.parseDateTime(loginPojo.getLoginTime());
		
		Result result = this.unionLogin(loginPojo.getUserBankId(), loginPojo.getBankOrgNo(), loginPojo.getMobile(), loginTime, loginPojo.getCreateChannel(), loginIP);
		
		if(result.getResult()){
			//登陆成功流程
			UserEnginePojo userEngineDto = userEngineSupport.UserSpecToUserEngineDto((UserSpecDto)result.getData(),clientId);
			LogCvt.info(String.format("用户联合登录成功>> 用户名:%s, 手机号 ：%s, 登录IP：%s", userEngineDto.getLoginId(), userEngineDto.getMobile(), loginIP));
			resMap.put("token",result.getToken());
			resMap.put("userAcct",userEngineDto);
			return resMap;
		}else{
			//登录失败流程
			if(ErrorCodeType.USER_BANK_ID_NOT_EXIST_BUT_MOBILE_EXIST.getValue().equals(result.getCode())){
				//银行用户ID不存在,但手机号已存在，返回手机号
				LogCvt.info(String.format("用户联合登陆失败>> 银行用户ID不存在,但手机号已存在;返回被绑定的手机号:%s", loginPojo.getMobile()));
				resMap.put(Results.code, ErrorCodeType.USER_BANK_ID_NOT_EXIST_BUT_MOBILE_EXIST.getValue()); 
				resMap.put(Results.msg, ErrorCodeType.USER_BANK_ID_NOT_EXIST_BUT_MOBILE_EXIST.getDesc());
				resMap.put("mobile", loginPojo.getMobile());
				return resMap;
			}else if(ErrorCodeType.USER_MOBILE_IS_BIND_UNION.getValue().equals(result.getCode())){
				//手机号已被绑定
				LogCvt.info("用户联合登陆失败>> 手机号已绑定手机银行联合账户");
				resMap.put(Results.code, ErrorCodeType.USER_MOBILE_IS_BIND_UNION.getValue());
				resMap.put(Results.msg, ErrorCodeType.USER_MOBILE_IS_BIND_UNION.getDesc());
				resMap.put("mobile", loginPojo.getMobile());
				return resMap;
			}else{
				resMap.put(Results.code, result.getCode());
				resMap.put(Results.msg,result.getMessage());
				return resMap;
			}
			
		}
	}
	
	
	

	
	/**
	 * 联合登陆(安徽)
	 *@description 
	 *@author Liebert
	 *@date 2015年6月5日 下午5:27:57
	 */
	public Result loginJoin(String mobile, String identifyType,String idnetifyNo, String loginIP, String createChannel,String bankOrg, String loginTime){
		Result result =	new Result(false);
		try{
			result = thirdPartySpecService.loginJoin(mobile, identifyType, idnetifyNo, loginIP, createChannel, bankOrg, loginTime);
		}catch(Exception e){
			LogCvt.error("联合登录接口异常", e);
			result.setResult(false);
			result.setCode(9999);
			result.setMessage("联合登录接口异常");
		}
		return result;
	}
	
	
	
	/**
	 * 联合登陆
	 *@description 
	 *@author Liebert
	 *@date 2015年4月7日 下午5:47:57
	 */
	public Result unionLogin(String userBankId, String bankOrgNo, String mobile, Date loginTime, String createChannel, String loginIP){
		Result result =	new Result(false);
		try{
			result = thirdPartySpecService.unionLogin(userBankId, bankOrgNo, mobile, loginTime, createChannel, loginIP);
		}catch(Exception e){
			LogCvt.error("联合登录接口异常", e);
			result.setResult(false);
			result.setCode(9999);
			result.setMessage("联合登录接口异常");
		}
		return result;
	}
	
	

	public Result unionLoginExceptMobile(String userBankId, String bankOrgNo, Date loginTime, String createChannel, String loginIP){
		Result result =	new Result(false);
		try{
			result = thirdPartySpecService.unionLoginExceptMobile(userBankId, bankOrgNo, loginTime, createChannel, loginIP);
		}catch(Exception e){
			LogCvt.error("无手机号联合登录接口异常", e);
			result.setResult(false);
			result.setCode(9999);
			result.setMessage("无手机号联合登录接口异常");
		}
		return result;
	}

	
	public Result unionLoginBind(String userBankId, String bankOrgNo, String mobile, Date loginTime, String createChannel, String loginIP){
		Result result =	new Result(false);
		try{
			result = thirdPartySpecService.unionLoginBind(userBankId, bankOrgNo, mobile, loginTime, createChannel, loginIP);
		}catch(Exception e){
			LogCvt.error("绑定已存在会员登录接口异常", e);
			result.setResult(false);
			result.setCode(9999);
			result.setMessage("绑定已存在会员登录接口异常");
		}
		return result;
	}
	
	public Result unionLoginUnbind(String userBankId, String bankOrgNo, String mobile, Date loginTime, String createChannel, String loginIP){
		Result result =	new Result(false);
		try{
			result = thirdPartySpecService.unionLoginUnbind(userBankId, bankOrgNo, mobile, loginTime, createChannel, loginIP);
		}catch(Exception e){
			LogCvt.error("解绑已存在会员登录接口异常", e);
			result.setResult(false);
			result.setCode(9999);
			result.setMessage("解绑已存在会员登录接口异常");
		}
		return result;
	}

	public Result isExistUnionMobile(String mobile){
		Result result =	new Result(false);
		try{
			result = thirdPartySpecService.isExistUnionMobile(mobile);
		}catch(Exception e){
			LogCvt.error("校验手机号接口异常", e);
			result.setResult(true);
			result.setCode(9999);
			result.setMessage("校验手机号接口异常");
		}
		return result;
	}
}
