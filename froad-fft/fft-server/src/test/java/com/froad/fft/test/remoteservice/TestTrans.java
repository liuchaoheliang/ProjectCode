/**
 * 文件名称:TestTrans.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.test.remoteservice;

import com.froad.fft.api.service.TransExportService;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.PayDto;
import com.froad.fft.dto.TransDetailDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

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
public class TestTrans
{
    @Resource
    private TransExportService transExportService;

    @Test
    public void page()
    {
        PageDto page = new PageDto();
        PageFilterDto<TransQueryDto> pageFilter = new PageFilterDto<TransQueryDto>();

        TransQueryDto productPresellDto = new TransQueryDto();
        //		refunds.setId(1L);

        pageFilter.setFilterEntity(productPresellDto);
        page.setPageSize(2);
        page.setPageFilterDto(pageFilter);

        page = transExportService.findTransByPage(null, null, page);
        System.out.println(page.getResultsContent().size() + "条数：" + page.getTotalCount());


        List<PayDto> payDtoList = transExportService.findPaysByTransId(null, null, Long.valueOf(6));
        System.out.println("条数：" + payDtoList.size());

        List<TransDetailDto> detailDtoList = transExportService.findTransDetailsByTransId(null, null, Long.valueOf(6));
        System.out.println("条数：" + detailDtoList.size());

        TransQueryDto dto = transExportService.findTransById(null, null, Long.valueOf(6));
        System.out.println(dto.getSn());
    }
    
    @Test
    public void queryPresellState(){
    	String sn="140415162044114";
    	String state=transExportService.queryPresellState(ClientAccessType.chongqing, ClientVersion.version_1_0, sn);
    	System.out.println(state);
    }

}
