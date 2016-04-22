package com.froad.cbank.coremodule.module.normal.user.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月27日 上午11:59:28
 * @version 1.0
 * @desc 提货人信息删除POJO
 */
public class DeliveryDelPojo {
	
	@NotEmpty("提货人信息ID不可为空")
	private String deliveryId;

	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
}
