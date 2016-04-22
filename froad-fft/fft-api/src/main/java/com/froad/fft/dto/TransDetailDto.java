/**
 * 文件名称:TransDetailDto.java
 * 文件描述: 交易明细数据
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class TransDetailDto implements Serializable
{

    private Long id;
    private Date createTime;

    private Long transId;//交易ID
    private Long productId;//商品ID
    private String productName;//商品名称
    private Integer quantity;//购买数量
    private String price;//金额(=单价*购买数量)
    private String single;//单价

    private Long supplyMerchantId;//商品所属商户Id
    private Long gatherMerchantId;//收款商户Id
    
    private ProductPresellDto productPresellDto;//交易关联预售商品

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Long getTransId()
    {
        return transId;
    }

    public void setTransId(Long transId)
    {
        this.transId = transId;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getSingle()
    {
        return single;
    }

    public void setSingle(String single)
    {
        this.single = single;
    }

    public Long getSupplyMerchantId()
    {
        return supplyMerchantId;
    }

    public void setSupplyMerchantId(Long supplyMerchantId)
    {
        this.supplyMerchantId = supplyMerchantId;
    }

    public Long getGatherMerchantId()
    {
        return gatherMerchantId;
    }

    public void setGatherMerchantId(Long gatherMerchantId)
    {
        this.gatherMerchantId = gatherMerchantId;
    }

	public ProductPresellDto getProductPresellDto() {
		return productPresellDto;
	}

	public void setProductPresellDto(ProductPresellDto productPresellDto) {
		this.productPresellDto = productPresellDto;
	}
    
}
