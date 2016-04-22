/**
 * 文件名称:TransStatisticExportServiceImpl.java
 * 文件描述: 交易统计实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-4
 * 历史修改:  
 */
package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.TransStatisticExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.dto.SysClientDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.enums.TransStatisticType;
import com.froad.fft.persistent.api.TransStatisticMapper;
import com.froad.fft.persistent.bean.OutletStatistic;
import com.froad.fft.persistent.bean.TransStatistic;
import com.froad.fft.service.SysClientService;
import com.froad.fft.service.TransStatisticService;
import com.froad.fft.thirdparty.request.userengine.UserEngineFunc;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class TransStatisticExportServiceImpl implements TransStatisticExportService
{

    @Resource(name = "transStatisticServiceImpl")
    private TransStatisticService transStatisticService;

    @Resource(name = "sysClientServiceImpl")
    SysClientService sysClientService;

    @Resource
    private UserEngineFunc userEngineFunc;

    /**
     * 根据金额排行
     *
     * @return 根据金额排行
     */
    public List<Map<String, Object>> selectTransStatistic(ClientAccessType clientAccessType, ClientVersion clientVersion, Date dataDate, Long productId,
                                                          com.froad.fft.enums.trans.TransType transType, TransStatisticType statisticType)
    {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        SysClientDto clientDto = BeanToDtoSupport.loadbySysClientDto(sysClientService.findSysClientByNumber(clientAccessType.getCode()));
        com.froad.fft.persistent.common.enums.TransType _transType = null;

        if (null != transType)
        {
            _transType = com.froad.fft.persistent.common.enums.TransType.valueOf(transType.name());
        }
        if (null == dataDate)
        {
            dataDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataDate);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            dataDate = calendar.getTime();
        }


        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat _sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginTime = new Date();
        Date endTime = new Date();
        try
        {
            beginTime = _sf.parse(sf.format(dataDate) + " 00:00:00");
            endTime = _sf.parse(sf.format(dataDate) + " 23:59:59");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        List<TransStatistic> statisticList = transStatisticService.selectTransStatistic(clientDto.getId(), beginTime, endTime, productId, _transType, statisticType.getCode());

        for (TransStatistic statistic : statisticList)
        {
            Map<String, Object> temp = new HashMap<String, Object>();

            String totalPrice = "0.00";
            if (null != statistic.getTotalPrice())
            {
                totalPrice = (String.format("%.2f", statistic.getTotalPrice()));
            }
            temp.put("totalPrice", totalPrice);
            temp.put("orderTime", statistic.getOrderTime());
            temp.put("memberCode", statistic.getMemberCode());

            String mobile = "";
            UserResult userResult = userEngineFunc.queryByMemberCode(statistic.getMemberCode());
            if (userResult.getResult())
            {
                List<com.pay.user.dto.UserSpecDto> userList = userResult.getUserList();
                if (null != userList && 0 < userList.size())
                {
                    UserSpecDto userSpecDto = userList.get(0);
                    mobile = userSpecDto.getMobile();
                }
            }
            temp.put("mobile", mobile);

            result.add(temp);
        }

        return result;
    }

    public List<Map<String, String>> selectOutletStatistic(ClientAccessType clientAccessType, ClientVersion clientVersion, Long merchantId, com.froad.fft.enums.trans.TransType transType)
    {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        SysClientDto clientDto = BeanToDtoSupport.loadbySysClientDto(sysClientService.findSysClientByNumber(clientAccessType.getCode()));
        com.froad.fft.persistent.common.enums.TransType _transType = com.froad.fft.persistent.common.enums.TransType.valueOf(transType.name());
        List<OutletStatistic> statisticList = transStatisticService.selectOutletStatistic(clientDto.getId(), merchantId, _transType);
        //数据处理
        for (OutletStatistic statistic : statisticList)
        {
            Map<String, String> temp = new HashMap<String, String>();
            //总金额
            String price = "0.00";
            if (null != statistic.getPrice())
            {
                price = (String.format("%.2f", statistic.getPrice()));
            }
            temp.put("price", price);
            //总数量
            String quantity = "0";
            if (null != statistic.getQuantity())
            {
                quantity = new BigDecimal(statistic.getQuantity()).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
            }
            temp.put("quantity", quantity);

            String orderNum = "0";
            if (null != statistic.getOrderNum())
            {
                orderNum = String.valueOf(statistic.getOrderNum());
            }
            temp.put("orderNum", orderNum);

            //deliveryId
            temp.put("deliveryId", statistic.getDeliveryId().toString());
            temp.put("deliveryName", statistic.getDeliveryName());
            //防止空数据，过滤处理，如果真不存在，则设置为提货点数据,呵呵，很变态
            String outLetId = statistic.getDeliveryId().toString();
            String outletName = statistic.getDeliveryName();
            if (null != statistic.getOutletId())
            {
                outLetId = statistic.getOutletId().toString();
            }
            if (null != statistic.getOutletName())
            {
                outletName = statistic.getOutletName();
            }

            temp.put("outLetId", outLetId);
            temp.put("outletName", outletName);

            result.add(temp);
        }
        return result;
    }

}
