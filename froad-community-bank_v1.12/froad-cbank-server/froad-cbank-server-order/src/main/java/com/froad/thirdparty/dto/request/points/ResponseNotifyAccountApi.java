/**
 * 文件名：RequestNotifyAccountApi.java
 * 版本信息：Version 1.0
 * 日期：2014年7月25日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.thirdparty.dto.request.points;

import com.froad.thirdparty.dto.response.points.BindAccountInfo;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年7月25日 下午12:32:57
 */
public class ResponseNotifyAccountApi {

	private PartnerAccount partnerAccount;

	private BindAccountInfo bindAccountInfo;

	private System system;

	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public BindAccountInfo getBindAccountInfo() {
		return bindAccountInfo;
	}

	public void setBindAccountInfo(BindAccountInfo bindAccountInfo) {
		this.bindAccountInfo = bindAccountInfo;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

}
