package com.froad.CB.po;

import java.util.ArrayList;
import java.util.List;

import com.froad.CB.common.Pager;
import com.froad.CB.po.merchant.Merchant;

public class Goods extends Pager{
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;			//
	private String merchantId;	//商户ID
	private Merchant merchant;//关联出的商户
	private String goodsSn;		//商品编号
	private String goodsName;	//名称
	private String originPrice;//商品进价
	private String price;		//商品价格
	private String marketPrice;	//市场价格
	private String weight;		//商品重量
	private String weightUnit;	//重量单位 （g、kg、t）
	private Integer store;		//商品库存数量
	private Integer freezeStore;	//冻结库存数量
	private String warnNumber;	//警告数量
	private String isBest;		//是否精品 0-否 1-是
	private String isNew;		//是否新品 0-否 1-是
	private String isHot;		//是否热销 0-否 1-是
	private String keywords;	//关键字
	private String goodsDesc;	//描述
	private String sourceUrl;	//商品图片原路径
	private String bigUrl;		//商品图片大路径
	private String smallUrl;	//商品图片小路径
	private String thumbnailUrl;//商品图片缩略图路径
	private String goodsCategoryId;		//商品分类ID
	private GoodsCategory goodsCategory;//
	private String brandId;		//品牌ID
	private String goodsType;	//类型ID
	private String state;		//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;	//创建时间
	private String updateTime;	//更新时间
	private String remark;		//备注
	private String goodsImageListStore;//图片路径存储
	private String isAuto;//是否为自动添加的商品 0:否  1：是
	
	
	//通过goods_rackid加对应table查询商品名事使用的临时字段
	private String goodsRockId;
	private String table;
	private List<GoodsRackAttribute> goodsRackAttrList=new ArrayList<GoodsRackAttribute>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId == null ? null : merchantId.trim();
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn == null ? null : goodsSn.trim();
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName == null ? null : goodsName.trim();
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price == null ? null : price.trim();
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice == null ? null : marketPrice.trim();
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight == null ? null : weight.trim();
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit == null ? null : weightUnit.trim();
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public Integer getFreezeStore() {
		return freezeStore;
	}

	public void setFreezeStore(Integer freezeStore) {
		this.freezeStore = freezeStore;
	}

	public String getWarnNumber() {
		return warnNumber;
	}

	public void setWarnNumber(String warnNumber) {
		this.warnNumber = warnNumber == null ? null : warnNumber.trim();
	}

	public String getIsBest() {
		return isBest;
	}

	public void setIsBest(String isBest) {
		this.isBest = isBest == null ? null : isBest.trim();
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew == null ? null : isNew.trim();
	}

	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot == null ? null : isHot.trim();
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords == null ? null : keywords.trim();
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc == null ? null : goodsDesc.trim();
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl == null ? null : sourceUrl.trim();
	}

	public String getBigUrl() {
		return bigUrl;
	}

	public void setBigUrl(String bigUrl) {
		this.bigUrl = bigUrl == null ? null : bigUrl.trim();
	}

	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl == null ? null : smallUrl.trim();
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl == null ? null : thumbnailUrl.trim();
	}

	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}

	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId == null ? null : goodsCategoryId
				.trim();
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId == null ? null : brandId.trim();
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType == null ? null : goodsType.trim();
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

	public String getGoodsImageListStore() {
		return goodsImageListStore;
	}

	public void setGoodsImageListStore(String goodsImageListStore) {
		this.goodsImageListStore = goodsImageListStore;
	}

	public GoodsCategory getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(GoodsCategory goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public List<GoodsRackAttribute> getGoodsRackAttrList() {
		return goodsRackAttrList;
	}

	public void setGoodsRackAttrList(List<GoodsRackAttribute> goodsRackAttrList) {
		this.goodsRackAttrList = goodsRackAttrList;
	}

	public String getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(String originPrice) {
		this.originPrice = originPrice;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public String getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}


	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getGoodsRockId() {
		return goodsRockId;
	}

	public void setGoodsRockId(String goodsRockId) {
		this.goodsRockId = goodsRockId;
	}

}