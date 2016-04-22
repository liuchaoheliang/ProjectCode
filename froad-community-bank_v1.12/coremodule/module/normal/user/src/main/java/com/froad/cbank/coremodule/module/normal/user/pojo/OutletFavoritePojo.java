package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月16日 下午7:20:11
 * @version 1.0
 * @desc 门店收藏POJO
 */
public class OutletFavoritePojo {
	
	private String merchantId;
	
	@NotEmpty("门店ID不可为空")
	private String outletId;
	
	private String outletName;
	private String defaultImage;
	private List<CategoryInfoPojo> categoryList;
	private Boolean isEnable;
	private String address;
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getDefaultImage() {
		return defaultImage;
	}
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
	public List<CategoryInfoPojo> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<CategoryInfoPojo> categoryList) {
		this.categoryList = categoryList;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
