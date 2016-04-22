/**
 * 文件名称:TestFundsChannel.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-2
 * 历史修改:  
 */
package com.froad.fft.test.remoteservice;

import com.froad.fft.api.service.FundsChannelExportService;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.FundsChannelDto;
import com.froad.fft.enums.trans.TransPayChannel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/servlet/applicationContext-mapper-remote.xml", "classpath:context/servlet/applicationContext-service-remote.xml",
        "classpath:context/applicationContext.xml", "classpath:context/applicationContext-expand.xml"})


public class TestFundsChannel
{
    @Resource
    FundsChannelExportService fundsChannelExportService;

    @Test
    public void insert()
    {
        FundsChannelDto fundsChannelDto = new FundsChannelDto();
        fundsChannelDto.setShortName("shortName1");
        fundsChannelDto.setFullName("fullName1");
        fundsChannelDto.setChannelType(TransPayChannel.filmCard);
        fundsChannelDto.setPayOrg("payOrg1");

        Long Id = fundsChannelExportService.addFundsChannel(null, null, fundsChannelDto);
        System.out.println(Id);
    }

    @Test
    public void page()
    {
        PageDto page = new PageDto();
        PageFilterDto<FundsChannelDto> pageFilter = new PageFilterDto<FundsChannelDto>();

        FundsChannelDto dto = new FundsChannelDto();

        pageFilter.setFilterEntity(dto);
        page.setPageSize(5);
        page.setPageFilterDto(pageFilter);

        page = fundsChannelExportService.findFundsChannelByPage(null, null, page);
        System.out.println(page.getResultsContent().size() + "条数：" + page.getTotalCount());

    }


}
