package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;
import com.froad.cbank.coremodule.framework.common.valid.Regulars;

public class Add_Face_Group_Req extends BasePojo {
	/**
	 * 价格
	 */
	@NotEmpty(value="金额不为空")
	@Regular(reg=Regulars.MONEY,value="金额格式不正确")
	private String money; 
	/**
	 * 商品id
	 */
	private String productId; 
	/**
	 * 备注
	 */
	private String remark; 


	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
