package com.froad.fft.persistent.entity;

/**
 * 入库明细
 * @author FQ
 *
 */
public class StockIncomeDetail extends BaseEntity {
	
	private Long stockIncomeId;//入库ID
	
	private Long productId;//商品ID
	private Integer quantity;//数量
	private String price;//价格
	
}
