package com.froad.po;

import java.io.Serializable;
import java.util.Date;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Min;

/**
 * 门店修改审核变更表 po
 * @author user
 *
 */
public class OutletTemp implements Serializable {

	/**
	 * 序列化.
	 */
	private static final long serialVersionUID = 2967884857645240856L;
	
	private Long id;						//主键Id
	private Date createTime;				//创建时间
	
	@MaxLength(value = 32, message = "客户端id不能超过{max}个字符")
	private String clientId;				//客户端Id
	
	@MaxLength(value = 20, message = "审核流水号不能超过{max}个字符")
	private String auditId;					//审核流水号 -对应AuditTask的auditId
	
	@MaxLength(value = 32, message = "商户Id	不能超过{max}个字符")
	private String  merchantId;				//商户Id		
	
	@MaxLength(value = 32, message = "门店Id	不能超过{max}个字符")
	private String outletId;                //门店Id
	
	@Min(1)
	private Long areaId;                  //地区Id
	
	@MaxLength(value = 32, message = "门店名称不能超过{max}个字符")
	private String outletName;              //门店名称
	
	@MaxLength(value = 64, message = "门店全称不能超过{max}个字符")
	private String outletFullName;          //门店全称
	
	@MaxLength(value = 240, message = "地址不能超过{max}个字符")
	private String address;                 //地址
	
	@MaxLength(value = 100, message = "营业时间不能超过{max}个字符")
	private String businessHours;           //营业时间
	
	@MaxLength(value = 16, message = "邮政编码不能超过{max}个字符")
	private String zip;                     //邮政编码
	
	@MaxLength(value = 20, message = "传真不能超过{max}个字符")
	private String fax;                     //传真
	
	@MaxLength(value = 20, message = "电话不能超过{max}个字符")
	private String phone;                   //电话
	
	@MaxLength(value = 16, message = "联系人姓名不能超过{max}个字符")
	private String contactName;             //联系人姓名
	
	@MaxLength(value = 16, message = "联系人电话不能超过{max}个字符")
	private String contactPhone;            //联系人电话
	
	@MaxLength(value = 32, message = "联系人邮箱不能超过{max}个字符")
	private String contactEmail;            //联系人邮箱
	
	private String description;             //描述
	private String preferDetails;           //优惠详情
	private String discount;                //折扣
	
	@MaxLength(value = 64, message = "优惠折扣码不能超过{max}个字符")
	private String discountCode;            //优惠折扣码
	
	@MaxLength(value = 3, message = "优惠折扣比不能超过{max}个字符")
	private String discountRate;            //优惠折扣比
	
	@MaxLength(value = 100, message = "账户名不能超过{max}个字符")
	private String acctName;                //账户名
	
	@MaxLength(value = 32, message = "账户号不能超过{max}个字符")
	private String acctNumber;              //账户号
	
	private String photoList;               //相册列表
	private String primeval;                //原始门店信息
	
	private String outletCategoryId;		//门店分类Id
	private String outletCategoryName;	//门店分类名称
	
	private String longitude;//经度
	private String latitude;//纬度
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
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
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
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
	public String getBusinessHours() {
		return businessHours;
	}
	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPreferDetails() {
		return preferDetails;
	}
	public void setPreferDetails(String preferDetails) {
		this.preferDetails = preferDetails;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
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
	public String getPhotoList() {
		return photoList;
	}
	public void setPhotoList(String photoList) {
		this.photoList = photoList;
	}
	public String getPrimeval() {
		return primeval;
	}
	public void setPrimeval(String primeval) {
		this.primeval = primeval;
	}	
	public String getOutletCategoryId() {
		return outletCategoryId;
	}
	public void setOutletCategoryId(String outletCategoryId) {
		this.outletCategoryId = outletCategoryId;
	}
	public String getOutletCategoryName() {
		return outletCategoryName;
	}
	public void setOutletCategoryName(String outletCategoryName) {
		this.outletCategoryName = outletCategoryName;
	}
	
	public OutletTemp(Long id, Date createTime, String clientId,
			String auditId, String merchantId, String outletId, Long areaId,
			String outletName, String outletFullName, String address,
			String businessHours, String zip, String fax, String phone,
			String contactName, String contactPhone, String contactEmail,
			String description, String preferDetails, String discount,
			String discountCode, String discountRate, String acctName,
			String acctNumber, String photoList, String primeval,
			String outletCategoryId, String outletCategoryName,
			String longitude, String latitude) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.clientId = clientId;
		this.auditId = auditId;
		this.merchantId = merchantId;
		this.outletId = outletId;
		this.areaId = areaId;
		this.outletName = outletName;
		this.outletFullName = outletFullName;
		this.address = address;
		this.businessHours = businessHours;
		this.zip = zip;
		this.fax = fax;
		this.phone = phone;
		this.contactName = contactName;
		this.contactPhone = contactPhone;
		this.contactEmail = contactEmail;
		this.description = description;
		this.preferDetails = preferDetails;
		this.discount = discount;
		this.discountCode = discountCode;
		this.discountRate = discountRate;
		this.acctName = acctName;
		this.acctNumber = acctNumber;
		this.photoList = photoList;
		this.primeval = primeval;
		this.outletCategoryId = outletCategoryId;
		this.outletCategoryName = outletCategoryName;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public OutletTemp() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((acctName == null) ? 0 : acctName.hashCode());
		result = prime * result
				+ ((acctNumber == null) ? 0 : acctNumber.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result + ((auditId == null) ? 0 : auditId.hashCode());
		result = prime * result
				+ ((businessHours == null) ? 0 : businessHours.hashCode());
		result = prime * result
				+ ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result
				+ ((contactEmail == null) ? 0 : contactEmail.hashCode());
		result = prime * result
				+ ((contactName == null) ? 0 : contactName.hashCode());
		result = prime * result
				+ ((contactPhone == null) ? 0 : contactPhone.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((discount == null) ? 0 : discount.hashCode());
		result = prime * result
				+ ((discountCode == null) ? 0 : discountCode.hashCode());
		result = prime * result
				+ ((discountRate == null) ? 0 : discountRate.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result
				+ ((merchantId == null) ? 0 : merchantId.hashCode());
		result = prime
				* result
				+ ((outletCategoryId == null) ? 0 : outletCategoryId.hashCode());
		result = prime
				* result
				+ ((outletCategoryName == null) ? 0 : outletCategoryName
						.hashCode());
		result = prime * result
				+ ((outletFullName == null) ? 0 : outletFullName.hashCode());
		result = prime * result
				+ ((outletId == null) ? 0 : outletId.hashCode());
		result = prime * result
				+ ((outletName == null) ? 0 : outletName.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result
				+ ((photoList == null) ? 0 : photoList.hashCode());
		result = prime * result
				+ ((preferDetails == null) ? 0 : preferDetails.hashCode());
		result = prime * result
				+ ((primeval == null) ? 0 : primeval.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutletTemp other = (OutletTemp) obj;
		if (acctName == null) {
			if (other.acctName != null)
				return false;
		} else if (!acctName.equals(other.acctName))
			return false;
		if (acctNumber == null) {
			if (other.acctNumber != null)
				return false;
		} else if (!acctNumber.equals(other.acctNumber))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (auditId == null) {
			if (other.auditId != null)
				return false;
		} else if (!auditId.equals(other.auditId))
			return false;
		if (businessHours == null) {
			if (other.businessHours != null)
				return false;
		} else if (!businessHours.equals(other.businessHours))
			return false;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (contactEmail == null) {
			if (other.contactEmail != null)
				return false;
		} else if (!contactEmail.equals(other.contactEmail))
			return false;
		if (contactName == null) {
			if (other.contactName != null)
				return false;
		} else if (!contactName.equals(other.contactName))
			return false;
		if (contactPhone == null) {
			if (other.contactPhone != null)
				return false;
		} else if (!contactPhone.equals(other.contactPhone))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (discountCode == null) {
			if (other.discountCode != null)
				return false;
		} else if (!discountCode.equals(other.discountCode))
			return false;
		if (discountRate == null) {
			if (other.discountRate != null)
				return false;
		} else if (!discountRate.equals(other.discountRate))
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (merchantId == null) {
			if (other.merchantId != null)
				return false;
		} else if (!merchantId.equals(other.merchantId))
			return false;
		if (outletCategoryId == null) {
			if (other.outletCategoryId != null)
				return false;
		} else if (!outletCategoryId.equals(other.outletCategoryId))
			return false;
		if (outletCategoryName == null) {
			if (other.outletCategoryName != null)
				return false;
		} else if (!outletCategoryName.equals(other.outletCategoryName))
			return false;
		if (outletFullName == null) {
			if (other.outletFullName != null)
				return false;
		} else if (!outletFullName.equals(other.outletFullName))
			return false;
		if (outletId == null) {
			if (other.outletId != null)
				return false;
		} else if (!outletId.equals(other.outletId))
			return false;
		if (outletName == null) {
			if (other.outletName != null)
				return false;
		} else if (!outletName.equals(other.outletName))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (photoList == null) {
			if (other.photoList != null)
				return false;
		} else if (!photoList.equals(other.photoList))
			return false;
		if (preferDetails == null) {
			if (other.preferDetails != null)
				return false;
		} else if (!preferDetails.equals(other.preferDetails))
			return false;
		if (primeval == null) {
			if (other.primeval != null)
				return false;
		} else if (!primeval.equals(other.primeval))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "OutletTemp [id=" + id + ", createTime=" + createTime
				+ ", clientId=" + clientId + ", auditId=" + auditId
				+ ", merchantId=" + merchantId + ", outletId=" + outletId
				+ ", areaId=" + areaId + ", outletName=" + outletName
				+ ", outletFullName=" + outletFullName + ", address=" + address
				+ ", businessHours=" + businessHours + ", zip=" + zip
				+ ", fax=" + fax + ", phone=" + phone + ", contactName="
				+ contactName + ", contactPhone=" + contactPhone
				+ ", contactEmail=" + contactEmail + ", description="
				+ description + ", preferDetails=" + preferDetails
				+ ", discount=" + discount + ", discountCode=" + discountCode
				+ ", discountRate=" + discountRate + ", acctName=" + acctName
				+ ", acctNumber=" + acctNumber + ", photoList=" + photoList
				+ ", primeval=" + primeval + ", outletCategoryId="
				+ outletCategoryId + ", outletCategoryName="
				+ outletCategoryName + ", longitude=" + longitude
				+ ", latitude=" + latitude + "]";
	}	
}