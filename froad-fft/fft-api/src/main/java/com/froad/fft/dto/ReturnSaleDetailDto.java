package com.froad.fft.dto;

import java.io.Serializable;

/**
* @ClassName: ReturnSaleDetailDto
* @Description: 退/换货详细
* @author larry
* @date 2014-4-3 下午04:26:55
*
 */
public class ReturnSaleDetailDto implements Serializable{
	
	private Long id;
	
	private Long returnSaleId;//退货ID
	
	private Long productId;//商品ID
	private Integer quantity;//数量
	private String securitiesNo;//券号
	
	private ReturnSaleDto returnSaleDto;//关联数据
	
	public Long getReturnSaleId() {
		return returnSaleId;
	}
	public void setReturnSaleId(Long returnSaleId) {
		this.returnSaleId = returnSaleId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getSecuritiesNo() {
		return securitiesNo;
	}
	public void setSecuritiesNo(String securitiesNo) {
		this.securitiesNo = securitiesNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ReturnSaleDto getReturnSaleDto() {
		return returnSaleDto;
	}
	public void setReturnSaleDto(ReturnSaleDto returnSaleDto) {
		this.returnSaleDto = returnSaleDto;
	}
	
}
