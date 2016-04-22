package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-2下午4:42:11 
 */
public class MerchantTypePercentVo implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 6530544033219134837L;
	
	/**
	 * 商户类型
	 */
	private String type;
	/**
	 * 商户类型占比
	 */
	private BigDecimal percent;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
}
