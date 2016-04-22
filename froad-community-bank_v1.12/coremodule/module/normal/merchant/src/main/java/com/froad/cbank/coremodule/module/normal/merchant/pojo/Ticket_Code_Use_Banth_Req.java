package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Ticket_Code_Use_Banth_Req extends BasePojo {

	private String type;

	@NotEmpty(value = "商品不能为空")
	private String productIds;

	@NotEmpty(value = "商品数量不能为空")
	private String quantitys;

	@NotEmpty(value = "提货人编号不能为空")
	private String memberCode;
	
	@NotEmpty(value = "订单编号不能为空")
	private String subOrderId;

	/**
	 * 必须要验证的券号(客户提供给门店操作员的券号)
	 */
	private String mustValidTicketId; // required

	public String getMustValidTicketId() {
		return mustValidTicketId;
	}

	public void setMustValidTicketId(String mustValidTicketId) {
		this.mustValidTicketId = mustValidTicketId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getQuantitys() {
		return quantitys;
	}

	public void setQuantitys(String quantitys) {
		this.quantitys = quantitys;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
}
