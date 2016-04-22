/**
 * 文件名：ResponseQueryCardDepositApi.java
 * 版本信息：Version 1.0
 * 日期：2014年8月18日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.thirdparty.dto.request.points;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年8月18日 上午10:53:53
 */
public class ResponseQueryCardDepositApi {

	private PointsCard pointsCard;

	private PartnerAccount partnerAccount;

	private System system;

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

	public PointsCard getPointsCard() {
		return pointsCard;
	}

	public void setPointsCard(PointsCard pointsCard) {
		this.pointsCard = pointsCard;
	}

	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

}
