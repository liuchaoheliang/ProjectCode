package com.froad.fft.persistent.entity;

import java.util.Date;

/**
 * 出库
 * @author FQ
 *
 */
public class StockOutcome extends BaseEntity{
	
	public enum Type{
		sales//销售出库
	}
	
	public enum AuditStatus {
		initial,//初始
		audit_pass,//审核通过
		audit_not_pass//审核不通过
	}
	
	private String sn;//
	private Long merchantOutletId;//出库分店ID
	private String operator;//出库操作员
	
	private AuditStatus auditStatus;//审核状态
	private String inspectors;//审核人
	private Date auditTime;//审核时间
	
	private String remark;//备注
}
