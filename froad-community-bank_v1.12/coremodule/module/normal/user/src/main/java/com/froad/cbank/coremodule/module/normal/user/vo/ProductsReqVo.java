package com.froad.cbank.coremodule.module.normal.user.vo;
/**  
 * 前端传入商品VO-查询校验时使用
 * @date 2015年11月25日 下午5:11:00  
 * 项目名称：coremodule-user  
 * @author 周煜涵  
 * 文件名称：ProductsVo.java  
 */
public class ProductsReqVo {
	//商品id
	private String productId;
	//普通金额
	private Double generalMoney;
	//vip金额
	private Double vipMoney;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Double getGeneralMoney() {
		return generalMoney;
	}
	public void setGeneralMoney(Double generalMoney) {
		this.generalMoney = generalMoney;
	}
	public Double getVipMoney() {
		return vipMoney;
	}
	public void setVipMoney(Double vipMoney) {
		this.vipMoney = vipMoney;
	}
	
}
