/**
 * 文件名称:TransStatisticMapper.java
 * 文件描述: 交易统计
 * 产品标识: fft
 * 单元描述: fft-persistent
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-4
 * 历史修改:  
 */
package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.OutletStatistic;
import com.froad.fft.persistent.bean.TransStatistic;

import java.util.List;
import java.util.Map;

/**
 * 交易统计
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface TransStatisticMapper
{

    /**
     * 根据金额排行
     *
     * @return 根据金额排行
     */
    public List<TransStatistic> selectTransStatistic(Map<String, Object> para);



    /**
     * 分店统计数据
     *
     * @return 分店统计数据排名
     */
    public List<OutletStatistic> selectOutletStatistic(Map<String, Object> para);
}
