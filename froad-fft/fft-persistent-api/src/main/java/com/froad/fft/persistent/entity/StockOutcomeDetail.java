package com.froad.fft.persistent.entity;

/**
 * 出库明细
 * @author FQ
 *
 */
public class StockOutcomeDetail extends BaseEntity {

	private Long stockOutcomeId;//出库ID
	
	private Long productId;//商品ID
	private Integer quantity;//数量
	private String price;//价格
}
