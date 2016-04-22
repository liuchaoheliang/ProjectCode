
package com.froad.client.point;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pointsAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pointsAccount">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="frozenPoints" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="points" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pointsAccount", propOrder = {
    "accountId",
    "frozenPoints",
    "orgName",
    "orgNo",
    "points"
})
public class PointsAccount {

    protected String accountId;
    protected String frozenPoints;
    protected String orgName;
    protected String orgNo;
    protected String points;

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
     * Gets the value of the frozenPoints property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrozenPoints() {
        return frozenPoints;
    }

    /**
     * Sets the value of the frozenPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrozenPoints(String value) {
        this.frozenPoints = value;
    }

    /**
     * Gets the value of the orgName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * Sets the value of the orgName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgName(String value) {
        this.orgName = value;
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

}
