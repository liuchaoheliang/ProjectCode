/**
 * 文件名称:MerchantOutletTest.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-29
 * 历史修改:  
 */
package com.froad.fft.test.remoteservice;

import com.froad.fft.api.service.MerchantOutletExportService;
import com.froad.fft.api.service.TransStatisticExportService;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.trans.TransType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/servlet/applicationContext-mapper-remote.xml", "classpath:context/servlet/applicationContext-service-remote.xml",
        "classpath:context/applicationContext.xml", "classpath:context/applicationContext-expand.xml"})
public class MerchantOutletTest
{
    @Resource
    private TransStatisticExportService transStatisticExportService;

    @Test
    public void statistic()
    {
        List<Map<String, String>> statisticList = transStatisticExportService.selectOutletStatistic(ClientAccessType.chongqing, null,  12L, TransType.presell);
        int i = 0;

        for (Map<String, String> temp : statisticList)
        {
            System.out.println(++i);
            System.out.println(temp.get("deliveryId"));
            System.out.println(temp.get("deliveryName"));
            System.out.println(temp.get("outLetId"));
            System.out.println(temp.get("outletName"));
            System.out.println(temp.get("price"));
            System.out.println(temp.get("quantity"));

            System.out.println();

        }

    }


}
