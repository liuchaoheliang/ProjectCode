
package com.froad.logic.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.froad.common.beans.ResultBean;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.BankCardLogic;
import com.froad.support.BankCardSupport;
import com.froad.support.RedisDataAccessSupport;
import com.froad.support.impl.BankCardSupportImpl;
import com.froad.support.impl.RedisDataAccessSupportImpl;
import com.froad.thirdparty.common.PointsCommand;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.response.points.BindAccountInfo;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.thirdparty.request.points.PointsApiFunc;
import com.froad.thirdparty.request.points.impl.PointsApiFuncImpl;
import com.froad.thirdparty.request.userengine.UserEngineFunc;
import com.froad.thirdparty.request.userengine.impl.UserEngineFuncImpl;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.pay.user.dto.MemberBankSpecDto;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;

public class BankCardLogicImpl implements BankCardLogic {

    private static final BankCardSupport bankCardSupport = new BankCardSupportImpl();

    private static final RedisDataAccessSupport redis = new RedisDataAccessSupportImpl();
    
    private static final UserEngineFunc userEngineFunc = new UserEngineFuncImpl();
    
    private PointsApiFunc pointsApiFunc = new PointsApiFuncImpl();
    
    @Override
    public ResultBean selectSignedBankCardByClientId(String clientId,long memberCode) {
        UserResult userResult = bankCardSupport.getAllSignCardByMemberCode(clientId,memberCode);
        if (userResult.getResult()) {
            return new ResultBean(ResultCode.success, "查询成功", userResult.getMemberBankList());
        }
        return new ResultBean(ResultCode.failed, userResult.getErrorMsg());
    }

    
    @Override
	public ResultBean signBankCardByClientId(String clientId, long memberCode,String cardNo, String uname, String idcard, String phone,String singlePenLimit, String dayLimit, String monthLimit,String mobileToken,String pointCardNo) {
		if ("chongqing".equals(clientId)) { // 只签约opneapi pe
			// 暂时解决重庆的问题
			LogCvt.info("签约快捷银行卡: 请求OpenApi系统进行签约银行认证 ");
			OpenApiRes res = bankCardSupport.signBankCardByClientIdOnOpenAPI(clientId, memberCode, uname, cardNo, idcard, phone,mobileToken, singlePenLimit, dayLimit, monthLimit);
			if (isRequestSuccess(res.getResultCode())) {
				LogCvt.info("签约快捷银行卡: OpenApi签约通过，进行积分平台签约");
				UserResult userResult = userEngineFunc.queryByMemberCode(memberCode);
				if (userResult.getResult()) {
					LogCvt.info("签约快捷银行卡: 进行账户账务快捷信息保存");
					userResult = bankCardSupport.signCardOnUserEngine(clientId,memberCode, cardNo, uname, idcard, phone,res.getSignNo(), res.getSinglePenLimit(),res.getDailyLimit(), res.getMonthlyLimit());
					LogCvt.info("签约快捷银行卡: 进行账户快捷卡信息保存 , 请求结果: " + JSONObject.toJSONString(userResult));
					if (userResult.getResult()) {
						return new ResultBean(ResultCode.success, "签约认证成功");
					} else {
						LogCvt.info("签约快捷银行卡: 进行账户快捷卡信息保存失败:" + userResult.getErrorMsg());
						return new ResultBean(ResultCode.failed,userResult.getErrorMsg());
					}
				} else {
					LogCvt.error("签约快捷银行卡: 进行积分平台快捷积分签约未找到用户");
					return new ResultBean(ResultCode.failed,userResult.getErrorMsg());
				}
			} else {
				LogCvt.info("签约快捷银行卡:  请求OpenApi系统进行签约银行认证失败: "+ res.getResultDesc());
				return new ResultBean(ResultCode.failed, res.getResultDesc());
			}
		} else { // 完整三方平台签约
			LogCvt.info("签约快捷银行卡: 请求OpenApi系统进行签约银行认证 ");
			OpenApiRes res = bankCardSupport.signBankCardByClientIdOnOpenAPI(clientId, memberCode, uname, cardNo, idcard, phone,mobileToken, singlePenLimit, dayLimit, monthLimit);
			if (isRequestSuccess(res.getResultCode())) {
				LogCvt.info("签约快捷银行卡: OpenApi签约通过，进行积分平台签约");
				UserResult userResult = userEngineFunc.queryByMemberCode(memberCode);
				UserSpecDto userSpecDto;
				if (userResult.getResult()) {
					userSpecDto = userResult.getUserList().get(0);
					LogCvt.info("签约快捷银行卡: 进行账户账务快捷信息保存");
					userResult = bankCardSupport.signCardOnUserEngine(clientId,memberCode, cardNo, uname, idcard, phone,res.getSignNo(), res.getSinglePenLimit(),res.getDailyLimit(), res.getMonthlyLimit());
					LogCvt.info("签约快捷银行卡: 进行账户快捷卡信息保存 , 请求结果: " + JSONObject.toJSONString(userResult));
					if (userResult.getResult()) {
						LogCvt.info("签约快捷银行卡: 进行账户账务签约信息保存成功");
						String bankId = redis.acquireBankId(clientId);
						PointsReq pointsReq = new PointsReq(
								redis.acquirePointPartnerNo(clientId),
								userSpecDto.getLoginID(),
								PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME,
								uname, 
								bankId,
								BaseSubassembly.acquireClientFromRedis(clientId).getBankName(),
								cardNo,
								idcard, 
								phone, 
								"", 
								pointCardNo, "");
						PointsRes pointsRes = null;
						try {
							pointsRes = pointsApiFunc.bindBankAccount(pointsReq);
						} catch (Exception e) {
							LogCvt.error("签约快捷银行卡:  发送签约积分快捷信息异常", e);
							ResultBean resultBean = cancelSignedBankCard(clientId, memberCode, cardNo);
							if (!isRequestSuccess(resultBean.getCode())) {
								EsyT.sendPoint(MonitorPointEnum.order_pay_reverse_cancel_fast_pay_failed);
							}
							LogCvt.info("签约失败--解约银行卡结果： "+ JSONObject.toJSONString(resultBean));
							return new ResultBean(ResultCode.failed,pointsRes.getResultDesc());
						}
						if (!"0000".equals(pointsRes.getResultCode())) {
							ResultBean resultBean = cancelSignedBankCard(clientId, memberCode, cardNo);
							if (!isRequestSuccess(resultBean.getCode())) {
								EsyT.sendPoint(MonitorPointEnum.order_pay_reverse_cancel_fast_pay_failed);
							}
							LogCvt.info("签约失败--解约银行卡结果： "+ JSONObject.toJSONString(resultBean));
							return new ResultBean(ResultCode.failed,pointsRes.getResultDesc());
						}
						return new ResultBean(ResultCode.success, "签约认证成功");
					} else {
						LogCvt.info("签约快捷银行卡: 进行账户快捷卡信息保存失败:"+ userResult.getErrorMsg());
						return new ResultBean(ResultCode.failed,userResult.getErrorMsg());
					}
				} else {
					LogCvt.error("签约快捷银行卡: 进行积分平台快捷积分签约未找到用户");
					return new ResultBean(ResultCode.failed,userResult.getErrorMsg());
				}
			} else {
				LogCvt.info("签约快捷银行卡:  请求OpenApi系统进行签约银行认证失败: " + res.getResultDesc());
				return new ResultBean(ResultCode.failed, res.getResultDesc());
			}
		}
	}

    @Override
    public ResultBean setDefaultSignerBankCard(long memberCode,long cardId) {
        UserResult userResult = bankCardSupport.bindDefaultCard(memberCode,cardId);
        if (userResult.getResult()) {
            return new ResultBean(ResultCode.success, "设置成功");
        }
        return new ResultBean(ResultCode.failed, userResult.getErrorMsg());
    }

    @Override
    public ResultBean sendSignBankCardMobileToken(String clientId, String phone, String cardNo) {
        LogCvt.info("发送快捷签约银行手机验证码: Step.1 请求OpenApi系统进行快捷签约银行手机验证码");
        OpenApiRes res = bankCardSupport.sendSignBankCardMobileTokenUseOpenAPI(clientId, cardNo, phone);
        if (isRequestSuccess(res.getResultCode())) {
            LogCvt.info("发送快捷签约银行手机验证码: Step.2 请求OpenApi系统进行快捷签约银行手机验证码发送成功");
            return new ResultBean(ResultCode.success, "签约短信发送成功");
        } else {
            LogCvt.info("发送快捷签约银行手机验证码: Step.2 请求OpenApi系统进行快捷签约银行手机验证码发送失败: " + JSONObject.toJSONString(res));
            return new ResultBean(ResultCode.failed, res.getResultDesc());
        }
    }

//    @Override
//    public ResultBean cancelSignedBankCard(String clientId, long memberCode, String cardNo) {
//       
//    	LogCvt.info("解约已绑定快捷银行卡: Step.1 请求OpenApi系统查询该用户在该客户端下绑定的银行卡，并进行解绑操作");
//        OpenApiRes res = bankCardSupport.cancelSignedBankCardOnOpenAPI(clientId,memberCode, cardNo);
//
//        if (isRequestSuccess(res.getResultCode())) {
//            LogCvt.info("解约已绑定快捷银行卡: Step.2 OpenApi系统解绑成功，请求账户系统查询该用户在该客户端下绑定的银行卡，并进行解绑操作");
//            ResultBean genericResult = this.selectSignedBankCardByClientId(clientId,memberCode);
//            List<MemberBankSpecDto> userEngineMemberBankDtos = (List<MemberBankSpecDto>) genericResult.getData();
//            if (userEngineMemberBankDtos == null || userEngineMemberBankDtos.size() == 0) {
//                LogCvt.info("解约已绑定快捷银行卡: Step.3请求账户系统查询该用户在该客户端下绑定的银行卡 结果: 未找到对应账户系统保存该银行卡的签约信息，放弃解约账户系统操作");
//                return new ResultBean(ResultCode.success, "解约成功");
//            } else {
//                long memberBankId = -1;
//                for (MemberBankSpecDto userEngineMemberBankDto : userEngineMemberBankDtos) {
//                    if (cardNo.trim().equals(userEngineMemberBankDto.getCardNo().trim())) {
//                        memberBankId = userEngineMemberBankDto.getId();
//                        LogCvt.info("解约已绑定快捷银行卡: Step.3 请求账户系统查询该用户在该客户端下绑定的银行卡 结果: 已发现需要解约的银行卡信息 memberBankId" + memberBankId);
//                        break;
//                    }
//                }
//                if (memberBankId == -1) {
//                    LogCvt.info("解约已绑定快捷银行卡: Step.3 请求账户系统查询该用户在该客户端下绑定的银行卡 结果: 未能在用户系统找到该卡的签约信息（该用户有其他卡的签约信息），放弃解约账户系统操作");
//                    return new ResultBean(ResultCode.success, "解约成功");
//                }
//
//                UserResult userResult = bankCardSupport.cancelCardByDataID(memberBankId);
//                if (userResult.getResult()) {
//                    LogCvt.info("解约已绑定快捷银行卡: Step.4 请求账户系统删除对应的签约信息成功");
//                    
//                    userResult = userEngineFunc.queryByMemberCode(memberCode);
//                    UserSpecDto userSpecDto;
//                    if(userResult.getResult()){
//                    	userSpecDto = userResult.getUserList().get(0);
//                    }else{
//                    	LogCvt.error("解约快捷银行卡: Step.4 进行积分平台快捷积分解约未找到用户");
//                    	return new ResultBean(ResultCode.failed);
//                    }
//                    
//                    PointsReq pointsReq = new PointsReq(redis.acquirePointPartnerNo(clientId), userSpecDto.getLoginID(), PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME,cardNo,0);
//                    
//                    PointsRes pointRes;
//                    try {
//                    	pointRes = pointsApiFunc.unBindBankAccount(pointsReq);
//                    } catch (Exception e) {
//                    	LogCvt.error("解约快捷银行卡: Step.5 进行积分平台快捷积分解约异常",e);
//                    	return new ResultBean(ResultCode.failed,"进行积分平台快捷积分解约异常");
//                    }
//                    if ("0000".equals(pointRes.getResultCode())) {
//                    	BindAccountInfo bindAccountInfo = pointRes.getBindAccountInfo();
//                    	if( "0".equals(bindAccountInfo.getStatus()) ){
//                    		LogCvt.info("解约快捷银行卡: Step.5 进行积分平台快捷积分解约成功");
//                    		return new ResultBean(ResultCode.success, "解约成功");
//                    	}else{
//                    		LogCvt.info("解约快捷银行卡: Step.5 进行积分平台快捷积分解约失败 status: " + bindAccountInfo.getStatus());
//                    		return new ResultBean(ResultCode.failed);
//                    	}
//                    }
//                    return new ResultBean(ResultCode.failed);
//                } else {
//                    LogCvt.info("解约已绑定快捷银行卡: Step.4 请求账户系统删除对应的签约信息失败: " + userResult.getErrorMsg());
//                    return new ResultBean(ResultCode.failed, userResult.getErrorMsg());
//                }
//            }
//        } else {
//            LogCvt.info("解约已绑定快捷银行卡: Step.2 请求OpenApi系统进行解约银行卡 失败: " + res.getResultDesc());
//            return new ResultBean(ResultCode.failed, res.getResultDesc());
//        }
//    }
    
    @Override
    public ResultBean cancelSignedBankCard(String clientId, long memberCode, String cardNo) {
       
    	LogCvt.info("解约已绑定快捷银行卡: Step.1 请求OpenApi系统查询该用户在该客户端下绑定的银行卡，并进行解绑操作");
        OpenApiRes res = bankCardSupport.cancelSignedBankCardOnOpenAPI(clientId,memberCode, cardNo);

        if (isRequestSuccess(res.getResultCode())) {
            LogCvt.info("解约已绑定快捷银行卡: Step.2 OpenApi系统解绑成功，请求积分系统解约");
            
            UserResult ur = userEngineFunc.queryByMemberCode(memberCode);
            UserSpecDto userSpecDto = ur.getUserList().get(0);
            PointsReq pointsReq = new PointsReq(redis.acquirePointPartnerNo(clientId), userSpecDto.getLoginID(), PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME,cardNo,0);
            
            PointsRes pointRes;
            try {
            	pointRes = pointsApiFunc.unBindBankAccount(pointsReq);
            } catch (Exception e) {
            	LogCvt.error("解约快捷银行卡: Step.5 进行积分平台快捷积分解约异常",e);
            	return new ResultBean(ResultCode.failed,"进行积分平台快捷积分解约异常");
            }
            if ("0000".equals(pointRes.getResultCode())) {
            	BindAccountInfo bindAccountInfo = pointRes.getBindAccountInfo();
            	if( "0".equals(bindAccountInfo.getStatus()) ){
            		ResultBean genericResult = this.selectSignedBankCardByClientId(clientId,memberCode);
                    List<MemberBankSpecDto> userEngineMemberBankDtos = (List<MemberBankSpecDto>) genericResult.getData();
                    if (userEngineMemberBankDtos == null || userEngineMemberBankDtos.size() == 0) {
                        LogCvt.info("解约已绑定快捷银行卡: Step.3请求账户系统查询该用户在该客户端下绑定的银行卡 结果: 未找到对应账户系统保存该银行卡的签约信息，放弃解约账户系统操作");
                        return new ResultBean(ResultCode.success, "解约成功");
                    } else {
                        long memberBankId = -1;
                        for (MemberBankSpecDto userEngineMemberBankDto : userEngineMemberBankDtos) {
                            if (cardNo.trim().equals(userEngineMemberBankDto.getCardNo().trim())) {
                                memberBankId = userEngineMemberBankDto.getId();
                                LogCvt.info("解约已绑定快捷银行卡: Step.3 请求账户系统查询该用户在该客户端下绑定的银行卡 结果: 已发现需要解约的银行卡信息 memberBankId" + memberBankId);
                                break;
                            }
                        }
                        if (memberBankId == -1) {
                            LogCvt.info("解约已绑定快捷银行卡: Step.3 请求账户系统查询该用户在该客户端下绑定的银行卡 结果: 未能在用户系统找到该卡的签约信息（该用户有其他卡的签约信息），放弃解约账户系统操作");
                            return new ResultBean(ResultCode.success, "解约成功");
                        }
                        UserResult userResult = bankCardSupport.cancelCardByDataID(memberBankId);
                        if (userResult.getResult()) {
                            LogCvt.info("解约已绑定快捷银行卡: Step.4 请求账户系统删除对应的签约信息成功");
                           
                            return new ResultBean(ResultCode.success);
                        } else {
                            LogCvt.info("解约已绑定快捷银行卡: Step.4 请求账户系统删除对应的签约信息失败: " + userResult.getErrorMsg());
                            return new ResultBean(ResultCode.failed, userResult.getErrorMsg());
                        }
                    }
            	}else{
            		LogCvt.info("解约快捷银行卡: Step.5 进行积分平台快捷积分解约失败 status: " + bindAccountInfo.getStatus());
            		return new ResultBean(ResultCode.failed,pointRes.getResultDesc());
            	}
            }else{
            	 LogCvt.info("解约已绑定快捷银行卡: Step.2 请求Point系统进行解约银行卡 失败: " + res.getResultDesc());
                 return new ResultBean(ResultCode.failed, pointRes.getResultDesc());
            }
        } else {
            LogCvt.info("解约已绑定快捷银行卡: Step.2 请求OpenApi系统进行解约银行卡 失败: " + res.getResultDesc());
            return new ResultBean(ResultCode.failed, res.getResultDesc());
        }
    }

    @Override
    public ResultBean updateSignBankCardLimitCash(String clientId, String cardNo, String singlePenLimit, String dailyLimit, String monthlyLimit) {
        OpenApiRes res = bankCardSupport.setFastPayLimitOnOpenAPI(clientId,cardNo, singlePenLimit, dailyLimit, monthlyLimit);
        if (isRequestSuccess(res.getResultCode())) {
            UserResult userResult = bankCardSupport.setFastPayLimitOnUserEngine(cardNo, singlePenLimit, dailyLimit, monthlyLimit);
            if (userResult.getResult()) {
                return new ResultBean(ResultCode.success, "限额修改成功");
            } else {
                LogCvt.info("设置快捷银行卡限额:请求账户系统，进行对应银行卡限额调整失败: " + userResult.getErrorMsg());
                return new ResultBean(ResultCode.failed, userResult.getErrorMsg());
            }
        } else {
            LogCvt.info("设置快捷银行卡限额:请求OpenApi系统进行快捷银行卡限额设置 失败: " + res.getResultDesc());
            return new ResultBean(ResultCode.failed, res.getResultDesc());
        }
    }
    
	private boolean isRequestSuccess(String code){
        return code!=null&&code.equals("0000");
    }


	@Override
	public ResultBean setMerchantWhiteList(String clientId,String merchantId,String merchantName, String accountNo, String mac, String optionType,String accountName) {
		OpenApiRes res;
		try{
			res = bankCardSupport.setMerchantWhiteList(clientId, merchantId, merchantName, accountNo, mac, optionType, accountName);
		}catch (Exception e) {
			LogCvt.error("设置商户银行卡白名单失败: " , e);
            return new ResultBean(ResultCode.failed, "设置商户银行卡白名单失败");
		}
		
		if("0000".equals(res.getResultCode())){
			return new ResultBean(ResultCode.success);
		}else{
			return new ResultBean(ResultCode.failed, res.getResultDesc());
		}
	}


	@Override
	public ResultBean synchBankLabel(String bankLabelID, String bankLabelName, int state,String clientId) {
		com.pay.user.dto.Result result = bankCardSupport.synchBankLabel(bankLabelID, bankLabelName, state,BaseSubassembly.acquireBankOrg(clientId));
		if(result.getResult()){
			return new ResultBean(ResultCode.success);
		}
		return new ResultBean(ResultCode.failed,result.getMessage());
	}

	@Override
	public ResultBean auditStatusQuery(String clientId, String accountName,String accountNo) {
		OpenApiRes res = bankCardSupport.auditStatusQuery(BaseSubassembly.acquireClientFromRedis(clientId).getSettlePayOrg(), accountName, accountNo, BaseSubassembly.acquireOpenAPIPartnerNo(clientId));
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			
			return new ResultBean(ResultCode.success,res.getQueryResultContent(),res.getQueryResultCode());
		}
		return new ResultBean(ResultCode.failed,res.getResultDesc());
	}


	@Override
	public ResultBean bankCardAccountCheck(String clientId, String accountName,String accountNo, String certificateType, String certificateNo) {
		OpenApiRes res = bankCardSupport.bankCardAccountCheck(BaseSubassembly.acquireClientFromRedis(clientId).getSettlePayOrg(), accountName, accountNo, certificateType, certificateNo, BaseSubassembly.acquireOpenAPIPartnerNo(clientId));
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			return new ResultBean(ResultCode.success,res.getCheckResultContent(),res.getCheckResultCode());
		}
		return new ResultBean(ResultCode.failed,res.getResultDesc());
	}


	@Override
	public String getSignPointCardNo(Long memberCode) {
		UserResult userResult = userEngineFunc.queryByMemberCode(memberCode);
		if (userResult.getResult()) {
			PointsReq req = new PointsReq(userResult.getUserList().get(0).getLoginID());
			try {
				PointsRes res = pointsApiFunc.contractRelationshipQuery(req);
				if("0000".equals(res.getResultCode())){
					return res.getResultDesc();
				}
				return "";
			} catch (Exception e) {
				return "";
			}
		}
		
		return "";
	}

}
