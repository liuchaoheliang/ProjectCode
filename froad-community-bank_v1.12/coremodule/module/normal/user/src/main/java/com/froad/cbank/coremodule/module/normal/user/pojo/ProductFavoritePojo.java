package com.froad.cbank.coremodule.module.normal.user.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月16日 下午7:19:37
 * @version 1.0
 * @desc 商品收藏POJO
 */
public class ProductFavoritePojo {
	private String merchantId;
	@NotEmpty("商品ID不可为空")
	private String productId;
	
	private String name;
	private String imagePic;
	private String isMarketable;
	private String type;
	private Double price;//销售价
	private Double marketPrice;//市场价
	private Double vipPrice;//vip价
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImagePic() {
		return imagePic;
	}
	public void setImagePic(String imagePic) {
		this.imagePic = imagePic;
	}
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
	
}
