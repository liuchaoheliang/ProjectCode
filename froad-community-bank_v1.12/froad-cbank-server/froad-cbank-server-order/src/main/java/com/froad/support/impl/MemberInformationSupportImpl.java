
package com.froad.support.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.common.beans.ResultBean;
import com.froad.enums.PaymentChannel;
import com.froad.enums.PointsType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.po.ClientPaymentChannel;
import com.froad.support.MemberInformationSupport;
import com.froad.support.RedisDataAccessSupport;
import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.common.PointsCommand;
import com.froad.thirdparty.common.RespCodeCommand;
import com.froad.thirdparty.common.ThirdPartyConfig;
import com.froad.thirdparty.dto.request.openapi.CreateMobileTokenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.CheckType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.Client;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.points.PointsAccount;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.request.userengine.Node;
import com.froad.thirdparty.dto.response.pe.MemberInfo;
import com.froad.thirdparty.dto.response.pe.MemberPointsInfo;
import com.froad.thirdparty.dto.response.pe.MemberVipInfo;
import com.froad.thirdparty.dto.response.pe.QueryProtocolDto;
import com.froad.thirdparty.dto.response.pe.UserEngineDto;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.thirdparty.enums.MobileTokenType;
import com.froad.thirdparty.enums.ProtocolType;
import com.froad.thirdparty.exception.AppException;
import com.froad.thirdparty.request.openapi.OpenApiFunc;
import com.froad.thirdparty.request.openapi.impl.OpenApiFuncImpl;
import com.froad.thirdparty.request.points.PointsApiFunc;
import com.froad.thirdparty.request.points.impl.PointsApiFuncImpl;
import com.froad.thirdparty.request.userengine.UserEngineFunc;
import com.froad.thirdparty.request.userengine.impl.UserEngineFuncImpl;
import com.froad.thirdparty.util.DateUtil;
import com.froad.thirdparty.util.EncryptSensInfoUtil;
import com.froad.thirdparty.util.NullValueCheckUtil;
import com.froad.thirdparty.util.ThreeDES;
import com.froad.util.Arith;
import com.froad.util.BeanUtil;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.dto.VIPSpecDto;

public class MemberInformationSupportImpl implements MemberInformationSupport {

    private static final PointsApiFunc pointsApiFunc = new PointsApiFuncImpl();

    private static final UserEngineFunc userEngineFunc = new UserEngineFuncImpl();

    private static final RedisDataAccessSupport redis = new RedisDataAccessSupportImpl();

    private static final OpenApiFunc openApiFunc = new OpenApiFuncImpl();

    @Override
    public ResultBean queryByMemberCode(long memberCode,String clientId) {
        UserResult userResult = userEngineFunc.queryByMemberCode(memberCode);
        if (userResult.getResult()) {// 请求成功
        	if(userResult.getUserList() != null && userResult.getUserList().size() != 0){
        		return new ResultBean(ResultCode.success, BeanUtil.copyProperties(MemberInfo.class, userResult.getUserList().get(0)));
        	}
        	return new ResultBean(ResultCode.failed,"用户不存在");
        } else {
            return new ResultBean(ResultCode.failed, userResult.getErrorMsg());
        }
    }

    @Override
    public ResultBean queryVIPInfoByMemberCode(long memberCode,String clientId) {
        Result result = userEngineFunc.queryVIPInfoByMemberCode(memberCode,clientId);
        if (result.getResult()) {
        	MemberVipInfo mVip = new MemberVipInfo();
        	List<VIPSpecDto> vip = (List<VIPSpecDto>) result.getData();
        	if(vip == null || vip.size() != 1){
        		mVip = null;
        	}else{
        		VIPSpecDto vDto = vip.get(0);
        		mVip.setBankLabelName(vDto.getBankLabelName());
        		mVip.setMemberCode(vDto.getMemberCode());
        		mVip.setBankOrgNo(vDto.getBankOrgNo());
        		mVip.setVipLevel(vDto.getVipLevel().getValue());
        		mVip.setVipStatus(vDto.getVipStatus().getValue());
        		mVip.setVipExpiratioinTime(vDto.getVipExpirationTime());
        		mVip.setAvailableDays(vDto.getAvailableDays());
        	}
        	return new ResultBean(ResultCode.success, "查询成功",mVip);
        } else {
            return new ResultBean(ResultCode.failed, result.getMessage());
        }
    }

    @Override
    public ResultBean queryByLoginID(String loginID) {
        UserResult userResult = userEngineFunc.queryByLoginID(loginID);
        if (userResult.getResult()) {// 请求成功
        	if(userResult.getUserList() != null && userResult.getUserList().size() != 0){
        		return new ResultBean(ResultCode.success, BeanUtil.copyProperties(MemberInfo.class, userResult.getUserList().get(0)));
        	}
        	return new ResultBean(ResultCode.failed,"用户不存在");
        } else {
            return new ResultBean(ResultCode.failed, userResult.getErrorMsg());
        }
    }

    @Override
    public ResultBean queryUserPoints(String clientId, String loginID) {
        PointsReq req = new PointsReq(null, loginID, PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME, redis.acquirePointPartnerNo(clientId));

        PointsRes res = pointsApiFunc.queryParAccountPoints(req);
        if (PointsCommand.RESP_CODE_SUCCESS.equals(res.getResultCode())) {
            List<PointsAccount> pointsAccounts = res.getAccountPointsInfoList();
            String pointsOrgNo = redis.acquirePaymentOrgNoByType(clientId, 2);
            boolean acquireBankPoints = true;
            if (StringUtils.isBlank(pointsOrgNo)) {
                acquireBankPoints = false;
            }

            MemberPointsInfo userPointsInfo = new MemberPointsInfo();
            if (pointsAccounts != null) {
                for (PointsAccount pointsAccount : pointsAccounts) { // 取出该用户所有积分信息中属于方付通积分和对应的平台银行积分
                    if (PointsCommand.FROAD_ORG_NO.equals(pointsAccount.getOrgNo())) { // 该积分为方付通积分
                        userPointsInfo.setFroadPoints(pointsAccount.getPoints());
                        userPointsInfo.setFroadPointsExchageRate(pointsAccount.getExchageRate());
                        userPointsInfo.setFroadOrgNo(pointsAccount.getOrgNo());
                        continue;
                    }

                    if (acquireBankPoints) {
                        if (pointsOrgNo.equals(pointsAccount.getOrgNo())) { // 得到对应平台银行积分
                            userPointsInfo.setBankPoints(pointsAccount.getPoints());
                            userPointsInfo.setBankPointsExchageRate(pointsAccount.getExchageRate());
                            userPointsInfo.setBankOrgNO(pointsAccount.getOrgNo());
                            continue;
                        }
                    }
                }
            }
            return new ResultBean(ResultCode.success, userPointsInfo);
        }
        return new ResultBean(ResultCode.failed, res.getResultDesc(),null);
    }

    @Override
    public ResultBean updateUserMobile(long memberCode, String mobile) {
        UserResult userResult = userEngineFunc.bindMobile(memberCode, mobile);
        return new ResultBean(ResultCode.success, toUserEngineDto(null, userResult));
    }
    
    


	/**
	 * 绑定邮箱
	 * @param memberCode
	 * @param email
	 * @return    
	 * @see com.froad.support.MemberInformationSupport#bindMail(java.lang.Long, java.lang.String)
	 */
	@Override
	public ResultBean bindMail(Long memberCode, String email) {
		UserResult userResult = userEngineFunc.bindMail(memberCode, email);
//		return new ResultBean(ResultCode.success, toUserEngineDto(null, userResult));
		return new ResultBean(userResult != null && userResult.getResult() ? ResultCode.success : ResultCode.failed, toUserEngineDto(null, userResult));
	}
	

	
	/**   
	 * 解绑会员手机
	 * @param memberCode
	 * @return    
	 * @see com.froad.support.MemberInformationSupport#unBindMobile(java.lang.Long)
	 */
	@Override
	public ResultBean unBindMobile(Long memberCode) {
		UserResult userResult = userEngineFunc.unBindMobile(memberCode);
//		return new ResultBean(ResultCode.success, toUserEngineDto(null, userResult));
		return new ResultBean(userResult != null && userResult.getResult() ? ResultCode.success : ResultCode.failed, toUserEngineDto(null, userResult));
	}

	
	/**   
	 * 解绑会员邮箱
	 * @param memberCode
	 * @return    
	 * @see com.froad.support.MemberInformationSupport#unBindMail(java.lang.Long)
	 */
	@Override
	public ResultBean unBindMail(Long memberCode) {
		UserResult userResult = userEngineFunc.unBindMail(memberCode);
//		return new ResultBean(ResultCode.success, toUserEngineDto(null, userResult));
		return new ResultBean(userResult != null && userResult.getResult() ? ResultCode.success : ResultCode.failed, toUserEngineDto(null, userResult));
	}

    private UserEngineDto toUserEngineDto(UserSpecDto userSpecDto, UserResult userResult) {
        if (userSpecDto == null) {
            userSpecDto = new UserSpecDto();
        }
        if (userResult == null) {
            userResult = new UserResult();
        }
        UserEngineDto userEngineDto = new UserEngineDto(userSpecDto.getMemberCode(), userSpecDto.getLoginID(), userSpecDto.getLoginPwd(), userSpecDto.getEmail(),
                userSpecDto.getStatus(), userSpecDto.getRegisterIP(), userSpecDto.getMobile(), userSpecDto.getCreateChannel(), userResult.getResult(), userResult.getMsgCode(),
                userResult.getErrorMsg(), userResult.getDemo(), userSpecDto.getUname(), userSpecDto.getIntroducer());
        userEngineDto.setMobileEncrypt(EncryptSensInfoUtil.encryptPhoneNum(userEngineDto.getMobile()));// 将手机号码加密
        return userEngineDto;
    }

    @Override
    public ResultBean sendSignBankCardPhoneToken(String clientId, String phone, String cardNo) {

        String easyPayGroupId = redis.acquirePaymentOrgNoByType(clientId, PaymentChannel.fast_pay.getCode());

        if (NullValueCheckUtil.isStrEmpty(easyPayGroupId)) {
            LogCvt.info("快捷认证操作未找到快捷支付PayOrg");
            return new ResultBean(ResultCode.failed, "快捷认证操作未找到快捷支付PayOrg");
        }

        CreateMobileTokenApiReq req = new CreateMobileTokenApiReq(cardNo, phone, MobileTokenType.SIGN, "由[" + redis.acquireClientName(clientId) + "]客户端发起的签约短信", easyPayGroupId,
                redis.acquireOpenAPIPartnerNo(clientId));
        LogCvt.info("开始进行openapi签约短信发送，请求：" + JSONObject.toJSONString(req));
        return sendfastPayMoblieToken(req);
    }

    @Override
    public ResultBean sendFastPayMoblieToken(String clientId, String bankCardNo, String phone, String remark) {
        String easyPayGroupId = redis.acquirePaymentOrgNoByType(clientId, PaymentChannel.fast_pay.getCode());

        if (NullValueCheckUtil.isStrEmpty(easyPayGroupId)) {
            LogCvt.info("快捷发送银行手机验证码操作未找到快捷支付 PayOrg");
            return new ResultBean(ResultCode.failed, "快捷认证操作未找到快捷支付PayOrg");
        }

        CreateMobileTokenApiReq req = new CreateMobileTokenApiReq(bankCardNo, phone, MobileTokenType.PAY, remark, easyPayGroupId, redis.acquireOpenAPIPartnerNo(clientId));
        LogCvt.info("准备进行openapi发送银行手机验证码 : " + JSONObject.toJSONString(req));
        return sendfastPayMoblieToken(req);
    }

    public ResultBean sendfastPayMoblieToken(CreateMobileTokenApiReq req) {
        try {
            OpenApiRes res = openApiFunc.fastPayMoblieToken(req);
            LogCvt.info("openapi发送银行手机验证码开始");
            if (RespCodeCommand.SUCCESS.equals(res.getResultCode())) {
                LogCvt.info("openapi发送银行手机验证码成功");
                return new ResultBean(ResultCode.success, "短信发送成功");
            } else {
                LogCvt.info("openapi发送银行手机验证码失败：" + JSONObject.toJSONString(res));
                return new ResultBean(ResultCode.failed, res.getResultDesc());
            }
        } catch (AppException e) {
            LogCvt.info("openapi发送银行手机验证码异常：", e);
            return new ResultBean(ResultCode.failed, "系统繁忙，请稍后重试");
        }
    }

    @Override
    public ResultBean validateFilmMobile(String clientId, String filmMobile) {

        String payOrg = redis.acquirePaymentOrgNoByType(clientId, PaymentChannel.foil_card.getCode());
        if (payOrg == null) {
            LogCvt.error("资金渠道为空，客户端编号为：" + clientId + " 支付渠道：" + PaymentChannel.foil_card);
            return new ResultBean(ResultCode.failed, "支付渠道对应的资金渠道为空");
        }
        if (StringUtils.isEmpty(filmMobile)) {
            return new ResultBean(ResultCode.failed, "贴膜卡手机号为空");
        }
        OpenApiReq req = new OpenApiReq(payOrg, CheckType.ACCOUNT_MOBILE, filmMobile, null, redis.acquireOpenAPIPartnerNo(clientId));
        try {
            OpenApiRes res = openApiFunc.accountCheck(req);
            if (OpenApiCommand.SUCCESS.equals(res.getCheckResultCode())) {
                return new ResultBean(ResultCode.success, "成功");
            }
            return new ResultBean(ResultCode.failed, "该手机号不是有效的贴膜卡手机号");
        } catch (AppException e) {
            LogCvt.error("调用openapi账户校验接口出现异常", e);
            return new ResultBean(ResultCode.failed, "账户校验接口异常");
        }
    }

    @Override
    public ResultBean queryPointsTradeHistory(String clientId, String userName, Date fromTime, Date toTime, ProtocolType protocolType, PointsType pointsType, Integer pageSize,
            Integer pageNum) {
        QueryProtocolDto protocolDto = new QueryProtocolDto();
        try {
            String bankOrgNo = null;
            switch (pointsType) {
            case bankPoints:
                bankOrgNo = redis.acquirePaymentOrgNoByType(clientId, PaymentChannel.bank_point.getCode());
                break;
            case froadPoints:
                bankOrgNo = redis.acquirePaymentOrgNoByType(clientId, PaymentChannel.froad_point.getCode());
            default:
                break;
            }
            String fromTimeStr, toTimeStr;
            if (fromTime == null) {
                fromTimeStr = "";
            } else {
                fromTimeStr = DateUtil.formatDate2Str(fromTime, DateUtil.C_TIME_PATTON_DEFAULT);
            }
            if (toTime == null) {
                toTimeStr = "";
            } else {
                toTimeStr = DateUtil.formatDate2Str(toTime, DateUtil.C_TIME_PATTON_DEFAULT);
            }

            PointsReq pointsReq = new PointsReq(redis.acquirePointPartnerNo(clientId), bankOrgNo, userName, "", protocolType, pageSize, pageNum, fromTimeStr, toTimeStr);

            PointsRes pointsRes = pointsApiFunc.getMyPointsTrans(pointsReq);

            LogCvt.info("用户消费积分分页查询，响应结果码：" + pointsRes.getResultCode() + ",描述：" + pointsRes.getResultDesc());
            if (RespCodeCommand.SUCCESS.equals(pointsRes.getResultCode())) {
                protocolDto.setPointInfos(pointsRes.getProtocolInfos());
                protocolDto.setQueryInfo(pointsRes.getQueryInfo());
                protocolDto.setTotalPointsInfosDtos(pointsRes.getTotalPointsInfos());
//                if(pointsRes.getProtocolInfos()!=null){
//                	String[] paymentIds = new String[protocolDto.getPointInfos().size()];
//	                DBObject cond = new BasicDBObject();
//	                DBObject ids = new BasicDBObject();
//	                ids.put("$in", paymentIds);
//	                cond.put("payment_id",ids);
//	                
//	                for(int i=0;i<paymentIds.length;i++){
//	                	paymentIds[i] = protocolDto.getPointInfos().get(i).getObjectNo();
//	                }
//	                List<PaymentMongoEntity> pmes = (List<PaymentMongoEntity>) mongo.findAll(cond, MongoTableName.CB_PAYMENT, PaymentMongoEntity.class);
//	                Map<String,String> orderIDMap = new HashMap<String,String>();
//	                
//	                for(PaymentMongoEntity pe : pmes){
//	                	orderIDMap.put(pe.getPayment_id(), pe.getOrder_id());
//	                }
//	                for(int i=0;i<paymentIds.length;i++){
//	                	protocolDto.getPointInfos().get(i).setOrderId(orderIDMap.get(paymentIds[i]));
//	                }
//	                
//                }
                
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
        return new ResultBean(ResultCode.success, protocolDto);
    }

    @Override
    public ResultBean sendExchangeCheckCode(String clientId, String mobile, String points) {
        PointsReq pointsReq = new PointsReq();
        pointsReq.setAccountMarked(mobile);
        pointsReq.setOrgNo(redis.acquirePaymentOrgNoByType(clientId, PaymentChannel.bank_point.getCode()));
        pointsReq.setPoints(points);
        pointsReq.setPartnerNo(redis.acquirePointPartnerNo(clientId));
        String msg;
        try {
            PointsRes res = pointsApiFunc.sendCheckCode(pointsReq);
            if (RespCodeCommand.SUCCESS.equals(res.getResultCode())) {
                return new ResultBean(ResultCode.success, "成功");
            }
            msg = res.getResultDesc();
        } catch (AppException e) {
            LogCvt.error("发送消费积分的验证码异常，手机号：" + mobile, e);
            msg = e.getMessage();
        }
        return new ResultBean(ResultCode.failed, msg);
    }

    @Override
    public ResultBean queryBankPointsByMobile(String clientId, String mobile) {
        PointsReq pointsReq = new PointsReq();
        pointsReq.setMobileNum(mobile);
        pointsReq.setOrgNo(redis.acquirePaymentOrgNoByType(clientId, PaymentChannel.bank_point.getCode()));
        pointsReq.setPartnerNo(redis.acquirePointPartnerNo(clientId));
        String msg;
        try {
            PointsRes res = pointsApiFunc.queryBankPointsByMobile(pointsReq);
            if (RespCodeCommand.SUCCESS.equals(res.getResultCode())) {
                String points = res.getPointsAccount().getPoints();
                return new ResultBean(ResultCode.success, "积分查询成功", points);
            }
            if (pointsReq.getPartnerNo() != null && pointsReq.getPartnerNo().equals(PointsCommand.POINTS_PARTENER_NO_AN_HUI_NONG_XIN) && res.getResultCode().equals("4001")) {// 安徽农信
                msg = "没有查到对应的积分账户";
            } else {
                msg = res.getResultDesc();
            }
        } catch (AppException e) {
            LogCvt.error("银行积分查询异常，手机号：" + mobile, e);
            msg = e.getMessage();
        }
        return new ResultBean(ResultCode.failed, msg);
    }

    @Override
    public ResultBean queryBankPointsByBankCardNo(String clientId, String bankNo) {
        String bankOrgNo = redis.acquirePaymentOrgNoByType(clientId,PaymentChannel.bank_point.getCode());
        String pointPartner = redis.acquirePointPartnerNo(clientId);
        if (StringUtils.isEmpty(bankOrgNo)) {
            return new ResultBean(ResultCode.failed, "未获取到当前客户端下的银行机构号");
        }
        PointsReq req = new PointsReq();
        req.setOrgNo(bankOrgNo);
        req.setMobileNum(bankNo);
        req.setPartnerNo(pointPartner);
        try {
            PointsRes res = pointsApiFunc.queryBankPointsByMobile(req);
            if (RespCodeCommand.SUCCESS.equals(res.getResultCode())) {
                return new ResultBean(ResultCode.success, res);
            } else {
                return new ResultBean(ResultCode.failed, res.getResultDesc(), res);
            }
        } catch (Exception e) {
            LogCvt.error("通过卡号查询银行积分异常", e);
            return new ResultBean(ResultCode.failed);
        }
    }

    @Override
    public ResultBean payPointsByMobile(String clientId,String orderId,String payReason,String remark,String outletOrgNo,String loginId,int points,String bankNo) {
    	PointsReq request= new PointsReq();
        String bankOrgNo = redis.acquirePaymentOrgNoByType(clientId,PaymentChannel.bank_point.getCode());
        String pointPartner = redis.acquirePointPartnerNo(clientId);
        String rateValue = redis.acquirePointsRate(clientId);
    	if(rateValue==null||!rateValue.matches("\\d+")){
			List<ClientPaymentChannel> payChannel = BaseSubassembly.acquireClientPaymentChannelFromRedis(clientId);
			if(payChannel==null||payChannel.size()==0){
				ResultBean rb = this.queryUserPoints(clientId, loginId);
				if(rb.isSuccess()){
					MemberPointsInfo pointsInfo = (MemberPointsInfo) rb.getData();
					rateValue = pointsInfo.getBankPointsExchageRate();
				}
			}
    	}
    	
    	if(rateValue==null||!rateValue.matches("\\d+")){
			return new ResultBean(ResultCode.failed,"未找到积分兑换率");
		}
    	
        if (StringUtils.isEmpty(bankOrgNo)) {
            return new ResultBean(ResultCode.failed, "未获取到当前客户端下的银行机构号");
        }
		
        request.setAccountMarked(loginId);
        request.setOrgNo(bankOrgNo);
        request.setObjectNo(orderId);
        request.setObjectDes(payReason+"|"+remark);
        request.setPartnerNo(pointPartner);
        request.setPoints(String.valueOf(Arith.div(points, Integer.valueOf(rateValue))));
        request.setOrgPoints(String.valueOf(points));
        request.setBusinessType(payReason + "_ah_mobilepay_" + outletOrgNo);
        request.setMobileNum(bankNo);
//        request.setRemark(remark);
        request.setRemark(orderId);
        
        try {
            PointsRes res = pointsApiFunc.payPointsByMobile(request);
            if (RespCodeCommand.SUCCESS.equals(res.getResultCode())) {
                return new ResultBean(ResultCode.success, res);
            } else {
                return new ResultBean(ResultCode.failed, res.getResultDesc(), res);
            }
        } catch (Exception e) {
            LogCvt.error("通过手机号消费银行积分异常", e);
            return new ResultBean(ResultCode.failed);
        }
    }

    @Override
    public ResultBean queryMemberAndCardInfo(String clientId, String cardNo) {
        String bankOrgNo = redis.acquirePaymentOrgNoByType(clientId,PaymentChannel.fast_pay.getCode());
        UserResult result = userEngineFunc.queryMemberAndCardInfo(cardNo, bankOrgNo);
        if (result.getResult()) {
            return new ResultBean(ResultCode.success, result);
        } else {
            return new ResultBean(ResultCode.failed, result.getErrorMsg());
        }
    }

	@Override
	public ResultBean registerMember(MemberInfo memberInfo) {
		UserSpecDto userSpecDto = (UserSpecDto) BeanUtil.copyProperties(UserSpecDto.class, memberInfo);
		UserResult result = userEngineFunc.registerMember(userSpecDto);
        if (result.getResult()) {
            return new ResultBean(ResultCode.success, "注册成功",result);
        } else {
            return new ResultBean(ResultCode.failed, result.getErrorMsg());
        }
	}
	
	@Override
	public ResultBean processVIPOrder(Long memeberCode, String level, Integer availableDays, Long labelID, Integer bankLevel){
//		VIPLevel levelEnum = VIPLevel.getInstanceByValue(level);
//		Result result = userEngineFunc.processVIPOrder(memeberCode, levelEnum, availableDays, labelID, bankLevel);
//		MemberVipInfo vip = new MemberVipInfo();
//		if(result.isResult()){
//			VIPDto dto = (VIPDto) result.getData();
//			vip.setMemberCode(memeberCode);
//			vip.setOrgCode(dto.getOrgCode());
//			vip.setVipActivationTime(dto.getVipActivationTime());
//			vip.setVipExpiratioinTime(dto.getVipExpirationTime());
//			if(dto.getVipLevel()!=null)
//			vip.setVipLevel(com.froad.thirdparty.dto.response.pe.MemberVipInfo.VIPLevel.getInstanceByValue(level));
//			if(dto.getVipStatus()!=null)
//			vip.setVipStatus(VIPStatus.getInstanceByValue(dto.getVipStatus().getValue()));
//			return new ResultBean(ResultCode.success,"购买VIP成功",vip);
//		}else{
//			return new ResultBean(ResultCode.failed,"购买VIP失败："+result.getMessage());
//		}
		return null;
	}

	@Override
	public ResultBean queryMemberVIPLabelByClientID(String clientId){
		Long bankId = ThirdPartyConfig.getPEBankLabelIDByClientID(clientId);
		if(bankId==null){
			return new ResultBean(ResultCode.failed,"未找到clientId对应的一级法人行社在用户中心的ID");
		}
		Result result = userEngineFunc.querySecondaryBankListByBankID(bankId);
		if(result.isResult()){
			List<Node> resultList = (List<Node>) BeanUtil.copyProperties(Node.class, result.getData());
			return new ResultBean(ResultCode.success,"查询成功",result);
		}else{
			return new ResultBean(ResultCode.failed,"查询失败："+result.getMessage());
		}
	}

	@Override
	public ResultBean employeeCodeVerify(String clientId, String verifyOrg, String employeeCode, String password,Client client) {
		OpenApiReq req = null;
		try {
			req = new OpenApiReq(verifyOrg, employeeCode, ThreeDES.encrypt(password,ThreeDES.VERIFY_KEY), client, BaseSubassembly.acquireOpenAPIPartnerNo(clientId));
			OpenApiRes res = openApiFunc.employeeCodeVerify(req);
			if(Const.SUCCESS_CODE.equals(res.getResultCode())){
				String content = ThreeDES.decrypt(res.getVerifyResultContent(), ThreeDES.VERIFY_KEY);
				Map<String, String> resultKV = (Map<String, String>)JSON.parse(content);
				if("00".equals(res.getVerifyResultCode())){
					return new ResultBean(ResultCode.success, content);
				}
				return new ResultBean(ResultCode.failed,resultKV.get("remark"));
			}
			return new ResultBean(ResultCode.failed,res.getResultDesc());
		} catch (Exception e) {
			LogCvt.error("登录验证异常", e);
			return new ResultBean(ResultCode.failed);
		}
		
	}

}
