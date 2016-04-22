/**
 * 文件名：TestProductPresell.java
 * 版本信息：Version 1.0
 * 日期：2014年3月29日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.fft.test.remoteservice;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.api.service.ProductPresellExportService;
import com.froad.fft.api.service.impl.ProductPresellExportServiceImpl;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.ProductPresellDto;
import com.froad.fft.dto.StockPileDto;

import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 下午4:59:15
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/servlet/applicationContext-mapper-remote.xml", "classpath:context/servlet/applicationContext-service-remote.xml",
        "classpath:context/applicationContext.xml", "classpath:context/applicationContext-expand.xml"})
public class TestProductPresell
{
    @Resource
    private ProductPresellExportService presellExportService;

    @Test
    public void page()
    {
        PageDto page = new PageDto();
        PageFilterDto<ProductPresellDto> pageFilter = new PageFilterDto<ProductPresellDto>();

        ProductPresellDto productPresellDto = new ProductPresellDto();
        //		refunds.setId(1L);

        pageFilter.setFilterEntity(productPresellDto);
        page.setPageSize(2);
        page.setPageFilterDto(pageFilter);

        page = presellExportService.findProductPresellByPage(null, null, page);
        System.out.println(page.getResultsContent().size() + "条数：" + page.getTotalCount());


    }

    @Test
    public void testStatistic()
    {


        List<Map> transList = presellExportService.findPresellTransByProductId(null,null,7L);

        List<Map> statisticList = presellExportService.findPresellTransStatisticByProductId(null,null,7L);


        System.out.println();
    }


}
