package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;

/**
 * VIP批量导入请求类
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月30日 上午10:37:51
 */
public class VipImpReq {
	@NotEmpty("文件名为空")
	private String fileName;//CSV文件名
	@NotEmpty("银行渠道为空")
	private String bankOrg;//银行渠道
	@NotEmpty("事由为空")
	@Regular(reg = "^[\\s\\S]{1,50}$", value = "事由限50字内")
	private String description;//事由
	private String operator;//操作人
	@NotEmpty("银行机构ID为空")
	private String labelId;//银行机构ID
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBankOrg() {
		return bankOrg;
	}
	public void setBankOrg(String bankOrg) {
		this.bankOrg = bankOrg;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
}
