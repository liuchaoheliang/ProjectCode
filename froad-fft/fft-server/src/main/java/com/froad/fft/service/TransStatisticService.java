/**
 * 文件名称:TransStatisticService.java
 * 文件描述: 交易统计接口定义
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-4
 * 历史修改:  
 */
package com.froad.fft.service;

import com.froad.fft.persistent.bean.OutletStatistic;
import com.froad.fft.persistent.bean.TransStatistic;
import com.froad.fft.persistent.common.enums.TransType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 交易统计接口定义
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface TransStatisticService
{

    /**
     * 根据金额排行
     *
     * @return 根据金额排行
     */
    public List<TransStatistic> selectTransStatistic(Long clientId, Date beginTime, Date endTime, Long productId, TransType transType, String orderBy);

    /**
     * 分店统计数据
     *
     * @return 分店统计数据排名
     */
    public List<OutletStatistic> selectOutletStatistic(Long clientId, Long merchantId, TransType transType);

}
