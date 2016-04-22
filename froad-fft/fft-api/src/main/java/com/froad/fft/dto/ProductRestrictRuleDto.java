/**
 * 文件名称:ProductRestrictRuleDto.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-21
 * 历史修改:  
 */
package com.froad.fft.dto;

import com.froad.fft.enums.RestrictType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class ProductRestrictRuleDto implements Serializable
{

    private Long id;// ID
    private Date createTime; //创建日期

    private RestrictType restrictType;//限购类型
    private Long productId;//商品
    private Integer quantity;//数量
    private String remark;//备注

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

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }
}
