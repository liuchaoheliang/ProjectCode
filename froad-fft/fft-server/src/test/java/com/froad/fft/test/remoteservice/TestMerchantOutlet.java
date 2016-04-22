/**
 * 文件名称:TestMerchantOutlet.java
 * 文件描述: 测试商户分店
 * 产品标识: fft
 * 单元描述: server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-1
 * 历史修改:  
 */
package com.froad.fft.test.remoteservice;

import com.froad.fft.api.service.MerchantOutletExportService;
import com.froad.fft.bean.page.*;
import com.froad.fft.dto.MerchantOutletDto;
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

public class TestMerchantOutlet
{
    @Resource
    MerchantOutletExportService merchantOutletExportService;

    @Test
    public void insert()
    {
        MerchantOutletDto dto = new MerchantOutletDto();
        dto.setName("测试分1店");
        dto.setFullName("牛逼测试分店1号1牙科");
        dto.setAddress("$%^&*()FGHJK11L$%^&*(FGHJ");
        dto.setBusinessHours("9:00-10:00");
        dto.setZip("201001");
        dto.setFax("01110191918");
        dto.setTel("122336366373");
        dto.setContactName("狗子");
        dto.setContactPhone("3312306746");
        dto.setMerchantId(Long.valueOf(2));


        Long Id = merchantOutletExportService.addMerchantOutlet(null, null, dto);
        System.out.println(Id);
    }

    @Test
    public void page()
    {
        PageDto page = new PageDto();
        PageFilterDto<MerchantOutletDto> pageFilter = new PageFilterDto<MerchantOutletDto>();

        MerchantOutletDto dto = new MerchantOutletDto();

        pageFilter.setFilterEntity(dto);
        page.setPageSize(5);
        page.setPageFilterDto(pageFilter);

        page = merchantOutletExportService.findMerchantOutletByPage(null, null, page);
        System.out.println(page.getResultsContent().size() + "条数：" + page.getTotalCount());

    }

    @Test
    public void unbound()
    {
        List<MerchantOutletDto> dtoList = merchantOutletExportService.selectAllUnboundtoPresellDeliveryOutlet();
        System.out.println(dtoList.size());
    }


}
