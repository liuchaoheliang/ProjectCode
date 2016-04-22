
package com.froad.client.trans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for trans complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="trans">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="authTicketId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authTicketList" type="{http://service.CB.froad.com/}authTicket" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="bankPointsAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankPointsValueAll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankPointsValueRealAll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="belongUserBecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="belongUserBecodeAuth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="buyer" type="{http://service.CB.froad.com/}buyers" minOccurs="0"/>
 *         &lt;element name="buyerChannel" type="{http://service.CB.froad.com/}buyerChannel" minOccurs="0"/>
 *         &lt;element name="buyerChannelId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="buyersId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consumeTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="costpriceTotal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currencyValueAll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currencyValueRealAll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="failureTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftFactorage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointsAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointsValueAll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointsValueRealAll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="financeExcel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsGatherRack" type="{http://service.CB.froad.com/}goodsGatherRack" minOccurs="0"/>
 *         &lt;element name="goodsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isConsume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotteryBillNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchant" type="{http://service.CB.froad.com/}merchant" minOccurs="0"/>
 *         &lt;element name="merchantId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="merchantIdList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="payChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payList" type="{http://service.CB.froad.com/}pay" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="payMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payMethodList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="payStateList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="paymentUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pointBillNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pointsAmountRealValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pointsAmountValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presentPointsValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presentRuleId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="refundOrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securitiesNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seller" type="{http://service.CB.froad.com/}seller" minOccurs="0"/>
 *         &lt;element name="sellerChannel" type="{http://service.CB.froad.com/}sellerChannel" minOccurs="0"/>
 *         &lt;element name="sellerChannelId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sellerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sellerIdList" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sellerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smsNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storeStortName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trackNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transDetailsList" type="{http://service.CB.froad.com/}transDetails" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transGoodsAttrList" type="{http://service.CB.froad.com/}transGoodsAttribute" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transSn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="user" type="{http://service.CB.froad.com/}user" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="virtualType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trans", propOrder = {
    "authTicketId",
    "authTicketList",
    "bankPointsAccount",
    "bankPointsValueAll",
    "bankPointsValueRealAll",
    "beCode",
    "beName",
    "belongUserBecode",
    "belongUserBecodeAuth",
    "billNo",
    "buyer",
    "buyerChannel",
    "buyerChannelId",
    "buyersId",
    "channelId",
    "clientType",
    "consumeTime",
    "costpriceTotal",
    "createTime",
    "currency",
    "currencyValueAll",
    "currencyValueRealAll",
    "failureTime",
    "fftFactorage",
    "fftPointsAccount",
    "fftPointsValueAll",
    "fftPointsValueRealAll",
    "financeExcel",
    "goodsGatherRack",
    "goodsName",
    "id",
    "isConsume",
    "lotteryBillNo",
    "merchant",
    "merchantId",
    "merchantIdList",
    "payChannel",
    "payList",
    "payMethod",
    "payMethodList",
    "payStateList",
    "paymentUrl",
    "phone",
    "pointBillNo",
    "pointsAmountRealValue",
    "pointsAmountValue",
    "presentPointsValue",
    "presentRuleId",
    "refundOrderId",
    "remark",
    "respCode",
    "respMsg",
    "securitiesNo",
    "seller",
    "sellerChannel",
    "sellerChannelId",
    "sellerId",
    "sellerIdList",
    "sellerType",
    "smsNumber",
    "state",
    "storeStortName",
    "trackNo",
    "transDetailsList",
    "transGoodsAttrList",
    "transSn",
    "transType",
    "updateTime",
    "user",
    "userId",
    "virtualType"
})
public class Trans
    extends Pager
{

    protected String authTicketId;
    @XmlElement(nillable = true)
    protected List<AuthTicket> authTicketList;
    protected String bankPointsAccount;
    protected String bankPointsValueAll;
    protected String bankPointsValueRealAll;
    protected String beCode;
    protected String beName;
    protected String belongUserBecode;
    protected String belongUserBecodeAuth;
    protected String billNo;
    protected Buyers buyer;
    protected BuyerChannel buyerChannel;
    protected String buyerChannelId;
    protected String buyersId;
    protected String channelId;
    protected String clientType;
    protected String consumeTime;
    protected String costpriceTotal;
    protected String createTime;
    protected String currency;
    protected String currencyValueAll;
    protected String currencyValueRealAll;
    protected String failureTime;
    protected String fftFactorage;
    protected String fftPointsAccount;
    protected String fftPointsValueAll;
    protected String fftPointsValueRealAll;
    protected String financeExcel;
    protected GoodsGatherRack goodsGatherRack;
    protected String goodsName;
    protected Integer id;
    protected String isConsume;
    protected String lotteryBillNo;
    protected Merchant merchant;
    protected Integer merchantId;
    @XmlElement(nillable = true)
    protected List<String> merchantIdList;
    protected String payChannel;
    @XmlElement(nillable = true)
    protected List<Pay> payList;
    protected String payMethod;
    @XmlElement(nillable = true)
    protected List<String> payMethodList;
    @XmlElement(nillable = true)
    protected List<String> payStateList;
    protected String paymentUrl;
    protected String phone;
    protected String pointBillNo;
    protected String pointsAmountRealValue;
    protected String pointsAmountValue;
    protected String presentPointsValue;
    protected Integer presentRuleId;
    protected String refundOrderId;
    protected String remark;
    protected String respCode;
    protected String respMsg;
    protected String securitiesNo;
    protected Seller seller;
    protected SellerChannel sellerChannel;
    protected String sellerChannelId;
    protected String sellerId;
    @XmlElement(nillable = true)
    protected List<Integer> sellerIdList;
    protected String sellerType;
    protected String smsNumber;
    protected String state;
    protected String storeStortName;
    protected String trackNo;
    @XmlElement(nillable = true)
    protected List<TransDetails> transDetailsList;
    @XmlElement(nillable = true)
    protected List<TransGoodsAttribute> transGoodsAttrList;
    protected String transSn;
    protected String transType;
    protected String updateTime;
    protected User user;
    protected String userId;
    protected String virtualType;

    /**
     * Gets the value of the authTicketId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthTicketId() {
        return authTicketId;
    }

    /**
     * Sets the value of the authTicketId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthTicketId(String value) {
        this.authTicketId = value;
    }

    /**
     * Gets the value of the authTicketList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authTicketList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthTicketList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AuthTicket }
     * 
     * 
     */
    public List<AuthTicket> getAuthTicketList() {
        if (authTicketList == null) {
            authTicketList = new ArrayList<AuthTicket>();
        }
        return this.authTicketList;
    }

    /**
     * Gets the value of the bankPointsAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankPointsAccount() {
        return bankPointsAccount;
    }

    /**
     * Sets the value of the bankPointsAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankPointsAccount(String value) {
        this.bankPointsAccount = value;
    }

    /**
     * Gets the value of the bankPointsValueAll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankPointsValueAll() {
        return bankPointsValueAll;
    }

    /**
     * Sets the value of the bankPointsValueAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankPointsValueAll(String value) {
        this.bankPointsValueAll = value;
    }

    /**
     * Gets the value of the bankPointsValueRealAll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankPointsValueRealAll() {
        return bankPointsValueRealAll;
    }

    /**
     * Sets the value of the bankPointsValueRealAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankPointsValueRealAll(String value) {
        this.bankPointsValueRealAll = value;
    }

    /**
     * Gets the value of the beCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeCode() {
        return beCode;
    }

    /**
     * Sets the value of the beCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeCode(String value) {
        this.beCode = value;
    }

    /**
     * Gets the value of the beName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeName() {
        return beName;
    }

    /**
     * Sets the value of the beName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeName(String value) {
        this.beName = value;
    }

    /**
     * Gets the value of the belongUserBecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBelongUserBecode() {
        return belongUserBecode;
    }

    /**
     * Sets the value of the belongUserBecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBelongUserBecode(String value) {
        this.belongUserBecode = value;
    }

    /**
     * Gets the value of the belongUserBecodeAuth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBelongUserBecodeAuth() {
        return belongUserBecodeAuth;
    }

    /**
     * Sets the value of the belongUserBecodeAuth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBelongUserBecodeAuth(String value) {
        this.belongUserBecodeAuth = value;
    }

    /**
     * Gets the value of the billNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * Sets the value of the billNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillNo(String value) {
        this.billNo = value;
    }

    /**
     * Gets the value of the buyer property.
     * 
     * @return
     *     possible object is
     *     {@link Buyers }
     *     
     */
    public Buyers getBuyer() {
        return buyer;
    }

    /**
     * Sets the value of the buyer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Buyers }
     *     
     */
    public void setBuyer(Buyers value) {
        this.buyer = value;
    }

    /**
     * Gets the value of the buyerChannel property.
     * 
     * @return
     *     possible object is
     *     {@link BuyerChannel }
     *     
     */
    public BuyerChannel getBuyerChannel() {
        return buyerChannel;
    }

    /**
     * Sets the value of the buyerChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link BuyerChannel }
     *     
     */
    public void setBuyerChannel(BuyerChannel value) {
        this.buyerChannel = value;
    }

    /**
     * Gets the value of the buyerChannelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyerChannelId() {
        return buyerChannelId;
    }

    /**
     * Sets the value of the buyerChannelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyerChannelId(String value) {
        this.buyerChannelId = value;
    }

    /**
     * Gets the value of the buyersId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyersId() {
        return buyersId;
    }

    /**
     * Sets the value of the buyersId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyersId(String value) {
        this.buyersId = value;
    }

    /**
     * Gets the value of the channelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * Sets the value of the channelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelId(String value) {
        this.channelId = value;
    }

    /**
     * Gets the value of the clientType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * Sets the value of the clientType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientType(String value) {
        this.clientType = value;
    }

    /**
     * Gets the value of the consumeTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsumeTime() {
        return consumeTime;
    }

    /**
     * Sets the value of the consumeTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsumeTime(String value) {
        this.consumeTime = value;
    }

    /**
     * Gets the value of the costpriceTotal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCostpriceTotal() {
        return costpriceTotal;
    }

    /**
     * Sets the value of the costpriceTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCostpriceTotal(String value) {
        this.costpriceTotal = value;
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
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the currencyValueAll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyValueAll() {
        return currencyValueAll;
    }

    /**
     * Sets the value of the currencyValueAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyValueAll(String value) {
        this.currencyValueAll = value;
    }

    /**
     * Gets the value of the currencyValueRealAll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyValueRealAll() {
        return currencyValueRealAll;
    }

    /**
     * Sets the value of the currencyValueRealAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyValueRealAll(String value) {
        this.currencyValueRealAll = value;
    }

    /**
     * Gets the value of the failureTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailureTime() {
        return failureTime;
    }

    /**
     * Sets the value of the failureTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailureTime(String value) {
        this.failureTime = value;
    }

    /**
     * Gets the value of the fftFactorage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftFactorage() {
        return fftFactorage;
    }

    /**
     * Sets the value of the fftFactorage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftFactorage(String value) {
        this.fftFactorage = value;
    }

    /**
     * Gets the value of the fftPointsAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointsAccount() {
        return fftPointsAccount;
    }

    /**
     * Sets the value of the fftPointsAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointsAccount(String value) {
        this.fftPointsAccount = value;
    }

    /**
     * Gets the value of the fftPointsValueAll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointsValueAll() {
        return fftPointsValueAll;
    }

    /**
     * Sets the value of the fftPointsValueAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointsValueAll(String value) {
        this.fftPointsValueAll = value;
    }

    /**
     * Gets the value of the fftPointsValueRealAll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointsValueRealAll() {
        return fftPointsValueRealAll;
    }

    /**
     * Sets the value of the fftPointsValueRealAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointsValueRealAll(String value) {
        this.fftPointsValueRealAll = value;
    }

    /**
     * Gets the value of the financeExcel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinanceExcel() {
        return financeExcel;
    }

    /**
     * Sets the value of the financeExcel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinanceExcel(String value) {
        this.financeExcel = value;
    }

    /**
     * Gets the value of the goodsGatherRack property.
     * 
     * @return
     *     possible object is
     *     {@link GoodsGatherRack }
     *     
     */
    public GoodsGatherRack getGoodsGatherRack() {
        return goodsGatherRack;
    }

    /**
     * Sets the value of the goodsGatherRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoodsGatherRack }
     *     
     */
    public void setGoodsGatherRack(GoodsGatherRack value) {
        this.goodsGatherRack = value;
    }

    /**
     * Gets the value of the goodsName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * Sets the value of the goodsName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsName(String value) {
        this.goodsName = value;
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
     * Gets the value of the isConsume property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsConsume() {
        return isConsume;
    }

    /**
     * Sets the value of the isConsume property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsConsume(String value) {
        this.isConsume = value;
    }

    /**
     * Gets the value of the lotteryBillNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotteryBillNo() {
        return lotteryBillNo;
    }

    /**
     * Sets the value of the lotteryBillNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotteryBillNo(String value) {
        this.lotteryBillNo = value;
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
     * Gets the value of the merchantId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMerchantId() {
        return merchantId;
    }

    /**
     * Sets the value of the merchantId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMerchantId(Integer value) {
        this.merchantId = value;
    }

    /**
     * Gets the value of the merchantIdList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the merchantIdList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMerchantIdList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMerchantIdList() {
        if (merchantIdList == null) {
            merchantIdList = new ArrayList<String>();
        }
        return this.merchantIdList;
    }

    /**
     * Gets the value of the payChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayChannel() {
        return payChannel;
    }

    /**
     * Sets the value of the payChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayChannel(String value) {
        this.payChannel = value;
    }

    /**
     * Gets the value of the payList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the payList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPayList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Pay }
     * 
     * 
     */
    public List<Pay> getPayList() {
        if (payList == null) {
            payList = new ArrayList<Pay>();
        }
        return this.payList;
    }

    /**
     * Gets the value of the payMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayMethod() {
        return payMethod;
    }

    /**
     * Sets the value of the payMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayMethod(String value) {
        this.payMethod = value;
    }

    /**
     * Gets the value of the payMethodList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the payMethodList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPayMethodList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPayMethodList() {
        if (payMethodList == null) {
            payMethodList = new ArrayList<String>();
        }
        return this.payMethodList;
    }

    /**
     * Gets the value of the payStateList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the payStateList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPayStateList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPayStateList() {
        if (payStateList == null) {
            payStateList = new ArrayList<String>();
        }
        return this.payStateList;
    }

    /**
     * Gets the value of the paymentUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentUrl() {
        return paymentUrl;
    }

    /**
     * Sets the value of the paymentUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentUrl(String value) {
        this.paymentUrl = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the pointBillNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointBillNo() {
        return pointBillNo;
    }

    /**
     * Sets the value of the pointBillNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointBillNo(String value) {
        this.pointBillNo = value;
    }

    /**
     * Gets the value of the pointsAmountRealValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointsAmountRealValue() {
        return pointsAmountRealValue;
    }

    /**
     * Sets the value of the pointsAmountRealValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointsAmountRealValue(String value) {
        this.pointsAmountRealValue = value;
    }

    /**
     * Gets the value of the pointsAmountValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointsAmountValue() {
        return pointsAmountValue;
    }

    /**
     * Sets the value of the pointsAmountValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointsAmountValue(String value) {
        this.pointsAmountValue = value;
    }

    /**
     * Gets the value of the presentPointsValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresentPointsValue() {
        return presentPointsValue;
    }

    /**
     * Sets the value of the presentPointsValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresentPointsValue(String value) {
        this.presentPointsValue = value;
    }

    /**
     * Gets the value of the presentRuleId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPresentRuleId() {
        return presentRuleId;
    }

    /**
     * Sets the value of the presentRuleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPresentRuleId(Integer value) {
        this.presentRuleId = value;
    }

    /**
     * Gets the value of the refundOrderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefundOrderId() {
        return refundOrderId;
    }

    /**
     * Sets the value of the refundOrderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefundOrderId(String value) {
        this.refundOrderId = value;
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
     * Gets the value of the securitiesNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecuritiesNo() {
        return securitiesNo;
    }

    /**
     * Sets the value of the securitiesNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecuritiesNo(String value) {
        this.securitiesNo = value;
    }

    /**
     * Gets the value of the seller property.
     * 
     * @return
     *     possible object is
     *     {@link Seller }
     *     
     */
    public Seller getSeller() {
        return seller;
    }

    /**
     * Sets the value of the seller property.
     * 
     * @param value
     *     allowed object is
     *     {@link Seller }
     *     
     */
    public void setSeller(Seller value) {
        this.seller = value;
    }

    /**
     * Gets the value of the sellerChannel property.
     * 
     * @return
     *     possible object is
     *     {@link SellerChannel }
     *     
     */
    public SellerChannel getSellerChannel() {
        return sellerChannel;
    }

    /**
     * Sets the value of the sellerChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link SellerChannel }
     *     
     */
    public void setSellerChannel(SellerChannel value) {
        this.sellerChannel = value;
    }

    /**
     * Gets the value of the sellerChannelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSellerChannelId() {
        return sellerChannelId;
    }

    /**
     * Sets the value of the sellerChannelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSellerChannelId(String value) {
        this.sellerChannelId = value;
    }

    /**
     * Gets the value of the sellerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * Sets the value of the sellerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSellerId(String value) {
        this.sellerId = value;
    }

    /**
     * Gets the value of the sellerIdList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sellerIdList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSellerIdList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getSellerIdList() {
        if (sellerIdList == null) {
            sellerIdList = new ArrayList<Integer>();
        }
        return this.sellerIdList;
    }

    /**
     * Gets the value of the sellerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSellerType() {
        return sellerType;
    }

    /**
     * Sets the value of the sellerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSellerType(String value) {
        this.sellerType = value;
    }

    /**
     * Gets the value of the smsNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsNumber() {
        return smsNumber;
    }

    /**
     * Sets the value of the smsNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsNumber(String value) {
        this.smsNumber = value;
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
     * Gets the value of the storeStortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreStortName() {
        return storeStortName;
    }

    /**
     * Sets the value of the storeStortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreStortName(String value) {
        this.storeStortName = value;
    }

    /**
     * Gets the value of the trackNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackNo() {
        return trackNo;
    }

    /**
     * Sets the value of the trackNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackNo(String value) {
        this.trackNo = value;
    }

    /**
     * Gets the value of the transDetailsList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transDetailsList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransDetailsList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransDetails }
     * 
     * 
     */
    public List<TransDetails> getTransDetailsList() {
        if (transDetailsList == null) {
            transDetailsList = new ArrayList<TransDetails>();
        }
        return this.transDetailsList;
    }

    /**
     * Gets the value of the transGoodsAttrList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transGoodsAttrList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransGoodsAttrList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransGoodsAttribute }
     * 
     * 
     */
    public List<TransGoodsAttribute> getTransGoodsAttrList() {
        if (transGoodsAttrList == null) {
            transGoodsAttrList = new ArrayList<TransGoodsAttribute>();
        }
        return this.transGoodsAttrList;
    }

    /**
     * Gets the value of the transSn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransSn() {
        return transSn;
    }

    /**
     * Sets the value of the transSn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransSn(String value) {
        this.transSn = value;
    }

    /**
     * Gets the value of the transType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransType() {
        return transType;
    }

    /**
     * Sets the value of the transType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransType(String value) {
        this.transType = value;
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
     * Gets the value of the virtualType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVirtualType() {
        return virtualType;
    }

    /**
     * Sets the value of the virtualType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVirtualType(String value) {
        this.virtualType = value;
    }

}
