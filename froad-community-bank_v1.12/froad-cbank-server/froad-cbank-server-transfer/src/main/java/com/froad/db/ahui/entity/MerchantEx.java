package com.froad.db.ahui.entity;


import com.froad.fft.persistent.entity.Merchant;

/**
 * 商户
 * 
 * @author FQ
 * 
 */
public class MerchantEx extends Merchant{
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
