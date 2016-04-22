package com.froad.CB.po.activity;

import com.froad.CB.common.Pager;

public class SpringFestivalCoupon extends Pager{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -7263423554607016725L;

	private Integer id; //主键
	
	private String o2oInfoId; //o2o平台中奖信息id
	
	private String memberCode; //中奖人关联账户服务memberc_ode
	
	private String securitiesNo; //具体券号
	
	private String worthCash; //面值
	
	private Integer awardLevel; //奖品等级:从2到4
	
	private String type; //1:电影票;2:抵用券
	
	private String isGot; // 0:该券未中奖;1:该券已中奖
	
	private String gotTime; //中奖时间
	
	private String isConsume; //是否消费
	
	private String consumeTime; //消费时间
	
	private String merchantId; //验证商户Id
	
	private String beCode; //操作员工号
	
	private String beName; //操作员姓名
	
	private String state; //状态
	
	private String expireTime; //券过期时间
	
	private String updateTime; //数据修改时间
	
	private String createTime; //数据创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getO2oInfoId() {
		return o2oInfoId;
	}

	public void setO2oInfoId(String o2oInfoId) {
		this.o2oInfoId = o2oInfoId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getSecuritiesNo() {
		return securitiesNo;
	}

	public void setSecuritiesNo(String securitiesNo) {
		this.securitiesNo = securitiesNo;
	}

	public String getWorthCash() {
		return worthCash;
	}

	public void setWorthCash(String worthCash) {
		this.worthCash = worthCash;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsGot() {
		return isGot;
	}

	public void setIsGot(String isGot) {
		this.isGot = isGot;
	}

	public String getGotTime() {
		return gotTime;
	}

	public void setGotTime(String gotTime) {
		this.gotTime = gotTime;
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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getAwardLevel() {
		return awardLevel;
	}

	public void setAwardLevel(Integer awardLevel) {
		this.awardLevel = awardLevel;
	}


	
}
