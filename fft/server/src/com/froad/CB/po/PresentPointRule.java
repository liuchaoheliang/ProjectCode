package com.froad.CB.po;

import com.froad.CB.common.Pager;


	/**
	 * 类描述：积分兑换商品自动赠送积分规则表
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2014-2-24 下午05:29:25 
	 */
public class PresentPointRule extends Pager{
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;	
	private String goodExchangeRackId;//对应的积分兑换商品ID
	private String goodName; //商品名称
	private Integer state;//规则状态(1启用，0废弃)
	private String pointValue;//可赠送积分值
	private String activeTime;//规则生效时间
	private String expireTime;//规则失效时间
	
	private String createTime;
	private String updateTime; 
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodExchangeRackId() {
		return goodExchangeRackId;
	}
	public void setGoodExchangeRackId(String goodExchangeRackId) {
		this.goodExchangeRackId = goodExchangeRackId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getPointValue() {
		return pointValue;
	}
	public void setPointValue(String pointValue) {
		this.pointValue = pointValue;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
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
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}	
	
}
