/**
 * 文件名称:MerchantGroupUserDto.java
 * 文件描述: 商户用户组Dto
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class MerchantGroupUserDto implements Serializable
{
    private Long id;// ID
    private Date createTime; //创建日期

    private Long merchantId;//商户ID
    private Long merchantOutletId;//门店ID

    private Long sysUserId;//用户ID

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

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Long getMerchantOutletId()
    {
        return merchantOutletId;
    }

    public void setMerchantOutletId(Long merchantOutletId)
    {
        this.merchantOutletId = merchantOutletId;
    }

    public Long getSysUserId()
    {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId)
    {
        this.sysUserId = sysUserId;
    }
}
