
package com.froad.client.lottery;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for period complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="period">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bssj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ct" type="{http://service.CB.froad.com/}ct" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotteryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotteryNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="openTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="period" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tznum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "period", propOrder = {
    "bssj",
    "content",
    "ct",
    "endTime",
    "lotteryName",
    "lotteryNo",
    "openTime",
    "period",
    "startTime",
    "tznum"
})
public class Period {

    protected String bssj;
    protected String content;
    @XmlElement(nillable = true)
    protected List<Ct> ct;
    protected String endTime;
    protected String lotteryName;
    protected String lotteryNo;
    protected String openTime;
    protected String period;
    protected String startTime;
    protected String tznum;

    /**
     * Gets the value of the bssj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBssj() {
        return bssj;
    }

    /**
     * Sets the value of the bssj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBssj(String value) {
        this.bssj = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the ct property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ct property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ct }
     * 
     * 
     */
    public List<Ct> getCt() {
        if (ct == null) {
            ct = new ArrayList<Ct>();
        }
        return this.ct;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the lotteryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotteryName() {
        return lotteryName;
    }

    /**
     * Sets the value of the lotteryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotteryName(String value) {
        this.lotteryName = value;
    }

    /**
     * Gets the value of the lotteryNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotteryNo() {
        return lotteryNo;
    }

    /**
     * Sets the value of the lotteryNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotteryNo(String value) {
        this.lotteryNo = value;
    }

    /**
     * Gets the value of the openTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpenTime() {
        return openTime;
    }

    /**
     * Sets the value of the openTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpenTime(String value) {
        this.openTime = value;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeriod(String value) {
        this.period = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the tznum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTznum() {
        return tznum;
    }

    /**
     * Sets the value of the tznum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTznum(String value) {
        this.tznum = value;
    }

}
