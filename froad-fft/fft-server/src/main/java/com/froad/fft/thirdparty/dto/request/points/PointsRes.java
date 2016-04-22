package com.froad.fft.thirdparty.dto.request.points;

import java.util.List;

/**
 * 积分响应对象
 * @author FQ
 *
 */
public class PointsRes {
	
	private String resultCode;//结果
	private String resultDesc;//结果描述
	private String payPointsNo;//积分消费号
	private String refundPointsNo;//退积分号
	
	private List<PointsAccount> pointsAccountList;
	
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
	
}
