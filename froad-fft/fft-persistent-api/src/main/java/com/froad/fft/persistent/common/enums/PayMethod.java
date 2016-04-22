package com.froad.fft.persistent.common.enums;


/**
 * 交易支付方式
 * @author FQ
 *
 */
public enum PayMethod {

	/**
	 * 贴膜卡支付
	 */
	film_card,

	/**
	 * 方付通积分
	 */
	points_fft,

	/**
	 * 银行积分
	 */
	points_bank,

	/**
	 * 方付通积分+贴膜卡支付
	 */
	points_fft_film_card,

	/**
	 * 银行积分+贴膜卡支付
	 */
	points_bank_film_card,

	/**
	 * 支付宝支付
	 */
	alipay,

	/**
	 * 支付宝+分分通积分
	 */
	alipay_fft_points,

	/**
	 * 支付宝+银行积分
	 */
	alipay_bank_points,

	/**
	 * 网银支付
	 */
	internate_bank,

	/**
	 * 网银支付+分分通积分
	 */
	internate_fft_points,

	/**
	 * 网银支付+银行积分
	 */
	internate_bank_points;;
}
