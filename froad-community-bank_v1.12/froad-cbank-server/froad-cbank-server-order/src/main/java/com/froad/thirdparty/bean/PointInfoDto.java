package com.froad.thirdparty.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.froad.thirdparty.enums.ProtocolType;


public class PointInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 订单的编号 */
	private String orderId;
	/** 订单的编号 - 支付流水*/
	private String objectNo;

	/** 账户编号 */
	private String accountNo;

	/** 商户编号 */
	private String partnerNo;

	/** 机构积分 */
	private String orgPoints;

	/** 积分平台的积分 */
	private String points;

	/** 协议生成时间 */
	private String time;

	/** 协议在积分平台中的标识 */
	private String protocolNo;

	/** 协议类型 */
	private ProtocolType protocolType;

	/** 业务类型 */
	private String businessType;

	/** 业务说明 */
	private String businessInstructions;

	/** 备注 */
	private String remark;

	private String displayName;// 积分显示名称

	private String orgNo;// 积分机构编号

	/********************** 其他 **********************/
	/** 扩展 */
	private Map<String, String> extension = new HashMap<String, String>();

	public Map<String, String> getExtension() {
		return extension;
	}

	public void setExtension(Map<String, String> extension) {
		this.extension = extension;
	}

	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getOrgPoints() {
		return orgPoints;
	}

	public void setOrgPoints(String orgPoints) {
		this.orgPoints = orgPoints;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getProtocolNo() {
		return protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public ProtocolType getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(ProtocolType protocolType) {
		this.protocolType = protocolType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBusinessInstructions() {
		return businessInstructions;
	}

	public void setBusinessInstructions(String businessInstructions) {
		this.businessInstructions = businessInstructions;
	}

	public String getPartnerNo() {
		return partnerNo;
	}

	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
