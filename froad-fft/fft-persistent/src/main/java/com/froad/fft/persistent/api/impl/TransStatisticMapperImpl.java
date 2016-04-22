/**
 * 文件名称:TransStatisticMapperImpl.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-4
 * 历史修改:  
 */
package com.froad.fft.persistent.api.impl;

import com.froad.fft.persistent.api.TransStatisticMapper;
import com.froad.fft.persistent.bean.OutletStatistic;
import com.froad.fft.persistent.bean.TransStatistic;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class TransStatisticMapperImpl implements TransStatisticMapper
{
    @Resource
    TransStatisticMapper transStatisticMapper;

    public List<TransStatistic> selectTransStatistic(Map<String, Object> para)
    {
        return transStatisticMapper.selectTransStatistic(para);
    }


    public List<OutletStatistic> selectOutletStatistic(Map<String, Object> para)
    {
        return transStatisticMapper.selectOutletStatistic(para);
    }
}
