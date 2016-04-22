/**
 * 文件名称:TestArea.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-31
 * 历史修改:  
 */
package com.froad.fft.test.remoteservice;

import com.froad.fft.api.service.AreaExportService;
import com.froad.fft.bean.page.*;
import com.froad.fft.dto.AreaDto;
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
public class TestArea
{
    @Resource
    AreaExportService areaExportService;

    @Test
    public void page()
    {
        PageDto page = new PageDto();
        PageFilterDto<AreaDto> pageFilter = new PageFilterDto<AreaDto>();

        AreaDto dto = new AreaDto();

        pageFilter.setFilterEntity(dto);
        page.setPageSize(5);
        page.setPageFilterDto(pageFilter);

        page = areaExportService.findAreaByPage(null, null, page);
        System.out.println(page.getResultsContent().size() + "条数：" + page.getTotalCount());

    }

}
