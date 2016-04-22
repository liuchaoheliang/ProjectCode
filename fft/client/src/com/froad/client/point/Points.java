
package com.froad.client.point;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for points complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="points">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountMarked" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountMarkedType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankCard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="charset" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exchPointsNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobilePhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objectDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objectNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objectType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgPoints" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partnerNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payPointsNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="points" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pointsAccountList" type="{http://service.CB.froad.com/}pointsAccount" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pointsCateNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presentPointsNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="realName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiveReqTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refundPointsNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="withdrawPoints" type="{http://service.CB.froad.com/}withdrawPoints" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "points", propOrder = {
    "accountId",
    "accountMarked",
    "accountMarkedType",
    "bankCard",
    "bankId",
    "bankName",
    "businessType",
    "charset",
    "exchPointsNo",
    "mobilePhone",
    "objectDes",
    "objectNo",
    "objectType",
    "orgNo",
    "orgPoints",
    "partnerNo",
    "payPointsNo",
    "points",
    "pointsAccountList",
    "pointsCateNo",
    "presentPointsNo",
    "realName",
    "receiveReqTime",
    "refundPointsNo",
    "remark",
    "requestNo",
    "respCode",
    "respMsg",
    "resultCode",
    "signMsg",
    "signType",
    "withdrawPoints"
})
public class Points {

    protected String accountId;
    protected String accountMarked;
    protected String accountMarkedType;
    protected String bankCard;
    protected String bankId;
    protected String bankName;
    protected String businessType;
    protected String charset;
    protected String exchPointsNo;
    protected String mobilePhone;
    protected String objectDes;
    protected String objectNo;
    protected String objectType;
    protected String orgNo;
    protected String orgPoints;
    protected String partnerNo;
    protected String payPointsNo;
    protected String points;
    @XmlElement(nillable = true)
    protected List<PointsAccount> pointsAccountList;
    protected String pointsCateNo;
    protected String presentPointsNo;
    protected String realName;
    protected String receiveReqTime;
    protected String refundPointsNo;
    protected String remark;
    protected String requestNo;
    protected String respCode;
    protected String respMsg;
    protected String resultCode;
    protected String signMsg;
    protected String signType;
    protected WithdrawPoints withdrawPoints;

    /**
     * Gets the value of the accountId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Sets the value of the accountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountId(String value) {
        this.accountId = value;
    }

    /**
     * Gets the value of the accountMarked property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountMarked() {
        return accountMarked;
    }

    /**
     * Sets the value of the accountMarked property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountMarked(String value) {
        this.accountMarked = value;
    }

    /**
     * Gets the value of the accountMarkedType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountMarkedType() {
        return accountMarkedType;
    }

    /**
     * Sets the value of the accountMarkedType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountMarkedType(String value) {
        this.accountMarkedType = value;
    }

    /**
     * Gets the value of the bankCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCard() {
        return bankCard;
    }

    /**
     * Sets the value of the bankCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCard(String value) {
        this.bankCard = value;
    }

    /**
     * Gets the value of the bankId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * Sets the value of the bankId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankId(String value) {
        this.bankId = value;
    }

    /**
     * Gets the value of the bankName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Sets the value of the bankName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankName(String value) {
        this.bankName = value;
    }

    /**
     * Gets the value of the businessType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * Sets the value of the businessType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessType(String value) {
        this.businessType = value;
    }

    /**
     * Gets the value of the charset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Sets the value of the charset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharset(String value) {
        this.charset = value;
    }

    /**
     * Gets the value of the exchPointsNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchPointsNo() {
        return exchPointsNo;
    }

    /**
     * Sets the value of the exchPointsNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchPointsNo(String value) {
        this.exchPointsNo = value;
    }

    /**
     * Gets the value of the mobilePhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Sets the value of the mobilePhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobilePhone(String value) {
        this.mobilePhone = value;
    }

    /**
     * Gets the value of the objectDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectDes() {
        return objectDes;
    }

    /**
     * Sets the value of the objectDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectDes(String value) {
        this.objectDes = value;
    }

    /**
     * Gets the value of the objectNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectNo() {
        return objectNo;
    }

    /**
     * Sets the value of the objectNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectNo(String value) {
        this.objectNo = value;
    }

    /**
     * Gets the value of the objectType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * Sets the value of the objectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectType(String value) {
        this.objectType = value;
    }

    /**
     * Gets the value of the orgNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgNo() {
        return orgNo;
    }

    /**
     * Sets the value of the orgNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgNo(String value) {
        this.orgNo = value;
    }

    /**
     * Gets the value of the orgPoints property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgPoints() {
        return orgPoints;
    }

    /**
     * Sets the value of the orgPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgPoints(String value) {
        this.orgPoints = value;
    }

    /**
     * Gets the value of the partnerNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerNo() {
        return partnerNo;
    }

    /**
     * Sets the value of the partnerNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerNo(String value) {
        this.partnerNo = value;
    }

    /**
     * Gets the value of the payPointsNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayPointsNo() {
        return payPointsNo;
    }

    /**
     * Sets the value of the payPointsNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayPointsNo(String value) {
        this.payPointsNo = value;
    }

    /**
     * Gets the value of the points property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoints() {
        return points;
    }

    /**
     * Sets the value of the points property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoints(String value) {
        this.points = value;
    }

    /**
     * Gets the value of the pointsAccountList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pointsAccountList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPointsAccountList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PointsAccount }
     * 
     * 
     */
    public List<PointsAccount> getPointsAccountList() {
        if (pointsAccountList == null) {
            pointsAccountList = new ArrayList<PointsAccount>();
        }
        return this.pointsAccountList;
    }

    /**
     * Gets the value of the pointsCateNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointsCateNo() {
        return pointsCateNo;
    }

    /**
     * Sets the value of the pointsCateNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointsCateNo(String value) {
        this.pointsCateNo = value;
    }

    /**
     * Gets the value of the presentPointsNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresentPointsNo() {
        return presentPointsNo;
    }

    /**
     * Sets the value of the presentPointsNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresentPointsNo(String value) {
        this.presentPointsNo = value;
    }

    /**
     * Gets the value of the realName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Sets the value of the realName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRealName(String value) {
        this.realName = value;
    }

    /**
     * Gets the value of the receiveReqTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiveReqTime() {
        return receiveReqTime;
    }

    /**
     * Sets the value of the receiveReqTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveReqTime(String value) {
        this.receiveReqTime = value;
    }

    /**
     * Gets the value of the refundPointsNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefundPointsNo() {
        return refundPointsNo;
    }

    /**
     * Sets the value of the refundPointsNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefundPointsNo(String value) {
        this.refundPointsNo = value;
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
     * Gets the value of the requestNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestNo() {
        return requestNo;
    }

    /**
     * Sets the value of the requestNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestNo(String value) {
        this.requestNo = value;
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
     * Gets the value of the resultCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Sets the value of the resultCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultCode(String value) {
        this.resultCode = value;
    }

    /**
     * Gets the value of the signMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignMsg() {
        return signMsg;
    }

    /**
     * Sets the value of the signMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignMsg(String value) {
        this.signMsg = value;
    }

    /**
     * Gets the value of the signType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignType() {
        return signType;
    }

    /**
     * Sets the value of the signType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignType(String value) {
        this.signType = value;
    }

    /**
     * Gets the value of the withdrawPoints property.
     * 
     * @return
     *     possible object is
     *     {@link WithdrawPoints }
     *     
     */
    public WithdrawPoints getWithdrawPoints() {
        return withdrawPoints;
    }

    /**
     * Sets the value of the withdrawPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link WithdrawPoints }
     *     
     */
    public void setWithdrawPoints(WithdrawPoints value) {
        this.withdrawPoints = value;
    }

}
