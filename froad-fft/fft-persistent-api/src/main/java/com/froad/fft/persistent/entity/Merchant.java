package com.froad.fft.persistent.entity;

import java.util.Date;

import com.froad.fft.persistent.common.enums.DataState;
import com.froad.fft.persistent.common.enums.MerchantType;

/**
 * 商户
 * @author FQ
 *
 */
public class Merchant extends BaseEntity {
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private String name;//名称
	private String fullName;//全名
	private String logo;//公司LOGO
	private String address;//地址
	private String zip;//邮编
	private String fax;//传真
	private String tel;//电话
	private String contactName;//联系人姓名
	private String contactPhone;//联系人电话
	private String contactEmail;//联系人邮件
	private String legalName;//法人名
	private String legalCredentType;//法人证件类型
	private String legalCredentNo;//法人证件号
	
	private Date contractBegintime;//签约时间
	private Date contractEndtime;//到期时间
	private String contractStaff;//签约人员
	private String reviewStaff;//复核人员 
	private Boolean isAudit;//是否审核
	
	private Integer orderValue;//排序
	
	private MerchantType type;//商户类型
	private Long areaId;//所在地区
	private Long merchantCategoryId;//所属分类
	private Long clientId;//所属客户端
	
	private DataState dataState; //'数据状态10-创建，20-录入，30-启用，40-停用，50-删除 '
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegalCredentType() {
		return legalCredentType;
	}

	public void setLegalCredentType(String legalCredentType) {
		this.legalCredentType = legalCredentType;
	}

	public String getLegalCredentNo() {
		return legalCredentNo;
	}

	public void setLegalCredentNo(String legalCredentNo) {
		this.legalCredentNo = legalCredentNo;
	}

	public Date getContractBegintime() {
		return contractBegintime;
	}

	public void setContractBegintime(Date contractBegintime) {
		this.contractBegintime = contractBegintime;
	}

	public Date getContractEndtime() {
		return contractEndtime;
	}

	public void setContractEndtime(Date contractEndtime) {
		this.contractEndtime = contractEndtime;
	}

	public String getContractStaff() {
		return contractStaff;
	}
	public void setContractStaff(String contractStaff) {
		this.contractStaff = contractStaff;
	}
	public String getReviewStaff() {
		return reviewStaff;
	}
	public void setReviewStaff(String reviewStaff) {
		this.reviewStaff = reviewStaff;
	}
	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public MerchantType getType() {
		return type;
	}

	public void setType(MerchantType type) {
		this.type = type;
	}
	
	public Long getMerchantCategoryId() {
		return merchantCategoryId;
	}

	public void setMerchantCategoryId(Long merchantCategoryId) {
		this.merchantCategoryId = merchantCategoryId;
	}
	public Boolean getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(Boolean isAudit) {
		this.isAudit = isAudit;
	}
	public DataState getDataState() {
		return dataState;
	}
	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}
	
	
}
