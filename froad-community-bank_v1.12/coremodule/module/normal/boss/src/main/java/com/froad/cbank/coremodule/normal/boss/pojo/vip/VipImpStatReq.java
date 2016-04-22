package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * VIP导入批次状态变更请求
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月30日 上午11:35:34
 */
public class VipImpStatReq {
	@NotEmpty("批次ID为空")
	private Long batchCode;

	public Long getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(Long batchCode) {
		this.batchCode = batchCode;
	}
}
