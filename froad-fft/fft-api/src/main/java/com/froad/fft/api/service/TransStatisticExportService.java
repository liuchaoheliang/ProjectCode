/**
 * 文件名称:TransStatisticExportService.java
 * 文件描述: 交易统计
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-4
 * 历史修改:  
 */
package com.froad.fft.api.service;

import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.enums.TransStatisticType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 交易统计
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface TransStatisticExportService
{

    /**
     * 根据金额排行
     *
     * @return 根据金额排行
     */
    public List<Map<String, Object>> selectTransStatistic(ClientAccessType clientAccessType, ClientVersion clientVersion, Date dataDate, Long productId, com.froad.fft.enums.trans.TransType transType,TransStatisticType statisticType);


    /**
     * 分店统计数据
     *
     * @return 分店统计数据排名
     */
    public List<Map<String, String>> selectOutletStatistic(ClientAccessType clientAccessType, ClientVersion clientVersion, Long merchantId, com.froad.fft.enums.trans.TransType transType);


}
