package com.froad.cbank.coremodule.module.normal.finance.vo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;
import com.froad.cbank.coremodule.framework.common.valid.Regulars;


/**   
 * 创建订单参数Vo
 * @author liuhuangle@f-road.com.cn   
 * com.froad.cbank.coremodule.module.normal.finance.vo
 * @date 2015-6-15 下午3:31:05
 */
public class OrderCreateVo {
	
	/**下单商品*/
	@NotEmpty(value="商品信息不能为空")
	private String productId;
	
	/**下单金额*/
	@NotEmpty(value="必须输入购买金额")
	@Regular(reg=Regulars.MONEY,value="金额格式不正确")
	private double buyAmount;
	
	/**创建渠道：PC：100；Android：200；iPhone：300；iPad:400*/
	private String createSource;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public double getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}
	public String getCreateSource() {
		return createSource;
	}
	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}
	
	
}
