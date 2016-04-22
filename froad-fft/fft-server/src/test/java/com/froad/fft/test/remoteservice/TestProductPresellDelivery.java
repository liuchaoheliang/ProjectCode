/**
 * 文件名称:TestProductPresellDelivery.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.test.remoteservice;

import com.froad.fft.api.service.ProductPresellDeliveryExportService;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.ProductPresellDeliveryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/servlet/applicationContext-mapper-remote.xml", "classpath:context/servlet/applicationContext-service-remote.xml",
        "classpath:context/applicationContext.xml", "classpath:context/applicationContext-expand.xml"})
public class TestProductPresellDelivery
{
    @Resource
    private ProductPresellDeliveryExportService productPresellDeliveryExportService;

    @Test
    public void insert()
    {
        ProductPresellDeliveryDto dto1 = new ProductPresellDeliveryDto();
        dto1.setProductPresellId(Long.valueOf(1));
        dto1.setPresellDeliveryId(Long.valueOf(11));


        ProductPresellDeliveryDto dto2 = new ProductPresellDeliveryDto();
        dto2.setProductPresellId(Long.valueOf(1));
        dto2.setPresellDeliveryId(Long.valueOf(12));

        ProductPresellDeliveryDto dto3 = new ProductPresellDeliveryDto();
        dto3.setProductPresellId(Long.valueOf(1));
        dto3.setPresellDeliveryId(Long.valueOf(13));

        Long Id = productPresellDeliveryExportService.saveProductPresellDelivery(null, null, dto1);
        System.out.println(Id);
//        Id = productPresellDeliveryExportService.saveProductPresellDelivery(null, null, dto2);
//        System.out.println(Id);
//        Id = productPresellDeliveryExportService.saveProductPresellDelivery(null, null, dto3);
//        System.out.println(Id);
    }

    @Test
    public void page()
    {

        List<ProductPresellDeliveryDto> list = productPresellDeliveryExportService.selectByProductPresellId(null, null, Long.valueOf(1));
        System.out.println(list.size() + "条数：");

    }
}
