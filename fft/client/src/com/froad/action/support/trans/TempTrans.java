package com.froad.action.support.trans;

import com.froad.client.trans.Trans;


	/**
	 * 类描述：用于传递参数
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 4, 2013 11:10:22 PM 
	 */
public class TempTrans extends Trans{
	private String goodsRackId;
	private String goodsNumber;
	private String goodsName;
	private String sellerRuleId;
	public String getGoodsRackId() {
		return goodsRackId;
	}

	public void setGoodsRackId(String goodsRackId) {
		this.goodsRackId = goodsRackId;
	}

	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getSellerRuleId() {
		return sellerRuleId;
	}

	public void setSellerRuleId(String sellerRuleId) {
		this.sellerRuleId = sellerRuleId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
}
