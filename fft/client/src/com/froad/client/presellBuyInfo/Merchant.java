
package com.froad.client.presellBuyInfo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for merchant complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="merchant">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="activityId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="auditStaff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessLicenseUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientImg1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientImg2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyContactName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyContactTel1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyContactTel2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyFax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyFullName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyLegalCredentNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyLegalCredentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyLegalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyLogoFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyLogoUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyShortName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyTel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyZip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dcHyperlink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hotLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isAffordPresellFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isHot" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isInternalAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isSpringActivity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legalIdcardUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localBankId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localStationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mcompanyContactEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mcontractBegintime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mcontractEndtime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mcontractStaff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchantPresent" type="{http://service.CB.froad.com/}merchantPresent" minOccurs="0"/>
 *         &lt;element name="merchantPriority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchantTrain" type="{http://service.CB.froad.com/}merchantTrain" minOccurs="0"/>
 *         &lt;element name="mreviewStaff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreContactEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreContactName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreContactTel1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreContactTel2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreFax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreFullName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreLogoFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreLogoUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreShortName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreTel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mstoreZip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organizationCodeCertificateUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="photoList" type="{http://service.CB.froad.com/}merchantPhoto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pointRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preferenceDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preferentialRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preferentialType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productList" type="{http://service.CB.froad.com/}merchantProduct" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stateList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="storeList" type="{http://service.CB.froad.com/}store" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tagClassifyAId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagClassifyAList" type="{http://service.CB.froad.com/}tagClassifyA" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tagClassifyBId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagClassifyBList" type="{http://service.CB.froad.com/}tagClassifyB" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tagDistrictAId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagDistrictAList" type="{http://service.CB.froad.com/}tagDistrictA" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tagDistrictBId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagDistrictBList" type="{http://service.CB.froad.com/}tagDistrictB" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tagMapList" type="{http://service.CB.froad.com/}tagMAP" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tagValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="taxRegistrationCertificateUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="user" type="{http://service.CB.froad.com/}user" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mPreferentialList" type="{http://service.CB.froad.com/}merchantPreferential" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mPresent" type="{http://service.CB.froad.com/}merchantPresent" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "merchant", propOrder = {
    "activityId",
    "auditStaff",
    "businessLicenseUrl",
    "clientImg1",
    "clientImg2",
    "companyAddress",
    "companyContactName",
    "companyContactTel1",
    "companyContactTel2",
    "companyFax",
    "companyFullName",
    "companyLegalCredentNo",
    "companyLegalCredentType",
    "companyLegalName",
    "companyLogoFormat",
    "companyLogoUrl",
    "companyShortName",
    "companyTel",
    "companyZip",
    "createTime",
    "dcHyperlink",
    "hotLevel",
    "id",
    "isAffordPresellFlag",
    "isHot",
    "isInternalAccount",
    "isSpringActivity",
    "legalIdcardUrl",
    "localBankId",
    "localStationId",
    "mcompanyContactEmail",
    "mcontractBegintime",
    "mcontractEndtime",
    "mcontractStaff",
    "merchantPresent",
    "merchantPriority",
    "merchantTrain",
    "mreviewStaff",
    "mstoreAddress",
    "mstoreContactEmail",
    "mstoreContactName",
    "mstoreContactTel1",
    "mstoreContactTel2",
    "mstoreFax",
    "mstoreFullName",
    "mstoreLogoFormat",
    "mstoreLogoUrl",
    "mstoreShortName",
    "mstoreTel",
    "mstoreZip",
    "organizationCodeCertificateUrl",
    "photoList",
    "pointRate",
    "preferenceDesc",
    "preferentialRate",
    "preferentialType",
    "productList",
    "remark",
    "respCode",
    "respMsg",
    "state",
    "stateList",
    "storeList",
    "tagClassifyAId",
    "tagClassifyAList",
    "tagClassifyBId",
    "tagClassifyBList",
    "tagDistrictAId",
    "tagDistrictAList",
    "tagDistrictBId",
    "tagDistrictBList",
    "tagMapList",
    "tagValue",
    "taxRegistrationCertificateUrl",
    "updateTime",
    "user",
    "userId",
    "mPreferentialList",
    "mPresent"
})
public class Merchant
    extends Pager
{

    protected String activityId;
    protected String auditStaff;
    protected String businessLicenseUrl;
    protected String clientImg1;
    protected String clientImg2;
    protected String companyAddress;
    protected String companyContactName;
    protected String companyContactTel1;
    protected String companyContactTel2;
    protected String companyFax;
    protected String companyFullName;
    protected String companyLegalCredentNo;
    protected String companyLegalCredentType;
    protected String companyLegalName;
    protected String companyLogoFormat;
    protected String companyLogoUrl;
    protected String companyShortName;
    protected String companyTel;
    protected String companyZip;
    protected String createTime;
    protected String dcHyperlink;
    protected String hotLevel;
    protected Integer id;
    protected String isAffordPresellFlag;
    protected String isHot;
    protected String isInternalAccount;
    protected String isSpringActivity;
    protected String legalIdcardUrl;
    protected String localBankId;
    protected String localStationId;
    protected String mcompanyContactEmail;
    protected String mcontractBegintime;
    protected String mcontractEndtime;
    protected String mcontractStaff;
    protected MerchantPresent merchantPresent;
    protected String merchantPriority;
    protected MerchantTrain merchantTrain;
    protected String mreviewStaff;
    protected String mstoreAddress;
    protected String mstoreContactEmail;
    protected String mstoreContactName;
    protected String mstoreContactTel1;
    protected String mstoreContactTel2;
    protected String mstoreFax;
    protected String mstoreFullName;
    protected String mstoreLogoFormat;
    protected String mstoreLogoUrl;
    protected String mstoreShortName;
    protected String mstoreTel;
    protected String mstoreZip;
    protected String organizationCodeCertificateUrl;
    @XmlElement(nillable = true)
    protected List<MerchantPhoto> photoList;
    protected String pointRate;
    protected String preferenceDesc;
    protected String preferentialRate;
    protected String preferentialType;
    @XmlElement(nillable = true)
    protected List<MerchantProduct> productList;
    protected String remark;
    protected String respCode;
    protected String respMsg;
    protected String state;
    @XmlElement(nillable = true)
    protected List<String> stateList;
    @XmlElement(nillable = true)
    protected List<Store> storeList;
    protected String tagClassifyAId;
    @XmlElement(nillable = true)
    protected List<TagClassifyA> tagClassifyAList;
    protected String tagClassifyBId;
    @XmlElement(nillable = true)
    protected List<TagClassifyB> tagClassifyBList;
    protected String tagDistrictAId;
    @XmlElement(nillable = true)
    protected List<TagDistrictA> tagDistrictAList;
    protected String tagDistrictBId;
    @XmlElement(nillable = true)
    protected List<TagDistrictB> tagDistrictBList;
    @XmlElement(nillable = true)
    protected List<TagMAP> tagMapList;
    protected String tagValue;
    protected String taxRegistrationCertificateUrl;
    protected String updateTime;
    protected User user;
    protected String userId;
    @XmlElement(nillable = true)
    protected List<MerchantPreferential> mPreferentialList;
    protected MerchantPresent mPresent;

    /**
     * Gets the value of the activityId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * Sets the value of the activityId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityId(String value) {
        this.activityId = value;
    }

    /**
     * Gets the value of the auditStaff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditStaff() {
        return auditStaff;
    }

    /**
     * Sets the value of the auditStaff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditStaff(String value) {
        this.auditStaff = value;
    }

    /**
     * Gets the value of the businessLicenseUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessLicenseUrl() {
        return businessLicenseUrl;
    }

    /**
     * Sets the value of the businessLicenseUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessLicenseUrl(String value) {
        this.businessLicenseUrl = value;
    }

    /**
     * Gets the value of the clientImg1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientImg1() {
        return clientImg1;
    }

    /**
     * Sets the value of the clientImg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientImg1(String value) {
        this.clientImg1 = value;
    }

    /**
     * Gets the value of the clientImg2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientImg2() {
        return clientImg2;
    }

    /**
     * Sets the value of the clientImg2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientImg2(String value) {
        this.clientImg2 = value;
    }

    /**
     * Gets the value of the companyAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * Sets the value of the companyAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyAddress(String value) {
        this.companyAddress = value;
    }

    /**
     * Gets the value of the companyContactName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyContactName() {
        return companyContactName;
    }

    /**
     * Sets the value of the companyContactName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyContactName(String value) {
        this.companyContactName = value;
    }

    /**
     * Gets the value of the companyContactTel1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyContactTel1() {
        return companyContactTel1;
    }

    /**
     * Sets the value of the companyContactTel1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyContactTel1(String value) {
        this.companyContactTel1 = value;
    }

    /**
     * Gets the value of the companyContactTel2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyContactTel2() {
        return companyContactTel2;
    }

    /**
     * Sets the value of the companyContactTel2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyContactTel2(String value) {
        this.companyContactTel2 = value;
    }

    /**
     * Gets the value of the companyFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyFax() {
        return companyFax;
    }

    /**
     * Sets the value of the companyFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyFax(String value) {
        this.companyFax = value;
    }

    /**
     * Gets the value of the companyFullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyFullName() {
        return companyFullName;
    }

    /**
     * Sets the value of the companyFullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyFullName(String value) {
        this.companyFullName = value;
    }

    /**
     * Gets the value of the companyLegalCredentNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyLegalCredentNo() {
        return companyLegalCredentNo;
    }

    /**
     * Sets the value of the companyLegalCredentNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyLegalCredentNo(String value) {
        this.companyLegalCredentNo = value;
    }

    /**
     * Gets the value of the companyLegalCredentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyLegalCredentType() {
        return companyLegalCredentType;
    }

    /**
     * Sets the value of the companyLegalCredentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyLegalCredentType(String value) {
        this.companyLegalCredentType = value;
    }

    /**
     * Gets the value of the companyLegalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyLegalName() {
        return companyLegalName;
    }

    /**
     * Sets the value of the companyLegalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyLegalName(String value) {
        this.companyLegalName = value;
    }

    /**
     * Gets the value of the companyLogoFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyLogoFormat() {
        return companyLogoFormat;
    }

    /**
     * Sets the value of the companyLogoFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyLogoFormat(String value) {
        this.companyLogoFormat = value;
    }

    /**
     * Gets the value of the companyLogoUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    /**
     * Sets the value of the companyLogoUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyLogoUrl(String value) {
        this.companyLogoUrl = value;
    }

    /**
     * Gets the value of the companyShortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyShortName() {
        return companyShortName;
    }

    /**
     * Sets the value of the companyShortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyShortName(String value) {
        this.companyShortName = value;
    }

    /**
     * Gets the value of the companyTel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyTel() {
        return companyTel;
    }

    /**
     * Sets the value of the companyTel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyTel(String value) {
        this.companyTel = value;
    }

    /**
     * Gets the value of the companyZip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyZip() {
        return companyZip;
    }

    /**
     * Sets the value of the companyZip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyZip(String value) {
        this.companyZip = value;
    }

    /**
     * Gets the value of the createTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * Sets the value of the createTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateTime(String value) {
        this.createTime = value;
    }

    /**
     * Gets the value of the dcHyperlink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDcHyperlink() {
        return dcHyperlink;
    }

    /**
     * Sets the value of the dcHyperlink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDcHyperlink(String value) {
        this.dcHyperlink = value;
    }

    /**
     * Gets the value of the hotLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHotLevel() {
        return hotLevel;
    }

    /**
     * Sets the value of the hotLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHotLevel(String value) {
        this.hotLevel = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the isAffordPresellFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAffordPresellFlag() {
        return isAffordPresellFlag;
    }

    /**
     * Sets the value of the isAffordPresellFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAffordPresellFlag(String value) {
        this.isAffordPresellFlag = value;
    }

    /**
     * Gets the value of the isHot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHot() {
        return isHot;
    }

    /**
     * Sets the value of the isHot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHot(String value) {
        this.isHot = value;
    }

    /**
     * Gets the value of the isInternalAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsInternalAccount() {
        return isInternalAccount;
    }

    /**
     * Sets the value of the isInternalAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsInternalAccount(String value) {
        this.isInternalAccount = value;
    }

    /**
     * Gets the value of the isSpringActivity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsSpringActivity() {
        return isSpringActivity;
    }

    /**
     * Sets the value of the isSpringActivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsSpringActivity(String value) {
        this.isSpringActivity = value;
    }

    /**
     * Gets the value of the legalIdcardUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalIdcardUrl() {
        return legalIdcardUrl;
    }

    /**
     * Sets the value of the legalIdcardUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalIdcardUrl(String value) {
        this.legalIdcardUrl = value;
    }

    /**
     * Gets the value of the localBankId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalBankId() {
        return localBankId;
    }

    /**
     * Sets the value of the localBankId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalBankId(String value) {
        this.localBankId = value;
    }

    /**
     * Gets the value of the localStationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalStationId() {
        return localStationId;
    }

    /**
     * Sets the value of the localStationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalStationId(String value) {
        this.localStationId = value;
    }

    /**
     * Gets the value of the mcompanyContactEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMcompanyContactEmail() {
        return mcompanyContactEmail;
    }

    /**
     * Sets the value of the mcompanyContactEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMcompanyContactEmail(String value) {
        this.mcompanyContactEmail = value;
    }

    /**
     * Gets the value of the mcontractBegintime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMcontractBegintime() {
        return mcontractBegintime;
    }

    /**
     * Sets the value of the mcontractBegintime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMcontractBegintime(String value) {
        this.mcontractBegintime = value;
    }

    /**
     * Gets the value of the mcontractEndtime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMcontractEndtime() {
        return mcontractEndtime;
    }

    /**
     * Sets the value of the mcontractEndtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMcontractEndtime(String value) {
        this.mcontractEndtime = value;
    }

    /**
     * Gets the value of the mcontractStaff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMcontractStaff() {
        return mcontractStaff;
    }

    /**
     * Sets the value of the mcontractStaff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMcontractStaff(String value) {
        this.mcontractStaff = value;
    }

    /**
     * Gets the value of the merchantPresent property.
     * 
     * @return
     *     possible object is
     *     {@link MerchantPresent }
     *     
     */
    public MerchantPresent getMerchantPresent() {
        return merchantPresent;
    }

    /**
     * Sets the value of the merchantPresent property.
     * 
     * @param value
     *     allowed object is
     *     {@link MerchantPresent }
     *     
     */
    public void setMerchantPresent(MerchantPresent value) {
        this.merchantPresent = value;
    }

    /**
     * Gets the value of the merchantPriority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantPriority() {
        return merchantPriority;
    }

    /**
     * Sets the value of the merchantPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantPriority(String value) {
        this.merchantPriority = value;
    }

    /**
     * Gets the value of the merchantTrain property.
     * 
     * @return
     *     possible object is
     *     {@link MerchantTrain }
     *     
     */
    public MerchantTrain getMerchantTrain() {
        return merchantTrain;
    }

    /**
     * Sets the value of the merchantTrain property.
     * 
     * @param value
     *     allowed object is
     *     {@link MerchantTrain }
     *     
     */
    public void setMerchantTrain(MerchantTrain value) {
        this.merchantTrain = value;
    }

    /**
     * Gets the value of the mreviewStaff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMreviewStaff() {
        return mreviewStaff;
    }

    /**
     * Sets the value of the mreviewStaff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMreviewStaff(String value) {
        this.mreviewStaff = value;
    }

    /**
     * Gets the value of the mstoreAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreAddress() {
        return mstoreAddress;
    }

    /**
     * Sets the value of the mstoreAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreAddress(String value) {
        this.mstoreAddress = value;
    }

    /**
     * Gets the value of the mstoreContactEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreContactEmail() {
        return mstoreContactEmail;
    }

    /**
     * Sets the value of the mstoreContactEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreContactEmail(String value) {
        this.mstoreContactEmail = value;
    }

    /**
     * Gets the value of the mstoreContactName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreContactName() {
        return mstoreContactName;
    }

    /**
     * Sets the value of the mstoreContactName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreContactName(String value) {
        this.mstoreContactName = value;
    }

    /**
     * Gets the value of the mstoreContactTel1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreContactTel1() {
        return mstoreContactTel1;
    }

    /**
     * Sets the value of the mstoreContactTel1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreContactTel1(String value) {
        this.mstoreContactTel1 = value;
    }

    /**
     * Gets the value of the mstoreContactTel2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreContactTel2() {
        return mstoreContactTel2;
    }

    /**
     * Sets the value of the mstoreContactTel2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreContactTel2(String value) {
        this.mstoreContactTel2 = value;
    }

    /**
     * Gets the value of the mstoreFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreFax() {
        return mstoreFax;
    }

    /**
     * Sets the value of the mstoreFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreFax(String value) {
        this.mstoreFax = value;
    }

    /**
     * Gets the value of the mstoreFullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreFullName() {
        return mstoreFullName;
    }

    /**
     * Sets the value of the mstoreFullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreFullName(String value) {
        this.mstoreFullName = value;
    }

    /**
     * Gets the value of the mstoreLogoFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreLogoFormat() {
        return mstoreLogoFormat;
    }

    /**
     * Sets the value of the mstoreLogoFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreLogoFormat(String value) {
        this.mstoreLogoFormat = value;
    }

    /**
     * Gets the value of the mstoreLogoUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreLogoUrl() {
        return mstoreLogoUrl;
    }

    /**
     * Sets the value of the mstoreLogoUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreLogoUrl(String value) {
        this.mstoreLogoUrl = value;
    }

    /**
     * Gets the value of the mstoreShortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreShortName() {
        return mstoreShortName;
    }

    /**
     * Sets the value of the mstoreShortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreShortName(String value) {
        this.mstoreShortName = value;
    }

    /**
     * Gets the value of the mstoreTel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreTel() {
        return mstoreTel;
    }

    /**
     * Sets the value of the mstoreTel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreTel(String value) {
        this.mstoreTel = value;
    }

    /**
     * Gets the value of the mstoreZip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMstoreZip() {
        return mstoreZip;
    }

    /**
     * Sets the value of the mstoreZip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMstoreZip(String value) {
        this.mstoreZip = value;
    }

    /**
     * Gets the value of the organizationCodeCertificateUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationCodeCertificateUrl() {
        return organizationCodeCertificateUrl;
    }

    /**
     * Sets the value of the organizationCodeCertificateUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationCodeCertificateUrl(String value) {
        this.organizationCodeCertificateUrl = value;
    }

    /**
     * Gets the value of the photoList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the photoList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhotoList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MerchantPhoto }
     * 
     * 
     */
    public List<MerchantPhoto> getPhotoList() {
        if (photoList == null) {
            photoList = new ArrayList<MerchantPhoto>();
        }
        return this.photoList;
    }

    /**
     * Gets the value of the pointRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointRate() {
        return pointRate;
    }

    /**
     * Sets the value of the pointRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointRate(String value) {
        this.pointRate = value;
    }

    /**
     * Gets the value of the preferenceDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferenceDesc() {
        return preferenceDesc;
    }

    /**
     * Sets the value of the preferenceDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferenceDesc(String value) {
        this.preferenceDesc = value;
    }

    /**
     * Gets the value of the preferentialRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferentialRate() {
        return preferentialRate;
    }

    /**
     * Sets the value of the preferentialRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferentialRate(String value) {
        this.preferentialRate = value;
    }

    /**
     * Gets the value of the preferentialType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferentialType() {
        return preferentialType;
    }

    /**
     * Sets the value of the preferentialType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferentialType(String value) {
        this.preferentialType = value;
    }

    /**
     * Gets the value of the productList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MerchantProduct }
     * 
     * 
     */
    public List<MerchantProduct> getProductList() {
        if (productList == null) {
            productList = new ArrayList<MerchantProduct>();
        }
        return this.productList;
    }

    /**
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * Gets the value of the respCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * Sets the value of the respCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespCode(String value) {
        this.respCode = value;
    }

    /**
     * Gets the value of the respMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespMsg() {
        return respMsg;
    }

    /**
     * Sets the value of the respMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespMsg(String value) {
        this.respMsg = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the stateList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stateList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStateList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStateList() {
        if (stateList == null) {
            stateList = new ArrayList<String>();
        }
        return this.stateList;
    }

    /**
     * Gets the value of the storeList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the storeList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStoreList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Store }
     * 
     * 
     */
    public List<Store> getStoreList() {
        if (storeList == null) {
            storeList = new ArrayList<Store>();
        }
        return this.storeList;
    }

    /**
     * Gets the value of the tagClassifyAId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagClassifyAId() {
        return tagClassifyAId;
    }

    /**
     * Sets the value of the tagClassifyAId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagClassifyAId(String value) {
        this.tagClassifyAId = value;
    }

    /**
     * Gets the value of the tagClassifyAList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tagClassifyAList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTagClassifyAList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TagClassifyA }
     * 
     * 
     */
    public List<TagClassifyA> getTagClassifyAList() {
        if (tagClassifyAList == null) {
            tagClassifyAList = new ArrayList<TagClassifyA>();
        }
        return this.tagClassifyAList;
    }

    /**
     * Gets the value of the tagClassifyBId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagClassifyBId() {
        return tagClassifyBId;
    }

    /**
     * Sets the value of the tagClassifyBId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagClassifyBId(String value) {
        this.tagClassifyBId = value;
    }

    /**
     * Gets the value of the tagClassifyBList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tagClassifyBList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTagClassifyBList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TagClassifyB }
     * 
     * 
     */
    public List<TagClassifyB> getTagClassifyBList() {
        if (tagClassifyBList == null) {
            tagClassifyBList = new ArrayList<TagClassifyB>();
        }
        return this.tagClassifyBList;
    }

    /**
     * Gets the value of the tagDistrictAId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagDistrictAId() {
        return tagDistrictAId;
    }

    /**
     * Sets the value of the tagDistrictAId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagDistrictAId(String value) {
        this.tagDistrictAId = value;
    }

    /**
     * Gets the value of the tagDistrictAList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tagDistrictAList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTagDistrictAList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TagDistrictA }
     * 
     * 
     */
    public List<TagDistrictA> getTagDistrictAList() {
        if (tagDistrictAList == null) {
            tagDistrictAList = new ArrayList<TagDistrictA>();
        }
        return this.tagDistrictAList;
    }

    /**
     * Gets the value of the tagDistrictBId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagDistrictBId() {
        return tagDistrictBId;
    }

    /**
     * Sets the value of the tagDistrictBId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagDistrictBId(String value) {
        this.tagDistrictBId = value;
    }

    /**
     * Gets the value of the tagDistrictBList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tagDistrictBList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTagDistrictBList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TagDistrictB }
     * 
     * 
     */
    public List<TagDistrictB> getTagDistrictBList() {
        if (tagDistrictBList == null) {
            tagDistrictBList = new ArrayList<TagDistrictB>();
        }
        return this.tagDistrictBList;
    }

    /**
     * Gets the value of the tagMapList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tagMapList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTagMapList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TagMAP }
     * 
     * 
     */
    public List<TagMAP> getTagMapList() {
        if (tagMapList == null) {
            tagMapList = new ArrayList<TagMAP>();
        }
        return this.tagMapList;
    }

    /**
     * Gets the value of the tagValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagValue() {
        return tagValue;
    }

    /**
     * Sets the value of the tagValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagValue(String value) {
        this.tagValue = value;
    }

    /**
     * Gets the value of the taxRegistrationCertificateUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxRegistrationCertificateUrl() {
        return taxRegistrationCertificateUrl;
    }

    /**
     * Sets the value of the taxRegistrationCertificateUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxRegistrationCertificateUrl(String value) {
        this.taxRegistrationCertificateUrl = value;
    }

    /**
     * Gets the value of the updateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets the value of the updateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateTime(String value) {
        this.updateTime = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUser(User value) {
        this.user = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the mPreferentialList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mPreferentialList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMPreferentialList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MerchantPreferential }
     * 
     * 
     */
    public List<MerchantPreferential> getMPreferentialList() {
        if (mPreferentialList == null) {
            mPreferentialList = new ArrayList<MerchantPreferential>();
        }
        return this.mPreferentialList;
    }

    /**
     * Gets the value of the mPresent property.
     * 
     * @return
     *     possible object is
     *     {@link MerchantPresent }
     *     
     */
    public MerchantPresent getMPresent() {
        return mPresent;
    }

    /**
     * Sets the value of the mPresent property.
     * 
     * @param value
     *     allowed object is
     *     {@link MerchantPresent }
     *     
     */
    public void setMPresent(MerchantPresent value) {
        this.mPresent = value;
    }

}
