
package com.froad.client.lottery;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for lottery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lottery">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankCardNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="blueball" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="body" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="buyamount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="canBuy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="checkContent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="checkResultContent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="checkType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enddate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flowId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isHit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotteryNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotteryType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobilephone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numCount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="openTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="other" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PList" type="{http://service.CB.froad.com/}period" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="period" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="playType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prizeCount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prizeGrade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="redball" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reqID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rewardStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tranID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trangoodsID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transDateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="winAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zqamount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lottery", propOrder = {
    "accountName",
    "amount",
    "bankCardNum",
    "blueball",
    "body",
    "buyamount",
    "canBuy",
    "checkContent",
    "checkResultContent",
    "checkType",
    "content",
    "createTime",
    "description",
    "endTime",
    "enddate",
    "flowId",
    "id",
    "isHit",
    "lotteryNo",
    "lotteryType",
    "mac",
    "message",
    "mobile",
    "mobilephone",
    "numCount",
    "numType",
    "openTime",
    "orderID",
    "other",
    "pList",
    "period",
    "playType",
    "prizeCount",
    "prizeGrade",
    "providerID",
    "rd",
    "redball",
    "reqID",
    "resCode",
    "respCode",
    "respMsg",
    "rewardStatus",
    "startTime",
    "startdate",
    "status",
    "tranID",
    "trangoodsID",
    "transDateTime",
    "unitAmount",
    "userID",
    "userId",
    "winAmount",
    "zqamount"
})
public class Lottery {

    protected String accountName;
    protected String amount;
    protected String bankCardNum;
    protected String blueball;
    protected String body;
    protected String buyamount;
    protected String canBuy;
    protected String checkContent;
    protected String checkResultContent;
    protected String checkType;
    protected String content;
    protected String createTime;
    protected String description;
    protected String endTime;
    protected String enddate;
    protected String flowId;
    @XmlElement(name = "ID")
    protected String id;
    protected String isHit;
    protected String lotteryNo;
    protected String lotteryType;
    protected String mac;
    protected String message;
    protected String mobile;
    protected String mobilephone;
    protected String numCount;
    protected String numType;
    protected String openTime;
    protected String orderID;
    protected String other;
    @XmlElement(name = "PList", nillable = true)
    protected List<Period> pList;
    protected String period;
    protected String playType;
    protected String prizeCount;
    protected String prizeGrade;
    protected String providerID;
    protected String rd;
    protected String redball;
    protected String reqID;
    protected String resCode;
    protected String respCode;
    protected String respMsg;
    protected String rewardStatus;
    protected String startTime;
    protected String startdate;
    protected String status;
    protected String tranID;
    protected String trangoodsID;
    protected String transDateTime;
    protected String unitAmount;
    protected String userID;
    protected String userId;
    protected String winAmount;
    protected String zqamount;

    /**
     * Gets the value of the accountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets the value of the accountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountName(String value) {
        this.accountName = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmount(String value) {
        this.amount = value;
    }

    /**
     * Gets the value of the bankCardNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCardNum() {
        return bankCardNum;
    }

    /**
     * Sets the value of the bankCardNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCardNum(String value) {
        this.bankCardNum = value;
    }

    /**
     * Gets the value of the blueball property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlueball() {
        return blueball;
    }

    /**
     * Sets the value of the blueball property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlueball(String value) {
        this.blueball = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBody(String value) {
        this.body = value;
    }

    /**
     * Gets the value of the buyamount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyamount() {
        return buyamount;
    }

    /**
     * Sets the value of the buyamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyamount(String value) {
        this.buyamount = value;
    }

    /**
     * Gets the value of the canBuy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanBuy() {
        return canBuy;
    }

    /**
     * Sets the value of the canBuy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanBuy(String value) {
        this.canBuy = value;
    }

    /**
     * Gets the value of the checkContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckContent() {
        return checkContent;
    }

    /**
     * Sets the value of the checkContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckContent(String value) {
        this.checkContent = value;
    }

    /**
     * Gets the value of the checkResultContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckResultContent() {
        return checkResultContent;
    }

    /**
     * Sets the value of the checkResultContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckResultContent(String value) {
        this.checkResultContent = value;
    }

    /**
     * Gets the value of the checkType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * Sets the value of the checkType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckType(String value) {
        this.checkType = value;
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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
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
     * Gets the value of the enddate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * Sets the value of the enddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnddate(String value) {
        this.enddate = value;
    }

    /**
     * Gets the value of the flowId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlowId() {
        return flowId;
    }

    /**
     * Sets the value of the flowId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlowId(String value) {
        this.flowId = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the isHit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHit() {
        return isHit;
    }

    /**
     * Sets the value of the isHit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHit(String value) {
        this.isHit = value;
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
     * Gets the value of the lotteryType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotteryType() {
        return lotteryType;
    }

    /**
     * Sets the value of the lotteryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotteryType(String value) {
        this.lotteryType = value;
    }

    /**
     * Gets the value of the mac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMac() {
        return mac;
    }

    /**
     * Sets the value of the mac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMac(String value) {
        this.mac = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the mobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets the value of the mobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    /**
     * Gets the value of the mobilephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobilephone() {
        return mobilephone;
    }

    /**
     * Sets the value of the mobilephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobilephone(String value) {
        this.mobilephone = value;
    }

    /**
     * Gets the value of the numCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumCount() {
        return numCount;
    }

    /**
     * Sets the value of the numCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumCount(String value) {
        this.numCount = value;
    }

    /**
     * Gets the value of the numType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumType() {
        return numType;
    }

    /**
     * Sets the value of the numType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumType(String value) {
        this.numType = value;
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
     * Gets the value of the orderID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * Sets the value of the orderID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderID(String value) {
        this.orderID = value;
    }

    /**
     * Gets the value of the other property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOther() {
        return other;
    }

    /**
     * Sets the value of the other property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOther(String value) {
        this.other = value;
    }

    /**
     * Gets the value of the pList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Period }
     * 
     * 
     */
    public List<Period> getPList() {
        if (pList == null) {
            pList = new ArrayList<Period>();
        }
        return this.pList;
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
     * Gets the value of the playType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlayType() {
        return playType;
    }

    /**
     * Sets the value of the playType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlayType(String value) {
        this.playType = value;
    }

    /**
     * Gets the value of the prizeCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrizeCount() {
        return prizeCount;
    }

    /**
     * Sets the value of the prizeCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrizeCount(String value) {
        this.prizeCount = value;
    }

    /**
     * Gets the value of the prizeGrade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrizeGrade() {
        return prizeGrade;
    }

    /**
     * Sets the value of the prizeGrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrizeGrade(String value) {
        this.prizeGrade = value;
    }

    /**
     * Gets the value of the providerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderID() {
        return providerID;
    }

    /**
     * Sets the value of the providerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderID(String value) {
        this.providerID = value;
    }

    /**
     * Gets the value of the rd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRd() {
        return rd;
    }

    /**
     * Sets the value of the rd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRd(String value) {
        this.rd = value;
    }

    /**
     * Gets the value of the redball property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRedball() {
        return redball;
    }

    /**
     * Sets the value of the redball property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedball(String value) {
        this.redball = value;
    }

    /**
     * Gets the value of the reqID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqID() {
        return reqID;
    }

    /**
     * Sets the value of the reqID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqID(String value) {
        this.reqID = value;
    }

    /**
     * Gets the value of the resCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResCode() {
        return resCode;
    }

    /**
     * Sets the value of the resCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResCode(String value) {
        this.resCode = value;
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
     * Gets the value of the rewardStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRewardStatus() {
        return rewardStatus;
    }

    /**
     * Sets the value of the rewardStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRewardStatus(String value) {
        this.rewardStatus = value;
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
     * Gets the value of the startdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartdate() {
        return startdate;
    }

    /**
     * Sets the value of the startdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartdate(String value) {
        this.startdate = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
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
     * Gets the value of the trangoodsID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrangoodsID() {
        return trangoodsID;
    }

    /**
     * Sets the value of the trangoodsID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrangoodsID(String value) {
        this.trangoodsID = value;
    }

    /**
     * Gets the value of the transDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransDateTime() {
        return transDateTime;
    }

    /**
     * Sets the value of the transDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransDateTime(String value) {
        this.transDateTime = value;
    }

    /**
     * Gets the value of the unitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitAmount() {
        return unitAmount;
    }

    /**
     * Sets the value of the unitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitAmount(String value) {
        this.unitAmount = value;
    }

    /**
     * Gets the value of the userID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
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
     * Gets the value of the winAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWinAmount() {
        return winAmount;
    }

    /**
     * Sets the value of the winAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWinAmount(String value) {
        this.winAmount = value;
    }

    /**
     * Gets the value of the zqamount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZqamount() {
        return zqamount;
    }

    /**
     * Sets the value of the zqamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZqamount(String value) {
        this.zqamount = value;
    }

}
