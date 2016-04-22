package com.froad.cbank.coremodule.module.normal.user.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月27日 上午11:34:40
 * @version 1.0
 * @desc 收货地址删除请求POJO
 */
public class ReceiverDelPojo {
	
	@NotEmpty("收货地址ID不可为空")
	private String recvId;

	public String getRecvId() {
		return recvId;
	}
	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}
}
