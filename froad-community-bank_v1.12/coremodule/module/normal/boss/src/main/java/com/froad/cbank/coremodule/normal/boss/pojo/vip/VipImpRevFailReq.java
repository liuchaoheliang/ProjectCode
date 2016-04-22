package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;

/**
 * VIP导入批次审核不通过请求
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月30日 上午11:31:15
 */
public class VipImpRevFailReq {
	@NotEmpty("批次ID为空")
	private Long batchCode;
	@NotEmpty("审核不通过原因为空")
	@Regular(reg = "^[\\s\\S]{1,500}$", value = "不通过原因限500字内")
	private String descriptionFail;

	public Long getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(Long batchCode) {
		this.batchCode = batchCode;
	}
	public String getDescriptionFail() {
		return descriptionFail;
	}
	public void setDescriptionFail(String descriptionFail) {
		this.descriptionFail = descriptionFail;
	}
}
