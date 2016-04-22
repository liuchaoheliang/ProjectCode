package com.froad.fft.persistent.entity;

import java.io.Serializable;


	/**
	 * 类描述：客户端实体和资金渠道实体的中间实体
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年4月29日 下午2:45:37 
	 */
public class ClientChannel implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Long clientId;//客户端id
	
	private Long fundsChannelId;//资金渠道id

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getFundsChannelId() {
		return fundsChannelId;
	}

	public void setFundsChannelId(Long fundsChannelId) {
		this.fundsChannelId = fundsChannelId;
	}
	
	
}
