/**
 * 文件名称:TransStatisticTest.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-5
 * 历史修改:  
 */
package com.froad.fft.test.remoteservice;

import com.froad.fft.api.service.TransStatisticExportService;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.TransStatisticType;
import com.froad.fft.enums.trans.TransType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/servlet/applicationContext-mapper-remote.xml", "classpath:context/servlet/applicationContext-service-remote.xml",
        "classpath:context/applicationContext.xml", "classpath:context/applicationContext-expand.xml"})

public class TransStatisticTest
{

    @Resource
    private TransStatisticExportService transStatisticExportService;

    @Test
    public void testStatistic() throws Exception
    {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date dataDate = sf.parse("2014-4-24");
        SimpleDateFormat _sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i = 0;
        List<Map<String, Object>> statisticList = transStatisticExportService.selectTransStatistic(ClientAccessType.chongqing, null, dataDate, 22L, TransType.presell, TransStatisticType.TOTALPRICE);


        for (Map<String, Object> temp : statisticList)
        {
            System.out.println(++i);
            System.out.println(temp.get("memberCode"));
            System.out.println(temp.get("mobile"));
            System.out.println(temp.get("totalPrice"));
            Date orderTime = (Date) temp.get("orderTime");
            System.out.println(_sf.format(orderTime));

            System.out.println();
        }

        //下单时间排行
        System.out.println("下单时间排行");
        statisticList = transStatisticExportService.selectTransStatistic(ClientAccessType.chongqing, null, dataDate, 22L, TransType.presell, TransStatisticType.ORDERTIME);
        i = 0;
        for (Map<String, Object> temp : statisticList)
        {
            System.out.println(++i);
            System.out.println(temp.get("memberCode"));
            System.out.println(temp.get("mobile"));
            System.out.println(temp.get("totalPrice"));
            Date orderTime = (Date) temp.get("orderTime");
            System.out.println(_sf.format(orderTime));

            System.out.println();
        }
    }
}
