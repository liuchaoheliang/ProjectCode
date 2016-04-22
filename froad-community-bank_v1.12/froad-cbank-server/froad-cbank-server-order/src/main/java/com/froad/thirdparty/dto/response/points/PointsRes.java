package com.froad.thirdparty.dto.response.points;

import java.util.List;

import com.froad.thirdparty.bean.PointInfoDto;
import com.froad.thirdparty.bean.QueryInfoDto;
import com.froad.thirdparty.bean.TotalPointsInfosDto;
import com.froad.thirdparty.dto.request.points.CheckResult;
import com.froad.thirdparty.dto.request.points.PointsAccount;
import com.froad.thirdparty.dto.request.points.PointsCard;
import com.froad.thirdparty.dto.request.points.ResponseQueryOrderStatusApi;
/**
 * 积分响应对象
 * @author FQ
 *
 */
public class PointsRes {
	
	private String resultCode;//结果
	private String resultDesc;//结果描述
	private String payPointsNo;//积分消费号
	private String presentPointsNo;//赠送积分号
	private String cashPointsNo;//积分提现号
	private String refundPointsNo;//退积分号
	private String exchangeRate;//积分兑换比例
	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	private PointsAccount pointsAccount;
	private List<PointsAccount> pointsAccountList;
	private PointsCard pointsCard;//充值卡信息
	private CheckResult checkResult;//短信验证码发送结果
	
	private BindAccountInfo bindAccountInfo;
	
	private QueryInfoDto queryInfo;
	private List<TotalPointsInfosDto> totalPointsInfos;
	private List<PointInfoDto> protocolInfos;
	
	
	public ResponseQueryOrderStatusApi getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ResponseQueryOrderStatusApi orderStatus) {
		this.orderStatus = orderStatus;
	}

	private ResponseQueryOrderStatusApi orderStatus;
	
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public List<PointsAccount> getAccountPointsInfoList() {
		return pointsAccountList;
	}

	public void setAccountPointsInfoList(
			List<PointsAccount> accountPointsInfoList) {
		this.pointsAccountList = accountPointsInfoList;
	}

	public String getPayPointsNo() {
		return payPointsNo;
	}

	public void setPayPointsNo(String payPointsNo) {
		this.payPointsNo = payPointsNo;
	}

	public String getRefundPointsNo() {
		return refundPointsNo;
	}

	public void setRefundPointsNo(String refundPointsNo) {
		this.refundPointsNo = refundPointsNo;
	}

	public String getPresentPointsNo() {
		return presentPointsNo;
	}

	public void setPresentPointsNo(String presentPointsNo) {
		this.presentPointsNo = presentPointsNo;
	}

	public String getCashPointsNo() {
		return cashPointsNo;
	}

	public void setCashPointsNo(String cashPointsNo) {
		this.cashPointsNo = cashPointsNo;
	}

	public PointsCard getPointsCard() {
		return pointsCard;
	}

	public void setPointsCard(PointsCard pointsCard) {
		this.pointsCard = pointsCard;
	}

	public CheckResult getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(CheckResult checkResult) {
		this.checkResult = checkResult;
	}

	public PointsAccount getPointsAccount() {
		return pointsAccount;
	}

	public void setPointsAccount(PointsAccount pointsAccount) {
		this.pointsAccount = pointsAccount;
	}

	public BindAccountInfo getBindAccountInfo() {
		return bindAccountInfo;
	}

	public void setBindAccountInfo(BindAccountInfo bindAccountInfo) {
		this.bindAccountInfo = bindAccountInfo;
	}

	public QueryInfoDto getQueryInfo() {
		return queryInfo;
	}

	public void setQueryInfo(QueryInfoDto queryInfo) {
		this.queryInfo = queryInfo;
	}

	public List<TotalPointsInfosDto> getTotalPointsInfos() {
		return totalPointsInfos;
	}

	public void setTotalPointsInfos(List<TotalPointsInfosDto> totalPointsInfos) {
		this.totalPointsInfos = totalPointsInfos;
	}

	public List<PointInfoDto> getProtocolInfos() {
		return protocolInfos;
	}

	public void setProtocolInfos(List<PointInfoDto> protocolInfos) {
		this.protocolInfos = protocolInfos;
	}
	
}
