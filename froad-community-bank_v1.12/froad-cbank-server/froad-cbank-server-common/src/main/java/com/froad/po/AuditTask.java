package com.froad.po;

import java.util.Date;

/**
 * 审核任务订单表 po
 * @author ll 20150814 add
 *
 */
public class AuditTask  implements java.io.Serializable {

	private Long id;						//主键Id
	private Date createTime;				//创建时间
	private String clientId;				//客户端Id
	private String thridId;					//1-商户审核记录商户id(merchant_id)
	private String userName;				//创建人，银行登录用户名
	private String auditId;					//审核流水号 (yyyymmd+4位数字时间流水号)
	private String orgCode;					//提交审核人所属机构
	private String orgName;					//提交审核人所属机构名称
	private String type;					//审核类型：1-商户审核
	private String name;					//名称：若为商户审核则是商户名称
	private String busCode;					//业务号码：若为商户审核则是商户的营业执照号。
	private String auditStartOrgCode;		//审核开始机构
	private String auditEndOrgCode;			//审核结束机构
	private String auditOrgCode;			//当前审核机构
	private String auditState;				//最终审核状态：0-审核中  1-审核通过 2-审核未通过
	private String state;					//0-在途 1-归档
	private Date auditTime;					//归档时间
	
	private Date startCreateTime;			//开始创建时间
    private Date endCreateTime;				//结束创建时间
    private Date startAuditTime;			//开始归档时间
    private Date endAuditTime;				//结束归档时间
	
    
	public AuditTask() {
	}

	public AuditTask(Date createTime, String clientId, String thridId,
			String userName, String auditId, String orgCode, String orgName,
			String type, String name, String busCode,
			String auditStartOrgCode, String auditEndOrgCode,
			String auditOrgCode, String state) {
		this.createTime = createTime;
		this.clientId = clientId;
		this.thridId = thridId;
		this.userName = userName;
		this.auditId = auditId;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.type = type;
		this.name = name;
		this.busCode = busCode;
		this.auditStartOrgCode = auditStartOrgCode;
		this.auditEndOrgCode = auditEndOrgCode;
		this.auditOrgCode = auditOrgCode;
		this.state = state;
	}
	
	public AuditTask(Date createTime, String clientId, String thridId,
			String userName, String auditId, String orgCode, String orgName,
			String type, String name, String busCode,
			String auditStartOrgCode, String auditEndOrgCode,
			String auditOrgCode, String auditState, String state,
			Date auditTime) {
		this.createTime = createTime;
		this.clientId = clientId;
		this.thridId = thridId;
		this.userName = userName;
		this.auditId = auditId;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.type = type;
		this.name = name;
		this.busCode = busCode;
		this.auditStartOrgCode = auditStartOrgCode;
		this.auditEndOrgCode = auditEndOrgCode;
		this.auditOrgCode = auditOrgCode;
		this.auditState = auditState;
		this.state = state;
		this.auditTime = auditTime;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getThridId() {
		return thridId;
	}
	public void setThridId(String thridId) {
		this.thridId = thridId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBusCode() {
		return busCode;
	}
	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}
	public String getAuditStartOrgCode() {
		return auditStartOrgCode;
	}
	public void setAuditStartOrgCode(String auditStartOrgCode) {
		this.auditStartOrgCode = auditStartOrgCode;
	}
	public String getAuditEndOrgCode() {
		return auditEndOrgCode;
	}
	public void setAuditEndOrgCode(String auditEndOrgCode) {
		this.auditEndOrgCode = auditEndOrgCode;
	}
	public String getAuditOrgCode() {
		return auditOrgCode;
	}
	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public Date getStartAuditTime() {
		return startAuditTime;
	}

	public void setStartAuditTime(Date startAuditTime) {
		this.startAuditTime = startAuditTime;
	}

	public Date getEndAuditTime() {
		return endAuditTime;
	}

	public void setEndAuditTime(Date endAuditTime) {
		this.endAuditTime = endAuditTime;
	}
	
	
		
	

}
