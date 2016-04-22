package com.froad.cbank.coremodule.normal.boss.pojo.product;

/**
 * boss商品管理列表实体类
 * @author yfy
 * @date 2015年7月27日 上午10:30:12
 */
public class BossProductVoRes {

	private Long id; //主键ID
  	private String createTime; //创建时间
  	private String productId; // 商品编号
  	private String name; // 商品名称
  	private String type; //商品类型 "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品;
  	private String categoryName; // 商品分类名称
  	private String clientId; // 客户端id
  	private String clientName; //客户端名称即所属行
  	private String bankName; //所属银行
  	private String platType; // 录入渠道 "1":boss,"2":银行端,"3":商户pc,"4":商户h5,"5":个人pc,"6":个人h5;
  	private String auditStatus; //审核状态0:待审核,1：审核通过,2:审核未通过,3：未提交
  	private String marketableStatus; // 上下架状态
	  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getPlatType() {
		return platType;
	}
	public void setPlatType(String platType) {
		this.platType = platType;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getMarketableStatus() {
		return marketableStatus;
	}
	public void setMarketableStatus(String marketableStatus) {
		this.marketableStatus = marketableStatus;
	}
	  
}
