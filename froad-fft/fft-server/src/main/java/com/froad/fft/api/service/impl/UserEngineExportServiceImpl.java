package com.froad.fft.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.api.service.UserEngineExportService;
import com.froad.fft.bean.FroadException;
import com.froad.fft.common.AppException;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.entity.Member;
import com.froad.fft.persistent.entity.RegisterGivePointRule;
import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.service.SmsContentService;
import com.froad.fft.service.SysClientService;
import com.froad.fft.service.impl.MemberServiceImpl;
import com.froad.fft.service.impl.RegisterGivePointRuleServiceImpl;
import com.froad.fft.thirdparty.common.PointsCommand;
import com.froad.fft.thirdparty.dto.request.points.PointsAccount;
import com.froad.fft.thirdparty.dto.request.points.PointsReq;
import com.froad.fft.thirdparty.dto.request.points.PointsRes;
import com.froad.fft.thirdparty.request.points.impl.PointsFuncImpl;
import com.froad.fft.thirdparty.request.userengine.impl.UserEngineFuncImpl;
import com.froad.fft.util.EncryptSensInfoUtil;
import com.froad.fft.util.NullValueCheckUtil;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.CreateChannel;
import com.pay.user.helper.MemberType;

/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: UserEngineExportServiceImpl.java </p>
 *<p> 描述: *-- <b>实现用户引擎dto接口</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年3月28日 下午5:17:21 </p>
 ********************************************************
 */
public class UserEngineExportServiceImpl implements UserEngineExportService {

	private static Logger logger = Logger.getLogger(UserEngineExportServiceImpl.class);
	
	@Resource
	private UserEngineFuncImpl userEngineFuncImpl;
	
	@Resource
	private MemberServiceImpl memberServiceImpl;
	
	@Resource
	private PointsFuncImpl pointsFuncImpl;
	
	@Resource(name="smsContentServiceImpl")
	private SmsContentService smsContentService;
	
	@Resource
	private SysClientService sysClientService;
	
	@Resource
	private RegisterGivePointRuleServiceImpl registerGivePointRuleServiceImpl;
	
	//将客户端Dto 转换成用户系统接受的Dto
	private UserSpecDto toUserSpecDto(UserEngineDto userEngineDto){
		UserSpecDto userSpecDto = new UserSpecDto();
		userSpecDto.setLoginID(userEngineDto.getLoginID());
		userSpecDto.setEmail(userEngineDto.getEmail());
		userSpecDto.setLoginPwd(userEngineDto.getLoginPwd());
		userSpecDto.setStatus(userEngineDto.getStatus());
		userSpecDto.setMemberCode(userEngineDto.getMemberCode());
		userSpecDto.setRegisterIP(userEngineDto.getRegisterIP());
		userSpecDto.setMobile(userEngineDto.getMobile());
		userSpecDto.setCreateChannel(userEngineDto.getCreateChannel());
		userSpecDto.setUname(userEngineDto.getUname());
		userSpecDto.setIntroducer(userEngineDto.getIntroducer());
		return userSpecDto;
	}
	
	private UserEngineDto toUserEngineDto(UserSpecDto userSpecDto,UserResult userResult){
		if(userSpecDto == null){
			userSpecDto = new UserSpecDto();
		}
		if(userResult == null){
			userResult = new UserResult();
		}
		UserEngineDto userEngineDto = new UserEngineDto
		(
				userSpecDto.getMemberCode(), userSpecDto.getLoginID(), userSpecDto.getLoginPwd(),
				userSpecDto.getEmail(), userSpecDto.getStatus(), userSpecDto.getRegisterIP(), 
				userSpecDto.getMobile(), userSpecDto.getCreateChannel(), userResult.getResult(), userResult.getMsgCode(),
				userResult.getErrorMsg(), userResult.getDemo(),userSpecDto.getUname(),userSpecDto.getIntroducer()
		);
		userEngineDto.setMobileEncrypt(EncryptSensInfoUtil.encryptPhoneNum(userEngineDto.getMobile()));//将手机号码加密
		return userEngineDto;
	}
	
	@Override
	public UserEngineDto registerUser(ClientAccessType clientAccessType, ClientVersion clientVersion,UserEngineDto userEngineDto)throws FroadException {
		
		UserSpecDto userSpecDto = toUserSpecDto(userEngineDto);
		if(NullValueCheckUtil.isStrEmpty(userSpecDto.getEmail())){
			userSpecDto.setEmail(null);
		}
		userSpecDto.setMemberType(MemberType.PERSONAL.getValue());
		
		//------------------------------------------------进行注册渠道辨认----begin
		if(ClientAccessType.chongqing.equals(clientAccessType)){
			logger.info("注册渠道来源于CQ-WEB");
			userSpecDto.setCreateChannel
			(
			NullValueCheckUtil.isStrEmpty(userSpecDto.getCreateChannel()) ? CreateChannel.CQ_PRE_SALE.getValue() : userSpecDto.getCreateChannel()
			);
		}
		//------------------------------------------------进行注册渠道辨认----over
		
		
		userSpecDto.setIsBindMobile(true); //设置该用户的手机号码已绑定
		UserResult userResult = userEngineFuncImpl.registerMember(userSpecDto);
		
		//如果注册时账户账务返回失败
		if(userResult.getResult()){
			logger.info("注册用户成功");
			userSpecDto = userResult.getUserList().get(0);  //将返回的User取出放入对象
			Member member = new Member();
			member.setMemberCode(userSpecDto.getMemberCode());
			member.setUsername(userSpecDto.getLoginID());
			member.setMobile(userSpecDto.getMobile());
			Long id = memberServiceImpl.saveMember(member);
			
			logger.info("开始调用points平台进行送分操作实现以达到创建积分账户操作");
			SysClient sysClient = sysClientService.findSysClientByNumber(clientAccessType.getCode());
			RegisterGivePointRule registerGivePointRule = registerGivePointRuleServiceImpl.selectRegisterGivePointRuleByClientId(sysClient.getId());
			
			
			Double points = 0.0;
			if(registerGivePointRule != null){
				if(registerGivePointRule.getIsEnable()){
					Date timeBegin = registerGivePointRule.getBegineTime();
					Date timeEnd = registerGivePointRule.getEndTime();
					Date timeCurr = new Date();
					if(!(timeCurr.before(timeBegin) || timeCurr.after(timeEnd))){ //规则生效
						points = (registerGivePointRule.getGivePoint() != null ? Double.parseDouble(registerGivePointRule.getGivePoint()) : 0.0);
					}
				}
			}
			logger.info("注册用户赠送积分：" + points.toString());
			PointsReq req = new PointsReq(
					PointsCommand.FROAD_ORG_NO, 
					userSpecDto.getLoginID(), 
					PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME,
					"",
					clientAccessType.getDescribe() + "注册开通积分账户",
					"1",
					"开通积分账户", 
					points.toString(),
					sysClient.getPartnerId()
					);
			try {
				PointsRes pointsRes = pointsFuncImpl.donatePoints(req);
				logger.info("开通积分账户结果：" + JSONObject.toJSONString(pointsRes));
			} catch (AppException e) {
				logger.info("开通积分账户异常：",e);
			}
			
			logger.info("为该用户保存本地信息 返回的Id " + id);
			userEngineDto = toUserEngineDto(userSpecDto, userResult);
			return queryUserPoints(userEngineDto, clientAccessType, sysClient);
		}else{
			logger.info("注册用户失败 相关描述：" + JSONObject.toJSONString(userResult));
			return toUserEngineDto(new UserSpecDto(), userResult);
		}
	}

	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>查询用户积分信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月22日 下午6:48:39 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	private UserEngineDto queryUserPoints(UserEngineDto userEngineDto,ClientAccessType clientAccessType,SysClient sysClient){
	
		PointsRes pointsRes;
		List<PointsAccount> pointsAccounts = new ArrayList<PointsAccount>();
		try {
			pointsRes = pointsFuncImpl.queryParAccountPoints(new PointsReq(
					null,
					userEngineDto.getLoginID(),
					PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME,
					sysClient.getPartnerId()
					)
			);
		} catch (AppException e) {
			logger.info("查询用户积分信息失败",e);
			return userEngineDto;
		}
		
		pointsAccounts = pointsRes.getAccountPointsInfoList();
		if(!NullValueCheckUtil.isListArrayEmpty(pointsAccounts)){
			for (PointsAccount pointsAccount : pointsAccounts) {
				if(PointsCommand.FROAD_ORG_NO.equals(pointsAccount.getOrgNo())){//获取方付通积分
					userEngineDto.setFroadPoints(pointsAccount.getPoints());
					continue;
				}
				//TODO : 还需要遍历对应的银行积分
			}
		}
		return userEngineDto;
	}
	
	
	@Override
	public UserEngineDto login(ClientAccessType clientAccessType, ClientVersion clientVersion,String loginID, String loginPWD, String loginIP)throws FroadException {
		SysClient sysClient = sysClientService.findSysClientByNumber(clientAccessType.getCode());
		UserResult userResult = userEngineFuncImpl.login(loginID, loginPWD, loginIP);
		if(userResult.getResult()){
			UserEngineDto userEngineDto = toUserEngineDto(userResult.getUserList().get(0),userResult);
			return queryUserPoints(userEngineDto, clientAccessType, sysClient);
		}
		return toUserEngineDto(null, userResult);
	}

	@Override
	public UserEngineDto queryByLoginID(ClientAccessType clientAccessType,ClientVersion clientVersion, String loginID)throws FroadException {
		UserResult userResult = userEngineFuncImpl.queryByLoginID(loginID);
		if(!NullValueCheckUtil.isListArrayEmpty(userResult.getUserList())){
			if(userResult.getUserList()!=null&&userResult.getUserList().size()==1){
				return toUserEngineDto(userResult.getUserList().get(0), userResult);
			}
		}
		return toUserEngineDto(null, userResult);
	}

	@Override
	public UserEngineDto updatePwd(ClientAccessType clientAccessType,ClientVersion clientVersion, Long memberCode, String oldPwd,String newPwd)throws FroadException {
		return toUserEngineDto(null, userEngineFuncImpl.updatePwd(memberCode, oldPwd, newPwd));
	}

	@Override
	public UserEngineDto updateMemberInfo(ClientAccessType clientAccessType,ClientVersion clientVersion, UserEngineDto userEngineDto)throws FroadException {
		return toUserEngineDto(null,userEngineFuncImpl.updateMemberInfo(toUserSpecDto(userEngineDto)));
	}

	@Override
	public UserEngineDto updateUserPoints(ClientAccessType clientAccessType,ClientVersion clientVersion, String loginID) {
		SysClient sysClient = sysClientService.findSysClientByNumber(clientAccessType.getCode());
		UserEngineDto userEngineDto = queryByLoginID(clientAccessType,clientVersion, loginID);
		return queryUserPoints(userEngineDto, clientAccessType, sysClient);
	}

	@Override
	public UserEngineDto resetUserPwd(ClientAccessType clientAccessType,ClientVersion version10, Long memCode, String password) {
		return toUserEngineDto(null, userEngineFuncImpl.resetUserPwd(memCode,password));
	}

	@Override
	public UserEngineDto updateUserMobile(ClientAccessType clientAccessType,ClientVersion clientVersion, Long memCode, String mobile) {
		return toUserEngineDto(null, userEngineFuncImpl.bindMobile(memCode, mobile));
	}


}
