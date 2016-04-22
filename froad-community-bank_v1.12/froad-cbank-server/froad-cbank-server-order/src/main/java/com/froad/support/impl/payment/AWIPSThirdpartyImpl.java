package com.froad.support.impl.payment;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.PointInfo;
import com.froad.common.beans.ResultBean;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.ResultCode;
import com.froad.exceptions.PaymentException;
import com.froad.support.BankCardSupport;
import com.froad.support.impl.BankCardSupportImpl;
import com.froad.support.payment.AWIPSThirdparty;
import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.openapi.QueryParamApiReq;
import com.froad.thirdparty.dto.request.points.PointsAccount;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.thirdparty.request.openapi.OpenApiFunc;
import com.froad.thirdparty.request.openapi.impl.OpenApiFuncImpl;
import com.froad.thirdparty.request.points.PointsApiFunc;
import com.froad.thirdparty.request.points.impl.PointsApiFuncImpl;
import com.froad.thirdparty.request.userengine.UserEngineFunc;
import com.froad.thirdparty.request.userengine.impl.UserEngineFuncImpl;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;
import com.froad.util.payment.ThirdpartyRequestBuilder;
import com.pay.user.dto.MemberBankSpecDto;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.dto.VIPSpecDto;
import com.pay.user.helper.VIPLevel;
import com.pay.user.helper.VIPStatus;

public class AWIPSThirdpartyImpl implements AWIPSThirdparty {

	private OpenApiFunc openApiFunc = new OpenApiFuncImpl();
	private PointsApiFunc pointsApiFunc = new PointsApiFuncImpl();
	private UserEngineFunc userEngineFunc = new UserEngineFuncImpl();
	private BankCardSupport bankCardSupport = new BankCardSupportImpl();
	
	
	/**
	 * 贴膜卡手机号验证不通过
	 */
	private final String FOIL_CARD_IS_NOT_SUPPORT = "该手机号码尚未开通贴膜卡业务";
	
	@Override
	public UserSpecDto queryUserByMemberCode(Long memberCode) {
		if(memberCode == null){
			Logger.logError("查询会员信息,memberCode为空");
		}
		UserResult userResult = userEngineFunc.queryByMemberCode(memberCode);
		if (userResult.getResult()) {
			if (userResult.getUserList() != null && userResult.getUserList().size() == 1) {
				return userResult.getUserList().get(0);
			}
		}
		Logger.logError("查询会员用户不存在","memberCode",memberCode);
		EsyT.sendPoint(MonitorPointEnum.order_pay_user_void);
		return null;
	}
	
	/**
	 * 查询用户积分信息
	* <p>Function: queryUserPoints</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午9:56:10
	* @version 1.0
	* @param loginID
	* @param pointPartnerNo
	* @param clientId
	* @return
	 */
	public PointInfo queryUserPoints(String loginID,String clientId){
		
		String pointPartnerNo = BaseSubassembly.acquirePointPartnerNo(clientId);
		PointsRes res = pointsApiFunc.queryParAccountPoints(new PointsReq(null, loginID, "1", pointPartnerNo));
		
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			List<PointsAccount> pointsAccounts = res.getAccountPointsInfoList();
			PointInfo pointInfo = new PointInfo();
			//取出该用户所有积分信息中属于方付通积分和对应的平台银行积分
			for (PointsAccount pointsAccount : pointsAccounts) {
				if(Const.FROAD_POINT_ORG_NO.equals(pointsAccount.getOrgNo())){
					pointInfo.setFroadPoint(Double.parseDouble(pointsAccount.getPoints()));
					pointInfo.setFroadAccountId(pointsAccount.getAccountId());
					break;
				}
			}
			String bankOrgNo = BaseSubassembly.acquireBankPointPaymentOrgNo(clientId);//获取用户银行机构号
			if(StringUtils.isEmpty(bankOrgNo)){ //没有银行机构号，无需遍历银行积分
				return pointInfo;
			}
			for (PointsAccount pointsAccount : pointsAccounts) {
				if(bankOrgNo.equals(pointsAccount.getOrgNo())){
					pointInfo.setBankPoint(Double.parseDouble(pointsAccount.getPoints()));
					pointInfo.setBankAccountId(pointsAccount.getAccountId());
					break;
				}
			}
			return pointInfo;
		}
		
		Logger.logInfo("未能获取用户积分信息","loginID",loginID);
		return null;
	}
	
	@Override
	public PointsRes pointConsume(PointsReq req) {
		PointsRes res;
		try {
			res = pointsApiFunc.consumePoints(req);
		} catch (Exception e) {
			throw new PaymentException(e);
		}
		return res;
		
	}
	
	@Override
	public OpenApiRes cashConsume(OpenApiReq req) {
		OpenApiRes res;
		try {
			res = openApiFunc.combineOrder(req);
		} catch (Exception e) {
			throw new PaymentException(e);
		}
		return res;
	}

	@Override
	public ResultBean verifyFoilCardNumOfOpenAPI(String clientId, String foilCardNum) {
		try {
			OpenApiRes res = openApiFunc.accountCheck(
						ThirdpartyRequestBuilder.builderVerifyFoilCardNumReq(clientId, foilCardNum)
					);
			if(OpenApiCommand.SUCCESS.equals(res.getCheckResultCode())){
				return new ResultBean(ResultCode.success);
			}else{
				return new ResultBean(ResultCode.payment_foil_card_none,FOIL_CARD_IS_NOT_SUPPORT);
			}
		} catch (Exception e) {
			Logger.logError("发送校验贴膜卡请求异常", e);
		}
		return new ResultBean(ResultCode.payment_foil_card_none,FOIL_CARD_IS_NOT_SUPPORT);
	}

	@Override
	public PointsRes pointRefund(PointsReq req) {
		PointsRes res;
		try {
			res = pointsApiFunc.refundPoints(req);
		} catch (Exception e) {
			throw new PaymentException(e);
		}
		return res;
	}

	@Override
	public PointsRes pointPresent(PointsReq req) {
		PointsRes res;
		try {
			res = pointsApiFunc.presentPoints(req);
		} catch (Exception e) {
			throw new PaymentException(e);
		}
		return res;
	}

	@Override
	public OpenApiRes cashRefund(OpenApiReq req) {
		OpenApiRes res;
		try {
			res = openApiFunc.refund(req);
		} catch (Exception e) {
			throw new PaymentException(e);
		}
		return res;
	}

	@Override
	public OpenApiRes verifyPayResult(QueryParamApiReq req) {
		OpenApiRes res;
		try {
			res = openApiFunc.query(req);
		} catch (Exception e) {
			throw new PaymentException(e);
		}
		return res;
	}

	@Override
	public PointsRes verfyPayResult(PointsReq req) {
		PointsRes res;
		try {
			res = pointsApiFunc.queryOrderStatus(req);
		} catch (Exception e) {
			throw new PaymentException(e);
		}
		return res;
	}

	@Override
	public Result processVIPOrder(Long memberCode, VIPLevel vipLevel,Date vipExpirationTime, String bankLabelID, String bankOrg,String clientChannel) {
		return userEngineFunc.processVIPOrder(memberCode, vipLevel, vipExpirationTime, bankLabelID, bankOrg, clientChannel);
	}

	@Override
	public MemberBankSpecDto queryUserSignatoryBankCardOfClient(String clientId, Long memberCode) {
		//这个接口实际查询的是客户端下的签约银行卡信息 接口命名有问题?
		UserResult userResult = bankCardSupport.getAllSignCardByMemberCode(clientId, memberCode);
		if(userResult == null || !userResult.getResult()){
			return null;
		}
		List<MemberBankSpecDto> banks = userResult.getMemberBankList();
		if(banks == null || banks.size() == 0){
			return null;
		}
		
		//TODO:目前数据结构表示一个用户在一个银行只能签约一张卡
		return banks.get(0);
	}

	@Override
	public VIPSpecDto queryVIPInfoByMemberCode(Long memberCode, String clientId) {
		Result result = userEngineFunc.queryVIPInfoByMemberCode(memberCode, clientId);
		if (result.getResult()) {
			@SuppressWarnings("unchecked")
			List<VIPSpecDto> vipSpecDtos = (List<VIPSpecDto>) result.getData();
			if (vipSpecDtos == null || vipSpecDtos.size() != 1) {
				return null;
			}
			if(VIPStatus.NORMAL.equals(vipSpecDtos.get(0).getVipStatus())){//会员状态正常
				return vipSpecDtos.get(0);
			}else{//会员状态失效或者未购买
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public boolean cancelVIPOrder(Long memberCode, String bankOrg) {
		Result result = userEngineFunc.cancelVIPOrder(memberCode, bankOrg);
		Logger.logInfo("请求PE系统取消会员","result",result);
		return result.getResult();
	}

	
}
