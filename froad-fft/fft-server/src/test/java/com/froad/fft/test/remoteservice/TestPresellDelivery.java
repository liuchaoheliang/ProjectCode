/**
 * 文件名：TestPresellDelivery.java
 * 版本信息：Version 1.0
 * 日期：2014年3月29日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.fft.test.remoteservice;

import javax.annotation.Resource;

import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.api.service.PresellDeliveryExportService;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.PresellDeliveryDto;
import com.froad.fft.dto.RefundsDto;

/**
 * 类描述：
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 下午8:07:35
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/servlet/applicationContext-mapper-remote.xml", "classpath:context/servlet/applicationContext-service-remote.xml",
        "classpath:context/applicationContext.xml", "classpath:context/applicationContext-expand.xml"})
public class TestPresellDelivery
{

    @Resource
    PresellDeliveryExportService deliveryExportService;

    @Test
    public void page()
    {
        PageDto page = new PageDto();
        PageFilterDto<PresellDeliveryDto> pageFilter = new PageFilterDto<PresellDeliveryDto>();

        PresellDeliveryDto presellDeliveryDto = new PresellDeliveryDto();

        pageFilter.setFilterEntity(presellDeliveryDto);
        page.setPageSize(5);
        page.setPageFilterDto(pageFilter);

        page = deliveryExportService.findPresellDeliveryByPage(null, null, page);
        System.out.println(page.getResultsContent().size() + "条数：" + page.getTotalCount());

    }

    @Test
    public void add()
    {
        PresellDeliveryDto dto = new PresellDeliveryDto();


        long clientId = 10;
        long businessCircleId = 100;
        dto.setName("test Nam1e1");
        dto.setAddress("des1ert Land1");
        dto.setTelephone("1123211903931");
        dto.setBusinessTime("1109000-0901001");
        dto.setCoordinate("2,21");
        dto.setOrderValue(20);
        dto.setClientId(clientId);
        dto.setBusinessCircleId(businessCircleId);

        Long id = deliveryExportService.savePresellDelivery(null, null, dto);
        System.out.println("保存Id:" + id);

    }

}
