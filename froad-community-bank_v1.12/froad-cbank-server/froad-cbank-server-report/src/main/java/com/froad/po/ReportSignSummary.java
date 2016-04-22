package com.froad.po;

import java.util.Date;

/**签约商户汇总
 * @ClassName: ReportSignSummary 
 * @Description:  
 * @author longyunbo
 * @date 2015年5月21日 下午6:01:30 
 */
public class ReportSignSummary {
	private Long id;					//编号
	private Date createTime;			//日期
	private String clientId;			//客户端
	private String forgCode;			//一级机构号
	private String forgName;			//一级机构名
	private String sorgCode;			//二级机构号
	private String sorgName;			//二级机构名
	private String torgCode;			//三级机构号
	private String torgName;			//三级机构名
	private String lorgCode;			//四级机构号
	private String lorgName;			//四级机构名
	private String signUserName;		//签约人名
	private Long totalMerchants;		//商户总数
	
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
	public String getForgCode() {
		return forgCode;
	}
	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}
	public String getForgName() {
		return forgName;
	}
	public void setForgName(String forgName) {
		this.forgName = forgName;
	}
	public String getSorgCode() {
		return sorgCode;
	}
	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}
	public String getSorgName() {
		return sorgName;
	}
	public void setSorgName(String sorgName) {
		this.sorgName = sorgName;
	}
	public String getTorgCode() {
		return torgCode;
	}
	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}
	public String getTorgName() {
		return torgName;
	}
	public void setTorgName(String torgName) {
		this.torgName = torgName;
	}
	public String getLorgCode() {
		return lorgCode;
	}
	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}
	public String getLorgName() {
		return lorgName;
	}
	public void setLorgName(String lorgName) {
		this.lorgName = lorgName;
	}
	public String getSignUserName() {
		return signUserName;
	}
	public void setSignUserName(String signUserName) {
		this.signUserName = signUserName;
	}
	public Long getTotalMerchants() {
		return totalMerchants;
	}
	public void setTotalMerchants(Long totalMerchants) {
		this.totalMerchants = totalMerchants;
	}
	
}
