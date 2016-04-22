package com.froad.CB.po.activity;

import com.froad.CB.common.Pager;
import com.froad.CB.po.Goods;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.po.user.User;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-7-24  
 * @version 1.0
 */
public class ActivityLotteryResult extends Pager {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -1343218112058815947L;	
	private Integer id;				//主键 自增100001001开始
	private String userId;			//用户ID --
	private String merchantId;		//商户ID --
	private String lotteryCustId;	//中奖信息表ID --
	private String type;			//类型 0-团购 1-积分兑换(目前主要是农产品) --
	private String securitiesNo;	//券号
	private String isConsume;		//是否消费 0-否 1-是
	private String consumeTime;		//消费时间
	private String expireTime;		//消费券或者打折优惠商品过期时间
	private Integer smsNumber;		//短信次数
	private String smsTime;			//短信时间	
	private String price;			//价格
	private String point;			//积分
	private String goodsId;			//商品ID
	private String state;			//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;		//创建时间
	private String updateTime;		//更新时间
	private String remark;			//备注
	private String field1;			//备用字段1
	private String field2;			//备用字段2
	private User user;				//用户(会员)
	private Goods goods;				//商品
	private String beCode;						//操作员工号
	private String beName;						//操作员昵称
	private Merchant merchant;		//商户
	private ActivityCustInfo activityCustInfo; //关联相关活动详情表
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}	
	public String getLotteryCustId() {
		return lotteryCustId;
	}
	public void setLotteryCustId(String lotteryCustId) {
		this.lotteryCustId = lotteryCustId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSecuritiesNo() {
		return securitiesNo;
	}
	public void setSecuritiesNo(String securitiesNo) {
		this.securitiesNo = securitiesNo;
	}
	public String getIsConsume() {
		return isConsume;
	}
	public void setIsConsume(String isConsume) {
		this.isConsume = isConsume;
	}
	public String getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public Integer getSmsNumber() {
		return smsNumber;
	}
	public void setSmsNumber(Integer smsNumber) {
		this.smsNumber = smsNumber;
	}
	public String getSmsTime() {
		return smsTime;
	}
	public void setSmsTime(String smsTime) {
		this.smsTime = smsTime;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public ActivityCustInfo getActivityCustInfo() {
		return activityCustInfo;
	}
	public void setActivityCustInfo(ActivityCustInfo activityCustInfo) {
		this.activityCustInfo = activityCustInfo;
	}
	public String getBeCode() {
		return beCode;
	}
	public void setBeCode(String beCode) {
		this.beCode = beCode;
	}
	public String getBeName() {
		return beName;
	}
	public void setBeName(String beName) {
		this.beName = beName;
	}	
	
}
