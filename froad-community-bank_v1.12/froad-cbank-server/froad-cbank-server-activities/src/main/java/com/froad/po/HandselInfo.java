/**
 * @Title: HandselInfo.java
 * @Package com.froad.po
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月8日
 * @version V1.0
 **/

package com.froad.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: HandselInfo
 * @Description: TODO
 * @author froad-Joker 2015年12月8日
 * @modify froad-Joker 2015年12月8日
 */

public class HandselInfo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 客户端
	 */
	private String clientId;
	/**
	 * 赠送人
	 */
	private Long handselMemberCode;
	/**
	 * 赠送方式 0注册送 1首单送
	 */
	private Boolean handselType;
	/**
	 * 赠送内容 1满减2红包3实物4.联盟积分5.银行积分
	 */
	private String handselValue;
	/**
	 * 赠送活动id
	 */
	private String handselActiveId;
	/**
	 * 满减的额度
	 */
	private Integer cutMoney;
	/**
	 * 赠送红包的代金券规则id
	 */
	private String handselVouchersActiveId;
	/**
	 * 赠送红包/代金券ID
	 */
	private String vouchersId;
	/**
	 * 赠送红包/代金券金额
	 */
	private Integer vouchersMoney;
	/**
	 * 赠送商品ID
	 */
	private String productId;
	/**
	 * 赠送银行积分
	 */
	private Integer bankIntegral;
	/**
	 * 赠送联盟积分
	 */
	private Integer unionIntegral;
	/**
	 * 推荐人奖励方式 0红包 1实物
	 */
	private String creAwardType;
	/**
	 * 推荐人
	 */
	private Long creMemberCode;
	/**
	 * 推荐人奖励红包的代金券规则id
	 */
	private String creVouchersActiveId;
	/**
	 * 推荐人奖励红包/代金券id
	 */
	private String creVouchersId;
	/**
	 * 推荐人奖励红包/代金券金额
	 */
	private Integer creVouchersMoney;
	/**
	 * 推荐人奖励实物的商品id
	 */
	private String creProductId;	
	/**
	 * 满赠的订单id
	 */	
	private String fullGiveOrderId;
	/**
	 * 赠送时间
	 */	
	private Date giveTime;

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

	public Long getHandselMemberCode() {
		return handselMemberCode;
	}

	public void setHandselMemberCode(Long handselMemberCode) {
		this.handselMemberCode = handselMemberCode;
	}

	public Boolean getHandselType() {
		return handselType;
	}

	public void setHandselType(Boolean handselType) {
		this.handselType = handselType;
	}

	public String getHandselValue() {
		return handselValue;
	}

	public void setHandselValue(String handselValue) {
		this.handselValue = handselValue;
	}

	public String getHandselActiveId() {
		return handselActiveId;
	}

	public void setHandselActiveId(String handselActiveId) {
		this.handselActiveId = handselActiveId;
	}

	public Integer getCutMoney() {
		return cutMoney;
	}

	public void setCutMoney(Integer cutMoney) {
		this.cutMoney = cutMoney;
	}

	public String getHandselVouchersActiveId() {
		return handselVouchersActiveId;
	}

	public void setHandselVouchersActiveId(String handselVouchersActiveId) {
		this.handselVouchersActiveId = handselVouchersActiveId;
	}

	public String getVouchersId() {
		return vouchersId;
	}

	public void setVouchersId(String vouchersId) {
		this.vouchersId = vouchersId;
	}

	public Integer getVouchersMoney() {
		return vouchersMoney;
	}

	public void setVouchersMoney(Integer vouchersMoney) {
		this.vouchersMoney = vouchersMoney;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getBankIntegral() {
		return bankIntegral;
	}

	public void setBankIntegral(Integer bankIntegral) {
		this.bankIntegral = bankIntegral;
	}

	public Integer getUnionIntegral() {
		return unionIntegral;
	}

	public void setUnionIntegral(Integer unionIntegral) {
		this.unionIntegral = unionIntegral;
	}

	public String getCreAwardType() {
		return creAwardType;
	}

	public void setCreAwardType(String creAwardType) {
		this.creAwardType = creAwardType;
	}

	public Long getCreMemberCode() {
		return creMemberCode;
	}

	public void setCreMemberCode(Long creMemberCode) {
		this.creMemberCode = creMemberCode;
	}

	public String getCreVouchersActiveId() {
		return creVouchersActiveId;
	}

	public void setCreVouchersActiveId(String creVouchersActiveId) {
		this.creVouchersActiveId = creVouchersActiveId;
	}

	public String getCreVouchersId() {
		return creVouchersId;
	}

	public void setCreVouchersId(String creVouchersId) {
		this.creVouchersId = creVouchersId;
	}

	public Integer getCreVouchersMoney() {
		return creVouchersMoney;
	}

	public void setCreVouchersMoney(Integer creVouchersMoney) {
		this.creVouchersMoney = creVouchersMoney;
	}

	public String getCreProductId() {
		return creProductId;
	}

	public void setCreProductId(String creProductId) {
		this.creProductId = creProductId;
	}

	public Date getGiveTime() {
		return giveTime;
	}

	public void setGiveTime(Date giveTime) {
		this.giveTime = giveTime;
	}

	public String getFullGiveOrderId() {
		return fullGiveOrderId;
	}

	public void setFullGiveOrderId(String fullGiveOrderId) {
		this.fullGiveOrderId = fullGiveOrderId;
	}
	
	
}
