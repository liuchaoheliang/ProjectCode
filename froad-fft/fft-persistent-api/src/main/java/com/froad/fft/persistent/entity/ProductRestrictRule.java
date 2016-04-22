package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.RestrictType;

/**
 * 商品限购规则
 *
 * @author FQ
 */
public class ProductRestrictRule extends BaseEntity
{

    private RestrictType restrictType;//限购类型
    private Long productId;//商品
    private Integer quantity;//数量
    private String remark;//备注

    public RestrictType getRestrictType()
    {
        return restrictType;
    }

    public void setRestrictType(RestrictType restrictType)
    {
        this.restrictType = restrictType;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

}
