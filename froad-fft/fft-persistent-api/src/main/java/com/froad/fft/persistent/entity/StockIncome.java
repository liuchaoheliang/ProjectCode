package com.froad.fft.persistent.entity;

import java.util.Date;

/**
 * 入库
 * @author FQ
 *
 */
public class StockIncome extends BaseEntity {
	
	public enum Type{
		
		initial//初始入库
	}
	
	public enum AuditStatus {
		initial,//初始
		audit_pass,//审核通过
		audit_not_pass//审核不通过
	}
	
	private String sn;//编号 如: CQYYMMDDXXXXX
	private Long merchantOutletId;//入库分店ID
	private Type type;//入库类型
	private String operator;//入库操作员
	
	private AuditStatus auditStatus;//审核状态
	private String inspectors;//审核人
	private Date auditTime;//审核时间
	
	private String remark;//备注
}
