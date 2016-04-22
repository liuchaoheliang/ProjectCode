/**
 * 文件名称:TransStatisticServiceImpl.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-4
 * 历史修改:  
 */
package com.froad.fft.service.impl;

import com.froad.fft.persistent.api.TransStatisticMapper;
import com.froad.fft.persistent.bean.OutletStatistic;
import com.froad.fft.persistent.bean.TransStatistic;
import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.service.TransStatisticService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
@Service("transStatisticServiceImpl")
public class TransStatisticServiceImpl implements TransStatisticService
{
    private static Logger logger = Logger.getLogger(TransStatisticServiceImpl.class);

    @Resource
    private TransStatisticMapper transStatisticMapper;

    public List<TransStatistic> selectTransStatistic(Long clientId, Date beginTime, Date endTime, Long productId, TransType transType, String orderBy)
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("clientId", clientId);
        para.put("beginTime", beginTime);
        para.put("endTime", endTime);
        para.put("productId", productId);
        para.put("transType", transType);
        para.put("orderby", orderBy);
        return transStatisticMapper.selectTransStatistic(para);
    }

    /**
     * 分店统计数据
     *
     * @return 分店统计数据排名
     */
    public List<OutletStatistic> selectOutletStatistic(Long clientId, Long merchantId, TransType transType)
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("clientId", clientId);
        para.put("merchantId", merchantId);
        para.put("transType", transType);
        return transStatisticMapper.selectOutletStatistic(para);
    }

}
