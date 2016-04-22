package com.froad.CB.po.merchant;

import java.io.Serializable;
import java.util.List;

import com.froad.CB.po.tag.TagMAP;


	/**
	 * 类描述：商户信息集合
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:31:15 PM 
	 */
public class MerchantInfoMap  implements Serializable{
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Merchant merchant;
	private MerchantPresent merchantPresentInfo;
	private List<MerchantPreferential> merchantPreferentials;
	private List<TagMAP> tags;
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	public MerchantPresent getMerchantPresentInfo() {
		return merchantPresentInfo;
	}
	public void setMerchantPresentInfo(MerchantPresent merchantPresentInfo) {
		this.merchantPresentInfo = merchantPresentInfo;
	}
	public List<MerchantPreferential> getMerchantPreferentials() {
		return merchantPreferentials;
	}
	public void setMerchantPreferentials(
			List<MerchantPreferential> merchantPreferentials) {
		this.merchantPreferentials = merchantPreferentials;
	}
	public List<TagMAP> getTags() {
		return tags;
	}
	public void setTags(List<TagMAP> tags) {
		this.tags = tags;
	}

	
}
