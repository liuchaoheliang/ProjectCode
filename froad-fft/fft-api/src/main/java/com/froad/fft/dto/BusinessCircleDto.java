/**
 * 文件名称:BusinessCircleDto.java
 * 文件描述: BusinessCircle 的Dto
 * 产品标识: 分分通
 * 单元描述: fft-api
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-26
 * 历史修改:  
 */
package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Aides
 * @version 1.0
 */
public class BusinessCircleDto extends TreeDto
{
    private String name;// 商圈名称
    private Integer orderValue;// 排序
    private Long areaId;//所属地区

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getOrderValue()
    {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue)
    {
        this.orderValue = orderValue;
    }

    public Long getAreaId()
    {
        return areaId;
    }

    public void setAreaId(Long areaId)
    {
        this.areaId = areaId;
    }
}
