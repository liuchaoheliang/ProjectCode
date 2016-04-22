/**
 * 文件名称:TestAgreement.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-9
 * 历史修改:  
 */
package com.froad.fft.test.remoteservice;

import com.froad.fft.api.service.AgreementExportService;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.AgreementDto;
import com.froad.fft.enums.AgreementType;
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
public class TestAgreement
{
    @Resource
    AgreementExportService agreementExportService;

    @Test
    public void insert()
    {
        AgreementDto dto = new AgreementDto();
        dto.setClientId(1L);
        dto.setContent("content");
        dto.setType(AgreementType.register);
        Long id = agreementExportService.addAgreement(null, null, dto);

        System.out.println(id);

    }
    @Test
    public void update()
    {
        AgreementDto dto  = agreementExportService.findAgreementById(null,null,1L);
        dto.setContent("xilihuala");
        Boolean flag = agreementExportService.updateAgreementById(null,null,dto);

        System.out.println(flag);
    }

    @Test
    public void page()
    {
        PageDto page = new PageDto();
        PageFilterDto<AgreementDto> pageFilter = new PageFilterDto<AgreementDto>();

        AgreementDto dto = new AgreementDto();

        pageFilter.setFilterEntity(dto);
        page.setPageSize(5);
        page.setPageFilterDto(pageFilter);

        page = agreementExportService.findAgreementByPage(null, null, page);
        System.out.println(page.getResultsContent().size() + "条数：" + page.getTotalCount());

    }


}
