/**
 * 文件名称:PresellTrans.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-23
 * 历史修改:  
 */
package com.froad.fft.persistent.entity;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class PresellTrans  extends BaseEntity
{

    private Double quantity;

    private String name;

    public Double getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Double quantity)
    {
        this.quantity = quantity;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
