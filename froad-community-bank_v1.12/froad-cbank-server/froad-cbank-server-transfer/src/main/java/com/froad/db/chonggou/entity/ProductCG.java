package com.froad.db.chonggou.entity;

import java.util.Date;

import com.froad.db.ahui.entity.ProductGroup;
import com.froad.db.ahui.entity.ProductPresell;

/**
 * 
 * @author wangyan
 *
 */
public class ProductCG implements java.io.Serializable {

	/**
	  * @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = -6940380166377094686L;
	/**
     * 
     */
    
    // Fields
	private Long id;
	private String clientId;
	private String merchantId;
	private String orgCode;
	private String productId;
	private String isMarketable;
	private Date rackTime;
	private Date createTime;
	private Date startTime;
    private Date endTime;
	private String type;
	private String deliveryOption;
	private String name;
	private String fullName;
	private Integer price;
	private Integer cost;
	private Integer marketPrice;
	private String weight;
	private String weightUnit;
	private Integer store;
	private Boolean isBest;
	private Boolean isLimit;
	private Integer point;
	private String briefIntroduction;
	private String introduction;
	private String buyKnow;
	private String auditState;
	private String auditOrgCode;
	private String auditStage;
	private Date auditTime;
	private String auditComment;
	private String reviewStaff;
	private String auditStaff;
	private Integer orderValue;
	private Integer sellCount;
	private Date downTime;
	private Integer deliveryMoney;
	private String isSeckill;
	private ProductGroup productGroup;
	private ProductPresell productPresell;
	//20150515性能调优添加冗余字段
	private String merchantName;//商户名称
	private String categoryTreePath;//商品分类TreePath
	/**
	 * 一级机构
	 */
	private String proOrgCode;
	/**
	 * 二级机构
	 */
	private String cityOrgCode;
	/**
	 * 三级机构
	 */
	private String countryOrgCode;
	/**
	 * 起始审核机构编号
	 */
	private String auditStartOrgCode;
	/**
	 * 最终审核机编号
	 */
	private String auditEndOrgCode;

	// Constructors

	/** default constructor */
	public ProductCG() {
	}

	/** minimal constructor */
	public ProductCG(Date createTime, String clientId, String productId, String type, String name, Integer store, Boolean isBest) {
		this.createTime = createTime;
		this.clientId = clientId;
		this.productId = productId;
		this.type = type;
		this.name = name;
		this.store = store;
		this.isBest = isBest;
	}

	/** full constructor */
	public ProductCG(Date createTime, String clientId, String merchantId, String orgCode, String productId, String isMarketable, Date rackTime, String type, String deliveryOption, String name, String fullName, Integer price, Integer cost, Integer marketPrice, String weight, String weightUnit, Integer store, Boolean isBest, Boolean isLimit, Integer point, String briefIntroduction, String introduction, String buyKnow, String auditState, String auditOrgCode, String auditStage, String reviewStaff, String auditStaff, Integer orderValue, Integer sellCount) {
		this.createTime = createTime;
		this.clientId = clientId;
		this.merchantId = merchantId;
		this.orgCode = orgCode;
		this.productId = productId;
		this.isMarketable = isMarketable;
		this.rackTime = rackTime;
		this.type = type;
		this.deliveryOption = deliveryOption;
		this.name = name;
		this.fullName = fullName;
		this.price = price;
		this.cost = cost;
		this.marketPrice = marketPrice;
		this.weight = weight;
		this.weightUnit = weightUnit;
		this.store = store;
		this.isBest = isBest;
		this.isLimit = isLimit;
		this.point = point;
		this.briefIntroduction = briefIntroduction;
		this.introduction = introduction;
		this.buyKnow = buyKnow;
		this.auditState = auditState;
		this.auditOrgCode = auditOrgCode;
		this.auditStage = auditStage;
		this.reviewStaff = reviewStaff;
		this.auditStaff = auditStaff;
		this.orderValue = orderValue;
		this.sellCount = sellCount;
	}
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(String isMarketable) {
        this.isMarketable = isMarketable;
    }

    public Date getRackTime() {
        return rackTime;
    }

    public void setRackTime(Date rackTime) {
        this.rackTime = rackTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Boolean getIsBest() {
        return isBest;
    }

    public void setIsBest(Boolean isBest) {
        this.isBest = isBest;
    }

    public Boolean getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(Boolean isLimit) {
        this.isLimit = isLimit;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBuyKnow() {
        return buyKnow;
    }

    public void setBuyKnow(String buyKnow) {
        this.buyKnow = buyKnow;
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    public String getAuditOrgCode() {
        return auditOrgCode;
    }

    public void setAuditOrgCode(String auditOrgCode) {
        this.auditOrgCode = auditOrgCode;
    }

    public String getAuditStage() {
        return auditStage;
    }

    public void setAuditStage(String auditStage) {
        this.auditStage = auditStage;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }
    
    public String getReviewStaff() {
        return reviewStaff;
    }

    public void setReviewStaff(String reviewStaff) {
        this.reviewStaff = reviewStaff;
    }

    public String getAuditStaff() {
        return auditStaff;
    }

    public void setAuditStaff(String auditStaff) {
        this.auditStaff = auditStaff;
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }
    
    public Date getDownTime() {
        return downTime;
    }

    public void setDownTime(Date downTime) {
        this.downTime = downTime;
    }
    
    public Integer getDeliveryMoney() {
        return deliveryMoney;
    }

    public void setDeliveryMoney(Integer deliveryMoney) {
        this.deliveryMoney = deliveryMoney;
    }
    
   
    
    public String getIsSeckill() {
		return isSeckill;
	}

	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}

	public String getProOrgCode() {
        return proOrgCode;
    }

    public void setProOrgCode(String proOrgCode) {
        this.proOrgCode = proOrgCode;
    }

    public String getCityOrgCode() {
        return cityOrgCode;
    }

    public void setCityOrgCode(String cityOrgCode) {
        this.cityOrgCode = cityOrgCode;
    }

    public String getCountryOrgCode() {
        return countryOrgCode;
    }

    public void setCountryOrgCode(String countryOrgCode) {
        this.countryOrgCode = countryOrgCode;
    }
    
    public String getAuditStartOrgCode() {
        return auditStartOrgCode;
    }

    public void setAuditStartOrgCode(String auditStartOrgCode) {
        this.auditStartOrgCode = auditStartOrgCode;
    }

    public String getAuditEndOrgCode() {
        return auditEndOrgCode;
    }

    public void setAuditEndOrgCode(String auditEndOrgCode) {
        this.auditEndOrgCode = auditEndOrgCode;
    }

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	public ProductPresell getProductPresell() {
		return productPresell;
	}

	public void setProductPresell(ProductPresell productPresell) {
		this.productPresell = productPresell;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getCategoryTreePath() {
		return categoryTreePath;
	}

	public void setCategoryTreePath(String categoryTreePath) {
		this.categoryTreePath = categoryTreePath;
	}
    
}