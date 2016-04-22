package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName: BankOutletReqVo
 * @Description: 待审核门店列表请求VO
 * @author ming
 * @date 2015年10月22日 上午11:00:25
 *
 */
public class BankOutletReqVo extends BaseVo implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 6415441231237953873L;
	private String merchantName;// 商户名称
	private String outletName;// 门店名称
	private String ip;// 请求ip
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
