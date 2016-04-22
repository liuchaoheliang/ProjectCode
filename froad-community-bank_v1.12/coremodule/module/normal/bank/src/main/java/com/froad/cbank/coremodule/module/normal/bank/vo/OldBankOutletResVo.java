package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName: OldBankOutletResVo
 * @Description: 返回原门店属性的vo
 * @author ming
 * @date 2015年10月26日 上午10:42:50
 *
 */
public class OldBankOutletResVo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 2327718367327314763L;

	private String outletName;// 门店简称
	private String outletFullName;// 门店全称
	private String phone;// 联系人电话
	private String address;// 地址
	private String contactName;// 联系人名字
	private String contactPhone;// 联系人手机
	private String discountCode;// 优惠码
	private String discountRate;// 优惠比
	private String acctName;// 收款账户名
	private String acctNumber;// 收款账户号
	private String outletCategoryName;// 所属分类
	private long areaId;// 区域id

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getOutletFullName() {
		return outletFullName;
	}

	public void setOutletFullName(String outletFullName) {
		this.outletFullName = outletFullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}

	public String getOutletCategoryName() {
		return outletCategoryName;
	}

	public void setOutletCategoryName(String outletCategoryName) {
		this.outletCategoryName = outletCategoryName;
	}

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

}
