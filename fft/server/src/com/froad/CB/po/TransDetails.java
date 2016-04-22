package com.froad.CB.po;


public class TransDetails {

	private Integer id;
	private Integer transId;	//交易ID
	private String goodsRackId;	//交易架商品ID
	private Goods goods;//java代码中关联出的基础商品
//	private Object baseGoods;//关联出的交易架商品
	/**关联出的各种交易品 begin***/
	private GoodsGroupRack goodsGroupRack;
	private GoodsExchangeRack goodsExchangeRack;
	private GoodsGatherRack goodsGatherRack;
	private GoodsCarryRack goodsCarryRack;
	private GoodsPresellRack goodsPresellRack;
	
	private ClientGoodsGroupRack clientGoodsGroupRack;
	private ClientGoodsExchangeRack clientGoodsExchangeRack;
	private ClientGoodsGatherRack clientGoodsGatherRack;
	private ClientGoodsCarryRack clientGoodsCarryRack;
	/**关联出的各种交易品 end****/
	private String goodsNumber;	//购买数量
	private String buyersRuleId;//买家规则ID
	private String buyersRuleDesc;//买家规则描述
	private String sellerRuleId;//卖家规则ID
	private String sellerRuleDesc;//卖家规则描述
	private String currency;	//货币单位
	private String costpriceTotal;//原价总和
	private String currencyValue; //货币值
	private String currencyValueReal;		//实际货币值
	private String bankPointsValueAll;		//银行总积分值，所有交易明细累计
	private String bankPointsValueRealAll;	//银行总实际积分数值，由规则计算出
	private String fftPointsValueAll;		//分分通总积分值，所有交易明细累计
	private String fftPointsValueRealAll;	//分分通总实际积分数值，由规则计算出
	private String state;		//状态
	private String createTime;	//创建时间
	private String updateTime;	//更新时间'
	private String remark;		//备注
	
	/***用作传递参数**/
	private String goodsCategoryId;//上架商品编号
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTransId() {
		return transId;
	}

	public void setTransId(Integer transId) {
		this.transId = transId;
	}

	public String getGoodsRackId() {
		return goodsRackId;
	}

	public void setGoodsRackId(String goodsRackId) {
		this.goodsRackId = goodsRackId == null ? null : goodsRackId.trim();
	}

	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber == null ? null : goodsNumber.trim();
	}

	public String getBuyersRuleId() {
		return buyersRuleId;
	}

	public void setBuyersRuleId(String buyersRuleId) {
		this.buyersRuleId = buyersRuleId == null ? null : buyersRuleId.trim();
	}

	public String getBuyersRuleDesc() {
		return buyersRuleDesc;
	}

	public void setBuyersRuleDesc(String buyersRuleDesc) {
		this.buyersRuleDesc = buyersRuleDesc == null ? null : buyersRuleDesc
				.trim();
	}

	public String getSellerRuleId() {
		return sellerRuleId;
	}

	public void setSellerRuleId(String sellerRuleId) {
		this.sellerRuleId = sellerRuleId == null ? null : sellerRuleId.trim();
	}

	public String getSellerRuleDesc() {
		return sellerRuleDesc;
	}

	public void setSellerRuleDesc(String sellerRuleDesc) {
		this.sellerRuleDesc = sellerRuleDesc == null ? null : sellerRuleDesc
				.trim();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public String getCostpriceTotal() {
		return costpriceTotal;
	}

	public void setCostpriceTotal(String costpriceTotal) {
		this.costpriceTotal = costpriceTotal == null ? null : costpriceTotal
				.trim();
	}

	public String getCurrencyValue() {
		return currencyValue;
	}

	public void setCurrencyValue(String currencyValue) {
		this.currencyValue = currencyValue == null ? null : currencyValue
				.trim();
	}

	public String getCurrencyValueReal() {
		return currencyValueReal;
	}

	public void setCurrencyValueReal(String currencyValueReal) {
		this.currencyValueReal = currencyValueReal == null ? null
				: currencyValueReal.trim();
	}

	public String getBankPointsValueAll() {
		return bankPointsValueAll;
	}

	public void setBankPointsValueAll(String bankPointsValueAll) {
		this.bankPointsValueAll = bankPointsValueAll == null ? null
				: bankPointsValueAll.trim();
	}

	public String getBankPointsValueRealAll() {
		return bankPointsValueRealAll;
	}

	public void setBankPointsValueRealAll(String bankPointsValueRealAll) {
		this.bankPointsValueRealAll = bankPointsValueRealAll == null ? null
				: bankPointsValueRealAll.trim();
	}

	public String getFftPointsValueAll() {
		return fftPointsValueAll;
	}

	public void setFftPointsValueAll(String fftPointsValueAll) {
		this.fftPointsValueAll = fftPointsValueAll == null ? null
				: fftPointsValueAll.trim();
	}

	public String getFftPointsValueRealAll() {
		return fftPointsValueRealAll;
	}

	public void setFftPointsValueRealAll(String fftPointsValueRealAll) {
		this.fftPointsValueRealAll = fftPointsValueRealAll == null ? null
				: fftPointsValueRealAll.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public GoodsGroupRack getGoodsGroupRack() {
		return goodsGroupRack;
	}

	public void setGoodsGroupRack(GoodsGroupRack goodsGroupRack) {
		this.goodsGroupRack = goodsGroupRack;
	}

	public GoodsExchangeRack getGoodsExchangeRack() {
		return goodsExchangeRack;
	}

	public void setGoodsExchangeRack(GoodsExchangeRack goodsExchangeRack) {
		this.goodsExchangeRack = goodsExchangeRack;
	}

	public GoodsGatherRack getGoodsGatherRack() {
		return goodsGatherRack;
	}

	public void setGoodsGatherRack(GoodsGatherRack goodsGatherRack) {
		this.goodsGatherRack = goodsGatherRack;
	}

	public ClientGoodsGroupRack getClientGoodsGroupRack() {
		return clientGoodsGroupRack;
	}

	public void setClientGoodsGroupRack(ClientGoodsGroupRack clientGoodsGroupRack) {
		this.clientGoodsGroupRack = clientGoodsGroupRack;
	}

	public ClientGoodsExchangeRack getClientGoodsExchangeRack() {
		return clientGoodsExchangeRack;
	}

	public void setClientGoodsExchangeRack(
			ClientGoodsExchangeRack clientGoodsExchangeRack) {
		this.clientGoodsExchangeRack = clientGoodsExchangeRack;
	}

	public ClientGoodsGatherRack getClientGoodsGatherRack() {
		return clientGoodsGatherRack;
	}

	public void setClientGoodsGatherRack(ClientGoodsGatherRack clientGoodsGatherRack) {
		this.clientGoodsGatherRack = clientGoodsGatherRack;
	}

	public GoodsCarryRack getGoodsCarryRack() {
		return goodsCarryRack;
	}

	public void setGoodsCarryRack(GoodsCarryRack goodsCarryRack) {
		this.goodsCarryRack = goodsCarryRack;
	}

	public ClientGoodsCarryRack getClientGoodsCarryRack() {
		return clientGoodsCarryRack;
	}

	public void setClientGoodsCarryRack(ClientGoodsCarryRack clientGoodsCarryRack) {
		this.clientGoodsCarryRack = clientGoodsCarryRack;
	}

	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}

	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}

	public GoodsPresellRack getGoodsPresellRack() {
		return goodsPresellRack;
	}

	public void setGoodsPresellRack(GoodsPresellRack goodsPresellRack) {
		this.goodsPresellRack = goodsPresellRack;
	}

}