
package com.froad.client.hfcz;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for hfcz complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="hfcz">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CZNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="md5_str" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchantNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchantPwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="money" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="operater" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SPID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="salePrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tranDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tranID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hfcz", propOrder = {
    "area",
    "czNo",
    "md5Str",
    "merchantNo",
    "merchantPwd",
    "money",
    "operater",
    "respCode",
    "respMsg",
    "spid",
    "salePrice",
    "tranDate",
    "tranID",
    "version"
})
public class Hfcz {

    protected String area;
    @XmlElement(name = "CZNo")
    protected String czNo;
    @XmlElement(name = "md5_str")
    protected String md5Str;
    protected String merchantNo;
    protected String merchantPwd;
    protected BigDecimal money;
    protected String operater;
    protected String respCode;
    protected String respMsg;
    @XmlElement(name = "SPID")
    protected String spid;
    protected BigDecimal salePrice;
    protected String tranDate;
    protected String tranID;
    protected String version;

    /**
     * Gets the value of the area property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArea(String value) {
        this.area = value;
    }

    /**
     * Gets the value of the czNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCZNo() {
        return czNo;
    }

    /**
     * Sets the value of the czNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCZNo(String value) {
        this.czNo = value;
    }

    /**
     * Gets the value of the md5Str property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMd5Str() {
        return md5Str;
    }

    /**
     * Sets the value of the md5Str property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMd5Str(String value) {
        this.md5Str = value;
    }

    /**
     * Gets the value of the merchantNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantNo() {
        return merchantNo;
    }

    /**
     * Sets the value of the merchantNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantNo(String value) {
        this.merchantNo = value;
    }

    /**
     * Gets the value of the merchantPwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantPwd() {
        return merchantPwd;
    }

    /**
     * Sets the value of the merchantPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantPwd(String value) {
        this.merchantPwd = value;
    }

    /**
     * Gets the value of the money property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * Sets the value of the money property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMoney(BigDecimal value) {
        this.money = value;
    }

    /**
     * Gets the value of the operater property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperater() {
        return operater;
    }

    /**
     * Sets the value of the operater property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperater(String value) {
        this.operater = value;
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
     * Gets the value of the spid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPID() {
        return spid;
    }

    /**
     * Sets the value of the spid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPID(String value) {
        this.spid = value;
    }

    /**
     * Gets the value of the salePrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * Sets the value of the salePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSalePrice(BigDecimal value) {
        this.salePrice = value;
    }

    /**
     * Gets the value of the tranDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranDate() {
        return tranDate;
    }

    /**
     * Sets the value of the tranDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranDate(String value) {
        this.tranDate = value;
    }

    /**
     * Gets the value of the tranID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranID() {
        return tranID;
    }

    /**
     * Sets the value of the tranID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranID(String value) {
        this.tranID = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
