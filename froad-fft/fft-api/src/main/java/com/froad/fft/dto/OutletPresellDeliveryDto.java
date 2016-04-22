/**
 * 文件名：OutletPresellDeliveryDto.java
 * 版本信息：Version 1.0
 * 日期：2014年3月29日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.fft.dto;

import java.io.Serializable;

/**
 * 类描述：
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 下午1:56:32
 */
public class OutletPresellDeliveryDto implements Serializable
{
    private Long merchantOutletId;//商户门店ID
    private Long presellDeliveryId;//预售提货点ID

    public Long getMerchantOutletId()
    {
        return merchantOutletId;
    }

    public void setMerchantOutletId(Long merchantOutletId)
    {
        this.merchantOutletId = merchantOutletId;
    }

    public Long getPresellDeliveryId()
    {
        return presellDeliveryId;
    }

    public void setPresellDeliveryId(Long presellDeliveryId)
    {
        this.presellDeliveryId = presellDeliveryId;
    }

}
