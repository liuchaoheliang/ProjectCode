package com.froad.fft.dto;

import java.io.Serializable;

import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 类描述：dto基类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: 2014年3月27日 下午3:37:28
 */
public class BaseDto implements Serializable {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private ClientAccessType clientAccessType;
	private ClientVersion clientVersion;

	public ClientAccessType getClientAccessType() {
		return clientAccessType;
	}

	public void setClientAccessType(ClientAccessType clientAccessType) {
		this.clientAccessType = clientAccessType;
	}

	public ClientVersion getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(ClientVersion clientVersion) {
		this.clientVersion = clientVersion;
	}
}
