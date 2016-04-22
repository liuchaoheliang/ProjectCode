
package com.froad.client.complaint;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for complaint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complaint">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="complaintCategory" type="{http://service.CB.froad.com/}complaintCategory" minOccurs="0"/>
 *         &lt;element name="complaintCategoryId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deleteStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromUser" type="{http://service.CB.froad.com/}user" minOccurs="0"/>
 *         &lt;element name="fromUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isRead" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isReply" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchant" type="{http://service.CB.froad.com/}merchant" minOccurs="0"/>
 *         &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="replyList" type="{http://service.CB.froad.com/}complaintReply" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toUser" type="{http://service.CB.froad.com/}user" minOccurs="0"/>
 *         &lt;element name="toUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complaint", propOrder = {
    "complaintCategory",
    "complaintCategoryId",
    "content",
    "createTime",
    "deleteStatus",
    "fromUser",
    "fromUserId",
    "id",
    "isRead",
    "isReply",
    "merchant",
    "orderId",
    "remark",
    "replyList",
    "state",
    "toUser",
    "toUserId",
    "updateTime"
})
public class Complaint
    extends Pager
{

    protected ComplaintCategory complaintCategory;
    protected String complaintCategoryId;
    protected String content;
    protected String createTime;
    protected String deleteStatus;
    protected User fromUser;
    protected String fromUserId;
    protected Integer id;
    protected String isRead;
    protected String isReply;
    protected Merchant merchant;
    protected String orderId;
    protected String remark;
    @XmlElement(nillable = true)
    protected List<ComplaintReply> replyList;
    protected String state;
    protected User toUser;
    protected String toUserId;
    protected String updateTime;

    /**
     * Gets the value of the complaintCategory property.
     * 
     * @return
     *     possible object is
     *     {@link ComplaintCategory }
     *     
     */
    public ComplaintCategory getComplaintCategory() {
        return complaintCategory;
    }

    /**
     * Sets the value of the complaintCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplaintCategory }
     *     
     */
    public void setComplaintCategory(ComplaintCategory value) {
        this.complaintCategory = value;
    }

    /**
     * Gets the value of the complaintCategoryId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplaintCategoryId() {
        return complaintCategoryId;
    }

    /**
     * Sets the value of the complaintCategoryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplaintCategoryId(String value) {
        this.complaintCategoryId = value;
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
     * Gets the value of the deleteStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * Sets the value of the deleteStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteStatus(String value) {
        this.deleteStatus = value;
    }

    /**
     * Gets the value of the fromUser property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getFromUser() {
        return fromUser;
    }

    /**
     * Sets the value of the fromUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setFromUser(User value) {
        this.fromUser = value;
    }

    /**
     * Gets the value of the fromUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * Sets the value of the fromUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromUserId(String value) {
        this.fromUserId = value;
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
     * Gets the value of the isRead property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRead() {
        return isRead;
    }

    /**
     * Sets the value of the isRead property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRead(String value) {
        this.isRead = value;
    }

    /**
     * Gets the value of the isReply property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsReply() {
        return isReply;
    }

    /**
     * Sets the value of the isReply property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsReply(String value) {
        this.isReply = value;
    }

    /**
     * Gets the value of the merchant property.
     * 
     * @return
     *     possible object is
     *     {@link Merchant }
     *     
     */
    public Merchant getMerchant() {
        return merchant;
    }

    /**
     * Sets the value of the merchant property.
     * 
     * @param value
     *     allowed object is
     *     {@link Merchant }
     *     
     */
    public void setMerchant(Merchant value) {
        this.merchant = value;
    }

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
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
     * Gets the value of the replyList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the replyList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReplyList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplaintReply }
     * 
     * 
     */
    public List<ComplaintReply> getReplyList() {
        if (replyList == null) {
            replyList = new ArrayList<ComplaintReply>();
        }
        return this.replyList;
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
     * Gets the value of the toUser property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getToUser() {
        return toUser;
    }

    /**
     * Sets the value of the toUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setToUser(User value) {
        this.toUser = value;
    }

    /**
     * Gets the value of the toUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToUserId() {
        return toUserId;
    }

    /**
     * Sets the value of the toUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToUserId(String value) {
        this.toUserId = value;
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

}
